package org.csc133.a3.gameobjects.strategies;

import org.csc133.a3.gameobjects.GameObjectCollection;
import org.csc133.a3.gameobjects.NonPlayerHelicopter;

/**
 * The interface Strategy.
 */
// Wow, what an incredibly descriptive name. I love that the spec requires us to use such a great name for this interface.
public interface IStrategy {
    /**
     * Invoke strategy.
     *
     * @param me the me
     * @param c  the c
     */
    public void invokeStrategy(NonPlayerHelicopter me, GameObjectCollection c);
}