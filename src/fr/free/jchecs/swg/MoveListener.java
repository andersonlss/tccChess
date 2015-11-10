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

import java.util.EventListener;

/**
 * Interface présentée par un objet à l'écoute des mouvements.
 * 
 * @author David Cotton
 */
interface MoveListener extends EventListener
{
  /**
   * Prend en charge la réception d'un évènement signalant un mouvement.
   * 
   * @param pEvent Evènement signalant le mouvement.
   */
  void moved(final MoveEvent pEvent);
}
