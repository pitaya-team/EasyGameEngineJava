package io.theflysong.github.registry;

import io.theflysong.github.resource.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public class Register<T extends IRegistryEntry> implements IRegister<T>{
    protected Map<ResourceLocation, T> values = new HashMap<>();
    private ResourceLocation type;

    public Register(String type) {
        this(new ResourceLocation(type));
    }

    public Register(ResourceLocation type) {
        registers.add(this);
        this.type = type;
    }

    @Override
    public void register(ResourceLocation id, T value) {
        values.put(id, value);
    }

    @Override
    public T getByID(ResourceLocation id) {
        return values.get(id);
    }

    @Override
    public ResourceLocation getType() {
        return type;
    }
}
