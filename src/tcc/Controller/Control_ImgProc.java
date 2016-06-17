/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcc.Controller;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import fr.free.jchecs.core.Move;
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
    private Move moveFromImgs = null;

    /**
     * Construtor da classe que recebe o Centro de Controle do Projeto
     *
     * @param centerCtrl Endereco da Variavel Center_Control
     */
    public Control_ImgProc(Control_Center centerCtrl) {
        this.centerCtrl = centerCtrl;
        this.bridgeToDetectMotion = new BridgeToDetectMotion(this);
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
            if ( !centerCtrl.getCtrlChessIA().strMoveIAIsEmpty() ) {
            //if (centerCtrl.getCtrlRobix().isRobixStopped()) {
                return true;
            }
            try {
                Thread.sleep(200);
            } catch (InterruptedException ex) {
                Logger.getLogger(Control_ChessIA.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Metodo que valida a StrMoveFromImg, se estiver correta, obtemos o
     * MoveFromImgs.
     *
     */
    private boolean validaStrMove(String strMoveFromImg) {

        if (strMoveFromImg.matches("[a-h][1-8]_[a-h][1-8]")) {

            Move moveFromImgs = centerCtrl.getCtrlChessIA().getMoveFromStringMove(strMoveFromImg);

            if (moveFromImgs != null) {
                System.out.println("jogada valida");
                setMoveImg(moveFromImgs);
                return true;
            }
        }

        return false;
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
                String strMoveFromImg;

                /**
                 * Se a String recebida estiver válida, iremos setar a variavel
                 * MoveFromImg E liberar a operacao para o Tabuleiro ser
                 * atualizado.
                 */
                /**
                 * O loop do_while tem como função garantir um Move válido
                 * para ser enviado ao tabuleiro.
                 */
                do {
//                    System.out.println("esperando movimento");

                    //strMoveFromImg = stringsDoTeclado();
                    strMoveFromImg = bridgeToDetectMotion.getDm().getSaida();

                    System.out.println("strMoveFromImg = " + strMoveFromImg);

                    if (validaStrMove(strMoveFromImg)) {
                        break;
                    } else {
                        /**
                         * Nesse else, temos que criar um tipo de alerta ao usuário,
                         * lhe informando que precisa jogar novamente.
                         */
                        System.out.println("StrMove incorreta");
                    }

                } while (true);

//                setMoveImg(moveFromImg);
//                if (moveFromImg.matches("[a-h][1-8]_[a-h][1-8]")) {
//                    setStrMoveImg(moveFromImg);
//                } else {
//                    System.out.println("entrada errada");
//                }
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
    public synchronized Move getMoveImg() {
        Move saida = moveFromImgs;
        moveFromImgs = null;

        return saida;
    }

    /**
     * Set para a String de Movimento Obtida pelas Imagens.
     *
     * @param strMove
     */
    private void setMoveImg(Move moveFromImgs) {
        this.moveFromImgs = moveFromImgs;
    }

    /**
     * Verifica se a String de Movimento esta vazia.
     *
     * @return True se estiver ou ao contrário
     */
    public boolean moveImgIsEmpty() {
        if (moveFromImgs == null) {
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
            if (moveImgIsEmpty()) {
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
    
    // Codigos feitos para testes
    private boolean autorizado = false;
    
    public void setAutoriza(){
        autorizado = true;        
    }
    
    public boolean esperaRobix(){
        
        return centerCtrl.getCtrlRobix().isRobixStopped();
        //while (true) {
//            if () {
//                return true;
//            }
//            try {
//                Thread.sleep(200);
//            } catch (InterruptedException ex) {
//                Logger.getLogger(Control_ChessIA.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
    }
    
    public boolean autorizaRobix(){
        if (autorizado) {
            autorizado = false;
            return true;
        } else {
            return false;
        }
    }
}
