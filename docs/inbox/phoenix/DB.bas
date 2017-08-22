Attribute VB_Name = "DB"
Option Explicit
Public Const glngDuplicateKey As Long = -2147467259


Const mstrDbUserId   As String = ""
Const mstrDbPassword As String = ""
Const mstrDatabase   As String = "Phoenix"

Private mcnDatabase     As ADODB.Connection
Private mlngTransLevel  As Long


Public Enum ustStaticTable
 ustCompanyName
 ustVatRate
 ustTradeRate
 ustRetailRate
 ustHistoricalRetail
 ustBusinessDate
 ustVatNumber
 ustLastNicHandle
 ustCompanyAddress
 ustLastInvoiceRun
 ustLastRemittleRun
 ustLastInvoiceNo
 ustPhone
 ustFax
 ustWeb
 ustSmtp
 ustSmtpPort
 ustReplyAddr
 ustWebReport 'Celina Leong Phoenix1.1 23/05/03 For Report Heading Use
End Enum

'* Converts a VB date into a Database date
'* (avoids confusion with American formats)
Public Function CDBDate(ByVal dtm As Date) As String
 CDBDate = Format(dtm, "\'yyyy-mm-dd\'")
End Function


'* Converts a VB date/time into a Database date/time
'* (to avoid confusion with American formats)
Public Function CDBTime(ByVal dtm As Date) As String
 CDBTime = Format(dtm, "\'yyyy-mm-dd hh:nn:ss\'")
End Function


'* Converts a VB date/time into a Database date/time
'* (avoids confusion with American formats)
Public Function CDBTimeStamp(ByVal str As String) As String
 CDBTimeStamp = Format(str, "\'yyyymmddhhnnss\'")
End Function


'* Converts a string into a valid string for use in SQL
'* Adds quotes and changes single quotes to apostrophies
Public Function CDBText(ByVal str As String) As String
 str = Replace(str, Chr(34), "'")
 CDBText = Chr(34) & str & Chr(34)
End Function


'* Converts a boolean expression into
'* corresponding SQL value
Public Function CDBBool(ByVal bln As Boolean) As String
 If bln Then
    CDBBool = "TRUE"
 Else
    CDBBool = "FALSE"
 End If
End Function


Public Function NoNull(fld As ADODB.Field) As Variant
 If IsNull(fld) Then
    Select Case fld.Type
     Case adBoolean
       NoNull = False
     Case adBigInt, adCurrency, adDecimal, adDouble, adInteger, adNumeric, adSingle, adTinyInt, adSmallInt, adUnsignedBigInt, adUnsignedInt, adUnsignedSmallInt, adUnsignedTinyInt, adVarNumeric
       NoNull = 0
     Case adDate, adDBDate, adDBTime, adDBTimeStamp, adFileTime
       NoNull = 0
     Case Else
       NoNull = ""
    End Select
 Else
    NoNull = fld.Value
 End If
End Function


Public Property Let Connection(cn As ADODB.Connection)
 Set mcnDatabase = cn
End Property
Public Property Get Connection() As ADODB.Connection
 Set Connection = mcnDatabase
End Property


Public Sub OpenConnection(Optional ByVal strOther As String)
 Set mcnDatabase = New ADODB.Connection
 mcnDatabase.Open "Username=" & mstrDbUserId & ";Password=" & mstrDbPassword & ";DSN=" & mstrDatabase & ";" & strOther
End Sub


Public Sub CloseConnection()
' mcnDatabase.Close
 Set mcnDatabase = Nothing
 
 
#If Development Then                    '* Conditional Code
    If TransactionLevel <> 0 Then       '* Conditional Code
       Debug.Print "****************************"
       Debug.Print "****** W A R N I N G *******"
       Debug.Print "****************************"
       Debug.Print "*** Transaction Level = " & TransactionLevel
       Debug.Print "****************************"
    End If                              '* Conditional Code
#End If                                 '* Conditional Code
End Sub


'* Runs SQL against the database
'*
'* NOTE : The code between the #If will only run if
'* the Conditional Compilation Argument is set to : Development = 1
'* Meunus - Project; IEDR Properties; Make (tab)
'* It will write any SQL errors to the Debug Window along with the SQL statement
'*
'* NOTE : Remove this Argument when making the final Exe as this code
'* is not needed in the production version (will do *NO* harm if left in)
Public Sub Execute(ByVal strSQL As String, Optional rec As ADODB.Recordset, Optional ByVal CursorType As CursorTypeEnum = adOpenForwardOnly, Optional ByVal LockType As LockTypeEnum = adLockReadOnly, Optional ByRef lngRecordsAffected As Long)
 #If Development Then         '* Conditional Code
  On Error GoTo ExecuteERROR  '* Conditional Code
 #End If                      '* Conditional Code
 
 strSQL = Trim(strSQL)
 
' if connection closed / dropped reopen. May 2007
' If mcnDatabase.State = 0 Then
'    Set mcnDatabase = Nothing
'   OpenConnection
' End If
    
    
 If UCase(Left(strSQL, 6)) = "SELECT" Then
    Set rec = New ADODB.Recordset
    rec.Open strSQL, mcnDatabase, CursorType, LockType
 Else  '* Else UPDATE, INSERT or DELETE
    Set rec = mcnDatabase.Execute(strSQL, lngRecordsAffected)
 End If


#If Development Then           '* Conditional Code
 Exit Sub                      '* Conditional Code
ExecuteERROR:                  '* Conditional Code
 Debug.Print ""                '* Conditional Code
 Debug.Print "ERROR (" & Err.Number & ") " & Err.Description                       '* Conditional Code
 Debug.Print strSQL            '* Conditional Code
 Err.Raise Err.Number, Err.Source, Err.Description, Err.HelpFile, Err.HelpContext   '* Conditional Code
#End If                        '* Conditional Code
End Sub


'* Returns the last AutoIncrement field Inserted
'* Note : Does not have any error handeling. Errors
'* must be handled bu Calling routine
Public Function LastInsertID(ByVal strTable As String, ByVal strField As String) As Variant
 Dim rec As ADODB.Recordset
 
 Execute "SELECT * FROM " & strTable & " WHERE " & strField & " IS NULL", rec
 LastInsertID = rec.Fields(strField)
End Function



'**** Start of Transaction Code ****

'* Returns the nesting level of the transaction
Public Property Get TransactionLevel() As Long
 TransactionLevel = mlngTransLevel
End Property

Public Sub BeginTransaction()
' mcnDatabase.BeginTrans
 Execute "BEGIN;"
 mlngTransLevel = mlngTransLevel + 1
End Sub

Public Sub CommitTransaction()
 mlngTransLevel = mlngTransLevel - 1
 Execute "COMMIT;"
' mcnDatabase.CommitTrans
End Sub

Public Sub RollbackTransaction()
 mlngTransLevel = mlngTransLevel - 1
 Execute "ROLLBACK;"
' mcnDatabase.RollbackTrans
End Sub
'**** End of Transaction Code ****


'**** Start of Static Table Code ****

Public Property Get StaticData(ByVal ustField As ustStaticTable) As Variant
 Dim strSQL As String
 Dim rec    As ADODB.Recordset
 
 strSQL = "SELECT " & GetStaticField(ustField) & " FROM StaticTableName"
 DB.Execute strSQL, rec
 
 If rec.EOF Then
    Err.Raise 9999, "Phoenix", "Unable to Get Static Data for " & GetStaticField(ustField)
 Else
    StaticData = NoNull(rec.Fields(0))
 End If
End Property


Public Property Let StaticData(ByVal ustField As ustStaticTable, ByVal vntValue As Variant)
 Dim strSQL            As String
 Dim lngRecordsUpdated As Long
 
 strSQL = "UPDATE StaticTableName SET "
 strSQL = strSQL & GetStaticField(ustField) & "="
 Select Case GetStaticFieldType(ustField)
  Case vbVDate
    strSQL = strSQL & CDBDate(vntValue)
  Case vbVString
    strSQL = strSQL & CDBText(vntValue)
  Case Else
    strSQL = strSQL & vntValue
 End Select
 DB.Execute strSQL, , , , lngRecordsUpdated
 
 If lngRecordsUpdated = 0 Then
    Err.Raise 9999, "Phoenix", "Unable to Save Static Data for " & GetStaticField(ustField)
 End If
End Property


Private Function GetStaticField(ByVal ustField As ustStaticTable) As String
 Select Case ustField
   Case ustCompanyName
     GetStaticField = "CompanyName"
   Case ustVatRate
     GetStaticField = "VatRate"
   Case ustTradeRate
     GetStaticField = "TradeRate"
   Case ustRetailRate
     GetStaticField = "RetailRate"
   Case ustHistoricalRetail
     GetStaticField = "HistoricRetailRate"
   Case ustBusinessDate
     GetStaticField = "BusinessDate"
   Case ustVatNumber
     GetStaticField = "VatNumber"
   Case ustLastNicHandle
     GetStaticField = "LastNicHandle"
   Case ustCompanyAddress
     GetStaticField = "CompanyAddress"
   Case ustLastInvoiceRun
     GetStaticField = "LastInvoiceRun"
   Case ustLastRemittleRun
     GetStaticField = "LastRemittalRun"
   Case ustLastInvoiceNo
     GetStaticField = "LastInvoiceNo"
   Case ustPhone
     GetStaticField = "Phone"
   Case ustFax
     GetStaticField = "Fax"
   Case ustWeb
     GetStaticField = "Web"
   Case ustSmtp
     GetStaticField = "SMTP"
   Case ustSmtpPort
     GetStaticField = "SMTP_Port"
   Case ustReplyAddr
     GetStaticField = "ReplyAddr"
   Case ustWebReport
     GetStaticField = "WebReport"   'Celina Leong Phoenix1.1 23/05/03
     
  End Select
End Function


Private Function GetStaticFieldType(ByVal ustField As ustStaticTable) As VariantTypeConstants
 Select Case ustField
   Case ustCompanyName, ustVatNumber, ustLastNicHandle, ustCompanyAddress, ustPhone, ustFax, ustWeb, ustSmtp, ustReplyAddr
     GetStaticFieldType = vbVString
   Case ustBusinessDate, ustLastInvoiceRun, ustLastRemittleRun
     GetStaticFieldType = vbVDate
   Case ustTradeRate, ustRetailRate, ustHistoricalRetail
     GetStaticFieldType = vbVCurrency
   Case ustVatRate
     GetStaticFieldType = vbVDouble
   Case ustSmtpPort
     GetStaticFieldType = vbVLong
 End Select
End Function

'**** End of Static Table Code ****


Public Sub ShowError(ByVal strError As String)
 MsgBox strError & vbCrLf & _
        "Err No:   " & Err.Number & vbCrLf & _
        "Err Desc: " & Err.Description, vbCritical
End Sub


