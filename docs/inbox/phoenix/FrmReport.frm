VERSION 5.00
Object = "{86CF1D34-0C5F-11D2-A9FC-0000F8754DA1}#2.0#0"; "MSCOMCT2.OCX"
Object = "{00025600-0000-0000-C000-000000000046}#4.6#0"; "CRYSTL32.OCX"
Begin VB.Form FrmReport 
   Caption         =   "Report"
   ClientHeight    =   7395
   ClientLeft      =   60
   ClientTop       =   345
   ClientWidth     =   11835
   HelpContextID   =   8000
   LinkTopic       =   "Form1"
   MDIChild        =   -1  'True
   ScaleHeight     =   7395
   ScaleWidth      =   11835
   WindowState     =   2  'Maximized
   Begin VB.Frame FraSearchCirteria 
      Caption         =   "Search Criteria"
      Height          =   1815
      Left            =   720
      TabIndex        =   6
      Top             =   4080
      Width           =   8055
      Begin VB.TextBox TxtBillingCon 
         Height          =   285
         Left            =   6360
         TabIndex        =   18
         Top             =   600
         Width           =   1455
      End
      Begin VB.TextBox TxtAccNo 
         Height          =   285
         Left            =   6360
         TabIndex        =   17
         Top             =   1080
         Width           =   1455
      End
      Begin VB.OptionButton OptAccount 
         Caption         =   "Account No"
         Height          =   255
         Left            =   4560
         TabIndex        =   16
         Top             =   1080
         Width           =   1215
      End
      Begin VB.OptionButton OptBilContact 
         Caption         =   "Billing Contact"
         Height          =   255
         Left            =   4560
         TabIndex        =   15
         Top             =   600
         Width           =   1335
      End
      Begin VB.Frame fraDateRange 
         Caption         =   "Date Range"
         ForeColor       =   &H00000000&
         Height          =   1215
         Left            =   120
         TabIndex        =   7
         Top             =   360
         Width           =   4215
         Begin VB.Frame fraFromTo 
            BeginProperty Font 
               Name            =   "MS Sans Serif"
               Size            =   8.25
               Charset         =   0
               Weight          =   700
               Underline       =   0   'False
               Italic          =   0   'False
               Strikethrough   =   0   'False
            EndProperty
            Height          =   975
            Left            =   1680
            TabIndex        =   10
            Top             =   120
            Width           =   2295
            Begin MSComCtl2.DTPicker dtpFrom 
               BeginProperty DataFormat 
                  Type            =   1
                  Format          =   "dd-MM-yy"
                  HaveTrueFalseNull=   0
                  FirstDayOfWeek  =   0
                  FirstWeekOfYear =   0
                  LCID            =   1033
                  SubFormatType   =   3
               EndProperty
               Height          =   300
               Left            =   735
               TabIndex        =   11
               Top             =   210
               Width           =   1335
               _ExtentX        =   2355
               _ExtentY        =   529
               _Version        =   393216
               CalendarTitleBackColor=   -2147483635
               CalendarTitleForeColor=   -2147483634
               Format          =   19988483
               CurrentDate     =   37356
            End
            Begin MSComCtl2.DTPicker dtpTo 
               BeginProperty DataFormat 
                  Type            =   1
                  Format          =   "d/MMM/yy"
                  HaveTrueFalseNull=   0
                  FirstDayOfWeek  =   0
                  FirstWeekOfYear =   0
                  LCID            =   1033
                  SubFormatType   =   3
               EndProperty
               Height          =   300
               Left            =   735
               TabIndex        =   12
               Top             =   570
               Width           =   1335
               _ExtentX        =   2355
               _ExtentY        =   529
               _Version        =   393216
               CalendarTitleBackColor=   -2147483635
               CalendarTitleForeColor=   -2147483634
               Format          =   19988483
               CurrentDate     =   37356
            End
            Begin VB.Label lblTo 
               Caption         =   "To"
               Height          =   255
               Left            =   240
               TabIndex        =   14
               Top             =   480
               Width           =   615
            End
            Begin VB.Label lblFrom 
               Caption         =   "From"
               Height          =   255
               Left            =   240
               TabIndex        =   13
               Top             =   240
               Width           =   615
            End
         End
         Begin VB.OptionButton optAllDates 
            Caption         =   "All &Dates"
            Height          =   195
            Left            =   240
            TabIndex        =   9
            Top             =   360
            Value           =   -1  'True
            Width           =   1335
         End
         Begin VB.OptionButton optSelDates 
            Caption         =   "Dates Ran&ge"
            Height          =   195
            Left            =   240
            TabIndex        =   8
            Top             =   720
            Width           =   1335
         End
      End
   End
   Begin Crystal.CrystalReport crpReport 
      Index           =   0
      Left            =   120
      Top             =   1080
      _ExtentX        =   741
      _ExtentY        =   741
      _Version        =   262150
      WindowControlBox=   -1  'True
      WindowMaxButton =   -1  'True
      WindowMinButton =   -1  'True
   End
   Begin VB.Frame Frame1 
      Caption         =   "Select Report"
      ForeColor       =   &H00000000&
      Height          =   3735
      Left            =   720
      TabIndex        =   5
      Top             =   240
      Width           =   8055
      Begin VB.ListBox lstReport 
         BeginProperty Font 
            Name            =   "Tahoma"
            Size            =   9.75
            Charset         =   0
            Weight          =   400
            Underline       =   0   'False
            Italic          =   0   'False
            Strikethrough   =   0   'False
         EndProperty
         ForeColor       =   &H00000000&
         Height          =   3180
         ItemData        =   "FrmReport.frx":0000
         Left            =   240
         List            =   "FrmReport.frx":001C
         TabIndex        =   0
         Top             =   240
         Width           =   7575
      End
   End
   Begin VB.Frame FraOption 
      Caption         =   "Options"
      ForeColor       =   &H00000000&
      Height          =   735
      Left            =   720
      TabIndex        =   4
      Top             =   6000
      Width           =   8055
      Begin VB.CommandButton cmdDisplay 
         Caption         =   "Displa&y"
         Default         =   -1  'True
         Height          =   375
         Left            =   1440
         TabIndex        =   1
         Top             =   240
         Width           =   1695
      End
      Begin VB.CommandButton cmdClose 
         Cancel          =   -1  'True
         Caption         =   "&Close"
         Height          =   375
         Left            =   5160
         TabIndex        =   3
         Top             =   240
         Width           =   1695
      End
      Begin VB.CommandButton cmdPrint 
         Caption         =   "&Print"
         Height          =   375
         Left            =   3360
         TabIndex        =   2
         Top             =   240
         Width           =   1575
      End
   End
End
Attribute VB_Name = "FrmReport"
Attribute VB_GlobalNameSpace = False
Attribute VB_Creatable = False
Attribute VB_PredeclaredId = True
Attribute VB_Exposed = False
Option Explicit

'*********************************************
'*                                           *
'* Written By : Brian Devlin                 *
'* Function   : Handles Reports on the system*
'*              Driven from Reports Table    *
'*********************************************
Private tx As Boolean
Private mblnReportsAvailable As Boolean
Private murfoInfo()          As urfoReportInfo

Private Type urfoReportInfo
 strReportID    As String
 strDescription As String
 strFileName    As String
 blnUsesDates   As Boolean
End Type


Private Sub CmdClose_Click()
 Unload Me
End Sub


Private Sub cmdDisplay_Click()
 RunReport True
End Sub


Private Sub CmdPrint_Click()
 RunReport False
End Sub






Private Sub Form_Load()
 SetDates
 DateRangeEnable = False
 LoadReportNames
End Sub


Private Sub SetDates()
 Dim dtmTemp As Date
 
 dtmTemp = DateSerial(Year(Now), Month(Now), 1)
 dtpFrom.Value = DateAdd("m", -1, dtmTemp)
 dtpTo.Value = dtmTemp - 1
End Sub


Private Property Let DateRangeEnable(ByVal blnNewValue As Boolean)
 fraDateRange.Enabled = blnNewValue
 optAllDates.Enabled = blnNewValue
 optSelDates.Enabled = blnNewValue
 'Celina Leong Phoenix1.1 15/04/03
 OptBilContact.Enabled = blnNewValue
 OptAccount.Enabled = blnNewValue
 TxtBillingCon.Enabled = blnNewValue
 TxtAccNo.Enabled = blnNewValue
 
' If blnNewValue = False Then
'    DateToFromSelected = False
' ElseIf optAllDates.Value = True Then
'    DateToFromSelected = False
' Else
'    DateToFromSelected = True
' End If

 If blnNewValue = False Then
    DateToFromSelected = False
     
 ElseIf optAllDates.Value = True Then
    DateToFromSelected = False
 Else
    DateToFromSelected = True
  End If
 '----------------------------------------
End Property

Private Property Get DateRangeEnable() As Boolean
 DateRangeEnable = fraDateRange.Enabled
End Property


Private Property Let DateToFromSelected(ByVal blnNewValue As Boolean)
 fraFromTo.Enabled = blnNewValue
 lblFrom.Enabled = blnNewValue
 lblTo.Enabled = blnNewValue
 dtpFrom.Enabled = blnNewValue
 dtpTo.Enabled = blnNewValue
End Property

Private Property Get DateToFromSelected() As Boolean
 DateToFromSelected = fraFromTo.Enabled
End Property


Private Sub LoadReportNames()
 Dim strSQL   As String
 Dim rec      As ADODB.Recordset
 Dim lngCount As Long
 
 On Error GoTo LoadReportsERROR
  
 strSQL = "SELECT "
 strSQL = strSQL & "R_ID, R_Description, R_File_Name, R_Uses_Date "
 strSQL = strSQL & "FROM Report "
 strSQL = strSQL & "WHERE (R_Authority & " & AuthLevel & ") > 0 "
 strSQL = strSQL & "ORDER BY R_Order"
 DB.Execute strSQL, rec

 lstReport.Clear
 
 If rec.EOF Then
    mblnReportsAvailable = False
 Else
    mblnReportsAvailable = True
 End If
 cmdDisplay.Enabled = mblnReportsAvailable
 cmdPrint.Enabled = mblnReportsAvailable
 
 Do Until rec.EOF
    ReDim Preserve murfoInfo(lngCount)
    murfoInfo(lngCount).strReportID = rec.Fields("R_ID")
    murfoInfo(lngCount).strDescription = rec.Fields("R_Description")
    murfoInfo(lngCount).strFileName = rec.Fields("R_File_Name")
    If UCase(rec.Fields("R_Uses_Date")) = "Y" Then
       murfoInfo(lngCount).blnUsesDates = True
    Else
       murfoInfo(lngCount).blnUsesDates = False
    End If
 
    lstReport.AddItem murfoInfo(lngCount).strReportID & vbTab & murfoInfo(lngCount).strDescription
    lngCount = lngCount + 1
    rec.MoveNext
 Loop
 
 If mblnReportsAvailable Then
    lstReport.ListIndex = 0
 End If
Exit Sub
LoadReportsERROR:
 DB.ShowError "Unable to load list of reports"
End Sub


Private Sub Form_Resize()
 CenterControls Me
End Sub


Private Sub Form_Unload(Cancel As Integer)
 On Error Resume Next
 Unload crpReport(1)
End Sub







Private Sub lstReport_Click()
 If lstReport.ListIndex > -1 Then
    DateRangeEnable = murfoInfo(lstReport.ListIndex).blnUsesDates
 End If
'       Celina Leong Phoenix1.1 15/04/03

 If murfoInfo(lstReport.ListIndex).blnUsesDates Then
         txtControl TxtAccNo, True
         txtControl TxtBillingCon, True
 Else
          txtControl TxtAccNo, False
          txtControl TxtBillingCon, False
 End If

 If murfoInfo(lstReport.ListIndex).strReportID = "R007" Then
          txtControl TxtAccNo, False
          txtControl TxtBillingCon, False
          Me.optAllDates.Value = False
          Me.optSelDates.Value = True
          Me.OptBilContact.Enabled = False
          Me.OptAccount.Enabled = False
                 
 End If
If murfoInfo(lstReport.ListIndex).strReportID = "R008" Then
          txtControl TxtAccNo, False
          txtControl TxtBillingCon, False
          Me.optAllDates.Value = False
          Me.optSelDates.Value = True
          Me.OptBilContact.Enabled = False
          Me.OptAccount.Enabled = False
 End If
 
End Sub


Private Sub RunReport(ByVal blnToScreen As Boolean)
 Dim strFileName As String
 
 On Error Resume Next
 Unload crpReport(1)
 On Error GoTo RunReportERROR
 Load crpReport(1)
 
 strFileName = App.Path & "\Reports\" & murfoInfo(lstReport.ListIndex).strFileName
 
 If Dir(strFileName) = "" Then
    MsgBox "Unable to locate report file : " & strFileName
 Else
    crpReport(1).ReportFileName = strFileName
    crpReport(1).Connect = DB.Connection.ConnectionString   '* Added Fix for UAT OM 15.1 [BD 5/7/02]
    crpReport(1).Formulas(0) = "CompanyName=" & CDBText(DB.StaticData(ustCompanyName))
    crpReport(1).Formulas(1) = "CompanyAddress=" & CDBText(DB.StaticData(ustCompanyAddress))
    crpReport(1).Formulas(2) = "UserID=" & CDBText(UserID)
    crpReport(1).Formulas(3) = "Phone=" & CDBText(DB.StaticData(ustPhone))
    crpReport(1).Formulas(4) = "Fax=" & CDBText(DB.StaticData(ustFax))
    crpReport(1).Formulas(5) = "Web=" & CDBText(DB.StaticData(ustWebReport))
    If murfoInfo(lstReport.ListIndex).blnUsesDates Then
       crpReport(1).Formulas(6) = "Start=" & GetStartDate()
       crpReport(1).Formulas(7) = "End=" & GetEndDate()
 
    End If
'      Celina Leong Phoenix1.1 15/04/03 allow to enter billing contact or account no
    If TxtAccNo.Text <> "" Then
        crpReport(1).Formulas(8) = "AccountNo=" & GetAccount()

    End If
    If TxtBillingCon.Text <> "" Then
        crpReport(1).Formulas(9) = "BillCon=" & GetBillCon()
    End If

     
'-----------------------------------------------------
   
    
    If blnToScreen Then
       crpReport(1).Destination = crptToWindow
       crpReport(1).WindowState = crptMaximized
       crpReport(1).WindowTitle = murfoInfo(lstReport.ListIndex).strDescription
    Else
       crpReport(1).Destination = crptToPrinter
    End If
    crpReport(1).Action = 1
 End If
 
Exit Sub
RunReportERROR:
 DB.ShowError "Unable to run report"
End Sub


Private Function GetStartDate() As String
 If DateToFromSelected Then
    GetStartDate = Format(dtpFrom, "\D\A\T\E\(YYYY,MM,DD\)")
 Else
    GetStartDate = "Date (1900,1 ,1 )"
 End If
End Function

Private Function GetEndDate() As String
 If DateToFromSelected Then
    GetEndDate = Format(dtpTo, "\D\A\T\E\(YYYY,MM,DD\)")
 Else
    GetEndDate = "Date (3000,1 ,1 )"
 End If
End Function

Private Sub lstReport_DblClick()
 If cmdDisplay.Enabled Then
    cmdDisplay.Value = True
 End If
End Sub


Private Sub OptAccount_Click()
    txtControl TxtBillingCon, False
    txtControl TxtAccNo, True
End Sub

Private Sub OptAccount_DblClick()
    OptAccount.Value = False
    txtControl TxtBillingCon, False
    txtControl TxtAccNo, False
End Sub

Private Sub OptAllDates_Click()
 DateToFromSelected = False
End Sub


Private Sub OptBilContact_Click()

    txtControl TxtAccNo, False
    txtControl TxtBillingCon, True
End Sub

Private Sub OptBilContact_DblClick()
    OptBilContact.Value = False
    txtControl TxtBillingCon, False
    txtControl TxtAccNo, False
End Sub

Private Sub optSelDates_Click()
 DateToFromSelected = True
End Sub

'Celina Leong Phoenix1.1 15/04/03
Private Function GetAccount() As String
         GetAccount = TxtAccNo
End Function

Private Function GetBillCon() As String
      GetBillCon = UCase(CDBText(TxtBillingCon))
End Function

'-----------------------------------------------
'Celina Leong Phoenix1.1 15/04/03
Private Function txtControl(ctl As Control, ByVal tx As Boolean)
    If tx = True Then
        ctl.Enabled = True
        ctl.BackColor = &H80000005
      
    End If
    
    If tx = False Then
        ctl.Enabled = False
        ctl.BackColor = &H8000000F
        ctl.Text = ""
      
    End If
End Function



