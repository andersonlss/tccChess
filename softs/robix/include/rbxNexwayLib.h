/*******************************************************************/
/* rbxNexwayLib.h                                                  */
/*  version: 1.0.0                                                 */
/*  last modified: 2005-02-28                                      */
/*                                                                 */
/* For use with the Robix(tm) Usbor Nexway Library                 */
/*  rbxNexwayLib.dll   (Windows)                                   */
/*  rbxNexwayLib.dylib (Mac OSX)                                   */
/*  rbxNexwayLib.so    (Linux)                                     */
/*                                                                 */
/* This file includes:                                             */
/*  - Error code definitions                                       */
/*  - Enumerated data types                                        */
/*  - Usbor Nexway constants                                       */
/*  - Library function pointer declarations                        */
/*  - inline functions for loading and initializing                */
/*    the Nexway library                                           */
/*                                                                 */
/* Note:                                                           */
/*  Symbols in this file that begin with h_ are for internal use   */
/*  only and should be considered private to this header file.     */
/*                                                                 */
/* Copyright (c) 2004-2005                                         */
/* Robix                                                           */
/* www.robix.com                                                   */
/*                                                                 */
/*******************************************************************/
#ifndef RBX_NEXWAY_LIB_H
#define RBX_NEXWAY_LIB_H  1

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <limits.h>
#include <sys/stat.h>

#ifdef __cplusplus
 extern "C" {
#endif


/* Windows-specific definitions */
#if defined(_WIN32)
# include <windows.h>
# include <direct.h>

# define RBX_USBOR_INSTALL_DIR  "C:\\Program Files\\Robix\\Usbor"
# define RBX_NEXWAYLIB_NAME     "rbxNexwayLib.dll"
# define RBX_FILESEP            "\\"

# define RBX_SYM_PREFIX
# define RBX_CALL_CONV   __stdcall
# define RBX_INLINE      __inline
# define RBX_MAX_PATH    MAX_PATH

# define RBX_LIB_HANDLE            HINSTANCE
# define h_rbxLoadLib(path)        ( LoadLibrary(path) )
# define h_rbxFindFunc(hLib,func)  ( GetProcAddress(hLib,func) )
# define h_rbxGetCwd()             ( _getcwd(NULL,0) )

RBX_INLINE
void h_rbxReportError( const char* title, const char* msg )
{
   /* Beep and display error message */
   MessageBeep ( MB_ICONEXCLAMATION );
   MessageBox ( NULL, msg, title,
                MB_OK | MB_ICONERROR | MB_TASKMODAL );
}

#endif /* _WIN32 */


/* Mac OSX-specific definitions */
#if defined(__APPLE__) && defined(__MACH__)
# include <mach-o/dyld.h>
# include <unistd.h>

# define RBX_USBOR_INSTALL_DIR  "/Applications/Robix/Usbor"
# define RBX_NEXWAYLIB_NAME     "rbxNexwayLib.dylib"
# define RBX_FILESEP            "/"

# define RBX_SYM_PREFIX  "_"
# define RBX_CALL_CONV
# define RBX_INLINE      inline
# define RBX_MAX_PATH    PATH_MAX

# define RBX_LIB_HANDLE      const struct mach_header*
# define h_rbxLoadLib(path)  ( NSAddImage( path, NSADDIMAGE_OPTION_RETURN_ON_ERROR ) );

RBX_INLINE
void* h_rbxFindFunc( RBX_LIB_HANDLE hLib, const char* func )
{
   NSSymbol symb =
      NSLookupSymbolInImage( hLib, func,
                             NSLOOKUPSYMBOLINIMAGE_OPTION_BIND |
                             NSLOOKUPSYMBOLINIMAGE_OPTION_RETURN_ON_ERROR );
   if ( !symb )
      return NULL;
   return NSAddressOfSymbol( symb );
}

# define h_rbxGetCwd()                ( getcwd(NULL,0) )
# define h_rbxReportError(title,msg)  do { fprintf( stderr, "%s:\n%s", title, msg ); } while(0)

#endif /* __APPLE__ */


/* Linux-specific definitions */
#if defined(__linux__)
# include <dlfcn.h>
# include <unistd.h>

# define RBX_USBOR_INSTALL_DIR  "/opt/Robix/Usbor"
# define RBX_NEXWAYLIB_NAME     "rbxNexwayLib.so"
# define RBX_FILESEP            "/"

# define RBX_SYM_PREFIX
# define RBX_CALL_CONV
# define RBX_INLINE      __inline__
# define RBX_MAX_PATH    PATH_MAX

# define RBX_LIB_HANDLE               void*
# define h_rbxLoadLib(path)           ( dlopen(path,RTLD_NOW) )
# define h_rbxFindFunc(hLib,func)     ( dlsym(hLib,func) )
# define h_rbxGetCwd()                ( getcwd(NULL,0) )
# define h_rbxReportError(title,msg)  do { fprintf( stderr, "%s:\n%s", title, msg ); } while(0)

#endif /* __linux__ */


#define RBX_USBORJAR_NAME     "rbxUsbor.jar"
#define RBX_USBOR_LIB_SUBDIR  "lib"
#define RBX_DEFAULT_APP_NAME  "Anonymous C App"

/* Target NexwayLib version
   01.00.00.00 (ver) = 0x01000000 (hex) */
#define RBX_NEXWAYLIB_VERSION  0x01000000

/* Robix Error Codes
   NOTE: When diplaying these error codes, always prefix them
   with "RBX_" to prevent confusion with system error codes.
   For example, "Error running script. Error Code: RBX_1015"
*/
#define RBX_E_SUCCESS                        0
#define RBX_E_GENERAL_EXCEPTION           1000
#define RBX_E_MEMORY_ALLOCATION           1001
#define RBX_E_FILE_NOT_FOUND              1002
#define RBX_E_FILE_ACCESS_DENIED          1003
#define RBX_E_LIB_LOAD_FAILURE            1004
#define RBX_E_LIB_FUNCTION_NOT_FOUND      1005
#define RBX_E_POD_HANDLE_INVALID          1015
#define RBX_E_SERVO_ID_INVALID            1022
#define RBX_E_SERVO_PROP_INVALID          1023
#define RBX_E_NULL_POINTER                1032
#define RBX_E_ADMIN_REQUIRED              1033
#define RBX_E_NOT_YET_IMPLEMENTED         2000
#define RBX_E_LIB_UNLOAD_FAILURE          2001
#define RBX_E_JVM_INIT_FAILURE            2002
#define RBX_E_UNKNOWN_HOST                2003
#define RBX_E_IO_ERROR                    2004
#define RBX_E_UNSUPPORTED_VERSION         2005
#define RBX_E_ILLEGAL_STATE               2006
#define RBX_E_ILLEGAL_ARGUMENT            2007
#define RBX_E_BUFFER_TOO_SMALL            2008
#define RBX_E_NEXUS_HANDLE_INVALID        2009
#define RBX_E_USBOR_HANDLE_INVALID        2010
#define RBX_E_POD_ID_INVALID              2011
#define RBX_E_DIGOUT_ID_INVALID           2012
#define RBX_E_SENSOR_ID_INVALID           2013
#define RBX_E_JVM_NOT_INITIALIZED         2014
#define RBX_E_JVM_NOT_FOUND               2015


/* Pod Actions */
enum rbx_PodAction
{
   rbxPodAction_IDLE     = 0,  /* Pod is idle */
   rbxPodAction_WORKING  = 1,  /* Pod is busy */
   rbxPodAction_STOPPING = 2   /* Pod is stopping (from Working to Idle) */
};

/* Servo Properties */
enum rbx_ServoProp
{
   rbxServoProp_POS     = 0,  /* current position (relative to p0pos) */
   rbxServoProp_MINPOS  = 1,  /* minimum position (relative to p0pos) */
   rbxServoProp_MAXPOS  = 2,  /* maximum position (relative to p0pos) */
   rbxServoProp_INITPOS = 3,  /* initial position (relative to p0pos) */
   rbxServoProp_P0POS   = 4,  /* physical 0 position */
   rbxServoProp_MAXSPD  = 5,  /* maximum speed */
   rbxServoProp_ACCEL   = 6,  /* acceleration */
   rbxServoProp_DECEL   = 7,  /* deceleration */
   rbxServoProp_INVERT  = 8,  /* invert status (0-not inverted, 1-inverted) */
   rbxServoProp_PINNED  = 9,  /* pinned status (0-not pinned, 1-pinned) */
   rbxServoProp_POWER   = 10, /* power status  (as a percent, 0-100) */
   rbxServoProp_ABSI    = 11  /* absolute value of average servo electrical current */
};

/* Sensor types */
enum rbx_SensorType
{
   rbxSensorType_ADC        = 4,
   rbxSensorType_QUADRATURE = 5
};

#ifndef NEXWAYLIB_INTERNAL_BUILD

/********************
 * Debugging macro  *
 ********************/
#ifdef RBX_DEBUG_LIBLOADER
# define h_rbxDbgMsgNh(printf_exp)  do { printf printf_exp; fflush(stdout); } while(0)
#else
# define h_rbxDbgMsgNh(printf_exp)  do { } while(0)
#endif

#define h_rbxDbgMsg(printf_exp)  do { h_rbxDbgMsgNh(( "rbxNexwayLib.h : " )); \
                                      h_rbxDbgMsgNh( printf_exp );            } while(0)

/********************
 * Global variables *
 ********************/
#ifdef RBX_MAIN
# define RBX_GLOBAL_VAR(type,name,val)  type name = val
#else
# define RBX_GLOBAL_VAR(type,name,val)  extern type name
#endif

RBX_GLOBAL_VAR( RBX_LIB_HANDLE, h_RbxNexwayLibHandle, NULL ); /* Lib handle */
RBX_GLOBAL_VAR( int, h_RbxNexwayLibLinkErr, 0 );              /* Error flag */
RBX_GLOBAL_VAR( int, h_RbxInitLibCalled, 0 );                 /* Lib-init flag */


/*********************************
 * Function pointer declarations *
 *********************************/
#define RBX_PROC_T(name) name##_t

#define NEXWAYLIB_PROC(name,params) \
   typedef int (RBX_CALL_CONV *RBX_PROC_T(name)) params; \
   RBX_GLOBAL_VAR( RBX_PROC_T(name), name, NULL )

#define LINK_NEXWAYLIB_PROC(name) \
   do { \
      name = (RBX_PROC_T(name))h_rbxFindFunc( h_RbxNexwayLibHandle, (RBX_SYM_PREFIX #name) ); \
      if ( name == NULL ) { h_RbxNexwayLibLinkErr = 1; } \
   } while(0)

NEXWAYLIB_PROC( rbxLib_getVersion, ( int *libVersion ) );
NEXWAYLIB_PROC( rbxLib_getErrMsg, ( int errCode, char *errMsg, int maxLen, int *retLen ) );
NEXWAYLIB_PROC( rbxLib_setJvm, ( const char *jvmLibPath ) );
NEXWAYLIB_PROC( rbxLib_setJvmClassPath, ( const char *classpath ) );
NEXWAYLIB_PROC( rbxLib_addJvmOption, ( const char *option ) );
NEXWAYLIB_PROC( rbxLib_addJvmProperty, ( const char *property, const char *value ) );
NEXWAYLIB_PROC( rbxLib_initJvm, ( const char *appName ) );

NEXWAYLIB_PROC( rbxNexway_getName, ( char *name, int maxLen, int *retLen ) );
NEXWAYLIB_PROC( rbxNexway_createNexusConnection, ( const char *hostName, int *nexusHandle ) );
NEXWAYLIB_PROC( rbxNexway_createNexusConnectionEx, ( const char *hostName, int port, int *nexusHandle ) );
NEXWAYLIB_PROC( rbxNexway_removeNexusConnection, ( int nexusHandle ) );
NEXWAYLIB_PROC( rbxNexway_getNexusConnectionCount, ( int *count ) );
NEXWAYLIB_PROC( rbxNexway_getNexusConnection, ( int index, int *nexusHandle ) );
NEXWAYLIB_PROC( rbxNexway_getNexusConnectionListMod, ( int *mod ) );
NEXWAYLIB_PROC( rbxNexway_getUsborByName, ( const char *usborName, int *usborHandle ) );
NEXWAYLIB_PROC( rbxNexway_getUsborBySerial, ( const char *serialNum, int *usborHandle ) );
NEXWAYLIB_PROC( rbxNexway_getVersion, ( char *nexwayVer, int maxLen, int *retLen ) );

NEXWAYLIB_PROC( rbxNexusConnection_getName, ( int nexusHandle, char *nexusName, int maxLen, int *retLen ) );
NEXWAYLIB_PROC( rbxNexusConnection_connect, ( int nexusHandle ) );
NEXWAYLIB_PROC( rbxNexusConnection_disconnect, ( int nexusHandle ) );
NEXWAYLIB_PROC( rbxNexusConnection_getUsborCount, ( int nexusHandle, int *count ) );
NEXWAYLIB_PROC( rbxNexusConnection_getUsborByIndex, ( int nexusHandle, int index, int *usborHandle ) );
NEXWAYLIB_PROC( rbxNexusConnection_getUsborByName, ( int nexusHandle, const char *usborName, int *usborHandle ) );
NEXWAYLIB_PROC( rbxNexusConnection_getUsborBySerial, ( int nexusHandle, const char *serialNum, int *usborHandle ) );
NEXWAYLIB_PROC( rbxNexusConnection_getUsborListMod, ( int nexusHandle, int *mod ) );
NEXWAYLIB_PROC( rbxNexusConnection_getRepName, ( int nexusHandle, char *repName, int maxLen, int *retLen ) );
NEXWAYLIB_PROC( rbxNexusConnection_getRepLocation, ( int nexusHandle, char *repLoc, int maxLen, int *retLen ) );
NEXWAYLIB_PROC( rbxNexusConnection_getRepResponseTimeMs, ( int nexusHandle, int *timeMs ) );

NEXWAYLIB_PROC( rbxUsbor_getNexusConnection, ( int usborHandle, int *nexusHandle ) );
NEXWAYLIB_PROC( rbxUsbor_getName, ( int usborHandle, char *usborName, int maxLen, int *retLen ) );
NEXWAYLIB_PROC( rbxUsbor_getSerialNumber, ( int usborHandle, char *serialNum, int maxLen, int *retLen ) );
NEXWAYLIB_PROC( rbxUsbor_getFirmwareVersion, ( int usborHandle, char *firmVer, int maxLen, int *retLen ) );
NEXWAYLIB_PROC( rbxUsbor_getConfigName, ( int usborHandle, char *configName, int maxLen, int *retLen ) );
NEXWAYLIB_PROC( rbxUsbor_getConfigId, ( int usborHandle, int *configId ) );
NEXWAYLIB_PROC( rbxUsbor_getPropertyHeartbeatInterval, ( int usborHandle, int *intervalMs ) );
NEXWAYLIB_PROC( rbxUsbor_getSensorHeartbeatInterval, ( int usborHandle, int *intervalMs ) );
NEXWAYLIB_PROC( rbxUsbor_getPodCount, ( int usborHandle, int *count ) );
NEXWAYLIB_PROC( rbxUsbor_getPod, ( int usborHandle, int podId, int *podHandle ) );
NEXWAYLIB_PROC( rbxUsbor_getRepName, ( int usborHandle, char *repName, int maxLen, int *retLen ) );
NEXWAYLIB_PROC( rbxUsbor_getRepLocation, ( int usborHandle, char *repLoc, int maxLen, int *retLen ) );
NEXWAYLIB_PROC( rbxUsbor_getRepResponseTimeMs, ( int usborHandle, int *timeMs ) );

NEXWAYLIB_PROC( rbxPod_getUsbor, ( int podHandle, int *usborHandle ) );
NEXWAYLIB_PROC( rbxPod_getName, ( int podHandle, char *podName, int maxLen, int *retLen ) );
NEXWAYLIB_PROC( rbxPod_getId, ( int podHandle, int *podId ) );
NEXWAYLIB_PROC( rbxPod_getServoCount, ( int podHandle, int *count ) );
NEXWAYLIB_PROC( rbxPod_getDigoutCount, ( int podHandle, int *count ) );
NEXWAYLIB_PROC( rbxPod_getSensorCount, ( int podHandle, int *count ) );
NEXWAYLIB_PROC( rbxPod_getPropertyHeartbeatTick, ( int podHandle, int *tick ) );
NEXWAYLIB_PROC( rbxPod_getSensorHeartbeatTick, ( int podHandle, int *tick ) );
NEXWAYLIB_PROC( rbxPod_getAction, ( int podHandle, enum rbx_PodAction *action ) );
NEXWAYLIB_PROC( rbxPod_isPaused, ( int podHandle, int *paused ) );
NEXWAYLIB_PROC( rbxPod_pause, ( int podHandle ) );
NEXWAYLIB_PROC( rbxPod_unpause, ( int podHandle ) );
NEXWAYLIB_PROC( rbxPod_stopScript, ( int podHandle ) );
NEXWAYLIB_PROC( rbxPod_powerAllServos, ( int podHandle, int power ) );
NEXWAYLIB_PROC( rbxPod_runQuickScript, ( int podHandle, const char *scriptText, int *seq ) );
NEXWAYLIB_PROC( rbxPod_runQuickScriptEx, ( int podHandle, const char *scriptText, const char *scriptName, int *seq ) );
NEXWAYLIB_PROC( rbxPod_assignMainScript, ( int podHandle, const char *scriptText, const char *scriptName, int *seq ) );
NEXWAYLIB_PROC( rbxPod_assignMainScriptFromFile, ( int podHandle, const char *filename, int *seq ) );
NEXWAYLIB_PROC( rbxPod_runMainLines, ( int podHandle, int startLine, int endLine, int *seq ) );
NEXWAYLIB_PROC( rbxPod_runMainMacro, ( int podHandle, const char *macroName, int iterations, int *seq ) );
NEXWAYLIB_PROC( rbxPod_runTeachCommand, ( int podHandle, const char *teachCommand ) );
NEXWAYLIB_PROC( rbxPod_isPodCmdStarted, ( int podHandle, int seq, int *started ) );
NEXWAYLIB_PROC( rbxPod_isPodCmdFinished, ( int podHandle, int seq, int *finished ) );

NEXWAYLIB_PROC( rbxServo_getPort, ( int podHandle, int servoId, int *port ) );
NEXWAYLIB_PROC( rbxServo_getMod, ( int podHandle, int servoId, int *mod ) );
NEXWAYLIB_PROC( rbxServo_getPropertyValue, ( int podHandle, int servoId, enum rbx_ServoProp prop, int *propVal ) );

NEXWAYLIB_PROC( rbxDigout_getPort, ( int podHandle, int digoutId, int *port ) );
NEXWAYLIB_PROC( rbxDigout_getMod, ( int podHandle, int digoutId, int *mod ) );
NEXWAYLIB_PROC( rbxDigout_getValue, ( int podHandle, int digoutId, int *digoutVal ) );

NEXWAYLIB_PROC( rbxSensor_getPort, ( int podHandle, int sensorId, int *port ) );
NEXWAYLIB_PROC( rbxSensor_getType, ( int podHandle, int sensorId, enum rbx_SensorType *type ) );
NEXWAYLIB_PROC( rbxSensor_getMod, ( int podHandle, int sensorId, int *mod ) );
NEXWAYLIB_PROC( rbxSensor_getValue, ( int podHandle, int sensorId, int *sensorVal ) );


/*******************************
 * Determines if a file exists *
 *******************************/
RBX_INLINE int h_rbxFileExists( const char* path )
{
   struct stat fileStat;
   int exists;

   if ( !path )
      return 0;

   exists = ( stat( path, &fileStat ) != -1 );
   h_rbxDbgMsg(( " File %s: %s\n", (exists ? "exists" : "does not exist" ), path ));
   return exists;
}

/***************************************
 * Allocates and assembles a file path *
 ***************************************/
RBX_INLINE char* h_rbxCreateFilePath( const char* baseDir, const char* subDir,
                                      const char* fileName )
{
   size_t baseLen, subLen, pathLen;
   const char* baseSep;
   const char* subSep;
   char* filePath;

   baseLen = strlen(baseDir);
   subLen = strlen(subDir);
   pathLen = baseLen + subLen + strlen(fileName) + 3;

   filePath = (char*)malloc( pathLen );
   if ( !filePath )
      return NULL;

   /* handle trailing file separators */
   baseSep = ( baseLen != 0 && strcmp( baseDir+(baseLen-1), RBX_FILESEP ) != 0 )
             ? RBX_FILESEP : "";
   subSep =  ( subLen != 0 && strcmp( subDir+(subLen-1), RBX_FILESEP ) != 0 )
             ? RBX_FILESEP : "";

   sprintf( filePath,  "%s%s%s%s%s",
            baseDir, baseSep, subDir, subSep, fileName );
   return filePath;
}

/********************************************************
 * Attempts to find a file according to our search path *
 ********************************************************/
RBX_INLINE char* h_rbxFindRbxFile( const char* fileName )
{
   char* baseDir;
   char* filePath;

   h_rbxDbgMsg(( "Searching for file: %s\n", fileName ));

   /***** Current Working Directory *****/
   baseDir = h_rbxGetCwd();
   if ( !baseDir )
   {  /* prefer the full CWD (for debugging/display), but
         settle for '.' if necessary */
      baseDir = (char*)malloc( 2 );
      if ( !baseDir )
         return NULL;
      baseDir[0] = '.';
      baseDir[1] = '\0';
   }
   h_rbxDbgMsg(( "Searching current working directory (%s)\n", baseDir ));
   filePath = h_rbxCreateFilePath( baseDir, "", fileName );

   free( baseDir );

   if ( !filePath )
      return NULL;
   if ( h_rbxFileExists( filePath ) )
      return filePath;
   free( filePath );

   /***** USBOR_HOME environment variable *****/
   h_rbxDbgMsg(( "Searching based on USBOR_HOME environment var " ));
   baseDir = getenv( "USBOR_HOME" );
   if ( baseDir )
   {
      h_rbxDbgMsgNh(( "(%s)\n", baseDir ));
      filePath = h_rbxCreateFilePath( baseDir, RBX_USBOR_LIB_SUBDIR, fileName );
      /* note we must not free 'baseDir' returned by getenv() */
      if ( !filePath )
         return NULL;
      if ( h_rbxFileExists( filePath ) )
         return filePath;
      free( filePath );
   }
   else
   {
      h_rbxDbgMsgNh(( "(not defined)\n" ));
   }

   /***** Hard-coded Install Path *****/
   h_rbxDbgMsg(( "Searching #defined path: RBX_USBOR_INSTALL_DIR (%s)\n", RBX_USBOR_INSTALL_DIR ));
   filePath = h_rbxCreateFilePath( RBX_USBOR_INSTALL_DIR, RBX_USBOR_LIB_SUBDIR,
                                   fileName );

   if ( !filePath )
      return NULL;
   if ( h_rbxFileExists( filePath ) )
      return filePath;
   free( filePath );
   return NULL;
}

/**************************************************************
 * Loads library, checks version, and links function pointers *
 **************************************************************/
RBX_INLINE int h_rbxLoadNexwayLibrary( const char* libPath )
{
   int err;
   int libVer;

   h_RbxNexwayLibLinkErr = 0;

   if ( !libPath )
      return RBX_E_LIB_LOAD_FAILURE;

   h_rbxDbgMsg(( "Attempting to load library: %s\n", libPath ));

   /* Load library */
   h_RbxNexwayLibHandle = h_rbxLoadLib( libPath );
   if ( !h_RbxNexwayLibHandle )
      return RBX_E_LIB_LOAD_FAILURE;

   h_rbxDbgMsg(( "Checking library version...\n" ));

   /* Link function pointer for version-getter; should be
      available in all releases of the Nexway library */
   LINK_NEXWAYLIB_PROC( rbxLib_getVersion );

   if ( h_RbxNexwayLibLinkErr )
      return RBX_E_LIB_FUNCTION_NOT_FOUND;

   /* Get NexwayLib version */
   err = rbxLib_getVersion( &libVer );
   if ( err != RBX_E_SUCCESS )
      return err;

   h_rbxDbgMsg(( "Library version = %x.%x.%x.%x\n",
               ((libVer >> 24) & 0xFF), ((libVer >> 16) & 0xFF),
               ((libVer >> 8)  & 0xFF), ((libVer >> 0)  & 0xFF) ));

   /* Check for major version mismatch */
   if ( ( libVer & 0xFF000000 ) != ( RBX_NEXWAYLIB_VERSION & 0xFF000000 ) )
   {
      char expectedVer[12];
      char actualVer[12];
      char* msg;
      char* fmt = "Incompatible version of the Robix Nexway library encountered.\n" \
                  "Created for library version %s.\n" \
                  "Library located: %s\n" \
                  "        version: %s\n\n";

      /* Convert version numbers to printable format */
      sprintf( expectedVer, "%x.%x.%x.%x",
               ((RBX_NEXWAYLIB_VERSION >> 24) & 0xFF),
               ((RBX_NEXWAYLIB_VERSION >> 16) & 0xFF),
               ((RBX_NEXWAYLIB_VERSION >> 8)  & 0xFF),
               ((RBX_NEXWAYLIB_VERSION >> 0)  & 0xFF) );

      sprintf( actualVer, "%x.%x.%x.%x",
               ((libVer >> 24) & 0xFF),
               ((libVer >> 16) & 0xFF),
               ((libVer >> 8)  & 0xFF),
               ((libVer >> 0)  & 0xFF) );

      msg = (char*)malloc( strlen(fmt) + strlen(libPath) +
                           strlen(expectedVer) + strlen(actualVer) );
      if ( msg )
      {
         sprintf( msg, fmt, libPath, expectedVer, actualVer );
         h_rbxReportError( "Robix NexwayLib Error", msg );
         free( msg );
      }
      return RBX_E_UNSUPPORTED_VERSION;
   }

   h_rbxDbgMsg(( "Linking function pointers...\n" ));

   /* Assign Function Pointers */
   /* LINK_NEXWAYLIB_PROC( rbxLib_getVersion ); */
   LINK_NEXWAYLIB_PROC( rbxLib_getErrMsg );
   LINK_NEXWAYLIB_PROC( rbxLib_setJvm );
   LINK_NEXWAYLIB_PROC( rbxLib_setJvmClassPath );
   LINK_NEXWAYLIB_PROC( rbxLib_addJvmOption );
   LINK_NEXWAYLIB_PROC( rbxLib_addJvmProperty );
   LINK_NEXWAYLIB_PROC( rbxLib_initJvm );
   LINK_NEXWAYLIB_PROC( rbxNexway_getName );
   LINK_NEXWAYLIB_PROC( rbxNexway_createNexusConnection );
   LINK_NEXWAYLIB_PROC( rbxNexway_createNexusConnectionEx );
   LINK_NEXWAYLIB_PROC( rbxNexway_removeNexusConnection );
   LINK_NEXWAYLIB_PROC( rbxNexway_getNexusConnectionCount );
   LINK_NEXWAYLIB_PROC( rbxNexway_getNexusConnection );
   LINK_NEXWAYLIB_PROC( rbxNexway_getNexusConnectionListMod );
   LINK_NEXWAYLIB_PROC( rbxNexway_getUsborByName );
   LINK_NEXWAYLIB_PROC( rbxNexway_getUsborBySerial );
   LINK_NEXWAYLIB_PROC( rbxNexway_getVersion );
   LINK_NEXWAYLIB_PROC( rbxNexusConnection_getName );
   LINK_NEXWAYLIB_PROC( rbxNexusConnection_connect );
   LINK_NEXWAYLIB_PROC( rbxNexusConnection_disconnect );
   LINK_NEXWAYLIB_PROC( rbxNexusConnection_getUsborCount );
   LINK_NEXWAYLIB_PROC( rbxNexusConnection_getUsborByIndex );
   LINK_NEXWAYLIB_PROC( rbxNexusConnection_getUsborByName );
   LINK_NEXWAYLIB_PROC( rbxNexusConnection_getUsborBySerial );
   LINK_NEXWAYLIB_PROC( rbxNexusConnection_getUsborListMod );
   LINK_NEXWAYLIB_PROC( rbxNexusConnection_getRepName );
   LINK_NEXWAYLIB_PROC( rbxNexusConnection_getRepLocation );
   LINK_NEXWAYLIB_PROC( rbxNexusConnection_getRepResponseTimeMs );
   LINK_NEXWAYLIB_PROC( rbxUsbor_getNexusConnection );
   LINK_NEXWAYLIB_PROC( rbxUsbor_getName );
   LINK_NEXWAYLIB_PROC( rbxUsbor_getSerialNumber );
   LINK_NEXWAYLIB_PROC( rbxUsbor_getFirmwareVersion );
   LINK_NEXWAYLIB_PROC( rbxUsbor_getConfigName );
   LINK_NEXWAYLIB_PROC( rbxUsbor_getConfigId );
   LINK_NEXWAYLIB_PROC( rbxUsbor_getPropertyHeartbeatInterval );
   LINK_NEXWAYLIB_PROC( rbxUsbor_getSensorHeartbeatInterval );
   LINK_NEXWAYLIB_PROC( rbxUsbor_getPodCount );
   LINK_NEXWAYLIB_PROC( rbxUsbor_getPod );
   LINK_NEXWAYLIB_PROC( rbxUsbor_getRepName );
   LINK_NEXWAYLIB_PROC( rbxUsbor_getRepLocation );
   LINK_NEXWAYLIB_PROC( rbxUsbor_getRepResponseTimeMs );
   LINK_NEXWAYLIB_PROC( rbxPod_getUsbor );
   LINK_NEXWAYLIB_PROC( rbxPod_getName );
   LINK_NEXWAYLIB_PROC( rbxPod_getId );
   LINK_NEXWAYLIB_PROC( rbxPod_getServoCount );
   LINK_NEXWAYLIB_PROC( rbxPod_getDigoutCount );
   LINK_NEXWAYLIB_PROC( rbxPod_getSensorCount );
   LINK_NEXWAYLIB_PROC( rbxPod_getPropertyHeartbeatTick );
   LINK_NEXWAYLIB_PROC( rbxPod_getSensorHeartbeatTick );
   LINK_NEXWAYLIB_PROC( rbxPod_getAction );
   LINK_NEXWAYLIB_PROC( rbxPod_isPaused );
   LINK_NEXWAYLIB_PROC( rbxPod_pause );
   LINK_NEXWAYLIB_PROC( rbxPod_unpause );
   LINK_NEXWAYLIB_PROC( rbxPod_stopScript );
   LINK_NEXWAYLIB_PROC( rbxPod_powerAllServos );
   LINK_NEXWAYLIB_PROC( rbxPod_runQuickScript );
   LINK_NEXWAYLIB_PROC( rbxPod_runQuickScriptEx );
   LINK_NEXWAYLIB_PROC( rbxPod_assignMainScript );
   LINK_NEXWAYLIB_PROC( rbxPod_assignMainScriptFromFile );
   LINK_NEXWAYLIB_PROC( rbxPod_runMainLines );
   LINK_NEXWAYLIB_PROC( rbxPod_runMainMacro );
   LINK_NEXWAYLIB_PROC( rbxPod_runTeachCommand );
   LINK_NEXWAYLIB_PROC( rbxPod_isPodCmdStarted );
   LINK_NEXWAYLIB_PROC( rbxPod_isPodCmdFinished );
   LINK_NEXWAYLIB_PROC( rbxServo_getPort );
   LINK_NEXWAYLIB_PROC( rbxServo_getMod );
   LINK_NEXWAYLIB_PROC( rbxServo_getPropertyValue );
   LINK_NEXWAYLIB_PROC( rbxDigout_getPort );
   LINK_NEXWAYLIB_PROC( rbxDigout_getMod );
   LINK_NEXWAYLIB_PROC( rbxDigout_getValue );
   LINK_NEXWAYLIB_PROC( rbxSensor_getPort );
   LINK_NEXWAYLIB_PROC( rbxSensor_getType );
   LINK_NEXWAYLIB_PROC( rbxSensor_getMod );
   LINK_NEXWAYLIB_PROC( rbxSensor_getValue );

   if ( h_RbxNexwayLibLinkErr )
      return RBX_E_LIB_FUNCTION_NOT_FOUND;

   return RBX_E_SUCCESS;
}

/*****************************************************
 * Dynamic library-loading function (explicit paths) *
 *****************************************************/
RBX_INLINE int rbxInitNexwayLibEx( const char* appName,
                                   const char* libPath,
                                   const char* jarPath )
{
   int err;

   h_rbxDbgMsg(( "rbxInitNexwayLibEx() called with the following parameters:\n" ));
   h_rbxDbgMsg(( "   appname = %s\n", ( appName ? appName : "NULL" ) ));
   h_rbxDbgMsg(( "   libPath = %s\n", ( libPath ? libPath : "NULL" ) ));
   h_rbxDbgMsg(( "   jarPath = %s\n", ( jarPath ? jarPath : "NULL" ) ));

   /* Prevent multiple init calls */
   if ( h_RbxInitLibCalled )
   {
      h_rbxDbgMsg(( "Init should not be called more than once. Returning Error Code: RBX_%d\n",
                   RBX_E_LIB_LOAD_FAILURE ));
      return RBX_E_LIB_LOAD_FAILURE;
   }
   h_RbxInitLibCalled = 1;

   if ( !appName )
      appName = RBX_DEFAULT_APP_NAME;

   /***** Load Nexway Library *****/

   h_rbxDbgMsg(( "Loading Nexway library...\n" ));
   err = RBX_E_FILE_NOT_FOUND;

   if ( libPath )
   {  /* Explicit library path */
      if ( h_rbxFileExists( libPath ) )
         err = h_rbxLoadNexwayLibrary( libPath );
   }
   else
   {  /* Search for library */
      char* path = h_rbxFindRbxFile( RBX_NEXWAYLIB_NAME );
      if ( path )
      {
         err = h_rbxLoadNexwayLibrary( path );
         free( path );
      }
   }

   if ( err != RBX_E_SUCCESS )
   {
      h_rbxDbgMsg(( "Failed to load library. Error Code: RBX_%d\n", err ));
      return err;
   }
   else
   {
      h_rbxDbgMsg(( "Library successfully loaded.\n" ));
   }

   /***** Locate Usbor JAR *****/

   h_rbxDbgMsg(( "Setting JVM classpath...\n" ));
   err = RBX_E_FILE_NOT_FOUND;

   if ( jarPath )
   {  /* Explicit JAR path */
      if ( h_rbxFileExists( jarPath ) )
      {
         h_rbxDbgMsg(( "Trying classpath = %s\n", jarPath ));
         err = rbxLib_setJvmClassPath( jarPath );
      }
   }
   else
   {  /* Search for JAR file */
      char* path = h_rbxFindRbxFile( RBX_USBORJAR_NAME );
      if ( path )
      {
         h_rbxDbgMsg(( "Trying classpath = %s\n", path ));
         err = rbxLib_setJvmClassPath( path );
         free( path );
      }
   }

   if ( err != RBX_E_SUCCESS )
   {
      h_rbxDbgMsg(( "Failed to set JVM classpath. Error Code: RBX_%d\n", err ));
      return err;
   }
   else
   {
      h_rbxDbgMsg(( "JVM classpath successfully set.\n" ));
   }

   /* Additional JVM configuration should be performed here,
      before the JVM is initialized. */

   h_rbxDbgMsg(( "Initializing the JVM...\n" ));
   err = rbxLib_initJvm( appName );
   if ( err != RBX_E_SUCCESS )
   {
      h_rbxDbgMsg(( "Failed to initialize the JVM. Error Code: RBX_%d\n", err ));
      return err;
   }
   else
   {
      h_rbxDbgMsg(( "JVM successfully initialized.\n" ));
   }

   h_rbxDbgMsg(( "rbxInitNexwayLibEx() done.\n" ));
   return err;
}

/****************************************************
 * Dynamic library-loading function (default paths) *
 ****************************************************/
RBX_INLINE int rbxInitNexwayLib( const char* appName )
{
   return rbxInitNexwayLibEx( appName, NULL, NULL );
}

/**************************************
 * Dynamic library-unloading function *
 * - no longer used                   *
 **************************************/
RBX_INLINE int rbxFreeNexwayLib( void )
{
//   h_RbxNexwayLibHandle = NULL;
   return RBX_E_LIB_UNLOAD_FAILURE;
}

#endif /* NEXWAYLIB_INTERNAL_BUILD */


/********************************************
 * Backwards compatibility with RascalDLL.h *
 ********************************************/
#define RBX_E_DLL_LOAD_FAILURE            RBX_E_LIB_LOAD_FAILURE
#define RBX_E_DLL_FUNCTION_NOT_FOUND      RBX_E_LIB_FUNCTION_NOT_FOUND
#define RBX_E_ROBOT_HANDLE_INVALID        RBX_E_POD_HANDLE_INVALID

#ifdef __cplusplus
 }
#endif

#endif /* RBX_NEXWAY_LIB_H */
