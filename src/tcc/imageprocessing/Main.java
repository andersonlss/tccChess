/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcc.imageprocessing;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamResolution;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import tcc.imageprocessing.elementos.ElementoEstruturante;
import tcc.imageprocessing.elementos.Imagem;
import tcc.imageprocessing.operations.OperacaoMorfologica;

/**
 *
 * @author gabrielamaral
 */
public class Main {

    public static void main(String[] args) throws IOException {
//        // get default webcam and open it
        Webcam webcam = Webcam.getDefault();
        webcam.setViewSize(WebcamResolution.VGA.getSize());
        webcam.open();

        // get image
        BufferedImage image = webcam.getImage();

        // save image to PNG file
        ImageIO.write(image, "JPG", new File("ruido/test2.jpg"));
        
        BufferedImage image1 = ImageIO.read(new File("ruido/test3.jpg"));
        
        ElementoEstruturante ee = new ElementoEstruturante();
        ee.carregarElementoEstruturante(new File("imageprocessing/EE15.ee"));
        
        Imagem imagem = OperacaoMorfologica.erodir(OperacaoMorfologica.subtracao(new Imagem(image1), 
                new Imagem(ImageIO.read(new File("ruido/test2.jpg")))), ee);
        
        ImageIO.write(new BufferedImage(640, 480, 1), "jpg", new File("ruido/sub.jpg"));
        
        BufferedImage image3 = new BufferedImage(image1.getWidth(), image1.getHeight(), 1);

        //LIMIARIZAÇÃO
        int[][] matriz = imagem.getImagem();
        for (int x = 0; x < imagem.getLine(); x++) {
            for (int y = 0; y < imagem.getColumn(); y++) {
                int bDiff = matriz[x][y];
                int diff = (255 << 24) | (bDiff << 16) | (bDiff << 8) | bDiff;
                image3.setRGB(x, y, diff);
            }
        }

        try {
            ImageIO.write(image3, "jpg", new File("ruido/sub.jpg"));
        } catch (IOException ex) {
            Logger.getLogger(DetectMotion.class.getName()).log(Level.SEVERE, null, ex);
        }
        
//        Scanner tec = new Scanner(System.in);
//        
//        DetectMotion dm = new DetectMotion();
//        
//        while (true) {
//            System.out.print("X");
//            String line = tec.nextLine();
//            
//            if (line.equalsIgnoreCase("ok")) {
//                break;
//            }
//            
//            System.out.println(">>"+dm.getSaida());
//            
//        }
//        
//        for (int i = 0; i < 5; i++) {
//            
//            String jog = dm.getSaida();
//            System.out.println("Jogada: "+jog);
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException ex) {
//                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
        
    }

}
