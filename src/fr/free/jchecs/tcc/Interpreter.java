/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.free.jchecs.tcc;

import fr.free.jchecs.core.MailboxBoard;
import fr.free.jchecs.core.Move;
import fr.free.jchecs.core.Piece;
import fr.free.jchecs.core.Square;
import static fr.free.jchecs.core.PieceType.PAWN;
import static fr.free.jchecs.core.Constants.FILE_COUNT;
import java.util.Scanner;

/**
 *
 * @author Anderson
 */
public class Interpreter {

    private MailboxBoard mbBoard;
    private String squareFEN1, squareFEN2;
    private Square squareOri, squareDst;
    private Piece pOri, pDst;
    private boolean isWhiteActive;

    public Interpreter(MailboxBoard mbBoard, boolean isWhiteActive) {
        this.mbBoard = mbBoard;
        this.isWhiteActive = isWhiteActive;
    }

    /*
     * @param pTrait Positionné à "true" pour indiquer une recherche pour les blancs.
     */
    private Move getNewMove(final boolean pTrait) {

        Move mv = null;

        //comeca aqui com umas das pecas ja identificada
        //final Piece p = _pieces[];
        final Piece p = mbBoard.getPieceAt(squareOri);

        if ((p != null) && (p.isWhite() == pTrait)) {
            final Square orig = squareOri;

            for (final Square dst : mbBoard.getValidTargets(orig)) {

                final Piece prise;
                if ((p.getType() != PAWN) || (dst != mbBoard.getEnPassant())) {
                    prise = mbBoard.getPieceAt(dst);
                } else {
                    if (pTrait) {
                        prise = mbBoard.getPieces()[dst.getIndex() - FILE_COUNT];
                    } else {
                        prise = mbBoard.getPieces()[dst.getIndex() + FILE_COUNT];
                    }
                }
                mv = new Move(p, orig, dst, prise);
                if (squareDst == dst) {
                    System.out.println(mv.toString());
                    break;
                }
            }
        }
        return mv;
    }

    public Move getMoveFromString_Move(String move) {

        squareFEN1 = move.split("_")[0];
        squareFEN2 = move.split("_")[1];

        Piece p1 = mbBoard.getPieceAt(Square.valueOf(squareFEN1));
        Piece p2 = mbBoard.getPieceAt(Square.valueOf(squareFEN2));

        try {

            validMove(p1, p2);

            if ((p1 != null) && (p1.isWhite() == isWhiteActive)) {
                squareOri = Square.valueOf(squareFEN1);
                squareDst = Square.valueOf(squareFEN2);
            } else {
                squareOri = Square.valueOf(squareFEN2);
                squareDst = Square.valueOf(squareFEN1);
            }

            return getNewMove(isWhiteActive);

        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
        
        return null;
    }

    public Move loopTest() {
        
        Scanner ler = new Scanner(System.in);

        System.out.println("Digite 'ok' depois de posicionar as pecas no tabuleiro.\n"
                + "Ou 'exit' para fechar programa.");
        
        Move mv = null;
        
        while (true) {
            System.out.print(">");

            String resp = ler.nextLine();

            mv = getMoveFromString_Move(resp);
            
            if (mv!= null) {
                break;
            }
        }
        return mv;
    }

    private void validMove(Piece p1, Piece p2) throws Exception {
        if (p1 == null && p2 == null) {
            throw new Exception("Posicoes vazias!!!");
        }
        if ((p1 != null) && (p2 != null) && p1.isWhite() != isWhiteActive && p2.isWhite() != isWhiteActive) {
            throw new Exception("Nenhuma peca é do Atual Jogador!!!");

        } else if ((p1 != null) && (p2 != null) && (p1.isWhite() == p2.isWhite())) {
            throw new Exception("Cores iguais!!!");
        }
    }
}
