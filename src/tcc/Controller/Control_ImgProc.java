/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcc.Controller;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import tcc.imageprocessing.BridgeToDetectMotion;

/**
 *
 * @author Anderson
 */
public class Control_ImgProc implements Runnable {

    // Classe que Mãe dos Controllers
    private Control_Center centerCtrl;
    /**
     * Neste local vc irá colocar a classe que obtem as strings de Move. Se vc
     * tiver que usar mais de uma, crie como eu fiz. Criei uma classe chama
     * BridgeToRobix, que seria ponte entre a classe Robix e a controller, onde
     * eu só uso um metodo para retornar o que desejo.
     *
     * No seu caso seria um metodo que tira as fotos e retorna somente uma
     * strMoveFromImgs
     *
     */
    
    private final BridgeToDetectMotion bridgeToDetectMotion;

    /**
     * A variavel que pode possui as strings de movimento, obtidas das imgs
     */
    private String strMoveFromImgs = "";

    /**
     * Construtor da classe que recebe o Centro de Controle do Projeto
     *
     * @param centerCtrl Endereco da Variavel Center_Control
     */
    public Control_ImgProc(Control_Center centerCtrl) {
        this.centerCtrl = centerCtrl;
        this.bridgeToDetectMotion = new BridgeToDetectMotion();
        /**
         * Se quiser pode iniciar sua variavel que obtem as imgs aqui
         */
    }

    /**
     * Loop que espera a jogada do Robix para poder tirar novas fotos. Caso vc
     * ja faça isso, ignore. Pq coloquei essa condição para ficar mais pratica
     * as conversas dos controllers
     *
     */
    public boolean takePictures() {
        while (true) {
            if (centerCtrl.getCtrlRobix().isRobixStopped()) {
                return true;
            }
        }
    }

    /**
     * Metodo Run da Thread que sempre ficará executando em loop.
     */
    @Override
    public void run() {
        while (true) {
            boolean takePictures = takePictures();
            if (takePictures) {
                /**
                 * Nesse local que vc irá receber a string das operações das
                 * imgs.
                 *
                 * Estou utilizando o metodo stringsDoTeclado, como forma de
                 * testes.
                 */
                
                //bridgeToDetectMotion.getDm().getSaida();
                
                //String moveFromImg = stringsDoTeclado();
                String moveFromImg = bridgeToDetectMotion.getDm().getSaida();
                System.out.println(moveFromImg);
                /**
                 * Se a String recebida estiver válida, iremos setar a variavel
                 * StrMoveImg E liberar a operacao para o Tabuleiro ser
                 * atualizado.
                 */
                if (moveFromImg.matches("[a-h][1-8]_[a-h][1-8]")) {
                    setStrMoveImg(moveFromImg);
                }
            }
        }
    }

    /*
     * Getters e Setters
     */
    /**
     * Retorna a String de Movimento Obtida pelas Imagens.
     *
     * @return String de Movimento XX_XX
     */
    public synchronized String getStrMoveImg() {
        String saida = strMoveFromImgs;
        strMoveFromImgs = "";
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException ex) {
//            Logger.getLogger(Control_ImgProc.class.getName()).log(Level.SEVERE, null, ex);
//        }
        return saida;
    }

    /**
     * Set para a String de Movimento Obtida pelas Imagens.
     *
     * @param strMove
     */
    private void setStrMoveImg(String strMove) {
        this.strMoveFromImgs = strMove;
    }

    /**
     * Verifica se a String de Movimento esta vazia.
     *
     * @return True se estiver ou ao contrário
     */
    public boolean strMoveImgIsEmpty() {
        if (strMoveFromImgs.equalsIgnoreCase("")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Método usado para obter as Strings pelo teclado.
     */
    private String stringsDoTeclado() {
        Scanner entrada = new Scanner(System.in);
        String linha;

        try {
            Thread.sleep(4000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Control_ImgProc.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.print("Digite 'exit' para terminar ou o move XX_XX para o movimento.\n>");
        while (true) {
            if (strMoveImgIsEmpty()) {
                System.out.print(">");
                linha = entrada.nextLine();

                if (!linha.equalsIgnoreCase("exit")
                        && linha.matches("[a-h][1-8]_[a-h][1-8]")) {
                    return linha;
                } else {
                    System.out.println(">> Teste termino");
                    break;
                }
            }
        }
        return "saida";
    }
}
