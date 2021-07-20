package net.intelie.challenges.unit.persistence;

import java.util.Set;
import java.util.stream.Collectors;
import net.intelie.challenges.Event;
import net.intelie.challenges.persistence.InMemoryPersistence;
import net.intelie.challenges.persistence.Persistence;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class InMemoryPersistenceTest {
    private final Persistence<Event> persistence = new InMemoryPersistence<>();

    @Before
    public void reset() {
        persistence.remove(persistence.listAll());
    }

    @Test
    public void should_return_a_collection_with_two_items_to_insert_two_events() {
        Event event1 = new Event("some_type", 123L);
        Event event2 = new Event("some_type", 124L);

        persistence.insert(event1);
        persistence.insert(event2);

        Assert.assertEquals(2, persistence.listAll().size());
    }

    @Test
    public void must_return_a_collection_with_an_event_by_inserting_two_events_and_remove_the_first() {
        Event event1 = new Event("some_type", 123L);
        Event event2 = new Event("some_type", 124L);

        persistence.insert(event1);
        persistence.insert(event2);

        Set<Event> search = persistence
                .listAll().stream()
                .filter(f -> f.timestamp() == 123L)
                .collect(Collectors.toSet());

        persistence.remove(search);
        Assert.assertEquals(1, persistence.listAll().size());
    }

    @Test
    public void should_return_a_when_add_the_same_event_more_than_once() {
        Event event1 = new Event("some_type", 123L);

        persistence.insert(event1);
        persistence.insert(event1);

        Assert.assertEquals(1, persistence.listAll().size());
    }
}
