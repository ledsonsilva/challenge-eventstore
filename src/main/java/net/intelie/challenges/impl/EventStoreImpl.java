package net.intelie.challenges.impl;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;
import net.intelie.challenges.Event;
import net.intelie.challenges.EventIterator;
import net.intelie.challenges.EventStore;
import net.intelie.challenges.persistence.InMemoryPersistence;
import net.intelie.challenges.persistence.Persistence;

public class EventStoreImpl implements EventStore {
    private final Persistence<Event> persistence = new InMemoryPersistence<>();

    @Override
    public void insert(Event event) {
        persistence.insert(event);
        System.out.println("Evento {" + event.type() + "} inserido com sucesso timestamp: " + event.timestamp());
    }

    @Override
    public void removeAll(String type) {
        Set<Event> list = persistence.listAll()
                .stream()
                .filter(e -> e.type().equals(type))
                .collect(Collectors.toSet());

        persistence.remove(list);
    }

    @Override
    public EventIterator query(String type, long startTime, long endTime) {
        Collection<Event> list = persistence.listAll()
                .stream()
                .filter(event -> event.type().equals(type))
                .filter(event -> event.timestamp() >= startTime)
                .filter(event -> event.timestamp() <= endTime)
                .collect(Collectors.toSet());

        return new EventIteratorImpl(list);
    }
}
