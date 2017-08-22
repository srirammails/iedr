VERSION 5.00
Begin VB.Form FrmVAT 
   BorderStyle     =   3  'Fixed Dialog
   Caption         =   "VAT "
   ClientHeight    =   2400
   ClientLeft      =   4050
   ClientTop       =   1830
   ClientWidth     =   4575
   KeyPreview      =   -1  'True
   LinkTopic       =   "Form1"
   MaxButton       =   0   'False
   MinButton       =   0   'False
   ScaleHeight     =   2400
   ScaleWidth      =   4575
   ShowInTaskbar   =   0   'False
   StartUpPosition =   2  'CenterScreen
   Begin VB.TextBox txtStatus 
      Height          =   315
      Left            =   1800
      TabIndex        =   10
      Tag             =   "T"
      Top             =   1200
      Width           =   1455
   End
   Begin VB.ComboBox cboStatus 
      Height          =   315
      Left            =   1800
      Style           =   2  'Dropdown List
      TabIndex        =   2
      Tag             =   "T"
      Top             =   1200
      Width           =   1695
   End
   Begin VB.CommandButton CmdCancel 
      Caption         =   "Ca&ncel"
      Height          =   375
      Left            =   1200
      TabIndex        =   4
      Top             =   1920
      Width           =   975
   End
   Begin VB.CommandButton CmdEdit 
      Caption         =   "&Edit"
      Height          =   375
      Left            =   120
      TabIndex        =   3
      Top             =   1920
      Width           =   975
   End
   Begin VB.CommandButton CmdSave 
      Caption         =   "Sa&ve"
      Height          =   375
      Left            =   2280
      TabIndex        =   5
      Top             =   1920
      Width           =   975
   End
   Begin VB.TextBox txtRegNo 
      BackColor       =   &H8000000F&
      BorderStyle     =   0  'None
      Height          =   285
      Left            =   1800
      TabIndex        =   1
      Top             =   720
      Width           =   1935
   End
   Begin VB.TextBox txtCompName 
      BackColor       =   &H8000000F&
      BorderStyle     =   0  'None
      Height          =   285
      Left            =   1800
      Locked          =   -1  'True
      TabIndex        =   0
      Tag             =   "X"
      Top             =   240
      Width           =   2055
   End
   Begin VB.CommandButton CmdClose 
      Caption         =   "&Close"
      Height          =   375
      Left            =   3360
      TabIndex        =   6
      Top             =   1920
      Width           =   975
   End
   Begin VB.Label LblStatus 
      Caption         =   "Status"
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
      Left            =   240
      TabIndex        =   9
      Top             =   1200
      Width           =   1335
   End
   Begin VB.Label Label2 
      Caption         =   "Company Name"
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
      Left            =   240
      TabIndex        =   8
      Top             =   240
      Width           =   1455
   End
   Begin VB.Label Label1 
      Caption         =   "Reg. No"
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
      Left            =   240
      TabIndex        =   7
      Top             =   720
      Width           =   1335
   End
End
Attribute VB_Name = "FrmVAT"
Attribute VB_GlobalNameSpace = False
Attribute VB_Creatable = False
Attribute VB_PredeclaredId = True
Attribute VB_Exposed = False
Option Explicit

'*********************************************
'*                                           *
'* Written By : Brian Devlin                 *
'* Function   : Maintain Vat details         *
'*                                           *
'*********************************************

Dim mcsFormState       As ControlState
Dim mblnRecordLoaded   As Boolean
Dim mstrBillingContact As String


Public Sub ShowVat(ByVal strBillingContact As String, ByVal blnReadOnly As Boolean, Optional blnCalledFromAccount As Boolean = False)
 If blnCalledFromAccount Then
    Me.HelpContextID = 4020
 Else
    Me.HelpContextID = 3030
 End If
 
 If blnReadOnly Then
    CmdCancel.Visible = False
    CmdEdit.Visible = False
    CmdSave.Visible = False
 End If
 LoadVat strBillingContact
 FormatControls Me, csView
 Me.Show vbModal
End Sub


Private Sub CmdCancel_Click()
 mcsFormState = csView
 LoadVat mstrBillingContact
 FormatControls Me, csView
 ShowButtons True, False, False, True
End Sub


Private Sub CmdClose_Click()
 Unload Me
End Sub


Private Sub CmdEdit_Click()
 mcsFormState = csEdit
 
 FormatControls Me, csEdit
 ShowButtons False, True, True, False
 
 txtRegNo.SetFocus
End Sub


Private Sub CmdSave_Click()
 Dim blnError As Boolean
 
 Screen.MousePointer = vbHourglass
    
 If IsValid Then
'    If ChangedControls(Me) Then
       UpdateVat blnError
'    End If
 Else
    blnError = True
 End If
    
 If Not blnError Then
    mcsFormState = csView
    FormatControls Me, csView
    ShowButtons True, False, False, True
 End If
 
 Screen.MousePointer = vbNormal
End Sub


Private Sub Form_Load()
 mcsFormState = csView
 mblnRecordLoaded = False
 
 FillVatStatusCombo cboStatus
 FormatControls Me, csView
 ShowButtons True, False, False, True
End Sub


Private Sub Form_KeyDown(KeyCode As Integer, Shift As Integer)
 Select Case KeyCode
  Case vbKeyF2    '* Edit
    If CmdEdit.Enabled And CmdEdit.Visible Then
       CmdEdit.Value = True
    End If
  Case vbKeyF5    '* Refresh
    If CmdClose.Enabled Then
       LoadVat mstrBillingContact
    End If
 End Select
End Sub


Private Sub ShowButtons(ByVal blnEdit As Boolean, _
                        ByVal blnCancel As Boolean, _
                        ByVal blnSave As Boolean, _
                        ByVal blnClose As Boolean)

 CmdEdit.Enabled = blnEdit And mblnRecordLoaded
 CmdCancel.Enabled = blnCancel
 CmdSave.Enabled = blnSave
 CmdClose.Enabled = blnClose
 
 If blnClose Then
    CmdClose.Cancel = True
    CmdClose.Default = True
 End If
 If blnSave Then CmdSave.Default = True
 If blnCancel Then CmdCancel.Cancel = True
End Sub


Private Function IsValid() As Boolean
 Dim blnInError As Boolean
 
 If Trim(txtRegNo) = "" Then
    blnInError = True
    FormatControl txtRegNo, csError
 End If
 
 IsValid = Not blnInError
End Function


Private Sub LoadVat(ByVal strBillingContact As String)
 Dim strSQL As String
 Dim rec    As ADODB.Recordset
 
 On Error GoTo LoadVatERROR
 Screen.MousePointer = vbHourglass
 
 mstrBillingContact = strBillingContact
 strSQL = "SELECT Co_Name, VAT_Reg_ID, VAT_exempt "
 strSQL = strSQL & "FROM Payment "
 strSQL = strSQL & "INNER JOIN NicHandle AS nh "
 strSQL = strSQL & "ON Billing_Contact = nh.Nic_Handle "
 strSQL = strSQL & "WHERE Billing_Contact=" & CDBText(mstrBillingContact)
 
 DB.Execute strSQL, rec
 
 If Not rec.EOF Then
    Me.Caption = "VAT - " & mstrBillingContact
    txtCompName = rec.Fields("Co_Name")
    txtRegNo = NoNull(rec.Fields("VAT_Reg_ID"))
    'txtStatus = rec.Fields("VAT_exempt")
    If NoNull(rec.Fields("VAT_exempt")) = "Exempt" Then
       cboStatus.ListIndex = 0
    Else
       cboStatus.ListIndex = 1
    End If
    mblnRecordLoaded = True
 Else
    mblnRecordLoaded = False
 End If
 ShowButtons True, False, False, True
 Screen.MousePointer = vbNormal
Exit Sub
LoadVatERROR:
 Screen.MousePointer = vbNormal
 DB.ShowError "Unable to Load Vat Details"
End Sub


Private Sub UpdateVat(ByRef blnError As Boolean)
 Dim strSQL         As String
 Dim lngRowsUpdated As Long
 
 On Error GoTo UpdateVatERROR
  
 If ChangedControls(Me) Then
    strSQL = "UPDATE Payment SET "
    strSQL = strSQL & "VAT_Reg_ID=" & CDBText(txtRegNo) & ", "
    strSQL = strSQL & "VAT_exempt=" & CDBText(cboStatus) & " "
    strSQL = strSQL & "WHERE Billing_Contact = " & CDBText(mstrBillingContact) & " "
    
    DB.Execute strSQL, , , , lngRowsUpdated
    
    If lngRowsUpdated <> 1 Then
       MsgBox "Could not Update Vat Details." & vbCrLf & vbCrLf & "Possible reasons : Account updated by another user while you were editing it", vbExclamation
    End If
    LoadVat mstrBillingContact
 End If
Exit Sub
UpdateVatERROR:
 DB.ShowError "Unable to Update Vat"
 blnError = True
End Sub


Private Sub FillVatStatusCombo(cbo As ComboBox)
 cbo.Clear
 cbo.AddItem "Exempt"
 cbo.AddItem "Not Exempt"
End Sub
