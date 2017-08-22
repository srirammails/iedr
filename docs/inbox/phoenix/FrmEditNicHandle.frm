VERSION 5.00
Object = "{0ECD9B60-23AA-11D0-B351-00A0C9055D8E}#6.0#0"; "MSHFLXGD.OCX"
Begin VB.Form FrmEditNicHandle 
   Caption         =   "Edit Ticket Nic Handle Validation"
   ClientHeight    =   7725
   ClientLeft      =   60
   ClientTop       =   345
   ClientWidth     =   11835
   LinkTopic       =   "Form1"
   MDIChild        =   -1  'True
   ScaleHeight     =   7725
   ScaleWidth      =   11835
   Begin VB.Frame FraOption 
      Caption         =   "Options"
      ForeColor       =   &H00000000&
      Height          =   735
      Left            =   240
      TabIndex        =   24
      Top             =   6720
      Width           =   11415
      Begin VB.CommandButton CmdSave 
         Caption         =   "Sa&ve"
         Height          =   375
         Left            =   7200
         TabIndex        =   11
         Top             =   240
         Width           =   1455
      End
      Begin VB.CommandButton CmdCan 
         Caption         =   "Ca&ncel"
         Height          =   375
         Left            =   5640
         TabIndex        =   10
         Top             =   240
         Width           =   1455
      End
      Begin VB.CommandButton CmdEdit 
         Caption         =   "&Edit"
         Height          =   375
         Left            =   1080
         TabIndex        =   6
         Top             =   240
         Width           =   1455
      End
      Begin VB.CommandButton cmdReject 
         Caption         =   "Re&ject"
         Height          =   375
         Left            =   4200
         TabIndex        =   8
         Top             =   240
         Width           =   1335
      End
      Begin VB.CommandButton CmdClose 
         Caption         =   "&Close"
         Height          =   375
         Left            =   8760
         MaskColor       =   &H8000000F&
         TabIndex        =   12
         Top             =   240
         Width           =   1455
      End
      Begin VB.CommandButton cmdCancel 
         Cancel          =   -1  'True
         Caption         =   "Ca&ncel"
         Height          =   375
         Left            =   5640
         MaskColor       =   &H8000000F&
         TabIndex        =   9
         Top             =   240
         Width           =   1455
      End
      Begin VB.CommandButton cmdAccept 
         Caption         =   "Acce&pt"
         Height          =   375
         Left            =   2640
         MaskColor       =   &H8000000F&
         TabIndex        =   7
         Top             =   240
         Width           =   1455
      End
   End
   Begin VB.Frame FraHistory 
      Caption         =   "History "
      ForeColor       =   &H00000000&
      Height          =   1635
      Left            =   240
      TabIndex        =   22
      Top             =   4920
      Width           =   11505
      Begin MSHierarchicalFlexGridLib.MSHFlexGrid HFlexEditNHandle 
         Height          =   1095
         Left            =   600
         TabIndex        =   23
         Top             =   360
         Width           =   10455
         _ExtentX        =   18441
         _ExtentY        =   1931
         _Version        =   393216
         Cols            =   4
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
            Name            =   "MS Sans Serif"
            Size            =   8.25
            Charset         =   0
            Weight          =   700
            Underline       =   0   'False
            Italic          =   0   'False
            Strikethrough   =   0   'False
         EndProperty
         _NumberOfBands  =   1
         _Band(0).Cols   =   4
      End
   End
   Begin VB.Frame FraCurrent 
      Caption         =   "Current"
      ForeColor       =   &H00000000&
      Height          =   4695
      Left            =   240
      TabIndex        =   19
      Top             =   120
      Width           =   11535
      Begin VB.Frame Frame1 
         Caption         =   "Requester's Remark"
         Height          =   1095
         Left            =   7080
         TabIndex        =   33
         Top             =   600
         Width           =   4215
         Begin VB.TextBox TxtReqRemark 
            BackColor       =   &H8000000F&
            ForeColor       =   &H00000000&
            Height          =   525
            Left            =   240
            Locked          =   -1  'True
            MultiLine       =   -1  'True
            ScrollBars      =   2  'Vertical
            TabIndex        =   34
            TabStop         =   0   'False
            Tag             =   "X"
            Top             =   360
            Width           =   3735
         End
      End
      Begin VB.Frame FrameHRemark 
         Caption         =   "HostMaster's Remark"
         Height          =   2175
         Left            =   7080
         TabIndex        =   21
         Top             =   2280
         Width           =   4215
         Begin VB.TextBox txtHMRemark 
            BackColor       =   &H8000000E&
            Height          =   1605
            Left            =   240
            MultiLine       =   -1  'True
            ScrollBars      =   2  'Vertical
            TabIndex        =   5
            Top             =   360
            Width           =   3735
         End
      End
      Begin VB.Frame FrameCurrent1 
         Height          =   4215
         Left            =   240
         TabIndex        =   20
         Top             =   240
         Width           =   6735
         Begin VB.ComboBox cboCompany 
            Height          =   315
            ItemData        =   "FrmEditNicHandle.frx":0000
            Left            =   5160
            List            =   "FrmEditNicHandle.frx":0002
            Style           =   2  'Dropdown List
            TabIndex        =   44
            Top             =   960
            Width           =   1455
         End
         Begin VB.TextBox TxtCompany 
            BackColor       =   &H8000000F&
            BorderStyle     =   0  'None
            Height          =   315
            Left            =   1680
            TabIndex        =   43
            Top             =   980
            Width           =   3375
         End
         Begin VB.ComboBox CboCountry 
            Height          =   315
            Left            =   1680
            TabIndex        =   41
            Tag             =   "X"
            Text            =   "CboCountry"
            Top             =   2400
            Width           =   3375
         End
         Begin VB.ComboBox CboCounty 
            Height          =   315
            Left            =   1680
            TabIndex        =   40
            Tag             =   "X"
            Text            =   "CboCounty"
            Top             =   2040
            Width           =   3375
         End
         Begin VB.TextBox TxtCountry 
            BackColor       =   &H8000000F&
            BorderStyle     =   0  'None
            Height          =   315
            Left            =   1680
            TabIndex        =   39
            Text            =   "Text1"
            Top             =   2400
            Width           =   3375
         End
         Begin VB.TextBox txtStatus 
            BackColor       =   &H8000000F&
            BorderStyle     =   0  'None
            ForeColor       =   &H00000000&
            Height          =   315
            Left            =   1680
            Locked          =   -1  'True
            TabIndex        =   35
            TabStop         =   0   'False
            Tag             =   "X"
            Top             =   3840
            Width           =   3375
         End
         Begin VB.TextBox txtEmail 
            BackColor       =   &H8000000F&
            BorderStyle     =   0  'None
            ForeColor       =   &H00000000&
            Height          =   315
            Left            =   1680
            Locked          =   -1  'True
            TabIndex        =   18
            TabStop         =   0   'False
            Top             =   3480
            Width           =   3375
         End
         Begin VB.TextBox txtFax 
            BackColor       =   &H8000000F&
            BorderStyle     =   0  'None
            ForeColor       =   &H00000000&
            Height          =   315
            Left            =   1680
            Locked          =   -1  'True
            TabIndex        =   17
            TabStop         =   0   'False
            Top             =   3120
            Width           =   3375
         End
         Begin VB.TextBox txtPhone 
            BackColor       =   &H8000000F&
            BorderStyle     =   0  'None
            ForeColor       =   &H00000000&
            Height          =   315
            Left            =   1680
            Locked          =   -1  'True
            TabIndex        =   16
            TabStop         =   0   'False
            Top             =   2760
            Width           =   3375
         End
         Begin VB.TextBox txtName 
            BackColor       =   &H8000000F&
            BorderStyle     =   0  'None
            ForeColor       =   &H00000000&
            Height          =   315
            Left            =   1680
            Locked          =   -1  'True
            TabIndex        =   13
            Top             =   700
            Width           =   3375
         End
         Begin VB.TextBox txtNicHandle 
            BackColor       =   &H8000000F&
            BorderStyle     =   0  'None
            ForeColor       =   &H00800000&
            Height          =   315
            Left            =   1680
            Locked          =   -1  'True
            TabIndex        =   25
            TabStop         =   0   'False
            Top             =   360
            Width           =   1455
         End
         Begin VB.TextBox txtAddress 
            BackColor       =   &H8000000F&
            BorderStyle     =   0  'None
            ForeColor       =   &H00000000&
            Height          =   735
            Left            =   1680
            Locked          =   -1  'True
            MultiLine       =   -1  'True
            TabIndex        =   14
            TabStop         =   0   'False
            Top             =   1290
            Width           =   3375
         End
         Begin VB.TextBox txtCounty 
            BackColor       =   &H8000000F&
            BorderStyle     =   0  'None
            ForeColor       =   &H00000000&
            Height          =   315
            Left            =   1680
            Locked          =   -1  'True
            TabIndex        =   15
            TabStop         =   0   'False
            Top             =   2040
            Width           =   3375
         End
         Begin VB.ComboBox cboName 
            Height          =   315
            ItemData        =   "FrmEditNicHandle.frx":0004
            Left            =   5160
            List            =   "FrmEditNicHandle.frx":0006
            Style           =   2  'Dropdown List
            TabIndex        =   0
            Top             =   700
            Width           =   1455
         End
         Begin VB.ComboBox cboAddress 
            Height          =   315
            Left            =   5160
            Style           =   2  'Dropdown List
            TabIndex        =   1
            Top             =   1290
            Width           =   1455
         End
         Begin VB.ComboBox cboPhone 
            Height          =   315
            Left            =   5160
            Style           =   2  'Dropdown List
            TabIndex        =   2
            Top             =   2760
            Width           =   1455
         End
         Begin VB.ComboBox cboFax 
            Height          =   315
            Left            =   5160
            Style           =   2  'Dropdown List
            TabIndex        =   3
            Top             =   3120
            Width           =   1455
         End
         Begin VB.ComboBox cboEmail 
            Height          =   315
            Left            =   5160
            Style           =   2  'Dropdown List
            TabIndex        =   4
            Top             =   3480
            Width           =   1455
         End
         Begin VB.Label Label4 
            Caption         =   "Comapny Name"
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
            TabIndex        =   42
            Top             =   980
            Width           =   1335
         End
         Begin VB.Label Label3 
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
            Left            =   240
            TabIndex        =   38
            Top             =   2400
            Width           =   735
         End
         Begin VB.Label Label2 
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
            Height          =   195
            Left            =   240
            TabIndex        =   37
            Top             =   2040
            Width           =   855
         End
         Begin VB.Label Label1 
            Caption         =   "Failure Reason:"
            Height          =   255
            Left            =   5160
            TabIndex        =   36
            Top             =   360
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
            Left            =   240
            TabIndex        =   32
            Top             =   3480
            Width           =   855
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
            Left            =   240
            TabIndex        =   31
            Top             =   3120
            Width           =   855
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
            Left            =   240
            TabIndex        =   30
            Top             =   2760
            Width           =   855
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
            Left            =   240
            TabIndex        =   29
            Top             =   3840
            Width           =   855
         End
         Begin VB.Label LblPerName 
            Caption         =   "Name"
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
            TabIndex        =   28
            Top             =   700
            Width           =   855
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
            Left            =   240
            TabIndex        =   27
            Top             =   360
            Width           =   855
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
            TabIndex        =   26
            Top             =   1290
            Width           =   855
         End
      End
   End
End
Attribute VB_Name = "FrmEditNicHandle"
Attribute VB_GlobalNameSpace = False
Attribute VB_Creatable = False
Attribute VB_PredeclaredId = True
Attribute VB_Exposed = False
Option Explicit

'*********************************************
'*                                           *
'* Written By : Brian Devlin                 *
'* Function   : Validates a new/renewed      *
'*              NIC Handle                   *
'*********************************************
'Celina Leong Phoenix1.1 08/04/03
Const mstrIreland1   As String = "Ireland"
Const mstrIreland2   As String = "Eire"
Const mstrIreland3   As String = "Northern Ireland"
Const mstrNA         As String = "N/A"
Const mstrUK         As String = "United Kingdom"
Const mstrUSA        As String = "USA"

'---------------------------------------------------
Const mlngColChngDt          As Long = 4
Const mstrRemarkChangeStatus As String = "Changed Status to Active"
Dim mblnHisRecordLoaded As Boolean   'Phoeni1.1 * Indicates if there is history record
Dim mblnRecordLoaded As Boolean      '* Indicates if there is a valid record displayed
Dim mstrNicHandle    As String       '* Key of record loaded
Dim mstrTimeStamp    As String       '* Holds the timestamd for the loaded record
Dim mstrCreatorNH    As String
Dim mstrBillNH       As String
Dim mblnHistory      As Boolean      '* Indicates if a History record is being displayed
Dim mblnEditHistory  As Boolean      'Celina Leong Phoenix1.1 * Indicates if a edit history record is being displayed
Dim CountryCode      As Integer      'Celina Leong Phoenix1.1
Dim TempFax          As String       'Celina Leong Phoenix1.1
Dim TempPhone        As String       'Celina Leong Phoenix1.1
Dim TempNow          As String       'Celina Leong Phoenix1.1
Dim TempTime         As String       'Celina Leong Phoenix1.1
Dim mblnNoHistory    As Boolean      'Celina Leong Phoenix1.1
Public Event Accepted(ByVal strNicHandle As String)
Public Event NicHandleChanged(ByVal strNewNicHandle As String, ByVal strOldNicHandle As String)




'Celina Leong Phoenix1.1 08/04/03
Private Sub cboCountry_Click()

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











Private Sub cmdAccept_Click()
 Dim blnError As Boolean
 
 On Error GoTo AcceptERROR
 Screen.MousePointer = vbHourglass
 If mblnRecordLoaded Then
    If IsValid(True) Then
       DB.BeginTransaction
       UpdateNicHandle blnError
       AcceptTicket blnError
       If Not blnError Then
          DB.CommitTransaction
          RaiseEvent Accepted(mstrNicHandle)
          Unload Me
       Else
          DB.RollbackTransaction
       End If
    
    End If
 End If
 Screen.MousePointer = vbNormal
Exit Sub
AcceptERROR:
 If DB.TransactionLevel > 0 Then
    DB.RollbackTransaction
 End If
End Sub


Private Sub CmdCancel_Click()
If mblnHistory Then

    LoadNicHandle mstrNicHandle, mstrCreatorNH, mstrBillNH 'celina Leong 08-10-2002
'Celina Leong 30/05/03
    
    txtNicHandle = mstrNicHandle
    FraCurrent.Caption = "Current"
    FormatControls Me, csEdit
    mblnHistory = False
    txtHMRemark = ""
    FormatControl txtNicHandle, csview
    FormatControl txtName, csview
    FormatControl TxtAddress, csview
    FormatControl TxtCompany, csview
    FormatControl txtCounty, csview
    FormatControl txtCountry, csview
    FormatControl txtPhone, csview
    FormatControl txtFax, csview
    FormatControl TxtEmail, csview
    cmdCancel.Visible = True
    CmdCan.Visible = False
    txtCountry.Visible = True
    txtCounty.Visible = True
    cboCountry.Visible = False
    cboCounty.Visible = False
    cboCounty.Clear
 ShowSubButtons True, False, False
 ShowButtons True, True, True
'-------------------------------------

 Else
     Unload Me
 
 End If
 
 
End Sub

Private Sub cmdReject_Click()
 Dim blnError As Boolean
 
 Screen.MousePointer = vbHourglass
 If mblnRecordLoaded Then
    If IsValid(False) Then
       RejectTicket blnError
       UpdateNicHandle blnError
       If Not blnError Then
          Unload Me
       End If
    End If
 End If
 Screen.MousePointer = vbNormal
End Sub


Private Sub CmdEdit_Click()
' FormatControl txtNicHandle, csEdit
'Celina Leong Phoenix1.1 04/04/2003
'Allow to amend all NicHandle details except NicHandle and Status.
 FormatControls Me, csview
 FormatControl txtName, csEdit
 FormatControl TxtAddress, csEdit
 FormatControl TxtCompany, csEdit
 FormatControl cboCounty, csEdit
 FormatControl cboCountry, csEdit
 FormatControl txtPhone, csEdit
 FormatControl txtFax, csEdit
 FormatControl TxtEmail, csEdit
 FormatControl txtHMRemark, csEdit
 txtCountry.Visible = False
 txtCounty.Visible = False
 cboCountry.Visible = True
 cboCounty.Visible = True
 CmdCan.Visible = True
 cmdCancel.Visible = False
 cboCounty.Text = txtCounty.Text
 FillCountryCombo cboCountry
 cboCountry.Text = txtCountry.Text
 If txtFax <> "" Then
    TempFax = txtFax
 End If
 If txtPhone > "" Then
    TempPhone = txtPhone
 End If
 
'-------------------------------------------------------------------------
 ShowSubButtons False, True, True
 ShowButtons False, False, False
' 'txtNicHandle.SetFocus
'Celina Leong Phoenix1.1 10/04/03
    txtName.SetFocus
HFlexEditNHandle.Enabled = False
'-------------------------------
End Sub


Private Sub CmdCan_Click()
'Celina Leong Phoenix1.1 07/4/03
    LoadNicHandle mstrNicHandle, mstrCreatorNH, mstrBillNH 'celina Leong 08-10-2002

    txtNicHandle = mstrNicHandle
    FormatControls Me, csEdit
    '30/05/03
    FraCurrent.Caption = "Current"
     txtHMRemark = ""
    '----------------------------
     
    FormatControl txtNicHandle, csview
    FormatControl txtName, csview
    FormatControl TxtAddress, csview
    FormatControl TxtCompany, csview
    FormatControl txtCounty, csview
    FormatControl txtCountry, csview
    FormatControl txtPhone, csview
    FormatControl txtFax, csview
    FormatControl TxtEmail, csview
    cmdCancel.Visible = True
    CmdCan.Visible = False
    txtCountry.Visible = True
    txtCounty.Visible = True
    cboCountry.Visible = False
    cboCounty.Visible = False
    cboCounty.Clear
    HFlexEditNHandle.Enabled = True
'-------------------------------------
 ShowSubButtons True, False, False
 ShowButtons True, True, True
End Sub


Private Sub CmdSave_Click()
'Celina Leong Phoenix1.1 07/04/2003
 Dim blnError As Boolean

 'SaveNicHandle blnError
 'Celina Leong Phoenix1.1 20/05/03
 'If IsValid(False) Then
 If IsValidEdit Then
    
    UpdateEditNicHandle blnError
    Update_Phone blnError
    Update_Fax blnError
  Else
    blnError = True
  End If
 
 If Not blnError Then
    ' FormatControl txtNicHandle, csView
        LoadNicHandle mstrNicHandle, mstrCreatorNH, mstrBillNH '20/05/03
        '30/05/03
        FraCurrent.Caption = "Current"
         txtHMRemark = ""
        '----------------------------
        FormatControl txtName, csview
        FormatControl TxtCompany, csview
        FormatControl TxtAddress, csview
        FormatControl txtCounty, csview
        FormatControl txtCountry, csview '23/05/03
        FormatControl txtPhone, csview
        FormatControl txtFax, csview
        FormatControl TxtEmail, csview
        txtCountry.Visible = True
        txtCounty.Visible = True
        cboCountry.Visible = False
        cboCounty.Visible = False
        ShowSubButtons True, False, False
        ShowButtons True, True, True
        cmdCancel.Visible = True
        CmdCan.Visible = False
        HFlexEditNHandle.Enabled = True
    
 End If
 
'----------------------------------
End Sub


Private Sub CmdClose_Click()
 Dim blnError As Boolean
 
 If IsValid(False) Then
    UpdateNicHandle blnError
    If Not blnError Then
       Unload Me
    End If
 End If
End Sub


Private Sub Form_Load()
 PopEditNicHandle_Grid

' FillNHFailureReasonCombo cboName, 50, True
' FillNHFailureReasonCombo cboAddress, 51, True
' FillNHFailureReasonCombo cboEmail, 52, True
' FillNHFailureReasonCombo cboPhone, 53, True
' FillNHFailureReasonCombo cboFax, 54, True
'Celina Leong Phoenix1.1 Add company failure
 FillNHFailureReasonCombo cboName, 50, True
 FillNHFailureReasonCombo cboCompany, 51, True
 FillNHFailureReasonCombo cboAddress, 52, True
 FillNHFailureReasonCombo cboEmail, 53, True
 FillNHFailureReasonCombo cboPhone, 54, True
 FillNHFailureReasonCombo cboFax, 54, True
 cmdCancel.Visible = True
 CmdCan.Visible = False
 ShowSubButtons True, False, False
 ShowButtons True, True, True
End Sub


Private Sub PopEditNicHandle_Grid()
 With HFlexEditNHandle
    .ColWidth(0) = 100
    .ColWidth(1) = 1250
    .ColWidth(2) = 2000
    .ColWidth(3) = 6780
    .ColWidth(4) = 0
 
    .TextMatrix(0, 1) = "Date"
    .TextMatrix(0, 2) = "Nic Handle"
    .TextMatrix(0, 3) = "Remark"
 End With
End Sub

'Celina Leong 08-10-2002
'Included Billing Contact NicHandle for send email used.

'Public Sub LoadNicHandle(ByVal strNicHandle As String, ByVal strCreatorNH As String, Optional ByVal strHistDate As String = "")
Public Sub LoadNicHandle(ByVal strNicHandle As String, ByVal strCreatorNH As String, ByVal strBillNH As String, Optional ByVal strHistDate As String = "")

 Dim strSQL As String
 Dim rec    As ADODB.Recordset
 
 On Error GoTo LoadNicHandleERROR
 Screen.MousePointer = vbHourglass

 strSQL = "SELECT * FROM NicHandle WHERE Nic_Handle=" & CDBText(strNicHandle)
 
 DB.Execute strSQL, rec
 
 If Not rec.EOF Then
    txtNicHandle = rec.Fields("Nic_Handle")
    txtName = rec.Fields("NH_Name")
    TxtAddress = Replace(rec.Fields("NH_Address"), "%", vbCrLf)
    txtCounty = NoNull(rec.Fields("NH_County"))
    'Celina Leong Phoenix1.1 08/04/03
    txtCountry = NoNull(rec.Fields("NH_Country"))
    TxtCompany = NoNull(rec.Fields("Co_Name"))
    txtPhone = ""
    txtFax = ""
    TxtEmail = rec.Fields("NH_Email")
    txtStatus = rec.Fields("NH_Status")
    txtHMRemark = NoNull(rec.Fields("NH_Remark"))
    
    mstrTimeStamp = NoNull(rec.Fields("NH_TStamp"))
    mstrNicHandle = strNicHandle
    mstrCreatorNH = strCreatorNH
    mstrBillNH = strBillNH ' Celina leong 08-10-2002
    mblnRecordLoaded = True
    If txtStatus = "Renew" Then
       Me.HelpContextID = 3020
    Else
       Me.HelpContextID = 5030
    End If
 End If
 
 strSQL = "SELECT Phone FROM Telecom "
 strSQL = strSQL & "WHERE Nic_Handle=" & CDBText(strNicHandle) & " "
 strSQL = strSQL & "AND Type=" & CDBText("P") & " "
 strSQL = strSQL & "LIMIT 1 "
 DB.Execute strSQL, rec
 If Not rec.EOF Then
    txtPhone = rec.Fields("Phone")
 End If
 
 strSQL = "SELECT Phone FROM Telecom "
 strSQL = strSQL & "WHERE Nic_Handle=" & CDBText(strNicHandle) & " "
 strSQL = strSQL & "AND Type=" & CDBText("F") & " "
 strSQL = strSQL & "LIMIT 1 "
 DB.Execute strSQL, rec
 If Not rec.EOF Then
    txtFax = rec.Fields("Phone")
 End If
 
 If mblnRecordLoaded Then
    strSQL = "SELECT * "
    strSQL = strSQL & "FROM NicHandleFailures "
    strSQL = strSQL & "WHERE Nic_Handle=" & CDBText(strNicHandle) & " "
    If strHistDate <> "" Then
       strSQL = strSQL & "AND Chng_Dt = " & CDBTime(strHistDate) & " "
    End If
    strSQL = strSQL & "ORDER BY Chng_Dt DESC "
    strSQL = strSQL & "LIMIT 1 "
    
    DB.Execute strSQL, rec
   If Not rec.EOF Then
       cboName.ListIndex = FindItemData(cboName, NoNull(rec.Fields("NH_Name_Fail_Cd")))
       cboAddress.ListIndex = FindItemData(cboAddress, NoNull(rec.Fields("NH_Address_Fail_Cd")))
       cboEmail.ListIndex = FindItemData(cboEmail, NoNull(rec.Fields("NH_Email_Fail_Cd")))
       cboPhone.ListIndex = FindItemData(cboPhone, NoNull(rec.Fields("NH_Phone_Fail_Cd")))
       cboFax.ListIndex = FindItemData(cboFax, NoNull(rec.Fields("NH_Fax_Fail_Cd")))
       'Celina Leong Phoenix1.1 17/04/03 Add Company fail code
       cboCompany.ListIndex = FindItemData(cboCompany, NoNull(rec.Fields("NH_Company_Fail_Cd")))
       '-------------------------------------------------
       If strHistDate = "" Then
          txtHMRemark = ""
          LoadNicHandleHistory strNicHandle
           '  If Not mblnEditHistory Then
           '     LoadEditNicHandle mstrNicHandle, mstrCreatorNH, mstrBillNH, strHistDate
        
           '  End If
           
          FraCurrent.Caption = "Current"
          FormatControls Me, csEdit
             mblnHistory = False
             FormatControl txtNicHandle, csview
             FormatControl txtName, csview
             FormatControl TxtCompany, csview
             FormatControl TxtAddress, csview
             FormatControl txtCounty, csview
             FormatControl txtCountry, csview
             FormatControl txtPhone, csview
             FormatControl txtFax, csview
             FormatControl TxtEmail, csview
             FormatControl cboCounty, csview
             FormatControl cboCountry, csview
             cmdCancel.Visible = True
             CmdCan.Visible = False
             txtCountry.Visible = True
             txtCounty.Visible = True
             cboCountry.Visible = False
             cboCounty.Visible = False
             mblnHisRecordLoaded = True
             ShowSubButtons True, False, False
'          '--------------------------------
          
       Else
          txtHMRemark = NoNull(rec.Fields("Remark"))
          FraCurrent.Caption = "Historical"
          FormatControls Me, csview
          mblnHistory = True
     
''          'Celina Leong Phoenix1.1 08/04/03
              txtHMRemark.BorderStyle = 1
              ShowSubButtons False, False, False
              
'             LoadEditNicHandleHistory strNicHandle
''          '--------------------------------

       End If
    Else
       cboName.ListIndex = FindItemData(cboName, 0)
       cboAddress.ListIndex = FindItemData(cboAddress, 0)
       cboEmail.ListIndex = FindItemData(cboEmail, 0)
       cboPhone.ListIndex = FindItemData(cboPhone, 0)
       cboFax.ListIndex = FindItemData(cboFax, 0)
       'Celina Leong Phoenix1.1 17/04/03 Add Company fail code
       cboCompany.ListIndex = FindItemData(cboCompany, 0)
        
   'Celina Leong Phoenix1.1
        LoadEditNicHandleHistory strNicHandle
        
        If strHistDate <> "" Then
            LoadEditNicHandle mstrNicHandle, mstrCreatorNH, mstrBillNH, strHistDate
            FraCurrent.Caption = "Historical"
            FormatControls Me, csview
            mblnHistory = True

              txtHMRemark.BorderStyle = 1
              ShowSubButtons False, False, False
        End If
       
   '-------------------------------------------------
    End If
    '30/05/03
    If cmdEdit.Enabled = True Then
        txtHMRemark = " "
    End If
    
    ShowButtons True, True, True
'    ShowSubButtons True, False, False 'Celina Leong Phoenix1.1 08/04/03
'    Celina Leong Phoenix1.1 08/04/03
             cmdCancel.Visible = True
             CmdCan.Visible = False
             txtCountry.Visible = True
             txtCounty.Visible = True
             cboCountry.Visible = False
             cboCounty.Visible = False
'    --------------------------------
 
'    ShowButtons False, False, True
'''    ShowSubButtons True, False, False 'Celina Leong Phoenix1.1 08/04/03
 End If
 Screen.MousePointer = vbNormal
Exit Sub
LoadNicHandleERROR:
 mblnRecordLoaded = False
 ShowButtons False, False, True
 ShowSubButtons False, False, False 'Celina Leong Phoenix1.1 08/04/03
 Screen.MousePointer = vbNormal
 DB.ShowError "Unable to load Nic handle"
End Sub


Private Sub LoadNicHandleHistory(ByVal strNicHandle As String)
 Dim strSQL As String
 Dim rec    As ADODB.Recordset
 
 On Error GoTo LoadNicHandleHistoryERROR
   
 strSQL = "SELECT " & FormatDBDate("Chng_Dt") & " AS Date, "
 strSQL = strSQL & "Chng_NH AS 'Nic Handle', "
 strSQL = strSQL & "Remark, "
 strSQL = strSQL & "Chng_Dt "
 strSQL = strSQL & "FROM NicHandleFailures "
 strSQL = strSQL & "WHERE Nic_Handle=" & CDBText(strNicHandle) & " "
 strSQL = strSQL & "ORDER BY Chng_Dt DESC"
 
 DB.Execute strSQL, rec
 
' If Not rec.EOF Then

     Set HFlexEditNHandle.DataSource = rec.DataSource
'     mblnEditHistory = True
' Else
'     mblnEditHistory = False
' End If
 

Exit Sub
LoadNicHandleHistoryERROR:
 DB.ShowError "Unable to load Nic Handle history"
End Sub


'Private Sub SaveNicHandle(ByRef blnError As Boolean)
' Dim strSQL         As String
' Dim lngRowsUpdated As Long
'
' '### code checking if allowed to chanmge nic handle ####
'
' strSQL = "UPDATE NicHandle "
' strSQL = strSQL & "SET Nic_Handle=" & CDBText(txtNicHandle) & " "
' strSQL = strSQL & "WHERE Nic_Handle=" & CDBText(mstrNicHandle)
'
' DB.Execute strSQL, , , , lngRowsUpdated
'
' If lngRowsUpdated = 1 Then
'    RaiseEvent NicHandleChanged(txtNicHandle, mstrNicHandle)
'    mstrNicHandle = txtNicHandle
'    blnError = False
' Else
'    blnError = True
' End If
'End Sub


Private Sub UpdateNicHandle(ByRef blnError As Boolean)
 Dim strSQL         As String
 Dim lngRowsUpdated As Long
 
 On Error GoTo UpdateNichandleError
 If blnError Then Exit Sub

 strSQL = "INSERT INTO NicHandleFailures ("
 strSQL = strSQL & "Chng_NH, Chng_Dt, Nic_Handle, "
 strSQL = strSQL & "NH_Name_Fail_Cd, NH_Address_Fail_Cd, NH_Email_Fail_Cd,NH_Phone_Fail_Cd,NH_Fax_Fail_Cd, "
 strSQL = strSQL & "NH_Company_Fail_Cd, Remark) "
 strSQL = strSQL & "VALUES ("
 strSQL = strSQL & CDBText(UserID) & ", "
 strSQL = strSQL & "NOW(), "
 strSQL = strSQL & CDBText(mstrNicHandle) & ", "
 strSQL = strSQL & GetFailCode(cboName.ItemData(cboName.ListIndex)) & ", "
 strSQL = strSQL & GetFailCode(cboAddress.ItemData(cboAddress.ListIndex)) & ", "
 strSQL = strSQL & GetFailCode(cboEmail.ItemData(cboEmail.ListIndex)) & ", "
 strSQL = strSQL & GetFailCode(cboPhone.ItemData(cboPhone.ListIndex)) & ", "
 strSQL = strSQL & GetFailCode(cboFax.ItemData(cboFax.ListIndex)) & ", "
 'Celina Leong Phoenix1.1 17/04/03 Add Company fail code
 strSQL = strSQL & GetFailCode(cboCompany.ItemData(cboCompany.ListIndex)) & ", "
 '---------------------------------------------------------
 strSQL = strSQL & CDBText(txtHMRemark) & ")"
  DB.Execute strSQL, , , , lngRowsUpdated
 
 If lngRowsUpdated <> 1 Then
    MsgBox "Could not Update Nic Handle.", vbExclamation
    blnError = True
 Else
    blnError = False
 End If
Exit Sub
UpdateNichandleError:
 DB.ShowError "Unable to save Nic Handle"
 blnError = True
End Sub


Private Function GetFailCode(ByVal lngFailCode) As String
 If lngFailCode = 0 Then
    GetFailCode = "NULL"
 Else
    GetFailCode = CStr(lngFailCode)
 End If
End Function


Private Function IsValid(ByVal blnCheckFails As Boolean) As Boolean
 Dim blnInError As Boolean
 Dim ctl        As Control
 
 If blnCheckFails Then
    For Each ctl In Me.Controls
        If TypeOf ctl Is ComboBox Then
           If ctl.Tag <> "X" Then
              If ctl.ListIndex <> 0 Then
                 blnInError = True
                 FormatControl ctl, csError
              End If
           End If
        End If
    Next ctl
 End If
 
 If Trim(txtHMRemark) = "" Then
    blnInError = True
    FormatControl txtHMRemark, csError
 End If
 
  
 If blnInError Then
    Set ctl = FindControlInError(Me)
    If Not (ctl Is Nothing) Then
       ctl.SetFocus
    End If
 End If
  
 IsValid = Not blnInError
End Function


Private Sub ShowButtons(ByVal blnAccept As Boolean, _
                        ByVal blnReject As Boolean, _
                        ByVal blnCancel As Boolean)
 Dim blnAllowed As Boolean
 
 blnAllowed = HasAuthority(atHostMaster + atLeadHostMaster + atAccounts)
 
 cmdAccept.Enabled = blnAccept And mblnRecordLoaded And blnAllowed 'And (Not mblnHistory)
 cmdReject.Enabled = blnReject And mblnRecordLoaded And blnAllowed 'And (Not mblnHistory)
 cmdCancel.Enabled = blnCancel
End Sub


Private Sub ShowSubButtons(ByVal blnEdit As Boolean, _
                           ByVal blnCancel As Boolean, _
                           ByVal blnSave As Boolean)
 
 Dim blnAllowed As Boolean
 
 blnAllowed = HasAuthority(atHostMaster + atLeadHostMaster)
 
' cmdEdit.Enabled = blnEdit And mblnRecordLoaded And blnAllowed
'Celina Leong Phoenix1.1 07/04/03
 cmdEdit.Enabled = blnEdit And blnAllowed
 CmdCan.Enabled = blnCancel
 cmdSave.Enabled = blnSave And mblnRecordLoaded And blnAllowed
 
 CmdCan.Cancel = blnCancel And mblnRecordLoaded
 cmdSave.Default = blnSave And mblnRecordLoaded
End Sub


Private Sub AcceptTicket(ByRef blnError As Boolean)
 Dim strEmail  As String
 Dim strHeader As String
 Dim strBody   As String
 Dim strFooter As String
 
 Dim strSQL         As String
 Dim lngRowsUpdated As Long
 
 On Error GoTo AcceptTicketERROR
 If blnError Then Exit Sub
 
 strSQL = "UPDATE NicHandle SET "
 strSQL = strSQL & "NH_Status=" & CDBText("Active") & ", "
 strSQL = strSQL & "NH_Status_Dt=NOW() "
 strSQL = strSQL & "WHERE Nic_Handle=" & CDBText(mstrNicHandle) & " "
 strSQL = strSQL & "AND NH_TStamp=" & CDBTimeStamp(mstrTimeStamp)
 
 DB.Execute strSQL, , , , lngRowsUpdated
 
 'Celina Leong 17-09-02
 'Send email to billing contact when ticket is accepted.
 
 
'Celina Leong 4-10-2002
'Check before the email is sent.
'If Ticket Creator NicHandle = 'EMAIL-IEDR'
'Then send to Ticket Billing NicHandle
'Else send to Ticket Creator NicHandle

' strEmail = GetEmail(mstrCreatorNH)

 If mstrCreatorNH = "EMAIL-IEDR" Then
      strEmail = GetEmail(mstrBillNH)
 Else
      strEmail = GetEmail(mstrCreatorNH)
 End If
 
'''''''''''''''''''''''''''''''''''

 strHeader = "PLEASE DO NOT REPLY TO THIS EMAIL.  THIS IS AN AUTOMATED EMAIL." & vbCrLf & vbCrLf
 strHeader = strHeader & "Your request has been successfully accepted. " & vbCrLf & vbCrLf
' strHeader = strHeader & "http://" & DB.StaticData(ustWeb) & vbCrLf & vbCrLf & vbCrLf

 strFooter = vbCrLf & vbCrLf & "Kind regards" & vbCrLf & vbCrLf & "IE Domain Registry Hostmaster"
 
 strBody = "Remarks : " & txtHMRemark & vbCrLf & vbCrLf
 
 FrmSendMail.SendMail blnError, _
                      DB.StaticData(ustSmtp), _
                      DB.StaticData(ustSmtpPort), _
                      strEmail, _
                      DB.StaticData(ustReplyAddr), _
                      "Query Accepted : " & txtNicHandle, _
                      strHeader & strBody & strFooter
'-------------------------------------------------------------------------------
 
 If lngRowsUpdated <> 1 Then
    MsgBox "Could not Update Nic Handle." & vbCrLf & vbCrLf & "Possible reasons : Account updated by another user while you were editing it", vbExclamation
    blnError = True
 Else
    WriteNicHandleHistory mstrNicHandle
    blnError = False
 End If
Exit Sub
AcceptTicketERROR:
 DB.ShowError "Unable to save Nic Handle"
 blnError = True
End Sub


Private Sub RejectTicket(ByRef blnError As Boolean)
 Dim strEmail  As String
 Dim strHeader As String
 Dim strBody   As String
 Dim strFooter As String
 
 On Error GoTo RejectTicketERROR
 
 
'Celina Leong 4-10-2002
'Check before the email is sent.
'If Ticket Creator NicHandle = 'EMAIL-IEDR'
'Then send to Ticket Billing NicHandle
'Else send to Ticket Creator NicHandle

' strEmail = GetEmail(mstrCreatorNH)

 If mstrCreatorNH = "EMAIL-IEDR" Then
      strEmail = GetEmail(mstrBillNH)
 Else
      strEmail = GetEmail(mstrCreatorNH)
 End If
 
'''''''''''''''''''''''''''''''''''

 strHeader = "PLEASE DO NOT REPLY TO THIS EMAIL.  THIS IS AN AUTOMATED EMAIL  REQUESTS MUST BE AMENDED ONLINE." & vbCrLf & vbCrLf
 strHeader = strHeader & "Please amend your request, as instructed by this IEDR Hostmaster, at the following URL:" & vbCrLf & vbCrLf
 strHeader = strHeader & "http://" & DB.StaticData(ustWeb) & vbCrLf & vbCrLf & vbCrLf
 
 strFooter = vbCrLf & vbCrLf & "Kind regards" & vbCrLf & vbCrLf & "IE Domain Registry Hostmaster"
 
 strBody = "Remarks : " & txtHMRemark & vbCrLf & vbCrLf
 If cboName.ListIndex <> 0 Then
    strBody = strBody & "Name : " & txtName & "  [" & cboName.Text & "]" & vbCrLf
 End If
 If cboAddress.ListIndex <> 0 Then
    strBody = strBody & "Address : " & "[" & cboAddress.Text & "]" & vbCrLf & TxtAddress & vbCrLf & txtCounty & vbCrLf & vbCrLf
 End If
 If cboPhone.ListIndex <> 0 Then
    strBody = strBody & "Phone No : " & txtPhone & "  [" & cboPhone.Text & "]" & vbCrLf
 End If
 If cboFax.ListIndex <> 0 Then
    strBody = strBody & "Fax No : " & txtFax & "  [" & cboFax.Text & "]" & vbCrLf
 End If
 If cboEmail.ListIndex <> 0 Then
    strBody = strBody & "Email : " & TxtEmail & "  [" & cboEmail.Text & "]" & vbCrLf
 End If
 
 FrmSendMail.SendMail blnError, _
                      DB.StaticData(ustSmtp), _
                      DB.StaticData(ustSmtpPort), _
                      strEmail, _
                      DB.StaticData(ustReplyAddr), _
                      "Query : " & txtNicHandle, _
                      strHeader & strBody & strFooter
 

Exit Sub
RejectTicketERROR:
 blnError = True
End Sub


Private Sub Form_Resize()
 CenterControls Me
End Sub

Private Sub HFlexEditNHandle_DblClick()
 Dim lngRow As Long
 
    If Not mblnRecordLoaded Then Exit Sub
      
            lngRow = HFlexEditNHandle.Row
 
''    'If lngRow = 1 Then   'Modify by celina 25/07/02
''       LoadNicHandleHistory mstrNicHandle ', mstrCreatorNH
''    'Else
''
''    '  LoadNicHandle mstrNicHandle, mstrCreatorNH, HFlexEditNHandle.TextMatrix(lngRow, mlngColChngDt)
''    'End If
'' Celina Leong Phoenix1.1 10/04/03
        If HFlexEditNHandle.Text <> "" Then
              LoadNicHandle mstrNicHandle, mstrCreatorNH, mstrBillNH, HFlexEditNHandle.TextMatrix(lngRow, mlngColChngDt)
              ShowButtons False, False, True
        End If
 
'---------------------------------------
End Sub

Private Sub HFlexEditNHandle_KeyDown(KeyCode As Integer, Shift As Integer)
 If KeyCode = vbKeyReturn Then
    If mblnRecordLoaded Then
       HFlexEditNHandle_DblClick
    End If
 End If
End Sub


Private Sub HFlexEditNHandle_RowColChange()
 HFlexEditNHandle.RowSel = HFlexEditNHandle.Row
End Sub


Private Sub WriteNicHandleHistory(ByVal strNicHandle As String)
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
 strSQL = strSQL & CDBText(mstrRemarkChangeStatus) & " "
 strSQL = strSQL & "FROM NicHandle "
 strSQL = strSQL & "WHERE Nic_Handle=" & CDBText(strNicHandle)
 DB.Execute strSQL
End Sub
'Celina Leong Phoenix1.1 08/04/03
Private Function IsValidEdit() As Boolean
 Dim blnInError As Boolean
 Dim ctl        As Control
 
  
 If txtName.Text = "" Then
    blnInError = True
    FormatControl txtName, csError
 End If
 
 If TxtAddress.Text = "" Then
    blnInError = True
    FormatControl TxtAddress, csError
 End If
 
 If txtPhone.Text = "" Then
    blnInError = True
    FormatControl txtPhone, csError
 End If
'Celina Leong Phoenix1.1 21/03/05
' If txtFax.Text = "" Then
'    blnInError = True
'    FormatControl txtFax, csError
' End If
 
 If TxtEmail.Text = "" Then
    blnInError = True
    FormatControl TxtEmail, csError
 End If
 
 If cboCountry.Text = "" Then
       blnInError = True
       FormatControl cboCountry, csError
 Else
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

 If Trim(txtHMRemark) = "" Then
    blnInError = True
    FormatControl txtHMRemark, csError
 End If
  
 If blnInError Then
    Set ctl = FindControlInError(Me)
    If Not (ctl Is Nothing) Then
       ctl.SetFocus
    End If
 End If
  
 IsValidEdit = Not blnInError
End Function
Private Sub UpdateEditNicHandle(ByRef blnError As Boolean)

Dim lngRowsUpdated As Long
Dim ctl As Control
Dim strSQL As String
Dim lngRow As Long
Dim rec    As ADODB.Recordset
On Error GoTo UpdateEditNichandle_Error
  
If ChangedControls(Me) Then
'Celina Leong Phoenix1.1 29/05/03
'Update History if is first update to History
'   lngRow = HFlexNHandle.Row
'   If lngRow = 0 And Not strHistDate <> "" Then
 
    strSQL = "SELECT * "
    strSQL = strSQL & "FROM NicHandleHist "
    strSQL = strSQL & "WHERE Nic_Handle=" & CDBText(mstrNicHandle) & " "
     
    DB.Execute strSQL, rec
    
    If rec.EOF Then
       mblnNoHistory = True
    Else
       mblnNoHistory = False
    End If
  
   If mblnNoHistory = True Then
     
       WriteFirst_EditHistory_Nichandle mstrNicHandle, blnError
       TempNow = TempTime
       WriteHistory_Fax blnError
       WriteHistory_Phone blnError
      MsgBox "First History write", vbInformation
         
   End If
    
'-------------------------------------------------------

'
    DB.BeginTransaction
'  Celina Leong Phoenx1.1 20/05/03
   WriteEditHistory_Nichandle mstrNicHandle, blnError
   WriteHistory_Fax blnError
   WriteHistory_Phone blnError
'--------------------------------
   strSQL = "UPDATE NicHandle  SET "
   strSQL = strSQL & "Nic_Handle=" & CDBText(txtNicHandle) & ", "
   strSQL = strSQL & "NH_Name=" & CDBText(txtName) & ", "
   'strSQL = strSQL & "A_Number=" & TxtAccNo & " ,"
   strSQL = strSQL & "Co_Name=" & CDBText(TxtCompany) & " , "
   strSQL = strSQL & "NH_Address=" & CDBText(TxtAddress) & " , "
   strSQL = strSQL & "NH_County=" & CDBText(cboCounty) & " , "
   strSQL = strSQL & "NH_Country=" & CDBText(cboCountry) & " , "  ' (TxtCountry)) & " , "
   strSQL = strSQL & "NH_Email=" & CDBText(TxtEmail) & " , "
   strSQL = strSQL & "NH_Status=" & CDBText(txtStatus) & ", "
   strSQL = strSQL & "NH_Remark=" & CDBText(txtHMRemark) & " "
   strSQL = strSQL & "WHERE Nic_Handle=" & CDBText(txtNicHandle) & " "
   strSQL = strSQL & "AND NH_TStamp = " & CDBTimeStamp(mstrTimeStamp) & " "
            
    DB.Execute strSQL ', , , , lngRowsUpdated
'Celina Leong Phoenix1.1 20/05/03
'     WriteEditHistory_Nichandle mstrNicHandle
   
'     If lngRowsUpdated <> 1 Then
'          DB.RollbackTransaction
'          'MsgBox "Could not Update Nic Handle." & vbCrLf & vbCrLf & "Possible reasons : Nic Handle updated by another user while you were editing it", vbExclamation
'          MsgBox "Could not Update Nic Handle." & vbCrLf & vbCrLf & "Nic Handle updated by another user while you were editing it", vbExclamation
'     Else
'
'         DB.CommitTransaction
'
'    End If
   
    
'    If lngRowsUpdated <> 1 Then
'       DB.RollbackTransaction
'       blnError = True
'       MsgBox "Could not update Account." & vbCrLf & vbCrLf & "Possible reasons : Account updated by another user while you were editing it", vbExclamation
'    Else
'       DB.CommitTransaction
''       LoadNicHandle mstrNicHandle ', mstrCreatorNH, mstrBillNH, HFlexEditNHandle.TextMatrix(lngRow, mlngColChngDt)
'
'       blnError = False
'    End If
   
End If

'    If Not blnError Then
'            LoadNicHandle mstrNicHandle, mstrCreatorNH, mstrBillNH
'    End If
'Celina Leong 20/05/03
Exit Sub

UpdateEditNichandle_Error:
     
On Error Resume Next
 
If Err.Number = glngDuplicateKey Then
    MsgBox " Duplicate key ", vbExclamation
Else
    DB.ShowError "Unable to Update Edit Nichandle "
End If

 blnError = True

End Sub

Public Sub WriteEditHistory_Nichandle(ByVal mstrNicHandle As String, ByRef blnError As Boolean)
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
    strSQL = strSQL & CDBText(txtHMRemark) & ", "
    strSQL = strSQL & CDBText(UserID) & ", "
    strSQL = strSQL & CDBTime(TempNow) & " "
    'strSQL = strSQL & "NOW()"
    strSQL = strSQL & "FROM NicHandle "
    strSQL = strSQL & "WHERE Nic_Handle =" & CDBText(mstrNicHandle)
    DB.Execute strSQL
'     strSQL = strSQL & "NH_Remark, " '30/05/03
End Sub

Private Sub LoadEditNicHandleHistory(ByVal strNicHandle As String)
 Dim strSQL As String
 Dim rec    As ADODB.Recordset
 
 On Error GoTo LoadEditNicHandleHistoryERROR
   
 strSQL = "SELECT " & FormatDBDate("Chng_Dt") & " AS Date, "
 strSQL = strSQL & "Chng_NH AS 'Nic Handle', "
 strSQL = strSQL & "NH_Remark, "
 strSQL = strSQL & "Chng_Dt "
 strSQL = strSQL & "FROM NicHandleHist "
 strSQL = strSQL & "WHERE Nic_Handle=" & CDBText(strNicHandle) & " "
 strSQL = strSQL & "ORDER BY Chng_Dt DESC"
 
 DB.Execute strSQL, rec

 Set HFlexEditNHandle.DataSource = rec.DataSource
 


Exit Sub
LoadEditNicHandleHistoryERROR:
 DB.ShowError "Unable to load Nic Handle history"
End Sub


Public Sub LoadEditNicHandle(ByVal strNicHandle As String, ByVal strCreatorNH As String, ByVal strBillNH As String, Optional ByVal strHistDate As String = "")



 Dim strSQL As String
 Dim rec    As ADODB.Recordset
 
 On Error GoTo LoadEditNicHandleERROR
 Screen.MousePointer = vbHourglass
    strSQL = "SELECT * "
    strSQL = strSQL & "FROM NicHandleHist "
    strSQL = strSQL & "WHERE Nic_Handle=" & CDBText(strNicHandle) & " "
    strSQL = strSQL & "AND Chng_Dt = " & CDBTime(strHistDate) & " "
 
 DB.Execute strSQL, rec
 
 If Not rec.EOF Then
    txtNicHandle = rec.Fields("Nic_Handle")
    txtName = rec.Fields("NH_Name")
    TxtAddress = Replace(rec.Fields("NH_Address"), "%", vbCrLf)
    txtCounty = NoNull(rec.Fields("NH_County"))
    'Celina Leong Phoenix1.1 08/04/03
    txtCountry = NoNull(rec.Fields("NH_Country"))
    TxtCompany = NoNull(rec.Fields("Co_Name"))
    txtPhone = ""
    txtFax = ""
    TxtEmail = rec.Fields("NH_Email")
    txtStatus = rec.Fields("NH_Status")
    txtHMRemark = NoNull(rec.Fields("NH_Remark"))
    
    mstrTimeStamp = NoNull(rec.Fields("NH_TStamp"))
    mstrNicHandle = strNicHandle
    mstrCreatorNH = strCreatorNH
    mstrBillNH = strBillNH ' Celina leong 08-10-2002
    mblnRecordLoaded = True
    If txtStatus = "Renew" Then
       Me.HelpContextID = 3020
    Else
       Me.HelpContextID = 5030
    End If
 End If
 
 strSQL = "SELECT Phone FROM Telecom "
 strSQL = strSQL & "WHERE Nic_Handle=" & CDBText(strNicHandle) & " "
 strSQL = strSQL & "AND Type=" & CDBText("P") & " "
 strSQL = strSQL & "LIMIT 1 "
 DB.Execute strSQL, rec
 If Not rec.EOF Then
    txtPhone = rec.Fields("Phone")
 End If
 
 strSQL = "SELECT Phone FROM Telecom "
 strSQL = strSQL & "WHERE Nic_Handle=" & CDBText(strNicHandle) & " "
 strSQL = strSQL & "AND Type=" & CDBText("F") & " "
 strSQL = strSQL & "LIMIT 1 "
 DB.Execute strSQL, rec
 If Not rec.EOF Then
    txtFax = rec.Fields("Phone")
 End If
 Screen.MousePointer = vbNormal
Exit Sub
LoadEditNicHandleERROR:
 mblnRecordLoaded = False
 ShowButtons False, False, True
 ShowSubButtons False, False, False 'Celina Leong Phoenix1.1 08/04/03
 Screen.MousePointer = vbNormal
 DB.ShowError "Unable to load Nic handle"
End Sub


'----------------------------------------------------------------

'Celina Leong Phoenix1.1 20/05/03

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


Private Sub txtFax_KeyPress(KeyAscii As Integer)
        Select Case KeyAscii
            Case 48 To 57
            Case 8
            Case 43
            Case 32
            Case Else
                KeyAscii = 0
        End Select
End Sub


Private Sub txtHMRemark_GotFocus()
    If Not txtHMRemark.Locked Then
        cmdSave.Default = False
    End If
End Sub

Private Sub txtHMRemark_LostFocus()
    If Not txtHMRemark.Locked Then
        cmdSave.Default = True
    End If
End Sub

Private Sub txtPhone_KeyPress(KeyAscii As Integer)
 Select Case KeyAscii
            Case 48 To 57
            Case 8
            Case 43
            Case 32
            Case Else
                KeyAscii = 0
        End Select
End Sub

Private Sub Update_Fax(ByRef blnError As Boolean)
Dim strSQL As String
Dim strFaxType As String

strFaxType = "F"

On Error GoTo UpdateFax_Error

    If TempFax = "" Then
    
        If txtFax <> "" Then
            strSQL = "INSERT INTO Telecom("
            strSQL = strSQL & "Nic_Handle, "
            strSQL = strSQL & "Phone, "
            strSQL = strSQL & "Type) "
       
            strSQL = strSQL & "VALUES( "
            strSQL = strSQL & CDBText(txtNicHandle.Text) & ","
            strSQL = strSQL & CDBText(txtFax) & ","
            strSQL = strSQL & CDBText(strFaxType) & ");"
            
            DB.Execute strSQL
        End If

   Else
             strSQL = "UPDATE Telecom SET "
             strSQL = strSQL & "Phone=" & CDBText(txtFax) & " "
             strSQL = strSQL & "WHERE Nic_Handle=" & CDBText(txtNicHandle) & " "
             strSQL = strSQL & "AND Phone=" & CDBText(TempFax) & " "
             strSQL = strSQL & "And Type =" & CDBText(strFaxType) & " "
                
             DB.Execute strSQL
     
          
   End If

Exit Sub

UpdateFax_Error:
 
On Error Resume Next
 
If Err.Number = glngDuplicateKey Then
    MsgBox " Duplicate key ", vbExclamation
Else
    DB.ShowError "Unable to Update Fax No "
End If


 blnError = True
     
    
End Sub

Private Sub Update_Phone(ByRef blnError As Boolean)
Dim strSQL As String
Dim strPhoneType As String

strPhoneType = "P"

On Error GoTo UpdatePhone_Error


            
      If TempPhone = "" Then
      
            strSQL = "INSERT INTO Telecom("
            strSQL = strSQL & "Nic_Handle, "
            strSQL = strSQL & "Phone, "
            strSQL = strSQL & "Type) "
            
            strSQL = strSQL & "VALUES( "
            strSQL = strSQL & CDBText(txtNicHandle.Text) & ","
            strSQL = strSQL & CDBText(txtPhone) & ","
            strSQL = strSQL & CDBText(strPhoneType) & ");"
            
            DB.Execute strSQL

   Else
   
    
             strSQL = "UPDATE Telecom SET "
             strSQL = strSQL & "Phone=" & CDBText(txtPhone) & " "
             strSQL = strSQL & "WHERE Nic_Handle=" & CDBText(txtNicHandle) & " "
             strSQL = strSQL & "AND Phone=" & CDBText(TempPhone) & " "
             strSQL = strSQL & "And Type =" & CDBText(strPhoneType) & " "
                
             DB.Execute strSQL
    End If

    
Exit Sub


UpdatePhone_Error:
     

On Error Resume Next
 
If Err.Number = glngDuplicateKey Then
    MsgBox " Duplicate key ", vbExclamation
Else
    DB.ShowError "Unable to Update Phone No "
End If


 blnError = True


End Sub

Private Sub WriteHistory_Phone(ByRef blnError As Boolean)
Dim strSQL As String
Dim TempType As String

TempType = "P"

      strSQL = "INSERT INTO TelecomHist("
            strSQL = strSQL & "Nic_Handle, "
            strSQL = strSQL & "Phone, "
            strSQL = strSQL & "Type, "
            strSQL = strSQL & "Chng_NH, "
            strSQL = strSQL & "Chng_Dt) "
 
            strSQL = strSQL & "SELECT DISTINCT " 'CC20051221 fix for MySQLv4.0
            strSQL = strSQL & "Nic_Handle, "
            strSQL = strSQL & CDBText(TempPhone) & ", "
            strSQL = strSQL & CDBText(TempType) & ", "
            strSQL = strSQL & CDBText(UserID) & ", "
            strSQL = strSQL & CDBTime(TempNow) & " "
            strSQL = strSQL & "FROM Telecom "
            strSQL = strSQL & "WHERE Nic_Handle ='" & txtNicHandle & "'"
           
            DB.Execute strSQL
''--------------------------------------------------------------
End Sub

Private Sub WriteHistory_Fax(ByRef blnError As Boolean)
Dim strSQL As String
Dim TempType As String

TempType = "F"

      strSQL = "INSERT INTO TelecomHist("
            strSQL = strSQL & "Nic_Handle, "
            strSQL = strSQL & "Phone, "
            strSQL = strSQL & "Type, "
            strSQL = strSQL & "Chng_NH, "
            strSQL = strSQL & "Chng_Dt) "
 
            strSQL = strSQL & "SELECT DISTINCT " 'CC20051221 fix for MySQLv4.0
            strSQL = strSQL & "Nic_Handle, "
            strSQL = strSQL & CDBText(TempFax) & ", "
            strSQL = strSQL & CDBText(TempType) & ", "
            strSQL = strSQL & CDBText(UserID) & ", "
            strSQL = strSQL & CDBTime(TempNow) & " "
            strSQL = strSQL & "FROM Telecom "
            strSQL = strSQL & "WHERE Nic_Handle ='" & txtNicHandle & "'"
           
            DB.Execute strSQL
''--------------------------------------------------------------
End Sub

Public Sub WriteFirst_EditHistory_Nichandle(ByVal mstrNicHandle As String, ByRef blnError As Boolean)
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
    'strSQL = strSQL & "NOW()"
    strSQL = strSQL & "FROM NicHandle "
    strSQL = strSQL & "WHERE Nic_Handle =" & CDBText(mstrNicHandle)
    DB.Execute strSQL
    
End Sub
