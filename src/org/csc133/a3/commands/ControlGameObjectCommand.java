package org.csc133.a3.commands;

import com.codename1.ui.Command;
import org.csc133.a3.gameobjects.*;
import org.csc133.a3.gameobjects.strategies.TargetPlayerStrategy;
import org.csc133.a3.gameobjects.strategies.TargetSkyScraperStrategy;

/**
 * The MutateGameObject class is an abstract ancestor to any class that mutates game objects. The design motivations for
 * this class were twofold; 1) Adhere to the DRY principle, and 2) reduce the number of classes where non-copy
 * references to the GameObjectCollection are stored.
 *
 * @author Joe Morgan
 */
public abstract class ControlGameObjectCommand extends Command {
    private static final int HEADING_CHANGE_STEP = 15;
    private static final int SPEED_CHANGE_STEP = 1;
    private static final int HELICOPTER_DAMAGE_VALUE = 30;
    private static final int BIRD_DAMAGE_VALUE = 15;
    private final GameObjectCollection c;

    /**
     * Instantiates a new Control game object command.
     *
     * @param command the command
     * @param c       the c
     */
    public ControlGameObjectCommand(String command, GameObjectCollection c) {
        super(command);
        this.c = c;
    }

    /**
     * Reduces the heading of the Player object in GameObjectCollection by HEADING_CHANGE_STEP
     */
    protected void decreaseHeading() {
        PlayerHelicopter p = c.findPlayer();
        p.setStickAngle(p.getStickAngle() - HEADING_CHANGE_STEP);
    }

    /**
     * Increases the heading of the Player object in GameObjectCollection by HEADING_CHANGE_STEP
     */
    protected void increaseHeading() {
        PlayerHelicopter p = c.findPlayer();
        p.setStickAngle(p.getStickAngle() + HEADING_CHANGE_STEP);
    }

    /**
     * Increases the speed of the Player object in GameObjectCollection by SPEED_CHANGE_STEP
     */
    protected void accelerate() {
        PlayerHelicopter p = c.findPlayer();
        p.setSpeed(p.getSpeed() + SPEED_CHANGE_STEP);
    }

    /**
     * Decreases the speed of the Player object in GameObjectCollection by SPEED_CHANGE_STEP
     */
    protected void decelerate() {
        PlayerHelicopter p = c.findPlayer();
        p.setSpeed(p.getSpeed() - SPEED_CHANGE_STEP);
    }

    /**
     * Change strategies.
     */
    protected void changeStrategies() {
        for (GameObject o : c) {
            if (o instanceof NonPlayerHelicopter) {
                if (((NonPlayerHelicopter) o).getStrategyId() == 1) {
                    ((NonPlayerHelicopter) o).setStrategy(new TargetSkyScraperStrategy());
                    ((NonPlayerHelicopter) o).updateNextSkyScraper();
                } else if (((NonPlayerHelicopter) o).getStrategyId() == 2) {
                    ((NonPlayerHelicopter) o).setStrategy(new TargetPlayerStrategy());
                }
            }
        }
    }
}