VERSION 5.00
Begin VB.Form FrmAdmin 
   Caption         =   "Adminstration"
   ClientHeight    =   7395
   ClientLeft      =   60
   ClientTop       =   345
   ClientWidth     =   11835
   HelpContextID   =   7000
   KeyPreview      =   -1  'True
   LinkTopic       =   "Form1"
   MDIChild        =   -1  'True
   ScaleHeight     =   7395
   ScaleWidth      =   11835
   WindowState     =   2  'Maximized
   Begin VB.Frame Frame2 
      Caption         =   "Options"
      Height          =   735
      Left            =   120
      TabIndex        =   30
      Top             =   5640
      Width           =   7575
      Begin VB.CommandButton cmdClose 
         Caption         =   "&Close"
         Height          =   375
         Left            =   6000
         TabIndex        =   20
         Top             =   240
         Width           =   1335
      End
      Begin VB.CommandButton cmdCancel 
         Caption         =   "Ca&ncel"
         Height          =   375
         Left            =   4560
         TabIndex        =   21
         Top             =   240
         Width           =   1335
      End
      Begin VB.CommandButton cmdSave 
         Caption         =   "Sa&ve"
         Height          =   375
         Left            =   3120
         TabIndex        =   19
         Top             =   240
         Width           =   1335
      End
      Begin VB.CommandButton cmdResetPW 
         Caption         =   "&Reset Password"
         Height          =   375
         Left            =   1680
         TabIndex        =   18
         Top             =   240
         Width           =   1335
      End
      Begin VB.CommandButton cmdEdit 
         Caption         =   "&Edit"
         Height          =   375
         Left            =   240
         TabIndex        =   17
         Top             =   240
         Width           =   1335
      End
   End
   Begin IEDR.Search Search1 
      Height          =   1290
      Left            =   120
      TabIndex        =   0
      Top             =   120
      Width           =   7575
      _ExtentX        =   13361
      _ExtentY        =   2275
      Option1Caption  =   "Nic &Handle"
      Option2Caption  =   "Na&me"
      OptionCount     =   2
   End
   Begin VB.Frame FraEnter 
      Caption         =   "Set Access:"
      Height          =   4095
      Left            =   120
      TabIndex        =   24
      Top             =   1440
      Width           =   7575
      Begin VB.TextBox txtMaiden 
         BackColor       =   &H8000000F&
         BorderStyle     =   0  'None
         Height          =   285
         Left            =   5280
         TabIndex        =   35
         Top             =   360
         Width           =   1215
      End
      Begin VB.TextBox txtNicHandle 
         BackColor       =   &H8000000F&
         BorderStyle     =   0  'None
         Height          =   285
         Left            =   2040
         TabIndex        =   28
         TabStop         =   0   'False
         Tag             =   "X"
         Top             =   360
         Width           =   1215
      End
      Begin VB.TextBox txtName 
         BackColor       =   &H8000000F&
         BorderStyle     =   0  'None
         Height          =   285
         Left            =   2040
         TabIndex        =   26
         TabStop         =   0   'False
         Tag             =   "X"
         Top             =   720
         Width           =   3015
      End
      Begin VB.Frame fraAccessLevel 
         Caption         =   "Access Level"
         Height          =   2655
         Left            =   240
         TabIndex        =   29
         Top             =   1200
         Width           =   7095
         Begin VB.CheckBox chkLevel 
            Height          =   255
            Index           =   7
            Left            =   3600
            TabIndex        =   16
            Top             =   1800
            Width           =   255
         End
         Begin VB.CheckBox chkLevel 
            Height          =   255
            Index           =   6
            Left            =   3600
            TabIndex        =   14
            Top             =   1440
            Width           =   255
         End
         Begin VB.CheckBox chkLevel 
            Height          =   255
            Index           =   5
            Left            =   3600
            TabIndex        =   12
            Top             =   1080
            Width           =   255
         End
         Begin VB.CheckBox chkLevel 
            Height          =   255
            Index           =   4
            Left            =   3600
            TabIndex        =   10
            Top             =   720
            Width           =   255
         End
         Begin VB.CheckBox chkLevel 
            Height          =   255
            Index           =   3
            Left            =   840
            TabIndex        =   8
            Top             =   1800
            Width           =   255
         End
         Begin VB.CheckBox chkLevel 
            Height          =   255
            Index           =   2
            Left            =   840
            TabIndex        =   6
            Top             =   1440
            Width           =   255
         End
         Begin VB.CheckBox chkLevel 
            Height          =   255
            Index           =   1
            Left            =   840
            TabIndex        =   4
            Top             =   1080
            Width           =   255
         End
         Begin VB.CheckBox chkLevel 
            Height          =   255
            Index           =   0
            Left            =   840
            TabIndex        =   2
            Top             =   720
            Width           =   255
         End
         Begin VB.Label lblLevel 
            Caption         =   "&Tech/Super User"
            Height          =   255
            Index           =   7
            Left            =   3960
            TabIndex        =   15
            Top             =   1800
            Width           =   1575
         End
         Begin VB.Label lblLevel 
            Caption         =   "&Batch"
            Height          =   255
            Index           =   6
            Left            =   3960
            TabIndex        =   13
            Top             =   1440
            Width           =   1575
         End
         Begin VB.Label lblLevel 
            Caption         =   "Lea&d Hostmaster"
            Height          =   255
            Index           =   5
            Left            =   3960
            TabIndex        =   11
            Top             =   1080
            Width           =   1575
         End
         Begin VB.Label lblLevel 
            Caption         =   "H&ostmaster"
            Height          =   255
            Index           =   4
            Left            =   3960
            TabIndex        =   9
            Top             =   720
            Width           =   1575
         End
         Begin VB.Label lblLevel 
            Caption         =   "&Finance"
            Height          =   255
            Index           =   3
            Left            =   1200
            TabIndex        =   7
            Top             =   1800
            Width           =   1575
         End
         Begin VB.Label lblLevel 
            Caption         =   "C&ustomer Service"
            Height          =   255
            Index           =   2
            Left            =   1200
            TabIndex        =   5
            Top             =   1440
            Width           =   1575
         End
         Begin VB.Label lblLevel 
            Caption         =   "&Web"
            Height          =   255
            Index           =   1
            Left            =   1200
            TabIndex        =   3
            Top             =   1080
            Width           =   1575
         End
         Begin VB.Label lblLevel 
            Caption         =   "&Guest"
            Height          =   255
            Index           =   0
            Left            =   1200
            TabIndex        =   1
            Top             =   720
            Width           =   1575
         End
      End
      Begin VB.Frame fraPassword 
         Caption         =   "Password"
         Height          =   2655
         Left            =   240
         TabIndex        =   31
         Top             =   1200
         Width           =   7095
         Begin VB.TextBox txtPassword 
            BackColor       =   &H8000000F&
            BorderStyle     =   0  'None
            Height          =   285
            IMEMode         =   3  'DISABLE
            Left            =   2520
            MaxLength       =   16
            PasswordChar    =   "*"
            TabIndex        =   22
            Top             =   600
            Width           =   1815
         End
         Begin VB.TextBox txtConfirmPW 
            BackColor       =   &H8000000F&
            BorderStyle     =   0  'None
            Height          =   285
            IMEMode         =   3  'DISABLE
            Left            =   2520
            MaxLength       =   16
            PasswordChar    =   "*"
            TabIndex        =   23
            Top             =   960
            Width           =   1815
         End
         Begin VB.Label LblNewPass 
            Caption         =   "New Password"
            Height          =   255
            Left            =   600
            TabIndex        =   33
            Top             =   600
            Width           =   1095
         End
         Begin VB.Label LblConNewPass 
            Caption         =   "Confirm New Password"
            Height          =   255
            Left            =   600
            TabIndex        =   32
            Top             =   1080
            Width           =   1695
         End
      End
      Begin VB.Label Label1 
         Caption         =   "Maiden Name"
         BeginProperty Font 
            Name            =   "MS Sans Serif"
            Size            =   8.25
            Charset         =   0
            Weight          =   700
            Underline       =   0   'False
            Italic          =   0   'False
            Strikethrough   =   0   'False
         EndProperty
         Height          =   255
         Left            =   3840
         TabIndex        =   34
         Top             =   360
         Width           =   1455
      End
      Begin VB.Label LblOldPass 
         Caption         =   "Nic Handle "
         BeginProperty Font 
            Name            =   "MS Sans Serif"
            Size            =   8.25
            Charset         =   0
            Weight          =   700
            Underline       =   0   'False
            Italic          =   0   'False
            Strikethrough   =   0   'False
         EndProperty
         Height          =   255
         Left            =   360
         TabIndex        =   27
         Top             =   360
         Width           =   975
      End
      Begin VB.Label LblUserName 
         Caption         =   "Full User Name"
         BeginProperty Font 
            Name            =   "MS Sans Serif"
            Size            =   8.25
            Charset         =   0
            Weight          =   700
            Underline       =   0   'False
            Italic          =   0   'False
            Strikethrough   =   0   'False
         EndProperty
         Height          =   255
         Left            =   360
         TabIndex        =   25
         Top             =   720
         Width           =   1335
      End
   End
End
Attribute VB_Name = "FrmAdmin"
Attribute VB_GlobalNameSpace = False
Attribute VB_Creatable = False
Attribute VB_PredeclaredId = True
Attribute VB_Exposed = False
Option Explicit

'*********************************************
'*                                           *
'* Written By : Brian Devlin                 *
'* Function   : Maintain All Access levels of*
'*              users and reset paswords     *
'*********************************************

Private Const mstrRemark   As String = "Change Password"
Private Const mstrPassword As String = "password"

Dim mcsFormState     As ControlState
Dim mblnResetPW      As Boolean
Dim mblnRecordLoaded As Boolean
Dim mstrNicHandle    As String
Dim mblnChangePW     As Boolean
Dim mstrTimeStamp    As String
Dim mblnAccessExists As Boolean


'* External entry to change password
Public Sub ChangePassword(ByVal strNicHandle, ByVal strTimeStamp As String)
 LoadNicHandle strNicHandle
 mblnChangePW = True
 cmdResetPW.Value = True
 mstrTimeStamp = strTimeStamp
End Sub


Private Sub chkLevel_Click(Index As Integer)
 If chkLevel(Index).Value = vbChecked Then
    lblLevel(Index).FontBold = True
 Else
    lblLevel(Index).FontBold = False
 End If
End Sub


Private Sub CmdCancel_Click()
 If mblnChangePW Then
    Unload Me
 Else
    If mcsFormState = csEdit Then
       LoadNicHandle mstrNicHandle
    End If
    
    mcsFormState = csView
    mblnResetPW = False
    
    FormatControls Me, csView
    ShowButtons True, True, False, False, True
    ShowAccessLevel True
 End If
End Sub


Private Sub CmdClose_Click()
 Unload Me
End Sub


Private Sub CmdEdit_Click()
 mcsFormState = csEdit
 
 FormatControls Me, csEdit
 FormatControl txtPassword, csView
 FormatControl txtConfirmPW, csView
 
 ShowButtons False, False, True, True, False
 ShowAccessLevel True
 
 chkLevel(0).SetFocus
End Sub


Private Sub cmdResetPW_Click()
 mcsFormState = csEdit
 mblnResetPW = True
 
 txtPassword = ""
 txtConfirmPW = ""
 FormatControl txtPassword, csEdit
 FormatControl txtConfirmPW, csEdit
 
 ShowButtons False, False, True, True, False
 ShowAccessLevel False
 
 txtPassword.SetFocus
End Sub


Private Sub CmdSave_Click()
 Dim blnError As Boolean
 Dim ctlError As Control
 
 Screen.MousePointer = vbHourglass
 If mcsFormState = csEdit Then
    If mblnResetPW Then
       If IsValidPW Then
          If mblnAccessExists Then
             UpdatePassword blnError
          Else
             InsertPassword blnError
          End If
          If Not blnError Then SendPasswordEmail 'CC20050425
       Else
          blnError = True
       End If
    Else
       If IsValidAccess Then
          If mblnAccessExists Then
             UpdateLevel blnError
          Else
             InsertLevel blnError
          End If
       Else
          blnError = True
       End If
    End If
 End If
 
 If Not blnError Then
    If mblnChangePW Then
       FrmNicHandle.ChangedPassword mstrNicHandle
       Unload Me
    Else
       mcsFormState = csView
       FormatControls Me, csView
       mblnResetPW = False
       ShowButtons True, True, False, False, True
       ShowAccessLevel True
    End If
 Else
    Set ctlError = FindControlInError(Me)
    If Not (ctlError Is Nothing) Then
       ctlError.SetFocus
    End If
 End If
 
 Screen.MousePointer = vbNormal
 
End Sub

Private Sub SendPasswordEmail() 'CC20050425
Dim rec As ADODB.Recordset
Dim sSQL As String
Dim blError As Boolean
Dim strTo, strCC, strBCC, strSubject, strBody As String

    sSQL = "SELECT NH_Email, NH_Name FROM NicHandle WHERE Nic_Handle = '" & mstrNicHandle & "'"
    DB.Execute sSQL, rec
    If rec.EOF Then Exit Sub
    strTo = rec.Fields("NH_Email")
    If strTo = "" Then Exit Sub
    
    strCC = "hostmaster@domainregistry.ie"
    strBCC = "hostmaster-archive@iedr.ie"
    strSubject = "IE Domain Registry Login: " & rec.Fields("NH_Name") & " (" & mstrNicHandle & ")"
    strBody = "Dear " & rec.Fields("NH_Name") & "," & vbCrLf & vbCrLf
    strBody = strBody & "Your login information is as follows." & vbCrLf & vbCrLf
    strBody = strBody & "Username: " & mstrNicHandle & vbCrLf
    strBody = strBody & "Password: " & Trim(txtPassword) & vbCrLf & vbCrLf & vbCrLf
    strBody = strBody & "To modify your settings go to:" & vbCrLf & vbCrLf
    strBody = strBody & "https://www.domainregistry.ie/Login.php?action=Modification" & vbCrLf & vbCrLf & vbCrLf
    strBody = strBody & "To submit a renewal or transfer payment go to:" & vbCrLf & vbCrLf
    strBody = strBody & "http://www.domainregistry.ie/Payments/" & vbCrLf & vbCrLf & vbCrLf
    strBody = strBody & "Kind Regards," & vbCrLf & "IE Hostmaster" & vbCrLf & vbCrLf
    strBody = strBody & "tel: +353-1-2365 400" & vbCrLf & "fax: +353-1-2300 365" & vbCrLf
    strBody = strBody & "email: hostmaster@iedr.ie" & vbCrLf & "web: http://www.iedr.ie" & vbCrLf
    
    FrmSendMail.NewSendMail blError, DB.StaticData(ustSmtp), DB.StaticData(ustSmtpPort), _
        strTo, strCC, strBCC, "hostmaster@iedr.ie", strSubject, strBody, "IE Hostmaster", rec.Fields("NH_Name")

End Sub


Private Sub Form_KeyDown(KeyCode As Integer, Shift As Integer)
 Select Case KeyCode
  Case vbKeyF2    '* Edit
    If cmdEdit.Enabled Then
       cmdEdit.Value = True
    End If
  Case vbKeyF3    '* Search
    If Search1.Enabled Then
       Search1.SetFocus
    End If
  Case vbKeyF5    '* Refresh
    If Search1.Enabled Then
       LoadNicHandle mstrNicHandle
    End If
 End Select
End Sub


Private Sub ReverseAccess(ByVal lngIndex As Long)
 If chkLevel(lngIndex) = vbChecked Then
    chkLevel(lngIndex) = vbUnchecked
 Else
    chkLevel(lngIndex) = vbChecked
 End If
End Sub


Private Sub Form_Load()
 mcsFormState = csView
 mblnRecordLoaded = False
 FormatControls Me, csView
 ShowButtons True, True, False, False, True
 ShowAccessLevel True
End Sub


Private Sub Form_Resize()
 CenterControls Me
End Sub


Private Sub lblLevel_Click(Index As Integer)
 If chkLevel(Index).Enabled Then
    If chkLevel(Index).Value = vbChecked Then
       chkLevel(Index).Value = vbUnchecked
    Else
       chkLevel(Index).Value = vbChecked
    End If
 End If
End Sub


Private Sub Search1_GetSQL(ByVal lngOptionSelected As Long, strSQL As String, ByVal strSearch As String)
 Select Case lngOptionSelected
  Case 1    '* Nic Handle
    strSQL = "SELECT Nic_Handle "
    strSQL = strSQL & "FROM NicHandle "
    strSQL = strSQL & "WHERE Nic_Handle LIKE " & CDBText(strSearch & "%") & " "
    strSQL = strSQL & "ORDER BY Nic_Handle"
  
  Case 2    '* Name
    strSQL = "SELECT Nic_Handle "
    strSQL = strSQL & "FROM NicHandle "
    strSQL = strSQL & "WHERE NH_Name LIKE " & CDBText(strSearch & "%") & " "
    strSQL = strSQL & "ORDER BY Nic_Handle"
 
 End Select
End Sub


Private Sub Search1_SelectedResult(ByVal strResult As String, ByVal strData As String, ByVal lngOptionSelected As Long)
 LoadNicHandle strResult
End Sub


Private Sub LoadNicHandle(ByVal strNicHandle As String)
 Dim strSQL   As String
 Dim rec      As ADODB.Recordset
 Dim lngLevel As Long
 
 On Error GoTo LoadNicHandleERROR
 Screen.MousePointer = vbHourglass
 
 strSQL = "SELECT NH_Name, NH_Level, Answer "
 strSQL = strSQL & "FROM NicHandle AS NH "
 strSQL = strSQL & "LEFT OUTER JOIN Access AS A "
 strSQL = strSQL & "ON NH.Nic_Handle=A.Nic_Handle "
 strSQL = strSQL & "WHERE NH.Nic_Handle=" & CDBText(strNicHandle)
 
 DB.Execute strSQL, rec
 
 
 If Not rec.EOF Then
    mstrNicHandle = strNicHandle
    txtNicHandle = strNicHandle
    txtName = rec.Fields("NH_Name")

'* Insert extra field for maiden name by celina 22-07-02
    txtMaiden = NoNull(rec.Fields("Answer"))
    
    If IsNull(rec.Fields("NH_Level")) Then
       mblnAccessExists = False
       lngLevel = 0
    Else
       mblnAccessExists = True
       lngLevel = rec.Fields("NH_Level")
    End If
            
    ShowAuthority 0, lngLevel, atGuest
    ShowAuthority 1, lngLevel, atWeb
    ShowAuthority 2, lngLevel, atCustomerServ
    ShowAuthority 3, lngLevel, atAccounts
    ShowAuthority 4, lngLevel, atHostMaster
    ShowAuthority 5, lngLevel, atLeadHostMaster
    ShowAuthority 6, lngLevel, atBatch
    ShowAuthority 7, lngLevel, atSuperUser
    
    mblnRecordLoaded = True
 Else
    mblnRecordLoaded = False
 End If
 mcsFormState = csView
 FormatControls Me, csView
 ShowButtons True, True, False, False, True
 Screen.MousePointer = vbNormal
Exit Sub
LoadNicHandleERROR:
 Screen.MousePointer = vbNormal
 mblnRecordLoaded = False
 DB.ShowError "Unable to Load Nic Handle"
 mcsFormState = csView
 FormatControls Me, csView
End Sub


Private Sub UpdateLevel(ByRef blnError As Boolean)
 Dim strSQL         As String
 Dim lngRowsUpdated As Long

 On Error GoTo UpdateLevelERROR
 DB.BeginTransaction
 
 strSQL = "UPDATE Access SET "
 strSQL = strSQL & "Answer=" & CDBText(txtMaiden) & ", "
 strSQL = strSQL & "NH_Level=" & GetAuthoityLevel() & " "
 strSQL = strSQL & "WHERE Nic_Handle=" & CDBText(mstrNicHandle) & " "
 
 DB.Execute strSQL, , , , lngRowsUpdated
 WriteAccessHistory
 
 If lngRowsUpdated <> 1 Then
    DB.RollbackTransaction
    blnError = True
    MsgBox "Could not Update Level." & vbCrLf & vbCrLf & "Possible reasons : Authority Level has not changed.", vbExclamation
 Else
    DB.CommitTransaction
    LoadNicHandle mstrNicHandle
    blnError = False
 End If
Exit Sub
UpdateLevelERROR:
 On Error Resume Next

 If DB.TransactionLevel > 0 Then
    DB.RollbackTransaction
 End If
 DB.ShowError "Unable to Update Authority Level"
 blnError = True
End Sub


Private Sub InsertLevel(ByRef blnError As Boolean)
 Dim strSQL         As String
 Dim lngRowsUpdated As Long

 On Error GoTo InsertLevelERROR
 DB.BeginTransaction
 
 strSQL = "INSERT INTO Access ("
 strSQL = strSQL & "Nic_Handle,NH_Password,NH_Level) "
 strSQL = strSQL & "VALUES ("
 strSQL = strSQL & CDBText(mstrNicHandle) & ","
'CC20060118 to implement sha1 passwords strSQL = strSQL & "PASSWORD(" & CDBText(mstrPassword) & "),"
 strSQL = strSQL & "SHA1(" & CDBText(mstrPassword) & ")," 'CC20060118 to implement sha1 passwords
 strSQL = strSQL & GetAuthoityLevel() & ") "
 
 DB.Execute strSQL, , , , lngRowsUpdated
 WriteAccessHistory
 
 If lngRowsUpdated <> 1 Then
    DB.RollbackTransaction
    blnError = True
    DB.ShowError "Could not Insert Level."
 Else
    DB.CommitTransaction
    LoadNicHandle mstrNicHandle
    blnError = False
 End If
Exit Sub
InsertLevelERROR:
 On Error Resume Next

 If DB.TransactionLevel > 0 Then
    DB.RollbackTransaction
 End If
 DB.ShowError "Unable to Insert Authority Level"
 blnError = True
End Sub


Private Sub UpdatePassword(ByRef blnError As Boolean)
 Dim strSQL         As String
 Dim lngRowsUpdated As Long

 On Error GoTo UpdatePasswordERROR
 DB.BeginTransaction
 
 strSQL = "UPDATE Access SET "
'CC20060118 to implement sha1 passwords  strSQL = strSQL & "NH_Password=PASSWORD(" & CDBText(txtPassword) & ") "
 strSQL = strSQL & "NH_Password=SHA1(" & CDBText(txtPassword) & ") " 'CC20060118 to implement sha1 passwords
 strSQL = strSQL & "WHERE Nic_Handle=" & CDBText(mstrNicHandle) & " "
   
 DB.Execute strSQL, , , , lngRowsUpdated
 WriteAccessHistory
 

 If lngRowsUpdated <> 1 Then
    DB.RollbackTransaction
    blnError = True
    MsgBox "Could not Update Password." & vbCrLf & vbCrLf & "Possible reasons : Password entered was the same as the original.", vbExclamation
 Else
    blnError = False
    WriteNicHandleHistory mstrNicHandle, mstrRemark
    DB.CommitTransaction
    LoadNicHandle mstrNicHandle
 End If
Exit Sub
UpdatePasswordERROR:
 On Error Resume Next

 If DB.TransactionLevel > 0 Then
    DB.RollbackTransaction
 End If
 DB.ShowError "Unable to Update Password"
 blnError = True
End Sub


Private Sub InsertPassword(ByRef blnError As Boolean)
 Dim strSQL         As String
 Dim lngRowsUpdated As Long

 On Error GoTo InsertPasswordERROR
 DB.BeginTransaction
 
 strSQL = "INSERT INTO Access ("
 strSQL = strSQL & "Nic_Handle,NH_Password,NH_Level)"
 strSQL = strSQL & "VALUES ("
 strSQL = strSQL & CDBText(mstrNicHandle) & ", "
'CC20060118 to implement sha1 passwords strSQL = strSQL & "PASSWORD(" & CDBText(txtPassword) & "),"
 strSQL = strSQL & "SHA1(" & CDBText(txtPassword) & ")," 'CC20060118 to implement sha1 passwords
 strSQL = strSQL & atGuest & ") "
   
 DB.Execute strSQL, , , , lngRowsUpdated
 WriteAccessHistory
 
 If lngRowsUpdated <> 1 Then
    DB.RollbackTransaction
    blnError = True
    MsgBox "Could not Insert Password." & vbCrLf & vbCrLf & "Possible reasons : Password entered was the same as the original.", vbExclamation
 Else
    blnError = False
    WriteNicHandleHistory mstrNicHandle, mstrRemark
    DB.CommitTransaction
    LoadNicHandle mstrNicHandle
 End If
Exit Sub
InsertPasswordERROR:
 On Error Resume Next

 If DB.TransactionLevel > 0 Then
    DB.RollbackTransaction
 End If
 DB.ShowError "Unable to Insert Password"
 blnError = True
End Sub


Private Sub WriteAccessHistory()
 Dim strSQL As String
 
 strSQL = "INSERT INTO AccessHist ("
 strSQL = strSQL & "Chng_Dt, "
 strSQL = strSQL & "Chng_NH, "
 strSQL = strSQL & "Nic_Handle, "
 strSQL = strSQL & "NH_Password, "
 strSQL = strSQL & "NH_Level, "
 strSQL = strSQL & "Answer) "

 strSQL = strSQL & "SELECT DISTINCT " 'CC20051221 fix for MySQLv4.0
 strSQL = strSQL & "NOW(), "
 strSQL = strSQL & CDBText(UserID) & ", "
 strSQL = strSQL & "Nic_Handle, "
 strSQL = strSQL & "NH_Password, "
 strSQL = strSQL & "NH_Level, "
 strSQL = strSQL & "Answer "

 strSQL = strSQL & "FROM Access "
 strSQL = strSQL & "WHERE Nic_Handle=" & CDBText(mstrNicHandle)

 DB.Execute strSQL
End Sub


Private Sub WriteNicHandleHistory(ByVal strNicHandle As String, ByVal strRemark As String)
 Dim strSQL          As String
 
 strSQL = "INSERT INTO NicHandleHist("
 strSQL = strSQL & "Chng_NH,"
 strSQL = strSQL & "Chng_Dt,"
 strSQL = strSQL & "Nic_Handle,NH_Name,A_Number,Co_Name,"
 strSQL = strSQL & "NH_Address,NH_County,NH_Country,NH_Email,"
 strSQL = strSQL & "NH_Status,NH_Status_Dt,NH_Reg_Dt,NH_TStamp,NH_BillC_Ind,"
 strSQL = strSQL & "NH_Remark) "
           
 strSQL = strSQL & "SELECT DISTINCT " 'CC20051221 fix for MySQLv4.0
 strSQL = strSQL & CDBText(UserID) & ","
 strSQL = strSQL & "NOW(),"
 strSQL = strSQL & "Nic_Handle,NH_Name,A_Number,Co_Name,"
 strSQL = strSQL & "NH_Address,NH_County,NH_Country,NH_Email,"
 strSQL = strSQL & "NH_Status,NH_Status_Dt,NH_Reg_Dt,NH_TStamp,NH_BillC_Ind,"
 If strRemark = "" Then
    strSQL = strSQL & "NH_Remark "
 Else
    strSQL = strSQL & CDBText(strRemark) & " "
 End If
 strSQL = strSQL & "FROM NicHandle "
 strSQL = strSQL & "WHERE Nic_Handle=" & CDBText(strNicHandle)
 DB.Execute strSQL
End Sub


Private Sub ShowAuthority(ByVal lngIndex As Long, ByVal lngLevel As Long, ByVal atLevel As AccessType)
 If lngLevel And atLevel Then
    chkLevel(lngIndex).Value = vbChecked
    lblLevel(lngIndex).FontBold = True
 Else
    chkLevel(lngIndex).Value = vbUnchecked
    lblLevel(lngIndex).FontBold = False
 End If
End Sub


Private Function GetAuthoityLevel() As Long
 Dim lngLevel As Long
 
 If chkLevel(0).Value = vbChecked Then
    lngLevel = lngLevel + atGuest
 End If
 If chkLevel(1).Value = vbChecked Then
    lngLevel = lngLevel + atWeb
 End If
 If chkLevel(2).Value = vbChecked Then
    lngLevel = lngLevel + atCustomerServ
 End If
 If chkLevel(3).Value = vbChecked Then
    lngLevel = lngLevel + atAccounts
 End If
 If chkLevel(4).Value = vbChecked Then
    lngLevel = lngLevel + atHostMaster
 End If
 If chkLevel(5).Value = vbChecked Then
    lngLevel = lngLevel + atLeadHostMaster
 End If
 If chkLevel(6).Value = vbChecked Then
    lngLevel = lngLevel + atBatch
 End If
 If chkLevel(7).Value = vbChecked Then
    lngLevel = lngLevel + atSuperUser
 End If
 
 GetAuthoityLevel = lngLevel
End Function


Private Function IsValidAccess() As Boolean
 IsValidAccess = True
End Function


Private Function IsValidPW() As Boolean
 Dim blnInError As Boolean
  
 If Len(Trim(txtPassword)) < 6 Then
    blnInError = True
    FormatControl txtPassword, csError
    MsgBox "Passwords must be at least 6 characters", vbCritical
    GoTo ExitIsValidPW
 End If
  
 If Trim(txtPassword) = "" Then
    blnInError = True
    FormatControl txtPassword, csError
 End If
 
 If txtPassword <> txtConfirmPW Then
    blnInError = True
    FormatControl txtConfirmPW, csError
    MsgBox "New and Confirmed Passwords do not match", vbCritical
 End If
 
ExitIsValidPW:
 IsValidPW = Not blnInError
End Function


Private Sub ShowButtons(ByVal blnEdit As Boolean, _
                        ByVal blnResetPW As Boolean, _
                        ByVal blnSave As Boolean, _
                        ByVal blnCancel As Boolean, _
                        ByVal blnClose As Boolean)
 Dim blnAllowed As Boolean
 
 blnAllowed = HasAuthority(atSuperUser) Or (mblnChangePW And HasAuthority(atHostMaster + atLeadHostMaster))
 
 If mblnAccessExists Then
    cmdEdit.Caption = "&Edit"
 Else
    cmdEdit.Caption = "&Add"
 End If
 cmdEdit.Enabled = blnEdit And mblnRecordLoaded And blnAllowed
 cmdResetPW.Enabled = blnResetPW And mblnRecordLoaded And blnAllowed And mblnAccessExists
 cmdSave.Enabled = blnSave And mblnRecordLoaded And blnAllowed
 cmdCancel.Enabled = blnCancel
 cmdClose.Enabled = blnClose
 Search1.Enabled = Not blnSave
 
 cmdSave.Default = blnSave And blnAllowed
 cmdCancel.Cancel = blnCancel And blnAllowed
 cmdClose.Cancel = blnClose And blnAllowed
End Sub
                         

Private Sub ShowAccessLevel(ByVal blnShowAccessLevel As Boolean)
 fraAccessLevel.Visible = blnShowAccessLevel
 fraPassword.Visible = Not blnShowAccessLevel
End Sub
