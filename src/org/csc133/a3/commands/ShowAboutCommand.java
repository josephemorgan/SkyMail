package org.csc133.a3.commands;

import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.events.ActionEvent;
import org.csc133.a3.main.GameWorld;

/**
 * The type Show about command.
 */
public class ShowAboutCommand extends Command {
    private GameWorld gw;

    /**
     * Instantiates a new Show about command.
     *
     * @param gw the Game World that will be paused while the dialog is shown.
     */
    public ShowAboutCommand(GameWorld gw) {
        super("About");
        this.gw = gw;
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        super.actionPerformed(evt);
        gw.pause();
        String attribution = "Background music by sawsquarenoise, downloaded from https://freemusicarchive.org/genre/Chiptune - CreativeCommons license with attribution \nOther sounds downloaded from https://www.zapsplat.com/?s=cry&post_type=music&sound-effect-category-id= - Standard License";
        Dialog.show("About", "Author: Joseph Morgan\nCourse: CSC133\nv 0.0.1\n" + attribution, new Command("Close"));
        gw.unpause();
    }
}
