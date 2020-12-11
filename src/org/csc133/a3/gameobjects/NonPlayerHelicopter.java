package org.csc133.a3.gameobjects;

import org.csc133.a3.gameobjects.strategies.IStrategy;
import org.csc133.a3.gameobjects.strategies.TargetPlayerStrategy;
import org.csc133.a3.gameobjects.strategies.TargetSkyScraperStrategy;

import java.util.HashMap;

/**
 * A type representing a non-player helicopter.
 */
public class NonPlayerHelicopter extends Helicopter{
    private static final String FRAME_BASE_NAME = "nphframe";
    private static final int NUMBER_OF_FRAMES = 2;
    private static int DEFAULT_SIZE = 80;
    private static int DEFAULT_COLOR = 0x535353;
    private IStrategy strategy = null;
    private GameObjectCollection c;

    /**
     * Instantiates a new Non player helicopter.
     *
     * @param location the location
     * @param gameObjectCollection        the gameObjectCollection
     */
    public NonPlayerHelicopter(Location location, GameObjectCollection gameObjectCollection) {
        super(DEFAULT_SIZE, location, DEFAULT_COLOR, 0, 2);
        setMaximumSpeed(5);
        this.c = gameObjectCollection;
        initSprites(FRAME_BASE_NAME, NUMBER_OF_FRAMES);
    }

    @Override
    public void move(long elapsedTimeMillis) {
        strategy.invokeStrategy(this, c);
        super.move(elapsedTimeMillis);
    }

    /**
     * Gets next unvisited Skyscraper.
     *
     * @return the next skyscraper
     */
    public int getNextSkyScraper() {
        return lastSkyScraperReached + 1;
    }

    /**
     * Increments the Helicopter state to target the next unvisited skyscraper.
     */
    public void updateNextSkyScraper() {
        ++lastSkyScraperReached;
    }

    /**
     * Sets strategy.
     *
     * @param strategy the new strategy that the NPH will use
     */
    public void setStrategy(IStrategy strategy) {
        this.strategy = strategy;
    }

    /**
     * Gets strategy id.
     *
     * @return the strategy id
     */
    public int getStrategyId() {
        if (strategy instanceof TargetPlayerStrategy)
            return 1;
        else if (strategy instanceof TargetSkyScraperStrategy)
            return 2;
        else
            return 0;
    }
}