Imports System
Imports System.Threading
' 
'This is a sample program to help Visual Basic .NET programmers get
'started using the Robix Nexway library (rbxNexwayLib).
'
' Quick start instructions
' ========================
' 1. Plug in an Usbor device.
' 2. Launch the Usbor Nexus application.
' 3. (Optional) Launch the Usbor Nexway application. This will let
'    you watch as this example program run scripts.
' 4. Compile and run this example program.
'
'
' Outline of example1
' ===================
' 1. Initialize the Nexway library.
' 2. Connect to a Nexus.
' 3. Get handle to the first Usbor contained in the Nexus.
' 4. Get handle to the Usbor's first Pod.
' 5. Repeatedly read absi (servo current) of the Pod's
'    first servo.
' 6. If absi exceeds a given threshold, cause the Pod to
'    react by running a quick script or a script macro.
' 7. Repeat 5-6 until CTRL-C is pressed.
'
'
' If the Robix Usbor software was not installed to the default 
' location, you may need to change the constant RBX_USBOR_ROOT 
' (defined in rbxNexwayLib.vb) to the correct directory.


Module Example1
   ' Program parameters
   Dim NEXUS_HOST As String = "localhost"
   Dim USBOR_INDEX As Integer = 0
   Dim POD_ID As Integer = 1
   Dim SERVO_ID As Integer = 1

   Dim ABSI_THRESH As Integer = 250

   Dim MAIN_SCRIPT_NAME As String = "example script"
   Dim MACRO_NAME As String = "react"
   Dim MAIN_SCRIPT As String _
                     = "macro " & MACRO_NAME & vbLf _
                     & "move 1 to 800; move 1 to -800;" & vbLf _
                     & "move 1 to 400; move 1 to -400;" & vbLf _
                     & "move 1 to 200; move 1 to -200;" & vbLf _
                     & "move 1 to 0;" & vbLf _
                     & "end"

   Dim QUICK_SCRIPT As String _
                     = "jump 1 to -300; wait 5" & vbLf _
                     & "jump 1 to 300;  wait 5" & vbLf _
                     & "jump 1 to -150; wait 5" & vbLf _
                     & "jump 1 to 150;  wait 5" & vbLf _
                     & "jump 1 to 0;"
   '
   'This error-reporting function accepts a error message string and a
   'Robix error code. The description of the Robix error code is
   'obtained, and the error message, the error code, and the error
   'description are then displayed in a message box.
   '
   'Parameters:
   ' errMsg - error message to display
   ' rbxErr - RBX_E_ error to display
   '
   Public Sub displayRobixError(ByRef errMsg As String, ByRef rbxErr As Integer)
      On Error GoTo FnError

      Dim retCode As Integer
      Dim descripSiz As Integer
      Dim errDescrip As String
      Dim retSiz As Integer

      'Get the size of the error description so we know how big our character
      'array will need to be in order to hold the description.
      retCode = rbxLib_getErrMsg(rbxErr, vbNullString, 0, descripSiz)
      If retCode = RBX_E_BUFFER_TOO_SMALL Then
         'Create string to hold error description
         errDescrip = New String(vbNullChar, descripSiz)

         retCode = rbxLib_getErrMsg(rbxErr, errDescrip, descripSiz, retSiz)
         If retCode <> RBX_E_SUCCESS Then
            errDescrip = "unavailable"
         End If
      End If

      'Display the entire error message
      Console.WriteLine(errMsg)
      Console.WriteLine(" Robix Error Code: RBX_" & rbxErr)
      Console.WriteLine(" Error Description: " & errDescrip)

      Exit Sub

FnError:
      'Display the error message (description unavailable)
      Console.WriteLine(errMsg)
      Console.WriteLine(" Robix Error Code: RBX_" & rbxErr)
      Console.WriteLine(" Error Description: unavailable")

   End Sub

   '
   'If the specified error code is not RBX_E_SUCCESS, this function
   'displays an error message and terminates the program.
   '
   'Parameters:
   ' errMsg - message to display on error
   ' rbxErr - RBX_E_ error to test
   '
   Sub exitOnRbxError(ByRef errMsg As String, ByVal rbxErr As Integer)

      If rbxErr <> RBX_E_SUCCESS Then
         displayRobixError(errMsg, rbxErr)
         Console.WriteLine("Press <return> to exit.")
         Console.Read()
         End
      End If

   End Sub

   Sub initProgramParams()
      NEXUS_HOST = "localhost"
      USBOR_INDEX = 0
      POD_ID = 1
      SERVO_ID = 1
      ABSI_THRESH = 250
      MACRO_NAME = "react"

      MAIN_SCRIPT = "macro " & MACRO_NAME & vbLf _
                  & "move 1 to 800; move 1 to -800;" & vbLf _
                  & "move 1 to 400; move 1 to -400;" & vbLf _
                  & "move 1 to 200; move 1 to -200;" & vbLf _
                  & "move 1 to 0;" & vbLf _
                  & "end"

      MAIN_SCRIPT_NAME = "example script"

      QUICK_SCRIPT = "jump 1 to -300; wait 5" & vbLf _
                   & "jump 1 to 300;  wait 5" & vbLf _
                   & "jump 1 to -150; wait 5" & vbLf _
                   & "jump 1 to 150;  wait 5" & vbLf _
                   & "jump 1 to 0;"

   End Sub

   '
   'Creates a connection to the Nexus which is running on the 
   'specified host.
   '
   'Parameters:
   ' host - target host may be an IP address, a host name, or 
   '        'localhost'
   '   
   'Returns:
   ' handle to the NexusConnection
   '
   Function connectToNexus(ByRef host As String) As Integer

      Dim rbxErr As Integer
      Dim nexusConnHandle As Integer

      Console.WriteLine("Connecting to Nexus: " & host)

      ' Create nexus connection
      rbxErr = rbxNexway_createNexusConnection(host, nexusConnHandle)
      exitOnRbxError("Error creating Nexus connection.", rbxErr)

      ' Connect to nexus
      rbxErr = rbxNexusConnection_connect(nexusConnHandle)
      exitOnRbxError("Error connecting to Nexus.", rbxErr)
      Return nexusConnHandle

   End Function

   '
   'Returns a handle to the specified Usbor.
   '
   'Parameters:
   ' nexusHandle - handle to Nexus which contains the Usbor
   ' index       - index of the Usbor in the specified Nexus
   '               (zero-based)
   '
   'Returns:
   ' handle to the Usbor
   '
   Function getUsbor(ByVal nexusHandle As Integer, ByVal index As Integer)

      Dim rbxErr As Integer
      Dim usborHandle As Integer

      Console.WriteLine("Getting Usbor: " & index)

      ' Get handle to Usbor
      rbxErr = rbxNexusConnection_getUsborByIndex(nexusHandle, index, usborHandle)
      exitOnRbxError("Error getting Usbor.", rbxErr)

      If (usborHandle = 0) Then
         Console.WriteLine("Could not find Usbor.")
         Console.WriteLine("Press <return> to exit.")
         Console.Read()
         End
      End If

      Return usborHandle

   End Function
   '
   'Returns a handle to the specified Pod.
   '
   'Parameters:
   ' usborHandle - handle to Usbor which contains the Pod
   ' podId       - ID of the Pod in the specified Usbor
   '               (one-based)
   '
   'Returns:
   ' handle to the Pod
   '
   Function getPod(ByVal usborHandle As Integer, ByVal podId As Integer)

      Dim rbxErr As Integer
      Dim podHandle As Integer

      Console.WriteLine("Getting Pod: " & podId)

      ' Get handle to Pod
      rbxErr = rbxUsbor_getPod(usborHandle, podId, podHandle)
      exitOnRbxError("Error getting Pod.", rbxErr)
      Return podHandle

   End Function

   '
   'Waits for the specified Pod command to finish.
   '
   'Parameters:
   ' podHandle - handle to the Pod
   ' seqNum    - sequence number (aka tracking number) of the desired 
   '             Pod command
   '             
   Sub waitForPodCmdFinished(ByVal podHandle As Integer, _
                             ByVal seqNum As Integer)

      Dim rbxErr As Integer
      Dim finished As Integer

      While (True)

         ' Wait for pod command to finish
         rbxErr = rbxPod_isPodCmdFinished(podHandle, seqNum, finished)
         exitOnRbxError("Error checking for pod command finished.", rbxErr)

         If (finished = 1) Then
            Exit While
         End If

         Thread.Sleep(100)
      End While


   End Sub

   '
   'Assigns a script to a Pod. A Pod's assigned script is known
   'as its 'main' script (as opposed to 'quick' scripts).
   '
   'Parameters:
   ' podHandle  - handle to the Pod
   ' scriptText - the script text
   ' scriptName - name of the script
   '
   Sub assignMainScript(ByVal podHandle As Integer, _
                        ByRef scriptText As String, _
                        ByRef scriptName As String)

      Dim rbxErr As Integer
      Dim seqNum As Integer

      Console.WriteLine("Assigning main script. Name: " & scriptName)

      ' Assign script
      rbxErr = rbxPod_assignMainScript(podHandle, scriptText, scriptName, seqNum)
      exitOnRbxError("Error assigning main script.", rbxErr)

      waitForPodCmdFinished(podHandle, seqNum)
   End Sub

   '
   'Runs a macro from a Pod's main script.
   '
   'Parameters:
   ' podHandle  - handle to the Pod
   ' macroName  - name of the macro
   ' iter       - number of iterations to run the macro
   '
   Sub runMainMacro(ByVal podHandle As Integer, ByRef macroName As String, _
                    ByVal iter As Integer)

      Dim rbxErr As Integer
      Dim seqNum As Integer

      Console.WriteLine("Running main script macro. Name: " & macroName _
                        & "  Iterations: " & iter)

      ' Start macro running
      rbxErr = rbxPod_runMainMacro(podHandle, macroName, iter, seqNum)
      exitOnRbxError("Error running main macro.", rbxErr)

      waitForPodCmdFinished(podHandle, seqNum)
   End Sub

   '
   'Runs a quick script.
   '
   'Parameters:
   ' podHandle  - handle to the Pod
   ' scriptText - the quick script text
   '
   Sub runQuickScript(ByVal podHandle As Integer, ByRef scriptText As String)

      Dim rbxErr As Integer
      Dim seqNum As Integer

      Console.WriteLine("Running quick script.")

      ' Start quick script running
      rbxErr = rbxPod_runQuickScript(podHandle, scriptText, seqNum)
      exitOnRbxError("Error running quick script.", rbxErr)

      waitForPodCmdFinished(podHandle, seqNum)
   End Sub

   '
   'Reads a servo's absi property.
   '
   'Parameters:
   ' podHandle  - handle to the Pod
   ' servoId    - ID of the servo in the Pod (one-based)
   '
   'Returns:
   ' current value of the servo's absi property
   '
   Function readAbsi(ByVal podHandle As Integer, ByVal servoId As Integer) _
   As Integer

      Dim rbxErr As Integer
      Dim value As Integer

      ' Read servo's absi value
      rbxErr = rbxServo_getPropertyValue(podHandle, servoId, _
                                         rbxServoProp_ABSI, value)
      exitOnRbxError("Error getting reading absi.", rbxErr)

      Return value
   End Function

   Sub Main()

      Dim rbxErr As Integer
      Dim nexusHandle As Integer
      Dim usborHandle As Integer
      Dim podHandle As Integer
      Dim absI As Integer
      Dim mainOrQuick As Boolean = True

      initProgramParams()

      '///// Initialize the Nexway library /////
      Console.WriteLine("Initializing Nexway library.")

      'Applications must initialize the library before any rbx...() 
      'functions are called. This function dynamically loads 
      'the library and sets up function pointers, making library
      'functions available for use.
      rbxErr = rbxInitNexwayLib("example1")
      exitOnRbxError("Error initializing Nexway lib.", rbxErr)

      '///// Connect and get handles /////
      nexusHandle = connectToNexus(NEXUS_HOST)
      usborHandle = getUsbor(nexusHandle, USBOR_INDEX)
      podHandle = getPod(usborHandle, POD_ID)

      '///// Assign script /////
      assignMainScript(podHandle, MAIN_SCRIPT, MAIN_SCRIPT_NAME)

      Console.WriteLine("Reading absi (servo current)")
      Console.WriteLine(" Servo ID: " & SERVO_ID)
      Console.WriteLine(" absi threshold: " & ABSI_THRESH)
      Console.WriteLine()
      Console.WriteLine("Apply torque to servo to cause a reaction.")
      Console.WriteLine("(CTRL-C to quit)")
      Console.WriteLine()

      ' Read absi repeatedly. If absi value exceeds the threshold,
      ' perform a reaction.
      While (True)
         absI = readAbsi(podHandle, SERVO_ID)

         If (absI > ABSI_THRESH) Then

            'Alternate between two types of reaction
            If (mainOrQuick) Then
               runMainMacro(podHandle, MACRO_NAME, 1)
            Else
               runQuickScript(podHandle, QUICK_SCRIPT)
            End If

            mainOrQuick = Not mainOrQuick
            Console.WriteLine("Reaction finished.")
         Else
            Thread.Sleep(100)
         End If
      End While

   End Sub

End Module
