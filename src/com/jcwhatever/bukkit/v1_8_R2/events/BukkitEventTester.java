package com.jcwhatever.bukkit.v1_8_R2.events;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A utility for testing Bukkit events. Most Bukkit events are already monitored by
 * {@link BukkitEventTester}. Custom events can be monitored by invoking {@link #notify}
 * from within a handler for the event.
 */
public class BukkitEventTester {

    private BukkitEventTester() {}

    private static final Map<Class<?>, Integer> _eventHandlerCount = new HashMap<>(10);
    private static final Map<Class<?>, Integer> _eventCount = new HashMap<>(10);
    private static final Map<Class<?>, Object> _recent = new HashMap<>(10);
    private static final List<Class<?>> _pendingCancel = new ArrayList<>(10);

    /**
     * Invoked from within an event handler to notify
     * that an event has been called.
     *
     * <p>The result returned indicates whether the event should
     * be cancelled, if possible.</p>
     *
     * @param event  The event that was called.
     *
     * @return  True to allow the event, false to cancel.
     */
    public static synchronized <T extends Event> boolean notify(T event) {

       Class<?> clazz = event.getClass();

       incrementCount(clazz, _eventHandlerCount);

        Object recent = _recent.get(clazz);
        if (recent == null || event != recent) {
            incrementCount(clazz, _eventCount);
            _recent.put(clazz, event);
            _pendingCancel.remove(clazz);
        }

        return !_pendingCancel.contains(clazz);
    }

    /**
     * Get the number of times an event was handled.
     *
     * @param eventClass  The event class.
     */
    public static synchronized int countHandlers(Class<?> eventClass) {
        Integer integer = _eventHandlerCount.get(eventClass);
        return integer != null ? integer : 0;
    }

    /**
     * Get the number of times an event was called.
     *
     * @param eventClass  The event class.
     */
    public static synchronized int countEvent(Class<?> eventClass) {
        Integer integer = _eventCount.get(eventClass);
        return integer != null ? integer : 0;
    }

    /**
     * Reset counters for an event.
     *
     * @param eventClass  The event class.
     */
    public static synchronized void reset(Class<?> eventClass) {
        _eventHandlerCount.remove(eventClass);
        _eventCount.remove(eventClass);
    }

    /**
     * Cancel the next call to the specified event.
     *
     * @param eventClass  The event class.
     */
    public static synchronized void cancelNextEvent(Class<? extends Cancellable> eventClass) {
        _pendingCancel.add(eventClass);
    }


    private static synchronized void incrementCount(Class<?> clazz, Map<Class<?>, Integer> map) {
        Integer count = map.get(clazz);
        if (count == null)
            count = 0;

        count++;
        map.put(clazz, count);
    }
}
