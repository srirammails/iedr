VERSION 5.00
Object = "{F9043C88-F6F2-101A-A3C9-08002B2F49FB}#1.2#0"; "COMDLG32.OCX"
Begin VB.Form FrmRepInv 
   BorderStyle     =   1  'Fixed Single
   Caption         =   "Report Invoice"
   ClientHeight    =   8010
   ClientLeft      =   45
   ClientTop       =   330
   ClientWidth     =   8580
   LinkTopic       =   "Form1"
   MaxButton       =   0   'False
   MinButton       =   0   'False
   ScaleHeight     =   8010
   ScaleWidth      =   8580
   StartUpPosition =   2  'CenterScreen
   Begin MSComDlg.CommonDialog CDlg 
      Left            =   360
      Top             =   7560
      _ExtentX        =   847
      _ExtentY        =   847
      _Version        =   393216
   End
   Begin VB.CommandButton CmdClose 
      Cancel          =   -1  'True
      Caption         =   "&Close"
      Height          =   375
      Left            =   6960
      TabIndex        =   4
      Top             =   7560
      Width           =   1455
   End
   Begin VB.CommandButton CmdPrint 
      Caption         =   "&Print"
      Height          =   375
      Left            =   5400
      TabIndex        =   3
      Top             =   7560
      Width           =   1455
   End
   Begin VB.PictureBox PicInvoice 
      BackColor       =   &H00FFFFFF&
      BorderStyle     =   0  'None
      Height          =   7335
      Left            =   120
      ScaleHeight     =   7335
      ScaleWidth      =   8295
      TabIndex        =   0
      Top             =   120
      Width           =   8295
      Begin VB.PictureBox PicDetails 
         BackColor       =   &H00FFFFFF&
         BorderStyle     =   0  'None
         Height          =   3135
         Left            =   1680
         Picture         =   "FrmIRepInv.frx":0000
         ScaleHeight     =   3135
         ScaleWidth      =   4935
         TabIndex        =   6
         Top             =   3000
         Width           =   4935
      End
      Begin VB.PictureBox PicAddress 
         BackColor       =   &H00FFFFFF&
         BorderStyle     =   0  'None
         Height          =   2175
         Left            =   0
         Picture         =   "FrmIRepInv.frx":30DD2
         ScaleHeight     =   2175
         ScaleWidth      =   2775
         TabIndex        =   2
         Top             =   0
         Width           =   2775
      End
      Begin VB.PictureBox PicLogo 
         BackColor       =   &H00FFFFFF&
         BorderStyle     =   0  'None
         Height          =   1935
         Left            =   5760
         Picture         =   "FrmIRepInv.frx":46FFC
         ScaleHeight     =   1935
         ScaleWidth      =   2295
         TabIndex        =   1
         Top             =   0
         Width           =   2295
      End
      Begin VB.Line Line4 
         X1              =   240
         X2              =   7800
         Y1              =   6240
         Y2              =   6240
      End
      Begin VB.Line Line3 
         X1              =   360
         X2              =   7920
         Y1              =   2880
         Y2              =   2880
      End
      Begin VB.Label LblHeader 
         Alignment       =   2  'Center
         BackColor       =   &H00FFFFFF&
         Caption         =   "INVOICE #3640"
         BeginProperty Font 
            Name            =   "Arial"
            Size            =   21.75
            Charset         =   0
            Weight          =   700
            Underline       =   0   'False
            Italic          =   0   'False
            Strikethrough   =   0   'False
         EndProperty
         Height          =   495
         Left            =   0
         TabIndex        =   5
         Top             =   2400
         Width           =   8295
      End
      Begin VB.Line Line1 
         BorderColor     =   &H0000C000&
         X1              =   360
         X2              =   7920
         Y1              =   2280
         Y2              =   2280
      End
      Begin VB.Line Line2 
         BorderColor     =   &H0000C000&
         X1              =   7920
         X2              =   360
         Y1              =   2340
         Y2              =   2340
      End
   End
End
Attribute VB_Name = "FrmRepInv"
Attribute VB_GlobalNameSpace = False
Attribute VB_Creatable = False
Attribute VB_PredeclaredId = True
Attribute VB_Exposed = False
Private Sub CmdClose_Click()
Unload Me
End Sub

Private Sub CmdPrint_Click()
 CDlg.ShowPrinter
End Sub
