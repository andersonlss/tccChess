package tcc.imageprocessing.elementos;

import java.awt.image.BufferedImage;

/**
 *
 * @author Gabriel Amaral
 */
public class Imagem {

    private int coluna;
    private int linha;
    private int maximumGray;
    private int[][] imagem;
    private int type;

    public Imagem() {
    }

    public Imagem(BufferedImage bufferedImage) {
        this.type = bufferedImage.getType();
        imagem = new int[bufferedImage.getWidth()][bufferedImage.getHeight()];
        for (int y = 0; y < bufferedImage.getHeight(); y++) {
            for (int x = 0; x < bufferedImage.getWidth(); x++) {
                imagem[x][y] = bufferedImage.getRGB(x, y) & 0xFF;
            }
        }
        this.linha = imagem.length;
        this.coluna = imagem[0].length;
        this.maximumGray = maiorValorNaMatriz(imagem);
    }

    public Imagem(int[][] imagem) {
        this.imagem = imagem;
        this.linha = imagem.length;
        this.coluna = imagem[0].length;
        this.maximumGray = maiorValorNaMatriz(imagem);
    }

    public int getType() {
        return type;
    }

    public int getColumn() {
        return coluna;
    }

    public int getLine() {
        return linha;
    }

    public int getMaximumGray() {
        return maximumGray;
    }

    public int[][] getImagem() {
        return imagem;
    }

    public void setImagem(int[][] imagem) {
        this.imagem = imagem;
    }

    private int maiorValorNaMatriz(int[][] matriz) {
        int maior = matriz[0][0];
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[0].length; j++) {
                if (matriz[i][j] > maior) {
                    maior = matriz[i][j];
                }
            }
        }
        return maior;
    }
    
    public void mostrarMatriz() {
        for (int i = 0; i < imagem.length; i++) {
            for (int j = 0; j < imagem[i].length; j++) {
                System.out.printf("%3d ", imagem[i][j]);
            }
            System.out.println("");
        }
    }

}
