package io.theflysong.github.registry;

import io.theflysong.github.resource.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public interface IRegister<T extends IRegistryEntry> {
    List<IRegister<? extends IRegistryEntry>> registers = new ArrayList<>();
    void register(ResourceLocation id, T value);
    T getByID(ResourceLocation id);
    ResourceLocation getType();
}
