package net.intelie.challenges.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import net.intelie.challenges.Event;
import net.intelie.challenges.EventIterator;

public class EventIteratorImpl implements EventIterator {
    private final List<Event> events;
    private Integer currentIndex = null;

    public EventIteratorImpl(Collection<Event> events) {
        if (Objects.isNull(events)) {
            throw new IllegalStateException("The collection can not be null");
        }

        this.events = new ArrayList<>(events);
    }

    @Override
    public boolean moveNext() {
        if (events.size() <= 0) return false;

        if (currentIndex == null) {
            currentIndex = 0;
            return true;
        }

        if (currentIndex < (events.size() -1)  && events.get(currentIndex) != null) {
            currentIndex++;
            return true;
        }

        return false;
    }

    @Override
    public Event current() {
        if (Objects.isNull(currentIndex)) {
            throw new IllegalStateException("Invoke method moveNext() to initialize iteration");
        }

        return events.get(currentIndex);
    }

    @Override
    public void remove() {
        if (Objects.isNull(currentIndex)) {
            throw new IllegalStateException("Invoke method moveNext() to initialize iteration");
        }

        events.remove(events.get(currentIndex));
    }

    @Override
    public void close() throws Exception {
        // Do Nothing
    }
}
