package org.csc133.a3.gameobjects;

/**
 * A runtime exception thrown when a location is used that is out of the bounds of the GameWorld.
 */
public class LocationOutOfBounds extends RuntimeException{
    public LocationOutOfBounds(String msg) {
        super(msg);
    }
}
