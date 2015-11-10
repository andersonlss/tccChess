package tcc.Template;


import com.robix.RbxGhostException;
import com.robix.nexway.Nexway;
import com.robix.nexway.NexusConnection;
import com.robix.nexway.Pod;
import com.robix.nexway.Usbor;
import com.robix.nexway.Servo;
import com.robix.nexway.ServoProperty;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This is a sample program to help Java programmers get started using the Robix
 * Nexway Java API.<p>
 *
 * Quick start instructions <br>
 * ======================== <br>
 * 1. Plug in an Usbor device. <br>
 * 2. Launch the Usbor Nexus application. <br>
 * 3. (Optional) Launch the Usbor Nexway application. This will let you watch as
 * this example program run scripts. <br>
 * 4. Compile and run this example program. <br>
 * <br>
 *
 * Outline of example1 <br>
 * =================== <br>
 * 1. Create a Nexway object. <br>
 * 2. Connect to a Nexus. <br>
 * 3. Get a reference to the first Usbor contained in the Nexus. <br>
 * 4. Get a reference to the Usbor's first Pod. <br>
 * 5. Repeatedly read absi (servo current) of the Pod's first servo. <br>
 * 6. If absi exceeds a given threshold, cause the Pod to react by running a
 * quick script or a script macro. <br>
 * 7. Repeat 5-6 until CTRL-C is pressed. <br>
 * NOTE: If you're running the example from within an IDE, like JBuilder, CTRL-C
 * might not work. If so, just stop the app using the IDE.
 *
 */
public class Example1 {
    // Program Parameters

    Process usbNexus;

    static final String NEXUS_HOST = "localhost";
    static final int USBOR_INDEX = 0;
    static final int POD_ID = 1;
    static final int SERVO_ID = 1;

    static final int ABSI_THRESH = 250;

    static final String MAIN_SCRIPT_NAME = "example_script";
    static final String MACRO_NAME = "react";
    static final String MAIN_SCRIPT
            = "minpos 5 -800; maxpos 5 900;\n"
            + "minpos 3 -1500;\n"
            + "initpos 5 -800; initpos 6 -1400; initpos 7 0;\n"
            + "maxspd all 10;\n"
            + "accdec all 5;";

    static final String QUICK_SCRIPT
            = "move 1 to -1400, 2 to 850, 3 to 1400, 4 to 1400;\n"
            + "move 1 to -300, 2 to 0, 3 to 0, 4 to 0;\n"
            + "move 1 to 1400, 2 to -680, 3 to -1500, 4 to -1400;";

    /**
     * Our one and only Nexway.
     */
    static final Nexway nexway = new Nexway("example1");

    /**
     * Creates a connection to the Nexus which is running on the specified host.
     *
     * @param host target host; may be an IP address, a host name, or
     * 'localhost'
     *
     * @return the connected NexusConnection
     */
    static NexusConnection connectToNexus(String host) {
        System.out.println("Connecting to Nexus: " + host);
        // Create nexus connection
        NexusConnection nexusConn = nexway.createNexusConnection(host);

        try {
            // Connect to nexus
            nexusConn.connect();
        } catch (Exception ex) {
            System.out.println("Error connecting to Nexus:");
            System.out.println(ex.getMessage());
            System.exit(1);
        }
        return nexusConn;
    }

    /**
     * Returns the specified Usbor.
     *
     * @param nexus the Nexus which contains the Usbor
     * @param index index of the Usbor in the specified Nexus (zero-based)
     *
     * @return the specified Usbor
     */
    static Usbor getUsbor(NexusConnection nexus, int index) {
        System.out.println("Getting Usbor: " + index);

        // Get Usbor
        Usbor usbor = nexus.getUsborByIndex(index);

        if (usbor == null) {
            System.out.println("Could not find Usbor.");
            System.exit(1);
        }

        return usbor;
    }

    /**
     * Returns the specified Pod.
     *
     * @param usbor the Usbor which contains the Pod
     * @param podId ID of the Pod in the specified Usbor (one-based)
     *
     * @return the specified Pod
     */
    static Pod getPod(Usbor usbor, int podId) {
        System.out.println("Getting Pod: " + podId);

        int podCount = usbor.getPodCount();
        if (podId <= 0 || podId > podCount) {
            System.out.println("Could not find Pod.");
            System.exit(1);
        }

        // Get Pod
        return usbor.getPod(podId);
    }

    /**
     * Waits for the specified Pod command to finish.
     *
     * @param pod the Pod
     * @param seqNum sequence number (aka tracking number) of the desired Pod
     * command
     *
     * @throws RbxGhostException if the Pod has become disconnected
     */
    static void waitForPodCmdFinished(Pod pod, int seqNum)
            throws RbxGhostException {
        // Wait for pod command to finish
        while (!pod.isPodCmdFinished(seqNum)) {
            if (pod.isGhost()) {
                throw new RbxGhostException("Disconnected from Pod.");
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                // Ignored in this single-threaded example application.
            }
        }
    }

    /**
     * Assigns a script to a Pod. A Pod's assigned script is known as its 'main'
     * script (as opposed to 'quick' scripts).
     *
     * @param pod the Pod
     * @param script the script
     * @param scriptName name of the script
     *
     * @throws RbxGhostException if the Pod has become disconnected
     */
    static void assignMainScript(Pod pod, String script, String scriptName)
            throws RbxGhostException {
        System.out.println("Assigning main script. Name: " + scriptName);

        // Assign script
        int seqNum = pod.assignMainScript(script, scriptName);
        waitForPodCmdFinished(pod, seqNum);
    }

    /**
     * Runs a macro from a Pod's main script.
     *
     * @param pod the Pod
     * @param macroName name of the macro
     * @param iter number of iterations to run the macro
     *
     * @throws RbxGhostException if the Pod has become disconnected
     */
    static void runMainMacro(Pod pod, String macroName, int iter)
            throws RbxGhostException {
        System.out.println("Running main script macro. Name: " + macroName
                + "  Iterations: " + iter);

        // Start macro running
        int seqNum = pod.runMainMacro(macroName, iter);
        waitForPodCmdFinished(pod, seqNum);
    }

    /**
     * Runs a quick script.
     *
     * @param pod the Pod
     * @param script the quick script
     *
     * @throws RbxGhostException if the Pod has become disconnected
     */
    static void runQuickScript(Pod pod, String script)
            throws RbxGhostException {
        System.out.println("Running quick script.");

        // Start quick script running
        int seqNum = pod.runQuickScript(script);
        System.out.println("seqNum=" + seqNum);
        waitForPodCmdFinished(pod, seqNum);
    }

    static void executaCMD() {

        String comando
                = "cmd /c start /min C:/\"Program Files (x86)\"/Robix/\"Robix Robot Systems\"/bin/UsborNexus.exe";

        Process exec;
        try {
            exec = Runtime.getRuntime().exec(comando);
            Thread.sleep(5000);

        } catch (IOException ex) {
            System.out.println("ERRO: " + ex.getMessage());
        } catch (InterruptedException ex) {
            System.out.println("ERRO: " + ex.getMessage());
        }

    }

    /**
     * Main function.
     */
    public static void main(String[] args) {

        executaCMD();

        System.out.println("eai td joia");

        System.out.println("");

        try {
            ///// Connect and get objects /////
            NexusConnection nexusConn = connectToNexus(NEXUS_HOST);
            Usbor usbor = getUsbor(nexusConn, USBOR_INDEX);
            Pod pod = getPod(usbor, POD_ID);
            Servo servo = pod.getServo(SERVO_ID);

            ///// Assign script /////
            assignMainScript(pod, MAIN_SCRIPT, MAIN_SCRIPT_NAME);

            System.out.println("Reading absi (servo current)");
            System.out.println(" Servo ID: " + SERVO_ID);
            System.out.println(" absi threshold: " + ABSI_THRESH);
            System.out.println();
            System.out.println("Apply torque to servo to cause a reaction.");
            System.out.println("(CTRL-C to quit)");

            runQuickScript(pod, MAIN_SCRIPT);
            runQuickScript(pod, QUICK_SCRIPT);
            runMainMacro(pod, QUICK_SCRIPT, 3);

//             Read absi repeatedly. If absi value exceeds the threshold,
//             perform a reaction.
            boolean mainOrQuick = false;
            while (true) {
                int absI = servo.getPropertyValue(ServoProperty.ABSI);
                System.out.println("absI=" + absI);
                if (absI > ABSI_THRESH) {
//                if (absI >= 15) {
                    // Alternate between two types of reaction
                    if (mainOrQuick) {
                        runMainMacro(pod, MACRO_NAME, 1);
                        System.out.println("oi");

                    } else {
                        
                        break;
                    }

                    mainOrQuick = !mainOrQuick;
                    System.out.println("Reaction finished.");
                } else {
                    Thread.sleep(100);
                }
            }
        } catch (Exception ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }
    }
}
