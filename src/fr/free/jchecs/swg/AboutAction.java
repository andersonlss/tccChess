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

import java.awt.event.ActionEvent;

import javax.swing.Action;
import javax.swing.Icon;

/**
 * Action de l'option "A propos...".
 * 
 * @author David Cotton
 */
final class AboutAction extends AbstractUIAction
{
  /** Identifiant de la classe pour la sérialisation. */
  private static final long serialVersionUID = 7796477339472294783L;

  /** Petite icône. */
  private static final Icon ICON16 = getImageIcon("about16.png");

  /** Icône moyenne. */
  private static final Icon ICON22 = getImageIcon("about22.png");

  /**
   * Crée une nouvelle instance de l'action.
   * 
   * @param pUI Référence du composant maitre de l'interface.
   */
  AboutAction(final UI pUI)
  {
    super("about", pUI);

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

    AboutDialog.showAboutDialog(getMainFrame());
  }
}
