Attribute VB_Name = "basCrPKI"
' The basCrPKI module contains the full list of declaration statements
' for the CryptoSys (tm) PKI Toolkit Version 2.7 library.
' Last updated:
'   $Date: 2006/01/09 18:25:00 $
'   $Revision: 2.7.1 $

'************************* COPYRIGHT NOTICE*************************
' Copyright (c) 2002-5 DI Management Services Pty Limited.
' All rights reserved.
' This code may only be used by licensed users.
' The latest version of the CryptoSys PKI Toolkit and a licence
' may be obtained from <www.cryptosys.net>.
' This copyright notice must always be left intact.
'****************** END OF COPYRIGHT NOTICE*************************

Option Explicit
Option Base 0

' CONSTANTS
Public Const PKI_MAX_HASH_LEN As Long = 40    ' For hex string output
Public Const PKI_MAX_HASH_BYTES As Long = 20  ' For byte output
Public Const PKI_DIR_ENCRYPT As Long = -1
Public Const PKI_DIR_DECRYPT As Long = 0

' CONSTANTS USED IN OPTION FLAGS
Public Const PKI_DEFAULT       As Long = 0
Public Const PKI_SIG_SHA1RSA   As Long = 0    ' default
Public Const PKI_SIG_MD5RSA    As Long = 1
Public Const PKI_SIG_MD2RSA    As Long = 2
Public Const PKI_PBE_SHA1_3DES As Long = 0    ' default
Public Const PKI_PBE_MD5_DES   As Long = 1
Public Const PKI_PBE_MD2_DES   As Long = 2
Public Const PKI_HASH_SHA1     As Long = 0    ' default
Public Const PKI_HASH_MD5      As Long = 1
Public Const PKI_HASH_MD2      As Long = 2
Public Const PKI_HASH_MODE_TEXT  As Long = &H10000

Public Const PKI_KEYGEN_INDICATE    As Long = &H10
Public Const PKI_KEY_NODELAY        As Long = &H20
Public Const PKI_KEY_FORMAT_PEM  As Long = &H10000
Public Const PKI_KEY_FORMAT_SSL  As Long = &H20000

Public Const PKI_PFX_NO_PRIVKEY     As Long = &H10

Public Const PKI_XML_RSAKEYVALUE     As Long = &H1
Public Const PKI_XML_EXCLPRIVATE    As Long = &H10
Public Const PKI_XML_HEXBINARY     As Long = &H100

Public Const PKI_EME_DEFAULT         As Long = &H0
Public Const PKI_EME_OAEP           As Long = &H10
Public Const PKI_EMSIG_DEFAULT      As Long = &H20
Public Const PKI_EMSIG_DIGESTONLY As Long = &H1000
Public Const PKI_EMSIG_DIGINFO    As Long = &H2000

Public Const PKI_X509_FORMAT_PEM  As Long = &H10000
Public Const PKI_X509_FORMAT_BIN  As Long = &H20000
Public Const PKI_X509_REQ_KLUDGE  As Long = &H100000
Public Const PKI_X509_LATIN1      As Long = &H400000
Public Const PKI_X509_UTF8        As Long = &H800000
Public Const PKI_X509_NO_BASIC    As Long = &H2000000
Public Const PKI_X509_CA_TRUE     As Long = &H4000000
Public Const PKI_X509_VERSION1    As Long = &H8000000

Public Const PKI_CMS_FORMAT_BASE64  As Long = &H10000
Public Const PKI_CMS_EXCLUDE_CERTS    As Long = &H100
Public Const PKI_CMS_EXCLUDE_DATA     As Long = &H200
Public Const PKI_CMS_INCLUDE_ATTRS    As Long = &H800
Public Const PKI_CMS_ADD_SIGNTIME    As Long = &H1000
Public Const PKI_CMS_ADD_SMIMECAP    As Long = &H2000

' CONSTANTS USED IN RSA EXPONENT PARAMETER
Public Const PKI_RSAEXP_EQ_3       As Long = 0
Public Const PKI_RSAEXP_EQ_5       As Long = 1
Public Const PKI_RSAEXP_EQ_17      As Long = 2
Public Const PKI_RSAEXP_EQ_257     As Long = 3
Public Const PKI_RSAEXP_EQ_65537   As Long = 4

' CONSTANTS USED IN KEY USAGE FLAG
Public Const PKI_X509_KEYUSAGE_DIGITALSIGNATURE  As Long = &H1
Public Const PKI_X509_KEYUSAGE_NONREPUDIATION    As Long = &H2
Public Const PKI_X509_KEYUSAGE_KEYENCIPHERMENT   As Long = &H4
Public Const PKI_X509_KEYUSAGE_DATAENCIPHERMENT  As Long = &H8
Public Const PKI_X509_KEYUSAGE_KEYAGREEMENT     As Long = &H10
Public Const PKI_X509_KEYUSAGE_KEYCERTSIGN      As Long = &H20
Public Const PKI_X509_KEYUSAGE_CRLSIGN          As Long = &H40
Public Const PKI_X509_KEYUSAGE_ENCIPHERONLY     As Long = &H80
Public Const PKI_X509_KEYUSAGE_DECIPHERONLY    As Long = &H100


' CRYPTOGRAPHIC MESSAGE SYNTAX (CMS) FUNCTIONS
Public Declare Function CMS_MakeEnvData Lib "diCrPKI.dll" _
    (ByVal strFileOut As String, ByVal strFileIn As String, _
    ByVal strCertList As String, ByVal strSeed As String, ByVal nSeedLen As Long, _
    ByVal nOptions As Long) As Long

Public Declare Function CMS_MakeEnvDataFromString Lib "diCrPKI.dll" _
    (ByVal strFileOut As String, ByVal strDataIn As String, _
    ByVal strCertList As String, ByVal strSeed As String, ByVal nSeedLen As Long, _
    ByVal nOptions As Long) As Long

Public Declare Function CMS_ReadEnvData Lib "diCrPKI.dll" _
    (ByVal strFileOut As String, ByVal strFileIn As String, _
    ByVal strCertFile As String, ByVal strPrivateKey As String, _
    ByVal nOptions As Long) As Long

Public Declare Function CMS_ReadEnvDataToString Lib "diCrPKI.dll" _
    (ByVal strDataOut As String, ByVal nDataLen As Long, _
    ByVal strFileIn As String, ByVal strCertFile As String, _
    ByVal strPrivateKey As String, ByVal nOptions As Long) As Long

Public Declare Function CMS_MakeSigData Lib "diCrPKI.dll" _
    (ByVal strFileOut As String, ByVal strFileIn As String, _
    ByVal strCertList As String, ByVal strPrivateKey As String, _
    ByVal nOptions As Long) As Long
    
Public Declare Function CMS_MakeSigDataFromString Lib "diCrPKI.dll" _
    (ByVal strFileOut As String, ByVal strDataIn As String, _
    ByVal strCertList As String, ByVal strPrivateKey As String, _
    ByVal nOptions As Long) As Long
    
Public Declare Function CMS_MakeDetachedSig Lib "diCrPKI.dll" _
    (ByVal strFileOut As String, ByVal strHexDigest As String, _
    ByVal strCertList As String, ByVal strPrivateKey As String, _
    ByVal nOptions As Long) As Long

Public Declare Function CMS_ReadSigData Lib "diCrPKI.dll" _
    (ByVal strFileOut As String, ByVal strFileIn As String, _
    ByVal nOptions As Long) As Long

Public Declare Function CMS_ReadSigDataToString Lib "diCrPKI.dll" _
    (ByVal strDataOut As String, ByVal nDataLen As Long, _
    ByVal strFileIn As String, ByVal nOptions As Long) As Long

Public Declare Function CMS_GetSigDataDigest Lib "diCrPKI.dll" _
    (ByVal strHexDigest As String, ByVal nHexDigestLen As Long, _
    ByVal strFileIn As String, ByVal strCertFile As String, _
    ByVal nOptions As Long) As Long
    
' RSA PUBLIC KEY FUNCTIONS
Public Declare Function RSA_MakeKeys Lib "diCrPKI.dll" _
    (ByVal strPubKeyFile As String, ByVal strPvkKeyFile As String, _
    ByVal nBits As Long, ByVal nExpFermat As Long, ByVal nTests As Long, _
    ByVal nCount As Long, ByVal strPassword As String, _
    ByVal strSeed As String, ByVal nSeedLen As Long, _
    ByVal nOptions As Long) As Long

Public Declare Function RSA_ReadEncPrivateKey Lib "diCrPKI.dll" _
    (ByVal strPrivateKey As String, ByVal nKeyMaxLen As Long, ByVal strEpkFileName As String, _
    ByVal strPassword As String, ByVal nOptions As Long) As Long

Public Declare Function RSA_ReadPrivateKeyInfo Lib "diCrPKI.dll" _
    (ByVal strPrivateKey As String, ByVal nKeyMaxLen As Long, ByVal strPRIFileName As String, _
    ByVal nOptions As Long) As Long

Public Declare Function RSA_ReadPublicKey Lib "diCrPKI.dll" _
    (ByVal strPublicKey As String, ByVal nKeyMaxLen As Long, ByVal strKeyFileName As String, _
    ByVal nOptions As Long) As Long

Public Declare Function RSA_SavePublicKey Lib "diCrPKI.dll" _
    (ByVal strOutputFile As String, ByVal strPublicKey As String, _
    ByVal nOptions As Long) As Long

Public Declare Function RSA_GetPublicKeyFromCert Lib "diCrPKI.dll" _
    (ByVal strPublicKey As String, ByVal nKeyMaxLen As Long, ByVal strCertFileName As String, _
    ByVal nOptions As Long) As Long
    
Public Declare Function RSA_SaveEncPrivateKey Lib "diCrPKI.dll" _
    (ByVal strOutputFile As String, ByVal strPrivateKey As String, ByVal nCount As Long, _
    ByVal strPassword As String, ByVal nOptions As Long) As Long

Public Declare Function RSA_SavePrivateKeyInfo Lib "diCrPKI.dll" _
    (ByVal strOutputFile As String, ByVal strPrivateKey As String, _
    ByVal nOptions As Long) As Long

Public Declare Function RSA_GetPrivateKeyFromPFX Lib "diCrPKI.dll" _
    (ByVal strOutputFile As String, ByVal strPfxFile As String, _
    ByVal nOptions As Long) As Long
    
Public Declare Function RSA_ToXMLString Lib "diCrPKI.dll" _
    (ByVal strOutput As String, ByVal nOutputLen As Long, _
    ByVal strKeyString As String, ByVal nOptions As Long) As Long

Public Declare Function RSA_FromXMLString Lib "diCrPKI.dll" _
    (ByVal strOutput As String, ByVal nOutputLen As Long, _
    ByVal strXmlString As String, ByVal nOptions As Long) As Long
    
Public Declare Function RSA_RawPublic Lib "diCrPKI.dll" _
    (ByRef abData As Byte, ByVal nDataLen As Long, _
    ByVal strPublicKey As String, ByVal nOptions As Long) As Long
    
Public Declare Function RSA_RawPrivate Lib "diCrPKI.dll" _
    (ByRef abData As Byte, ByVal nDataLen As Long, _
    ByVal strPrivateKey As String, ByVal nOptions As Long) As Long
    
Public Declare Function RSA_EncodeMsg Lib "diCrPKI.dll" _
    (ByRef abOutput As Byte, ByVal nOutputLen As Long, _
    ByRef abMessage As Byte, ByVal nMsgLen As Long, ByVal nOptions As Long) As Long

Public Declare Function RSA_DecodeMsg Lib "diCrPKI.dll" _
    (ByRef abOutput As Byte, ByVal nOutputLen As Long, _
    ByRef abInput As Byte, ByVal nInputLen As Long, ByVal nOptions As Long) As Long

Public Declare Function RSA_KeyBits Lib "diCrPKI.dll" _
    (ByVal strKey As String) As Long
    
Public Declare Function RSA_KeyBytes Lib "diCrPKI.dll" _
    (ByVal strKey As String) As Long
    
Public Declare Function RSA_CheckKey Lib "diCrPKI.dll" _
    (ByVal strKey As String, ByVal nOptions As Long) As Long

' X.509 CERTIFICATE FUNCTIONS
Public Declare Function X509_MakeCertSelf Lib "diCrPKI.dll" _
    (ByVal strNewCertFile As String, ByVal strEPKFile As String, _
    ByVal nCertNum As Long, ByVal nYearsValid As Long, _
    ByVal strDistName As String, ByVal strEmail As String, _
    ByVal KeyUsageFlags As Long, _
    ByVal strPassword As String, ByVal nOptions As Long) As Long

Public Declare Function X509_MakeCert Lib "diCrPKI.dll" _
    (ByVal strNewCertFile As String, ByVal strIssuerCertFile As String, _
    ByVal strSubjectPubKeyFile As String, ByVal strIssuerPvkInfoFile As String, _
    ByVal nCertNum As Long, ByVal nYearsValid As Long, _
    ByVal strDistName As String, ByVal strEmail As String, _
    ByVal KeyUsageFlags As Long, _
    ByVal strPassword As String, ByVal nOptions As Long) As Long

Public Declare Function X509_CertRequest Lib "diCrPKI.dll" _
    (ByVal strReqFile As String, ByVal strEPKFile As String, _
    ByVal strDistName As String, ByVal strReserved As String, _
    ByVal strPassword As String, ByVal nOptions As Long) As Long

Public Declare Function X509_VerifyCert Lib "diCrPKI.dll" _
    (ByVal strCertToVerify As String, ByVal strIssuerCert As String, _
    ByVal nOptions As Long) As Long
    
Public Declare Function X509_CertThumb Lib "diCrPKI.dll" _
    (ByVal strCertFile As String, ByVal strHexHash As String, _
    ByVal nHexHashLen As Long, ByVal nOptions As Long) As Long
    
Public Declare Function X509_CertIsValidNow Lib "diCrPKI.dll" _
    (ByVal strCertFile As String, ByVal nOptions As Long) As Long
   
Public Declare Function X509_CertIssuedOn Lib "diCrPKI.dll" _
    (ByVal strCertFile As String, ByVal strOutput As String, _
    ByVal nOutputLen As Long, ByVal nOptions As Long) As Long

Public Declare Function X509_CertExpiresOn Lib "diCrPKI.dll" _
    (ByVal strCertFile As String, ByVal strOutput As String, _
    ByVal nOutputLen As Long, ByVal nOptions As Long) As Long

Public Declare Function X509_CertSerialNumber Lib "diCrPKI.dll" _
    (ByVal strCertFile As String, ByVal strOutput As String, _
    ByVal nOutputLen As Long, ByVal nOptions As Long) As Long

Public Declare Function X509_CertIssuerName Lib "diCrPKI.dll" _
    (ByVal strCertFile As String, ByVal strOutput As String, _
    ByVal nOutputLen As Long, ByVal strDelim As String, ByVal nOptions As Long) As Long

Public Declare Function X509_CertSubjectName Lib "diCrPKI.dll" _
    (ByVal strCertFile As String, ByVal strOutput As String, _
    ByVal nOutputLen As Long, ByVal strDelim As String, ByVal nOptions As Long) As Long

Public Declare Function X509_HashIssuerAndSN Lib "diCrPKI.dll" _
    (ByVal strCertFile As String, ByVal strOutput As String, _
    ByVal nOutputLen As Long, ByVal nOptions As Long) As Long
    
' PFX (PKCS-12) FUNCTIONS
Public Declare Function PFX_MakeFile Lib "diCrPKI.dll" _
    (ByVal strOutputFile As String, ByVal strCertFile As String, _
    ByVal strKeyFile As String, ByVal strPassword As String, _
    ByVal strFriendlyName As String, ByVal nOptions As Long) As Long

Public Declare Function PFX_VerifySig Lib "diCrPKI.dll" _
    (ByVal strFileName As String, ByVal strPassword As String, _
    ByVal nOptions As Long) As Long
    
' TRIPLE DATA ENCRYPTION ALGORITHM (TDEA/3DES/TRIPLE DES) BLOCK CIPHER FUNCTIONS
Public Declare Function TDEA_BytesMode Lib "diCrPKI.dll" _
    (ByRef aResult As Byte, ByRef aData As Byte, ByVal lngDataLen As Long, _
    ByRef aKey As Byte, ByVal bEncrypt As Boolean, _
    ByVal sMode As String, ByRef aInitV As Byte) As Long

Public Declare Function TDEA_HexMode Lib "diCrPKI.dll" _
    (ByVal sOutput As String, ByVal sInput As String, _
    ByVal sHexKey As String, ByVal bEncrypt As Boolean, _
    ByVal sMode As String, ByVal sHexIV As String) As Long
    
Public Declare Function TDEA_B64Mode Lib "diCrPKI.dll" _
    (ByVal sOutput As String, ByVal sInput As String, _
    ByVal sKey64 As String, ByVal bEncrypt As Boolean, _
    ByVal sMode As String, ByVal sIV64 As String) As Long
    
Public Declare Function TDEA_File Lib "diCrPKI.dll" _
    (ByVal sFileOut As String, ByVal sFileIn As String, _
    ByRef aKey As Byte, ByVal bEncrypt As Boolean, _
    ByVal sMode As String, ByRef aInitV As Byte) As Long

' MESSAGE DIGEST HASH FUNCTIONS
Public Declare Function HASH_HexFromBytes Lib "diCrPKI.dll" _
    (ByVal strHexDigest As String, ByVal nLenHex As Long, ByRef abMessage As Byte, _
    ByVal nMsgLen As Long, ByVal nOptions As Long) As Long
' Alternative Alias to cope with ANSI strings...
Public Declare Function HASH_HexFromString Lib "diCrPKI.dll" Alias "HASH_HexFromBytes" _
    (ByVal strHexDigest As String, ByVal nLenHex As Long, ByVal strMessage As String, _
    ByVal nMsgLen As Long, ByVal nOptions As Long) As Long
Public Declare Function HASH_HexFromFile Lib "diCrPKI.dll" _
    (ByVal strHexDigest As String, ByVal nLenHex As Long, ByVal strFileName As String, _
    ByVal nOptions As Long) As Long
Public Declare Function HASH_Bytes Lib "diCrPKI.dll" _
    (ByRef abDigest As Byte, ByVal nDigLen As Long, ByRef abMessage As Byte, _
    ByVal nMsgLen As Long, ByVal nOptions As Long) As Long
Public Declare Function HASH_File Lib "diCrPKI.dll" _
    (ByRef abDigest As Byte, ByVal nDigLen As Long, ByVal strFileName As String, _
    ByVal nOptions As Long) As Long
    
' ENCODING CONVERSION FUNCTIONS
Public Declare Function CNV_HexStrFromBytes Lib "diCrPKI.dll" _
    (ByVal strHex As String, ByVal nHexStrLen As Long, _
    ByRef abData As Byte, ByVal nDataLen As Long) As Long
' See cnvHexStrFromBytes below

Public Declare Function CNV_BytesFromHexStr Lib "diCrPKI.dll" _
    (ByRef abData As Byte, ByVal nDataLen As Long, ByVal strHex As String) As Long
' See cnvBytesFromHexStr below
    
Public Declare Function CNV_HexFilter Lib "diCrPKI.dll" _
    (ByVal strOutput As String, ByVal strInput As String, ByVal nStrLen As Long) As Long
' See cnvHexFilter below
    
Public Declare Function CNV_B64StrFromBytes Lib "diCrPKI.dll" _
    (ByVal strB64 As String, ByVal nB64StrLen As Long, _
    ByRef abData As Byte, ByVal nDataLen As Long) As Long
' See cnvB64StrFromBytes below

Public Declare Function CNV_BytesFromB64Str Lib "diCrPKI.dll" _
    (ByRef abData As Byte, ByVal nDataLen As Long, ByVal strB64 As String) As Long
' See cnvBytesFromB64Str below

Public Declare Function CNV_B64Filter Lib "diCrPKI.dll" _
    (ByVal strOutput As String, ByVal strInput As String, ByVal nStrLen As Long) As Long
' See cnvB64Filter below
   
' UTF-8 CONVERSION FUNCTIONS (new in Version 2.7)
Public Declare Function CNV_UTF8FromLatin1 Lib "diCrPKI.dll" _
    (ByVal strOutput As String, ByVal nOutChars As Long, ByVal strInput As String) As Long
Public Declare Function CNV_Latin1FromUTF8 Lib "diCrPKI.dll" _
    (ByVal strOutput As String, ByVal nOutChars As Long, ByVal strInput As String) As Long

' MISCELLANEOUS FUNCTIONS
Public Declare Function PKI_LastError Lib "diCrPKI.dll" _
    (ByVal strErrMsg As String, ByVal nMaxMsgLen As Long) As Long
' See also pkiGetLastError() below

Public Declare Function PKI_ErrorCode Lib "diCrPKI.dll" () As Long
Public Declare Function PKI_ErrorLookup Lib "diCrPKI.dll" _
    (ByVal strErrMsg As String, ByVal nMaxMsgLen As Long, ByVal nErrorCode As Long) As Long
Public Declare Function PKI_PowerUpTests Lib "diCrPKI.dll" _
    (ByVal nOptions As Long) As Long
Public Declare Function PKI_Version Lib "diCrPKI.dll" _
    (ByRef nMajor As Long, ByRef nMinor As Long) As Long
Public Declare Function PKI_LicenceType Lib "diCrPKI.dll" _
    (ByVal nReserved As Long) As Long
Public Declare Function PKI_CompileTime Lib "diCrPKI.dll" _
    (ByVal strCompiledOn As String, ByVal nStrLen As Long) As Long
Public Declare Function PKI_ModuleName Lib "diCrPKI.dll" _
    (ByVal strModuleName As String, ByVal nStrLen As Long, ByVal nOptions As Long) As Long
Public Declare Function PWD_Prompt Lib "diCrPKI.dll" _
    (ByVal strPassword As String, ByVal nPwdLen As Long, ByVal strCaption As String) As Long
Public Declare Function PWD_PromptEx Lib "diCrPKI.dll" _
    (ByVal strPassword As String, ByVal nPwdLen As Long, ByVal strCaption As String, _
    ByVal strPrompt As String, ByVal nOptions As Long) As Long
    
Public Declare Function RNG_Bytes Lib "diCrPKI.dll" _
    (ByRef abData As Byte, ByVal nDataLen As Long, ByVal strSeed As String, _
    ByVal nSeedLen As Long) As Long
' Alternative Alias to write to an ANSI string...
Public Declare Function RNG_String Lib "diCrPKI.dll" Alias "RNG_Bytes" _
    (ByVal strData As String, ByVal nDataLen As Long, ByVal strSeed As String, _
    ByVal nSeedLen As Long) As Long
    
Public Declare Function WIPE_File Lib "diCrPKI.dll" _
    (ByVal strFileName As String, ByVal nOptions As Long) As Long
Public Declare Function WIPE_Data Lib "diCrPKI.dll" _
    (ByRef abData As Byte, ByVal nBytes As Long) As Long
' Alternative Aliases to cope with Byte and String types explicitly...
Public Declare Function WIPE_Bytes Lib "diCrPKI.dll" Alias "WIPE_Data" _
    (ByRef abData As Byte, ByVal nBytes As Long) As Long
Public Declare Function WIPE_String Lib "diCrPKI.dll" Alias "WIPE_Data" _
    (ByVal strData As String, ByVal nStrLen As Long) As Long
    

' *** END OF CRYPTOSYS PKI DECLARATIONS

' SOME USEFUL WRAPPER FUNCTIONS
Public Function cnvHexStrFromBytes(abData() As Byte) As String
    On Error GoTo CatchEmptyData
    Dim strHex As String
    Dim nHexLen As Long
    Dim nDataLen As Long
    
    nDataLen = UBound(abData) - LBound(abData) + 1
    nHexLen = CNV_HexStrFromBytes(vbNullString, 0, abData(0), nDataLen)
    If nHexLen <= 0 Then
        Exit Function
    End If
    strHex = String$(nHexLen, " ")
    nHexLen = CNV_HexStrFromBytes(strHex, nHexLen, abData(0), nDataLen)
    cnvHexStrFromBytes = Left$(strHex, nHexLen)
    
CatchEmptyData:

End Function

Public Function cnvBytesFromHexStr(strHex As String) As Variant
    Dim abData() As Byte
    Dim nDataLen As Long
    Dim nHexLen As Long
    
    cnvBytesFromHexStr = abData
    nDataLen = CNV_BytesFromHexStr(0, 0, strHex)
    If nDataLen <= 0 Then
        Exit Function
    End If
    ReDim abData(nDataLen - 1)
    nDataLen = CNV_BytesFromHexStr(abData(0), nDataLen, strHex)
    ReDim Preserve abData(nDataLen - 1)
    cnvBytesFromHexStr = abData
End Function

Public Function cnvHexFilter(strHex As String) As String
    Dim strFiltered As String
    Dim nDataLen As Long
    
    strFiltered = String(Len(strHex), " ")
    nDataLen = CNV_HexFilter(strFiltered, strHex, Len(strHex))
    strFiltered = Left$(strFiltered, nDataLen)
    cnvHexFilter = strFiltered
End Function

Public Function cnvB64StrFromBytes(abData() As Byte) As String
    On Error GoTo CatchEmptyData
    Dim strB64 As String
    Dim nB64Len As Long
    Dim nDataLen As Long
    
    nDataLen = UBound(abData) - LBound(abData) + 1
    nB64Len = CNV_B64StrFromBytes(vbNullString, 0, abData(0), nDataLen)
    If nB64Len <= 0 Then
        Exit Function
    End If
    strB64 = String$(nB64Len, " ")
    nB64Len = CNV_B64StrFromBytes(strB64, nB64Len, abData(0), nDataLen)
    cnvB64StrFromBytes = Left$(strB64, nB64Len)
    
CatchEmptyData:

End Function

Public Function cnvBytesFromB64Str(strB64 As String) As Variant
    Dim abData() As Byte
    Dim nDataLen As Long
    Dim nB64Len As Long
    
    cnvBytesFromB64Str = abData
    nDataLen = CNV_BytesFromB64Str(0, 0, strB64)
    If nDataLen <= 0 Then
        Exit Function
    End If
    ReDim abData(nDataLen - 1)
    nDataLen = CNV_BytesFromB64Str(abData(0), nDataLen, strB64)
    ReDim Preserve abData(nDataLen - 1)
    cnvBytesFromB64Str = abData
End Function

Public Function cnvB64Filter(strB64 As String) As String
    Dim strFiltered As String
    Dim nDataLen As Long
    
    strFiltered = String(Len(strB64), " ")
    nDataLen = CNV_B64Filter(strFiltered, strB64, Len(strB64))
    strFiltered = Left$(strFiltered, nDataLen)
    cnvB64Filter = strFiltered
End Function

Public Function pkiGetLastError() As String
' Returns the last error message as a string, if any
    Dim sErrMsg As String
    Dim nLen As Long
    
    nLen = 511
    sErrMsg = String$(nLen, " ")
    nLen = PKI_LastError(sErrMsg, nLen)
    sErrMsg = Left$(sErrMsg, nLen)
    pkiGetLastError = sErrMsg
End Function

Public Function pkiErrorLookup(nErrCode As Long) As String
' Returns the error message for error code nErrCode
    Dim sErrMsg As String
    Dim nLen As Long
    
    nLen = 127
    sErrMsg = String$(nLen, " ")
    nLen = PKI_ErrorLookup(sErrMsg, nLen, nErrCode)
    sErrMsg = Left$(sErrMsg, nLen)
    pkiErrorLookup = sErrMsg
End Function

Public Function pwdPrompt(Optional sCaption As String) As String
    Dim sPassword As String
    Dim nLen As Long
    
    nLen = 255
    sPassword = String(nLen, " ")
    nLen = PWD_Prompt(sPassword, nLen, sCaption)
    If nLen < 0 Then
        Exit Function
    ElseIf nLen > 0 Then
        pwdPrompt = Left(sPassword, nLen)
    End If
    ' Clean up local variable
    Call WIPE_String(sPassword, nLen)
End Function

Public Function rsaReadPrivateKey(strEPKFile As String, strPassword As String) As String
' Reads the private key from a PKCS-8 EncryptedPrivateKeyInfo file
' (as created by RSA_MakeKeys)
' Returns the key as a base64 string or an empty string on error
    Dim nLen As Long
    Dim lngRet As Long
    ' How long is PrivateKey string?
    nLen = RSA_ReadEncPrivateKey("", 0, strEPKFile, strPassword, 0)
    If nLen <= 0 Then
        Exit Function
    End If
    ' Pre-dimension the string to receive data
    rsaReadPrivateKey = String(nLen, " ")
    ' Read in the Private Key
    lngRet = RSA_ReadEncPrivateKey(rsaReadPrivateKey, nLen, strEPKFile, strPassword, 0)

End Function

Public Function rsaReadPublicKey(strKeyFile As String) As String
' Reads the public key from a PKCS-1 RSAPublicKey file
' (as created by RSA_MakeKeys)
' Returns the key as a base64 string or an empty string on error
    Dim nLen As Long
    Dim lngRet As Long
    ' How long is key string?
    nLen = RSA_ReadPublicKey("", 0, strKeyFile, 0)
    If nLen <= 0 Then
        Exit Function
    End If
    ' Pre-dimension the string to receive data
    rsaReadPublicKey = String(nLen, " ")
    ' Read in the Private Key
    lngRet = RSA_ReadPublicKey(rsaReadPublicKey, nLen, strKeyFile, 0)

End Function

Public Function rsaReadPrivateKeyInfo(strKeyFile As String) As String
' Like rsaReadPrivateKey but for an UNencrypted private key info file
' Returns the key as a base64 string or an empty string on error
    Dim lngKeyLen As Long
    Dim lngRet As Long
    Dim strKey As String
    ' How long is key string?
    lngKeyLen = RSA_ReadPrivateKeyInfo("", 0, strKeyFile, 0)
    If lngKeyLen <= 0 Then
        Exit Function
    End If
    ' Pre-dimension the string to receive data
    strKey = String(lngKeyLen, " ")
    ' Read in the Private Key
    lngRet = RSA_ReadPrivateKeyInfo(strKey, lngKeyLen, strKeyFile, 0)
    rsaReadPrivateKeyInfo = strKey

End Function

Public Function rsaGetPublicKeyFromCert(strCertFile As String) As String
' Reads the public key from an X.509 certificate file
' Returns the key as a base64 string or an empty string on error
    Dim nLen As Long
    Dim lngRet As Long
    ' How long is key string?
    nLen = RSA_GetPublicKeyFromCert("", 0, strCertFile, 0)
    If nLen <= 0 Then
        Exit Function
    End If
    ' Pre-dimension the string to receive data
    rsaGetPublicKeyFromCert = String(nLen, " ")
    ' Read in the Private Key
    lngRet = RSA_GetPublicKeyFromCert(rsaGetPublicKeyFromCert, nLen, strCertFile, 0)

End Function

' *** END OF USEFUL WRAPPER FUNCTIONS

