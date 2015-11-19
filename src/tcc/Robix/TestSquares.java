/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcc.Robix;

import java.util.ArrayList;

/**
 *
 * @author Anderson
 */
public class TestSquares {
    
    public static void main(String[] args) {
        
        BridgeToRobix btr = new BridgeToRobix();
        
        String [] letras = {"a","b","c","d","e","f","g","h"};
        String [] numeros = {"1","2","3","4","5","6","7","8"};
        
        ArrayList<String> squares = new ArrayList<>(64);
        
        for (int j = 0; j < numeros.length; j++) {
            for (int i = 0; i < letras.length; i++) {
                squares.add(letras[i]+numeros[j]);
            }
        }
        
        for (int i = 1; i < squares.size(); i++) {
            //System.out.println((i-1)+"_"+i);
            //System.out.println(squares.get(i-1)+"_"+squares.get(i));
            btr.efetuaJogada(new MoveRobix(squares.get(i-1), squares.get(i), false));
        }
    }
    
    
    
}
