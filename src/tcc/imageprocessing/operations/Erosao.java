package tcc.imageprocessing.operations;

import tcc.imageprocessing.elementos.*;

/**
 *
 * @author Gabriel Amaral
 */
public class Erosao extends Morfologia {

    public Erosao(Imagem imagem, ElementoEstruturante ee) {
        super(imagem, ee);
    }

    /**
     * Operações para erodir a imagem PGM
     *
     * @return Imagem Erodida
     */
    public Imagem erodir() {

        int[][] matrizResultante = new int[this.imagem.getLine()][this.imagem.getColumn()];
        int[][] matrizOriginal = this.imagem.getImagem();
        int[][] elementoEstruturante = this.ee.getElementoEstruturante();

        int result = this.imagem.getMaximumGray();
        boolean flag = false;

        for (int i = 0; i < matrizOriginal.length; i++) {
            for (int j = 0; j < matrizOriginal[i].length; j++) {

                for (int x = 0; x < elementoEstruturante.length; x++) {
                    for (int y = 0; y < elementoEstruturante[x].length; y++) {

                        try {
                            int linha = i + x - this.ee.getX();
                            int coluna = j + y - this.ee.getY();

                            int diferenca = matrizOriginal[linha][coluna] - elementoEstruturante[x][y];

                            if (matrizOriginal[linha][coluna] != -1 && elementoEstruturante[x][y] != -1) {

                                if (Math.min(result, diferenca) < result) {
                                    flag = true;
                                    result = diferenca;
                                    if (result < 0) {
                                        result = 0;
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
                result = this.imagem.getMaximumGray();
            }
        }

        return new Imagem(matrizResultante);
    }
}
