package org.csc133.a3.commands;

import com.codename1.ui.events.ActionEvent;
import org.csc133.a3.gameobjects.GameObjectCollection;

/**
 * The Decelerate player command.
 */
public class DeceleratePlayerCommand extends ControlGameObjectCommand {

    public DeceleratePlayerCommand(String command, GameObjectCollection g) {
        super(command, g);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        decelerate();
    }
}
