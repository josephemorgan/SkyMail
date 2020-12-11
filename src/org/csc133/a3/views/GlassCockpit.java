package org.csc133.a3.views;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Container;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.GridBagConstraints;
import com.codename1.ui.layouts.GridBagLayout;
import org.csc133.a3.gameobjects.GameObjectCollection;
import org.csc133.a3.gameobjects.GameObject;
import org.csc133.a3.gameobjects.PlayerHelicopter;

/**
 * A view that is designed to be placed at the top of the game window, displaying information about the game state.
 */
public class GlassCockpit extends Container {
    private final GridBagConstraints constraints = new GridBagConstraints();
    private final GameClockComponent gameClockDisplay;
    private final SevenSegmentDisplayDigits fuelDisplay;
    private static final int FUEL_DISPLAY_COLOR = ColorUtil.GREEN;
    private final SevenSegmentDisplayDigits damageDisplay;
    private static final int DAMAGE_DISPLAY_DEFAULT_COLOR = ColorUtil.GREEN;
    private static final int DAMAGE_DISPLAY_WARN_COLOR = ColorUtil.YELLOW;
    private static final int DAMAGE_DISPLAY_DANGER_COLOR = ColorUtil.MAGENTA;
    private final SevenSegmentDisplayDigits livesDisplay;
    private static final int LIVES_DISPLAY_COLOR = ColorUtil.GREEN;
    private final SevenSegmentDisplayDigits lastSkyScraperDisplay;
    private static final int LAST_SS_DISPLAY_COLOR = ColorUtil.GREEN;
    private final SevenSegmentDisplayDigits headingDisplay;
    private static final int HEADING_DISPLAY_COLOR = ColorUtil.YELLOW;

    /**
     * Instantiates a new Glass cockpit.
     */
    public GlassCockpit() {
        super(new GridBagLayout());
        gameClockDisplay = new GameClockComponent();
        fuelDisplay = new SevenSegmentDisplayDigits(1000, 4, "FUEL", FUEL_DISPLAY_COLOR);
        damageDisplay = new SevenSegmentDisplayDigits(0, 2, "DMG", DAMAGE_DISPLAY_DEFAULT_COLOR);
        livesDisplay = new SevenSegmentDisplayDigits(3, 1, "LIVES", LIVES_DISPLAY_COLOR);
        lastSkyScraperDisplay = new SevenSegmentDisplayDigits(1, 1, "LAST", LAST_SS_DISPLAY_COLOR);
        headingDisplay = new SevenSegmentDisplayDigits(0, 3, "HDG",HEADING_DISPLAY_COLOR);
        buildContainer();
    }

    /**
     * Orders the displays to update with respect to information contained in a GameObjectCollection.
     *
     * @param referenceToGameObjectCollection the referenceToGameObjectCollection
     */
    public void update(GameObjectCollection referenceToGameObjectCollection) {
        double newFuelLevel = -1;
        double newDamageLevel = -1;
        int newNumLives = -1;
        int newLastSkyScraper = -1;
        int newHeading = -1;

        for (GameObject object : referenceToGameObjectCollection) {
            if (object instanceof PlayerHelicopter) {
                newFuelLevel = ((PlayerHelicopter) object).getFuelLevel();
                newDamageLevel = ((PlayerHelicopter) object).getDamageLevel();
                newNumLives = ((PlayerHelicopter) object).getNumberOfLives();
                newLastSkyScraper = ((PlayerHelicopter) object).getLastSkyScraperReached();
                newHeading = ((PlayerHelicopter) object).getHeading();
                break;
            }
        }

        int damageColor;
        if (newDamageLevel >= 50) {
            if (newDamageLevel >= 85) {
                damageColor = DAMAGE_DISPLAY_DANGER_COLOR;
            } else {
                damageColor = DAMAGE_DISPLAY_WARN_COLOR;
            }
        } else {
            damageColor = DAMAGE_DISPLAY_DEFAULT_COLOR;
        }
        fuelDisplay.update((int)newFuelLevel, FUEL_DISPLAY_COLOR);
        damageDisplay.update((int)newDamageLevel, damageColor);
        livesDisplay.update(newNumLives, LIVES_DISPLAY_COLOR);
        lastSkyScraperDisplay.update(newLastSkyScraper, LAST_SS_DISPLAY_COLOR);
        headingDisplay.update(newHeading - 90, HEADING_DISPLAY_COLOR);
        repaint();
    }

    private void buildContainer() {
        constraints.anchor = GridBagConstraints.LAST_LINE_START;
        constraints.weighty = 0.2;

        constraints.anchor = GridBagConstraints.CENTER;
        constraints.weighty = 1.0;
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 6;
        constraints.gridheight = 1;
        add(constraints, gameClockDisplay);

        constraints.gridx = 6;
        constraints.gridy = 1;
        constraints.gridwidth = 4;
        constraints.gridheight = 1;
        add(constraints, fuelDisplay);

        constraints.gridx = 10;
        constraints.gridy = 1;
        constraints.gridwidth = 2;
        constraints.gridheight = 1;
        add(constraints, damageDisplay);

        constraints.gridx = 12;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        add(constraints, livesDisplay);

        constraints.gridx = 13;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        add(constraints, lastSkyScraperDisplay);

        constraints.gridx = 14;
        constraints.gridy = 1;
        constraints.gridwidth = 3;
        constraints.gridheight = 1;
        add(constraints, headingDisplay);
    }

    @Override
    protected Dimension calcPreferredSize() {
        return super.calcPreferredSize();
    }

    /**
     * Gets game clock component.
     *
     * @return the game clock component
     */
    public ITimer getGameClockComponent() {
        return gameClockDisplay;
    }
}
