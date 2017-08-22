Attribute VB_Name = "modLogIn"
Option Explicit

Private mstrUserID   As String
Private mstrPassword As String
Private matUserID    As AccessType

'* Note : for this to work properly, each
'* entry *MUST* be double the previous entry.
'* Converted to binary, each access type is represented by a bit
Public Enum AccessType
 atGuest = 1
 atWeb = 2
 atCustomerServ = 4
 atAccounts = 8
 atHostMaster = 16
 atLeadHostMaster = 32
 atBatch = 64
 atSuperUser = 128
End Enum


Public Function LogIn(ByVal strUser As String, ByVal strPassword As String) As Boolean
 Dim strSQL, strSQL1  As String
 Dim rec      As ADODB.Recordset
 
'CC20060118 implement SHA1 password hashes, ensuring continued compatibility when OLD_PASSWORD is removed from MySQL
 Dim Hash     As String
 Dim UseHash  As Boolean
On Error Resume Next
 LogIn = False
 Hash = String(40, " ")
 HASH_HexFromString Hash, 40, strPassword, Len(strPassword), PKI_HASH_SHA1

 strSQL = "SELECT NH_Level, NH_Password "
 strSQL = strSQL & "FROM Access "
 strSQL = strSQL & "WHERE Nic_Handle=" & CDBText(strUser)
 DB.Execute strSQL, rec
   
 If Not rec.EOF Then 'if Nic_Handle exists
    If NoNull(rec.Fields("NH_Password")) <> Hash Then 'if correct sha1 password not supplied
        strSQL = "SELECT NH_Level "
        strSQL = strSQL & "FROM Access "
        strSQL = strSQL & "WHERE Nic_Handle=" & CDBText(strUser) & " "
        strSQL = strSQL & "AND NH_Password=OLD_PASSWORD(" & CDBText(strPassword) & ")"
        DB.Execute strSQL, rec
    End If
    If Not rec.EOF Then 'rec contains only valid values (if not empty)
       SetUserID strUser, strPassword, NoNull(rec.Fields("NH_Level"))
       If HasAuthority(atCustomerServ + atAccounts + atHostMaster + atLeadHostMaster + atSuperUser) Then
          LogIn = True
       Else
          LogIn = False
       End If
    End If
 End If

' strSQL = "SELECT NH_Level "
' strSQL = strSQL & "FROM Access "
' strSQL = strSQL & "WHERE Nic_Handle=" & CDBText(strUser) & " "
' strSQL = strSQL & "AND NH_Password=PASSWORD(" & CDBText(strPassword) & ")"
' DB.Execute strSQL, rec
'
' If rec.EOF Then
'    LogIn = False
''    LogIn = True
''    SetUserID strUser, strPassword, atSuperUser
' Else
'    SetUserID strUser, strPassword, NoNull(rec.Fields("NH_Level"))
'
'    If HasAuthority(atCustomerServ + atAccounts + atHostMaster + atLeadHostMaster + atSuperUser) Then
'       LogIn = True
'    Else
'       LogIn = False
'    End If
' End If
End Function


Private Sub SetUserID(ByVal strUserId As String, ByVal strPassword As String, ByVal lngAccessType As Long)
 mstrUserID = UCase(strUserId)
 mstrPassword = strPassword
 matUserID = lngAccessType
End Sub


'* Returns the UserId used to log on
Public Property Get UserID() As String
 UserID = mstrUserID
End Property


'* Returns the Password used to log on
Public Property Get Password() As String
 Password = mstrPassword
End Property

'* Returns the Authority Level of the user
Public Property Get AuthLevel() As AccessType
 AuthLevel = matUserID
End Property


'* Add all the allowable authoritys together. If the
'* authority of the user is one of them, TRUE is returned
'* otherwise FALSE is rerurned
'*
'* To work out whats happening, treat numbers by their binary equivalent,
'* Each bit represents an authority level
Public Function HasAuthority(ByVal atAllowedAccessTypes As AccessType) As Boolean
 HasAuthority = ((matUserID And (atAllowedAccessTypes Or atSuperUser)) > 0)
End Function

