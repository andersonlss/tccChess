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
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class ChessGameDemo extends JFrame implements MouseListener, MouseMotionListener {

    JLayeredPane layeredPane;
    JPanel chessBoard;
    JPanel square;
    JLabel chessPiece;
    int xAdjustment;
    int yAdjustment;

    public ChessGameDemo() {
        Dimension boardSize = new Dimension(600, 600);

        //  Use a Layered Pane for this this application
        layeredPane = new JLayeredPane();
        getContentPane().add(layeredPane);
        layeredPane.setPreferredSize(boardSize);
        layeredPane.addMouseListener(this);
        layeredPane.addMouseMotionListener(this);

        //Add a chess board to the Layered Pane 
        chessBoard = new JPanel();
        layeredPane.add(chessBoard, JLayeredPane.DEFAULT_LAYER);
        chessBoard.setLayout(new GridLayout(8, 8));
        chessBoard.setPreferredSize(boardSize);
        chessBoard.setBounds(0, 0, boardSize.width, boardSize.height);

        for (int i = 0; i < 64; i++) {
            JPanel square = new JPanel(new BorderLayout());
            square.setName(Integer.toString(i));
            chessBoard.add(square);

            int row = (i / 8) % 2;
            if (row == 0) {
                square.setBackground(i % 2 == 0 ? Color.GRAY : Color.LIGHT_GRAY);
            } else {
                square.setBackground(i % 2 == 0 ? Color.LIGHT_GRAY : Color.GRAY);
            }
        }

//  Add a few pieces to the board
        JLabel piece = new JLabel(new ImageIcon(System.getProperty("user.dir") + "/src/Chess/imgs/Qb.png"));
        JPanel panel = (JPanel) chessBoard.getComponent(0);
        panel.add(piece);
//  piece = new JLabel(new ImageIcon("/home/vinod/amarexamples/chess1.jpg"));
//  panel = (JPanel)chessBoard.getComponent(15);
//  panel.add(piece);
//  piece = new JLabel(new ImageIcon("/home/vinod/amarexamples/king.jpg"));
//  panel = (JPanel)chessBoard.getComponent(16);
//  panel.add(piece);
//  piece = new JLabel(new ImageIcon("/home/vinod/amarexamples/camel.jpg"));
//  panel = (JPanel)chessBoard.getComponent(20);
//  panel.add(piece);
    }

    public void mousePressed(MouseEvent e) {

        chessPiece = null;
        Component c = chessBoard.findComponentAt(e.getX(), e.getY());

        if (c instanceof JPanel) {
            return;
        }

        Point parentLocation = c.getParent().getLocation();
        xAdjustment = parentLocation.x - e.getX();
        yAdjustment = parentLocation.y - e.getY();
        chessPiece = (JLabel) c;
        chessPiece.setLocation(e.getX() + xAdjustment, e.getY() + yAdjustment);
        chessPiece.setSize(chessPiece.getWidth(), chessPiece.getHeight());
        layeredPane.add(chessPiece, JLayeredPane.DRAG_LAYER);
    }

    //Move the chess piece around
    public void mouseDragged(MouseEvent me) {
        if (chessPiece == null) {
            return;
        }
        chessPiece.setLocation(me.getX() + xAdjustment, me.getY() + yAdjustment);
    }

    //Drop the chess piece back onto the chess board
    public void mouseReleased(MouseEvent e) {
        if (chessPiece == null) {
            return;
        }

        chessPiece.setVisible(false);
        Component c = chessBoard.findComponentAt(e.getX(), e.getY());

        if (c instanceof JLabel) {
            Container parent = c.getParent();
            parent.remove(0);
            parent.add(chessPiece);
        } else {
            Container parent = (Container) c;
            parent.add(chessPiece);
        }

        chessPiece.setVisible(true);
    }

    public void mouseClicked(MouseEvent e) {
        square = (JPanel) chessBoard.getComponentAt(e.getX(), e.getY());

        System.out.println(">" + square.getName());
    }

    public void mouseMoved(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {

    }

    public static void main(String[] args) {
        JFrame frame = new ChessGameDemo();
        frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setResizable(true);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
