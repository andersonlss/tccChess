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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author gabrielamaral
 */
public class Main {

    public static void main(String[] args) throws IOException {
//        // get default webcam and open it
//        Webcam webcam = Webcam.getDefault();
//        webcam.setViewSize(WebcamResolution.VGA.getSize());
//        webcam.open();
//
//        // get image
//        BufferedImage image = webcam.getImage();
//
//        // save image to PNG file
//        ImageIO.write(image, "JPG", new File("ruido/test.jpg"));
        
        DetectMotion dm = new DetectMotion();
        
        for (int i = 0; i < 5; i++) {
            
            String jog = dm.getSaida();
            System.out.println("Jogada: "+jog);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }

}
