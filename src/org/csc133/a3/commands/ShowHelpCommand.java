package org.csc133.a3.commands;

import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.events.ActionEvent;
import org.csc133.a3.main.GameWorld;

/**
 * The type Show help command.
 */
public class ShowHelpCommand extends Command {
    private GameWorld gw;

    /**
     * Instantiates a new Show help command.
     *
     * @param gw the Game World that will be paused while the dialog is shown.
     */
    public ShowHelpCommand (GameWorld gw) {
        super("Show Help");
        this.gw = gw;
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        super.actionPerformed(evt);
        gw.pause();
        Dialog.show("Keybindings",
                "a - Accellerate\n" +
                        "b - Brake\n" +
                        "l - Turn Stick Left\n" +
                        "r - Turn Stick Right\n" +
                        "n - Collide with NPH\n" +
                        "s - Collide with Skyscraper\n" +
                        "e - Collide with blimp\n" +
                        "g - Collide with bird\n" +
                        "x - Exit",
                new Command("Close"));
        gw.unpause();
    }
}
