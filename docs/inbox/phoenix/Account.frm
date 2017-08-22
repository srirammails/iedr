VERSION 5.00
Object = "{0ECD9B60-23AA-11D0-B351-00A0C9055D8E}#6.0#0"; "MSHFLXGD.OCX"
Begin VB.Form FrmAccount 
   Caption         =   "Account"
   ClientHeight    =   7950
   ClientLeft      =   60
   ClientTop       =   345
   ClientWidth     =   11835
   HelpContextID   =   4000
   KeyPreview      =   -1  'True
   LinkTopic       =   "Form1"
   MDIChild        =   -1  'True
   ScaleHeight     =   7950
   ScaleWidth      =   11835
   WindowState     =   2  'Maximized
   Begin IEDR.Search Search1 
      Height          =   1290
      Left            =   480
      TabIndex        =   0
      Top             =   120
      Width           =   7455
      _ExtentX        =   13150
      _ExtentY        =   2275
      Option1Caption  =   "D&omain Name"
      Option2Caption  =   "Nic &Handle"
      Option3Caption  =   "&Account No"
      Option4Caption  =   "Account Na&me"
      DefaultOption   =   3
      AllowNoCri      =   -1  'True
   End
   Begin VB.Frame Frame1 
      Caption         =   "Details"
      ForeColor       =   &H00000000&
      Height          =   1215
      Left            =   8160
      TabIndex        =   39
      Top             =   120
      Width           =   2775
      Begin VB.CommandButton CmdPayment 
         Caption         =   "&Payment"
         Height          =   375
         HelpContextID   =   4000
         Left            =   720
         MaskColor       =   &H8000000F&
         TabIndex        =   52
         Top             =   240
         Width           =   1335
      End
      Begin VB.CommandButton CmdVAT 
         Caption         =   "VA&T"
         Height          =   375
         HelpContextID   =   4000
         Left            =   720
         MaskColor       =   &H8000000F&
         TabIndex        =   51
         Top             =   720
         Width           =   1335
      End
   End
   Begin VB.Frame FraCurrent 
      Caption         =   "Current"
      ForeColor       =   &H00000000&
      Height          =   3780
      Left            =   480
      TabIndex        =   28
      Top             =   1440
      Width           =   10455
      Begin VB.TextBox txtWebAddress 
         BackColor       =   &H8000000F&
         ForeColor       =   &H00000000&
         Height          =   315
         Left            =   1800
         Locked          =   -1  'True
         TabIndex        =   8
         Top             =   2880
         Width           =   4860
      End
      Begin VB.TextBox txtStatus 
         Height          =   315
         Left            =   8400
         TabIndex        =   50
         Tag             =   "T"
         Top             =   2880
         Width           =   1545
      End
      Begin VB.TextBox txtTariff 
         Height          =   315
         Left            =   8400
         TabIndex        =   49
         Tag             =   "T"
         Top             =   2160
         Width           =   1545
      End
      Begin VB.TextBox txtNextInvMonth 
         Height          =   315
         Left            =   8400
         TabIndex        =   48
         Tag             =   "T"
         Top             =   1080
         Width           =   1545
      End
      Begin VB.TextBox txtInvoiceFreq 
         Height          =   315
         Left            =   8400
         TabIndex        =   47
         Tag             =   "T"
         Top             =   720
         Width           =   1545
      End
      Begin VB.TextBox txtCounty 
         Height          =   315
         Left            =   1800
         TabIndex        =   5
         Tag             =   "T"
         Top             =   2160
         Width           =   1545
      End
      Begin VB.TextBox txtPhone 
         Height          =   315
         Left            =   8400
         TabIndex        =   13
         Top             =   1440
         Width           =   1780
      End
      Begin VB.TextBox txtFax 
         Height          =   315
         Left            =   8400
         TabIndex        =   14
         Tag             =   "B"
         Top             =   1800
         Width           =   1780
      End
      Begin VB.TextBox txtRemarks 
         Height          =   315
         Left            =   1800
         TabIndex        =   9
         Top             =   3240
         Width           =   4860
      End
      Begin VB.ComboBox cboNextInvMonth 
         Height          =   315
         Left            =   8400
         Style           =   2  'Dropdown List
         TabIndex        =   12
         Tag             =   "T"
         Top             =   1080
         Width           =   1780
      End
      Begin VB.ComboBox cboInvoiceFreq 
         Height          =   315
         Left            =   8400
         Style           =   2  'Dropdown List
         TabIndex        =   11
         Tag             =   "T"
         Top             =   720
         Width           =   1780
      End
      Begin VB.TextBox txtCountry 
         BackColor       =   &H8000000F&
         ForeColor       =   &H00000000&
         Height          =   315
         Left            =   1800
         Locked          =   -1  'True
         TabIndex        =   6
         Tag             =   "T"
         Top             =   2520
         Width           =   4575
      End
      Begin VB.ComboBox cboStatus 
         BackColor       =   &H8000000F&
         Height          =   315
         ItemData        =   "Account.frx":0000
         Left            =   8400
         List            =   "Account.frx":000D
         Locked          =   -1  'True
         Style           =   2  'Dropdown List
         TabIndex        =   17
         Tag             =   "T"
         Top             =   2880
         Width           =   1780
      End
      Begin VB.ComboBox cboTariff 
         BackColor       =   &H8000000F&
         Height          =   315
         ItemData        =   "Account.frx":002D
         Left            =   8400
         List            =   "Account.frx":003A
         Locked          =   -1  'True
         Style           =   2  'Dropdown List
         TabIndex        =   15
         Tag             =   "T"
         Top             =   2160
         Width           =   1780
      End
      Begin VB.TextBox txtRegDate 
         BackColor       =   &H8000000F&
         BorderStyle     =   0  'None
         ForeColor       =   &H00000000&
         Height          =   315
         Left            =   8400
         Locked          =   -1  'True
         TabIndex        =   16
         Tag             =   "X"
         Top             =   2520
         Width           =   1780
      End
      Begin VB.TextBox txtBilinglConatact 
         BackColor       =   &H8000000F&
         ForeColor       =   &H00000000&
         Height          =   315
         Left            =   8400
         Locked          =   -1  'True
         TabIndex        =   10
         Top             =   360
         Width           =   1780
      End
      Begin VB.TextBox txtAccNo 
         BackColor       =   &H8000000F&
         BorderStyle     =   0  'None
         ForeColor       =   &H00000000&
         Height          =   315
         Left            =   1800
         Locked          =   -1  'True
         TabIndex        =   1
         Tag             =   "X"
         Top             =   360
         Width           =   1695
      End
      Begin VB.TextBox txtName 
         BackColor       =   &H8000000F&
         ForeColor       =   &H00000000&
         Height          =   315
         Left            =   1800
         Locked          =   -1  'True
         TabIndex        =   2
         Top             =   720
         Width           =   4860
      End
      Begin VB.TextBox txtAddress 
         BackColor       =   &H8000000F&
         ForeColor       =   &H00000000&
         Height          =   1035
         Left            =   1800
         Locked          =   -1  'True
         MultiLine       =   -1  'True
         TabIndex        =   3
         Top             =   1080
         Width           =   4860
      End
      Begin VB.ComboBox cboCounty 
         BackColor       =   &H8000000F&
         Height          =   315
         ItemData        =   "Account.frx":0056
         Left            =   1800
         List            =   "Account.frx":006C
         Locked          =   -1  'True
         TabIndex        =   4
         Tag             =   "T"
         Text            =   "cboCounty"
         Top             =   2160
         Width           =   1815
      End
      Begin VB.ComboBox cboCountry 
         Height          =   315
         Left            =   1800
         TabIndex        =   7
         Tag             =   "T"
         Text            =   "cboCountry"
         Top             =   2520
         Width           =   4860
      End
      Begin VB.Label Label6 
         Caption         =   "Remarks"
         BeginProperty Font 
            Name            =   "MS Sans Serif"
            Size            =   8.25
            Charset         =   0
            Weight          =   700
            Underline       =   0   'False
            Italic          =   0   'False
            Strikethrough   =   0   'False
         EndProperty
         Height          =   195
         Left            =   240
         TabIndex        =   46
         Top             =   3240
         Width           =   1215
      End
      Begin VB.Label Label5 
         Caption         =   "Next Inv Month"
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
         Left            =   6840
         TabIndex        =   45
         Top             =   1080
         Width           =   1575
      End
      Begin VB.Label Label4 
         Caption         =   "Invoice Freq"
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
         Left            =   6840
         TabIndex        =   44
         Top             =   720
         Width           =   1575
      End
      Begin VB.Label lblStatusDate 
         Height          =   315
         Left            =   8400
         TabIndex        =   43
         Top             =   3240
         Width           =   1785
      End
      Begin VB.Label Label3 
         Caption         =   "Status Date"
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
         Left            =   6840
         TabIndex        =   42
         Top             =   3240
         Width           =   1575
      End
      Begin VB.Label LblCountry 
         Caption         =   "Country"
         BeginProperty Font 
            Name            =   "MS Sans Serif"
            Size            =   8.25
            Charset         =   0
            Weight          =   700
            Underline       =   0   'False
            Italic          =   0   'False
            Strikethrough   =   0   'False
         EndProperty
         Height          =   195
         Left            =   240
         TabIndex        =   41
         Top             =   2520
         Width           =   1215
      End
      Begin VB.Label LblCounty 
         Caption         =   "County"
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
         TabIndex        =   40
         Top             =   2160
         Width           =   1215
      End
      Begin VB.Label LblSource 
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
         Height          =   195
         Left            =   6840
         TabIndex        =   38
         Top             =   2880
         Width           =   1575
      End
      Begin VB.Label LblTarriff 
         Caption         =   "Tariff"
         BeginProperty Font 
            Name            =   "MS Sans Serif"
            Size            =   8.25
            Charset         =   0
            Weight          =   700
            Underline       =   0   'False
            Italic          =   0   'False
            Strikethrough   =   0   'False
         EndProperty
         Height          =   195
         Left            =   6840
         TabIndex        =   37
         Top             =   2160
         Width           =   1575
      End
      Begin VB.Label LblPerName 
         Caption         =   "Account No"
         BeginProperty Font 
            Name            =   "MS Sans Serif"
            Size            =   8.25
            Charset         =   0
            Weight          =   700
            Underline       =   0   'False
            Italic          =   0   'False
            Strikethrough   =   0   'False
         EndProperty
         Height          =   195
         Left            =   240
         TabIndex        =   36
         ToolTipText     =   "35100"
         Top             =   360
         Width           =   1215
      End
      Begin VB.Label Label1 
         Caption         =   "Phone No"
         BeginProperty Font 
            Name            =   "MS Sans Serif"
            Size            =   8.25
            Charset         =   0
            Weight          =   700
            Underline       =   0   'False
            Italic          =   0   'False
            Strikethrough   =   0   'False
         EndProperty
         Height          =   195
         Left            =   6840
         TabIndex        =   35
         Top             =   1440
         Width           =   1575
      End
      Begin VB.Label LblCrDate 
         Caption         =   "Creation Date"
         BeginProperty Font 
            Name            =   "MS Sans Serif"
            Size            =   8.25
            Charset         =   0
            Weight          =   700
            Underline       =   0   'False
            Italic          =   0   'False
            Strikethrough   =   0   'False
         EndProperty
         Height          =   195
         Left            =   6840
         TabIndex        =   34
         Top             =   2520
         Width           =   1575
      End
      Begin VB.Label LblWebAdd 
         Caption         =   "Web Address"
         BeginProperty Font 
            Name            =   "MS Sans Serif"
            Size            =   8.25
            Charset         =   0
            Weight          =   700
            Underline       =   0   'False
            Italic          =   0   'False
            Strikethrough   =   0   'False
         EndProperty
         Height          =   195
         Left            =   240
         TabIndex        =   33
         Top             =   2880
         Width           =   1215
      End
      Begin VB.Label Lblfax 
         Caption         =   "Fax No"
         BeginProperty Font 
            Name            =   "MS Sans Serif"
            Size            =   8.25
            Charset         =   0
            Weight          =   700
            Underline       =   0   'False
            Italic          =   0   'False
            Strikethrough   =   0   'False
         EndProperty
         Height          =   195
         Left            =   6840
         TabIndex        =   32
         Top             =   1800
         Width           =   1575
      End
      Begin VB.Label LblBilContact 
         Caption         =   "Billing Contact"
         BeginProperty Font 
            Name            =   "MS Sans Serif"
            Size            =   8.25
            Charset         =   0
            Weight          =   700
            Underline       =   0   'False
            Italic          =   0   'False
            Strikethrough   =   0   'False
         EndProperty
         Height          =   195
         Left            =   6840
         TabIndex        =   31
         Top             =   360
         Width           =   1575
      End
      Begin VB.Label LblAdd1 
         Caption         =   "Address"
         BeginProperty Font 
            Name            =   "MS Sans Serif"
            Size            =   8.25
            Charset         =   0
            Weight          =   700
            Underline       =   0   'False
            Italic          =   0   'False
            Strikethrough   =   0   'False
         EndProperty
         Height          =   195
         Left            =   240
         TabIndex        =   30
         Top             =   1080
         Width           =   1215
      End
      Begin VB.Label LblNHandle 
         Caption         =   "Account Name"
         BeginProperty Font 
            Name            =   "MS Sans Serif"
            Size            =   8.25
            Charset         =   0
            Weight          =   700
            Underline       =   0   'False
            Italic          =   0   'False
            Strikethrough   =   0   'False
         EndProperty
         Height          =   195
         Left            =   240
         TabIndex        =   29
         Top             =   720
         Width           =   1575
      End
   End
   Begin VB.Frame FraOption 
      Caption         =   "Options"
      ForeColor       =   &H00000000&
      Height          =   735
      Left            =   480
      TabIndex        =   27
      Top             =   7080
      Width           =   10455
      Begin VB.CommandButton CmdAlStatus 
         Caption         =   "Alter Stat&us"
         Height          =   375
         Left            =   960
         MaskColor       =   &H8000000F&
         TabIndex        =   18
         Top             =   240
         Width           =   1100
      End
      Begin VB.CommandButton CmdCancel 
         Caption         =   "Ca&ncel"
         Height          =   375
         Left            =   5760
         MaskColor       =   &H8000000F&
         TabIndex        =   22
         Top             =   240
         Width           =   1100
      End
      Begin VB.CommandButton CmdEdit 
         Caption         =   "&Edit"
         Height          =   375
         Left            =   4560
         MaskColor       =   &H8000000F&
         TabIndex        =   21
         Top             =   240
         Width           =   1100
      End
      Begin VB.CommandButton CmdSave 
         Caption         =   "Sa&ve"
         Height          =   375
         Left            =   6960
         MaskColor       =   &H8000000F&
         TabIndex        =   23
         Top             =   240
         Width           =   1100
      End
      Begin VB.CommandButton CmdDelete 
         Caption         =   "De&lete"
         Height          =   375
         Left            =   3360
         MaskColor       =   &H8000000F&
         TabIndex        =   20
         Top             =   240
         Width           =   1100
      End
      Begin VB.CommandButton CmdAdd 
         Caption         =   "A&dd"
         Height          =   375
         Left            =   2160
         MaskColor       =   &H8000000F&
         TabIndex        =   19
         Top             =   240
         Width           =   1100
      End
      Begin VB.CommandButton CmdClose 
         Caption         =   "&Close"
         Height          =   375
         Left            =   8160
         MaskColor       =   &H8000000F&
         TabIndex        =   24
         Top             =   240
         Width           =   1100
      End
   End
   Begin VB.Frame FraHistory 
      Caption         =   "History"
      ForeColor       =   &H00000000&
      Height          =   1695
      Left            =   480
      TabIndex        =   25
      Top             =   5280
      Width           =   10455
      Begin MSHierarchicalFlexGridLib.MSHFlexGrid HFlexAccounts 
         Height          =   1335
         Left            =   120
         TabIndex        =   26
         Top             =   240
         Width           =   10215
         _ExtentX        =   18018
         _ExtentY        =   2355
         _Version        =   393216
         ForeColor       =   0
         Cols            =   5
         BackColorFixed  =   16744576
         ForeColorFixed  =   0
         BackColorSel    =   16761024
         GridColor       =   16744576
         GridColorFixed  =   16744576
         GridColorUnpopulated=   -2147483630
         AllowBigSelection=   0   'False
         FocusRect       =   0
         FillStyle       =   1
         SelectionMode   =   1
         AllowUserResizing=   1
         BeginProperty Font {0BE35203-8F91-11CE-9DE3-00AA004BB851} 
            Name            =   "MS Sans Serif"
            Size            =   8.25
            Charset         =   0
            Weight          =   700
            Underline       =   0   'False
            Italic          =   0   'False
            Strikethrough   =   0   'False
         EndProperty
         BeginProperty FontFixed {0BE35203-8F91-11CE-9DE3-00AA004BB851} 
            Name            =   "MS Sans Serif"
            Size            =   8.25
            Charset         =   0
            Weight          =   700
            Underline       =   0   'False
            Italic          =   0   'False
            Strikethrough   =   0   'False
         EndProperty
         _NumberOfBands  =   1
         _Band(0).Cols   =   5
      End
   End
End
Attribute VB_Name = "FrmAccount"
Attribute VB_GlobalNameSpace = False
Attribute VB_Creatable = False
Attribute VB_PredeclaredId = True
Attribute VB_Exposed = False
Option Explicit

'*********************************************
'*                                           *
'* Written By : Brian Devlin                 *
'* Function   : Maintain All accounts on the *
'*              system                       *
'*********************************************

Const mlngColChngDt  As Long = 4
Const mstrIreland1   As String = "Ireland"
Const mstrIreland2   As String = "Eire"
Const mstrIreland3   As String = "Northern Ireland"
Const mstrNA         As String = "N/A"

'Celina Leong Phoenix1.1 08/04/03

Const mstrUK         As String = "United Kingdom"
Const mstrUSA        As String = "USA"

Dim CountryCode      As Integer

'---------------------------------------------------

Dim mcsFormState     As ControlState
Dim mblnAlterStatus  As Boolean      '* Indicates if user is editing just the status field
Dim mblnRecordLoaded As Boolean      '* Indicates if there is a valid record displayed
Dim mlngAccountNo    As Long         '* Key of record loaded
Dim mstrTimeStamp    As String       '* Holds the timestamd for the loaded record
Dim mblnHistory      As Boolean      '* Indicates if a History record is being displayed
Dim mstrOrigBillNH   As String

Private Sub PopAccount_Grid()
 With HFlexAccounts
     .ColWidth(0) = 100
     .ColWidth(1) = 1500
     .ColWidth(2) = 2000
     .ColWidth(3) = 6285
     .ColWidth(4) = 0
 
     .TextMatrix(0, 1) = "Date"
     .TextMatrix(0, 2) = "Nic Handle"
     .TextMatrix(0, 3) = "Remark"
 End With
End Sub


Private Sub cboCountry_Click()
 If mcsFormState = csAdd Or mcsFormState = csEdit Then
''    If (cboCountry <> mstrIreland1) And (cboCountry <> mstrIreland2) And (cboCountry <> mstrIreland3) Then
''       cboCounty = mstrNA
''    End If
'' Celina Leong Phoenix1.1
    If (cboCountry <> mstrIreland1) And (cboCountry <> mstrIreland2) And (cboCountry <> mstrIreland3) _
        And (cboCountry <> mstrUK) And (cboCountry <> mstrUSA) Then
          cboCounty.Clear
          cboCounty = mstrNA
    
    Else
    
        If cboCountry = mstrUK Then
                
                 CountryCode = mstrUKCode
                 FillCountyCombo cboCounty, CountryCode
        ElseIf cboCountry = mstrUSA Then
               
                 CountryCode = mstrUSACode
                 FillCountyCombo cboCounty, CountryCode
                 
        ElseIf cboCountry = mstrIreland3 Then
              
                 CountryCode = mstrNIRECode
                 FillCountyCombo cboCounty, CountryCode
        ElseIf (cboCountry = mstrIreland1) Or (cboCountry = mstrIreland2) Then
             
                 CountryCode = mstrIRECode
                 FillCountyCombo cboCounty, CountryCode
        End If
    End If
    
''----------------------------------------------------------------------------
 End If
End Sub



Private Sub CmdAdd_Click()
 mcsFormState = csAdd
 mblnRecordLoaded = False
 
 FormatControls Me, csAdd
 TxtAccNo = "(auto)"
 lblStatusDate.Caption = ""
 cboCountry.Text = mstrIreland1
 TxtRegDate.Text = Date
 DefaultTarif cboTariff
 DefaultStatus cboStatus
 cboNextInvMonth.ListIndex = Month(TxtRegDate) - 1
 LoadAccountHistory "-1"
 
 ShowButtons False, False, False, False, True, True, False
 
 txtName.SetFocus
End Sub


'* Only allow editing of the Status and Remarks fields
Private Sub CmdAlStatus_Click()
 mcsFormState = csEdit
 mblnAlterStatus = True
 
 txtRemarks = ""
 FormatControl cboStatus, csEdit
 FormatControl txtRemarks, csEdit
 ShowButtons False, False, False, False, True, True, False
 
 cboStatus.SetFocus
End Sub


'* Cancel Adding a record or Editing a record or Editing Status Field
Private Sub CmdCancel_Click()
 If mblnHistory Then
    LoadAccount mlngAccountNo
 Else
    LoadAccount mlngAccountNo
 
    mcsFormState = csView
    mblnAlterStatus = False
 
    FormatControls Me, csView
 End If
 cboCounty.Clear 'Celina Leong Phoenix1.1 14/04/03
 ShowButtons True, True, True, True, False, False, True
End Sub


Private Sub CmdClose_Click()
 Unload Me
End Sub


'* Delete by changing status to Deleted
'* account not removed from the system
Private Sub CmdDelete_Click()
 CmdAlStatus.Value = True
 cboStatus.Text = "Deleted"
 FormatControl cboStatus, csView
 txtRemarks.SetFocus
End Sub


Private Sub CmdEdit_Click()
 mcsFormState = csEdit
 
 txtRemarks = ""
 FormatControls Me, csEdit
 FormatControl TxtAccNo, csView
 FormatControl cboStatus, csView
 FormatControl txtStatus, csView
 ShowButtons False, False, False, False, True, True, False
 
 txtName.SetFocus
End Sub


Private Sub CmdPayment_Click()
 If mcsFormState And (csEdit + csAdd) Then
    FrmPayment.ShowPayment txtBilinglConatact, False, True
 Else
    FrmPayment.ShowPayment txtBilinglConatact, True, True
 End If
End Sub


'* Saves an Added Record or Saves an edited record (both Status and rest of fields)
Private Sub CmdSave_Click()
 Dim blnError As Boolean
 Dim ctlError As Control
 
 Screen.MousePointer = vbHourglass
 If mcsFormState = csAdd Then
    If IsValid Then
       AddAccount blnError
    Else
       blnError = True
    End If
 
 ElseIf mcsFormState = csEdit Then
    If mblnAlterStatus Then
       If IsValidStatus Then
          UpdateStatus blnError
       Else
          blnError = True
       End If
    Else
       
       If IsValid Then
          UpdateAccount blnError
       Else
          blnError = True
       End If
    End If
 End If
 
 If Not blnError Then
    mcsFormState = csView
    FormatControls Me, csView
    mblnAlterStatus = False
    ShowButtons True, True, True, True, False, False, True
 Else
    Set ctlError = FindControlInError(Me)
    If Not (ctlError Is Nothing) Then
       ctlError.SetFocus
    End If
 End If
 
 Screen.MousePointer = vbNormal
End Sub


Private Sub CmdVAT_Click()
 If mcsFormState And (csEdit + csAdd) Then
    FrmVAT.ShowVat txtBilinglConatact, False, True
 Else
    FrmVAT.ShowVat txtBilinglConatact, True, True
 End If
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
  Case vbKeyF4    '* Add
    If CmdAdd.Enabled Then
       CmdAdd.Value = True
    End If
  Case vbKeyF5    '* Refresh
    If Search1.Enabled Then
       LoadAccount mlngAccountNo
    End If
 End Select
End Sub


Private Sub Form_Load()
 mcsFormState = csView
 
 mblnRecordLoaded = False
 PopAccount_Grid
 FillCountyCombo cboCounty, CountryCode
 FillCountryCombo cboCountry
 FillTariffCombo cboTariff
 FillStatusCombo cboStatus
 FillMonthCombo cboNextInvMonth
 FillInvFreqCombo cboInvoiceFreq
 
 FormatControls Me, csView
 ShowButtons True, True, True, True, False, False, True
 txtInvoiceFreq = ""

 'tsmyth oct2003 - ensure english format date on PC
 Call CheckDateFormat

End Sub


Private Sub Form_Resize()
 CenterControls Me
End Sub


'* Load Historical details - First row represents current details
Private Sub HFlexAccounts_DblClick()
 Dim lngRow As Long
 
 If Not mblnRecordLoaded Then Exit Sub
 
 lngRow = HFlexAccounts.Row
'Celina Leong Phoenix1.1 24/04/03
' If lngRow = 1 Then
'    LoadAccount mlngAccountNo
' Else
    LoadAccount mlngAccountNo, HFlexAccounts.TextMatrix(lngRow, mlngColChngDt)
' End If
End Sub


Private Sub HFlexAccounts_KeyDown(KeyCode As Integer, Shift As Integer)
 If KeyCode = vbKeyReturn Then
    If mblnRecordLoaded Then
       HFlexAccounts_DblClick
    End If
 End If
End Sub


Private Sub HFlexAccounts_SelChange()
 HFlexAccounts.RowSel = HFlexAccounts.Row
End Sub


'* Write the SQL to search the database
Private Sub Search1_GetSQL(ByVal lngOptionSelected As Long, strSQL As String, ByVal strSearch As String)
 Select Case lngOptionSelected
   Case 1  '* Domain Name
'   modify by celina 11/07/02
'     strSQL = "SELECT A_Name, A_Number "
'     strSQL = strSQL & "FROM Account "
'     strSQL = strSQL & "WHERE Web_Address LIKE " & CDBText(strSearch & "%") & " "
'     strSQL = strSQL & "ORDER BY A_Number"
      
         
      strSQL = "SELECT DISTINCT A_Name, Account.A_Number "
      strSQL = strSQL & "FROM Domain "
      strSQL = strSQL & "INNER JOIN Account "
      strSQL = strSQL & "ON Domain.A_Number = Account.A_Number "
      strSQL = strSQL & "WHERE Domain.D_Name LIKE " & CDBText(strSearch & "%") & " "
      strSQL = strSQL & "ORDER BY Account.A_Number"

     
      
   Case 2  '* NicHandle
     strSQL = "SELECT A_Name, A_Number "
     strSQL = strSQL & "FROM Account "
     strSQL = strSQL & "WHERE Billing_NH LIKE " & CDBText(strSearch & "%") & " "
     strSQL = strSQL & "ORDER BY A_Number"
     
   Case 3  '* Account No
     If IsNumeric(strSearch) Then
        strSQL = "SELECT A_Name, A_Number "
        strSQL = strSQL & "FROM Account "
        strSQL = strSQL & "WHERE A_Number = " & strSearch & " "
        strSQL = strSQL & "ORDER BY A_Number"
     End If
     
   Case 4  '* Account Name
     strSQL = "SELECT A_Name, A_Number "
     strSQL = strSQL & "FROM Account "
     strSQL = strSQL & "WHERE A_Name LIKE " & CDBText(strSearch & "%") & " "
     strSQL = strSQL & "ORDER BY A_Number"
     
 End Select
End Sub


Private Sub Search1_SelectedResult(ByVal strResult As String, ByVal strData As String, ByVal lngOptionSelected As Long)
 LoadAccount strData
End Sub


'* Inserts the record into the database
'* Copies the inserted record to the history table
'* Reloads the record to get new timestamp and any database formatting/truncation
Private Sub AddAccount(ByRef blnError As Boolean)
 Dim strSQL         As String
 Dim lngRowsUpdated As Long
 
 On Error GoTo AddAccountERROR
 
 DB.BeginTransaction
 
 strSQL = "INSERT INTO Account ("
 strSQL = strSQL & "A_Number, "
 strSQL = strSQL & "A_Name, "
 strSQL = strSQL & "Billing_NH, "
 strSQL = strSQL & "Address, "
 strSQL = strSQL & "County, "
 strSQL = strSQL & "Country, "
 strSQL = strSQL & "Web_Address, "
 strSQL = strSQL & "Phone, "
 strSQL = strSQL & "Fax, "
 strSQL = strSQL & "A_Status, "
 strSQL = strSQL & "A_Status_Dt, "
 strSQL = strSQL & "A_Tariff, "
 strSQL = strSQL & "A_Reg_Dt, "
 strSQL = strSQL & "Invoice_Freq, "
 strSQL = strSQL & "Next_Invoice_Month, "
 strSQL = strSQL & "A_Remarks) "
 strSQL = strSQL & "VALUES ( "
 strSQL = strSQL & "NULL, "
 strSQL = strSQL & CDBText(txtName) & ", "
 strSQL = strSQL & CDBText(txtBilinglConatact) & ", "
 strSQL = strSQL & CDBText(TxtAddress) & ", "
 strSQL = strSQL & CDBText(cboCounty) & ", "
 strSQL = strSQL & CDBText(cboCountry) & ", "
 strSQL = strSQL & CDBText(txtWebAddress) & ", "
 strSQL = strSQL & CDBText(txtPhone) & ", "
 strSQL = strSQL & CDBText(txtFax) & ", "
 strSQL = strSQL & CDBText(cboStatus) & ", "
 strSQL = strSQL & CDBDate(Now) & ", "
 strSQL = strSQL & CDBText(cboTariff) & ", "
 strSQL = strSQL & CDBDate(TxtRegDate) & ", "
 strSQL = strSQL & CDBText(cboInvoiceFreq) & ", "
 strSQL = strSQL & CDBText(cboNextInvMonth) & ", "
 strSQL = strSQL & CDBText(txtRemarks) & "); "
 
 DB.Execute strSQL, , , , lngRowsUpdated
 
 mlngAccountNo = DB.LastInsertID("Account", "A_Number")
 
 WriteHistory
 
 If lngRowsUpdated <> 1 Then
    DB.RollbackTransaction
    blnError = True
    MsgBox "Could not add Account.", vbExclamation
 Else
    DB.CommitTransaction
    LoadAccount mlngAccountNo
    blnError = False
 End If
Exit Sub
AddAccountERROR:
 On Error Resume Next
 
 If Err.Number = glngDuplicateKey Then
    MsgBox "Account number already exists", vbExclamation
 Else
    DB.ShowError "Unable to Add Account"
 End If
  
 If DB.TransactionLevel > 0 Then
    DB.RollbackTransaction
 End If
 blnError = True
End Sub


'* Updates the record into the database (using the Timestamp
'*   to ensure another user has not updated the record while editing)
'* Copies the updated record to the history table
'* Reloads the record to get new timestamp and any database formatting/truncation
Private Sub UpdateAccount(ByRef blnError As Boolean)
 Dim strSQL         As String
 Dim lngRowsUpdated As Long
 
 On Error GoTo UpdateAccountERROR
    
 If ChangedControls(Me) Then
    DB.BeginTransaction
     
    WriteHistory 'Celina Leong Phoenix1.1 24/04/03
    strSQL = "UPDATE Account SET "
    strSQL = strSQL & "A_Name=" & CDBText(txtName) & ", "
    strSQL = strSQL & "Billing_NH=" & CDBText(txtBilinglConatact) & ", "
    strSQL = strSQL & "Address=" & CDBText(TxtAddress) & ", "
    strSQL = strSQL & "County=" & CDBText(cboCounty) & ", "
    strSQL = strSQL & "Country=" & CDBText(cboCountry) & ", "
    strSQL = strSQL & "Web_Address=" & CDBText(txtWebAddress) & ", "
    strSQL = strSQL & "Phone=" & CDBText(txtPhone) & ", "
    strSQL = strSQL & "Fax=" & CDBText(txtFax) & ", "
    strSQL = strSQL & "Invoice_Freq=" & CDBText(cboInvoiceFreq) & ", "
    strSQL = strSQL & "Next_Invoice_Month=" & CDBText(cboNextInvMonth) & ", "
    strSQL = strSQL & "A_Tariff=" & CDBText(cboTariff) & ", "
    strSQL = strSQL & "A_Remarks=" & CDBText(txtRemarks) & " "
    strSQL = strSQL & "WHERE A_Number = " & mlngAccountNo & " "
    strSQL = strSQL & "AND A_TStamp = " & CDBTimeStamp(mstrTimeStamp) & " "
    
    DB.Execute strSQL, , , , lngRowsUpdated
    
    ' WriteHistory 'Celina Leong Phoenix1.1 24/04/03
    
    If mstrOrigBillNH <> txtBilinglConatact Then
       If mlngAccountNo <> glngGuestAccount Then
          UpdateAllBillingNH mlngAccountNo, txtBilinglConatact
       End If
    End If
    
    If lngRowsUpdated <> 1 Then
       DB.RollbackTransaction
       blnError = True
       MsgBox "Could not update Account." & vbCrLf & vbCrLf & "Possible reasons : Account updated by another user while you were editing it", vbExclamation
    Else
       DB.CommitTransaction
       LoadAccount mlngAccountNo
       blnError = False
    End If
 End If
Exit Sub
UpdateAccountERROR:
 On Error Resume Next
 
 If DB.TransactionLevel > 0 Then
    DB.RollbackTransaction
 End If
 DB.ShowError "Unable to Update Account"
 blnError = True
End Sub


Private Sub UpdateAllBillingNH(ByVal lngAccountNo As Long, ByVal strBilingNH As String)
 Dim strSQL     As String
 Dim rec        As ADODB.Recordset
 Dim strContact As String
 
 strSQL = "SELECT C.D_Name, C.Contact_NH "
 strSQL = strSQL & "FROM  Domain  AS D, "
 strSQL = strSQL & "      Contact AS C "
 strSQL = strSQL & "WHERE D.D_Name=C.D_Name "
 strSQL = strSQL & "AND   D.A_Number=" & lngAccountNo & " "
 strSQL = strSQL & "AND   C.Type='B'"
 
 DB.Execute strSQL, rec
 strContact = CDBText(strBilingNH)
 
 Do Until rec.EOF
    strSQL = "UPDATE Contact SET "
    strSQL = strSQL & "Contact_NH=" & strContact & " "
    strSQL = strSQL & "WHERE D_Name=" & CDBText(rec.Fields("D_Name")) & " "
    strSQL = strSQL & "AND   Type='B'"
    DB.Execute strSQL
     
    WriteContactHistory rec.Fields("D_Name"), "B"
    rec.MoveNext
 Loop
End Sub


'* Updates the Status and Remarks field (using the Timestamp
'*   to ensure another user has not updated the record while editing)
'* Copies the updated record to the history table
'* Reloads the record to get new timestamp and any database formatting/truncation
Private Sub UpdateStatus(ByRef blnError As Boolean)
 Dim strSQL         As String
 Dim lngRowsUpdated As Long

 On Error GoTo UpdateStatusERROR

 If ChangedControls(Me) Then
    DB.BeginTransaction
   
    strSQL = "UPDATE Account SET "
    strSQL = strSQL & "A_Status=" & CDBText(cboStatus) & ", "
    strSQL = strSQL & "A_Status_Dt=NOW(), "
    strSQL = strSQL & "A_Remarks=" & CDBText(txtRemarks) & " "
    strSQL = strSQL & "WHERE A_Number = " & CDBText(TxtAccNo) & " "
    strSQL = strSQL & "AND A_TStamp = " & CDBTimeStamp(mstrTimeStamp) & " "
    
    DB.Execute strSQL, , , , lngRowsUpdated
    
    If lngRowsUpdated <> 1 Then
       DB.RollbackTransaction
       MsgBox "Could not update Account." & vbCrLf & vbCrLf & "Possible reasons : Account updated by another user while you were editing it", vbExclamation
    Else
       WriteHistory
       DB.CommitTransaction
    End If
    LoadAccount mlngAccountNo
 End If
Exit Sub
UpdateStatusERROR:
 On Error Resume Next
 
 If DB.TransactionLevel > 0 Then
    DB.RollbackTransaction
 End If
 DB.ShowError "Unable to Update Status"
 blnError = True
End Sub


'* Indicates if an Account can be deleted
'* Check for referential integrity
'* Cannont delete system accounts 0 + 1
Private Function CanDelete() As Boolean
 Dim strSQL As String
 Dim rec    As ADODB.Recordset

 If (mlngAccountNo = glngDomainAccount) Or (mlngAccountNo = glngGuestAccount) Then
    MsgBox "Cannot delete Account. Built in Account", vbCritical
    CanDelete = False
    Exit Function
 End If
 
 strSQL = "SELECT D_Name FROM Domain WHERE A_Number=" & mlngAccountNo & " LIMIT 1"
 DB.Execute strSQL, rec
 If Not rec.EOF Then
    MsgBox "Cannot delete Account. Domains exist that use this Account number", vbCritical
    CanDelete = False
    Exit Function
 End If
 
 strSQL = "SELECT Nic_Handle FROM NicHandle WHERE A_Number=" & mlngAccountNo & " LIMIT 1"
 DB.Execute strSQL, rec
 If Not rec.EOF Then
    MsgBox "Cannot delete Account. NIC Handles exist that use this Account number", vbCritical
    CanDelete = False
    Exit Function
 End If
 
 strSQL = "SELECT T_Number FROM Ticket WHERE A_Number=" & mlngAccountNo & " LIMIT 1"
 DB.Execute strSQL, rec
 If Not rec.EOF Then
    MsgBox "Cannot delete Account. Tickets exist that use this Account number", vbCritical
    CanDelete = False
    Exit Function
 End If
 
 CanDelete = True
End Function


'* Loads the specified AccountNumbers Record onto the screen
Public Sub LoadAccount(ByVal lngAccountNo As Long, Optional strHistDate As String = "")
 Dim strSQL As String
 Dim rec    As ADODB.Recordset
 
 On Error GoTo LoadAccountERROR
 Screen.MousePointer = vbHourglass
 
 If strHistDate = "" Then
    strSQL = "SELECT * FROM Account WHERE A_Number = " & lngAccountNo
 Else
    strSQL = "SELECT * FROM AccountHist "
    strSQL = strSQL & "WHERE A_Number = " & lngAccountNo & " "
    strSQL = strSQL & "AND Chng_Dt = " & CDBTime(strHistDate)
 End If
 
 DB.Execute strSQL, rec
 
 If Not rec.EOF Then
    TxtAccNo.Text = Format(rec.Fields("A_Number"), gstrAccountNoPad)
    txtName.Text = rec.Fields("A_Name")
    TxtAddress.Text = rec.Fields("Address")
    FillAddress rec.Fields("County"), rec.Fields("Country")
    txtWebAddress.Text = rec.Fields("Web_Address")
    cboStatus.Text = rec.Fields("A_Status")
    txtStatus.Text = rec.Fields("A_Status")
    lblStatusDate.Caption = FormatDate(rec.Fields("A_Status_Dt"))
    txtPhone.Text = rec.Fields("Phone")
    txtFax.Text = NoNull(rec.Fields("Fax"))
    txtBilinglConatact.Text = rec.Fields("Billing_NH")
    txtBilinglConatact.ToolTipText = GetName(txtBilinglConatact.Text)
    cboTariff.Text = rec.Fields("A_Tariff")
    txtTariff.Text = rec.Fields("A_Tariff")
    TxtRegDate.Text = FormatDate(rec.Fields("A_Reg_Dt"))
    mstrTimeStamp = rec.Fields("A_TStamp")
    cboInvoiceFreq = rec.Fields("Invoice_Freq")
    txtInvoiceFreq = rec.Fields("Invoice_Freq")
    cboNextInvMonth = rec.Fields("Next_Invoice_Month")
    txtNextInvMonth = rec.Fields("Next_Invoice_Month")
    txtRemarks = rec.Fields("A_Remarks")
    mblnRecordLoaded = True
    
    If strHistDate = "" Then
       FraCurrent.Caption = "Current"
       mlngAccountNo = lngAccountNo
       mstrOrigBillNH = txtBilinglConatact
       LoadAccountHistory lngAccountNo
       mblnHistory = False
    Else
       FraCurrent.Caption = "Historical"
       mblnHistory = True
    End If
 Else
    mblnRecordLoaded = False
 End If
 
 ShowButtons True, True, True, True, False, False, True
 Screen.MousePointer = vbNormal
Exit Sub
LoadAccountERROR:
 Screen.MousePointer = vbNormal
 DB.ShowError "Unable to Load Account"
End Sub


'* Loads the Account History for the displayed record into the
'* History Grid
Private Sub LoadAccountHistory(ByVal lngAccountNo As Long)
 Dim strSQL As String
 Dim rec    As ADODB.Recordset
 
 On Error GoTo LoadAccountHistoryERROR
 
 strSQL = "SELECT " & FormatDBDate("Chng_Dt") & " AS 'Date',"
 strSQL = strSQL & "Chng_NH AS 'Nic Handle', "
 strSQL = strSQL & "A_Remarks AS Remark, "
 strSQL = strSQL & "Chng_Dt "
 strSQL = strSQL & "FROM AccountHist "
 strSQL = strSQL & "WHERE A_Number = " & lngAccountNo & " "
 strSQL = strSQL & "ORDER BY Chng_Dt DESC"
 
 DB.Execute strSQL, rec
 
 HFlexAccounts.Clear
 HFlexAccounts.FixedRows = 0
 HFlexAccounts.Rows = 2
 HFlexAccounts.FixedRows = 1

 Set HFlexAccounts.DataSource = rec.DataSource

 If HFlexAccounts.Rows <= 1 Then
    HFlexAccounts.Row = 0
 End If

Exit Sub
LoadAccountHistoryERROR:
 DB.ShowError "Unable to Load Account History"
End Sub


Private Sub FillAddress(ByVal strCounty As String, ByVal strCountry As String)
 On Error Resume Next
 txtCounty = strCounty
 txtCountry = strCountry
 cboCounty = strCounty
 cboCountry = strCountry
End Sub

'* Copies the displayed Account record into the History
'* table along with the users NicHandle and current Date
Private Sub WriteHistory()
 Dim strSQL As String
 
 strSQL = "INSERT INTO AccountHist ("
 strSQL = strSQL & "Chng_Dt, "
 strSQL = strSQL & "Chng_NH, "
 strSQL = strSQL & "A_Number, "
 strSQL = strSQL & "A_Name, "
 strSQL = strSQL & "Billing_NH, "
 strSQL = strSQL & "Address, "
 strSQL = strSQL & "County, "
 strSQL = strSQL & "Country, "
 strSQL = strSQL & "Web_Address, "
 strSQL = strSQL & "Phone, "
 strSQL = strSQL & "Fax, "
 strSQL = strSQL & "A_Status, "
 strSQL = strSQL & "A_Status_Dt, "
 strSQL = strSQL & "A_Tariff, "
 strSQL = strSQL & "Credit_Limit, "
 strSQL = strSQL & "A_Reg_Dt, "
 strSQL = strSQL & "Invoice_Freq, "
 strSQL = strSQL & "Next_Invoice_Month, "
 strSQL = strSQL & "A_Remarks) "
 
 strSQL = strSQL & "SELECT DISTINCT " 'CC20051221 fix for MySQLv4.0
 strSQL = strSQL & "NOW(), "
 strSQL = strSQL & CDBText(UserID) & ", "
 strSQL = strSQL & "A_Number, "
 strSQL = strSQL & "A_Name, "
 strSQL = strSQL & "Billing_NH, "
 strSQL = strSQL & "Address, "
 strSQL = strSQL & "County, "
 strSQL = strSQL & "Country, "
 strSQL = strSQL & "Web_Address, "
 strSQL = strSQL & "Phone, "
 strSQL = strSQL & "Fax, "
 strSQL = strSQL & "A_Status, "
 strSQL = strSQL & "A_Status_Dt, "
 strSQL = strSQL & "A_Tariff, "
 strSQL = strSQL & "Credit_Limit, "
 strSQL = strSQL & "A_Reg_Dt, "
 strSQL = strSQL & "Invoice_Freq, "
 strSQL = strSQL & "Next_Invoice_Month, "
 strSQL = strSQL & "A_Remarks "
 strSQL = strSQL & "FROM Account "
 strSQL = strSQL & "WHERE A_Number=" & mlngAccountNo
 DB.Execute strSQL

End Sub


Private Sub WriteContactHistory(ByVal strDomainName As String, ByVal strType As String, Optional ByVal dtmChangedDate As Date = 0)
 Dim strSQL         As String
 Dim lngRowsUpdated As Long

 strSQL = "INSERT INTO ContactHist ("
 strSQL = strSQL & "Chng_NH,"
 strSQL = strSQL & "Chng_Dt,"
 strSQL = strSQL & "D_Name,Contact_NH,Type) "
 
 strSQL = strSQL & "SELECT DISTINCT " 'CC20051221 fix for MySQLv4.0
 strSQL = strSQL & CDBText(UserID) & ","
 If dtmChangedDate = 0 Then
    strSQL = strSQL & "NOW(),"
 Else
    strSQL = strSQL & CDBTime(dtmChangedDate) & ","
 End If
 strSQL = strSQL & "D_Name,Contact_NH,Type "
 strSQL = strSQL & "FROM Contact "
 strSQL = strSQL & "WHERE D_Name=" & CDBText(strDomainName) & " "
 strSQL = strSQL & "AND Type=" & CDBText(strType)
 
 DB.Execute strSQL, , , , lngRowsUpdated
 If lngRowsUpdated <> 1 Then
    Err.Raise 9999, "TicketBatch", "Could not Insert Contact History Type " & strType
 End If
End Sub


'* Enables/Disables command buttons
Private Sub ShowButtons(ByVal blnAlterStatus As Boolean, _
                        ByVal blnAdd As Boolean, _
                        ByVal blnDelete As Boolean, _
                        ByVal blnEdit As Boolean, _
                        ByVal blnCancel As Boolean, _
                        ByVal blnSave As Boolean, _
                        ByVal blnClose As Boolean)

 Dim blnAllowed     As Boolean
 
 blnAllowed = HasAuthority(atAccounts)
 
 CmdAlStatus.Enabled = blnAlterStatus And mblnRecordLoaded And blnAllowed And (Not mblnHistory)
 CmdAdd.Enabled = blnAdd And blnAllowed
 CmdDelete.Enabled = blnDelete And mblnRecordLoaded And blnAllowed And (Not mblnHistory)
 cmdEdit.Enabled = blnEdit And mblnRecordLoaded And blnAllowed And (Not mblnHistory)
 cmdCancel.Enabled = blnCancel Or mblnHistory
 cmdSave.Enabled = blnSave And blnAllowed And (Not mblnHistory)
 cmdClose.Enabled = blnClose
 Search1.Enabled = Not blnSave
 FraHistory.Enabled = Not blnSave
 CmdPayment.Enabled = mblnRecordLoaded Or (mcsFormState = csAdd)
 CmdVAT.Enabled = mblnRecordLoaded Or (mcsFormState = csAdd)
 
 cmdSave.Default = blnSave And blnAllowed And mblnRecordLoaded
 cmdClose.Cancel = blnClose
 cmdCancel.Cancel = blnCancel
End Sub


'* Validates input - Returns FALSE if any fields are invalid
'* Checks all TextBoxes and ComboBoxes for blank entries but
'*   ignores any control with a Tag property of "B" or "X"
'* Does additional validation necessary on other fields
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
'Celina Leong Phoenix1.1 02/04/03
'Account Processing Change No 4
'Take out check that billing contact must have a domain
' If Not IsNicHandleType(txtBilinglConatact, "B") Then
'    blnInError = True
'    FormatControl txtBilinglConatact, csError
' End If
'--------------------------------------------------------
 
 If Not IsDate(TxtRegDate.Text) Then
    blnInError = True
    FormatControl TxtRegDate, csError
 End If
 
 If (cboCountry = mstrIreland1) Or (cboCountry = mstrIreland2) Then
    If cboCounty = mstrNA Then
       blnInError = True
       FormatControl cboCounty, csError
    End If
 ElseIf (cboCountry = mstrIreland3) Then
 Else
    If cboCounty <> mstrNA Then
       blnInError = True
       FormatControl cboCounty, csError
    End If
 End If
 
 IsValid = Not blnInError
End Function


'* Validates input for Altering Status
'* Returns FALSE if any fields are invalid
Private Function IsValidStatus() As Boolean
 Dim blnInError As Boolean
 
 blnInError = False
 If Trim(txtRemarks.Text) = "" Then
    blnInError = True
    FormatControl txtRemarks, csError
 End If
 
 Select Case Trim(cboStatus)
  Case "Deleted"
    If Not CanDelete() Then
       blnInError = True
    End If
 Case "Suspended"
    If mlngAccountNo = glngDomainAccount Then
       MsgBox "Cannot Suspend Domain Registry Account.", vbCritical
       blnInError = True
    ElseIf mlngAccountNo = glngGuestAccount Then
       MsgBox "Cannot Suspend Guest Account.", vbCritical
       blnInError = True
    End If
 End Select
 IsValidStatus = Not blnInError
End Function


Private Sub FillInvFreqCombo(cbo As ComboBox)
 cbo.Clear
 cbo.AddItem "Monthly"
 
 cbo.ListIndex = 0
End Sub


'* Allows carrage return in Address field
Private Sub txtAddress_GotFocus()
 If Not TxtAddress.Locked Then
    cmdSave.Default = False
 End If
End Sub

Private Sub txtAddress_LostFocus()
 If Not TxtAddress.Locked Then
    cmdSave.Default = True
 End If
End Sub

Private Sub txtBilinglConatact_DblClick()
 If mblnRecordLoaded Then
    FrmNicHandle.Show_Results_NHandle txtBilinglConatact
 End If
End Sub

Private Sub txtBilinglConatact_LostFocus()
 If mcsFormState = csAdd Or mcsFormState = csEdit Then
    If txtBilinglConatact.DataChanged Then
       txtBilinglConatact.ToolTipText = GetName(txtBilinglConatact)
    End If
 End If
End Sub
