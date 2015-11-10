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

import static fr.free.jchecs.core.BoardFactory.State.EMPTY;
import static fr.free.jchecs.core.BoardFactory.Type.FASTEST;
import static fr.free.jchecs.core.FENUtils.toBoard;
import static fr.free.jchecs.swg.ResourceUtils.getImageIcon;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.swing.Action;
import javax.swing.Icon;

import fr.free.jchecs.core.Board;
import fr.free.jchecs.core.BoardFactory;
import fr.free.jchecs.core.FENException;
import fr.free.jchecs.core.MoveGenerator;

/**
 * Action de l'option "Coller une position".
 * 
 * @author David Cotton
 */
final class PasteAction extends AbstractUIAction
{
  /** Identifiant de la classe pour la sérialisation. */
  private static final long serialVersionUID = 2821129884839866355L;

  /** Petite icône. */
  private static final Icon ICON16 = getImageIcon("paste16.png");

  /** Icône moyenne. */
  private static final Icon ICON22 = getImageIcon("paste22.png");

  /**
   * Crée une nouvelle instance de l'action.
   * 
   * @param pUI Référence du composant maitre de l'interface.
   */
  PasteAction(final UI pUI)
  {
    super("paste", pUI);

    putValue(Action.SMALL_ICON, ICON16);
    putValue(Action.LARGE_ICON_KEY, ICON22);
  }

  /**
   * Réaction face au déclenchement de l'action.
   * 
   * @param pEvent Evènement signalant le déclenchement.
   */
  public void actionPerformed(final ActionEvent pEvent)
  {
    assert pEvent != null;

    final Clipboard clp = Toolkit.getDefaultToolkit().getSystemClipboard();
    final Transferable trs = clp.getContents(null);
    if ((trs != null) && (trs.isDataFlavorSupported(DataFlavor.stringFlavor)))
    {
      try
      {
        final String fen = (String) trs.getTransferData(DataFlavor.stringFlavor);
        try
        {
          final Board plateau = toBoard(fen.trim());
          final MoveGenerator etat = BoardFactory.valueOf(FASTEST, EMPTY).derive(plateau);
          getUI().getGame().resetTo(etat);
        }
        catch (final FENException e)
        {
          // Pas grave, on ignore la demande...
        }
      }
      catch (final UnsupportedFlavorException e)
      {
        // Pas grave, on ignore la demande...
      }
      catch (final IOException e)
      {
        // Pas grave, on ignore la demande...
      }
    }
  }
}
