package org.csc133.a3.commands;

import com.codename1.ui.events.ActionEvent;
import org.csc133.a3.gameobjects.GameObjectCollection;

/**
 * Command to accelerate Player Helicopter
 */
public class AcceleratePlayerCommand extends ControlGameObjectCommand {

    /**
     * Instantiates a new Accelerate player command.
     *
     * @param command Command Title string
     * @param g       Collection of Game Objects containing player helicopter
     */
    public AcceleratePlayerCommand(String command, GameObjectCollection g) {
        super(command, g);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        accelerate();
    }
}
