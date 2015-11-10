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
import static fr.free.jchecs.swg.ResourceUtils.getI18NString;
import static fr.free.jchecs.swg.ResourceUtils.getImageIcon;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.Icon;
import javax.swing.JOptionPane;

/**
 * Classe prenant en charge la sauvegarde de la position dans le fichier FEN courant.
 * 
 * @author David Cotton
 */
final class PositionSaver implements Runnable
{
  /** Grande icône, pour la boite de dialogue d'erreur. */
  private static final Icon ICON32 = getImageIcon("error32.png");

  /** Référence de l'interface graphique liée. */
  private final UI _ui;

  /**
   * Instancie un nouveau objet de sauvegarde d'une position.
   * 
   * @param pUI Interface graphique mêre.
   */
  PositionSaver(final UI pUI)
  {
    assert pUI != null;

    _ui = pUI;
  }

  /**
   * Affiche la boite de dialogue signalant l'impossibilité d'écrire le fichier.
   */
  private void errorDialog()
  {
    JOptionPane.showMessageDialog(_ui.getMainFrame(),
        getI18NString("action.savePosition.error.message"),
        getI18NString("action.savePosition.error.title"), JOptionPane.ERROR_MESSAGE, ICON32);
  }

  /**
   * Réalise la sauvegarde.
   */
  public void run()
  {
    final File fichier = _ui.getPositionFile();
    if (fichier != null)
    {
      BufferedWriter out = null;
      try
      {
        out = new BufferedWriter(new FileWriter(fichier));
      }
      catch (final IOException e)
      {
        errorDialog();
      }
      if (out != null)
      {
        try
        {
          out.write(toFEN(_ui.getGame().getBoard()));
          out.close();
        }
        catch (final IOException e)
        {
          errorDialog();
          try
          {
            out.close();
          }
          catch (final IOException e1)
          {
            // Tant pis, la fermeture de la JVM s'en chargera.
          }
        }
      }
    }
  }
}
