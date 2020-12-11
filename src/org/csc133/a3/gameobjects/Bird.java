package org.csc133.a3.gameobjects;

import com.codename1.charts.util.ColorUtil;

/**
 * The type Bird.
 */
public class Bird extends MovableGameObject {
    private static final String FRAME_BASE_NAME = "birdframe";
    private static final int NUMBER_OF_FRAMES = 3;
    private static final int BIRD_SIZE = 60;
    private static final int BIRD_COLOR = ColorUtil.rgb(32, 32, 32);

    /**
     * Instantiates a new Bird.
     *
     * @param location Where the bird will be placed
     */
    public Bird(Location location) {
        super(BIRD_SIZE,
                location,
                BIRD_COLOR,
                MovableGameObject.generateRandomHeadingWithinBounds(),
                MovableGameObject.generateRandomSpeedWithinBounds());
        setOutlineColor(BIRD_COLOR);
        initSprites(FRAME_BASE_NAME, NUMBER_OF_FRAMES);
    }

    @Override
    public void setColor(int color) {
        throw new ObjectColorNotChangeable("Cannot change color of Bird object.\n");
    }

    @Override
    public void move(long elapsedTimeMillis) {
        int option = (int)(Math.random() * 3);
        int amountToChange = 0;

        if (option == 0) {
            amountToChange = 10;
        } else if (option == 1) {
            amountToChange = -10;
        }

        this.setHeading(this.getHeading() + amountToChange);
        super.move(elapsedTimeMillis);
    }

    @Override
    public String toString() {
        String ret = super.toString();
        ret = "Bird: " + ret;
        return ret;
    }

    @Override
    protected void collideWithObject(GameObject otherObject) {
        if (otherObject instanceof Helicopter) {
            super.handleCollision(otherObject);
        }
    }
}
