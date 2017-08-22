VERSION 5.00
Begin VB.Form FrmAbout 
   BorderStyle     =   3  'Fixed Dialog
   Caption         =   "About Phoenix System"
   ClientHeight    =   3345
   ClientLeft      =   45
   ClientTop       =   330
   ClientWidth     =   6300
   Icon            =   "FrmAbout.frx":0000
   LinkTopic       =   "Form1"
   MaxButton       =   0   'False
   MinButton       =   0   'False
   ScaleHeight     =   3345
   ScaleWidth      =   6300
   ShowInTaskbar   =   0   'False
   StartUpPosition =   1  'CenterOwner
   Begin VB.TextBox txtUserID 
      BackColor       =   &H8000000F&
      BorderStyle     =   0  'None
      BeginProperty Font 
         Name            =   "MS Sans Serif"
         Size            =   9.75
         Charset         =   0
         Weight          =   400
         Underline       =   0   'False
         Italic          =   0   'False
         Strikethrough   =   0   'False
      EndProperty
      ForeColor       =   &H8000000F&
      Height          =   405
      Left            =   120
      MousePointer    =   1  'Arrow
      TabIndex        =   7
      TabStop         =   0   'False
      Top             =   2760
      Width           =   2775
   End
   Begin VB.CommandButton CmdOK 
      Cancel          =   -1  'True
      Caption         =   "&OK"
      Default         =   -1  'True
      Height          =   375
      Left            =   4560
      TabIndex        =   6
      Top             =   2880
      Width           =   1095
   End
   Begin VB.ListBox List1 
      BackColor       =   &H8000000F&
      Height          =   450
      ItemData        =   "FrmAbout.frx":0CCA
      Left            =   2040
      List            =   "FrmAbout.frx":0CD4
      TabIndex        =   5
      Top             =   1920
      Width           =   3855
   End
   Begin VB.PictureBox Picture1 
      Height          =   2175
      Left            =   120
      Picture         =   "FrmAbout.frx":0D00
      ScaleHeight     =   2115
      ScaleWidth      =   1635
      TabIndex        =   0
      Top             =   240
      Width           =   1695
   End
   Begin VB.Label Label5 
      Caption         =   "This product is licensed to:"
      Height          =   255
      Left            =   2040
      TabIndex        =   4
      Top             =   1560
      Width           =   2895
   End
   Begin VB.Line Line1 
      BorderColor     =   &H00E0E0E0&
      X1              =   120
      X2              =   6120
      Y1              =   2640
      Y2              =   2640
   End
   Begin VB.Label Label4 
      Caption         =   "Copyright (C) 2002  Delphi Technologies Ltd"
      Height          =   255
      Left            =   2040
      TabIndex        =   3
      Top             =   1080
      Width           =   3735
   End
   Begin VB.Label lblVersion 
      Caption         =   "Version 1.1"
      BeginProperty Font 
         Name            =   "MS Sans Serif"
         Size            =   9.75
         Charset         =   0
         Weight          =   700
         Underline       =   0   'False
         Italic          =   0   'False
         Strikethrough   =   0   'False
      EndProperty
      Height          =   255
      Left            =   2040
      TabIndex        =   2
      Top             =   600
      Width           =   2175
   End
   Begin VB.Label Label3 
      BackColor       =   &H8000000E&
      BackStyle       =   0  'Transparent
      Caption         =   "Phoenix System"
      BeginProperty Font 
         Name            =   "Arial Black"
         Size            =   14.25
         Charset         =   0
         Weight          =   400
         Underline       =   0   'False
         Italic          =   0   'False
         Strikethrough   =   0   'False
      EndProperty
      ForeColor       =   &H00000000&
      Height          =   375
      Left            =   2040
      TabIndex        =   1
      Top             =   120
      Width           =   2655
   End
End
Attribute VB_Name = "FrmAbout"
Attribute VB_GlobalNameSpace = False
Attribute VB_Creatable = False
Attribute VB_PredeclaredId = True
Attribute VB_Exposed = False
Option Explicit

Private Sub CmdOK_Click()
    Unload Me
End Sub

Private Sub Form_Load()
 txtUserID = UserID
 lblVersion = "Version " & App.Major & "." & App.Minor & IIf(App.Revision = 0, "", "." & App.Revision)
End Sub
