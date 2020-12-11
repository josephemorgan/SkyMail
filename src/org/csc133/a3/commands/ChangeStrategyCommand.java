package org.csc133.a3.commands;

import com.codename1.ui.events.ActionEvent;
import org.csc133.a3.gameobjects.GameObjectCollection;

/**
 * The type Change strategy command.
 */
public class ChangeStrategyCommand extends ControlGameObjectCommand {
    /**
     * Instantiates a new Change strategy command.
     *
     * @param c The Game Object Collection containing the Non Player Helicopters
     */
    public ChangeStrategyCommand(GameObjectCollection c) {
        super("Change Strategy", c);
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        super.actionPerformed(evt);
        changeStrategies();
    }
}
