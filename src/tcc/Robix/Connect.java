package tcc.Robix;

import com.robix.nexway.NexusConnection;
import com.robix.nexway.Nexway;
import com.robix.nexway.Pod;
import com.robix.nexway.Servo;
import com.robix.nexway.Usbor;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Anderson
 */
public class Connect {

    static final String NEXUS_HOST = "localhost";
    static final int USBOR_INDEX = 0;
    static final int POD_ID = 1;
//    static final int SERVO_ID = 1;

    static final int ABSI_THRESH = 250;

    private Nexway nexway = new Nexway("robix1");
    private NexusConnection nexusConn;
    private Usbor usbor;
    private Pod pod;

    public Connect() {
        connect();
    }

    private void executaCMD() {

        String comando
                = "cmd /c start C:/\"Program Files (x86)\"/Robix/\"Robix Robot Systems\"/bin/UsborNexus.exe";

        try {
            //usbNexus = Runtime.getRuntime().exec(comando);
            Runtime.getRuntime().exec(comando);
            Thread.sleep(5000);

        } catch (IOException ex) {
            System.err.println("ERROR1: " + ex.getMessage());
        } catch (InterruptedException ex) {
            System.err.println("ERROR2: " + ex.getMessage());
        }

    }

    private NexusConnection connectToNexus(String host) {
        System.out.println("Connecting to Nexus: " + host);
        // Create nexus connection
        NexusConnection nexusConn = nexway.createNexusConnection(host);

        try {
            // Connect to nexus
            nexusConn.connect();
        } catch (Exception ex) {
            System.err.println("Error connecting to Nexus: " + ex.getMessage());
            System.exit(1);
        }
        return nexusConn;
    }

    private Usbor getUsbor(NexusConnection nexus, int index) {
        System.out.println("Getting Usbor: " + index);

        // Get Usbor
        Usbor usbor = nexus.getUsborByIndex(index);

        if (usbor == null) {
            System.err.println("Could not find Usbor.");
            System.exit(1);
        }

        return usbor;
    }

    private Pod getPodFromUsbor(Usbor usbor, int podId) {
        System.out.println("Getting Pod: " + podId);

        int podCount = usbor.getPodCount();
        if (podId <= 0 || podId > podCount) {
            System.err.println("Could not find Pod.");
            System.exit(1);
        }

        // Get Pod
        return usbor.getPod(podId);
    }

    private void connect() {
        ///// Connect and get objects /////
        executaCMD();
        //conecta
        nexusConn = connectToNexus(NEXUS_HOST);
        usbor = getUsbor(nexusConn, USBOR_INDEX);
        pod = getPodFromUsbor(usbor, POD_ID);
    }

    private void destroyProcess() {

        try {
            Runtime.getRuntime().exec("tskill usbornexus");
        } catch (IOException ex) {
            Logger.getLogger(Connect.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void disconnectToNexus() {
        System.out.println("\nDisconnecting to Nexus: " + NEXUS_HOST);
        nexusConn.disconnect();
        nexway.removeNexusConnection(nexusConn); 
    }

    public Pod getPod() {
        return pod;
    }

    public void destroy() {
        disconnectToNexus();
        destroyProcess();
        System.exit(0);
    }

//    public static void main(String[] args) throws InterruptedException {
//        Connect con = new Connect();
//        
////        con.connect();
//        
//        System.out.println("saida");
//        Thread.sleep(3000);
//        
//        con.disconnect();
//        
//        
//    }
}
