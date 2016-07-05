/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcc.imageprocessing;

import java.awt.image.BufferedImage;
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamResolution;

/**
 *
 * @author Anderson implementada para teste
 */
public class ImgJogada {
    
    private BufferedImage imgInicial, imgFinal;
    private Webcam webcam;

    public ImgJogada() {
        iniciaWeb();
    }

    public ImgJogada(ImgJogada atual) {
        this.imgInicial = atual.getImgInicial();
        this.imgFinal = atual.getImgFinal();
    }
       
    private void iniciaWeb(){
        webcam = Webcam.getDefault();
        webcam.setViewSize(WebcamResolution.VGA.getSize());
        webcam.open(true);
    }

    public BufferedImage getImgInicial() {
        return imgInicial;
    }

    public void setImgInicial() {
        System.out.println("#");
        this.imgInicial = webcam.getImage();
    }

    public BufferedImage getImgFinal() {
        return imgFinal;
    }

    public void setImgFinal() {
        System.out.println("###");
        this.imgFinal = webcam.getImage();
    }
    
    public void atuazilaInicial(){
        imgInicial = imgFinal;
    }
    
}
