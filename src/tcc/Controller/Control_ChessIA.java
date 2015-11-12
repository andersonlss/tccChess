/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcc.Controller;

import fr.free.jchecs.core.MailboxBoard;
import fr.free.jchecs.core.Move;
import fr.free.jchecs.core.MoveGenerator;
import fr.free.jchecs.swg.BridgeToJChess;
import fr.free.jchecs.tcc.Interpreter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Anderson
 */
public class Control_ChessIA implements Runnable {

    private Control_Center centerCtrl;
    private BridgeToJChess brgJChess;
    
    private String strMoveIA = "";

    public Control_ChessIA(Control_Center centerCtrl) {
        this.centerCtrl = centerCtrl;
//        startJChess();
    }

    public Control_ChessIA() {
        //startJChess();
    }

    public BridgeToJChess getJchess() {
        return brgJChess;
    }

    public Control_Center getCenterCtrl() {
        return centerCtrl;
    }

    public void startJChess() {
        brgJChess = new BridgeToJChess();
        brgJChess.run(getCenterCtrl());
    }

    public void stopJChess() {
        brgJChess.stop();
    }

    @Override
    public void run() {
        startJChess();
        while (true) {
            if (brgJChess.isClosed()) {
                break;
            }
        }
    }
    
    /*
     * Operacoes para SwingUI
     */
    /**
     * Metodo que espera a camera obter uma String de Jogada
     *
     */
    public Move coletaMove() {
//        System.out.println("\nColeta");
        String moveStr = "";
        Move move;
        while (true) {
            if (!centerCtrl.getCtrlImgProc().strMoveImgIsEmpty()) {
                moveStr = centerCtrl.getCtrlImgProc().getStrMoveImg();
                move = getMoveFromStringMove(moveStr);
                if (move != null) {
                    break;
                } else {
                    System.out.println("Esperando Jogada Válida");
                }
            }
        }

        System.out.println("MOVE: " + moveStr);
        return move;
    }

    /**
     * Interpreta a StringJogada da camera
     *
     * @param move String de movimento da Camera
     */
    private Move getMoveFromStringMove(String move) {

        final MoveGenerator moveGen = brgJChess.getBoardFromGame();

        Interpreter inter = new Interpreter((MailboxBoard) (moveGen), moveGen.isWhiteActive());

        Move tmp = inter.getMoveFromString_Move(move);

        if (tmp != null) {
            return tmp;
        } else {
            System.err.println("ERROR: String Move Invalida");
            return null;
        }
    }

    /*
     * Operacoes para Control_Robix
     */
    public synchronized String getStrMoveIA() {
        String saida = strMoveIA;
        strMoveIA = "";
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Control_ImgProc.class.getName()).log(Level.SEVERE, null, ex);
        }
        return saida;
    }

    private void setStrMoveIA(String strMoveIA) {
        this.strMoveIA = strMoveIA;
    }
    
    public boolean strMoveIAIsEmpty(){
        if (strMoveIA.equalsIgnoreCase("")) {
            return true;
        } else {
            return false;
        }
    }
    
    
    public void sendMoveStringToRobix(Move move) {
        String from = move.getFrom().getFENString();
        String to = move.getTo().getFENString();
        String isCapture;
        if (move.getCaptured() != null) {
            isCapture = "true";
        } else {
            isCapture = "false";
        }

        setStrMoveIA(from + "_" + to + "_" + isCapture);
    }

}
