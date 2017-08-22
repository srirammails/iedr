VERSION 5.00
Begin VB.UserControl Search 
   ClientHeight    =   1290
   ClientLeft      =   0
   ClientTop       =   0
   ClientWidth     =   6825
   ScaleHeight     =   1290
   ScaleWidth      =   6825
   Begin VB.Frame fraSearch 
      Caption         =   "Search"
      ForeColor       =   &H00000000&
      Height          =   1215
      Left            =   0
      TabIndex        =   7
      Top             =   0
      Width           =   6735
      Begin VB.ComboBox txtSearch 
         Height          =   315
         Left            =   3120
         TabIndex        =   9
         Top             =   360
         Width           =   2055
      End
      Begin VB.ComboBox cboSearchData 
         Height          =   315
         Left            =   2880
         Style           =   2  'Dropdown List
         TabIndex        =   8
         Top             =   720
         Visible         =   0   'False
         Width           =   1815
      End
      Begin VB.CommandButton cmdSearch 
         Caption         =   "&Search"
         Height          =   285
         Left            =   5400
         TabIndex        =   5
         Top             =   360
         Width           =   1095
      End
      Begin VB.TextBox txtSearchOld 
         Height          =   315
         Left            =   3120
         TabIndex        =   4
         Top             =   360
         Width           =   2055
      End
      Begin VB.ComboBox cboSearchResult 
         Height          =   315
         Left            =   3120
         TabIndex        =   6
         Top             =   720
         Width           =   3375
      End
      Begin VB.OptionButton optSearchType 
         Caption         =   "Option 3"
         Height          =   255
         Index           =   3
         Left            =   1680
         TabIndex        =   2
         Top             =   360
         Width           =   1455
      End
      Begin VB.OptionButton optSearchType 
         Caption         =   "Option 1"
         Height          =   255
         Index           =   1
         Left            =   120
         TabIndex        =   0
         Top             =   360
         Value           =   -1  'True
         Width           =   2655
      End
      Begin VB.OptionButton optSearchType 
         Caption         =   "Option 4"
         Height          =   255
         Index           =   4
         Left            =   1680
         TabIndex        =   3
         Top             =   720
         Width           =   1455
      End
      Begin VB.OptionButton optSearchType 
         Caption         =   "Option 2"
         Height          =   255
         Index           =   2
         Left            =   120
         TabIndex        =   1
         Top             =   720
         Width           =   2655
      End
   End
End
Attribute VB_Name = "Search"
Attribute VB_GlobalNameSpace = False
Attribute VB_Creatable = True
Attribute VB_PredeclaredId = False
Attribute VB_Exposed = False
Option Explicit

'***************************************************
'*                                                 *
'* Written By : Brian Devlin                       *
'* Function   : Control to handle the searches     *
'*              within the system to keep the forms*
'*              code simple by just supplying the  *
'*              control with SQL                   *
'***************************************************

Const mstrPropNameEnabled    As String = "Enabled"
Const mstrPropNameOpt1Cap    As String = "Option1Caption"
Const mstrPropNameOpt2Cap    As String = "Option2Caption"
Const mstrPropNameOpt3Cap    As String = "Option3Caption"
Const mstrPropNameOpt4Cap    As String = "Option4Caption"
Const mstrPropNameOptCount   As String = "OptionCount"
Const mstrPropNameDefOpt     As String = "DefaultOption"
Const mstrPropNameAllowNoCri As String = "AllowNoCri"

Const mblnPropDefEnabled     As Boolean = True
Const mstrPropDefOpt1Cap     As String = "Option 1"
Const mstrPropDefOpt2Cap     As String = "Option 2"
Const mstrPropDefOpt3Cap     As String = "Option 3"
Const mstrPropDefOpt4Cap     As String = "Option 4"
Const mlngPropDefOptCount    As Long = 4
Const mlngPropDefDefOpt      As Long = 1
Const mblnPropDefAllowNoCri  As Boolean = False

Const mlngControlHeight      As Long = 1290
Const mlngControlMinWidth    As Long = 6500

Dim mlngOptionCount   As Long
Dim mlngDefaultOption As Long
Dim mlngSelected      As Long
Dim mblnHasData       As Boolean
Dim mblnAllowNoCri    As Boolean

Public Event SelectedResult(ByVal strResult As String, ByVal strData As String, ByVal lngOptionSelected As Long)
Public Event GetSQL(ByVal lngOptionSelected As Long, ByRef strSQL As String, ByVal strSearch As String)


Public Property Get OptionCaption(ByVal Index As Long) As String
 If Index >= 1 And Index <= 4 Then
   OptionCaption = optSearchType(Index).Caption
 End If
End Property

Public Property Let OptionCaption(ByVal Index As Long, ByVal strNewValue As String)
 If Index >= 1 And Index <= 4 Then
    optSearchType(Index).Caption = strNewValue
 End If
End Property


Public Property Get Option1Caption() As String
Attribute Option1Caption.VB_Description = "Caption for Option 1"
 Option1Caption = OptionCaption(1)
End Property
Public Property Get Option2Caption() As String
Attribute Option2Caption.VB_Description = "Caption for Option 2"
 Option2Caption = OptionCaption(2)
End Property
Public Property Get Option3Caption() As String
Attribute Option3Caption.VB_Description = "Caption for Option 3"
 Option3Caption = OptionCaption(3)
End Property
Public Property Get Option4Caption() As String
Attribute Option4Caption.VB_Description = "Caption for Option 4"
 Option4Caption = OptionCaption(4)
End Property

Public Property Let Option1Caption(ByVal strNewValue As String)
 OptionCaption(1) = strNewValue
End Property
Public Property Let Option2Caption(ByVal strNewValue As String)
 OptionCaption(2) = strNewValue
End Property
Public Property Let Option3Caption(ByVal strNewValue As String)
 OptionCaption(3) = strNewValue
End Property
Public Property Let Option4Caption(ByVal strNewValue As String)
 OptionCaption(4) = strNewValue
End Property


Public Property Get OptionCount() As Long
Attribute OptionCount.VB_Description = "Indicates how many Options to display 2..4"
 OptionCount = mlngOptionCount
End Property

Public Property Let OptionCount(ByVal lngNewValue As Long)
 Dim lngLoop As Long
 
 If lngNewValue > 1 And lngNewValue <= 4 Then
    mlngOptionCount = lngNewValue
    
    For lngLoop = 1 To 4
        optSearchType(lngLoop).Visible = (lngLoop - 1 < lngNewValue)
    Next lngLoop
    If mlngDefaultOption > lngNewValue Then
       DefaultOption = 1
    End If
 End If
End Property


Public Property Get DefaultOption() As Long
Attribute DefaultOption.VB_Description = "Indicates which Option will de first selected"
 DefaultOption = mlngDefaultOption
End Property

Public Property Let DefaultOption(ByVal lngNewValue As Long)
 If lngNewValue >= 1 And lngNewValue <= 4 Then
    If lngNewValue <= OptionCount Then
       mlngDefaultOption = lngNewValue
       mlngSelected = lngNewValue
       optSearchType(lngNewValue).Value = True
    End If
 End If
End Property



Public Property Get SelectedOption() As Long
 SelectedOption = mlngSelected
End Property


Public Property Get Enabled() As Boolean
Attribute Enabled.VB_ProcData.VB_Invoke_Property = ";Appearance"
 Enabled = FraSearch.Enabled
End Property

Public Property Let Enabled(ByVal blnNewValue As Boolean)
 FraSearch.Enabled = blnNewValue
 cmdSearch.Enabled = blnNewValue
End Property


Public Property Get AllowNoCriteria() As Boolean
Attribute AllowNoCriteria.VB_Description = "Sets/Returns if the user can search when no search string has been specified"
 AllowNoCriteria = mblnAllowNoCri
End Property

Public Property Let AllowNoCriteria(ByVal blnNewValue As Boolean)
 mblnAllowNoCri = blnNewValue
 If Not mblnAllowNoCri Then
    If Len(txtSearch) = 0 Then
       cmdSearch.Enabled = False
    Else
       cmdSearch.Enabled = True
    End If
 Else
    cmdSearch.Enabled = True
 End If
End Property


Public Property Get SearchString() As String
Attribute SearchString.VB_Description = "Sets/Returns Text used by the user tosearch"
Attribute SearchString.VB_MemberFlags = "400"
 SearchString = txtSearch
End Property

Public Property Let SearchString(ByVal strNewValue As String)
 txtSearch = strNewValue
End Property


Public Property Get SearchResult() As String
Attribute SearchResult.VB_Description = "Item Selected by user from the Search result list"
 SearchResult = cboSearchResult.Text
End Property

Private Sub cboSearchResult_Click()
 If mblnHasData Then
    cboSearchData.ListIndex = cboSearchResult.ListIndex
 Else
    cboSearchData.ListIndex = -1
 End If
 RaiseEvent SelectedResult(cboSearchResult.Text, cboSearchData.Text, mlngSelected)
End Sub

Private Sub cmdSearch_Click()
 Dim strSQL     As String
 Dim rec        As ADODB.Recordset
 Dim TempComboSearch As String  'Phoenix1.1
 Dim Counter As Long ' Phoenix1.1
 Dim Listcnt As Long ' Phoenix1.1
 Dim Found As Boolean ' Phoenix1.1
 
 On Error GoTo cmdSearchERROR
 
 Screen.MousePointer = vbHourglass
 'Celina Leong Phoenix1.1 02/04/03
 'Keep history of values in search field
        TempComboSearch = txtSearch.Text
        Listcnt = txtSearch.Listcount
        Found = False
        Counter = 0
   
        Do While Counter <= Listcnt
                 
                  If txtSearch.List(Counter) = TempComboSearch Then
                     Found = True
                     Exit Do
                  Else
                     Found = False
                  End If
                 Counter = Counter + 1
        Loop
    
    
    If Found = False Then
            txtSearch.AddItem txtSearch.Text
    End If
'--------------------------------------
 Enabled = False
 cboSearchResult.Clear
 cboSearchData.Clear
 
 RaiseEvent GetSQL(mlngSelected, strSQL, txtSearch.Text)
 
 If strSQL <> "" Then
    DB.Execute strSQL, rec
    
    If Not rec.EOF Then
       If rec.Fields.Count > 1 Then
          mblnHasData = True
       Else
          mblnHasData = False
       End If
    End If
    
    Do Until rec.EOF
       cboSearchResult.AddItem rec.Fields(0)
       If mblnHasData Then
          cboSearchData.AddItem rec.Fields(1)
       End If
       rec.MoveNext
    Loop
    End If
 
 Enabled = True
 If cboSearchResult.Listcount > 0 Then
    cboSearchResult.ListIndex = 0
    If mblnHasData Then cboSearchData.ListIndex = 0
    cboSearchResult.SetFocus
 Else
    MsgBox "No Data Found", vbInformation
    txtSearch.SetFocus
 End If
 
 Screen.MousePointer = vbNormal
Exit Sub
cmdSearchERROR:
 Screen.MousePointer = vbNormal
 Enabled = True
 DB.ShowError "Unable to search database"
End Sub


Private Sub optSearchType_Click(Index As Integer)
 mlngSelected = Index
End Sub


Private Sub txtSearch_Change()
 If Not mblnAllowNoCri Then
    If Len(txtSearch) = 0 Then
       cmdSearch.Enabled = False
    Else
       cmdSearch.Enabled = True
    End If
 End If
End Sub

Private Sub txtSearch_GotFocus()
 txtSearch.SelStart = 0
 txtSearch.SelLength = 100
End Sub


Private Sub txtSearch_KeyDown(KeyCode As Integer, Shift As Integer)
 If KeyCode = vbKeyReturn Then
    If cmdSearch.Enabled Then
       cmdSearch.Value = True
    End If
 End If
End Sub


Private Sub txtSearch_KeyPress(KeyAscii As Integer)
 If KeyAscii = 13 Then  '* Suppress Beep
    KeyAscii = 0
 End If
End Sub

Private Sub UserControl_Resize()
 Dim lngWidth As Long
 
 lngWidth = UserControl.Width
 If lngWidth < mlngControlMinWidth Then
    UserControl.Width = mlngControlMinWidth
    Exit Sub
 End If
 If UserControl.Height <> mlngControlHeight Then
    UserControl.Height = mlngControlHeight
    Exit Sub
 End If
 
 FraSearch.Width = lngWidth - 10
 cboSearchResult.Width = lngWidth - 3450
 txtSearch.Width = lngWidth - 4770
 cmdSearch.Left = lngWidth - 1425
 
End Sub


'********************************************
Private Sub UserControl_Initialize()
 Enabled = mblnPropDefEnabled
 Option1Caption = mstrPropDefOpt1Cap
 Option2Caption = mstrPropDefOpt2Cap
 Option3Caption = mstrPropDefOpt3Cap
 Option4Caption = mstrPropDefOpt4Cap
 OptionCount = mlngPropDefOptCount
 DefaultOption = mlngPropDefDefOpt
 AllowNoCriteria = mblnPropDefAllowNoCri
End Sub

Private Sub UserControl_ReadProperties(PropBag As PropertyBag)
 On Error Resume Next
 Enabled = PropBag.ReadProperty(mstrPropNameEnabled, mblnPropDefEnabled)
 Option1Caption = PropBag.ReadProperty(mstrPropNameOpt1Cap, mstrPropDefOpt1Cap)
 Option2Caption = PropBag.ReadProperty(mstrPropNameOpt2Cap, mstrPropDefOpt2Cap)
 Option3Caption = PropBag.ReadProperty(mstrPropNameOpt3Cap, mstrPropDefOpt3Cap)
 Option4Caption = PropBag.ReadProperty(mstrPropNameOpt4Cap, mstrPropDefOpt4Cap)
 OptionCount = PropBag.ReadProperty(mstrPropNameOptCount, mlngPropDefOptCount)
 DefaultOption = PropBag.ReadProperty(mstrPropNameDefOpt, mlngPropDefDefOpt)
 AllowNoCriteria = PropBag.ReadProperty(mstrPropNameAllowNoCri, mblnPropDefAllowNoCri)
End Sub

Private Sub UserControl_Show()
 AllowNoCriteria = mblnAllowNoCri
End Sub

Private Sub UserControl_WriteProperties(PropBag As PropertyBag)
 PropBag.WriteProperty mstrPropNameEnabled, FraSearch.Enabled, mblnPropDefEnabled
 PropBag.WriteProperty mstrPropNameOpt1Cap, Option1Caption, mstrPropDefOpt1Cap
 PropBag.WriteProperty mstrPropNameOpt2Cap, Option2Caption, mstrPropDefOpt2Cap
 PropBag.WriteProperty mstrPropNameOpt3Cap, Option3Caption, mstrPropDefOpt3Cap
 PropBag.WriteProperty mstrPropNameOpt4Cap, Option4Caption, mstrPropDefOpt4Cap
 PropBag.WriteProperty mstrPropNameOptCount, OptionCount, mlngPropDefOptCount
 PropBag.WriteProperty mstrPropNameDefOpt, DefaultOption, mlngPropDefDefOpt
 PropBag.WriteProperty mstrPropNameAllowNoCri, AllowNoCriteria, mblnPropDefAllowNoCri
End Sub
