/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcc.imageprocessing;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamResolution;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import tcc.imageprocessing.elementos.Imagem;

/**
 *
 * @author Anderson
 */
public class MapaTabuleiro {

    public static void main(String[] args) {
        Webcam webcam;
        webcam = Webcam.getDefault();
        webcam.setViewSize(WebcamResolution.VGA.getSize());
        webcam.open(true);

        BufferedImage imageInicial = webcam.getImage();
        BufferedImage image3 = new BufferedImage(imageInicial.getWidth(), imageInicial.getHeight(), 1);
        Imagem imagem = new Imagem(imageInicial);
        for (int x = 0; x < imagem.getLine(); x++) {
            for (int y = 0; y < imagem.getColumn(); y++) {
                posicao(x, y, image3, imageInicial);
            }
        }
        try {
            ImageIO.write(image3, "jpg", new File("ruido/mapa.jpg"));
        } catch (IOException ex) {
            Logger.getLogger(MapaTabuleiro.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static String posicao(int x, int y, BufferedImage image, BufferedImage imageInicial) {
        StringBuilder saida = new StringBuilder();
        if (y >= 12 && y < 61) {
            if (x >= 41 && x < 93) {
                image.setRGB(x, y, imageInicial.getRGB(x, y));
            } else if (x >= 100 && x < 147) {
                image.setRGB(x, y, imageInicial.getRGB(x, y));
            } else if (x >= 155 && x < 202) {
                image.setRGB(x, y, imageInicial.getRGB(x, y));
            } else if (x >= 214 && x < 261) {
                image.setRGB(x, y, imageInicial.getRGB(x, y));
            } else if (x >= 269 && x < 315) {
                image.setRGB(x, y, imageInicial.getRGB(x, y));
            } else if (x >= 325 && x < 373) {
                image.setRGB(x, y, imageInicial.getRGB(x, y));
            } else if (x >= 385 && x < 433) {
                image.setRGB(x, y, imageInicial.getRGB(x, y));
            } else if (x >= 443 && x < 493) {
                image.setRGB(x, y, imageInicial.getRGB(x, y));
            } else {
                image.setRGB(x, y, imageInicial.getRGB(x, y));
            }
        } else if (y >= 65 && y < 115) {
            if (x >= 41 && x < 93) {
                image.setRGB(x, y, imageInicial.getRGB(x, y));
            } else if (x >= 100 && x < 147) {
                image.setRGB(x, y, imageInicial.getRGB(x, y));
            } else if (x >= 155 && x < 202) {
                image.setRGB(x, y, imageInicial.getRGB(x, y));
            } else if (x >= 214 && x < 261) {
                image.setRGB(x, y, imageInicial.getRGB(x, y));
            } else if (x >= 269 && x < 315) {
                image.setRGB(x, y, imageInicial.getRGB(x, y));
            } else if (x >= 325 && x < 373) {
                image.setRGB(x, y, imageInicial.getRGB(x, y));
            } else if (x >= 385 && x < 433) {
                image.setRGB(x, y, imageInicial.getRGB(x, y));
            } else if (x >= 443 && x < 493) {
                image.setRGB(x, y, imageInicial.getRGB(x, y));
            } else {
                image.setRGB(x, y, imageInicial.getRGB(x, y));
            }
        } else if (y >= 124 && y < 172) {
            if (x >= 41 && x < 93) {
                image.setRGB(x, y, imageInicial.getRGB(x, y));
            } else if (x >= 100 && x < 147) {
                image.setRGB(x, y, imageInicial.getRGB(x, y));
            } else if (x >= 155 && x < 202) {
                image.setRGB(x, y, imageInicial.getRGB(x, y));
            } else if (x >= 214 && x < 261) {
                image.setRGB(x, y, imageInicial.getRGB(x, y));
            } else if (x >= 269 && x < 315) {
                image.setRGB(x, y, imageInicial.getRGB(x, y));
            } else if (x >= 325 && x < 373) {
                image.setRGB(x, y, Color.BLUE.getRGB());
            } else if (x >= 385 && x < 433) {
                image.setRGB(x, y, imageInicial.getRGB(x, y));
            } else if (x >= 443 && x < 484) {
                image.setRGB(x, y, imageInicial.getRGB(x, y));
            } else {
                image.setRGB(x, y, imageInicial.getRGB(x, y));
            }
        } else if (y >= 180 && y < 229) {
            if (x >= 41 && x < 91) {
                image.setRGB(x, y, imageInicial.getRGB(x, y));
            } else if (x >= 100 && x < 147) {
                image.setRGB(x, y, imageInicial.getRGB(x, y));
            } else if (x >= 155 && x < 202) {
                image.setRGB(x, y, imageInicial.getRGB(x, y));
            } else if (x >= 214 && x < 261) {
                image.setRGB(x, y, imageInicial.getRGB(x, y));
            } else if (x >= 269 && x < 315) {
                image.setRGB(x, y, imageInicial.getRGB(x, y));
            } else if (x >= 325 && x < 373) {
                image.setRGB(x, y, imageInicial.getRGB(x, y));
            } else if (x >= 380 && x < 425) {
                image.setRGB(x, y, imageInicial.getRGB(x, y));
            } else if (x >= 433 && x < 483) {
                image.setRGB(x, y, imageInicial.getRGB(x, y));
            } else {
                image.setRGB(x, y, imageInicial.getRGB(x, y));
            }
        } else if (y >= 237 && y < 288) {
            if (x >= 41 && x < 93) {
                image.setRGB(x, y, imageInicial.getRGB(x, y));
            } else if (x >= 100 && x < 147) {
                image.setRGB(x, y, imageInicial.getRGB(x, y));
            } else if (x >= 152 && x < 202) {
                image.setRGB(x, y, imageInicial.getRGB(x, y));
            } else if (x >= 211 && x < 259) {
                image.setRGB(x, y, imageInicial.getRGB(x, y));
            } else if (x >= 267 && x < 315) {
                image.setRGB(x, y, imageInicial.getRGB(x, y));
            } else if (x >= 321 && x < 369) {
                image.setRGB(x, y, imageInicial.getRGB(x, y));
            } else if (x >= 375 && x < 423) {
                image.setRGB(x, y, imageInicial.getRGB(x, y));
            } else if (x >= 433 && x < 483) {
                image.setRGB(x, y, imageInicial.getRGB(x, y));
            } else {
                image.setRGB(x, y, imageInicial.getRGB(x, y));
            }
        } else if (y >= 296 && y < 342) {
            if (x >= 41 && x < 91) {
                image.setRGB(x, y, imageInicial.getRGB(x, y));
            } else if (x >= 100 && x < 147) {
                image.setRGB(x, y, imageInicial.getRGB(x, y));
            } else if (x >= 155 && x < 202) {
                image.setRGB(x, y, imageInicial.getRGB(x, y));
            } else if (x >= 211 && x < 261) {
                image.setRGB(x, y, imageInicial.getRGB(x, y));
            } else if (x >= 267 && x < 312) {
                image.setRGB(x, y, imageInicial.getRGB(x, y));
            } else if (x >= 321 && x < 371) {
                image.setRGB(x, y, imageInicial.getRGB(x, y));
            } else if (x >= 372 && x < 423) {
                image.setRGB(x, y, imageInicial.getRGB(x, y));
            } else if (x >= 430 && x < 485) {
                image.setRGB(x, y, imageInicial.getRGB(x, y));
            } else {
                image.setRGB(x, y, imageInicial.getRGB(x, y));
            }
        } else if (y >= 352 && y < 399) {
            if (x >= 41 && x < 91) {
                image.setRGB(x, y, imageInicial.getRGB(x, y));
            } else if (x >= 99 && x < 145) {
                image.setRGB(x, y, imageInicial.getRGB(x, y));
            } else if (x >= 152 && x < 202) {
                image.setRGB(x, y, imageInicial.getRGB(x, y));
            } else if (x >= 208 && x < 256) {
                image.setRGB(x, y, imageInicial.getRGB(x, y));
            } else if (x >= 264 && x < 312) {
                image.setRGB(x, y, imageInicial.getRGB(x, y));
            } else if (x >= 321 && x < 368) {
                image.setRGB(x, y, imageInicial.getRGB(x, y));
            } else if (x >= 372 && x < 423) {
                image.setRGB(x, y, imageInicial.getRGB(x, y));
            } else if (x >= 433 && x < 480) {
                image.setRGB(x, y, imageInicial.getRGB(x, y));
            } else {
                image.setRGB(x, y, imageInicial.getRGB(x, y));
            }
        } else if (y >= 407 && y < 454) {
            if (x >= 41 && x < 88) {
                image.setRGB(x, y, imageInicial.getRGB(x, y));
            } else if (x >= 96 && x < 145) {
                image.setRGB(x, y, imageInicial.getRGB(x, y));
            } else if (x >= 152 && x < 199) {
                image.setRGB(x, y, imageInicial.getRGB(x, y));
            } else if (x >= 208 && x < 256) {
                image.setRGB(x, y, imageInicial.getRGB(x, y));
            } else if (x >= 264 && x < 312) {
                image.setRGB(x, y, imageInicial.getRGB(x, y));
            } else if (x >= 320 && x < 368) {
                image.setRGB(x, y, imageInicial.getRGB(x, y));
            } else if (x >= 375 && x < 420) {
                image.setRGB(x, y, imageInicial.getRGB(x, y));
            } else if (x >= 433 && x < 480) {
                image.setRGB(x, y, imageInicial.getRGB(x, y));
            } else {
                image.setRGB(x, y, imageInicial.getRGB(x, y));
            }
        } else {
            image.setRGB(x, y, imageInicial.getRGB(x, y));
        }
        return saida.toString();
    }

}
