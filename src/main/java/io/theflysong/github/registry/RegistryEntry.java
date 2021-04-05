package io.theflysong.github.registry;

import io.theflysong.github.resource.ResourceLocation;

public abstract class RegistryEntry implements IRegistryEntry {
    protected ResourceLocation id;

    public RegistryEntry(ResourceLocation id) {
        this.id = id;
    }

    @Override
    public ResourceLocation getID() {
        return id;
    }
}
