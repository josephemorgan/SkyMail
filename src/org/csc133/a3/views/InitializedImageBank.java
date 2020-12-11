package org.csc133.a3.views;

import com.codename1.ui.Image;

import java.io.IOException;
import java.util.ArrayList;

/**
 * An class that exists to serve as a static container and distributor of seven segment digit images. Allows the bank of
 * images to only be instantiated once, and used everywhere else in code.
 */
public class InitializedImageBank {
    private static ArrayList<Image> digitImages;
    private static ArrayList<Image> digitWithDotImages;
    private static Image colonImage;
    private static boolean hasBeenInit = false;

    /**
     * Initialize the image bank
     */
    protected static void initialize() {
        try {
            digitImages = new ArrayList<>();
            for (int i = 0; i < 10; ++i) {
                digitImages.add(Image.createImage("/LED_digit_" + i + ".png"));
            }

            digitWithDotImages = new ArrayList<>();
            for (int i = 0; i < 10; ++i) {
                digitWithDotImages.add(Image.createImage("/LED_digit_" + i + "_with_dot.png"));
            }

            colonImage = Image.createImage("/LED_colon.png");
            hasBeenInit = true;
        } catch (
                IOException e) {
            System.out.println("7 Segment Images Unavailable");
            e.printStackTrace();
        }
    }

    /**
     * Gets digit image.
     *
     * @param digit the digit
     * @return the digit image
     */
    public static Image getDigitImage(int digit) {
        if (!hasBeenInit) {
            initialize();
        }

        return digitImages.get(digit);
    }

    /**
     * Gets digit with dot image.
     *
     * @param digit the digit
     * @return the digit with dot image
     */
    public static Image getDigitWithDotImage(int digit) {
        if (!hasBeenInit) {
            initialize();
        }

        return digitWithDotImages.get(digit);
    }

    /**
     * Gets colon image.
     *
     * @return the colon image
     */
    public static Image getColonImage() {
        if (!hasBeenInit) {
            initialize();
        }

        return colonImage;
    }
}

