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

import java.util.EventObject;

import fr.free.jchecs.core.Move;

/**
 * @author David Cotton
 */
final class MoveEvent extends EventObject
{
  /** Identifiant de la classe pour la sérialisation. */
  private static final long serialVersionUID = -558192316749148649L;

  /** Mouvement lié à l'évènement. */
  private final Move _move;

  /**
   * Instancie un nouvel évènement décrivant un mouvement.
   * 
   * @param pSource Objet émetteur de l'évènement (peut être à null).
   * @param pMouvement Mouvement lié à l'évènement.
   */
  MoveEvent(final Object pSource, final Move pMouvement)
  {
    super(pSource);

    assert pMouvement != null;
    _move = pMouvement;
  }

  /**
   * Renvoi le mouvement lié à l'évènement.
   * 
   * @return Mouvement lié.
   */
  Move getMove()
  {
    assert _move != null;
    return _move;
  }
}
