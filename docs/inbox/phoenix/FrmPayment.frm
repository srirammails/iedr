VERSION 5.00
Begin VB.Form FrmPayment 
   BorderStyle     =   1  'Fixed Single
   Caption         =   "Payment"
   ClientHeight    =   6585
   ClientLeft      =   645
   ClientTop       =   1830
   ClientWidth     =   9945
   KeyPreview      =   -1  'True
   LinkTopic       =   "Form1"
   MaxButton       =   0   'False
   MinButton       =   0   'False
   ScaleHeight     =   6585
   ScaleWidth      =   9945
   StartUpPosition =   2  'CenterScreen
   Begin VB.CommandButton CmdCancel 
      Caption         =   "Ca&ncel"
      Height          =   375
      Left            =   6360
      TabIndex        =   19
      Top             =   5880
      Width           =   975
   End
   Begin VB.CommandButton CmdEdit 
      Caption         =   "&Edit"
      Height          =   375
      Left            =   5280
      TabIndex        =   18
      Top             =   5880
      Width           =   975
   End
   Begin VB.CommandButton CmdSave 
      Caption         =   "Sa&ve"
      Height          =   375
      Left            =   7440
      TabIndex        =   20
      Top             =   5880
      Width           =   975
   End
   Begin VB.CommandButton CmdClose 
      Caption         =   "&Close"
      Height          =   375
      Left            =   8520
      TabIndex        =   21
      Top             =   5880
      Width           =   975
   End
   Begin VB.Frame fraDDMandate 
      Caption         =   "Direct Debit Mandate"
      ForeColor       =   &H00000000&
      Height          =   2415
      Left            =   240
      TabIndex        =   26
      Top             =   3120
      Width           =   9255
      Begin VB.TextBox txtBank 
         Height          =   315
         Left            =   2280
         TabIndex        =   43
         Tag             =   "T"
         Top             =   600
         Width           =   1815
      End
      Begin VB.ComboBox cboBank 
         Height          =   315
         Left            =   2280
         TabIndex        =   11
         Tag             =   "T"
         Top             =   600
         Width           =   2055
      End
      Begin VB.TextBox txtBankAddress 
         BackColor       =   &H8000000F&
         ForeColor       =   &H00000000&
         Height          =   1005
         Left            =   2280
         Locked          =   -1  'True
         MultiLine       =   -1  'True
         TabIndex        =   12
         Top             =   960
         Width           =   2055
      End
      Begin VB.TextBox TxtAccHolder 
         BackColor       =   &H8000000F&
         ForeColor       =   &H00000000&
         Height          =   285
         Left            =   6960
         Locked          =   -1  'True
         TabIndex        =   13
         Top             =   720
         Width           =   2055
      End
      Begin VB.TextBox txtNSC 
         BackColor       =   &H8000000F&
         ForeColor       =   &H00000000&
         Height          =   285
         Index           =   2
         Left            =   7740
         Locked          =   -1  'True
         MaxLength       =   2
         TabIndex        =   17
         Top             =   1440
         Width           =   285
      End
      Begin VB.TextBox txtNSC 
         BackColor       =   &H8000000F&
         ForeColor       =   &H00000000&
         Height          =   285
         Index           =   1
         Left            =   7350
         Locked          =   -1  'True
         MaxLength       =   2
         TabIndex        =   16
         Top             =   1440
         Width           =   285
      End
      Begin VB.TextBox txtNSC 
         BackColor       =   &H8000000F&
         ForeColor       =   &H00000000&
         Height          =   285
         Index           =   0
         Left            =   6960
         Locked          =   -1  'True
         MaxLength       =   2
         TabIndex        =   15
         Top             =   1440
         Width           =   285
      End
      Begin VB.TextBox TxtBkAccNo1 
         BackColor       =   &H8000000F&
         ForeColor       =   &H00000000&
         Height          =   285
         Left            =   6960
         Locked          =   -1  'True
         TabIndex        =   14
         Top             =   1080
         Width           =   1995
      End
      Begin VB.Label Label10 
         Alignment       =   2  'Center
         Caption         =   "-"
         BeginProperty Font 
            Name            =   "MS Sans Serif"
            Size            =   9.75
            Charset         =   0
            Weight          =   700
            Underline       =   0   'False
            Italic          =   0   'False
            Strikethrough   =   0   'False
         EndProperty
         Height          =   255
         Left            =   7635
         TabIndex        =   33
         Top             =   1440
         Width           =   135
      End
      Begin VB.Label Label3 
         Alignment       =   2  'Center
         Caption         =   "-"
         BeginProperty Font 
            Name            =   "MS Sans Serif"
            Size            =   9.75
            Charset         =   0
            Weight          =   700
            Underline       =   0   'False
            Italic          =   0   'False
            Strikethrough   =   0   'False
         EndProperty
         Height          =   255
         Left            =   7245
         TabIndex        =   32
         Top             =   1440
         Width           =   135
      End
      Begin VB.Label LblBkAdd 
         Caption         =   "Bank Address"
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
         TabIndex        =   31
         Top             =   960
         Width           =   1935
      End
      Begin VB.Label LblMgr 
         Caption         =   "To the Manager"
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
         TabIndex        =   30
         Top             =   600
         Width           =   1935
      End
      Begin VB.Label LblAccHolder 
         Caption         =   "Account Holder"
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
         Left            =   4800
         TabIndex        =   29
         Top             =   720
         Width           =   1935
      End
      Begin VB.Label LblBkSortCode 
         Caption         =   "Bank Sort Code"
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
         Left            =   4800
         TabIndex        =   28
         Top             =   1440
         Width           =   1935
      End
      Begin VB.Label LblBkAccNo 
         Caption         =   "Bank Account Number"
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
         Left            =   4800
         TabIndex        =   27
         Top             =   1080
         Width           =   1935
      End
   End
   Begin VB.Frame fraCredCardDetails 
      Caption         =   "Credit Card Details"
      ForeColor       =   &H00000000&
      Height          =   2415
      Left            =   240
      TabIndex        =   22
      Top             =   360
      Width           =   9255
      Begin VB.OptionButton optCardType 
         Height          =   255
         Index           =   3
         Left            =   240
         TabIndex        =   42
         Top             =   1680
         Width           =   255
      End
      Begin VB.OptionButton optCardType 
         Height          =   255
         Index           =   2
         Left            =   240
         TabIndex        =   41
         Top             =   1320
         Width           =   255
      End
      Begin VB.OptionButton optCardType 
         Height          =   255
         Index           =   1
         Left            =   240
         TabIndex        =   40
         Top             =   960
         Width           =   255
      End
      Begin VB.OptionButton optCardType 
         Enabled         =   0   'False
         Height          =   255
         Index           =   0
         Left            =   240
         TabIndex        =   39
         Top             =   600
         Width           =   255
      End
      Begin VB.PictureBox Picture4 
         BorderStyle     =   0  'None
         Height          =   255
         Left            =   1200
         Picture         =   "FrmPayment.frx":0000
         ScaleHeight     =   255
         ScaleWidth      =   405
         TabIndex        =   38
         TabStop         =   0   'False
         Top             =   940
         Width           =   400
      End
      Begin VB.PictureBox Picture3 
         BorderStyle     =   0  'None
         Height          =   255
         Left            =   1080
         Picture         =   "FrmPayment.frx":047A
         ScaleHeight     =   255
         ScaleWidth      =   375
         TabIndex        =   37
         TabStop         =   0   'False
         Top             =   500
         Width           =   375
      End
      Begin VB.PictureBox Picture2 
         BorderStyle     =   0  'None
         Height          =   255
         Left            =   1660
         Picture         =   "FrmPayment.frx":09CC
         ScaleHeight     =   255
         ScaleWidth      =   375
         TabIndex        =   36
         TabStop         =   0   'False
         Top             =   1320
         Width           =   375
      End
      Begin VB.PictureBox Picture1 
         BorderStyle     =   0  'None
         Height          =   255
         Left            =   1440
         Picture         =   "FrmPayment.frx":0F1A
         ScaleHeight     =   255
         ScaleWidth      =   495
         TabIndex        =   35
         TabStop         =   0   'False
         Top             =   1800
         Width           =   495
      End
      Begin VB.TextBox txtNameOnCard 
         BackColor       =   &H8000000F&
         ForeColor       =   &H00000000&
         Height          =   285
         Left            =   6120
         Locked          =   -1  'True
         TabIndex        =   10
         Top             =   1680
         Width           =   2055
      End
      Begin VB.TextBox txtExpDate 
         BackColor       =   &H8000000F&
         ForeColor       =   &H00000000&
         Height          =   285
         Index           =   0
         Left            =   6120
         Locked          =   -1  'True
         MaxLength       =   2
         TabIndex        =   8
         Top             =   1080
         Width           =   285
      End
      Begin VB.TextBox txtExpDate 
         BackColor       =   &H8000000F&
         ForeColor       =   &H00000000&
         Height          =   285
         Index           =   1
         Left            =   6495
         Locked          =   -1  'True
         MaxLength       =   2
         TabIndex        =   9
         Top             =   1080
         Width           =   285
      End
      Begin VB.TextBox txtCardNo 
         BackColor       =   &H8000000F&
         ForeColor       =   &H00000000&
         Height          =   285
         Index           =   3
         Left            =   7560
         Locked          =   -1  'True
         MaxLength       =   4
         TabIndex        =   7
         Top             =   600
         Width           =   495
      End
      Begin VB.TextBox txtCardNo 
         BackColor       =   &H8000000F&
         ForeColor       =   &H00000000&
         Height          =   285
         Index           =   2
         Left            =   7080
         Locked          =   -1  'True
         MaxLength       =   4
         TabIndex        =   6
         Top             =   600
         Width           =   495
      End
      Begin VB.TextBox txtCardNo 
         BackColor       =   &H8000000F&
         ForeColor       =   &H00000000&
         Height          =   285
         Index           =   1
         Left            =   6600
         Locked          =   -1  'True
         MaxLength       =   4
         TabIndex        =   5
         Top             =   600
         Width           =   495
      End
      Begin VB.TextBox txtCardNo 
         BackColor       =   &H8000000F&
         ForeColor       =   &H00000000&
         Height          =   285
         Index           =   0
         Left            =   6120
         Locked          =   -1  'True
         MaxLength       =   4
         TabIndex        =   4
         Top             =   600
         Width           =   495
      End
      Begin VB.Label lblCardType 
         Caption         =   "American Express"
         BeginProperty Font 
            Name            =   "MS Sans Serif"
            Size            =   8.25
            Charset         =   0
            Weight          =   700
            Underline       =   0   'False
            Italic          =   0   'False
            Strikethrough   =   0   'False
         EndProperty
         Height          =   495
         Index           =   3
         Left            =   550
         TabIndex        =   3
         Top             =   1680
         Width           =   735
      End
      Begin VB.Label lblCardType 
         Caption         =   "MasterCard"
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
         Index           =   2
         Left            =   550
         TabIndex        =   2
         Top             =   1340
         Width           =   975
      End
      Begin VB.Label lblCardType 
         Caption         =   "Laser"
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
         Index           =   1
         Left            =   550
         TabIndex        =   1
         Top             =   960
         Width           =   495
      End
      Begin VB.Label lblCardType 
         Caption         =   "Visa"
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
         Index           =   0
         Left            =   550
         TabIndex        =   0
         Top             =   600
         Width           =   495
      End
      Begin VB.Label Label11 
         Alignment       =   2  'Center
         Caption         =   "/"
         BeginProperty Font 
            Name            =   "MS Sans Serif"
            Size            =   9.75
            Charset         =   0
            Weight          =   700
            Underline       =   0   'False
            Italic          =   0   'False
            Strikethrough   =   0   'False
         EndProperty
         Height          =   285
         Left            =   6405
         TabIndex        =   34
         Top             =   1065
         Width           =   135
      End
      Begin VB.Label LblFullName 
         Caption         =   "Full Name On Card"
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
         Left            =   3960
         TabIndex        =   25
         Top             =   1680
         Width           =   1935
      End
      Begin VB.Label LblExpDate 
         Caption         =   "Expiry Date"
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
         Left            =   3960
         TabIndex        =   24
         Top             =   1080
         Width           =   1215
      End
      Begin VB.Label LblCrdNo 
         Caption         =   "Card Number"
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
         Left            =   3960
         TabIndex        =   23
         Top             =   600
         Width           =   1215
      End
   End
End
Attribute VB_Name = "FrmPayment"
Attribute VB_GlobalNameSpace = False
Attribute VB_Creatable = False
Attribute VB_PredeclaredId = True
Attribute VB_Exposed = False
Option Explicit

'*********************************************
'*                                           *
'* Written By : Brian Devlin                 *
'* Function   : Maintain Payment details     *
'*                                           *
'*********************************************

Dim mcsFormState       As ControlState
Dim mblnRecordLoaded   As Boolean
Dim mstrBillingContact As String
Dim mstrOrigCardType   As String


Public Sub ShowPayment(ByVal strBillingContact As String, ByVal blnReadOnly As Boolean, Optional blnCalledFromAccount As Boolean = False)
 If blnCalledFromAccount Then
    Me.HelpContextID = 4010
 Else
    Me.HelpContextID = 3010
 End If
 
 If blnReadOnly Then
    CmdCancel.Visible = False
    CmdEdit.Visible = False
    CmdSave.Visible = False
 End If
 LoadPayment strBillingContact
 FormatControls Me, csView
 Me.Show vbModal
End Sub


Private Sub CmdCancel_Click()
 If mcsFormState = csAdd Then
    BlankControls Me
 ElseIf mcsFormState = csEdit Then
    LoadPayment mstrBillingContact
 End If
 
 mcsFormState = csView
 FormatControls Me, csView
 ShowButtons True, False, False, True
End Sub


Private Sub CmdClose_Click()
 Unload Me
End Sub


Private Sub CmdEdit_Click()
 If mblnRecordLoaded Then
    mcsFormState = csEdit
 Else
    mcsFormState = csAdd
 End If
 
 FormatControls Me, mcsFormState
 ShowButtons False, True, True, False
 
 txtCardNo(0).SetFocus
End Sub


Private Sub CmdSave_Click()
 Dim blnError As Boolean
 
 Screen.MousePointer = vbHourglass
    
 If IsValid Then
    If mblnRecordLoaded Then
       UpdatePayment blnError
    Else
       InsertPayment blnError
    End If
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
 
 FillBankCombo cboBank
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
       LoadPayment mstrBillingContact
    End If
 End Select
End Sub


Private Sub ShowButtons(ByVal blnEdit As Boolean, _
                        ByVal blnCancel As Boolean, _
                        ByVal blnSave As Boolean, _
                        ByVal blnClose As Boolean)
 
 If mblnRecordLoaded Then
    CmdEdit.Caption = "Edit"
 Else
    CmdEdit.Caption = "Add"
 End If
 CmdEdit.Enabled = blnEdit
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
 Dim ctl As Control
 
 For Each ctl In Me.Controls
    If Not ((TypeOf ctl Is TextBox) And (ctl.Tag = "T")) Then
       If (TypeOf ctl Is TextBox) Or (TypeOf ctl Is ComboBox) Then
          If ctl.Tag <> "B" And ctl.Tag <> "X" Then
             If Trim(ctl.Text) = "" Then
                blnInError = True
                FormatControl ctl, csError
             End If
          End If
       End If
    End If
 Next ctl
 
 If Not IsNumeric(txtCardNo(0)) Then
    blnInError = True
    FormatControl txtCardNo(0), csError
 End If
 If Not IsNumeric(txtCardNo(1)) Then
    blnInError = True
    FormatControl txtCardNo(1), csError
 End If
 If Not IsNumeric(txtCardNo(2)) Then
    blnInError = True
    FormatControl txtCardNo(2), csError
 End If
 If Not IsNumeric(txtCardNo(3)) Then
    blnInError = True
    FormatControl txtCardNo(3), csError
 End If
 
 If Len(txtCardNo(0)) <> 4 Then
    blnInError = True
    FormatControl txtCardNo(0), csError
 End If
 If Len(txtCardNo(1)) <> 4 Then
    blnInError = True
    FormatControl txtCardNo(1), csError
 End If
 If Len(txtCardNo(2)) <> 4 Then
    blnInError = True
    FormatControl txtCardNo(2), csError
 End If
 If Len(txtCardNo(3)) <> 4 Then
    blnInError = True
    FormatControl txtCardNo(3), csError
 End If
 
 
 If Not IsNumeric(txtExpDate(0)) Then
    blnInError = True
    FormatControl txtExpDate(0), csError
 Else
    If txtExpDate(0) < 1 Or txtExpDate(0) > 12 Then
      blnInError = True
      FormatControl txtExpDate(0), csError
    End If
 End If

 If Not IsNumeric(txtExpDate(1)) Then
    blnInError = True
    FormatControl txtExpDate(1), csError
 End If
 
 If Len(txtExpDate(0)) <> 2 Then
    blnInError = True
    FormatControl txtExpDate(0), csError
 End If
 If Len(txtExpDate(1)) <> 2 Then
    blnInError = True
    FormatControl txtExpDate(1), csError
 End If
  
 If Not IsNumeric(txtNSC(0)) Then
    blnInError = True
    FormatControl txtNSC(0), csError
 End If
 If Not IsNumeric(txtNSC(1)) Then
    blnInError = True
    FormatControl txtNSC(1), csError
 End If
 If Not IsNumeric(txtNSC(2)) Then
    blnInError = True
    FormatControl txtNSC(2), csError
 End If
 
 If Len(txtNSC(0)) <> 2 Then
    blnInError = True
    FormatControl txtNSC(0), csError
 End If
 If Len(txtNSC(1)) <> 2 Then
    blnInError = True
    FormatControl txtNSC(1), csError
 End If
 If Len(txtNSC(2)) <> 2 Then
    blnInError = True
    FormatControl txtNSC(2), csError
 End If
 
 
 
 IsValid = Not blnInError
End Function


Private Sub LoadPayment(ByVal strBillingContact As String)
 Dim strSQL As String
 Dim rec    As ADODB.Recordset
 
 On Error GoTo LoadPaymentERROR
 Screen.MousePointer = vbHourglass
 
 mstrBillingContact = strBillingContact
 strSQL = "SELECT * "
 strSQL = strSQL & "FROM Payment "
 strSQL = strSQL & "WHERE Billing_Contact=" & CDBText(mstrBillingContact)
 
 DB.Execute strSQL, rec
 
 If Not rec.EOF Then
    Me.Caption = "Payment - " & mstrBillingContact
    CardType = rec.Fields("Credit_Card_Type")
    txtNameOnCard = rec.Fields("Name_on_Card")
    CardNumber = rec.Fields("Card_Number")
    ExpDate = rec.Fields("Expiry_Date")
    TxtAccHolder = rec.Fields("Bank_Account_Holder")
    cboBank = rec.Fields("Bank")
'    txtBank = rec.Fields("Bank")
    NSC = rec.Fields("NSC")
    TxtBkAccNo1 = rec.Fields("AC_Number")
    txtBankAddress = rec.Fields("Address")
    
    mstrOrigCardType = CardType
    mblnRecordLoaded = True
 Else
    mblnRecordLoaded = False
 End If
 ShowButtons True, False, False, True
 Screen.MousePointer = vbNormal
Exit Sub
LoadPaymentERROR:
 Screen.MousePointer = vbNormal
 DB.ShowError "Unable to Load Payment Details"
End Sub


Private Sub UpdatePayment(ByRef blnError As Boolean)
 Dim strSQL         As String
 Dim lngRowsUpdated As Long
 
 On Error GoTo UpdatePaymentERROR
  
 If ChangedControls(Me) Or (mstrOrigCardType <> CardType) Then
    strSQL = "UPDATE Payment SET "
    strSQL = strSQL & "Credit_Card_Type=" & CDBText(CardType) & ", "
    strSQL = strSQL & "Name_on_Card=" & CDBText(txtNameOnCard) & ", "
    strSQL = strSQL & "Card_Number=" & CDBText(CardNumber) & ", "
    strSQL = strSQL & "Expiry_Date=" & CDBText(ExpDate) & ", "
    strSQL = strSQL & "Bank_Account_Holder=" & CDBText(TxtAccHolder) & ", "
    strSQL = strSQL & "Bank=" & CDBText(cboBank) & ", "
    strSQL = strSQL & "NSC=" & CDBText(NSC) & ", "
    strSQL = strSQL & "AC_Number=" & CDBText(TxtBkAccNo1) & ", "
    strSQL = strSQL & "Address=" & CDBText(txtBankAddress) & " "
    strSQL = strSQL & "WHERE Billing_Contact = " & CDBText(mstrBillingContact) & " "
    
    DB.Execute strSQL, , , , lngRowsUpdated
    
    If lngRowsUpdated <> 1 Then
       MsgBox "Could not Update Payment Details." & vbCrLf & vbCrLf & "Possible reasons : Account updated by another user while you were editing it", vbExclamation
    End If
    LoadPayment mstrBillingContact
 End If
Exit Sub
UpdatePaymentERROR:
 DB.ShowError "Unable to Update Payment Details"
 blnError = True
End Sub


Private Sub InsertPayment(ByRef blnError As Boolean)
 Dim strSQL         As String
 Dim lngRowsUpdated As Long
 
 On Error GoTo InsertPaymentERROR
  
 
 strSQL = "INSERT INTO Payment ("
 strSQL = strSQL & "Billing_Contact,"
 strSQL = strSQL & "Credit_Card_Type,"
 strSQL = strSQL & "Name_on_Card,"
 strSQL = strSQL & "Card_Number,"
 strSQL = strSQL & "Expiry_Date,"
 strSQL = strSQL & "Bank_Account_Holder,"
 strSQL = strSQL & "Bank,"
 strSQL = strSQL & "NSC,"
 strSQL = strSQL & "AC_Number,"
 strSQL = strSQL & "Address) "
 
 strSQL = strSQL & "VALUES ("
 strSQL = strSQL & CDBText(mstrBillingContact) & ", "
 strSQL = strSQL & CDBText(CardType) & ", "
 strSQL = strSQL & CDBText(txtNameOnCard) & ", "
 strSQL = strSQL & CDBText(CardNumber) & ", "
 strSQL = strSQL & CDBText(ExpDate) & ", "
 strSQL = strSQL & CDBText(TxtAccHolder) & ", "
 strSQL = strSQL & CDBText(cboBank) & ", "
 strSQL = strSQL & CDBText(NSC) & ", "
 strSQL = strSQL & CDBText(TxtBkAccNo1) & ", "
 strSQL = strSQL & CDBText(txtBankAddress) & ") "
 
 DB.Execute strSQL, , , , lngRowsUpdated
 
 If lngRowsUpdated <> 1 Then
    MsgBox "Could not Insert Payment Details." & vbCrLf & vbCrLf & "Possible reasons : Account updated by another user while you were editing it", vbExclamation
 End If
 LoadPayment mstrBillingContact

Exit Sub
InsertPaymentERROR:
 DB.ShowError "Unable to Update Payment Details"
 blnError = True
End Sub


Private Sub FillBankCombo(cbo As ComboBox)
 cbo.Clear
 cbo.AddItem "Bank Of Ireland"
 cbo.AddItem "AIB"
End Sub


Private Property Get CardType() As String
 If optCardType(0) Then
    CardType = "VISA"
 ElseIf optCardType(1) Then
    CardType = "Laser"
 ElseIf optCardType(2) Then
    CardType = "Master Card"
 ElseIf optCardType(3) Then
    CardType = "American Express"
 End If
End Property


Private Property Let CardType(ByVal strNewCardType As String)
 lblCardType(0).FontBold = False
 lblCardType(1).FontBold = False
 lblCardType(2).FontBold = False
 lblCardType(3).FontBold = False
 Select Case strNewCardType
  Case "VISA"
    optCardType(0).Value = True
    lblCardType(0).FontBold = True
  Case "Laser"
    optCardType(1).Value = True
    lblCardType(1).FontBold = True
  Case "Master Card"
    optCardType(2).Value = True
    lblCardType(2).FontBold = True
  Case "American Express"
    optCardType(3).Value = True
    lblCardType(3).FontBold = True
 End Select
End Property


Private Property Get NSC() As String
 NSC = txtNSC(0) & "-" & txtNSC(1) & "-" & txtNSC(2)
End Property

Private Property Let NSC(ByVal strNewNSC As String)
 txtNSC(0) = Mid(strNewNSC, 1, 2)
 txtNSC(1) = Mid(strNewNSC, 4, 2)
 txtNSC(2) = Mid(strNewNSC, 7, 2)
End Property


Private Property Get ExpDate() As String
 ExpDate = txtExpDate(0) & "/" & txtExpDate(1)
End Property

Private Property Let ExpDate(ByVal strNewExpDate As String)
 txtExpDate(0) = Mid(strNewExpDate, 1, 2)
 txtExpDate(1) = Mid(strNewExpDate, 4, 2)
End Property


Private Property Get CardNumber() As String
 CardNumber = txtCardNo(0) & txtCardNo(1) & txtCardNo(2) & txtCardNo(3)
End Property

Private Property Let CardNumber(ByVal strNewCardNumber As String)
 txtCardNo(0) = Mid(strNewCardNumber, 1, 4)
 txtCardNo(1) = Mid(strNewCardNumber, 5, 4)
 txtCardNo(2) = Mid(strNewCardNumber, 9, 4)
 txtCardNo(3) = Mid(strNewCardNumber, 13, 4)
End Property



Private Sub lblCardType_Click(Index As Integer)
 If optCardType(Index).Enabled Then
    optCardType(Index).Value = True
 End If
End Sub

Private Sub optCardType_Click(Index As Integer)
 lblCardType(0).FontBold = False
 lblCardType(1).FontBold = False
 lblCardType(2).FontBold = False
 lblCardType(3).FontBold = False
 lblCardType(Index).FontBold = True
End Sub



Private Sub txtBankAddress_GotFocus()
 If Not txtBankAddress.Locked Then
    CmdSave.Default = False
 End If
End Sub

Private Sub txtBankAddress_LostFocus()
 If Not txtBankAddress.Locked Then
    CmdSave.Default = True
 End If
End Sub

Private Sub txtCardNo_Change(Index As Integer)
 If mcsFormState = csView Then Exit Sub
 If Index = 3 Then Exit Sub
 If Len(txtCardNo(Index)) = 4 Then
    txtCardNo(Index + 1).SetFocus
 End If
End Sub

Private Sub txtCardNo_GotFocus(Index As Integer)
 txtCardNo(Index).SelStart = 0
 txtCardNo(Index).SelLength = 4
End Sub


Private Sub txtExpDate_Change(Index As Integer)
 If mcsFormState = csView Then Exit Sub
 If Index = 0 Then
    If Len(txtExpDate(Index)) = 2 Then
       txtExpDate(1).SetFocus
    End If
 End If
End Sub

Private Sub txtExpDate_GotFocus(Index As Integer)
 txtExpDate(Index).SelStart = 0
 txtExpDate(Index).SelLength = 2
End Sub

Private Sub txtExpDate_LostFocus(Index As Integer)
 If IsNumeric(txtExpDate(Index)) Then
    txtExpDate(Index) = Format(txtExpDate(Index), "00")
 End If
End Sub

Private Sub txtNSC_Change(Index As Integer)
 If mcsFormState = csView Then Exit Sub
 If Index < 2 Then
    If Len(txtNSC(Index)) = 2 Then
       txtNSC(Index + 1).SetFocus
    End If
 End If
End Sub

Private Sub txtNSC_GotFocus(Index As Integer)
 txtNSC(Index).SelStart = 0
 txtNSC(Index).SelLength = 2
End Sub

Private Sub txtNSC_LostFocus(Index As Integer)
 If IsNumeric(txtNSC(Index)) Then
    txtNSC(Index) = Format(txtNSC(Index), "00")
 End If
End Sub
