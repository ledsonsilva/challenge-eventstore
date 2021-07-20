package net.intelie.challenges.persistence;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

public class InMemoryPersistence<T> implements Persistence<T> {
    private final static Collection<Object> database = Collections.synchronizedCollection(new HashSet<>());

    @Override
    public Collection<T> listAll() {
        return (Collection<T>) database;
    }

    @Override
    public void insert(T object) {
        synchronized (database) {
            database.add(object);
        }
    }

    @Override
    public void remove(Collection<T> objects) {
        synchronized (database) {
            database.removeAll(objects);
        }
    }
}
