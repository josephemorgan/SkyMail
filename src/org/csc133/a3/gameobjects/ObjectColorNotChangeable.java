package org.csc133.a3.gameobjects;

/**
 * An exception that gets thrown when a client tries to change the color of an object that the spec has required be
 * unchangeable.
 */
public class ObjectColorNotChangeable extends RuntimeException {
    /**
     * Instantiates a new Object color not changeable.
     *
     * @param message the message to send to superclass.
     */
    public ObjectColorNotChangeable(String message) {
        super(message);
    }
}
