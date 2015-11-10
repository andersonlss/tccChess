/*
 $Id$

 Copyright (C) 2006-2007 by David Cotton

 This program is free software; you can redistribute it and/or modify it under
 the terms of the GNU General Public License as published by the Free Software
 Foundation; either version 2 of the License, or (at your option) any later
 version.

 This program is distributed in the hope that it will be useful, but WITHOUT
 ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.

 You should have received a copy of the GNU General Public License along with
 this program; if not, write to the Free Software Foundation, Inc., 51 Franklin
 Street, Fifth Floor, Boston, MA 02110-1301, USA.
 */
package fr.free.jchecs.swg;

import static fr.free.jchecs.swg.ResourceUtils.getImageIcon;

import static java.awt.Image.SCALE_SMOOTH;
import static java.awt.image.BufferedImage.TYPE_INT_ARGB;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.EnumMap;
import java.util.Map;

import javax.swing.ImageIcon;

import fr.free.jchecs.core.Piece;

/**
 * Classe renvoyant la représentation graphique d'une pièce.
 * 
 * @author David Cotton
 */
final class PieceUI
{

  /** Correspondances pièce / curseur. */
  private final Map<Piece, Cursor> _pieceToCursor = new EnumMap<Piece, Cursor>(Piece.class);

  /** Correspondances pièce / image. */
  private final Map<Piece, Image> _pieceToImage = new EnumMap<Piece, Image>(Piece.class);

  /** Correspondances pièce / image. */
  private final Map<Piece, Image> _pieceToSmallImage = new EnumMap<Piece, Image>(Piece.class);

  /**
   * Créé une nouvelle instance.
   */
  PieceUI()
  {
    // Rien de spécifique...
  }

  /**
   * Renvoi le curseur représentant une pièce.
   * 
   * @param pPiece Pièce dont on veut le curseur.
   * @return Curseur représentant la pièce.
   */
  Cursor getCursor(final Piece pPiece)
  {
    assert pPiece != null;

    return _pieceToCursor.get(pPiece);
  }

  /**
   * Renvoi l'image représentant une pièce.
   * 
   * @param pPiece Pièce dont on veut l'image.
   * @return Image de la pièce.
   */
  Image getImage(final Piece pPiece)
  {
    assert pPiece != null;

    return _pieceToImage.get(pPiece);
  }

  /**
   * Renvoi l'image réduite représentant une pièce.
   * 
   * @param pPiece Pièce dont on veut l'image réduite.
   * @return Image réduite de la pièce.
   */
  Image getSmallImage(final Piece pPiece)
  {
    assert pPiece != null;

    return _pieceToSmallImage.get(pPiece);
  }

  /**
   * Initialise les sprites.
   * 
   * @param pLongueur Longueur d'un côté des pièces (>= 1).
   * @param pLF Préfixe de l'apparence, à ajouter au chemin d'accès des images.
   */
  void initialize(final int pLongueur, final String pLF)
  {
    assert pLongueur >= 1;
    assert pLF != null;

    final Toolkit tk = Toolkit.getDefaultToolkit();
    final int demiLongueur = pLongueur / 2;
    final Dimension d = tk.getBestCursorSize(pLongueur, pLongueur);
    final Point centre = new Point(d.width / 2, d.height / 2);
    for (final Piece p : Piece.values())
    {
      final StringBuilder sb = new StringBuilder(pLF);
      if (p.isWhite())
      {
        sb.append('w');
      }
      else
      {
        sb.append('b');
      }
      sb.append(p.getFENLetter()).append(".png");
      final Image imgTmp = getImageIcon(sb.toString()).getImage();
      final BufferedImage img = new BufferedImage(pLongueur, pLongueur, TYPE_INT_ARGB);
      final Graphics2D g2d = img.createGraphics();
      // getScaledInstance() est asynchrone : l'ImageIcon force une synchronisation.
      g2d.drawImage(new ImageIcon(imgTmp.getScaledInstance(pLongueur, pLongueur, SCALE_SMOOTH))
          .getImage(), 0, 0, null);
      g2d.dispose();
      _pieceToCursor.put(p, tk.createCustomCursor(new ImageIcon(imgTmp.getScaledInstance(d.width,
          d.height, SCALE_SMOOTH)).getImage(), centre, Character.toString(p.getFENLetter())));
      _pieceToImage.put(p, img);
      _pieceToSmallImage.put(p, new ImageIcon(imgTmp.getScaledInstance(demiLongueur, demiLongueur,
          SCALE_SMOOTH)).getImage());
    }
  }
}
