/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcc.Controller;


import tcc.Robix.MoveRobix;
import tcc.Robix.BridgeToRobix;

/**
 *
 * @author Anderson
 */
public class Control_Robix implements Runnable{

    private Control_Center centerCtrl;
    private BridgeToRobix brgRobix;
    
    private boolean robixStopped = false;

    public Control_Robix(Control_Center centerCtrl) {
        this.centerCtrl = centerCtrl;
        this.brgRobix = new BridgeToRobix();
    }
    
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
    }

    
    
    private MoveRobix getMoveFromStringMove(String moveIA) {
        if (moveIA.matches("[a-h][1-8]_[a-h][1-8]_(false|true)")) {
            return new MoveRobix(moveIA);
        } else {
            System.err.println("ERROR: String Move Invalida: "+moveIA);
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
                //    System.out.println(move.toString());
                    break;
                } else {
                    System.out.println("Esperando Jogada Válida");
                }
            }
        }

        return move;
    }
    
    @Override
    public void run() {
        while (true) {            
            MoveRobix move = coletaMove();
            if (move != null) {
                brgRobix.efetuaJogada(move);
                System.out.println("Robix jogou: "+move.toString());
                updateRobixStopped();
                System.out.println("");
            }
        }
    }
}
