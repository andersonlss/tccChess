/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcc.Controller;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Anderson
 */
public class Testes implements Runnable {

    private Center_Control centerCtrl;
    private String strMove = "";
    private int cont = 1;

    public Testes(Center_Control centerCtrl) {
        this.centerCtrl = centerCtrl;
    }

    public Testes() {
    }

    public synchronized String getStrMove() {
        String saida = strMove;
        strMove = "";
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Testes.class.getName()).log(Level.SEVERE, null, ex);
        }
        return saida;
    }

    private void setStrMove(String strMove) {
//        System.out.println("NotNull");
        this.strMove = strMove;
    }
    
    public boolean strMoveIsEmpty(){
        if (strMove.equalsIgnoreCase("")) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void run() {
        Scanner entrada = new Scanner(System.in);
        String linha;

        System.out.print("Digite 'exit' para terminar ou o move XX_XX para o movimento.\n>");
        while (true) {
            if (strMoveIsEmpty()) {
                System.out.print(">");
                linha = entrada.nextLine();

                if (!linha.equalsIgnoreCase("exit")) {
                    setStrMove(linha);
                } else {
                    System.out.println(">> Teste termino");
                    break;
                }
            }
        }
    }

    public static void main(String[] args) {
        new Thread(new Testes()).start();
    }

}
