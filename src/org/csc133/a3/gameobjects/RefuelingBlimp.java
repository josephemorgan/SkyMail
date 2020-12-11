package org.csc133.a3.gameobjects;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Display;
import com.codename1.ui.Font;
import com.codename1.ui.Graphics;

/**
 * The type Refueling blimp.
 */
public class RefuelingBlimp extends FixedGameObject {
    private static final int FULL_COLOR = ColorUtil.rgb(0,0,128);
    private static final int EMPTY_COLOR = ColorUtil.rgb(0,0,32);
    private static final String SPRITE_PATH_BASE = "RefuelingBlimp";
    private int capacity;

    /**
     * Instantiates a new Refueling blimp.
     *
     * @param initialLocation the initialLocation
     */
    public RefuelingBlimp(Location initialLocation) {
        super(125, initialLocation, FULL_COLOR);
        this.capacity = this.getSize() * 20;
        initSprites(SPRITE_PATH_BASE);
    }

    /**
     * Set the fuel level of the blimp to 0.
     */
    public void drainFuel() {
        capacity = 0;
        this.setColor(EMPTY_COLOR);
    }

    /**
     * Gets fuel level.
     *
     * @return the the fuel level
     */
    public int getCapacity() {
        return capacity;
    }

    @Override
    public String toString() {
        String ret = super.toString();
        ret = "Refueling Blimp: " + ret;
        ret = ret + "Capacity: " + capacity + " ";
        return ret;
    }

    @Override
    protected void collideWithObject(GameObject otherObject) {
        if (otherObject instanceof Helicopter) {
            drainFuel();
        }
    }

    @Override
    public void draw(Graphics g) {
        int anchorX = (int)getLocation().getAnchorPointX(this.getSize());
        int anchorY = (int)getLocation().getAnchorPointY(this.getSize());

        int fontSize = Display.getInstance().convertToPixels(1.5f);
        Font small = Font.createTrueTypeFont("Roboto", "RobotoCondensed-Regular.ttf").
                derive(fontSize, Font.STYLE_PLAIN);

        g.setColor(ColorUtil.BLACK);
        g.setFont(small);
        if (getCapacity() > 0) {
            drawSprite(g);
        } else {
            drawSpriteAlt(g);
        }
        g.drawString(Integer.toString(getCapacity()), anchorX + 20, anchorY + 20);
    }
}
