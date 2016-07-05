Option Explicit On
Imports System.IO

Module rbxNexwayLib
   '********************************************************************
   '* rbxNexwayLib.bas                                                 *
   '*  version: 1.0.0                                                  *
   '*  last modified: 2005-02-28                                       *
   '*                                                                  *
   '* For use with Robix(tm) Usbor Nexway Library                      *
   '* rbxNexwayLib.dll                                                 *
   '*                                                                  *
   '* This file includes:                                              *
   '*  - Error code definitions                                        *
   '*  - Usbor Nexway constants                                        *
   '*  - Library function declarations                                 *
   '*  - Library initialization functions                              *
   '*                                                                  *
   '* PURPOSE:                                                         *
   '* This file was created as a courtesy to Visual Basic programmers  *
   '* who wish to access the Robix Usbor Nexway library                *
   '* (rbxNexwayLib.dll). VB is not our programming language of        *
   '* choice. As such, this module has not been extensively tested.    *
   '*                                                                  *
   '* Copyright (c) 2004-2005                                          *
   '* Robix                                                            *
   '* www.robix.com                                                    *
   '*                                                                  *
   '********************************************************************

   '********************************************************************
   ' JAVA REQUIREMENTS
   '
   ' The Nexway library is a simple C-style library shell around our
   ' Java classes. Hence, a Java(tm) Virtual Machine is required for
   ' proper operation. See the Readme.txt file included with this
   ' software package for a guide to installing Java on your computer.
   '********************************************************************

   '********************************************************************
   ' DOCUMENTATION
   '
   ' The documentation for the Nexway library API is primarily aimed
   ' at C/C++ users. However, this VB module simply calls the functions
   ' exposed by the Nexway library, so the API's are quite similar.
   ' And the Nexway library calls our Java classes, so all API's have
   ' similar features.
   '
   ' There are currently no plans to generate a separate set of docs
   ' for Visual Basic. VB users should review the Nexway library docs
   ' (and the Java docs) in order to get a general idea of the
   ' functionality exposed by this module.
   '
   ' The example project(s) should also help users get acquainted with
   ' the Nexway API.
   '********************************************************************

   '********************************************************************
   ' USAGE
   '
   ' This file should be included in any VB.NET projects which need to
   ' call functions in the Nexway library.
   '********************************************************************

   Public Const RBX_USBOR_INSTALL_DIR As String = "C:\Program Files\Robix\Usbor"
   Public Const RBX_USBOR_LIB_SUBDIR As String = "lib"

   Public Const RBX_NEXWAYLIB_NAME As String = "rbxNexwayLib.dll"
   Public Const RBX_USBORJAR_NAME As String = "rbxUsbor.jar"

   Public Const RBX_DEFAULT_APP_NAME As String = "Anonymous VB app"

   'Target NexwayLib version
   '01.00.00.00 (ver) = 0x01000000 (hex)
   Public Const RBX_NEXWAYLIB_VERSION As Integer = &H1000000

   'Robix Error Codes
   'NOTE: When diplaying these error codes, always prefix them
   'with "RBX_" to prevent confusion with system error codes.
   'For example, "Error running script. Error Code: RBX_1015"
   Public Const RBX_E_SUCCESS As Integer = 0
   Public Const RBX_E_GENERAL_EXCEPTION As Integer = 1000
   Public Const RBX_E_MEMORY_ALLOCATION As Integer = 1001
   Public Const RBX_E_FILE_NOT_FOUND As Integer = 1002
   Public Const RBX_E_FILE_ACCESS_DENIED As Integer = 1003
   Public Const RBX_E_LIB_LOAD_FAILURE As Integer = 1004
   Public Const RBX_E_LIB_FUNCTION_NOT_FOUND As Integer = 1005
   Public Const RBX_E_POD_HANDLE_INVALID As Integer = 1015
   Public Const RBX_E_SERVO_ID_INVALID As Integer = 1022
   Public Const RBX_E_SERVO_PROP_INVALID As Integer = 1023
   Public Const RBX_E_NULL_POINTER As Integer = 1032
   Public Const RBX_E_ADMIN_REQUIRED As Integer = 1033
   Public Const RBX_E_NOT_YET_IMPLEMENTED As Integer = 2000
   Public Const RBX_E_LIB_UNLOAD_FAILURE As Integer = 2001
   Public Const RBX_E_JVM_INIT_FAILURE As Integer = 2002
   Public Const RBX_E_UNKNOWN_HOST As Integer = 2003
   Public Const RBX_E_IO_ERROR As Integer = 2004
   Public Const RBX_E_UNSUPPORTED_VERSION As Integer = 2005
   Public Const RBX_E_ILLEGAL_STATE As Integer = 2006
   Public Const RBX_E_ILLEGAL_ARGUMENT As Integer = 2007
   Public Const RBX_E_BUFFER_TOO_SMALL As Integer = 2008
   Public Const RBX_E_NEXUS_HANDLE_INVALID As Integer = 2009
   Public Const RBX_E_USBOR_HANDLE_INVALID As Integer = 2010
   Public Const RBX_E_POD_ID_INVALID As Integer = 2011
   Public Const RBX_E_DIGOUT_ID_INVALID As Integer = 2012
   Public Const RBX_E_SENSOR_ID_INVALID As Integer = 2013
   Public Const RBX_E_JVM_NOT_INITIALIZED as integer = 2014
   Public Const RBX_E_JVM_NOT_FOUND As Integer = 2015

   'Pod Actions
   Public Const rbxRobotAction_IDLE As Integer = 0 ' Pod is idle
   Public Const rbxRobotAction_WORKING As Integer = 1 ' Pod is busy
   Public Const rbxRobotAction_STOPPING As Integer = 2 ' Pod is stopping (from Working to Idle)

   'Servo Properties
   Public Const rbxServoProp_POS As Integer = 0 ' current position (relative to p0pos)
   Public Const rbxServoProp_MINPOS As Integer = 1 ' minimum position (relative to p0pos)
   Public Const rbxServoProp_MAXPOS As Integer = 2 ' maximum position (relative to p0pos)
   Public Const rbxServoProp_INITPOS As Integer = 3 ' initial position (relative to p0pos)
   Public Const rbxServoProp_P0POS As Integer = 4 ' physical 0 position
   Public Const rbxServoProp_MAXSPD As Integer = 5 ' maximum speed
   Public Const rbxServoProp_ACCEL As Integer = 6 ' acceleration
   Public Const rbxServoProp_DECEL As Integer = 7 ' deceleration
   Public Const rbxServoProp_INVERT As Integer = 8 ' invert status ( 0-not inverted, 1-inverted )
   Public Const rbxServoProp_PINNED As Integer = 9 ' pinned status ( 0-not pinned, 1-pinned )
   Public Const rbxServoProp_POWER As Integer = 10 ' power status  ( 0-not powered, 1-powered )
   Public Const rbxServoProp_ABSI As Integer = 11 ' absolute value of average servo electrical current

   'Sensor types
   Public Const rbxSensorType_ADC As Integer = 4
   Public Const rbxSensorType_QUADRATURE As Integer = 5

   'DLL function declarations

   'DLL Version Function
   Public Declare Function rbxLib_getVersion Lib "rbxNexwayLib.dll" _
      (ByRef Version As Integer) As Integer

   'Error Message Function
   Public Declare Function rbxLib_getErrMsg Lib "rbxNexwayLib.dll" _
      (ByVal ErrCode As Integer, ByVal errMsg As String, ByVal maxLen As Integer, ByRef retLen As Integer) As Integer

   'JVM Functions
   Public Declare Function rbxLib_setJvm Lib "rbxNexwayLib.dll" _
      (ByVal jvmDllPath As String) As Integer

   Public Declare Function rbxLib_setJvmClassPath Lib "rbxNexwayLib.dll" _
      (ByVal classpath As String) As Integer

   Public Declare Function rbxLib_addJvmOption Lib "rbxNexwayLib.dll" _
      (ByVal jvmOpt As String) As Integer

   Public Declare Function rbxLib_addJvmProperty Lib "rbxNexwayLib.dll" _
      (ByVal prop As String, ByVal value As String) As Integer

   Public Declare Function rbxLib_initJvm Lib "rbxNexwayLib.dll" _
      (ByVal nexwayName As String) As Integer

   'Nexway Functions
   Public Declare Function rbxNexway_getName Lib "rbxNexwayLib.dll" _
      (ByVal name As String, ByVal maxLen As Integer, ByRef retLen As Integer) As Integer

   Public Declare Function rbxNexway_createNexusConnection Lib "rbxNexwayLib.dll" _
      (ByVal hostName As String, ByRef nexusHandle As Integer) As Integer

   Public Declare Function rbxNexway_createNexusConnectionEx Lib "rbxNexwayLib.dll" _
      (ByVal hostName As String, ByVal tcpPort As Integer, ByRef nexusHandle As Integer) As Integer

   Public Declare Function rbxNexway_removeNexusConnection Lib "rbxNexwayLib.dll" _
      (ByVal nexusHandle As Integer) As Integer

   Public Declare Function rbxNexway_getNexusConnectionCount Lib "rbxNexwayLib.dll" _
      (ByRef count As Integer) As Integer

   Public Declare Function rbxNexway_getNexusConnection Lib "rbxNexwayLib.dll" _
      (ByVal index As Integer, ByRef nexusHandle As Integer) As Integer

   Public Declare Function rbxNexway_getNexusConnectionListMod Lib "rbxNexwayLib.dll" _
      (ByRef listMod As Integer) As Integer

   Public Declare Function rbxNexway_getUsborByName Lib "rbxNexwayLib.dll" _
      (ByVal usborName As String, ByRef usborHandle As Integer) As Integer

   Public Declare Function rbxNexway_getUsborBySerial Lib "rbxNexwayLib.dll" _
      (ByVal serialNum As String, ByRef usborHandle As Integer) As Integer

   Public Declare Function rbxNexway_getVersion Lib "rbxNexwayLib.dll" _
      (ByVal nexwayVer As String, ByVal maxLen As Integer, ByRef retLen As Integer) As Integer

   'Nexus Functions
   Public Declare Function rbxNexusConnection_getName Lib "rbxNexwayLib.dll" _
      (ByVal nexusHandle As Integer, ByVal nexusName As String, ByVal maxLen As Integer, ByRef retLen As Integer) As Integer

   Public Declare Function rbxNexusConnection_connect Lib "rbxNexwayLib.dll" _
      (ByVal nexusHandle As Integer) As Integer

   Public Declare Function rbxNexusConnection_disconnect Lib "rbxNexwayLib.dll" _
      (ByVal nexusHandle As Integer) As Integer

   Public Declare Function rbxNexusConnection_getUsborCount Lib "rbxNexwayLib.dll" _
      (ByVal nexusHandle As Integer, ByRef count As Integer) As Integer

   Public Declare Function rbxNexusConnection_getUsborByIndex Lib "rbxNexwayLib.dll" _
      (ByVal nexusHandle As Integer, ByVal index As Integer, ByRef usborHandle As Integer) As Integer

   Public Declare Function rbxNexusConnection_getUsborByName Lib "rbxNexwayLib.dll" _
      (ByVal nexusHandle As Integer, ByVal usborName As String, ByRef usborHandle As Integer) As Integer

   Public Declare Function rbxNexusConnection_getUsborBySerial Lib "rbxNexwayLib.dll" _
      (ByVal nexusHandle As Integer, ByVal serialNum As String, ByRef usborHandle As Integer) As Integer

   Public Declare Function rbxNexusConnection_getUsborListMod Lib "rbxNexwayLib.dll" _
      (ByVal nexusHandle As Integer, ByRef listMod As Integer) As Integer

   Public Declare Function rbxNexusConnection_getRepName Lib "rbxNexwayLib.dll" _
      (ByVal nexusHandle As Integer, ByVal repName As String, ByVal maxLen As Integer, ByRef retLen As Integer) As Integer

   Public Declare Function rbxNexusConnection_getRepLocation Lib "rbxNexwayLib.dll" _
      (ByVal nexusHandle As Integer, ByVal repLoc As String, ByVal maxLen As Integer, ByRef retLen As Integer) As Integer

   Public Declare Function rbxNexusConnection_getRepResponseTimeMs Lib "rbxNexwayLib.dll" _
      (ByVal nexusHandle As Integer, ByRef timeMs As Integer) As Integer

   'Usbor Functions
   Public Declare Function rbxUsbor_getNexusConnection Lib "rbxNexwayLib.dll" _
      (ByVal usborHandle As Integer, ByRef nexusHandle As Integer) As Integer

   Public Declare Function rbxUsbor_getName Lib "rbxNexwayLib.dll" _
      (ByVal usborHandle As Integer, ByVal usborName As String, ByVal maxLen As Integer, ByRef retLen As Integer) As Integer

   Public Declare Function rbxUsbor_getSerialNumber Lib "rbxNexwayLib.dll" _
      (ByVal usborHandle As Integer, ByVal serialNum As String, ByVal maxLen As Integer, ByRef retLen As Integer) As Integer

   Public Declare Function rbxUsbor_getFirmwareVersion Lib "rbxNexwayLib.dll" _
      (ByVal usborHandle As Integer, ByVal firmVer As String, ByVal maxLen As Integer, ByRef retLen As Integer) As Integer

   Public Declare Function rbxUsbor_getConfigName Lib "rbxNexwayLib.dll" _
      (ByVal usborHandle As Integer, ByVal configName As String, ByVal maxLen As Integer, ByRef retLen As Integer) As Integer

   Public Declare Function rbxUsbor_getConfigId Lib "rbxNexwayLib.dll" _
      (ByVal usborHandle As Integer, ByRef configId As Integer) As Integer

   Public Declare Function rbxUsbor_getPropertyHeartbeatInterval Lib "rbxNexwayLib.dll" _
      (ByVal usborHandle As Integer, ByRef intervalMs As Integer) As Integer

   Public Declare Function rbxUsbor_getSensorHeartbeatInterval Lib "rbxNexwayLib.dll" _
      (ByVal usborHandle As Integer, ByRef intervalMs As Integer) As Integer

   Public Declare Function rbxUsbor_getPodCount Lib "rbxNexwayLib.dll" _
      (ByVal usborHandle As Integer, ByRef count As Integer) As Integer

   Public Declare Function rbxUsbor_getPod Lib "rbxNexwayLib.dll" _
      (ByVal usborHandle As Integer, ByVal podId As Integer, ByRef podHandle As Integer) As Integer

   Public Declare Function rbxUsbor_getRepName Lib "rbxNexwayLib.dll" _
      (ByVal usborHandle As Integer, ByVal repName As String, ByVal maxLen As Integer, ByRef retLen As Integer) As Integer

   Public Declare Function rbxUsbor_getRepLocation Lib "rbxNexwayLib.dll" _
      (ByVal usborHandle As Integer, ByVal repLoc As String, ByVal maxLen As Integer, ByRef retLen As Integer) As Integer

   Public Declare Function rbxUsbor_getRepResponseTimeMs Lib "rbxNexwayLib.dll" _
      (ByVal usborHandle As Integer, ByRef timeMs As Integer) As Integer

   'Pod Functions
   Public Declare Function rbxPod_getUsbor Lib "rbxNexwayLib.dll" _
      (ByVal podHandle As Integer, ByRef usborHandle As Integer) As Integer

   Public Declare Function rbxPod_getName Lib "rbxNexwayLib.dll" _
      (ByVal podHandle As Integer, ByVal podName As String, ByVal maxLen As Integer, ByRef retLen As Integer) As Integer

   Public Declare Function rbxPod_getId Lib "rbxNexwayLib.dll" _
      (ByVal podHandle As Integer, ByRef podId As Integer) As Integer

   Public Declare Function rbxPod_getServoCount Lib "rbxNexwayLib.dll" _
      (ByVal podHandle As Integer, ByRef count As Integer) As Integer

   Public Declare Function rbxPod_getDigoutCount Lib "rbxNexwayLib.dll" _
      (ByVal podHandle As Integer, ByRef count As Integer) As Integer

   Public Declare Function rbxPod_getSensorCount Lib "rbxNexwayLib.dll" _
      (ByVal podHandle As Integer, ByRef count As Integer) As Integer

   Public Declare Function rbxPod_getPropertyHeartbeatTick Lib "rbxNexwayLib.dll" _
      (ByVal podHandle As Integer, ByRef tick As Integer) As Integer

   Public Declare Function rbxPod_getSensorHeartbeatTick Lib "rbxNexwayLib.dll" _
      (ByVal podHandle As Integer, ByRef tick As Integer) As Integer

   Public Declare Function rbxPod_getAction Lib "rbxNexwayLib.dll" _
      (ByVal podHandle As Integer, ByRef action As Integer) As Integer

   Public Declare Function rbxPod_isPaused Lib "rbxNexwayLib.dll" _
      (ByVal podHandle As Integer, ByRef paused As Integer) As Integer

   Public Declare Function rbxPod_pause Lib "rbxNexwayLib.dll" _
      (ByVal podHandle As Integer) As Integer

   Public Declare Function rbxPod_unpause Lib "rbxNexwayLib.dll" _
      (ByVal podHandle As Integer) As Integer

   Public Declare Function rbxPod_stopScript Lib "rbxNexwayLib.dll" _
      (ByVal podHandle As Integer) As Integer

   Public Declare Function rbxPod_powerAllServos Lib "rbxNexwayLib.dll" _
      (ByVal podHandle As Integer, ByVal power As Integer) As Integer

   Public Declare Function rbxPod_runQuickScript Lib "rbxNexwayLib.dll" _
      (ByVal podHandle As Integer, ByVal scriptText As String, ByRef seq As Integer) As Integer

   Public Declare Function rbxPod_runQuickScriptEx Lib "rbxNexwayLib.dll" _
      (ByVal podHandle As Integer, ByVal scriptText As String, ByVal scriptName As String, ByRef seq As Integer) As Integer

   Public Declare Function rbxPod_assignMainScript Lib "rbxNexwayLib.dll" _
      (ByVal podHandle As Integer, ByVal scriptText As String, ByVal scriptName As String, ByRef seq As Integer) As Integer

   Public Declare Function rbxPod_assignMainScriptFromFile Lib "rbxNexwayLib.dll" _
      (ByVal podHandle As Integer, ByVal filename As String, ByRef seq As Integer) As Integer

   Public Declare Function rbxPod_runMainLines Lib "rbxNexwayLib.dll" _
      (ByVal podHandle As Integer, ByVal startLine As Integer, ByVal endLine As Integer, ByRef seq As Integer) As Integer

   Public Declare Function rbxPod_runMainMacro Lib "rbxNexwayLib.dll" _
      (ByVal podHandle As Integer, ByVal macroName As String, ByVal iterations As Integer, ByRef seq As Integer) As Integer

   Public Declare Function rbxPod_runTeachCommand Lib "rbxNexwayLib.dll" _
      (ByVal podHandle As Integer, ByVal teachCommand As String) As Integer

   Public Declare Function rbxPod_isPodCmdStarted Lib "rbxNexwayLib.dll" _
      (ByVal podHandle As Integer, ByVal seq As Integer, ByRef started As Integer) As Integer

   Public Declare Function rbxPod_isPodCmdFinished Lib "rbxNexwayLib.dll" _
      (ByVal podHandle As Integer, ByVal seq As Integer, ByRef finished As Integer) As Integer

   'Servo Functions
   Public Declare Function rbxServo_getPort Lib "rbxNexwayLib.dll" _
      (ByVal podHandle As Integer, ByVal servoID As Integer, ByRef port As Integer) As Integer

   Public Declare Function rbxServo_getMod Lib "rbxNexwayLib.dll" _
      (ByVal podHandle As Integer, ByVal servoID As Integer, ByRef servoMod As Integer) As Integer

   Public Declare Function rbxServo_getPropertyValue Lib "rbxNexwayLib.dll" _
      (ByVal podHandle As Integer, ByVal servoID As Integer, ByVal prop As Integer, ByRef propVal As Integer) As Integer

   'Digout Functions
   Public Declare Function rbxDigout_getPort Lib "rbxNexwayLib.dll" _
      (ByVal podHandle As Integer, ByVal digoutId As Integer, ByRef port As Integer) As Integer

   Public Declare Function rbxDigout_getMod Lib "rbxNexwayLib.dll" _
      (ByVal podHandle As Integer, ByVal digoutId As Integer, ByRef digoutMod As Integer) As Integer

   Public Declare Function rbxDigout_getValue Lib "rbxNexwayLib.dll" _
      (ByVal podHandle As Integer, ByVal digoutId As Integer, ByRef digoutVal As Integer) As Integer

   'Sensor Functions
   Public Declare Function rbxSensor_getPort Lib "rbxNexwayLib.dll" _
      (ByVal podHandle As Integer, ByVal sensorId As Integer, ByRef port As Integer) As Integer

   Public Declare Function rbxSensor_getType Lib "rbxNexwayLib.dll" _
      (ByVal podHandle As Integer, ByVal sensorId As Integer, ByVal sensorType As Integer) As Integer

   Public Declare Function rbxSensor_getMod Lib "rbxNexwayLib.dll" _
      (ByVal podHandle As Integer, ByVal sensorId As Integer, ByRef sensorMod As Integer) As Integer

   Public Declare Function rbxSensor_getValue Lib "rbxNexwayLib.dll" _
      (ByVal podHandle As Integer, ByVal sensorId As Integer, ByRef sensorVal As Integer) As Integer

   'System functions needed by rbxVerifyNexwayLibVersion()
   Declare Function LoadLibrary Lib "kernel32" _
      Alias "LoadLibraryA" (ByVal lpLibFileName As String) As Integer

   Declare Function GetModuleFileName Lib "kernel32" _
      Alias "GetModuleFileNameA" (ByVal hModule As Integer, ByVal lpFileName As String, ByVal nSize As Integer) As Integer

   Declare Function FreeLibrary Lib "kernel32" _
      (ByVal hLibModule As Integer) As Integer

   '---------------------------------------------------------------------------
   'This function verifies that the version of the Nexway library found is a
   'compatible version.
   '---------------------------------------------------------------------------
   Private Function rbxVerifyNexwayLibVersion(ByRef libPath As String) As Integer
      On Error GoTo FnError

      Dim rbxErr As Integer
      Dim libVersion As Integer
      Dim expectedVer As String
      Dim actualVer As String
      Dim tempVer As String
      Dim errMsg As String
      Dim yesNo As Integer

      'Get NexwayLib version
      rbxErr = rbxLib_getVersion(libVersion)

      If rbxErr <> RBX_E_SUCCESS Then
         Return rbxErr
      End If

      'Check for major version mismatch
      If (libVersion And &HFF000000) <> (RBX_NEXWAYLIB_VERSION And &HFF000000) Then
         'Convert version numbers to printable format
         expectedVer = Hex(RBX_NEXWAYLIB_VERSION).PadLeft(8, "0"c)
         actualVer = Hex(libVersion).PadLeft(8, "0"c)

         tempVer = Mid(expectedVer, 1, 2) & "." _
                 & Mid(expectedVer, 3, 2) & "." _
                 & Mid(expectedVer, 5, 2) & "." _
                 & Mid(expectedVer, 7, 2)
         expectedVer = tempVer

         tempVer = Mid(actualVer, 1, 2) & "." _
                 & Mid(actualVer, 3, 2) & "." _
                 & Mid(actualVer, 5, 2) & "." _
                 & Mid(actualVer, 7, 2)
         actualVer = tempVer

         errMsg = "Incompatible version of the Robix Nexway library encountered." & vbLf _
                & "Created for library version " & expectedVer & vbLf _
                & "Library located: " & libPath & vbLf _
                & "        version: " & actualVer

         'Display error message
         MsgBox(errMsg, MsgBoxStyle.OKOnly _
                        Or MsgBoxStyle.Critical _
                        Or MsgBoxStyle.ApplicationModal, _
                "Robix NexwayLib Error")

         'Terminate program
         End
      End If

      Return RBX_E_SUCCESS

FnError:
      If Err.Number = 453 Then
         Return RBX_E_LIB_FUNCTION_NOT_FOUND
      Else
         Return RBX_E_LIB_LOAD_FAILURE
      End If

   End Function

   '---------------------------------------------------------------------------
   'This function is part of our internal development check to ensure that
   'library function declarations are correct. Applications do not need to call
   'this function.
   '---------------------------------------------------------------------------
   Private Function rbxCheckNexwayLibFunctions() As Integer
      On Error GoTo FnError

      'Dummy variables
      Dim r As Integer
      Dim lg As Integer
      Dim ret As Integer
      Dim st As String = vbNullString

      'Execute all library functions to ensure they exist
      r = rbxLib_getVersion(lg)
      r = rbxLib_getErrMsg(lg, st, lg, lg)
      r = rbxLib_setJvmClassPath(st)
      r = rbxLib_setJvm(st)
      r = rbxLib_addJvmOption(st)
      r = rbxLib_addJvmProperty(st, st)
      'rbxLib_initJvm() should only be called once, and we don't
      'want to call it here
      ' r = rbxLib_initJvm(st)

      r = rbxNexway_getName(st, lg, lg)
      r = rbxNexway_createNexusConnection(st, lg)
      r = rbxNexway_createNexusConnectionEx(st, lg, lg)
      r = rbxNexway_removeNexusConnection(lg)
      r = rbxNexway_getNexusConnectionCount(lg)
      r = rbxNexway_getNexusConnection(lg, lg)
      r = rbxNexway_getNexusConnectionListMod(lg)
      r = rbxNexway_getUsborByName(st, lg)
      r = rbxNexway_getUsborBySerial(st, lg)
      r = rbxNexway_getVersion(st, lg, lg)

      r = rbxNexusConnection_getName(lg, st, lg, lg)
      r = rbxNexusConnection_connect(lg)
      r = rbxNexusConnection_disconnect(lg)
      r = rbxNexusConnection_getUsborCount(lg, lg)
      r = rbxNexusConnection_getUsborByIndex(lg, lg, lg)
      r = rbxNexusConnection_getUsborByName(lg, st, lg)
      r = rbxNexusConnection_getUsborBySerial(lg, st, lg)
      r = rbxNexusConnection_getUsborListMod(lg, lg)
      r = rbxNexusConnection_getRepName(lg, st, lg, lg)
      r = rbxNexusConnection_getRepLocation(lg, st, lg, lg)
      r = rbxNexusConnection_getRepResponseTimeMs(lg, lg)

      r = rbxUsbor_getNexusConnection(lg, lg)
      r = rbxUsbor_getName(lg, st, lg, lg)
      r = rbxUsbor_getSerialNumber(lg, st, lg, lg)
      r = rbxUsbor_getFirmwareVersion(lg, st, lg, lg)
      r = rbxUsbor_getConfigName(lg, st, lg, lg)
      r = rbxUsbor_getConfigId(lg, lg)
      r = rbxUsbor_getPropertyHeartbeatInterval(lg, lg)
      r = rbxUsbor_getSensorHeartbeatInterval(lg, lg)
      r = rbxUsbor_getPodCount(lg, lg)
      r = rbxUsbor_getPod(lg, lg, lg)
      r = rbxUsbor_getRepName(lg, st, lg, lg)
      r = rbxUsbor_getRepLocation(lg, st, lg, lg)
      r = rbxUsbor_getRepResponseTimeMs(lg, lg)

      r = rbxPod_getUsbor(lg, lg)
      r = rbxPod_getName(lg, st, lg, lg)
      r = rbxPod_getId(lg, lg)
      r = rbxPod_getServoCount(lg, lg)
      r = rbxPod_getDigoutCount(lg, lg)
      r = rbxPod_getSensorCount(lg, lg)
      r = rbxPod_getPropertyHeartbeatTick(lg, lg)
      r = rbxPod_getSensorHeartbeatTick(lg, lg)
      r = rbxPod_getAction(lg, lg)
      r = rbxPod_isPaused(lg, lg)
      r = rbxPod_pause(lg)
      r = rbxPod_unpause(lg)
      r = rbxPod_stopScript(lg)
      r = rbxPod_powerAllServos(lg, lg)
      r = rbxPod_runQuickScript(lg, st, lg)
      r = rbxPod_runQuickScriptEx(lg, st, st, lg)
      r = rbxPod_assignMainScript(lg, st, st, lg)
      r = rbxPod_assignMainScriptFromFile(lg, st, lg)
      r = rbxPod_runMainLines(lg, lg, lg, lg)
      r = rbxPod_runMainMacro(lg, st, lg, lg)
      r = rbxPod_runTeachCommand(lg, st)
      r = rbxPod_isPodCmdStarted(lg, lg, lg)
      r = rbxPod_isPodCmdFinished(lg, lg, lg)

      r = rbxServo_getPort(lg, lg, lg)
      r = rbxServo_getMod(lg, lg, lg)
      r = rbxServo_getPropertyValue(lg, lg, lg, lg)

      r = rbxDigout_getPort(lg, lg, lg)
      r = rbxDigout_getMod(lg, lg, lg)
      r = rbxDigout_getValue(lg, lg, lg)

      r = rbxSensor_getPort(lg, lg, lg)
      r = rbxSensor_getType(lg, lg, lg)
      r = rbxSensor_getMod(lg, lg, lg)
      r = rbxSensor_getValue(lg, lg, lg)

      Return RBX_E_SUCCESS

FnError:
      If Err.Number = 453 Then
         Return RBX_E_LIB_FUNCTION_NOT_FOUND
      Else
         Return RBX_E_LIB_LOAD_FAILURE
      End If

   End Function

   '---------------------------------------------------------------------------
   'Attempts to find a file according to our search path.
   '---------------------------------------------------------------------------
   Private Function rbxFindRbxFile(ByRef fileName As String) As String
      Dim baseDir As String
      Dim filePath As String

      '***** Current Working Directory *****
      baseDir = CurDir()
      filePath = baseDir & "\" & fileName

      If File.Exists(filePath) Then
         Return filePath
      End If

      '***** USBOR_HOME environment variable *****
      baseDir = Environ("USBOR_HOME")
      If baseDir <> "" Then
         filePath = baseDir & "\" & RBX_USBOR_LIB_SUBDIR & "\" & fileName
         If File.Exists(filePath) Then
            Return filePath
         End If
      End If

      '***** Hard-coded Install Path *****
      baseDir = RBX_USBOR_INSTALL_DIR
      filePath = baseDir & "\" & RBX_USBOR_LIB_SUBDIR & "\" & fileName
      If File.Exists(filePath) Then
         Return filePath
      End If

      'File not found
      Return Nothing
   End Function

   '---------------------------------------------------------------------------
   'Loads library and checks version.
   '---------------------------------------------------------------------------
   Private Function rbxLoadNexwayLibrary(ByRef libPath As String) As Integer
      Dim rbxErr As Integer
      Dim prevCwd As String
      Dim libDir As String

      libDir = Path.GetDirectoryName(libPath)

      'Switch current dir so system can find Nexway library
      prevCwd = CurDir()
      ChDir(libDir)

      rbxErr = rbxVerifyNexwayLibVersion(libPath)

      'Restore current dir
      ChDir(prevCwd)

      Return rbxErr
   End Function

   '---------------------------------------------------------------------------
   'This function loads and initializes the Nexway library with the specified
   'app name, library path, and JAR path. When this function returns
   'RBX_E_SUCCESS, the Nexway library will be ready for use.
   '
   'NOTE:
   'This function is only for advanced users with special needs. Do not call
   'this function unless you have a good reason to do so. Instead, use
   'rbxInitNexwayLib().
   '---------------------------------------------------------------------------
   Public Function rbxInitNexwayLibEx(ByVal appName As String, _
                                      ByVal libPath As String, _
                                      ByVal jarPath As String) As Integer
      On Error GoTo LibError

      Static Dim initLibCalled As Boolean = False

      Dim rbxErr As Integer

      If initLibCalled Then
         Return RBX_E_LIB_LOAD_FAILURE
      End If
      initLibCalled = True

      If appName = Nothing Then
         appName = RBX_DEFAULT_APP_NAME
      End If

      '***** Load Nexway Library *****

      If libPath <> Nothing Then
         'Explicit library path
         If Not File.Exists(libPath) Then
            Return RBX_E_FILE_NOT_FOUND
         End If
         rbxErr = rbxLoadNexwayLibrary(libPath)
      Else
         'Search for library
         Dim path As String = rbxFindRbxFile(RBX_NEXWAYLIB_NAME)
         If path = Nothing Then
            Return RBX_E_FILE_NOT_FOUND
         End If
         rbxErr = rbxLoadNexwayLibrary(path)
      End If

      If rbxErr <> RBX_E_SUCCESS Then
         Return rbxErr
      End If

      '***** Locate Usbor JAR *****

      If jarPath <> Nothing Then
         'Explicit JAR path
         If Not File.Exists(jarPath) Then
            Return RBX_E_FILE_NOT_FOUND
         End If
         rbxErr = rbxLib_setJvmClassPath(jarPath)
      Else
         'Search for JAR file
         Dim path As String = rbxFindRbxFile(RBX_USBORJAR_NAME)
         If path = Nothing Then
            Return RBX_E_FILE_NOT_FOUND
         End If
         rbxErr = rbxLib_setJvmClassPath(path)
      End If

      If rbxErr <> RBX_E_SUCCESS Then
         Return rbxErr
      End If

      'Additional JVM configuration should be performed here,
      'before the JVM is initialized.

      'Initialize JVM
      rbxErr = rbxLib_initJvm(appName)
      If rbxErr <> RBX_E_SUCCESS Then
         Return rbxErr
      End If

      rbxErr = rbxCheckNexwayLibFunctions()
      If rbxErr <> RBX_E_SUCCESS Then
         Return rbxErr
      End If

      Return RBX_E_SUCCESS

LibError:
      If Err.Number = 453 Then
         Return RBX_E_LIB_FUNCTION_NOT_FOUND
      Else
         Return RBX_E_LIB_LOAD_FAILURE
      End If

   End Function

   '---------------------------------------------------------------------------
   'This function loads and initializes the Nexway library using the specified
   'app name and the default library directory and JAR path. When this function
   'returns RBX_E_SUCCESS, the Nexway library will be ready for use.
   '---------------------------------------------------------------------------
   Public Function rbxInitNexwayLib(ByVal appName As String) As Integer
      Return rbxInitNexwayLibEx(appName, Nothing, Nothing)
   End Function

End Module
