/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcc.Controller;

import java.util.logging.Level;
import java.util.logging.Logger;
import tcc.Robix.MoveRobix;
import tcc.Robix.BridgeToRobix;
import tcc.imageprocessing.ImgJogada;

/**
 *
 * @author Anderson
 */
public class Control_Robix implements Runnable {

    private Control_Center centerCtrl;
    private BridgeToRobix brgRobix;

    private boolean robixStopped = true;
    
//    ImgJogada imgJogada = new ImgJogada();

    public Control_Robix(Control_Center centerCtrl) {
        this.centerCtrl = centerCtrl;
        this.brgRobix = new BridgeToRobix();
//        imgJogada.setImgInicial();
    }

    // para testes
    
//    boolean procImg = false;
//
//    public ImgJogada isProcImg() {
//        if (procImg) {
//            procImg = false;
//
//            ImgJogada resp = new ImgJogada(imgJogada);
//            imgJogada.atuazilaInicial();
//
//            return resp;
//        } else {
//            return null;
//        }
//    }

    public boolean isRobixStopped() {
        boolean resp;
//        updateRobixStopped();
//        return resp;
        if (robixStopped) {
            updateRobixStopped();
            resp = true;
        } else {
            resp = false;
        }
        return resp;
    }

    private void updateRobixStopped() {
        robixStopped = !robixStopped;
        System.out.println("robixStopped="+robixStopped);
    }

    private MoveRobix getMoveFromStringMove(String moveIA) {
        if (moveIA.matches("[a-h][1-8]_[a-h][1-8]_(false|true)")) {
            return new MoveRobix(moveIA);
        } else {
            System.err.println("ERROR: String Move Invalida: " + moveIA);
            return null;
        }
    }

    public MoveRobix coletaMove() {
        MoveRobix move;
        while (true) {
            if (!centerCtrl.getCtrlChessIA().strMoveIAIsEmpty()) {
                String moveStr = centerCtrl.getCtrlChessIA().getStrMoveIA();
                move = getMoveFromStringMove(moveStr);
                if (move != null) {
                    System.out.println(move.toString());
                    break;
                } else {
                    System.out.println("Esperando Jogada Válida");
                }
            }
            try {
                Thread.sleep(200);
            } catch (InterruptedException ex) {
                Logger.getLogger(Control_ChessIA.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return move;
    }
    
    public void destroy(){
        brgRobix.destroy();
    }

    @Override
    public void run() {
        while (true) {
            MoveRobix move = coletaMove();
            if (move != null) {

                brgRobix.efetuaJogada(move);
//                imgJogada.setImgFinal();
//                procImg = true;
                System.out.println("\tRobix jogou: " + move.toString());
                updateRobixStopped();
            }
        }
    }
}
