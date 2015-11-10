/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcc.ChessAux.Utilitys;

import tcc.ChessAux.Structure.Board;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 *
 * @author Anderson
 */
public class CustomBoard {
    
    //auxiliar
    String[] letras = {"a", "b", "c", "d", "e", "f", "g", "h"};
    String[] numeros = {"5", "6", "7", "8"};
    ArrayList<String> pieces = new ArrayList<String>(32);
    ArrayList<String> squares = new ArrayList<String>(64);
    Random random = new Random();
    
    public CustomBoard() {
        iniciaArrayLists();
    }
    
    private void iniciaArrayLists(){
        iniciaPieces();
        iniciaSquares();
    }
    
    private void iniciaPieces() {
        //formato: nomePeca qtd
        String[] names = {"R 2", "N 2", "B 2", "Q 1", "K 1", "P 8"};
        
        for (int i = 0; i < names.length; i++) {
            String[] aux = names[i].split(" ");
            for (int j = 0; j < Integer.parseInt(aux[1]); j++) {
                pieces.add(aux[0] + " w");
                pieces.add(aux[0] + " b");
            }
        }
    }
    
    private void iniciaSquares(){
        for (int i = 0; i < letras.length; i++) {
            for (int j = 0; j < numeros.length; j++) {
                squares.add(letras[i]+numeros[j]);
            }
        }
    }
    
    private int randomInt(int limite){
        return (int) (random.nextDouble() * limite);
    }
    
    private String randomString(String[] vetorString) {
        int i = randomInt(vetorString.length);

        return vetorString[i];
    }

    private String randomSquare() {
        return randomString(letras) + randomString(numeros);
    }
    
    private String randomPiece() {
        
        Collections.sort(pieces);
        Collections.sort(squares);
        
        int i = randomInt(pieces.size());
        
        String piece = pieces.get(i);
        pieces.remove(i);
        
        i = randomInt(squares.size());
        
        String square = squares.get(i);
        squares.remove(i);

        return piece+" "+square;
    }

    public String randomJogada() {
        return randomSquare() + "_" + randomSquare();
    }

    public Board randomBoard(int qtdPieces){
        
        String [] customBoard = new String[qtdPieces];
        
        for (int i = 0; i < qtdPieces; i++) {
            customBoard[i] = randomPiece();
        }
        
        return new Board(customBoard);
    }
    
}
