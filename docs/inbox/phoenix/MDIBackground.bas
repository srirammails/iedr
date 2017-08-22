Attribute VB_Name = "MDIBackground"
  Option Explicit
  
  
  ' this module subclasses the Client area of the MDI form and stretches
  ' the logo bitmap into the area as the window is painted.
  
  ' IMPORTANT NOTES: Because the background bitmap is stored in a resource
  '                 file, you will need to compile the application before
  '                 the background can be viewed on the form.  This is
  '                 because Windows doesn't know where to find the resource
  '                 in when the application is run in the IDE.
  '
  '           ALSO: This sample uses a subclass routine to capture windows messages
  '                 sent to the MDI client window.  DO NOT try to step through the
  '                 code in the IDE!!!  This will cause unexpected results and most
  '                 likely cause VB to crash!!!
  
  ' Place the following line of code in the load event of the MDI form
  '  MDIBackground.InitializePaintBackground hWnd
  ' Place the following line of code in the unload event of the MDI form
  '  MDIBackground.TerminatePaintMDIBackground
  
  ' the width and height of the resource bitmap
  Private Const BITMAP_WIDTH As Long = 280&
  Private Const BITMAP_HEIGHT As Long = 161&
  
  ' constant for long zero value.  also used for null pointers
  Private Const API_FALSE As Long = 0&
  ' costant for long non-zero value for passing to API functions
  Private Const API_TRUE As Long = 1&
  
  ' address of the original window proc
  Private addBackgroundProcOld As Long

  ' handle of the MDI Client window that we will subclass
  Private m_hSubclassedhWnd As Long
  
  ' handle of the our logo bitmap and the original bitmaps from the DCs
  Private hBackgroundBmp As Long
  Private hPrevBmp As Long
  Private hPrevStretchBmp As Long

  ' the memory DCs that we use to store our bitmap
  Private hMemoryDC As Long
  Private hStretchDC As Long
  
  ' Windows Messages
  Public Const WM_SIZE As Long = &H5&
  Public Const WM_ERASEBKGND As Long = &H14&
  Public Const WM_PAINT As Long = &HF&
  
  ' misc. constants
  Public Const GW_CHILD As Long = 5&
  Public Const GWL_WNDPROC As Long = (-4&)
  
  ' RasterOp
  Public Const SRCCOPY As Long = &HCC0020  ' (DWORD) dest = source
  
  Public Type RECT
    Left As Long
    Top As Long
    Right As Long
    Bottom As Long
  End Type

  Public Declare Function GetClientRect& Lib "user32" (ByVal hWnd&, lpRect As RECT)
  
  Public Declare Function GetUpdateRect& Lib "user32" (ByVal hWnd&, lpRect As RECT, ByVal bErase&)
  
  Public Declare Function GetDC& Lib "user32" (ByVal hWnd&)
  
  Public Declare Function SaveDC& Lib "gdi32" (ByVal hDC&)
  
  Public Declare Function ReleaseDC& Lib "user32" (ByVal hWnd&, ByVal hDC&)
  
  Public Declare Function RestoreDC& Lib "gdi32" (ByVal hDC&, ByVal nSavedDC&)
  
  Public Declare Function DeleteDC& Lib "gdi32" (ByVal hDC&)

  Public Declare Function SelectObject& Lib "gdi32" (ByVal hDC&, ByVal hObject&)
  
  Public Declare Function DeleteObject& Lib "gdi32" (ByVal hObject&)
  
  Public Declare Function CreateCompatibleDC& Lib "gdi32" (ByVal hDC&)
  
  Public Declare Function SetWindowLong& Lib "user32" Alias "SetWindowLongA" (ByVal hWnd&, _
                                                              ByVal nIndex&, ByVal dwNewLong&)
  
  Public Declare Function CallWindowProc& Lib "user32" Alias "CallWindowProcA" (ByVal lpPrevWndFunc&, _
                                                ByVal hWnd&, ByVal Msg&, ByVal wParam&, ByVal lParam&)

  Public Declare Function LoadBitmapBynum& Lib "user32" Alias "LoadBitmapA" (ByVal hInstance&, _
                                                                                ByVal lpBitmapName&)
                                                                                
  Public Declare Function CreateCompatibleBitmap& Lib "gdi32" (ByVal hDC&, ByVal nWidth&, ByVal nHeight&)
  
  Public Declare Function BitBlt& Lib "gdi32" (ByVal hDestDC&, ByVal x&, ByVal y&, ByVal nWidth&, _
                                    ByVal nHeight&, ByVal hSrcDC&, ByVal xSrc&, ByVal ySrc&, ByVal dwRop&)
  
  Public Declare Function StretchBlt& Lib "gdi32" (ByVal hDC&, ByVal x&, ByVal y&, ByVal nWidth&, _
                      ByVal nHeight&, ByVal hSrcDC&, ByVal xSrc&, ByVal ySrc&, ByVal nSrcWidth&, _
                                                                      ByVal nSrcHeight&, ByVal dwRop&)

  Public Declare Function GetWindow& Lib "user32" (ByVal hWnd&, ByVal wCmd&)
  
  ' used to determine whether or not this demo is running in the IDE
  Private Declare Function GetClassName& Lib "user32" Alias "GetClassNameA" (ByVal hWnd&, _
                                                                ByVal lpClassName$, ByVal nMaxCount&)

Public Function BackgroundProc(ByVal hWnd As Long, ByVal iMsg As Long, _
                                              ByVal wParam As Long, ByVal lParam As Long) As Long
  ' OUR message handler for the client area of the MDI form
  
  Select Case iMsg ' the messages that we watch
    Case WM_SIZE
      Dim hClientDC&, lStretchRect As RECT

      ' get the rect of the window
      Call GetClientRect(hWnd, lStretchRect)

      ' create a new bitmap and select it into the stretched DC
      DeleteObject SelectObject(hStretchDC, CreateCompatibleBitmap(hMemoryDC, _
                                      lStretchRect.Right, lStretchRect.Bottom))

      ' stretch the bitmap onto the stretched DC
      Call StretchBlt(hStretchDC, lStretchRect.Left, lStretchRect.Top, lStretchRect.Right, _
          lStretchRect.Bottom, hMemoryDC, API_FALSE, API_FALSE, BITMAP_WIDTH, BITMAP_HEIGHT, SRCCOPY)

      ' get the DC to draw on
      hClientDC = GetDC(hWnd)

      ' copy the bitmap onto the window
      Call BitBlt(hClientDC, lStretchRect.Left, lStretchRect.Top, lStretchRect.Right, _
                          lStretchRect.Bottom, hStretchDC, API_FALSE, API_FALSE, SRCCOPY)
      ' clean up
      ReleaseDC hWnd, hClientDC
    
    
    Case WM_ERASEBKGND, WM_PAINT

      Dim hDC&, lRect As RECT, nRetVal&

      ' get the rect of the update region
      Call GetUpdateRect(hWnd, lRect, API_FALSE)
      
       ' call the default VB window proc first if this is a paint message and then do our stuff
      If iMsg = WM_PAINT Then
        Call CallWindowProc(addBackgroundProcOld, hWnd, iMsg, wParam, lParam)
      Else
        nRetVal = API_TRUE
      End If
     
      ' get the DC to draw on
      hDC = GetDC(hWnd)

      ' copy the bitmap onto the DC
      Call BitBlt(hDC, lRect.Left, lRect.Top, lRect.Right, lRect.Bottom, hStretchDC, _
                                                          lRect.Left, lRect.Top, SRCCOPY)
      ' clean up
      ReleaseDC hWnd, hDC
  
      ' pass false to windows
      BackgroundProc = nRetVal

      Exit Function

  End Select

  ' let VB and windows have all other messages
  BackgroundProc = CallWindowProc(addBackgroundProcOld, hWnd, iMsg, wParam, lParam)

End Function

Public Sub InitializePaintBackground(ByVal hWnd&)
  ' initializes the subclass procedure
  
  ' get the handle of the MDI Client window
  m_hSubclassedhWnd = GetWindow(hWnd, GW_CHILD)
  
  '********* create two memory DCs to hold our bitmap
  Dim hOwnerDC&

  hOwnerDC = GetDC(m_hSubclassedhWnd)
  hMemoryDC = CreateCompatibleDC(hOwnerDC)
  hStretchDC = CreateCompatibleDC(hOwnerDC)
  ReleaseDC m_hSubclassedhWnd, hOwnerDC
  SaveDC hMemoryDC
  SaveDC hStretchDC

  ' load the bitmap (201 is the resource ID)
  hBackgroundBmp = LoadBitmapBynum(App.hInstance, 101&)
  

  ' save the previous bitmap when we select the new one into the DC
  hPrevBmp = SelectObject(hMemoryDC, hBackgroundBmp)
  
  Dim lStretchRect As RECT
      
  ' get the rect of the window
  Call GetClientRect(m_hSubclassedhWnd, lStretchRect)

  ' create a new bitmap and select it into the stretch DC
  hPrevStretchBmp = SelectObject(hStretchDC, CreateCompatibleBitmap(hMemoryDC, _
                                      lStretchRect.Right, lStretchRect.Bottom))
  '*********

  ' subclass the MDI Client window
  addBackgroundProcOld = SetWindowLong(m_hSubclassedhWnd, GWL_WNDPROC, AddressOf BackgroundProc)

End Sub

Public Sub TerminatePaintMDIBackground()
  ' terminates the subclass procedure
  
  ' give message processing back to VB
  Call SetWindowLong(m_hSubclassedhWnd, GWL_WNDPROC, addBackgroundProcOld)
  
  ' delete the memory DCs used for holding the background bitmap
  DeleteObject SelectObject(hMemoryDC, hPrevBmp)
  RestoreDC hMemoryDC, True
  DeleteDC hMemoryDC
  hMemoryDC = API_FALSE
  
  DeleteObject SelectObject(hStretchDC, hPrevStretchBmp)
  RestoreDC hStretchDC, True
  DeleteDC hStretchDC
  hStretchDC = API_FALSE
  
End Sub

Public Function RunningInIde(ByVal hWnd&) As Boolean
  
  Dim sClassName$, nStrLen&
  ' check to see where we're running in the IDE
  
  sClassName = String$(260, vbNullChar)
  nStrLen = GetClassName(hWnd, sClassName, Len(sClassName))
  If nStrLen Then sClassName = Left$(sClassName, nStrLen)
  
  If sClassName = "ThunderMDIForm" Then RunningInIde = True
  
End Function

