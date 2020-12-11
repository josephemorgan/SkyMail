package org.csc133.a3.gameobjects;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;

import java.util.HashMap;

/**
 * The type Game object.
 */
public abstract class GameObject implements ICollider {
    private final int size;
    private Location location;
    private int color;
    private int outlineColor = ColorUtil.BLACK;
    protected static final int COLLISION_BLOCK_TIMEOUT = 5000;
    protected HashMap<GameObject, Long> alreadyCollidedList;

    /**
     * Instantiates a new Game object.
     *
     * @param size     the size
     * @param location the location
     * @param color    the color
     */
    public GameObject(int size, Location location, int color) {
        this.size = size;
        this.location = location;
        this.color = color;
        alreadyCollidedList = new HashMap<>();
    }

    /**
     * Gets size.
     *
     * @return the size
     */
    public int getSize() {
        return size;
    }

    /**
     * Gets location.
     *
     * @return the location
     */
    public Location getLocation() {
        return new Location(this.location);
    }

    /**
     * Sets location.
     *
     * @param x The x component of location according to a cartesian coordinate system.
     * @param y The y component of the location according to cartesian coordinates.
     * @throws LocationOutOfBounds the location out of bounds
     */
    public void setLocation(int x, int y) throws LocationOutOfBounds {
        this.location = new Location(x, y);
    }

    /**
     * Sets location.
     *
     * @param l the l
     */
    public void setLocation(Location l) {
        setLocation(l.getX(), l.getY());
    }

    /**
     * Gets color.
     *
     * @return the color
     */
    public int getColor() {
        return color;
    }

    /**
     * Gets outline color.
     *
     * @return the outline color
     */
    public int getOutlineColor() {
        return outlineColor;
    }

    /**
     * Sets outline color.
     *
     * @param color the color
     */
    public void setOutlineColor(int color) {
        outlineColor = color;
    }

    /**
     * Sets color.
     *
     * @param newColor the new color
     */
    public void setColor(int newColor) {
        this.color = newColor;
    }

    /**
     * Checks if the bounding rectangle of one object overlaps the bounding rectangle of another object.
     *
     * @param otherObject the other object
     * @return T if objects overlap, F if they don't
     */
    @Override
    public boolean checkCollidesWith(GameObject otherObject) {
        return checkCollidesWith(otherObject.getLocation(), otherObject.getSize());
    }

    /**
     * Checks if the bounding rectangle of one object overlaps the bounding rectangle of an object with specified size
     * at specified location.
     *
     * @param location        the location that the other object is at
     * @param otherObjectSize the other object size
     * @return T if objects overlap, F if they don't
     */
    public boolean checkCollidesWith(Location location, int otherObjectSize) {
        double objectsXDistance = Math.abs(this.getLocation().getX() - location.getX());
        double objectsYDistance = Math.abs(this.getLocation().getY() - location.getY());

        return objectsXDistance < (this.getSize() / 2f) + (otherObjectSize / 2f) &&
                objectsYDistance < (this.getSize() / 2f) + (otherObjectSize / 2f);
    }

    @Override
    public void handleCollision(GameObject otherObject) {
        if (alreadyCollidedList.get(otherObject) != null &&
        System.currentTimeMillis() - alreadyCollidedList.get(otherObject) <= COLLISION_BLOCK_TIMEOUT) {
            return;
        } else {
            alreadyCollidedList.put(otherObject, System.currentTimeMillis());
            collideWithObject(otherObject);
            otherObject.collideWithObject(this);
        }
    }

    protected abstract void collideWithObject(GameObject otherObject);

    /**
     * Draw.
     *
     * @param g the g
     */
    public abstract void draw(Graphics g);

    /**
     * Place an <b>unplaced</b> object at a given location. Used to place object at initial location when initializing
     * game world.
     *
     * @param newLocation the new location
     */
    public void place(Location newLocation) {
        if (location == null) {
            location = newLocation;
        } else {
            throw new ObjectAlreadyPlaced("Attempted to place object after it has already been placed. Please try move() instead.");
        }
    }

    @Override
    public String toString() {
        String ret = "";
        ret = ret + "Location: " + this.getLocation().getX() + ", " + this.getLocation().getY() + " ";
        ret = ret + "Color: " + "[" + ColorUtil.red(this.color) + ", " + ColorUtil.green(this.color) + ", " + ColorUtil.blue(this.color) + "] ";
        ret = ret + "Size: " + this.getSize() + " ";
        return ret;
    }
}
