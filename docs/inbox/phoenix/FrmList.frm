VERSION 5.00
Begin VB.Form FrmList 
   BorderStyle     =   3  'Fixed Dialog
   Caption         =   "List"
   ClientHeight    =   3195
   ClientLeft      =   45
   ClientTop       =   330
   ClientWidth     =   3390
   LinkTopic       =   "Form1"
   MaxButton       =   0   'False
   MinButton       =   0   'False
   ScaleHeight     =   3195
   ScaleWidth      =   3390
   ShowInTaskbar   =   0   'False
   StartUpPosition =   2  'CenterScreen
   Begin VB.CommandButton cmdCancel 
      Cancel          =   -1  'True
      Caption         =   "Ca&ncel"
      Height          =   375
      Left            =   1920
      TabIndex        =   2
      Top             =   2640
      Width           =   1095
   End
   Begin VB.CommandButton cmdOK 
      Caption         =   "&OK"
      Default         =   -1  'True
      Height          =   375
      Left            =   360
      TabIndex        =   1
      Top             =   2640
      Width           =   1095
   End
   Begin VB.ListBox List1 
      Height          =   2400
      Left            =   120
      TabIndex        =   0
      Top             =   120
      Width           =   3135
   End
End
Attribute VB_Name = "FrmList"
Attribute VB_GlobalNameSpace = False
Attribute VB_Creatable = False
Attribute VB_PredeclaredId = True
Attribute VB_Exposed = False
Option Explicit

'*********************************************
'*                                           *
'* Written By : Brian Devlin                 *
'* Function   : Common form for displaying   *
'*              a list of items for the user *
'*              to select from               *
'*********************************************

Dim mstrSelected  As String
Dim mblnCancelled As Boolean


'* Displays the list using the first field from the executed SQL
'* as the list item to display, if there is a 2nd field, this
'* will be used as the lists ItemData (returned in lngID)
Public Sub ShowList(ByVal strSQL As String, ByRef strSelected As String, Optional ByRef lngID As Long, Optional ByRef blnCancelled As Boolean = False, Optional ByVal strCaption As String = "List")
 Me.Caption = strCaption
 mstrSelected = strSelected
 mblnCancelled = False
 If strSQL <> "" Then
    LoadList strSQL
 End If
 Me.Show vbModal
 
 blnCancelled = mblnCancelled
 If Not mblnCancelled Then
    strSelected = mstrSelected
    If List1.ListIndex >= 0 Then
       lngID = List1.ItemData(List1.ListIndex)
    End If
 End If
 Unload Me
End Sub


'* Adds Additional Items to the list not returned from SQL
Public Sub AddItem(ByVal strItem As String, Optional ByVal lngItemData As Long, Optional ByVal lngPosition As Long = -1)
 If lngPosition = -1 Then
    List1.AddItem strItem
 Else
    List1.AddItem strItem, lngPosition
 End If
 List1.ItemData(List1.NewIndex) = lngItemData
End Sub


Private Sub LoadList(ByVal strSQL As String)
 Dim rec      As ADODB.Recordset
 Dim blnHasId As Boolean
 
 On Error GoTo LoadListERROR
 Screen.MousePointer = vbHourglass
 DB.Execute strSQL, rec
 If rec.Fields.Count > 1 Then
    blnHasId = True
 End If
 
 List1.Clear
 Do Until rec.EOF
    List1.AddItem rec.Fields(0)
    If blnHasId Then
       If IsNumeric(rec.Fields(1)) Then
          List1.ItemData(List1.NewIndex) = rec.Fields(1)
       End If
    End If
    rec.MoveNext
 Loop
 
' On Error GoTo LoadListNotFoundERROR
 List1.Text = mstrSelected
 Screen.MousePointer = vbNormal
Exit Sub
LoadListERROR:
 DB.ShowError "Unable to load list"
 cmdOK.Enabled = False
LoadListNotFoundERROR:
 List1.ListIndex = -1
 Screen.MousePointer = vbNormal
End Sub


Private Sub CmdCancel_Click()
 mblnCancelled = True
 Me.Hide
End Sub

Private Sub cmdOK_Click()
 If List1.ListIndex > -1 Then
    mstrSelected = List1.Text
 End If
 Me.Hide
End Sub

Private Sub List1_DblClick()
 If cmdOK.Enabled Then
    cmdOK.Value = True
 End If
End Sub
