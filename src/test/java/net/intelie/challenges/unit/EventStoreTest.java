package net.intelie.challenges.unit;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import net.intelie.challenges.Event;
import net.intelie.challenges.EventIterator;
import net.intelie.challenges.EventStore;
import net.intelie.challenges.impl.EventStoreImpl;
import net.intelie.challenges.persistence.InMemoryPersistence;
import net.intelie.challenges.persistence.Persistence;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class EventStoreTest {
    private final Persistence<Event> persistence = new InMemoryPersistence<>();
    private final EventStore store = new EventStoreImpl();

    @Before
    public void reset() {
        persistence.remove(persistence.listAll());
    }

    @Test
    public void should_return_a_collection_with_two_items_to_insert_two_events() {
        Event event1 = new Event("some_type", 123L);
        Event event2 = new Event("some_type", 124L);

        store.insert(event1);
        store.insert(event2);

        Assert.assertEquals(2, persistence.listAll().size());
    }

    @Test
    public void should_return_one_EventIterator_with_two_events_in_range_from_2_to_4_in_one_list_with_five_events_of_the_same_type() {
        Event event1 = new Event("some_type", 1L);
        Event event2 = new Event("some_type", 2L);
        Event event3 = new Event("some_type", 3L);
        Event event4 = new Event("some_type", 4L);
        Event event5 = new Event("some_type", 5L);

        store.insert(event1);
        store.insert(event2);
        store.insert(event3);
        store.insert(event4);
        store.insert(event5);

        EventIterator iterator = store.query("some_type", 2, 3);

        int total = 0;
        while (iterator.moveNext()) {
            total++;
        }

        Assert.assertEquals(2, total);
    }

    @Test
    public void test_to_validate_persistence_behavior_with_execution_in_concurrency() throws InterruptedException, ExecutionException {
        ExecutorService executor = Executors.newFixedThreadPool(25);

        List<Callable<Long>> inserts = new ArrayList<>();

        // Insert - type hello
        for (int i = 0; i < 5; i++) {
            inserts.add(new EventInsertTask("hello", (long) i));
        }

        // Insert - type word
        for (int i = 0; i < 10; i++) {
            inserts.add(new EventInsertTask("word", (long) i));
        }

        List<Future<Long>> futuresInserts = executor.invokeAll(inserts);
        Future<Boolean> futuresDeletes = executor.submit(new EventRemoveTask());

        executor.shutdown();

        System.out.println("Total inserts: " + futuresInserts.size());
        System.out.println("Delete execution: " + futuresDeletes.get());

        Assert.assertEquals(10, persistence.listAll().size());
    }

    public static class EventInsertTask implements Callable<Long> {
        private final Long task;
        private final String type;

        public EventInsertTask(String type, Long task) {
            this.task = task;
            this.type = type;
        }

        private final EventStore store = new EventStoreImpl();

        @Override
        public Long call() throws Exception {
            Event event1 = new Event(type, task);
            store.insert(event1);
            return task;
        }
    }

    public static class EventRemoveTask implements Callable<Boolean> {
        private final EventStore store = new EventStoreImpl();

        @Override
        public Boolean call() throws Exception {
            store.removeAll("hello");
            return true;
        }
    }
}
