VERSION 5.00
Object = "{0ECD9B60-23AA-11D0-B351-00A0C9055D8E}#6.0#0"; "mshflxgd.ocx"
Begin VB.Form FrmNicHandle 
   Caption         =   "Nic Handle Maintenance"
   ClientHeight    =   8190
   ClientLeft      =   60
   ClientTop       =   345
   ClientWidth     =   11835
   HelpContextID   =   3000
   LinkTopic       =   "Form1"
   MDIChild        =   -1  'True
   ScaleHeight     =   8190
   ScaleWidth      =   11835
   WindowState     =   2  'Maximized
   Begin VB.Frame FraDetails 
      Caption         =   "Details"
      ForeColor       =   &H00000000&
      Height          =   1335
      Left            =   8760
      TabIndex        =   45
      Top             =   120
      Width           =   2415
      Begin VB.CommandButton CmdVAT 
         Caption         =   "VA&T"
         Height          =   375
         Left            =   600
         MaskColor       =   &H8000000F&
         TabIndex        =   23
         Top             =   740
         Width           =   1335
      End
      Begin VB.CommandButton CmdPayment 
         Caption         =   "&Payment"
         Height          =   375
         Left            =   600
         MaskColor       =   &H8000000F&
         TabIndex        =   22
         Top             =   300
         Width           =   1335
      End
   End
   Begin VB.Frame FraOption 
      Caption         =   "Options"
      ForeColor       =   &H00000000&
      Height          =   855
      Left            =   360
      TabIndex        =   39
      Top             =   7080
      Width           =   10815
      Begin VB.CommandButton CmdAlStatus 
         Caption         =   "Alter Stat&us"
         Height          =   375
         Left            =   840
         MaskColor       =   &H8000000F&
         TabIndex        =   16
         Top             =   240
         Width           =   1455
      End
      Begin VB.CommandButton CmdCancel 
         Caption         =   "Ca&ncel"
         Height          =   375
         Left            =   5520
         TabIndex        =   19
         Top             =   240
         Width           =   1455
      End
      Begin VB.CommandButton CmdEdit 
         Caption         =   "&Edit"
         Height          =   375
         Left            =   3960
         MaskColor       =   &H8000000F&
         TabIndex        =   18
         Top             =   240
         Width           =   1455
      End
      Begin VB.CommandButton CmdSave 
         Caption         =   "Sa&ve"
         Height          =   375
         Left            =   7080
         TabIndex        =   20
         Top             =   240
         Width           =   1455
      End
      Begin VB.CommandButton CmdClose 
         Caption         =   "&Close"
         Height          =   375
         Left            =   8640
         MaskColor       =   &H8000000F&
         TabIndex        =   21
         Top             =   240
         Width           =   1455
      End
      Begin VB.CommandButton CmdSetPass 
         Caption         =   "&Reset Password"
         Height          =   375
         Left            =   2400
         MaskColor       =   &H8000000F&
         TabIndex        =   17
         Top             =   240
         Width           =   1455
      End
   End
   Begin VB.Frame FraCurrent 
      Caption         =   "Current"
      ForeColor       =   &H00000000&
      Height          =   3735
      Left            =   360
      TabIndex        =   30
      Top             =   1560
      Width           =   10815
      Begin VB.TextBox txtCountry 
         DataField       =   "NH_Country"
         Height          =   315
         Left            =   1800
         TabIndex        =   56
         Tag             =   "T"
         Top             =   3050
         Width           =   1695
      End
      Begin VB.TextBox txtCounty 
         DataField       =   "NH_County"
         Height          =   315
         Left            =   1800
         TabIndex        =   55
         Tag             =   "T"
         Top             =   2685
         Width           =   1695
      End
      Begin VB.ComboBox cboCountry 
         BackColor       =   &H8000000F&
         DataField       =   "NH_Country"
         Height          =   315
         ItemData        =   "FrmNicHandle.frx":0000
         Left            =   1800
         List            =   "FrmNicHandle.frx":0002
         TabIndex        =   54
         Tag             =   "T"
         Top             =   3050
         Width           =   1935
      End
      Begin VB.TextBox TxtAccNo 
         BackColor       =   &H8000000F&
         DataField       =   "A_Number"
         Height          =   300
         Left            =   8880
         MaxLength       =   8
         TabIndex        =   52
         Top             =   650
         Width           =   1695
      End
      Begin VB.ComboBox ComboFaxE 
         BackColor       =   &H8000000F&
         DataField       =   "NH_Phone"
         Height          =   315
         Left            =   8880
         TabIndex        =   51
         Top             =   1680
         Width           =   1680
      End
      Begin VB.TextBox TxtAddress 
         BackColor       =   &H8000000F&
         DataField       =   "NH_Address"
         Height          =   1335
         Left            =   1800
         MultiLine       =   -1  'True
         TabIndex        =   50
         Top             =   1320
         Width           =   5415
      End
      Begin VB.ComboBox cboCounty 
         BackColor       =   &H8000000F&
         DataField       =   "NH_County"
         Height          =   315
         ItemData        =   "FrmNicHandle.frx":0004
         Left            =   1800
         List            =   "FrmNicHandle.frx":0006
         TabIndex        =   49
         Tag             =   "T"
         Top             =   2685
         Width           =   1950
      End
      Begin VB.ComboBox ComboPhoneE 
         BackColor       =   &H8000000F&
         DataField       =   "NH_Phone"
         Height          =   315
         Left            =   8880
         TabIndex        =   48
         Top             =   1320
         Visible         =   0   'False
         Width           =   1695
      End
      Begin VB.ComboBox ComboStatus 
         BackColor       =   &H8000000F&
         DataField       =   "NH_Status"
         Height          =   315
         Left            =   8880
         Locked          =   -1  'True
         TabIndex        =   9
         Top             =   960
         Width           =   1680
      End
      Begin VB.ComboBox ComboFax 
         BackColor       =   &H8000000F&
         DataField       =   "NH_Phone"
         Height          =   315
         Left            =   8880
         TabIndex        =   11
         Top             =   1680
         Width           =   1680
      End
      Begin VB.ComboBox ComboPhone 
         BackColor       =   &H8000000F&
         DataField       =   "NH_Phone"
         Height          =   315
         Left            =   8880
         TabIndex        =   10
         Top             =   1320
         Width           =   1680
      End
      Begin VB.TextBox TxtConPass 
         BackColor       =   &H8000000F&
         BorderStyle     =   0  'None
         ForeColor       =   &H00000000&
         Height          =   300
         IMEMode         =   3  'DISABLE
         Left            =   8880
         Locked          =   -1  'True
         PasswordChar    =   "*"
         TabIndex        =   14
         Top             =   2685
         Visible         =   0   'False
         Width           =   1680
      End
      Begin VB.TextBox TxtNewPass 
         BackColor       =   &H8000000F&
         BorderStyle     =   0  'None
         ForeColor       =   &H00000000&
         Height          =   300
         IMEMode         =   3  'DISABLE
         Left            =   8880
         Locked          =   -1  'True
         PasswordChar    =   "*"
         TabIndex        =   13
         Top             =   2355
         Visible         =   0   'False
         Width           =   1680
      End
      Begin VB.TextBox TxtRemark 
         BackColor       =   &H8000000F&
         DataField       =   "NH_Remark"
         ForeColor       =   &H00000000&
         Height          =   525
         Left            =   3885
         Locked          =   -1  'True
         MultiLine       =   -1  'True
         ScrollBars      =   2  'Vertical
         TabIndex        =   15
         Top             =   3050
         Width           =   6615
      End
      Begin VB.TextBox TxtCompany 
         BackColor       =   &H8000000F&
         DataField       =   "Co_Name"
         ForeColor       =   &H00000000&
         Height          =   300
         Left            =   1800
         Locked          =   -1  'True
         TabIndex        =   6
         Top             =   1005
         Width           =   5415
      End
      Begin VB.TextBox TxtChanged 
         BackColor       =   &H8000000F&
         BorderStyle     =   0  'None
         DataField       =   "NH_Status_Dt"
         ForeColor       =   &H00000000&
         Height          =   300
         Left            =   8880
         Locked          =   -1  'True
         TabIndex        =   12
         Top             =   2025
         Width           =   1680
      End
      Begin VB.TextBox XtxtCountry 
         BackColor       =   &H8000000F&
         ForeColor       =   &H00000000&
         Height          =   300
         Left            =   120
         Locked          =   -1  'True
         TabIndex        =   7
         Top             =   1800
         Visible         =   0   'False
         Width           =   1575
      End
      Begin VB.TextBox TxtNHandle 
         BackColor       =   &H8000000F&
         DataField       =   "Nic_Handle"
         ForeColor       =   &H00000000&
         Height          =   300
         Left            =   1800
         Locked          =   -1  'True
         TabIndex        =   4
         Top             =   320
         Width           =   1950
      End
      Begin VB.TextBox TxtPerName 
         BackColor       =   &H8000000F&
         DataField       =   "NH_Name"
         ForeColor       =   &H00000000&
         Height          =   300
         Left            =   1800
         Locked          =   -1  'True
         TabIndex        =   5
         Top             =   650
         Width           =   5415
      End
      Begin VB.TextBox TxtEmail 
         BackColor       =   &H8000000F&
         DataField       =   "NH_Email"
         ForeColor       =   &H00000000&
         Height          =   300
         Left            =   4800
         Locked          =   -1  'True
         TabIndex        =   8
         Top             =   320
         Width           =   5745
      End
      Begin VB.Label Label2 
         Caption         =   "Account"
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
         Left            =   7320
         TabIndex        =   57
         Top             =   650
         Width           =   1215
      End
      Begin VB.Label LblConPass 
         Caption         =   "Confirm Password"
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
         Left            =   7320
         TabIndex        =   47
         Top             =   2685
         Visible         =   0   'False
         Width           =   1680
      End
      Begin VB.Label LblNewPass 
         Caption         =   "New Password"
         BeginProperty Font 
            Name            =   "MS Sans Serif"
            Size            =   8.25
            Charset         =   0
            Weight          =   700
            Underline       =   0   'False
            Italic          =   0   'False
            Strikethrough   =   0   'False
         EndProperty
         Height          =   315
         Left            =   7320
         TabIndex        =   46
         Top             =   2355
         Visible         =   0   'False
         Width           =   1320
      End
      Begin VB.Label Label4 
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
         Height          =   255
         Left            =   120
         TabIndex        =   44
         Top             =   3050
         Width           =   735
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
         Left            =   120
         TabIndex        =   43
         Top             =   2685
         Width           =   1095
      End
      Begin VB.Label Label1 
         Caption         =   "Requester's Remark"
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
         Left            =   3885
         TabIndex        =   42
         Top             =   2685
         Width           =   1935
      End
      Begin VB.Label LblAccComp 
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
         Height          =   195
         Left            =   120
         TabIndex        =   41
         Top             =   1005
         Width           =   1575
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
         Left            =   7320
         TabIndex        =   38
         Top             =   1005
         Width           =   1200
      End
      Begin VB.Label LblPhone 
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
         Left            =   7320
         TabIndex        =   37
         Top             =   1320
         Width           =   1005
      End
      Begin VB.Label LblAddress 
         Caption         =   "Address "
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
         Left            =   120
         TabIndex        =   36
         Top             =   1320
         Width           =   1095
      End
      Begin VB.Label LblChanged 
         Caption         =   "Last Changed"
         BeginProperty Font 
            Name            =   "MS Sans Serif"
            Size            =   8.25
            Charset         =   0
            Weight          =   700
            Underline       =   0   'False
            Italic          =   0   'False
            Strikethrough   =   0   'False
         EndProperty
         Height          =   315
         Left            =   7320
         TabIndex        =   35
         Top             =   2025
         Width           =   1560
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
         Left            =   7320
         TabIndex        =   34
         Top             =   1665
         Width           =   1005
      End
      Begin VB.Label LblNHandle 
         Caption         =   "Nic Handle"
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
         Left            =   120
         TabIndex        =   33
         Top             =   320
         Width           =   1215
      End
      Begin VB.Label LblPerName 
         Caption         =   "Person Name"
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
         Left            =   120
         TabIndex        =   32
         Top             =   650
         Width           =   1215
      End
      Begin VB.Label LblEmail 
         Caption         =   "E-mail"
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
         Left            =   4005
         TabIndex        =   31
         Top             =   320
         Width           =   645
      End
   End
   Begin VB.Frame FraScrh 
      Caption         =   "Search"
      ForeColor       =   &H00000000&
      Height          =   1335
      Left            =   360
      TabIndex        =   26
      Top             =   120
      Width           =   8295
      Begin VB.ComboBox TxtScrh 
         Height          =   315
         Left            =   3360
         TabIndex        =   0
         Top             =   360
         Width           =   2295
      End
      Begin VB.CheckBox ChkRenew 
         Caption         =   "Rene&w"
         Height          =   375
         Left            =   7080
         TabIndex        =   53
         Top             =   840
         Width           =   855
      End
      Begin VB.OptionButton OptName 
         Caption         =   "Nic Handle &Name"
         Height          =   375
         Left            =   1920
         TabIndex        =   40
         Top             =   840
         Width           =   1095
      End
      Begin VB.OptionButton OptAcc 
         Caption         =   "&Account No"
         Height          =   255
         Left            =   1920
         TabIndex        =   29
         Top             =   480
         Width           =   1335
      End
      Begin VB.OptionButton OptNHandle 
         Caption         =   "Nic &Handle"
         Height          =   255
         Left            =   240
         TabIndex        =   28
         Top             =   840
         Value           =   -1  'True
         Width           =   1455
      End
      Begin VB.OptionButton OptDName 
         Caption         =   "D&omain Name"
         Height          =   255
         Left            =   240
         TabIndex        =   27
         Top             =   480
         Width           =   1695
      End
      Begin VB.CommandButton CmdScrh 
         Caption         =   "&Search"
         Default         =   -1  'True
         Height          =   375
         Left            =   5880
         TabIndex        =   2
         Top             =   340
         Width           =   975
      End
      Begin VB.TextBox TxtScrhold 
         Height          =   285
         Left            =   3360
         TabIndex        =   1
         Top             =   360
         Width           =   2295
      End
      Begin VB.ComboBox CmbSearchList 
         Height          =   315
         Left            =   3360
         TabIndex        =   3
         Top             =   840
         Width           =   3495
      End
   End
   Begin VB.Frame FraHistory 
      Caption         =   "History"
      ForeColor       =   &H00000000&
      Height          =   1575
      Left            =   360
      TabIndex        =   24
      Top             =   5400
      Width           =   10815
      Begin MSHierarchicalFlexGridLib.MSHFlexGrid HFlexNHandle 
         Height          =   1215
         Left            =   240
         TabIndex        =   25
         Top             =   240
         Width           =   10455
         _ExtentX        =   18441
         _ExtentY        =   2143
         _Version        =   393216
         Cols            =   5
         BackColorFixed  =   16744576
         BackColorSel    =   16761024
         GridColor       =   16744576
         GridColorFixed  =   16744576
         GridColorUnpopulated=   -2147483630
         AllowBigSelection=   0   'False
         FocusRect       =   0
         FillStyle       =   1
         SelectionMode   =   1
         AllowUserResizing=   3
         BeginProperty Font {0BE35203-8F91-11CE-9DE3-00AA004BB851} 
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
Attribute VB_Name = "FrmNicHandle"
Attribute VB_GlobalNameSpace = False
Attribute VB_Creatable = False
Attribute VB_PredeclaredId = True
Attribute VB_Exposed = False
Option Explicit
'********************************************************************************
'                                                                               *
'Writen By   : Celina Leong                                                     *
'                                                                               *
'Description : View the current or historical details of an existing NIC Handle *
'              Modify the current details of a NIC Handle                       *
'              Validate a renewed NIC Handle                                    *
'              Reset a specific user password                                   *
'              View and/or modify the payment details of an existing NIC Handle *
'                   (if Billing contact only)                                   *
'              View and/or modify the VAT details of an existing NIC Handle     *
'                    (if Billing contact only)                                  *
'********************************************************************************

Private Const ACCOUNTNO  As String = "00000000"
Private Const REMARK As String = "Change Password"
Private Const RENEW As String = "Renew"
Private Const STRHISDATE As String = ""
'Const strIreland    As String = "Ireland"
'Const strNA         As String = "N/A"
Const mstrIreland1   As String = "Ireland"
Const mstrIreland2   As String = "Eire"
Const mstrIreland3   As String = "Northern Ireland"
Const mstrNA         As String = "N/A"
'Celina Leong Phoenix1.1 08/04/03
Const mstrUK         As String = "United Kingdom"
Const mstrUSA        As String = "USA"


Dim TempPhone        As String
Dim TempType         As String
Dim CountryCode      As Integer
''------------------------------------------------------
Dim RsSearch As ADODB.Recordset
Dim RsSearchResults As ADODB.Recordset
Dim mstrTimeStamp As String
Dim mblnAlterStatus As String
Dim mblnResetPassword As String
Dim mblnRecordLoaded As Boolean
Dim mcsFormState As ControlState
Dim strPhoneType As String
Dim strFaxType As String
Dim mblnProcesComboPh As Boolean
Dim mblnProcesComboFx As Boolean
Dim mlngTempP As Long
Dim mlngTempF As Long
Dim mlngPrevPhIndex As Long
Dim mstrPrevPhText As String
Dim mlngPrevFxIndex As Long
Dim mstrPrevFxText As String
Dim TempNow As String
Dim mstrNicHandle As String
Dim NHBillCInd As String
Dim strNicHandle As String
Dim mblnNoHistory As Boolean '29/05/03
Dim TempTime As String '29/05/03

'Dim HistNichandle, HistName, HistAddress, HistCounty, HistCompany As String
'Dim HistCountry, HistEmail, HistStatus, HistHMRemark As String
'Dim HistStatusDt, HistNHRegDt, HistNHTStamp, HistNHBillCInd As String
'Dim HistANumber As Integer
Dim WithEvents mfrmEditNicHandle As FrmEditNicHandle
Attribute mfrmEditNicHandle.VB_VarHelpID = -1

'JMcA 17.11.06
Dim bGuestAccount As Boolean
Dim iOriginalAccNo As Integer

Private Sub CmbSearchList_Click()
strNicHandle = CmbSearchList.Text
Show_Results_NHandle strNicHandle
End Sub
'Celina Leong Phoenix1.1 17/04/03
Private Sub CmbSearchList_Validate(KeepFocus As Boolean)
    If CmbSearchList.Listcount > 4000 Then
       KeepFocus = False
      MsgBox " Record in excess of 4000  ", vbCritical
    End If
End Sub
'----------------------------------------

'* Changable for Status field only when Alter Status command click

Private Sub CmdAlStatus_Click()

Dim ctl As Control

If TxtScrh.Text = "" And TxtNHandle = "" Then
    MsgBox " Please enter detail for Search ", vbExclamation
  
Else

 If TxtNHandle = "" Then
      MsgBox " Please enter details for search  ", vbExclamation
  Else
        txtRemark.Text = ""
        Set txtRemark.DataSource = Nothing
        mcsFormState = csEdit
        mblnAlterStatus = True
        mblnResetPassword = False
        FormatControl ComboStatus, csEdit
        FormatControl txtRemark, csEdit
        
        ShowButtons False, False, False, True, True, False
        ComboStatus.SetFocus
        ComboPhone.Enabled = False
        ComboFax.Enabled = False
        CmdScrh.Enabled = False
        TxtScrh.Enabled = False
        ChkRenew.Enabled = False

        
 End If
End If
End Sub

Private Sub CmdCancel_Click()
 Dim ctl As Control
  
  
 If mcsFormState = csEdit Then
    ' Show_Results_NHandle CmbSearchList.Text
      Show_Results_NHandle TxtNHandle.Text
 ElseIf mcsFormState = csView Then
     'Show_Results_NHandle CmbSearchList.Text
      Show_Results_NHandle TxtNHandle.Text
 End If

mcsFormState = csView
mblnAlterStatus = False

FormatControls Me, csView

FormatControl CmbSearchList, csEdit
FormatControl TxtScrh, csEdit
FormatControl ChkRenew, csEdit
txtRemark.BorderStyle = 1
ShowButtons True, True, True, False, False, True

 For Each ctl In Controls

                If TypeOf ctl Is OptionButton Then
                     ctl.Enabled = True
                     ctl.Visible = True
                End If
               
            Next
'OptHolder.Enabled = False '28/05/03
ComboPhoneE.Visible = False
ComboPhone.Visible = True
ComboPhone.Locked = False
ComboPhone.Enabled = True
ComboPhone.BackColor = vbButtonFace
ComboFaxE.Visible = False
ComboFax.Visible = True
ComboFax.Locked = False
ComboFax.Enabled = True
ComboFax.BackColor = vbButtonFace

LblNewPass.Visible = False
LblConPass.Visible = False
TxtNewPass.Visible = False
TxtConPass.Visible = False
CmbSearchList.Enabled = True
CmdScrh.Enabled = True
TxtScrh.Enabled = True
ChkRenew.Enabled = True
'OptHolder.Enabled = False ' 28/05/03
cboCounty.Clear 'Celina Leong Phoenix1.1 14/04/03
End Sub

Private Sub CmdEdit_Click()
    
Dim ctl As Control
   
If TxtScrh.Text = "" And TxtNHandle = "" Then
    MsgBox " Please enter details for search  ", vbExclamation
      
Else

  If TxtNHandle = "" Then
      MsgBox " Please enter details for search  ", vbExclamation
  Else
  
      If ComboStatus = "Renew" Or _
         ComboStatus = "New" Then
          If mblnRecordLoaded Then
             Set mfrmEditNicHandle = New FrmEditNicHandle
          End If
          
             mfrmEditNicHandle.LoadNicHandle TxtNHandle, TxtNHandle, STRHISDATE
          
      Else
      
                txtRemark.Text = "" '4/05
                mcsFormState = csEdit
                mblnAlterStatus = False
                mblnResetPassword = False
                
                FillPhoneE_FaxE
                Set txtRemark.DataSource = Nothing
'                Set ComboPhoneE.DataSource = Nothing
'                Set ComboFaxE.DataSource = Nothing
                
                ComboPhone.Visible = False
                ComboPhoneE.Visible = True
                ComboFax.Visible = False
                ComboFaxE.Visible = True
                
                
                FormatControls Me, csEdit
                FormatControl ComboPhoneE, csEdit
                FormatControl ComboFaxE, csEdit
                FormatControl TxtNHandle, csView
                FormatControl ComboStatus, csView
                FormatControl TxtChanged, csView
'                OptHolder.Enabled = False '28/05/03
                CmdScrh.Enabled = False
                ChkRenew.Enabled = False
                TxtScrh.Enabled = False
                       
                ShowButtons False, False, False, True, True, False
                TxtPerName.SetFocus
'Celina Leong Phoenix1.1 03/04/03
'                If TxtAccNo.Text = ACCOUNTNO Or TxtAccNo.Text = "" Then
'                    TxtAccNo.Visible = False
'                    TxtCompany.Visible = True
'                    FormatControl TxtAccNo, csView
'                    'TxtCompany.Tag = "B"
'                Else
'                    TxtAccNo.Visible = True
'                    TxtCompany.Visible = False
'                    FormatControl TxtCompany, csView
'                    'txtAccNo.Tag = "B"
'                End If
       End If
    End If
 End If
        
End Sub

Private Sub CmdClose_Click()
Unload Me
End Sub

Private Sub PopNicHandle_Grid()

With HFlexNHandle
     .ColWidth(0) = 100
     .ColWidth(1) = 1500
     .ColWidth(2) = 2000
     .ColWidth(3) = 9000
     .ColWidth(4) = 0
 
     .TextMatrix(0, 1) = "Date"
     .TextMatrix(0, 2) = "Nic Handle"
     .TextMatrix(0, 3) = "Remark"
 End With
 


End Sub

Private Sub CmdScrh_Click()
' Phoenix1.1 Change
Dim TempTxtSearch As String
Dim Counter As Long
Dim Listcnt As Long
Dim Found As Boolean
'-------------------------

Screen.MousePointer = vbHourglass
If ChkRenew.Value = 1 Then

        If TxtScrh.Text = "" Then
        
            If OptDName.Value = True Or _
               OptNHandle.Value = True Or _
               OptName.Value = True Or _
               OptAcc.Value = True Then
        
                sSQL = "SELECT * FROM NicHandle " & _
                       "WHERE NH_Status =" & CDBText(RENEW) & " "
        
        
                Set RsSearch = New ADODB.Recordset
                RsSearch.Open sSQL, CnPhoenix, adOpenDynamic, adLockOptimistic
                If Not RsSearch.EOF Then
                    CmbSearchList.Clear
                    While Not RsSearch.EOF
                        CmbSearchList.AddItem RsSearch("Nic_Handle")
                        RsSearch.MoveNext
                    Wend
                    If CmbSearchList.Listcount > 0 Then
                        CmbSearchList.ListIndex = 0
                    End If
        
                Else
                    MsgBox "No Data Found", vbInformation
                End If
        
                Screen.MousePointer = vbDefault
        
            End If
                        
        Else
 '---------Celina Leong Phoenix1.1 01/04/03
             'Keep History of values typed into the serch field.

             TempTxtSearch = TxtScrh.Text
             Listcnt = TxtScrh.Listcount
             Found = False
             Counter = 0
   
             Do While Counter <= Listcnt
                      'Counter = Counter + 1
                      If TxtScrh.List(Counter) = TempTxtSearch Then
                         Found = True
                      Exit Do
                      Else
                         Found = False
                      End If
                      Counter = Counter + 1
              Loop
    
    
            If Found = False Then
                TxtScrh.AddItem TxtScrh.Text
            End If
'--------------------------------------
                       
                Set RsSearch = New ADODB.Recordset
            
                If OptDName.Value = True Then
            
                    sSQL = "SELECT DISTINCT Contact.Contact_NH, Contact.D_Name, " & _
                           "NicHandle.NH_Status, NicHandle.Nic_Handle " & _
                           "FROM Contact "

                  
                    sSQL = sSQL & "INNER JOIN NicHandle " & _
                                  "ON Contact.Contact_NH = NicHandle.Nic_Handle " & _
                                  "WHERE D_Name like " & CDBText(TxtScrh & "%") & _
                                  "AND NH_Status =" & CDBText(RENEW) & " " & _
                                  "ORDER BY Contact_NH "
'                                 "WHERE D_Name like '" & TxtScrh & "%'" & _

                    Set RsSearch = New ADODB.Recordset
                    RsSearch.Open sSQL, CnPhoenix, adOpenDynamic, adLockOptimistic
            
                    If Not RsSearch.EOF Then
                        CmbSearchList.Clear
                        While Not RsSearch.EOF
                            CmbSearchList.AddItem RsSearch("Contact_NH")
                            RsSearch.MoveNext
                        Wend
                        If CmbSearchList.Listcount > 0 Then
                            CmbSearchList.ListIndex = 0
                        End If
                    Else
                        MsgBox " No Data Found", vbInformation
                       ' blnerror = True
                    End If
            
                    Screen.MousePointer = vbDefault
            
                ElseIf OptNHandle.Value = True Then
            
                    sSQL = "SELECT * FROM NicHandle " & _
                           "WHERE Nic_Handle like " & CDBText(TxtScrh & "%") & _
                           "AND NH_Status =" & CDBText(RENEW) & " "
'                          "WHERE Nic_Handle like '" & TxtScrh & "%'" & _

'Celina Leong Phoenix1.1 03/04/04
'Allow Search Nic Handle by Name
           
'                    sSQL = "SELECT * FROM NicHandle " & _
'                           "WHERE Nic_Handle like '" & TxtScrh & "%'" & _
'                           "AND NH_Status =" & CDBText(RENEW) & " " & _
'                           "OR NH_Name like'%" & TxtScrh & "%'" & _
'                           "AND NH_Status =" & CDBText(RENEW) & " "
'-----------------------------------------------------------------------------
                          
                    Set RsSearch = New ADODB.Recordset
                    RsSearch.Open sSQL, CnPhoenix, adOpenDynamic, adLockOptimistic
                    If Not RsSearch.EOF Then
                        CmbSearchList.Clear
                        While Not RsSearch.EOF
                            CmbSearchList.AddItem RsSearch("Nic_Handle")
                            RsSearch.MoveNext
                    
                        Wend
                        If CmbSearchList.Listcount > 0 Then
                            CmbSearchList.ListIndex = 0
                        End If
            
                    Else
                        MsgBox "No Data Found", vbInformation
                        ' blnerror = True
                    End If
            
                    Screen.MousePointer = vbDefault
'Celina Leong Phoenix1.1 28/05/03
                ElseIf OptName.Value = True Then
            
'
'                    sSQL = "SELECT * FROM NicHandle " & _
'                           "WHERE NH_Name like'%" & TxtScrh & "%'" & _
'                           "AND NH_Status =" & CDBText(RENEW) & " "
                      sSQL = "SELECT * FROM NicHandle " & _
                           "WHERE NH_Name like " & CDBText("%" & TxtScrh & "%") & _
                           "AND NH_Status =" & CDBText(RENEW) & " "
                    
                    Set RsSearch = New ADODB.Recordset
                    RsSearch.Open sSQL, CnPhoenix, adOpenDynamic, adLockOptimistic
                    If Not RsSearch.EOF Then
                        CmbSearchList.Clear
                        While Not RsSearch.EOF
                            CmbSearchList.AddItem RsSearch("Nic_Handle")
                            RsSearch.MoveNext
                    
                        Wend
                        If CmbSearchList.Listcount > 0 Then
                            CmbSearchList.ListIndex = 0
                        End If
            
                    Else
                        MsgBox "No Data Found", vbInformation
                        ' blnerror = True
                    End If
            
                    Screen.MousePointer = vbDefault

                
            
'-------------------------------------------------
                ElseIf OptAcc.Value = True Then
                      If IsNumeric(TxtScrh) Then
                           sSQL = "SELECT * FROM NicHandle " & _
                                   "WHERE A_Number = " & TxtScrh & " " & _
                                   "AND NH_Status =" & CDBText(RENEW) & " " & _
                                   "ORDER BY Nic_Handle "

                            Set RsSearch = New ADODB.Recordset
                            RsSearch.Open sSQL, CnPhoenix, adOpenDynamic, adLockOptimistic
                            If Not RsSearch.EOF Then
                                CmbSearchList.Clear
                                While Not RsSearch.EOF
                                    CmbSearchList.AddItem RsSearch("Nic_Handle")
                                    RsSearch.MoveNext
                                Wend
                
                                If CmbSearchList.Listcount > 0 Then
                                    CmbSearchList.ListIndex = 0
                                End If
                            Else
                                MsgBox "No data Found", vbInformation
                              
                            End If
                        Else
                                MsgBox "No data Found", vbInformation
                        End If
                        Screen.MousePointer = vbDefault
            
            
                End If
            End If

    
Else
    If TxtScrh.Text = "" Then 'And TxtNHandle = "" Then
        MsgBox " Please enter details for search ", vbExclamation
        Screen.MousePointer = vbNormal
    Else
'Celina Leong Phoenix1.1 01/04/03
'Keep History of values typed into the serch field.

        TempTxtSearch = TxtScrh.Text
        Listcnt = TxtScrh.Listcount
        Found = False
        Counter = 0
   
        Do While Counter <= Listcnt
                 'Counter = Counter + 1
                  If TxtScrh.List(Counter) = TempTxtSearch Then
                     Found = True
                     Exit Do
                  Else
                     Found = False
                  End If
                 Counter = Counter + 1
        Loop
    
    
    If Found = False Then
            TxtScrh.AddItem TxtScrh.Text
    End If
'--------------------------------------
        Set RsSearch = New ADODB.Recordset
        
        If OptDName.Value = True Then
         
            sSQL = "SELECT DISTINCT Contact_NH FROM Contact " & _
                   "WHERE D_Name like " & CDBText(TxtScrh & "%") & _
                   "ORDER BY Contact_NH "
                  '  "WHERE D_Name like '" & TxtScrh & "%'" & _

                  
            Set RsSearch = New ADODB.Recordset
            RsSearch.Open sSQL, CnPhoenix, adOpenDynamic, adLockOptimistic
        
            If Not RsSearch.EOF Then
                CmbSearchList.Clear
                While Not RsSearch.EOF
                    CmbSearchList.AddItem RsSearch("Contact_NH")
                    RsSearch.MoveNext
                Wend
                If CmbSearchList.Listcount > 0 Then
                    CmbSearchList.ListIndex = 0
                End If
            Else
                MsgBox " No Data Found", vbInformation
            End If
            
            Screen.MousePointer = vbDefault
            
        ElseIf OptNHandle.Value = True Then
            

            sSQL = "SELECT * FROM NicHandle " & _
                   "WHERE Nic_Handle like " & CDBText(TxtScrh & "%")
                  ' "WHERE Nic_Handle like '" & TxtScrh & "%'"
'Celina Leong Phoenix1.1 03/04/04
'Allow Search Nic Handle by Name
'
'            sSQL = "SELECT * FROM NicHandle " & _
'                   "WHERE Nic_Handle like '" & TxtScrh & "%'" & _
'                   "OR NH_Name like'%" & TxtScrh & "%'"
                   
'-----------------------------------------------------------------------------
                        
            Set RsSearch = New ADODB.Recordset
            RsSearch.Open sSQL, CnPhoenix, adOpenDynamic, adLockOptimistic
            
            If Not RsSearch.EOF Then
                CmbSearchList.Clear
                While Not RsSearch.EOF
                 ' 'Celina Leong Phoenix1.1 17/04/03
                    If CmbSearchList.Listcount >= 4000 Then
                       MsgBox " Record in excess of 4000  ", vbCritical
                       Screen.MousePointer = vbDefault
                       If CmbSearchList.Listcount > 0 Then
                          CmbSearchList.ListIndex = 0
                       End If
                       Exit Sub
                '----------------------------------------
                     Else
                       CmbSearchList.AddItem RsSearch("Nic_Handle")
                       RsSearch.MoveNext
                     End If
                Wend
                
                If CmbSearchList.Listcount > 0 Then
                    CmbSearchList.ListIndex = 0
                End If
            
            Else
                MsgBox "No Data Found", vbInformation
            End If
            
            Screen.MousePointer = vbDefault
            
 'Celina Leong Phoenix1.1 28/03/05 ------------------------------
            
        ElseIf OptName.Value = True Then
            
           
            sSQL = "SELECT * FROM NicHandle " & _
                    "WHERE NH_Name like " & CDBText("%" & TxtScrh & "%")
                  ' "WHERE NH_Name like'%" & TxtScrh & "%'"
                        
            Set RsSearch = New ADODB.Recordset
            RsSearch.Open sSQL, CnPhoenix, adOpenDynamic, adLockOptimistic
            
            If Not RsSearch.EOF Then
                CmbSearchList.Clear
                While Not RsSearch.EOF
                 ' 'Celina Leong Phoenix1.1 17/04/03
                    If CmbSearchList.Listcount >= 4000 Then
                       MsgBox " Record in excess of 4000  ", vbCritical
                       Screen.MousePointer = vbDefault
                       If CmbSearchList.Listcount > 0 Then
                          CmbSearchList.ListIndex = 0
                       End If
                       Exit Sub
                '----------------------------------------
                     Else
                       CmbSearchList.AddItem RsSearch("Nic_Handle")
                       RsSearch.MoveNext
                     End If
                Wend
                
                If CmbSearchList.Listcount > 0 Then
                    CmbSearchList.ListIndex = 0
                End If
            
            Else
                MsgBox "No Data Found", vbInformation
            End If
            
            Screen.MousePointer = vbDefault
'------------------------------------------------------------------------------
            
        ElseIf OptAcc.Value = True Then
            If IsNumeric(TxtScrh) Then
               sSQL = "SELECT * FROM NicHandle " & _
                       "WHERE A_Number = " & TxtScrh & " " & _
                       "ORDER BY Nic_Handle "
                
                Set RsSearch = New ADODB.Recordset
                RsSearch.Open sSQL, CnPhoenix, adOpenDynamic, adLockOptimistic
                If Not RsSearch.EOF Then
                    CmbSearchList.Clear
                    While Not RsSearch.EOF
                        CmbSearchList.AddItem RsSearch("Nic_Handle")
                        RsSearch.MoveNext
                    Wend
                    
                    If CmbSearchList.Listcount > 0 Then
                        CmbSearchList.ListIndex = 0
                    End If
                Else
                    MsgBox "No data Found", vbInformation
                End If
            Else
                MsgBox "No data Found", vbInformation
            End If
            Screen.MousePointer = vbDefault
             
        
        
    '    Else
    '        MsgBox "Option not available ", vbOKOnly
    '        TxtScrh.Text = ""
    '        CmbSearchList.Text = ""
    '        OptNHandle.SetFocus
    '        Screen.MousePointer = vbDefault
        End If

    End If
End If

End Sub

                                  
'* Enables/Disables command buttons
Private Sub ShowButtons(ByVal blnAlStatus As Boolean, _
                        ByVal blnResetPass As Boolean, _
                        ByVal blnEdit As Boolean, _
                        ByVal blnCancel As Boolean, _
                        ByVal blnSave As Boolean, _
                        ByVal blnClose As Boolean)



Dim blnEditAllowed  As Boolean
Dim blnAllowed As Boolean

blnAllowed = HasAuthority(atHostMaster + atLeadHostMaster + atSuperUser)
blnEditAllowed = HasAuthority(atSuperUser + atAccounts + atHostMaster + atLeadHostMaster)

 CmdAlStatus.Enabled = blnAlStatus And blnAllowed
 CmdSetPass.Enabled = blnResetPass And blnAllowed
 cmdEdit.Enabled = blnEdit And blnEditAllowed
 cmdCancel.Enabled = blnCancel
 cmdSave.Enabled = blnSave And blnEditAllowed
 cmdClose.Enabled = blnClose
 CmbSearchList.Enabled = Not blnSave
 'TxtScrh.Enabled = Not blnSave
 
 CmdPayment.Enabled = mblnRecordLoaded
 CmdVAT.Enabled = mblnRecordLoaded
 
 If blnSave And blnAllowed And blnEditAllowed Then cmdSave.Default = True
 If blnCancel And blnAllowed And blnEditAllowed Then cmdCancel.Cancel = True
  
 
 
End Sub

Private Sub CmdPayment_Click()

 If mcsFormState And (csEdit) Then
    FrmPayment.ShowPayment TxtNHandle, False
 Else
    FrmPayment.ShowPayment TxtNHandle, True
 End If
 
 
End Sub

Private Sub CmdSave_Click()
   Dim ctl As Control
   Dim blnError As Boolean
   Dim ctlError As Control

  
     
   Screen.MousePointer = vbHourglass

On Error GoTo EXE_Err
'First_History


DB.BeginTransaction
    
   If mcsFormState = csEdit Then
        If mblnAlterStatus Then
            If IsValidStatus Then
                UpdateStatus blnError
            Else
                blnError = True
            End If
'11/06/02
'        ElseIf mblnResetPassword Then
'            If ValidResetPassword Then
'                UpdateResetPassword blnerror
'            Else
'                blnerror = True
'            End If
'11/06/02
        Else
            If IsValid Then
               
                   UpdateNicHandle blnError
                   
                   Process_ComboPhoneE
                      
                   Update_Phone blnError
                
                   Process_ComboFaxE
                
                   Update_Fax blnError
                  
            Else
                blnError = True
            End If
        End If
   End If
   
 
  
If Not blnError Then

        ' Show_Results_NHandle CmbSearchList.Text
        ' Show_Results_NHandle strNicHandle
        
        Show_Results_NHandle TxtNHandle

    DB.CommitTransaction
    

    mcsFormState = csView
    FormatControls Me, csView
    ShowButtons True, True, True, False, False, True
    FormatControl CmbSearchList, csEdit
    FormatControl TxtScrh, csEdit
    FormatControl ComboPhoneE, csEdit
    FormatControl ComboFaxE, csEdit
    FormatControl ChkRenew, csEdit
    mblnAlterStatus = False
  
    
    For Each ctl In Controls

                If TypeOf ctl Is OptionButton Then
                     ctl.Enabled = True
                     ctl.Visible = True
                End If

            Next
'    OptHolder.Enabled = False 28/05/03
    txtRemark.BorderStyle = 1
    ComboPhoneE.Visible = False
    ComboFaxE.Visible = False
    LblNewPass.Visible = False
    LblConPass.Visible = False
    TxtConPass.Visible = False
    TxtNewPass.Visible = False
    ComboPhone.Visible = True
    ComboPhone.Enabled = True
    ComboPhone.Locked = False
    ComboPhone.BackColor = vbButtonFace
    ComboFax.Visible = True
    ComboFax.Enabled = True
    ComboFax.Locked = False
    ComboFax.BackColor = vbButtonFace
'    OptHolder.Enabled = False 28/05/03
    CmdScrh.Enabled = True
    TxtScrh.Enabled = True
    ChkRenew.Enabled = True
 Else
    DB.RollbackTransaction
    Set ctlError = FindControlInError(Me)
    If Not (ctlError Is Nothing) Then
        ctlError.SetFocus
    End If
 End If
    Screen.MousePointer = vbNormal
    
Exit Sub
EXE_Err:
    On Error Resume Next
    
    If DB.TransactionLevel > 0 Then
       DB.RollbackTransaction
    End If
    DB.ShowError " Unable to update NicHandle maintenance "
    blnError = True

    Screen.MousePointer = vbNormal
   
End Sub

Private Sub CmdSetPass_Click()
 If TxtScrh.Text = "" And TxtNHandle = "" Then
    MsgBox " Please enter detail for Search ", vbExclamation
 Else
     If TxtNHandle = "" Then
         MsgBox " Please enter details for search  ", vbExclamation
     Else
       FrmAdmin.ChangePassword mstrNicHandle, mstrTimeStamp
      
     End If
 End If
End Sub

Private Sub CmdVAT_Click()
 If mcsFormState And (csEdit) Then
    FrmVAT.ShowVat TxtNHandle, False
 Else
    FrmVAT.ShowVat TxtNHandle, True
 End If
   
 
End Sub

Private Sub cboCountry_Click()
'' If mcsFormState = csAdd Or mcsFormState = csEdit Then
''    If (cboCountry <> mstrIreland1) And (cboCountry <> mstrIreland2) And (cboCountry <> mstrIreland3) Then
''       cboCounty = mstrNA
''    End If
' Celina Leong Phoenix1.1 08/03/04

If mcsFormState = csAdd Or mcsFormState = csEdit Then
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
    
 End If
End Sub

Private Sub cboCountry_GotFocus()
    If Not cboCountry.Locked Then
            cmdSave.Default = False
    End If
End Sub

Private Sub cboCountry_LostFocus()
    If Not cboCountry.Locked Then
            cmdSave.Default = True
    End If
End Sub

Private Sub cboCounty_GotFocus()
    If Not cboCounty.Locked Then
            cmdSave.Default = False
    End If
        
End Sub

Private Sub cboCounty_LostFocus()
    If Not cboCounty.Locked Then
            cmdSave.Default = True
    End If
End Sub

Private Sub ComboFax_KeyDown(KeyCode As Integer, Shift As Integer)
KeyCode = 0
End Sub

Private Sub ComboFax_KeyPress(KeyAscii As Integer)
KeyAscii = 0
End Sub

Private Sub ComboFax_KeyUp(KeyCode As Integer, Shift As Integer)
KeyCode = 0
End Sub

Private Sub ComboFaxE_Change()
    mlngPrevFxIndex = ComboFaxE.ListIndex
    mstrPrevFxText = ComboFaxE.Text
    
End Sub

Private Sub ComboFaxE_KeyPress(KeyAscii As Integer)
    
    Select Case KeyAscii
        Case 48 To 57
        Case 8
        Case 43
        Case 32
        Case Else
            KeyAscii = 0
    End Select
End Sub

Private Sub ComboFaxE_LostFocus()
    Process_ComboFaxE
    mlngPrevFxIndex = ComboFaxE.ListIndex
    mstrPrevFxText = ComboFaxE.Text
    If ComboFaxE.ListIndex > -1 Then
         mlngTempF = ComboFaxE.ListIndex
    End If
    
End Sub

Private Sub ComboPhone_KeyDown(KeyCode As Integer, Shift As Integer)
KeyCode = 0
End Sub

Private Sub ComboPhone_KeyPress(KeyAscii As Integer)
KeyAscii = 0
End Sub

Private Sub ComboPhone_KeyUp(KeyCode As Integer, Shift As Integer)
KeyCode = 0
End Sub

Private Sub ComboPhoneE_Change()
    mlngPrevPhIndex = ComboPhoneE.ListIndex
    mstrPrevPhText = ComboPhoneE.Text
End Sub

Private Sub ComboPhoneE_Click()
    
    Process_ComboPhoneE
    mlngPrevPhIndex = ComboPhoneE.ListIndex
    mstrPrevPhText = ComboPhoneE.Text
    
   
End Sub
Private Sub ComboFaxE_Click()
    Process_ComboFaxE
    mlngPrevFxIndex = ComboFaxE.ListIndex
    mstrPrevFxText = ComboFaxE.Text
        
End Sub

Private Sub ComboPhoneE_KeyPress(KeyAscii As Integer)

    Select Case KeyAscii
        Case 48 To 57
        Case 8
        Case 43
        Case 32
        Case Else
            KeyAscii = 0
    End Select
End Sub

Private Sub Form_Load()
Dim ctl As Control

mcsFormState = csView
mblnRecordLoaded = False
PopNicHandle_Grid


FormatControls Me, csView
ShowButtons True, True, True, False, False, True

FormatControl CmbSearchList, csEdit
FormatControl TxtScrh, csEdit
FormatControl ChkRenew, csEdit

txtRemark.BorderStyle = 1
'FillCountyCombo cboCounty   'Celina Leong Phoenix1.1 08/04/03
FillCountryCombo cboCountry
FillStatusCombo ComboStatus

ComboPhone.Visible = True
ComboPhone.Locked = False
ComboPhone.Enabled = True
ComboFax.Visible = True
ComboFax.Locked = False
ComboFax.Enabled = True
ComboPhoneE.Visible = False
ComboFaxE.Visible = False

For Each ctl In Controls

                If TypeOf ctl Is OptionButton Then
                     ctl.Enabled = True
                     ctl.Visible = True
                End If
               
            Next




' OptHolder.Enabled = False 28/05/03


End Sub




Public Sub Show_Results_NHandle(ByVal strNicHandle As String)
Dim rec As ADODB.Recordset
Dim strSQL As String


strSQL = "SELECT * FROM NicHandle WHERE Nic_Handle = " & CDBText(strNicHandle)

DB.Execute strSQL, rec

If Not rec.EOF Then
    mstrNicHandle = strNicHandle
    mstrTimeStamp = NoNull(rec.Fields("NH_TStamp"))
    mblnRecordLoaded = True
    LoadNicHandleHistory strNicHandle
    NHBillCInd = rec.Fields("NH_BillC_Ind")
    
    
    'JMcA 17.11.06 Req 1.3
    'bGuestAccount = True
    iOriginalAccNo = rec.Fields("A_Number")
    bGuestAccount = False
    If (rec.Fields("A_Number") = 1) Then
        bGuestAccount = True
    End If
    
Else
    mblnRecordLoaded = False
   
End If


Set RsSearchResults = New ADODB.Recordset


 sSQL = "SELECT DISTINCT NicHandle.Nic_Handle, NicHandle.NH_Name, " & _
           "NicHandle.A_Number, " & _
           "NicHandle.Co_Name, NicHandle.NH_Address, " & _
           "NicHandle.NH_County, NicHandle.NH_Country, " & _
           "NicHandle.NH_Status, NicHandle.NH_Email, " & _
           "NicHandle.NH_Remark, " & _
           "NicHandle.NH_Status, " & _
           "NicHandle.NH_Status_Dt " & _
           "FROM NicHandle " & _
           "Where Nic_Handle =" & CDBText(strNicHandle)

  

Set RsSearchResults = New ADODB.Recordset
RsSearchResults.Open sSQL, CnPhoenix, adOpenDynamic, adLockOptimistic


Assign_Nichandle
        
 '''   LoadNicHandleHistory strNicHandle
    
If NHBillCInd = "Y" Then
    CmdPayment.Enabled = True
    CmdVAT.Enabled = True
Else
    CmdPayment.Enabled = False
    CmdVAT.Enabled = False
End If
End Sub


Private Sub Assign_Nichandle()
Dim sTemp As String
Dim sReplace As String

        
        Set TxtNHandle.DataSource = RsSearchResults
        
        Set TxtChanged.DataSource = RsSearchResults
         
        AssignCountyCountry
       ' Set TxtCountry.DataSource = RsSearchResults
       ' Set cboCountry.DataSource = RsSearchResults
       
        Set TxtPerName.DataSource = RsSearchResults
  
        Set TxtEmail.DataSource = RsSearchResults
        
       ' Set cboCounty.DataSource = RsSearchResults
         
        Set ComboStatus.DataSource = RsSearchResults
         
        Set txtRemark.DataSource = RsSearchResults
        
        Set TxtAccNo.DataSource = RsSearchResults
       
        Set TxtCompany.DataSource = RsSearchResults
        
        Set TxtAddress.DataSource = RsSearchResults
        
        
        TxtChanged.Text = Format(TxtChanged, "dd/mm/yyyy")
        
 
 
 Assign_Co_Name_AccNo
 Assign_Address
 FillPhone_Fax


    
End Sub


Private Sub AssignCountyCountry()
 On Error Resume Next
 
 Set txtCounty.DataSource = RsSearchResults
 Set txtCountry.DataSource = RsSearchResults
 Set cboCounty.DataSource = RsSearchResults
 Set cboCountry.DataSource = RsSearchResults
End Sub

Private Sub Assign_Co_Name_AccNo()

    
        TxtAccNo.Text = Format(TxtAccNo.Text, gstrAccountNoPad)
'celin Leong Phoenix1.1 03/04/03
'        If TxtAccNo.Text = "" Or TxtAccNo.Text = ACCOUNTNO Then
'            TxtCompany.Visible = True
'            TxtAccNo.Visible = False
'        Else
'            TxtAccNo.Visible = True
'
'            TxtCompany.Visible = False
'        End If
        
            

End Sub


Private Sub FillPhone_Fax()
 Dim strSQL As String
 Dim rec As ADODB.Recordset

 
 strSQL = "SELECT * From Telecom "
 strSQL = strSQL & "WHERE Nic_Handle= " & CDBText(TxtNHandle)
 DB.Execute strSQL, rec
 

ComboPhone.Clear
ComboFax.Clear


While Not rec.EOF

     Select Case rec.Fields("Type").Value
            Case "P"
                ComboPhone.AddItem rec.Fields("Phone").Value
            Case Else
                ComboFax.AddItem rec.Fields("Phone").Value
     End Select
     rec.MoveNext
 Wend
         
     
    If ComboPhone.Listcount > 0 Then
          ComboPhone.ListIndex = 0
    End If
    
    If ComboFax.Listcount > 0 Then
        ComboFax.ListIndex = 0
    End If
    

End Sub


Private Sub FillPhoneE_FaxE()
Dim anew As String
Dim strSQL As String
Dim rec As ADODB.Recordset

 
 strSQL = "SELECT * From Telecom "
 strSQL = strSQL & "WHERE Nic_Handle= " & CDBText(TxtNHandle)
 DB.Execute strSQL, rec
 
anew = "(NEW)"
ComboPhoneE.Clear
ComboFaxE.Clear


While Not rec.EOF

     Select Case rec.Fields("Type").Value
            Case "P"
                ComboPhoneE.AddItem rec.Fields("Phone").Value
                strPhoneType = rec.Fields("Type").Value
            Case Else
                ComboFaxE.AddItem rec.Fields("Phone").Value
                 strFaxType = rec.Fields("Type").Value
     End Select
     rec.MoveNext
 Wend
         
     
        ComboPhoneE.AddItem anew, 0
        'ComboFaxE.AddItem anew, 0
        
        If ComboPhoneE.Listcount > 1 Then
              mlngPrevPhIndex = 1
             ComboPhoneE.ListIndex = 1
        Else
             mlngPrevPhIndex = 0
             ComboPhoneE.ListIndex = 0
        End If
        ComboFaxE.AddItem anew, 0
        If ComboFaxE.Listcount > 1 Then
            mlngPrevFxIndex = 1
            ComboFaxE.ListIndex = 1
        Else
            mlngPrevFxIndex = 0
            ComboFaxE.ListIndex = 0
        End If
    
End Sub

Private Sub Assign_Address()

     TxtAddress = Replace(TxtAddress.Text, "%", vbCrLf)
     TxtAddress = Replace(TxtAddress, ",", vbCrLf)
        
End Sub

Private Sub LoadNicHandleHistory(ByVal strNicHandle As String)
 
 Set RsSearchResults = New ADODB.Recordset
 
 On Error GoTo LoadNicHandleHistoryERROR
  
  
  'sSQL = "SELECT DATE_FORMAT(Chng_Dt,'%Y-%m-%d')AS Date, " & _

  sSQL = "SELECT " & FormatDBDate("Chng_Dt") & " AS 'Date', " & _
           "Chng_NH AS 'Nic Handle', " & _
           "NH_Remark As 'Remark', " & _
           "Chng_Dt as 'TimeStamp' " & _
           "FROM NicHandleHist " & _
           "WHERE  Nic_Handle = " & CDBText(strNicHandle) & "" & _
           "ORDER BY Chng_Dt DESC"
     
    
        RsSearchResults.Open sSQL, CnPhoenix, adOpenDynamic, adLockOptimistic

 HFlexNHandle.Clear
 HFlexNHandle.FixedRows = 0
 HFlexNHandle.Rows = 2
 HFlexNHandle.FixedRows = 1
 
 Set HFlexNHandle.DataSource = RsSearchResults

If HFlexNHandle.Rows <= 1 Then
    HFlexNHandle.Row = 0
 
 End If

Exit Sub


LoadNicHandleHistoryERROR:
   DB.ShowError "Unble to load Nichandle History"

End Sub


Private Sub UpdateNicHandle(ByRef blnError As Boolean)

Dim lngRowsUpdated As Long
Dim ctl As Control
Dim strSQL As String
Dim lngRow As Long
Dim cnt As Integer



     
On Error GoTo UpdateNichandle_Error
  
If ChangedControls(Me) Then

 'Celina Leong Phoenix1.1 29/05/03
         'Update History if is first update to History

          lngRow = HFlexNHandle.Row
          If lngRow = 0 Then
        
            WriteFirst_History_Nichandle blnError
            TempNow = TempTime
            WritePhone blnError
            WriteFax blnError
        
           'Celina Leong Phoenxi1.1
           'Below message is to delay and wait when still executing
           'to allow time to access before second history writes

              MsgBox "First History write", vbInformation
          End If
''-------------------------------------------------------
''    DB.BeginTransaction ' Celina Leong  Phoenix1.1 14/04/03

    WriteHistory_Nichandle mstrNicHandle, blnError
    WritePhone blnError
    WriteFax blnError
'
'   strSQL = "UPDATE NicHandle  SET "
'   strSQL = strSQL & "Nic_Handle=" & CDBText(TxtNHandle) & ", "
'   strSQL = strSQL & "NH_Name=" & CDBText(UCase(TxtPerName)) & ", "
'   strSQL = strSQL & "A_Number=" & TxtAccNo & " ,"
'   strSQL = strSQL & "Co_Name=" & CDBText(TxtCompany) & " , "
'   strSQL = strSQL & "NH_Address=" & CDBText(UCase(TxtAddress)) & " , "
'   strSQL = strSQL & "NH_County=" & CDBText(UCase(cboCounty)) & " , "
'   strSQL = strSQL & "NH_Country=" & CDBText(UCase(cboCountry)) & " , "  ' (TxtCountry)) & " , "
'   strSQL = strSQL & "NH_Email=" & CDBText(TxtEmail) & " , "
'   strSQL = strSQL & "NH_Status=" & CDBText(ComboStatus) & ", "
'   strSQL = strSQL & "NH_Remark=" & CDBText(TxtRemark) & " "
'   strSQL = strSQL & "WHERE Nic_Handle=" & CDBText(TxtNHandle) & ""
'   strSQL = strSQL & "AND NH_TStamp = " & CDBTimeStamp(mstrTimeStamp) & " "
   
   
'David O'Leary - 09-September-2002
'SQL String changed to prevent from information being
'saved in Upper Case.

   strSQL = "UPDATE NicHandle  SET "
   strSQL = strSQL & "Nic_Handle=" & CDBText(TxtNHandle) & ", "
   strSQL = strSQL & "NH_Name=" & CDBText(TxtPerName) & ", "
   strSQL = strSQL & "A_Number=" & TxtAccNo & " ,"
   strSQL = strSQL & "Co_Name=" & CDBText(TxtCompany) & " , "
   strSQL = strSQL & "NH_Address=" & CDBText(TxtAddress) & " , "
   strSQL = strSQL & "NH_County=" & CDBText(cboCounty) & " , "
   strSQL = strSQL & "NH_Country=" & CDBText(cboCountry) & " , "  ' (TxtCountry)) & " , "
   strSQL = strSQL & "NH_Email=" & CDBText(TxtEmail) & " , "
   strSQL = strSQL & "NH_Status=" & CDBText(ComboStatus) & ", "
   strSQL = strSQL & "NH_Remark=" & CDBText(txtRemark & " by " & UserID & " on " & TempNow) & " "
   strSQL = strSQL & "WHERE Nic_Handle=" & CDBText(TxtNHandle) & " "
   strSQL = strSQL & "AND NH_TStamp = " & CDBTimeStamp(mstrTimeStamp) & " "
            
    DB.Execute strSQL ', , , , lngRowsUpdated  'Celina Leong Phoenix1.1

'     If lngRowsUpdated <> 1 Then
'          DB.RollbackTransaction
'          'MsgBox "Could not Update Nic Handle." & vbCrLf & vbCrLf & "Possible reasons : Nic Handle updated by another user while you were editing it", vbExclamation
'          MsgBox "Could not Update Nic Handle." & vbCrLf & vbCrLf & "Nic Handle updated by another user while you were editing it", vbExclamation
'     Else
'
'          DB.CommitTransaction
'


''         WriteHistory_Nichandle mstrNicHandle  'Celina Leong Phoenix1.1 14/04/03 write history before update
''    End If
''---------------------------------------------------
   
End If


Exit Sub

UpdateNichandle_Error:
     
On Error Resume Next
 
If Err.Number = glngDuplicateKey Then
    MsgBox " Duplicate key ", vbExclamation
Else
    DB.ShowError "Unable to Update Nichandle "
End If

 blnError = True

End Sub
  
Private Sub Update_Phone(ByRef blnError As Boolean)
Dim strSQL As String
Dim Counter As Integer
Dim Listcount As Long



Listcount = ComboPhoneE.Listcount - 1
strPhoneType = "P"

On Error GoTo UpdatePhone_Error

For Counter = 1 To Listcount

   If ComboPhoneE.ItemData(Counter) = 2 Then
   
        
            strSQL = "INSERT INTO Telecom("
            strSQL = strSQL & "Nic_Handle, "
            strSQL = strSQL & "Phone, "
            strSQL = strSQL & "Type) "
            strSQL = strSQL & "VALUES( "
            strSQL = strSQL & CDBText(TxtNHandle.Text) & ", "
            strSQL = strSQL & CDBText(ComboPhoneE.List(Counter)) & ","
            strSQL = strSQL & CDBText(strPhoneType) & "); "
            
           
            DB.Execute strSQL
   
''Celina Leong Phoenix1.1 14/04/03
''            strSQL = "INSERT INTO TelecomHist("
''            strSQL = strSQL & "Nic_Handle, "
''            strSQL = strSQL & "Phone, "
''            strSQL = strSQL & "Type, "
''            strSQL = strSQL & "Chng_NH, "
''            strSQL = strSQL & "Chng_Dt) "
''
''
''            strSQL = strSQL & "VALUES( "
''            strSQL = strSQL & CDBText(TxtNHandle) & ", "
''            strSQL = strSQL & CDBText(ComboPhoneE.List(Counter)) & ", "
''            strSQL = strSQL & CDBText(strPhoneType) & ", "
''            strSQL = strSQL & CDBText(UserID) & ", "
''            strSQL = strSQL & CDBTime(TempNow) & "); "
''
''
''            DB.Execute strSQL
''-----------------------------------------------------------------
    End If
    
    If ComboPhoneE.ItemData(Counter) = 1 Then
                
             strSQL = "UPDATE Telecom SET "
             strSQL = strSQL & "Phone=" & CDBText(ComboPhoneE.List(Counter)) & " "
             strSQL = strSQL & "WHERE Nic_Handle=" & CDBText(TxtNHandle) & " "
             strSQL = strSQL & "AND Phone=" & CDBText(ComboPhone.List(Counter - 1)) & " "
             strSQL = strSQL & "And Type =" & CDBText(strPhoneType) & " "
                
             DB.Execute strSQL
''Celina Leong Phoenix1.1 14/04/03
''             strSQL = "INSERT INTO TelecomHist("
''             strSQL = strSQL & "Nic_Handle, "
''             strSQL = strSQL & "Phone, "
''             strSQL = strSQL & "Type, "
''             strSQL = strSQL & "Chng_NH, "
''             strSQL = strSQL & "Chng_Dt) "
''
''             strSQL = strSQL & "VALUES( "
''             strSQL = strSQL & CDBText(TxtNHandle) & ", "
''             strSQL = strSQL & CDBText(ComboPhoneE.List(Counter)) & ", "
''             strSQL = strSQL & CDBText(strPhoneType) & ", "
''             strSQL = strSQL & CDBText(UserID) & ", "
''             strSQL = strSQL & CDBTime(TempNow) & "); "
                        
           
''             DB.Execute strSQL
''--------------------------------------------------------------------------
   
            
    End If
  

Next Counter

Exit Sub

UpdatePhone_Error:

On Error Resume Next
  
 If Err.Number = glngDuplicateKey Then
    MsgBox "Duplicate key ", vbExclamation
 Else
       DB.ShowError "Unable to Update Phone no"
 End If
  
 blnError = True
''11/06/02
End Sub

Private Sub Update_Fax(ByRef blnError As Boolean)
Dim strSQL As String
Dim Counter As Integer
Dim Listcount As Long

Listcount = ComboFaxE.Listcount - 1
strFaxType = "F"

On Error GoTo UpdateFax_Error

For Counter = 1 To Listcount

     
            
      If ComboFaxE.ItemData(Counter) = 2 Then
      
            strSQL = "INSERT INTO Telecom("
            strSQL = strSQL & "Nic_Handle, "
            strSQL = strSQL & "Phone, "
            strSQL = strSQL & "Type) "
            
            strSQL = strSQL & "VALUES( "
            strSQL = strSQL & CDBText(TxtNHandle.Text) & ","
            strSQL = strSQL & CDBText(ComboFaxE.List(Counter)) & ","
            strSQL = strSQL & CDBText(strFaxType) & ");"
            
            DB.Execute strSQL
''Celina Leong Phoenix1.1 14/04/03
''            strSQL = "INSERT INTO TelecomHist("
''            strSQL = strSQL & "Nic_Handle, "
''            strSQL = strSQL & "Phone, "
''            strSQL = strSQL & "Type, "
''            strSQL = strSQL & "Chng_NH, "
''            strSQL = strSQL & "Chng_Dt) "
''
''            strSQL = strSQL & "VALUES( "
''            strSQL = strSQL & CDBText(TxtNHandle) & ", "
''            strSQL = strSQL & CDBText(ComboFaxE.List(Counter)) & ", "
''            strSQL = strSQL & CDBText(strFaxType) & ", "
''            strSQL = strSQL & CDBText(UserID) & ", "
''            strSQL = strSQL & CDBTime(TempNow) & "); "
''
''
''            DB.Execute strSQL
''----------------------------------------------------------------
    End If
    
    If ComboFaxE.ItemData(Counter) = 1 Then
    
             strSQL = "UPDATE Telecom SET "
             strSQL = strSQL & "Phone=" & CDBText(ComboFaxE.List(Counter)) & " "
             strSQL = strSQL & "WHERE Nic_Handle=" & CDBText(TxtNHandle) & " "
             strSQL = strSQL & "AND Phone=" & CDBText(ComboFax.List(Counter - 1)) & " "
             strSQL = strSQL & "And Type =" & CDBText(strFaxType) & " "
                
             DB.Execute strSQL
''Celina Leong Phoenix1.1 14/04/03
''             strSQL = "INSERT INTO TelecomHist("
''             strSQL = strSQL & "Nic_Handle, "
''             strSQL = strSQL & "Phone, "
''             strSQL = strSQL & "Type, "
''             strSQL = strSQL & "Chng_NH, "
''             strSQL = strSQL & "Chng_Dt) "
''
''             strSQL = strSQL & "VALUES( "
''             strSQL = strSQL & CDBText(TxtNHandle) & ", "
''             strSQL = strSQL & CDBText(ComboFaxE.List(Counter)) & ", "
''             strSQL = strSQL & CDBText(strFaxType) & ", "
''             strSQL = strSQL & CDBText(UserID) & ", "
''             strSQL = strSQL & CDBTime(TempNow) & "); "
''
''
''             DB.Execute strSQL
''-------------------------------------------------------------------------
    End If

    
Next Counter
      
Exit Sub

UpdateFax_Error:
     

On Error Resume Next
 
If Err.Number = glngDuplicateKey Then
    MsgBox " Duplicate key ", vbExclamation
Else
    DB.ShowError "Update to Update Fax No "
End If


 blnError = True


End Sub

   

'* Validates iinput for Altering Status


Private Function IsValidStatus() As Boolean
 Dim blnInError As Boolean
'Celina Leong Phoenix1.1 04/04/03
 Dim strSQL As String
 Dim rec As ADODB.Recordset

    
          
'Celina Leong Phoenix1.1 04/04/03
'Don't allow deletion if NicHandle is assigned to a domain
    If ComboStatus = "Deleted" Then
         strSQL = "SELECT Contact_NH FROM Contact "
         strSQL = strSQL & "WHERE Contact_NH=" & CDBText(TxtNHandle.Text) & " "
         DB.Execute strSQL, rec
            
            If Not rec.EOF Then
                MsgBox "Can not delete Nic Handle, Nichandle is assigned to a domain. ", vbExclamation
                blnInError = True
                FormatControl ComboStatus, csError
            End If
    End If
 '----------------------------------------------------------------------------
    If txtRemark.Text = "" Then
        blnInError = True
        FormatControl txtRemark, csError
    End If
    
IsValidStatus = Not blnInError
End Function

'* Validates input - Returns FALSE if any fields are invalid
'* Checks all TextBoxes and ComboBoxes for blank entries but
'*   ignores any control with a Tag property of "B" or "X"
'* Does additional validation necessary on other fields

Private Function IsValid() As Boolean
Dim blnInError As Boolean
Dim ctl As Control
Dim strSQL As String
Dim rec As ADODB.Recordset
 
 
 If TxtAccNo.Visible = True Then
    If TxtAccNo = "" Then
        blnInError = True
        FormatControl TxtAccNo, csError
    Else
          If Not IsNumeric(TxtAccNo) Then
                MsgBox "Invalid Account No", vbExclamation
                blnInError = True
                FormatControl TxtAccNo, csError
          Else
            strSQL = "SELECT A_Number, Billing_NH FROM Account "
            strSQL = strSQL & "WHERE A_Number=" & TxtAccNo.Text & " "
            strSQL = strSQL & "AND A_Status= " & CDBText("Active") & ""
            DB.Execute strSQL, rec
            
            If rec.EOF Then
                MsgBox "Account No not exists ", vbExclamation
                blnInError = True
                FormatControl TxtAccNo, csError
                        
            End If

            
          End If
    End If
    
 Else
    TxtAccNo.Tag = ""
 End If
 
 
  If TxtCompany.Visible = True Then
'' Celina Leong Phoenix1.1 Company can be blank 14/04/03
''    If TxtCompany = "" Then
''        blnInError = True
''        FormatControl TxtCompany, csError
''    End If
 Else
    TxtCompany.Tag = ""
 End If
 
 If TxtNewPass = "" Then
        TxtNewPass.Tag = "B"
 End If

 If TxtConPass = "" Then
        TxtConPass.Tag = "B"
 End If
 
 
 If TxtChanged = "" Then
        TxtChanged.Tag = "B"
 End If
 
 If ComboFax = "" Then
        ComboFax.Tag = "X"
 End If
 
 If ComboPhone = "" Then
        ComboPhone.Tag = "X"
 End If
 
 
 If ComboPhoneE.Text = "" Then
     blnInError = True
     FormatControl ComboPhoneE, csError
 Else
    If IsDuplicatePhone Then
        MsgBox " Phone No already exists ", vbExclamation
        blnInError = True
        FormatControl ComboPhoneE, csError
    End If
 
 End If
 
 If ComboFaxE.Text = "" Then
     blnInError = True
     FormatControl ComboFaxE, csError
 Else
    If IsDuplicateFax Then
        MsgBox " Fax No already exists ", vbExclamation
        blnInError = True
        FormatControl ComboFaxE, csError
    End If
 
 End If
 
 If TxtEmail.Text = "" Then
    blnInError = True
    FormatControl TxtEmail, csError
 End If
 
 If cboCountry.Text = "" Then
       blnInError = True
       FormatControl cboCountry, csError
 Else
'    If (cboCountry = mstrIreland1) Or (cboCountry = mstrIreland2) Then
' Celina Leong Phoenix1.1 08/04/03
       If (cboCountry = mstrIreland1) Or (cboCountry = mstrIreland2) Or _
           (cboCountry = mstrUSA) Or (cboCountry = mstrUK) Then
            If cboCounty = mstrNA Or cboCounty = "" Then
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
 End If
 
 
  
 If txtRemark.Text = "" Then
     blnInError = True
     FormatControl txtRemark, csError
 End If
 

 
IsValid = Not blnInError
 


End Function



Private Sub UpdateStatus(ByRef blnInError As Boolean)
Dim lngRowsUpdated As Long
Dim ctl As Control
Dim strSQL As String
Dim blnError As Boolean
Dim lngRow As Long

     
On Error GoTo UpdateStatus_Error
  
If ChangedControls(Me) Then
'First_History
'Celina Leong Phoenix1.1 29/05/03
'Update History if is first update to History
   lngRow = HFlexNHandle.Row
   If lngRow = 0 Then
   
        WriteFirst_History_Nichandle blnError
        WritePhone blnError
        WriteFax blnError
        
   End If
'-------------------------------------------------------
'''    DB.BeginTransaction ' Celina Leong  Phoenix1.1 14/04/03

   WriteHistory_Nichandle mstrNicHandle, blnError
   WriteFax blnError
   WritePhone blnError
   
   strSQL = "UPDATE NicHandle  SET "
   strSQL = strSQL & "NH_Status=" & CDBText(ComboStatus) & ", "
   strSQL = strSQL & "NH_Status_Dt=NOW(), "
   strSQL = strSQL & "NH_Remark=" & CDBText(txtRemark & " by " & UserID & " on " & TempNow) & " "
   strSQL = strSQL & "WHERE Nic_Handle=" & CDBText(TxtNHandle) & " "
   strSQL = strSQL & "AND NH_TStamp = " & CDBTimeStamp(mstrTimeStamp) & " "
            
     DB.Execute strSQL '', , , , lngRowsUpdated Celina Leong Phoenix1.1
''
''
''    If lngRowsUpdated <> 1 Then
''         DB.RollbackTransaction
''         'MsgBox "Could not Update Status." & vbCrLf & vbCrLf & "Possible reasons : Nic Handle updated by another user while you were editing it", vbExclamation
''         MsgBox "Could not Update Status." & vbCrLf & vbCrLf & "Nic Handle updated by another user while you were editing it", vbExclamation
''    Else
''
''        DB.CommitTransaction
''''        WriteHistory_Nichandle mstrNicHandle 'Celina Leong Phoenix1.1 14/04/03 write history before update
''
''    End If
''---------------------------------------------------------------
End If


Exit Sub
UpdateStatus_Error:
     
On Error Resume Next
''Celina Leong Phoenix1.1 14/04/03
'' If DB.TransactionLevel > 0 Then
''    DB.RollbackTransaction
'' End If
''-------------------------------
 Error.Show_Error_DB "Unable to Update Status "
 blnError = True

End Sub


Public Sub WriteHistory_Nichandle(ByVal mstrNicHandle As String, ByRef blnError As Boolean)
Dim strSQL As String
  

    
    TempNow = Now()
    
    strSQL = "INSERT INTO NicHandleHist("
    strSQL = strSQL & "Nic_Handle, "
    strSQL = strSQL & "NH_Name, "
    strSQL = strSQL & "A_Number, "
    strSQL = strSQL & "Co_Name, "
    strSQL = strSQL & "NH_Address, "
    strSQL = strSQL & "NH_County, "
    strSQL = strSQL & "NH_Country, "
    strSQL = strSQL & "NH_Email, "
    strSQL = strSQL & "NH_Status, "
    strSQL = strSQL & "NH_Status_Dt, "
    strSQL = strSQL & "NH_Reg_Dt, "
    strSQL = strSQL & "NH_TStamp, "
    strSQL = strSQL & "NH_BillC_Ind, "
    strSQL = strSQL & "NH_Remark, "
    strSQL = strSQL & "Chng_NH, "
    strSQL = strSQL & "Chng_Dt) "
   
              
    strSQL = strSQL & "SELECT DISTINCT " 'CC20051221 fix for MySQLv4.0
    strSQL = strSQL & "Nic_Handle, "
    strSQL = strSQL & "NH_Name, "
    strSQL = strSQL & "A_Number, "
    strSQL = strSQL & "Co_Name, "
    strSQL = strSQL & "NH_Address, "
    strSQL = strSQL & "NH_County, "
    strSQL = strSQL & "NH_Country, "
    strSQL = strSQL & "NH_Email, "
    strSQL = strSQL & "NH_Status, "
    strSQL = strSQL & "NH_Status_Dt, "
    strSQL = strSQL & "NH_Reg_Dt,"
    strSQL = strSQL & "NH_TStamp, "
    strSQL = strSQL & "NH_BillC_Ind, "
    strSQL = strSQL & CDBText(txtRemark & " by " & UserID & " on " & TempNow) & ", "
    strSQL = strSQL & CDBText("PHOENIX") & ", " 'JMA 19.4.7 NicHandle hardcoded to PHOENIX for consistency with Dom Maint screen
    strSQL = strSQL & CDBTime(TempNow) & " "
    strSQL = strSQL & "FROM NicHandle "
    strSQL = strSQL & "WHERE Nic_Handle ='" & TxtNHandle & "'"
    DB.Execute strSQL
     'strSQL = strSQL & "NH_Remark, " '29/05/03
End Sub

Private Sub WriteHistory_Telecom()
Dim strSQL As String

      strSQL = "INSERT INTO TelecomHist("
            strSQL = strSQL & "Nic_Handle, "
            strSQL = strSQL & "Phone, "
            strSQL = strSQL & "Type, "
            strSQL = strSQL & "Chng_NH, "
            strSQL = strSQL & "Chng_Dt) "
        
            
''            strSQL = strSQL & "SELECT "
''            strSQL = strSQL & "Nic_Handle, "
''            strSQL = strSQL & "Phone, "
''            strSQL = strSQL & "Type, "
''            strSQL = strSQL & CDBText(UserID) & ", "
''            strSQL = strSQL & "NOW() "
''            strSQL = strSQL & "FROM Telecom "
''            strSQL = strSQL & "WHERE Nic_Handle ='" & TxtNHandle & "'"
''Celina Leong Phoenix1.1 14/04/03
            strSQL = strSQL & "SELECT DISTINCT " 'CC20051221 fix for MySQLv4.0
            strSQL = strSQL & "Nic_Handle, "
            strSQL = strSQL & CDBText(UCase(TempPhone)) & ", "
            strSQL = strSQL & CDBText(TempType) & ", "
            strSQL = strSQL & CDBText(UserID) & ", "
            strSQL = strSQL & CDBTime(TempNow) & " "
            strSQL = strSQL & "FROM Telecom "
            strSQL = strSQL & "WHERE Nic_Handle ='" & TxtNHandle & "'"
           
            DB.Execute strSQL
''--------------------------------------------------------------
End Sub


Private Sub Form_Resize()
 CenterControls Me
End Sub

Private Sub Form_Unload(Cancel As Integer)
    On Error Resume Next
    
    If Not mfrmEditNicHandle Is Nothing Then
       Unload mfrmEditNicHandle
       Set mfrmEditNicHandle = Nothing
    End If
        
End Sub



Private Sub HFlexNHandle_DblClick()
Dim lngRow As Long

If Not mblnRecordLoaded Then Exit Sub
    
    
    lngRow = HFlexNHandle.Row
    
    If lngRow <> 0 Then
       If mcsFormState = csView Then
         
            Assign_NH_History
            Assign_Telecom_History
            ShowButtons False, False, True, True, False, True
            cmdEdit.Enabled = False
            CmbSearchList.Enabled = False
            CmdScrh.Enabled = False
            ChkRenew.Enabled = False
           'End If
        End If
        
    End If
End Sub


'*Load Nic Handle History to screen

Private Sub Assign_NH_History()
Dim rec As ADODB.Recordset
Dim strSQL As String
Dim lngRow

    lngRow = HFlexNHandle.Row
    strSQL = "SELECT * FROM NicHandleHist "
    strSQL = strSQL & "WHERE Nic_Handle =" & CDBText(CmbSearchList.Text) & " "
    strSQL = strSQL & "AND Chng_Dt=" & CDBTime(HFlexNHandle.TextMatrix(lngRow, 4))
    
    DB.Execute strSQL, rec
        If Not rec.EOF Then
            
            TxtNHandle.Text = NoNull(rec.Fields("Nic_Handle"))
            TxtPerName.Text = NoNull(rec.Fields("NH_Name"))
            TxtAccNo.Text = Format(rec.Fields("A_Number"), gstrAccountNoPad)
            TxtCompany.Text = NoNull(rec.Fields("Co_Name"))
            TxtAddress.Text = NoNull(rec.Fields("NH_Address"))
            AssignCountyCountryHistory NoNull(rec.Fields("NH_County")), NoNull(rec.Fields("NH_Country"))
            'cboCounty.Text = NoNull(rec.Fields("NH_County"))
            'TxtCountry.Text = NoNull(rec.Fields("NH_Country"))
            'cboCountry.Text = NoNull(rec.Fields("NH_Country"))
            TxtEmail.Text = NoNull(rec.Fields("NH_Email"))
            ComboStatus.Text = NoNull(rec.Fields("NH_Status"))
            TxtChanged.Text = NoNull(rec.Fields("NH_Status_Dt"))
            txtRemark.Text = NoNull(rec.Fields("NH_Remark"))
            txtRemark.Text = Replace(txtRemark.Text, "%", vbCrLf)

        End If
            TxtChanged.Text = Format(TxtChanged, "dd/mm/yyyy")
End Sub
Private Sub AssignCountyCountryHistory(ByVal strCounty As String, ByVal strCountry As String)
 On Error Resume Next
 txtCounty = strCounty
 'Celina 24-09-02
 'txtCountry = strCounty
 txtCountry = strCountry
 cboCounty = strCounty
 cboCountry = strCountry
End Sub

Private Sub Assign_Telecom_History()
Dim strSQL As String
Dim rec As ADODB.Recordset
Dim lngRow

    lngRow = HFlexNHandle.Row
    
    strSQL = "SELECT * FROM TelecomHist "
    strSQL = strSQL & "WHERE Nic_Handle =" & CDBText(CmbSearchList.Text) & " "
    strSQL = strSQL & "AND Chng_Dt=" & CDBTime(HFlexNHandle.TextMatrix(lngRow, 4))
    
    DB.Execute strSQL, rec
    
    
ComboPhone.Clear
ComboFax.Clear
If Not rec.EOF Then
   While Not rec.EOF
     Select Case rec.Fields("Type").Value
            Case "P"
                ComboPhone.AddItem rec.Fields("Phone").Value
                strPhoneType = rec.Fields("Type").Value
            Case Else
                ComboFax.AddItem rec.Fields("Phone").Value
                 strPhoneType = rec.Fields("Type").Value
     End Select
     rec.MoveNext
    Wend
 
 
    If ComboPhone.Listcount > 0 Then
          ComboPhone.ListIndex = 0
    End If
    
    If ComboFax.Listcount > 0 Then
        ComboFax.ListIndex = 0
    End If
 
 Else
       ComboFax.Text = ""
       ComboPhone.Text = ""
   
 End If

       
End Sub


Private Sub Process_ComboPhoneE()
Dim lngtempsortorder As Long
    If mblnProcesComboPh Then Exit Sub
    mblnProcesComboPh = True
    

    If mlngPrevPhIndex = -1 Then
        If mlngTempP = 0 Then    '/* new item */
           ComboPhoneE.AddItem mstrPrevPhText
           ComboPhoneE.ItemData(ComboPhoneE.NewIndex) = 2
           ComboPhoneE.ListIndex = ComboPhoneE.NewIndex
          
        Else
       
            ComboPhoneE.RemoveItem (mlngTempP)
            ComboPhoneE.AddItem mstrPrevPhText, mlngTempP
            If ComboPhoneE.ItemData(mlngTempP) = 0 Then
                ComboPhoneE.ItemData(mlngTempP) = 1
            End If
        End If
        
      End If
    
    
    mblnProcesComboPh = False
    If ComboPhoneE.ListIndex > -1 Then
        mlngTempP = ComboPhoneE.ListIndex
    End If
    
End Sub
Private Sub Process_ComboFaxE()

Dim lngtempsortorder As Long
    If mblnProcesComboFx Then Exit Sub
    mblnProcesComboFx = True
    

    If mlngPrevFxIndex = -1 Then
        If mlngTempF = 0 Then    '/* new item */
           ComboFaxE.AddItem mstrPrevFxText
           ComboFaxE.ItemData(ComboFaxE.NewIndex) = 2
           ComboFaxE.ListIndex = ComboFaxE.NewIndex
          
        Else
         
            ComboFaxE.RemoveItem (mlngTempF)
            ComboFaxE.AddItem mstrPrevFxText, mlngTempF
                     
            If ComboFaxE.ItemData(mlngTempF) = 0 Then
                ComboFaxE.ItemData(mlngTempF) = 1
            End If
        End If
        
      End If
    
    
    mblnProcesComboFx = False
    If ComboFaxE.ListIndex > -1 Then
        mlngTempF = ComboFaxE.ListIndex
    End If
    
End Sub

Private Sub ComboPhoneE_LostFocus()
    Process_ComboPhoneE
    mlngPrevPhIndex = ComboPhoneE.ListIndex
    mstrPrevPhText = ComboPhoneE.Text
    If ComboPhoneE.ListIndex > -1 Then
         mlngTempP = ComboPhoneE.ListIndex
    End If
End Sub

Private Sub HFlexNHandle_KeyDown(KeyCode As Integer, Shift As Integer)
        If KeyCode = vbKeyReturn Then
            If mblnRecordLoaded Then
               HFlexNHandle_DblClick
            End If
         End If
End Sub

Private Sub HFlexNHandle_SelChange()
        HFlexNHandle.RowSel = HFlexNHandle.Row
End Sub

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


Private Sub mfrmEditNicHandle_Accepted(ByVal strNicHandle As String)
 If TxtNHandle = strNicHandle Then
    ComboStatus.Text = "Active"

 End If
 
End Sub

Public Sub ChangedPassword(ByVal strNicHandle As String)
    If mcsFormState = csView Then
        If TxtNHandle.Text = strNicHandle Then
            Show_Results_NHandle strNicHandle
        End If
    End If
 
End Sub




Private Function IsDuplicatePhone() As Boolean
Dim lngloopA, lngloopB, lngloopC
Dim lngCount

        lngCount = ComboPhoneE.Listcount - 1
        For lngloopA = 1 To lngCount
            For lngloopB = lngloopA + 1 To lngCount
                If ComboPhoneE.List(lngloopA) = ComboPhoneE.List(lngloopB) Then
                     IsDuplicatePhone = True
                     Exit Function
                End If
            Next lngloopB
        
            For lngloopC = 0 To lngCount - 1
                If lngloopC <> lngloopA Then
                    If ComboPhoneE.List(lngloopA) = ComboPhoneE.List(lngloopC) Then
                       IsDuplicatePhone = True
                       Exit Function
                    End If
                End If
            Next lngloopC
            
        Next lngloopA
        
        
       Exit Function
        
    
End Function

Private Function IsDuplicateFax() As Boolean
Dim lngloopA, lngloopB, lngloopC
Dim lngCount

        lngCount = ComboFaxE.Listcount - 1
        For lngloopA = 1 To lngCount
            For lngloopB = lngloopA + 1 To lngCount
                If ComboFaxE.List(lngloopA) = ComboFaxE.List(lngloopB) Then
                     IsDuplicateFax = True
                     Exit Function
                End If
            Next lngloopB
        
            For lngloopC = 0 To lngCount - 1
                If lngloopC <> lngloopA Then
                    If ComboFaxE.List(lngloopA) = ComboFaxE.List(lngloopC) Then
                       IsDuplicateFax = True
                       Exit Function
                    End If
                End If
            Next lngloopC
            
        Next lngloopA
        
        
       Exit Function
        
    
End Function



Private Sub TxtRemark_GotFocus()
 If Not txtRemark.Locked Then
     cmdSave.Default = False
  End If
        
End Sub

Private Sub TxtRemark_LostFocus()
  If Not txtRemark.Locked Then
     cmdSave.Default = True
  End If
End Sub

''Celina Leong Phoenix1.1 14/04/03

Private Sub WritePhone(ByRef blnError As Boolean)

Dim Counter As Integer
Dim Listcount As Long
Dim Listcnt As Long
Dim Count As Integer




Listcount = ComboPhoneE.Listcount - 1

strPhoneType = "P"


For Counter = 1 To Listcount
        If ComboPhoneE.ItemData(Counter) = 0 Then
        
            TempPhone = ComboPhoneE.List(Counter)
            TempType = strPhoneType
            WriteHistory_Telecom
             
        End If
 Next Counter
 
End Sub

Private Sub WriteFax(ByRef blnError As Boolean)

Dim Counter As Integer
Dim strSQL As String
Dim Listcount As Long
Dim Listcnt As Long
Dim Count As Integer



Listcount = ComboFaxE.Listcount - 1
strFaxType = "F"

For Counter = 1 To Listcount
        
        If ComboFaxE.ItemData(Counter) = 0 Then
            TempPhone = ComboFaxE.List(Counter)
            TempType = strFaxType
            WriteHistory_Telecom
        End If
  
 Next Counter
 
End Sub

''---------------------------------------------------------------------
'29/05/03
Public Sub WriteFirst_History_Nichandle(ByRef blnError As Boolean)
Dim strSQL As String


    TempTime = Now()
    
    strSQL = "INSERT INTO NicHandleHist("
    strSQL = strSQL & "Nic_Handle, "
    strSQL = strSQL & "NH_Name, "
    strSQL = strSQL & "A_Number, "
    strSQL = strSQL & "Co_Name, "
    strSQL = strSQL & "NH_Address, "
    strSQL = strSQL & "NH_County, "
    strSQL = strSQL & "NH_Country, "
    strSQL = strSQL & "NH_Email, "
    strSQL = strSQL & "NH_Status, "
    strSQL = strSQL & "NH_Status_Dt, "
    strSQL = strSQL & "NH_Reg_Dt, "
    strSQL = strSQL & "NH_TStamp, "
    strSQL = strSQL & "NH_BillC_Ind, "
    strSQL = strSQL & "NH_Remark, "
    strSQL = strSQL & "Chng_NH, "
    strSQL = strSQL & "Chng_Dt) "
   
   
              
    strSQL = strSQL & "SELECT DISTINCT " 'CC20051221 fix for MySQLv4.0
    strSQL = strSQL & "Nic_Handle, "
    strSQL = strSQL & "NH_Name, "
    strSQL = strSQL & "A_Number, "
    strSQL = strSQL & "Co_Name, "
    strSQL = strSQL & "NH_Address, "
    strSQL = strSQL & "NH_County, "
    strSQL = strSQL & "NH_Country, "
    strSQL = strSQL & "NH_Email, "
    strSQL = strSQL & "NH_Status, "
    strSQL = strSQL & "NH_Status_Dt, "
    strSQL = strSQL & "NH_Reg_Dt,"
    strSQL = strSQL & "NH_TStamp, "
    strSQL = strSQL & "NH_BillC_Ind, "
    strSQL = strSQL & "NH_Remark, "
    strSQL = strSQL & CDBText(UserID) & ", "
    strSQL = strSQL & CDBTime(TempTime) & " "
    strSQL = strSQL & "FROM NicHandle "
    strSQL = strSQL & "WHERE Nic_Handle ='" & TxtNHandle & "'"
    DB.Execute strSQL
    

End Sub



