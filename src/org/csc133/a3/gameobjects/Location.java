package org.csc133.a3.gameobjects;

import com.codename1.ui.geom.Point;

import java.util.Random;

/**
 * Represents a Location in the game world. When used with reference to instances of GameObject, specifically indicates
 * the <i>center</i> of the game object.
 */
public class Location extends Point {
    private static final Random rand = new Random();

    /**
     * Instantiates a new Location at (0, 0)
     */
    public Location() {
        super(0, 0);
    }

    /**
     * Instantiates a new Location, copying from an existing Locationn
     *
     * @param copyFromLocation the location to copy from.
     */
    public Location(Location copyFromLocation) {
        super(copyFromLocation.getX(), copyFromLocation.getY());
    }

    /**
     * Instantiates a new Location at (x, y)
     *
     * @param x the x
     * @param y the y
     */
    public Location(int x, int y) {
        super(x, y);
    }

    @Override
    public boolean equals(Object secondLocation) {
        return super.equals(secondLocation);
    }

    /**
     * Used for drawing game objects; gets the Y coordinate of the upper-left hand corner of the rectangle of given size
     * centered at this's location.
     *
     * @param objectSize the object size
     * @return the anchor point y
     */
    public double getAnchorPointY(int objectSize) {
        return getY() - (objectSize / 2);
    }

     /**
     * Used for drawing game objects; gets the X coordinate of the upper-left hand corner of the rectangle of given size
     * centered at this's location.
     *
     * @param objectSize the object size
     * @return the anchor point x
     */
    public double getAnchorPointX(int objectSize) {
        return getX() - (objectSize / 2);
    }

    /**
     * Generate random location within a rectangle with with two given corners.
     *
     * @param minLocation The corner of the bounding rectangle with the the smallest possible (x, y).
     * @param maxLocation The corner of the bounding rectangle with the the largest possible (x, y).
     * @return the location
     */
    public static Location generateRandomLocationWithinBounds(Location minLocation, Location maxLocation) {
        Location toReturn = null;
        while (toReturn == null) {
            try {
                toReturn = new Location(
                        (rand.nextInt(maxLocation.getX() - minLocation.getX()) + minLocation.getX()),
                        (rand.nextInt(maxLocation.getY() - minLocation.getY()) + minLocation.getY()));
            } catch (LocationOutOfBounds e){}
        }
        return toReturn;
    }

    /**
     * Generate a random location within two bounds that is a minimum distance from another object.
     *
     * @param minLocation     The lower bound for location.
     * @param maxLocation     The upper bound for location.
     * @param objectLocation  The location of the object that will be avoided.
     * @param avoidanceRadius The radius with respect to the object within which the random location won't be generated.
     * @return The randomly generated location.
     */
    public static Location generateRandomLocationWithinBounds(Location minLocation, Location maxLocation, Location objectLocation, int avoidanceRadius) {
        Location toReturn;

        do {
            double avoidanceAngle = rand.nextDouble() * 2 * Math.PI;
            toReturn = new Location((int)(avoidanceRadius * Math.cos(avoidanceAngle)),
                    (int)(avoidanceRadius * Math.sin(avoidanceAngle)));
            toReturn.add(objectLocation);
        } while (!checkWithinBounds(toReturn, minLocation, maxLocation));

        return toReturn;
    }

    /**
     * Adds the x and y values of one location to the x and y values of another.
     *
     * @param otherLocation the other location
     * @return The location after adding
     */
    public Location add(Location otherLocation) {
        setX(getX() + otherLocation.getX());
        setY(getY() + otherLocation.getY());
        return this;
    }

    /**
     * Checks whether a location is within set bounds
     *
     * @param locationToCheck the location to check
     * @param minLocation     A location representing the minimum bound
     * @param maxLocation     A location representing the maximum bound
     * @return True if locationToCheck is within bounds, false if it isn't
     */
    public static boolean checkWithinBounds(Location locationToCheck, Location minLocation, Location maxLocation) {
        if (locationToCheck.getX() < minLocation.getX()
                || locationToCheck.getX() > maxLocation.getX()
                || locationToCheck.getY() < minLocation.getY()
                || locationToCheck.getY() > maxLocation.getY()) {
            return false;
        }
        return true;
    }

    /**
     * Gets heading toward.
     *
     * @param a the a
     * @param b the b
     * @return the heading toward
     */
    public static double getHeadingToward(Location a, Location b) {
        int xDifference = b.getX() - a.getX();
        int yDifference = b.getY() - a.getY();

        double headingTowardB = Math.toDegrees(Math.atan2(yDifference, xDifference));
        if (headingTowardB < 0)
            headingTowardB += 360;
        if (headingTowardB > 360)
            headingTowardB %= 360;
        return headingTowardB;
    }
}
