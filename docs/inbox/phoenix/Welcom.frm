VERSION 5.00
Begin VB.Form FrmWelcom 
   BorderStyle     =   0  'None
   ClientHeight    =   4725
   ClientLeft      =   0
   ClientTop       =   0
   ClientWidth     =   7905
   DrawStyle       =   5  'Transparent
   Icon            =   "Welcom.frx":0000
   LinkTopic       =   "Form1"
   MaxButton       =   0   'False
   MinButton       =   0   'False
   ScaleHeight     =   4725
   ScaleWidth      =   7905
   ShowInTaskbar   =   0   'False
   StartUpPosition =   2  'CenterScreen
   Begin VB.Timer Timer1 
      Interval        =   2000
      Left            =   240
      Top             =   4200
   End
   Begin VB.Label Label7 
      BackStyle       =   0  'Transparent
      Caption         =   "And Invoicing System"
      BeginProperty Font 
         Name            =   "Arial Narrow"
         Size            =   18
         Charset         =   0
         Weight          =   700
         Underline       =   0   'False
         Italic          =   0   'False
         Strikethrough   =   0   'False
      EndProperty
      ForeColor       =   &H00000000&
      Height          =   495
      Left            =   240
      TabIndex        =   6
      Top             =   2640
      Width           =   3255
   End
   Begin VB.Label lblVersion 
      BackStyle       =   0  'Transparent
      Caption         =   "Version 1.2"
      BeginProperty Font 
         Name            =   "Arial Narrow"
         Size            =   14.25
         Charset         =   0
         Weight          =   700
         Underline       =   0   'False
         Italic          =   0   'False
         Strikethrough   =   0   'False
      EndProperty
      Height          =   375
      Left            =   6000
      TabIndex        =   5
      Top             =   3600
      Width           =   1695
   End
   Begin VB.Label Label5 
      BackStyle       =   0  'Transparent
      Caption         =   "IE Domain Registry Ltd"
      Height          =   255
      Left            =   480
      TabIndex        =   4
      Top             =   3840
      Width           =   1815
   End
   Begin VB.Label Label4 
      BackStyle       =   0  'Transparent
      Caption         =   "This product is licensed to:"
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
      TabIndex        =   3
      Top             =   3600
      Width           =   3495
   End
   Begin VB.Label Label2 
      BackStyle       =   0  'Transparent
      Caption         =   "All rights reserved. This product is protected by international copyright laws "
      Height          =   255
      Left            =   1680
      TabIndex        =   2
      Top             =   4320
      Width           =   5295
   End
   Begin VB.Label Label3 
      BackColor       =   &H8000000E&
      BackStyle       =   0  'Transparent
      Caption         =   "Phoenix"
      BeginProperty Font 
         Name            =   "Arial Black"
         Size            =   24
         Charset         =   0
         Weight          =   400
         Underline       =   0   'False
         Italic          =   0   'False
         Strikethrough   =   0   'False
      EndProperty
      ForeColor       =   &H00000000&
      Height          =   615
      Left            =   240
      TabIndex        =   1
      Top             =   1680
      Width           =   2655
   End
   Begin VB.Label Label1 
      BackStyle       =   0  'Transparent
      Caption         =   "Domain Registration "
      BeginProperty Font 
         Name            =   "Arial Narrow"
         Size            =   18
         Charset         =   0
         Weight          =   700
         Underline       =   0   'False
         Italic          =   0   'False
         Strikethrough   =   0   'False
      EndProperty
      ForeColor       =   &H00000000&
      Height          =   495
      Left            =   240
      TabIndex        =   0
      Top             =   2280
      Width           =   3135
   End
   Begin VB.Image Image1 
      BorderStyle     =   1  'Fixed Single
      Height          =   4740
      Left            =   0
      Picture         =   "Welcom.frx":000C
      Top             =   0
      Width           =   7995
   End
End
Attribute VB_Name = "FrmWelcom"
Attribute VB_GlobalNameSpace = False
Attribute VB_Creatable = False
Attribute VB_PredeclaredId = True
Attribute VB_Exposed = False
Option Explicit

Private Sub Form_Activate()
#If Development Then
    Unload Me
#End If
End Sub

Private Sub Form_Load()
 lblVersion = "Version " & App.Major & "." & App.Minor & IIf(App.Revision = 0, "", "." & App.Revision)
End Sub

Private Sub Timer1_Timer()
    If Timer1.Interval >= 10 Then
  
        Timer1.Interval = 0
        Unload FrmWelcom
    End If
    Screen.MousePointer = vbDefault
End Sub
