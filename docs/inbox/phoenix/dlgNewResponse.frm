VERSION 5.00
Begin VB.Form dlgNewResponse 
   BorderStyle     =   3  'Fixed Dialog
   Caption         =   "New Ticket Response"
   ClientHeight    =   3660
   ClientLeft      =   2760
   ClientTop       =   3750
   ClientWidth     =   5205
   LinkTopic       =   "Form1"
   MaxButton       =   0   'False
   MinButton       =   0   'False
   ScaleHeight     =   3660
   ScaleWidth      =   5205
   ShowInTaskbar   =   0   'False
   StartUpPosition =   2  'CenterScreen
   Begin VB.Frame Frame1 
      Caption         =   " Ticket Response Details "
      Height          =   2655
      Left            =   240
      TabIndex        =   2
      Top             =   240
      Width           =   4695
      Begin VB.TextBox txtResponse 
         Height          =   1095
         Left            =   240
         MultiLine       =   -1  'True
         ScrollBars      =   2  'Vertical
         TabIndex        =   6
         Top             =   1320
         Width           =   4095
      End
      Begin VB.TextBox txtTitle 
         Height          =   285
         Left            =   240
         TabIndex        =   5
         Top             =   600
         Width           =   4095
      End
      Begin VB.Label lblResponse 
         Caption         =   "Response"
         Height          =   255
         Left            =   240
         TabIndex        =   4
         Top             =   1080
         Width           =   975
      End
      Begin VB.Label lblTitle 
         Caption         =   "Title"
         Height          =   255
         Left            =   240
         TabIndex        =   3
         Top             =   360
         Width           =   615
      End
   End
   Begin VB.CommandButton CancelButton 
      Caption         =   "&Cancel"
      Height          =   375
      Left            =   2760
      TabIndex        =   1
      Top             =   3120
      Width           =   1215
   End
   Begin VB.CommandButton btnSave 
      Caption         =   "Sa&ve"
      Height          =   375
      Left            =   960
      TabIndex        =   0
      Top             =   3120
      Width           =   1215
   End
End
Attribute VB_Name = "dlgNewResponse"
Attribute VB_GlobalNameSpace = False
Attribute VB_Creatable = False
Attribute VB_PredeclaredId = True
Attribute VB_Exposed = False

Option Explicit

Private Sub btnSave_Click()
Dim sSQL As String
Dim rec As ADODB.Recordset

On Error GoTo DBError
Screen.MousePointer = vbHourglass

sSQL = "REPLACE INTO TicketResponse VALUES (default, """ & _
                    txtTitle.Text & """" & ",""" & _
                    txtResponse.Text & """)"

DB.Execute sSQL
Screen.MousePointer = vbNormal
MsgBox "Ticket Response Database has been updated", vbOKOnly, "New Response Added"

Unload Me

Exit Sub

DBError:
    Screen.MousePointer = vbNormal
    DB.ShowError "Unable to load responses"
End Sub

Private Sub CancelButton_Click()

    Unload Me
    
End Sub
