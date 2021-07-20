package net.intelie.challenges.persistence;

import java.util.Collection;

public interface Persistence<T> {
    Collection<T> listAll();
    void insert(T data);
    void remove(Collection<T> objects);
}
