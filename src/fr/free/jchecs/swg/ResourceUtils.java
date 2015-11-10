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

import static fr.free.jchecs.core.Constants.APPLICATION_NAME;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.logging.Logger;

import javax.swing.ImageIcon;

/**
 * Classe utilitaire facilitant l'accès aux ressources internationalisées.
 * 
 * @author David Cotton
 */
public final class ResourceUtils
{
  /** Log de la classe. */
  private static final Logger LOGGER = Logger.getLogger(ResourceUtils.class.getName());

  /** Instance interne du fichier des propriétés correspondant à la locale active. */
  private static final Properties PROPERTIES = new Properties();
  static
  {
    final String fic = APPLICATION_NAME + "_" + Locale.getDefault().getLanguage() + ".properties";
    InputStream in = ResourceUtils.class.getResourceAsStream(fic);
    if (in == null)
    {
      in = ResourceUtils.class.getResourceAsStream(APPLICATION_NAME + "_en.properties");
    }
    if (in != null)
    {
      try
      {
        PROPERTIES.load(in);
      }
      catch (final IOException e)
      {
        throw new MissingResourceException("Resource file loading error", null, null);
      }
    }
  }

  /** Buffer des icônes en cours d'utilisation. */
  private static final Map<String, ImageIcon> ICONS = new HashMap<String, ImageIcon>();

  /**
   * Classe utilitaire : ne pas instancier.
   */
  private ResourceUtils()
  {
    // Rien de spécifique...
  }

  /**
   * Renvoi le code mnémonique internationalisé correspondant à une clé.
   * 
   * @param pCle Clé identifiant le code recherché.
   * @return Code mnémonique correspondant (ou première lettre de la clé si non trouvée).
   */
  static char getI18NChar(final String pCle)
  {
    assert pCle != null;

    final String v = PROPERTIES.getProperty(pCle, pCle);
    if (v.length() >= 1)
    {
      return v.charAt(0);
    }

    LOGGER.warning("Char resource [" + pCle + "] not found");
    return 0;
  }

  /**
   * Renvoi la chaine de caractère internationalisée correspondant à une clé.
   * 
   * @param pCle Clé identifiant la chaine recherchée.
   * @return Chaine de caractère correspondante (ou clé si non trouvée).
   */
  static String getI18NString(final String pCle)
  {
    assert pCle != null;

    String res = PROPERTIES.getProperty(pCle);
    if (res == null)
    {
      res = pCle;
      LOGGER.warning("String resource [" + pCle + "] not found");
    }

    return res;
  }

  /**
   * Renvoi l'icône correspondant à une clé.
   * 
   * @param pCle Clé identifiant l'icône recherchée.
   * @return Icône correspondante (ou null si elle est introuvable).
   */
  static synchronized ImageIcon getImageIcon(final String pCle)
  {
    assert pCle != null;

    ImageIcon res = ICONS.get(pCle);
    if (res == null)
    {
      final URL url = ResourceUtils.class.getResource(pCle);
      if (url == null)
      {
        LOGGER.warning("ImageIcon [" + pCle + "] not found");
      }
      else
      {
        res = new ImageIcon(ResourceUtils.class.getResource(pCle));
        ICONS.put(pCle, res);
      }
    }
    return res;
  }
}
