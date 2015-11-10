/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcc.ChessAux.Structure;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;

/**
 *
 * @author Anderson
 */
public class Board {

    HashMap<String, Piece> chessboard;
    Board_JFrame boardFrame;

    public Board() {
        chessboard = new HashMap<String, Piece>(32);
        boardFrame = new Board_JFrame();
        readInitChess();
        boardFrame.setVisible(true);
    }

    public Board(String[] customBoard) {
        chessboard = new HashMap<String, Piece>(32);
        boardFrame = new Board_JFrame();
        readCustomBoard(customBoard);
        boardFrame.setVisible(true);
    }
    
    public void destroy(){
        boardFrame.dispose();
    }

    private void readCustomBoard(String[] customBoard) {
        //formato da string de entrada: piece color customBoard

        String entrada;
        for (int i = 0; i < customBoard.length; i++) {
            entrada = customBoard[i];

            String[] split = entrada.split(" ");

            insertPieceChessBoard( new Piece(split[0], split[1], split[2]) );
        }

    }

    private void readInitChess() {

        String caminho = System.getProperty("user.dir") + "/src/Chess/Structure/initChess.txt";
        File f = new File(caminho);
        Scanner in = null;

        try {
            in = new Scanner(f);
        } catch (FileNotFoundException ex) {
            System.err.println("ERROR: " + ex.getMessage());
        }

        int w = 0, b = 0;

        while (in.hasNextLine()) {
            String line = in.nextLine();

            if ('w' == line.charAt(0)) {
                String[] temp = line.split(" ");
                w = Integer.parseInt(temp[0].substring(2));
                b = Integer.parseInt(temp[1].substring(2));

            } else {
                String piece = line.substring(0, 1);
                String[] temp = line.substring(2).split("_");

                for (int i = 0; i < temp.length; i++) {

                    insertPieceChessBoard(new Piece(piece, "w", temp[i] + w));
                    insertPieceChessBoard(new Piece(piece, "b", temp[i] + b));
//                    System.out.println(piece+"w_"+temp[i]+w);
//                    System.out.println(piece+"b_"+temp[i]+b);
                }
            }
        }
    }

    public void insertPieceChessBoard(Piece piece) {
        chessboard.put(piece.getSquare(), piece);
        boardFrame.insertPieceBoard(piece);
    }

    public Piece getPieceChessBoard(String square) {
        Piece p = chessboard.get(square);

        if (p == null) {
            p = new Piece("vazio", "vazio", square);
        }

        return p;
    }

    public void removePieceChessBoard(String square) {
        chessboard.remove(square);
    }

    public void movePieceChessBoard(String move) {
        String square = move.split("_")[0];
        String newSquare = move.split("_")[1];

        Piece piece = chessboard.remove(square);
        piece.setSquare(newSquare);

        chessboard.put(newSquare, piece);
        boardFrame.movePieceBoard(square, newSquare);
    }

    public void viewChessBoard() {
        System.out.print(">");
        for (String key : chessboard.keySet()) {
            System.out.print(" ");
            System.out.print(chessboard.get(key).toString());
        }
        System.out.println("");
    }

//    public static void main(String[] args) {
//        
//        String [] custom = {"K b a5","K w h8", "P b e1"};
//        
//        Board b = new Board(custom);
//
////        b.readInitChess();
////        b.boardFrame.setVisible(true);
////        try {
////            Thread.sleep(500);
////        } catch (InterruptedException ex) {
////            Logger.getLogger(Board.class.getName()).log(Level.SEVERE, null, ex);
////        }
////        b.movePieceChessBoard("h1_h5");
//
////        b.viewChessBoard();
//    }
}
