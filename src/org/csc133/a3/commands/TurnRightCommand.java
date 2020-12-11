package org.csc133.a3.commands;

import com.codename1.ui.events.ActionEvent;
import org.csc133.a3.gameobjects.GameObjectCollection;

/**
 * The type Turn right command.
 */
public class TurnRightCommand extends ControlGameObjectCommand {
    /**
     * Instantiates a new Turn right command.
     *
     * @param command              the string describing the command
     * @param gameObjectCollection the game object collection containing the player helicopter
     */
    public TurnRightCommand(String command, GameObjectCollection gameObjectCollection) {
        super(command, gameObjectCollection);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        increaseHeading();
    }
}