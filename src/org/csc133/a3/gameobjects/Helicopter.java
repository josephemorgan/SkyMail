package org.csc133.a3.gameobjects;

import com.codename1.ui.Graphics;
import com.codename1.ui.Image;

import java.io.IOException;
import java.util.ArrayList;

/**
 * The type Helicopter.
 */
public abstract class Helicopter extends MovableGameObject implements ISteerable {
    private static final int DAMAGE_FROM_HELICOPTER = 10;
    private static final int DAMAGE_FROM_BIRD = 5;
    protected final double fuelConsumptionRate;
    protected double fuelLevel;
    private double stickAngle;
    private int maximumSpeed;
    private double damageLevel;
    private ArrayList<Image> baseSpriteFrames = new ArrayList<>();
    private ArrayList<Image> altSpriteFrames = new ArrayList<>();

    protected final long indicateDamageDuration = 3000;
    protected boolean indicateDamage = false;
    protected long indicateDamageStartTime;
    protected int lastSkyScraperReached;

    /**
     * Instantiates a new Helicopter.
     *
     * @param size     the size
     * @param location the location
     * @param color    the color
     * @param heading  the heading
     * @param speed    the speed
     */
    public Helicopter(int size, Location location, int color, int heading, int speed) {
        super(size, location, color, heading, speed);
        this.stickAngle = 0;
        maximumSpeed = 10;
        indicateDamage = false;
        this.fuelLevel = 10000;
        this.fuelConsumptionRate = 10;
    }

    /**
     * Gets maximum speed.
     *
     * @return the maximum speed
     */
    protected int getMaximumSpeed() {
        return maximumSpeed;
    }

    /**
     * Sets maximum speed.
     *
     * @param maximumSpeed the maximum speed
     */
    protected void setMaximumSpeed(int maximumSpeed) {
        this.maximumSpeed = maximumSpeed;
    }

    @Override
    public void changeHeading() {
        if (stickAngle < -5) {
            this.setHeading(this.getHeading() - 5);
            stickAngle += 5;
        } else if (stickAngle > 5) {
            this.setHeading(this.getHeading() + 5);
            stickAngle -= 5;
        } else {
            this.setHeading(this.getHeading() + (int)stickAngle);
            stickAngle -= stickAngle;
        }
    }

    @Override
    public void setSpeed(int newSpeed) {
        newSpeed = (Math.min(newSpeed, this.maximumSpeed));
        if (newSpeed < 0) {
            newSpeed = 0;
        }
        super.setSpeed(newSpeed);
    }

    /**
     * Gets stick angle.
     *
     * @return the stick angle
     */
    public double getStickAngle() {
        return stickAngle;
    }

    /**
     * Sets stick angle.
     *
     * @param stickAngle the stick angle
     */
    public void setStickAngle(double stickAngle) {
        if (stickAngle <= -40) {
            this.stickAngle = -40;
        } else if (stickAngle >= 40) {
            this.stickAngle = 40;
        } else {
            this.stickAngle = stickAngle;
        }
    }

    /**
     * Gets damage level.
     *
     * @return the damage level
     */
    public double getDamageLevel() {
        return this.damageLevel;
    }

    /**
     * Reset damage level.
     */
    protected void resetDamageLevel() {
        damageLevel = 0;
        setMaximumSpeed(10);
    }

    /**
     * Take damage.
     *
     * @param amountOfDamage the amount of damage
     */
    public void takeDamage(int amountOfDamage) {
        damageLevel += amountOfDamage;
        indicateDamage = true;
        indicateDamageStartTime = System.currentTimeMillis();
        spriteFrames = altSpriteFrames;
        setMaximumSpeed((100 - (int)damageLevel) / 10);
        setSpeed(getSpeed());
    }

    @Override
    public String toString() {
        String ret = super.toString();
        ret = "Helicopter: " + ret;
        ret = ret + "Max Speed: " + this.maximumSpeed + " ";
        ret = ret + "Stick Position: " + this.getStickAngle() + " ";
        return ret;
    }

    @Override
    public void move(long elapsedTimeMillis) {
        this.changeHeading();
        setFuelLevel(getFuelLevel() - ((elapsedTimeMillis / 10) * getFuelConsumptionRate()));
        super.move(elapsedTimeMillis);
    }

    @Override
    protected void collideWithObject(GameObject otherObject) {
        if (otherObject instanceof Helicopter) {
            takeDamage(DAMAGE_FROM_HELICOPTER);
        } else if (otherObject instanceof Bird) {
            takeDamage(DAMAGE_FROM_BIRD);
        } else if (otherObject instanceof SkyScraper) {
            if (((SkyScraper) otherObject).getSequenceNumber() == (lastSkyScraperReached + 1)) {
                ++lastSkyScraperReached;
            }
        } else if (otherObject instanceof RefuelingBlimp) {
            if (((RefuelingBlimp) otherObject).getCapacity() > 0) {
                this.setFuelLevel(getFuelLevel() + (((RefuelingBlimp) otherObject).getCapacity()));
            }
        }
    }

    /**
     * Sets fuel level.
     *
     * @param updatedFuelLevel the updated fuel level
     */
    public void setFuelLevel(double updatedFuelLevel) {
        this.fuelLevel = updatedFuelLevel;
    }

    /**
     * Gets fuel level.
     *
     * @return the fuel level
     */
    public double getFuelLevel() {
        return this.fuelLevel;
    }

    /**
     * Gets fuel consumption rate.
     *
     * @return the fuel consumption rate
     */
    public double getFuelConsumptionRate() {
        return this.fuelConsumptionRate;
    }

    @Override
    protected void initSprites(String frameBasePath, int numberOfFrames) {
        super.initSprites(frameBasePath, numberOfFrames);
        baseSpriteFrames = spriteFrames;
        for (int i = 0; i < numberOfFrames; ++i) {
            String path = "/" + frameBasePath + "alt" + i + ".png";
            try {
                altSpriteFrames.add(Image.createImage(path));
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Error creating sprite for image at: " + path);
                System.exit(1);
            }
        }
    }

    @Override
    public void draw(Graphics g) {
        super.draw(g);
        if (System.currentTimeMillis() - indicateDamageStartTime > indicateDamageDuration) {
            spriteFrames = baseSpriteFrames;
        }
    }
}
