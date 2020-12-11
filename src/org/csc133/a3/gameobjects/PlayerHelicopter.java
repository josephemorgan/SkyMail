package org.csc133.a3.gameobjects;

/**
 * The singleton type Player helicopter.
 */
public class PlayerHelicopter extends Helicopter {
    private static final String BASE_FRAME_NAME = "phframe";
    private static final String BASE_FRAME_NAME_ALT = "phframealt";
    private static final int  NUMBER_OF_FRAMES = 2;
    private static int DEFAULT_SIZE = 80;
    private static int DEFAULT_COLOR = 0x00FF00;
    private static int DEFAULT_HEADING = 90;
    private static int DEFAULT_SPEED = 1;
    private static PlayerHelicopter singleInstance = null;
    private int numberOfLives;

    private PlayerHelicopter(Location location, int numberOfLives) {
        super(DEFAULT_SIZE, location, DEFAULT_COLOR, DEFAULT_HEADING, DEFAULT_SPEED);
        this.numberOfLives = numberOfLives;
        this.lastSkyScraperReached = 0;
        initSprites(BASE_FRAME_NAME, NUMBER_OF_FRAMES);
    }

    /**
     * If player helicopter doesn't exist yet, creates one with specified initial attributes. Otherwise, returns the
     * instance of the PlayerHelicopter.
     *
     * @param location      the location
     * @param numberOfLives the number of lives
     * @return the instance
     */
    public static synchronized PlayerHelicopter getInstance(Location location, int numberOfLives) {
        if (singleInstance == null)
            return new PlayerHelicopter(location, numberOfLives);
        else
            return singleInstance;
    }

    @Override
    public String toString() {
        String ret = super.toString();
        ret = ret + "Fuel Level: " + this.getFuelLevel() + " ";
        ret = ret + "Damage Level: " + this.getDamageLevel() + " ";
        return ret;
    }

    /**
     * Gets number of lives.
     *
     * @return the number of lives
     */
    public int getNumberOfLives() {
        return numberOfLives;
    }

    /**
     * Decrement lives.
     */
    public void decrementLives() {
        --numberOfLives;
        resetDamageLevel();
    }

    /**
     * Gets last sky scraper reached.
     *
     * @return the last sky scraper reached
     */
    public int getLastSkyScraperReached() {
        return lastSkyScraperReached;
    }

    @Override
    public void takeDamage(int amountOfDamage) {
        super.takeDamage(amountOfDamage);
    }
}
