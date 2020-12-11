package org.csc133.a3.views;

import com.codename1.ui.Component;
import com.codename1.ui.Graphics;
import org.csc133.a3.gameobjects.GameObjectCollection;
import org.csc133.a3.gameobjects.Location;
import org.csc133.a3.gameobjects.GameObject;

/**
 * The central view of the Game window. MapView shows graphical representations of GameObjects at their current
 * locations.
 */
public class MapView extends Component {
    private GameObjectCollection gameObjects = null;
    private Location minLocation = null;
    private Location maxLocation = null;

    /**
     * Gets min location.
     *
     * @return the min location
     */
    public Location getMinLocation() {
        return minLocation;
    }

    /**
     * Gets max location.
     *
     * @return the max location
     */
    public Location getMaxLocation() {
        return maxLocation;
    }

    /**
     * Updates the MapView with respect to information contained in a GameObjectCollection.
     *
     * @param gameObjectCollection the gameObjectCollection
     */
    public void update(GameObjectCollection gameObjectCollection) {
        gameObjects = gameObjectCollection;
        repaint();
    }

    @Override
    protected void laidOut() {
        minLocation = new Location(getX(), getY());
        maxLocation = new Location((getWidth() + getX()) - 1, (getHeight() + getY()) - 1);
    }

    @Override
    public void paint(Graphics g) {
        g.setAntiAliased(true);
        if (gameObjects != null) {
            for (GameObject obj : gameObjects) {
                obj.draw(g);
            }
        }
    }
}
