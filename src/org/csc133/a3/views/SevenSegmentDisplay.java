package org.csc133.a3.views;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.*;
import com.codename1.ui.Component;
import com.codename1.ui.Font;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.geom.Point;

/**
 * An abstract datatype used to represent a seven-segment display.
 */
public abstract class SevenSegmentDisplay extends Component {
    private final int CHAR_WIDTH;
    private final int CHAR_HEIGHT;
    private Point origin;
    private Point nextCharacterPosition;
    private SSDisplayBuffer displayBuffer;
    private String label = null;

    /**
     * Instantiates a new Seven segment display.
     */
    public SevenSegmentDisplay() {
        super();
        CHAR_WIDTH = (int)(InitializedImageBank.getDigitImage(0).getWidth() * 0.8);
        CHAR_HEIGHT = (int) (InitializedImageBank.getDigitImage(0).getHeight() * 0.8);
    }

    /**
     * Gets character container size.
     *
     * @return the character container size
     */
    protected int getCharacterContainerSize() {
        return displayBuffer.getSize();
    }

    /**
     * Sets display buffer.
     *
     * @param displayBuffer the display buffer
     */
    protected void setDisplayBuffer(SSDisplayBuffer displayBuffer) {
        this.displayBuffer = displayBuffer;
    }

    /**
     * Gets display buffer.
     *
     * @return the display buffer
     */
    protected SSDisplayBuffer getDisplayBuffer() {
        return displayBuffer;
    }

    /**
     * Increment character position.
     */
    protected void incrementCharacterPosition() {
        nextCharacterPosition = new Point(nextCharacterPosition.getX() + CHAR_WIDTH, nextCharacterPosition.getY());
    }

    /**
     * Gets next character position.
     *
     * @return the next character position
     */
    protected Point getNextCharacterPosition() {
        return new Point(nextCharacterPosition.getX(), nextCharacterPosition.getY());
    }

    @Override
    public void laidOut() {
        origin = new Point(getX() + 5, getY() + 5);
        nextCharacterPosition = new Point(origin.getX(), origin.getY());
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        int fontSize = Display.getInstance().convertToPixels(1.2f);
        Font roboto = Font.createTrueTypeFont("Roboto", "RobotoCondensed-Regular.ttf").derive(fontSize, Font.STYLE_PLAIN);
        g.setFont(roboto);
        g.setColor(ColorUtil.BLACK);
        g.drawStringBaseline(label, origin.getX(), origin.getY() + 22);
        for (int i = 0; i < displayBuffer.getSize(); ++i) {
            int nextX = getNextCharacterPosition().getX();
            int nextY = getNextCharacterPosition().getY();
            g.setColor(displayBuffer.getBackgroundColor(i));
            g.fillRect(nextX + 1, nextY + 1 + 25, CHAR_WIDTH - 2, CHAR_HEIGHT - 2);
            g.drawImage(displayBuffer.getImage(i), nextX, nextY + 25, CHAR_WIDTH, CHAR_HEIGHT);
            incrementCharacterPosition();
        }
        nextCharacterPosition = origin;
        calcPreferredSize();
    }

    @Override
    protected Dimension calcPreferredSize() {
        return new Dimension(CHAR_WIDTH * getCharacterContainerSize() + 5, CHAR_HEIGHT + 60);
    }

    /**
     * Sets the label that appears above the seven-segment display.
     *
     * @param label the label
     */
    protected void setLabel(String label) {
        this.label = label;
    }
}
