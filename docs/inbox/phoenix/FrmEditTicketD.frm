VERSION 5.00
Object = "{0ECD9B60-23AA-11D0-B351-00A0C9055D8E}#6.0#0"; "mshflxgd.ocx"
Begin VB.Form FrmEditTicketD 
   Caption         =   "Edit Ticket Validation"
   ClientHeight    =   9315
   ClientLeft      =   60
   ClientTop       =   345
   ClientWidth     =   11835
   HelpContextID   =   5010
   LinkTopic       =   "Form1"
   MDIChild        =   -1  'True
   ScaleHeight     =   9315
   ScaleWidth      =   11835
   Begin VB.Frame FraCurrent 
      Caption         =   "Current"
      ForeColor       =   &H00000000&
      Height          =   5775
      Left            =   240
      TabIndex        =   33
      Top             =   240
      Width           =   11535
      Begin VB.Frame FrameCurrent2 
         Height          =   3210
         Left            =   5760
         TabIndex        =   43
         Top             =   240
         Width           =   5655
         Begin VB.ComboBox cboClikPaid 
            Height          =   315
            Left            =   960
            TabIndex        =   59
            Tag             =   "T"
            Text            =   "cboClikPaid"
            Top             =   1440
            Width           =   675
         End
         Begin VB.TextBox txtClikPaid 
            BackColor       =   &H8000000F&
            BorderStyle     =   0  'None
            Height          =   315
            Left            =   960
            Locked          =   -1  'True
            TabIndex        =   58
            TabStop         =   0   'False
            Tag             =   "T"
            Top             =   1440
            Width           =   675
         End
         Begin VB.ComboBox cboClass 
            Height          =   315
            Left            =   960
            Style           =   2  'Dropdown List
            TabIndex        =   19
            Tag             =   "T"
            Top             =   480
            Width           =   2955
         End
         Begin VB.ComboBox cboCategory 
            Height          =   315
            Left            =   960
            Style           =   2  'Dropdown List
            TabIndex        =   22
            Tag             =   "T"
            Top             =   960
            Width           =   2955
         End
         Begin VB.TextBox TxtChanged 
            BackColor       =   &H8000000F&
            BorderStyle     =   0  'None
            ForeColor       =   &H00000000&
            Height          =   255
            Left            =   3840
            Locked          =   -1  'True
            TabIndex        =   50
            TabStop         =   0   'False
            Tag             =   "X"
            Top             =   1440
            Width           =   1575
         End
         Begin VB.Frame Frame1 
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
            Height          =   1095
            Left            =   120
            TabIndex        =   46
            Top             =   1920
            Width           =   5415
            Begin VB.TextBox txtRemark 
               BackColor       =   &H8000000F&
               Height          =   645
               Left            =   120
               Locked          =   -1  'True
               MultiLine       =   -1  'True
               ScrollBars      =   2  'Vertical
               TabIndex        =   47
               TabStop         =   0   'False
               Tag             =   "X"
               Top             =   360
               Width           =   5175
            End
         End
         Begin VB.ComboBox cboCategoryFail 
            Height          =   315
            Left            =   3960
            Style           =   2  'Dropdown List
            TabIndex        =   23
            Top             =   960
            Width           =   1455
         End
         Begin VB.ComboBox cboClassFail 
            Height          =   315
            Left            =   3960
            Style           =   2  'Dropdown List
            TabIndex        =   20
            Top             =   480
            Width           =   1455
         End
         Begin VB.TextBox txtCategory 
            BackColor       =   &H8000000F&
            BorderStyle     =   0  'None
            ForeColor       =   &H00000000&
            Height          =   315
            Left            =   960
            Locked          =   -1  'True
            TabIndex        =   21
            TabStop         =   0   'False
            Tag             =   "T"
            Top             =   960
            Width           =   2835
         End
         Begin VB.TextBox txtClass 
            BackColor       =   &H8000000F&
            BorderStyle     =   0  'None
            ForeColor       =   &H00000000&
            Height          =   315
            Left            =   960
            Locked          =   -1  'True
            TabIndex        =   18
            TabStop         =   0   'False
            Tag             =   "T"
            Top             =   480
            Width           =   2955
         End
         Begin VB.Label LblClikPaid 
            Caption         =   "Clik Paid"
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
            TabIndex        =   57
            Top             =   1440
            Width           =   1095
         End
         Begin VB.Label Label3 
            Caption         =   "Failure Reason:"
            Height          =   255
            Left            =   3960
            TabIndex        =   53
            Top             =   240
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
            Height          =   255
            Left            =   2880
            TabIndex        =   51
            Top             =   1440
            Width           =   855
         End
         Begin VB.Label LblSndCat 
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
            Left            =   120
            TabIndex        =   45
            Top             =   480
            Width           =   1575
         End
         Begin VB.Label LblPriCat 
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
            Left            =   120
            TabIndex        =   44
            Top             =   960
            Width           =   1575
         End
      End
      Begin VB.Frame FrameCurrent1 
         BeginProperty Font 
            Name            =   "MS Sans Serif"
            Size            =   12
            Charset         =   0
            Weight          =   700
            Underline       =   0   'False
            Italic          =   0   'False
            Strikethrough   =   0   'False
         EndProperty
         Height          =   5055
         Left            =   240
         TabIndex        =   35
         Top             =   240
         Width           =   5415
         Begin VB.ComboBox cboDNSEdit 
            Height          =   315
            Left            =   1560
            TabIndex        =   56
            Top             =   4080
            Visible         =   0   'False
            Width           =   2100
         End
         Begin VB.ComboBox cboIpFail 
            Height          =   315
            Left            =   3840
            Style           =   2  'Dropdown List
            TabIndex        =   17
            Top             =   4560
            Width           =   1455
         End
         Begin VB.TextBox txtIP 
            BackColor       =   &H8000000F&
            BorderStyle     =   0  'None
            ForeColor       =   &H00000000&
            Height          =   285
            Left            =   1560
            Locked          =   -1  'True
            TabIndex        =   6
            TabStop         =   0   'False
            Top             =   4560
            Width           =   1575
         End
         Begin VB.ComboBox cboDnsFail 
            Height          =   315
            Left            =   3840
            Style           =   2  'Dropdown List
            TabIndex        =   16
            Top             =   4080
            Width           =   1455
         End
         Begin VB.ComboBox cboDNS 
            BackColor       =   &H8000000F&
            ForeColor       =   &H00000000&
            Height          =   315
            ItemData        =   "FrmEditTicketD.frx":0000
            Left            =   1320
            List            =   "FrmEditTicketD.frx":0002
            Style           =   2  'Dropdown List
            TabIndex        =   15
            Tag             =   "X"
            Top             =   4080
            Width           =   2340
         End
         Begin VB.ComboBox cboBillContactFail 
            Height          =   315
            Left            =   3840
            Style           =   2  'Dropdown List
            TabIndex        =   14
            Top             =   3600
            Width           =   1455
         End
         Begin VB.ComboBox cboTechContactFail 
            Height          =   315
            Left            =   3840
            Style           =   2  'Dropdown List
            TabIndex        =   12
            Top             =   3120
            Width           =   1455
         End
         Begin VB.ComboBox cboAdminContact1Fail 
            Height          =   315
            Left            =   3840
            Style           =   2  'Dropdown List
            TabIndex        =   8
            Top             =   2160
            Width           =   1455
         End
         Begin VB.ComboBox cboAdminContact2Fail 
            Height          =   315
            Left            =   3840
            Style           =   2  'Dropdown List
            TabIndex        =   10
            Top             =   2640
            Width           =   1455
         End
         Begin VB.ComboBox cboAccountNameFail 
            Height          =   315
            Left            =   3840
            Style           =   2  'Dropdown List
            TabIndex        =   5
            Top             =   1680
            Width           =   1455
         End
         Begin VB.ComboBox cboDomainHolderFail 
            Height          =   315
            Left            =   3840
            Style           =   2  'Dropdown List
            TabIndex        =   3
            Top             =   1200
            Width           =   1455
         End
         Begin VB.ComboBox cboDomainNameFail 
            Height          =   315
            Left            =   3840
            Style           =   2  'Dropdown List
            TabIndex        =   1
            Top             =   720
            Width           =   1455
         End
         Begin VB.TextBox txtBillContact 
            BackColor       =   &H8000000F&
            BorderStyle     =   0  'None
            ForeColor       =   &H00000000&
            Height          =   315
            Left            =   1560
            Locked          =   -1  'True
            MousePointer    =   1  'Arrow
            TabIndex        =   13
            Top             =   3600
            Width           =   2100
         End
         Begin VB.TextBox txtTechContact 
            BackColor       =   &H8000000F&
            BorderStyle     =   0  'None
            ForeColor       =   &H00000000&
            Height          =   315
            Left            =   1560
            Locked          =   -1  'True
            MousePointer    =   1  'Arrow
            TabIndex        =   11
            Top             =   3120
            Width           =   2100
         End
         Begin VB.TextBox txtAdminContact2 
            BackColor       =   &H8000000F&
            BorderStyle     =   0  'None
            Height          =   315
            Left            =   1560
            Locked          =   -1  'True
            MousePointer    =   1  'Arrow
            TabIndex        =   9
            Top             =   2640
            Width           =   2100
         End
         Begin VB.TextBox txtAdminContact1 
            BackColor       =   &H8000000F&
            BorderStyle     =   0  'None
            ForeColor       =   &H00000000&
            Height          =   315
            Left            =   1560
            Locked          =   -1  'True
            MousePointer    =   1  'Arrow
            TabIndex        =   7
            Top             =   2160
            Width           =   2100
         End
         Begin VB.TextBox txtDomainHolder 
            BackColor       =   &H8000000F&
            BorderStyle     =   0  'None
            ForeColor       =   &H00000000&
            Height          =   315
            Left            =   1560
            Locked          =   -1  'True
            TabIndex        =   2
            TabStop         =   0   'False
            Top             =   1200
            Width           =   2100
         End
         Begin VB.TextBox txtAccountName 
            BackColor       =   &H8000000F&
            BorderStyle     =   0  'None
            ForeColor       =   &H80000012&
            Height          =   315
            Left            =   1560
            Locked          =   -1  'True
            TabIndex        =   4
            TabStop         =   0   'False
            Tag             =   "X"
            Top             =   1680
            Width           =   2100
         End
         Begin VB.TextBox txtDomainName 
            BackColor       =   &H8000000F&
            BorderStyle     =   0  'None
            ForeColor       =   &H00000000&
            Height          =   315
            Left            =   1560
            Locked          =   -1  'True
            TabIndex        =   0
            TabStop         =   0   'False
            Tag             =   "X"
            Top             =   720
            Width           =   2100
         End
         Begin VB.Label LblDNSIP 
            Caption         =   "DNS IP Addr."
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
            TabIndex        =   52
            Top             =   4560
            Width           =   1215
         End
         Begin VB.Label Label1 
            Caption         =   "Failure Reason:"
            Height          =   255
            Left            =   3960
            TabIndex        =   49
            Top             =   360
            Width           =   1215
         End
         Begin VB.Label LblDNS 
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
            Left            =   120
            TabIndex        =   48
            Top             =   4080
            Width           =   1575
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
            Left            =   120
            TabIndex        =   42
            Top             =   3600
            Width           =   1335
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
            Left            =   120
            TabIndex        =   41
            Top             =   3120
            Width           =   1335
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
            Left            =   120
            TabIndex        =   40
            Top             =   2160
            Width           =   1335
         End
         Begin VB.Label LblDescr 
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
            Left            =   120
            TabIndex        =   39
            Top             =   1200
            Width           =   1335
         End
         Begin VB.Label LblAccName 
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
            Left            =   120
            TabIndex        =   38
            Top             =   1680
            Width           =   1335
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
            Height          =   195
            Left            =   120
            TabIndex        =   37
            Top             =   720
            Width           =   1335
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
            Left            =   120
            TabIndex        =   36
            Top             =   2640
            Width           =   1335
         End
      End
      Begin VB.Frame FrameHRemark 
         Caption         =   "HostMaster's Remark"
         BeginProperty Font 
            Name            =   "MS Sans Serif"
            Size            =   8.25
            Charset         =   0
            Weight          =   700
            Underline       =   0   'False
            Italic          =   0   'False
            Strikethrough   =   0   'False
         EndProperty
         Height          =   1815
         Left            =   5760
         TabIndex        =   34
         Top             =   3480
         Width           =   5655
         Begin VB.CommandButton btnSearch 
            Caption         =   "Sear&ch..."
            Height          =   375
            Left            =   720
            TabIndex        =   61
            Top             =   1320
            Width           =   1815
         End
         Begin VB.CommandButton btnNew 
            Caption         =   "Ne&w..."
            Height          =   375
            Left            =   3120
            TabIndex        =   60
            Top             =   1320
            Width           =   1695
         End
         Begin VB.TextBox txtHMRemark 
            BackColor       =   &H8000000E&
            Height          =   885
            Left            =   120
            MultiLine       =   -1  'True
            ScrollBars      =   2  'Vertical
            TabIndex        =   24
            Tag             =   "X"
            Top             =   360
            Width           =   5295
         End
      End
      Begin VB.Label Label6 
         Caption         =   "*"
         BeginProperty Font 
            Name            =   "MS Sans Serif"
            Size            =   12
            Charset         =   0
            Weight          =   400
            Underline       =   0   'False
            Italic          =   0   'False
            Strikethrough   =   0   'False
         EndProperty
         ForeColor       =   &H00FF0000&
         Height          =   195
         Left            =   240
         TabIndex        =   55
         Top             =   5370
         Width           =   135
      End
      Begin VB.Label Label5 
         Caption         =   "Indicates new Nic Handle"
         ForeColor       =   &H00FF0000&
         Height          =   195
         Left            =   360
         TabIndex        =   54
         Top             =   5400
         Width           =   2175
      End
   End
   Begin VB.Frame FraHistory 
      Caption         =   "History"
      ForeColor       =   &H00000000&
      Height          =   1995
      Left            =   240
      TabIndex        =   32
      Top             =   6240
      Width           =   11505
      Begin MSHierarchicalFlexGridLib.MSHFlexGrid HFlexEditTicketD 
         Height          =   1335
         Left            =   360
         TabIndex        =   30
         Top             =   360
         Width           =   10815
         _ExtentX        =   19076
         _ExtentY        =   2355
         _Version        =   393216
         Cols            =   5
         BackColorFixed  =   16744576
         BackColorSel    =   16761024
         GridColor       =   16744576
         GridColorFixed  =   16744576
         GridColorUnpopulated=   -2147483630
         ScrollTrack     =   -1  'True
         FocusRect       =   0
         FillStyle       =   1
         SelectionMode   =   1
         AllowUserResizing=   1
         BeginProperty Font {0BE35203-8F91-11CE-9DE3-00AA004BB851} 
            Name            =   "Tahoma"
            Size            =   9
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
   Begin VB.Frame FraOption 
      Caption         =   "Options"
      ForeColor       =   &H00000000&
      Height          =   735
      Left            =   240
      TabIndex        =   31
      Top             =   8400
      Width           =   11535
      Begin VB.CommandButton cmdEdit 
         Caption         =   "&Edit"
         Height          =   375
         Left            =   4920
         TabIndex        =   27
         Top             =   240
         Width           =   1455
      End
      Begin VB.CommandButton cmdSave 
         Caption         =   "&Save"
         Height          =   375
         Left            =   8280
         TabIndex        =   29
         Top             =   240
         Width           =   1455
      End
      Begin VB.CommandButton cmdReject 
         Caption         =   "Re&ject"
         Height          =   375
         Left            =   3240
         TabIndex        =   26
         Top             =   240
         Width           =   1455
      End
      Begin VB.CommandButton cmdAccept 
         Caption         =   "Acce&pt"
         Height          =   375
         Left            =   1560
         MaskColor       =   &H8000000F&
         TabIndex        =   25
         Top             =   240
         Width           =   1455
      End
      Begin VB.CommandButton cmdCancel 
         Cancel          =   -1  'True
         Caption         =   "Ca&ncel"
         Height          =   375
         Left            =   6600
         MaskColor       =   &H8000000F&
         TabIndex        =   28
         Top             =   240
         Width           =   1455
      End
   End
End
Attribute VB_Name = "FrmEditTicketD"
Attribute VB_GlobalNameSpace = False
Attribute VB_Creatable = False
Attribute VB_PredeclaredId = True
Attribute VB_Exposed = False
Option Explicit

'*********************************************
'*                                           *
'* Written By : Brian Devlin                 *
'* Function   : Validates Domain information *
'*              on tickets                   *
'*********************************************

Const mlngColChngDt  As Long = 4

Dim mblnRecordLoaded As Boolean      '* Indicates if there is a valid record displayed
Dim mlngTicketNumber As Long         '* Key of record loaded
Dim mstrTimeStamp    As String       '* Holds the timestamd for the loaded record
Dim mstrCreatorNH    As String
Dim mstrBillNH       As String
Dim mblnHistory      As Boolean      '* Indicates if a History record is being displayed
Dim mblnViewing      As Boolean
Dim mcsFailState     As ControlState '* State of failure combos
Dim mcsFormState     As ControlState '* State of ticket values
Dim mlngDNSIndex     As Long


Dim WithEvents mfrmEditNicHandle As FrmEditNicHandle
Attribute mfrmEditNicHandle.VB_VarHelpID = -1

Private Type udtDNS
 strDNS      As String
 strIP       As String
 lngNameFail As Long
 lngIPFail   As Long
 strNameFail As String
 strIPFail   As String
End Type

Dim mudtDNS(0 To 2) As udtDNS

Public Event TypeChanged(ByVal strType As String)
Public Event Accepted(ByVal lngTicketNumber As Long, ByVal strStatus As String, ByVal lngStatus As Long)


Public Sub EditTicket(ByVal lngTicketNumber As Long)
 mblnViewing = False
 mcsFailState = csEdit
 mcsFormState = csView
 
 LoadTicket lngTicketNumber
 
 FormatControls Me, mcsFormState
 FormatContacts
 
 If GetOtherDomains(txtDomainHolder.Text) Then
        txtDomainHolder.ForeColor = vbRed
 End If
 
 ShowButtons True, True, True, True, True
End Sub


Public Sub ViewTicket(ByVal lngTicketNumber As Long)
 mblnViewing = True
 mcsFailState = csView
 mcsFormState = csView
 
 LoadTicket lngTicketNumber
 
 FormatControls Me, mcsFormState
 FormatContacts
 ShowButtons False, False, False, True, False
End Sub

Private Sub btnNew_Click()
    dlgNewResponse.Show 'BATCH 5
End Sub

Private Sub btnSearch_Click()
  Set gEditTicketForm = Me
  dlgBrowseTicketResponse.Show 'BATCH 5
End Sub

Private Sub cboClass_Click()
 FillCategoryCombo cboCategory, cboClass
End Sub

Private Sub cboDNS_Click()
 Dim lngIndex As Long
 
 lngIndex = cboDNS.ListIndex
 txtIP = mudtDNS(lngIndex).strIP
 
 cboDnsFail.ListIndex = FindItemData(cboDnsFail, mudtDNS(lngIndex).lngNameFail)
 cboIpFail.ListIndex = FindItemData(cboIpFail, mudtDNS(lngIndex).lngIPFail)
End Sub

Private Sub cboDNSEdit_Click()
 On Error GoTo DNSErr
 
 mlngDNSIndex = cboDNSEdit.ListIndex
 cboDNS.ListIndex = mlngDNSIndex
 txtIP = mudtDNS(mlngDNSIndex).strIP
 cboDnsFail.ListIndex = FindItemData(cboDnsFail, mudtDNS(mlngDNSIndex).lngNameFail)
 cboIpFail.ListIndex = FindItemData(cboIpFail, mudtDNS(mlngDNSIndex).lngIPFail)
 
'19-09-2002
Exit Sub

DNSErr:

    MsgBox "There was an error retrieving information for " & _
           "the address " & cboDNS.List(cboDNS.ListIndex) & vbCrLf & _
           "Err No:   " & Err.Number & vbCrLf & _
           "Err Desc: " & Err.Description, vbCritical
    
    Exit Sub

End Sub

Private Sub cboDNSEdit_LostFocus()
 If mcsFormState = csEdit Then
    If cboDNSEdit.DataChanged Then
       mudtDNS(mlngDNSIndex).strDNS = cboDNSEdit
       cboDNSEdit.RemoveItem mlngDNSIndex
       cboDNSEdit.AddItem mudtDNS(mlngDNSIndex).strDNS, mlngDNSIndex
       cboDNSEdit.DataChanged = False
       cboDNSEdit.ListIndex = mlngDNSIndex
    End If
 End If
End Sub



Private Sub txtIP_LostFocus()
 If mcsFormState = csEdit Then
    mudtDNS(mlngDNSIndex).strIP = txtIP
 End If
End Sub


Private Sub cboDnsFail_Click()
 DoEvents
 mudtDNS(cboDNS.ListIndex).lngNameFail = cboDnsFail.ItemData(cboDnsFail.ListIndex)
 mudtDNS(cboDNS.ListIndex).strNameFail = cboDnsFail.Text

End Sub


Private Sub cboIpFail_Click()
 DoEvents
 mudtDNS(cboDNS.ListIndex).lngIPFail = cboIpFail.ItemData(cboIpFail.ListIndex)
 mudtDNS(cboDNS.ListIndex).strIPFail = cboIpFail.Text
End Sub


Private Sub cmdAccept_Click()
 Dim blnError  As Boolean
 Dim strStatus As String
 Dim lngStatus As Long
 Dim strSQL    As String

 
 Screen.MousePointer = vbHourglass
 If mblnRecordLoaded Then
    If IsValidFailure(True) Then
       strSQL = "SELECT description, status FROM TicketAdminStatus ORDER BY status ASC"
       strStatus = "Passed"
       FrmList.ShowList strSQL, strStatus, lngStatus, blnError, "Admin Status"
'Celina Leong Phoenix1.1 03/04/03
'              AcceptTicket blnError  'line inserted by Celina Leong 17-09-2002
'              UpdateTicketFailures blnError, strStatus, lngStatus
       If Not blnError Then
          AcceptTicket blnError
          UpdateTicketFailures blnError, strStatus, lngStatus
          Unload Me
       End If
    End If
 End If
 Screen.MousePointer = vbNormal
End Sub


Private Sub CmdCancel_Click()


' If (mcsFormState = csEdit) Or (mblnHistory) Then
'    mcsFormState = csview
'    LoadTicket mlngTicketNumber
'    FormatFailureReasons mcsFailState
'    FormatControls Me, mcsFormState
'    FormatContacts
'    EditDNS False
'    txtHMRemark.Tag = ""
'    FormatControl txtHMRemark, csEdit
'    txtHMRemark.Tag = "X"
'    FraHistory.Enabled = True
' Else
'
'    Unload Me
' End If

'  Celina Leong Phoenix1.1 04/04/03
'If Ticket status is deleted then data only display in view mode only.
 
    If mblnViewing Then
        If mblnHistory And (mblnHistory) Then
    
         mcsFormState = csView
         LoadTicket mlngTicketNumber
         FormatFailureReasons mcsFailState = csView
         FormatControls Me, mcsFormState = csView
         FormatContacts
         EditDNS False
         
         FormatControl txtHMRemark, csView
        
         FraHistory.Enabled = True
        Else
            Unload Me
    End If
    Else
        If (mcsFormState = csEdit) Or (mblnHistory) Then
             mcsFormState = csView
             LoadTicket mlngTicketNumber
             FormatFailureReasons mcsFailState
             FormatControls Me, mcsFormState
             FormatContacts
             EditDNS False
             txtHMRemark.Tag = ""
             FormatControl txtHMRemark, csEdit
             txtHMRemark.Tag = "X"
             FraHistory.Enabled = True
 Else

    Unload Me
 End If
   End If
 
 
 '------------------------------------------------
End Sub


Private Sub CmdEdit_Click()
 mcsFormState = csEdit
 FormatControls Me, mcsFormState
 EditDNS True
 mlngDNSIndex = cboDNS.ListIndex
 If HasAuthority(atLeadHostMaster) Then
    If cboDNS.Listcount = 2 Then
       cboDNS.AddItem "", 2
    End If
 End If
 FraHistory.Enabled = False
 ShowButtons False, False, True, True, False
End Sub

Private Sub cmdReject_Click()
 Dim blnError As Boolean
 
 Screen.MousePointer = vbHourglass
 If mblnRecordLoaded Then
    If IsValidFailure(False) Then
       RejectTicket blnError
       UpdateTicketFailures blnError
       If Not blnError Then
          Unload Me
       End If
    End If
 End If
 Screen.MousePointer = vbNormal
End Sub


Private Sub CmdSave_Click()
 Dim blnError As Boolean

 Screen.MousePointer = vbHourglass
 If mblnRecordLoaded Then
    If mcsFormState = csEdit Then
       If IsValidFailure(False) Then
          If IsValidTicket() Then
             UpdateTicket blnError
             If Not blnError Then
                mcsFormState = csView
                LoadTicket mlngTicketNumber
                FormatControls Me, mcsFormState
                FormatContacts
                FormatFailureReasons mcsFailState
                EditDNS False
                txtHMRemark.Tag = ""
                FormatControl txtHMRemark, csEdit
                txtHMRemark.Tag = "X"
                FraHistory.Enabled = True
             End If
          End If
       Else
          txtHMRemark.SetFocus
       End If
       
    Else  '* Save Failure Codes
       If IsValidFailure(False) Then
          UpdateTicketFailures blnError
          If Not blnError Then
             Unload Me
          End If
       Else
          txtHMRemark.SetFocus
       End If
    End If
 End If
 Screen.MousePointer = vbNormal
End Sub


Private Sub Form_Load()
 PopEditTicketD_Grid
 
 FillTicketFailureReasonCombo cboDomainNameFail, 10, True
 FillTicketFailureReasonCombo cboDomainHolderFail, 11, True
 FillTicketFailureReasonCombo cboAccountNameFail, 12, True
 FillTicketFailureReasonCombo cboAdminContact1Fail, 13, True
 FillTicketFailureReasonCombo cboAdminContact2Fail, 14, True
 FillTicketFailureReasonCombo cboTechContactFail, 15, True
 FillTicketFailureReasonCombo cboBillContactFail, 16, True
 FillTicketFailureReasonCombo cboDnsFail, 17, True
 FillTicketFailureReasonCombo cboIpFail, 18, True
 FillTicketFailureReasonCombo cboCategoryFail, 19, True
 FillTicketFailureReasonCombo cboClassFail, 20, True
 
 FillClassCombo cboClass
 FillComboClikPay cboClikPaid ' Celina Leong Phoenix1.1 09/04/03
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

Set gEditTicketForm = Nothing

End Sub


Private Sub PopEditTicketD_Grid()
 With HFlexEditTicketD
    .ColWidth(0) = 100
    .ColWidth(1) = 1250
    .ColWidth(2) = 2000
    .ColWidth(3) = 7140
    .ColWidth(4) = 0
 
    .TextMatrix(0, 1) = "Date"
    .TextMatrix(0, 2) = "Nic Handle"
    .TextMatrix(0, 3) = "Remark"
 End With
End Sub


Private Sub HFlexEditTicketD_DblClick()
 Dim lngRow As Long
 
 If Not mblnRecordLoaded Then Exit Sub
 
 lngRow = HFlexEditTicketD.Row
' If lngRow = 1 Then
'    LoadTicket mlngTicketNumber
' Else
    LoadTicket mlngTicketNumber, HFlexEditTicketD.TextMatrix(lngRow, mlngColChngDt)
' End If
End Sub


Private Sub HFlexEditTicketD_KeyDown(KeyCode As Integer, Shift As Integer)
 If KeyCode = vbKeyReturn Then
    If mblnRecordLoaded Then
       HFlexEditTicketD_DblClick
    End If
 End If
End Sub

Private Sub HFlexEditTicketD_RowColChange()
 HFlexEditTicketD.RowSel = HFlexEditTicketD.Row
End Sub

Private Sub mfrmEditNicHandle_Accepted(ByVal strNicHandle As String)
 If txtAdminContact1 = strNicHandle Then
    FillContact txtAdminContact1, strNicHandle, "Active"
 End If
 If txtAdminContact2 = strNicHandle Then
    FillContact txtAdminContact2, strNicHandle, "Active"
 End If
 If txtTechContact = strNicHandle Then
    FillContact txtTechContact, strNicHandle, "Active"
 End If
 If txtBillContact = strNicHandle Then
    FillContact txtBillContact, strNicHandle, "Active"
 End If
End Sub

Private Sub TxtAdminContact1_DblClick()
 EditNicHandle txtAdminContact1
End Sub


Private Sub txtAdminContact1_KeyDown(KeyCode As Integer, Shift As Integer)
 If KeyCode = vbKeyReturn Then
    TxtAdminContact1_DblClick
 End If
End Sub


Private Sub TxtBillContact_DblClick()
 EditNicHandle txtBillContact
End Sub


Private Sub txtBillContact_KeyDown(KeyCode As Integer, Shift As Integer)
 If KeyCode = vbKeyReturn Then
    TxtBillContact_DblClick
 End If
End Sub




Private Sub TxtTechContact_DblClick()
 EditNicHandle txtTechContact
End Sub


Private Sub txtTechContact_KeyDown(KeyCode As Integer, Shift As Integer)
 If KeyCode = vbKeyReturn Then
    TxtTechContact_DblClick
 End If
End Sub


Private Sub TxtAdminContact2_DblClick()
 EditNicHandle txtAdminContact2
End Sub


Private Sub txtAdminContact2_KeyDown(KeyCode As Integer, Shift As Integer)
 If KeyCode = vbKeyReturn Then
    TxtAdminContact2_DblClick
 End If
End Sub


Public Sub LoadTicket(ByVal lngTicketNumber As Long, Optional ByVal strHistDate As String = "")
 Dim strSQL As String
 Dim rec    As ADODB.Recordset
 
 On Error GoTo LoadTicketERROR
 Screen.MousePointer = vbHourglass
 
 strSQL = "SELECT "
 strSQL = strSQL & "T_Number, "
 strSQL = strSQL & "D_Name, "
 strSQL = strSQL & "DN_Fail_Cd, "
 strSQL = strSQL & "D_Holder, "
 strSQL = strSQL & "DH_Fail_Cd, "
 strSQL = strSQL & "A_Name, "
 strSQL = strSQL & "AC_Fail_Cd, "
 strSQL = strSQL & "Admin_NH1, "
 strSQL = strSQL & "NHA1.NH_Status  AS nha1_Status, "
 strSQL = strSQL & "ANH1_Fail_Cd, "
 strSQL = strSQL & "Admin_NH2, "
 strSQL = strSQL & "NHA2.NH_Status  AS nha2_Status, "
 strSQL = strSQL & "ANH2_Fail_Cd, "
 strSQL = strSQL & "Tech_NH, "
 strSQL = strSQL & "NHT.NH_Status   AS nht_Status, "
 strSQL = strSQL & "TNH_Fail_Cd, "
 strSQL = strSQL & "Bill_NH, "
 strSQL = strSQL & "NHB.NH_Status   AS nhb_Status, "
 strSQL = strSQL & "BNH_Fail_Cd, "
 strSQL = strSQL & "DNS_Name1, "
 strSQL = strSQL & "DNS1_Fail_Cd, "
 strSQL = strSQL & "DNS_IP1, "
 strSQL = strSQL & "DNSIP1_Fail_Cd, "
 strSQL = strSQL & "DNS_Name2, "
 strSQL = strSQL & "DNS2_Fail_Cd, "
 strSQL = strSQL & "DNS_IP2, "
 strSQL = strSQL & "DNSIP2_Fail_Cd, "
 strSQL = strSQL & "DNS_Name3, "
 strSQL = strSQL & "DNS3_Fail_Cd, "
 strSQL = strSQL & "DNS_IP3, "
 strSQL = strSQL & "DNSIP3_Fail_Cd, "
 strSQL = strSQL & "T_Category, "
 strSQL = strSQL & "T_Category_Fail_Cd, "
 strSQL = strSQL & "T_Class, "
 strSQL = strSQL & "T_Class_Fail_Cd, "
 strSQL = strSQL & "t_Reg_Dt, "
 strSQL = strSQL & "T_Remark, "
 strSQL = strSQL & "H_Remark, "
 strSQL = strSQL & "T_TStamp, "
 strSQL = strSQL & "Creator_NH, "
 strSQL = strSQL & "Admin_Status, "
 strSQL = strSQL & "T_ClikPaid  " ' Celina Leong Phoenix1.1 09/04/03
 strSQL = strSQL & "FROM  "
 strSQL = strSQL & " NicHandle AS NHA1, "
 'strSQL = strSQL & " NicHandle AS NHA2, "
 strSQL = strSQL & " NicHandle AS NHT, "
 strSQL = strSQL & " NicHandle AS NHB, "
 strSQL = strSQL & " Account   AS a, "
 
 If strHistDate = "" Then
    strSQL = strSQL & "Ticket AS T "
 Else
    strSQL = strSQL & "TicketHist AS T "
 End If

 strSQL = strSQL & "LEFT JOIN NicHandle AS NHA2 "
 'strSQL = strSQL & "ON a.A_Number = T.A_Number "
 strSQL = strSQL & "ON  T.Admin_NH2=NHA2.Nic_Handle "
 
 strSQL = strSQL & "WHERE T.Admin_NH1=NHA1.Nic_Handle "
 'strSQL = strSQL & "AND   T.Admin_NH2=NHA2.Nic_Handle "
 strSQL = strSQL & "AND   T.Tech_NH=NHT.Nic_Handle "
 strSQL = strSQL & "AND   T.Bill_NH=NHB.Nic_Handle "
 strSQL = strSQL & "AND   a.A_Number=T.A_Number "
 strSQL = strSQL & "AND   T_Number=" & lngTicketNumber & " "
  If strHistDate <> "" Then
    strSQL = strSQL & "AND Chng_Dt = " & CDBTime(strHistDate)
 End If
 
 DB.Execute strSQL, rec
 
 
 If Not rec.EOF Then
    cboDNS.Clear
    cboDNSEdit.Clear
    
   
    
    mstrTimeStamp = rec.Fields("T_TStamp")
    mstrCreatorNH = rec.Fields("Creator_NH")
    mstrBillNH = rec.Fields("Bill_NH") 'Celina Leong 08-10-2002
'    mstrStatus = rec.Fields("Admin_Status")
    txtDomainName = rec.Fields("D_Name")
    txtDomainHolder = rec.Fields("D_Holder")
    txtAccountName = rec.Fields("A_Name")
    FillContact txtAdminContact1, rec.Fields("Admin_NH1"), rec.Fields("nha1_Status")
    FillContact txtAdminContact2, NoNull(rec.Fields("Admin_NH2")), NoNull(rec.Fields("nha2_Status"))
    FillContact txtTechContact, rec.Fields("Tech_NH"), rec.Fields("nht_Status")
    FillContact txtBillContact, rec.Fields("Bill_NH"), rec.Fields("nhb_Status")
    txtCategory = NoNull(rec.Fields("T_Category"))
    txtClass = NoNull(rec.Fields("T_Class"))
    TxtChanged = NoNull(rec.Fields("T_TStamp"))
    txtRemark = NoNull(rec.Fields("T_Remark"))
    txtHMRemark = NoNull(rec.Fields("H_Remark"))
    txtClikPaid = NoNull(rec.Fields("T_ClikPaid")) 'Celina Leong Phoenix1.1 09/04/03
''    cboClikPaid = NoNull(rec.Fields("T_ClikPaid")) 'Celina Leong Phoenix1.1 09/04/03


    cboDNS.AddItem rec.Fields("DNS_Name1"), 0
    cboDNS.AddItem rec.Fields("DNS_Name2"), 1

    If NoNull(rec.Fields("DNS_Name3")) <> "" Then
       cboDNS.AddItem rec.Fields("DNS_Name3"), 2
     
    End If

    cboDNSEdit.AddItem rec.Fields("DNS_Name1"), 0
    cboDNSEdit.AddItem rec.Fields("DNS_Name2"), 1

    If NoNull(rec.Fields("DNS_Name3")) <> "" Then
       cboDNSEdit.AddItem rec.Fields("DNS_Name3"), 2

    Else
       cboDNSEdit.AddItem "", 2
    End If
    
    mudtDNS(0).strDNS = NoNull(rec.Fields("DNS_Name1"))
    mudtDNS(0).strIP = NoNull(rec.Fields("DNS_IP1"))
    mudtDNS(0).lngNameFail = NoNull(rec.Fields("DNS1_Fail_Cd"))
    mudtDNS(0).lngIPFail = NoNull(rec.Fields("DNSIP1_Fail_Cd"))
    mudtDNS(0).strNameFail = cboDnsFail.List(FindItemData(cboDnsFail, mudtDNS(0).lngNameFail))
    mudtDNS(0).strIPFail = cboIpFail.List(FindItemData(cboIpFail, mudtDNS(0).lngIPFail))
 
    mudtDNS(1).strDNS = NoNull(rec.Fields("DNS_Name2"))
    mudtDNS(1).strIP = NoNull(rec.Fields("DNS_IP2"))
    mudtDNS(1).lngNameFail = NoNull(rec.Fields("DNS2_Fail_Cd"))
    mudtDNS(1).lngIPFail = NoNull(rec.Fields("DNSIP2_Fail_Cd"))
    mudtDNS(1).strNameFail = cboDnsFail.List(FindItemData(cboDnsFail, mudtDNS(1).lngNameFail))
    mudtDNS(1).strIPFail = cboIpFail.List(FindItemData(cboIpFail, mudtDNS(1).lngIPFail))
 
    mudtDNS(2).strDNS = NoNull(rec.Fields("DNS_Name3"))
    mudtDNS(2).strIP = NoNull(rec.Fields("DNS_IP3"))
    mudtDNS(2).lngNameFail = NoNull(rec.Fields("DNS3_Fail_Cd"))
    mudtDNS(2).lngIPFail = NoNull(rec.Fields("DNSIP3_Fail_Cd"))
    mudtDNS(2).strNameFail = cboDnsFail.List(FindItemData(cboDnsFail, mudtDNS(2).lngNameFail))
    mudtDNS(2).strIPFail = cboIpFail.List(FindItemData(cboIpFail, mudtDNS(2).lngIPFail))
 
    cboDomainNameFail.ListIndex = FindItemData(cboDomainNameFail, NoNull(rec.Fields("DN_Fail_Cd")))
    cboDomainHolderFail.ListIndex = FindItemData(cboDomainHolderFail, NoNull(rec.Fields("DH_Fail_Cd")))
    cboAccountNameFail.ListIndex = FindItemData(cboAccountNameFail, NoNull(rec.Fields("AC_Fail_Cd")))
    cboAdminContact1Fail.ListIndex = FindItemData(cboAdminContact1Fail, NoNull(rec.Fields("ANH1_Fail_Cd")))
'    cboAdminContact2Fail.ListIndex = FindItemData(cboAdminContact2Fail, NoNull(rec.Fields("ANH2_Fail_Cd")))
'Celina Leong Phoenix1.1 10/04/03
    If Trim(txtAdminContact2) = "" Then
        cboAdminContact2Fail.Text = gstrComboNoneText
    Else
        cboAdminContact2Fail.ListIndex = FindItemData(cboAdminContact2Fail, NoNull(rec.Fields("ANH2_Fail_Cd")))
    End If
 '---------------------------------------------------------
    cboTechContactFail.ListIndex = FindItemData(cboTechContactFail, NoNull(rec.Fields("TNH_Fail_Cd")))
    cboBillContactFail.ListIndex = FindItemData(cboBillContactFail, NoNull(rec.Fields("BNH_Fail_Cd")))
    cboCategoryFail.ListIndex = FindItemData(cboCategoryFail, NoNull(rec.Fields("T_Category_Fail_Cd")))
    cboClassFail.ListIndex = FindItemData(cboClassFail, NoNull(rec.Fields("T_Class_Fail_Cd")))
 
    On Error Resume Next
    cboClass = NoNull(rec.Fields("T_Class"))
    FillCategoryCombo cboCategory, cboClass
    cboCategory = NoNull(rec.Fields("T_Category"))
    cboClikPaid = NoNull(rec.Fields("T_clikpaid")) 'Celina Leong Phoenix1.1 09/04/03
    
    On Error GoTo LoadTicketERROR
 
    cboDNS.ListIndex = 0
    mblnRecordLoaded = True
    mlngTicketNumber = lngTicketNumber
        
    If (strHistDate = "") Then
       If Not mblnViewing Then
          txtHMRemark.Text = ""
          FormatFailureReasons csEdit
          txtHMRemark.Tag = ""
          FormatControl txtHMRemark, csEdit
          txtHMRemark.Tag = "X"
       Else
          txtHMRemark = NoNull(rec.Fields("H_Remark"))
          FormatFailureReasons csView
          txtHMRemark.Tag = ""
          FormatControl txtHMRemark, csView
          txtHMRemark.Tag = "X"
       End If
       FraCurrent.Caption = "Current"
       mlngTicketNumber = lngTicketNumber
       LoadTicketHistory lngTicketNumber
       mblnHistory = False
    Else
       txtHMRemark = NoNull(rec.Fields("H_Remark"))
       FraCurrent.Caption = "Historical"
       FormatFailureReasons csView
       txtHMRemark.Tag = ""
       FormatControl txtHMRemark, csView
       txtHMRemark.Tag = "X"
       mblnHistory = True
    End If

'CC20060425 Added to highlight items that have changed on the ticket
'CC20060425 Reset all label highlights
    SetLabelHighlight LblDescr
    SetLabelHighlight LblAccName
    SetLabelHighlight LblAdCon1
    SetLabelHighlight LblAdCon2
    SetLabelHighlight LblTechCon
    SetLabelHighlight LblBilCon
    SetLabelHighlight LblDNS
    SetLabelHighlight LblDNSIP
    SetLabelHighlight LblSndCat
    SetLabelHighlight LblPriCat
    SetLabelHighlight LblClikPaid

    If Not mblnHistory Then 'only process for ticket record (not history)
        strSQL = "SELECT "
        strSQL = strSQL & "d.D_Name, d.D_Holder, d.D_Category, d.D_Class, d.D_ClikPaid, a.A_Name, "
        strSQL = strSQL & "c1.Contact_NH as Admin_NH1, "
        strSQL = strSQL & "c2.Contact_NH as Admin_NH2, "
        strSQL = strSQL & "c3.Contact_NH as Tech_NH, "
        strSQL = strSQL & "c4.Contact_NH as Bill_NH, "
        strSQL = strSQL & "n1.DNS_Name as DNS_Name1, n1.DNS_IpAddr as DNS_IP1, "
        strSQL = strSQL & "n2.DNS_Name as DNS_Name2, n2.DNS_IpAddr as DNS_IP2, "
        strSQL = strSQL & "n3.DNS_Name as DNS_Name3, n3.DNS_IpAddr as DNS_IP3 "
        strSQL = strSQL & "FROM Domain as d "
        strSQL = strSQL & "LEFT JOIN Account AS a ON d.A_Number=a.A_Number "
        strSQL = strSQL & "LEFT JOIN Contact as c1 ON d.D_Name=c1.D_Name AND c1.Type='A' "
        strSQL = strSQL & "LEFT JOIN Contact as c2 ON d.D_Name=c2.D_Name AND c2.Type='A' AND c1.Contact_NH<>c2.Contact_NH "
        strSQL = strSQL & "LEFT JOIN Contact as c3 ON d.D_Name=c3.D_Name AND c3.Type='T' "
        strSQL = strSQL & "LEFT JOIN Contact as c4 ON d.D_Name=c4.D_Name AND c4.Type='B' "
        strSQL = strSQL & "LEFT JOIN DNS as n1 ON d.D_Name=n1.D_Name AND n1.DNS_Order=1 "
        strSQL = strSQL & "LEFT JOIN DNS as n2 ON d.D_Name=n2.D_Name AND n2.DNS_Order=2 "
        strSQL = strSQL & "LEFT JOIN DNS as n3 ON d.D_Name=n3.D_Name AND n3.DNS_Order=3 "
        strSQL = strSQL & "WHERE d.D_Name='" & txtDomainName.Text & "' LIMIT 0,1 "
        
        DB.Execute strSQL, rec
 
        If Not rec.EOF Then 'there is a domain record for comparision
            SetLabelHighlight LblAdCon1, NoNull(rec.Fields("Admin_NH1")), txtAdminContact1.Text
            SetLabelHighlight LblAdCon2, NoNull(rec.Fields("Admin_NH2")), txtAdminContact2.Text
            SetLabelHighlight LblTechCon, NoNull(rec.Fields("Tech_NH")), txtTechContact.Text
            SetLabelHighlight LblBilCon, NoNull(rec.Fields("Bill_NH")), txtBillContact.Text
            SetLabelHighlight LblSndCat, NoNull(rec.Fields("D_Class")), cboClass.Text
            SetLabelHighlight LblPriCat, NoNull(rec.Fields("D_Category")), cboCategory.Text
            SetLabelHighlight LblAccName, NoNull(rec.Fields("A_Name")), txtAccountName.Text
            SetLabelHighlight LblDescr, NoNull(rec.Fields("D_Holder")), txtDomainHolder.Text
            SetLabelHighlight LblClikPaid, NoNull(rec.Fields("D_ClikPaid")), txtClikPaid.Text
            SetLabelHighlight LblDNS, _
                UCase(NoNull(rec.Fields("DNS_Name1")) & "," & NoNull(rec.Fields("DNS_Name2")) & "," & NoNull(rec.Fields("DNS_Name3"))), _
                UCase(mudtDNS(0).strDNS & "," & mudtDNS(1).strDNS & "," & mudtDNS(2).strDNS)
            SetLabelHighlight LblDNSIP, _
                UCase(NoNull(rec.Fields("DNS_IP1")) & "," & NoNull(rec.Fields("DNS_IP2"))) & "," & NoNull(rec.Fields("DNS_IP3")), _
                UCase(mudtDNS(0).strIP & "," & mudtDNS(1).strIP & "," & mudtDNS(2).strIP)
                
        End If
    End If
'CC20060425 end....
    ShowButtons True, True, True, True, True
 Else
    mblnRecordLoaded = False
    ShowButtons False, False, False, True, False
 End If
 
 If Not HasAuthority(atLeadHostMaster) Then
    cboDNSEdit.Tag = "X"
    cboDNS.Tag = "X"
    txtIP.Tag = "X"
 End If

'  If GetOtherDomains(txtDomainHolder.Text) Then
'       txtDomainHolder.ForeColor = vbRed
'  End If

 Screen.MousePointer = vbNormal
Exit Sub
LoadTicketERROR:
 mblnRecordLoaded = False
'@ ShowButtons False, False, False, True
 Screen.MousePointer = vbNormal
 DB.ShowError "Unable to load Ticket"
End Sub

Private Sub SetLabelHighlight(ByRef Lab As Label, Optional ByVal OldS As String = "", _
    Optional ByVal NewS As String = "")
    
    If OldS <> NewS Then 'set the colour to RED and the tooltip to the old string
        Lab.ForeColor = &HFF&
        Lab.ToolTipText = OldS
    Else 'reset to standard colour and reset tooltip
        Lab.ForeColor = &H80000012
        Lab.ToolTipText = ""
    End If
End Sub


Private Sub LoadTicketHistory(ByVal lngTicketNumber As Long)
 Dim strSQL As String
 Dim rec    As ADODB.Recordset
 
 On Error GoTo LoadTicketHistoryERROR
 
 strSQL = "SELECT " & FormatDBDate("Chng_Dt") & " AS 'Date',"
 strSQL = strSQL & "Chng_NH AS 'Nic Handle', "
 strSQL = strSQL & "H_Remark AS Remark, "
 strSQL = strSQL & "Chng_Dt "
 strSQL = strSQL & "FROM TicketHist "
 strSQL = strSQL & "WHERE T_Number = " & lngTicketNumber & " "
 strSQL = strSQL & "ORDER BY Chng_Dt DESC"
 
 DB.Execute strSQL, rec
 
 Set HFlexEditTicketD.DataSource = rec.DataSource

Exit Sub
LoadTicketHistoryERROR:
 DB.ShowError "Unable to Load Ticket History"
End Sub


Private Sub UpdateTicketFailures(ByRef blnError As Boolean, Optional strStatus As String = "", Optional ByVal lngStatus As Long = -1)
 Dim strSQL         As String
 Dim lngRowsUpdated As Long
 
 On Error GoTo UpdateTicketFailuresERROR
 If blnError Then Exit Sub
 
 DB.BeginTransaction
 
 strSQL = "UPDATE Ticket SET "
 strSQL = strSQL & "DN_Fail_Cd=" & GetFailCode(cboDomainNameFail.ItemData(cboDomainNameFail.ListIndex)) & ", "
 strSQL = strSQL & "DH_Fail_Cd=" & GetFailCode(cboDomainHolderFail.ItemData(cboDomainHolderFail.ListIndex)) & ", "
 strSQL = strSQL & "AC_Fail_Cd=" & GetFailCode(cboAccountNameFail.ItemData(cboAccountNameFail.ListIndex)) & ", "
 strSQL = strSQL & "ANH1_Fail_Cd=" & GetFailCode(cboAdminContact1Fail.ItemData(cboAdminContact1Fail.ListIndex)) & ", "
 strSQL = strSQL & "ANH2_Fail_Cd=" & GetFailCode(cboAdminContact2Fail.ItemData(cboAdminContact2Fail.ListIndex)) & ", "
 strSQL = strSQL & "TNH_Fail_Cd=" & GetFailCode(cboTechContactFail.ItemData(cboTechContactFail.ListIndex)) & ", "
 strSQL = strSQL & "BNH_Fail_Cd=" & GetFailCode(cboBillContactFail.ItemData(cboBillContactFail.ListIndex)) & ", "
 
 strSQL = strSQL & "DNS1_Fail_Cd=" & GetFailCode(mudtDNS(0).lngNameFail) & ", "
 strSQL = strSQL & "DNSIP1_Fail_Cd=" & GetFailCode(mudtDNS(0).lngIPFail) & ", "
 strSQL = strSQL & "DNS2_Fail_Cd=" & GetFailCode(mudtDNS(1).lngNameFail) & ", "
 strSQL = strSQL & "DNSIP2_Fail_Cd=" & GetFailCode(mudtDNS(1).lngIPFail) & ", "
 If mudtDNS(2).strDNS = "" Then
    strSQL = strSQL & "DNS3_Fail_Cd=NULL, "
    strSQL = strSQL & "DNSIP3_Fail_Cd=NULL, "
 Else
    strSQL = strSQL & "DNS3_Fail_Cd=" & GetFailCode(mudtDNS(2).lngNameFail) & ", "
    strSQL = strSQL & "DNSIP3_Fail_Cd=" & GetFailCode(mudtDNS(2).lngIPFail) & ", "
 End If
 
 strSQL = strSQL & "T_Category_Fail_Cd=" & GetFailCode(cboCategoryFail.ItemData(cboCategoryFail.ListIndex)) & ", "
 strSQL = strSQL & "T_Class_Fail_Cd=" & GetFailCode(cboClassFail.ItemData(cboClassFail.ListIndex)) & ", "
 If strStatus <> "" Then
     strSQL = strSQL & "Admin_Status=" & lngStatus & ", "
     strSQL = strSQL & "Ad_StatusDt=NOW(), "
     strSQL = strSQL & "CheckedOut=" & CDBText("N") & ", "
     strSQL = strSQL & "CheckedOutTo=NULL, "
 End If
 strSQL = strSQL & "H_Remark=" & CDBText(txtHMRemark) & " "
 strSQL = strSQL & "WHERE T_Number=" & mlngTicketNumber & " "
 strSQL = strSQL & "AND T_TStamp=" & CDBTimeStamp(mstrTimeStamp) & " "
 strSQL = strSQL & "AND CheckedOutTo=" & CDBText(UserID) & " "
 
 DB.Execute strSQL, , , , lngRowsUpdated
 
 If lngRowsUpdated <> 1 Then
    MsgBox "Could not Update Ticket." & vbCrLf & vbCrLf & "Possible reasons : Account updated by another user while you were editing it", vbExclamation
    blnError = True
 Else
    WriteHistory
    If strStatus <> "" Then
       RaiseEvent Accepted(mlngTicketNumber, strStatus, lngStatus)
    End If
    DB.CommitTransaction
    blnError = False
 End If
Exit Sub
UpdateTicketFailuresERROR:
 On Error Resume Next
 
 If DB.TransactionLevel > 0 Then
    DB.RollbackTransaction
 End If
 DB.ShowError "Unable to save Ticket"
 blnError = True
End Sub



Private Sub UpdateTicket(ByRef blnError As Boolean)
 Dim strSQL         As String
 Dim lngRowsUpdated As Long
 
 On Error GoTo UpdateTicketERROR
 If blnError Then Exit Sub
 
 DB.BeginTransaction
 
 strSQL = "UPDATE Ticket SET "
 strSQL = strSQL & "D_Holder=" & CDBText(txtDomainHolder) & ","
 strSQL = strSQL & "T_Class=" & CDBText(cboClass) & ","
 strSQL = strSQL & "T_Category=" & CDBText(cboCategory) & ","
 strSQL = strSQL & "Admin_NH1=" & CDBText(txtAdminContact1) & ","
 If Trim(txtAdminContact2) = "" Then
    strSQL = strSQL & "Admin_NH2=NULL,"
 Else
    strSQL = strSQL & "Admin_NH2=" & CDBText(txtAdminContact2) & ","
 End If
 strSQL = strSQL & "Tech_NH=" & CDBText(txtTechContact) & ","
 strSQL = strSQL & "Bill_NH=" & CDBText(txtBillContact) & ","
  
 strSQL = strSQL & "DN_Fail_Cd=" & GetFailCode(cboDomainNameFail.ItemData(cboDomainNameFail.ListIndex)) & ", "
 strSQL = strSQL & "DH_Fail_Cd=" & GetFailCode(cboDomainHolderFail.ItemData(cboDomainHolderFail.ListIndex)) & ", "
 strSQL = strSQL & "AC_Fail_Cd=" & GetFailCode(cboAccountNameFail.ItemData(cboAccountNameFail.ListIndex)) & ", "
 strSQL = strSQL & "ANH1_Fail_Cd=" & GetFailCode(cboAdminContact1Fail.ItemData(cboAdminContact1Fail.ListIndex)) & ", "
 strSQL = strSQL & "ANH2_Fail_Cd=" & GetFailCode(cboAdminContact2Fail.ItemData(cboAdminContact2Fail.ListIndex)) & ", "
 strSQL = strSQL & "TNH_Fail_Cd=" & GetFailCode(cboTechContactFail.ItemData(cboTechContactFail.ListIndex)) & ", "
 strSQL = strSQL & "BNH_Fail_Cd=" & GetFailCode(cboBillContactFail.ItemData(cboBillContactFail.ListIndex)) & ", "
 strSQL = strSQL & "T_Category_Fail_Cd=" & GetFailCode(cboCategoryFail.ItemData(cboCategoryFail.ListIndex)) & ", "
 strSQL = strSQL & "T_Class_Fail_Cd=" & GetFailCode(cboClassFail.ItemData(cboClassFail.ListIndex)) & ", "
 
 strSQL = strSQL & "DNS_Name1=" & CDBText(mudtDNS(0).strDNS) & ", "
 strSQL = strSQL & "DNS1_Fail_Cd=" & GetFailCode(mudtDNS(0).lngNameFail) & ", "
 strSQL = strSQL & "DNSIP1_Fail_Cd=" & GetFailCode(mudtDNS(0).lngIPFail) & ", "
 If mudtDNS(0).strIP = "" Then
    strSQL = strSQL & "DNS_IP1=NULL, "
 Else
    strSQL = strSQL & "DNS_IP1=" & CDBText(mudtDNS(0).strIP) & ", "
 End If
 
 strSQL = strSQL & "DNS_Name2=" & CDBText(mudtDNS(1).strDNS) & ", "
 strSQL = strSQL & "DNS2_Fail_Cd=" & GetFailCode(mudtDNS(1).lngNameFail) & ", "
 strSQL = strSQL & "DNSIP2_Fail_Cd=" & GetFailCode(mudtDNS(1).lngIPFail) & ", "
 If mudtDNS(1).strIP = "" Then
    strSQL = strSQL & "DNS_IP2=NULL, "
 Else
    strSQL = strSQL & "DNS_IP2=" & CDBText(mudtDNS(1).strIP) & ", "
 End If
 
 If mudtDNS(2).strDNS = "" Then
    strSQL = strSQL & "DNS_Name3=NULL, "
    strSQL = strSQL & "DNS3_Fail_Cd=NULL, "
    strSQL = strSQL & "DNSIP3_Fail_Cd=NULL, "
    strSQL = strSQL & "DNS_IP3=NULL, "
 Else
    strSQL = strSQL & "DNS_Name3=" & CDBText(mudtDNS(2).strDNS) & ", "
    strSQL = strSQL & "DNS3_Fail_Cd=" & GetFailCode(mudtDNS(2).lngNameFail) & ", "
    strSQL = strSQL & "DNSIP3_Fail_Cd=" & GetFailCode(mudtDNS(2).lngIPFail) & ", "
    If mudtDNS(2).strIP = "" Then
       strSQL = strSQL & "DNS_IP3=NULL, "
    Else
       strSQL = strSQL & "DNS_IP3=" & CDBText(mudtDNS(2).strIP) & ", "
    End If
 
 End If
   
 strSQL = strSQL & "H_Remark=" & CDBText(txtHMRemark) & ", "
 strSQL = strSQL & "T_ClikPaid=" & CDBText(cboClikPaid) & " " ' Celina Leong Phoenix1.1 09/04/03

 strSQL = strSQL & "WHERE T_Number=" & mlngTicketNumber & " "
 strSQL = strSQL & "AND T_TStamp=" & CDBTimeStamp(mstrTimeStamp) & " "
 strSQL = strSQL & "AND CheckedOutTo=" & CDBText(UserID) & " "
 
 DB.Execute strSQL, , , , lngRowsUpdated
 
 If lngRowsUpdated <> 1 Then
    MsgBox "Could not Update Ticket." & vbCrLf & vbCrLf & "Possible reasons : Account updated by another user while you were editing it", vbExclamation
    blnError = True
 Else
    WriteHistory
    DB.CommitTransaction
    blnError = False
 End If
Exit Sub
UpdateTicketERROR:
 On Error Resume Next
 
 If DB.TransactionLevel > 0 Then
    DB.RollbackTransaction
 End If
 DB.ShowError "Unable to save Ticket"
 blnError = True
End Sub


Private Sub WriteHistory()
 Dim strSQL As String
 
 strSQL = "INSERT INTO TicketHist ("
 strSQL = strSQL & "Chng_Dt, "
 strSQL = strSQL & "Chng_NH, "
 strSQL = strSQL & "T_Number, T_Type, D_Name, DN_Fail_Cd, D_Holder, DH_Fail_Cd, "
 strSQL = strSQL & "A_Number, AC_Fail_Cd, T_Class, T_Class_Fail_Cd, T_Category, T_Category_Fail_Cd, T_Remark, "
 strSQL = strSQL & "Admin_NH1, ANH1_Fail_Cd, Admin_NH2, ANH2_Fail_Cd, "
 strSQL = strSQL & "Tech_NH, TNH_Fail_Cd, Bill_NH, BNH_Fail_Cd, Creator_NH, "
 strSQL = strSQL & "DNS_Name1, DNS1_Fail_Cd, DNS_IP1, DNSIP1_Fail_Cd, "
 strSQL = strSQL & "DNS_Name2, DNS2_Fail_Cd, DNS_IP2, DNSIP2_Fail_Cd, "
 strSQL = strSQL & "DNS_Name3, DNS3_Fail_Cd, DNS_IP3, DNSIP3_Fail_Cd, "
 strSQL = strSQL & "Admin_Status, Ad_StatusDt, Tech_Status, T_Status_Dt, "
 strSQL = strSQL & "CheckedOut, CheckedOutTo, T_Reg_Dt, T_Ren_Dt, T_TStamp, H_Remark,  T_ClikPaid) " 'Celina Leong Phoenix1.1 08/09 insert T_ClikPaid
 
 strSQL = strSQL & "SELECT DISTINCT " 'CC20051221 fix for MySQLv4.0
 strSQL = strSQL & "NOW(), "
 strSQL = strSQL & CDBText(UserID) & ", "
 strSQL = strSQL & "T_Number, T_Type, D_Name, DN_Fail_Cd, D_Holder, DH_Fail_Cd, "
 strSQL = strSQL & "A_Number, AC_Fail_Cd, T_Class, T_Class_Fail_Cd, T_Category, T_Category_Fail_Cd, T_Remark, "
 strSQL = strSQL & "Admin_NH1, ANH1_Fail_Cd, Admin_NH2, ANH2_Fail_Cd, "
 strSQL = strSQL & "Tech_NH, TNH_Fail_Cd, Bill_NH, BNH_Fail_Cd, Creator_NH, "
 strSQL = strSQL & "DNS_Name1, DNS1_Fail_Cd, DNS_IP1, DNSIP1_Fail_Cd, "
 strSQL = strSQL & "DNS_Name2, DNS2_Fail_Cd, DNS_IP2, DNSIP2_Fail_Cd, "
 strSQL = strSQL & "DNS_Name3, DNS3_Fail_Cd, DNS_IP3, DNSIP3_Fail_Cd, "
 strSQL = strSQL & "Admin_Status, Ad_StatusDt, Tech_Status, T_Status_Dt, "
 strSQL = strSQL & "CheckedOut, CheckedOutTo, T_Reg_Dt, T_Ren_Dt, T_TStamp, H_Remark, T_ClikPaid " 'Celina Leong Phoenix1.1 08/09 insert T_ClikPaid

 strSQL = strSQL & "FROM Ticket "
 strSQL = strSQL & "WHERE T_Number=" & mlngTicketNumber
 DB.Execute strSQL
End Sub


Private Function GetFailCode(ByVal lngFailCode) As String
 If lngFailCode = 0 Then
    GetFailCode = "NULL"
 Else
    GetFailCode = CStr(lngFailCode)
 End If
End Function


Private Sub FillContact(txtContact As TextBox, ByVal strNicHandle As String, ByVal strStatus As String)
 txtContact.Text = strNicHandle
 If strStatus = "New" Then
'    txtContact.ForeColor = vbBlue
    txtContact.Tag = "N"
 Else
'    txtContact.ForeColor = vbButtonText
    txtContact.Tag = ""
 End If
 txtContact.ToolTipText = GetName(strNicHandle)
End Sub

Private Sub FormatContacts()
 If txtAdminContact1.Tag = "N" Then
    txtAdminContact1.ForeColor = vbBlue
 Else
    txtAdminContact1.ForeColor = vbButtonText
 End If
 If txtAdminContact2.Tag = "N" Then
    txtAdminContact2.ForeColor = vbBlue
 Else
    txtAdminContact2.ForeColor = vbButtonText
 End If
 If txtTechContact.Tag = "N" Then
    txtTechContact.ForeColor = vbBlue
 Else
    txtTechContact.ForeColor = vbButtonText
 End If
 If txtBillContact.Tag = "N" Then
    txtBillContact.ForeColor = vbBlue
 Else
    txtBillContact.ForeColor = vbButtonText
 End If
End Sub

Private Sub ShowButtons(ByVal blnAccept As Boolean, _
                        ByVal blnReject As Boolean, _
                        ByVal blnSave As Boolean, _
                        ByVal blnCancel As Boolean, _
                        ByVal blnEdit As Boolean)
 Dim blnAllowed As Boolean

 blnAllowed = HasAuthority(atHostMaster + atLeadHostMaster)
 cmdAccept.Enabled = blnAccept And blnAllowed And (Not mblnHistory) And (Not mblnViewing)
 cmdReject.Enabled = blnReject And blnAllowed And (Not mblnHistory) And (Not mblnViewing)
 cmdSave.Enabled = blnSave And blnAllowed And (Not mblnHistory) And (Not mblnViewing)
 cmdCancel.Enabled = blnCancel
 cmdEdit.Enabled = blnEdit And HasAuthority(atLeadHostMaster + atHostMaster) And (Not mblnHistory) And (Not mblnViewing)
End Sub


Private Function IsValidFailure(ByVal blnCheckFails As Boolean) As Boolean
 Dim blnInError As Boolean
 Dim ctl        As Control
 
 If blnCheckFails Then
    IsValidFailureReason cboDomainNameFail, blnInError
    IsValidFailureReason cboDomainHolderFail, blnInError
    IsValidFailureReason cboAccountNameFail, blnInError
    IsValidFailureReason cboAdminContact1Fail, blnInError
    IsValidFailureReason cboAdminContact2Fail, blnInError
    IsValidFailureReason cboAdminContact2Fail, blnInError
    IsValidFailureReason cboTechContactFail, blnInError
    IsValidFailureReason cboBillContactFail, blnInError
    IsValidFailureReason cboDnsFail, blnInError
    IsValidFailureReason cboIpFail, blnInError
    IsValidFailureReason cboCategoryFail, blnInError
    IsValidFailureReason cboClassFail, blnInError
  
    If mudtDNS(2).lngNameFail <> 0 Then
       blnInError = True
       FormatControl cboDnsFail, csError
       cboDNS.ListIndex = 2
    End If
    If mudtDNS(2).lngIPFail <> 0 Then
       blnInError = True
       FormatControl cboIpFail, csError
       cboDNS.ListIndex = 2
    End If
    
    If mudtDNS(1).lngNameFail <> 0 Then
       blnInError = True
       FormatControl cboDnsFail, csError
       cboDNS.ListIndex = 1
    End If
    If mudtDNS(1).lngIPFail <> 0 Then
       blnInError = True
       FormatControl cboIpFail, csError
       cboDNS.ListIndex = 1
    End If
    
    If mudtDNS(0).lngNameFail <> 0 Then
       blnInError = True
       FormatControl cboDnsFail, csError
       cboDNS.ListIndex = 0
    End If
    If mudtDNS(0).lngIPFail <> 0 Then
       blnInError = True
       FormatControl cboIpFail, csError
       cboDNS.ListIndex = 0
    End If
 End If
 
 If Trim(txtHMRemark) = "" Then
    blnInError = True
    txtHMRemark.Tag = ""
    FormatControl txtHMRemark, csError
    txtHMRemark.Tag = "X"
 End If
  
 If blnInError Then
    Set ctl = FindControlInError(Me)
    If Not (ctl Is Nothing) Then
       ctl.SetFocus
    End If
 End If
  
 IsValidFailure = Not blnInError
End Function


Private Sub IsValidFailureReason(cbo As ComboBox, ByRef blnInError As Boolean)
 If cbo.ListIndex <> 0 Then
    blnInError = True
    cbo.Tag = ""
    FormatControl cbo, csError
    cbo.Tag = "X"
 End If
End Sub


Private Function IsValidTicket() As Boolean
 Dim blnInError As Boolean
 Dim ctl        As Control
 
 If Trim(txtDomainHolder) = "" Then
    blnInError = True
    FormatControl txtDomainHolder, csError
 End If
 If Trim(txtAdminContact1) = "" Then
    blnInError = True
    FormatControl txtAdminContact1, csError
 End If
 If Trim(txtTechContact) = "" Then
    blnInError = True
    FormatControl txtTechContact, csError
 End If
 If Trim(txtBillContact) = "" Then
    blnInError = True
    FormatControl txtBillContact, csError
 End If
 If cboCategory.ListIndex = -1 Then
    blnInError = True
    FormatControl cboCategory, csError
 End If
 If cboClass.ListIndex = -1 Then
    blnInError = True
    FormatControl cboClass, csError
 End If
 
'Celina Leong Phoenix1.1 09/04/03
 If txtClikPaid = "" Then
     If cboClikPaid.ListIndex = -1 Then
        blnInError = True
        FormatControl cboClikPaid, csError
     End If
 End If
'---------------------------------------

 If Not IsNicHandle(txtAdminContact1) Then
    blnInError = True
    FormatControl txtAdminContact1, csError
 End If
 If Trim(txtAdminContact2) <> "" Then
    If Not IsNicHandle(txtAdminContact2) Then
       blnInError = True
       FormatControl txtAdminContact2, csError
    End If
 End If
 If Not IsNicHandle(txtTechContact) Then
    blnInError = True
    FormatControl txtTechContact, csError
 End If
 If Not IsNicHandle(txtBillContact) Then
    blnInError = True
    FormatControl txtBillContact, csError
 End If
 
 If Trim(txtHMRemark) = "" Then
    blnInError = True
    txtHMRemark.Tag = ""
    FormatControl txtHMRemark, csError
    txtHMRemark.Tag = "X"
 End If

 If blnInError Then
    Set ctl = FindControlInError(Me)
    If Not (ctl Is Nothing) Then
       ctl.SetFocus
    End If
 End If
  
 IsValidTicket = Not blnInError
End Function


Private Sub RejectTicket(ByRef blnError As Boolean)

 
  'Added by David Gildea for Release 1.4
 Dim strSubject As String
 Dim strSQL     As String
 Dim rec        As ADODB.Recordset
 Dim t_Type     As String
 Dim Creator    As String
 Dim Admin1     As String
 Dim Bill       As String
 Dim Tech       As String
 Dim Admin2     As String
 Dim strTo      As String
 Dim strCC      As String
 Dim strBCC     As String
 Dim D_Name     As String
 Dim result     As String
 Dim strRemarks    As String
 
 
 On Error GoTo RejectTicketERROR
  
'''''''''''''''''''''''''''''''''''''''''''''''''''''
'Email Enhancements Specification 24. January Release
'Designed and developed by David Gildea
'Date 12/01/05

'Step 01
'Get the ticket type of the ticket: Registration, Modification, Deletion
'
 strSQL = "SELECT T_Type, Creator_NH FROM Ticket WHERE D_Name = '" & txtDomainName.Text & "'"
 DB.Execute strSQL, rec
 t_Type = rec.Fields("T_Type")
 Creator = rec.Fields("Creator_NH")


'Step 02
'Select the correct email row from email table in the database based on ticket type
'
 Select Case t_Type
    Case "R"
        strSQL = "SELECT * from Email WHERE E_Name = 'queryReg'"
        t_Type = "Registration"
    Case "M"
        strSQL = "SELECT * from Email WHERE E_Name = 'queryMod'"
        t_Type = "Modification"
    Case "D"
        strSQL = "SELECT * from Email WHERE E_Name = 'queryDel'"
        t_Type = "Deletion"
 End Select
 
 DB.Execute strSQL, rec
 

'Step 03
'Compile the remarks into the strBody String to be put into the email
'

strRemarks = strRemarks & txtHMRemark.Text & vbCrLf

 If cboDomainNameFail.ListIndex <> 0 Then
    strRemarks = strRemarks & "Domain Name : " & txtDomainName & "  [" & cboDomainNameFail.Text & "]" & vbCrLf
 End If
 If cboDomainHolderFail.ListIndex <> 0 Then
    strRemarks = strRemarks & "Domain Holder : " & txtDomainHolder & "  [" & cboDomainHolderFail.Text & "]" & vbCrLf
 End If
 If cboAccountNameFail.ListIndex <> 0 Then
    strRemarks = strRemarks & "Account Name : " & txtAccountName & "  [" & cboAccountNameFail.Text & "]" & vbCrLf
 End If
 If cboAdminContact1Fail.ListIndex <> 0 Then
    strRemarks = strRemarks & "Admin Contact : " & txtAdminContact1 & "  [" & cboAdminContact1Fail.Text & "]" & vbCrLf
 End If
 If cboAdminContact2Fail.ListIndex <> 0 Then
    strRemarks = strRemarks & "Admin Contact : " & txtAdminContact2 & "  [" & cboAdminContact2Fail.Text & "]" & vbCrLf
 End If
 If cboTechContactFail.ListIndex <> 0 Then
    strRemarks = strRemarks & "Tech Contact : " & txtTechContact & "  [" & cboTechContactFail.Text & "]" & vbCrLf
 End If
 If cboBillContactFail.ListIndex <> 0 Then
    strRemarks = strRemarks & "Billing Contact : " & txtBillContact & "  [" & cboBillContactFail.Text & "]" & vbCrLf
 End If
 
 If mudtDNS(0).lngNameFail <> 0 Then
    strRemarks = strRemarks & "DNS Name 1 : " & cboDNS.List(0) & "  [" & mudtDNS(0).strNameFail & "]" & vbCrLf
 End If
 If mudtDNS(0).lngIPFail <> 0 Then
    strRemarks = strRemarks & "DNS IP Addr. 1 : " & mudtDNS(0).strIP & "  [" & mudtDNS(0).strIPFail & "]" & vbCrLf
 End If
 
 If mudtDNS(1).lngNameFail <> 0 Then
    strRemarks = strRemarks & "DNS Name 2 : " & cboDNS.List(1) & "  [" & mudtDNS(1).strNameFail & "]" & vbCrLf
 End If
 If mudtDNS(1).lngIPFail <> 0 Then
    strRemarks = strRemarks & "DNS IP Addr. 2 : " & mudtDNS(1).strIP & "  [" & mudtDNS(1).strIPFail & "]" & vbCrLf
 End If
 
 If mudtDNS(2).lngNameFail <> 0 Then
    strRemarks = strRemarks & "DNS Name 3 : " & cboDNS.List(2) & "  [" & mudtDNS(2).strNameFail & "]" & vbCrLf
 End If
 If mudtDNS(2).lngIPFail <> 0 Then
    strRemarks = strRemarks & "DNS IP Addr. 3 : " & mudtDNS(2).strIP & "  [" & mudtDNS(2).strIPFail & "]" & vbCrLf
 End If
 
 'Insert failure reason for class and category  modify by celina 05/09/02
 
 If cboClassFail.ListIndex <> 0 Then
    strRemarks = strRemarks & "Class : " & txtClass & "  [" & cboClassFail.Text & "]" & vbCrLf
 End If

 If cboCategoryFail.ListIndex <> 0 Then
    strRemarks = strRemarks & "Category : " & txtCategory & "  [" & cboCategoryFail.Text & "]" & vbCrLf
 End If
 ' end modify 05/09/02
 'End of step 03

'Step 04
'Assign the correct variables from the recordset to the corresponding variables
'
Admin1 = GetEmail(txtAdminContact1.Text)
Admin2 = GetEmail(txtAdminContact2.Text)
Bill = GetEmail(txtBillContact.Text)
Creator = GetEmail(Creator)
Tech = GetEmail(txtTechContact.Text)
D_Name = txtDomainName.Text
Dim strBody As String

strTo = rec.Fields("E_To")
If (rec.Fields("E_CC") <> "") Then strCC = rec.Fields("E_CC")
If (rec.Fields("E_BCC") <> "") Then strBCC = rec.Fields("E_BCC")
strSubject = rec.Fields("E_Subject")
strBody = rec.Fields("E_Text")

'Step 04
'Replace the vaarables in the addresses, subjects etc with the correct information from the Ticket
' E.G $D_Name -> 'delphi.ie'
'     $admin  -> 'david.gildea@delphi.ie'
'
strTo = Replace(strTo, "$admin1", Admin1)
strTo = Replace(strTo, "$tech", Tech)
strTo = Replace(strTo, "$admin2", Admin2)
strTo = Replace(strTo, "$bill", Bill)
strTo = Replace(strTo, "$creator", Creator)

strCC = Replace(strCC, "$admin1", Admin1)
strCC = Replace(strCC, "$tech", Tech)
strCC = Replace(strCC, "$admin2", Admin2)
strCC = Replace(strCC, "$bill", Bill)
strCC = Replace(strCC, "$creator", Creator)

strBCC = Replace(strBCC, "$admin1", Admin1)
strBCC = Replace(strBCC, "$tech", Tech)
strBCC = Replace(strBCC, "$admin2", Admin2)
strBCC = Replace(strBCC, "$bill", Bill)
strBCC = Replace(strBCC, "$creator", Creator)

strSubject = Replace(strSubject, "$Ticket_Type", t_Type)
strSubject = Replace(strSubject, "$D_Name", D_Name)

strBody = Replace(strBody, "$D_Name", D_Name)
strBody = Replace(strBody, "$remark", strRemarks)

'Step 05
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

 '''''''''''''''''''''''''''''''''''''''''''''''
                        
 blnError = False
Exit Sub
RejectTicketERROR:
 MsgBox "Err No:   " & Err.Number & vbCrLf & _
        "Err Desc: " & Err.Description, vbCritical
        
 blnError = True
End Sub

' Celina Leong 17-09-2002

Private Sub AcceptTicket(ByRef blnError As Boolean)
 Dim strEmail  As String
 Dim strBody   As String
 
 'Added by David Gildea for Release 1.4
 Dim strSubject As String
 Dim strSQL     As String
 Dim rec        As ADODB.Recordset
 Dim t_Type     As String
 Dim Creator    As String
 Dim Admin1     As String
 Dim Bill       As String
 Dim Tech       As String
 Dim Admin2     As String
 Dim strTo      As String
 Dim strCC      As String
 Dim strBCC     As String
 Dim D_Name     As String
 
 
 On Error GoTo AcceptTicketERROR
 
 
'''''''''''''''''''''''''''''''''''''''''''''''''''''
'Email Enhancements Specification 24. January Release
'Designed and developed by David Gildea
'Date 12/01/05

'Step 01
'Get the ticket type of the ticket: Registration, Modification, Deletion
'
 strSQL = "SELECT T_Type, Creator_NH FROM Ticket WHERE D_Name = '" & txtDomainName.Text & "'"
 DB.Execute strSQL, rec
 t_Type = rec.Fields("T_Type")
 Creator = rec.Fields("Creator_NH")


'Step 02
'Select the correct email row from email table in the database based on ticket type
'
 Select Case t_Type
    Case "R"
        strSQL = "SELECT * from Email WHERE E_Name = 'acceptReg'"
        t_Type = "Registration"
    Case "M"
        strSQL = "SELECT * from Email WHERE E_Name = 'acceptMod'"
        t_Type = "Modification"
    Case "D"
        strSQL = "SELECT * from Email WHERE E_Name = 'acceptDel'"
        t_Type = "Deletion"
 End Select
 
 DB.Execute strSQL, rec
 
'Step 03
'Assign the correct variables from the recordset to the corresponding variables
'
Admin1 = GetEmail(txtAdminContact1.Text)
Admin2 = GetEmail(txtAdminContact2.Text)
Bill = GetEmail(txtBillContact.Text)
Creator = GetEmail(Creator)
Tech = GetEmail(txtTechContact.Text)
D_Name = txtDomainName.Text

strTo = rec.Fields("E_To")
If (rec.Fields("E_CC") <> "") Then strCC = rec.Fields("E_CC")
If (rec.Fields("E_BCC") <> "") Then strBCC = rec.Fields("E_BCC")
strSubject = rec.Fields("E_Subject")
strBody = rec.Fields("E_Text")

'Step 04
'Replace the vaarables in the addresses, subjects etc with the correct information from the Ticket
' E.G $D_Name -> 'delphi.ie'
'     $admin  -> 'david.gildea@delphi.ie'
'
strTo = Replace(strTo, "$admin1", Admin1)
strTo = Replace(strTo, "$tech", Tech)
strTo = Replace(strTo, "$admin2", Admin2)
strTo = Replace(strTo, "$bill", Bill)
strTo = Replace(strTo, "$creator", Creator)

strCC = Replace(strCC, "$admin1", Admin1)
strCC = Replace(strCC, "$tech", Tech)
strCC = Replace(strCC, "$admin2", Admin2)
strCC = Replace(strCC, "$bill", Bill)
strCC = Replace(strCC, "$creator", Creator)

strBCC = Replace(strBCC, "$admin1", Admin1)
strBCC = Replace(strBCC, "$tech", Tech)
strBCC = Replace(strBCC, "$admin2", Admin2)
strBCC = Replace(strBCC, "$bill", Bill)
strBCC = Replace(strBCC, "$creator", Creator)

strSubject = Replace(strSubject, "$Ticket_Type", t_Type)
strSubject = Replace(strSubject, "$D_Name", D_Name)

strBody = Replace(strBody, "$D_Name", D_Name)

'Step 05
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


                      
'----------------------------------------------------------------------------------------------------------
                       
 blnError = False
Exit Sub
AcceptTicketERROR:
 MsgBox "Err No:   " & Err.Number & vbCrLf & _
        "Err Desc: " & Err.Description, vbCritical
        
 blnError = True
End Sub

Private Sub EditNicHandle(txt As TextBox)
 If txt = "" Then Exit Sub
 
 If txt.ForeColor = vbBlue Then
    If mfrmEditNicHandle Is Nothing Then
       Set mfrmEditNicHandle = New FrmEditNicHandle
    End If
'Celina Leong 08-10-2002
'Included Billing Contact NicHandle for send email used.
'   mfrmEditNicHandle.LoadNicHandle txt, mstrCreatorNH
    mfrmEditNicHandle.LoadNicHandle txt, mstrCreatorNH, mstrBillNH
 Else
    FrmNicHandle.Show_Results_NHandle txt
 End If
End Sub


'Private Sub CheckInTicket(ByRef blnError As Boolean)
' Dim strSQL    As String
' Dim strStatus As String
' Dim lngStatus As Long
'
' strSQL = "SELECT description, status FROM TicketAdminStatus ORDER BY status ASC"
' FrmList.ShowList strSQL, strStatus, lngStatus, blnError, "Admin Status"
'
' If blnError Then Exit Sub
' Screen.MousePointer = vbHourglass
' UpdateAdminStatus lngStatus, strStatus, blnError
'
' If Not blnError Then
'    CheckIn
' End If
' ShowButtons True, True, True, True, True, True
' Screen.MousePointer = vbNormal
'End Sub


Private Sub FormatFailureReasons(ByVal cs As ControlState)
 FormatFailureControl cboDomainNameFail, cs
 FormatFailureControl cboDomainHolderFail, cs
 FormatFailureControl cboAccountNameFail, cs
 FormatFailureControl cboAdminContact1Fail, cs
 If txtAdminContact2 = "" Then
    FormatFailureControl cboAdminContact2Fail, csView
 Else
    FormatFailureControl cboAdminContact2Fail, cs
 End If
 FormatFailureControl cboTechContactFail, cs
 FormatFailureControl cboBillContactFail, cs
 FormatFailureControl cboDnsFail, cs
 FormatFailureControl cboIpFail, cs
 FormatFailureControl cboCategoryFail, cs
 FormatFailureControl cboClassFail, cs
End Sub


Private Sub FormatFailureControl(cbo As ComboBox, ByVal cs As ControlState)
 cbo.Tag = ""
 FormatControl cbo, cs
 cbo.Tag = "X"
End Sub


Private Sub EditDNS(ByVal blnEdit As Boolean)
 If HasAuthority(atLeadHostMaster) Then
    cboDNS.Visible = Not blnEdit
    cboDNSEdit.Visible = blnEdit
    If blnEdit Then
       cboDNSEdit = cboDNS
    End If
 End If
End Sub
'Added for BATCH 5
Function GetOtherDomains(sHolder As String) As Boolean
    
    Dim rsDom As ADODB.Recordset
    Dim sSQL, sApplicantsDomains As String
    Dim i As Integer

    sApplicantsDomains = ""
    
    sSQL = "SELECT D_Name FROM Domain "
    sSQL = sSQL & " WHERE D_Holder = """ & sHolder & """"
    
    DB.Execute sSQL, rsDom
     
    If rsDom.EOF Then
        GetOtherDomains = False
    Else
        
        rsDom.MoveFirst
        'HFlexTickets.TextMatrix(iRowIndex, mlngColToolTipText) = ""
        
        While Not rsDom.EOF
            sApplicantsDomains = " " & sApplicantsDomains & rsDom("D_Name")
            rsDom.MoveNext
            
            If Not rsDom.EOF Then
                sApplicantsDomains = sApplicantsDomains & "      "
            Else
                sApplicantsDomains = sApplicantsDomains & " "
            End If
            
        Wend
        
        txtDomainHolder.ToolTipText = sApplicantsDomains
        GetOtherDomains = True
    End If
        
    rsDom.Close
    
End Function
