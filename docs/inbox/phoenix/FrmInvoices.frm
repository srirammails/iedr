VERSION 5.00
Object = "{0ECD9B60-23AA-11D0-B351-00A0C9055D8E}#6.0#0"; "MSHFLXGD.OCX"
Object = "{86CF1D34-0C5F-11D2-A9FC-0000F8754DA1}#2.0#0"; "MSCOMCT2.OCX"
Begin VB.Form FrmInvoices 
   Caption         =   "Invoice"
   ClientHeight    =   7800
   ClientLeft      =   60
   ClientTop       =   345
   ClientWidth     =   11835
   HelpContextID   =   6000
   LinkTopic       =   "Form1"
   MDIChild        =   -1  'True
   ScaleHeight     =   7800
   ScaleWidth      =   11835
   WindowState     =   2  'Maximized
   Begin VB.Frame fraRunDate 
      Caption         =   "Run Dates"
      Height          =   1575
      Left            =   9120
      TabIndex        =   22
      Top             =   240
      Width           =   2415
      Begin VB.Label lblLastRemittleRun 
         Height          =   255
         Left            =   480
         TabIndex        =   26
         Top             =   1080
         Width           =   1575
      End
      Begin VB.Label lblLastInvoiceRun 
         Height          =   255
         Left            =   480
         TabIndex        =   25
         Top             =   480
         Width           =   1575
      End
      Begin VB.Label Label3 
         Caption         =   "Last Remmital Run"
         Height          =   255
         Left            =   240
         TabIndex        =   24
         Top             =   840
         Width           =   1695
      End
      Begin VB.Label Label2 
         Caption         =   "Last Invoice Run"
         Height          =   255
         Left            =   240
         TabIndex        =   23
         Top             =   240
         Width           =   1455
      End
   End
   Begin VB.Frame Frame1 
      Height          =   735
      Left            =   7200
      TabIndex        =   21
      Top             =   6960
      Width           =   4335
      Begin VB.CommandButton cmdClose 
         Cancel          =   -1  'True
         Caption         =   "&Close"
         Height          =   375
         Left            =   1440
         TabIndex        =   10
         Top             =   240
         Width           =   1380
      End
   End
   Begin VB.Frame FraInvoices 
      Caption         =   "Remittals/Invoices"
      ForeColor       =   &H00000000&
      Height          =   5040
      Left            =   240
      TabIndex        =   13
      Top             =   1920
      Width           =   11295
      Begin VB.ComboBox cboStatus 
         Height          =   315
         ItemData        =   "FrmInvoices.frx":0000
         Left            =   9360
         List            =   "FrmInvoices.frx":0002
         Style           =   2  'Dropdown List
         TabIndex        =   7
         Top             =   4560
         Width           =   1455
      End
      Begin MSHierarchicalFlexGridLib.MSHFlexGrid HFlexInvoices 
         Height          =   4095
         Left            =   240
         TabIndex        =   14
         Top             =   360
         Width           =   10815
         _ExtentX        =   19076
         _ExtentY        =   7223
         _Version        =   393216
         Cols            =   12
         BackColorFixed  =   16744576
         BackColorSel    =   16761024
         GridColorFixed  =   16744576
         GridColorUnpopulated=   16761024
         ScrollTrack     =   -1  'True
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
         _Band(0).Cols   =   12
         _Band(0).GridLinesBand=   1
         _Band(0).TextStyleBand=   0
         _Band(0).TextStyleHeader=   0
      End
      Begin VB.Label LblStaRemittal 
         Caption         =   "Change status of remittal"
         Height          =   195
         Left            =   7455
         TabIndex        =   20
         Top             =   4650
         Width           =   1815
      End
   End
   Begin VB.Frame FraOption 
      Caption         =   "Generate"
      ForeColor       =   &H00000000&
      Height          =   735
      Left            =   240
      TabIndex        =   12
      Top             =   6960
      Width           =   4215
      Begin VB.CommandButton cmdInvoices 
         Caption         =   "In&voices"
         Height          =   375
         Left            =   2160
         TabIndex        =   9
         Top             =   240
         Width           =   1425
      End
      Begin VB.CommandButton cmdRemittals 
         Caption         =   "Remi&ttals"
         Height          =   375
         Left            =   480
         TabIndex        =   8
         Top             =   240
         Width           =   1425
      End
   End
   Begin VB.Frame FraSearch 
      Caption         =   "Search"
      ForeColor       =   &H00000000&
      Height          =   1575
      Left            =   240
      TabIndex        =   11
      Top             =   240
      Width           =   8775
      Begin VB.ComboBox cboSearchStatus 
         Height          =   315
         ItemData        =   "FrmInvoices.frx":0004
         Left            =   6600
         List            =   "FrmInvoices.frx":0014
         TabIndex        =   5
         Text            =   "All"
         Top             =   480
         Width           =   1695
      End
      Begin VB.CommandButton cmdSearch 
         Caption         =   "&Search"
         Height          =   375
         Left            =   6960
         TabIndex        =   6
         Top             =   960
         Width           =   1140
      End
      Begin VB.Frame FrameSearch1 
         BorderStyle     =   0  'None
         Height          =   1095
         Left            =   120
         TabIndex        =   18
         Top             =   240
         Width           =   1335
         Begin VB.OptionButton OptPreRe 
            Caption         =   "&Pre-remittals"
            Height          =   255
            Left            =   120
            TabIndex        =   0
            Top             =   120
            Width           =   1335
         End
         Begin VB.OptionButton OptInvoice 
            Caption         =   "&Invoices"
            Height          =   255
            Left            =   120
            TabIndex        =   2
            Top             =   840
            Width           =   975
         End
         Begin VB.OptionButton OptRemittals 
            Caption         =   "&Remittals"
            Height          =   255
            Left            =   120
            TabIndex        =   1
            Top             =   480
            Width           =   975
         End
      End
      Begin VB.Frame fraDateSearch 
         Caption         =   "Date Range"
         Height          =   1095
         Left            =   1920
         TabIndex        =   15
         Top             =   240
         Width           =   3975
         Begin MSComCtl2.DTPicker dtpFrom 
            Height          =   300
            Left            =   1320
            TabIndex        =   3
            Top             =   240
            Width           =   1335
            _ExtentX        =   2355
            _ExtentY        =   529
            _Version        =   393216
            CalendarTitleBackColor=   -2147483646
            CalendarTitleForeColor=   -2147483639
            CustomFormat    =   "dd/MM/yyyy"
            Format          =   20381699
            CurrentDate     =   37356
         End
         Begin MSComCtl2.DTPicker dtpTo 
            Height          =   300
            Left            =   1320
            TabIndex        =   4
            Top             =   600
            Width           =   1335
            _ExtentX        =   2355
            _ExtentY        =   529
            _Version        =   393216
            CalendarTitleBackColor=   -2147483646
            CalendarTitleForeColor=   -2147483639
            CustomFormat    =   "dd/MM/yyyy"
            Format          =   20381699
            CurrentDate     =   37356
         End
         Begin VB.Label lblTo 
            Alignment       =   1  'Right Justify
            Caption         =   "To:"
            Height          =   300
            Left            =   645
            TabIndex        =   17
            Top             =   660
            Width           =   375
         End
         Begin VB.Label lblFrom 
            Alignment       =   1  'Right Justify
            Caption         =   "From:"
            Height          =   300
            Left            =   405
            TabIndex        =   16
            Top             =   300
            Width           =   615
         End
      End
      Begin VB.Label Label1 
         Caption         =   "Status"
         Height          =   255
         Left            =   6600
         TabIndex        =   19
         Top             =   240
         Width           =   1215
      End
   End
End
Attribute VB_Name = "FrmInvoices"
Attribute VB_GlobalNameSpace = False
Attribute VB_Creatable = False
Attribute VB_PredeclaredId = True
Attribute VB_Exposed = False
Option Explicit

'*********************************************
'*                                           *
'* Written By : Brian Devlin                 *
'* Function   : Shows all Pre-Remittals,     *
'*              Remittals and Invoices on the*
'*              system. Allows running of    *
'*              Remittal and Invoice batch   *
'*********************************************

Const mlngDaysIn         As Long = 7
Const mlngColKey         As Long = 2
Const mlngColRenSort     As Long = 1
Const mlngColRenewal     As Long = 3
Const mlngColAmount      As Long = 7
Const mlngColStatus      As Long = 9
Const mstrFileDelimiter  As String = ","

Dim mblnInvoicesLoaded As Boolean
Dim mblnDatesLoaded    As Boolean
Dim mblnChangingStatus As Boolean
Dim mitSelected        As InvoiceType

Dim mblnProcessedRemittles As Boolean

Dim mdtmLastInvoiceRun  As Date
Dim mdtmLastRemittleRun As Date
Dim mdtmPreRemitCutOff  As Date

Private Enum InvoiceType
 itPreRemittal = 1
 itRemittal = 2
 itInvoice = 3
End Enum


Private Sub PopInvoices_Grid()
 With HFlexInvoices
    .ColWidth(0) = 195
    .ColWidth(1) = 0
    .ColWidth(2) = 3480
    .ColWidth(3) = 1095
    .ColWidth(4) = 990
    .ColWidth(5) = 1365
    .ColWidth(6) = 480
    .ColWidth(7) = 825
    .ColWidth(8) = 870
    .ColWidth(9) = 870
    .ColWidth(10) = 255
            
    .TextMatrix(0, 1) = "RenSort"
    .TextMatrix(0, 2) = "Domain Name"
    .TextMatrix(0, 3) = "Renewal"
    .TextMatrix(0, 4) = "Account"
    .TextMatrix(0, 5) = "Billing Contact"
    .TextMatrix(0, 6) = "Discount"
    .TextMatrix(0, 7) = "Amount"
    .TextMatrix(0, 8) = "New Reg"
    .TextMatrix(0, 9) = "Status"
    .TextMatrix(0, 10) = "Proc Created"
            
    .ColAlignment(mlngColAmount) = flexAlignRightCenter
 End With
End Sub


Private Sub cboStatus_Click()
 If Not mblnChangingStatus Then
    UpdateStatus cboStatus
 End If
End Sub


Private Sub CmdClose_Click()
 Unload Me
End Sub


Private Sub cmdInvoices_Click()
 If Dir(App.Path & "\Invoices.exe") = "" Then
    MsgBox "Could not locate file Invoices.exe", vbCritical
 Else
    Shell App.Path & "\Invoices.exe /U " & UserID & " /P " & Password & " /W / A", vbNormalFocus
 End If
End Sub



Private Sub cmdRemittals_Click()
 If Dir(App.Path & "\Remittals.exe") = "" Then
    MsgBox "Could not locate file Remittals.exe", vbCritical
 Else
    Shell App.Path & "\Remittals.exe /U " & UserID & " /P " & Password & " /W / A", vbNormalFocus
 End If
 mblnProcessedRemittles = True
End Sub


Private Sub cmdSearch_Click()
 On Error Resume Next
 Screen.MousePointer = vbHourglass
 
 LoadInvoices SearchWhere()
 cboStatus.Visible = (mitSelected <> itInvoice)
 LblStaRemittal.Visible = (mitSelected <> itInvoice)
  
 ShowButtons True, True, True
 Screen.MousePointer = vbNormal
End Sub


Private Function SearchWhere() As String
 Dim strWhere As String
 
 If OptPreRe Then
    strWhere = strWhere & "I_Ren_Dt>=" & CDBDate(mdtmPreRemitCutOff) & " AND "
 ElseIf OptRemittals Then
    strWhere = strWhere & "I_Ren_Dt<=" & CDBDate(mdtmPreRemitCutOff - 1) & " AND "
 End If
 
 If cboSearchStatus <> gstrComboAllText Then
    strWhere = strWhere & "I_Status=" & CDBText(cboSearchStatus) & " AND "
 End If
 
 If DateSearchEnable Then
    strWhere = strWhere & "I_Ren_Dt>=" & CDBDate(dtpFrom.Value) & " AND "
    strWhere = strWhere & "I_Ren_Dt<=" & CDBDate(dtpTo.Value) & " AND "
 End If
 
 If strWhere <> "" Then
    strWhere = Left(strWhere, Len(strWhere) - 5)
 End If
 
 SearchWhere = strWhere
End Function


Private Sub Form_Load()
 FillRunDate
 FillInvoiceStatus cboSearchStatus, True
 FillInvoiceStatus cboStatus
  
 OptPreRe.Value = True
 
 PopInvoices_Grid
 ShowButtons True, True, True
 'tsmyth oct2003 - ensure english format date on PC
 Call CheckDateFormat

End Sub


Private Sub Form_Resize()
 CenterControls Me
End Sub


Private Sub HFlexInvoices_MouseUp(Button As Integer, Shift As Integer, x As Single, y As Single)
 Static lngSortedCol As Long
 Dim lngCol  As Long
 Dim lngCols As Long
 Dim lngX    As Long
 
 If y <= HFlexInvoices.RowHeight(0) Then
    lngCols = HFlexInvoices.Cols - 1
    For lngCol = 0 To lngCols
        lngX = lngX + HFlexInvoices.ColWidth(lngCol)
        If lngX >= x Then
           If lngCol = mlngColRenewal Then lngCol = mlngColRenSort
           HFlexInvoices.Col = lngCol
           If lngSortedCol = lngCol Then
              HFlexInvoices.Sort = 2
              lngSortedCol = -1
           Else
              HFlexInvoices.Sort = 1
              lngSortedCol = lngCol
           End If
           Exit For
        End If
    Next lngCol
 End If
End Sub


Private Sub HFlexInvoices_RowColChange()
 If mitSelected = itInvoice Then Exit Sub
  
 mblnChangingStatus = True
 SetComboText cboStatus, HFlexInvoices.TextMatrix(HFlexInvoices.Row, mlngColStatus)
 mblnChangingStatus = False
End Sub


Private Sub HFlexInvoices_SelChange()
 HFlexInvoices.RowSel = HFlexInvoices.Row
End Sub


Private Sub OptInvoice_Click()
 DateSearchEnable = True
 mitSelected = itInvoice
 cmdSearch.Value = True
End Sub


Private Sub OptRemittals_Click()
 DateSearchEnable = False
 mitSelected = itRemittal
 cmdSearch.Value = True
End Sub


Private Sub OptPreRe_Click()
 DateSearchEnable = False
 mitSelected = itPreRemittal
 cmdSearch.Value = True
End Sub


Public Sub LoadInvoices(ByVal strWhere As String)
 Dim strSQL As String
 Dim rec    As ADODB.Recordset
 
 Dim curTrade     As Currency
 Dim curNonTradeD As Currency
 Dim curNonTrade  As Currency
 Dim dblVAT       As Double
 
 On Error GoTo LoadInvoicesERROR
 
 If mblnProcessedRemittles Then FillRunDate
 
 curTrade = DB.StaticData(ustTradeRate)
 curNonTradeD = DB.StaticData(ustHistoricalRetail)
 curNonTrade = DB.StaticData(ustRetailRate)
 dblVAT = DB.StaticData(ustVatRate)
  
 If mitSelected = itInvoice Then
    strSQL = "SELECT "
    strSQL = strSQL & "DATE_FORMAT(I_Ren_Dt,'%Y%m%d') AS 'RenSort',"
    strSQL = strSQL & "I_DName AS 'Domain Name',"
    strSQL = strSQL & FormatDBDate("I_Ren_Dt") & " AS 'Renewal',"
    strSQL = strSQL & "LPAD(I_Acc_No,8,'0') AS 'Account',"
    strSQL = strSQL & "I_Bill_NH AS 'Billing contact',"
    strSQL = strSQL & "I_Discount AS 'Discount',"
    strSQL = strSQL & "I_Amount AS 'Amount',"
    strSQL = strSQL & "I_NewReg AS 'New Reg',"
    strSQL = strSQL & "I_Status AS 'Status',"
    strSQL = strSQL & "I_Create_Prc AS 'Proc Created' "
   
    strSQL = strSQL & "FROM InvoiceHist "
 
 Else
    strSQL = "SELECT "
    strSQL = strSQL & "DATE_FORMAT(I_Ren_Dt,'%Y%m%d') AS 'RenSort',"
    strSQL = strSQL & "I.I_DName AS 'Domain Name',"
    strSQL = strSQL & FormatDBDate("I.I_Ren_Dt") & " AS 'Renewal',"
    strSQL = strSQL & "LPAD(D.A_Number,8,'0') AS 'Account',"
    strSQL = strSQL & "B.Contact_NH AS 'Billing contact',"
    strSQL = strSQL & "D.D_Discount AS 'Discount',"
   
    strSQL = strSQL & "FORMAT(IF(D.A_Number <> 1," & curTrade & " * IF(P.VAT_Exempt='Exempt',1," & 1 + dblVAT & "),"
    strSQL = strSQL & " IF(D.D_Discount='Y'," & curNonTradeD & " * IF(P.VAT_Exempt='Exempt',1," & 1 + dblVAT & "), "
    strSQL = strSQL & curNonTrade & " * IF(P.VAT_Exempt='Exempt',1," & 1 + dblVAT & "))),2) AS 'Amount',"
   
    strSQL = strSQL & "I.I_NewReg AS 'New Reg',"
    strSQL = strSQL & "I.I_Status AS 'Status',"
    strSQL = strSQL & "I.I_Create_Prc AS 'Proc Created' "
   
    strSQL = strSQL & "FROM Invoice AS I, "
    strSQL = strSQL & "     Domain  AS D, "
    strSQL = strSQL & "     Contact AS B "
    strSQL = strSQL & "LEFT OUTER JOIN Payment AS P "
    strSQL = strSQL & "ON P.Billing_Contact=B.Contact_NH "
    strSQL = strSQL & "WHERE I.I_DName=D.D_Name "
    strSQL = strSQL & "AND   D.D_Name=B.D_Name "
    strSQL = strSQL & "AND   B.Type='B' "
 End If
 
 If strWhere <> "" Then
   strSQL = strSQL & IIf(mitSelected = itInvoice, "WHERE  ", "AND ") & strWhere
 End If
 strSQL = strSQL & " ORDER BY I_Ren_Dt "
 
 DB.Execute strSQL, rec

 EmptyGrid
 Set HFlexInvoices.DataSource = rec.DataSource
 HFlexInvoices.ColAlignment(mlngColAmount) = flexAlignRightCenter
 If HFlexInvoices.Rows <= 1 Then
    HFlexInvoices.Row = 0
    mblnChangingStatus = True
    cboStatus.ListIndex = -1
    mblnChangingStatus = False
    mblnInvoicesLoaded = False
 Else
    mblnInvoicesLoaded = True
    HFlexInvoices_RowColChange
 End If
 
Exit Sub
LoadInvoicesERROR:
 mblnInvoicesLoaded = False
 DB.ShowError "Unable to load Invoices"
End Sub


Private Sub UpdateStatus(ByVal strSelected As String)
 Dim strSQL         As String
 Dim lngRow         As Long
 Dim strDomain      As String
 Dim strOrigStatus  As String
 Dim lngRowsUpdated As Long
 
 On Error GoTo UpdateStatusERROR
 
 Screen.MousePointer = vbHourglass
 With HFlexInvoices
    lngRow = .Row
    strDomain = .TextMatrix(lngRow, mlngColKey)
    strOrigStatus = .TextMatrix(lngRow, mlngColStatus)
 End With
  
 strSQL = "UPDATE Invoice SET "
 strSQL = strSQL & "I_Status=" & CDBText(strSelected) & ", "
 strSQL = strSQL & "I_Stat_Dt=NOW() "
 strSQL = strSQL & "WHERE I_DName=" & CDBText(strDomain) & " "
 strSQL = strSQL & "AND I_Status=" & CDBText(strOrigStatus)
 
 DB.Execute strSQL, , , , lngRowsUpdated
 
 If lngRowsUpdated < 1 Then
    MsgBox "Status not updated", vbCritical
 Else
    With HFlexInvoices
      .TextMatrix(lngRow, mlngColStatus) = strSelected
    End With
 End If
 Screen.MousePointer = vbNormal
Exit Sub
UpdateStatusERROR:
 Screen.MousePointer = vbNormal
 DB.ShowError "Unable to update Status"
End Sub


Private Sub ShowButtons(ByVal blnRemittals As Boolean, _
                        ByVal blnInvoices As Boolean, _
                        ByVal blnClose As Boolean)

 cmdRemittals.Enabled = blnRemittals And HasAuthority(atAccounts) And mblnDatesLoaded
 cmdInvoices.Enabled = blnInvoices And HasAuthority(atAccounts) And mblnDatesLoaded
 cmdClose.Enabled = blnClose
 
 cmdSearch.Enabled = mblnDatesLoaded
 cboStatus.Enabled = mblnInvoicesLoaded And HasAuthority(atAccounts)
End Sub


Private Sub EmptyGrid()
 HFlexInvoices.Clear
 HFlexInvoices.FixedRows = 0
 HFlexInvoices.Rows = 2
 HFlexInvoices.FixedRows = 1
 mblnInvoicesLoaded = False
End Sub


Private Sub FillInvoiceStatus(cbo As ComboBox, Optional ByVal blnShowAll As Boolean = False)
 cbo.Clear
 If blnShowAll Then
    cbo.AddItem gstrComboAllText
    cbo.ListIndex = 0
 End If
 cbo.AddItem "Active"
 cbo.AddItem "Suspend"
End Sub


Private Property Let DateSearchEnable(ByVal blnNewValue As Boolean)
 fraDateSearch.Enabled = blnNewValue
 LblFrom.Enabled = blnNewValue
 LblTo.Enabled = blnNewValue
 dtpFrom.Enabled = blnNewValue
 dtpTo.Enabled = blnNewValue
End Property


Private Property Get DateSearchEnable() As Boolean
 DateSearchEnable = fraDateSearch.Enabled
End Property


Private Sub FillRunDate()
 On Error GoTo FillRunDateERROR
 
 mdtmLastInvoiceRun = DB.StaticData(ustLastInvoiceRun)
 mdtmLastRemittleRun = DB.StaticData(ustLastRemittleRun)
 
 lblLastInvoiceRun = Format(mdtmLastInvoiceRun, "mmmm yyyy")
 lblLastRemittleRun = Format(mdtmLastRemittleRun, "mmmm yyyy")
 
' If Day(mdtmLastRemittleRun) <= mlngDaysIn Then
'    mdtmPreRemitCutOff = DateSerial(Year(mdtmLastRemittleRun), Month(mdtmLastRemittleRun) + 1, 1)
' Else
    mdtmPreRemitCutOff = DateSerial(Year(mdtmLastRemittleRun), Month(mdtmLastRemittleRun) + 2, 1)
' End If
 dtpFrom = mdtmLastInvoiceRun
 dtpTo = DateSerial(Year(mdtmLastInvoiceRun), Month(mdtmLastInvoiceRun) + 1, 1) - 1

 
 mblnDatesLoaded = True
Exit Sub
FillRunDateERROR:
 mblnDatesLoaded = False
 DB.ShowError "Unable to load last Run Dates"
End Sub
