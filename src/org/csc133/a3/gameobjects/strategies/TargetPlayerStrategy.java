package org.csc133.a3.gameobjects.strategies;

import org.csc133.a3.gameobjects.GameObjectCollection;
import org.csc133.a3.gameobjects.Location;
import org.csc133.a3.gameobjects.NonPlayerHelicopter;

/**
 * The type Target player strategy.
 */
public class TargetPlayerStrategy implements IStrategy {

    @Override
    public void invokeStrategy(NonPlayerHelicopter me, GameObjectCollection c) {
        Location nphLocation = me.getLocation();
        Location playerLocation = c.findPlayer().getLocation();

        double headingTowardPlayer = Location.getHeadingToward(nphLocation, playerLocation);
        int currentHeading = me.getHeading();
        double differenceBetweenHeadings = headingTowardPlayer - currentHeading;

        // me.setStickAngle(differenceBetweenHeadings);

        me.setHeadingForStrategy(headingTowardPlayer);
    }
}
