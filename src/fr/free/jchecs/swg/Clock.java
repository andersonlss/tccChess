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

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import fr.free.jchecs.core.Game;

/**
 * Horloge du jeu.
 * 
 * @author David Cotton
 */
final class Clock
{
  /** Bordure par défaut. */
  private static final Border DEFAULT_BORDER = BorderFactory.createEmptyBorder(2, 2, 2, 2);

  /** Bordure signalant un dépassement du temps. */
  private static final Border TIMEOUT_BORDER = BorderFactory.createLineBorder(Color.RED, 2);

  /** Date utilisée pour alimenter l'affichage. */
  private final Date _date = new Date();

  /** Composant graphique externe. */
  private final JComponent _component;

  /** Composant affichant le compte à rebours noir. */
  private final JLabel _blackCountdown;

  /** Composant affichant le compte à rebours blanc. */
  private final JLabel _whiteCountdown;

  /**
   * Construit un nouveau composant d'affichage de l'horloge.
   * 
   * @param pPartie Définition de partie liée au composant.
   */
  Clock(final Game pPartie)
  {
    assert pPartie != null;

    final JPanel fond = new JPanel(new GridLayout(1, 2));
    _whiteCountdown = new JLabel();
    _whiteCountdown.setHorizontalAlignment(SwingConstants.CENTER);
    _whiteCountdown.setForeground(Color.BLACK);
    _whiteCountdown.setBackground(Color.WHITE);
    _whiteCountdown.setBorder(DEFAULT_BORDER);
    _whiteCountdown.setOpaque(true);
    final Font fonte = _whiteCountdown.getFont().deriveFont(17.5F);
    _whiteCountdown.setFont(fonte);
    setTimer(pPartie, true);
    fond.add(_whiteCountdown);
    _blackCountdown = new JLabel();
    _blackCountdown.setHorizontalAlignment(SwingConstants.CENTER);
    _blackCountdown.setForeground(Color.WHITE);
    _blackCountdown.setBackground(Color.BLACK);
    _blackCountdown.setBorder(DEFAULT_BORDER);
    _blackCountdown.setOpaque(true);
    _blackCountdown.setFont(fonte);
    setTimer(pPartie, false);
    fond.add(_blackCountdown);

    _component = fond;

    final Game partie = pPartie;
    partie.addPropertyChangeListener("timer", new PropertyChangeListener()
    {
      /**
       * Réagit aux changements des compteurs de temps.
       * 
       * @param pEvt Evènement signalant le changement.
       */
      public void propertyChange(final PropertyChangeEvent pEvt)
      {
        assert pEvt != null;

        setTimer(partie, false);
        setTimer(partie, true);
      }
    });
  }

  /**
   * Formate la date pour l'affichage du compteur.
   * 
   * @param pDate Date à formater.
   * @return Chaîne formattée pour le compteur.
   */
  private static String formatDate(final Date pDate)
  {
    assert pDate != null;

    final SimpleDateFormat sdf = new SimpleDateFormat("H:mm:ss");
    sdf.setTimeZone(TimeZone.getTimeZone("GMT"));

    return sdf.format(pDate);
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
   * Modifie le décompte affiché par un compteur.
   * 
   * @param pPartie Référence de la description de la partie en cours.
   * @param pCouleur A "true" pour alimenter le timer blanc, à "false" sinon.
   */
  void setTimer(final Game pPartie, final boolean pCouleur)
  {
    assert pPartie != null;

    final JLabel dst;
    if (pCouleur)
    {
      dst = _whiteCountdown;
    }
    else
    {
      dst = _blackCountdown;
    }

    final StringBuilder sb = new StringBuilder();
    long t = pPartie.getTimer(pCouleur);
    if (t >= 0)
    {
      dst.setBorder(DEFAULT_BORDER);
    }
    else
    {
      dst.setBorder(TIMEOUT_BORDER);
      t = -t + 1000;
      sb.append('-');
    }
    _date.setTime(t);
    sb.append(formatDate(_date));
    dst.setText(sb.toString());
  }
}
