package tcc.imageprocessing.operations;

import tcc.imageprocessing.elementos.*;

/**
 *
 * @author Gabriel Amaral
 */
public class OperacaoMorfologica {

    /**
     * 
     * @param imagem Imagem PGM
     * @param ee Elemento Estruturante
     * @return Imagem Dilatada
     */
    public static Imagem dilatar(Imagem imagem, ElementoEstruturante ee){
        Dilatacao dilatacao = new Dilatacao(imagem, ee);
        return dilatacao.dilatar();
    }
    
    /**
     * 
     * @param imagem Imagem PGM
     * @param ee Elemento Estruturante
     * @return Imagem Erodida
     */
    public static Imagem erodir(Imagem imagem, ElementoEstruturante ee){
        Erosao erosao = new Erosao(imagem, ee);
        return erosao.erodir();
    }
    
    /**
     * 
     * @param imagem Imagem PGM
     * @param ee Elemento Estruturante
     * @return Imagem Aberta
     */
    public static Imagem abertura(Imagem imagem, ElementoEstruturante ee){
        Erosao erosao = new Erosao(imagem, ee);
        Dilatacao dilatacao = new Dilatacao(erosao.erodir(), ee);
        return dilatacao.dilatar();
    } 
    
    /**
     * 
     * @param imagem Imagem PGM
     * @param ee Elemento Estruturante
     * @return Imagem Fechada
     */
    public static Imagem fechamento(Imagem imagem, ElementoEstruturante ee){
        Dilatacao dilatacao = new Dilatacao(imagem, ee);
        Erosao erosao = new Erosao(dilatacao.dilatar(), ee);
        return erosao.erodir();
    }
    
    /**
     * 
     * @param imagem Imagem PGM
     * @param ee Elemento Estruturante
     * @return Imagem Suavizada
     */
    public static Imagem suavizacao(Imagem imagem, ElementoEstruturante ee){
        return fechamento(abertura(imagem, ee), ee);
    }
    
    /**
     * 
     * @param imagem Imagem PGM
     * @param ee Elemento Estruturante
     * @return Imagem Gradiente
     */
    public static Imagem gradiente(Imagem imagem, ElementoEstruturante ee){
        Dilatacao dilatacao = new Dilatacao(imagem, ee);
        Erosao erosao = new Erosao(imagem, ee);
        return divisao(subtracao(dilatacao.dilatar(), erosao.erodir()));
    }
    
    /**
     * 
     * @param imagemI Imagem imagem inicial
     * @param imagemF Imagem imagem final
     * @return Imagem
     */
    public static Imagem subtracao(Imagem imagemI, Imagem imagemF){
        int[][] matrizI = imagemI.getImagem();
        int[][] matrizF = imagemF.getImagem();
        int[][] matrizResultante = new int[matrizI.length][matrizI[0].length];
        for (int i = 0; i < matrizI.length; i++) {
            for (int j = 0; j < matrizI[i].length; j++) {
                matrizResultante[i][j] = Math.abs(matrizI[i][j] - matrizF[i][j]);
            }
        }
        return new Imagem(matrizResultante);
    }
    
    /**
     * 
     * @param imagem Imagem a ser dividida por 2
     * @return Imagem
     */
    private static Imagem divisao(Imagem imagem){
        int[][] matrizOriginal = imagem.getImagem();
        int[][] matrizResultante = new int[matrizOriginal.length][matrizOriginal[0].length];
        for (int i = 0; i < matrizOriginal.length; i++) {
            for (int j = 0; j < matrizOriginal[i].length; j++) {
                matrizResultante[i][j] = matrizOriginal[i][j] / 2;
            }
        }
        return new Imagem(matrizResultante);
    }
    
}
