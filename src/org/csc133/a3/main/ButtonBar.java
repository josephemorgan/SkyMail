package org.csc133.a3.main;

import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.FontImage;
import com.codename1.ui.layouts.GridBagConstraints;
import com.codename1.ui.layouts.GridBagLayout;
import org.csc133.a3.commands.AcceleratePlayerCommand;
import org.csc133.a3.commands.DeceleratePlayerCommand;
import org.csc133.a3.commands.TurnLeftCommand;
import org.csc133.a3.commands.TurnRightCommand;

/**
 * A container designed to be placed at the bottom of the game, with the buttons needed to play the game.
 */
public class ButtonBar extends Container {
    private Button accelerateButton;
    private Button decelerateButton;
    private Button turnLeftButton;
    private Button turnRightButton;

    /**
     * Instantiates a new Button bar.
     */
    public ButtonBar() {
        super(new GridBagLayout());
        accelerateButton = new Button(FontImage.MATERIAL_KEYBOARD_ARROW_UP);
        decelerateButton = new Button(FontImage.MATERIAL_KEYBOARD_ARROW_DOWN);
        turnLeftButton = new Button(FontImage.MATERIAL_KEYBOARD_ARROW_LEFT);
        turnRightButton = new Button(FontImage.MATERIAL_KEYBOARD_ARROW_RIGHT);

        buildLayout();
    }

    private void buildLayout() {
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridy = 0;

        c.gridx = 0;
        add(c, accelerateButton);
        c.gridx = 1;
        add(c, decelerateButton);
        c.gridx = 2;
        add(c, turnLeftButton);
        c.gridx = 3;
        add(c, turnRightButton);
    }

    /**
     * Add listeners.
     *
     * @param accel the command to accelerate the PlayerHelicopter
     * @param decel the command to decelerate the PlayerHelicopter
     * @param lTurn the command to turn the PlayerHelicopter left
     * @param rTurn the command to turn the PlayerHelicopter right
     */
    public void addListeners(AcceleratePlayerCommand accel, DeceleratePlayerCommand decel, TurnLeftCommand lTurn, TurnRightCommand rTurn) {
        accelerateButton.addActionListener(accel);
        decelerateButton.addActionListener(decel);
        turnLeftButton.addActionListener(lTurn);
        turnRightButton.addActionListener(rTurn);
    }
}
