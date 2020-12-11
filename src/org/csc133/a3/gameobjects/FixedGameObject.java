package org.csc133.a3.gameobjects;

import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.geom.Point;

import java.io.IOException;
import java.util.ArrayList;

/**
 * The abstract type representing a GameObject that can't move.
 */
public abstract class FixedGameObject extends GameObject {
    private ArrayList<Image> sprites;
    /**
     * Instantiates a new Fixed game object.
     *
     * @param size     the size
     * @param location the location
     * @param color    the color
     */
    public FixedGameObject(int size, Location location, int color) {
        super(size, location, color);
    }

    protected void initSprites(String pathBase) {
        sprites = new ArrayList<>();
        String path = "/" + pathBase + ".png";
        String pathAlt = "/" + pathBase + "Alt.png";
        try {
            sprites.add(Image.createImage(path));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error creating image for file: " + path);
            System.exit(1);
        }
        try {
            sprites.add(Image.createImage(pathAlt));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error creating image for file: " + pathAlt);
            System.exit(1);
        }
    }

    protected void drawSprite(Graphics g) {
        draw(g, 0);
    }

    protected void drawSpriteAlt(Graphics g) {
        draw(g, 1);
    }

    private void draw(Graphics g, int index) {
        Point anchorPoint = new Point(
                (int) Math.round(getLocation().getAnchorPointX(getSize())),
                (int) Math.round(getLocation().getAnchorPointY(getSize())));

        g.drawImage(sprites.get(index), anchorPoint.getX(), anchorPoint.getY(), getSize(), getSize());
    }

    /**
     * Overrides the setLocation function of parent to block access, throws exception indicating that object can't be moved.
     *
     * @Param l The location where the object won't be placed.
     * @throws ObjectNotMovable the object not movable
     */
    @Override
    public void setLocation(Location l) throws ObjectNotMovable {
        if (getLocation() != null) {
            throw new ObjectNotMovable("Cannot move FixedGameObject");
        }
    }
}
