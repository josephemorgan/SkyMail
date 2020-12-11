package org.csc133.a3.main;

import com.codename1.ui.*;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.GridBagConstraints;
import com.codename1.ui.layouts.GridBagLayout;
import com.codename1.ui.util.UITimer;
import org.csc133.a3.commands.*;
import org.csc133.a3.views.GlassCockpit;
import org.csc133.a3.views.MapView;

/**
 * An extension of Codenameone's Form class, serves as the main view of the game.
 */
public class Game extends Form implements Runnable {
    private final GameWorld gw;
    private final long timerTimeMillis = 20;
    private AcceleratePlayerCommand accelCommand;
    private DeceleratePlayerCommand decelCommand;
    private TurnLeftCommand lCommand;
    private TurnRightCommand rCommand;
    private ExitCommand exitCommand;
    private ChangeStrategyCommand strategyCommand;
    private ShowAboutCommand aboutCommand;
    private ShowHelpCommand helpCommand;

    /**
     * Instantiates a new Game.
     */
    public Game() {
        GridBagLayout gbl = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        Toolbar tb = new Toolbar();

        setToolbar(tb);
        setTitle("SkyMail 3000");
        setLayout(new BorderLayout());

        MapView map = new MapView();
        GlassCockpit hud = new GlassCockpit();
        ButtonBar buttons = new ButtonBar();

        add(BorderLayout.NORTH, hud);
        add(BorderLayout.CENTER, map);
        add(BorderLayout.SOUTH, buttons);

		// Calling show here even though we're calling it again later so that MapView knows how much room it's got.
        show();

        gw = new GameWorld(map, hud, hud.getGameClockComponent());
        gw.init();

        accelCommand = new AcceleratePlayerCommand("Accelerate", gw.getGameObjectCollection());
        decelCommand = new DeceleratePlayerCommand("Decelerate", gw.getGameObjectCollection());
        lCommand = new TurnLeftCommand("Turn Left", gw.getGameObjectCollection());
        rCommand = new TurnRightCommand("Turn Right", gw.getGameObjectCollection());
        exitCommand = new ExitCommand(gw);
        strategyCommand = new ChangeStrategyCommand(gw.getGameObjectCollection());
        aboutCommand = new ShowAboutCommand(gw);
        helpCommand = new ShowHelpCommand(gw);

        buttons.addListeners(accelCommand, decelCommand, lCommand, rCommand);

        UITimer timer = new UITimer(this);
        timer.schedule((int)timerTimeMillis, true, this);

        addKeyListener('a', accelCommand);
        addKeyListener('b', decelCommand);
        addKeyListener('l', lCommand);
        addKeyListener('r', rCommand);
        addKeyListener('x', exitCommand);

        tb.addCommandToSideMenu(strategyCommand);
        tb.addCommandToSideMenu(aboutCommand);
        tb.addCommandToSideMenu(helpCommand);
        tb.addCommandToSideMenu(exitCommand);
        show();
    }

    @Override
    public void run() {
        gw.tickClock(timerTimeMillis);
    }
}
