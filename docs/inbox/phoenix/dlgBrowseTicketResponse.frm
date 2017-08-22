VERSION 5.00
Begin VB.Form dlgBrowseTicketResponse 
   BorderStyle     =   3  'Fixed Dialog
   Caption         =   "Ticket Reponse Database"
   ClientHeight    =   3900
   ClientLeft      =   2760
   ClientTop       =   3750
   ClientWidth     =   8370
   LinkTopic       =   "Form1"
   MaxButton       =   0   'False
   MinButton       =   0   'False
   ScaleHeight     =   3900
   ScaleWidth      =   8370
   ShowInTaskbar   =   0   'False
   StartUpPosition =   2  'CenterScreen
   Begin VB.CommandButton Delete 
      Caption         =   "&Delete"
      Height          =   375
      Left            =   5400
      TabIndex        =   4
      Top             =   3360
      Width           =   1335
   End
   Begin VB.CommandButton btnUpdate 
      Caption         =   "&Update"
      Height          =   375
      Left            =   3960
      TabIndex        =   3
      Top             =   3360
      Width           =   1215
   End
   Begin VB.Frame frmTR 
      Caption         =   " Ticket Response Database "
      Height          =   2895
      Left            =   240
      TabIndex        =   6
      Top             =   240
      Width           =   7815
      Begin VB.ListBox lstTitle 
         Height          =   1620
         Left            =   360
         TabIndex        =   0
         Top             =   840
         Width           =   3015
      End
      Begin VB.TextBox txtText 
         Height          =   1620
         Left            =   3840
         MultiLine       =   -1  'True
         ScrollBars      =   2  'Vertical
         TabIndex        =   1
         Top             =   840
         Width           =   3735
      End
      Begin VB.Label Label1 
         Caption         =   "Response Text"
         Height          =   255
         Left            =   3840
         TabIndex        =   8
         Top             =   480
         Width           =   2415
      End
      Begin VB.Label lblTitle 
         Caption         =   "Response Title"
         Height          =   255
         Left            =   360
         TabIndex        =   7
         Top             =   480
         Width           =   2535
      End
   End
   Begin VB.CommandButton CancelButton 
      Caption         =   "&Cancel"
      Height          =   375
      Left            =   6960
      TabIndex        =   5
      Top             =   3360
      Width           =   1095
   End
   Begin VB.CommandButton btnCopyPaste 
      Caption         =   "Copy &and Paste >>"
      Height          =   375
      Left            =   840
      TabIndex        =   2
      Top             =   3360
      Width           =   1815
   End
End
Attribute VB_Name = "dlgBrowseTicketResponse"
Attribute VB_GlobalNameSpace = False
Attribute VB_Creatable = False
Attribute VB_PredeclaredId = True
Attribute VB_Exposed = False

Option Explicit
Dim sSQL As String
Dim rec  As ADODB.Recordset
Const iMaxResponses As Integer = 250
Dim sResponses(iMaxResponses) As String
Private Sub CancelButton_Click()
    Unload Me
End Sub

Private Sub Form_Load()
    
     Dim iIndex As Integer
     
     On Error GoTo LoadERROR
     Screen.MousePointer = vbHourglass
    
     iIndex = 0
     lstTitle.Clear
     txtText.Text = ""
     
     sSQL = "SELECT *"
     sSQL = sSQL & "FROM TicketResponse "
     sSQL = sSQL & "ORDER BY Response_Title"
     
     DB.Execute sSQL, rec
     
     
     While Not rec.EOF
     
        lstTitle.AddItem rec("Response_Title")
        sResponses(iIndex) = rec("Response_Text")
        rec.MoveNext
        iIndex = iIndex + 1
        
     Wend
     
     If iIndex > 0 Then
        lstTitle.ListIndex = 0
     End If
     
     Screen.MousePointer = vbNormal
Exit Sub

LoadERROR:
    Screen.MousePointer = vbNormal
    DB.ShowError "Unable to load responses"
End Sub

Private Sub lstTitle_Click()

    txtText.Text = sResponses(lstTitle.ListIndex)
    
End Sub

Private Sub btnCopyPaste_Click()

    gEditTicketForm.txtHMRemark = txtText.Text
    rec.Close
    Unload Me
    
End Sub

Private Sub btnUpdate_Click()
     
    Screen.MousePointer = vbHourglass
    On Error GoTo DBError

    sSQL = "UPDATE TicketResponse " & _
           "SET Response_Text = """ & txtText.Text & """" & _
           " WHERE Response_Title = """ & lstTitle.List(lstTitle.ListIndex) & """"
            
     DB.Execute sSQL
     Screen.MousePointer = vbNormal
     MsgBox "Ticket Response Database has been updated", vbOKOnly, "Updated Response"
     Form_Load
     
Exit Sub

DBError:
    Screen.MousePointer = vbNormal
    DB.ShowError "Unable to update response"
End Sub

Private Sub Delete_Click()
    
    Screen.MousePointer = vbHourglass
    On Error GoTo DBError

     sSQL = "DELETE FROM TicketResponse " & _
           "WHERE Response_Title = """ & lstTitle.List(lstTitle.ListIndex) & """"
            
     DB.Execute sSQL
     Screen.MousePointer = vbNormal
     MsgBox "Ticket Response Database has been updated", vbOKOnly, "Deleted Response"
     Form_Load
     
Exit Sub

DBError:
    Screen.MousePointer = vbNormal
    DB.ShowError "Unable to delete response"
End Sub

