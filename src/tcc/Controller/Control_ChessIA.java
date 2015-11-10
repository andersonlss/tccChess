/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcc.Controller;

import fr.free.jchecs.swg.StartJChess;


/**
 *
 * @author Anderson
 */
public class Control_ChessIA implements Runnable{
    
    StartJChess jchess;
    
    public void startJChess(){
        jchess = new StartJChess();
        jchess.run();
    }
    
    public void stopJChess(){
        jchess.stop();
    }
    

    public void run() {
        startJChess();
        while (true) {            
            if (jchess.isClosed()) {
                break;
            }
        }
    }
    
    public static void main(String[] args) throws InterruptedException {
        Control_ChessIA ia = new Control_ChessIA();
        
        Thread t = new Thread(ia);
        t.run();
            System.out.println("ola");
    }
}
