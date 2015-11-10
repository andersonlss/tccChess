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

import static fr.free.jchecs.core.Game.State.IN_PROGRESS;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import fr.free.jchecs.ai.Engine;
import fr.free.jchecs.ai.EngineFactory;
import fr.free.jchecs.core.Game;
import fr.free.jchecs.core.Move;
import fr.free.jchecs.core.MoveGenerator;
import fr.free.jchecs.core.Player;

/**
 * Applet de démonstration permettant de tester la présence de la bonne version de Java SE, par le
 * mécanisme standard d'installation du plugin, avant de lancer un déploiement par Java Web Start.
 * <p>
 * Parametres de l'applet :
 * </p>
 * <ul>
 * <li>size : longueur, en pixels, d'un côté d'une case du plateau (facultatif, 32 par défaut).</li>
 * <li>href : URL cible pour le déploiement par WebStart (obligatoire).</li>
 * </ul>
 * 
 * @author David Cotton
 */
public final class DemoApplet extends JApplet
{
  /** Identifiant de la classe pour la sérialisation. */
  private static final long serialVersionUID = 8099449360271948498L;

  /** Processus gérant le corps de l'Applet. */
  private transient GameThread _gameThread;

  /**
   * Constructeur par défaut.
   */
  public DemoApplet()
  {
    // Rien de spécifique...
  }

  /**
   * Initialisation de l'applet.
   */
  @Override
  public void init()
  {
    final Game partie = new Game();
    Player joueur = partie.getPlayer(true);
    joueur.setName("Human");
    joueur.setEngine(null);
    joueur = partie.getPlayer(false);
    joueur.setName("Computer");
    final Engine moteur = EngineFactory.newInstance();
    moteur.setOpeningsEnabled(false);
    joueur.setEngine(moteur);

    final BoardUI plateau = new BoardUI(partie, new PieceUI());
    plateau.setCoordinatesPainted(false);

    final String paramSize = getParameter("size");
    int tailleCase = 32;
    if (paramSize != null)
    {
      try
      {
        tailleCase = Integer.parseInt(paramSize);
      }
      catch (final NumberFormatException e)
      {
        // On reste sur la valeur par défaut...
      }
    }
    plateau.setCellSideLength(tailleCase);

    final Container fond = getContentPane();

    final JPanel haut = new JPanel(new BorderLayout());
    haut.setBackground(Color.WHITE);
    final JButton webStart = new JButton("Install now !");
    webStart.setToolTipText("Downloads and installs jChecs by Java Web Start");
    webStart.setEnabled(false);
    final String paramHRef = getParameter("href");
    URL hrefTmp = null;
    if (paramHRef != null)
    {
      try
      {
        hrefTmp = new URL(getCodeBase(), paramHRef);
        webStart.setEnabled(true);
      }
      catch (final MalformedURLException e)
      {
        webStart.setText("Invalid url");
      }
    }
    final URL href = hrefTmp;
    haut.add(webStart, BorderLayout.WEST);
    final JLabel infos = new JLabel();
    infos.setHorizontalAlignment(SwingConstants.CENTER);
    haut.add(infos, BorderLayout.CENTER);
    fond.add(haut, BorderLayout.NORTH);

    fond.add(plateau, BorderLayout.CENTER);

    _gameThread = new GameThread(partie, plateau, infos);
    webStart.addActionListener(new ActionListener()
    {
      /**
       * Prend en charge les boutons.
       * 
       * @param pEvnt Evènement signalant le déclenchement d'un bouton.
       */
      public void actionPerformed(final ActionEvent pEvnt)
      {
        if ((pEvnt.getSource() == webStart) && (href != null))
        {
          getAppletContext().showDocument(href);
        }
      }
    });
  }

  /**
   * Démarrage de l'applet.
   */
  @Override
  public void start()
  {
    new Thread(_gameThread).start();
  }

  /**
   * Arrêt de l'applet.
   */
  @Override
  public void stop()
  {
    _gameThread.stop();
  }

  /**
   * Processus gérant l'éxécution de l'Applet.
   */
  private static final class GameThread implements MoveListener, Runnable
  {
    /** Composant affichant le plateau de jeu. */
    private final Game _game;

    /** Composant affichant le plateau de jeu. */
    private final BoardUI _boardUI;

    /** Composant affichant les infos. */
    private final JLabel _infos;

    /** Flag permettant d'arrèter le processus. */
    private volatile boolean _running;

    /** Dernier mouvement reçu (peut être à null). */
    private Move _lastMove;

    /**
     * Instancie un nouveau processus.
     * 
     * @param pPartie Decription de la partie.
     * @param pPlateau Composant affichant le plateau.
     * @param pInfos Composant affichant les infos.
     */
    GameThread(final Game pPartie, final BoardUI pPlateau, final JLabel pInfos)
    {
      _game = pPartie;
      _boardUI = pPlateau;
      _infos = pInfos;

      _boardUI.addMoveListener(this);
    }

    /**
     * Tient compte des évènements signalant un mouvement.
     * 
     * @param pEvent Evènement signalant un mouvement.
     */
    public synchronized void moved(final MoveEvent pEvent)
    {
      _lastMove = pEvent.getMove();
      notifyAll();
    }

    /**
     * Lance le processus gérant l'Applet.
     * 
     * @see Runnable#run()
     */
    public void run()
    {
      _running = true;

      while (_running)
      {
        while (_running && (_game.getState() == IN_PROGRESS))
        {
          _lastMove = null;

          final MoveGenerator plateau = _game.getBoard();
          final Player joueur = _game.getPlayer(plateau.isWhiteActive());
          _infos.setText(joueur.getName() + " playing...");
          final Engine ia = joueur.getEngine();
          if (ia == null)
          {
            _boardUI.setEnabled(true);
            waitForMove();
          }
          else
          {
            _boardUI.setEnabled(false);
            _lastMove = ia.getMoveFor(plateau);
          }

          if (_lastMove != null)
          {
            _game.moveFromCurrent(_lastMove);
          }
        }
        _boardUI.setEnabled(false);
        try
        {
          Thread.sleep(250);
        }
        catch (final InterruptedException e)
        {
          // Peut être ignoré dans ce cas.
        }
        if (_running)
        {
          switch (_game.getState())
          {
            case BLACK_MATES :
              _infos.setText(_game.getPlayer(false).getName() + " wins.");
              break;
            case WHITE_MATES :
              _infos.setText(_game.getPlayer(true).getName() + " wins.");
              break;
            case STALEMATE :
              _infos.setText("Stalemate.");
              break;
            case DRAWN_BY_50_MOVE_RULE :
            case DRAWN_BY_TRIPLE_REPETITION :
              _infos.setText("Drawn by repetition.");
              break;
            default :
              // Ne rien faire...
          }
        }
        else
        {
          _infos.setText("Applet stopped");
        }
      }
    }

    /**
     * Arrète le processus.
     */
    void stop()
    {
      _running = false;
    }

    /**
     * Attend que le joueur humain ait fait un mouvement.
     */
    private synchronized void waitForMove()
    {
      try
      {
        while (_lastMove == null)
        {
          wait();
        }
      }
      catch (final InterruptedException e)
      {
        // Peut être ignoré dans ce cas.
      }
    }
  }
}
