/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcc.Robix;

import com.robix.RbxGhostException;
import com.robix.nexway.Pod;
import java.util.logging.Level;
import java.util.logging.Logger;
import tcc.Controller.Control_Robix;

/**
 *
 * @author Anderson
 */
public class BridgeToRobix {
    
    private Tab_Mov tab_mov;
    private Connect connect;

    public BridgeToRobix() {
        tab_mov = new Tab_Mov();
//        connect = new Connect();
    }
    
    private void waitForPodCmdFinished(Pod pod, int seqNum) {
        // Wait for pod command to finish
        while (!pod.isPodCmdFinished(seqNum)) {
            if (pod.isGhost()) {
                try {
                    throw new RbxGhostException("Disconnected from Pod.");
                } catch (RbxGhostException ex) {
                    Logger.getLogger(Control_Robix.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            try {
                Thread.sleep(200);
            } catch (InterruptedException ex) {
                // Ignored in this single-threaded example application.
            }
        }
    }

    private void runQuickScript(Pod pod, MoveRobix jogada) {

        String script = tab_mov.getScript(jogada);

        if (script == null) {
            System.out.println(">null");
            return;
        }

        System.out.println("\t\t>>> Running quick script for move: "+jogada.getJogada());
        //System.out.println("Script= " + jogada.getJogada() + ":=>\n" + script);

        // Start quick script running
        int seqNum;
        try {
            
            seqNum = pod.runQuickScript(script);
            waitForPodCmdFinished(pod, seqNum);
            
        } catch (RbxGhostException ex) {
            Logger.getLogger(Control_Robix.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    public void efetuaJogada(MoveRobix jogada){
        runQuickScript(connect.getPod(), jogada);
    }
    
    public void destroy(){
        connect.destroy();
    }
    
}
