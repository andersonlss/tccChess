/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcc.imageprocessing;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Gabriel
 */
public class BridgeToDetectMotion implements Runnable {
    
    private DetectMotion dm;

    public BridgeToDetectMotion() {
        this.dm = new DetectMotion();
    }

    public DetectMotion getDm() {
        return dm;
    }
    
    
    
    @Override
    public void run() {
        try {
            System.in.read();
        } catch (IOException ex) {
            Logger.getLogger(BridgeToDetectMotion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
