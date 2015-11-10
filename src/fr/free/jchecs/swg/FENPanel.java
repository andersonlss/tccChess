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

import static fr.free.jchecs.swg.ResourceUtils.getI18NString;

import java.awt.BorderLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import fr.free.jchecs.core.Game;

/**
 * Composant affichant la notation FEN.
 * 
 * @author David Cotton
 */
final class FENPanel
{
  /** Composant graphique externe. */
  private final JComponent _component;

  /**
   * Construit un nouveau composant d'affichage de la notation FEN.
   * 
   * @param pPartie Définition de partie liée au composant.
   */
  FENPanel(final Game pPartie)
  {
    assert pPartie != null;

    final JPanel fond = new JPanel(new BorderLayout(4, 4));

    fond.add(new JLabel(getI18NString("panel.fen.title")), BorderLayout.WEST);
    final JTextField champ = new JTextField(pPartie.getFENPosition());
    champ.setEditable(false);
    fond.add(champ, BorderLayout.CENTER);

    _component = fond;

    pPartie.addPropertyChangeListener("position", new FENPanelListener(pPartie, champ));
  }

  /**
   * Renvoi le composant graphique affichable.
   * 
   * @return Composant graphique.
   */
  JComponent getComponent()
  {
    assert _component != null;
    return _component;
  }

  /**
   * Classe à l'écoute des évènement modifiant le panel.
   */
  private static final class FENPanelListener implements PropertyChangeListener
  {
    /** Référence de la partie suivie. */
    private final Game _game;

    /** Référence du champ à modifier. */
    private final JTextField _textField;

    /**
     * Instancie un nouvel écouteur.
     * 
     * @param pGame Partie à écouter.
     * @param pField Champ à modifier.
     */
    FENPanelListener(final Game pGame, final JTextField pField)
    {
      assert pGame != null;
      assert pField != null;

      _game = pGame;
      _textField = pField;
    }

    /**
     * Réagit au changement dans la définition de la partie.
     * 
     * @param pEvt Evènement signalant le changement.
     */
    public void propertyChange(final PropertyChangeEvent pEvt)
    {
      assert pEvt != null;

      _textField.setText(_game.getFENPosition());
    }
  }
}
