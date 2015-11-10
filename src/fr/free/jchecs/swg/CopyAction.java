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

import static fr.free.jchecs.core.FENUtils.toFEN;
import static fr.free.jchecs.swg.ResourceUtils.getImageIcon;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;

import javax.swing.Action;
import javax.swing.Icon;

import fr.free.jchecs.core.Board;

/**
 * Action de l'option "Copier une position".
 * 
 * @author David Cotton
 */
final class CopyAction extends AbstractUIAction
{
  /** Identifiant de la classe pour la sérialisation. */
  private static final long serialVersionUID = 5008253135868238198L;

  /** Petite icône. */
  private static final Icon ICON16 = getImageIcon("copy16.png");

  /** Icône moyenne. */
  private static final Icon ICON22 = getImageIcon("copy22.png");

  /**
   * Crée une nouvelle instance de l'action.
   * 
   * @param pUI Référence du composant maitre de l'interface.
   */
  CopyAction(final UI pUI)
  {
    super("copy", pUI);

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

    final Board plateau = getUI().getGame().getBoard();
    final StringSelection fen = new StringSelection(toFEN(plateau));
    final Clipboard clp = Toolkit.getDefaultToolkit().getSystemClipboard();
    clp.setContents(fen, fen);
  }
}
