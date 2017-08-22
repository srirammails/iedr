VERSION 5.00
Object = "{0ECD9B60-23AA-11D0-B351-00A0C9055D8E}#6.0#0"; "mshflxgd.ocx"
Object = "{86CF1D34-0C5F-11D2-A9FC-0000F8754DA1}#2.0#0"; "mscomct2.ocx"
Begin VB.Form FrmTicketHis 
   Caption         =   "Ticket History"
   ClientHeight    =   7395
   ClientLeft      =   60
   ClientTop       =   345
   ClientWidth     =   11835
   HelpContextID   =   5000
   KeyPreview      =   -1  'True
   LinkTopic       =   "Form1"
   LockControls    =   -1  'True
   MDIChild        =   -1  'True
   ScaleHeight     =   7395
   ScaleWidth      =   11835
   WindowState     =   2  'Maximized
   Begin VB.Frame FraCurTicket 
      Caption         =   "Current Ticket History"
      ForeColor       =   &H00000000&
      Height          =   3615
      Left            =   360
      TabIndex        =   18
      Top             =   2520
      Width           =   11295
      Begin VB.PictureBox HFlexTickets 
         Height          =   2895
         Left            =   240
         ScaleHeight     =   2835
         ScaleWidth      =   10875
         TabIndex        =   0
         Top             =   240
         Width           =   10935
         Begin MSHierarchicalFlexGridLib.MSHFlexGrid HFlexTicketsHis 
            Height          =   2895
            Left            =   0
            TabIndex        =   25
            Top             =   0
            Width           =   10935
            _ExtentX        =   19288
            _ExtentY        =   5106
            _Version        =   393216
            Cols            =   13
            BackColorFixed  =   16744576
            BackColorSel    =   16761024
            FocusRect       =   0
            FillStyle       =   1
            SelectionMode   =   1
            AllowUserResizing=   1
            _NumberOfBands  =   1
            _Band(0).Cols   =   13
         End
      End
   End
   Begin VB.Frame FraOption 
      Caption         =   "Options"
      ForeColor       =   &H00000000&
      Height          =   735
      Left            =   360
      TabIndex        =   17
      Top             =   6600
      Width           =   11295
      Begin VB.CommandButton cmdClose 
         Caption         =   "&Close"
         Height          =   375
         Left            =   7680
         TabIndex        =   6
         Top             =   240
         Width           =   1215
      End
      Begin VB.CommandButton cmdCheckIn 
         Caption         =   "Check &In"
         Enabled         =   0   'False
         Height          =   375
         Left            =   2400
         TabIndex        =   2
         Top             =   240
         Width           =   1215
      End
      Begin VB.CommandButton cmdAlterStatus 
         Caption         =   "Alter Stat&us"
         Enabled         =   0   'False
         Height          =   375
         Left            =   3720
         TabIndex        =   3
         Top             =   240
         Width           =   1215
      End
      Begin VB.CommandButton cmdEdit 
         Caption         =   "&Edit"
         Height          =   375
         Left            =   6360
         TabIndex        =   5
         Top             =   240
         Width           =   1215
      End
      Begin VB.CommandButton cmdReassign 
         Caption         =   "&Reassign"
         Enabled         =   0   'False
         Height          =   375
         Left            =   5040
         TabIndex        =   4
         Top             =   240
         Width           =   1215
      End
      Begin VB.CommandButton cmdCheckOut 
         Caption         =   "Check &Out"
         Enabled         =   0   'False
         Height          =   375
         Left            =   1080
         TabIndex        =   1
         Top             =   240
         Width           =   1215
      End
   End
   Begin VB.Frame FraSearchCriteria 
      Caption         =   "Search Criteria"
      ForeColor       =   &H00000000&
      Height          =   1935
      Left            =   360
      TabIndex        =   14
      Top             =   360
      Width           =   11295
      Begin VB.Frame FraDateRange 
         Caption         =   "Date Range"
         ForeColor       =   &H00000000&
         Height          =   1455
         Left            =   480
         TabIndex        =   20
         Top             =   360
         Width           =   4935
         Begin VB.Frame FraRanges 
            Height          =   975
            Left            =   2160
            TabIndex        =   21
            Top             =   360
            Width           =   2295
            Begin MSComCtl2.DTPicker DTPickerFrom 
               Height          =   375
               Left            =   840
               TabIndex        =   24
               Top             =   240
               Width           =   1335
               _ExtentX        =   2355
               _ExtentY        =   661
               _Version        =   393216
               Format          =   20381697
               CurrentDate     =   37715
            End
            Begin VB.PictureBox DTPickerTo 
               Enabled         =   0   'False
               Height          =   315
               Left            =   840
               ScaleHeight     =   255
               ScaleWidth      =   1275
               TabIndex        =   9
               Top             =   600
               Width           =   1335
               Begin MSComCtl2.DTPicker DTPicker_To 
                  Height          =   255
                  Left            =   0
                  TabIndex        =   26
                  Top             =   0
                  Width           =   1335
                  _ExtentX        =   2355
                  _ExtentY        =   450
                  _Version        =   393216
                  Format          =   20381697
                  CurrentDate     =   37715
               End
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
               TabIndex        =   23
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
               TabIndex        =   22
               Top             =   600
               Width           =   495
            End
         End
         Begin VB.OptionButton OptAllDates 
            Caption         =   "All &Dates"
            Height          =   255
            Left            =   240
            TabIndex        =   7
            Top             =   480
            Value           =   -1  'True
            Width           =   1095
         End
         Begin VB.OptionButton OptDateRange 
            Caption         =   "Date Ran&ge"
            Height          =   255
            Left            =   240
            TabIndex        =   8
            Top             =   960
            Width           =   1455
         End
      End
      Begin VB.ComboBox cboTechStatus 
         Height          =   315
         ItemData        =   "FrmTicketHis.frx":0000
         Left            =   8400
         List            =   "FrmTicketHis.frx":0002
         Style           =   2  'Dropdown List
         TabIndex        =   11
         Top             =   600
         Width           =   1935
      End
      Begin VB.CommandButton cmdSearch 
         Caption         =   "&Search"
         Height          =   375
         Left            =   8400
         TabIndex        =   13
         Top             =   1200
         Width           =   1215
      End
      Begin VB.ComboBox cboNicHandle 
         Height          =   315
         ItemData        =   "FrmTicketHis.frx":0004
         Left            =   6000
         List            =   "FrmTicketHis.frx":001D
         Style           =   2  'Dropdown List
         TabIndex        =   12
         Top             =   1200
         Width           =   1935
      End
      Begin VB.ComboBox cboAdminStatus 
         Height          =   315
         ItemData        =   "FrmTicketHis.frx":0096
         Left            =   6000
         List            =   "FrmTicketHis.frx":0098
         Style           =   2  'Dropdown List
         TabIndex        =   10
         Top             =   600
         Width           =   1935
      End
      Begin VB.Label Label1 
         Caption         =   "Tech Status"
         Height          =   255
         Left            =   8400
         TabIndex        =   19
         Top             =   360
         Width           =   1575
      End
      Begin VB.Label LblAssignedTo 
         Caption         =   "NIC Handle"
         Height          =   255
         Left            =   5880
         TabIndex        =   16
         Top             =   960
         Width           =   1455
      End
      Begin VB.Label LblStatus 
         Caption         =   "Admin Status"
         Height          =   255
         Left            =   5880
         TabIndex        =   15
         Top             =   360
         Width           =   1575
      End
   End
End
Attribute VB_Name = "FrmTicketHis"
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
Const mlngColType        As Long = 6
Const mlngColAdminCode   As Long = 7
Const mlngColAdminStatus As Long = 8
Const mlngColNicHandle   As Long = 11
Const mlngColCheckedOut  As Long = 12

Const mstrRemarkRemove   As String = "Admin status set to Cancelled - Ticket Deleted"
''Celina Leong Phoenix1.1 04/04/03
Const TempStatus  As Long = 9

Dim WithEvents mfrmEditTicketD As FrmEditTicketD
Attribute mfrmEditTicketD.VB_VarHelpID = -1
Dim mblnTicketsLoaded    As Boolean






Private Sub cmdAlterStatus_Click()
 Dim strSQL    As String
 Dim strStatus As String
 Dim lngStatus As Long
 Dim blnError  As Boolean
 
 strSQL = "SELECT description, status FROM TicketAdminStatus ORDER BY status ASC"
 strStatus = HFlexTicketsHis.TextMatrix(HFlexTicketsHis.Row, mlngColAdminStatus)
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
 strStatus = HFlexTicketsHis.TextMatrix(HFlexTicketsHis.Row, mlngColAdminStatus)
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
 ShowButtons True, True, True, True, True, True
 Screen.MousePointer = vbNormal
End Sub


Private Sub CmdClose_Click()
 Unload Me
End Sub


Private Sub CmdEdit_Click()
 If mblnTicketsLoaded Then
    If mfrmEditTicketD Is Nothing Then
       Set mfrmEditTicketD = New FrmEditTicketD
    End If

     If cmdEdit.Caption = "&Edit" Then
       mfrmEditTicketD.EditTicket HFlexTicketsHis.TextMatrix(HFlexTicketsHis.Row, mlngColKey)
    Else
    'Celina Leong Phoenix1.1 04/04/03
       mfrmEditTicketD.Caption = "View Ticket History"
       mfrmEditTicketD.ViewTicket HFlexTicketsHis.TextMatrix(HFlexTicketsHis.Row, mlngColKey)
    End If
 End If
' FrmEditTicketD.LoadTicket HFlexTicketsHis.TextMatrix(HFlexTicketsHis.Row, mlngColKey)
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
 
 strCheckedOutTo = HFlexTicketsHis.TextMatrix(HFlexTicketsHis.Row, mlngColNicHandle)
 FrmList.ShowList strSQL, strCheckedOutTo, , blnError, "Nic Handle"
 
 Screen.MousePointer = vbHourglass
 Reassign strCheckedOutTo, blnError
 ShowButtons True, True, True, True, True, True
 Screen.MousePointer = vbNormal
End Sub


Private Sub cmdSearch_Click()
 Dim strWhere As String
 
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
'    strWhere = strWhere & "T_Reg_Dt<=" & CDBDate(DTPicker_To.Value) & " AND "
    strWhere = strWhere & "T_Created_TS>=" & CDBDate(DTPickerFrom.Value) & " AND "
    strWhere = strWhere & "T_Created_TS<=" & CDBDate(DTPicker_To.Value) & " AND "

 End If
 If cboNicHandle.Text <> gstrComboAllText Then
    strWhere = strWhere & "CheckedOutTo=" & CDBText(cboNicHandle.Text) & " AND "
 End If
 
 If strWhere <> "" Then
    strWhere = Left(strWhere, Len(strWhere) - 5)
 End If
 
 LoadTickets strWhere
 HFlexTicketsHis_RowColChange
 Screen.MousePointer = vbNormal
End Sub


Private Sub DTPicker1_CallbackKeyDown(ByVal KeyCode As Integer, ByVal Shift As Integer, ByVal CallbackField As String, CallbackDate As Date)

End Sub

Private Sub DTPicker_From_CallbackKeyDown(ByVal KeyCode As Integer, ByVal Shift As Integer, ByVal CallbackField As String, CallbackDate As Date)

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
 DTPicker_To = Date
 DTPickerFrom = DateAdd("m", -1, Date)
 
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






Private Sub HFlexTicketsHis_DblClick()
 If cmdEdit.Enabled Then
    cmdEdit.Value = True
 End If
End Sub


Private Sub HFlexTicketsHis_KeyDown(KeyCode As Integer, Shift As Integer)
 If KeyCode = vbKeyReturn Then
    If cmdEdit.Enabled = True Then
       cmdEdit.Value = True
    End If
 End If
End Sub


Private Sub HFlexTicketsHis_MouseUp(Button As Integer, Shift As Integer, x As Single, y As Single)
 Static lngSortedCol As Long
 Dim lngCol  As Long
 Dim lngCols As Long
 Dim lngX    As Long
 
 If y <= HFlexTicketsHis.RowHeight(0) Then
    lngCols = HFlexTicketsHis.Cols - 1
    For lngCol = 0 To lngCols
        lngX = lngX + HFlexTicketsHis.ColWidth(lngCol)
        If lngX >= x Then
           If lngCol = mlngColDate Then lngCol = mlngColDateSort
           HFlexTicketsHis.Col = lngCol
           If lngSortedCol = lngCol Then
              HFlexTicketsHis.Sort = 2
              lngSortedCol = -1
           Else
              HFlexTicketsHis.Sort = 1
              lngSortedCol = lngCol
           End If
           Exit For
        End If
    Next lngCol
 End If
End Sub


Private Sub HFlexTicketsHis_RowColChange()
 ShowButtons False, False, False, False, True, True
End Sub


Private Sub HFlexTicketsHis_SelChange()
 HFlexTicketsHis.RowSel = HFlexTicketsHis.Row
End Sub


Private Sub mfrmEditTicketD_Accepted(ByVal lngTicketNumber As Long, ByVal strStatus As String, ByVal lngStatus As Long)
 Dim lngRow As Long
 
 lngRow = HFlexTicketsHis.Row
 If HFlexTicketsHis.TextMatrix(lngRow, mlngColKey) = lngTicketNumber Then
    HFlexTicketsHis.TextMatrix(lngRow, mlngColAdminCode) = lngStatus
    HFlexTicketsHis.TextMatrix(lngRow, mlngColAdminStatus) = strStatus
    HFlexTicketsHis.TextMatrix(lngRow, mlngColCheckedOut) = "N"
    HFlexTicketsHis.TextMatrix(lngRow, mlngColNicHandle) = ""
 End If
End Sub


Private Sub mfrmEditTicketD_TypeChanged(ByVal strType As String)
 HFlexTicketsHis.TextMatrix(HFlexTicketsHis.Row, mlngColType) = strType
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
 DTPickerFrom.Enabled = blnEnabled
 DTPicker_To.Enabled = blnEnabled
End Sub


Private Sub Populate_Grid()
 With HFlexTicketsHis
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
    
    
    .TextMatrix(0, 2) = "Date"
    .TextMatrix(0, 3) = "Account Name"
    .TextMatrix(0, 4) = "Domain Name"
    .TextMatrix(0, 5) = "Req"
    .TextMatrix(0, 6) = "Admin Code"
    .TextMatrix(0, 7) = "Admin Status"
    .TextMatrix(0, 8) = "Tech Code"
    .TextMatrix(0, 9) = "Tech Status"
    .TextMatrix(0, 10) = "Nic Handle"
    .TextMatrix(0, 11) = "Chk"
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
 strSQL = strSQL & "CheckedOut     AS 'Chk' "
 
 strSQL = strSQL & "FROM "
 strSQL = strSQL & "  TicketAdminStatus AS ax, "
 strSQL = strSQL & "  TicketTechStatus  AS ts, TicketHist "
 strSQL = strSQL & "INNER JOIN AccountHist AS a "
 strSQL = strSQL & "ON a.A_Number = TicketHist.A_Number "
 strSQL = strSQL & "WHERE ts.Status = Tech_Status "
 strSQL = strSQL & "AND   ax.Status = Admin_Status "
 'celina Lwong Phoenix1.1 04/04/03
 strSQL = strSQL & "AND   Admin_Status = " & TempStatus & " "
 'strSQL = strSQL & "AND   Tech_Status = " & CDBText("Cancelled")
 '-----------------------------------------------------
 
 If strWhere <> "" Then
   strSQL = strSQL & "AND " & strWhere
 End If
 strSQL = strSQL & " ORDER BY T_Created_TS "
 
 DB.Execute strSQL, rec

 HFlexTicketsHis.Clear
 HFlexTicketsHis.FixedRows = 0
 HFlexTicketsHis.Rows = 2
 HFlexTicketsHis.FixedRows = 1
 
 Set HFlexTicketsHis.DataSource = rec.DataSource
 
 If HFlexTicketsHis.Rows <= 1 Then
    HFlexTicketsHis.Row = 0
 End If
 
 mblnTicketsLoaded = True
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
 
 lngRow = HFlexTicketsHis.Row
 strSQL = "UPDATE Ticket SET "
 strSQL = strSQL & "CheckedOutTo=" & CDBText(UserID) & ", "
 strSQL = strSQL & "CheckedOut=" & CDBText("Y") & " "
 strSQL = strSQL & "WHERE T_Number=" & HFlexTicketsHis.TextMatrix(lngRow, mlngColKey) & " "
 strSQL = strSQL & "AND  CheckedOutTo IS NULL "
 strSQL = strSQL & "AND  CheckedOut=" & CDBText("N")
 DB.Execute strSQL, , , , lngRowsUpdated
 
 If lngRowsUpdated <> 1 Then
    MsgBox "Ticket Not checked out", vbCritical
 Else
    With HFlexTicketsHis
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
    
 On Error GoTo CheckInERROR
 
 lngRow = HFlexTicketsHis.Row
 strSQL = "UPDATE Ticket SET "
 strSQL = strSQL & "CheckedOutTo=NULL, "
 strSQL = strSQL & "CheckedOut=" & CDBText("N") & " "
 strSQL = strSQL & "WHERE T_Number=" & HFlexTicketsHis.TextMatrix(lngRow, mlngColKey) & " "
 strSQL = strSQL & "AND  CheckedOutTo=" & CDBText(HFlexTicketsHis.TextMatrix(lngRow, mlngColNicHandle)) & " "
 strSQL = strSQL & "AND  CheckedOut=" & CDBText("Y")
 DB.Execute strSQL, , , , lngRowsUpdated
 
 
 If lngRowsUpdated <> 1 Then
   MsgBox "Ticket Not checked in", vbCritical
 Else
    With HFlexTicketsHis
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
 lngRow = HFlexTicketsHis.Row
 lngTicketNumber = HFlexTicketsHis.TextMatrix(lngRow, mlngColKey)
 WriteTicketHistory lngTicketNumber, mstrRemarkRemove
 
 strSQL = "DELETE FROM Ticket "
 strSQL = strSQL & "WHERE T_Number=" & lngTicketNumber
 DB.Execute strSQL, , , , lngRowsUpdated
 If lngRowsUpdated <> 1 Then
    DB.RollbackTransaction
    blnError = True
    MsgBox "Unable to Delete Ticket"
 Else
    HFlexTicketsHis.RemoveItem lngRow
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
 
 lngRow = HFlexTicketsHis.Row
 lngOrigStatus = HFlexTicketsHis.TextMatrix(lngRow, mlngColAdminCode)
 
 If lngStatus = lngOrigStatus Then
    blnError = False
    Exit Sub
 End If
 lngTicketNumber = HFlexTicketsHis.TextMatrix(lngRow, mlngColKey)
 
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
    
    With HFlexTicketsHis
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
 
 lngRow = HFlexTicketsHis.Row
 strOrigCheckedOutTo = HFlexTicketsHis.TextMatrix(lngRow, mlngColNicHandle)
 
 If strCheckedOutTo = strOrigCheckedOutTo Then
    blnError = False
    Exit Sub
 End If
 
 strSQL = "UPDATE Ticket SET "
 strSQL = strSQL & "CheckedOutTo=" & CDBText(strCheckedOutTo) & " "
 strSQL = strSQL & "WHERE T_Number=" & HFlexTicketsHis.TextMatrix(lngRow, mlngColKey) & " "
 strSQL = strSQL & "AND CheckedOutTo=" & CDBText(strOrigCheckedOutTo)
 DB.Execute strSQL, , , , lngRowsUpdated
 
 If lngRowsUpdated <> 1 Then
    MsgBox "Checked Out To not updated", vbCritical
 Else
    With HFlexTicketsHis
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
 lngRow = HFlexTicketsHis.Row
 If lngRow > 0 Then
    blnCheckedOut = (HFlexTicketsHis.TextMatrix(lngRow, mlngColCheckedOut) = "Y")
    blnCheckedOutToUser = (HFlexTicketsHis.TextMatrix(lngRow, mlngColNicHandle) = UserID)
    blnRowSelectd = True
 Else
    blnRowSelectd = False
 End If

''' cmdCheckOut.Enabled = blnCheckOut And blnRowSelectd And blnAllowed And (Not blnCheckedOut)
''' cmdCheckIn.Enabled = blnCheckIn And blnRowSelectd And blnAllowed And blnCheckedOutToUser And blnCheckedOut
''' cmdAlterStatus.Enabled = blnAlerStatus And blnRowSelectd And blnCheckedOutToUser
''' cmdReassign.Enabled = blnReassign And blnRowSelectd And blnCheckedOut And blnLeadAllowed
 cmdClose.Enabled = blnClose
 
 If blnRowSelectd Then '''And (Not blnCheckedOutToUser) And HasAuthority(atSuperUser) Then
    cmdEdit.Caption = "Vi&ew"
    cmdEdit.Enabled = True
''' Else
'''    cmdEdit.Caption = "&Edit"
'''    cmdEdit.Enabled = blnEdit And blnRowSelectd And blnCheckedOutToUser And blnCheckedOut
 End If
 
''''Celina Leong Phoenix1.1
''''If Ticket admin status is cancelled ticket change to View only
''' blnAdminStatus = (HFlexTicketsHis.TextMatrix(lngRow, mlngColAdminStatus) = "Cancelled")
''' If blnRowSelectd And (Not blnAdminStatus) And HasAuthority(atSuperUser) Then
'''    cmdEdit.Caption = "&Edit"
'''    cmdEdit.Enabled = blnEdit And blnRowSelectd And blnCheckedOutToUser And blnCheckedOut
''' Else
'''    cmdEdit.Caption = "Vi&ew"
'''    cmdEdit.Enabled = True
'''     End If
''''-----------------------------------------------------------------
 cmdClose.Enabled = blnClose
End Sub


Private Sub WriteTicketHistory(ByVal lngTicketNumber As Long, Optional ByVal strRemark As String = "")
 Dim strSQL          As String
 
' lngTicketNumber = HFlexTicketsHis.TextMatrix(HFlexTicketsHis.Row, mlngColKey)
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

