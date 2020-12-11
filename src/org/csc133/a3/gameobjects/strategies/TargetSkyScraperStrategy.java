package org.csc133.a3.gameobjects.strategies;

import org.csc133.a3.gameobjects.*;

/**
 * The type Target sky scraper strategy.
 */
public class TargetSkyScraperStrategy implements IStrategy {
    @Override
    public void invokeStrategy(NonPlayerHelicopter me, GameObjectCollection c) {
        SkyScraper nextSkyScraper = null;
        for (GameObject o : c) {
            if (o instanceof SkyScraper && ((SkyScraper) o).getSequenceNumber() == me.getNextSkyScraper()) {
                nextSkyScraper = (SkyScraper) o;
                break;
            }
        }
        if (nextSkyScraper == null) {
            return;
        }
        double headingTowardSkyScraper = Location.getHeadingToward(me.getLocation(), nextSkyScraper.getLocation());
        int currentHeading = me.getHeading();
        double differenceBetweenHeadings = headingTowardSkyScraper - currentHeading;

        // me.setStickAngle(differenceBetweenHeadings);
        me.setHeadingForStrategy(headingTowardSkyScraper);
    }
}
