package tcc.imageprocessing;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamMotionDetector;
import com.github.sarxos.webcam.WebcamResolution;

import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import tcc.Controller.Control_ImgProc;

import tcc.imageprocessing.elementos.ElementoEstruturante;
import tcc.imageprocessing.elementos.Imagem;
import tcc.imageprocessing.operations.OperacaoMorfologica;

/**
 *
 * @author Gabriel Amaral
 */
public class DetectMotion {

    private Webcam webcam = null;
    private WebcamMotionDetector detector;

    private BufferedImage imageInicial;
    private BufferedImage imageFinal;
    
    Control_ImgProc ctrlImg;

    public DetectMotion() {
        webcam = Webcam.getDefault();
        webcam.setViewSize(WebcamResolution.VGA.getSize());
        webcam.open(true);
    }
    
    public DetectMotion(Control_ImgProc ctrlImg) {
        webcam = Webcam.getDefault();
        webcam.setViewSize(WebcamResolution.VGA.getSize());
        webcam.open(true);
        this.ctrlImg = ctrlImg;
    }

    //add para testes
    public void setImgsJogada(ImgJogada imgJogada) {
        this.imageInicial = imgJogada.getImgInicial();
        this.imageFinal = imgJogada.getImgFinal();

        String saida = processar(imageInicial, imageFinal);

        if (saida.matches("[a-h][1-8]_[a-h][1-8]")) {
            System.out.print("SaidaImg_OK>>" + saida);
        } else {
            System.out.println("SaidaImg_ERROR>>" + saida);
        }
    }

    public String getSaida() {
        detector = new WebcamMotionDetector(webcam);
        detector.setInterval(2000); // one check per 500 ms
        detector.setPixelThreshold(20);
        detector.start();
        imageInicial = webcam.getImage();
        System.out.print(">");
        String saida = null;

        boolean motion = false;

        int cont = 0;

        while (true) {
            if (detector.isMotion() || cont == 3) {
                if (!motion) {
                    cont = 0;
                    motion = true;
                }
            } else {
                cont++;
                if (motion) {
                    motion = false;
                    //System.out.println("getImage()");
                    imageFinal = webcam.getImage();
                    saida = processar(imageInicial, imageFinal);
                    System.out.println("saidaAtual=" + saida);
                    if (ctrlImg.validaStrMove(saida)) {
                    //if (saida.matches("[a-h][1-8]_[a-h][1-8]")) {
                        imageInicial = imageFinal;
                        break;
                    }
                }
            }
            try {
                Thread.sleep(200); // must be smaller than interval
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        detector.stop();

        return saida;
    }

    public String processar(BufferedImage imageInicial, BufferedImage imageFinal) {
        //CARREGA ELEMENTO ESTRUTURANTE
        ElementoEstruturante ee = new ElementoEstruturante();
        ee.carregarElementoEstruturante(new File("imageprocessing/EE15.ee"));

        //IMAGEM PROCESSA: EROSÃO
        Imagem imagem = OperacaoMorfologica.erodir(
                OperacaoMorfologica.subtracao(new Imagem(imageInicial), new Imagem(imageFinal)), ee);

        BufferedImage image3 = new BufferedImage(imageInicial.getWidth(), imageInicial.getHeight(), 1);

        //LIMIARIZAÇÃO
        int[][] matriz = imagem.getImagem();
        for (int x = 0; x < imagem.getLine(); x++) {
            for (int y = 0; y < imagem.getColumn(); y++) {
                int bDiff = matriz[x][y];
                int diff = (255 << 24) | (bDiff << 16) | (bDiff << 8) | bDiff;
                image3.setRGB(x, y, diff);
                if (matriz[x][y] <= 30) {
                    matriz[x][y] = 0;
                } else {
                    matriz[x][y] = 255;
                }
            }
        }

        try {
            ImageIO.write(imageInicial, "jpg", new File("ruido/img1.jpg"));
            ImageIO.write(imageFinal, "jpg", new File("ruido/img2.jpg"));
            ImageIO.write(image3, "jpg", new File("ruido/sub.jpg"));
        } catch (IOException ex) {
            Logger.getLogger(DetectMotion.class.getName()).log(Level.SEVERE, null, ex);
        }

        //VERIFICA MUDANÇA
        Set<String> casa = new HashSet<>();
        for (int y = 0; y < imagem.getLine(); y++) {
            for (int x = 0; x < imagem.getColumn(); x++) {
                try {
                    if (matriz[x][y] > 30) {
                        casa.add(posicao(x, y));
                    }
                } catch (Exception e) {
                    e.getStackTrace();
                }
            }
        }

//        if(casa.size() > 2){
//            System.out.println("");
//        }
        //FORMATA SAÍDA
        StringBuilder builder = new StringBuilder();
        for (Iterator<String> iterator = casa.iterator(); iterator.hasNext();) {
            String next = iterator.next();
            builder.append(next);
            if (iterator.hasNext()) {
                builder.append("_");
            }
        }
        //System.out.println(builder.toString());
        return builder.toString();
    }

    public static String posicao(int x, int y) {
        StringBuilder saida = new StringBuilder();
        if (y >= 12 && y < 61) {
            if (x >= 41 && x < 93) {
                saida.append("h1");
            } else if (x >= 100 && x < 147) {
                saida.append("g1");
            } else if (x >= 155 && x < 202) {
                saida.append("f1");
            } else if (x >= 214 && x < 261) {
                saida.append("e1");
            } else if (x >= 269 && x < 315) {
                saida.append("d1");
            } else if (x >= 325 && x < 373) {
                saida.append("c1");
            } else if (x >= 385 && x < 433) {
                saida.append("b1");
            } else if (x >= 443 && x < 493) {
                saida.append("a1");
            }
        } else if (y >= 65 && y < 115) {
            if (x >= 41 && x < 93) {
                saida.append("h2");
            } else if (x >= 100 && x < 147) {
                saida.append("g2");
            } else if (x >= 155 && x < 202) {
                saida.append("f2");
            } else if (x >= 214 && x < 261) {
                saida.append("e2");
            } else if (x >= 269 && x < 315) {
                saida.append("d2");
            } else if (x >= 325 && x < 373) {
                saida.append("c2");
            } else if (x >= 385 && x < 433) {
                saida.append("b2");
            } else if (x >= 443 && x < 493) {
                saida.append("a2");
            }
        } else if (y >= 124 && y < 172) {
            if (x >= 41 && x < 93) {
                saida.append("h3");
            } else if (x >= 100 && x < 147) {
                saida.append("g3");
            } else if (x >= 155 && x < 202) {
                saida.append("f3");
            } else if (x >= 214 && x < 261) {
                saida.append("e3");
            } else if (x >= 269 && x < 315) {
                saida.append("d3");
            } else if (x >= 325 && x < 373) {
                saida.append("c3");
            } else if (x >= 385 && x < 433) {
                saida.append("b3");
            } else if (x >= 443 && x < 484) {
                saida.append("a3");
            }
        } else if (y >= 180 && y < 229) {
            if (x >= 41 && x < 91) {
                saida.append("h4");
            } else if (x >= 100 && x < 147) {
                saida.append("g4");
            } else if (x >= 155 && x < 202) {
                saida.append("f4");
            } else if (x >= 214 && x < 261) {
                saida.append("e4");
            } else if (x >= 269 && x < 315) {
                saida.append("d4");
            } else if (x >= 325 && x < 373) {
                saida.append("c4");
            } else if (x >= 380 && x < 425) {
                saida.append("b4");
            } else if (x >= 433 && x < 483) {
                saida.append("a4");
            }
        } else if (y >= 237 && y < 288) {
            if (x >= 41 && x < 93) {
                saida.append("h5");
            } else if (x >= 100 && x < 147) {
                saida.append("g5");
            } else if (x >= 152 && x < 202) {
                saida.append("f5");
            } else if (x >= 211 && x < 259) {
                saida.append("e5");
            } else if (x >= 267 && x < 315) {
                saida.append("d5");
            } else if (x >= 321 && x < 369) {
                saida.append("c5");
            } else if (x >= 375 && x < 423) {
                saida.append("b5");
            } else if (x >= 433 && x < 483) {
                saida.append("a5");
            }
        } else if (y >= 296 && y < 342) {
            if (x >= 41 && x < 91) {
                saida.append("h6");
            } else if (x >= 100 && x < 147) {
                saida.append("g6");
            } else if (x >= 155 && x < 202) {
                saida.append("f6");
            } else if (x >= 211 && x < 261) {
                saida.append("e6");
            } else if (x >= 267 && x < 312) {
                saida.append("d6");
            } else if (x >= 321 && x < 371) {
                saida.append("c6");
            } else if (x >= 372 && x < 423) {
                saida.append("b6");
            } else if (x >= 430 && x < 485) {
                saida.append("a6");
            }
        } else if (y >= 352 && y < 399) {
            if (x >= 41 && x < 91) {
                saida.append("h7");
            } else if (x >= 99 && x < 145) {
                saida.append("g7");
            } else if (x >= 152 && x < 202) {
                saida.append("f7");
            } else if (x >= 208 && x < 256) {
                saida.append("e7");
            } else if (x >= 264 && x < 312) {
                saida.append("d7");
            } else if (x >= 321 && x < 368) {
                saida.append("c7");
            } else if (x >= 372 && x < 423) {
                saida.append("b7");
            } else if (x >= 433 && x < 480) {
                saida.append("a7");
            }
        } else if (y >= 407 && y < 454) {
            if (x >= 41 && x < 88) {
                saida.append("h8");
            } else if (x >= 96 && x < 145) {
                saida.append("g8");
            } else if (x >= 152 && x < 199) {
                saida.append("f8");
            } else if (x >= 208 && x < 256) {
                saida.append("e8");
            } else if (x >= 264 && x < 312) {
                saida.append("d8");
            } else if (x >= 320 && x < 368) {
                saida.append("c8");
            } else if (x >= 375 && x < 420) {
                saida.append("b8");
            } else if (x >= 433 && x < 480) {
                saida.append("a8");
            }
        }
        return saida.toString();
    }

//    public static void main(String[] args) throws IOException {
//        new DetectMotion();
//        System.in.read();
//    }
}
