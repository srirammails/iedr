Attribute VB_Name = "GLOBAL"
Public CnPhoenix As New ADODB.Connection
Public sSQL, s1SQL As String


' tsmyth fields used to obtain system information
' API Declarations for the Locale Methods
' Locale Constants
Public gsDatefmt As String

Public Const LOCALE_SDECIMAL As Long = &HE
Public Const LOCALE_SLONGDATE As Long = &H20
Public Const LOCALE_SSHORTDATE As Long = &H1F
Public Const LOCALE_SCURRENCY As Long = &H14
Public Const LOCALE_STHOUSAND As Long = &HF
Public Const LOCALE_SINTLSYMBOL As Long = &H15
Public Const LOCALE_STIMEFORMAT As Long = &H1003
Public Const LOCALE_SNEGATIVESIGN = &H51
Public Const LOCALE_SPOSITIVESIGN = &H50
Public Const LOCALE_SCOUNTRY As Long = &H6
Public Const LOCALE_SDAYNAME1 As Long = &H2A
Public Const LOCALE_SDAYNAME2 As Long = &H2B
Public Const LOCALE_SDAYNAME3 As Long = &H2C
Public Const LOCALE_SDAYNAME4 As Long = &H2D
Public Const LOCALE_SDAYNAME5 As Long = &H2E
Public Const LOCALE_SDAYNAME6 As Long = &H2F
Public Const LOCALE_SDAYNAME7 As Long = &H30
Public Const LOCALE_SENGCOUNTRY As Long = &H1002
Public Const LOCALE_SENGLANGUAGE As Long = &H1001
Public Const LOCALE_SLANGUAGE As Long = &H2
Public Const LOCALE_SMONTHNAME1 As Long = &H38
Public Const LOCALE_SMONTHNAME10 As Long = &H41
Public Const LOCALE_SMONTHNAME11 As Long = &H42
Public Const LOCALE_SMONTHNAME12 As Long = &H43
Public Const LOCALE_SMONTHNAME2 As Long = &H39
Public Const LOCALE_SMONTHNAME3 As Long = &H3A
Public Const LOCALE_SMONTHNAME4 As Long = &H3B
Public Const LOCALE_SMONTHNAME5 As Long = &H3C
Public Const LOCALE_SMONTHNAME6 As Long = &H3D
Public Const LOCALE_SMONTHNAME7 As Long = &H3E
Public Const LOCALE_SMONTHNAME8 As Long = &H3F
Public Const LOCALE_SMONTHNAME9 As Long = &H40
Public Const LOCALE_SABBREVCTRYNAME = &H7
Public Const LOCALE_SABBREVDAYNAME1 = &H31
Public Const LOCALE_SABBREVDAYNAME3 = &H33
Public Const LOCALE_SABBREVDAYNAME2 = &H32
Public Const LOCALE_SABBREVDAYNAME4 = &H34
Public Const LOCALE_SABBREVDAYNAME5 = &H35
Public Const LOCALE_SABBREVDAYNAME6 = &H36
Public Const LOCALE_SABBREVDAYNAME7 = &H37
Public Const LOCALE_SABBREVLANGNAME = &H3
Public Const LOCALE_SABBREVMONTHNAME1 = &H44
Public Const LOCALE_SABBREVMONTHNAME10 = &H4D
Public Const LOCALE_SABBREVMONTHNAME11 = &H4E
Public Const LOCALE_SABBREVMONTHNAME12 = &H4F
Public Const LOCALE_SABBREVMONTHNAME13 = &H100F
Public Const LOCALE_SABBREVMONTHNAME2 = &H45
Public Const LOCALE_SABBREVMONTHNAME3 = &H46
Public Const LOCALE_SABBREVMONTHNAME4 = &H47
Public Const LOCALE_SABBREVMONTHNAME5 = &H48
Public Const LOCALE_SABBREVMONTHNAME6 = &H49
Public Const LOCALE_SABBREVMONTHNAME7 = &H4A
Public Const LOCALE_SABBREVMONTHNAME8 = &H4B
Public Const LOCALE_SABBREVMONTHNAME9 = &H4C
Public Const LOCALE_SNATIVECTRYNAME = &H8
Public Const LOCALE_SNATIVELANGNAME = &H4

Public Const LOCALE_USER_DEFAULT As Long = &H400

Public Declare Function GetLocaleInfo Lib "kernel32" Alias "GetLocaleInfoA" (ByVal lLocale As Long, ByVal lLocaleType As Long, ByVal sLCData As String, ByVal lBufferLength As Long) As Long
Public Declare Function GetSystemDefaultLangID Lib "kernel32" () As Integer
Public Declare Function VerLanguageName Lib "kernel32" Alias "VerLanguageNameA" (ByVal wLang As Long, ByVal szLang As String, ByVal nSize As Long) As Long
Public Const g_iReconnectInterval = 15 '5.6.7 Timeout Issue



