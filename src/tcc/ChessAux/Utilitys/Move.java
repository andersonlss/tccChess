/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcc.ChessAux.Utilitys;

/**
 *
 * @author Anderson
 */
public class Move {
    
    private String origem, destino;
    private boolean isCapture;

    public Move(String origem, String destino, boolean isCapture) {
        this.origem = origem;
        this.destino = destino;
        this.isCapture = isCapture;
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
    
}
