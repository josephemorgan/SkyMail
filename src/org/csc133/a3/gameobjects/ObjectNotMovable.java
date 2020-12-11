package org.csc133.a3.gameobjects;

/**
 * An exception that is thrown when a client tries to call move() on a FixedGameObject.
 */
public class ObjectNotMovable extends RuntimeException {
    /**
     * Instantiates a new Object not movable.
     *
     * @param message the message
     */
    public ObjectNotMovable(String message) {
        super(message);
    }
}
