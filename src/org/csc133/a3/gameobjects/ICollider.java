package org.csc133.a3.gameobjects;

public interface ICollider {
    boolean checkCollidesWith(GameObject otherObject);
    void handleCollision(GameObject otherObject);
}
