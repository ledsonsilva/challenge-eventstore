package net.intelie.challenges.unit;

import java.util.Collection;
import java.util.HashSet;
import net.intelie.challenges.Event;
import net.intelie.challenges.EventIterator;
import net.intelie.challenges.impl.EventIteratorImpl;
import org.junit.Assert;
import org.junit.Test;

public class EventIteratorTest {
    @Test
    public void should_throw_IllegalStateException_if_collection_passed_is_null() {
        Assert.assertThrows(IllegalStateException.class, () -> new EventIteratorImpl(null));
    }

    @Test
    public void should_throw_IllegalStateException_when_to_call_the_method_current_before_starting_iteration_with_method_moveNext() {
        Collection<Event> events = new HashSet<>();
        events.add(new Event("tp_01", 123L));

        EventIterator iterator = new EventIteratorImpl(events);
        Assert.assertThrows(IllegalStateException.class, iterator::current);
    }

    @Test
    public void should_throw_IllegalStateException_when_to_call_the_method_remove_before_starting_iteration_with_method_moveNext() {
        Collection<Event> events = new HashSet<>();
        events.add(new Event("tp_01", 123L));

        EventIterator iterator = new EventIteratorImpl(events);
        Assert.assertThrows(IllegalStateException.class, iterator::current);
    }

    @Test
    public void should_return_false_when_collection_is_empty_when_calling_method_moveNext() {
        Collection<Event> events = new HashSet<>();
        EventIterator iterator = new EventIteratorImpl(events);

        Assert.assertFalse(iterator.moveNext());
    }

    @Test
    public void should_return_true_when_method_moveNext_is_called_for_first_time() {
        Collection<Event> events = new HashSet<>();
        events.add(new Event("tp_01", 123L));

        EventIterator iterator = new EventIteratorImpl(events);
        Assert.assertTrue(iterator.moveNext());
    }

    @Test
    public void should_return_true_when_method_moveNext_is_called_twice_in_a_collection_with_two_items() {
        Collection<Event> events = new HashSet<>();
        events.add(new Event("tp_01", 123L));
        events.add(new Event("tp_02", 124L));

        EventIterator iterator = new EventIteratorImpl(events);
        iterator.moveNext(); // first call

        Assert.assertTrue(iterator.moveNext());
    }

    @Test
    public void should_return_true_when_method_moveNext_for_called_tres_times_in_a_collection_with_tres_items() {
        Collection<Event> events = new HashSet<>();
        events.add(new Event("tp_01", 123L));
        events.add(new Event("tp_02", 124L));
        events.add(new Event("tp_03", 125L));

        EventIterator iterator = new EventIteratorImpl(events);
        iterator.moveNext(); // first call
        iterator.moveNext(); // second call

        Assert.assertTrue(iterator.moveNext());
    }

    @Test
    public void should_return_false_when_method_moveNext_is_called_twice_in_a_collection_with_only_one_item() {
        Collection<Event> events = new HashSet<>();
        events.add(new Event("tp_01", 123L));

        EventIterator iterator = new EventIteratorImpl(events);
        iterator.moveNext(); // first call

        Assert.assertFalse(iterator.moveNext());
    }

    @Test
    public void should_return_an_event_when_method_current_is_called_and_iteration_is_since_initiated() {
        Collection<Event> events = new HashSet<>();
        events.add(new Event("tp_01", 123L));

        EventIterator iterator = new EventIteratorImpl(events);
        iterator.moveNext(); // startup

        Event event = iterator.current();

        Assert.assertEquals("tp_01", event.type());
        Assert.assertEquals(123L, event.timestamp());
    }

    @Test
    public void must_throw_IllegalStateException_to_search_item_when_iteration_has_not_been_initialized() {
        Collection<Event> events = new HashSet<>();
        events.add(new Event("tp_01", 123L));

        EventIterator iterator = new EventIteratorImpl(events);
        Assert.assertThrows(IllegalStateException.class, iterator::current);
    }

    @Test
    public void should_throw_IllegalStateException_to_remove_when_iteration_has_not_been_initialized() {
        Collection<Event> events = new HashSet<>();
        events.add(new Event("tp_01", 123L));

        EventIterator iterator = new EventIteratorImpl(events);
        Assert.assertThrows(IllegalStateException.class, iterator::remove);
    }
}
