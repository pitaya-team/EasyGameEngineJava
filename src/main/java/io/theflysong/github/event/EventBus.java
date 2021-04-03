package io.theflysong.github.event;

import io.theflysong.github.resource.ResourceLocation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class EventBus implements IEventBus{
    private Map<ResourceLocation, List<Object>> listeners = new HashMap<>();
    public static Map<ResourceLocation, IEventBus> busMap = new HashMap<>();

    public static void register(String name, IEventBus bus) {
        busMap.put(new ResourceLocation(name), bus);
    }

    public static IEventBus get(String name) {
        return busMap.get(new ResourceLocation(name));
    }

    public static boolean contains(String name) {
        return busMap.containsKey(new ResourceLocation(name));
    }

    @Override
    public <T extends IEvent> void addEvent(T event) {
        listeners.put(event.getId(), new ArrayList<>());
    }

    @Override
    public <T extends IEvent> void postEvent(T event) {
        for (Object listener : listeners.get(event.getId())) {
            if (listener instanceof Consumer) {
                ((Consumer)listener).accept(event);
            }
        }
    }

    @Override
    public <T extends IEvent> void addListener(T event, Consumer<T> listener) {
        listeners.get(event.getId()).add(listener);
    }
}
