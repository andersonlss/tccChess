package tcc.imageprocessing.elementos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author Gabriel Amaral
 */
public class ElementoEstruturante {

    private int[][] elementoEstruturante;
    private int x;
    private int y;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    
    public int[][] getElementoEstruturante() {
        return elementoEstruturante;
    }

    public void setElementoEstruturante(int[][] elementoEstruturante) {
        this.elementoEstruturante = elementoEstruturante;
    }

    /**
     *
     * @param file Elemento Estruturante
     * @return True se carregou e False caso contrário
     */
    public boolean carregarElementoEstruturante(File file) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            try {
                if (br.ready()) {
                    String linhaDoArquivo = br.readLine();
                    String[] elementos = linhaDoArquivo.split(" ");
                    if (elementos.length == 2) {
                        int col = Integer.parseInt(elementos[0]);
                        int lin = Integer.parseInt(elementos[1]);
                        elementoEstruturante = new int[lin][col];
                    }
                }
                if (br.ready()) {
                    String linhaDoArquivo = br.readLine();
                    String[] elementos = linhaDoArquivo.split(" ");
                    if (elementos.length == 2) {
                        this.x = Integer.parseInt(elementos[0]);
                        this.y = Integer.parseInt(elementos[1]);
                    }
                }
                int lin = 0, col = 0;
                while (br.ready()) {
                    String linhaDoArquivo = br.readLine();
                    String[] elementos = linhaDoArquivo.split(" ");
                    int numElementos = elementos.length;
                    for (int i = 0; i < numElementos; i++) {
                        try {
                            elementoEstruturante[lin][col++] = Integer.parseInt(elementos[i]);
                        } catch (NumberFormatException exception) {
                            if (elementos[i].equals("")) {
                                col--;
                            }
                        }
                        if (col == elementoEstruturante[0].length) {
                            lin++;
                            col = 0;
                        }
                    }

                }
                br.close();
            } catch (IOException ex) {
                System.out.println("Impossivel ler arquivo!");
                return false;
            }
        } catch (FileNotFoundException ex) {
            System.out.println("Arquivo não encontrado!");
            return false;
        }
        return true;
    }
}
