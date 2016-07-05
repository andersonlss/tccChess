/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcc.imageprocessing;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import tcc.Controller.Control_ImgProc;

/**
 *
 * @author Gabriel
 */
public class BridgeToDetectMotion  {
    
    private DetectMotion dm;

    public BridgeToDetectMotion() {
        this.dm = new DetectMotion();
    }

     public BridgeToDetectMotion(Control_ImgProc ctrlImg) {
        this.dm = new DetectMotion(ctrlImg);
    }
    
    public DetectMotion getDm() {
        return dm;
    }
//    
//    @Override
//    public void run() {
//        try {
//            System.in.read();
//        } catch (IOException ex) {
//            Logger.getLogger(BridgeToDetectMotion.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }

}
