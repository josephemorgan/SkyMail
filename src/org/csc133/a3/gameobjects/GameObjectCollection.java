package org.csc133.a3.gameobjects;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * An implementation of Collection, essentially serves as a wrapper class for an arraylist with additional functionality
 * for locating specific instances of GameObject
 */
public class GameObjectCollection implements Collection<GameObject> {
    private ArrayList<GameObject> internalCollection;

    public GameObjectCollection() {
        internalCollection = new ArrayList<>();
    }

    public GameObjectCollection(GameObjectCollection collectionToCopy) {
        this();
        for (GameObject toCopy : collectionToCopy) {
            internalCollection.add(toCopy);
        }
    }

    @Override
    public int size() {
        return internalCollection.size();
    }

    @Override
    public boolean isEmpty() {
        return internalCollection.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return internalCollection.contains(o);
    }

    @Override
    public Iterator<GameObject> iterator() {
        return internalCollection.iterator();
    }

    @Override
    public Object[] toArray() {
        return internalCollection.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return null;
    }

    @Override
    public boolean add(GameObject gameObject) {
        return internalCollection.add(gameObject);
    }

    @Override
    public boolean remove(Object o) {
        return internalCollection.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return this.internalCollection.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends GameObject> c) {
        return this.internalCollection.addAll(c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return this.internalCollection.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return this.internalCollection.retainAll(c);
    }

    @Override
    public void clear() {
        internalCollection = new ArrayList<>();
    }

    /**
     * Find player helicopter.
     *
     * @return the player helicopter
     */
    public PlayerHelicopter findPlayer() {
        for (GameObject o : internalCollection)
            if (o instanceof PlayerHelicopter)
                return (PlayerHelicopter) o;
        return null;
    }

    public GameObject get(int index) {
        return internalCollection.get(index);
    }
}
