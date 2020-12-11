package org.csc133.a3.views;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Image;

import java.util.ArrayList;
import java.util.Stack;

/**
 * A type of SevenSegmentDisplay used to display a set number of digits
 */
public class SevenSegmentDisplayDigits extends SevenSegmentDisplay {
    private int color_okay = ColorUtil.CYAN;
    private final int numDigits;

    /**
     * Instantiates a new SevenSegmentDisplayDigits
     *
     * @param number    the number to display
     * @param numDigits the number of digits
     * @param label     the label to show above the display
     * @param color     the background color
     */
    public SevenSegmentDisplayDigits(int number, int numDigits, String label, int color) {
        this.numDigits = numDigits;
        setLabel(label);
        update(number, color);
        calcPreferredSize();
    }

    /**
     * Updates the SevenSegmentDisplay with a new number to display, as well as a new background color
     *
     * @param number the number to display
     * @param color  the background color
     */
    public void update(int number, int color) {
        Stack<Integer> digits = new Stack<>();
        ArrayList<Image> digitImages = new ArrayList<>();
        ArrayList<Integer> backgroundColors = new ArrayList<>();

        while (number > 0) {
            digits.push(number % 10);
            number /= 10;
        }

        while (numDigits > digitImages.size() + digits.size()) {
            digits.add(0);
        }

        for (int i = 0; i < numDigits; ++i) {
            digitImages.add(InitializedImageBank.getDigitImage(digits.pop()));
            backgroundColors.add(color);
        }

        setDisplayBuffer(new SSDisplayBuffer(digitImages, backgroundColors));
        repaint();
    }
}
