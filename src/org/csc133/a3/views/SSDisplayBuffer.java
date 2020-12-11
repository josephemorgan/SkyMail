package org.csc133.a3.views;

import com.codename1.ui.Image;

import java.util.ArrayList;

/**
 * A type to represent a seven-segment display buffer. Holds references to a list of images and a list of background
 * colors.
 */
public class SSDisplayBuffer {
    private ArrayList<Image> images;
    private ArrayList<Integer> backgroundColors;

    /**
     * Instantiates a new SSDisplayBuffer.
     *
     * @param images           An ArrayList of images to be held in the buffer.
     * @param backgroundColors An ArrayList of background colors to be held in the buffer.
     */
    public SSDisplayBuffer(ArrayList<Image> images, ArrayList<Integer> backgroundColors) {
        this.images = images;
        this.backgroundColors = backgroundColors;
    }

    /**
     * Gets image at specified index.
     *
     * @param index the index
     * @return the image
     */
    protected Image getImage(int index) {
        return images.get(index);
    }

    /**
     * Sets digit image at specified index.
     *
     * @param index the index
     * @param digit the digit
     */
    protected void setDigitImage(int index, int digit) {
        images.set(index, InitializedImageBank.getDigitImage(digit));
    }

    /**
     * Sets digit with dot image at specified index.
     *
     * @param index the index
     * @param digit the digit
     */
    protected void setDigitWithDotImage(int index, int digit) {
        images.set(index, InitializedImageBank.getDigitWithDotImage(digit));
    }

    /**
     * Sets colon image at specified index.
     *
     * @param index the index
     */
    protected void setColonImage(int index) {
        images.set(index, InitializedImageBank.getColonImage());
    }

    /**
     * Gets background color at specified index.
     *
     * @param index the index
     * @return the background color
     */
    protected int getBackgroundColor(int index) {
        return backgroundColors.get(index);
    }

    /**
     * Sets background color at specified index.
     *
     * @param index the index
     * @param color the color
     */
    protected void setBackgroundColor(int index, int color) {
        backgroundColors.set(index, color);
    }

    /**
     * Gets size.
     *
     * @return the size
     */
    protected int getSize() {
        return images.size();
    }
}
