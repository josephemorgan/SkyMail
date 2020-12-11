package org.csc133.a3.commands;

import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.events.ActionEvent;
import org.csc133.a3.main.GameWorld;

/**
 * The Exit command. Used to exit a game.
 */
public class ExitCommand extends Command {
    private GameWorld gw;

    private class Confirm extends Command {
        private GameWorld gw;
        public Confirm(GameWorld gw) {
            super("Confirm");
            this.gw = gw;
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            super.actionPerformed(evt);
            gw.exit();
        }
    }

    /**
     * Instantiates a new Exit command.
     *
     * @param gw The Game World to be terminating
     */
    public ExitCommand(GameWorld gw) {
        super("Exit");
        this.gw = gw;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        gw.pause();
        Dialog.show("Exit", "Really exit?", new Confirm(gw), new Command("No"));
        gw.unpause();
    }
}
