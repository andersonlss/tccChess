/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcc.Main;

import tcc.Controller.Control_BoardAux;
import tcc.ChessAux.Utilitys.CustomBoard;
import tcc.Controller.Control_Robix;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Anderson
 */
public class ExecuteAux {

    static int qtdPieces = 15;

    Control_BoardAux controlBoard;
    Control_Robix controlRobix;
    CustomBoard customBoard = new CustomBoard();

    public ExecuteAux() {
        inicialize();
    }

    private void inicialize() {
        controlBoard = new Control_BoardAux(customBoard.randomBoard(qtdPieces));
        controlRobix = new Control_Robix();
    }

    private void destroy() {
        controlBoard.destroy();
        controlRobix.destroy();
    }

    public static void main(String[] args) {

        Scanner ler = new Scanner(System.in);
        ExecuteAux e = new ExecuteAux();

        System.out.println("Digite 'ok' depois de posicionar as pecas no tabuleiro.\n"
                + "Ou 'exit' para fechar programa.");
        while (true) {
            System.out.print(">");

            String resp = ler.nextLine();
            if (resp.equalsIgnoreCase("ok")) {

                int qtdMovimentos = 15;
                int cont = 0;

                while (cont < qtdMovimentos) {

                    String jogada = e.customBoard.randomJogada();

                    if (e.controlBoard.recebeMov(jogada)) {
                        e.controlRobix.efetuaJogada(e.controlBoard.getAtualJogada());
                        cont++;
                        System.out.println(">nº" + cont);
                    }

                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(ExecuteAux.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                System.out.println("\nDigite 'exit' para fechar programa, ou 'ok' para continuar.");

            } else {
                if (resp.equalsIgnoreCase("exit")) {
                    e.destroy();
                    break;
                }
            }
        }
    }

}
