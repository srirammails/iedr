Attribute VB_Name = "Errors"
Option Explicit
Public Sub Show_Error_DB(sErr As String)

MsgBox sErr & vbCrLf & _
       "Err No:   " & Err.Number & vbCrLf & _
       "Err Desc: " & Err.Description, vbCritical

End Sub
