/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcc.Robix;

/**
 *
 * @author Anderson
 */
public class MoveRobix {
    
    private String origem, destino;
    private boolean isCapture;

    public MoveRobix(String origem, String destino, boolean isCapture) {
        this.origem = origem;
        this.destino = destino;
        this.isCapture = isCapture;
    }
    
    public MoveRobix(String moveIA){
        String [] aux = moveIA.split("_");
        this.origem = aux[0];
        this.destino = aux[1];
        if (aux[2].equalsIgnoreCase("true")) {
            this.isCapture = true;
        } else {
            this.isCapture = false;
        }
    }

    public String getJogada(){
        return origem+"_"+destino;
    }
    
    public String getOrigem() {
        return origem;
    }

    public String getDestino() {
        return destino;
    }

    public boolean isIsCapture() {
        return isCapture;
    }

    @Override
    public String toString() {
        return "Move:" + origem + "_" + destino + " , captura=" + isCapture;
    }
}
