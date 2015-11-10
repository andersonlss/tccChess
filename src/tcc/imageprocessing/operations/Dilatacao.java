package tcc.imageprocessing.operations;

import tcc.imageprocessing.elementos.*;

/**
 *
 * @author Gabriel Amaral
 */
public class Dilatacao extends Morfologia {

    public Dilatacao(Imagem imagem, ElementoEstruturante ee) {
        super(imagem, ee);
    }

    /**
     * Operações para dilatar a imagem PGM
     * @return Imagem Dilatada
     */
    public Imagem dilatar() {

        int[][] matrizResultante = new int[this.imagem.getLine()][this.imagem.getColumn()];
        int[][] matrizOriginal = this.imagem.getImagem();
        int[][] elementoEstruturante = this.ee.getElementoEstruturante();


        int result = 0;
        boolean flag = false;

        for (int i = 0; i < matrizOriginal.length; i++) {
            for (int j = 0; j < matrizOriginal[i].length; j++) {

                for (int x = 0; x < elementoEstruturante.length; x++) {
                    for (int y = 0; y < elementoEstruturante[x].length; y++) {

                        try {
                            int linha = i + x - this.ee.getX();
                            int coluna = j + y - this.ee.getY();
                            
                            int soma = matrizOriginal[linha][coluna] + elementoEstruturante[x][y];
                            
                            if (matrizOriginal[linha][coluna] != -1 && elementoEstruturante[x][y] != -1) {

                                if (Math.max(result, soma) > result) {
                                    flag = true;
                                    result = soma;
                                    if (result > 255) {
                                        result = 255;
                                    }
                                }

                            }
                        } catch (ArrayIndexOutOfBoundsException exception) {
                            // BORDA
                            continue;
                        }

                    }
                }
                if (flag) {
                    matrizResultante[i][j] = result;
                    flag = false;
                } else {
                    matrizResultante[i][j] = matrizOriginal[i][j];
                }
                result = 0;
            }
        }

        return new Imagem(matrizResultante);
    }
}
