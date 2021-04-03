package io.theflysong.github.event;

import io.theflysong.github.resource.ResourceLocation;

public interface IEvent {
    ResourceLocation getId();
    boolean canCancel();
    boolean isCanceled();
    void cancel();
}
