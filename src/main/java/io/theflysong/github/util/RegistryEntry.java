package io.theflysong.github.util;

import io.theflysong.github.resource.ResourceLocation;

public abstract class RegistryEntry implements IRegistry{
    protected ResourceLocation id;

    public RegistryEntry(ResourceLocation id) {
        this.id = id;
    }

    @Override
    public ResourceLocation getID() {
        return id;
    }
}
