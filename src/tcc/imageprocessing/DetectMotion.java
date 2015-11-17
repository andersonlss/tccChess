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

import tcc.imageprocessing.elementos.ElementoEstruturante;
import tcc.imageprocessing.elementos.Imagem;
import tcc.imageprocessing.operations.OperacaoMorfologica;

/**
 *
 * @author Gabriel Amaral
 */
public class DetectMotion {

    private final Webcam webcam;
    private final WebcamMotionDetector detector;

    private BufferedImage imageInicial;
    private BufferedImage imageFinal;

    public DetectMotion() {
        webcam = Webcam.getDefault();
        webcam.setViewSize(WebcamResolution.VGA.getSize());
        webcam.open(true);

        detector = new WebcamMotionDetector(webcam);
        detector.setInterval(500); // one check per 500 ms
        detector.setPixelThreshold(20);

        imageInicial = webcam.getImage();

//        detector.start();
//
//        imageInicial = webcam.getImage();
//
//        Thread t = new Thread("motion-detector") {
//
//            @Override
//            public void run() {
//
//                boolean motion = false;
//
//                while (true) {
//                    if (detector.isMotion()) {
//                        if (!motion) {
//                            motion = true;
//                        }
//                    } else {
//                        if (motion) {
//                            motion = false;
//                            imageFinal = webcam.getImage();
//                            processar(imageInicial, imageFinal);
//                            imageInicial = imageFinal;
//                        }
//                    }
//                    try {
//                        Thread.sleep(50); // must be smaller than interval
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        };
//
//        t.setDaemon(true);
//        t.start();
    }

    public String getSaida() {
        detector.start();
        
        String saida = null;

        boolean motion = false;

        while (true) {
            if (detector.isMotion()) {
                if (!motion) {
                    motion = true;
                }
            } else {
                if (motion) {
                    motion = false;
                    imageFinal = webcam.getImage();
                    saida = processar(imageInicial, imageFinal);
                    imageInicial = imageFinal;
                    break;
                }
            }
            try {
                Thread.sleep(50); // must be smaller than interval
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

        //LIMIARIZAÇÃO
        int[][] matriz = imagem.getImagem();
        for (int x = 0; x < imagem.getLine(); x++) {
            for (int y = 0; y < imagem.getColumn(); y++) {
                if (matriz[x][y] <= 30) {
                    matriz[x][y] = 0;
                } else {
                    matriz[x][y] = 255;
                }
            }
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
        if (y >= 6 && y < 62) {
            if (x >= 45 && x < 101) {
                saida.append("h1");
            } else if (x >= 101 && x < 157) {
                saida.append("g1");
            } else if (x >= 157 && x < 213) {
                saida.append("f1");
            } else if (x >= 213 && x < 269) {
                saida.append("e1");
            } else if (x >= 269 && x < 325) {
                saida.append("d1");
            } else if (x >= 325 && x < 385) {
                saida.append("c1");
            } else if (x >= 385 && x < 443) {
                saida.append("b1");
            } else if (x >= 443 && x < 503) {
                saida.append("a1");
            }
        } else if (y >= 62 && y < 118) {
            if (x >= 45 && x < 101) {
                saida.append("h2");
            } else if (x >= 101 && x < 157) {
                saida.append("g2");
            } else if (x >= 157 && x < 213) {
                saida.append("f2");
            } else if (x >= 213 && x < 269) {
                saida.append("e2");
            } else if (x >= 269 && x < 325) {
                saida.append("d2");
            } else if (x >= 325 && x < 385) {
                saida.append("c2");
            } else if (x >= 385 && x < 443) {
                saida.append("b2");
            } else if (x >= 443 && x < 500) {
                saida.append("a2");
            }
        } else if (y >= 118 && y < 176) {
            if (x >= 45 && x < 101) {
                saida.append("h3");
            } else if (x >= 101 && x < 157) {
                saida.append("g3");
            } else if (x >= 157 && x < 213) {
                saida.append("f3");
            } else if (x >= 213 && x < 269) {
                saida.append("e3");
            } else if (x >= 269 && x < 325) {
                saida.append("d3");
            } else if (x >= 325 && x < 385) {
                saida.append("c3");
            } else if (x >= 385 && x < 443) {
                saida.append("b3");
            } else if (x >= 443 && x < 500) {
                saida.append("a3");
            }
        } else if (y >= 176 && y < 234) {
            if (x >= 45 && x < 101) {
                saida.append("h4");
            } else if (x >= 101 && x < 157) {
                saida.append("g4");
            } else if (x >= 157 && x < 213) {
                saida.append("f4");
            } else if (x >= 213 && x < 269) {
                saida.append("e4");
            } else if (x >= 269 && x < 325) {
                saida.append("d4");
            } else if (x >= 325 && x < 385) {
                saida.append("c4");
            } else if (x >= 385 && x < 443) {
                saida.append("b4");
            } else if (x >= 443 && x < 498) {
                saida.append("a4");
            }
        } else if (y >= 234 && y < 292) {
            if (x >= 45 && x < 101) {
                saida.append("h5");
            } else if (x >= 101 && x < 157) {
                saida.append("g5");
            } else if (x >= 157 && x < 213) {
                saida.append("f5");
            } else if (x >= 213 && x < 269) {
                saida.append("e5");
            } else if (x >= 269 && x < 325) {
                saida.append("d5");
            } else if (x >= 325 && x < 385) {
                saida.append("c5");
            } else if (x >= 385 && x < 443) {
                saida.append("b5");
            } else if (x >= 443 && x < 497) {
                saida.append("a5");
            }
        } else if (y >= 292 && y < 348) {
            if (x >= 45 && x < 101) {
                saida.append("h6");
            } else if (x >= 101 && x < 157) {
                saida.append("g6");
            } else if (x >= 157 && x < 213) {
                saida.append("f6");
            } else if (x >= 213 && x < 269) {
                saida.append("e6");
            } else if (x >= 269 && x < 325) {
                saida.append("d6");
            } else if (x >= 325 && x < 385) {
                saida.append("c6");
            } else if (x >= 385 && x < 443) {
                saida.append("b6");
            } else if (x >= 443 && x < 496) {
                saida.append("a6");
            }
        } else if (y >= 348 && y < 406) {
            if (x >= 45 && x < 101) {
                saida.append("h7");
            } else if (x >= 101 && x < 157) {
                saida.append("g7");
            } else if (x >= 157 && x < 213) {
                saida.append("f7");
            } else if (x >= 213 && x < 269) {
                saida.append("e7");
            } else if (x >= 269 && x < 325) {
                saida.append("d7");
            } else if (x >= 325 && x < 385) {
                saida.append("c7");
            } else if (x >= 385 && x < 443) {
                saida.append("b7");
            } else if (x >= 443 && x < 497) {
                saida.append("a7");
            }
        } else if (y >= 406 && y < 462) {
            if (x >= 45 && x < 101) {
                saida.append("h8");
            } else if (x >= 101 && x < 157) {
                saida.append("g8");
            } else if (x >= 157 && x < 213) {
                saida.append("f8");
            } else if (x >= 213 && x < 269) {
                saida.append("e8");
            } else if (x >= 269 && x < 325) {
                saida.append("d8");
            } else if (x >= 325 && x < 385) {
                saida.append("c8");
            } else if (x >= 385 && x < 443) {
                saida.append("b8");
            } else if (x >= 443 && x < 496) {
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
