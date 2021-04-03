package io.theflysong.github.event;

import io.theflysong.github.resource.ResourceLocation;
import io.theflysong.github.util.PackageUtils;

import java.lang.annotation.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Set;

@Events.SubscribeOrBusIn()
public class Events {
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface Event {
        String[] value();
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public @interface Subscribe {
        String[] value();
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface SubscribeOrBusIn {
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    public @interface Bus {
        String value();
    }

    @Bus("easygame$generic")
    public static IEventBus GENERIC;

    @Event({"easygame$generic"})
    public static class InitEvent implements IEvent {
        @Override
        public ResourceLocation getId() {
            return new ResourceLocation("easygame$init");
        }

        @Override
        public boolean canCancel() {
            return false;
        }

        @Override
        public boolean isCanceled() {
            return false;
        }

        @Override
        public void cancel() {
        }
    }

    @Subscribe({"easygame$generic"})
    public static void ListenerInit(InitEvent event) {
        System.out.println("Initializing");
    }

    public static void RegisterEvents() {
        Set<String> classes = PackageUtils.getAllClass();
        for (String clazz_n : classes) {
            if (clazz_n.startsWith("org.lwjgl") || clazz_n.startsWith("org.joml"))
                continue;
            try {
                Class<?> clazz = Class.forName(clazz_n);
                if (clazz.isAnnotationPresent(Event.class)) {
                    if (! Arrays.stream(clazz.getInterfaces()).anyMatch(c -> c == IEvent.class)) {
                        throw new IllegalStateException("You should never put a @Event in any non-event class");
                    }
                    String[] busNames = clazz.getAnnotation(Event.class).value();

                    for (String busName : busNames) {
                        if (! EventBus.contains(busName)) {
                            throw new IllegalStateException("No bus " + busName + " !");
                        }
                        EventBus.get(busName).addEvent((IEvent)clazz.getConstructor().newInstance());
                    }
                }
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public static void RegisterListeners() {
        Set<String> classes = PackageUtils.getAllClass();
        for (String clazz_n : classes) {
            if (clazz_n.startsWith("org.lwjgl") || clazz_n.startsWith("org.joml"))
                continue;
            try {
                Class<?> clazz = Class.forName(clazz_n);
                if (clazz.isAnnotationPresent(SubscribeOrBusIn.class)) {
                    Method[] methods = clazz.getMethods();
                    for (Method method : methods) {
                        if (method.isAnnotationPresent(Subscribe.class)) {
                            Class<?>[] params = method.getParameterTypes();
                            if (params.length != 1) {
                                throw new IllegalStateException("A Listener must only one param: The Event");
                            }

                            String[] busNames = method.getAnnotation(Subscribe.class).value();

                            for (String busName : busNames) {
                                EventBus.get(busName).addListener((IEvent)params[0].getConstructor().newInstance(), (e) -> {
                                    try {
                                        method.invoke(null, e);
                                    } catch (IllegalAccessException | InvocationTargetException illegalAccessException) {
                                        illegalAccessException.printStackTrace();
                                    }
                                });
                            }
                        }
                    }
                }
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public static void RegisterBuses() {
        Set<String> classes = PackageUtils.getAllClass();
        for (String clazz_n : classes) {
            if (clazz_n.startsWith("org.lwjgl") || clazz_n.startsWith("org.joml"))
                continue;
            try {
                Class<?> clazz = Class.forName(clazz_n);
                if (clazz.isAnnotationPresent(SubscribeOrBusIn.class)) {
                    Field[] fields = clazz.getFields();
                    for (Field field : fields) {
                        if (field.isAnnotationPresent(Bus.class)) {
                            if (! Modifier.isStatic(field.getModifiers())) {
                                throw new IllegalStateException("The type of the bus field be static");
                            }
                            if (Arrays.stream(field.getType().getInterfaces()).anyMatch(c -> c == IEventBus.class)) {
                                throw new IllegalStateException("The type of the bus field variable must extends from IEventBus");
                            }

                            String busName = field.getAnnotation(Bus.class).value();

                            EventBus.register(busName, new EventBus());
                            field.set(null, EventBus.get(busName));
                        }
                    }
                }
            } catch (ClassNotFoundException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    public static void init() {
        RegisterBuses();
        RegisterEvents();
        RegisterListeners();
        GENERIC.postEvent(new InitEvent());
    }
}
