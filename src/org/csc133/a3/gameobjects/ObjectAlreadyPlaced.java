package org.csc133.a3.gameobjects;

/**
 * The exception Object already placed. Gets created when a client tries to call the place() method on an object with
 * non-null Location.
 */
public class ObjectAlreadyPlaced extends RuntimeException{
    /**
     * Instantiates a new Object already placed.
     *
     * @param msg the message to pass to superclass.
     */
    public ObjectAlreadyPlaced(String msg) {
        super(msg);
    }
}
