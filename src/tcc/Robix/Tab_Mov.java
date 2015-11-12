/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcc.Robix;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

/**
 *
 * @author Anderson
 */
public class Tab_Mov {

    HashMap<String, String> moves;

    public Tab_Mov() {
        moves = new HashMap<String, String>();
        //registra();
        leSquares();
    }

//    private void registra() {
//        moves.put("Init", "move all to initpos;");
//        moves.put("A", "move 1 to -450, 2 to 750, 3 to 130, 4 to 560, 5 to 650, 6 to 900, 7 to 230;");
//        moves.put("B", "move 1 to -1390, 2 to 910, 3 to 1100, 4 to 1250, 5 to 750, 6 to 900, 7 to 230;");
//        moves.put("C", "move 1 to 374, 2 to 680, 3 to -1500, 4 to -510, 5 to 670, 6 to 900, 7 to 230;");
//        moves.put("D", "move 1 to 764, 2 to 680, 3 to -1500, 4 to -510, 5 to 670, 6 to 900, 7 to 230;");
//    }

    private void leSquares() {
        String caminho = System.getProperty("user.dir") + "/src/tcc/Robix/Squares.txt";
        File f = new File(caminho);
        Scanner in = null;

        try {
            in = new Scanner(f);
        } catch (FileNotFoundException ex) {
            System.err.println("ERROR: " + ex.getMessage());
        }

        while (in.hasNextLine()) {
            moves.put(in.nextLine(), in.nextLine().replaceAll("\t", " "));
//            System.out.println(in.nextLine()+".\n> "+in.nextLine().replaceAll("\t", " "));
        }
    }

    public String getScript(MoveRobix jogada){
        
        String origem = getMov(jogada.getOrigem());
        String destino = getMov(jogada.getDestino());
        
        MakeScript ms = new MakeScript(origem, destino, jogada.isIsCapture());
        
        return ms.getScript();
    }
    
    private String getMov(String key) {
        return moves.get(key);
    }

//    public static void main(String[] args) {
//        Tab_Mov tm = new Tab_Mov();
//        
//    }

}
