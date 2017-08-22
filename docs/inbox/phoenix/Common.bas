Attribute VB_Name = "Common"
Option Explicit

Public Const glngAllsItemData  As Long = -999
Public Const glngNonesItemData As Long = 0
Public Const gstrComboAllText  As String = "(All)"
Public Const gstrComboNoneText As String = "(none)"
Public Const gstrAccountNoPad  As String = "00000000"
Public Const glngDomainAccount As Long = 0
Public Const glngGuestAccount  As Long = 1
'Celina Leong Phoenix1.1 08/04/03

Public Const mstrUKCode     As Integer = 254
Public Const mstrUSACode    As Integer = 265
Public Const mstrIRECode    As Integer = 121
Public Const mstrNIRECode   As Integer = 119

Public gEditTicketForm      As Form

'-------------------------------------------
Private Const mlngCatCount     As Long = 10
Private Const mlngClassCount   As Long = 8
Private mblnClassCat(mlngClassCount, mlngCatCount) As Boolean
Private mstrCatNames(mlngCatCount)                 As String


'* As each state can be represented by a Bit, to check
'* against multiple states add the states together and AND them
'* to the value your are checkeing. Eg:
'*     If csFromState AND (csAdd + csEdit) Then
'* The above code will be TRUE when the csFormState is either
'* caAdd or csEdit
Public Enum ControlState
 csView = 1
 csAdd = 2
 csEdit = 4
 csError = 8
End Enum


Public Sub Main()
 Dim blnValid As Boolean
 
 'David O'Leary - 4-Nov-2002
 'If the system does not have a valid license, then do not run the program
 
 '----------ONLY TO BE USED IF REQUESTED BY DELPHI MANAGEMENT----------'
 'If Valid_License = False Then End
 '---------------------------------------------------------------------'
 
 App.HelpFile = App.Path & "\Phoenix.hlp"
 DB.OpenConnection
 Set CnPhoenix = DB.Connection
 
 FrmLogin.VisualLogIn blnValid
 If blnValid Then
    If HasAuthority(atCustomerServ + atHostMaster + atLeadHostMaster + atAccounts + atSuperUser) Then
       FillClassCatArray
       FrmWelcom.Show vbModal
       MDIMain.Show
    Else
       MsgBox "Insufficient Authority", vbCritical
       On Error Resume Next
       CnPhoenix.Close
       Set CnPhoenix = Nothing
       DB.CloseConnection
    End If
 Else
    On Error Resume Next
    CnPhoenix.Close
    Set CnPhoenix = Nothing
    DB.CloseConnection
 End If
End Sub


'* Changes to appearence of all TextBoxes and ComboBoxes on
'* the specified form to the specified state
'*
'* NOTE : Controls with a tag property of "X" will be ignored
Public Sub FormatControls(frm As Form, ByVal cs As ControlState)
 Dim ctl As Control
 For Each ctl In frm.Controls
     FormatControl ctl, cs
 Next ctl
End Sub


'* Changes to appearence of a specified TextBox or ComboBox
'* to the specified state.
'*
'* NOTE : Controls with a Tag property of "X" will be ignored.
Public Sub FormatControl(ctl As Control, ByVal cs As ControlState)
 Dim ctlTxt          As Control
 Dim blnGhostControl As Boolean
 
 On Error Resume Next
 If ctl.Tag = "X" Then Exit Sub
 If Not ((TypeOf ctl Is TextBox) Or (TypeOf ctl Is ComboBox) Or (TypeOf ctl Is OptionButton) Or (TypeOf ctl Is CheckBox)) Then Exit Sub
 
 If TypeOf ctl Is ComboBox Then
    If ctl.Tag = "T" Then
       Set ctlTxt = ctl.Parent.Controls("txt" & Mid(ctl.Name, 4))
       If Not (ctlTxt Is Nothing) Then
          If ctlTxt.Tag = "T" Then
             blnGhostControl = True
          Else
             blnGhostControl = False
          End If
       End If
    End If
 End If
 
 If cs = csView Then
    ctl.BackColor = vbButtonFace
    ctl.ForeColor = vbButtonText
    If (TypeOf ctl Is ComboBox) Or (TypeOf ctl Is TextBox) Then 'CC20051221 improve flow control
        ctl.Locked = True
    End If
    If (TypeOf ctl Is TreeView) Or (TypeOf ctl Is Toolbar) Or (TypeOf ctl Is ListView) Or (TypeOf ctl Is MonthView) Or _
        (TypeOf ctl Is ProgressBar) Or (TypeOf ctl Is Slider) Or (TypeOf ctl Is Toolbar) Or (TypeOf ctl Is TreeView) Then 'CC20051221 improve flow control
        ctl.BorderStyle = vbBSNone
    End If
    If (TypeOf ctl Is Binding) Or (TypeOf ctl Is CheckBox) Or (TypeOf ctl Is ComboBox) Or _
        (TypeOf ctl Is Image) Or (TypeOf ctl Is Label) Or (TypeOf ctl Is ListBox) Or _
        (TypeOf ctl Is MonthView) Or (TypeOf ctl Is PictureBox) Or (TypeOf ctl Is TextBox) Then  'CC20051221 improve flow control
        ctl.DataChanged = False
    End If
    If (TypeOf ctl Is OptionButton) Or (TypeOf ctl Is CheckBox) Then
       ctl.Enabled = False
    ElseIf TypeOf ctl Is ComboBox Then
       ctl.Enabled = False
       If blnGhostControl Then
          ctlTxt.Text = ctl.Text
          ctlTxt.Visible = True
          ctl.Visible = False
       End If
    End If
    
 ElseIf cs = csAdd Then
    ctl.BackColor = vbWhite
    ctl.ForeColor = vbButtonText
    If (TypeOf ctl Is ComboBox) Or (TypeOf ctl Is TextBox) Then 'CC20051221 improve flow control
        ctl.Locked = False
    End If
    If (TypeOf ctl Is TreeView) Or (TypeOf ctl Is Toolbar) Or (TypeOf ctl Is ListView) Or (TypeOf ctl Is MonthView) Or _
        (TypeOf ctl Is ProgressBar) Or (TypeOf ctl Is Slider) Or (TypeOf ctl Is Toolbar) Or (TypeOf ctl Is TreeView) Then 'CC20051221 improve flow control
        ctl.BorderStyle = vbFixedSingle
    End If
    ctl.Text = ""
    If (TypeOf ctl Is Binding) Or (TypeOf ctl Is CheckBox) Or (TypeOf ctl Is ComboBox) Or _
        (TypeOf ctl Is Image) Or (TypeOf ctl Is Label) Or (TypeOf ctl Is ListBox) Or _
        (TypeOf ctl Is MonthView) Or (TypeOf ctl Is PictureBox) Or (TypeOf ctl Is TextBox) Then 'CC20051221 improve flow control
        ctl.DataChanged = False
    End If
    If (TypeOf ctl Is OptionButton) Or (TypeOf ctl Is CheckBox) Then
       ctl.Enabled = True
       ctl.BackColor = vbButtonFace
    ElseIf TypeOf ctl Is ComboBox Then
       ctl.Enabled = True
       If blnGhostControl Then
          ctl.Text = ctlTxt.Text
          ctlTxt.Visible = False
          ctl.Visible = True
       End If
    End If
 
 ElseIf cs = csEdit Then
    ctl.BackColor = vbWindowBackground
    ctl.ForeColor = vbButtonText
    If (TypeOf ctl Is ComboBox) Or (TypeOf ctl Is TextBox) Then 'CC20051221 improve flow control
        ctl.Locked = False
    End If
    If (TypeOf ctl Is TreeView) Or (TypeOf ctl Is Toolbar) Or (TypeOf ctl Is ListView) Or (TypeOf ctl Is MonthView) Or _
        (TypeOf ctl Is ProgressBar) Or (TypeOf ctl Is Slider) Or (TypeOf ctl Is Toolbar) Or (TypeOf ctl Is TreeView) Then 'CC20051221 improve flow control
    ctl.BorderStyle = vbFixedSingle
    End If
    If (TypeOf ctl Is Binding) Or (TypeOf ctl Is CheckBox) Or (TypeOf ctl Is ComboBox) Or _
        (TypeOf ctl Is Image) Or (TypeOf ctl Is Label) Or (TypeOf ctl Is ListBox) Or _
        (TypeOf ctl Is MonthView) Or (TypeOf ctl Is PictureBox) Or (TypeOf ctl Is TextBox) Then 'CC20051221 improve flow control
        ctl.DataChanged = False
    End If
    If (TypeOf ctl Is OptionButton) Or (TypeOf ctl Is CheckBox) Then
       ctl.Enabled = True
       ctl.BackColor = vbButtonFace
    ElseIf TypeOf ctl Is ComboBox Then
       ctl.Enabled = True
       If blnGhostControl Then
          ctl.Text = ctlTxt.Text
          ctlTxt.Visible = False
          ctl.Visible = True
       End If
    End If
 ElseIf cs = csError Then
    ctl.BackColor = vbRed
    ctl.ForeColor = vbWhite
    If (TypeOf ctl Is OptionButton) Or (TypeOf ctl Is CheckBox) Then
       ctl.Enabled = True
       ctl.BackColor = vbButtonFace
       ctl.ForeColor = vbRed
    End If
 End If
End Sub



'* Indicates if the value of any TextBoxes or ComboBoxes has
'* changed on the specified form.
'*
'* NOTE : To work, controls *MUST* have been formatted using
'*        the FormatControls routine.
'*        Controls with a Tag property of "X" will be ignored.
'*        Does *NOT* work for OptionButtons
Public Function ChangedControls(frm As Form) As Boolean
 Dim ctl        As Control
 Dim blnChanged As Boolean
 
 For Each ctl In frm.Controls
     If (TypeOf ctl Is TextBox) Or (TypeOf ctl Is ComboBox) Then
        If ctl.Tag <> "X" Then
           If ctl.DataChanged = True Then
              blnChanged = True
           End If
        End If
     End If
 Next ctl
 ChangedControls = blnChanged
End Function



'* Sets the Text property of all Text box and Combo boxes
'* on a from to blank. Does not effect controls with a Tag of "X"
Public Sub BlankControls(frm As Form)
 Dim ctl As Control
 
 On Error Resume Next
 For Each ctl In frm.Controls
     If (TypeOf ctl Is TextBox) Or (TypeOf ctl Is ComboBox) Then
        If ctl.Tag <> "X" Then
           ctl.Text = ""
        End If
     End If
 Next ctl
End Sub


'* Returns the firs field found that was flagged
'* as being in error
Public Function FindControlInError(frm As Form) As Control
 Dim ctlLoop     As Control
 Dim ctlFirst    As Control
 Dim lngTabIndex As Long
  
 On Error Resume Next
 lngTabIndex = 99999
 For Each ctlLoop In frm.Controls
     If (TypeOf ctlLoop Is TextBox) Or (TypeOf ctlLoop Is ComboBox) Then
        If ctlLoop.Tag <> "X" Then
           If (ctlLoop.Enabled = True) And (ctlLoop.BackColor = vbRed) Then
              If ctlLoop.TabIndex < lngTabIndex Then
                 Set ctlFirst = ctlLoop
                 lngTabIndex = ctlLoop.TabIndex
              End If
           End If
        End If
     End If
 Next ctlLoop
 Set FindControlInError = ctlFirst
End Function


'* Centers all Frames and Search controls on a form
'* If the form is not big enough, the form is resized
Public Sub CenterControls(frm As Form)
 Dim ctl        As Control
 Dim strFrmName As String
 Dim lngTop     As Long
 Dim lngBot     As Long
 Dim lngLeft    As Long
 Dim lngRight   As Long
 Dim lngXTop    As Long
 Dim lngXLeft   As Long
  
 On Error Resume Next
 
 strFrmName = frm.Name
 lngTop = 999999
 lngLeft = 999999
 For Each ctl In frm.Controls
     If (TypeOf ctl Is Frame) Or (TypeOf ctl Is Search) Then
        If ctl.Container.Name = strFrmName Then
           If ctl.Top < lngTop Then
              lngTop = ctl.Top
           End If
           If ctl.Top + ctl.Height > lngBot Then
              lngBot = ctl.Top + ctl.Height
           End If
           If ctl.Left < lngLeft Then
              lngLeft = ctl.Left
           End If
           If ctl.Width + ctl.Left > lngRight Then
              lngRight = ctl.Width + ctl.Left
           End If
        End If
     End If
 Next ctl
 
 lngXTop = (frm.ScaleHeight / 2) - (lngTop + (lngBot - lngTop) / 2)
 lngXLeft = (frm.ScaleWidth / 2) - (lngLeft + (lngRight - lngLeft) / 2)
 If (lngTop + lngXTop) < 0 Then
    lngXTop = lngTop * -1
 End If
 If (lngLeft + lngXLeft) < 0 Then
    lngXLeft = lngLeft * -1
 End If
 
 
 For Each ctl In frm.Controls
     If (TypeOf ctl Is Frame) Or (TypeOf ctl Is Search) Then
        If ctl.Container.Name = strFrmName Then
           ctl.Move ctl.Left + lngXLeft, ctl.Top + lngXTop
        End If
     End If
 Next ctl
 If (lngBot - lngTop + 400 > frm.Height) Then
    frm.Height = lngBot - lngTop + 400
 End If
 If (lngRight - lngLeft + 190 > frm.Width) Then
    frm.Width = lngRight - lngLeft + 190
 End If
 
 If (MDIMain.Height < lngBot - lngTop + 400) Or _
    (MDIMain.Width < lngRight - lngLeft + 190) Then
     frm.WindowState = vbNormal
     frm.Move 0, 0, lngRight - lngLeft + 190, lngBot - lngTop + 400
 End If
 
End Sub


Public Function FormatDate(ByVal strDate As String) As String
 FormatDate = Format(strDate, "dd/mm/yyyy")
End Function

Public Function FormatDBDate(ByVal strField As String) As String
 FormatDBDate = "DATE_FORMAT(" & strField & ",'%d-%m-%Y')"
End Function


'* Returns TRUE if the NicHndle specifeid matches the Type
'* specified. Checks uses the domain_contact table
Public Function IsNicHandleType(ByVal strNicHandle As String, ByVal strType As String) As Boolean
 Dim strSQL As String
 Dim rec    As ADODB.Recordset
               
 On Error GoTo IsNicHandleTypeERROR
 
 strSQL = "SELECT Contact_NH "
 strSQL = strSQL & "FROM Contact "
 strSQL = strSQL & "WHERE Contact_NH=" & CDBText(strNicHandle) & " "
 strSQL = strSQL & "AND Type=" & CDBText(strType) & " "
 strSQL = strSQL & "LIMIT 1 "
 
 DB.Execute strSQL, rec
 If Not rec.EOF Then
    IsNicHandleType = True
 Else
    IsNicHandleType = False
 End If
 
Exit Function
IsNicHandleTypeERROR:
 IsNicHandleType = False
End Function


'* Returns TRUE if the NicHndle specifeid matches a Nic Handel
'* in the system. Checks uses the NicHandle table
Public Function IsNicHandle(ByVal strNicHandle As String) As Boolean
 Dim strSQL As String
 Dim rec    As ADODB.Recordset
               
 On Error GoTo IsNicHandleERROR
 
 strSQL = "SELECT Nic_Handle "
 strSQL = strSQL & "FROM NicHandle "
 strSQL = strSQL & "WHERE Nic_Handle=" & CDBText(strNicHandle) & " "
 
 DB.Execute strSQL, rec
 If Not rec.EOF Then
    IsNicHandle = True
 Else
    IsNicHandle = False
 End If
 
Exit Function
IsNicHandleERROR:
 IsNicHandle = False
End Function

'Celina Leong 20-09-2002
'* Returns TRUE if the NicHndle and account no specifeid matches a Nic Handle
'* in the system. Checks uses the NicHandle table
Public Function IsAccNicHandle(ByVal strAccName As Long, ByVal strBilcon As String) As Boolean
 Dim strSQL As String
 Dim rec    As ADODB.Recordset

 
 
 
 On Error GoTo IsAccNicHandleERROR
 
 strSQL = "SELECT A_Number, Nic_Handle "
 strSQL = strSQL & "FROM NicHandle "
 strSQL = strSQL & "WHERE A_Number=" & strAccName & " "
 strSQL = strSQL & "AND Nic_Handle=" & CDBText(strBilcon) & " "
 
 DB.Execute strSQL, rec
 If Not rec.EOF Then
    IsAccNicHandle = True
 Else
    IsAccNicHandle = False
 End If
 
Exit Function
IsAccNicHandleERROR:
 IsAccNicHandle = False
End Function

Public Function GetEmail(ByVal strNicHandle) As String
 Dim strSQL As String
 Dim rec    As ADODB.Recordset
 
 On Error GoTo GetEmailERROR
 
 strSQL = "SELECT NH_Email FROM NicHandle WHERE Nic_Handle=" & CDBText(strNicHandle)
 DB.Execute strSQL, rec
 GetEmail = rec.Fields("NH_Email")
Exit Function
GetEmailERROR:
End Function

'* Returns the NicHandles name
Public Function GetName(ByVal strNicHandle) As String
 Dim strSQL As String
 Dim rec    As ADODB.Recordset
 
 On Error GoTo GetNameERROR
 If strNicHandle = "" Then Exit Function
 
 strSQL = "SELECT NH_Name FROM NicHandle WHERE Nic_Handle=" & CDBText(strNicHandle)
 DB.Execute strSQL, rec
 
 GetName = rec.Fields("NH_Name")
Exit Function
GetNameERROR:
End Function



'* Finds the specified ItemData within a combo box or listbox
'* and returns its ListIndex. Returns -1 if not found
Public Function FindItemData(cbo As Control, ByVal lngItemData As Long) As Long
 Dim lngLoop  As Long
 Dim lngCount As Long
 
 lngCount = cbo.Listcount - 1
 For lngLoop = 0 To lngCount
     If cbo.ItemData(lngLoop) = lngItemData Then
        FindItemData = lngLoop
        Exit Function
     End If
 Next lngLoop
'celina leong 24-09-2002 Returns 0 if not found
'FindItemData = -1
 FindItemData = 0
End Function


Public Sub SetComboText(cbo As ComboBox, ByVal strText As String)
 Dim lngLoop  As Long
 Dim lngCount As Long
 
 lngCount = cbo.Listcount - 1
 For lngLoop = 0 To lngCount
     If cbo.List(lngLoop) = strText Then
        cbo.ListIndex = lngLoop
        Exit Sub
     End If
 Next lngLoop
 cbo.ListIndex = -1
End Sub


''Public Sub FillCountyCombo(cbo As ComboBox)
'' cbo.Clear
'' cbo.AddItem "Co. Antrim"
'' cbo.AddItem "Co. Armagh"
'' cbo.AddItem "Co. Carlow"
'' cbo.AddItem "Co. Cavan"
'' cbo.AddItem "Co. Clare"
'' cbo.AddItem "Co. Cork"
'' cbo.AddItem "Co. Derry"
'' cbo.AddItem "Co. Donegal"
'' cbo.AddItem "Co. Down"
'' cbo.AddItem "Co. Dublin"
'' cbo.AddItem "Co. Fermanagh"
'' cbo.AddItem "Co. Galway"
'' cbo.AddItem "Co. Kerry"
'' cbo.AddItem "Co. Kildare"
'' cbo.AddItem "Co. Kilkenny"
'' cbo.AddItem "Co. Laois"
'' cbo.AddItem "Co. Leitrim"
'' cbo.AddItem "Co. Limerick"
'' cbo.AddItem "Co. Longford"
'' cbo.AddItem "Co. Louth"
'' cbo.AddItem "Co. Mayo"
'' cbo.AddItem "Co. Meath"
'' cbo.AddItem "Co. Monaghan"
'' cbo.AddItem "Co. Offaly"
'' cbo.AddItem "Co. Roscommon"
'' cbo.AddItem "Co. Sligo"
'' cbo.AddItem "Co. Tipperary"
'' cbo.AddItem "Co. Tyrone"
'' cbo.AddItem "Co. Waterford"
'' cbo.AddItem "Co. Westmeath"
'' cbo.AddItem "Co. Wexford"
'' cbo.AddItem "Co. Wicklow"
'' cbo.AddItem "N/A"
''
''
 'cbo.ListIndex = 3
''End Sub

Public Sub FillCountyCombo(cbo As ComboBox, ByVal CountryCode As Integer)
Dim strSQL As String
 Dim rec    As ADODB.Recordset
 
 On Error GoTo FillCountyComboERROR
 
 strSQL = "SELECT County "
 strSQL = strSQL & "FROM Counties "
 strSQL = strSQL & "WHERE CountryCode=" & CountryCode & " "
 strSQL = strSQL & "ORDER BY County ASC "
 DB.Execute strSQL, rec
 
 cbo.Clear
 
 Do Until rec.EOF
    cbo.AddItem rec.Fields("County")
   
    rec.MoveNext
 Loop
 
Exit Sub
FillCountyComboERROR:
 DB.ShowError "Unable to load Countries"

End Sub

'Celina Leong Phoenix1.1 08/04/03
''Public Sub FillUSStateCombo(cbo As ComboBox)
'' Dim strSQL As String
'' Dim rec    As ADODB.Recordset
''
'' On Error GoTo FillUSStateComboERROR
''
'' strSQL = "SELECT State "
'' strSQL = strSQL & "FROM USState "
'' strSQL = strSQL & "ORDER BY State ASC "
'' DB.Execute strSQL, rec
''
'' cbo.Clear
''
'' Do Until rec.EOF
''    cbo.AddItem rec.Fields("State")
''    rec.MoveNext
'' Loop
''Exit Sub
''FillUSStateComboERROR:
'' DB.ShowError "Unable to load US State"
''End Sub
''Public Sub FillUKCountyCombo(cbo As ComboBox)
'' Dim strSQL As String
'' Dim rec    As ADODB.Recordset
''
'' On Error GoTo FillUKCountyComboERROR
''
'' strSQL = "SELECT County "
'' strSQL = strSQL & "FROM UKCounty "
'' strSQL = strSQL & "ORDER BY County ASC "
'' DB.Execute strSQL, rec
''
'' cbo.Clear
''
'' Do Until rec.EOF
''    cbo.AddItem rec.Fields("County")
''    rec.MoveNext
'' Loop
''Exit Sub
''FillUKCountyComboERROR:
'' DB.ShowError "Unable to load UK Counties"
''End Sub
'---------------------------------------------------------


Public Sub FillCountryCombo(cbo As ComboBox)
 Dim strSQL As String
 Dim rec    As ADODB.Recordset
 
 On Error GoTo FillCountryComboERROR
 
 strSQL = "SELECT Country "
 strSQL = strSQL & "FROM Countries "
 strSQL = strSQL & "ORDER BY Country ASC "
 DB.Execute strSQL, rec
 
 cbo.Clear
 
 Do Until rec.EOF
    cbo.AddItem rec.Fields("Country")
   
    rec.MoveNext
 Loop
 
Exit Sub
FillCountryComboERROR:
 DB.ShowError "Unable to load Countries"
End Sub

''Celina Leong Phoenix1.1 14/04/03 Read tariff from table
''Public Sub FillTariffCombo(cbo As ComboBox)
'' cbo.Clear
'' cbo.AddItem "Silver"
'' cbo.AddItem "Gold"
'' cbo.AddItem "Platinum"
''End Sub

Public Sub FillTariffCombo(cbo As ComboBox)
'Celina Leong Phoenix1.1 24/04/03 Change tariff

Dim strSQL As String
 Dim rec    As ADODB.Recordset
 
 On Error GoTo FilltariffStatusComboERROR
 
 strSQL = "SELECT Description "
 strSQL = strSQL & "FROM Tariff "
 DB.Execute strSQL, rec
 
 cbo.Clear
 
 Do Until rec.EOF
    cbo.AddItem rec.Fields("Description")
    rec.MoveNext
 Loop
Exit Sub
FilltariffStatusComboERROR:
 DB.ShowError "Unable to load Tariff Status"
End Sub
''------------------------------------------------------


Public Sub DefaultTarif(cbo As ComboBox)
 cbo.Text = "Silver"
End Sub

Public Sub FillStatusCombo(cbo As ComboBox)
 cbo.Clear
 cbo.AddItem "Active"
 cbo.AddItem "Deleted"
 cbo.AddItem "Suspended"
End Sub
'Celina Leong Phoenix1.1 02/04/03
Public Sub FillComboVatStatus(cbo As ComboBox)
 cbo.Clear
 cbo.AddItem "Y"
 cbo.AddItem "N"
End Sub

Public Sub FillComboBillStatus(cbo As ComboBox)
Dim strSQL As String
 Dim rec    As ADODB.Recordset
 
 On Error GoTo FillBillStatusComboERROR
 'Celina Leong Phoenix1.1 24/04/03 Change billstatus
 strSQL = "SELECT Description "
 strSQL = strSQL & "FROM BillStatus "
 DB.Execute strSQL, rec
 
 cbo.Clear
 
 Do Until rec.EOF
    cbo.AddItem rec.Fields("Description")
    rec.MoveNext
 Loop
Exit Sub
FillBillStatusComboERROR:
 DB.ShowError "Unable to load Bill Status"
End Sub

'''''''''''''''''''''''''''''''''''''''''''''''''''
'' FillImageComboBill Added for release 1.2 By David Gildea
'' 31/08/04
'''''''''''''''''''''''''''''''''''''''''''''''''''


Public Sub FillImageComboBillStatus(cbo As ImageCombo, img As ImageList)
Dim strSQL As String
Dim rec    As ADODB.Recordset
 
 On Error GoTo FillBillStatusComboERROR
 'Celina Leong Phoenix1.1 24/04/03 Change billstatus
 strSQL = "SELECT Status, Description, DetailedDescription, Colour "
 strSQL = strSQL & "FROM BillStatus "
 DB.Execute strSQL, rec
 cbo.ComboItems.Clear
 Set cbo.ImageList = img
 Dim objNewItem As ComboItem
 
 Do Until rec.EOF
    Set objNewItem = cbo.ComboItems.Add(1, rec.Fields("Description"), rec.Fields("DetailedDescription"), rec.Fields("Colour").Value)
    rec.MoveNext
 Loop

 
Exit Sub
FillBillStatusComboERROR:
 DB.ShowError "Unable to load Bill Status"
End Sub


'''''''''''''''''''''''''''''''''''''''''''''''''''
Public Sub FillComboClikPay(cbo As ComboBox)
 Dim strSQL As String
 Dim rec    As ADODB.Recordset
 
 On Error GoTo FillClikPayComboERROR
 'Celina Leong Phoenix1.1 24/04/03 Change clikpay
 strSQL = "SELECT Description "
 strSQL = strSQL & "FROM ClikPaid "
' strSQL = strSQL & "ORDER BY Description ASC "
 DB.Execute strSQL, rec
 
 cbo.Clear
 
 Do Until rec.EOF
    cbo.AddItem rec.Fields("Description")
    rec.MoveNext
 Loop
Exit Sub
FillClikPayComboERROR:
 DB.ShowError "Unable to load Clik Pay"
End Sub
'-----------------------------------------
Public Sub DefaultStatus(cbo As ComboBox)
 cbo.Text = "Active"
End Sub

Public Sub FillMonthCombo(cbo As ComboBox)
 cbo.Clear
 cbo.AddItem "January"
 cbo.AddItem "February"
 cbo.AddItem "March"
 cbo.AddItem "April"
 cbo.AddItem "May"
 cbo.AddItem "June"
 cbo.AddItem "July"
 cbo.AddItem "August"
 cbo.AddItem "September"
 cbo.AddItem "October"
 cbo.AddItem "November"
 cbo.AddItem "December"
End Sub


Public Sub FillAdminStatusCombo(cbo As ComboBox, Optional ByVal blnAddAllItem As Boolean = False)
 Dim strSQL As String
 Dim rec    As ADODB.Recordset
 
 On Error GoTo FillAdminStatusComboERROR
 
 strSQL = "SELECT * "
 strSQL = strSQL & "FROM TicketAdminStatus "
 strSQL = strSQL & "ORDER BY Status ASC "
 DB.Execute strSQL, rec
 
 cbo.Clear
 
 If blnAddAllItem Then
    cbo.AddItem gstrComboAllText
    cbo.ItemData(cbo.NewIndex) = glngAllsItemData
    cbo.ListIndex = 0
 End If
 Do Until rec.EOF
    cbo.AddItem rec.Fields("Description")
    cbo.ItemData(cbo.NewIndex) = rec.Fields("Status")
    rec.MoveNext
 Loop
Exit Sub
FillAdminStatusComboERROR:
 DB.ShowError "Unable to load Admin Status Codes"
End Sub


Public Sub FillTechStatusCombo(cbo As ComboBox, Optional ByVal blnAddAllItem As Boolean = False)
 Dim strSQL As String
 Dim rec    As ADODB.Recordset
 
 On Error GoTo FillTechStatusComboERROR
 
 strSQL = "SELECT * "
 strSQL = strSQL & "FROM TicketTechStatus "
 strSQL = strSQL & "ORDER BY Status ASC "
 DB.Execute strSQL, rec
 
 cbo.Clear
 
 If blnAddAllItem Then
    cbo.AddItem gstrComboAllText
    cbo.ItemData(cbo.NewIndex) = glngAllsItemData
    cbo.ListIndex = 0
 End If
 Do Until rec.EOF
    cbo.AddItem rec.Fields("Description")
    cbo.ItemData(cbo.NewIndex) = rec.Fields("Status")
    rec.MoveNext
 Loop
Exit Sub
FillTechStatusComboERROR:
 DB.ShowError "Unable to load Tech Status Codes"
End Sub

Public Sub FillClassCombo(cbo As ComboBox)
 cbo.Clear
 cbo.AddItem "Natural Person"
 cbo.AddItem "Body Corporate (Ltd,PLC,Company)"
 cbo.AddItem "Sole Trader"
 cbo.AddItem "School/Educational Institution"
 cbo.AddItem "Unincorporated Association"
 cbo.AddItem "Statutory Body"
 cbo.AddItem "Constitutional Body"
 cbo.AddItem "Discretionary Applicant"
 cbo.AddItem "Charity" 'JMcA added 21.11.06 BATCH requirement 1.1
End Sub


Public Sub FillCategoryCombo(cboCat As ComboBox, cboClass As ComboBox)
 Dim lngIndex As Long
 Dim lngLoop  As Long
 
 cboCat.Clear
 lngIndex = cboClass.ListIndex
 For lngLoop = 0 To mlngCatCount
     If mblnClassCat(lngIndex, lngLoop) Then
        cboCat.AddItem mstrCatNames(lngLoop)
     End If
 Next lngLoop
End Sub


Public Sub FillTicketFailureReasonCombo(cbo As ComboBox, ByVal lngDataField As Long, Optional ByVal blnAddNoneItem As Boolean = False)
 Dim strSQL As String
 Dim rec    As ADODB.Recordset
 
 On Error GoTo FillFailureReasonComboERROR
 
 strSQL = "SELECT FailCd, Description "
 strSQL = strSQL & "FROM TicketFailCd "
 strSQL = strSQL & "WHERE Data_Field=" & lngDataField & " "
 strSQL = strSQL & "ORDER BY FailCd ASC "
 DB.Execute strSQL, rec
 
 cbo.Clear
 
 If blnAddNoneItem Then
    cbo.AddItem gstrComboNoneText
    cbo.ItemData(cbo.NewIndex) = glngNonesItemData
 End If
 Do Until rec.EOF
    cbo.AddItem rec.Fields("Description")
    cbo.ItemData(cbo.NewIndex) = rec.Fields("FailCd")
    rec.MoveNext
 Loop
Exit Sub
FillFailureReasonComboERROR:
 DB.ShowError "Unable to load Failure Reason Codes"
End Sub


Public Sub FillNHFailureReasonCombo(cbo As ComboBox, ByVal lngDataField As Long, Optional ByVal blnAddNoneItem As Boolean = False)
 FillTicketFailureReasonCombo cbo, lngDataField, blnAddNoneItem
End Sub


Private Sub FillClassCatArray()
 FillClassCatLine 0, 1, 0, 0, 1, 0, 0, 0, 0, 1, 0, 1
 FillClassCatLine 1, 0, 0, 1, 1, 1, 0, 1, 0, 0, 0, 1
 FillClassCatLine 2, 0, 1, 0, 1, 1, 0, 1, 0, 0, 0, 1
 FillClassCatLine 3, 0, 0, 0, 1, 0, 0, 1, 1, 0, 0, 1
 FillClassCatLine 4, 0, 0, 0, 1, 1, 0, 1, 0, 0, 1, 1
 FillClassCatLine 5, 0, 0, 0, 1, 0, 1, 1, 0, 0, 0, 1
 FillClassCatLine 6, 0, 0, 0, 1, 0, 1, 1, 0, 0, 0, 1
 FillClassCatLine 7, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1
 FillClassCatLine 8, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 'JMcA added for charity capture

 mstrCatNames(0) = "Personal Name"
 mstrCatNames(1) = "Personal Trading Name"
 mstrCatNames(2) = "Corporate Name"
 mstrCatNames(3) = "Registered Trade Mark Name"
 mstrCatNames(4) = "Registered Business Name"
 mstrCatNames(5) = "State Agency Name"
 mstrCatNames(6) = "Publication Name"
 mstrCatNames(7) = "School/Educational Institution Name"
 mstrCatNames(8) = "Politician's Name"
 mstrCatNames(9) = "Unincorporated Association Name"
 mstrCatNames(10) = "Discretionary Name"
End Sub


Private Sub FillClassCatLine(ByVal lngClass As Long, ParamArray vntCat() As Variant)
 Dim lngLoop  As Long
 Dim lngCount As Long
 
 If lngClass < 0 Or lngClass > mlngClassCount Then Exit Sub
 lngCount = UBound(vntCat)
 If lngCount > mlngCatCount Then lngCount = mlngCatCount
 For lngLoop = 0 To lngCount
    mblnClassCat(lngClass, lngLoop) = CBool(vntCat(lngLoop))
 Next lngLoop
End Sub

Private Function Valid_License() As Boolean
'David O'Leary - 4-Nov-2002
'Check that a license file on the shared drive exists.  Return FALSE if there is an
'error, and true if the file is found.

Dim iLicenseFile As Integer

'If there is an error opening the file, go to the error handler
On Error GoTo FileNotLocated

'Get the next free file
iLicenseFile = FreeFile

'Open the license file
Open "P:/lic.dll" For Input As #iLicenseFile

'if the file was opened successfully, close the file
Close #iLicenseFile

'Set the return value to TRUE
Valid_License = True

Exit Function

FileNotLocated:

    Valid_License = False
    MsgBox "Invalid Software License", vbCritical, "License"
    

End Function

Public Sub CheckDateFormat()
    ' tsmyth oct2003
    ' This function check the format of dates on the user's PC
    Dim sDateFormat As String
    ' On Error GoTo vbErrorHandler
    sDateFormat = GetLocaleString(LOCALE_SSHORTDATE)

    ' Make sure we always have YYYY format for y2k
    'If InStr(1, sDateFormat, "YYYY", vbTextCompare) = 0 Then
    '    Replace sDateFormat, "YY", "YYYY"
    'End If
    ' DateFormat = sDateFormat
   
  '  If Left(UCase(sDateFormat), 4) <> "DD/M" _
  '  And Left(UCase(sDateFormat), 4) <> "DD.M" _
  '  And Left(UCase(sDateFormat), 4) <> "DD-M" _
  '  And Left(UCase(sDateFormat), 3) <> "D/M" _
  '  And Left(UCase(sDateFormat), 3) <> "D-M" _
  '  And Left(UCase(sDateFormat), 3) <> "D.M" _
  '  Then
  '     MsgBox "The Date Format on this PC is incompatible: " & sDateFormat & vbCr _
  '       & "Use Control Panel to set a UK-English Date Format, eg DD-MM-YYYY ", vbCritical, "Date Format Error"
  '     End
  '  End If
    
    Dim xFmt As String
    xFmt = UCase(sDateFormat)
    
    If Left(xFmt, 1) = "D" _
    And (Mid(xFmt, 3, 1) = "M" Or Mid(xFmt, 4, 1) = "M") _
    And InStr(xFmt, "Y") > 2 _
    Then
       
    Else
       MsgBox "The Date Format on this PC is incompatible: " & sDateFormat & vbCr _
         & "Use Control Panel to set a UK-English Date Format, eg DD-MM-YYYY ", vbCritical, "Date Format Error"
       End
    End If
    

End Sub
Private Function GetLocaleString(ByVal lLocaleNum As Long) As String
' tsmyth oct2003
' Generic routine to get the locale string from the Operating system.
    Dim lBuffSize As String
    Dim sBuffer As String
    Dim lRet As Long

    lBuffSize = 256
    sBuffer = String$(lBuffSize, vbNullChar)

    'Get the information from the registry
    lRet = GetLocaleInfo(LOCALE_USER_DEFAULT, lLocaleNum, sBuffer, lBuffSize)
    'If lRet > 0 then success - lret is the size of the string returned
    If lRet > 0 Then
        GetLocaleString = Left$(sBuffer, lRet - 1)
    End If
End Function

'JMcA added BATCH req. 1.3 04.12.06
Public Function BillingConOtherDomain(sDomain, sAdminContact1, sAdminContact2) As Boolean
Dim sSQL As String
Dim rsResult As ADODB.Recordset


If (sAdminContact2 = "") Then
 
 sSQL = "Select * from Contact" & _
        " where Contact_NH = '" & sAdminContact1 & "'" & _
        " and D_Name <> '" & sDomain & "'" & _
        " and Type = 'B'"

Else

sSQL = "Select * from Contact" & _
        " where ( Contact_NH = '" & sAdminContact1 & "'" & _
        " or Contact_NH = '" & sAdminContact2 & "')" & _
        " and D_Name <> '" & sDomain & "'" & _
        " and Type = 'B'"
End If

    Set rsResult = New ADODB.Recordset
    DB.Execute sSQL, rsResult
            
    If Not rsResult.EOF Then
       BillingConOtherDomain = True
    End If
    
    Set rsResult = Nothing
    
End Function
Public Sub FlagFaxForDomain(sDomain As String, Lblfax As Label, Optional bShowDom As Boolean = False)
Dim rsFax As ADODB.Recordset
Dim TheDay, TheMonthName, TheYear As String
On Error GoTo DBError:

    sSQL = "USE faxlog;"
    DB.Execute sSQL
    
    sSQL = "SELECT date FROM faxlog " & _
           "WHERE domain like " & CDBText(sDomain) & " " & _
           "AND date >= DATE_SUB(CURDATE(),INTERVAL 60 DAY);"
           
    Set rsFax = New ADODB.Recordset
    rsFax.Open sSQL, CnPhoenix, adOpenDynamic, adLockOptimistic
    
    sSQL = "USE phoenixdb;"
    DB.Execute sSQL
    
 
    If Not rsFax.EOF Then
        'get the day and format it
        TheDay = Day(rsFax.Fields("date"))
          
        TheMonthName = Month(rsFax.Fields("date"))
        TheMonthName = MonthName(TheMonthName)
        
        Select Case TheDay
        
        Case 1, 21, 31
        TheDay = TheDay & "st"
        
        Case 2, 22
        TheDay = TheDay & "nd"
                
        Case 3, 23
        TheDay = TheDay & "rd"
        
        Case 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 24, 25, 26, 27, 28, 29, 30
        TheDay = TheDay & "th"
        
        End Select
        
        'now the year
        TheYear = Year(rsFax.Fields("date"))
    
       Lblfax.Visible = True
       
       If bShowDom Then
          Lblfax.Caption = "Fax Received : " & TheDay & " " & TheMonthName & " " & TheYear & "  [ " & sDomain & " ]"
       Else
          Lblfax.Caption = "Fax Received : " & TheDay & " " & TheMonthName & " " & TheYear
       End If
    
       
       
    Else
       Lblfax.Visible = False
       Lblfax.Caption = ""
    End If
    
    rsFax.Close
 
Exit Sub
DBError:
    DB.ShowError "Error reading Faxlog"
    On Error Resume Next
End Sub


