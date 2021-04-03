package io.theflysong.github.event;

import java.util.function.Consumer;

public interface IEventBus {
    <T extends IEvent> void addEvent(T event);
    <T extends IEvent> void postEvent(T event);
    <T extends IEvent> void addListener(T event, Consumer<T> listener);
}
