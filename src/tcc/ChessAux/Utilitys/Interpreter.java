/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcc.ChessAux.Utilitys;

import tcc.ChessAux.Structure.Board;
import tcc.ChessAux.Structure.Piece;

/**
 *
 * @author Anderson
 */
public class Interpreter {

    private final Board board;
    private Piece p0, p1;
    private String move, player, status;
    private boolean valid;
    private Move jogada;

    public Interpreter(Board board, String move, String player) {
        this.board = board;
        this.move = move;
        this.player = player;
        interpretMove();
    }

    private void validMove() {
        try {
            if (p0.getName().equalsIgnoreCase("vazio")
                    && p1.getName().equalsIgnoreCase("vazio")) {
                throw new Exception("Posicoes vazias!!!");
            }

            if (!p0.getColor().equalsIgnoreCase(player)
                    && !p1.getColor().equalsIgnoreCase(player)) {
                throw new Exception("Nenhuma peca é do Atual Jogador!!!");

            } else if (p0.getColor().equalsIgnoreCase(p1.getColor())) {
                throw new Exception("Cores iguais!!!");
            }

            //status = "Move Ok";
            valid = true;

        } catch (Exception e) {
            status = "ERROR: " + e.getMessage();
            valid = false;
        }
    }

    private void interpretMove() {

        String[] squares = move.split("_");

        p0 = board.getPieceChessBoard(squares[0]);
        p1 = board.getPieceChessBoard(squares[1]);

        //validando as jogadas
        validMove();

        if (isValid()) {
            String saida = "", destino = "";

            //interpretando
            if (p0.getColor().equalsIgnoreCase(player)) {
                saida = p0.getSquare();
            } else {
                destino = p0.getSquare();
            }
            if (p1.getColor().equalsIgnoreCase(player)) {
                saida = p1.getSquare();
            } else {
                destino = p1.getSquare();
            }

            //jogada valida
            makeJogada(saida, destino);
        }
    }
    
    private void makeJogada(String origem, String destino){
        
        boolean isCapture;
        
        Piece pDestino = board.getPieceChessBoard(destino);
        
        if (pDestino.getName().equalsIgnoreCase("vazio")) {
            status = "OK: Movimento Simples";
            isCapture = false;
        } else {
            status = "OK: Movimento Captura";
            isCapture = true;
        }
        
        jogada = new Move(origem, destino, isCapture);
    }

    public String getMov() {
        return jogada.getJogada();
    }

    public Move getJogada() {
        return jogada;
    }
    
    public String getStatus() {
        return status;
    }

    public boolean isValid() {
        return valid;
    }

}
