/* 
   This is a sample program to help C/C++ programmers get started 
   using the Robix Nexway library (rbxNexwayLib).

   Quick start instructions
   ========================
   1. Plug in an Usbor device.
   2. Launch the Usbor Nexus application.
   3. (Optional) Launch the Usbor Nexway application. This will let
      you watch as this example program run scripts.
   4. Compile and run this example program.


   Outline of example1
   ===================
   1. Initialize the Nexway library.
   2. Connect to a Nexus.
   3. Get handle to the first Usbor contained in the Nexus.
   4. Get handle to the Usbor's first Pod.
   5. Repeatedly read absi (servo current) of the Pod's
      first servo.
   6. If absi exceeds a given threshold, cause the Pod to
      react by running a quick script or a script macro.
   7. Repeat 5-6 until CTRL-C is pressed.
*/

#define RBX_MAIN
#include "../../../../include/rbxNexwayLib.h"

#include <stdio.h>
#include <stdlib.h>

/* Program parameters */
#define NEXUS_HOST   "localhost"
#define USBOR_INDEX  0
#define POD_ID       1
#define SERVO_ID     1

#define ABSI_THRESH  250

#define MAIN_SCRIPT_NAME "example_script"  
#define MACRO_NAME   "react"
#define MAIN_SCRIPT  "macro " MACRO_NAME "\n" \
                     "move 1 to 800; move 1 to -800;\n" \
                     "move 1 to 400; move 1 to -400;\n" \
                     "move 1 to 200; move 1 to -200;\n" \
                     "move 1 to 0\n" \
                     "end\n"

#define QUICK_SCRIPT  "jump 1 to -300; wait 5;\n" \
                      "jump 1 to 300;  wait 5;\n" \
                      "jump 1 to -150; wait 5;\n" \
                      "jump 1 to 150;  wait 5;\n" \
                      "jump 1 to 0;\n"

/* 
 * Generic sleep function. System call depends on platform.
 */

/* Windows */
#if defined(_WIN32) 
void doSleep( int milliseconds )
{
   Sleep( milliseconds );
}

/* Mac OSX */
#elif defined(__APPLE__) && defined(__MACH__)
void doSleep( int milliseconds )
{
   struct timespec ts;
   ts.tv_sec = milliseconds / 1000;
   ts.tv_nsec = ( milliseconds % 1000 ) * 1000000;
   nanosleep( &ts, NULL );
}

/* Linux */
#elif defined(__linux__)
# include <time.h>
void doSleep( int milliseconds )
{
   struct timespec ts;
   ts.tv_sec = milliseconds / 1000;
   ts.tv_nsec = ( milliseconds % 1000 ) * 1000000;
   nanosleep( &ts, NULL );
}

#endif

/* 
 * Displays the specified error message and a description of the 
 * specified RBX_E_ error (if available).
 *
 * Parameters:
 *  errMsg - error message to display
 *  rbxErr - RBX_E_ error to display
 */
void displayRobixError ( char *errMsg, int rbxErr )
{
   int siz;
   char *errDescrip = NULL;

   /* Make sure function pointer is valid */
   if ( rbxLib_getErrMsg )
   {
      /* Get the size of the error description */
      if ( rbxLib_getErrMsg ( rbxErr, NULL, 0, &siz ) 
              == RBX_E_BUFFER_TOO_SMALL )
      {
         /* Allocate space for description */
         errDescrip = (char*)malloc( siz );
         if ( errDescrip )
         {
            if ( rbxLib_getErrMsg ( rbxErr, errDescrip, siz, NULL ) 
                    != RBX_E_SUCCESS )
            {
               free( errDescrip );
               errDescrip = NULL;
            }
         }
      }
   }

   printf ( "%s\n Robix Error Code: RBX_%d\n", errMsg, rbxErr );
   if ( errDescrip )
   {
      printf ( " Error Description: %s\n", errDescrip );
      free( errDescrip );
   }
}

/* 
 * If the specified error code is not RBX_E_SUCCESS, this function
 * displays an error message and terminates the program.
 *
 * Parameters:
 *  errMsg - message to display on error
 *  rbxErr - RBX_E_ error to test
 */
void exitOnRbxError( char* errMsg, int rbxErr )
{
   if ( rbxErr != RBX_E_SUCCESS )
   {
      char c;
      displayRobixError ( errMsg, rbxErr );
      printf ( "\nPress <return> to exit.\n" );
      scanf( "%c", &c);
      exit ( EXIT_FAILURE );
   }
}

/*
 * Creates a connection to the Nexus which is running on the 
 * specified host.
 *
 * Parameters:
 *  host - target host; may be an IP address, a host name, or 
 *         'localhost'
 *
 * Returns:
 *  handle to the Nexus
 */
int connectToNexus( char* host )
{
   int rbxErr;
   int nexusConnHandle;

   printf( "Connecting to Nexus: %s\n", host );

   /* Create nexus connection */
   rbxErr = rbxNexway_createNexusConnection( host, &nexusConnHandle );
   exitOnRbxError( "Error creating Nexus connection.", rbxErr );

   /* Connect to nexus */
   rbxErr = rbxNexusConnection_connect( nexusConnHandle );
   exitOnRbxError( "Error connecting to Nexus.", rbxErr );
   return nexusConnHandle;
}

/*
 * Returns a handle to the specified Usbor.
 *
 * Parameters:
 *  nexusHandle - handle to Nexus which contains the Usbor
 *  index       - index of the Usbor in the specified Nexus
 *                (zero-based)
 *
 * Returns:
 *  handle to the Usbor
 */
int getUsbor( int nexusHandle, int index )
{
   int rbxErr;
   int usborHandle;

   printf( "Getting Usbor: %d\n", index );

   /* Get handle to Usbor */
   rbxErr = rbxNexusConnection_getUsborByIndex( nexusHandle, index, &usborHandle );
   exitOnRbxError( "Error getting Usbor.", rbxErr );

   if ( usborHandle == 0 )
   {
      char c;
      printf( "Could not find Usbor.\n" );
      printf ( "\nPress <return> to exit.\n" );
      scanf( "%c", &c);
      exit( EXIT_FAILURE );
   }
   return usborHandle;
}

/*
 * Returns a handle to the specified Pod.
 *
 * Parameters:
 *  usborHandle - handle to Usbor which contains the Pod
 *  podId       - ID of the Pod in the specified Usbor
 *                (one-based)
 *
 * Returns:
 *  handle to the Pod
 */
int getPod( int usborHandle, int podId )
{
   int rbxErr;
   int podHandle;

   printf( "Getting Pod: %d\n", podId );

   /* Get handle to Pod */
   rbxErr = rbxUsbor_getPod( usborHandle, podId, &podHandle );
   exitOnRbxError( "Error getting Pod.", rbxErr );
   return podHandle;
}

/*
 * Waits for the specified Pod command to finish.
 *
 * Parameters:
 *  podHandle - handle to the Pod
 *  seqNum    - sequence number (aka tracking number) of the desired 
 *              Pod command
 *              
 */
void waitForPodCmdFinished( int podHandle, int seqNum )
{
   int rbxErr;
   int finished;

   while ( 1 )
   {
      /* Wait for pod command to finish */
      rbxErr = rbxPod_isPodCmdFinished( podHandle, seqNum, &finished );
      exitOnRbxError( "Error checking for pod command finished.", rbxErr );

      if ( finished )
         break;

      doSleep( 100 );
   }
}

/*
 * Assigns a script to a Pod. A Pod's assigned script is known
 * as its 'main' script (as opposed to 'quick' scripts).
 *
 * Parameters:
 *  podHandle  - handle to the Pod
 *  scriptText - the script text
 *  scriptName - name of the script
 */
void assignMainScript( int podHandle, char* scriptText, char* scriptName )
{
   int rbxErr;
   int seqNum;
   
   printf( "Assigning main script. Name: %s\n", scriptName );

   /* Assign script */
   rbxErr = rbxPod_assignMainScript( podHandle, scriptText, 
                                     scriptName, &seqNum );
   exitOnRbxError( "Error assigning main script.", rbxErr );

   waitForPodCmdFinished( podHandle, seqNum );
}

/*
 * Runs a macro from a Pod's main script.
 *
 * Parameters:
 *  podHandle  - handle to the Pod
 *  macroName  - name of the macro
 *  iter       - number of iterations to run the macro
 */
void runMainMacro( int podHandle, char* macroName, int iter )
{
   int rbxErr;
   int seqNum;

   printf( "Running main script macro. Name: %s  Iterations: %d\n",
           macroName, iter );

   /* Start macro running */
   rbxErr = rbxPod_runMainMacro( podHandle, macroName, iter, &seqNum );
   exitOnRbxError( "Error running main macro.", rbxErr );

   waitForPodCmdFinished( podHandle, seqNum );
}

/*
 * Runs a quick script.
 *
 * Parameters:
 *  podHandle  - handle to the Pod
 *  scriptText - the quick script text
 */
void runQuickScript( int podHandle, char* scriptText )
{
   int rbxErr;
   int seqNum;

   printf( "Running quick script.\n" );

   /* Start quick script running */
   rbxErr = rbxPod_runQuickScript( podHandle, scriptText, &seqNum );
   exitOnRbxError( "Error running quick script.", rbxErr );

   waitForPodCmdFinished( podHandle, seqNum );
}

/*
 * Reads a servo's absi property.
 *
 * Parameters:
 *  podHandle  - handle to the Pod
 *  servoId    - ID of the servo in the Pod (one-based)
 *
 * Returns:
 *  current value of the servo's absi property
 */
int readAbsi( int podHandle, int servoId )
{
   int rbxErr;
   int value;

   /* Read servo's absi value */
   rbxErr = rbxServo_getPropertyValue( podHandle, servoId, 
                                       rbxServoProp_ABSI, 
                                       &value );
   exitOnRbxError( "Error getting reading absi.", rbxErr );

   return value;
}


/*
 * Main function.
 */
int main( int argc, char* argv[] )
{
   int rbxErr;
   int nexusConnHandle;
   int usborHandle;
   int podHandle;
   int absI;
   int mainOrQuick = 1;

   /***** Initialize the Nexway library *****/
   printf( "Initializing Nexway library.\n" );

   /* Applications must initialize the library before any rbx...() 
      functions are called. This function dynamically loads 
      the library and sets up function pointers, making library
      functions available for use. */
   rbxErr = rbxInitNexwayLib( "example1" );
   exitOnRbxError( "Error initializing Nexway lib.", rbxErr );

   /***** Connect and get handles *****/
   nexusConnHandle = connectToNexus( NEXUS_HOST );
   usborHandle = getUsbor( nexusConnHandle, USBOR_INDEX );
   podHandle = getPod( usborHandle, POD_ID );

   /***** Assign script *****/
   assignMainScript( podHandle, MAIN_SCRIPT, MAIN_SCRIPT_NAME );

   printf( "Reading absi (servo current)\n" );
   printf( " Servo ID: %d\n", SERVO_ID );
   printf( " absi threshold: %d\n\n", ABSI_THRESH );
   printf( "Apply torque to servo to cause a reaction.\n" );
   printf( "(CTRL-C to quit)\n\n" );

   /* Read absi repeatedly. If absi value exceeds the threshold,
      perform a reaction. */
   while ( 1 )
   {
      absI = readAbsi( podHandle, SERVO_ID );

      if ( absI > ABSI_THRESH )
      {
         /* Alternate between two types of reaction */
         if ( mainOrQuick )
            runMainMacro( podHandle, MACRO_NAME, 1 );
         else
            runQuickScript( podHandle, QUICK_SCRIPT );
   
         mainOrQuick = !mainOrQuick;
         printf( "Reaction finished.\n" );
      }
      else
      {
         doSleep( 200 );
      }
   }

   return 0;
}

