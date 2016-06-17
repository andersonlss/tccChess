/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcc.Controller;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Anderson
 */
public class Control_Center {

    private Control_ChessIA ctrlChessIA;
    private Control_Robix ctrlRobix;
    private Control_ImgProc ctrlImgProc;

    public Control_Center() {
        ctrlChessIA = new Control_ChessIA(this);
        ctrlRobix = new Control_Robix(this);
        ctrlImgProc = new Control_ImgProc(this);
    }

    public void starts() {
        try {
            new Thread(ctrlImgProc, "ctrlImgProc").start();
            Thread.sleep(200);

            new Thread(ctrlRobix, "ctrlRobix").start();
            Thread.sleep(200);
            
            new Thread(ctrlChessIA, "ctrlChessIA").start();
        
        } catch (InterruptedException ex) {
            Logger.getLogger(Control_Center.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /*
     * Getters
     */
    public Control_ImgProc getCtrlImgProc() {
        return ctrlImgProc;
    }

    public Control_ChessIA getCtrlChessIA() {
        return ctrlChessIA;
    }

    public Control_Robix getCtrlRobix() {
        return ctrlRobix;
    }

}
