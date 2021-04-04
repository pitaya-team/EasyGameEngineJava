package io.theflysong.github;

import io.theflysong.github.event.Events;
import io.theflysong.github.network.pack.PackManager;

public class Init {
    public static void init() {
        Events.init();
        PackManager.init();
    }
}
