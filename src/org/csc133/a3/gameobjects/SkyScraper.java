package org.csc133.a3.gameobjects;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Font;
import com.codename1.ui.Graphics;

/**
 * The type Sky scraper.
 */
public class SkyScraper extends FixedGameObject {
    private static final String SPRITE_PATH_BASE = "Skyscraper";
    private static final int DEFAULT_SIZE = 150;
    private static final int NOT_REACHED_BORDER = ColorUtil.MAGENTA;
    private static final int HAS_BEEN_REACHED_BORDER = ColorUtil.BLACK;
    private static int numberOfSkyscrapers = 0;
    private boolean hasBeenReached = false;
    private final int sequenceNumber;

    /**
     * Gets the sequence number of the skyscraper.
     *
     * @return the sequence number
     */
    public int getSequenceNumber() {
        return sequenceNumber;
    }

    /**
     * Instantiates a new Sky scraper.
     *
     * @param initialLocation the initial location
     */
    public SkyScraper(Location initialLocation) {
        super(DEFAULT_SIZE, initialLocation, ColorUtil.rgb(0, 128, 128));
        ++numberOfSkyscrapers;
        sequenceNumber = numberOfSkyscrapers;
        initSprites(SPRITE_PATH_BASE);
    }

    /**
     * Sets a flag that indicates that the skyscraper has been reached in the correct order.
     */
    private void setHasBeenReached() {
        hasBeenReached = true;
    }

    public void setColor(int newColor) {
        throw new ObjectColorNotChangeable("Cannot change color of SkyScraper object.\n");
    }

    @Override
    protected void collideWithObject(GameObject otherObject) {
        if (otherObject instanceof PlayerHelicopter) {
            if (((PlayerHelicopter) otherObject).getLastSkyScraperReached() == sequenceNumber - 1)
                setHasBeenReached();
        }
    }

    @Override
    public String toString() {
        String ret = super.toString();
        ret = "Skyscraper: " + ret;
        ret = ret + "Sequence number: " + this.getSequenceNumber() + " ";
        return ret;
    }

    /**
     * Resets the number of skyscrapers already created to 0.
     */
    public static void resetCount() {
        numberOfSkyscrapers = 0;
    }

    @Override
    public void draw(Graphics g) {
        int size = getSize();
        int anchorPointX = (int)getLocation().getAnchorPointX(size);
        int anchorPointY = (int)getLocation().getAnchorPointY(size);

        g.setColor(this.getColor());
        g.setColor(ColorUtil.BLACK);
        g.setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_MEDIUM));
        if (hasBeenReached) {
            drawSpriteAlt(g);
        } else {
            drawSprite(g);
        }
        g.drawChar(Integer.toString(sequenceNumber).charAt(0), anchorPointX + (size / 3), anchorPointY + (size / 4));
    }
}
