package io.theflysong.github;

import io.theflysong.github.event.Events;
import io.theflysong.github.network.pack.PackManager;
import io.theflysong.github.registry.IRegister;
import io.theflysong.github.registry.RegisterEvent;

public class Init {
    public static void init() {
        Events.init();
        PackManager.init();
        for (IRegister register : IRegister.registers) {
            Events.GENERIC.postEvent(new RegisterEvent<>(register));
        }
    }
}
