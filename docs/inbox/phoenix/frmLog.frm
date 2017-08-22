VERSION 5.00
Begin VB.Form frmLog 
   BorderStyle     =   3  'Fixed Dialog
   Caption         =   "Form1"
   ClientHeight    =   4740
   ClientLeft      =   45
   ClientTop       =   330
   ClientWidth     =   6390
   LinkTopic       =   "Form1"
   MaxButton       =   0   'False
   ScaleHeight     =   4740
   ScaleWidth      =   6390
   StartUpPosition =   2  'CenterScreen
   Begin VB.ListBox lstLog 
      Height          =   4545
      Left            =   120
      TabIndex        =   0
      Top             =   120
      Width           =   6135
   End
End
Attribute VB_Name = "frmLog"
Attribute VB_GlobalNameSpace = False
Attribute VB_Creatable = False
Attribute VB_PredeclaredId = True
Attribute VB_Exposed = False
Option Explicit

Dim mblnUnload    As Boolean
Dim mblnWriteFile As Boolean
Dim mlngFileNo    As Long


Public Sub LogItem(ByVal strLogItem As String, Optional ByVal blnHidden As Boolean = False)
 If Not blnHidden Then
    lstLog.AddItem strLogItem
    lstLog.Refresh
 End If
 WrileItem strLogItem
End Sub

Public Sub ReplaceLastItem(ByVal strLogItem As String, Optional ByVal blnWriteToLog As Boolean = False)
 If blnWriteToLog Then
    WrileItem strLogItem
 End If
 lstLog.RemoveItem lstLog.ListCount - 1
 lstLog.AddItem strLogItem
 lstLog.Refresh
End Sub


Public Sub ClearLog()
 lstLog.Clear
End Sub


Public Sub ShowLog(Optional ByVal strFileName As String = "")
 If strFileName <> "" Then
    mlngFileNo = FreeFile
    Open strFileName For Output As mlngFileNo
    mblnWriteFile = True
 End If
 
 Me.Show
 DoEvents
End Sub


Public Sub AllowedToClose()
 mblnUnload = True
End Sub


Public Sub UnloadLog()
 mblnUnload = True
 Unload Me
End Sub


Private Sub Form_Paint()
 DoEvents
End Sub

Private Sub Form_QueryUnload(Cancel As Integer, UnloadMode As Integer)
 Cancel = Not mblnUnload
End Sub


Private Sub WrileItem(ByVal strItem As String)
 If mblnWriteFile Then
    Print #mlngFileNo, strItem
 End If
End Sub


Private Sub Form_Unload(Cancel As Integer)
 If mblnWriteFile Then
    Close mlngFileNo
 End If
End Sub
