package org.csc133.a3.views;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Image;

import java.util.ArrayList;

/**
 * A type of Seven Segment Display that's used to show a clock in the MM:SS.hundredths format.
 */
public class GameClockComponent extends SevenSegmentDisplay implements ITimer {
    private final int COLOR_DEFAULT = ColorUtil.CYAN;
    private final int MS_COLOR_DEFAULT = ColorUtil.BLUE;
    private final int COLOR_DANGER = ColorUtil.rgb(255, 64, 64);
    private final int MS_COLOR_DANGER = ColorUtil.rgb(128, 0, 0);
    private long startTimeMillis;
    private long totalPausedTimeMillis;
    private long startPausedTimeMillis;
    private long lastUpdateMillis;

    /**
     * Instantiates a new Game clock component.
     */
    public GameClockComponent() {
        ArrayList<Image> images = new ArrayList<>(6);
        ArrayList<Integer> backgroundColors = new ArrayList<>(6);
        setLabel("GAME CLOCK");
        for (int i = 0; i < 6; ++i) {
            images.add(InitializedImageBank.getDigitImage(i));
        }
        for (int i = 0; i < 5; ++i) {
            backgroundColors.add(COLOR_DEFAULT);
        }
        backgroundColors.add(MS_COLOR_DEFAULT);
        setDisplayBuffer(new SSDisplayBuffer(images, backgroundColors));
        update(0);
        calcPreferredSize();
        startTimeMillis = System.currentTimeMillis();
    }

    /**
     * Issues the order to the GameClockComponent to update it's display to reflect given millisecond value.
     *
     * @param elapsedMilliseconds the millisecond representation of the time to be displayed.
     */
    protected void update(long elapsedMilliseconds) {
        int minutes = (int)(elapsedMilliseconds / 60000);
        int seconds = (int)((elapsedMilliseconds % 60000) / 1000);
        int tenthsOfSeconds = (int)((elapsedMilliseconds % 1000) / 100);

        getDisplayBuffer().setDigitImage(0, (minutes / 10));
        getDisplayBuffer().setDigitImage(1, (minutes % 10));
        getDisplayBuffer().setColonImage(2);
        getDisplayBuffer().setDigitImage(3, (seconds / 10));
        getDisplayBuffer().setDigitWithDotImage(4, (seconds % 10));
        getDisplayBuffer().setDigitImage(5, (tenthsOfSeconds));
    }

    @Override
    public boolean animate() {
        if (System.currentTimeMillis() - lastUpdateMillis >= 10) {
            lastUpdateMillis = System.currentTimeMillis();
            long elapsedTime = lastUpdateMillis - startTimeMillis - totalPausedTimeMillis;
            if (elapsedTime >= 600000) {
                for (int i = 0; i < getDisplayBuffer().getSize() - 1; ++i) {
                    getDisplayBuffer().setBackgroundColor(i, COLOR_DANGER);
                }
                getDisplayBuffer().setBackgroundColor(getDisplayBuffer().getSize() - 1, MS_COLOR_DANGER);
            }
            update(elapsedTime);
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public void laidOut() {
        super.laidOut();
        getComponentForm().registerAnimated(this);
    }

    @Override
    public void resetElapsedTime() {
        totalPausedTimeMillis = 0;
        startPausedTimeMillis = 0;
        startTimeMillis = System.currentTimeMillis();
        lastUpdateMillis = 0;
    }

    @Override
    public void startElapsedTime() {
        if (startTimeMillis == 0) {
            startTimeMillis = System.currentTimeMillis();
        } else if (0 != startPausedTimeMillis) {
            totalPausedTimeMillis += System.currentTimeMillis() - startPausedTimeMillis;
        }
    }

    @Override
    public void stopElapsedTime() {
        startPausedTimeMillis = System.currentTimeMillis();
    }

    @Override
    public long getElapsedTime() {
        return lastUpdateMillis - startTimeMillis - totalPausedTimeMillis;
    }
}
