VERSION 5.00
Object = "{0ECD9B60-23AA-11D0-B351-00A0C9055D8E}#6.0#0"; "mshflxgd.ocx"
Object = "{86CF1D34-0C5F-11D2-A9FC-0000F8754DA1}#2.0#0"; "mscomct2.ocx"
Begin VB.Form FrmTicket 
   Caption         =   "Ticket "
   ClientHeight    =   7875
   ClientLeft      =   60
   ClientTop       =   345
   ClientWidth     =   14070
   HelpContextID   =   5000
   KeyPreview      =   -1  'True
   LinkTopic       =   "Form1"
   MDIChild        =   -1  'True
   ScaleHeight     =   7875
   ScaleWidth      =   14070
   WindowState     =   2  'Maximized
   Begin VB.Frame FraCurTicket 
      Caption         =   "Current Ticket"
      ForeColor       =   &H00000000&
      Height          =   3915
      Left            =   360
      TabIndex        =   25
      Top             =   2760
      Width           =   13335
      Begin MSHierarchicalFlexGridLib.MSHFlexGrid HFlexTickets 
         Height          =   3015
         Left            =   240
         TabIndex        =   9
         Top             =   360
         Width           =   12855
         _ExtentX        =   22675
         _ExtentY        =   5318
         _Version        =   393216
         Cols            =   13
         BackColorFixed  =   16744576
         BackColorSel    =   16761024
         GridColor       =   16744576
         GridColorFixed  =   16744576
         AllowBigSelection=   0   'False
         FocusRect       =   0
         FillStyle       =   1
         SelectionMode   =   1
         AllowUserResizing=   1
         BeginProperty Font {0BE35203-8F91-11CE-9DE3-00AA004BB851} 
            Name            =   "MS Sans Serif"
            Size            =   8.25
            Charset         =   0
            Weight          =   400
            Underline       =   0   'False
            Italic          =   0   'False
            Strikethrough   =   0   'False
         EndProperty
         _NumberOfBands  =   1
         _Band(0).Cols   =   13
         _Band(0).GridLinesBand=   1
         _Band(0).TextStyleBand=   0
         _Band(0).TextStyleHeader=   0
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
         TabIndex        =   26
         Top             =   3480
         Visible         =   0   'False
         Width           =   10815
      End
   End
   Begin VB.Frame FraOption 
      Caption         =   "Options"
      ForeColor       =   &H00000000&
      Height          =   735
      Left            =   360
      TabIndex        =   16
      Top             =   6840
      Width           =   13335
      Begin VB.CommandButton cmdClose 
         Caption         =   "&Close"
         Height          =   375
         Left            =   10800
         TabIndex        =   21
         Top             =   240
         Width           =   1215
      End
      Begin VB.CommandButton cmdCheckIn 
         Caption         =   "Check &In"
         Height          =   375
         Left            =   3240
         TabIndex        =   13
         Top             =   240
         Width           =   1215
      End
      Begin VB.CommandButton cmdAlterStatus 
         Caption         =   "Alter Stat&us"
         Height          =   375
         Left            =   5160
         TabIndex        =   15
         Top             =   240
         Width           =   1215
      End
      Begin VB.CommandButton cmdEdit 
         Caption         =   "&Edit"
         Height          =   375
         Left            =   9000
         TabIndex        =   19
         Top             =   240
         Width           =   1215
      End
      Begin VB.CommandButton cmdReassign 
         Caption         =   "&Reassign"
         Height          =   375
         Left            =   7080
         TabIndex        =   17
         Top             =   240
         Width           =   1215
      End
      Begin VB.CommandButton cmdCheckOut 
         BackColor       =   &H0080C0FF&
         Caption         =   "Check &Out"
         Height          =   375
         Left            =   1440
         TabIndex        =   11
         Top             =   240
         Width           =   1215
      End
   End
   Begin VB.Frame FraSearchCriteria 
      Caption         =   "Search Criteria"
      ForeColor       =   &H00000000&
      Height          =   2175
      Left            =   360
      TabIndex        =   10
      Top             =   360
      Width           =   13335
      Begin VB.TextBox txtDomainName 
         Height          =   285
         Left            =   8400
         TabIndex        =   7
         Text            =   "*"
         Top             =   1560
         Width           =   1935
      End
      Begin VB.CommandButton cmdSearch 
         Caption         =   "&Search"
         Height          =   375
         Left            =   11160
         TabIndex        =   8
         Top             =   1440
         Width           =   1575
      End
      Begin VB.Frame FraDateRange 
         Caption         =   "Date Range"
         ForeColor       =   &H00000000&
         Height          =   1575
         Left            =   360
         TabIndex        =   20
         Top             =   360
         Width           =   4935
         Begin VB.Frame FraRanges 
            Height          =   975
            Left            =   2160
            TabIndex        =   22
            Top             =   360
            Width           =   2295
            Begin MSComCtl2.DTPicker DTPickerTo 
               Height          =   315
               Left            =   840
               TabIndex        =   3
               Top             =   600
               Width           =   1335
               _ExtentX        =   2355
               _ExtentY        =   556
               _Version        =   393216
               Enabled         =   0   'False
               CalendarTitleBackColor=   -2147483635
               CalendarTitleForeColor=   -2147483634
               Format          =   20250625
               CurrentDate     =   37354
            End
            Begin MSComCtl2.DTPicker DTPickerFrm 
               Height          =   315
               Left            =   840
               TabIndex        =   2
               Top             =   240
               Width           =   1335
               _ExtentX        =   2355
               _ExtentY        =   556
               _Version        =   393216
               Enabled         =   0   'False
               CalendarTitleBackColor=   -2147483635
               CalendarTitleForeColor=   -2147483634
               Format          =   20250625
               CurrentDate     =   37354
            End
            Begin VB.Label LblFrom 
               Caption         =   "From"
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
               TabIndex        =   24
               Top             =   240
               Width           =   495
            End
            Begin VB.Label LblTo 
               Caption         =   "To"
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
               TabIndex        =   23
               Top             =   600
               Width           =   495
            End
         End
         Begin VB.OptionButton OptAllDates 
            Caption         =   "All &Dates"
            Height          =   255
            Left            =   240
            TabIndex        =   0
            Top             =   480
            Value           =   -1  'True
            Width           =   1095
         End
         Begin VB.OptionButton OptDateRange 
            Caption         =   "Date Ran&ge"
            Height          =   255
            Left            =   240
            TabIndex        =   1
            Top             =   960
            Width           =   1455
         End
      End
      Begin VB.ComboBox cboTechStatus 
         Height          =   315
         ItemData        =   "FrmTicket.frx":0000
         Left            =   6000
         List            =   "FrmTicket.frx":0002
         Style           =   2  'Dropdown List
         TabIndex        =   6
         Top             =   1560
         Width           =   1935
      End
      Begin VB.ComboBox cboNicHandle 
         Height          =   315
         ItemData        =   "FrmTicket.frx":0004
         Left            =   8400
         List            =   "FrmTicket.frx":001D
         Style           =   2  'Dropdown List
         TabIndex        =   5
         Top             =   720
         Width           =   1935
      End
      Begin VB.ComboBox cboAdminStatus 
         Height          =   315
         ItemData        =   "FrmTicket.frx":0096
         Left            =   6000
         List            =   "FrmTicket.frx":0098
         Style           =   2  'Dropdown List
         TabIndex        =   4
         Top             =   720
         Width           =   1935
      End
      Begin VB.Label lblDomainName 
         Caption         =   "Domain Name"
         Height          =   255
         Left            =   8400
         TabIndex        =   27
         Top             =   1320
         Width           =   1575
      End
      Begin VB.Label Label1 
         Caption         =   "Tech Status"
         Height          =   255
         Left            =   6000
         TabIndex        =   18
         Top             =   1320
         Width           =   1095
      End
      Begin VB.Label LblAssignedTo 
         Caption         =   "NIC Handle"
         Height          =   255
         Left            =   8400
         TabIndex        =   14
         Top             =   480
         Width           =   1095
      End
      Begin VB.Label LblStatus 
         Caption         =   "Admin Status"
         Height          =   255
         Left            =   6000
         TabIndex        =   12
         Top             =   480
         Width           =   1095
      End
   End
End
Attribute VB_Name = "FrmTicket"
Attribute VB_GlobalNameSpace = False
Attribute VB_Creatable = False
Attribute VB_PredeclaredId = True
Attribute VB_Exposed = False
Option Explicit

'*********************************************
'*                                           *
'* Written By : Brian Devlin                 *
'* Function   : Handles all ticket checking  *
'*              in/out                       *
'*********************************************

Const mlngColKey         As Long = 1
Const mlngColDateSort    As Long = 2
Const mlngColDate        As Long = 3
Const mlngColAccountName As Long = 4
Const mlngColDom         As Long = 5
Const mlngColType        As Long = 6
Const mlngColAdminCode   As Long = 7
Const mlngColAdminStatus As Long = 8
Const mlngColNicHandle   As Long = 11
Const mlngColCheckedOut  As Long = 12
Const mlngColHolder      As Long = 13
Const mlngColToolTipText As Long = 0
'Const lCellFlagOtherDoms_BackColor As Long = 13239354
'Const lCellFlagOtherDoms_ForeColor As Long = vbWhite

Const mstrRemarkRemove   As String = "Admin status set to Cancelled - Ticket Deleted"

Dim WithEvents mfrmEditTicketD As FrmEditTicketD
Attribute mfrmEditTicketD.VB_VarHelpID = -1
Dim mblnTicketsLoaded    As Boolean
Dim sPreviousDomainHolder() As String
Const lCellBackColor_PreviousDomainHolder As Long = 13239354
Const lCellForeColor_PreviousDomainHolder As Long = vbWhite


'Const iMaxTickets        As Integer = 5000
'Dim ApplicantsDomains(iMaxTickets) As String
Private Sub cmdAlterStatus_Click()
 Dim strSQL    As String
 Dim strStatus As String
 Dim lngStatus As Long
 Dim blnError  As Boolean
 
 strSQL = "SELECT description, status FROM TicketAdminStatus ORDER BY status ASC"
 strStatus = HFlexTickets.TextMatrix(HFlexTickets.Row, mlngColAdminStatus)
 FrmList.ShowList strSQL, strStatus, lngStatus, blnError, "Admin Status"
 
 Screen.MousePointer = vbHourglass
 ChangeAdminStatus lngStatus, strStatus, blnError
 'Celina Leong Phoenix1.1
 'If Ticket admin status is cancelled ticket change to View only
 ShowButtons True, True, True, True, True, True
 Screen.MousePointer = vbNormal
 
End Sub


Private Sub cmdCheckIn_Click()
 Dim strSQL    As String
 Dim strStatus As String
 Dim lngStatus As Long
 Dim blnError  As Boolean

 strSQL = "SELECT description, status FROM TicketAdminStatus ORDER BY status ASC"
 strStatus = HFlexTickets.TextMatrix(HFlexTickets.Row, mlngColAdminStatus)
 FrmList.ShowList strSQL, strStatus, lngStatus, blnError, "Admin Status"
  
 If blnError Then Exit Sub
 Screen.MousePointer = vbHourglass
 ChangeAdminStatus lngStatus, strStatus, blnError

 If Not blnError Then
    CheckIn
 End If
 ShowButtons True, True, True, True, True, True
 Screen.MousePointer = vbNormal
End Sub
Private Sub cmdCheckOut_Click()
 Screen.MousePointer = vbHourglass
 CheckOut
 
 If HFlexTickets.TextMatrix(HFlexTickets.Row, mlngColType) = "R" Then
    
    If DomainPreviouslyRegistered(HFlexTickets.TextMatrix(HFlexTickets.Row, mlngColDom)) Then
       IndicatePreviousDomainHistory (HFlexTickets.TextMatrix(HFlexTickets.Row, mlngColDom))
    End If
    
 End If

 ShowButtons True, True, True, True, True, True
 Screen.MousePointer = vbNormal
End Sub

Private Sub HFlexTickets_MouseMove(Button As Integer, Shift As Integer, x As Single, y As Single)
    Dim r As Long, c As Long
     
 '   With HFlexTickets
  '      r = .MouseRow
   '     c = .MouseCol
        
    '    If r >= 1 And ((c = mlngColDom) Or (c = mlngColHolder)) Then
                
     '       If Len(sPreviousDomainHolder(HFlexTickets.Row)) >= 0 Then
'                    .Row = r
'                    .Col = c
'                   .ToolTipText = sPreviousDomainHolder(HFlexTickets.Row)
      '       Else
'                    .ToolTipText = ""
       '     End If
        
        'End If
    'End With
    
End Sub


Private Sub cmdClose_Click()
 Unload Me
End Sub


Private Sub cmdEdit_Click()
 If mblnTicketsLoaded Then
    If mfrmEditTicketD Is Nothing Then
       Set mfrmEditTicketD = New FrmEditTicketD
    End If

     If cmdEdit.Caption = "&Edit" Then
       mfrmEditTicketD.EditTicket HFlexTickets.TextMatrix(HFlexTickets.Row, mlngColKey)
    Else
       mfrmEditTicketD.ViewTicket HFlexTickets.TextMatrix(HFlexTickets.Row, mlngColKey)
    End If
 End If
' FrmEditTicketD.LoadTicket HFlexTickets.TextMatrix(HFlexTickets.Row, mlngColKey)
End Sub


Private Sub cmdReassign_Click()
 Dim strSQL          As String
 Dim strCheckedOutTo As String
 Dim blnError        As Boolean
 
 strSQL = "SELECT Nic_Handle "
 strSQL = strSQL & "FROM Access "
 strSQL = strSQL & "WHERE (NH_Level & "
 strSQL = strSQL & atHostMaster + atLeadHostMaster + atSuperUser & ") > 0 "
 strSQL = strSQL & "ORDER BY Nic_Handle ASC"
 
 strCheckedOutTo = HFlexTickets.TextMatrix(HFlexTickets.Row, mlngColNicHandle)
 FrmList.ShowList strSQL, strCheckedOutTo, , blnError, "Nic Handle"
 
 Screen.MousePointer = vbHourglass
 Reassign strCheckedOutTo, blnError
 ShowButtons True, True, True, True, True, True
 Screen.MousePointer = vbNormal
End Sub


Private Sub cmdSearch_Click()
 Dim strWhere As String
 
 lblFaxes.Caption = ""
 
 On Error Resume Next
 Screen.MousePointer = vbHourglass
 If cboAdminStatus.ItemData(cboAdminStatus.ListIndex) <> glngAllsItemData Then
    strWhere = "Admin_Status=" & cboAdminStatus.ItemData(cboAdminStatus.ListIndex) & " AND "
 End If
 If cboTechStatus.ItemData(cboTechStatus.ListIndex) <> glngAllsItemData Then
    strWhere = strWhere & "Tech_Status=" & cboTechStatus.ItemData(cboTechStatus.ListIndex) & " AND "
 End If
 If OptDateRange.Value = True Then
' Modify by celina 19/08/2002 'Date Range search using Ticket Creation Date instead.
'    strWhere = strWhere & "T_Reg_Dt>=" & CDBDate(DTPickerFrm.Value) & " AND "
'    strWhere = strWhere & "T_Reg_Dt<=" & CDBDate(DTPickerTo.Value) & " AND "
    strWhere = strWhere & "T_Created_TS>=" & CDBDate(DTPickerFrm.Value) & " AND "
    strWhere = strWhere & "T_Created_TS<=" & CDBDate(DTPickerTo.Value) & " AND "

 End If
 If cboNicHandle.Text <> gstrComboAllText Then
    strWhere = strWhere & "CheckedOutTo=" & CDBText(cboNicHandle.Text) & " AND "
 End If
 
 'Batch 7.1 - May 2007
 If txtDomainName.Text <> "*" Then
    strWhere = strWhere & "D_Name like " & CDBText(txtDomainName.Text & "%") & " AND "
 End If
 
 If strWhere <> "" Then
    strWhere = Left(strWhere, Len(strWhere) - 5)
 End If
 
 LoadTickets strWhere
 HFlexTickets_RowColChange
 'BATCH 5.1
 FlagFaxForDomain HFlexTickets.TextMatrix(1, mlngColDom), lblFaxes, True
 
 HFlexTickets.Row = 1
 HFlexTickets.RowSel = HFlexTickets.Row
 HFlexTickets.Col = mlngColHolder
 HFlexTickets.ColSel = 1
 
 Screen.MousePointer = vbNormal
End Sub


Private Sub Form_KeyDown(KeyCode As Integer, Shift As Integer)
 If KeyCode = vbKeyF5 Then
    cmdSearch.Value = True
 End If
End Sub
Private Sub Form_Load()
 Date_Range_Enabled False
 
 Populate_Grid
 FillAdminStatusCombo cboAdminStatus, True
 FillTechStatusCombo cboTechStatus, True
 FillNicHandleCombo cboNicHandle
 DTPickerTo = Date
 DTPickerFrm = DateAdd("m", -1, Date)
 cmdSearch.Value = True
End Sub


Private Sub Form_Resize()
 CenterControls Me
End Sub


Private Sub Form_Unload(Cancel As Integer)
 On Error Resume Next
 If Not mfrmEditTicketD Is Nothing Then
    Unload mfrmEditTicketD
    Set mfrmEditTicketD = Nothing
 End If
End Sub

Private Sub HFlexTickets_DblClick()
 If cmdEdit.Enabled Then
    cmdEdit.Value = True
 End If
End Sub


Private Sub HFlexTickets_KeyDown(KeyCode As Integer, Shift As Integer)
 If KeyCode = vbKeyReturn Then
    If cmdEdit.Enabled = True Then
       cmdEdit.Value = True
    End If
 End If
End Sub


Private Sub HFlexTickets_MouseUp(Button As Integer, Shift As Integer, x As Single, y As Single)
 Static lngSortedCol As Long
 Dim lngCol  As Long
 Dim lngCols As Long
 Dim lngX    As Long
 
 If y <= HFlexTickets.RowHeight(0) Then
    lngCols = HFlexTickets.Cols - 1
    For lngCol = 0 To lngCols
        lngX = lngX + HFlexTickets.ColWidth(lngCol)
        If lngX >= x Then
           If lngCol = mlngColDate Then lngCol = mlngColDateSort
           HFlexTickets.Col = lngCol
           If lngSortedCol = lngCol Then
              HFlexTickets.Sort = 2
              lngSortedCol = -1
           Else
              HFlexTickets.Sort = 1
              lngSortedCol = lngCol
           End If
           Exit For
        End If
    Next lngCol
 End If
End Sub


Private Sub HFlexTickets_RowColChange()
 ShowButtons True, True, True, True, True, True
End Sub

Private Sub HFlexTickets_SelChange()
Dim lngTicket As Long
Dim sDom As String
'Dim sDomHolder As String

HFlexTickets.RowSel = HFlexTickets.Row
 
'JMcA BATCH 5.1
lngTicket = HFlexTickets.TextMatrix(HFlexTickets.Row, mlngColKey)

'sDomHolder = HFlexTickets.TextMatrix(HFlexTickets.Row, mlngColDHolder)
sDom = HFlexTickets.TextMatrix(HFlexTickets.Row, mlngColDom)
FlagFaxForDomain sDom, lblFaxes, True
'HFlexTickets.ToolTipText = sPreviousDomainHolder(HFlexTickets.Row)

End Sub


Private Sub mfrmEditTicketD_Accepted(ByVal lngTicketNumber As Long, ByVal strStatus As String, ByVal lngStatus As Long)
 Dim lngRow As Long
 
 lngRow = HFlexTickets.Row
 If HFlexTickets.TextMatrix(lngRow, mlngColKey) = lngTicketNumber Then
    HFlexTickets.TextMatrix(lngRow, mlngColAdminCode) = lngStatus
    HFlexTickets.TextMatrix(lngRow, mlngColAdminStatus) = strStatus
    HFlexTickets.TextMatrix(lngRow, mlngColCheckedOut) = "N"
    HFlexTickets.TextMatrix(lngRow, mlngColNicHandle) = ""
 End If
End Sub


Private Sub mfrmEditTicketD_TypeChanged(ByVal strType As String)
 HFlexTickets.TextMatrix(HFlexTickets.Row, mlngColType) = strType
End Sub


Private Sub OptAllDates_Click()
 If OptAllDates.Value = True Then
    Date_Range_Enabled False
 End If
End Sub

Private Sub OptDateRange_Click()
 If OptDateRange.Value = True Then
    Date_Range_Enabled True
 End If
End Sub


Private Sub Date_Range_Enabled(ByVal blnEnabled As Boolean)
 DTPickerFrm.Enabled = blnEnabled
 DTPickerTo.Enabled = blnEnabled
End Sub

Private Sub Populate_Grid()
 With HFlexTickets
    .ColWidth(0) = 100
    .ColWidth(1) = 0
    .ColWidth(2) = 0
    .ColWidth(3) = 1200
    .ColWidth(4) = 2505
    .ColWidth(5) = 2040
    .ColWidth(6) = 480
    .ColWidth(7) = 0
    .ColWidth(8) = 1515
    .ColWidth(9) = 0
    .ColWidth(10) = 1170
    .ColWidth(11) = 1110
    .ColWidth(12) = 435
    .ColWidth(13) = 1980 'BATCH 5
    
    .TextMatrix(0, 2) = "Date"
    .TextMatrix(0, 3) = "Account Name"
    .TextMatrix(0, 4) = "Domain Name"
    .TextMatrix(0, 5) = "Req"
    .TextMatrix(0, 6) = "Admin Code"
    .TextMatrix(0, 7) = "Admin Status"
    .TextMatrix(0, 8) = "Tech Code"
    .TextMatrix(0, 9) = "Tech Status"
    .TextMatrix(0, 10) = "Nic Handle"
    .TextMatrix(0, 12) = "Holder"
    
    
 End With
End Sub


Private Sub FillNicHandleCombo(cbo As ComboBox)
 Dim strSQL As String
 Dim rec    As ADODB.Recordset
 
 On Error GoTo FillNicHandleComboERROR
 Screen.MousePointer = vbHourglass

 strSQL = "SELECT Nic_Handle "
 strSQL = strSQL & "FROM Access "
 strSQL = strSQL & "WHERE (NH_Level & "
 strSQL = strSQL & atHostMaster + atLeadHostMaster + atSuperUser & ") > 0 "
 strSQL = strSQL & "ORDER BY Nic_Handle "
 DB.Execute strSQL, rec
 
 cbo.Clear
 
 cbo.AddItem gstrComboAllText
 cbo.ListIndex = 0
 Do Until rec.EOF
    cbo.AddItem rec.Fields(0)
    rec.MoveNext
 Loop
 Screen.MousePointer = vbNormal
Exit Sub
FillNicHandleComboERROR:
 Screen.MousePointer = vbNormal
 DB.ShowError "Unable to load Nic Handles"
End Sub


Public Sub LoadTickets(ByVal strWhere As String)
 Dim strSQL As String
 Dim rec    As ADODB.Recordset
 Dim sHolder As String
 Dim iRowIndex As Integer
 Dim iDomsIndex As Integer

 iDomsIndex = 1
 iRowIndex = 1
 
 On Error GoTo LoadTicketsERROR
 
 strSQL = "SELECT "
 strSQL = strSQL & "T_Number,  "
 strSQL = strSQL & "DATE_FORMAT(T_Created_TS,'%Y%m%d') AS 'SortDate', "
 strSQL = strSQL & FormatDBDate("T_Created_TS") & " AS 'Date', "
 strSQL = strSQL & "a.A_Name       AS 'Account Name', "
 strSQL = strSQL & "D_Name         AS 'Domain Name', "
 strSQL = strSQL & "T_Type         AS 'Req', "
 strSQL = strSQL & "Admin_Status   AS 'Admin Code', "
 strSQL = strSQL & "ax.Description AS 'Admin Status', "
 strSQL = strSQL & "Tech_Status    AS 'Tech Code', "
 strSQL = strSQL & "ts.Description AS 'Tech Status', "
 strSQL = strSQL & "CheckedOutTo   AS 'Nic Handle', "
 strSQL = strSQL & "CheckedOut     AS 'Chk', "
 strSQL = strSQL & "D_Holder       AS 'Holder'"
 
 
 strSQL = strSQL & "FROM "
 strSQL = strSQL & "  TicketAdminStatus AS ax, "
 strSQL = strSQL & "  TicketTechStatus  AS ts, Ticket "
 strSQL = strSQL & "INNER JOIN Account AS a "
 strSQL = strSQL & "ON a.A_Number = Ticket.A_Number "
 strSQL = strSQL & "WHERE ts.Status = Tech_Status "
 strSQL = strSQL & "AND   ax.Status = Admin_Status "
 If strWhere <> "" Then
   strSQL = strSQL & "AND " & strWhere
 End If
 strSQL = strSQL & " ORDER BY T_Created_TS "
 
 DB.Execute strSQL, rec

 HFlexTickets.Clear
 HFlexTickets.FixedRows = 0
 HFlexTickets.Rows = 2
 HFlexTickets.FixedRows = 1
 
 Set HFlexTickets.DataSource = rec.DataSource
 
 If HFlexTickets.Rows <= 1 Then
    HFlexTickets.Row = 0
 End If
 
 mblnTicketsLoaded = True
 ReDim sPreviousDomainHolder(1 To HFlexTickets.Rows)
  
Exit Sub
LoadTicketsERROR:
 mblnTicketsLoaded = False
 DB.ShowError "Unable to load Tickets"
End Sub


Private Sub CheckOut()
 Dim strSQL         As String
 Dim lngRow         As Long
 Dim lngRowsUpdated As Long
 
 On Error GoTo CheckOutERROR
 
 lngRow = HFlexTickets.Row
 strSQL = "UPDATE Ticket SET "
 strSQL = strSQL & "CheckedOutTo=" & CDBText(UserID) & ", "
 strSQL = strSQL & "CheckedOut=" & CDBText("Y") & " "
 strSQL = strSQL & "WHERE T_Number=" & HFlexTickets.TextMatrix(lngRow, mlngColKey) & " "
 strSQL = strSQL & "AND (CheckedOutTo IS NULL "
 strSQL = strSQL & "OR  CheckedOutTO =" & CDBText("") & " ) "
  
 strSQL = strSQL & "AND  CheckedOut=" & CDBText("N")
 DB.Execute strSQL, , , , lngRowsUpdated
 
 If lngRowsUpdated <> 1 Then
    MsgBox "Ticket Not checked out", vbCritical
 Else
    With HFlexTickets
      .TextMatrix(lngRow, mlngColNicHandle) = UserID
      .TextMatrix(lngRow, mlngColCheckedOut) = "Y"
    End With
 End If
Exit Sub
CheckOutERROR:
 DB.ShowError "Unable to Check out Ticket"
End Sub


Private Sub CheckIn()
 Dim strSQL         As String
 Dim lngRow         As Long
 Dim lngRowsUpdated As Long
 Dim rsTkt          As New ADODB.Recordset
 
 
 On Error GoTo CheckInERROR
 
 lngRow = HFlexTickets.Row
 strSQL = "UPDATE Ticket SET "
 strSQL = strSQL & "CheckedOutTo=NULL, "
 strSQL = strSQL & "CheckedOut=" & CDBText("N") & " "
 strSQL = strSQL & "WHERE T_Number=" & HFlexTickets.TextMatrix(lngRow, mlngColKey) & " "
 strSQL = strSQL & "AND  CheckedOutTo=" & CDBText(HFlexTickets.TextMatrix(lngRow, mlngColNicHandle)) & " "
 strSQL = strSQL & "AND  CheckedOut=" & CDBText("Y")
 DB.Execute strSQL, , , , lngRowsUpdated
 
 If lngRowsUpdated <> 1 Then
     'JMcA 05.12.06
     'Bug Fix - Accept button on Edit Ticket screen already checks in ticket,
     'so lngRowsUpdated will be 0 if ticket Accepted then re-checked in on Ticket screen
     
     'See if ticket has already been checked in
      strSQL = "SELECT * FROM Ticket" & _
              " WHERE CheckedOut='N'" & _
              " AND T_Number=" & HFlexTickets.TextMatrix(lngRow, mlngColKey)
    
      rsTkt.Open strSQL, CnPhoenix, adOpenDynamic, adLockOptimistic
     
      If rsTkt.EOF Then 'Already CheckedIn so some other kind of error
         MsgBox "Ticket Not checked in", vbCritical
      End If
     
      rsTkt.Close

Else
    With HFlexTickets
      .TextMatrix(lngRow, mlngColNicHandle) = ""
          
      .TextMatrix(lngRow, mlngColCheckedOut) = "N"
    End With
 End If
Exit Sub
CheckInERROR:
 DB.ShowError "Unable to Check in Ticket"
End Sub


Private Sub ChangeAdminStatus(ByVal lngStatus As Long, ByVal strStatus As String, ByRef blnError As Boolean)
 If lngStatus = 9 Then  '* Cancelled
    If MsgBox("Are you sure you want to Archive this Ticket now", vbQuestion + vbYesNo) = vbYes Then
       'DeleteTicket blnError
       'Celina Leong Phoenix1.1
       'Do not delete ticket from database Update the status only, bath job will do the delete.
       UpdateAdminStatus lngStatus, strStatus, blnError
    End If
 Else
    UpdateAdminStatus lngStatus, strStatus, blnError
 End If
End Sub


Private Sub DeleteTicket(ByRef blnError As Boolean)
 Dim lngTicketNumber As Long
 Dim strSQL          As String
 Dim lngRowsUpdated  As Long
 Dim lngRow          As Long
 
 On Error GoTo DeleteTicketERROR
 
 DB.BeginTransaction
 lngRow = HFlexTickets.Row
 lngTicketNumber = HFlexTickets.TextMatrix(lngRow, mlngColKey)
 WriteTicketHistory lngTicketNumber, mstrRemarkRemove
 
 strSQL = "DELETE FROM Ticket "
 strSQL = strSQL & "WHERE T_Number=" & lngTicketNumber
 DB.Execute strSQL, , , , lngRowsUpdated
 If lngRowsUpdated <> 1 Then
    DB.RollbackTransaction
    blnError = True
    MsgBox "Unable to Delete Ticket"
 Else
    HFlexTickets.RemoveItem lngRow
 End If
Exit Sub
DeleteTicketERROR:
 On Error Resume Next
 
 If DB.TransactionLevel > 0 Then
    DB.RollbackTransaction
 End If
 blnError = True
 DB.ShowError "Unable to Archive Ticket"
End Sub


Private Sub UpdateAdminStatus(ByVal lngStatus As Long, ByVal strStatus As String, ByRef blnError As Boolean)
 Dim strSQL          As String
 Dim lngRow          As Long
 Dim lngRowsUpdated  As Long
 Dim lngOrigStatus   As Long
 Dim lngTicketNumber As Long
 
 On Error GoTo UpdateAdminStatusERROR
 
 lngRow = HFlexTickets.Row
 lngOrigStatus = HFlexTickets.TextMatrix(lngRow, mlngColAdminCode)
 
 If lngStatus = lngOrigStatus Then
    blnError = False
    Exit Sub
 End If
 lngTicketNumber = HFlexTickets.TextMatrix(lngRow, mlngColKey)
 
 DB.BeginTransaction
 
 strSQL = "UPDATE Ticket SET "
 strSQL = strSQL & "Admin_Status=" & lngStatus & ", "
 strSQL = strSQL & "Ad_StatusDt=NOW() "
 strSQL = strSQL & "WHERE T_Number=" & lngTicketNumber & " "
 strSQL = strSQL & "AND Admin_Status=" & lngOrigStatus
 DB.Execute strSQL, , , , lngRowsUpdated
 
 If lngRowsUpdated <> 1 Then
    DB.RollbackTransaction
    blnError = True
    MsgBox "Admin Status not updated", vbCritical
 Else
    WriteTicketHistory lngTicketNumber
    DB.CommitTransaction
    
    With HFlexTickets
      .TextMatrix(lngRow, mlngColAdminCode) = lngStatus
      .TextMatrix(lngRow, mlngColAdminStatus) = strStatus
    End With
    blnError = False
 End If
Exit Sub
UpdateAdminStatusERROR:
 On Error Resume Next
 
 If DB.TransactionLevel > 0 Then
    DB.RollbackTransaction
 End If
 blnError = True
 DB.ShowError "Unable to Update Admin Status"
End Sub


Private Sub Reassign(ByVal strCheckedOutTo As String, ByRef blnError As Boolean)
 Dim strSQL As String
 Dim lngRow         As Long
 Dim lngRowsUpdated As Long
 Dim strOrigCheckedOutTo As String
 
 On Error GoTo ReassignERROR
 
 lngRow = HFlexTickets.Row
 strOrigCheckedOutTo = HFlexTickets.TextMatrix(lngRow, mlngColNicHandle)
 
 If strCheckedOutTo = strOrigCheckedOutTo Then
    blnError = False
    Exit Sub
 End If
 
 strSQL = "UPDATE Ticket SET "
 strSQL = strSQL & "CheckedOutTo=" & CDBText(strCheckedOutTo) & " "
 strSQL = strSQL & "WHERE T_Number=" & HFlexTickets.TextMatrix(lngRow, mlngColKey) & " "
 strSQL = strSQL & "AND CheckedOutTo=" & CDBText(strOrigCheckedOutTo)
 DB.Execute strSQL, , , , lngRowsUpdated
 
 If lngRowsUpdated <> 1 Then
    MsgBox "Checked Out To not updated", vbCritical
 Else
    With HFlexTickets
      .TextMatrix(lngRow, mlngColNicHandle) = strCheckedOutTo
    End With
 End If
 blnError = False
Exit Sub
ReassignERROR:
 blnError = True
 DB.ShowError "Unable to Update Checked Out To"
End Sub

Private Sub ShowButtons(ByVal blnCheckOut As Boolean, _
                        ByVal blnCheckIn As Boolean, _
                        ByVal blnAlerStatus As Boolean, _
                        ByVal blnReassign As Boolean, _
                        ByVal blnEdit As Boolean, _
                        ByVal blnClose As Boolean)
 
 Dim blnAllowed          As Boolean
 Dim blnLeadAllowed      As Boolean
 Dim blnCheckedOut       As Boolean
 Dim blnCheckedOutToUser As Boolean
 Dim blnRowSelectd       As Boolean
 Dim lngRow              As Long
 Dim blnAdminStatus      As Boolean
 
 blnAllowed = HasAuthority(atHostMaster + atLeadHostMaster)
 blnLeadAllowed = HasAuthority(atLeadHostMaster)
 lngRow = HFlexTickets.Row
 If lngRow > 0 Then
    blnCheckedOut = (HFlexTickets.TextMatrix(lngRow, mlngColCheckedOut) = "Y")
    blnCheckedOutToUser = (HFlexTickets.TextMatrix(lngRow, mlngColNicHandle) = UserID)
    blnRowSelectd = True
 Else
    blnRowSelectd = False
 End If

 cmdCheckOut.Enabled = blnCheckOut And blnRowSelectd And blnAllowed And (Not blnCheckedOut)
 cmdCheckIn.Enabled = blnCheckIn And blnRowSelectd And blnAllowed And blnCheckedOutToUser And blnCheckedOut
 cmdAlterStatus.Enabled = blnAlerStatus And blnRowSelectd And blnCheckedOutToUser
 cmdReassign.Enabled = blnReassign And blnRowSelectd And blnCheckedOut And blnLeadAllowed
 cmdClose.Enabled = blnClose
 
 If blnRowSelectd And (Not blnCheckedOutToUser) And HasAuthority(atSuperUser) Then
    cmdEdit.Caption = "Vi&ew"
    cmdEdit.Enabled = True
 Else
    cmdEdit.Caption = "&Edit"
    cmdEdit.Enabled = blnEdit And blnRowSelectd And blnCheckedOutToUser And blnCheckedOut
 End If
 
'Celina Leong Phoenix1.1
'If Ticket admin status is cancelled ticket change to View only
' blnAdminStatus = (HFlexTickets.TextMatrix(lngRow, mlngColAdminStatus) = "Cancelled")
' If blnRowSelectd And (Not blnAdminStatus) And HasAuthority(atSuperUser) Then
'    cmdEdit.Caption = "&Edit"
'    cmdEdit.Enabled = blnEdit And blnRowSelectd And blnCheckedOutToUser And blnCheckedOut
' Else
'    cmdEdit.Caption = "Vi&ew"
'    cmdEdit.Enabled = True
'     End If
'-----------------------------------------------------------------
''Celina Leong Phoenix1.1 24/04/03
If blnRowSelectd And blnAdminStatus And HasAuthority(atSuperUser) Then
     cmdEdit.Caption = "Vi&ew"
     cmdEdit.Enabled = True
End If
'--------------------------------------------------------------------
 cmdClose.Enabled = blnClose
End Sub
Private Sub WriteTicketHistory(ByVal lngTicketNumber As Long, Optional ByVal strRemark As String = "")
 Dim strSQL          As String
 
' lngTicketNumber = HFlexTickets.TextMatrix(HFlexTickets.Row, mlngColKey)
 strSQL = "INSERT INTO TicketHist ("
 strSQL = strSQL & "Chng_Dt, "
 strSQL = strSQL & "Chng_NH, "
 strSQL = strSQL & "T_Number, T_Type, D_Name, DN_Fail_Cd, D_Holder, DH_Fail_Cd, "
 strSQL = strSQL & "A_Number, AC_Fail_Cd, T_Class, T_Category, T_Remark, "
 strSQL = strSQL & "Admin_NH1, ANH1_Fail_Cd, Admin_NH2, ANH2_Fail_Cd, "
 strSQL = strSQL & "Tech_NH, TNH_Fail_Cd, Bill_NH, BNH_Fail_Cd, Creator_NH, "
 strSQL = strSQL & "DNS_Name1, DNS1_Fail_Cd, DNS_IP1, DNSIP1_Fail_Cd, "
 strSQL = strSQL & "DNS_Name2, DNS2_Fail_Cd, DNS_IP2, DNSIP2_Fail_Cd, "
 strSQL = strSQL & "DNS_Name3, DNS3_Fail_Cd, DNS_IP3, DNSIP3_Fail_Cd, "
 strSQL = strSQL & "Admin_Status, Ad_StatusDt, Tech_Status, T_Status_Dt, "
 strSQL = strSQL & "CheckedOut, CheckedOutTo, T_Reg_Dt, T_Ren_Dt, T_TStamp,"
 strSQL = strSQL & "H_Remark) "
 
 strSQL = strSQL & "SELECT DISTINCT " 'CC20051221 fix for MySQLv4.0
 strSQL = strSQL & "NOW(), "
 strSQL = strSQL & CDBText(UserID) & ", "
 strSQL = strSQL & "T_Number, T_Type, D_Name, DN_Fail_Cd, D_Holder, DH_Fail_Cd, "
 strSQL = strSQL & "A_Number, AC_Fail_Cd, T_Class, T_Category, T_Remark, "
 strSQL = strSQL & "Admin_NH1, ANH1_Fail_Cd, Admin_NH2, ANH2_Fail_Cd, "
 strSQL = strSQL & "Tech_NH, TNH_Fail_Cd, Bill_NH, BNH_Fail_Cd, Creator_NH, "
 strSQL = strSQL & "DNS_Name1, DNS1_Fail_Cd, DNS_IP1, DNSIP1_Fail_Cd, "
 strSQL = strSQL & "DNS_Name2, DNS2_Fail_Cd, DNS_IP2, DNSIP2_Fail_Cd, "
 strSQL = strSQL & "DNS_Name3, DNS3_Fail_Cd, DNS_IP3, DNSIP3_Fail_Cd, "
 strSQL = strSQL & "Admin_Status, Ad_StatusDt, Tech_Status, T_Status_Dt, "
 strSQL = strSQL & "CheckedOut, CheckedOutTo, T_Reg_Dt, T_Ren_Dt, T_TStamp,"
 If strRemark = "" Then
    strSQL = strSQL & "H_Remark "
 Else
    strSQL = strSQL & CDBText(strRemark) & " "
 End If
 strSQL = strSQL & "FROM Ticket "
 strSQL = strSQL & "WHERE T_Number=" & lngTicketNumber
 DB.Execute strSQL
End Sub
'May 2007 - Batch 7.2
Function DomainPreviouslyRegistered(sDomain As String)

Dim sSQL As String
Dim rsResult As ADODB.Recordset

On Error GoTo DPR_ERROR

    DomainPreviouslyRegistered = False

    sPreviousDomainHolder(HFlexTickets.Row) = ""
    
    sSQL = "Select D_Holder from DomainHist " & _
       "Where D_Name = '" & sDomain & "'"

    Set rsResult = New ADODB.Recordset
    DB.Execute sSQL, rsResult
            
    If Not rsResult.EOF Then
       DomainPreviouslyRegistered = True
       sPreviousDomainHolder(HFlexTickets.Row) = "Previously Registered To : " & rsResult("D_Holder")
    End If
    
    Set rsResult = Nothing

Exit Function

DPR_ERROR:
 DB.ShowError "Unable to check Domain History"
 
End Function

Sub IndicatePreviousDomainHistory(sDomain As String)
Dim sSQL, sRemark As String
Dim rsResult As ADODB.Recordset


    ' Get previous Ticket remark
    sSQL = "Select T_Remark from Ticket " & _
           "Where D_Name = '" & sDomain & "'"

    Set rsResult = New ADODB.Recordset
    DB.Execute sSQL, rsResult
            
            
    'Prepend new flag indicator and update
    If Not rsResult.EOF Then
        
        sRemark = rsResult("T_Remark")
        sRemark = "****  " & sPreviousDomainHolder(HFlexTickets.Row) & "  ****  " & vbCrLf & sRemark
        
        sSQL = "UPDATE Ticket SET T_Remark = '" & sRemark & "'" & _
              " WHERE D_Name = '" & sDomain & "'"
        
        DB.Execute sSQL
        
    End If
    
    Set rsResult = Nothing
        
    'Visual indicator when user hovers over cell
    With HFlexTickets
        
        .Col = mlngColDom
        .CellForeColor = lCellForeColor_PreviousDomainHolder
        .CellBackColor = lCellBackColor_PreviousDomainHolder
        .ToolTipText = sPreviousDomainHolder(HFlexTickets.Row)
        
        .Col = mlngColHolder
        .CellForeColor = lCellForeColor_PreviousDomainHolder
        .CellBackColor = lCellBackColor_PreviousDomainHolder
        .ToolTipText = sPreviousDomainHolder(HFlexTickets.Row)
        
    End With
     
    'MsgBox sPreviousDomainHolder(HFlexTickets.Row), vbInformation, "Domain History"
    

End Sub


