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
import static fr.free.jchecs.swg.ResourceUtils.getImageIcon;

import java.awt.event.ActionEvent;
import java.io.File;
import java.util.Locale;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JFileChooser;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Action de l'option "Enregistrer la position sous...".
 * 
 * @author David Cotton
 */
final class SavePositionAsAction extends AbstractUIAction
{
  /** Identifiant de la classe pour la sérialisation. */
  private static final long serialVersionUID = 3176295779735665880L;

  /** Petite icône. */
  private static final Icon ICON16 = getImageIcon("saveFENAs16.png");

  /** Icône moyenne. */
  private static final Icon ICON22 = getImageIcon("saveFENAs22.png");

  /**
   * Crée une nouvelle instance de l'action.
   * 
   * @param pUI Référence du composant maitre de l'interface.
   */
  SavePositionAsAction(final UI pUI)
  {
    super("savePositionAs", pUI);

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

    final JFileChooser dialog = new JFileChooser();
    dialog.setDialogTitle(getI18NString("action.savePosition.name"));
    dialog.addChoosableFileFilter(new FileNameExtensionFilter(
        getI18NString("action.savePosition.filter"), "fen"));
    final UI ui = getUI();
    if (dialog.showSaveDialog(ui.getMainFrame()) == JFileChooser.APPROVE_OPTION)
    {
      File fichier = dialog.getSelectedFile();
      if (!fichier.getName().toLowerCase(Locale.getDefault()).endsWith(".fen"))
      {
        fichier = new File(fichier.getAbsolutePath() + ".fen");
      }
      ui.setPositionFile(fichier);
      SwingUtilities.invokeLater(new PositionSaver(ui));
    }
  }
}
