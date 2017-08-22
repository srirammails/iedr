VERSION 5.00
Begin VB.Form FrmLogin 
   BorderStyle     =   1  'Fixed Single
   Caption         =   "Login"
   ClientHeight    =   1905
   ClientLeft      =   45
   ClientTop       =   330
   ClientWidth     =   2835
   HelpContextID   =   1300
   LinkTopic       =   "Form1"
   MaxButton       =   0   'False
   MinButton       =   0   'False
   ScaleHeight     =   1905
   ScaleWidth      =   2835
   StartUpPosition =   2  'CenterScreen
   Begin VB.CommandButton CmdCancel 
      Cancel          =   -1  'True
      Caption         =   "Ca&ncel"
      Height          =   375
      Left            =   1560
      TabIndex        =   5
      Top             =   1440
      Width           =   1215
   End
   Begin VB.CommandButton CmdLogin 
      Caption         =   "&Login"
      Default         =   -1  'True
      Height          =   375
      Left            =   120
      TabIndex        =   4
      Top             =   1440
      Width           =   1215
   End
   Begin VB.TextBox TxtPassword 
      Height          =   285
      IMEMode         =   3  'DISABLE
      Left            =   120
      MaxLength       =   30
      PasswordChar    =   "*"
      TabIndex        =   1
      Top             =   960
      Width           =   2655
   End
   Begin VB.TextBox TxtUsername 
      Height          =   285
      Left            =   120
      MaxLength       =   30
      TabIndex        =   0
      Top             =   360
      Width           =   2655
   End
   Begin VB.Label LblPassword 
      Caption         =   "Password"
      Height          =   255
      Left            =   120
      TabIndex        =   3
      Top             =   720
      Width           =   2655
   End
   Begin VB.Label LblUsername 
      Caption         =   "Username"
      Height          =   255
      Left            =   120
      TabIndex        =   2
      Top             =   120
      Width           =   2535
   End
End
Attribute VB_Name = "FrmLogin"
Attribute VB_GlobalNameSpace = False
Attribute VB_Creatable = False
Attribute VB_PredeclaredId = True
Attribute VB_Exposed = False
Option Explicit

'*********************************************
'*                                           *
'* Written By : Brian Devlin                 *
'* Function   : Shows log in form            *
'*                                           *
'*********************************************

Const mlngMaxTries   As Long = 4

Dim mlngTries        As Long
Dim mblnValid        As Boolean


Public Sub VisualLogIn(ByRef blnValid As Boolean, Optional ByVal strUser As String, Optional ByVal strPassword As String, Optional ByVal strCaption As String = "")
 If strUser <> "" And strPassword <> "" Then
    If modLogIn.LogIn(strUser, strPassword) Then
       blnValid = True
    Else
       If strCaption <> "" Then Me.Caption = strCaption
       TxtUsername = strUser
       Me.Show vbModal
       blnValid = mblnValid
    End If
 Else
    If strCaption <> "" Then Me.Caption = strCaption
    TxtUsername = strUser
    Me.Show vbModal
    blnValid = mblnValid
 End If
 Unload Me
End Sub


Private Sub CmdCancel_Click()
 Unload Me
End Sub



Private Sub CmdLogin_Click()
 Dim blnValid As Boolean
 
 If modLogIn.LogIn(TxtUsername, TxtPassword) Then
    mblnValid = True
    Me.Visible = False
 Else
    MsgBox "Invalid username or password.", vbCritical
    CmdLogin.SetFocus
    If TxtUsername = "" Then
       TxtUsername.SetFocus
    Else
       TxtPassword.SetFocus
    End If
    mlngTries = mlngTries + 1
 End If
 
 If mlngTries > mlngMaxTries Then
    mblnValid = False
    Me.Visible = False
 End If
End Sub


Private Sub TxtPassword_GotFocus()
 TxtPassword.SelStart = 0
 TxtPassword.SelLength = 100
End Sub


Private Sub TxtUsername_GotFocus()
 TxtUsername.SelStart = 0
 TxtUsername.SelLength = 100
End Sub
