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

/**
 *
 * @author Anderson
 */
public class Control_ChessIA implements Runnable {

    private Center_Control centerCtrl;
    private BridgeToJChess jchess;

    public Control_ChessIA(Center_Control centerCtrl) {
        this.centerCtrl = centerCtrl;
//        startJChess();
    }

    public Control_ChessIA() {
        //startJChess();
    }

    public BridgeToJChess getJchess() {
        return jchess;
    }

    public Center_Control getCenterCtrl() {
        return centerCtrl;
    }

    public void startJChess() {
        jchess = new BridgeToJChess();
        jchess.run(getCenterCtrl());
    }

    public void stopJChess() {
        jchess.stop();
    }

    @Override
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
        t.start();
        System.out.println("ola");
    }

    /*
     * Operacoes para SwingUI
     */
    /**
     * Metodo que espera a camera obter uma String de Jogada
     *
     */
    public Move coletaMove() {
        System.out.println("\nColeta");
        String moveStr = "";
        Move move;
        while (true) {
            if (!centerCtrl.getTe().strMoveIsEmpty()) {
                moveStr = centerCtrl.getTe().getStrMove();
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

        final MoveGenerator moveGen = jchess.getBoardFromGame();

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
    public void sendMoveStringToRobix(Move move) {
        String from = move.getFrom().getFENString();
        String to = move.getTo().getFENString();
        String isCapture;
        if (move.getCaptured() != null) {
            isCapture = "true";
        } else {
            isCapture = "false";
        }

        System.out.println("jogadaIA = " + from + "_" + to + "_" + isCapture);
        //return "";
    }

}
