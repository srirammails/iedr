VERSION 5.00
Begin VB.Form FrmBackground 
   BorderStyle     =   0  'None
   Caption         =   "Form1"
   ClientHeight    =   4965
   ClientLeft      =   0
   ClientTop       =   0
   ClientWidth     =   7110
   Enabled         =   0   'False
   LinkTopic       =   "Form1"
   MDIChild        =   -1  'True
   ScaleHeight     =   4965
   ScaleWidth      =   7110
   ShowInTaskbar   =   0   'False
   Begin VB.Image Image1 
      Height          =   4680
      Left            =   0
      Picture         =   "FrmBackground.frx":0000
      Top             =   0
      Width           =   7935
   End
End
Attribute VB_Name = "FrmBackground"
Attribute VB_GlobalNameSpace = False
Attribute VB_Creatable = False
Attribute VB_PredeclaredId = True
Attribute VB_Exposed = False
Option Explicit

Private Sub Form_Load()
 Me.Width = Image1.Width
 Me.Height = Image1.Height
End Sub
