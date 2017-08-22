VERSION 5.00
Object = "{831FDD16-0C5C-11D2-A9FC-0000F8754DA1}#2.0#0"; "MSCOMCTL.OCX"
Object = "{0ECD9B60-23AA-11D0-B351-00A0C9055D8E}#6.0#0"; "mshflxgd.ocx"
Begin VB.Form FrmDomain 
   Caption         =   "Domain Maintenance"
   ClientHeight    =   8535
   ClientLeft      =   60
   ClientTop       =   945
   ClientWidth     =   12270
   ForeColor       =   &H80000008&
   HelpContextID   =   2000
   LinkTopic       =   "Form1"
   MDIChild        =   -1  'True
   ScaleHeight     =   8535
   ScaleMode       =   0  'User
   ScaleWidth      =   12270
   WindowState     =   2  'Maximized
   Begin VB.Frame FraOption 
      Caption         =   "Options"
      ForeColor       =   &H00000000&
      Height          =   915
      Left            =   240
      TabIndex        =   32
      Top             =   7440
      Width           =   11775
      Begin VB.CommandButton cmdTraAC 
         Caption         =   "Trans&fer Account"
         Height          =   375
         Left            =   3720
         MaskColor       =   &H8000000F&
         TabIndex        =   66
         Top             =   320
         Width           =   1445
      End
      Begin VB.CommandButton cmdAlStatus 
         Caption         =   "Alter Stat&us"
         Height          =   375
         Left            =   2160
         MaskColor       =   &H8000000F&
         TabIndex        =   65
         Top             =   320
         Width           =   1445
      End
      Begin VB.CommandButton cmdDelDNSName 
         Caption         =   "Delete DNS Name"
         Height          =   375
         Left            =   480
         TabIndex        =   64
         Top             =   320
         Width           =   1575
      End
      Begin VB.CommandButton cmdClose 
         Caption         =   "&Close"
         Height          =   375
         Left            =   9960
         MaskColor       =   &H8000000F&
         TabIndex        =   56
         Top             =   320
         Width           =   1445
      End
      Begin VB.CommandButton cmdCancel 
         Caption         =   "Ca&ncel"
         Height          =   375
         Left            =   6840
         TabIndex        =   27
         Top             =   320
         Width           =   1445
      End
      Begin VB.CommandButton cmdEdit 
         Caption         =   "&Edit"
         Height          =   375
         Left            =   5280
         MaskColor       =   &H8000000F&
         TabIndex        =   26
         Top             =   320
         Width           =   1445
      End
      Begin VB.CommandButton cmdSave 
         Caption         =   "Sa&ve"
         Height          =   375
         Left            =   8400
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
      Top             =   5640
      Width           =   11775
      Begin MSHierarchicalFlexGridLib.MSHFlexGrid HFlexDomains 
         Height          =   1215
         Left            =   240
         TabIndex        =   25
         Top             =   240
         Width           =   11295
         _ExtentX        =   19923
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
      Caption         =   "Current"
      ForeColor       =   &H00000000&
      Height          =   4095
      Left            =   240
      TabIndex        =   30
      Top             =   1440
      Width           =   11775
      Begin MSComctlLib.ImageCombo ImageComboBillStatus 
         Height          =   330
         Left            =   6240
         TabIndex        =   68
         Top             =   1440
         Width           =   2415
         _ExtentX        =   4260
         _ExtentY        =   582
         _Version        =   393216
         ForeColor       =   -2147483640
         BackColor       =   -2147483643
         Enabled         =   0   'False
         Locked          =   -1  'True
      End
      Begin MSComctlLib.ImageList BillStatusList 
         Left            =   4320
         Top             =   720
         _ExtentX        =   1005
         _ExtentY        =   1005
         BackColor       =   -2147483643
         ImageWidth      =   30
         ImageHeight     =   10
         MaskColor       =   12632256
         _Version        =   393216
         BeginProperty Images {2C247F25-8591-11D1-B16A-00C0F0283628} 
            NumListImages   =   9
            BeginProperty ListImage1 {2C247F27-8591-11D1-B16A-00C0F0283628} 
               Picture         =   "FrmDomains.frx":0000
               Key             =   "blue"
            EndProperty
            BeginProperty ListImage2 {2C247F27-8591-11D1-B16A-00C0F0283628} 
               Picture         =   "FrmDomains.frx":03EA
               Key             =   "black"
            EndProperty
            BeginProperty ListImage3 {2C247F27-8591-11D1-B16A-00C0F0283628} 
               Picture         =   "FrmDomains.frx":07D4
               Key             =   "green"
            EndProperty
            BeginProperty ListImage4 {2C247F27-8591-11D1-B16A-00C0F0283628} 
               Picture         =   "FrmDomains.frx":0BBE
               Key             =   "lightblue"
            EndProperty
            BeginProperty ListImage5 {2C247F27-8591-11D1-B16A-00C0F0283628} 
               Picture         =   "FrmDomains.frx":0FA8
               Key             =   "orange"
            EndProperty
            BeginProperty ListImage6 {2C247F27-8591-11D1-B16A-00C0F0283628} 
               Picture         =   "FrmDomains.frx":1392
               Key             =   "purple"
            EndProperty
            BeginProperty ListImage7 {2C247F27-8591-11D1-B16A-00C0F0283628} 
               Picture         =   "FrmDomains.frx":177C
               Key             =   "red"
            EndProperty
            BeginProperty ListImage8 {2C247F27-8591-11D1-B16A-00C0F0283628} 
               Picture         =   "FrmDomains.frx":1B66
               Key             =   "white"
            EndProperty
            BeginProperty ListImage9 {2C247F27-8591-11D1-B16A-00C0F0283628} 
               Picture         =   "FrmDomains.frx":1F50
               Key             =   "yellow"
            EndProperty
         EndProperty
      End
      Begin VB.TextBox TxtClikPay 
         BackColor       =   &H8000000F&
         Height          =   315
         Left            =   10080
         TabIndex        =   23
         Top             =   1800
         Width           =   1215
      End
      Begin VB.TextBox TxtBillStatus 
         BackColor       =   &H8000000F&
         Height          =   315
         Left            =   6240
         TabIndex        =   15
         Top             =   1440
         Width           =   1575
      End
      Begin VB.TextBox TxtVATStatus 
         BackColor       =   &H8000000F&
         Height          =   315
         Left            =   6240
         TabIndex        =   18
         Top             =   1080
         Visible         =   0   'False
         Width           =   1575
      End
      Begin VB.ListBox ListDNSname 
         Height          =   255
         Left            =   10080
         TabIndex        =   67
         Top             =   2760
         Visible         =   0   'False
         Width           =   1455
      End
      Begin VB.ComboBox ComboCategory 
         Height          =   315
         ItemData        =   "FrmDomains.frx":233A
         Left            =   1800
         List            =   "FrmDomains.frx":233C
         Style           =   2  'Dropdown List
         TabIndex        =   63
         Top             =   1800
         Width           =   3060
      End
      Begin VB.ComboBox ComboClass 
         Height          =   315
         Left            =   1800
         Style           =   2  'Dropdown List
         TabIndex        =   62
         Top             =   1440
         Width           =   3060
      End
      Begin VB.TextBox TxtCategory 
         BackColor       =   &H8000000F&
         DataField       =   "D_Category"
         Height          =   315
         Left            =   1800
         TabIndex        =   61
         Top             =   1800
         Width           =   3015
      End
      Begin VB.TextBox TxtClass 
         BackColor       =   &H8000000F&
         DataField       =   "D_Class"
         Height          =   315
         Left            =   1800
         TabIndex        =   60
         Top             =   1440
         Width           =   3015
      End
      Begin VB.ComboBox ComboClikPay 
         Height          =   315
         Left            =   10080
         Style           =   2  'Dropdown List
         TabIndex        =   24
         Top             =   1800
         Width           =   1335
      End
      Begin VB.ComboBox ComboVATStatus 
         Height          =   315
         Left            =   6240
         Style           =   2  'Dropdown List
         TabIndex        =   19
         Top             =   1080
         Visible         =   0   'False
         Width           =   1815
      End
      Begin VB.ComboBox ComboBillStatus 
         Height          =   315
         Left            =   6240
         Style           =   2  'Dropdown List
         TabIndex        =   20
         Top             =   1440
         Width           =   1815
      End
      Begin VB.ListBox ListIPAdd 
         Height          =   450
         Left            =   10080
         TabIndex        =   55
         Top             =   2280
         Visible         =   0   'False
         Width           =   1455
      End
      Begin VB.ComboBox ComboDNSNameE 
         DataField       =   "DNS_Name"
         Height          =   315
         Left            =   6240
         TabIndex        =   11
         Text            =   "ComboDNSNameE"
         Top             =   1800
         Width           =   2415
      End
      Begin VB.TextBox TxtRemark 
         BackColor       =   &H8000000F&
         DataField       =   "D_Remark"
         ForeColor       =   &H00000000&
         Height          =   855
         Left            =   5040
         MultiLine       =   -1  'True
         ScrollBars      =   2  'Vertical
         TabIndex        =   22
         Top             =   3120
         Width           =   6495
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
         Left            =   10080
         Locked          =   -1  'True
         TabIndex        =   17
         Top             =   1130
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
         Left            =   10080
         Locked          =   -1  'True
         TabIndex        =   21
         Top             =   1440
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
         Height          =   255
         Left            =   10080
         Locked          =   -1  'True
         TabIndex        =   16
         Top             =   775
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
         Top             =   3255
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
         Top             =   2880
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
         Top             =   2565
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
         Top             =   2235
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
         Top             =   720
         Width           =   4380
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
         Top             =   1080
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
         Top             =   400
         Width           =   4425
      End
      Begin VB.TextBox TxtDNSIPAdd 
         BackColor       =   &H8000000F&
         BorderStyle     =   0  'None
         DataField       =   "DNS_IpAddr"
         ForeColor       =   &H00000000&
         Height          =   315
         Left            =   6240
         Locked          =   -1  'True
         MaxLength       =   20
         TabIndex        =   13
         Top             =   2400
         Width           =   1995
      End
      Begin VB.ComboBox ComboDNSName 
         BackColor       =   &H8000000F&
         ForeColor       =   &H00000000&
         Height          =   315
         Left            =   6240
         Style           =   2  'Dropdown List
         TabIndex        =   12
         Top             =   1800
         Width           =   2415
      End
      Begin VB.ComboBox ComboStatus 
         BackColor       =   &H8000000F&
         DataField       =   "D_Status"
         Height          =   315
         ItemData        =   "FrmDomains.frx":233E
         Left            =   10080
         List            =   "FrmDomains.frx":2340
         Locked          =   -1  'True
         TabIndex        =   14
         Top             =   360
         Width           =   1335
      End
      Begin VB.Label lblFaxes 
         Caption         =   "Fax(es)"
         BeginProperty Font 
            Name            =   "MS Sans Serif"
            Size            =   9.75
            Charset         =   0
            Weight          =   700
            Underline       =   0   'False
            Italic          =   0   'False
            Strikethrough   =   0   'False
         EndProperty
         ForeColor       =   &H00008000&
         Height          =   255
         Left            =   240
         TabIndex        =   71
         Top             =   3650
         Visible         =   0   'False
         Width           =   4575
      End
      Begin VB.Label lblTickets 
         Caption         =   "Ticket(s)"
         BeginProperty Font 
            Name            =   "MS Sans Serif"
            Size            =   9.75
            Charset         =   0
            Weight          =   700
            Underline       =   0   'False
            Italic          =   0   'False
            Strikethrough   =   0   'False
         EndProperty
         ForeColor       =   &H00008000&
         Height          =   255
         Left            =   6240
         TabIndex        =   70
         Top             =   720
         Visible         =   0   'False
         Width           =   975
      End
      Begin VB.Label lLocked 
         Caption         =   "LOCKED"
         BeginProperty Font 
            Name            =   "MS Sans Serif"
            Size            =   12
            Charset         =   0
            Weight          =   700
            Underline       =   0   'False
            Italic          =   0   'False
            Strikethrough   =   0   'False
         EndProperty
         ForeColor       =   &H000000FF&
         Height          =   375
         Left            =   6240
         TabIndex        =   69
         Top             =   240
         Visible         =   0   'False
         Width           =   1095
      End
      Begin VB.Label LblClikPay 
         Caption         =   "ClikPay"
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
         Left            =   8760
         TabIndex        =   59
         Top             =   1850
         Width           =   975
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
         Top             =   1080
         Visible         =   0   'False
         Width           =   1155
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
         Top             =   1470
         Width           =   1035
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
         Left            =   5040
         TabIndex        =   54
         Top             =   2880
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
         Top             =   3255
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
         Top             =   2910
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
         Top             =   2235
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
         Top             =   750
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
         Top             =   1080
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
         Top             =   400
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
         Top             =   2565
         Width           =   1500
      End
      Begin VB.Label LblDNSIPAdd 
         Caption         =   "DNS IP Add"
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
         Top             =   2400
         Width           =   1155
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
         Top             =   1875
         Width           =   1035
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
         Left            =   8760
         TabIndex        =   44
         Top             =   1125
         Width           =   1095
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
         Left            =   8760
         TabIndex        =   43
         Top             =   1450
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
         Left            =   8760
         TabIndex        =   42
         Top             =   795
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
         Left            =   8760
         TabIndex        =   41
         Top             =   390
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
         Top             =   1440
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
         Top             =   1785
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
      Width           =   11775
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
         Begin VB.CommandButton cmdScrh 
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
Attribute VB_Name = "FrmDomain"
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
'Description : View all domains that are registered on the system          *
'              Modify the general details of a specific domain             *
'              Modify the status of a domain                               *
'              View the historical details of an existing domain           *
'              Transfer accounts                                           *
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
Dim strDNSNameIndex As Long
Dim TempTraEnabled As Boolean '20060322CC Underlying state of transfer button

'JMcA 17.11.06
Dim bGuestAccount As Boolean
Dim iOriginalAccNo As Integer
Dim NHBillCInd As String





'* Only allow editing of the status field
Private Sub cmdAlStatus_Click()

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
    cmdScrh.Enabled = False
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
Private Sub cmdCancel_Click()

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
ShowButtons True, True, True, False, False, True, False
'ComboVATStatus.Visible = False 'Celina Leong not display in phase Phoenix1.1
ComboBillStatus.Visible = False

' David Gildea Phoenix 1.2 Spec 09 31/08/04
ImageComboBillStatus.Enabled = False
ImageComboBillStatus.BackColor = &H8000000F
Me.ImageComboBillStatus.Locked = True

ComboClikPay.Visible = False
'TxtVATStatus.Visible = True 'Celina Leong not display in phase Phoenix1.1
'TxtBillStatus.Visible = True
TxtClikPay.Visible = True
'cmdAlStatus.Visible = True
'cmdTraAC.Visible = True
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
 cmdScrh.Enabled = True
 'TxtScrh.Enabled = True
 'celina Leong Phoenix1.1 28/03/03
    ComboSearch.Visible = True
    ComboClass.Visible = False
    ComboCategory.Visible = False
    TxtClass.Visible = True
    TxtCategory.Visible = True
'-----------------------------------------
End Sub

Private Sub cmdClose_Click()
Unload Me
End Sub

Private Sub PopDomain_Grid()
'Title heading name for Domain Grid


With HFlexDomains
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
Private Sub cmdDelDNSIPAdd_Click()

End Sub

'       Celina Leong Phoenix1.1 28/03/03
Private Sub cmdDelDNSName_Click()
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

'celina Leong Phoenix1.1 28/03/03
'Private Sub cmdDelete_DNS_Click()
''If TxtScrh.Text = "" Or TxtDName.Text = "" Then
''    MsgBox " Please enter details for search ", vbExclamation
'' Celina Leong Phoenix.1.1 01/04/03
'If ComboSearch.Text = "" Or TxtDName.Text = "" Then
'    MsgBox " Please enter details for search ", vbExclamation
'
'Else
'
'
'    TxtRemark.Text = ""
'    Set TxtRemark.DataSource = Nothing
'    mcsFormState = csEdit
''    mblnDeleteStatus = True
'    TxtRemark.Enabled = True
'    TxtRemark.Text = ""
'    cmdScrh.Enabled = False
' '   TxtScrh.Enabled = False
'    ComboSearch.Enabled = False 'Celina Leong Phoenix1.1 01/04/03
'    FillDNSNameComboE
'    Set ComboDNSNameE.DataSource = Nothing
'    Set TxtDNSIPAdd.DataSource = Nothing
'    ComboDNSNameE.Visible = False
'    ComboDNSName.Visible = True
'    FormatControl ComboDNSName, csEdit
' '   FormatControl TxtDNSIPAdd, csEdit
'    FormatControl TxtRemark, csEdit
'
'    ShowButtons False, False, False, False, True, True, False, True, True
'    cmdDelDNSName.Enabled = True
'    cmdDelDNSName.Visible = True
'    cmdDelDNSIPAdd.Visible = True
'    cmdDelDNSIPAdd.Enabled = True
'    cmdAlStatus.Visible = False
'    cmdTraAC.Visible = False
'
'
'End If
'
'End Sub
'-----------------------------------------------


' Changable for most of the field when Edit command click
' Change not allow for Domain Name, Acccount Name, Billing Contacta and Date Changed
Private Sub cmdEdit_Click()

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
        'ComboBillStatus.Visible = True

'Added by David Gildea 31/08/04 for ImagComboBillStatus Phoenix 1.2
' Spec 09

        ImageComboBillStatus.Enabled = True
        Me.ImageComboBillStatus.BackColor = &H8000000E
        
        
        ComboClikPay.Visible = True
'        TxtVATStatus.Visible = False
        TxtBillStatus.Visible = False
        TxtClikPay.Visible = False
'        FillComboVatStatus ComboVATStatus
'        FillComboBillStatus ComboBillStatus, ComboBillStatus
'        FillComboClikPay ComboClikPay, ComboClikPay
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
    
       
        
        cmdScrh.Enabled = False
'        OptHolder.Enabled = False 'Celina Leong Phoenix1.1 03/04/03
 '       TxtScrh.Enabled = False
'       ShowButtons True, False, False, False, True, True
'       Celina Leong Phoenix1.1 28/03/03
        ComboSearch.Enabled = False
'        cmdAlStatus.Visible = False
'        cmdDelDNSName.Visible = True
              
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
Dim lngRow As Long

'Set RsSearchResults = New ADODB.Recordset

   ' sSQL = "SELECT DNS_IpAddr,DNS_Name,DNS_Order FROM DNS " &
   '        "WHERE D_Name=" & CDBText(CmbSearchList.Text) & " " & _
   '        "AND DNS_Name =" & CDBText(ComboDNSName.Text) & " "
   If cmdEdit.Enabled = True Then       'Celina 08/05/03
'Celina Leong Phoenix1.1 Read DNS

     strSQL = "SELECT DNS_IpAddr,DNS_Name,DNS_Order FROM DNS "
     strSQL = strSQL & "WHERE D_Name=" & CDBText(CmbSearchList.Text) & " "
     strSQL = strSQL & "AND DNS_Name =" & CDBText(ComboDNSName.Text) & " "
     
       DB.Execute strSQL, rec
       
       If Not rec.EOF Then  ' Celina Leong Phoenxi1.1 26/05/03
                 
          sDNS_Order = NoNull(rec.Fields("DNS_Order"))
          TxtDNSIPAdd.Text = NoNull(rec.Fields("DNS_IpAddr"))
              
       End If
       
       FormatControl TxtDNSIPAdd, csView  ' Celina Leong Phoenxi1.1 26/05/03
   Else
'Celina Leong Phoenix1.1 Read DNS History
    
      lngRow = HFlexDomains.Row
      If lngRow <> 0 Then
    
        'strSQL = "SELECT * FROM DNSHist "
        strSQL = "SELECT DNS_IpAddr,DNS_Name,DNS_Order FROM DNSHist "
        strSQL = strSQL & "WHERE D_Name =" & CDBText(strDName) & "  "
        strSQL = strSQL & "AND DNS_Name =" & CDBText(ComboDNSName.Text) & " "
        strSQL = strSQL & "AND Chng_Dt=" & CDBTime(HFlexDomains.TextMatrix(lngRow, 4))
    
        DB.Execute strSQL, rec
         
        If Not rec.EOF Then    ' Celina Leong Phoenxi1.1 26/05/03
                 
          sDNS_Order = NoNull(rec.Fields("DNS_Order"))
          TxtDNSIPAdd.Text = NoNull(rec.Fields("DNS_IpAddr"))
              
        End If
      End If
      
      FormatControl TxtDNSIPAdd, csView  ' Celina Leong Phoenxi1.1 26/05/03
    End If
        
'Celina Leong Phoenix1.1 26/05/03
'        If Not rec.EOF Then
'
'          sDNS_Order = NoNull(rec.Fields("DNS_Order"))
'          TxtDNSIPAdd.Text = NoNull(rec.Fields("DNS_IpAddr"))
'
'        End If
  
  
'  FormatControl TxtDNSIPAdd, csView
'--------------------------------------------------
  
  
''  celina Leong Phoenix1.1 28/03/03
'  If mblnDeleteStatus = True Then
'     FormatControl TxtDNSIPAdd, csEdit
'
'  Else
'     FormatControl TxtDNSIPAdd, csView
'  End If
End Sub

'* Saves an edited record

Private Sub cmdSave_Click()

Dim ctl As Control
Dim blnError As Boolean
Dim ctlError As Control
Dim sNextMSDRun As String

Debug.Print ("Stage 1.0")
Screen.MousePointer = vbHourglass
   TempNow = Now()
    
On Error GoTo EXE_Err
   
'tsmyth oct2003 - ensure english format date on PC
Call CheckDateFormat
   
'JMcA 17.11.06 Requirement 1.3
'If it's a guest account and Admin Contact is Billing contact on another table
'then don't allow update of account number.
If (CInt(TxtAccName.Text) <> iOriginalAccNo) Then
 If bGuestAccount And BillingConOtherDomain(TxtDName.Text, TxtAdCon1.Text, TxtAdCon2.Text) Then
    MsgBox "Cannot change from Guest Account Number." & vbCrLf & "Nic Handle is listed as Billing Contact on another Domain.", vbInformation
    TxtAccName.Text = CStr(iOriginalAccNo)
    Screen.MousePointer = vbNormal
    Exit Sub
 End If
 
 
End If
   
   
   
DB.BeginTransaction
Debug.Print ("Stage 1.1")

If mcsFormState = csEdit Then
Debug.Print ("Stage 1.2")
    If mblnAlterStatus Then
    Debug.Print ("Stage 1.3")
        If IsValidStatus Then
            Debug.Print ("Stage 1.4")
            UpdateStatus blnError
            
            If ComboStatus = "Deleted" Then
                
                sSQL = "SELECT Next_MSD_Run FROM StaticTable"
                
                Set RsSearchResults = New ADODB.Recordset
                RsSearchResults.Open sSQL, CnPhoenix, adOpenDynamic, adLockOptimistic
    
                If Not RsSearchResults.EOF Then
                  sNextMSDRun = RsSearchResults("Next_MSD_Run")
                End If

                sSQL = "INSERT INTO DeleteListHist VALUES (" & CDBText(TxtDName) & "," & CDBDate(sNextMSDRun) & "," & CDBText("MANUAL") & ")"
                DB.Execute (sSQL)
                
            End If
            
         Else
            Debug.Print ("Stage 1.5")
            blnError = True
        End If
'    'Celina Leong Phoenix1.1 01/04/03
'    ElseIf mblnDeleteDNS Then
'
'        UpdateDN blnError
'        Process_Combo
'            If DNS_Changed Then
'                  Update_DNS blnError
'            End If
        
    ElseIf mblnTraAccount Then
        Debug.Print ("Stage 1.6")
        If ValidTraAccount Then
            Debug.Print ("Stage 1.7")
            UpdateTraAccount blnError
               
                If NH_Changed Then
                    Debug.Print ("Stage 1.8")
                     Update_Nichandle blnError
                End If
                Process_Combo
               If DNS_Changed Then
                     Update_DNS blnError
                End If
            
        Else
            blnError = True
        End If
 
    Else
        If IsValid Then
             
            UpdateDN blnError

            If NH_Changed Then
                  Update_Nichandle blnError
            End If
            Process_Combo
            If DNS_Changed Then
                  Update_DNS blnError
            End If
  
        Else
            blnError = True
        End If
      
    End If

   

End If

If Not blnError Then
   
    DB.CommitTransaction
    If OptDName = True Then
           Show_Results_DName CmbSearchList.Text
       
    ElseIf OptNHandle = True Then
           Show_Results_NHandle CmbSearchList.Text

    ElseIf OptAcc = True Then
           Show_Results_Acc CmbSearchList.Text
'    Else
'           Show_Results_Holder CmbSearchList.Text
    End If

    
   
    
    mcsFormState = csView
    FormatControls Me, csView
   ' ShowButtons True, True, True, False, False, True
   ' Celina Leong Phoenix1.1 28/03/03
     ShowButtons True, True, True, False, False, True, False
     cmdAlStatus.Visible = True
     cmdTraAC.Visible = True
'     ComboVATStatus.Visible = False 'Celina Leong not display in phase Phoenix1.1
     ComboBillStatus.Visible = False
     
' David Gildea Phoenix 1.2 Spec 09 31/08/04
        'ImageComboBillStatus.Enabled = False
        'ImageComboBillStatus.BackColor = &H8000000F
        
     ComboClikPay.Visible = False
'     TxtVATStatus.Visible = True 'Celina Leong not display in phase Phoenix1.1
     'TxtBillStatus.Visible = True
     TxtClikPay.Visible = True
   '-----------------------------------------------------------
    FormatControl CmbSearchList, csEdit
 '   FormatControl TxtScrh, csEdit
    FormatControl ComboSearch, csEdit 'Celina Leong Phoenix1.1 01/04/03
    FormatControl ComboDNSNameE, csEdit
    mblnAlterStatus = False
    For Each ctl In Controls

                If TypeOf ctl Is OptionButton Then
                     ctl.Enabled = True
                     ctl.Visible = True
                End If
               
            Next
            
'    OptHolder.Enabled = False 'Celina Leong Phoenix1.1 03/04/03
    ComboDNSNameE.Visible = False
    ComboDNSName.Visible = True
    ComboDNSName.Enabled = True
    ComboDNSName.Locked = False
    TxtRemark.BorderStyle = 1
    cmdScrh.Enabled = True
'    TxtScrh.Enabled = True
'celina Leong Phoenix1.1 28/03/03
    ComboSearch.Enabled = True
    ComboClass.Visible = False
    ComboCategory.Visible = False
    TxtClass.Visible = True
    TxtCategory.Visible = True
    
    ImageComboBillStatus.Enabled = False
    Me.ImageComboBillStatus.BackColor = &H8000000F
'-----------------------------------------
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
    DB.ShowError " Unable to update domain maintenance "
    blnError = True
    Screen.MousePointer = vbNormal
End Sub

Private Sub cmdScrh_Click()
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

         sSQL = "SELECT * FROM Domain " & _
                "WHERE D_Name like " & CDBText(ComboSearch & "%") & _
                "ORDER BY D_Name "
'                "WHERE D_Name like '" & ComboSearch & "%'" & _
'------------------------------------------------------
        Set RsSearch = New ADODB.Recordset
        RsSearch.Open sSQL, CnPhoenix, adOpenDynamic, adLockOptimistic
        If Not RsSearch.EOF Then
            CmbSearchList.Clear
            
            'JMcA 21.11.06 BATCH 5.2 :
            'To ensure a correct ticket-domain match, we need to use the full domain name found on domain table rather than wildcard
            FlagTicketForDomain (RsSearch("D_Name"))
            FlagFaxForDomain RsSearch("D_Name"), lblFaxes 'JMcA 21.11.06 BATCH 5.3
            
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
                'Celina Leong
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

         sSQL = "SELECT * FROM Domain " & _
               "WHERE D_Holder like " & CDBText("%" & ComboSearch & "%") & _
               "ORDER BY D_Name "
'               "WHERE D_Holder like '%" & ComboSearch & "%'" & _

        Set RsSearch = New ADODB.Recordset
        RsSearch.Open sSQL, CnPhoenix, adOpenDynamic, adLockOptimistic
        If Not RsSearch.EOF Then
            CmbSearchList.Clear
            
            'JMcA 21.11.06 BATCH 5.2 :
            'To ensure a correct ticket-domain match, we need to use the full domain name found on domain table rather than wildcard
            FlagTicketForDomain (RsSearch("D_Name"))
            'FlagFaxForDomain (RsSearch("D_Name")) 'JMcA 21.11.06 BATCH 5.3
            
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
         
'         sSQL = "SELECT DISTINCT D_Name FROM Contact " & _
'                "WHERE Contact_NH like '" & TxtScrh & "%'" & _
'                "ORDER BY D_Name "
'Celina Leong Phonenix1.1 01/04/03

           sSQL = "SELECT DISTINCT D_Name FROM Contact " & _
                  "WHERE Contact_NH like " & CDBText(ComboSearch & "%") & _
                  "ORDER BY D_Name "
'                   "WHERE Contact_NH like '" & ComboSearch & "%'" & _
'-------------------------------------------------------------
        Set RsSearch = New ADODB.Recordset
        RsSearch.Open sSQL, CnPhoenix, adOpenDynamic, adLockOptimistic
        If Not RsSearch.EOF Then
            CmbSearchList.Clear
            
            'JMcA 21.11.06 BATCH 5.2 :
            'To ensure a correct ticket-domain match, we need to use the full domain name found on domain table rather than wildcard
            FlagTicketForDomain (RsSearch("D_Name"))
            'FlagFaxForDomain (RsSearch("D_Name")) 'JMcA 21.11.06 BATCH 5.3
            
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
    
'        If IsNumeric(TxtScrh) Then 'celina Leong Phoenix1.1
         If IsNumeric(ComboSearch) Then
                     sSQL = "SELECT * FROM Domain " & _
                               "WHERE A_Number = " & ComboSearch & " " & _
                               "ORDER BY D_Name "
                 
                  
                     Set RsSearch = New ADODB.Recordset
                     RsSearch.Open sSQL, CnPhoenix, adOpenDynamic, adLockOptimistic
                
                     
                     If Not RsSearch.EOF Then
                         CmbSearchList.Clear
                         
                        'JMcA 21.11.06 BATCH 5.2 :
                        'To ensure a correct ticket-domain match, we need to use the full domain name found on domain table rather than wildcard
                        FlagTicketForDomain (RsSearch("D_Name"))
                        'FlagFaxForDomain (RsSearch("D_Name")) 'JMcA 21.11.06 BATCH 5.3
                         
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
  
  
        
'    Else
'
'        'MsgBox " Under construction !"
'        MsgBox "Option not available ", vbOKOnly
'        TxtScrh.Text = ""
'        CmbSearchList.Text = ""
'        OptDName.SetFocus
        
        Screen.MousePointer = vbDefault
    
    End If


   'JMcA 17.11.06 Req 1.3
   If Len(TxtAccName.Text) > 0 Then
   
       Set RsSearch = New ADODB.Recordset
    
       iOriginalAccNo = CInt(TxtAccName.Text)
       bGuestAccount = False
       If (iOriginalAccNo = 1) Then
          bGuestAccount = True
       End If
    
      sSQL = "Select NH_BillC_Ind FROM NicHandle WHERE A_Number = " & TxtAccName.Text
      RsSearch.Open sSQL, CnPhoenix, adOpenDynamic, adLockOptimistic
      
      If Not RsSearch.EOF Then
        NHBillCInd = RsSearch.Fields("NH_BillC_Ind")
      End If
      
      RsSearch.Close
      
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

Private Sub cmdTraAC_Click()
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
        cmdScrh.Enabled = False
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
    

If IsValidDNS Then
    Process_Combo
    ListIPAdd.ListIndex = ComboDNSNameE.ListIndex
    TxtDNSIPAdd.Text = ListIPAdd.Text
    TxtDNSIPAdd.DataChanged = False
    mlngPrevIndex = ComboDNSNameE.ListIndex
    mstrPrevText = ComboDNSNameE.Text
    ComboDNSNameE.List(0) = "(NEW)"
Else
    ComboDNSNameE.List(0) = mstrPrevText
    
End If
End Sub
Private Sub Process_Combo()
Dim lngtempsortorder As Long
    If mblnProcessingCombo Then Exit Sub
    mblnProcessingCombo = True
  
    If mlngPrevIndex = -1 Then
        If mlngTemp = 0 Then    '/* new item */
       
                ComboDNSNameE.AddItem mstrPrevText
                If TxtDNSIPAdd.Text = "(NEW)" Then
                   TxtDNSIPAdd.Text = ""
                End If
                
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

Private Sub ComboDNSNameE_KeyPress(KeyAscii As Integer)
Dim sSearchString As String, iStrPos As Integer, iDotCount As Integer

Select Case KeyAscii
    Case 45
    Case 97 To 122
    Case 48 To 57
    Case 8
    Case 46
    Case 65 To 90
    Case Else
        KeyAscii = 0
End Select
 
End Sub

Private Sub ComboDNSNameE_LostFocus()
'Celina Leong Phoenix1.1 08/05/03
'Validate DNS before Process_combo
 If IsValidDNS Then
    Process_Combo
    mlngPrevIndex = ComboDNSNameE.ListIndex
    mstrPrevText = ComboDNSNameE.Text
    If ComboDNSNameE.ListIndex > -1 Then
         mlngTemp = ComboDNSNameE.ListIndex
    End If
 End If

End Sub


Private Sub Form_KeyDown(KeyCode As Integer, Shift As Integer)
    Select Case KeyCode
    Case vbKeyF4     '* Alter Status
        If cmdAlStatus.Enabled Then
            cmdAlStatus.Value = True
        End If
    Case vbKeyF8    '* Transfer Account
        If cmdTraAC.Enabled Then
            cmdTraAC.Value = True
        End If
    Case vbKeyF2    '* Edit
        If cmdEdit.Enabled Then
            cmdEdit.Value = True
        End If
    Case vbKeyF5    '* Cancel
        If cmdCancel.Enabled Then
            cmdCancel.Value = True
        End If
    Case vbKeyF6    '* Save
        If cmdSave.Enabled Then
            cmdSave.Value = True
        End If
    Case vbKeyF7    '* Close
        If cmdClose.Enabled Then
            cmdClose.Value = True
        End If
    Case vbKeyF3    '* Search
        If cmdScrh.Enabled Then
            cmdScrh.Value = True
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
ShowButtons True, True, True, False, False, True, False
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
'Celina Leong Phoenix1.1 25/04/03
FillComboBillStatus ComboBillStatus



' David Gildea Phoenix 1.2 Spec 09 31/08/04
FillImageComboBillStatus Me.ImageComboBillStatus, Me.BillStatusList
ImageComboBillStatus.Enabled = False
ImageComboBillStatus.Locked = True
ImageComboBillStatus.BackColor = &H8000000F


FillComboClikPay ComboClikPay
'---------------------------------------------



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

Private Sub Form_Unload(Save As Integer)
If cmdSave.Enabled = True Then
    If MsgBox("Do you want to save the changes " & _
              "you made ? ", vbQuestion + vbYesNo) = vbNo Then
              
        Save = False
    
    Else
        cmdSave_Click
       
    End If
End If

End Sub

Private Sub HFlexDomains_DblClick()
Dim lngRow As Long

If Not mblnRecordLoaded Then Exit Sub
    lngRow = HFlexDomains.Row
    If lngRow <> 0 Then
   
   
     
         If mcsFormState = csView Then
              
'                strDName = CmbSearchList.Text
'                Assign_DN_History strDName
'                Assign_DNS_History strDName
'                Assign_Contact_History strDName
    
'                ShowButtons False, False, True, True, False, True
'Celina Leong - Phoenix1.1 26/03/03
                 ShowButtons False, False, True, True, False, True, False
                cmdEdit.Enabled = False
                CmbSearchList.Enabled = False
                cmdScrh.Enabled = False
                
                strDName = CmbSearchList.Text
                Assign_DN_History strDName
                Assign_DNS_History strDName
                Assign_Contact_History strDName
         End If
         
    End If
     
End Sub




Private Sub HFlexDomains_KeyDown(KeyCode As Integer, Shift As Integer)
        If KeyCode = vbKeyReturn Then
            If mblnRecordLoaded Then
               HFlexDomains_DblClick
            End If
         End If
End Sub

Private Sub HFlexDomains_SelChange()
        HFlexDomains.RowSel = HFlexDomains.Row
End Sub









'Celina Leong 20-09-2002

Private Sub TxtAccName_Validate(KeepFocus As Boolean)
'    If TxtAccName = 1 Then
    If (Val(Trim(TxtAccName)) = 1) And (mcsFormState = csEdit) Then 'CC20051221 only make Billing contact editable if the domain is editable
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



Private Sub TxtDNSIPAdd_LostFocus()
 Dim IPDot%, Length%, i%
 Dim strDNS As String
 
 
 If TxtDNSIPAdd.Text = "(NEW)" Then
    TxtDNSIPAdd.Text = ""

 Else
    If Not TxtDNSIPAdd.Text = "" Then
            strDNS = TxtDNSIPAdd.Text
            IPDot% = 0
            Length% = Len(strDNS)
            For i% = 1 To Length%
                If Mid(strDNS, i%, 1) = "." Then
                    IPDot% = IPDot% + 1
                End If
            Next i%

        If IPDot% <> 3 Then
            MsgBox "DNS IP Address " & "'" & TxtDNSIPAdd & "'" & " must be 3 '.' in the field", vbExclamation
            TxtDNSIPAdd.SetFocus
          
        End If
    End If
End If
End Sub

Private Sub TxtRemark_GotFocus()
  If Not TxtRemark.Locked Then
        cmdSave.Default = False
  End If
End Sub

Private Sub TxtRemark_LostFocus()
    If Not TxtRemark.Locked Then
        cmdSave.Default = True
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

strSQL = "Select * From Domain where D_Name = " & CDBText(strDName)
DB.Execute strSQL, rec

If Not rec.EOF Then
    
    mstrTimeStamp = NoNull(rec.Fields("D_TStamp"))
    mblnRecordLoaded = True
    LoadDomainHistory strDName
    
Else
    mblnRecordLoaded = False
End If

Set RsSearchResults = New ADODB.Recordset

'----------------------------------------------------------------------------------------
'  sSQL = "SELECT Domain.D_Name, Domain.D_Holder, " & _
'               "Domain.D_Class, Domain.D_Category, Domain.A_Number, " & _
'               "Domain.D_Status, Domain.D_Status_Dt, Domain.D_Reg_Dt, Domain.D_Ren_Dt, " & _
'               "Domain.D_Remark, DNS.DNS_Name, DNS.DNS_IpAddr,DNS.DNS_Order " & _
'               "FROM DNS "
        


'Celina Leong - Phoenix1.1 26/03/03
'Change the SQL String to included D_Bill_Status,D_VAT_Status and D_ClikPaid
             
        sSQL = "SELECT Domain.D_Name, Domain.D_Holder, " & _
               "Domain.D_Class, Domain.D_Category, Domain.A_Number, " & _
               "Domain.D_Bill_Status, Domain.D_VAT_Status, Domain.D_ClikPaid, " & _
               "Domain.D_Status, Domain.D_Status_Dt, Domain.D_Reg_Dt, Domain.D_Ren_Dt, " & _
               "Domain.D_Remark, DNS.DNS_Name, DNS.DNS_IpAddr,DNS.DNS_Order " & _
               "FROM DNS "
               
'---------------------------------------------------------------------------------------

        sSQL = sSQL & "INNER JOIN Domain " & _
               "ON Domain.D_Name =DNS.D_Name " & _
               "WHERE Domain.D_Name = " & CDBText(strDName) & ""
          
              
               
       
         Set RsSearchResults = New ADODB.Recordset
        RsSearchResults.Open sSQL, CnPhoenix, adOpenDynamic, adLockOptimistic
       
      
      
LoadDomains


       s1SQL = " SELECT Contact_NH, Type FROM Contact " & _
         "WHERE  D_Name =" & CDBText(strDName) & "  "
         
          
        Set Rs1SearchResults = New ADODB.Recordset
        
        Rs1SearchResults.Open s1SQL, CnPhoenix, adOpenDynamic, adLockOptimistic

LoadNicHandle

Assign_IPAddress

mDName = strDName

TxtAccName = Format(TxtAccName, gstrAccountNoPad)

'--------------------------------------------------20060322CC
'View locked status and availability of transfer button
    lLocked.Visible = DomainLocked() 'set visibility of "Locked" label
    cmdTraAC.Enabled = TempTraEnabled And Not lLocked.Visible

End Sub

'Celina Leong Phoenix1.1 03/04/03
'***************************************************************************
' Show domain detail that exist in the system for a specific domain holder.  *
'***************************************************************************


Public Sub Show_Results_Holder(ByVal strDName As String)
Dim rec As ADODB.Recordset
Dim strSQL As String

strSQL = "Select * From Domain where D_Name = " & CDBText(CmbSearchList.Text)
DB.Execute strSQL, rec

If Not rec.EOF Then
    
    mstrTimeStamp = NoNull(rec.Fields("D_TStamp"))
    mblnRecordLoaded = True
    LoadDomainHistory strDName
Else
    mblnRecordLoaded = False
  
End If


Set RsSearchResults = New ADODB.Recordset

'----------------------------------------------------------------------------------------
'  sSQL = "SELECT Domain.D_Name, Domain.D_Holder, " & _
'               "Domain.D_Class, Domain.D_Category, Domain.A_Number, " & _
'               "Domain.D_Status, Domain.D_Status_Dt, Domain.D_Reg_Dt, Domain.D_Ren_Dt, " & _
'               "Domain.D_Remark, DNS.DNS_Name, DNS.DNS_IpAddr,DNS.DNS_Order " & _
'               "FROM DNS "
        


'Celina Leong - Phoenix1.1 26/03/03
'Change the SQL String to included D_Bill_Status,D_VAT_Status and D_ClikPaid
             
        sSQL = "SELECT Domain.D_Name, Domain.D_Holder, " & _
               "Domain.D_Class, Domain.D_Category, Domain.A_Number, " & _
               "Domain.D_Bill_Status, Domain.D_VAT_Status, Domain.D_ClikPaid, " & _
               "Domain.D_Status, Domain.D_Status_Dt, Domain.D_Reg_Dt, Domain.D_Ren_Dt, " & _
               "Domain.D_Remark, DNS.DNS_Name, DNS.DNS_IpAddr,DNS.DNS_Order " & _
               "FROM DNS "
               
'---------------------------------------------------------------------------------------

        sSQL = sSQL & "INNER JOIN Domain " & _
               "ON Domain.D_Name =DNS.D_Name " & _
               "WHERE Domain.D_Name = " & CDBText(strDName) & ""
          
              
               
       
         Set RsSearchResults = New ADODB.Recordset
        RsSearchResults.Open sSQL, CnPhoenix, adOpenDynamic, adLockOptimistic
       
      
      
LoadDomains


       s1SQL = " SELECT Contact_NH, Type FROM Contact " & _
         "WHERE  D_Name =" & CDBText(strDName) & "  "
         
          
        Set Rs1SearchResults = New ADODB.Recordset
        
        Rs1SearchResults.Open s1SQL, CnPhoenix, adOpenDynamic, adLockOptimistic

LoadNicHandle

Assign_IPAddress

mDName = strDName

TxtAccName = Format(TxtAccName, gstrAccountNoPad)

'--------------------------------------------------20060322CC
'View locked status and availability of transfer button
    lLocked.Visible = DomainLocked() 'set visibility of "Locked" label
    cmdTraAC.Enabled = TempTraEnabled And Not lLocked.Visible

End Sub
'--------------------------------------------------------------------------------

'***************************************************************************
' Show domain detail that exist in the system for a specific Nichandle.    *
'***************************************************************************

Public Sub Show_Results_NHandle(ByVal strDName As String)
Dim rec As ADODB.Recordset
Dim strSQL As String

strSQL = "Select * From Domain where D_Name = " & CDBText(CmbSearchList.Text) '(StrDName) '(TxtDName)
 DB.Execute strSQL, rec

If Not rec.EOF Then
    mstrTimeStamp = NoNull(rec.Fields("D_TStamp"))
    mblnRecordLoaded = True
    LoadDomainHistory strDName
Else
    mblnRecordLoaded = False
       
End If


Set RsSearchResults = New ADODB.Recordset

'--------------------------------------------------------------------------------------------------------
 sSQL = "SELECT DISTINCT Contact.Contact_NH,Domain.D_Name, " & _
           "Contact.Type,Domain.D_Holder, " & _
           "Domain.D_Class,Domain.D_Category, " & _
           "Domain.A_Number,Domain.D_Status, " & _
           "Domain.D_Status_Dt,Domain.D_Reg_Dt, " & _
           "Domain.D_Ren_Dt,Domain.D_Vat_Status, Domain.D_Remark, " & _
           "DNS.D_Name, DNS.DNS_Name, DNS.DNS_IpAddr, DNS.DNS_Order " & _
           "FROM Contact, DNS "

'Celina Leong - Phoenix1.1 26/03/03
'Change the SQL String to included D_Bill_Status,D_VAT_Status and D_ClikPaid

    sSQL = "SELECT DISTINCT Contact.Contact_NH,Domain.D_Name, " & _
           "Contact.Type,Domain.D_Holder, " & _
           "Domain.D_Class,Domain.D_Category, " & _
           "Domain.A_Number,Domain.D_Status, " & _
           "Domain.D_Status_Dt,Domain.D_Reg_Dt, " & _
           "Domain.D_Bill_Status, Domain.D_ClikPaid, " & _
           "Domain.D_Ren_Dt,Domain.D_Vat_Status, Domain.D_Remark, " & _
           "DNS.D_Name, DNS.DNS_Name, DNS.DNS_IpAddr, DNS.DNS_Order " & _
           "FROM DNS, Contact "
      
 '-----------------------------------------------------------------------------
 
    sSQL = sSQL & "INNER JOIN Domain " & _
           "ON Contact.D_Name=Domain.D_Name " & _
           "WHERE Contact.D_Name = " & CDBText(CmbSearchList.Text) & " " & _
           "AND Contact.D_Name=DNS.D_Name "

                                                       
        Set RsSearchResults = New ADODB.Recordset
        RsSearchResults.Open sSQL, CnPhoenix, adOpenDynamic, adLockOptimistic

LoadDomains
           
         
        s1SQL = "SELECT Contact_NH, Type FROM Contact " & _
                "WHERE  D_Name = " & CDBText(CmbSearchList.Text) & " "

          
        Set Rs1SearchResults = New ADODB.Recordset
        
        Rs1SearchResults.Open s1SQL, CnPhoenix, adOpenDynamic, adLockOptimistic
        
 
  
LoadNicHandle
  Assign_IPAddress
  mDName = strDName
 'LoadDomainHistory strDName
 TxtAccName = Format(TxtAccName, gstrAccountNoPad)

'--------------------------------------------------20060322CC
'View locked status and availability of transfer button
    lLocked.Visible = DomainLocked() 'set visibility of "Locked" label
    cmdTraAC.Enabled = TempTraEnabled And Not lLocked.Visible

End Sub

'***************************************************************************
' Show domain detail that exist in the system for a specific Account no    *
'***************************************************************************
Private Sub Show_Results_Acc(ByVal strDName As String)
Dim rec As ADODB.Recordset
Dim strSQL As String

        strSQL = "Select * From Domain where D_Name = " & CDBText(CmbSearchList.Text)
         DB.Execute strSQL, rec

If Not rec.EOF Then
    mstrTimeStamp = NoNull(rec.Fields("D_TStamp"))
    mblnRecordLoaded = True
    LoadDomainHistory strDName
Else
    mblnRecordLoaded = False
       
End If
    
Set RsSearchResults = New ADODB.Recordset

'--------------------------------------------------------------------------------------
'    sSQL = "SELECT Domain.D_Name, Domain.D_Holder, " & _
'               "Domain.D_Class, Domain.D_Category, Domain.A_Number, " & _
'               "Domain.D_Status, Domain.D_Status_Dt, Domain.D_Reg_Dt, Domain.D_Ren_Dt, " & _
'               "Domain.D_Remark, DNS.DNS_Name, DNS.DNS_IpAddr,DNS.DNS_Order " & _
'               "FROM DNS "
               
'Celina Leong - Phoenix1.1 26/03/03
'Change the SQL String to included D_Bill_Status,D_VAT_Status and D_ClikPaid
             
        sSQL = "SELECT Domain.D_Name, Domain.D_Holder, " & _
               "Domain.D_Class, Domain.D_Category, Domain.A_Number, " & _
               "Domain.D_Bill_Status, Domain.D_VAT_Status, Domain.D_ClikPaid, " & _
               "Domain.D_Status, Domain.D_Status_Dt, Domain.D_Reg_Dt, Domain.D_Ren_Dt, " & _
               "Domain.D_Remark, DNS.DNS_Name, DNS.DNS_IpAddr,DNS.DNS_Order " & _
               "FROM DNS "
               
'----------------------------------------------------------------------------------------
        sSQL = sSQL & "INNER JOIN Domain " & _
               "ON Domain.D_Name =DNS.D_Name " & _
               "WHERE Domain.D_Name = " & CDBText(CmbSearchList.Text) & ""
          
              
               
       
         Set RsSearchResults = New ADODB.Recordset
        RsSearchResults.Open sSQL, CnPhoenix, adOpenDynamic, adLockOptimistic
       
      
        
    
      
LoadDomains


       s1SQL = " SELECT Contact_NH, Type FROM Contact " & _
         "WHERE  D_Name =" & CDBText(CmbSearchList.Text) & "  "
         
          
        Set Rs1SearchResults = New ADODB.Recordset
        
        Rs1SearchResults.Open s1SQL, CnPhoenix, adOpenDynamic, adLockOptimistic

LoadNicHandle

Assign_IPAddress

mDName = strDName

TxtAccName = Format(TxtAccName, gstrAccountNoPad)

'--------------------------------------------------20060322CC
'View locked status and availability of transfer button
    lLocked.Visible = DomainLocked() 'set visibility of "Locked" label
    cmdTraAC.Enabled = TempTraEnabled And Not lLocked.Visible

End Sub

Private Sub LoadDomains()
Dim ctl As Control
Dim sTemp As String
Dim rec    As ADODB.Recordset
Dim strSQL As String

For Each ctl In Controls

    If TypeOf ctl Is ComboBox Then
        sTemp = ctl.Name
'        If UCase(ctl.Name) <> "TXTSCRH" Then 'Celina Leong Phoenix1.1
        If UCase(ctl.Name) <> "Combosearch" Then
            Set ctl.DataSource = RsSearchResults
        End If
    End If
Next

    With TxtDName
         sTemp = TxtDName.Name
'         If CDBText(TxtDName.Name) <> "TXTSCRH" Then 'Phoenix1.1
         If CDBText(TxtDName.Name) <> "Combosearch" Then
            Set TxtDName.DataSource = RsSearchResults
         End If
    End With
      
    With TxtDHolder
        sTemp = TxtDHolder.Name
         If CDBText(TxtDHolder.Name) <> "Combosearch" Then
            Set TxtDHolder.DataSource = RsSearchResults
         End If
    End With
    
    With TxtAccName
        sTemp = TxtAccName.Name
         If UCase(TxtAccName.Name) <> "Combosearch" Then
            Set TxtAccName.DataSource = RsSearchResults
           
         End If
    End With
     
     
    With TxtRegDate
        sTemp = TxtRegDate.Name
         If TxtRegDate.Name <> "Combosearch" Then
            Set TxtRegDate.DataSource = RsSearchResults
        End If

    End With


    With TxtRenewDate
        sTemp = TxtRenewDate.Name
         If TxtRenewDate.Name <> "Combosearch" Then
            Set TxtRenewDate.DataSource = RsSearchResults
         End If

    End With

    
    
    With TxtChanged
        sTemp = TxtChanged.Name
         If UCase(TxtChanged.Name) <> "Combosearch" Then
            Set TxtChanged.DataSource = RsSearchResults
         End If
    
    End With
    
'
'    With ComboCategory
'        sTemp = ComboCategory.Name
'         If UCase(ComboCategory.Name) <> "TXTSCRH" Then
'            Set ComboCategory.DataSource = RsSearchResults
'         End If
'
'    End With

'Celina Leong - Phoenix1.1 26/03/03
'Included Bill Status,VAT Status and ClikPaid
'Modify category list box to pick up existing class when editing



     With TxtClass
        sTemp = TxtClass.Name
         If UCase(TxtClass.Name) <> "Combosearch" Then
            Set TxtClass.DataSource = RsSearchResults
         End If
    
    End With
    
    With TxtCategory
        sTemp = TxtCategory.Name
         If UCase(TxtCategory.Name) <> "Combosearch" Then
            Set TxtCategory.DataSource = RsSearchResults
         End If
    End With
   
'    With ComboBillStatus
'        sTemp = ComboBillStatus.Name
'        If UCase(ComboBillStatus.Name) <> "Combosearch" Then
'            Set ComboBillStatus.DataSource = RsSearchResults
'        End If
'    End With
    
'    With ComboVATStatus
'        sTemp = ComboVATStatus.Name
'        If UCase(ComboVATStatus.Name) <> "Combosearch" Then
'            Set ComboVATStatus.DataSource = RsSearchResults
'        End If
'    End With
'
'    With ComboClikPay
'        sTemp = ComboClikPay.Name
'        If UCase(ComboClikPay.Name) <> "Combosearch" Then
'            Set ComboClikPay.DataSource = RsSearchResults
'        End If
'    End With
   
   strSQL = "Select * From Domain where D_Name = " & CDBText(CmbSearchList.Text)
   DB.Execute strSQL, rec
   If Not rec.EOF Then

   On Error Resume Next
   ComboClass = NoNull(rec.Fields("D_Class"))
   FillCategoryCombo ComboCategory, ComboClass
   ComboCategory = NoNull(rec.Fields("D_Category"))
   TxtVATStatus = NoNull(rec.Fields("D_Vat_Status"))
   TxtClikPay = NoNull(rec.Fields("D_ClikPaid"))
   
   'TxtBillStatus = NoNull(rec.Fields("D_Bill_Status"))
   
   'Celina Leong Phoenix1.1 25/04/03
   ComboClikPay = NoNull(rec.Fields("D_ClikPaid"))
   
   ''ComboBillStatus = NoNull(rec.Fields("D_Bill_Status"))
   
    '-----------------------------------------------------
   End If

 
 '---------------------------------------------------------------
    strAcNo = Val(Trim(TxtAccName))
    Assign_Remark
   ' FillDNSNameComboE 30/05
    FillDNSNameCombo
      
   '''''''''''''''''''''''''''''''''''''''''''''
   '' Added by David Gildea on 31/08/04
   '' Phoenix 1.2 spec 09
   ''''''''''''''''''''''''''''''''''''''''''''
   ' For each item in the image combo box, check to see if
   ' its key is = rec.Fields("D_Bill_Status").
   ' If so then make this the current selection
   Dim i As Integer
   
   
   For i = 1 To Me.ImageComboBillStatus.ComboItems.Count
   
    Debug.Print Me.ImageComboBillStatus.ComboItems.Item(i).Key
        If (Me.ImageComboBillStatus.ComboItems.Item(i).Key = rec.Fields("D_Bill_Status")) Then
            Me.ImageComboBillStatus.ComboItems.Item(i).Selected = True
        End If
   Next
   
   
   ''''''''''''''''''''''''''''''''''''''''''''
   
End Sub
Private Sub Assign_Remark()
  
         Set TxtRemark.DataSource = RsSearchResults
         TxtRemark = Replace(TxtRemark.Text, "%", vbCrLf)

End Sub

Private Sub LoadNicHandle()
Dim TxtTypeA As String

            TxtAdCon1.Text = ""
            TxtBilCon.Text = ""
            TxtTechCon.Text = ""
            TxtAdCon2.Text = ""
            TxtAdCon1.ToolTipText = ""
            TxtBilCon.ToolTipText = ""
            TxtTechCon.ToolTipText = ""
            TxtAdCon2.ToolTipText = ""
            strAdCon2 = ""
            

  While Not Rs1SearchResults.EOF
            Select Case Rs1SearchResults.Fields("Type").Value
            Case "A"
If TxtAdCon1.Text = "" Then '20060424 Admin contacts are now all "A"; not "A"&"2"
                TxtAdCon1.Text = Rs1SearchResults.Fields("Contact_NH").Value
'                TxtAdCon1.ToolTipText = Rs1SearchResults.Fields("Contact_NH").Value
                 TxtAdCon1.ToolTipText = GetName(TxtAdCon1.Text)
                 TempAdmin1 = TxtAdCon1 'Celina Leong Phoenix 1.1 11/04/03
Else '20060424 Admin contacts are now all "A"; not "A"&"2"
                TxtAdCon2.Text = Rs1SearchResults.Fields("Contact_NH").Value
                TxtAdCon2.ToolTipText = GetName(TxtAdCon2.Text)
                strAdCon2 = TxtAdCon2.Text
End If '20060424 Admin contacts are now all "A"; not "A"&"2"

''Celina Leong 02/05/03 code for future used
''If more then one type "A" fill the second one into Admin Contact 2
''                TxtTypeA = Rs1SearchResults.Fields("Contact_NH").Value
''                If TxtAdCon1.Text = "" Then
''                   TxtAdCon1.Text = UCase(TxtTypeA)
''                   TxtAdCon1.ToolTipText = GetName(TxtAdCon1.Text)
''                   TempAdmin1 = TxtAdCon1
''                Else
''                If TxtTypeA <> TxtAdCon1.Text Then
''                   TxtAdCon2.Text = UCase(TxtTypeA)
''                   TxtAdCon2.ToolTipText = GetName(TxtAdCon2.Text)
''                   strAdCon2 = TxtAdCon2.Text
''                End If
''                End If
''---------------------------------------------------------
            Case "B"
                TxtBilCon.Text = Rs1SearchResults.Fields("Contact_NH").Value
                strOldBilCon = TxtBilCon ' celina leong 20-09-2002
'                TxtBilCon.ToolTipText = Rs1SearchResults.Fields("Contact_NH").Value
                 TxtBilCon.ToolTipText = GetName(TxtBilCon.Text)
      
            Case "T"
                TxtTechCon.Text = Rs1SearchResults.Fields("Contact_NH").Value
'                TxtTechCon.ToolTipText = Rs1SearchResults.Fields("Contact_NH").Value
                 TxtTechCon.ToolTipText = GetName(TxtTechCon.Text)
                 TempTechC = TxtTechCon 'Celina Leong Phoenix 1.1 11/04/03
            Case "2"
                TxtAdCon2.Text = Rs1SearchResults.Fields("Contact_NH").Value
'                TxtAdCon2.ToolTipText = Rs1SearchResults.Fields("Contact_NH").Value
                TxtAdCon2.ToolTipText = GetName(TxtAdCon2.Text)
                strAdCon2 = TxtAdCon2.Text
          
            End Select
            
            Rs1SearchResults.MoveNext
         
    Wend
     
     
  
  
 

   
End Sub
Private Sub Assign_IPAddress()
    Dim sTemp As String
    
    
    sSQL = " SELECT DNS_Name, DNS_IpAddr FROM DNS " & _
         "WHERE  D_Name = " & CDBText(CmbSearchList.Text) & " " & _
         "ORDER BY DNS_Order "
         
     
        Set RsSearchResults = New ADODB.Recordset
    
        RsSearchResults.Open sSQL, CnPhoenix, adOpenDynamic, adLockOptimistic

   With ComboDNSName
        sTemp = ComboDNSName.Name
         If UCase(ComboDNSName.Name) <> "Combosearch" Then
            Set ComboDNSName.DataSource = RsSearchResults
         End If
    End With
    


    With TxtDNSIPAdd
        sTemp = TxtDNSIPAdd.Name
         If UCase(TxtDNSIPAdd.Name) <> "Combosearch" Then
            Set TxtDNSIPAdd.DataSource = RsSearchResults
         End If
    
    End With
    
     With ComboDNSNameE
        sTemp = ComboDNSNameE.Name
         If UCase(ComboDNSNameE.Name) <> "TXTSCRH" Then
            Set ComboDNSNameE.DataSource = RsSearchResults
         End If
    End With
    

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
 cmdAlStatus.Enabled = blnAlStatus And blnViewAllowed
 cmdTraAC.Enabled = blnTraAcc And blnxferAllowed 'blnxferAllowed
 TempTraEnabled = cmdTraAC.Enabled
 cmdEdit.Enabled = blnEdit And blnEditAllowed
 cmdCancel.Enabled = blnCancel
 cmdSave.Enabled = blnSave And blnViewAllowed
 cmdClose.Enabled = blnClose
 CmbSearchList.Enabled = Not blnSave
 'TxtScrh.Enabled = Not blnSave
 ' Celina Leong Phoenix1.1 28/03/03
 blnDeleteAllowed = HasAuthority(atLeadHostMaster + atSuperUser)
 cmdDelDNSName.Enabled = blnDeleteDNSName
 
 '---------------------------------------------
 If blnSave And blnEditAllowed And blnxferAllowed And blnViewAllowed Then cmdSave.Default = True
 If blnCancel And blnEditAllowed And blnxferAllowed And blnViewAllowed Then cmdCancel.Cancel = True

'--------------------------------------------------20060322CC
'View locked status and availability of transfer button
    lLocked.Visible = DomainLocked() 'set visibility of "Locked" label
    cmdTraAC.Enabled = TempTraEnabled And Not lLocked.Visible
  
End Sub

'20060322CC Check for Locked Domain
Private Function DomainLocked() As Boolean
Dim rec As ADODB.Recordset
Dim strSQL As String
    If Len(Trim(mDName)) = 0 Then DomainLocked = False: Exit Function
    strSQL = "select D_Name from D_Locked where D_Name = " & CDBText(mDName)
    DB.Execute strSQL, rec
    DomainLocked = Not rec.EOF
End Function

'Celina Leong 18-09-2002
'Send mail to Old Billing Contact

Private Sub MailToOldBilCon(ByRef blnError As Boolean)
 Dim strEmail  As String
 Dim strHeader As String
 Dim strBody   As String
 Dim strFooter As String
 
 On Error GoTo MailSendERROR
 
 strEmail = GetEmail(strOldBilCon)

'---------------------------------------------------------------------------------------------------------------
' strHeader = "PLEASE DO NOT REPLY TO THIS EMAIL.  THIS IS AN AUTOMATED EMAIL. " & vbCrLf & vbCrLf
'' strHeader = strHeader & "Please Note Billing Contact : " & UCase(strOldBilCon) & vbCrLf
'' strHeader = strHeader & "has been transferred to Billing Contact : " & UCase(TxtBilCon) & vbCrLf
'' strHeader = strHeader & "and Account No Transfer From : " & Format(strAcNo, gstrAccountNoPad) & _
''                                       " To : " & Format(TxtAccName, gstrAccountNoPad) & vbCrLf & vbCrLf
' strHeader = strHeader & "Please Note Domain Name : " & TxtDName & vbCrLf
' strHeader = strHeader & "Billing Contact : " & UCase(strOldBilCon) & _
'                         " has been transferred to Billing Contact : " & UCase(TxtBilCon) & vbCrLf
' strHeader = strHeader & "and Account No Transfer From : " & Format(strAcNo, gstrAccountNoPad) & _
'                                       " To : " & Format(TxtAccName, gstrAccountNoPad) & vbCrLf & vbCrLf
'
' strFooter = vbCrLf & vbCrLf & "Kind regards" & vbCrLf & vbCrLf & "IE Domain Registry Hostmaster"
'
'
'  strBody = strBody & "Remarks : " & TxtRemark & vbCrLf & vbCrLf
 
'Celina Leong - Phoenix1.1 27/03/03
'Change wording for transfering account

 strHeader = "PLEASE DO NOT REPLY TO THIS E-MAIL.  THIS IS AN AUTOMATED E-MAIL. " & vbCrLf & vbCrLf
 strHeader = strHeader & "Please Note Domain Name : " & TxtDName & " held by " & TxtDHolder & vbCrLf
 strHeader = strHeader & "Billing Contact : " & UCase(strOldBilCon) & _
                         " has been transferred to Billing Contact : " & UCase(TxtBilCon) & "." & vbCrLf & vbCrLf
' strHeader = strHeader & "and Account No Transfer From : " & Format(strAcNo, gstrAccountNoPad) & _
'                                       " To : " & Format(TxtAccName, gstrAccountNoPad) & vbCrLf & vbCrLf

 strFooter = vbCrLf & vbCrLf & "Kind regards," & vbCrLf & vbCrLf & "IE Domain Registry Hostmaster"
 
 strBody = strBody & "If the nameserver entries are to be updated, the administrative contact " & vbCrLf
 strBody = strBody & "or billing contact for this domain should logon to the online modification " & vbCrLf
 strBody = strBody & "generator on our website with their current user ID and password to make " & vbCrLf
 strBody = strBody & "any necessary changes. " & vbCrLf & vbCrLf
 
 

'-----------------------------------------------------------------------------------------------------------------
'Occur Send1
 FrmSendMail.SendMail blnError, _
                      DB.StaticData(ustSmtp), _
                      DB.StaticData(ustSmtpPort), _
                      strEmail, _
                      DB.StaticData(ustReplyAddr), _
                      "Account Transfer Accepted : " & TxtDName, _
                      strHeader & strBody & strFooter
                        
 blnError = False
Exit Sub
MailSendERROR:
 MsgBox "Err No:   " & Err.Number & vbCrLf & _
        "Err Desc: " & Err.Description, vbCritical
        
 blnError = True
End Sub

Private Sub MailBillContacts(ByRef blnError As Boolean)
 On Error GoTo MailSendERROR
 
 Dim strBody    As String
 Dim strSubject As String
 Dim strSQL     As String
 Dim rec        As ADODB.Recordset
 Dim Admin1     As String
 Dim Bill       As String
 Dim OldBill    As String
 Dim Tech       As String
 Dim Admin2     As String
 Dim strTo      As String
 Dim strCC      As String
 Dim strBCC     As String
 Dim D_Name     As String
 Dim Remarks    As String
 Dim strHolder  As String
 
'''''''''''''''''''''''''''''''''''''''''''''''''''''
'Email Enhancements Specification 24. January Release
'Designed and developed by David Gildea
'Date 12/01/05

'Step 01
'Select the correct email row from email table in the database based on ticket type
'
 strSQL = "SELECT * from Email WHERE E_Name = 'transferBill'"

 DB.Execute strSQL, rec
 
'Step 02
'Assign the correct variables from the recordset to the corresponding variables
'
Admin1 = GetEmail(TxtAdCon1.Text)
Admin2 = GetEmail(TxtAdCon2.Text)
Bill = GetEmail(TxtBilCon.Text)
OldBill = GetEmail(strOldBilCon)
Tech = GetEmail(TxtTechCon.Text)
D_Name = TxtDName.Text
Remarks = TxtRemark.Text
strHolder = TxtDHolder.Text

strTo = rec.Fields("E_To")
If (rec.Fields("E_CC") <> "") Then strCC = rec.Fields("E_CC")
If (rec.Fields("E_BCC") <> "") Then strBCC = rec.Fields("E_BCC")
strSubject = rec.Fields("E_Subject")
strBody = rec.Fields("E_Text")

'Step 03
'Replace the vaarables in the addresses, subjects etc with the correct information from the Ticket
' E.G $D_Name -> 'delphi.ie'
'     $admin  -> 'david.gildea@delphi.ie'
'
strTo = Replace(strTo, "$admin1", Admin1)
strTo = Replace(strTo, "$tech", Tech)
strTo = Replace(strTo, "$admin2", Admin2)
strTo = Replace(strTo, "$bill", Bill)
strTo = Replace(strTo, "$bll_Old", OldBill)


strCC = Replace(strCC, "$admin1", Admin1)
strCC = Replace(strCC, "$tech", Tech)
strCC = Replace(strCC, "$admin2", Admin2)
strCC = Replace(strCC, "$bill", Bill)
strCC = Replace(strCC, "$bill_Old", OldBill)

strBCC = Replace(strBCC, "$admin1", Admin1)
strBCC = Replace(strBCC, "$tech", Tech)
strBCC = Replace(strBCC, "$admin2", Admin2)
strBCC = Replace(strBCC, "$bill", Bill)
strBCC = Replace(strBCC, "$bill_Old", OldBill)


strSubject = Replace(strSubject, "$D_Name", D_Name)

strBody = Replace(strBody, "$D_Name", D_Name)
strBody = Replace(strBody, "$billNH", UCase(TxtBilCon.Text))
strBody = Replace(strBody, "$bill_OldNH", UCase(strOldBilCon))
strBody = Replace(strBody, "$Domain_Holder", strHolder)

'Step 04
'Take the string variables and call the send mail function
'
                      
FrmSendMail.NewSendMail blnError, _
                        DB.StaticData(ustSmtp), _
                        DB.StaticData(ustSmtpPort), _
                        strTo, _
                        strCC, _
                        strBCC, _
                        "hostmaster@iedr.ie", _
                        strSubject, _
                        strBody

 
'''''''''''''''''''''''''''''''''''''''''''''''''''''




blnError = False

Exit Sub
MailSendERROR:
 MsgBox "Err No:   " & Err.Number & vbCrLf & _
        "Err Desc: " & Err.Description, vbCritical
        
 blnError = True
 
End Sub

Private Sub MailToNewBilCon(ByRef blnError As Boolean)
 Dim strEmail  As String
 Dim strHeader As String
 Dim strBody   As String
 Dim strFooter As String
 
 On Error GoTo MailSendERROR
 
 strEmail = GetEmail(TxtBilCon)
'---------------------------------------------------------------------------------------------------------
' strHeader = "PLEASE DO NOT REPLY TO THIS EMAIL.  THIS IS AN AUTOMATED EMAIL. " & vbCrLf & vbCrLf
' strHeader = strHeader & "Please Note Domain Name : " & TxtDName & vbCrLf
' strHeader = strHeader & "Billing Contact : " & UCase(strOldBilCon) & _
'                         " has been transferred to Billing Contact : " & UCase(TxtBilCon) & vbCrLf
' strHeader = strHeader & "and Account No Transfer From : " & Format(strAcNo, gstrAccountNoPad) & _
'                                       " To : " & Format(TxtAccName, gstrAccountNoPad) & vbCrLf & vbCrLf
'
' strFooter = vbCrLf & vbCrLf & "Kind regards" & vbCrLf & vbCrLf & "IE Domain Registry Hostmaster"
'
'
' strBody = strBody & "Remarks : " & txtRemark & vbCrLf & vbCrLf

'Celina Leong - Phoenix1.1 27/03/03
'Change wording for transfering account

 strHeader = "PLEASE DO NOT REPLY TO THIS E-MAIL.  THIS IS AN AUTOMATED E-MAIL. " & vbCrLf & vbCrLf
 strHeader = strHeader & "Please Note Domain Name : " & TxtDName & " held by " & TxtDHolder & vbCrLf
 strHeader = strHeader & "Billing Contact : " & UCase(strOldBilCon) & _
                         " has been transferred to Billing Contact : " & UCase(TxtBilCon) & "." & vbCrLf & vbCrLf
' strHeader = strHeader & "and Account No Transfer From : " & Format(strAcNo, gstrAccountNoPad) & _
'                                       " To : " & Format(TxtAccName, gstrAccountNoPad) & vbCrLf & vbCrLf

 strFooter = vbCrLf & vbCrLf & "Kind regards," & vbCrLf & vbCrLf & ".IE Domain Registry Hostmaster"
 
 
 strBody = strBody & "If the nameserver entries are to be updated, the administrative contact " & vbCrLf
 strBody = strBody & "or billing contact for this domain should logon to the online modification " & vbCrLf
 strBody = strBody & "generator on our website with their current user ID and password to make " & vbCrLf
 strBody = strBody & "any necessary changes. " & vbCrLf & vbCrLf
 
 '------------------------------------------------------------------------------------------------------------
 'Occur Send2
 FrmSendMail.SendMail blnError, _
                      DB.StaticData(ustSmtp), _
                      DB.StaticData(ustSmtpPort), _
                      strEmail, _
                      DB.StaticData(ustReplyAddr), _
                      "Account Transfer Accepted : " & TxtDName, _
                      strHeader & strBody & strFooter
                        
 blnError = False
Exit Sub
MailSendERROR:
 MsgBox "Err No:   " & Err.Number & vbCrLf & _
        "Err Desc: " & Err.Description, vbCritical
        
 blnError = True
End Sub

Private Sub UpdateDN(ByRef blnError As Boolean)
Dim ctl As Control
Dim lngRowsUpdated As Long
Dim lngRow As Long
    
    On Error GoTo EXE_Err

If ChangedControls(Me) Then
   ' DB.BeginTransaction
'-----------------------------------------------------
'    sSQL = "UPDATE Domain " & _
'            "SET D_Name=" & CDBText(UCase(TxtDName)) & ",  " & _
'            "D_Holder=" & CDBText(UCase(TxtDHolder)) & ",  " & _
'            "A_Number=" & TxtAccName & ",  " & _
'            "D_Class=" & CDBText(ComboClass) & " ," & _
'            "D_Category=" & CDBText(ComboCategory) & "," & _
'            "D_Status=" & CDBText(ComboStatus) & " , " & _
'            "D_Reg_Dt=" & CDBDate(TxtRegDate) & "," & _
'            "D_Ren_Dt=" & CDBDate(TxtRenewDate) & "," & _
'            "D_Remark=" & CDBText(TxtRemark) & " " & _
'            "WHERE D_Name=" & CDBText(TxtDName) & "" & _
'            "AND D_TStamp = " & CDBTimeStamp(mstrTimeStamp) & " "

'David O'Leary - 09-September-2002
'Change the SQL String so that information is not
'converted to upper case before being saved.


'    sSQL = "UPDATE Domain " & _
'            "SET D_Name=" & CDBText(TxtDName) & ",  " & _
'            "D_Holder=" & CDBText(TxtDHolder) & ",  " & _
'            "A_Number=" & TxtAccName & ",  " & _
'            "D_Class=" & CDBText(ComboClass) & " ," & _
'            "D_Category=" & CDBText(ComboCategory) & "," & _
'            "D_Status=" & CDBText(ComboStatus) & " , " & _
'            "D_Reg_Dt=" & CDBDate(TxtRegDate) & "," & _
'            "D_Ren_Dt=" & CDBDate(TxtRenewDate) & "," & _
'            "D_Remark=" & CDBText(TxtRemark) & " " & _
'            "WHERE D_Name=" & CDBText(TxtDName) & "" & _
'            "AND D_TStamp = " & CDBTimeStamp(mstrTimeStamp) & " "

'-----------------------------------------------------

'Celina Leong - Phoenix1.1 26/03/03
'Change the SQL String to included D_Bill_Status,D_VAT_Status and D_ClikPaid
    
'Celina Leong Phoenix1.1 28/05/03
'Update History if is first update to History
'   lngRow = HFlexDomains.Row
'   If lngRow = 0 Then
'
'        WriteFirst_Hist_Domain blnError
'        WriteDNS blnError   ' Write history DNS
'        WriteNichandle blnError 'Write history dns
'   End If
'-----------------------------------------------------
'Celina Leong Phoenix1.1 11/04/03
'Update all History before Update
   WriteHistory_Domain blnError
   WriteDNS blnError
   WriteNichandle blnError
  
   
   
   sSQL = "UPDATE Domain " & _
            "SET D_Name=" & CDBText(TxtDName) & ",  " & _
            "D_Holder=" & CDBText(TxtDHolder) & ",  " & _
            "A_Number=" & TxtAccName & ",  " & _
            "D_Class=" & CDBText(ComboClass) & " ," & _
            "D_Category=" & CDBText(ComboCategory) & "," & _
            "D_Status=" & CDBText(ComboStatus) & " , " & _
            "D_Reg_Dt=" & CDBDate(TxtRegDate) & "," & _
            "D_Ren_Dt=" & CDBDate(TxtRenewDate) & "," & _
            "D_Bill_Status=" & CDBText(ImageComboBillStatus.SelectedItem.Key) & " , " & _
            "D_Vat_Status=" & CDBText(ComboVATStatus) & ", " & _
            "D_Remark=" & CDBText(TxtRemark & " by " & UserID & " on " & TempNow) & ", " & _
            "D_ClikPaid=" & CDBText(ComboClikPay) & " " & _
            "WHERE D_Name=" & CDBText(TxtDName) & "" & _
            "AND D_TStamp = " & CDBTimeStamp(mstrTimeStamp) & " "

'-----------------------------------------------------------------------
    DB.Execute sSQL ' , , , , lngRowsUpdated



  ''' WriteHistory_Domain 'Celina Leong Phoenix1.1 write history before update
   
     
   

'    If lngRowsUpdated <> 1 Then
'        DB.RollbackTransaction
'        MsgBox "Could not Update Domain.", vbExclamation
'        'MsgBox "Could not Update Status Domain." & vbCrLf & vbCrLf & "Domain updated by another user while you were editing it", vbExclamation
'    Else

'       DB.CommitTransaction
'       WriteHistory_Domain
'    End If
End If
           
      '   MsgBox "Changes saved successfully. "
       
Exit Sub

EXE_Err:

   
 On Error Resume Next   'Celina Leong Phoenix1.1 24/04/03
 
 If Err.Number = glngDuplicateKey Then
    MsgBox "Duplicate key ", vbExclamation
 Else
        DB.ShowError "Unable to Update Domain"
 End If
  
  
 blnError = True


End Sub
Private Sub Update_Nichandle(ByRef blnError As Boolean)

 Dim strSQL As String
 Dim FieldChng As Boolean
 Dim rec As ADODB.Recordset

    If TxtAdCon1.DataChanged = True Then
            TempType = "A"
          '  TempNichandle = TxtAdCon1 ' Phoenix1.1
            
            ' sSQL = "UPDATE Contact " & _
                    "SET Contact_NH=" & CDBText(UCase(TxtAdCon1)) & " " & _
                    "Where D_name=" & CDBText(TxtDName) & " " & _
                    "AND type=" & CDBText(TempType) & " " & _
                    "AND Contact_NH ='" & TempAdmin1 & "' " 'JMA Batch 2.1 bug fix
            
           'JMcA replaced....
            sSQL = "DELETE FROM Contact " & _
                   "WHERE Contact_NH='" & TempAdmin1 & "' " & _
                   "AND D_name=" & CDBText(TxtDName) & " " & _
                   "AND type=" & CDBText(TempType) & "; "
            
            CnPhoenix.Execute sSQL
           
            sSQL = "INSERT INTO Contact ( D_Name, Contact_NH, Type ) VALUES ( " & _
                    CDBText(TxtDName) & ", " & _
                    CDBText(UCase(TxtAdCon1)) & ", " & _
                    CDBText(TempType) & ") "
            
            CnPhoenix.Execute sSQL
            
            ' WriteHistory_Contact ' Celina Leong Phoenix1.1 already write history
   
    End If
    
       
    If TxtTechCon.DataChanged = True Then
            TempType = "T"
'            TempNichandle = TxtTechCon ' Phoenix1.1
            
                sSQL = "UPDATE Contact " & _
                       "SET Contact_NH=" & CDBText(UCase(TxtTechCon)) & " " & _
                       "Where D_name=" & CDBText(TxtDName) & " " & _
                       "AND type=" & CDBText(TempType) & " "
                       
                CnPhoenix.Execute sSQL
'                WriteHistory_Contact ' Celina Leong Phoenix1.1 already write history
   
    End If

    If TxtAdCon2.DataChanged = True Then
        
        If TxtAdCon2.Text <> "" Then
        
            TempType = "A" 'TempType = "2" '20060424 Admin contacts are now all "A"; not "A"&"2"
            'TempNichandle = TxtAdCon2 ' Phoenix1.1
            If strAdCon2 = "" Then
            
                   strSQL = "REPLACE INTO Contact("
                   strSQL = strSQL & "D_Name, "
                   strSQL = strSQL & "Contact_NH, "
                   strSQL = strSQL & "Type)"
                            
                   strSQL = strSQL & "VALUES( "
                   strSQL = strSQL & CDBText(TxtDName) & ", "
                   strSQL = strSQL & CDBText(UCase(TxtAdCon2)) & ", "
                   strSQL = strSQL & CDBText(TempType) & "); "
                                  
                   DB.Execute strSQL
'                   WriteHistory_Contact ' Celina Leong Phoenix1.1 already write history
            Else
                     TempType = "A" 'TempType = "2" '20060424 Admin contacts are now all "A"; not "A"&"2"
'                    TempNichandle = TxtAdCon2 ' Phoenix1.1
                     sSQL = "UPDATE Contact " & _
                           "SET Contact_NH=" & CDBText(UCase(TxtAdCon2)) & " " & _
                           "Where D_name=" & CDBText(TxtDName) & " " & _
                           "AND type=" & CDBText(TempType) & " " & _
                           "AND Contact_NH ='" & strAdCon2 & "' " 'JMA Batch 2.1 bug fix
                            
                           '"AND type='Z'"
        
                    CnPhoenix.Execute sSQL
'                    WriteHistory_Contact ' Celina Leong Phoenix1.1 already write history
            
            End If
        Else
                   TempType = "A" 'TempType = "2" '20060424 Admin contacts are now all "A"; not "A"&"2"
'                   TempNichandle = strAdCon2 ' Phoenix1.1
'                   WriteHistory_Contact  ' Celina Leong Phoenix1.1 already write history
                   sSQL = "Delete From Contact " & _
                          "Where D_name=" & CDBText(TxtDName) & " " & _
                          "AND type=" & CDBText(TempType) & " "
                           '"AND type='Z'"
        
                    
                    CnPhoenix.Execute sSQL
                   
        
        End If
   
    
    End If

     If TxtBilCon.DataChanged = True And AcDataChanged = True Then
            TempType = "B"
'            TempNichandle = TxtBilCon ' Phoenix1.1
            sSQL = "UPDATE Contact " & _
                   "SET Contact_NH=" & CDBText(UCase(TxtBilCon)) & " " & _
                   "Where D_name=" & CDBText(TxtDName) & " " & _
                    "AND type=" & CDBText(TempType) & " "
                  ' "AND type='T'"
            
            CnPhoenix.Execute sSQL
'            WriteHistory_Contact ' Celina Leong Phoenix1.1 already write history
'Celina Leong Phoenix1.1 11/04/03
'Update Admin Contact if transfer to a new account
            TempAdmin = TxtAdCon1
            UpdateAdminContact blnError
            
            'JMcA removed as bug fix for 2.1 - Xfer of doms wit 2 admins
            'Attempt to write a duplicate primary key value to NicHandle table.
            'If TxtAdCon2 <> "" Then
            '    TempAdmin = TxtAdCon2
            '    UpdateAdminContact blnError
            'End If
                       
'Celina Leong 18-09-2002
'Update creator NicHandle when billing contact changed
             TempType = "C"
'             TempNichandle = TxtBilCon ' Phoenix1.1
             
             strSQL = "SELECT Type FROM Contact "
             strSQL = strSQL & "WHERE D_Name=" & CDBText(TxtDName) & " "
             'strSQL = strSQL & "AND Contact_NH=" & CDBText(TxtBilCon) & ""
             strSQL = strSQL & "AND Type= " & CDBText(TempType) & " "
             DB.Execute strSQL, rec
    
    
                If rec.EOF Then
                    
                    strSQL = "INSERT INTO Contact("
                    strSQL = strSQL & "D_Name, "
                    strSQL = strSQL & "Contact_NH, "
                    strSQL = strSQL & "Type)"
                             
                    strSQL = strSQL & "VALUES( "
                    strSQL = strSQL & CDBText(TxtDName) & ", "
                    strSQL = strSQL & CDBText(UCase(TxtBilCon)) & ", "
                    strSQL = strSQL & CDBText(TempType) & "); "
                                   
                   DB.Execute strSQL
'                   WriteHistory_Contact  ' Celina Leong Phoenix1.1 already write history
                    
                Else
'                    TempNichandle = TxtBilCon ' Phoenix1.1
                     sSQL = "UPDATE Contact " & _
                            "SET Contact_NH=" & CDBText(UCase(TxtBilCon)) & " " & _
                            "Where D_name=" & CDBText(TxtDName) & " " & _
                            "AND Type=" & CDBText(TempType) & " "
                                    
                    CnPhoenix.Execute sSQL
'                    WriteHistory_Contact  ' Celina Leong Phoenix1.1 already write history
                    
    
                End If

                'MailToOldBilCon blnError
                'MailToNewBilCon blnError
                
                'Added by David Gilea on 19/01/05
                'Spec 24 Email Release
                MailBillContacts (blnError)
                
    End If
     
End Sub
Private Sub Update_DNS(ByRef blnError As Boolean)
Dim Counter As Integer
Dim strSQL As String
Dim Listcount As Long
Dim Listcnt As Long
Dim Count As Integer


Listcount = ComboDNSNameE.Listcount - 1

For Counter = 1 To Listcount

              
    If ComboDNSNameE.ItemData(Counter) = 2 Then
        
         DNSOrder = GetNext
            
'-----------------------------------------------------
'David O'Leary - 09-September-2002
'All SQL Strings below have been changed so that data
'is not converted into upper case when being saved
'-----------------------------------------------------
'                 strSQL = "INSERT INTO DNS("
'                 strSQL = strSQL & "D_Name, "
'                 strSQL = strSQL & "DNS_Name, "
'                 strSQL = strSQL & "DNS_IpAddr,"
'                 strSQL = strSQL & "DNS_Order) "
'                 strSQL = strSQL & "VALUES( "
'                 strSQL = strSQL & CDBText(UCase(TxtDName.Text)) & ","
'                 strSQL = strSQL & CDBText(UCase(ComboDNSNameE.List(Counter))) & ","
'                 strSQL = strSQL & CDBText(ListIPAdd.List(Counter)) & ","
'                 strSQL = strSQL & CDBText(DNSOrder) & ");"
                 
                 strSQL = "INSERT INTO DNS("
                 strSQL = strSQL & "D_Name, "
                 strSQL = strSQL & "DNS_Name, "
                 strSQL = strSQL & "DNS_IpAddr,"
                 strSQL = strSQL & "DNS_Order) "
                 strSQL = strSQL & "VALUES( "
                 strSQL = strSQL & CDBText(TxtDName.Text) & ","
                 strSQL = strSQL & CDBText(ComboDNSNameE.List(Counter)) & ","
                 strSQL = strSQL & CDBText(ListIPAdd.List(Counter)) & ","
                 strSQL = strSQL & CDBText(DNSOrder) & ");"
               
               '  strsql = strsql & CDBText(TempDNS_Order) & ");"
                    
                DB.Execute strSQL
               ' WriteHistory_DNS

'-----------------------------------------------------
               
'                strSQL = "INSERT INTO DNSHist("
'                strSQL = strSQL & "D_Name, "
'                strSQL = strSQL & "DNS_Name, "
'                strSQL = strSQL & "DNS_IpAddr, "
'                strSQL = strSQL & "DNS_Order, "
'                strSQL = strSQL & "Chng_NH, "
'                strSQL = strSQL & "Chng_Dt) "
'
'                strSQL = strSQL & "VALUES( "
'                strSQL = strSQL & CDBText(TxtDName) & ", "
'                strSQL = strSQL & CDBText(UCase(ComboDNSNameE.List(Counter))) & ", "
'                strSQL = strSQL & CDBText(ListIPAdd.List(Counter)) & ", "
'                strSQL = strSQL & CDBText(DNSOrder) & ","
'                strSQL = strSQL & CDBText(UserID) & ", "
'                strSQL = strSQL & CDBTime(TempNow) & "); "
''Celina Leong Phoenix1.1 14/04/03
''                strSQL = "INSERT INTO DNSHist("
''                strSQL = strSQL & "D_Name, "
''                strSQL = strSQL & "DNS_Name, "
''                strSQL = strSQL & "DNS_IpAddr, "
''                strSQL = strSQL & "DNS_Order, "
''                strSQL = strSQL & "Chng_NH, "
''                strSQL = strSQL & "Chng_Dt) "
''
''                strSQL = strSQL & "VALUES( "
''                strSQL = strSQL & CDBText(TxtDName) & ", "
''                strSQL = strSQL & CDBText(ComboDNSNameE.List(Counter)) & ", "
''                strSQL = strSQL & CDBText(ListIPAdd.List(Counter)) & ", "
''                strSQL = strSQL & CDBText(DNSOrder) & ","
''                strSQL = strSQL & CDBText(UserID) & ", "
''                strSQL = strSQL & CDBTime(TempNow) & "); "

'-----------------------------------------------------
                
''                DB.Execute strSQL
''-----------------------------------------------------
     End If
     


     If ComboDNSNameE.ItemData(Counter) = 1 Then

           
       strSQL = "UPDATE DNS SET "
       strSQL = strSQL & "DNS_Name=" & CDBText(ComboDNSNameE.List(Counter)) & ", "
       strSQL = strSQL & "DNS_IpAddr=" & CDBText(ListIPAdd.List(Counter)) & " "
       strSQL = strSQL & "Where D_Name=" & CDBText(TxtDName) & " "
       strSQL = strSQL & "AND DNS_Name=" & CDBText(ComboDNSName.List(Counter - 1)) & " "
       
       
                 
       DB.Execute strSQL
   '    WriteHistory_DNS
   
''Celina Leong Phoenix1.1 14/04/03
 
''         strSQL = "INSERT INTO DNSHist("
''            strSQL = strSQL & "D_Name, "
''            strSQL = strSQL & "DNS_Name, "
''            strSQL = strSQL & "DNS_IpAddr, "
''            strSQL = strSQL & "DNS_Order, "
''            strSQL = strSQL & "Chng_NH, "
''            strSQL = strSQL & "Chng_Dt) "
''
''            strSQL = strSQL & "VALUES( "
''            strSQL = strSQL & CDBText(TxtDName) & ", "
''            strSQL = strSQL & CDBText(ComboDNSNameE.List(Counter)) & ", "
''            strSQL = strSQL & CDBText(ListIPAdd.List(Counter)) & ", "
''            strSQL = strSQL & ListIPAdd.ItemData(Counter) & ","
''            strSQL = strSQL & CDBText(UserID) & ", "
''            strSQL = strSQL & CDBTime(TempNow) & "); "
            

'-----------------------------------------------------

''            DB.Execute strSQL
''-------------------------------------------------------------------------
            
                
    End If
Next Counter

    If mblnDeleteDNS And Not ListDNSname.Listcount = 0 Then
           Delete_DNS blnError
    End If
'Delete_DNS blnError
' 'Celina Leong Phoenix1.1 01/04/03
'Listcnt = ListDNSname.Listcount
'DB.BeginTransaction
'For Count = 1 To Listcnt
'
'   If ListDNSname.ItemData(Count) = 1 Then
'
'       strSQL = "DELETE FROM DNS "
'       strSQL = strSQL & "WHERE DNS_Name=" & CDBText(ListDNSname.List(Count))
'
'       DB.Execute strSQL
'
'
''            strSQL = "INSERT INTO DNSHist("
''            strSQL = strSQL & "D_Name, "
''            strSQL = strSQL & "DNS_Name, "
''            strSQL = strSQL & "DNS_IpAddr, "
''            strSQL = strSQL & "DNS_Order, "
''            strSQL = strSQL & "Chng_NH, "
''            strSQL = strSQL & "Chng_Dt) "
''
''            strSQL = strSQL & "VALUES( "
''            strSQL = strSQL & CDBText(TxtDName) & ", "
''            strSQL = strSQL & CDBText(ComboDNSNameE.List(Counter)) & ", "
''            strSQL = strSQL & CDBText(ListIPAdd.List(Counter)) & ", "
''            strSQL = strSQL & ListIPAdd.ItemData(Counter) & ","
''            strSQL = strSQL & CDBText(UserID) & ", "
''            strSQL = strSQL & CDBTime(TempNow) & "); "
''
''
''
''
''            DB.Execute strSQL
'   End If
''------------------------------------------
'
'Next Count

End Sub
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


Private Sub UpdateStatus(ByRef blnError As Boolean)
Dim lngRowsUpdated As Long
Dim ctl As Control
Dim strSQL As String
Dim lngRow As Long

     
  On Error GoTo EXE_Err
  
If ChangedControls(Me) Then

'Celina Leong Phoenix1.1 28/05/03
'Update History if is first update to History
'
'    lngRow = HFlexDomains.Row
'
'    If lngRow = 0 Then
'
'        WriteFirst_Hist_Domain blnError
'        WriteDNS blnError   ' Write history DNS
'        WriteNichandle blnError 'Write history dns
'    End If
'--------------------------------------------------
'Celina Leong Phoenix1.1 11/04/03
''    DB.BeginTransaction
    WriteHistory_Domain blnError
    WriteDNS blnError   ' Write history DNS
    WriteNichandle blnError 'Write history dns
'-------------------------------------------------
    
    
    strSQL = "UPDATE Domain SET "
    strSQL = strSQL & "D_Status=" & CDBText(ComboStatus) & ", "
    strSQL = strSQL & "D_Status_Dt=NOW(), "
    strSQL = strSQL & "D_Remark=" & CDBText(TxtRemark & " by " & UserID & " " & TempNow) & " "
    strSQL = strSQL & "WHERE D_Name=" & CDBText(TxtDName) & " "
    strSQL = strSQL & "AND D_TStamp=" & CDBTimeStamp(mstrTimeStamp) & " "
    
     
    DB.Execute strSQL ', , , , lngRowsUpdated 'Celina LEong Phoenix1.1 11/04/03
'''
'''
'''    If lngRowsUpdated <> 1 Then
'''        DB.RollbackTransaction
'''      ' MsgBox "Could not Update Status Domain." & vbCrLf & vbCrLf & "Possible reasons : Domain updated by another user while you were editing it", vbExclamation
'''        MsgBox "Could not Update Status Domain." & vbCrLf & vbCrLf & "Domain updated by another user while you were editing it", vbExclamation
'''    Else
'''        DB.CommitTransaction
''''        WriteHistory_Domain ' Celina Leong Phoenix1.1 already write history
'''    End If
'''----------------------------------------------------------
'Celina Leong - Phoenix1.1 27/03/03
'Add code to send mail to admin contact /billing contact when
' domain status change to 'suspended'  or 'deleted'.

    If ComboStatus.Text = "Suspended" Then
         TempContact = TxtBilCon
         SendSuspendedMail blnError
    End If
    
    If ComboStatus.Text = "Deleted" Then
        TempContact = TxtBilCon
        SendDeletedMail blnError
    End If
'----------------------------------------------------------------------------------

End If
   
     For Each ctl In Controls

                If TypeOf ctl Is TextBox Then
                    ctl.BackColor = &H8000000F
                    ctl.Enabled = True
                    ctl.Locked = False
                    ctl.BorderStyle = 0
'                    TxtScrh.BackColor = &H80000005 'Celina Leong Phoenix1.1
                    ComboSearch.BackColor = &H80000005
'                    TxtScrh.BorderStyle = 1 'Celina Leong Phoenix1.1
                    
                    TxtRemark.BorderStyle = 1
                End If

                If TypeOf ctl Is ComboBox Then
                    ctl.BackColor = &H8000000F
                    ctl.Enabled = False
                    ctl.Locked = True
                    CmbSearchList.BackColor = &H80000005
                    CmbSearchList.Enabled = True
                    CmbSearchList.Locked = False


                End If

                If TypeOf ctl Is CommandButton Then
                    ctl.Enabled = True
                    cmdCancel.Enabled = False
                    cmdSave.Enabled = False
                End If
            Next



Exit Sub

EXE_Err:


On Error Resume Next
 
' If Err.Number = glngDuplicateKey Then
'    MsgBox "Duplicate key", vbExclamation
' Else
'        DB.ShowError "Unable to Update Status"
' End If
'Celina Leong Phoenix1.1 11/04/03
' If DB.TransactionLevel > 0 Then
'    DB.RollbackTransaction
' End If
'-----------------------------------------------------
 Error.Show_Error_DB " Unable to update status "
 blnError = True

 
End Sub

Private Sub UpdateTraAccount(ByRef blnError As Boolean)
  Dim ctl As Control
  Dim lngRow As Long
     
     On Error GoTo EXE_Err
     
  If ChangedControls(Me) Then
'Celina Leong Phoenix1.1 28/05/03
'Update History if is first update to History
'
'   lngRow = HFlexDomains.Row
'
'    If lngRow = 0 Then
'
'        WriteFirst_Hist_Domain blnError
'        WriteDNS blnError   ' Write history DNS
'        WriteNichandle blnError 'Write history dns
'    End If
'---------------------------------------------------
  'Celina Leong Phoenix1.1 11/04/03
    
    WriteHistory_Domain blnError
    WriteDNS blnError   ' Write history DNS
    WriteNichandle blnError 'Write history dns
'-------------------------------------------------
'------------------------------------------------
' 20060319CC Create records in Transfers table (if appropriate)
    If mblnTraAccount And TxtBilCon.Text <> strOldBilCon Then
        sSQL = "replace into Transfers values(" & _
            CDBText(TxtDName) & "," & _
            CDBDate(TempNow) & "," & _
            CDBText(strOldBilCon) & "," & _
            CDBText(TxtBilCon.Text) & ")"
        CnPhoenix.Execute sSQL
' 20060321CC Create records in D_Locked table (if appropriate)
        If Val(Trim(TxtAccName.Text)) <> 1 Then
            sSQL = "replace into D_Locked values(" & _
                CDBText(TxtDName) & ", 'Xfer', " & _
                CDBTimeStamp(TempNow) & ")"
            CnPhoenix.Execute sSQL
        End If
    End If
'Celina leong 20-09-2002
    'MsgBox ("The new bill status is: " & CDBText(mYes))
    If Val(Trim(TxtAccName)) = 1 Then
    
            'June 2007 Scope 2 Change - If transfer occurs to /within a guest a/c renew date rolled fwd.
            RollForwardRenewDate 1
            sSQL = "UPDATE Domain " & _
            "SET A_Number=" & TxtAccName & ",  " & _
            "D_Discount=" & CDBText(mNo) & ",  " & _
            "D_Bill_Status=" & CDBText(mYes) & ", " & _
            "D_Vat_Status=" & CDBText(TxtVATStatus) & ", " & _
            "D_Ren_Dt=" & CDBDate(TxtRenewDate) & ", " & _
            "D_Remark=" & CDBText(TxtRemark & " by " & UserID & " on " & TempNow & ". Renew Date automatically rolled forward 1 year") & " " & _
            "WHERE D_Name=" & CDBText(TxtDName) & " " & _
            "AND D_TStamp = " & CDBTimeStamp(mstrTimeStamp) & " "
        
           CnPhoenix.Execute sSQL

'           WriteHistory_Domain ' Celina Leong Phoenix1.1 already write history
           
           Update_NicHandle_Table blnError
           
    Else
        
     'IEDR Req. 1.2 JMcA added conditional wrapper 16.11.06
     'to check if Domain is on the Mail/Suspend/Delete List
     If OnMSDList(TxtDName.Text) Then
        
        DeleteFromMSDList (TxtDName.Text)
        
        sSQL = "UPDATE Domain " & _
                "SET A_Number=" & TxtAccName & ",  " & _
                "D_Bill_Status=" & CDBText(mYes) & ", " & _
                "D_Vat_Status=" & CDBText(TxtVATStatus) & ", " & _
                "D_Remark=" & CDBText(TxtRemark & " by " & UserID & " on " & TempNow) & " " & _
                "WHERE D_Name=" & CDBText(TxtDName) & " " & _
                "AND D_TStamp = " & CDBTimeStamp(mstrTimeStamp) & " "
                
        CnPhoenix.Execute sSQL
        
        
     Else  'Proceed as before
     
        sSQL = "UPDATE Domain " & _
                "SET A_Number=" & TxtAccName & ",  " & _
                "D_Vat_Status=" & CDBText(TxtVATStatus) & ", " & _
                "D_Remark=" & CDBText(TxtRemark & " by " & UserID & " on " & TempNow) & " " & _
                "WHERE D_Name=" & CDBText(TxtDName) & " " & _
                "AND D_TStamp = " & CDBTimeStamp(mstrTimeStamp) & " "
     
        CnPhoenix.Execute sSQL
        
     End If
     
        'JMcA Batch 2.2 - Transfers take priority over pending tickets......
        StallPendingTicket
        
     
'       WriteHistory_Domain ' Celina Leong Phoenix1.1 already write history
      End If
    End If
        
     For Each ctl In Controls
                
                If TypeOf ctl Is TextBox Then
                    ctl.BackColor = &H8000000F
                    ctl.Enabled = True
                    ctl.Locked = False
                    ctl.BorderStyle = 0
'                    TxtScrh.BackColor = &H80000005 'Phoenix1.1
                    ComboSearch.BackColor = &H80000005
                   ' TxtScrh.BorderStyle = 1 ' Phoenix1.1
                    TxtRemark.BorderStyle = 1
                End If
                
                If TypeOf ctl Is ComboBox Then
                    ctl.BackColor = &H8000000F
                    ctl.Enabled = False
                    ctl.Locked = True
                    CmbSearchList.BackColor = &H80000005
                    CmbSearchList.Enabled = True
                    CmbSearchList.Locked = False
                    
                  
                End If
                
                If TypeOf ctl Is CommandButton Then
                    ctl.Enabled = True
                    cmdCancel.Enabled = False
                    cmdSave.Enabled = False
                End If
            Next
           
           
     '    MsgBox "Changes saved successfully. "

Exit Sub

EXE_Err:

    'CnPhoenix.Execute "ROLLBACK TRANSACTION"
   '
   ' blnError = True
On Error Resume Next '?????????????
 
 If Err.Number = glngDuplicateKey Then
    MsgBox "Duplicate key", vbExclamation
 Else
    DB.ShowError "Unable to Update Transfer Account"
 End If
  
 'If DB.TransactionLevel > 0 Then
 '   DB.RollbackTransaction
 'End If
 
 blnError = True


End Sub

'Celina Leong 23-09-2002
'Update NicHandle Billing Contact Indicated if Transfer Account = 1


Private Sub Update_NicHandle_Table(ByRef blnError As Boolean)

Dim strSQL         As String
Dim lngRowsUpdated As Long
 
 On Error GoTo UpdateNichandleError
 
 If blnError Then Exit Sub
'' DB.BeginTransaction ' 02/05/03
   strSQL = "UPDATE NicHandle  SET "
   strSQL = strSQL & "Nic_Handle=" & CDBText(TxtBilCon) & ", "
   strSQL = strSQL & "NH_BillC_Ind=" & CDBText(mYes) & " , "
   strSQL = strSQL & "NH_Remark=" & CDBText(TxtRemark) & " "
   strSQL = strSQL & "WHERE Nic_Handle=" & CDBText(TxtBilCon) & ""
   'strSQL = strSQL & "AND NH_TStamp = " & CDBTimeStamp(mstrTimeStamp) & " "
            
    DB.Execute strSQL ', , , , lngRowsUpdated ' 02/05/03

''     If lngRowsUpdated <> 1 Then
''          DB.RollbackTransaction
''          'MsgBox "Could not Update Nic Handle." & vbCrLf & vbCrLf & "Possible reasons : Nic Handle updated by another user while you were editing it", vbExclamation
''          MsgBox "Could not Update Nic Handle." & vbCrLf & vbCrLf & "Nic Handle updated by another user while you were editing it", vbExclamation
''     Else
''
''         DB.CommitTransaction
''         'WriteHistory_Nichandle  ' Celina Leong Phoenix1.1 already write history
''    End If
   
  
Exit Sub

UpdateNichandleError:
     
On Error Resume Next
 
If Err.Number = glngDuplicateKey Then
    MsgBox " Duplicate key ", vbExclamation
Else
    DB.ShowError "Unable to Update Nichandle "
End If

 blnError = True


            
            
End Sub

'* Validates input for Altering Status
'* Returns FALSE if any fields are invalid

Private Function IsValidStatus() As Boolean
 Dim blnInError As Boolean
 
 If TxtRemark.Text = "" Then
    blnInError = True
    FormatControl TxtRemark, csError
 End If
 
 
 IsValidStatus = Not blnInError
End Function

'* Validates input - Returns FALSE if any fields are invalid
'* Checks all TextBoxes and ComboBoxes for blank entries but
'*   ignores any control with a Tag property of "B" or "X"
'* Does additional validation necessary on other fields

Private Function IsValid() As Boolean
 Dim blnInError As Boolean
 Dim strSQL As String
 Dim rec As ADODB.Recordset
 Dim ctl As Control
' Celina Leong Phoenix1.1 28/03/03
 Dim IPDot%, Length%, i%
 Dim strDNS As String
'-------------------------------------
 
 
 If TxtRemark.Text = "" Then
    blnInError = True
    FormatControl TxtRemark, csError
 End If
 
 If TxtDHolder.Text = "" Then
    blnInError = True
    FormatControl TxtDHolder, csError
 End If
 
' If TxtAccName.Text = "" Then
'    blnInError = True
'    FormatControl TxtAccName, csError
' End If
 
 If TxtRegDate.Text = "" Then
    blnInError = True
    FormatControl TxtRegDate, csError
 Else
    If Not IsDate(TxtRegDate.Text) Then
         blnInError = True
         FormatControl TxtRegDate, csError
    
    End If
 End If
 
 If TxtRenewDate.Text = "" Then
    blnInError = True
    FormatControl TxtRenewDate, csError
 Else
    If Not IsDate(TxtRenewDate.Text) Then
         blnInError = True
         FormatControl TxtRenewDate, csError
         
    End If
    
 End If
 
 If TxtAdCon1.Text = "" Then
    blnInError = True
    FormatControl TxtAdCon1, csError
    
 Else
    strSQL = "SELECT Nic_Handle FROM NicHandle "
    strSQL = strSQL & "WHERE Nic_Handle=" & CDBText(UCase(TxtAdCon1.Text)) & ""
    DB.Execute strSQL, rec
    
    If rec.EOF Then
        MsgBox "Nic Handle not exists ", vbExclamation
        blnInError = True
        FormatControl TxtAdCon1, csError
    End If
 
 End If
   
 
  '* For Admin Contact 2
    If TxtAdCon2.Text <> "" Then
      
        strSQL = "SELECT Nic_Handle FROM NicHandle "
        strSQL = strSQL & "WHERE Nic_Handle=" & CDBText(UCase(TxtAdCon2.Text)) & ""
        DB.Execute strSQL, rec
        
        If rec.EOF Then
            MsgBox "Nic Handle not exists ", vbExclamation
            blnInError = True
            FormatControl TxtAdCon2, csError
        End If
        
     End If
 
 
 If TxtTechCon.Text = "" Then
    blnInError = True
    FormatControl TxtTechCon, csError
    
 Else
    strSQL = "SELECT Nic_Handle FROM NicHandle "
    strSQL = strSQL & "WHERE Nic_Handle=" & CDBText(UCase(TxtTechCon.Text)) & ""
    DB.Execute strSQL, rec
    
    If rec.EOF Then
        MsgBox "Nic Handle not exists ", vbExclamation
        blnInError = True
        FormatControl TxtTechCon, csError
    End If
    

 End If
 
 
 If ComboClass.Text = "" Then
    blnInError = True
    FormatControl ComboClass, csError
 End If
 
 If ComboCategory.Text = "" Then
    blnInError = True
    FormatControl ComboCategory, csError
 End If
 
 
 If ComboStatus.Text = "" Then
    blnInError = True
    FormatControl ComboStatus, csError
 End If

 
 If ComboDNSNameE.Text = "" Then
     blnInError = True
     FormatControl ComboDNSNameE, csError
 Else
    If IsDuplicateDNS Then
        MsgBox "DNS name already exists ", vbExclamation
        blnInError = True
        FormatControl ComboDNSNameE, csError
    End If

 End If
  
 
' Celina Leong Phoenix1.1 28/03/03
'Check DNS name to insert at least one Dot

       
'    If Not ComboDNSNameE.Text = "" Then
'        strDNS = ComboDNSNameE.Text
'        IPDot% = 0
'        Length% = Len(strDNS)
'        For I% = 1 To Length%
'            If Mid(strDNS, I%, 1) = "." Then
'                IPDot% = IPDot% + 1
'            End If
'        Next I%
'
'    If IPDot% < 1 Then
'        MsgBox "DNS Name at least one '.' in the field", vbExclamation
'        blnInError = True
'        FormatControl ComboDNSNameE, csError
'
'    End If
'   End If
' If TxtDNSIPAdd.Text = "(NEW)" Then
'    TxtDNSIPAdd.Text = ""
'
' Else
'    If Not TxtDNSIPAdd.Text = "" Then
'            strDNS = TxtDNSIPAdd.Text
'            IPDot% = 0
'            Length% = Len(strDNS)
'            For I% = 1 To Length%
'                If Mid(strDNS, I%, 1) = "." Then
'                    IPDot% = IPDot% + 1
'                End If
'            Next I%
'
''        If IPDot% <> 3 Then
''            MsgBox "DNS IP Address at least one '.' in the field", vbExclamation
''            blnInError = True
''            FormatControl TxtDNSIPAdd, csError
''        End If
'        If IPDot% <> 3 Then
'            MsgBox "DNS IP Address must be 3 '.' in the field", vbExclamation
'            blnInError = True
'            FormatControl TxtDNSIPAdd, csError
'        End If
'    End If
    
    
' End If
'-----------------------------------------------------------------------------
 
 
 IsValid = Not blnInError
End Function

Private Function ValidTraAccount() As Boolean
 Dim blnInError As Boolean
 Dim strSQL As String
 Dim strTempAccNo As Long
 Dim rec As ADODB.Recordset
 Dim ctl As Control
' Celina Leong Pheonix1.1
 Dim strTempCountry As String
 Dim IPDot%, Length%, i%
 Dim strDNS As String
'------------------------------

'Celina Leong 18-09-2002
' If TxtAccName.Text = "" Then
'    blnInError = True
'    FormatControl TxtAccName, csError
' Else
'   If strAcNo <> TxtAccName Then
'        If Not IsNumeric(TxtAccName) Then
'                MsgBox "Invalid Account No ", vbExclamation
'                blnInError = True
'                FormatControl TxtAccName, csError
'
'        Else
'
'            strSQL = "SELECT A_Number, Billing_NH FROM Account "
'            strSQL = strSQL & "WHERE A_Number=" & TxtAccName.Text & " "
'            strSQL = strSQL & "AND A_Status= " & CDBText("Active") & ""
'            DB.Execute strSQL, rec
'

'            If rec.EOF Then
'                MsgBox "Account No not exists ", vbExclamation
'                blnInError = True
'                FormatControl TxtAccName, csError
'
'            Else
'
'                AcDataChanged = True
'                TxtBilCon.Text = NoNull(rec.Fields("Billing_NH"))
'
'            End If
'         End If
'  Else
'        AcDataChanged = False
'
'  End If
'End If
 If TxtAccName.Text = "" Then
    blnInError = True
    FormatControl TxtAccName, csError
 Else
   If strAcNo <> Val(Trim(TxtAccName)) Or Val(Trim(strAcNo)) = 1 Then
        If Not IsNumeric(TxtAccName) Then
                MsgBox "Invalid Account No ", vbExclamation
                blnInError = True
                FormatControl TxtAccName, csError
        Else
            
            If Val(Trim(TxtAccName)) = 1 Then
         
'
'                blnInError = True
'                FormatControl TxtBilCon, csEdit
               
                
               
'                If Not IsNicHandleType(TxtBilCon, "B") Then
'                        MsgBox "Invalid Billing Contact ", vbExclamation
'                        blnInError = True
'                        FormatControl TxtBilCon, csError
'                End If
                
                If Not IsAccNicHandle(TxtAccName, TxtBilCon) Then
                       MsgBox "You not allow to transfer a non-account billing contact " & vbCrLf & _
                       " to another non-account billing contact ", vbExclamation
                       blnInError = True
                       FormatControl TxtBilCon, csError
                End If
               
            Else

                strSQL = "SELECT A_Number, Billing_NH FROM Account "
                strSQL = strSQL & "WHERE A_Number=" & TxtAccName.Text & " "
                strSQL = strSQL & "AND A_Status= " & CDBText("Active") & ""
                DB.Execute strSQL, rec

    
                If rec.EOF Then
                    MsgBox "Account No not exists ", vbExclamation
                    blnInError = True
                    FormatControl TxtAccName, csError
    
                Else
                    AcDataChanged = True
                    TxtBilCon.Text = NoNull(rec.Fields("Billing_NH"))
                    TempNichandle = TxtBilCon 'Celina Leong Phoenix1.1 14/04/03

                End If
            
            End If
        End If
       'Celina Leong 'Phoenix1.1 02/04/03
       'When transferring account, the vat status reflects the new billing contact
'' Celina Leong Phoenix1.1 16/05/03 Codes not require at this phase
''       strSQL = "SELECT NH_Country FROM Nichandle "
''       strSQL = strSQL & "WHERE Nic_Handle =" & CDBText(TxtBilCon.Text) & " "
''       DB.Execute strSQL, rec
''
''
''       If Not rec.EOF Then
''       strTempCountry = NoNull(rec.Fields("NH_Country"))
''       '   blnInError = True
''       End If
''
''       If strTempCountry <> "IRELAND" Then
''          TxtVATStatus = "Y"
''       End If
   Else
        AcDataChanged = False
        TempNichandle = TxtBilCon 'Celina Leong Phoenix1.1 14/04/03
   End If
 
 End If

   
 If ComboDNSNameE.Text = "" Then
     blnInError = True
     FormatControl ComboDNSNameE, csError
 Else
    If IsDuplicateDNS Then
        MsgBox " DNS Name already exists ", vbExclamation
        blnInError = True
        FormatControl ComboDNSNameE, csError
    End If
 
 End If
 
 If TxtAdCon1.Text = "" Then
    blnInError = True
    FormatControl TxtAdCon1, csError
 Else
    strSQL = "SELECT Nic_Handle FROM NicHandle "
    strSQL = strSQL & "WHERE Nic_Handle=" & CDBText(UCase(TxtAdCon1.Text)) & ""
    DB.Execute strSQL, rec
    
    If rec.EOF Then
        MsgBox "Nic Handle not exists ", vbExclamation
        blnInError = True
        FormatControl TxtAdCon1, csError
    End If
 End If
   
 'If TxtAdCon2.Text = "" Then
 '   blnInError = True
 '   FormatControl TxtAdCon2, csError
 'End If
 
 '* For Admin Contact 2
If TxtAdCon2.Text <> "" Then
  
    strSQL = "SELECT Nic_Handle FROM NicHandle "
    strSQL = strSQL & "WHERE Nic_Handle=" & CDBText(UCase(TxtAdCon2.Text)) & ""
    DB.Execute strSQL, rec
    
    If rec.EOF Then
        MsgBox "Nic Handle not exists ", vbExclamation
        blnInError = True
        FormatControl TxtAdCon2, csError
    End If
    
 End If
 
 If TxtTechCon.Text = "" Then
    blnInError = True
    FormatControl TxtTechCon, csError
 Else
    strSQL = "SELECT Nic_Handle FROM NicHandle "
    strSQL = strSQL & "WHERE Nic_Handle=" & CDBText(UCase(TxtTechCon.Text)) & ""
    DB.Execute strSQL, rec
    
    If rec.EOF Then
        MsgBox "Nic Handle not exists ", vbExclamation
        blnInError = True
        FormatControl TxtTechCon, csError
    End If
 
 End If

 If TxtRemark.Text = "" Then
    blnInError = True
    FormatControl TxtRemark, csError
 End If
 ' Celina Leong Phoenix1.1 28/03/03
'Check DNS name to insert at least one Dot

       
    If Not ComboDNSNameE.Text = "" Then
        strDNS = ComboDNSNameE.Text
        IPDot% = 0
        Length% = Len(strDNS)
        For i% = 1 To Length%
            If Mid(strDNS, i%, 1) = "." Then
                IPDot% = IPDot% + 1
            End If
        Next i%
        
    If IPDot% < 1 Then
        MsgBox "DNS Name at least one '.' in the field", vbExclamation
        blnInError = True
        FormatControl ComboDNSNameE, csError
    End If
   End If
 If TxtDNSIPAdd.Text = "(NEW)" Then
    TxtDNSIPAdd.Text = " "
    
 Else
    If Not TxtDNSIPAdd.Text = "" Then
            strDNS = TxtDNSIPAdd.Text
            IPDot% = 0
            Length% = Len(strDNS)
            For i% = 1 To Length%
                If Mid(strDNS, i%, 1) = "." Then
                    IPDot% = IPDot% + 1
                End If
            Next i%
        
        If IPDot% < 1 Then
            MsgBox "DNS IP Address at least one '.' in the field", vbExclamation
            blnInError = True
            FormatControl TxtDNSIPAdd, csError
        End If
    End If
    
    
 End If
'-----------------------------------------------------------------------------
 
 ValidTraAccount = Not blnInError
End Function

Private Sub WriteHistory_Domain(ByRef blnError As Boolean)

Dim strSQL As String
 '   TempNow = Now()
'--------------------------------------------------------------------
'    strSQL = "INSERT INTO DomainHist("
'    strSQL = strSQL & "D_Name, "
'    strSQL = strSQL & "D_Holder, "
'    strSQL = strSQL & "D_Class, "
'    strSQL = strSQL & "D_Category, "
'    strSQL = strSQL & "A_Number, "
'    strSQL = strSQL & "D_Status, "
'    strSQL = strSQL & "D_Status_Dt, "
'    strSQL = strSQL & "D_Reg_Dt, "
'    strSQL = strSQL & "D_Ren_Dt, "
'    strSQL = strSQL & "D_TStamp, "
'    strSQL = strSQL & "D_Discount, "
'    strSQL = strSQL & "D_Bill_Status, "
'    strSQL = strSQL & "D_Vat_Status, "
'    strSQL = strSQL & "D_Remark, "
'    strSQL = strSQL & "Chng_NH, "
'    strSQL = strSQL & "Chng_Dt) "
'
'
'    strSQL = strSQL & "SELECT "
'    strSQL = strSQL & "D_Name, "
'    strSQL = strSQL & "D_Holder, "
'    strSQL = strSQL & "D_Class, "
'    strSQL = strSQL & "D_Category, "
'    strSQL = strSQL & "A_Number, "
'    strSQL = strSQL & "D_Status, "
'    strSQL = strSQL & "D_Status_Dt, "
'    strSQL = strSQL & "D_Reg_Dt, "
'    strSQL = strSQL & "D_Ren_Dt, "
'    strSQL = strSQL & "D_TStamp, "
'    strSQL = strSQL & "D_Discount,"
'    strSQL = strSQL & "D_Bill_Status, "
'    strSQL = strSQL & "D_Vat_Status, "
'    strSQL = strSQL & "D_Remark, "
'    strSQL = strSQL & CDBText(UserID) & ", "
'    strSQL = strSQL & CDBTime(TempNow) & " "
'    strSQL = strSQL & "FROM Domain "
'    strSQL = strSQL & "WHERE D_Name ='" & TxtDName & "'"
'    DB.Execute strSQL
'    ' strSQL = strSQL & "NOW() "
'
    
'Celina Leong - Phoenix1.1 26/03/03
'Change the SQL String to included D_Bill_Status,D_VAT_Status and D_ClikPaid
    TempNow = Now()
    strSQL = "INSERT INTO DomainHist("
    strSQL = strSQL & "D_Name, "
    strSQL = strSQL & "D_Holder, "
    strSQL = strSQL & "D_Class, "
    strSQL = strSQL & "D_Category, "
    strSQL = strSQL & "A_Number, "
    strSQL = strSQL & "D_Status, "
    strSQL = strSQL & "D_Status_Dt, "
    strSQL = strSQL & "D_Reg_Dt, "
    strSQL = strSQL & "D_Ren_Dt, "
    strSQL = strSQL & "D_TStamp, "
    strSQL = strSQL & "D_Discount, "
    strSQL = strSQL & "D_Bill_Status, "
    strSQL = strSQL & "D_Vat_Status, "
    strSQL = strSQL & "D_Remark, "
    strSQL = strSQL & "D_ClikPaid, "
    strSQL = strSQL & "Chng_NH, "
    strSQL = strSQL & "Chng_Dt) "
     
              
    'strSQL = strSQL & "SELECT " 'CC20051212 Added to fix multiple duplicate records being added to history
    strSQL = strSQL & "SELECT DISTINCT " 'CC20051212
    strSQL = strSQL & "D_Name, "
    strSQL = strSQL & "D_Holder, "
    strSQL = strSQL & "D_Class, "
    strSQL = strSQL & "D_Category, "
    strSQL = strSQL & "A_Number, "
    strSQL = strSQL & "D_Status, "
    strSQL = strSQL & "D_Status_Dt, "
    strSQL = strSQL & "D_Reg_Dt, "
    strSQL = strSQL & "D_Ren_Dt, "
    strSQL = strSQL & "D_TStamp, "
    strSQL = strSQL & "D_Discount,"
    strSQL = strSQL & "D_Bill_Status, "
    strSQL = strSQL & "D_Vat_Status, "
    strSQL = strSQL & "D_Remark, "
    strSQL = strSQL & "D_ClikPaid, "
    'strSQL = strSQL & CDBText(UserID) & ", "
    strSQL = strSQL & CDBText("PHOENIX") & ", "
    strSQL = strSQL & CDBTime(TempNow) & " "
    
    strSQL = strSQL & "FROM Domain "
    strSQL = strSQL & "WHERE D_Name ='" & TxtDName & "'"
    DB.Execute strSQL
    ' strSQL = strSQL & "NOW() "
   
    ' strSQL = strSQL & CDBText(TxtRemark) & ", "
End Sub
Private Sub WriteHistory_Contact()
Dim strSQL As String
        
    strSQL = "INSERT INTO ContactHist("
    strSQL = strSQL & "D_Name, "
    strSQL = strSQL & "Contact_NH, "
    strSQL = strSQL & "Type, "
    strSQL = strSQL & "Chng_NH, "
    strSQL = strSQL & "Chng_Dt) "
     
              
''    strSQL = strSQL & "VALUES( "
''    strSQL = strSQL & CDBText(TxtDName) & ", "
''    strSQL = strSQL & CDBText(UCase(TempNichandle)) & ", "
''    strSQL = strSQL & CDBText(TempType) & ","
''    strSQL = strSQL & CDBText(UserID) & ", "
''    strSQL = strSQL & CDBTime(TempNow) & "); "
''    'strSQL = strSQL & "Now() ); "
'' Celina Leong Phoenix1.1 11/04/03
    
    'strSQL = strSQL & "SELECT " 'CC20051212 Added to fix multiple duplicate records being added to history
    strSQL = strSQL & "SELECT DISTINCT " 'CC20051212
    strSQL = strSQL & "D_Name, "
    strSQL = strSQL & CDBText(UCase(TempNichandle)) & ", "
    strSQL = strSQL & CDBText(TempType) & ","
    strSQL = strSQL & CDBText(UserID) & ", "
    strSQL = strSQL & CDBTime(TempNow) & " "
    strSQL = strSQL & "FROM Contact "
    strSQL = strSQL & "WHERE D_Name ='" & TxtDName & "'"
 
 
 
 
 
 
 
    DB.Execute strSQL
    
End Sub
Private Sub WriteHistory_DNS()
Dim Counter As Integer
    Dim strSQL As String
    
     If TempDNS = "(NEW)" Then
            strSQL = "INSERT INTO DNSHist("
            strSQL = strSQL & "D_Name, "
            strSQL = strSQL & "DNS_Name, "
            strSQL = strSQL & "DNS_IpAddr, "
            strSQL = strSQL & "DNS_Order, "
            strSQL = strSQL & "Chng_NH, "
            strSQL = strSQL & "Chng_Dt) "

            strSQL = strSQL & "VALUES( "
            strSQL = strSQL & CDBText(TxtDName) & ", "
            strSQL = strSQL & CDBText(ComboDNSNameE) & ", "
            strSQL = strSQL & CDBText(TxtDNSIPAdd) & ", "
            strSQL = strSQL & CDBText(TempDNS_Order) & ","
            strSQL = strSQL & CDBText(UserID) & ", "
            strSQL = strSQL & CDBTime(TempNow) & "); "

            DB.Execute strSQL
    Else
            strSQL = "INSERT INTO DNSHist("
            strSQL = strSQL & "D_Name, "
            strSQL = strSQL & "DNS_Name, "
            strSQL = strSQL & "DNS_IpAddr, "
            strSQL = strSQL & "DNS_Order, "
            strSQL = strSQL & "Chng_NH, "
            strSQL = strSQL & "Chng_Dt) "

'            strSQL = strSQL & "VALUES( "
'            strSQL = strSQL & CDBText(TxtDName) & ", "
'            strSQL = strSQL & CDBText(ComboDNSNameE.List(Counter)) & ", "
'            strSQL = strSQL & CDBText(ListIPAdd.List(Counter)) & ", "
'            strSQL = strSQL & CDBText(ListIPAdd.ItemData(Counter)) & ","
'            strSQL = strSQL & CDBText(UserID) & ", "
'            strSQL = strSQL & CDBTime(TempNow) & "); "
'Celina Leong Phoenix1.1 11/04/03 write history befor update dns
             
             'strSQL = strSQL & "SELECT " 'CC20051212 Added to fix multiple duplicate records being added to history
             strSQL = strSQL & "SELECT DISTINCT " 'CC20051212
             strSQL = strSQL & "D_Name, "
             strSQL = strSQL & CDBText(ComboDNSNameE.List(Counter)) & ", "
             strSQL = strSQL & CDBText(ListIPAdd.List(Counter)) & ", "
             strSQL = strSQL & CDBText(ListIPAdd.ItemData(Counter)) & ","
             strSQL = strSQL & CDBText(UserID) & ", "
             strSQL = strSQL & CDBTime(TempNow) & " "

             strSQL = strSQL & "FROM Dns "
             strSQL = strSQL & "WHERE D_Name ='" & TxtDName & "'"

            DB.Execute strSQL
    End If


End Sub
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


 HFlexDomains.Clear
 HFlexDomains.FixedRows = 0
 HFlexDomains.Rows = 2
 HFlexDomains.FixedRows = 1
 
 
 Set HFlexDomains.DataSource = RsSearchResults
 
 If HFlexDomains.Rows <= 1 Then
    HFlexDomains.Row = 0
 
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

    lngRow = HFlexDomains.Row
    strSQL = "SELECT * FROM DomainHist "
    strSQL = strSQL & "WHERE D_Name =" & CDBText(strDName) & "  "
    strSQL = strSQL & "AND Chng_Dt=" & CDBTime(HFlexDomains.TextMatrix(lngRow, 4))
    
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
           Dim i As Integer
           
           For i = 1 To Me.ImageComboBillStatus.ComboItems.Count
                
                If (Me.ImageComboBillStatus.ComboItems.Item(i).Key = rec.Fields("D_Bill_Status")) Then
                    Me.ImageComboBillStatus.ComboItems.Item(i).Selected = True
                End If
           Next
           
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

    lngRow = HFlexDomains.Row
    
    strSQL = "SELECT * FROM DNSHist "
    strSQL = strSQL & "WHERE D_Name =" & CDBText(strDName) & "  "
    strSQL = strSQL & "AND Chng_Dt=" & CDBTime(HFlexDomains.TextMatrix(lngRow, 4))
    
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
        TxtDNSIPAdd.Text = NoNull(rec.Fields("DNS_IpAddr"))
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


    lngRow = HFlexDomains.Row
    
    strSQL = "SELECT D_Name, Contact_NH, Type, Chng_Dt FROM ContactHist "
    strSQL = strSQL & "WHERE D_Name =" & CDBText(strDName) & "  "
    strSQL = strSQL & "AND Chng_Dt=" & CDBTime(HFlexDomains.TextMatrix(lngRow, 4)) & " "
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

'----------------------------------------------
'    If TxtDHolder.DataChanged Or _
'       TxtAccName.DataChanged Or _
'       ComboClass.ListIndex = -1 Or _
'       ComboCategory.ListIndex = -1 Or _
'       ComboStatus.ListIndex = -1 Or _
'       TxtRenewDate.DataChanged Or _
'       TxtRegDate.DataChanged Then

'Celina Leong - Phoenix1.1 26/03/03
'Add code to included Bill Status,VAT Status and ClikPaid combo check
    
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
 Dim Listcount As Long
 Dim Counter As Long
 
'  If ComboDNSNameE.ListIndex = -1 Or _
'       TxtDNSIPAdd.DataChanged Or mblnDeleteDNS Then
'        FieldChng = True
'
'  End If
'Celina Leong Phoenix1.1 08/05/03

 Listcount = ComboDNSNameE.Listcount - 1
 
 For Counter = 1 To Listcount
  
       If ComboDNSNameE.ItemData(Counter) <> 0 Or _
       TxtDNSIPAdd.DataChanged Or mblnDeleteDNS Then
        FieldChng = True
        
End If
  
 Next Counter
'-------------------------------------------------------
          
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

Public Sub WriteHistory_Nichandle() 'ByVal mstrNicHandle As String)
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


    'strSQL = strSQL & "SELECT " 'CC20051212 Added to fix multiple duplicate records being added to history
    strSQL = strSQL & "SELECT DISTINCT " 'CC20051212
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
'    strSQL = strSQL & CDBTime(TempNow) & " "
    strSQL = strSQL & "NOW() "
    strSQL = strSQL & "FROM NicHandle "
    strSQL = strSQL & "WHERE Nic_Handle ='" & TxtBilCon & "'"
    DB.Execute strSQL

End Sub
'Celina Leong - Phoenix1.1 27/03/03
'Send Email to admin or billing conatact when alter status for suspended
Private Sub SendSuspendedMail(ByRef blnError As Boolean)

 Dim strBody   As String
 
 Dim strSubject As String
 Dim strSQL     As String
 Dim rec        As ADODB.Recordset
 Dim Admin1     As String
 Dim Bill       As String
 Dim Tech       As String
 Dim Admin2     As String
 Dim strTo      As String
 Dim strCC      As String
 Dim strBCC     As String
 Dim D_Name     As String
 Dim Remarks    As String

 On Error GoTo MailSendERROR
'''''''''''''''''''''''''''''''''''''''''''''''''''''
'Email Enhancements Specification 24. January Release
'Designed and developed by David Gildea
'Date 12/01/05

'Step 01
'Select the correct email row from email table in the database based on ticket type
'
 strSQL = "SELECT * from Email WHERE E_Name = 'alterSus'"

 DB.Execute strSQL, rec
 
'Step 02
'Assign the correct variables from the recordset to the corresponding variables
'
Admin1 = GetEmail(TxtAdCon1.Text)
Admin2 = GetEmail(TxtAdCon2.Text)
Bill = GetEmail(TxtBilCon.Text)
Tech = GetEmail(TxtTechCon.Text)
D_Name = TxtDName.Text
Remarks = TxtRemark.Text

strTo = rec.Fields("E_To")
If (rec.Fields("E_CC") <> "") Then strCC = rec.Fields("E_CC")
If (rec.Fields("E_BCC") <> "") Then strBCC = rec.Fields("E_BCC")
strSubject = rec.Fields("E_Subject")
strBody = rec.Fields("E_Text")

'Step 03
'Replace the vaarables in the addresses, subjects etc with the correct information from the Ticket
' E.G $D_Name -> 'delphi.ie'
'     $admin  -> 'david.gildea@delphi.ie'
'
strTo = Replace(strTo, "$admin1", Admin1)
strTo = Replace(strTo, "$tech", Tech)
strTo = Replace(strTo, "$admin2", Admin2)
strTo = Replace(strTo, "$bill", Bill)


strCC = Replace(strCC, "$admin1", Admin1)
strCC = Replace(strCC, "$tech", Tech)
strCC = Replace(strCC, "$admin2", Admin2)
strCC = Replace(strCC, "$bill", Bill)

strBCC = Replace(strBCC, "$admin1", Admin1)
strBCC = Replace(strBCC, "$tech", Tech)
strBCC = Replace(strBCC, "$admin2", Admin2)
strBCC = Replace(strBCC, "$bill", Bill)

strSubject = Replace(strSubject, "$D_Name", D_Name)

strBody = Replace(strBody, "$D_Name", D_Name)
strBody = Replace(strBody, "$remark", Remarks)

'Step 04
'Take the string variables and call the send mail function
'
                      
FrmSendMail.NewSendMail blnError, _
                        DB.StaticData(ustSmtp), _
                        DB.StaticData(ustSmtpPort), _
                        strTo, _
                        strCC, _
                        strBCC, _
                        "hostmaster@iedr.ie", _
                        strSubject, _
                        strBody

 
'''''''''''''''''''''''''''''''''''''''''''''''''''''
                        
 blnError = False
Exit Sub
MailSendERROR:
 MsgBox "Err No:   " & Err.Number & vbCrLf & _
        "Err Desc: " & Err.Description, vbCritical
        
 blnError = True
End Sub

'Celina Leong - Phoenix1.1 27/03/03
'Send Email to admin or billing conatact when alter status for deleted
Private Sub SendDeletedMail(ByRef blnError As Boolean)

 Dim strBody   As String
 
 Dim strSubject As String
 Dim strSQL     As String
 Dim rec        As ADODB.Recordset
 Dim Admin1     As String
 Dim Bill       As String
 Dim Tech       As String
 Dim Admin2     As String
 Dim strTo      As String
 Dim strCC      As String
 Dim strBCC     As String
 Dim D_Name     As String
 Dim Remarks    As String

 On Error GoTo MailSendERROR
'''''''''''''''''''''''''''''''''''''''''''''''''''''
'Email Enhancements Specification 24. January Release
'Designed and developed by David Gildea
'Date 12/01/05

'Step 01
'Select the correct email row from email table in the database based on ticket type
'
 strSQL = "SELECT * from Email WHERE E_Name = 'alterDel'"

 DB.Execute strSQL, rec
 
'Step 02
'Assign the correct variables from the recordset to the corresponding variables
'
Admin1 = GetEmail(TxtAdCon1.Text)
Admin2 = GetEmail(TxtAdCon2.Text)
Bill = GetEmail(TxtBilCon.Text)
Tech = GetEmail(TxtTechCon.Text)
D_Name = TxtDName.Text
Remarks = TxtRemark.Text

strTo = rec.Fields("E_To")
If (rec.Fields("E_CC") <> "") Then strCC = rec.Fields("E_CC")
If (rec.Fields("E_BCC") <> "") Then strBCC = rec.Fields("E_BCC")
strSubject = rec.Fields("E_Subject")
strBody = rec.Fields("E_Text")

'Step 03
'Replace the vaarables in the addresses, subjects etc with the correct information from the Ticket
' E.G $D_Name -> 'delphi.ie'
'     $admin  -> 'david.gildea@delphi.ie'
'
strTo = Replace(strTo, "$admin1", Admin1)
strTo = Replace(strTo, "$tech", Tech)
strTo = Replace(strTo, "$admin2", Admin2)
strTo = Replace(strTo, "$bill", Bill)


strCC = Replace(strCC, "$admin1", Admin1)
strCC = Replace(strCC, "$tech", Tech)
strCC = Replace(strCC, "$admin2", Admin2)
strCC = Replace(strCC, "$bill", Bill)

strBCC = Replace(strBCC, "$admin1", Admin1)
strBCC = Replace(strBCC, "$tech", Tech)
strBCC = Replace(strBCC, "$admin2", Admin2)
strBCC = Replace(strBCC, "$bill", Bill)

strSubject = Replace(strSubject, "$D_Name", D_Name)

strBody = Replace(strBody, "$D_Name", D_Name)
strBody = Replace(strBody, "$remark", Remarks)

'Step 04
'Take the string variables and call the send mail function
'
                      
FrmSendMail.NewSendMail blnError, _
                        DB.StaticData(ustSmtp), _
                        DB.StaticData(ustSmtpPort), _
                        strTo, _
                        strCC, _
                        strBCC, _
                        "hostmaster@iedr.ie", _
                        strSubject, _
                        strBody

 
'''''''''''''''''''''''''''''''''''''''''''''''''''''
 blnError = False
Exit Sub
MailSendERROR:
 MsgBox "Err No:   " & Err.Number & vbCrLf & _
        "Err Desc: " & Err.Description, vbCritical
        
 blnError = True
End Sub
'Celina Leong Phoenix1.1 01/04/03
Private Sub Delete_DNS(ByRef blnError As Boolean)
Dim Count As Integer
Dim strSQL As String
Dim Listcnt As Long
 
     
On Error GoTo DeleteDNSERROR


Listcnt = ListDNSname.Listcount

For Count = 0 To Listcnt

        
       strSQL = "DELETE FROM DNS "
       strSQL = strSQL & "WHERE DNS_Name=" & CDBText(ListDNSname.List(Count)) & " "
       strSQL = strSQL & "AND D_Name=" & CDBText(TxtDName)

       DB.Execute strSQL



Next Count




''WriteHistory_Domain
''WriteHistory_DNS
''WriteHistory_Contact
''WriteHistory_Nichandle
'
'
Exit Sub
DeleteDNSERROR:

On Error Resume Next
    
blnError = True
    
    
 '            strSQL = "INSERT INTO DNSHist("
'            strSQL = strSQL & "D_Name, "
'            strSQL = strSQL & "DNS_Name, "
'            strSQL = strSQL & "DNS_IpAddr, "
'            strSQL = strSQL & "DNS_Order, "
'            strSQL = strSQL & "Chng_NH, "
'            strSQL = strSQL & "Chng_Dt) "
'
'            strSQL = strSQL & "VALUES( "
'            strSQL = strSQL & CDBText(TxtDName) & ", "
'            strSQL = strSQL & CDBText(ComboDNSNameE.List(Counter)) & ", "
'            strSQL = strSQL & CDBText(ListIPAdd.List(Counter)) & ", "
'            strSQL = strSQL & ListIPAdd.ItemData(Counter) & ","
'            strSQL = strSQL & CDBText(UserID) & ", "
'            strSQL = strSQL & CDBTime(TempNow) & "); "
'
'
'
'
'            DB.Execute strSQL
  ' End If
'------------------------------------------
End Sub

'Celina Leong 11-04-2003
'Update NicHandle Billing Contact Indicated if Transfer Account = 1


Private Sub UpdateAdminContact(ByRef blnError As Boolean)

Dim strSQL         As String
Dim lngRowsUpdated As Long
 
 On Error GoTo UpdateAdmincontactError
 
 If blnError Then Exit Sub
'' DB.BeginTransaction  ' 02/05/03
   strSQL = "UPDATE NicHandle  SET "
   strSQL = strSQL & "A_Number=" & CDBText(TxtAccName) & ", "
   strSQL = strSQL & "NH_Remark=" & CDBText(TxtRemark) & " "
   strSQL = strSQL & "WHERE Nic_Handle=" & CDBText(TempAdmin) & ""
            
    DB.Execute strSQL ', , , , lngRowsUpdated  ' 02/05/03
    'Celina Leong Phoenix1.1 show old history before update
        WriteHistory_Nichandle
    '------------------------------------------------
''     If lngRowsUpdated <> 1 Then
''          DB.RollbackTransaction
''          MsgBox "Could not Update Nic Handle." & vbCrLf & vbCrLf & "Nic Handle updated by another user while you were editing it", vbExclamation
''     Else
''
''         DB.CommitTransaction
''         'WriteHistory_Nichandle 'Celina Leong Phoenix1.1 show old history before update
''    End If
   
  
Exit Sub

UpdateAdmincontactError:
     
On Error Resume Next
 
If Err.Number = glngDuplicateKey Then
    MsgBox " Duplicate key ", vbExclamation
Else
    DB.ShowError "Unable to Update Nichandle "
End If

 blnError = True
      
End Sub
'Celina Leong Phoenix1.1 11/04/03
Private Sub WriteNichandle(ByRef blnError As Boolean)
    
            
            TempType = "A"
            TempNichandle = TempAdmin1
            WriteHistory_Contact
          
            TempType = "T"
            TempNichandle = TempTechC
            WriteHistory_Contact
    
            TempType = "A" 'TempType = "2" '20060424 Admin contacts are now all "A"; not "A"&"2"
            TempNichandle = strAdCon2
            WriteHistory_Contact
   
            TempType = "B"
            TempNichandle = strOldBilCon
            WriteHistory_Contact
  
  
  
End Sub

Private Sub WriteDNS(ByRef blnError As Boolean)

Dim Counter As Integer
Dim strSQL As String
Dim Listcount As Long
Dim Listcnt As Long
Dim Count As Integer

' Listcount = ComboDNSNameE.Listcount - 1 'Celina Leong 08/05/03
Listcount = ComboDNSName.Listcount
For Counter = 0 To Listcount


            strSQL = "INSERT INTO DNSHist("
            strSQL = strSQL & "D_Name, "
            strSQL = strSQL & "DNS_Name, "
            strSQL = strSQL & "DNS_IpAddr, "
            strSQL = strSQL & "DNS_Order, "
            strSQL = strSQL & "Chng_NH, "
            strSQL = strSQL & "Chng_Dt) "
             

             strSQL = strSQL & "SELECT DISTINCT " 'CC20051221 fix for MySQLv4.0
             strSQL = strSQL & "D_Name, "
             strSQL = strSQL & "DNS_Name, "
             strSQL = strSQL & "DNS_IpAddr, "
             strSQL = strSQL & "DNS_Order, "
             strSQL = strSQL & CDBText(UserID) & ", "
             strSQL = strSQL & CDBTime(TempNow) & " "

             strSQL = strSQL & "FROM DNS "
             strSQL = strSQL & "WHERE D_Name = " & CDBText(TxtDName) & " "
             strSQL = strSQL & "AND DNS_NAME = " & CDBText(ComboDNSName.List(Counter))
            
       
            DB.Execute strSQL
            
            '            strSQL = strSQL & "VALUES( "
'            strSQL = strSQL & CDBText(TxtDName) & ", "
'            strSQL = strSQL & CDBText(ComboDNSNameE.List(Counter)) & ", "
'            strSQL = strSQL & CDBText(ListIPAdd.List(Counter)) & ", "
'            strSQL = strSQL & CDBText(ListIPAdd.ItemData(Counter)) & ","
'            strSQL = strSQL & CDBText(UserID) & ", "
'            strSQL = strSQL & CDBTime(TempNow) & "); "
'
'             strSQL = strSQL & "FROM Dns "
'             strSQL = strSQL & "WHERE D_Name ='" & TxtDName & "'"
     '   End If
  
 Next Counter
 
'''If Not ListDNSname.Listcount = 0 Then
'''        Listcount = ListDNSname.Listcount
'''
'''        For counter = 0 To Listcount
'''
'''
'''                    strSQL = "INSERT INTO DNSHist("
'''                    strSQL = strSQL & "D_Name, "
'''                    strSQL = strSQL & "DNS_Name, "
'''                    strSQL = strSQL & "DNS_IpAddr, "
'''                    strSQL = strSQL & "DNS_Order, "
'''                    strSQL = strSQL & "Chng_NH, "
'''                    strSQL = strSQL & "Chng_Dt) "
'''
'''
'''                     strSQL = strSQL & "SELECT "
'''                     strSQL = strSQL & "D_Name, "
'''                     strSQL = strSQL & "DNS_Name, "
'''                     strSQL = strSQL & "DNS_IpAddr, "
'''                     strSQL = strSQL & "DNS_Order, "
'''                     strSQL = strSQL & CDBText(UserID) & ", "
'''                     strSQL = strSQL & CDBTime(TempNow) & " "
'''
'''                     strSQL = strSQL & "FROM DNS "
'''                     strSQL = strSQL & "WHERE D_Name ='" & TxtDName & "'"
'''                     strSQL = strSQL & "AND DNS_NAME = " & CDBText(ListDNSname.List(counter)) & ""
'''
'''
'''
'''             DB.Execute strSQL
'''
'''
'''
'''
'''     Next counter

'''End If

End Sub
' Celina Leong Phoenix1.1 08/05/03
'Check DNS name to insert at least one Dot
Private Function IsValidDNS() As Boolean
 Dim blnInError As Boolean
  Dim IPDot%, Length%, i%
 Dim strDNS As String
 Dim Listcount As Long
 Dim Counter As Long
  
''  Listcount = ComboDNSNameE.Listcount - 1
  
''  For counter = 1 To Listcount
     
    If Not ComboDNSNameE.Text = "(NEW)" Then
''        strDNS = ComboDNSNameE.List(counter)
 ''       strDNS = mstrPrevText '28/05/03
        strDNS = ComboDNSNameE.Text
        IPDot% = 0
        Length% = Len(strDNS)
        For i% = 1 To Length%
            If Mid(strDNS, i%, 1) = "." Then
                IPDot% = IPDot% + 1
            End If
        Next i%
        
    If IPDot% < 1 Then
        MsgBox "DNS Name at least one '.' in the field  " & "'" & strDNS & "'", vbExclamation
        blnInError = True
        ComboDNSNameE.SetFocus
    End If
   End If
   
'' Next counter
 
 IsValidDNS = Not blnInError
End Function

'Celina Leong Phoenix1.1 28/05/03
'Write old History if there is a new record

Private Sub WriteFirst_Hist_Domain(ByRef blnError As Boolean)

Dim strSQL As String

    TempNow = Now()
    strSQL = "INSERT INTO DomainHist("
    strSQL = strSQL & "D_Name, "
    strSQL = strSQL & "D_Holder, "
    strSQL = strSQL & "D_Class, "
    strSQL = strSQL & "D_Category, "
    strSQL = strSQL & "A_Number, "
    strSQL = strSQL & "D_Status, "
    strSQL = strSQL & "D_Status_Dt, "
    strSQL = strSQL & "D_Reg_Dt, "
    strSQL = strSQL & "D_Ren_Dt, "
    strSQL = strSQL & "D_TStamp, "
    strSQL = strSQL & "D_Discount, "
    strSQL = strSQL & "D_Bill_Status, "
    strSQL = strSQL & "D_Vat_Status, "
    strSQL = strSQL & "D_Remark, "
    strSQL = strSQL & "D_ClikPaid, "
    strSQL = strSQL & "Chng_NH, "
    strSQL = strSQL & "Chng_Dt) "
     
              
    strSQL = strSQL & "SELECT DISTINCT " 'CC20051221 fix for MySQLv4.0
    strSQL = strSQL & "D_Name, "
    strSQL = strSQL & "D_Holder, "
    strSQL = strSQL & "D_Class, "
    strSQL = strSQL & "D_Category, "
    strSQL = strSQL & "A_Number, "
    strSQL = strSQL & "D_Status, "
    strSQL = strSQL & "D_Status_Dt, "
    strSQL = strSQL & "D_Reg_Dt, "
    strSQL = strSQL & "D_Ren_Dt, "
    strSQL = strSQL & "D_TStamp, "
    strSQL = strSQL & "D_Discount,"
    strSQL = strSQL & "D_Bill_Status, "
    strSQL = strSQL & "D_Vat_Status, "
    strSQL = strSQL & "D_Remark, "
    strSQL = strSQL & "D_ClikPaid, "
    strSQL = strSQL & CDBText(UserID) & ", "
    strSQL = strSQL & CDBTime(TempNow) & " "
    
    strSQL = strSQL & "FROM Domain "
    strSQL = strSQL & "WHERE D_Name ='" & TxtDName & "'"
    DB.Execute strSQL
   
End Sub

'JMcA BACTH 1.2 added 16.11.06
Private Function OnMSDList(sDom As String) As Boolean

Dim strSQL As String
Dim rsResult As ADODB.Recordset
Dim iCount As Integer
On Error GoTo MSDError
    
    OnMSDList = False
    Set rsResult = New ADODB.Recordset
    iCount = 0
    
    strSQL = "Select Count(*) as Count From MailList "
    strSQL = strSQL & "WHERE D_Name ='" & sDom & "'"
    rsResult.Open strSQL, CnPhoenix
    iCount = iCount + rsResult("Count")
    rsResult.Close
    
    strSQL = "Select Count(*) as Count From SuspendList "
    strSQL = strSQL & "WHERE D_Name ='" & sDom & "'"
    rsResult.Open strSQL, CnPhoenix
    iCount = iCount + rsResult("Count")
    rsResult.Close
    
    strSQL = "Select Count(*) as Count From DeleteList "
    strSQL = strSQL & "WHERE D_Name ='" & sDom & "'"
    rsResult.Open strSQL, CnPhoenix
    iCount = iCount + rsResult("Count")
    rsResult.Close
    
    If (iCount > 0) Then
        OnMSDList = True
    End If

Exit Function
MSDError:
    DB.ShowError "Unable to Query MSDList"
    On Error Resume Next

End Function
'1.2 JMcA added 16.11.06
Private Sub DeleteFromMSDList(sDom As String)

Dim strSQL As String
On Error GoTo MSDDeleteError
Dim rsResult As ADODB.Recordset
Dim iCount As Integer

    strSQL = "DELETE FROM MailList "
    strSQL = strSQL & "WHERE D_Name ='" & sDom & "'"
    
    CnPhoenix.Execute strSQL
    strSQL = ""
    
    strSQL = "DELETE FROM SuspendList "
    strSQL = strSQL & "WHERE D_Name ='" & sDom & "'"

    CnPhoenix.Execute strSQL
    strSQL = ""
    
    strSQL = "DELETE FROM DeleteList "
    strSQL = strSQL & "WHERE D_Name ='" & sDom & "'"

    CnPhoenix.Execute strSQL


Exit Sub
MSDDeleteError:
    DB.ShowError "Unable to Delete from MSDList"
    On Error Resume Next
End Sub
'JMcA 21.11.06 BATCH Requirement 5.2
Private Sub FlagTicketForDomain(sDomain As String)
Dim rsTkt As ADODB.Recordset
On Error GoTo DBError
 
    'Search for a ticket for the precise domain name found
    sSQL = "SELECT * FROM Ticket " & _
           "WHERE D_Name like " & CDBText(sDomain) & _
           "ORDER BY D_Name "
          
    Set rsTkt = New ADODB.Recordset
    
    rsTkt.Open sSQL, CnPhoenix, adOpenDynamic, adLockOptimistic
    
    If Not rsTkt.EOF Then
       lblTickets.Visible = True
    Else
       lblTickets.Visible = False
    End If
    
    rsTkt.Close

Exit Sub

DBError:
    DB.ShowError "Error reading from Ticket table"
    On Error Resume Next
End Sub
'JMcA 21.11.06 BATCH Requirement 2.2
Private Sub StallPendingTicket()
Dim sSQL As String
On Error GoTo PTDeleteError
    
    sSQL = "UPDATE Ticket " & _
           "SET Admin_Status = 4 " & _
           "WHERE D_Name = " & CDBText(TxtDName)
           '"AND Admin_Status = 0" 'Line removed - need to stall all tickets for domain.
           
    CnPhoenix.Execute sSQL
    
'Further modification needed for TicketHistory table ?

Exit Sub

PTDeleteError:
    DB.ShowError "Unable to Stall Pending Ticket"
    On Error Resume Next
End Sub
'Scope 2 Change Jun 2007
Sub RollForwardRenewDate(iYears As Integer)
Dim dRenewDate As Date
Dim iYear As Integer
    
    dRenewDate = CDate(TxtRenewDate)
    
    iYear = CInt(Year(dRenewDate)) + 1
    
    If Day(dRenewDate) = "29" And Month(dRenewDate) = "02" Then
        dRenewDate = "28/02/" & CStr(iYear)
    Else
        dRenewDate = Day(dRenewDate) & "/" & Month(dRenewDate) & "/" & CStr(iYear)
    End If
    
    
    TxtRenewDate = dRenewDate

End Sub


