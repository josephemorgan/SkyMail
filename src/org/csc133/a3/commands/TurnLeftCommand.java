package org.csc133.a3.commands;

import com.codename1.ui.events.ActionEvent;
import org.csc133.a3.gameobjects.GameObjectCollection;

/**
 * The type Turn left command.
 */
public class TurnLeftCommand extends ControlGameObjectCommand {
    /**
     * Instantiates a new Turn left command.
     *
     * @param command the command
     * @param gameObjectCollection       the GameObjectCollection containing the player helicopter.
     */
    public TurnLeftCommand(String command, GameObjectCollection gameObjectCollection) {
        super(command, gameObjectCollection);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        decreaseHeading();
    }
}
