/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcc.Controller;

import fr.free.jchecs.swg.ExceptionHandler;
import fr.free.jchecs.swg.SwingUI;
import fr.free.jchecs.swg.UI;

/**
 *
 * @author Anderson
 */
public class Control_ChessIA {
    
    private void startJChess(){
        final ExceptionHandler gestionErreur = new ExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(gestionErreur);

        final UI ihm = new SwingUI();
        gestionErreur.setUI(ihm);
        ihm.start();
    }
    
    public static void main(String[] args) {
        
    }
    
}
