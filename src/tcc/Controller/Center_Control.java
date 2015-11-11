/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcc.Controller;

/**
 *
 * @author Anderson
 */
public class Center_Control {
    
    private Control_ChessIA controlChessIA;
    private Testes te;

    public Center_Control() {
        controlChessIA = new Control_ChessIA(this);
        te = new Testes(this);
        starts();
    }
    
    private void starts(){
        new Thread(te).start();
        new Thread(controlChessIA).start();
    }
    
//    public boolean sendStringMove(String move){
//        return controlChessIA.getJchess().sendStringMove(move);
//    }

    /*
     * Getters
     */
    
    public Testes getTe() {
        return te;
    }

    public Control_ChessIA getControlChessIA() {
        return controlChessIA;
    }
    
    public static void main(String[] args) {
        Center_Control cc = new Center_Control();
    }
    
}
