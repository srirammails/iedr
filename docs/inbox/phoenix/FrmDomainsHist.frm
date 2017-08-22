VERSION 5.00
Object = "{0ECD9B60-23AA-11D0-B351-00A0C9055D8E}#6.0#0"; "mshflxgd.ocx"
Begin VB.Form FrmDomainsHis 
   Caption         =   "Domain History "
   ClientHeight    =   7500
   ClientLeft      =   60
   ClientTop       =   945
   ClientWidth     =   11835
   ForeColor       =   &H80000008&
   HelpContextID   =   2000
   LinkTopic       =   "Form1"
   MDIChild        =   -1  'True
   ScaleHeight     =   7500
   ScaleMode       =   0  'User
   ScaleWidth      =   11835
   WindowState     =   2  'Maximized
   Begin VB.Frame FraOption 
      Caption         =   "Options"
      ForeColor       =   &H00000000&
      Height          =   915
      Left            =   240
      TabIndex        =   32
      Top             =   6520
      Width           =   11295
      Begin VB.CommandButton CmdTraAC 
         Caption         =   "Trans&fer Account"
         Height          =   375
         Left            =   3480
         MaskColor       =   &H8000000F&
         TabIndex        =   66
         Top             =   320
         Width           =   1445
      End
      Begin VB.CommandButton CmdAlStatus 
         Caption         =   "Alter Stat&us"
         Height          =   375
         Left            =   1920
         MaskColor       =   &H8000000F&
         TabIndex        =   65
         Top             =   320
         Width           =   1445
      End
      Begin VB.CommandButton CmdDelDNSName 
         Caption         =   "Delete DNS Name"
         Height          =   375
         Left            =   240
         TabIndex        =   64
         Top             =   320
         Width           =   1575
      End
      Begin VB.CommandButton CmdClose 
         Caption         =   "&Close"
         Height          =   375
         Left            =   9720
         MaskColor       =   &H8000000F&
         TabIndex        =   56
         Top             =   320
         Width           =   1445
      End
      Begin VB.CommandButton CmdCancel 
         Caption         =   "Ca&ncel"
         Height          =   375
         Left            =   6600
         TabIndex        =   27
         Top             =   320
         Width           =   1445
      End
      Begin VB.CommandButton CmdEdit 
         Caption         =   "&Edit"
         Height          =   375
         Left            =   5040
         MaskColor       =   &H8000000F&
         TabIndex        =   26
         Top             =   320
         Width           =   1445
      End
      Begin VB.CommandButton CmdSave 
         Caption         =   "Sa&ve"
         Height          =   375
         Left            =   8160
         MaskColor       =   &H8000000F&
         TabIndex        =   28
         Top             =   320
         Width           =   1445
      End
   End
   Begin VB.Frame FraHistory 
      Caption         =   "History"
      ForeColor       =   &H00000000&
      Height          =   1635
      Left            =   240
      TabIndex        =   31
      Top             =   4870
      Width           =   11295
      Begin MSHierarchicalFlexGridLib.MSHFlexGrid HFlexDomainsHis 
         Height          =   1215
         Left            =   240
         TabIndex        =   25
         Top             =   240
         Width           =   10815
         _ExtentX        =   19076
         _ExtentY        =   2143
         _Version        =   393216
         Cols            =   5
         BackColorFixed  =   16744576
         BackColorSel    =   16761024
         GridColor       =   16744576
         GridColorFixed  =   16744576
         GridColorUnpopulated=   -2147483630
         AllowBigSelection=   0   'False
         ScrollTrack     =   -1  'True
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
   Begin VB.Frame FraCurrent 
      BackColor       =   &H8000000B&
      Caption         =   "Current "
      ForeColor       =   &H00000000&
      Height          =   3510
      Left            =   240
      TabIndex        =   30
      Top             =   1350
      Width           =   11295
      Begin VB.TextBox TxtClikPay 
         BackColor       =   &H8000000F&
         Height          =   315
         Left            =   9720
         TabIndex        =   23
         Top             =   1560
         Width           =   1215
      End
      Begin VB.TextBox TxtBillStatus 
         BackColor       =   &H8000000F&
         Height          =   315
         Left            =   6480
         TabIndex        =   15
         Top             =   1550
         Width           =   1575
      End
      Begin VB.TextBox TxtVATStatus 
         BackColor       =   &H8000000F&
         Height          =   315
         Left            =   6480
         TabIndex        =   18
         Top             =   1220
         Visible         =   0   'False
         Width           =   1575
      End
      Begin VB.ListBox ListDNSname 
         Height          =   255
         Left            =   9600
         TabIndex        =   67
         Top             =   2400
         Visible         =   0   'False
         Width           =   1455
      End
      Begin VB.ComboBox ComboCategory 
         Height          =   315
         ItemData        =   "FrmDomainsHist.frx":0000
         Left            =   1800
         List            =   "FrmDomainsHist.frx":0002
         Style           =   2  'Dropdown List
         TabIndex        =   63
         Top             =   1550
         Width           =   3060
      End
      Begin VB.ComboBox ComboClass 
         Height          =   315
         Left            =   1800
         Style           =   2  'Dropdown List
         TabIndex        =   62
         Top             =   1200
         Width           =   3060
      End
      Begin VB.TextBox TxtCategory 
         BackColor       =   &H8000000F&
         DataField       =   "D_Category"
         Height          =   315
         Left            =   1800
         TabIndex        =   61
         Top             =   1550
         Width           =   3015
      End
      Begin VB.TextBox TxtClass 
         BackColor       =   &H8000000F&
         DataField       =   "D_Class"
         Height          =   315
         Left            =   1800
         TabIndex        =   60
         Top             =   1200
         Width           =   3015
      End
      Begin VB.ComboBox ComboClikPay 
         Height          =   315
         Left            =   9720
         Style           =   2  'Dropdown List
         TabIndex        =   24
         Top             =   1560
         Width           =   1335
      End
      Begin VB.ComboBox ComboVATStatus 
         Height          =   315
         Left            =   6480
         Style           =   2  'Dropdown List
         TabIndex        =   19
         Top             =   1220
         Visible         =   0   'False
         Width           =   1815
      End
      Begin VB.ComboBox ComboBillStatus 
         Height          =   315
         Left            =   6480
         Style           =   2  'Dropdown List
         TabIndex        =   20
         Top             =   1550
         Width           =   1815
      End
      Begin VB.ListBox ListIPAdd 
         Height          =   450
         Left            =   9600
         TabIndex        =   55
         Top             =   1920
         Visible         =   0   'False
         Width           =   1455
      End
      Begin VB.ComboBox ComboDNSNameE 
         DataField       =   "DNS_Name"
         Height          =   315
         Left            =   6480
         TabIndex        =   11
         Text            =   "ComboDNSNameE"
         Top             =   1870
         Width           =   2775
      End
      Begin VB.TextBox TxtRemark 
         BackColor       =   &H8000000F&
         DataField       =   "D_Remark"
         ForeColor       =   &H00000000&
         Height          =   615
         Left            =   4080
         MultiLine       =   -1  'True
         ScrollBars      =   2  'Vertical
         TabIndex        =   22
         Top             =   2760
         Width           =   6615
      End
      Begin VB.TextBox TxtRegDate 
         BackColor       =   &H8000000F&
         BorderStyle     =   0  'None
         DataField       =   "D_Reg_Dt"
         BeginProperty DataFormat 
            Type            =   1
            Format          =   "dd/MM/yyyy"
            HaveTrueFalseNull=   0
            FirstDayOfWeek  =   0
            FirstWeekOfYear =   0
            LCID            =   2057
            SubFormatType   =   3
         EndProperty
         ForeColor       =   &H00000000&
         Height          =   315
         Left            =   9720
         Locked          =   -1  'True
         TabIndex        =   17
         Top             =   885
         Width           =   1320
      End
      Begin VB.TextBox TxtRenewDate 
         BackColor       =   &H8000000F&
         BorderStyle     =   0  'None
         DataField       =   "D_Ren_Dt"
         BeginProperty DataFormat 
            Type            =   1
            Format          =   "dd/MM/yyyy"
            HaveTrueFalseNull=   0
            FirstDayOfWeek  =   0
            FirstWeekOfYear =   0
            LCID            =   2057
            SubFormatType   =   3
         EndProperty
         ForeColor       =   &H00000000&
         Height          =   315
         Left            =   9720
         Locked          =   -1  'True
         TabIndex        =   21
         Top             =   1200
         Width           =   1320
      End
      Begin VB.TextBox TxtChanged 
         BackColor       =   &H8000000F&
         BorderStyle     =   0  'None
         DataField       =   "D_Status_Dt"
         BeginProperty DataFormat 
            Type            =   1
            Format          =   "dd/MM/yyyy"
            HaveTrueFalseNull=   0
            FirstDayOfWeek  =   0
            FirstWeekOfYear =   0
            LCID            =   2057
            SubFormatType   =   3
         EndProperty
         ForeColor       =   &H00000000&
         Height          =   315
         Left            =   9720
         Locked          =   -1  'True
         TabIndex        =   16
         Top             =   555
         Width           =   1320
      End
      Begin VB.TextBox TxtBilCon 
         BackColor       =   &H8000000F&
         BorderStyle     =   0  'None
         DataField       =   "Contact_NH"
         DataSource      =   "DataEnvironment"
         ForeColor       =   &H00000000&
         Height          =   315
         Left            =   1800
         Locked          =   -1  'True
         TabIndex        =   10
         Top             =   2900
         Width           =   2100
      End
      Begin VB.TextBox TxtTechCon 
         BackColor       =   &H8000000F&
         BorderStyle     =   0  'None
         DataField       =   "Contact_NH"
         DataSource      =   "DataEnvironment"
         ForeColor       =   &H00000000&
         Height          =   315
         Left            =   1800
         Locked          =   -1  'True
         MaxLength       =   12
         TabIndex        =   9
         Top             =   2520
         Width           =   2100
      End
      Begin VB.TextBox TxtAdCon2 
         BackColor       =   &H8000000F&
         BorderStyle     =   0  'None
         DataField       =   "Contact_NH"
         DataSource      =   "DataEnvironment"
         ForeColor       =   &H00000000&
         Height          =   315
         Left            =   1800
         Locked          =   -1  'True
         MaxLength       =   12
         TabIndex        =   8
         Top             =   2200
         Width           =   2100
      End
      Begin VB.TextBox TxtAdCon1 
         BackColor       =   &H8000000F&
         BorderStyle     =   0  'None
         DataField       =   "Contact_NH"
         DataSource      =   "DataEnvironment"
         ForeColor       =   &H00000000&
         Height          =   315
         Left            =   1800
         Locked          =   -1  'True
         MaxLength       =   12
         TabIndex        =   7
         Top             =   1870
         Width           =   2100
      End
      Begin VB.TextBox TxtDHolder 
         BackColor       =   &H8000000F&
         BorderStyle     =   0  'None
         DataField       =   "D_Holder"
         ForeColor       =   &H00000000&
         Height          =   315
         Left            =   1800
         Locked          =   -1  'True
         TabIndex        =   5
         Top             =   555
         Width           =   5940
      End
      Begin VB.TextBox TxtAccName 
         BackColor       =   &H8000000F&
         BorderStyle     =   0  'None
         DataField       =   "A_Number"
         ForeColor       =   &H00000000&
         Height          =   315
         Left            =   1800
         Locked          =   -1  'True
         MaxLength       =   8
         TabIndex        =   6
         Top             =   885
         Width           =   2100
      End
      Begin VB.TextBox TxtDName 
         BackColor       =   &H8000000F&
         BorderStyle     =   0  'None
         DataField       =   "D_Name"
         ForeColor       =   &H00000000&
         Height          =   315
         Left            =   1800
         Locked          =   -1  'True
         TabIndex        =   4
         Top             =   240
         Width           =   5940
      End
      Begin VB.TextBox TxtDNSIPAdd 
         BackColor       =   &H8000000F&
         BorderStyle     =   0  'None
         DataField       =   "DNS_IpAddr"
         ForeColor       =   &H00000000&
         Height          =   315
         Left            =   6480
         Locked          =   -1  'True
         MaxLength       =   20
         TabIndex        =   13
         Top             =   2200
         Width           =   1995
      End
      Begin VB.ComboBox ComboDNSName 
         BackColor       =   &H8000000F&
         ForeColor       =   &H00000000&
         Height          =   315
         Left            =   6480
         Style           =   2  'Dropdown List
         TabIndex        =   12
         Top             =   1870
         Width           =   2715
      End
      Begin VB.ComboBox ComboStatus 
         BackColor       =   &H8000000F&
         DataField       =   "D_Status"
         Height          =   315
         ItemData        =   "FrmDomainsHist.frx":0004
         Left            =   9720
         List            =   "FrmDomainsHist.frx":0006
         Locked          =   -1  'True
         TabIndex        =   14
         Top             =   240
         Width           =   1335
      End
      Begin VB.Label LblClikPay 
         Caption         =   "Clik Pay"
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
         Left            =   8400
         TabIndex        =   59
         Top             =   1550
         Width           =   1215
      End
      Begin VB.Label LblVATStatus 
         Caption         =   "VAT Status"
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
         Left            =   5040
         TabIndex        =   58
         Top             =   1220
         Visible         =   0   'False
         Width           =   1395
      End
      Begin VB.Label LblBillStatus 
         Caption         =   "Bill Status"
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
         Left            =   5040
         TabIndex        =   57
         Top             =   1545
         Width           =   1395
      End
      Begin VB.Label LblRemark 
         Caption         =   "Remark"
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
         Left            =   4080
         TabIndex        =   54
         Top             =   2400
         Width           =   1455
      End
      Begin VB.Label LblBilCon 
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
         Left            =   240
         TabIndex        =   53
         Top             =   2900
         Width           =   1500
      End
      Begin VB.Label LblTechCon 
         Caption         =   "Tech Contact"
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
         TabIndex        =   52
         Top             =   2550
         Width           =   1500
      End
      Begin VB.Label LblAdCon1 
         Caption         =   "Admin Contact 1"
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
         TabIndex        =   51
         Top             =   1870
         Width           =   1500
      End
      Begin VB.Label LblDomainHolder 
         Caption         =   "Domain Holder"
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
         TabIndex        =   50
         Top             =   555
         Width           =   1500
      End
      Begin VB.Label LblAccName 
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
         TabIndex        =   49
         Top             =   885
         Width           =   1500
      End
      Begin VB.Label LblDName 
         Caption         =   "Domain Name"
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
         Left            =   240
         TabIndex        =   48
         Top             =   240
         Width           =   1500
      End
      Begin VB.Label LblAdCon2 
         Caption         =   "Admin Contact 2"
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
         TabIndex        =   47
         Top             =   2200
         Width           =   1500
      End
      Begin VB.Label LblDNSIPAdd 
         Caption         =   "DNS IP Address"
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
         Left            =   5040
         TabIndex        =   46
         Top             =   2200
         Width           =   1395
      End
      Begin VB.Label LblDNSName 
         Caption         =   "DNS Name"
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
         Left            =   5040
         TabIndex        =   45
         Top             =   1870
         Width           =   1395
      End
      Begin VB.Label LblRegDate 
         Caption         =   "Reg Date"
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
         Left            =   8400
         TabIndex        =   44
         Top             =   885
         Width           =   1215
      End
      Begin VB.Label LblRenewDate 
         Caption         =   "Renew Date"
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
         Left            =   8400
         TabIndex        =   43
         Top             =   1200
         Width           =   1215
      End
      Begin VB.Label LblChanged 
         Caption         =   "Changed"
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
         Left            =   8400
         TabIndex        =   42
         Top             =   555
         Width           =   1215
      End
      Begin VB.Label LbStatus 
         Caption         =   "Status"
         DataField       =   "Status"
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
         Left            =   8400
         TabIndex        =   41
         Top             =   240
         Width           =   1215
      End
      Begin VB.Label LblClass 
         Caption         =   "Class"
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
         TabIndex        =   40
         Top             =   1200
         Width           =   1575
      End
      Begin VB.Label LblCategory 
         Caption         =   "Category"
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
         TabIndex        =   39
         Top             =   1550
         Width           =   1335
      End
   End
   Begin VB.Frame FraScrh 
      Caption         =   "Search"
      ForeColor       =   &H00000000&
      Height          =   1215
      Left            =   240
      TabIndex        =   29
      Top             =   120
      Width           =   11295
      Begin VB.Frame FrameSearch2 
         BorderStyle     =   0  'None
         Height          =   855
         Left            =   3120
         TabIndex        =   37
         Top             =   240
         Width           =   6855
         Begin VB.ComboBox ComboSearch 
            Height          =   315
            Left            =   480
            TabIndex        =   0
            Top             =   120
            Width           =   2295
         End
         Begin VB.ComboBox CmbSearchList 
            Height          =   315
            Left            =   480
            TabIndex        =   3
            Top             =   480
            Width           =   6315
         End
         Begin VB.TextBox TxtScrh 
            Height          =   285
            Left            =   480
            TabIndex        =   1
            Top             =   120
            Width           =   2295
         End
         Begin VB.CommandButton CmdScrh 
            Caption         =   "&Search"
            Default         =   -1  'True
            Height          =   375
            Left            =   3120
            TabIndex        =   2
            Top             =   0
            Width           =   975
         End
      End
      Begin VB.Frame FrameSearch1 
         BorderStyle     =   0  'None
         Height          =   735
         Left            =   120
         TabIndex        =   33
         Top             =   240
         Width           =   3135
         Begin VB.OptionButton OptHolder 
            Caption         =   "Domain Ho&lder"
            Height          =   255
            Left            =   1680
            TabIndex        =   38
            Top             =   480
            Width           =   1455
         End
         Begin VB.OptionButton OptDName 
            Caption         =   "D&omain Name"
            Height          =   255
            Left            =   120
            TabIndex        =   36
            Top             =   120
            Value           =   -1  'True
            Width           =   1455
         End
         Begin VB.OptionButton OptNHandle 
            Caption         =   "Nic &Handle"
            Height          =   255
            Left            =   120
            TabIndex        =   35
            Top             =   480
            Width           =   1455
         End
         Begin VB.OptionButton OptAcc 
            Caption         =   "&Account No"
            Height          =   255
            Left            =   1680
            TabIndex        =   34
            Top             =   120
            Width           =   1335
         End
      End
   End
End
Attribute VB_Name = "FrmDomainsHis"
Attribute VB_GlobalNameSpace = False
Attribute VB_Creatable = False
Attribute VB_PredeclaredId = True
Attribute VB_Exposed = False
Option Explicit
'***************************************************************************
'                                                                          *
'Writen By   : Celina Leong                                                *
'                                                                          *
'                                                                          *
'Description : View all domains that are deleted on the system             *
'              View the historical details of an existing domain           *

'                                                                          *
'                                                                          *
'***************************************************************************
Const mNo  As String = "N"
Const mYes As String = "Y"

Dim RsSearch As ADODB.Recordset
Dim RsSearchResults As ADODB.Recordset
Dim Rs1SearchResults As ADODB.Recordset
Dim mcsFormState As ControlState
Dim mblnAlterStatus As Boolean
Dim mblnTraAccount As Boolean
Dim mblnRecordLoaded As Boolean
Dim mstrTimeStamp As String
Dim mtimeStamp As String
Dim mDName As String
Dim sDNS_Order As Long
Dim DNSOrder As Long
Dim TempDNS As String
Dim TempDNS_Order As String
Dim TempDNSIpAdd As String
Dim DNSChng As Boolean
Dim ContactChng As Boolean
Dim strNicHandle As String
Dim strDName As String
Dim TempNichandle As String
Dim IndexDNSName As Integer
Dim TempType As String
Dim TempNow As String
Dim TempDNSName As String
Dim mblnProcessingCombo As Boolean
Dim mlngTemp As Long
Dim mlngPrevIndex As Long
Dim mstrPrevText As String
Dim strAdCon2 As String
Dim AcDataChanged As Boolean
Dim strAcNo As Long
Dim strContact As String
Dim strOldBilCon As String


Dim TempComboClassIndex As Long
Dim mblnDeleteStatus As Boolean 'Phoenenix1.1
Dim mblnDeleteDNS As Boolean ' Phoenix1.1
Dim TempCombosListIndex As Integer ' Phoenix1.1
Dim TempContact As String ' Phoenix1.1
Dim TempAdmin   As String ' Phoenix1.1
Dim TempAdmin1   As String ' Phoenix1.1
Dim TempTechC   As String ' Phoenix1.1
Dim TempChangeDate As String
Dim strBatch As String








'* Only allow editing of the status field
Private Sub CmdAlStatus_Click()

'If TxtScrh.Text = "" Or TxtDName.Text = "" Then
'    MsgBox " Please enter details for search ", vbExclamation
''    Celina Leong Phoenix1.1 28/03/03
If ComboSearch.Text = "" Or TxtDName.Text = "" Then
    MsgBox " Please enter details for search ", vbExclamation
Else
    TxtRemark.Text = ""
    Set TxtRemark.DataSource = Nothing
    mcsFormState = csEdit
    mblnAlterStatus = True
    FormatControl ComboStatus, csEdit
    FormatControl TxtRemark, csEdit
    TxtRemark.Enabled = True
    TxtRemark.Text = ""
    CmdScrh.Enabled = False
'Celina Leong Phoenix1.1 11/04/03
     FillDNSNameComboE
     Set ComboDNSNameE.DataSource = Nothing
'---------------------------------------
'    TxtScrh.Enabled = False
'    ShowButtons False, False, False, True, True, False
'    Celina Leong Phoenix1.1 28/03/03
    ComboSearch.Enabled = False
    ShowButtons False, False, False, True, True, False, False
    ComboStatus.SetFocus

End If



End Sub
'Cancel Editing a record or Editing Status or Editing Transfer Account fields
Private Sub CmdCancel_Click()

 Dim ctl As Control
 
If mcsFormState = csEdit Then

    If OptDName = True Then
        Show_Results_DName CmbSearchList.Text
        
    ElseIf OptNHandle = True Then
       Show_Results_NHandle CmbSearchList.Text
    
    ElseIf OptAcc = True Then
        Show_Results_Acc CmbSearchList.Text
    'Else
    '    Show_Results_Holder CmbSearchList.Text
    End If

ElseIf mcsFormState = csView Then
    If OptDName = True Then
        Show_Results_DName CmbSearchList.Text
        
    ElseIf OptNHandle = True Then
       Show_Results_NHandle CmbSearchList.Text
    
    ElseIf OptAcc = True Then
        Show_Results_Acc CmbSearchList.Text
'    Else
'        Show_Results_Holder CmbSearchList.Text
    End If

End If

mcsFormState = csView
mblnAlterStatus = False
mblnTraAccount = False
mblnDeleteStatus = False

FormatControls Me, csView
FormatControl CmbSearchList, csEdit
'FormatControl TxtScrh, csEdit
TxtRemark.BorderStyle = 1
'ShowButtons True, True, True, False, False, True
' Celina Leong Phoenix1.1 28/03/03
FormatControl ComboSearch, csEdit
ShowButtons False, False, False, False, False, True, False
'ComboVATStatus.Visible = False 'Celina Leong not display in phase Phoenix1.1
ComboBillStatus.Visible = False
ComboClikPay.Visible = False
'TxtVATStatus.Visible = True 'Celina Leong not display in phase Phoenix1.1
TxtBillStatus.Visible = True
TxtClikPay.Visible = True
'CmdAlStatus.Visible = True
'CmdTraAC.Visible = True
'-------------------------------------------------------
 For Each ctl In Controls

                If TypeOf ctl Is OptionButton Then
                     ctl.Enabled = True
                     ctl.Visible = True
                End If
               
            Next
' OptHolder.Enabled = False 'Celina Leong Phoenix1.1 03/04/03
 ComboDNSNameE.Visible = False
 ComboDNSName.Visible = True
 ComboDNSName.Enabled = True
 ComboDNSName.Locked = False
 CmbSearchList.Enabled = True
 CmdScrh.Enabled = True
 'TxtScrh.Enabled = True
 'celina Leong Phoenix1.1 28/03/03
    ComboSearch.Visible = True
    ComboClass.Visible = False
    ComboCategory.Visible = False
    TxtClass.Visible = True
    TxtCategory.Visible = True
'-----------------------------------------
End Sub

Private Sub CmdClose_Click()
Unload Me
End Sub

Private Sub PopDomain_Grid()
'Title heading name for Domain Grid


With HFlexDomainsHis
     .ColWidth(0) = 100
     .ColWidth(1) = 1500
     .ColWidth(2) = 2000
     .ColWidth(3) = 9900
     .ColWidth(4) = 0
 
     .TextMatrix(0, 1) = "Date"
     .TextMatrix(0, 2) = "Nic Handle"
     .TextMatrix(0, 3) = "Remark"
 End With

End Sub

'       Celina Leong Phoenix1.1 28/03/03


'       Celina Leong Phoenix1.1 28/03/03
Private Sub CmdDelDNSName_Click()
Dim TempIndex As Long
    
    
    If ComboDNSNameE.Text = "(NEW)" Then
        MsgBox " You cannot delete (NEW)", vbExclamation
    Else
    
      If MsgBox("Are you sure you want to delete DNS Name : " & ComboDNSNameE.Text & _
              " and DNS IP Address : " & ListIPAdd.Text & " ? ", vbQuestion + vbYesNo) = vbNo Then
          mblnDeleteDNS = False
      Else
        mblnDeleteDNS = True
        If ComboDNSNameE.ItemData(ComboDNSNameE.ListIndex) = 2 Then
            TempIndex = ListIPAdd.NewIndex
            ListIPAdd.RemoveItem TempIndex
            ComboDNSNameE.RemoveItem TempIndex

        Else
            TempIndex = ListIPAdd.ListIndex
            ListDNSname.AddItem ComboDNSNameE.List(TempIndex)
            ListIPAdd.RemoveItem TempIndex
            ComboDNSNameE.RemoveItem TempIndex
            ListDNSname.ItemData(ListDNSname.NewIndex) = 1
        End If
            
           ComboDNSNameE.ListIndex = 1
         
     End If
    End If
    

      
End Sub


' Changable for most of the field when Edit command click
' Change not allow for Domain Name, Acccount Name, Billing Contacta and Date Changed
Private Sub CmdEdit_Click()

'tsmyth oct2003 - ensure english format date on PC
Call CheckDateFormat

'If TxtScrh.Text = "" Or TxtDName.Text = "" Then
'    MsgBox " Please enter details for search ", vbExclamation
'Celina Leong Phoenix1.1 01/04/03
If ComboSearch.Text = "" Or TxtDName.Text = "" Then
    MsgBox " Please enter details for search ", vbExclamation
        
Else
        
        TxtRemark.Text = ""
        
        mcsFormState = csEdit
        mblnAlterStatus = False
        mblnTraAccount = False
        FillDNSNameComboE
        Set TxtRemark.DataSource = Nothing
        Set ComboDNSNameE.DataSource = Nothing
        Set TxtDNSIPAdd.DataSource = Nothing
        Set TxtRegDate.DataSource = Nothing
        ComboDNSName.Visible = False
        ComboDNSNameE.Visible = True
'celina Leong Phoenix1.1 28/03/03
        mblnDeleteDNS = False
        TxtClass.Visible = False
        TxtCategory.Visible = False
        ComboClass.Visible = True
        ComboCategory.Visible = True
'        ComboVATStatus.Visible = True 'Celina Leong not display in phase Phoenix1.1
        ComboBillStatus.Visible = True
        ComboClikPay.Visible = True
'        TxtVATStatus.Visible = False
        TxtBillStatus.Visible = False
        TxtClikPay.Visible = False
        FillComboVatStatus ComboVATStatus
        FillComboBillStatus ComboBillStatus
        FillComboClikPay ComboClikPay
'----------------------------------------
        FormatControls Me, csEdit
        FormatControl ComboDNSNameE, csEdit
        FormatControl TxtDNSIPAdd, csEdit
        FormatControl TxtDHolder, csEdit   ' add code 5/7/02 celina
        FormatControl TxtDName, csView
        FormatControl TxtAccName, csView
        FormatControl TxtBilCon, csView
        FormatControl TxtChanged, csView
        FormatControl ComboStatus, csView
        CmdScrh.Enabled = False
'        OptHolder.Enabled = False 'Celina Leong Phoenix1.1 03/04/03
 '       TxtScrh.Enabled = False
'       ShowButtons True, False, False, False, True, True
'       Celina Leong Phoenix1.1 28/03/03
        ComboSearch.Enabled = False
'        CmdAlStatus.Visible = False
'        CmdDelDNSName.Visible = True
      
        ShowButtons False, False, False, True, True, False, True
        TxtDHolder.SetFocus
        ListDNSname.Clear
       
 End If
End Sub

Private Sub FillDNSNameCombo()
     
 sSQL = "SELECT DNS_Name,DNS_Order FROM DNS " & _
        "WHERE D_Name=" & CDBText(CmbSearchList.Text) & " " & _
        "ORDER BY DNS_Order "
        
  Set RsSearchResults = New ADODB.Recordset
  RsSearchResults.Open sSQL, CnPhoenix, adOpenDynamic, adLockOptimistic
    
    ComboDNSName.Clear
     
    While Not RsSearchResults.EOF
        ComboDNSName.AddItem RsSearchResults("DNS_Name")
        'ComboDNSName.ItemData(ComboDNSName.NewIndex) = RsSearchResults("DNS_Order")
         ComboDNSName.Enabled = True
        If RsSearchResults.EOF = False Then
            RsSearchResults.MoveNext
        End If
    
    Wend
'    If TxtScrh.Text = "" Then
'        MsgBox " Please enter details for search ", vbExclamation
'
'    Else
'        RsSearchResults.MoveFirst
'    End If
    If ComboDNSName.Listcount > 0 Then
            ComboDNSName.ListIndex = 0
    End If
    
     
 
End Sub
Private Sub FillDNSNameComboE()
  
  Dim anew As String
  
  anew = "(NEW)"
  
 sSQL = "SELECT DNS_Name, DNS_IpAddr, DNS_Order FROM DNS " & _
        "WHERE D_Name=" & CDBText(CmbSearchList.Text) & " " & _
         "ORDER BY DNS_Order "
      
     
  Set RsSearchResults = New ADODB.Recordset
  RsSearchResults.Open sSQL, CnPhoenix, adOpenDynamic, adLockOptimistic
    
    mblnProcessingCombo = True
    ComboDNSNameE.Clear
    ListIPAdd.Clear
    
    While Not RsSearchResults.EOF
        ComboDNSNameE.AddItem RsSearchResults("DNS_Name")
        ListIPAdd.AddItem RsSearchResults("DNS_IpAddr")
  
       ' TempDNS_Order = RsSearchResults("DNS_Order")
        ListIPAdd.ItemData(ListIPAdd.NewIndex) = RsSearchResults("DNS_order")
        If RsSearchResults.EOF = False Then
            RsSearchResults.MoveNext
        End If
    
    Wend
    
    RsSearchResults.MoveFirst
   
    ComboDNSNameE.AddItem anew, 0
    ListIPAdd.AddItem anew, 0
    
    mblnProcessingCombo = False
      
    If ComboDNSNameE.Listcount > 1 Then
         mlngPrevIndex = 1
        ComboDNSNameE.ListIndex = 1
      
    Else
         mlngPrevIndex = 0
        ComboDNSNameE.ListIndex = 0
       
    End If
    
    'If ListIPAdd.ListCount > 0 Then
    '        ListIPAdd.ListIndex = 0
    'End If
        
    
End Sub






Private Sub ComboClass_Click()
    FillCategoryCombo ComboCategory, ComboClass
End Sub

Private Sub ComboDNSName_Click()
Dim sTemp As String
Dim strSQL As String
Dim rec As ADODB.Recordset

'tsmyth oct2003 - ensure english format date on PC
Call CheckDateFormat

'Set RsSearchResults = New ADODB.Recordset

   ' sSQL = "SELECT DNS_IpAddr,DNS_Name,DNS_Order FROM DNS " &
   '        "WHERE D_Name=" & CDBText(CmbSearchList.Text) & " " & _
   '        "AND DNS_Name =" & CDBText(ComboDNSName.Text) & " "
          
     strSQL = "SELECT DNS_IpAddr,DNS_Name,DNS_Order FROM DNS "
     strSQL = strSQL & "WHERE D_Name=" & CDBText(CmbSearchList.Text) & " "
     strSQL = strSQL & "AND DNS_Name =" & CDBText(ComboDNSName.Text) & " "
     
       DB.Execute strSQL, rec
    
    'Set RsSearchResults = New ADODB.Recordset
    'RsSearchResults.Open sSQL, CnPhoenix, adOpenDynamic, adLockOptimistic
    
  If Not rec.EOF Then
  
    'sDNS_Order = RsSearchResults("DNS_Order")
    'TxtDNSIPAdd.Text = RsSearchResults("DNS_IpAddr")
    sDNS_Order = NoNull(rec.Fields("DNS_Order"))
    TxtDNSIPAdd.Text = NoNull(rec.Fields("DNS_IpAddr"))
        
  End If
  FormatControl TxtDNSIPAdd, csView
''  celina Leong Phoenix1.1 28/03/03
'  If mblnDeleteStatus = True Then
'     FormatControl TxtDNSIPAdd, csEdit
'
'  Else
'     FormatControl TxtDNSIPAdd, csView
'  End If
End Sub
'-----------------------------------------------------------------------
''''Not Use in DomainHis
'* Saves an edited record
''''
''''Private Sub CmdSave_Click()
''''
''''Dim ctl As Control
''''Dim blnError As Boolean
''''Dim ctlError As Control
''''
''''Screen.MousePointer = vbHourglass
''''   TempNow = Now()
''''
''''On Error GoTo EXE_Err
''''
''''DB.BeginTransaction
''''
''''If mcsFormState = csEdit Then
''''
''''    If mblnAlterStatus Then
''''        If IsValidStatus Then
''''            UpdateStatus blnError
''''
''''         Else
''''            blnError = True
''''        End If
'''''    'Celina Leong Phoenix1.1 01/04/03
'''''    ElseIf mblnDeleteDNS Then
'''''
'''''        UpdateDN blnError
'''''        Process_Combo
'''''            If DNS_Changed Then
'''''                  Update_DNS blnError
'''''            End If
''''
''''    ElseIf mblnTraAccount Then
''''        If ValidTraAccount Then
''''
''''            UpdateTraAccount blnError
''''
''''                If NH_Changed Then
''''
''''                     Update_Nichandle blnError
''''                End If
''''                Process_Combo
''''                If DNS_Changed Then
''''                     Update_DNS blnError
''''                End If
''''
''''        Else
''''            blnError = True
''''        End If
''''
''''    Else
''''        If IsValid Then
''''
''''            UpdateDN blnError
''''
''''            If NH_Changed Then
''''                  Update_Nichandle blnError
''''            End If
''''            Process_Combo
''''            If DNS_Changed Then
''''                  Update_DNS blnError
''''            End If
''''
''''        Else
''''            blnError = True
''''        End If
''''
''''    End If
''''
''''
''''
''''End If
''''
''''If Not blnError Then
''''
''''    DB.CommitTransaction
''''    If OptDName = True Then
''''           Show_Results_DName CmbSearchList.Text
''''
''''    ElseIf OptNHandle = True Then
''''           Show_Results_NHandle CmbSearchList.Text
''''
''''    ElseIf OptAcc = True Then
''''           Show_Results_Acc CmbSearchList.Text
'''''    Else
'''''           Show_Results_Holder CmbSearchList.Text
''''    End If
''''
''''
''''
''''
''''    mcsFormState = csView
''''    FormatControls Me, csView
''''   ' ShowButtons True, True, True, False, False, True
''''   ' Celina Leong Phoenix1.1 28/03/03
''''     ShowButtons True, True, True, False, False, True, False
''''     CmdAlStatus.Visible = True
''''     CmdTraAC.Visible = True
'''''     ComboVATStatus.Visible = False 'Celina Leong not display in phase Phoenix1.1
''''     ComboBillStatus.Visible = False
''''     ComboClikPay.Visible = False
'''''     TxtVATStatus.Visible = True 'Celina Leong not display in phase Phoenix1.1
''''     TxtBillStatus.Visible = True
''''     TxtClikPay.Visible = True
''''   '-----------------------------------------------------------
''''    FormatControl CmbSearchList, csEdit
'''' '   FormatControl TxtScrh, csEdit
''''    FormatControl ComboSearch, csEdit 'Celina Leong Phoenix1.1 01/04/03
''''    FormatControl ComboDNSNameE, csEdit
''''    mblnAlterStatus = False
''''    For Each ctl In Controls
''''
''''                If TypeOf ctl Is OptionButton Then
''''                     ctl.Enabled = True
''''                     ctl.Visible = True
''''                End If
''''
''''            Next
''''
'''''    OptHolder.Enabled = False 'Celina Leong Phoenix1.1 03/04/03
''''    ComboDNSNameE.Visible = False
''''    ComboDNSName.Visible = True
''''    ComboDNSName.Enabled = True
''''    ComboDNSName.Locked = False
''''    TxtRemark.BorderStyle = 1
''''    CmdScrh.Enabled = True
'''''    TxtScrh.Enabled = True
'''''celina Leong Phoenix1.1 28/03/03
''''    ComboSearch.Enabled = True
''''    ComboClass.Visible = False
''''    ComboCategory.Visible = False
''''    TxtClass.Visible = True
''''    TxtCategory.Visible = True
'''''-----------------------------------------
''''Else
''''    DB.RollbackTransaction
''''    Set ctlError = FindControlInError(Me)
''''    If Not (ctlError Is Nothing) Then
''''        ctlError.SetFocus
''''    End If
''''End If
''''    Screen.MousePointer = vbNormal
''''Exit Sub
''''
''''EXE_Err:
''''    On Error Resume Next
''''
''''    If DB.TransactionLevel > 0 Then
''''       DB.RollbackTransaction
''''    End If
''''    DB.ShowError " Unable to update domain maintenance "
''''    blnError = True
''''    Screen.MousePointer = vbNormal
''''End Sub

Private Sub CmdScrh_Click()
Dim TempComboSearch As String  'Phoenix1.1
Dim Counter As Long ' Phoenix1.1
Dim Listcnt As Long ' Phoenix1.1
Dim Found As Boolean ' Phoenix1.1

Screen.MousePointer = vbHourglass
'If TxtScrh.Text = "" Then
'    MsgBox " Please enter details for search ", vbExclamation
'    Screen.MousePointer = vbDefault
'Celina Leong Phoenix1.1 01/04/03
If ComboSearch.Text = "" Then
    MsgBox " Please enter details for search ", vbExclamation
    Screen.MousePointer = vbDefault
'-------------------------------------------------------
Else
    
    Screen.MousePointer = vbHourglass

'Celina Leong Phoenix1.1 01/04/03
        TempComboSearch = ComboSearch.Text
        Listcnt = ComboSearch.Listcount
        Found = False
        Counter = 0
   
        Do While Counter <= Listcnt
                 'Counter = Counter + 1
                  If ComboSearch.List(Counter) = TempComboSearch Then
                     Found = True
                     Exit Do
                  Else
                     Found = False
                  End If
                 Counter = Counter + 1
        Loop
    
    
    If Found = False Then
            ComboSearch.AddItem ComboSearch.Text
    End If
'--------------------------------------
    
    Set RsSearch = New ADODB.Recordset
    
    '* Select option domain name for search
    
    If OptDName.Value = True Then
    
'        sSQL = "SELECT * FROM Domain " & _
'               "WHERE D_Name like '" & TxtScrh & "%'" & _
'               "ORDER BY D_Name "
'Celina Leong Phoenix1.1 01/04/03

         sSQL = "SELECT * FROM DomainHist " & _
                "WHERE D_Name like " & CDBText(ComboSearch & "%") & _
                "AND D_Status = " & CDBText("Deleted") & " " & _
                "ORDER BY D_Name "
'------------------------------------------------------
        Set RsSearch = New ADODB.Recordset
        RsSearch.Open sSQL, CnPhoenix, adOpenDynamic, adLockOptimistic
        
        If Not RsSearch.EOF Then
            CmbSearchList.Clear
            While Not RsSearch.EOF
            
'            'Celina Leong Phoenix1.1 17/04/03
                If CmbSearchList.Listcount >= 4000 Then
                     MsgBox " Record in excess of 4000  ", vbCritical
                     Screen.MousePointer = vbDefault
                     If CmbSearchList.Listcount > 0 Then
                        CmbSearchList.ListIndex = 0
                     End If
                     Exit Sub
    '            '----------------------------------------
                Else
                
                    CmbSearchList.AddItem RsSearch("D_Name")
                    RsSearch.MoveNext
                End If
            Wend
            
            If CmbSearchList.Listcount > 0 Then
                CmbSearchList.ListIndex = 0
            End If
        Else
            MsgBox "No data Found", vbInformation
        End If
    
        Screen.MousePointer = vbDefault

'Celina Leong Phoenix1.1 03/04/03
'Select option Domain Holder for search
     ElseIf OptHolder.Value = True Then

         sSQL = "SELECT * FROM DomainHist " & _
               "WHERE D_Holder like " & CDBText("%" & ComboSearch & "%") & _
               "AND D_Status = " & CDBText("Deleted") & " " & _
               "ORDER BY D_Name "

        Set RsSearch = New ADODB.Recordset
        RsSearch.Open sSQL, CnPhoenix, adOpenDynamic, adLockOptimistic
        If Not RsSearch.EOF Then
            CmbSearchList.Clear
            While Not RsSearch.EOF
                CmbSearchList.AddItem RsSearch("D_Name")
                RsSearch.MoveNext
            Wend
            
            If CmbSearchList.Listcount > 0 Then
                CmbSearchList.ListIndex = 0
            End If
        Else
            MsgBox "No data Found", vbInformation
        End If
    
        Screen.MousePointer = vbDefault
'------------------------------------------------------
'* Select option nichandle for search
    
    ElseIf OptNHandle.Value = True Then
         

           sSQL = "SELECT DISTINCT ContactHist.Contact_NH, ContactHist.D_Name, " & _
                           "DomainHist.D_Name, DomainHist.D_Status " & _
                           "FROM ContactHist "

            sSQL = sSQL & "INNER JOIN DomainHist " & _
                          "ON ContactHist.D_Name = DomainHist.D_Name " & _
                          "WHERE Contact_NH like " & CDBText(ComboSearch & "%") & _
                          "AND DomainHist.D_Status = " & CDBText("Deleted") & " " & _
                          "ORDER BY DomainHist.D_Name "

        Set RsSearch = New ADODB.Recordset
        RsSearch.Open sSQL, CnPhoenix, adOpenDynamic, adLockOptimistic
   
       
        
        
        If Not RsSearch.EOF Then
            CmbSearchList.Clear
            While Not RsSearch.EOF
            
                CmbSearchList.AddItem RsSearch("D_Name")
                RsSearch.MoveNext
            Wend
            If CmbSearchList.Listcount > 0 Then
                CmbSearchList.ListIndex = 0
            End If
        Else
            MsgBox "No data Found", vbInformation
        End If
    
        Screen.MousePointer = vbDefault
    
    'Select option Account for search
    
    ElseIf OptAcc.Value = True Then
    
         If IsNumeric(ComboSearch) Then
                     sSQL = "SELECT * FROM DomainHist " & _
                               "WHERE A_Number = " & ComboSearch & " " & _
                               "AND D_Status = " & CDBText("Deleted") & " " & _
                               "ORDER BY D_Name "
                 
                  
                     Set RsSearch = New ADODB.Recordset
                     RsSearch.Open sSQL, CnPhoenix, adOpenDynamic, adLockOptimistic
                
                     
                     If Not RsSearch.EOF Then
                         CmbSearchList.Clear
                         While Not RsSearch.EOF
                             CmbSearchList.AddItem RsSearch("D_Name")
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
       Screen.MousePointer = vbDefault
    
    End If

End If

End Sub


Private Sub CmbSearchList_Click()
If OptDName = True Then
    Show_Results_DName CmbSearchList.Text
  
ElseIf OptNHandle = True Then
   Show_Results_NHandle CmbSearchList.Text

ElseIf OptAcc = True Then
    Show_Results_Acc CmbSearchList.Text
'Celina Leong Phoenix1.1 03/04/03
Else
    Show_Results_Holder CmbSearchList.Text
  
'------------------------------------------
   
End If

End Sub

Private Sub CmdTraAC_Click()
Dim ctl As Control

'If TxtScrh.Text = "" Or TxtDName.Text = "" Then
'    MsgBox " Please enter details for search  ", vbExclamation
'Celina LeongPhoenix1.1

If ComboSearch.Text = "" Or TxtDName.Text = "" Then
    MsgBox " Please enter details for search  ", vbExclamation
'-------------------------------
Else
        TxtRemark.Text = ""
        mcsFormState = csEdit
        mblnTraAccount = True
        FillDNSNameComboE
        Set TxtRemark.DataSource = Nothing
        Set ComboDNSNameE.DataSource = Nothing
        Set TxtDNSIPAdd.DataSource = Nothing
        ComboDNSNameE.Visible = True
        ComboDNSName.Visible = False
        FormatControl TxtAccName, csEdit
        FormatControl TxtAdCon1, csEdit
        FormatControl TxtAdCon2, csEdit
        FormatControl TxtTechCon, csEdit
        FormatControl ComboDNSNameE, csEdit
        FormatControl TxtDNSIPAdd, csEdit
        FormatControl TxtRemark, csEdit
        CmdScrh.Enabled = False
       ' TxtScrh.Enabled = False
    
'        ShowButtons False, False, False, True, True, False
'        Celina Leong Phoenix1.1 28/03/03
        ComboSearch.Enabled = False
'        ComboVATStatus.Visible = True 'Celina Leong not display in phase Phoenix1.1
'        ComboBillStatus.Visible = True
'        ComboClikPay.Visible = True
'        TxtVATStatus.Visible = False 'Celina Leong not display in phase Phoenix1.1
'        TxtBillStatus.Visible = False
'        TxtClikPay.Visible = False
'        FillComboVatStatus ComboVATStatus
'        FillComboBillStatus ComboBillStatus
'        FillComboClikPay ComboClikPay
        ShowButtons False, False, False, True, True, False, True
      
     '   FillDNSNameCombo
        
End If
End Sub



Private Sub ComboDNSName_KeyDown(KeyCode As Integer, Shift As Integer)

KeyCode = 0
End Sub

Private Sub ComboDNSName_KeyPress(KeyAscii As Integer)
KeyAscii = 0
End Sub

Private Sub ComboDNSName_KeyUp(KeyCode As Integer, Shift As Integer)
KeyCode = 0
End Sub

Private Sub ComboDNSNameE_Change()
    mlngPrevIndex = ComboDNSNameE.ListIndex
    mstrPrevText = ComboDNSNameE.Text
    
End Sub

Private Sub ComboDNSNameE_Click()

'  Dim sTemp As String
  Dim strSQL As String
  Dim rec As ADODB.Recordset

       strSQL = "SELECT DNS_IpAddr, DNS_Name, DNS_Order From DNS "
       strSQL = strSQL & " WHERE D_Name=" & CDBText(CmbSearchList.Text) & ""
       strSQL = strSQL & " AND DNS_Name =" & CDBText(ComboDNSNameE.Text) & ""

       DB.Execute strSQL, rec

       If Not rec.EOF Then

          sDNS_Order = NoNull(rec.Fields("DNS_Order"))
          '  TxtDNSIPAdd.Text = NoNull(rec.Fields("DNS_IpAddr"))
          '  TempDNS = ComboDNSNameE.Text

         '   TempDNSIpAdd = TxtDNSIPAdd.Text
       End If


   ' FormatControl ComboDNSNameE, csEdit
   ' FormatControl TxtDNSIPAdd, csEdit
    
     
    Process_Combo
    ListIPAdd.ListIndex = ComboDNSNameE.ListIndex
    TxtDNSIPAdd.Text = ListIPAdd.Text
    TxtDNSIPAdd.DataChanged = False
    mlngPrevIndex = ComboDNSNameE.ListIndex
    mstrPrevText = ComboDNSNameE.Text
End Sub
Private Sub Process_Combo()
Dim lngtempsortorder As Long
    If mblnProcessingCombo Then Exit Sub
    mblnProcessingCombo = True
    

    If mlngPrevIndex = -1 Then
        If mlngTemp = 0 Then    '/* new item */
           ComboDNSNameE.AddItem mstrPrevText
           ListIPAdd.AddItem TxtDNSIPAdd.Text
           ComboDNSNameE.ItemData(ComboDNSNameE.NewIndex) = 2
           TxtDNSIPAdd.DataChanged = False
           ComboDNSNameE.ListIndex = ComboDNSNameE.NewIndex
           ListIPAdd.ItemData(ListIPAdd.NewIndex) = GetNext
         
            
        Else
            lngtempsortorder = ListIPAdd.ItemData(mlngTemp)
            ComboDNSNameE.RemoveItem (mlngTemp)
            ComboDNSNameE.AddItem mstrPrevText, mlngTemp
            ListIPAdd.ItemData(mlngTemp) = lngtempsortorder
            
            If ComboDNSNameE.ItemData(mlngTemp) = 0 Then
                ComboDNSNameE.ItemData(mlngTemp) = 1
            End If
        End If
        
        
        
    
            
  '          ComboDNSNameE.ListIndex = mlngTemp
    End If
    
    If TxtDNSIPAdd.DataChanged Then
        lngtempsortorder = ListIPAdd.ItemData(mlngTemp)
        ListIPAdd.RemoveItem (mlngTemp)
        ListIPAdd.AddItem TxtDNSIPAdd.Text, mlngTemp
        ListIPAdd.ItemData(mlngTemp) = lngtempsortorder
        If ComboDNSNameE.ItemData(mlngTemp) = 0 Then
            ComboDNSNameE.ItemData(mlngTemp) = 1
        End If
    End If
    
    mblnProcessingCombo = False
    If ComboDNSNameE.ListIndex > -1 Then
        mlngTemp = ComboDNSNameE.ListIndex
    End If
    
    
    
    
  
     
End Sub
' Celina Leong Phoenix1.1 28/03/03
'Allow DNS name insert alpha numeric and Dot only
'Private Sub ComboDNSNameE_KeyPress(KeyAscii As Integer)
'Dim sSearchString As String, iStrPos As Integer, iDotCount As Integer
'
'   Select Case KeyAscii
'          Case 48 To 57     ' 0 to 9
'          Case 97 To 122    ' A to Z
'          Case 8            ' Backspace
'          Case 46           ' .
'
''          sSearchString = ComboDNSNameE
''          iDotCount = 0
''          iStrPos = 1
''          While Not iStrPos = 0
''             iStrPos = InStr(iStrPos, sSearchString, ".", vbTextCompare)
''
''             If iStrPos <> 0 Then
''                iDotCount = iDotCount + 1
''                sSearchString = Left(sSearchString, iStrPos - 1) & "#" & Right(sSearchString, Len(sSearchString) - iStrPos)
''             End If
''          Wend
''
''          If iDotCount > 2 Then KeyAscii = 0
'    Case Else
'        KeyAscii = 0
'    End Select
'End Sub

Private Sub ComboDNSNameE_LostFocus()
    Process_Combo
    mlngPrevIndex = ComboDNSNameE.ListIndex
    mstrPrevText = ComboDNSNameE.Text
    If ComboDNSNameE.ListIndex > -1 Then
         mlngTemp = ComboDNSNameE.ListIndex
    End If
End Sub








Private Sub Form_KeyDown(KeyCode As Integer, Shift As Integer)
    Select Case KeyCode
    Case vbKeyF4     '* Alter Status
        If CmdAlStatus.Enabled Then
            CmdAlStatus.Value = True
        End If
    Case vbKeyF8    '* Transfer Account
        If CmdTraAC.Enabled Then
            CmdTraAC.Value = True
        End If
    Case vbKeyF2    '* Edit
        If CmdEdit.Enabled Then
            CmdEdit.Value = True
        End If
    Case vbKeyF5    '* Cancel
        If CmdCancel.Enabled Then
            CmdCancel.Value = True
        End If
    Case vbKeyF6    '* Save
        If CmdSave.Enabled Then
            CmdSave.Value = True
        End If
    Case vbKeyF7    '* Close
        If CmdClose.Enabled Then
            CmdClose.Value = True
        End If
    Case vbKeyF3    '* Search
        If CmdScrh.Enabled Then
            CmdScrh.Value = True
        End If
           
    End Select
End Sub
Private Sub Form_Load()
Dim ctl As Control

mcsFormState = csView
mblnRecordLoaded = False
PopDomain_Grid

'FillCategoryCombo ComboCategory

FormatControls Me, csView
'ShowButtons True, True, True, False, False, True
' Celina Leong Phoenix1.1 28/03/03
ShowButtons False, False, False, False, False, True, False
'ComboVATStatus.Visible = False 'Celina Leong not display in phase Phoenix1.1
ComboBillStatus.Visible = False
ComboClikPay.Visible = False
'--------------------------------------------------------
FormatControl CmbSearchList, csEdit
'FormatControl TxtScrh, csEdit 'Celina Leong Phoenix1.1
 FormatControl ComboSearch, csEdit
TxtRemark.BorderStyle = 1
ComboDNSName.Visible = True
ComboDNSName.Locked = False
ComboDNSName.Enabled = False
CmbSearchList.Locked = False
ComboDNSNameE.Visible = False
ComboClass.Visible = False
ComboCategory.Visible = False
FillStatusCombo ComboStatus
FillClassCombo ComboClass
FormatControl ComboClass, csEdit
FormatControl ComboCategory, csEdit
'FillCategoryCombo ComboCategory




For Each ctl In Controls

                If TypeOf ctl Is OptionButton Then
                     ctl.Enabled = True
                     ctl.Visible = True
                End If
               
            Next

' OptHolder.Enabled = False 'Celina Leong Phoenix1.1 03/04/03
'tsmyth oct2003 - ensure english format date on PC
Call CheckDateFormat


End Sub

Private Sub Form_Resize()
     CenterControls Me
End Sub
'-----------------------------------------------------------------------
''''Not Use in DomainHis
'''''
'''''Private Sub Form_Unload(Save As Integer)
'''''If CmdSave.Enabled = True Then
'''''    If MsgBox("Do you want to save the changes " & _
'''''              "you made ? ", vbQuestion + vbYesNo) = vbNo Then
'''''
'''''        Save = False
'''''
'''''    Else
'''''        CmdSave_Click
'''''
'''''    End If
'''''End If
'''''
'''''End Sub


Private Sub HFlexDomainsHis_DblClick()
Dim lngRow As Long

'If Not mblnRecordLoaded Then Exit Sub '02/05/03
    lngRow = HFlexDomainsHis.Row
    If lngRow <> 0 Then
   
   
    
         If mcsFormState = csView Then
           
                strDName = CmbSearchList.Text
                Assign_DN_History strDName
                Assign_DNS_History strDName
                Assign_Contact_History strDName
    
'                ShowButtons False, False, True, True, False, True
'Celina Leong - Phoenix1.1 26/03/03
                 ShowButtons False, False, True, True, False, True, False
                CmdEdit.Enabled = False
                CmbSearchList.Enabled = False
                CmdScrh.Enabled = False
         End If
         
    End If
     
End Sub




Private Sub HFlexDomainsHis_KeyDown(KeyCode As Integer, Shift As Integer)
        If KeyCode = vbKeyReturn Then
            If mblnRecordLoaded Then
               HFlexDomainsHis_DblClick
            End If
         End If
End Sub

Private Sub HFlexDomainsHis_SelChange()
        HFlexDomainsHis.RowSel = HFlexDomainsHis.Row
End Sub







'Celina Leong 20-09-2002

Private Sub TxtAccName_Validate(KeepFocus As Boolean)
    If TxtAccName = 1 Then
        FormatControl TxtBilCon, csEdit
        KeepFocus = False
        AcDataChanged = True
    End If
 
End Sub

'***************************************************************************
' View the NIC Handle details of the Admin contacts 1                      *
' by double-clicking the relevant contact.                                 *
'***************************************************************************

Private Sub TxtAdCon1_DblClick()
'FrmNicHandle.Show
   
    If mcsFormState = csView Then
        If TxtAdCon1 <> "" Then
            strNicHandle = TxtAdCon1.Text
            FrmNicHandle.Show_Results_NHandle TxtAdCon1.Text
        End If
   End If
   
End Sub

Private Sub TxtAdCon1_LostFocus()
    If mcsFormState = csEdit Then
        If TxtAdCon1.DataChanged Then
             TxtAdCon1.ToolTipText = GetName(TxtAdCon1.Text)
        End If
    End If
        
End Sub

'***************************************************************************
' View the NIC Handle details of the Admin contacts 2                      *
' by double-clicking the relevant contact.                                 *
'***************************************************************************
Private Sub TxtAdCon2_DblClick()
 
   If mcsFormState = csView Then
        If TxtAdCon2 <> "" Then 'Exit Sub
            strNicHandle = TxtAdCon2.Text
            FrmNicHandle.Show_Results_NHandle TxtAdCon2
          
        End If
    End If
     
End Sub

Private Sub TxtAdCon2_LostFocus()
    If mcsFormState = csEdit Then
        If TxtAdCon2.DataChanged Then
             TxtAdCon2.ToolTipText = GetName(TxtAdCon2.Text)
        End If
    End If
End Sub

'***************************************************************************
' View the NIC Handle details of the Billing contacts                      *
' by double-clicking the relevant contact.                                 *
'***************************************************************************
Private Sub TxtBilCon_DblClick()
    
     If mcsFormState = csView Then
        If TxtBilCon <> "" Then  'Exit Sub
           strNicHandle = TxtBilCon.Text
           FrmNicHandle.Show_Results_NHandle TxtBilCon
        End If
     End If
End Sub

Private Sub TxtBilCon_LostFocus()
    If mcsFormState = csEdit Then
        If TxtBilCon.DataChanged Then
             TxtBilCon.ToolTipText = GetName(TxtBilCon.Text)
        End If
    End If
End Sub



Private Sub TxtDNSIPAdd_KeyPress(KeyAscii As Integer)
Dim sSearchString As String, iStrPos As Integer, iDotCount As Integer

Select Case KeyAscii
    Case 48 To 57
    Case 8
    Case 46
        sSearchString = TxtDNSIPAdd
        iDotCount = 0
        iStrPos = 1
        While Not iStrPos = 0
            iStrPos = InStr(iStrPos, sSearchString, ".", vbTextCompare)
        
            If iStrPos <> 0 Then
                iDotCount = iDotCount + 1
                sSearchString = Left(sSearchString, iStrPos - 1) & "#" & Right(sSearchString, Len(sSearchString) - iStrPos)
            End If
        Wend
        
        If iDotCount > 2 Then KeyAscii = 0
        
    Case Else
        KeyAscii = 0
End Select
End Sub
Private Sub TxtRemark_GotFocus()
  If Not TxtRemark.Locked Then
        CmdSave.Default = False
  End If
End Sub

Private Sub TxtRemark_LostFocus()
    If Not TxtRemark.Locked Then
        CmdSave.Default = True
  End If
End Sub





Private Sub TxtTechCon_DblClick()
     
      If mcsFormState = csView Then
        If TxtTechCon <> "" Then 'Exit Sub
           strNicHandle = TxtTechCon.Text
           FrmNicHandle.Show_Results_NHandle TxtTechCon
        End If
     End If
End Sub

'***************************************************************************
' Show domain detail that exist in the system for a specific domain name.  *
'***************************************************************************


Public Sub Show_Results_DName(ByVal strDName As String)
Dim rec As ADODB.Recordset
Dim strSQL As String

strSQL = "SELECT * FROM DomainHist "
strSQL = strSQL & "WHERE D_Name = " & CDBText(strDName) & " "
strSQL = strSQL & "AND D_Status = " & CDBText("Deleted")

DB.Execute strSQL, rec

If Not rec.EOF Then
   strBatch = NoNull(rec.Fields("Chng_NH"))
   TempChangeDate = NoNull(rec.Fields("Chng_Dt"))
   LoadDomainHistory strDName
End If

    
      
LoadDomains (strDName)

LoadNicHandle (strDName)

Assign_IPAddress (strDName)

mDName = strDName

TxtAccName = Format(TxtAccName, gstrAccountNoPad)

End Sub

'Celina Leong Phoenix1.1 03/04/03
'***************************************************************************
' Show domain detail that exist in the system for a specific domain holder.  *
'***************************************************************************


Public Sub Show_Results_Holder(ByVal strDName As String)
Dim rec As ADODB.Recordset
Dim strSQL As String

strSQL = "Select * From DomainHist where D_Name = " & CDBText(CmbSearchList.Text)
strSQL = strSQL & "AND D_Status = " & CDBText("Deleted")

DB.Execute strSQL, rec

If Not rec.EOF Then
    TempChangeDate = NoNull(rec.Fields("Chng_Dt"))
    LoadDomainHistory strDName
End If


      
      
LoadDomains (strDName)

LoadNicHandle (strDName)

Assign_IPAddress (strDName)

mDName = strDName

TxtAccName = Format(TxtAccName, gstrAccountNoPad)

End Sub
'--------------------------------------------------------------------------------

'***************************************************************************
' Show domain detail that exist in the system for a specific Nichandle.    *
'***************************************************************************

Public Sub Show_Results_NHandle(ByVal strDName As String)
Dim rec As ADODB.Recordset
Dim strSQL As String

strSQL = "Select * From DomainHist where D_Name = " & CDBText(CmbSearchList.Text) '(StrDName) '(TxtDName)
strSQL = strSQL & "AND D_Status = " & CDBText("Deleted")


 DB.Execute strSQL, rec

If Not rec.EOF Then
     TempChangeDate = NoNull(rec.Fields("Chng_Dt"))
    LoadDomainHistory strDName
       
End If



LoadDomains (strDName)
           
         
LoadNicHandle (strDName)

Assign_IPAddress (strDName)
  mDName = strDName
 'LoadDomainHistory strDName
 TxtAccName = Format(TxtAccName, gstrAccountNoPad)

End Sub

'***************************************************************************
' Show domain detail that exist in the system for a specific Account no    *
'***************************************************************************
Private Sub Show_Results_Acc(ByVal strDName As String)
Dim rec As ADODB.Recordset
Dim strSQL As String

        strSQL = "Select * From DomainHist where D_Name = " & CDBText(CmbSearchList.Text)
        strSQL = strSQL & "AND D_Status = " & CDBText("Deleted")
        DB.Execute strSQL, rec

If Not rec.EOF Then
    TempChangeDate = NoNull(rec.Fields("Chng_Dt"))
    LoadDomainHistory strDName
      
End If
    

      
LoadDomains (strDName)

LoadNicHandle (strDName)

Assign_IPAddress (strDName)

mDName = strDName

TxtAccName = Format(TxtAccName, gstrAccountNoPad)


End Sub

Private Sub LoadDomains(ByVal strDName As String, Optional ByVal strBatch As String = "")

Dim rec As ADODB.Recordset
Dim strSQL As String


    strSQL = "SELECT * FROM DomainHist "
    strSQL = strSQL & "WHERE D_Name =" & CDBText(strDName) & "  "
    strSQL = strSQL & "AND D_Status = " & CDBText("Deleted")
    If strBatch <> "" Then
        strSQL = strSQL & "AND Chng_NH = " & CDBText("BATCH")
    End If
    strSQL = strSQL & "ORDER BY Chng_Dt DESC"
    
    
    DB.Execute strSQL, rec
        If Not rec.EOF Then
            
            TxtDName.Text = rec.Fields("D_Name")
            TxtDHolder.Text = NoNull(rec.Fields("D_Holder"))
            TxtClass.Text = NoNull(rec.Fields("D_Class"))
            TxtCategory.Text = NoNull(rec.Fields("D_category"))
            TxtAccName.Text = Format(rec.Fields("A_Number"), gstrAccountNoPad)
            ComboStatus.Text = NoNull(rec.Fields("D_Status"))
            TxtChanged.Text = NoNull(rec.Fields("D_Status_Dt"))
            TxtRegDate.Text = NoNull(rec.Fields("D_Reg_Dt"))
            TxtRenewDate.Text = NoNull(rec.Fields("D_Ren_Dt"))
            TxtRemark.Text = NoNull(rec.Fields("D_Remark"))
            TxtRemark.Text = Replace(TxtRemark.Text, "%", vbCrLf)
            TxtBillStatus.Text = NoNull(rec.Fields("D_Bill_Status"))
            TxtVATStatus.Text = NoNull(rec.Fields("D_Vat_Status"))
            TxtClikPay.Text = NoNull(rec.Fields("D_ClikPaid"))
'-----------------------------------------------------------------------
        End If
              
        TxtRegDate.Text = Format(TxtRegDate, "dd/mm/yyyy")
        TxtRenewDate.Text = Format(TxtRenewDate, "dd/mm/yyyy")
        TxtChanged.Text = Format(TxtChanged, "dd/mm/yyyy")
 '---------------------------------------------------------------
    strAcNo = TxtAccName
'    Assign_Remark
   ' FillDNSNameComboE 30/05
    FillDNSNameCombo
   
   
   
End Sub
Private Sub Assign_Remark()
  
         Set TxtRemark.DataSource = RsSearchResults
         TxtRemark = Replace(TxtRemark.Text, "%", vbCrLf)

End Sub

Private Sub LoadNicHandle(ByVal strDName As String, Optional ByVal TempChangDate As String = "")

Dim strSQL As String
Dim rec As ADODB.Recordset
Dim txtContact As String


   
    strSQL = "SELECT D_Name, Contact_NH, Type, Chng_Dt FROM ContactHist "
    strSQL = strSQL & "WHERE D_Name =" & CDBText(strDName) & "  "
    If TempChangeDate <> "" Then
       strSQL = strSQL & "AND Chng_Dt=" & CDBTime(TempChangeDate) & " "
    End If
    DB.Execute strSQL, rec
            txtContact = ""
            TxtAdCon1.Text = ""
            TxtBilCon.Text = ""
            TxtTechCon.Text = ""
            TxtAdCon2.Text = ""
            TxtAdCon1.ToolTipText = ""
            TxtBilCon.ToolTipText = ""
            TxtTechCon.ToolTipText = ""
            TxtAdCon2.ToolTipText = ""

    If Not rec.EOF Then
         While Not rec.EOF
            Select Case rec.Fields("Type").Value
            Case "A"
                txtContact = rec.Fields("Contact_NH").Value

If TxtAdCon1.Text = "" Then '20060424 Admin contacts are now all "A"; not "A"&"2"
                TxtAdCon1.Text = UCase(txtContact)
                TxtAdCon1.ToolTipText = GetName(TxtAdCon1.Text)
Else '20060424 Admin contacts are now all "A"; not "A"&"2"
                TxtAdCon2.Text = UCase(txtContact) '20060424 Admin contacts are now all "A"; not "A"&"2"
                TxtAdCon2.ToolTipText = GetName(TxtAdCon2.Text) '20060424 Admin contacts are now all "A"; not "A"&"2"
End If '20060424 Admin contacts are now all "A"; not "A"&"2"

''Celina Leong 02/05/03 code for future used
''If more then one type "A" fill the second one into Admin Contact 2
''                TxtTypeA = rec.Fields("Contact_NH").Value
''                If TxtAdCon1.Text = "" Then
''                   TxtAdCon1.Text = UCase(TxtTypeA)
''                   TxtAdCon1.ToolTipText = GetName(TxtAdCon1.Text)
''                Else
''                If TxtTypeA <> TxtAdCon1.Text Then
''                   TxtAdCon2.Text = UCase(TxtTypeA)
''                   TxtAdCon2.ToolTipText = GetName(TxtAdCon2.Text)
''                   strAdCon2 = TxtAdCon2.Text
''                End If
''                End If
''--------------------------
            Case "B"
                txtContact = rec.Fields("Contact_NH").Value
                TxtBilCon.Text = UCase(txtContact)
                TxtBilCon.ToolTipText = GetName(TxtBilCon.Text)
            Case "T"
                txtContact = rec.Fields("Contact_NH").Value
                TxtTechCon.Text = UCase(txtContact)
                TxtTechCon.ToolTipText = GetName(TxtTechCon.Text)
            Case "2"
                txtContact = rec.Fields("Contact_NH").Value
                TxtAdCon2.Text = UCase(txtContact)
                TxtAdCon2.ToolTipText = GetName(TxtAdCon2.Text)
            End Select
            
            rec.MoveNext
         
       Wend
        
    Else
            TxtAdCon1.Text = ""
            TxtAdCon2.Text = ""
            TxtTechCon.Text = ""
            TxtBilCon.Text = ""
       
    End If
      
   
End Sub
Private Sub Assign_IPAddress(ByVal strDName As String, Optional ByVal TempChangeDate As String = "")

Dim strSQL As String
Dim rec As ADODB.Recordset

    
    strSQL = "SELECT * FROM DNSHist "
    strSQL = strSQL & "WHERE D_Name =" & CDBText(strDName) & "  "
    strSQL = strSQL & "ORDER BY Chng_Dt DESC"
   
    If TempChangeDate <> "" Then
        strSQL = strSQL & "AND Chng_Dt = " & CDBTime(TempChangeDate)
    End If
    
    DB.Execute strSQL, rec
    ComboDNSName.Clear
    TxtDNSIPAdd.Text = ""

  While Not rec.EOF
        ComboDNSName.AddItem rec.Fields("DNS_Name")
        If rec.EOF = False Then
            rec.MoveNext
        End If
        
  Wend
  
 
  If ComboDNSName.Listcount > 0 Then
     ComboDNSName.ListIndex = 0
  End If


End Sub

'***************************************************************************
'* Enables/Disables command buttons                                        *
'***************************************************************************
Private Sub ShowButtons(ByVal blnAlStatus As Boolean, _
                        ByVal blnTraAcc As Boolean, _
                        ByVal blnEdit As Boolean, _
                        ByVal blnCancel As Boolean, _
                        ByVal blnSave As Boolean, _
                        ByVal blnClose As Boolean, _
                        ByVal blnDeleteDNSName As Boolean)
                       ' Celina Leong Phoenix1.1 28/03/03 Insert blndelete

Dim blnxferAllowed  As Boolean
Dim blnViewAllowed  As Boolean
Dim blnEditAllowed As Boolean
Dim blnDeleteAllowed As Boolean ' Celina Leong Phoenix1.1 28/03/03


blnEditAllowed = HasAuthority(atLeadHostMaster + atSuperUser)
blnxferAllowed = HasAuthority(atHostMaster + atLeadHostMaster + atSuperUser)
blnViewAllowed = HasAuthority(atSuperUser + atAccounts + atHostMaster + atLeadHostMaster)
 CmdAlStatus.Enabled = blnAlStatus And blnViewAllowed
 CmdTraAC.Enabled = blnTraAcc And blnxferAllowed 'blnxferAllowed
 CmdEdit.Enabled = blnEdit And blnEditAllowed
 CmdCancel.Enabled = blnCancel
 CmdSave.Enabled = blnSave And blnViewAllowed
 CmdClose.Enabled = blnClose
 CmbSearchList.Enabled = Not blnSave
 'TxtScrh.Enabled = Not blnSave
 ' Celina Leong Phoenix1.1 28/03/03
 blnDeleteAllowed = HasAuthority(atLeadHostMaster + atSuperUser)
 CmdDelDNSName.Enabled = blnDeleteDNSName
 
 '---------------------------------------------
 If blnSave And blnEditAllowed And blnxferAllowed And blnViewAllowed Then CmdSave.Default = True
 If blnCancel And blnEditAllowed And blnxferAllowed And blnViewAllowed Then CmdCancel.Cancel = True
''   If blnCancel And blnViewAllowed Then CmdCancel.Cancel = True
 
 
End Sub
'-----------------------------------------------------------------------
''''Not Use in DomainHis

'''''Celina Leong 18-09-2002
'''''Send mail to Old Billing Contact
''''
''''Private Sub MailToOldBilCon(ByRef blnError As Boolean)
'''' Dim strEmail  As String
'''' Dim strHeader As String
'''' Dim strBody   As String
'''' Dim strFooter As String
''''
'''' On Error GoTo MailSendERROR
''''
'''' strEmail = GetEmail(strOldBilCon)
''''
'''''---------------------------------------------------------------------------------------------------------------
''''' strHeader = "PLEASE DO NOT REPLY TO THIS EMAIL.  THIS IS AN AUTOMATED EMAIL. " & vbCrLf & vbCrLf
'''''' strHeader = strHeader & "Please Note Billing Contact : " & UCase(strOldBilCon) & vbCrLf
'''''' strHeader = strHeader & "has been transferred to Billing Contact : " & UCase(TxtBilCon) & vbCrLf
'''''' strHeader = strHeader & "and Account No Transfer From : " & Format(strAcNo, gstrAccountNoPad) & _
''''''                                       " To : " & Format(TxtAccName, gstrAccountNoPad) & vbCrLf & vbCrLf
''''' strHeader = strHeader & "Please Note Domain Name : " & TxtDName & vbCrLf
''''' strHeader = strHeader & "Billing Contact : " & UCase(strOldBilCon) & _
'''''                         " has been transferred to Billing Contact : " & UCase(TxtBilCon) & vbCrLf
''''' strHeader = strHeader & "and Account No Transfer From : " & Format(strAcNo, gstrAccountNoPad) & _
'''''                                       " To : " & Format(TxtAccName, gstrAccountNoPad) & vbCrLf & vbCrLf
'''''
''''' strFooter = vbCrLf & vbCrLf & "Kind regards" & vbCrLf & vbCrLf & "IE Domain Registry Hostmaster"
'''''
'''''
'''''  strBody = strBody & "Remarks : " & TxtRemark & vbCrLf & vbCrLf
''''
'''''Celina Leong - Phoenix1.1 27/03/03
'''''Change wording for transfering account
''''
'''' strHeader = "PLEASE DO NOT REPLY TO THIS E-MAIL.  THIS IS AN AUTOMATED E-MAIL. " & vbCrLf & vbCrLf
'''' strHeader = strHeader & "Please Note Domain Name : " & TxtDName & " held by " & TxtDHolder & vbCrLf
'''' strHeader = strHeader & "Billing Contact : " & UCase(strOldBilCon) & _
''''                         " has been transferred to Billing Contact : " & UCase(TxtBilCon) & "." & vbCrLf & vbCrLf
''''' strHeader = strHeader & "and Account No Transfer From : " & Format(strAcNo, gstrAccountNoPad) & _
'''''                                       " To : " & Format(TxtAccName, gstrAccountNoPad) & vbCrLf & vbCrLf
''''
'''' strFooter = vbCrLf & vbCrLf & "Kind regards," & vbCrLf & vbCrLf & "IE Domain Registry Hostmaster"
''''
'''' strBody = strBody & "If the nameserver entries are to be updated, the administrative contact " & vbCrLf
'''' strBody = strBody & "or billing contact for this domain should logon to the online modification " & vbCrLf
'''' strBody = strBody & "generator on our website with their current user ID and password to make " & vbCrLf
'''' strBody = strBody & "any necessary changes. " & vbCrLf & vbCrLf
''''
 
''''
'''''-----------------------------------------------------------------------------------------------------------------
''''
'''' FrmSendMail.SendMail blnError, _
''''                      DB.StaticData(ustSmtp), _
''''                      DB.StaticData(ustSmtpPort), _
''''                      strEmail, _
''''                      DB.StaticData(ustReplyAddr), _
''''                      "Account Transfer Accepted : " & TxtDName, _
''''                      strHeader & strBody & strFooter
''''
'''' blnError = False
''''Exit Sub
''''MailSendERROR:
'''' MsgBox "Err No:   " & Err.Number & vbCrLf & _
''''        "Err Desc: " & Err.Description, vbCritical
''''
'''' blnError = True
''''End Sub

''''Private Sub MailToNewBilCon(ByRef blnError As Boolean)
'''' Dim strEmail  As String
'''' Dim strHeader As String
'''' Dim strBody   As String
'''' Dim strFooter As String
''''
'''' On Error GoTo MailSendERROR
''''
'''' strEmail = GetEmail(TxtBilCon)
'''''---------------------------------------------------------------------------------------------------------
''''' strHeader = "PLEASE DO NOT REPLY TO THIS EMAIL.  THIS IS AN AUTOMATED EMAIL. " & vbCrLf & vbCrLf
''''' strHeader = strHeader & "Please Note Domain Name : " & TxtDName & vbCrLf
''''' strHeader = strHeader & "Billing Contact : " & UCase(strOldBilCon) & _
'''''                         " has been transferred to Billing Contact : " & UCase(TxtBilCon) & vbCrLf
''''' strHeader = strHeader & "and Account No Transfer From : " & Format(strAcNo, gstrAccountNoPad) & _
'''''                                       " To : " & Format(TxtAccName, gstrAccountNoPad) & vbCrLf & vbCrLf
'''''
''''' strFooter = vbCrLf & vbCrLf & "Kind regards" & vbCrLf & vbCrLf & "IE Domain Registry Hostmaster"
'''''
'''''
''''' strBody = strBody & "Remarks : " & txtRemark & vbCrLf & vbCrLf
''''
'''''Celina Leong - Phoenix1.1 27/03/03
'''''Change wording for transfering account
''''
'''' strHeader = "PLEASE DO NOT REPLY TO THIS E-MAIL.  THIS IS AN AUTOMATED E-MAIL. " & vbCrLf & vbCrLf
'''' strHeader = strHeader & "Please Note Domain Name : " & TxtDName & " held by " & TxtDHolder & vbCrLf
'''' strHeader = strHeader & "Billing Contact : " & UCase(strOldBilCon) & _
''''                         " has been transferred to Billing Contact : " & UCase(TxtBilCon) & "." & vbCrLf & vbCrLf
''''' strHeader = strHeader & "and Account No Transfer From : " & Format(strAcNo, gstrAccountNoPad) & _
'''''                                       " To : " & Format(TxtAccName, gstrAccountNoPad) & vbCrLf & vbCrLf
''''
'''' strFooter = vbCrLf & vbCrLf & "Kind regards," & vbCrLf & vbCrLf & ".IE Domain Registry Hostmaster"
''''
''''
'''' strBody = strBody & "If the nameserver entries are to be updated, the administrative contact " & vbCrLf
'''' strBody = strBody & "or billing contact for this domain should logon to the online modification " & vbCrLf
'''' strBody = strBody & "generator on our website with their current user ID and password to make " & vbCrLf
'''' strBody = strBody & "any necessary changes. " & vbCrLf & vbCrLf
''''
'''' '------------------------------------------------------------------------------------------------------------
'''' FrmSendMail.SendMail blnError, _
''''                      DB.StaticData(ustSmtp), _
''''                      DB.StaticData(ustSmtpPort), _
''''                      strEmail, _
''''                      DB.StaticData(ustReplyAddr), _
''''                      "Account Transfer Accepted : " & TxtDName, _
''''                      strHeader & strBody & strFooter
''''
'''' blnError = False
''''Exit Sub
''''MailSendERROR:
'''' MsgBox "Err No:   " & Err.Number & vbCrLf & _
''''        "Err Desc: " & Err.Description, vbCritical
''''
'''' blnError = True
''''End Sub
'-----------------------------------------------------------------------
''''Not Use in DomainHis
''''Private Sub UpdateDN(ByRef blnError As Boolean)
''''Dim ctl As Control
''''Dim lngRowsUpdated As Long
''''
''''    On Error GoTo EXE_Err
''''
''''If ChangedControls(Me) Then
''''   ' DB.BeginTransaction
'''''-----------------------------------------------------
'''''    sSQL = "UPDATE Domain " & _
'''''            "SET D_Name=" & CDBText(UCase(TxtDName)) & ",  " & _
'''''            "D_Holder=" & CDBText(UCase(TxtDHolder)) & ",  " & _
'''''            "A_Number=" & TxtAccName & ",  " & _
'''''            "D_Class=" & CDBText(ComboClass) & " ," & _
'''''            "D_Category=" & CDBText(ComboCategory) & "," & _
'''''            "D_Status=" & CDBText(ComboStatus) & " , " & _
'''''            "D_Reg_Dt=" & CDBDate(TxtRegDate) & "," & _
'''''            "D_Ren_Dt=" & CDBDate(TxtRenewDate) & "," & _
'''''            "D_Remark=" & CDBText(TxtRemark) & " " & _
'''''            "WHERE D_Name=" & CDBText(TxtDName) & "" & _
'''''            "AND D_TStamp = " & CDBTimeStamp(mstrTimeStamp) & " "
''''
'''''David O'Leary - 09-September-2002
'''''Change the SQL String so that information is not
'''''converted to upper case before being saved.
''''
''''
'''''    sSQL = "UPDATE Domain " & _
'''''            "SET D_Name=" & CDBText(TxtDName) & ",  " & _
'''''            "D_Holder=" & CDBText(TxtDHolder) & ",  " & _
'''''            "A_Number=" & TxtAccName & ",  " & _
'''''            "D_Class=" & CDBText(ComboClass) & " ," & _
'''''            "D_Category=" & CDBText(ComboCategory) & "," & _
'''''            "D_Status=" & CDBText(ComboStatus) & " , " & _
'''''            "D_Reg_Dt=" & CDBDate(TxtRegDate) & "," & _
'''''            "D_Ren_Dt=" & CDBDate(TxtRenewDate) & "," & _
'''''            "D_Remark=" & CDBText(TxtRemark) & " " & _
'''''            "WHERE D_Name=" & CDBText(TxtDName) & "" & _
'''''            "AND D_TStamp = " & CDBTimeStamp(mstrTimeStamp) & " "
''''
'''''-----------------------------------------------------
''''
'''''Celina Leong - Phoenix1.1 26/03/03
'''''Change the SQL String to included D_Bill_Status,D_VAT_Status and D_ClikPaid
''''
'''''Celina Leong Phoenix1.1 11/04/03
'''''Update all History before Update
''''
''''
''''   WriteHistory_Domain blnError
''''   WriteDNS blnError
''''   WriteNichandle blnError
''''
''''   sSQL = "UPDATE Domain " & _
''''            "SET D_Name=" & CDBText(TxtDName) & ",  " & _
''''            "D_Holder=" & CDBText(TxtDHolder) & ",  " & _
''''            "A_Number=" & TxtAccName & ",  " & _
''''            "D_Class=" & CDBText(ComboClass) & " ," & _
''''            "D_Category=" & CDBText(ComboCategory) & "," & _
''''            "D_Status=" & CDBText(ComboStatus) & " , " & _
''''            "D_Reg_Dt=" & CDBDate(TxtRegDate) & "," & _
''''            "D_Ren_Dt=" & CDBDate(TxtRenewDate) & "," & _
''''            "D_Bill_Status=" & CDBText(ComboBillStatus) & " , " & _
''''            "D_Vat_Status=" & CDBText(ComboVATStatus) & ", " & _
''''            "D_Remark=" & CDBText(TxtRemark) & ", " & _
''''            "D_ClikPaid=" & CDBText(ComboClikPay) & " " & _
''''            "WHERE D_Name=" & CDBText(TxtDName) & "" & _
''''            "AND D_TStamp = " & CDBTimeStamp(mstrTimeStamp) & " "
''''
'''''-----------------------------------------------------------------------
''''    DB.Execute sSQL ' , , , , lngRowsUpdated
''''
''''
''''
''''  ''' WriteHistory_Domain 'Celina Leong Phoenix1.1 write history before update
''''
''''
''''
''''
'''''    If lngRowsUpdated <> 1 Then
'''''        DB.RollbackTransaction
'''''        MsgBox "Could not Update Domain.", vbExclamation
'''''        'MsgBox "Could not Update Status Domain." & vbCrLf & vbCrLf & "Domain updated by another user while you were editing it", vbExclamation
'''''    Else
''''
'''''       DB.CommitTransaction
'''''       WriteHistory_Domain
'''''    End If
''''End If
''''
''''      '   MsgBox "Changes saved successfully. "
''''
''''Exit Sub
''''
''''EXE_Err:
''''
''''
''''
''''
'''' If Err.Number = glngDuplicateKey Then
''''    MsgBox "Duplicate key ", vbExclamation
'''' Else
''''        DB.ShowError "Unable to Update Domain"
'''' End If
''''
''''
'''' blnError = True
''''
''''
''''End Sub
''''Private Sub Update_Nichandle(ByRef blnError As Boolean)
''''
'''' Dim strSQL As String
'''' Dim FieldChng As Boolean
'''' Dim rec As ADODB.Recordset
''''
''''    If TxtAdCon1.DataChanged = True Then
''''            TempType = "A"
''''          '  TempNichandle = TxtAdCon1 ' Phoenix1.1
''''
''''            sSQL = "UPDATE Contact " & _
''''                    "SET Contact_NH=" & CDBText(UCase(TxtAdCon1)) & " " & _
''''                    "Where D_name=" & CDBText(TxtDName) & " " & _
''''                    "AND type=" & CDBText(TempType) & " "
''''
''''             CnPhoenix.Execute sSQL
''''            ' WriteHistory_Contact ' Celina Leong Phoenix1.1 already write history
''''
''''    End If
''''
''''
''''    If TxtTechCon.DataChanged = True Then
''''            TempType = "T"
'''''            TempNichandle = TxtTechCon ' Phoenix1.1
''''
''''                sSQL = "UPDATE Contact " & _
''''                       "SET Contact_NH=" & CDBText(UCase(TxtTechCon)) & " " & _
''''                       "Where D_name=" & CDBText(TxtDName) & " " & _
''''                        "AND type=" & CDBText(TempType) & " "
''''                      ' "AND type='T'"
''''
''''                CnPhoenix.Execute sSQL
'''''                WriteHistory_Contact ' Celina Leong Phoenix1.1 already write history
''''
''''    End If
''''
''''    If TxtAdCon2.DataChanged = True Then
''''
''''        If TxtAdCon2.Text <> "" Then
''''
''''            TempType = "2"
''''            'TempNichandle = TxtAdCon2 ' Phoenix1.1
''''            If strAdCon2 = "" Then
''''
''''                   strSQL = "INSERT INTO Contact("
''''                   strSQL = strSQL & "D_Name, "
''''                   strSQL = strSQL & "Contact_NH, "
''''                   strSQL = strSQL & "Type)"
''''
''''                   strSQL = strSQL & "VALUES( "
''''                   strSQL = strSQL & CDBText(TxtDName) & ", "
''''                   strSQL = strSQL & CDBText(UCase(TxtAdCon2)) & ", "
''''                   strSQL = strSQL & CDBText(TempType) & "); "
''''
''''                   DB.Execute strSQL
'''''                   WriteHistory_Contact ' Celina Leong Phoenix1.1 already write history
''''            Else
''''                    TempType = "2"
'''''                    TempNichandle = TxtAdCon2 ' Phoenix1.1
''''                     sSQL = "UPDATE Contact " & _
''''                           "SET Contact_NH=" & CDBText(UCase(TxtAdCon2)) & "" & _
''''                           "Where D_name=" & CDBText(TxtDName) & " " & _
''''                            "AND type=" & CDBText(TempType) & " "
''''                           '"AND type='Z'"
''''
''''                    CnPhoenix.Execute sSQL
'''''                    WriteHistory_Contact ' Celina Leong Phoenix1.1 already write history
''''
''''            End If
''''        Else
''''                   TempType = "2"
'''''                   TempNichandle = strAdCon2 ' Phoenix1.1
'''''                   WriteHistory_Contact  ' Celina Leong Phoenix1.1 already write history
''''                   sSQL = "Delete From Contact " & _
''''                          "Where D_name=" & CDBText(TxtDName) & " " & _
''''                          "AND type=" & CDBText(TempType) & " "
''''                           '"AND type='Z'"
''''
''''
''''                    CnPhoenix.Execute sSQL
''''
''''
''''        End If
''''
''''
''''    End If
''''
''''     If TxtBilCon.DataChanged = True And AcDataChanged = True Then
''''            TempType = "B"
'''''            TempNichandle = TxtBilCon ' Phoenix1.1
''''            sSQL = "UPDATE Contact " & _
''''                   "SET Contact_NH=" & CDBText(UCase(TxtBilCon)) & " " & _
''''                   "Where D_name=" & CDBText(TxtDName) & " " & _
''''                    "AND type=" & CDBText(TempType) & " "
''''                  ' "AND type='T'"
''''
''''            CnPhoenix.Execute sSQL
'''''            WriteHistory_Contact ' Celina Leong Phoenix1.1 already write history
'''''Celina Leong Phoenix1.1 11/04/03
'''''Update Admin Contact if transfer to a new account
''''            TempAdmin1 = TxtAdCon1
''''            UpdateAdminContact blnError
''''            If TxtAdCon2 <> "" Then
''''                TempAdmin = TxtAdCon2
''''                UpdateAdminContact blnError
''''            End If
''''
'''''Celina Leong 18-09-2002
'''''Update creator NicHandle when billing contact changed
''''             TempType = "C"
'''''             TempNichandle = TxtBilCon ' Phoenix1.1
''''
''''             strSQL = "SELECT Type FROM Contact "
''''             strSQL = strSQL & "WHERE D_Name=" & CDBText(TxtDName) & " "
''''             'strSQL = strSQL & "AND Contact_NH=" & CDBText(TxtBilCon) & ""
''''             strSQL = strSQL & "AND Type= " & CDBText(TempType) & " "
''''             DB.Execute strSQL, rec
''''
''''
''''                If rec.EOF Then
''''
''''                    strSQL = "INSERT INTO Contact("
''''                    strSQL = strSQL & "D_Name, "
''''                    strSQL = strSQL & "Contact_NH, "
''''                    strSQL = strSQL & "Type)"
''''
''''                    strSQL = strSQL & "VALUES( "
''''                    strSQL = strSQL & CDBText(TxtDName) & ", "
''''                    strSQL = strSQL & CDBText(UCase(TxtBilCon)) & ", "
''''                    strSQL = strSQL & CDBText(TempType) & "); "
''''
''''                   DB.Execute strSQL
'''''                   WriteHistory_Contact  ' Celina Leong Phoenix1.1 already write history
''''
''''                Else
'''''                    TempNichandle = TxtBilCon ' Phoenix1.1
''''                     sSQL = "UPDATE Contact " & _
''''                            "SET Contact_NH=" & CDBText(UCase(TxtBilCon)) & " " & _
''''                            "Where D_name=" & CDBText(TxtDName) & " " & _
''''                            "AND Type=" & CDBText(TempType) & " "
''''
''''                    CnPhoenix.Execute sSQL
'''''                    WriteHistory_Contact  ' Celina Leong Phoenix1.1 already write history
''''
''''
''''                End If
''''
''''                MailToOldBilCon blnError
''''                MailToNewBilCon blnError
''''
''''    End If
''''
''''End Sub
'-----------------------------------------------------------------------
''''Not Use in DomainHis
''''Private Sub Update_DNS(ByRef blnError As Boolean)
''''Dim Counter As Integer
''''Dim strSQL As String
''''Dim Listcount As Long
''''Dim Listcnt As Long
''''Dim Count As Integer
''''
''''
''''Listcount = ComboDNSNameE.Listcount - 1
''''
''''For Counter = 1 To Listcount
''''
''''
''''    If ComboDNSNameE.ItemData(Counter) = 2 Then
''''
''''         DNSOrder = GetNext
''''
'''''-----------------------------------------------------
'''''David O'Leary - 09-September-2002
'''''All SQL Strings below have been changed so that data
'''''is not converted into upper case when being saved
'''''-----------------------------------------------------
'''''                 strSQL = "INSERT INTO DNS("
'''''                 strSQL = strSQL & "D_Name, "
'''''                 strSQL = strSQL & "DNS_Name, "
'''''                 strSQL = strSQL & "DNS_IpAddr,"
'''''                 strSQL = strSQL & "DNS_Order) "
'''''                 strSQL = strSQL & "VALUES( "
'''''                 strSQL = strSQL & CDBText(UCase(TxtDName.Text)) & ","
'''''                 strSQL = strSQL & CDBText(UCase(ComboDNSNameE.List(Counter))) & ","
'''''                 strSQL = strSQL & CDBText(ListIPAdd.List(Counter)) & ","
'''''                 strSQL = strSQL & CDBText(DNSOrder) & ");"
''''
''''                 strSQL = "INSERT INTO DNS("
''''                 strSQL = strSQL & "D_Name, "
''''                 strSQL = strSQL & "DNS_Name, "
''''                 strSQL = strSQL & "DNS_IpAddr,"
''''                 strSQL = strSQL & "DNS_Order) "
''''                 strSQL = strSQL & "VALUES( "
''''                 strSQL = strSQL & CDBText(TxtDName.Text) & ","
''''                 strSQL = strSQL & CDBText(ComboDNSNameE.List(Counter)) & ","
''''                 strSQL = strSQL & CDBText(ListIPAdd.List(Counter)) & ","
''''                 strSQL = strSQL & CDBText(DNSOrder) & ");"
''''
''''               '  strsql = strsql & CDBText(TempDNS_Order) & ");"
''''
''''                DB.Execute strSQL
''''               ' WriteHistory_DNS
''''
'''''-----------------------------------------------------
''''
'''''                strSQL = "INSERT INTO DNSHist("
'''''                strSQL = strSQL & "D_Name, "
'''''                strSQL = strSQL & "DNS_Name, "
'''''                strSQL = strSQL & "DNS_IpAddr, "
'''''                strSQL = strSQL & "DNS_Order, "
'''''                strSQL = strSQL & "Chng_NH, "
'''''                strSQL = strSQL & "Chng_Dt) "
'''''
'''''                strSQL = strSQL & "VALUES( "
'''''                strSQL = strSQL & CDBText(TxtDName) & ", "
'''''                strSQL = strSQL & CDBText(UCase(ComboDNSNameE.List(Counter))) & ", "
'''''                strSQL = strSQL & CDBText(ListIPAdd.List(Counter)) & ", "
'''''                strSQL = strSQL & CDBText(DNSOrder) & ","
'''''                strSQL = strSQL & CDBText(UserID) & ", "
'''''                strSQL = strSQL & CDBTime(TempNow) & "); "
''''''Celina Leong Phoenix1.1 14/04/03
''''''                strSQL = "INSERT INTO DNSHist("
''''''                strSQL = strSQL & "D_Name, "
''''''                strSQL = strSQL & "DNS_Name, "
''''''                strSQL = strSQL & "DNS_IpAddr, "
''''''                strSQL = strSQL & "DNS_Order, "
''''''                strSQL = strSQL & "Chng_NH, "
''''''                strSQL = strSQL & "Chng_Dt) "
''''''
''''''                strSQL = strSQL & "VALUES( "
''''''                strSQL = strSQL & CDBText(TxtDName) & ", "
''''''                strSQL = strSQL & CDBText(ComboDNSNameE.List(Counter)) & ", "
''''''                strSQL = strSQL & CDBText(ListIPAdd.List(Counter)) & ", "
''''''                strSQL = strSQL & CDBText(DNSOrder) & ","
''''''                strSQL = strSQL & CDBText(UserID) & ", "
''''''                strSQL = strSQL & CDBTime(TempNow) & "); "
''''
'''''-----------------------------------------------------
''''
''''''                DB.Execute strSQL
''''''-----------------------------------------------------
''''     End If
''''
''''
''''
''''     If ComboDNSNameE.ItemData(Counter) = 1 Then
''''
''''
''''       strSQL = "UPDATE DNS SET "
''''       strSQL = strSQL & "DNS_Name=" & CDBText(ComboDNSNameE.List(Counter)) & ", "
''''       strSQL = strSQL & "DNS_IpAddr=" & CDBText(ListIPAdd.List(Counter)) & " "
''''       strSQL = strSQL & "Where D_Name=" & CDBText(TxtDName) & " "
''''       strSQL = strSQL & "AND DNS_Name=" & CDBText(ComboDNSName.List(Counter - 1)) & " "
''''
''''
''''
''''       DB.Execute strSQL
''''   '    WriteHistory_DNS
''''
''''''Celina Leong Phoenix1.1 14/04/03
''''
''''''         strSQL = "INSERT INTO DNSHist("
''''''            strSQL = strSQL & "D_Name, "
''''''            strSQL = strSQL & "DNS_Name, "
''''''            strSQL = strSQL & "DNS_IpAddr, "
''''''            strSQL = strSQL & "DNS_Order, "
''''''            strSQL = strSQL & "Chng_NH, "
''''''            strSQL = strSQL & "Chng_Dt) "
''''''
''''''            strSQL = strSQL & "VALUES( "
''''''            strSQL = strSQL & CDBText(TxtDName) & ", "
''''''            strSQL = strSQL & CDBText(ComboDNSNameE.List(Counter)) & ", "
''''''            strSQL = strSQL & CDBText(ListIPAdd.List(Counter)) & ", "
''''''            strSQL = strSQL & ListIPAdd.ItemData(Counter) & ","
''''''            strSQL = strSQL & CDBText(UserID) & ", "
''''''            strSQL = strSQL & CDBTime(TempNow) & "); "
''''
''''
'''''-----------------------------------------------------
''''
''''''            DB.Execute strSQL
''''''-------------------------------------------------------------------------
''''
''''
''''    End If
''''Next Counter
''''
''''    If mblnDeleteDNS And Not ListDNSname.Listcount = 0 Then
''''           Delete_DNS blnError
''''    End If
'''''Delete_DNS blnError
''''' 'Celina Leong Phoenix1.1 01/04/03
'''''Listcnt = ListDNSname.Listcount
'''''DB.BeginTransaction
'''''For Count = 1 To Listcnt
'''''
'''''   If ListDNSname.ItemData(Count) = 1 Then
'''''
'''''       strSQL = "DELETE FROM DNS "
'''''       strSQL = strSQL & "WHERE DNS_Name=" & CDBText(ListDNSname.List(Count))
'''''
'''''       DB.Execute strSQL
'''''
'''''
''''''            strSQL = "INSERT INTO DNSHist("
''''''            strSQL = strSQL & "D_Name, "
''''''            strSQL = strSQL & "DNS_Name, "
''''''            strSQL = strSQL & "DNS_IpAddr, "
''''''            strSQL = strSQL & "DNS_Order, "
''''''            strSQL = strSQL & "Chng_NH, "
''''''            strSQL = strSQL & "Chng_Dt) "
''''''
''''''            strSQL = strSQL & "VALUES( "
''''''            strSQL = strSQL & CDBText(TxtDName) & ", "
''''''            strSQL = strSQL & CDBText(ComboDNSNameE.List(Counter)) & ", "
''''''            strSQL = strSQL & CDBText(ListIPAdd.List(Counter)) & ", "
''''''            strSQL = strSQL & ListIPAdd.ItemData(Counter) & ","
''''''            strSQL = strSQL & CDBText(UserID) & ", "
''''''            strSQL = strSQL & CDBTime(TempNow) & "); "
''''''
''''''
''''''
''''''
''''''            DB.Execute strSQL
'''''   End If
''''''------------------------------------------
'''''
'''''Next Count
''''
''''End Sub
Private Function GetNext() As Long
Dim strSQL As String
Dim rec As ADODB.Recordset
Dim DNSOdr As Long
Dim Listcount As Long
Dim Counter As Long
Dim TempOrder As Long

Listcount = ListIPAdd.Listcount - 1

For Counter = 0 To Listcount
    If TempOrder > ListIPAdd.ItemData(Counter) Then
       TempOrder = TempOrder + 1
    Else
       TempOrder = ListIPAdd.ItemData(Counter)
    End If
Next Counter

 GetNext = TempOrder

End Function


'-----------------------------------------------------------------------
''''Not Use in DomainHis
''''Private Sub UpdateStatus(ByRef blnError As Boolean)
''''Dim lngRowsUpdated As Long
''''Dim ctl As Control
''''Dim strSQL As String
''''
''''
''''  On Error GoTo EXE_Err
''''
''''If ChangedControls(Me) Then
'''''Celina Leong Phoenix1.1 11/04/03
''''''    DB.BeginTransaction
''''
''''    WriteHistory_Domain blnError
''''    WriteDNS blnError   ' Write history DNS
''''    WriteNichandle blnError 'Write history dns
'''''-------------------------------------------------
''''
''''
''''    strSQL = "UPDATE Domain SET "
''''    strSQL = strSQL & "D_Status=" & CDBText(ComboStatus) & ", "
''''    strSQL = strSQL & "D_Status_Dt=NOW(), "
''''    strSQL = strSQL & "D_Remark=" & CDBText(TxtRemark) & " "
''''    strSQL = strSQL & "WHERE D_Name=" & CDBText(TxtDName) & ""
''''    strSQL = strSQL & "AND D_TStamp=" & CDBTimeStamp(mstrTimeStamp) & " "
''''
''''
''''    DB.Execute strSQL ', , , , lngRowsUpdated 'Celina LEong Phoenix1.1 11/04/03
'''''''
'''''''
'''''''    If lngRowsUpdated <> 1 Then
'''''''        DB.RollbackTransaction
'''''''      ' MsgBox "Could not Update Status Domain." & vbCrLf & vbCrLf & "Possible reasons : Domain updated by another user while you were editing it", vbExclamation
'''''''        MsgBox "Could not Update Status Domain." & vbCrLf & vbCrLf & "Domain updated by another user while you were editing it", vbExclamation
'''''''    Else
'''''''        DB.CommitTransaction
''''''''        WriteHistory_Domain ' Celina Leong Phoenix1.1 already write history
'''''''    End If
'''''''----------------------------------------------------------
'''''Celina Leong - Phoenix1.1 27/03/03
'''''Add code to send mail to admin contact /billing contact when
''''' domain status change to 'suspended'  or 'deleted'.
''''
''''    If ComboStatus.Text = "Suspended" Then
''''         TempContact = TxtBilCon
''''         SendSuspendedMail blnError
''''         TempContact = TxtAdCon1
''''         SendSuspendedMail blnError
''''    End If
''''
''''    If ComboStatus.Text = "Deleted" Then
''''        TempContact = TxtBilCon
''''        SendDeletedMail blnError
''''        TempContact = TxtAdCon1
''''        SendDeletedMail blnError
''''    End If
'''''----------------------------------------------------------------------------------
''''
''''End If
''''
''''     For Each ctl In Controls
''''
''''                If TypeOf ctl Is TextBox Then
''''                    ctl.BackColor = &H8000000F
''''                    ctl.Enabled = True
''''                    ctl.Locked = False
''''                    ctl.BorderStyle = 0
'''''                    TxtScrh.BackColor = &H80000005 'Celina Leong Phoenix1.1
''''                    ComboSearch.BackColor = &H80000005
'''''                    TxtScrh.BorderStyle = 1 'Celina Leong Phoenix1.1
''''
''''                    TxtRemark.BorderStyle = 1
''''                End If
''''
''''                If TypeOf ctl Is ComboBox Then
''''                    ctl.BackColor = &H8000000F
''''                    ctl.Enabled = False
''''                    ctl.Locked = True
''''                    CmbSearchList.BackColor = &H80000005
''''                    CmbSearchList.Enabled = True
''''                    CmbSearchList.Locked = False
''''
''''
''''                End If
''''
''''                If TypeOf ctl Is CommandButton Then
''''                    ctl.Enabled = True
''''                    CmdCancel.Enabled = False
''''                    CmdSave.Enabled = False
''''                End If
''''            Next
''''
''''
''''
''''Exit Sub
''''
''''EXE_Err:
''''
''''
''''On Error Resume Next
''''
''''' If Err.Number = glngDuplicateKey Then
'''''    MsgBox "Duplicate key", vbExclamation
''''' Else
'''''        DB.ShowError "Unable to Update Status"
''''' End If
'''''Celina Leong Phoenix1.1 11/04/03
''''' If DB.TransactionLevel > 0 Then
'''''    DB.RollbackTransaction
''''' End If
'''''-----------------------------------------------------
'''' Error.Show_Error_DB " Unable to update status "
'''' blnError = True
''''
''''
''''End Sub
'''''-----------------------------------------------------------------------
''''Not Use in DomainHis
''''Private Sub UpdateTraAccount(ByRef blnError As Boolean)
''''  Dim ctl As Control
''''
''''     On Error GoTo EXE_Err
''''
''''  If ChangedControls(Me) Then
''''
''''  'Celina Leong Phoenix1.1 11/04/03
''''
''''    WriteHistory_Domain blnError
''''    WriteDNS blnError   ' Write history DNS
''''    WriteNichandle blnError 'Write history dns
'''''-------------------------------------------------
''''
'''''Celina leong 20-09-2002
''''
''''    If TxtAccName = "1" Or TxtAccName = "00000001" Then
''''         sSQL = "UPDATE Domain " & _
''''            "SET A_Number=" & TxtAccName & ",  " & _
''''            "D_Discount=" & CDBText(mNo) & ",  " & _
''''            "D_Bill_Status=" & CDBText(mYes) & ", " & _
''''            "D_Vat_Status=" & CDBText(TxtVATStatus) & ", " & _
''''            "D_Remark=" & CDBText(TxtRemark) & " " & _
''''            "WHERE D_Name=" & CDBText(TxtDName) & " " & _
''''            "AND D_TStamp = " & CDBTimeStamp(mstrTimeStamp) & " "
''''
''''
''''           CnPhoenix.Execute sSQL
''''
'''''           WriteHistory_Domain ' Celina Leong Phoenix1.1 already write history
''''
''''
''''           Update_NicHandle_Table blnError
''''
''''
''''    Else
''''
''''
''''        sSQL = "UPDATE Domain " & _
''''                "SET A_Number=" & TxtAccName & ",  " & _
''''                "D_Vat_Status=" & CDBText(TxtVATStatus) & ", " & _
''''                "D_Remark=" & CDBText(TxtRemark) & " " & _
''''                "WHERE D_Name=" & CDBText(TxtDName) & " " & _
''''                "AND D_TStamp = " & CDBTimeStamp(mstrTimeStamp) & " "
''''
''''        CnPhoenix.Execute sSQL
''''
''''
'''''       WriteHistory_Domain ' Celina Leong Phoenix1.1 already write history
''''      End If
''''    End If
''''
''''     For Each ctl In Controls
''''
''''                If TypeOf ctl Is TextBox Then
''''                    ctl.BackColor = &H8000000F
''''                    ctl.Enabled = True
''''                    ctl.Locked = False
''''                    ctl.BorderStyle = 0
'''''                    TxtScrh.BackColor = &H80000005 'Phoenix1.1
''''                    ComboSearch.BackColor = &H80000005
''''                   ' TxtScrh.BorderStyle = 1 ' Phoenix1.1
''''                    TxtRemark.BorderStyle = 1
''''                End If
''''
''''                If TypeOf ctl Is ComboBox Then
''''                    ctl.BackColor = &H8000000F
''''                    ctl.Enabled = False
''''                    ctl.Locked = True
''''                    CmbSearchList.BackColor = &H80000005
''''                    CmbSearchList.Enabled = True
''''                    CmbSearchList.Locked = False
''''
''''
''''                End If
''''
''''                If TypeOf ctl Is CommandButton Then
''''                    ctl.Enabled = True
''''                    CmdCancel.Enabled = False
''''                    CmdSave.Enabled = False
''''                End If
''''            Next
''''
''''
''''     '    MsgBox "Changes saved successfully. "
''''
''''Exit Sub
''''
''''EXE_Err:
''''
''''    'CnPhoenix.Execute "ROLLBACK TRANSACTION"
''''   '
''''   ' blnError = True
''''On Error Resume Next '?????????????
''''
'''' If Err.Number = glngDuplicateKey Then
''''    MsgBox "Duplicate key", vbExclamation
'''' Else
''''    DB.ShowError "Unable to Update Transfer Account"
'''' End If
''''
'''' 'If DB.TransactionLevel > 0 Then
'''' '   DB.RollbackTransaction
'''' 'End If
''''
'''' blnError = True
''''
''''
''''End Sub
'''''-----------------------------------------------------------------------
''''Not Use in DomainHis
''''
'''''Celina Leong 23-09-2002
'''''Update NicHandle Billing Contact Indicated if Transfer Account = 1
''''
''''
''''Private Sub Update_NicHandle_Table(ByRef blnError As Boolean)
''''
''''Dim strSQL         As String
''''Dim lngRowsUpdated As Long
''''
'''' On Error GoTo UpdateNichandleError
''''
'''' If blnError Then Exit Sub
'''' DB.BeginTransaction
''''   strSQL = "UPDATE NicHandle  SET "
''''   strSQL = strSQL & "Nic_Handle=" & CDBText(TxtBilCon) & ", "
''''   strSQL = strSQL & "NH_BillC_Ind=" & CDBText(mYes) & " , "
''''   strSQL = strSQL & "NH_Remark=" & CDBText(TxtRemark) & " "
''''   strSQL = strSQL & "WHERE Nic_Handle=" & CDBText(TxtBilCon) & ""
''''   'strSQL = strSQL & "AND NH_TStamp = " & CDBTimeStamp(mstrTimeStamp) & " "
''''
''''    DB.Execute strSQL, , , , lngRowsUpdated
''''
''''     If lngRowsUpdated <> 1 Then
''''          DB.RollbackTransaction
''''          'MsgBox "Could not Update Nic Handle." & vbCrLf & vbCrLf & "Possible reasons : Nic Handle updated by another user while you were editing it", vbExclamation
''''          MsgBox "Could not Update Nic Handle." & vbCrLf & vbCrLf & "Nic Handle updated by another user while you were editing it", vbExclamation
''''     Else
''''
''''         DB.CommitTransaction
''''         'WriteHistory_Nichandle  ' Celina Leong Phoenix1.1 already write history
''''    End If
''''
''''
''''Exit Sub
''''
''''UpdateNichandleError:
''''
''''On Error Resume Next
''''
''''If Err.Number = glngDuplicateKey Then
''''    MsgBox " Duplicate key ", vbExclamation
''''Else
''''    DB.ShowError "Unable to Update Nichandle "
''''End If
''''
'''' blnError = True
''''
''''
''''
''''
''''End Sub
''''
'''''* Validates input for Altering Status
'''''* Returns FALSE if any fields are invalid
''''
''''Private Function IsValidStatus() As Boolean
'''' Dim blnInError As Boolean
''''
'''' If TxtRemark.Text = "" Then
''''    blnInError = True
''''    FormatControl TxtRemark, csError
'''' End If
''''
''''
'''' IsValidStatus = Not blnInError
''''End Function
''''
'''''* Validates input - Returns FALSE if any fields are invalid
'''''* Checks all TextBoxes and ComboBoxes for blank entries but
'''''*   ignores any control with a Tag property of "B" or "X"
'''''* Does additional validation necessary on other fields
''''
''''Private Function IsValid() As Boolean
'''' Dim blnInError As Boolean
'''' Dim strSQL As String
'''' Dim rec As ADODB.Recordset
'''' Dim ctl As Control
''''' Celina Leong Phoenix1.1 28/03/03
'''' Dim IPDot%, Length%, I%
'''' Dim strDNS As String
'''''-------------------------------------
''''
''''
'''' If TxtRemark.Text = "" Then
''''    blnInError = True
''''    FormatControl TxtRemark, csError
'''' End If
''''
'''' If TxtDHolder.Text = "" Then
''''    blnInError = True
''''    FormatControl TxtDHolder, csError
'''' End If
''''
''''' If TxtAccName.Text = "" Then
'''''    blnInError = True
'''''    FormatControl TxtAccName, csError
''''' End If
''''
'''' If TxtRegDate.Text = "" Then
''''    blnInError = True
''''    FormatControl TxtRegDate, csError
'''' Else
''''    If Not IsDate(TxtRegDate.Text) Then
''''         blnInError = True
''''         FormatControl TxtRegDate, csError
''''
''''    End If
'''' End If
''''
'''' If TxtRenewDate.Text = "" Then
''''    blnInError = True
''''    FormatControl TxtRenewDate, csError
'''' Else
''''    If Not IsDate(TxtRenewDate.Text) Then
''''         blnInError = True
''''         FormatControl TxtRenewDate, csError
''''
''''    End If
''''
'''' End If
''''
'''' If TxtAdCon1.Text = "" Then
''''    blnInError = True
''''    FormatControl TxtAdCon1, csError
''''
'''' Else
''''    strSQL = "SELECT Nic_Handle FROM NicHandle "
''''    strSQL = strSQL & "WHERE Nic_Handle=" & CDBText(UCase(TxtAdCon1.Text)) & ""
''''    DB.Execute strSQL, rec
''''
''''    If rec.EOF Then
''''        MsgBox "Nic Handle not exists ", vbExclamation
''''        blnInError = True
''''        FormatControl TxtAdCon1, csError
''''    End If
''''
'''' End If
''''
''''
''''  '* For Admin Contact 2
''''    If TxtAdCon2.Text <> "" Then
''''
''''        strSQL = "SELECT Nic_Handle FROM NicHandle "
''''        strSQL = strSQL & "WHERE Nic_Handle=" & CDBText(UCase(TxtAdCon2.Text)) & ""
''''        DB.Execute strSQL, rec
''''
''''        If rec.EOF Then
''''            MsgBox "Nic Handle not exists ", vbExclamation
''''            blnInError = True
''''            FormatControl TxtAdCon2, csError
''''        End If
''''
''''     End If
''''
''''
'''' If TxtTechCon.Text = "" Then
''''    blnInError = True
''''    FormatControl TxtTechCon, csError
''''
'''' Else
''''    strSQL = "SELECT Nic_Handle FROM NicHandle "
''''    strSQL = strSQL & "WHERE Nic_Handle=" & CDBText(UCase(TxtTechCon.Text)) & ""
''''    DB.Execute strSQL, rec
''''
''''    If rec.EOF Then
''''        MsgBox "Nic Handle not exists ", vbExclamation
''''        blnInError = True
''''        FormatControl TxtTechCon, csError
''''    End If
''''
''''
'''' End If
''''
''''
'''' If ComboClass.Text = "" Then
''''    blnInError = True
''''    FormatControl ComboClass, csError
'''' End If
''''
'''' If ComboCategory.Text = "" Then
''''    blnInError = True
''''    FormatControl ComboCategory, csError
'''' End If
''''
''''
'''' If ComboStatus.Text = "" Then
''''    blnInError = True
''''    FormatControl ComboStatus, csError
'''' End If
''''
''''
'''' If ComboDNSNameE.Text = "" Then
''''     blnInError = True
''''     FormatControl ComboDNSNameE, csError
'''' Else
''''    If IsDuplicateDNS Then
''''        MsgBox "DNS name already exists ", vbExclamation
''''        blnInError = True
''''        FormatControl ComboDNSNameE, csError
''''    End If
''''
'''' End If
''''
''''
''''' Celina Leong Phoenix1.1 28/03/03
'''''Check DNS name to insert at least one Dot
''''
''''
''''    If Not ComboDNSNameE.Text = "" Then
''''        strDNS = ComboDNSNameE.Text
''''        IPDot% = 0
''''        Length% = Len(strDNS)
''''        For I% = 1 To Length%
''''            If Mid(strDNS, I%, 1) = "." Then
''''                IPDot% = IPDot% + 1
''''            End If
''''        Next I%
''''
''''    If IPDot% < 1 Then
''''        MsgBox "DNS Name at least one '.' in the field", vbExclamation
''''        blnInError = True
''''        FormatControl ComboDNSNameE, csError
''''    End If
''''   End If
'''' If TxtDNSIPAdd.Text = "(NEW)" Then
''''    TxtDNSIPAdd.Text = " "
''''
'''' Else
''''    If Not TxtDNSIPAdd.Text = "" Then
''''            strDNS = TxtDNSIPAdd.Text
''''            IPDot% = 0
''''            Length% = Len(strDNS)
''''            For I% = 1 To Length%
''''                If Mid(strDNS, I%, 1) = "." Then
''''                    IPDot% = IPDot% + 1
''''                End If
''''            Next I%
''''
'''''        If IPDot% <> 3 Then
'''''            MsgBox "DNS IP Address at least one '.' in the field", vbExclamation
'''''            blnInError = True
'''''            FormatControl TxtDNSIPAdd, csError
'''''        End If
''''        If IPDot% <> 3 Then
''''            MsgBox "DNS IP Address must be 3 '.' in the field", vbExclamation
''''            blnInError = True
''''            FormatControl TxtDNSIPAdd, csError
''''        End If
''''    End If
''''
''''
'''' End If
'''''-----------------------------------------------------------------------------
''''
''''
'''' IsValid = Not blnInError
''''End Function
''''
''''Private Function ValidTraAccount() As Boolean
'''' Dim blnInError As Boolean
'''' Dim strSQL As String
'''' Dim strTempAccNo As Long
'''' Dim rec As ADODB.Recordset
'''' Dim ctl As Control
''''' Celina Leong Pheonix1.1
'''' Dim strTempCountry As String
'''' Dim IPDot%, Length%, I%
'''' Dim strDNS As String
'''''------------------------------
''''
'''''Celina Leong 18-09-2002
''''' If TxtAccName.Text = "" Then
'''''    blnInError = True
'''''    FormatControl TxtAccName, csError
''''' Else
'''''   If strAcNo <> TxtAccName Then
'''''        If Not IsNumeric(TxtAccName) Then
'''''                MsgBox "Invalid Account No ", vbExclamation
'''''                blnInError = True
'''''                FormatControl TxtAccName, csError
'''''
'''''        Else
'''''
'''''            strSQL = "SELECT A_Number, Billing_NH FROM Account "
'''''            strSQL = strSQL & "WHERE A_Number=" & TxtAccName.Text & " "
'''''            strSQL = strSQL & "AND A_Status= " & CDBText("Active") & ""
'''''            DB.Execute strSQL, rec
'''''
''''
'''''            If rec.EOF Then
'''''                MsgBox "Account No not exists ", vbExclamation
'''''                blnInError = True
'''''                FormatControl TxtAccName, csError
'''''
'''''            Else
'''''
'''''                AcDataChanged = True
'''''                TxtBilCon.Text = NoNull(rec.Fields("Billing_NH"))
'''''
'''''            End If
'''''         End If
'''''  Else
'''''        AcDataChanged = False
'''''
'''''  End If
'''''End If
'''' If TxtAccName.Text = "" Then
''''    blnInError = True
''''    FormatControl TxtAccName, csError
'''' Else
''''   If strAcNo <> TxtAccName Or strAcNo = "00000001" Or strAcNo = 1 Then
''''        If Not IsNumeric(TxtAccName) Then
''''                MsgBox "Invalid Account No ", vbExclamation
''''                blnInError = True
''''                FormatControl TxtAccName, csError
''''        Else
''''
''''            If TxtAccName = "00000001" Or TxtAccName = "1" Then
''''
'''''
'''''                blnInError = True
'''''                FormatControl TxtBilCon, csEdit
''''
''''
''''
'''''                If Not IsNicHandleType(TxtBilCon, "B") Then
'''''                        MsgBox "Invalid Billing Contact ", vbExclamation
'''''                        blnInError = True
'''''                        FormatControl TxtBilCon, csError
'''''                End If
''''
''''                If Not IsAccNicHandle(TxtAccName, TxtBilCon) Then
''''                       MsgBox "You not allow to transfer a non-account billing contact " & vbCrLf & _
''''                       " to another non-account billing contact ", vbExclamation
''''                       blnInError = True
''''                       FormatControl TxtBilCon, csError
''''                End If
''''
''''            Else
''''
''''                strSQL = "SELECT A_Number, Billing_NH FROM Account "
''''                strSQL = strSQL & "WHERE A_Number=" & TxtAccName.Text & " "
''''                strSQL = strSQL & "AND A_Status= " & CDBText("Active") & ""
''''                DB.Execute strSQL, rec
''''
''''
''''                If rec.EOF Then
''''                    MsgBox "Account No not exists ", vbExclamation
''''                    blnInError = True
''''                    FormatControl TxtAccName, csError
''''
''''                Else
''''                    AcDataChanged = True
''''                    TxtBilCon.Text = NoNull(rec.Fields("Billing_NH"))
''''                    TempNichandle = TxtBilCon 'Celina Leong Phoenix1.1 14/04/03
''''
''''                End If
''''
''''            End If
''''        End If
''''       'Celina Leong 'Phoenix1.1 02/04/03
''''       'When transferring account, the vat status reflects the new billing contact
''''       strSQL = "SELECT NH_Country FROM Nichandle "
''''       strSQL = strSQL & "WHERE Nic_Handle =" & CDBText(TxtBilCon.Text) & " "
''''       DB.Execute strSQL, rec
''''
''''
''''       If Not rec.EOF Then
''''       strTempCountry = NoNull(rec.Fields("NH_Country"))
''''       '   blnInError = True
''''       End If
''''
''''       If strTempCountry <> "IRELAND" Then
''''          TxtVATStatus = "Y"
''''       End If
''''   Else
''''        AcDataChanged = False
''''        TempNichandle = TxtBilCon 'Celina Leong Phoenix1.1 14/04/03
''''   End If
''''
'''' End If
''''
''''
'''' If ComboDNSNameE.Text = "" Then
''''     blnInError = True
''''     FormatControl ComboDNSNameE, csError
'''' Else
''''    If IsDuplicateDNS Then
''''        MsgBox " DNS Name already exists ", vbExclamation
''''        blnInError = True
''''        FormatControl ComboDNSNameE, csError
''''    End If
''''
'''' End If
''''
'''' If TxtAdCon1.Text = "" Then
''''    blnInError = True
''''    FormatControl TxtAdCon1, csError
'''' Else
''''    strSQL = "SELECT Nic_Handle FROM NicHandle "
''''    strSQL = strSQL & "WHERE Nic_Handle=" & CDBText(UCase(TxtAdCon1.Text)) & ""
''''    DB.Execute strSQL, rec
''''
''''    If rec.EOF Then
''''        MsgBox "Nic Handle not exists ", vbExclamation
''''        blnInError = True
''''        FormatControl TxtAdCon1, csError
''''    End If
'''' End If
''''
'''' 'If TxtAdCon2.Text = "" Then
'''' '   blnInError = True
'''' '   FormatControl TxtAdCon2, csError
'''' 'End If
''''
'''' '* For Admin Contact 2
''''If TxtAdCon2.Text <> "" Then
''''
''''    strSQL = "SELECT Nic_Handle FROM NicHandle "
''''    strSQL = strSQL & "WHERE Nic_Handle=" & CDBText(UCase(TxtAdCon2.Text)) & ""
''''    DB.Execute strSQL, rec
''''
''''    If rec.EOF Then
''''        MsgBox "Nic Handle not exists ", vbExclamation
''''        blnInError = True
''''        FormatControl TxtAdCon2, csError
''''    End If
''''
'''' End If
''''
'''' If TxtTechCon.Text = "" Then
''''    blnInError = True
''''    FormatControl TxtTechCon, csError
'''' Else
''''    strSQL = "SELECT Nic_Handle FROM NicHandle "
''''    strSQL = strSQL & "WHERE Nic_Handle=" & CDBText(UCase(TxtTechCon.Text)) & ""
''''    DB.Execute strSQL, rec
''''
''''    If rec.EOF Then
''''        MsgBox "Nic Handle not exists ", vbExclamation
''''        blnInError = True
''''        FormatControl TxtTechCon, csError
''''    End If
''''
'''' End If
''''
'''' If TxtRemark.Text = "" Then
''''    blnInError = True
''''    FormatControl TxtRemark, csError
'''' End If
'''' ' Celina Leong Phoenix1.1 28/03/03
'''''Check DNS name to insert at least one Dot
''''
''''
''''    If Not ComboDNSNameE.Text = "" Then
''''        strDNS = ComboDNSNameE.Text
''''        IPDot% = 0
''''        Length% = Len(strDNS)
''''        For I% = 1 To Length%
''''            If Mid(strDNS, I%, 1) = "." Then
''''                IPDot% = IPDot% + 1
''''            End If
''''        Next I%
''''
''''    If IPDot% < 1 Then
''''        MsgBox "DNS Name at least one '.' in the field", vbExclamation
''''        blnInError = True
''''        FormatControl ComboDNSNameE, csError
''''    End If
''''   End If
'''' If TxtDNSIPAdd.Text = "(NEW)" Then
''''    TxtDNSIPAdd.Text = " "
''''
'''' Else
''''    If Not TxtDNSIPAdd.Text = "" Then
''''            strDNS = TxtDNSIPAdd.Text
''''            IPDot% = 0
''''            Length% = Len(strDNS)
''''            For I% = 1 To Length%
''''                If Mid(strDNS, I%, 1) = "." Then
''''                    IPDot% = IPDot% + 1
''''                End If
''''            Next I%
''''
''''        If IPDot% < 1 Then
''''            MsgBox "DNS IP Address at least one '.' in the field", vbExclamation
''''            blnInError = True
''''            FormatControl TxtDNSIPAdd, csError
''''        End If
''''    End If
''''
''''
'''' End If
'''''-----------------------------------------------------------------------------
''''
'''' ValidTraAccount = Not blnInError
''''End Function
''''
''''Private Sub WriteHistory_Domain(ByRef blnError As Boolean)
''''
''''Dim strSQL As String
'''' '   TempNow = Now()
'''''--------------------------------------------------------------------
'''''    strSQL = "INSERT INTO DomainHist("
'''''    strSQL = strSQL & "D_Name, "
'''''    strSQL = strSQL & "D_Holder, "
'''''    strSQL = strSQL & "D_Class, "
'''''    strSQL = strSQL & "D_Category, "
'''''    strSQL = strSQL & "A_Number, "
'''''    strSQL = strSQL & "D_Status, "
'''''    strSQL = strSQL & "D_Status_Dt, "
'''''    strSQL = strSQL & "D_Reg_Dt, "
'''''    strSQL = strSQL & "D_Ren_Dt, "
'''''    strSQL = strSQL & "D_TStamp, "
'''''    strSQL = strSQL & "D_Discount, "
'''''    strSQL = strSQL & "D_Bill_Status, "
'''''    strSQL = strSQL & "D_Vat_Status, "
'''''    strSQL = strSQL & "D_Remark, "
'''''    strSQL = strSQL & "Chng_NH, "
'''''    strSQL = strSQL & "Chng_Dt) "
'''''
'''''
'''''    strSQL = strSQL & "SELECT "
'''''    strSQL = strSQL & "D_Name, "
'''''    strSQL = strSQL & "D_Holder, "
'''''    strSQL = strSQL & "D_Class, "
'''''    strSQL = strSQL & "D_Category, "
'''''    strSQL = strSQL & "A_Number, "
'''''    strSQL = strSQL & "D_Status, "
'''''    strSQL = strSQL & "D_Status_Dt, "
'''''    strSQL = strSQL & "D_Reg_Dt, "
'''''    strSQL = strSQL & "D_Ren_Dt, "
'''''    strSQL = strSQL & "D_TStamp, "
'''''    strSQL = strSQL & "D_Discount,"
'''''    strSQL = strSQL & "D_Bill_Status, "
'''''    strSQL = strSQL & "D_Vat_Status, "
'''''    strSQL = strSQL & "D_Remark, "
'''''    strSQL = strSQL & CDBText(UserID) & ", "
'''''    strSQL = strSQL & CDBTime(TempNow) & " "
'''''    strSQL = strSQL & "FROM Domain "
'''''    strSQL = strSQL & "WHERE D_Name ='" & TxtDName & "'"
'''''    DB.Execute strSQL
'''''    ' strSQL = strSQL & "NOW() "
'''''
''''
'''''Celina Leong - Phoenix1.1 26/03/03
'''''Change the SQL String to included D_Bill_Status,D_VAT_Status and D_ClikPaid
''''    TempNow = Now()
''''    strSQL = "INSERT INTO DomainHist("
''''    strSQL = strSQL & "D_Name, "
''''    strSQL = strSQL & "D_Holder, "
''''    strSQL = strSQL & "D_Class, "
''''    strSQL = strSQL & "D_Category, "
''''    strSQL = strSQL & "A_Number, "
''''    strSQL = strSQL & "D_Status, "
''''    strSQL = strSQL & "D_Status_Dt, "
''''    strSQL = strSQL & "D_Reg_Dt, "
''''    strSQL = strSQL & "D_Ren_Dt, "
''''    strSQL = strSQL & "D_TStamp, "
''''    strSQL = strSQL & "D_Discount, "
''''    strSQL = strSQL & "D_Bill_Status, "
''''    strSQL = strSQL & "D_Vat_Status, "
''''    strSQL = strSQL & "D_Remark, "
''''    strSQL = strSQL & "D_ClikPaid, "
''''    strSQL = strSQL & "Chng_NH, "
''''    strSQL = strSQL & "Chng_Dt) "
''''
''''
''''    strSQL = strSQL & "SELECT "
''''    strSQL = strSQL & "D_Name, "
''''    strSQL = strSQL & "D_Holder, "
''''    strSQL = strSQL & "D_Class, "
''''    strSQL = strSQL & "D_Category, "
''''    strSQL = strSQL & "A_Number, "
''''    strSQL = strSQL & "D_Status, "
''''    strSQL = strSQL & "D_Status_Dt, "
''''    strSQL = strSQL & "D_Reg_Dt, "
''''    strSQL = strSQL & "D_Ren_Dt, "
''''    strSQL = strSQL & "D_TStamp, "
''''    strSQL = strSQL & "D_Discount,"
''''    strSQL = strSQL & "D_Bill_Status, "
''''    strSQL = strSQL & "D_Vat_Status, "
''''    strSQL = strSQL & "D_Remark, "
''''    strSQL = strSQL & "D_ClikPaid, "
''''    strSQL = strSQL & CDBText(UserID) & ", "
''''    strSQL = strSQL & CDBTime(TempNow) & " "
''''
''''    strSQL = strSQL & "FROM Domain "
''''    strSQL = strSQL & "WHERE D_Name ='" & TxtDName & "'"
''''    DB.Execute strSQL
''''    ' strSQL = strSQL & "NOW() "
''''
''''
''''End Sub
''''Private Sub WriteHistory_Contact()
''''Dim strSQL As String
''''
''''    strSQL = "INSERT INTO ContactHist("
''''    strSQL = strSQL & "D_Name, "
''''    strSQL = strSQL & "Contact_NH, "
''''    strSQL = strSQL & "Type, "
''''    strSQL = strSQL & "Chng_NH, "
''''    strSQL = strSQL & "Chng_Dt) "
''''
''''
''''''    strSQL = strSQL & "VALUES( "
''''''    strSQL = strSQL & CDBText(TxtDName) & ", "
''''''    strSQL = strSQL & CDBText(UCase(TempNichandle)) & ", "
''''''    strSQL = strSQL & CDBText(TempType) & ","
''''''    strSQL = strSQL & CDBText(UserID) & ", "
''''''    strSQL = strSQL & CDBTime(TempNow) & "); "
''''''    'strSQL = strSQL & "Now() ); "
'''''' Celina Leong Phoenix1.1 11/04/03
''''    strSQL = strSQL & "SELECT "
''''    strSQL = strSQL & "D_Name, "
''''    strSQL = strSQL & CDBText(UCase(TempNichandle)) & ", "
''''    strSQL = strSQL & CDBText(TempType) & ","
''''    strSQL = strSQL & CDBText(UserID) & ", "
''''    strSQL = strSQL & CDBTime(TempNow) & " "
''''    strSQL = strSQL & "FROM Contact "
''''    strSQL = strSQL & "WHERE D_Name ='" & TxtDName & "'"
''''
''''
''''
''''
''''
''''
''''
''''    DB.Execute strSQL
''''
''''End Sub
''''Private Sub WriteHistory_DNS()
''''Dim Counter As Integer
''''    Dim strSQL As String
''''
''''     If TempDNS = "(NEW)" Then
''''            strSQL = "INSERT INTO DNSHist("
''''            strSQL = strSQL & "D_Name, "
''''            strSQL = strSQL & "DNS_Name, "
''''            strSQL = strSQL & "DNS_IpAddr, "
''''            strSQL = strSQL & "DNS_Order, "
''''            strSQL = strSQL & "Chng_NH, "
''''            strSQL = strSQL & "Chng_Dt) "
''''
''''            strSQL = strSQL & "VALUES( "
''''            strSQL = strSQL & CDBText(TxtDName) & ", "
''''            strSQL = strSQL & CDBText(ComboDNSNameE) & ", "
''''            strSQL = strSQL & CDBText(TxtDNSIPAdd) & ", "
''''            strSQL = strSQL & CDBText(TempDNS_Order) & ","
''''            strSQL = strSQL & CDBText(UserID) & ", "
''''            strSQL = strSQL & CDBTime(TempNow) & "); "
''''
''''            DB.Execute strSQL
''''    Else
''''            strSQL = "INSERT INTO DNSHist("
''''            strSQL = strSQL & "D_Name, "
''''            strSQL = strSQL & "DNS_Name, "
''''            strSQL = strSQL & "DNS_IpAddr, "
''''            strSQL = strSQL & "DNS_Order, "
''''            strSQL = strSQL & "Chng_NH, "
''''            strSQL = strSQL & "Chng_Dt) "
''''
'''''            strSQL = strSQL & "VALUES( "
'''''            strSQL = strSQL & CDBText(TxtDName) & ", "
'''''            strSQL = strSQL & CDBText(ComboDNSNameE.List(Counter)) & ", "
'''''            strSQL = strSQL & CDBText(ListIPAdd.List(Counter)) & ", "
'''''            strSQL = strSQL & CDBText(ListIPAdd.ItemData(Counter)) & ","
'''''            strSQL = strSQL & CDBText(UserID) & ", "
'''''            strSQL = strSQL & CDBTime(TempNow) & "); "
'''''Celina Leong Phoenix1.1 11/04/03 write history befor update dns
''''             strSQL = strSQL & "SELECT "
''''             strSQL = strSQL & "D_Name, "
''''             strSQL = strSQL & CDBText(ComboDNSNameE.List(Counter)) & ", "
''''             strSQL = strSQL & CDBText(ListIPAdd.List(Counter)) & ", "
''''             strSQL = strSQL & CDBText(ListIPAdd.ItemData(Counter)) & ","
''''             strSQL = strSQL & CDBText(UserID) & ", "
''''             strSQL = strSQL & CDBTime(TempNow) & " "
''''
''''             strSQL = strSQL & "FROM Dns "
''''             strSQL = strSQL & "WHERE D_Name ='" & TxtDName & "'"
''''
''''            DB.Execute strSQL
''''    End If
''''
''''End Sub
'--------------------------------------------------------------------------
Private Sub LoadDomainHistory(ByVal strDName As String)

 Set RsSearchResults = New ADODB.Recordset
 
 On Error GoTo LoadDomainHistoryERROR
 


   ' sSQL = "SELECT DATE_FORMAT(Chng_Dt,'%Y-%m-%d')AS 'Date', " &

     sSQL = "SELECT " & FormatDBDate("Chng_Dt") & " AS 'Date', " & _
           "Chng_NH AS 'Nic Handle', " & _
           "D_Remark As 'Remark', " & _
           "Chng_Dt " & _
           "FROM DomainHist " & _
           "WHERE  D_Name = " & CDBText(strDName) & "" & _
           "ORDER BY Chng_Dt DESC"

          Set RsSearchResults = New ADODB.Recordset
    
        RsSearchResults.Open sSQL, CnPhoenix, adOpenDynamic, adLockOptimistic


 HFlexDomainsHis.Clear
 HFlexDomainsHis.FixedRows = 0
 HFlexDomainsHis.Rows = 2
 HFlexDomainsHis.FixedRows = 1
 
 
 Set HFlexDomainsHis.DataSource = RsSearchResults
 
 If HFlexDomainsHis.Rows <= 1 Then
    HFlexDomainsHis.Row = 0
 
 End If

Exit Sub
LoadDomainHistoryERROR:
 DB.ShowError "Unable to Load Domain History"
End Sub

'***************************************************************************
'*Load Domain History to screen

Private Sub Assign_DN_History(ByVal strDName As String)
Dim rec As ADODB.Recordset
Dim strSQL As String
Dim lngRow As Long

    lngRow = HFlexDomainsHis.Row
    strSQL = "SELECT * FROM DomainHist "
    strSQL = strSQL & "WHERE D_Name =" & CDBText(strDName) & "  "
    strSQL = strSQL & "AND Chng_Dt=" & CDBTime(HFlexDomainsHis.TextMatrix(lngRow, 4))
    
    DB.Execute strSQL, rec
        If Not rec.EOF Then
            
            TxtDName.Text = rec.Fields("D_Name")
            TxtDHolder.Text = NoNull(rec.Fields("D_Holder"))
'Celina Leong Phoenix1.1 28/03/03
'            ComboClass.Text = NoNull(rec.Fields("D_Class"))
'            ComboCategory.Text = NoNull(rec.Fields("D_category"))
            TxtClass.Text = NoNull(rec.Fields("D_Class"))
            TxtCategory.Text = NoNull(rec.Fields("D_category"))
'-----------------------------------------------------------------

            TxtAccName.Text = Format(rec.Fields("A_Number"), gstrAccountNoPad)
            ComboStatus.Text = NoNull(rec.Fields("D_Status"))
            TxtChanged.Text = NoNull(rec.Fields("D_Status_Dt"))
            TxtRegDate.Text = NoNull(rec.Fields("D_Reg_Dt"))
            TxtRenewDate.Text = NoNull(rec.Fields("D_Ren_Dt"))
            TxtRemark.Text = NoNull(rec.Fields("D_Remark"))
            TxtRemark.Text = Replace(TxtRemark.Text, "%", vbCrLf)

'Celina Leong - Phoenix1.1 26/03/03
'Change the codes to included D_Bill_Status,D_VAT_Status and D_ClikPaid
            TxtBillStatus.Text = NoNull(rec.Fields("D_Bill_Status"))
            TxtVATStatus.Text = NoNull(rec.Fields("D_Vat_Status"))
            TxtClikPay.Text = NoNull(rec.Fields("D_ClikPaid"))
'-----------------------------------------------------------------------
        End If
              
        TxtRegDate.Text = Format(TxtRegDate, "dd/mm/yyyy")
        TxtRenewDate.Text = Format(TxtRenewDate, "dd/mm/yyyy")
        TxtChanged.Text = Format(TxtChanged, "dd/mm/yyyy")
End Sub

'***************************************************************************
'* Load DNS History to screen

Private Sub Assign_DNS_History(ByVal strDName As String)
Dim strSQL As String
Dim rec As ADODB.Recordset
Dim lngRow As Long

    lngRow = HFlexDomainsHis.Row
    
    strSQL = "SELECT * FROM DNSHist "
    strSQL = strSQL & "WHERE D_Name =" & CDBText(strDName) & "  "
    strSQL = strSQL & "AND Chng_Dt=" & CDBTime(HFlexDomainsHis.TextMatrix(lngRow, 4))
    
    DB.Execute strSQL, rec
    ComboDNSName.Clear ' add in 31/05/02
    TxtDNSIPAdd.Text = ""
'If Not rec.EOF Then
'    If Not rec.EOF Then
'        ComboDNSName.AddItem rec.Fields("DNS_Name")
'       ' TxtDNSIPAdd = NoNull(rec.Fields("DNS_IpAddr"))
'    End If
' Else
'       ComboDNSName.Text = ""
'       TxtDNSIPAdd.Text = ""
   
' End If

  While Not rec.EOF
        ComboDNSName.AddItem rec.Fields("DNS_Name")
        If rec.EOF = False Then
            rec.MoveNext
        End If
        
  Wend
  
  '     ComboDNSName.Text = ""
  '     TxtDNSIPAdd.Text = ""
  
  If ComboDNSName.Listcount > 0 Then
     ComboDNSName.ListIndex = 0
  End If
  
  
End Sub

'***************************************************************************
'*  Load Contact History to screen                                                        *


Private Sub Assign_Contact_History(ByVal strDName As String)
Dim strSQL As String
Dim rec As ADODB.Recordset
Dim lngRow As Long
Dim txtContact As String


    lngRow = HFlexDomainsHis.Row
    
    strSQL = "SELECT D_Name, Contact_NH, Type, Chng_Dt FROM ContactHist "
    strSQL = strSQL & "WHERE D_Name =" & CDBText(strDName) & "  "
    strSQL = strSQL & "AND Chng_Dt=" & CDBTime(HFlexDomainsHis.TextMatrix(lngRow, 4)) & " "
    DB.Execute strSQL, rec
            txtContact = ""
            TxtAdCon1.Text = ""
            TxtBilCon.Text = ""
            TxtTechCon.Text = ""
            TxtAdCon2.Text = ""
            TxtAdCon1.ToolTipText = ""
            TxtBilCon.ToolTipText = ""
            TxtTechCon.ToolTipText = ""
            TxtAdCon2.ToolTipText = ""

    If Not rec.EOF Then
         While Not rec.EOF
            Select Case rec.Fields("Type").Value
            Case "A"
                txtContact = rec.Fields("Contact_NH").Value
If TxtAdCon1.Text = "" Then '20060424 Admin contacts are now all "A"; not "A"&"2"
                TxtAdCon1.Text = UCase(txtContact)
                TxtAdCon1.ToolTipText = GetName(TxtAdCon1.Text)
Else '20060424 Admin contacts are now all "A"; not "A"&"2"
                TxtAdCon2.Text = UCase(txtContact)
                TxtAdCon2.ToolTipText = GetName(TxtAdCon2.Text)
End If '20060424 Admin contacts are now all "A"; not "A"&"2"
            Case "B"
                txtContact = rec.Fields("Contact_NH").Value
                TxtBilCon.Text = UCase(txtContact)
                TxtBilCon.ToolTipText = GetName(TxtBilCon.Text)
            Case "T"
                txtContact = rec.Fields("Contact_NH").Value
                TxtTechCon.Text = UCase(txtContact)
                TxtTechCon.ToolTipText = GetName(TxtTechCon.Text)
            Case "2"
                txtContact = rec.Fields("Contact_NH").Value
                TxtAdCon2.Text = UCase(txtContact)
                TxtAdCon2.ToolTipText = GetName(TxtAdCon2.Text)
            End Select
            
            rec.MoveNext
         
       Wend
        
    Else
            TxtAdCon1.Text = ""
            TxtAdCon2.Text = ""
            TxtTechCon.Text = ""
            TxtBilCon.Text = ""
       
    End If
      
End Sub
Private Function DN_Changed() As Boolean
 Dim FieldChng As Boolean

    
   If TxtDHolder.DataChanged Or _
       TxtAccName.DataChanged Or _
       ComboClass.ListIndex = -1 Or _
       ComboCategory.ListIndex = -1 Or _
       ComboStatus.ListIndex = -1 Or _
       ComboBillStatus.ListIndex = -1 Or _
       ComboVATStatus.ListIndex = -1 Or _
       ComboClikPay.ListIndex = -1 Or _
       TxtRenewDate.DataChanged Or _
       TxtRegDate.DataChanged Then
          FieldChng = True
    End If

DN_Changed = FieldChng

End Function
Private Function NH_Changed() As Boolean
 Dim FieldChng As Boolean
             
    If TxtAdCon1.DataChanged Or _
       TxtTechCon.DataChanged Or _
       TxtBilCon.DataChanged Or _
       TxtAdCon2.DataChanged Then
          FieldChng = True
    End If
          
NH_Changed = FieldChng
   
End Function

Private Function DNS_Changed() As Boolean
 Dim FieldChng As Boolean
 
  If ComboDNSNameE.ListIndex = -1 Or _
       TxtDNSIPAdd.DataChanged Or mblnDeleteDNS Then
        FieldChng = True
          
  End If
          
DNS_Changed = FieldChng
   
End Function

Private Function IsDuplicateDNS() As Boolean
Dim lngloopA, lngloopB, lngloopC
Dim lngCount

        lngCount = ComboDNSNameE.Listcount - 1
        For lngloopA = 1 To lngCount
            For lngloopB = lngloopA + 1 To lngCount
                If ComboDNSNameE.List(lngloopA) = ComboDNSNameE.List(lngloopB) Then
                     IsDuplicateDNS = True
                     Exit Function
                End If
            Next lngloopB
        
            For lngloopC = 0 To lngCount - 1
                If lngloopC <> lngloopA Then
                    If ComboDNSNameE.List(lngloopA) = ComboDNSNameE.List(lngloopC) Then
                       IsDuplicateDNS = True
                       Exit Function
                    End If
                End If
            Next lngloopC
            
        Next lngloopA
        
        
       Exit Function
        
    
End Function

Private Sub LoadContact_Name()
Dim rec As ADODB.Recordset
Dim strSQL As String

If strContact <> " " Then


    Set RsSearchResults = New ADODB.Recordset


    sSQL = "SELECT DISTINCT NicHandle.Nic_Handle, NicHandle.NH_Name, " & _
           "FROM NicHandle " & _
           "Where Nic_Handle =" & CDBText(strSQL)

  

    Set RsSearchResults = New ADODB.Recordset
    RsSearchResults.Open sSQL, CnPhoenix, adOpenDynamic, adLockOptimistic



End If

End Sub

Private Sub TxtTechCon_LostFocus()
    If mcsFormState = csEdit Then
        If TxtTechCon.DataChanged Then
             TxtTechCon.ToolTipText = GetName(TxtTechCon.Text)
        End If
    End If
End Sub
'-----------------------------------------------------------------------
''''Not Use in DomainHis
''''Public Sub WriteHistory_Nichandle() 'ByVal mstrNicHandle As String)
''''Dim strSQL As String
''''
''''    TempNow = Now()
''''
''''    strSQL = "INSERT INTO NicHandleHist("
''''    strSQL = strSQL & "Nic_Handle, "
''''    strSQL = strSQL & "NH_Name, "
''''    strSQL = strSQL & "A_Number, "
''''    strSQL = strSQL & "Co_Name, "
''''    strSQL = strSQL & "NH_Address, "
''''    strSQL = strSQL & "NH_County, "
''''    strSQL = strSQL & "NH_Country, "
''''    strSQL = strSQL & "NH_Email, "
''''    strSQL = strSQL & "NH_Status, "
''''    strSQL = strSQL & "NH_Status_Dt, "
''''    strSQL = strSQL & "NH_Reg_Dt, "
''''    strSQL = strSQL & "NH_TStamp, "
''''    strSQL = strSQL & "NH_BillC_Ind, "
''''    strSQL = strSQL & "NH_Remark, "
''''    strSQL = strSQL & "Chng_NH, "
''''    strSQL = strSQL & "Chng_Dt) "
''''
''''
''''    strSQL = strSQL & "SELECT "
''''    strSQL = strSQL & "Nic_Handle, "
''''    strSQL = strSQL & "NH_Name, "
''''    strSQL = strSQL & "A_Number, "
''''    strSQL = strSQL & "Co_Name, "
''''    strSQL = strSQL & "NH_Address, "
''''    strSQL = strSQL & "NH_County, "
''''    strSQL = strSQL & "NH_Country, "
''''    strSQL = strSQL & "NH_Email, "
''''    strSQL = strSQL & "NH_Status, "
''''    strSQL = strSQL & "NH_Status_Dt, "
''''    strSQL = strSQL & "NH_Reg_Dt,"
''''    strSQL = strSQL & "NH_TStamp, "
''''    strSQL = strSQL & "NH_BillC_Ind, "
''''    strSQL = strSQL & "NH_Remark, "
''''    strSQL = strSQL & CDBText(UserID) & ", "
'''''    strSQL = strSQL & CDBTime(TempNow) & " "
''''    strSQL = strSQL & "NOW() "
''''    strSQL = strSQL & "FROM NicHandle "
''''    strSQL = strSQL & "WHERE Nic_Handle ='" & TxtBilCon & "'"
''''    DB.Execute strSQL
''''
''''End Sub
'''''Celina Leong - Phoenix1.1 27/03/03
'''''Send Email to admin or billing conatact when alter status for suspended
''''Private Sub SendSuspendedMail(ByRef blnError As Boolean)
'''' Dim strEmail  As String
'''' Dim strHeader As String
'''' Dim strBody   As String
'''' Dim strFooter As String
''''
'''' On Error GoTo MailSendERROR
''''
'''' strEmail = GetEmail(TempContact)
''''
''''
'''' strHeader = "PLEASE DO NOT REPLY TO THIS E-MAIL.  THIS IS AN AUTOMATED E-MAIL. " & vbCrLf & vbCrLf
'''' strHeader = strHeader & "Your domain name  " & TxtDName & " has been suspended." & vbCrLf & vbCrLf
''''
'''' strFooter = vbCrLf & vbCrLf & "Kind regards," & vbCrLf & vbCrLf & ".IE Domain Registry Hostmaster"
''''
'''' strBody = strBody & "Remarks:  " & TxtRemark & vbCrLf & vbCrLf
''''
'''' FrmSendMail.SendMail blnError, _
''''                      DB.StaticData(ustSmtp), _
''''                      DB.StaticData(ustSmtpPort), _
''''                      strEmail, _
''''                      DB.StaticData(ustReplyAddr), _
''''                      "Domain Status has been suspended : " & TxtDName, _
''''                      strHeader & strBody & strFooter
''''
'''' blnError = False
''''Exit Sub
''''MailSendERROR:
'''' MsgBox "Err No:   " & Err.Number & vbCrLf & _
''''        "Err Desc: " & Err.Description, vbCritical
''''
'''' blnError = True
''''End Sub
''''
'''''Celina Leong - Phoenix1.1 27/03/03
'''''Send Email to admin or billing conatact when alter status for deleted
''''Private Sub SendDeletedMail(ByRef blnError As Boolean)
'''' Dim strEmail  As String
'''' Dim strHeader As String
'''' Dim strBody   As String
'''' Dim strFooter As String
''''
'''' On Error GoTo MailSendERROR
''''
'''' strEmail = GetEmail(TempContact)
''''
''''
'''' strHeader = "PLEASE DO NOT REPLY TO THIS E-MAIL.  THIS IS AN AUTOMATED E-MAIL. " & vbCrLf & vbCrLf
'''' strHeader = strHeader & "Your domain name  " & TxtDName & " has been deleted." & vbCrLf & vbCrLf
''''
'''' strFooter = vbCrLf & vbCrLf & "Kind regards," & vbCrLf & vbCrLf & ".IE Domain Registry Hostmaster"
''''
'''' strBody = strBody & "Remarks:  " & TxtRemark & vbCrLf & vbCrLf
''''
'''' FrmSendMail.SendMail blnError, _
''''                      DB.StaticData(ustSmtp), _
''''                      DB.StaticData(ustSmtpPort), _
''''                      strEmail, _
''''                      DB.StaticData(ustReplyAddr), _
''''                      "Domain Status has been deleted : " & TxtDName, _
''''                      strHeader & strBody & strFooter
''''
'''' blnError = False
''''Exit Sub
''''MailSendERROR:
'''' MsgBox "Err No:   " & Err.Number & vbCrLf & _
''''        "Err Desc: " & Err.Description, vbCritical
''''
'''' blnError = True
''''End Sub
'-----------------------------------------------------------------------
''''Not Use in DomainHis
'''''Celina Leong Phoenix1.1 01/04/03
''''Private Sub Delete_DNS(ByRef blnError As Boolean)
''''Dim Count As Integer
''''Dim strSQL As String
''''Dim Listcnt As Long
''''
''''
''''On Error GoTo DeleteDNSERROR
''''
''''
''''Listcnt = ListDNSname.Listcount
''''
''''For Count = 0 To Listcnt
''''
''''
''''       strSQL = "DELETE FROM DNS "
''''       strSQL = strSQL & "WHERE DNS_Name=" & CDBText(ListDNSname.List(Count))
''''
''''       DB.Execute strSQL
''''
''''
''''
''''Next Count
''''
''''
''''
''''
''''''WriteHistory_Domain
''''''WriteHistory_DNS
''''''WriteHistory_Contact
''''''WriteHistory_Nichandle
'''''
'''''
''''Exit Sub
''''DeleteDNSERROR:
''''
''''On Error Resume Next
''''
''''blnError = True
''''
''''
'''' '            strSQL = "INSERT INTO DNSHist("
'''''            strSQL = strSQL & "D_Name, "
'''''            strSQL = strSQL & "DNS_Name, "
'''''            strSQL = strSQL & "DNS_IpAddr, "
'''''            strSQL = strSQL & "DNS_Order, "
'''''            strSQL = strSQL & "Chng_NH, "
'''''            strSQL = strSQL & "Chng_Dt) "
'''''
'''''            strSQL = strSQL & "VALUES( "
'''''            strSQL = strSQL & CDBText(TxtDName) & ", "
'''''            strSQL = strSQL & CDBText(ComboDNSNameE.List(Counter)) & ", "
'''''            strSQL = strSQL & CDBText(ListIPAdd.List(Counter)) & ", "
'''''            strSQL = strSQL & ListIPAdd.ItemData(Counter) & ","
'''''            strSQL = strSQL & CDBText(UserID) & ", "
'''''            strSQL = strSQL & CDBTime(TempNow) & "); "
'''''
'''''
'''''
'''''
'''''            DB.Execute strSQL
''''  ' End If
'''''------------------------------------------
''''End Sub
'-----------------------------------------------------------------------
''''Not Use in DomainHis
''''
'''''Celina Leong 11-04-2003
'''''Update NicHandle Billing Contact Indicated if Transfer Account = 1
''''
''''
''''Private Sub UpdateAdminContact(ByRef blnError As Boolean)
''''
''''Dim strSQL         As String
''''Dim lngRowsUpdated As Long
''''
'''' On Error GoTo UpdateAdmincontactError
''''
'''' If blnError Then Exit Sub
'''' DB.BeginTransaction
''''   strSQL = "UPDATE NicHandle  SET "
''''   strSQL = strSQL & "A_Number=" & CDBText(TxtAccName) & ", "
''''   strSQL = strSQL & "NH_Remark=" & CDBText(TxtRemark) & " "
''''   strSQL = strSQL & "WHERE Nic_Handle=" & CDBText(TempAdmin) & ""
''''
''''    DB.Execute strSQL, , , , lngRowsUpdated
''''    'Celina Leong Phoenix1.1 show old history before update
''''        WriteHistory_Nichandle
''''    '------------------------------------------------
''''     If lngRowsUpdated <> 1 Then
''''          DB.RollbackTransaction
''''          MsgBox "Could not Update Nic Handle." & vbCrLf & vbCrLf & "Nic Handle updated by another user while you were editing it", vbExclamation
''''     Else
''''
''''         DB.CommitTransaction
''''         'WriteHistory_Nichandle 'Celina Leong Phoenix1.1 show old history before update
''''    End If
''''
''''
''''Exit Sub
''''
''''UpdateAdmincontactError:
''''
''''On Error Resume Next
''''
''''If Err.Number = glngDuplicateKey Then
''''    MsgBox " Duplicate key ", vbExclamation
''''Else
''''    DB.ShowError "Unable to Update Nichandle "
''''End If
''''
'''' blnError = True
''''
''''End Sub
'-----------------------------------------------------------------------
''''Not Use in DomainHis
'''''Celina Leong Phoenix1.1 11/04/03
''''Private Sub WriteNichandle(ByRef blnError As Boolean)
''''
''''
''''            TempType = "A"
''''            TempNichandle = TempAdmin1
''''            WriteHistory_Contact
''''
''''            TempType = "T"
''''            TempNichandle = TempTechC
''''            WriteHistory_Contact
''''
''''            TempType = "2"
''''            TempNichandle = strAdCon2
''''            WriteHistory_Contact
''''
''''            TempType = "B"
''''            TempNichandle = strOldBilCon
''''            WriteHistory_Contact
''''
''''
''''
''''End Sub
'-----------------------------------------------------------------------
''''Not Used in DomainHist
''''Private Sub WriteDNS(ByRef blnError As Boolean)
''''
''''Dim Counter As Integer
''''Dim strSQL As String
''''Dim Listcount As Long
''''Dim Listcnt As Long
''''Dim Count As Integer
''''
''''
''''Listcount = ComboDNSNameE.Listcount - 1
''''
''''For Counter = 1 To Listcount
''''
''''
''''            strSQL = "INSERT INTO DNSHist("
''''            strSQL = strSQL & "D_Name, "
''''            strSQL = strSQL & "DNS_Name, "
''''            strSQL = strSQL & "DNS_IpAddr, "
''''            strSQL = strSQL & "DNS_Order, "
''''            strSQL = strSQL & "Chng_NH, "
''''            strSQL = strSQL & "Chng_Dt) "
''''
''''
''''             strSQL = strSQL & "SELECT "
''''             strSQL = strSQL & "D_Name, "
''''             strSQL = strSQL & "DNS_Name, "
''''             strSQL = strSQL & "DNS_IpAddr, "
''''             strSQL = strSQL & "DNS_Order, "
''''             strSQL = strSQL & CDBText(UserID) & ", "
''''             strSQL = strSQL & CDBTime(TempNow) & " "
''''
''''             strSQL = strSQL & "FROM Dns "
''''             strSQL = strSQL & "WHERE D_Name ='" & TxtDName & "'"
''''             strSQL = strSQL & "AND DNS_NAME = " & CDBText(ComboDNSNameE.List(Counter)) & ""
''''
''''
''''            DB.Execute strSQL
''''
''''            '            strSQL = strSQL & "VALUES( "
'''''            strSQL = strSQL & CDBText(TxtDName) & ", "
'''''            strSQL = strSQL & CDBText(ComboDNSNameE.List(Counter)) & ", "
'''''            strSQL = strSQL & CDBText(ListIPAdd.List(Counter)) & ", "
'''''            strSQL = strSQL & CDBText(ListIPAdd.ItemData(Counter)) & ","
'''''            strSQL = strSQL & CDBText(UserID) & ", "
'''''            strSQL = strSQL & CDBTime(TempNow) & "); "
'''''
'''''             strSQL = strSQL & "FROM Dns "
'''''             strSQL = strSQL & "WHERE D_Name ='" & TxtDName & "'"
''''     '   End If
''''
'''' Next Counter
''''
''''If Not ListDNSname.Listcount = 0 Then
''''        Listcount = ListDNSname.Listcount
''''
''''        For Counter = 0 To Listcount
''''
''''
''''                    strSQL = "INSERT INTO DNSHist("
''''                    strSQL = strSQL & "D_Name, "
''''                    strSQL = strSQL & "DNS_Name, "
''''                    strSQL = strSQL & "DNS_IpAddr, "
''''                    strSQL = strSQL & "DNS_Order, "
''''                    strSQL = strSQL & "Chng_NH, "
''''                    strSQL = strSQL & "Chng_Dt) "
''''
''''
''''                     strSQL = strSQL & "SELECT "
''''                     strSQL = strSQL & "D_Name, "
''''                     strSQL = strSQL & "DNS_Name, "
''''                     strSQL = strSQL & "DNS_IpAddr, "
''''                     strSQL = strSQL & "DNS_Order, "
''''                     strSQL = strSQL & CDBText(UserID) & ", "
''''                     strSQL = strSQL & CDBTime(TempNow) & " "
''''
''''                     strSQL = strSQL & "FROM Dns "
''''                     strSQL = strSQL & "WHERE D_Name ='" & TxtDName & "'"
''''                     strSQL = strSQL & "AND DNS_NAME = " & CDBText(ListDNSname.List(Counter)) & ""
''''
''''
''''
''''             DB.Execute strSQL
''''
''''
''''
''''
''''     Next Counter
''''
''''End If
''''
''''End Sub
