VERSION 5.00
Object = "{248DD890-BB45-11CF-9ABC-0080C7E7B78D}#1.0#0"; "MSWINSCK.OCX"
Begin VB.Form FrmSendMail 
   BorderStyle     =   3  'Fixed Dialog
   Caption         =   "Send Email"
   ClientHeight    =   1710
   ClientLeft      =   150
   ClientTop       =   435
   ClientWidth     =   4080
   LinkTopic       =   "Form1"
   MaxButton       =   0   'False
   MinButton       =   0   'False
   ScaleHeight     =   1710
   ScaleWidth      =   4080
   ShowInTaskbar   =   0   'False
   StartUpPosition =   1  'CenterOwner
   Begin VB.ListBox lstInfo 
      Height          =   1035
      Left            =   120
      TabIndex        =   2
      Top             =   480
      Width           =   3735
   End
   Begin VB.TextBox txtTo 
      BackColor       =   &H8000000F&
      BorderStyle     =   0  'None
      Height          =   285
      Left            =   600
      TabIndex        =   1
      TabStop         =   0   'False
      Top             =   120
      Width           =   3135
   End
   Begin MSWinsockLib.Winsock WinSock 
      Left            =   0
      Top             =   720
      _ExtentX        =   741
      _ExtentY        =   741
      _Version        =   393216
   End
   Begin VB.Label Label1 
      Caption         =   "To"
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
      Left            =   120
      TabIndex        =   0
      Top             =   120
      Width           =   495
   End
End
Attribute VB_Name = "FrmSendMail"
Attribute VB_GlobalNameSpace = False
Attribute VB_Creatable = False
Attribute VB_PredeclaredId = True
Attribute VB_Exposed = False
Option Explicit

Private mstrSmtpHost     As String
Private mlngPort         As Long
Private mstrMailTo       As String
Private mstrMailFrom     As String
Private mstrSenderName   As String
Private mstrRecieverName As String
Private mstrSubject      As String
Private mstrMailText     As String
Private mstrMailAll      As String
Private mstrMailCC       As String
Private mstrMailBCC      As String

Private mblnGreenLight   As Boolean
Private mblnCanClose     As Boolean
Private mblnError        As Boolean


'''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
' New Send Mail to handle the collections of CC and BCC addresses
' Added By David Gildea on 17/01/05 for Email Specification 24
' strMailAll will have all email address to recieve the mail and will
' be used in the SMTP Data sending.
' strMailTo, strMailCC and strMailBCC are string to be put into the mailbody
'
'''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
Public Sub NewSendMail(ByRef blnError As Boolean, _
                    ByVal strSmtpHost As String, _
                    ByVal lngPort As Long, _
                    ByVal strMailTo As String, _
                    ByVal strMailCC As String, _
                    ByVal strMailBCC As String, _
                    ByVal strMailFrom As String, _
                    ByVal strSubject As String, _
                    ByVal strMailText As String, _
                    Optional ByVal strSenderName As String = "", _
                    Optional ByVal strRecieverName As String = "")
                     
 
 mblnError = False
 mstrSmtpHost = strSmtpHost
 mlngPort = lngPort
 mstrMailTo = strMailTo
 mstrMailAll = strMailTo & "," & strMailCC & "," & strMailBCC
 mstrMailCC = strMailCC
 mstrMailBCC = strMailBCC
 mstrMailFrom = strMailFrom
 mstrSubject = strSubject
 mstrMailText = strMailText
 mstrSenderName = strSenderName
 mstrRecieverName = strRecieverName
 
 
 If mstrRecieverName = "" Then
    txtTo = mstrMailTo
 Else
    txtTo = mstrRecieverName
 End If
 
 FrmSendMail.Show
 DoEvents
 
 SendNewEMail
  
 blnError = mblnError
 mblnCanClose = True
 If Not mblnError Then
    Unload Me
 End If
End Sub

Public Sub SendMail(ByRef blnError As Boolean, _
                    ByVal strSmtpHost As String, _
                    ByVal lngPort As Long, _
                    ByVal strMailTo As String, _
                    ByVal strMailFrom As String, _
                    ByVal strSubject As String, _
                    ByVal strMailText As String, _
                    Optional ByVal strSenderName As String = "", _
                    Optional ByVal strRecieverName As String = "")
                     
 
 mblnError = False
 mstrSmtpHost = strSmtpHost
 mlngPort = lngPort
 mstrMailTo = strMailTo
 mstrMailFrom = strMailFrom
 mstrSubject = strSubject
 mstrMailText = strMailText
 mstrSenderName = strSenderName
 mstrRecieverName = strRecieverName
 
 If mstrRecieverName = "" Then
    txtTo = mstrMailTo
 Else
    txtTo = mstrRecieverName
 End If
 
 FrmSendMail.Show
 DoEvents
 
 SendEMail
  
 blnError = mblnError
 mblnCanClose = True
 If Not mblnError Then
    Unload Me
 End If
End Sub

Private Sub SendNewEMail()
 On Error GoTo SendMailERROR
 

 'Added by David Gildea to deal with the array of Addresses
 'Split this into an array of elements
 '17/01/05
 
 Dim addressArray
 Dim i As Integer
 Dim OK As Boolean
 
 addressArray = Split(mstrMailAll, ",")
 'CC200512113 Check that there is at least 1 reciepient address
 OK = False
 For i = 0 To UBound(addressArray)
    If (Len(Trim(addressArray(i))) <> 0) Then OK = True
 Next
 If Not OK Then 'no receipient address
    Exit Sub
 End If
 
  
 ValidateSettings
 ConnectToSmtpHost
 WaitForReplyFromSmtp
 
 
 SendCommand "HELO " & WinSock.RemoteHostIP
 SendCommand "MAIL FROM: " & mstrMailFrom
 
 'Repeat the send command for the SMTP server
 For i = 0 To UBound(addressArray)
    If (Len(Trim(addressArray(i))) <> 0) Then SendCommand "RCPT TO: " & addressArray(i)
 Next
 
 SendCommand "DATA"
 SendNewMailBody
 SendQuitCommand
 
Exit Sub
SendMailERROR:
 WinSock.Close
 mblnCanClose = True
 mblnError = True
End Sub

Private Sub SendEMail()
 On Error GoTo SendMailERROR
 
 
 ValidateSettings
 ConnectToSmtpHost
 WaitForReplyFromSmtp
 
 
 SendCommand "HELO " & WinSock.RemoteHostIP
 SendCommand "MAIL FROM: " & mstrMailFrom
 SendCommand "RCPT TO: " & mstrMailTo
 SendCommand "DATA"
 SendMailBody
 SendQuitCommand
 
Exit Sub
SendMailERROR:
 WinSock.Close
 mblnCanClose = True
 mblnError = True
End Sub


Private Sub Form_Unload(Cancel As Integer)
 If Not mblnCanClose Then
    Cancel = True
 End If
End Sub

Private Sub Winsock_DataArrival(ByVal bytesTotal As Long)
 Dim strDataFile As String
 Dim lngReply    As Long
 
 WinSock.GetData strDataFile '* Recieve Reply "DATAFile" from SMTP Host
 lngReply = Mid(strDataFile, 1, 3)

 If lngReply = 220 Or lngReply = 250 Or lngReply = 354 Then '* if reply is OK then greenlight = true
    mblnGreenLight = True
 Else ' Else if reply is other than OK log and close winsock
    LogInfo "DataArrival Denied.." & strDataFile
    WinSock.Close           '* close port
    mblnError = True
 End If
End Sub


Private Sub WinSock_Error(ByVal Number As Integer, Description As String, ByVal Scode As Long, ByVal Source As String, ByVal HelpFile As String, ByVal HelpContext As Long, CancelDisplay As Boolean)
 LogInfo "Error : " & Description
 mblnError = True
 mblnCanClose = True
End Sub


Private Sub ValidateSettings()
  If Trim(mstrSmtpHost) = "" Then
     LogInfo "No SMTP Host specified"
     Err.Raise 9999, , "No SMTP Host specified"
  End If
  
  If Trim(mstrMailTo) = "" Then
     LogInfo "Mail address to send to not specified"
     Err.Raise 9999, , "Mail address to send to not specified"
  End If

  If Trim(mstrMailFrom) = "" Then
     LogInfo "Senders Mail address not specified"
     Err.Raise 9999, , "Senders Mail address not specified"
  End If
End Sub


Private Sub ConnectToSmtpHost()
 WinSock.Close                        '* Close Winsock Port
 WinSock.Connect mstrSmtpHost, mlngPort '* Connect to SMTP Host on PORT
 
 LogInfo "Connecting..."
 Do While WinSock.State <> sckConnected '* loop until connection established
    DoEvents
    If WinSock.State = sckError Then    '* if winsock has an error log and exit sub
       LogInfo "Error Connecting to " & mstrSmtpHost & " on port : " & mlngPort
       WinSock.Close
       Err.Raise 9999, "Error Connecting to " & mstrSmtpHost & " on port : " & mlngPort
    End If
 Loop
 LogInfo "Connected"
End Sub

Private Sub WaitForReplyFromSmtp()
 LogInfo "Waiting for reply..."
      
 Do While (mblnGreenLight = False) And (mblnError = False) '* wait for incomming data before proceding
    DoEvents
 Loop
 
 If mblnError Then
    Err.Raise 9999
 End If
 
 mblnGreenLight = False '* set greenlight to false
End Sub


Private Sub SendCommand(ByVal strCommand As String)
 WinSock.SendData strCommand & vbCrLf '* Sendcommand to SMTP Host
 Do While (mblnGreenLight = False) And (mblnError = False)
    DoEvents
 Loop
 
 If mblnError Then
    Err.Raise 9999
 End If
 
 mblnGreenLight = False
End Sub

Private Sub SendNewMailBody()
 LogInfo "Sending Mail..."
 
 If mstrSenderName = "" Then
    WinSock.SendData "FROM: " & mstrMailFrom & vbCrLf   ' Send senders name
 Else
    WinSock.SendData "FROM: " & mstrSenderName & " <" & mstrMailFrom & ">" & vbCrLf ' Send senders name
 End If
 If mstrRecieverName = "" Then
    WinSock.SendData "TO: " & mstrMailTo & vbCrLf    ' send recievers name
 Else
    WinSock.SendData "TO: " & mstrRecieverName & " <" & mstrMailTo & ">" & vbCrLf   ' send recievers name
 End If
 
 'Change to the email system to only print CC if there is a CC mail address
 'Added by David Gildea 01-03-2005 V 1.4.6
 
 If mstrMailCC <> "" Then
    WinSock.SendData "CC: " & mstrMailCC & vbCrLf   ' send CC recipients
 End If
 
 WinSock.SendData "SUBJECT: " & mstrSubject & vbCrLf              '* send subject
 WinSock.SendData vbCrLf
 WinSock.SendData mstrMailText & vbCrLf ' Send DATA to SMTP_HOST
            
 SendCommand vbCrLf & "."
End Sub

Private Sub SendMailBody()
 LogInfo "Sending Mail..."
 
 If mstrSenderName = "" Then
    WinSock.SendData "FROM: " & mstrMailFrom & vbCrLf   ' Send senders name
 Else
    WinSock.SendData "FROM: " & mstrSenderName & " <" & mstrMailFrom & ">" & vbCrLf ' Send senders name
 End If
 If mstrRecieverName = "" Then
    WinSock.SendData "TO: " & mstrMailTo & vbCrLf    ' send recievers name
 Else
    WinSock.SendData "TO: " & mstrRecieverName & " <" & mstrMailTo & ">" & vbCrLf   ' send recievers name
 End If
 
 WinSock.SendData "SUBJECT: " & mstrSubject & vbCrLf              '* send subject
 WinSock.SendData vbCrLf
 WinSock.SendData mstrMailText & vbCrLf ' Send DATA to SMTP_HOST
            
 SendCommand vbCrLf & "."
End Sub


Private Sub SendQuitCommand()
 WinSock.SendData "QUIT" & vbCrLf '* Send Quit command
 LogInfo "Email Sent Successfully..."
 WinSock.Close
End Sub


Private Sub LogInfo(ByVal strText As String)
 lstInfo.AddItem strText, 0
End Sub
