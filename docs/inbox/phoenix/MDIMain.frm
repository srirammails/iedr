VERSION 5.00
Object = "{831FDD16-0C5C-11D2-A9FC-0000F8754DA1}#2.0#0"; "MSCOMCTL.OCX"
Object = "{F9043C88-F6F2-101A-A3C9-08002B2F49FB}#1.2#0"; "comdlg32.ocx"
Begin VB.MDIForm MDIMain 
   BackColor       =   &H8000000C&
   Caption         =   "Phoenix Registration & Invoicing System"
   ClientHeight    =   6810
   ClientLeft      =   165
   ClientTop       =   735
   ClientWidth     =   7035
   HelpContextID   =   500
   Icon            =   "MDIMain.frx":0000
   LinkTopic       =   "MDIForm1"
   StartUpPosition =   3  'Windows Default
   WindowState     =   2  'Maximized
   Begin VB.Timer DBConnectionTimer 
      Enabled         =   0   'False
      Interval        =   60000
      Left            =   360
      Top             =   1800
   End
   Begin MSComDlg.CommonDialog dlgHelp 
      Left            =   240
      Top             =   120
      _ExtentX        =   847
      _ExtentY        =   847
      _Version        =   393216
   End
   Begin MSComctlLib.ImageList ILMain 
      Left            =   240
      Top             =   720
      _ExtentX        =   1005
      _ExtentY        =   1005
      BackColor       =   -2147483643
      ImageWidth      =   16
      ImageHeight     =   16
      MaskColor       =   12632256
      _Version        =   393216
      BeginProperty Images {2C247F25-8591-11D1-B16A-00C0F0283628} 
         NumListImages   =   5
         BeginProperty ListImage1 {2C247F27-8591-11D1-B16A-00C0F0283628} 
            Picture         =   "MDIMain.frx":0CCA
            Key             =   ""
         EndProperty
         BeginProperty ListImage2 {2C247F27-8591-11D1-B16A-00C0F0283628} 
            Picture         =   "MDIMain.frx":0FE4
            Key             =   ""
         EndProperty
         BeginProperty ListImage3 {2C247F27-8591-11D1-B16A-00C0F0283628} 
            Picture         =   "MDIMain.frx":113E
            Key             =   ""
         EndProperty
         BeginProperty ListImage4 {2C247F27-8591-11D1-B16A-00C0F0283628} 
            Picture         =   "MDIMain.frx":1298
            Key             =   ""
         EndProperty
         BeginProperty ListImage5 {2C247F27-8591-11D1-B16A-00C0F0283628} 
            Picture         =   "MDIMain.frx":15B2
            Key             =   ""
         EndProperty
      EndProperty
   End
   Begin VB.Menu MnuFile 
      Caption         =   "&File"
      Begin VB.Menu MnuPrint 
         Caption         =   "&Print"
         Visible         =   0   'False
      End
      Begin VB.Menu MnuLogoff 
         Caption         =   "&Logoff"
      End
      Begin VB.Menu MnuSpace1 
         Caption         =   "-"
      End
      Begin VB.Menu MnuExit 
         Caption         =   "E&xit"
      End
   End
   Begin VB.Menu MnuEdit 
      Caption         =   "&Edit"
      Visible         =   0   'False
      Begin VB.Menu MnuCut 
         Caption         =   "Cu&t"
      End
      Begin VB.Menu MnuCopy 
         Caption         =   "&Copy"
      End
      Begin VB.Menu MnuPaste 
         Caption         =   "&Paste"
      End
      Begin VB.Menu Space3 
         Caption         =   "-"
      End
      Begin VB.Menu MnuSelectAll 
         Caption         =   "Select &All"
      End
   End
   Begin VB.Menu MnuProcess 
      Caption         =   "&Process"
      Begin VB.Menu MnuDomains 
         Caption         =   "&Domains"
      End
      Begin VB.Menu MnuNICHandles 
         Caption         =   "&NIC Handles"
      End
      Begin VB.Menu MnuAccount 
         Caption         =   "&Accounts"
      End
      Begin VB.Menu MnuTicket 
         Caption         =   "&Tickets"
      End
      Begin VB.Menu MnuInvoice 
         Caption         =   "&Invoices"
      End
      Begin VB.Menu MnuAdmin 
         Caption         =   "A&dmin"
      End
   End
   Begin VB.Menu MnuView 
      Caption         =   "&View"
      Begin VB.Menu MnuDomainHistory 
         Caption         =   "&Domain History"
      End
      Begin VB.Menu MnuTicketHistory 
         Caption         =   "&Ticket History"
      End
   End
   Begin VB.Menu MnuReports 
      Caption         =   "&Reports"
   End
   Begin VB.Menu MnuWindow 
      Caption         =   "&Window"
      WindowList      =   -1  'True
   End
   Begin VB.Menu MnuHelp 
      Caption         =   "&Help"
      Begin VB.Menu MnuHelpContents 
         Caption         =   "&Contents"
      End
      Begin VB.Menu MnuHelpIndex 
         Caption         =   "&Index"
      End
      Begin VB.Menu MnuHelpSearch 
         Caption         =   "&Search"
      End
      Begin VB.Menu MnuSpace2 
         Caption         =   "-"
      End
      Begin VB.Menu MnuAbout 
         Caption         =   "&About Phoenix System"
      End
   End
End
Attribute VB_Name = "MDIMain"
Attribute VB_GlobalNameSpace = False
Attribute VB_Creatable = False
Attribute VB_PredeclaredId = True
Attribute VB_Exposed = False
Option Explicit


Private Sub MDIForm_Load()
 ShowMenus
 'Modified by Celina  27/08/02
 'the following line of code is to load background picture  of the MDI form
'' MDIBackground.InitializePaintBackground hWnd
 'FrmBackground.Show
'Load FrmLogo
'FrmLogo.Enabled = False


  DBConnectionTimer.Enabled = True
  'One Minute (Max in VB is 65000) - Needs to be 15 minutes see below
  DBConnectionTimer.Interval = 60000
  
End Sub

Private Sub DBConnectionTimer_Timer()
  Static iMinutes As Integer
  
  iMinutes = iMinutes + 1
  
  If iMinutes = g_iReconnectInterval Then
    
    CnPhoenix.Close
    Set CnPhoenix = Nothing
    DB.CloseConnection
    iMinutes = 0
    
    DB.OpenConnection
    Set CnPhoenix = DB.Connection

  End If
  
  
End Sub


Private Sub MDIForm_QueryUnload(Cancel As Integer, UnloadMode As Integer)
 If MsgBox("Are you sure you want to close the " & _
           "Phoenix system?", vbQuestion + vbYesNo) = vbNo Then
    Cancel = True
 End If
End Sub


Private Sub MDIForm_Resize()
 'FrmBackground.Move (MDIMain.ScaleWidth / 2) - (FrmBackground.ScaleWidth / 2), (MDIMain.ScaleHeight / 2) - (FrmBackground.ScaleHeight / 2)
End Sub

Private Sub MDIForm_Unload(Cancel As Integer)
 CnPhoenix.Close
 Set CnPhoenix = Nothing
 DB.CloseConnection
 'Modified by Celina  27/08/02
 'the following line of code is to unload background picture  of the MDI form

'' MDIBackground.TerminatePaintMDIBackground
 
End Sub

Private Sub MnuAbout_Click()
 FrmAbout.Show vbModal
' On Error Resume Next
' FrmAbout.SetFocus
End Sub

Private Sub MnuAccount_Click()
 FrmAccount.Show
 On Error Resume Next
 FrmAccount.SetFocus
End Sub

Private Sub MnuAdmin_Click()
 FrmAdmin.Show
 On Error Resume Next
 FrmAdmin.SetFocus
End Sub

'Celina Leong 04/04/03
Private Sub MnuDomainHistory_Click()
 FrmDomainsHis.Show
 On Error Resume Next
 FrmDomainsHis.SetFocus
End Sub
'-------------------------------------

Private Sub MnuDomains_Click()
 FrmDomain.Show
 On Error Resume Next
 FrmDomain.SetFocus
End Sub

Private Sub MnuExit_Click()
 Unload Me
End Sub



Private Sub MnuHelpContents_Click()
 On Error Resume Next
  
 dlgHelp.HelpFile = App.HelpFile
 dlgHelp.HelpCommand = &HB Or cdlHelpSetContents
 dlgHelp.ShowHelp
End Sub

Private Sub MnuHelpIndex_Click()
 On Error Resume Next
 
 dlgHelp.HelpFile = App.HelpFile
 dlgHelp.HelpCommand = cdlHelpPartialKey
 dlgHelp.ShowHelp
End Sub


Private Sub MnuInvoice_Click()
 FrmInvoices.Show
 On Error Resume Next
 FrmInvoices.SetFocus
End Sub


Private Sub MnuLogoff_Click()
 Dim blnValid As Boolean
 
 FrmLogin.VisualLogIn blnValid
 If Not blnValid Then
    Unload Me
 Else
    ShowMenus
 End If
End Sub

Private Sub MnuNICHandles_Click()
 FrmNicHandle.Show
 On Error Resume Next
 FrmNicHandle.SetFocus
End Sub



Private Sub MnuReports_Click()
 FrmReport.Show
 On Error Resume Next
 FrmReport.SetFocus
End Sub

Private Sub MnuTicket_Click()
 FrmTicket.Show
 On Error Resume Next
 FrmTicket.SetFocus
End Sub


Private Sub ShowMenus()
 MnuDomains.Enabled = HasAuthority(atCustomerServ + atAccounts + atHostMaster + atLeadHostMaster)
 MnuNICHandles.Enabled = HasAuthority(atCustomerServ + atAccounts + atHostMaster + atLeadHostMaster)
 MnuAccount.Enabled = HasAuthority(atCustomerServ + atAccounts + atHostMaster + atLeadHostMaster)
 MnuTicket.Enabled = HasAuthority(atCustomerServ + atHostMaster + atLeadHostMaster)
 MnuInvoice.Enabled = HasAuthority(atAccounts)
 MnuAdmin.Enabled = HasAuthority(atSuperUser)
 MnuReports.Enabled = HasAuthority(atCustomerServ + atAccounts + atHostMaster + atLeadHostMaster)
'Celina Leong Phoenix1.1 04/04/03
MnuDomainHistory.Enabled = HasAuthority(atCustomerServ + atAccounts + atHostMaster + atLeadHostMaster)
MnuTicketHistory.Enabled = HasAuthority(atCustomerServ + atHostMaster + atLeadHostMaster)
'--------------------------------------------------------------------------------------------------
End Sub
'Celina Leong Phoenix1.1 04/04/03
Private Sub MnuTicketHistory_Click()
 FrmTicketHis.Show
 On Error Resume Next
 FrmTicketHis.SetFocus
End Sub
'-----------------------------------
