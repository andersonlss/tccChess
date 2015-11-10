/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcc.ChessAux.Structure;

/**
 *
 * @author Anderson
 */
public class Piece {
    
    String name;
    String color;
    String square;

    public Piece(String name, String color, String square) {
        this.name = name;
        this.color = color;
        this.square = square;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }
    
    public String getNamePiece(){
        return name+color;
    }
    
    public String getSquare() {
        return square;
    }

    public void setSquare(String square) {
        this.square = square;
    }

    @Override
    public String toString() {
        return name+color+"_"+square;
    }
    
    
    
}
