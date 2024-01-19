package io.github.rainvaporeon.activitychecker.misc;

import io.github.rainvaporeon.fishutils.misc.annotations.DelegatesToShadow;
import org.jetbrains.annotations.NotNull;

import java.util.AbstractSet;
import java.util.Iterator;
import java.util.WeakHashMap;

@DelegatesToShadow
public class WeakHashSet<E> extends AbstractSet<E> {
    @DelegatesToShadow.Target
    private final WeakHashMap<E, Object> coll;

    private static final Object PRESENT = new Object();

    public WeakHashSet() {
        this.coll = new WeakHashMap<>();
    }

    @Override
    public boolean add(E e) {
        return coll.put(e, PRESENT) == null;
    }

    @Override
    public boolean remove(Object o) {
        return coll.remove(o) == PRESENT;
    }

    @Override
    public boolean contains(Object o) {
        return coll.containsKey(o);
    }

    @Override
    public void clear() {
        coll.clear();
    }

    @NotNull
    @Override
    public Iterator<E> iterator() {
        return coll.keySet().iterator();
    }

    @Override
    public int size() {
        return coll.size();
    }

    public WeakHashSet(int capacity) {
        this.coll = new WeakHashMap<>(capacity);
    }

    public WeakHashSet(int capacity, float loadFactor) {
        this.coll = new WeakHashMap<>(capacity, loadFactor);
    }


}
