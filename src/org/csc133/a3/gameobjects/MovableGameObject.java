package org.csc133.a3.gameobjects;

import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import org.csc133.a3.main.GameWorld;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 * The abstract type representing a GameObject that can be moved.
 */
public abstract class MovableGameObject extends GameObject {
    private static final int MAX_HEADING = 259;
    private static final int MIN_REASONABLE_SPEED = 5;
    private static final int MAX_REASONABLE_SPED = 15;
    private static final Random rand = new Random();
    private String frameBaseName = "";
    private int numberOfFrames;
    private int currentFrame = 0;
    protected ArrayList<Image> spriteFrames;
    private int heading;
    private int speed;

    /**
     * Instantiates a new Movable game object.
     *
     * @param size     the size
     * @param location the location
     * @param color    the color
     * @param heading  the heading
     * @param speed    the speed
     */
    public MovableGameObject(int size, Location location, int color, int heading, int speed) {
        super(size, location, color);
        this.heading = heading;
        this.speed = speed;
    }

    protected void initSprites(String frameBasePath, int numberOfFrames) {
        this.frameBaseName = frameBasePath;
        this.numberOfFrames = numberOfFrames;
        spriteFrames = new ArrayList<>();
        for (int i = 0; i < numberOfFrames; ++i) {
            String path = "/" + frameBasePath + i + ".png";
            try {
                spriteFrames.add(Image.createImage(path));
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Error creating image for file: " + path);
                System.exit(1);
            }
        }
    }

    /**
     * Generate random heading from 0 to 360.
     *
     * @return an int representing random heading
     */
    public static int generateRandomHeadingWithinBounds() {
        return rand.nextInt(MAX_HEADING);
    }

    /**
     * Generate random speed within bounds.
     *
     * @return an int representing a reandom speed
     */
    public static int generateRandomSpeedWithinBounds() {
        return rand.nextInt(MAX_REASONABLE_SPED - MIN_REASONABLE_SPEED) + MIN_REASONABLE_SPEED;
    }

    /**
     * Moves the GameObject with respect to its heading and speed, as well as the elapsed time since the last move call.
     * Also handles rebounding against the bounds of the game world.
     *
     * @param elapsedTimeMillis The elapsed time in milliseconds since the last call to move();
     */
    public void move(long elapsedTimeMillis) {
        Location minLocation = GameWorld.getMinLocation();
        Location maxLocation = GameWorld.getMaxLocation();

        int deltaX = (int)Math.round((elapsedTimeMillis / 20L) * Math.cos(Math.toRadians(this.heading)) * this.getSpeed());
        int deltaY = (int)Math.round((elapsedTimeMillis / 20L) * Math.sin(Math.toRadians(this.heading)) * this.getSpeed());
        Location newLocation = new Location(getLocation().getX() + deltaX, getLocation().getY() + deltaY);

        if (newLocation.getX() < minLocation.getX()) {
            int incidentAngle = 180 - heading;
            heading = incidentAngle;
        }
        if (newLocation.getX() > maxLocation.getX()) {
            int incidentAngle = heading;
            heading = 180 - incidentAngle;
        }
        if (newLocation.getY() < minLocation.getY()) {
            int incidentAngle = 90 - heading;
            heading = 270 + incidentAngle;
        }
        if (newLocation.getY() > maxLocation.getY()) {
            int incidentAngle = 90 - heading;
            heading = 270 + incidentAngle;
        }

        this.setLocation(this.getLocation().getX() + deltaX, this.getLocation().getY() + deltaY);
    }

    /**
     * Sets speed.
     *
     * @param speed the speed
     */
    protected void setSpeed(int speed) {
        this.speed = speed;
    }

    /**
     * Gets speed.
     *
     * @return the speed
     */
    public int getSpeed() {
        return this.speed;
    }

    /**
     * Gets heading.
     *
     * @return the heading
     */
    public int getHeading() {
        return this.heading;
    }

    /**
     * Sets heading.
     *
     * @param heading the heading
     */
    protected void setHeading(int heading) {
        while (heading < 0) {
            heading += 360;
        }
        heading %= 360;
        this.heading = heading;
    }

    @Override
    public String toString() {
        String ret = super.toString();
        ret = ret + "Heading: " + this.getHeading() + " ";
        ret = ret + "Speed: " + this.getSpeed() + " ";
        return ret;
    }

    @Override
    public void draw(Graphics g) {
        int drawFromX = (int) Math.round(getLocation().getAnchorPointX(getSize()));
        int drawFromY = (int) Math.round(getLocation().getAnchorPointY(getSize()));
        int size = getSize();
        try {
            Image imageToDraw = spriteFrames.get(currentFrame);
            g.drawImage(imageToDraw.rotate(getHeading() - 90), drawFromX, drawFromY, size, size);
        } catch (Exception e) {

        }


        ++currentFrame;
        currentFrame %= numberOfFrames;
    }

    /**
     * A function for use by NonPlayerHelicopter strategies, sets the heading explicitly to bypass control stick.
     *
     * @param newHeading the new heading
     */
    public void setHeadingForStrategy(double newHeading) {
        this.heading = (int) Math.round(newHeading);
    }
}