/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcc.Controller;

import tcc.ChessAux.Structure.Board;
import tcc.ChessAux.Utilitys.Interpreter;
import tcc.Robix.MoveRobix;
import java.util.Scanner;

/**
 *
 * @author Anderson
 */
public class Control_BoardAux {

    private Board board;
    private String atualJogador;
    private MoveRobix atualJogada;

    public Control_BoardAux() {
        this.atualJogador = "w";
        this.board = new Board();
    }

    public Control_BoardAux(Board board) {
        this.atualJogador = "w";
        this.board = board;
    }

    public Control_BoardAux(Board board, String atualJogador) {
        this.board = board;
        this.atualJogador = atualJogador;
    }

    private void proximoJogador() {
        if (atualJogador.equalsIgnoreCase("b")) {
            atualJogador = "w";
        } else {
            atualJogador = "b";
        }
    }

    public boolean recebeMov(String mov) {

        boolean movIsValid = false;

        if (mov.matches("[a-h][1-8]_[a-h][1-8]")) {

            Interpreter interpreter = new Interpreter(board, mov, atualJogador);

            if (interpreter.isValid()) {
                atualJogada = interpreter.getJogada();

                //faz jogada automatica
                board.movePieceChessBoard(atualJogada.getJogada());
                //System.out.println("\t" + interpreter.getStatus() + "-> " + atualJogada.getJogada());

                proximoJogador();
                movIsValid = !movIsValid;
            }
            System.out.println("\tMove=> " + mov + "\tStatus: " + interpreter.getStatus());

        } else {
            System.out.println("Entrada Errada: XX_XX");
        }

        return movIsValid;
    }

    public MoveRobix getAtualJogada() {
        return atualJogada;
    }
    
    public void destroy(){
        board.destroy();
    }

//    public static void main(String[] args) {
//
//        Board b = new Board();
//
//        int atualJogador = 0;
//        String[] jogador = {"b", "w"};
//        Scanner ler = new Scanner(System.in);
//        String comando = "";
//
//        System.out.println("Digite 'sair' para terminar o loop.\n");
//
//        while (!comando.equalsIgnoreCase("sair")) {
//            System.out.print(jogador[atualJogador] + "_>");
//
//            comando = ler.nextLine();
//
//            if (comando.matches("[a-h][1-8]_[a-h][1-8]")) {
//
//                Interpreter interpreter = new Interpreter(b, comando, jogador[atualJogador]);
//
//                if (interpreter.isValid()) {
//                    String jogada = interpreter.getMov();
//
//                    b.movePieceChessBoard(jogada);
//                    System.out.println("\t" + interpreter.getStatus() + "-> " + jogada);
//
//                    if (atualJogador == 0) {
//                        atualJogador = 1;
//                    } else {
//                        atualJogador = 0;
//                    }
//
//                } else {
//                    System.out.println("\tStatus: " + interpreter.getStatus() + " move-> " + interpreter.getMov());
//                    System.out.println("\tTente novamente.");
//                }
//            } else {
//                System.out.println("entrada errada: XX_XX");
//            }
//        }
//        System.out.println("EXIT");
//    }

}
