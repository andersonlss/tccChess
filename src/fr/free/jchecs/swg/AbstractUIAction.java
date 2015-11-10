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

import static fr.free.jchecs.swg.ResourceUtils.getI18NChar;
import static fr.free.jchecs.swg.ResourceUtils.getI18NString;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFrame;

/**
 * Classe mère des actions supportées par l'interface Swing.
 * 
 * @author David Cotton
 */
abstract class AbstractUIAction extends AbstractAction
{
  /** Référence vers le composant maitre de l'interface Swing. */
  private final UI _ui;

  /**
   * Instancie une nouvelle action.
   * 
   * @param pNom Nom identifiant l'action.
   * @param pUI Référence du composant maitre de l'interface.
   */
  protected AbstractUIAction(final String pNom, final UI pUI)
  {
    super(pNom);

    assert pNom != null;
    assert pUI != null;

    _ui = pUI;

    final String racine = "action." + pNom + '.';
    putValue(Action.NAME, getI18NString(racine + "name"));
    putValue(Action.SHORT_DESCRIPTION, getI18NString(racine + "description"));
    putValue(Action.MNEMONIC_KEY, Integer.valueOf(getI18NChar(racine + "mnemonic")));
  }

  /**
   * Renvoi la référence de la fenêtre principale.
   * 
   * @return Fenêtre principale.
   */
  protected final JFrame getMainFrame()
  {
    return getUI().getMainFrame();
  }

  /**
   * Renvoi la référence vers le composant maitre.
   * 
   * @return Composant maitre.
   */
  protected final UI getUI()
  {
    assert _ui != null;
    return _ui;
  }
}
