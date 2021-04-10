package io.theflysong.github.registry;

import io.theflysong.github.event.Events;
import io.theflysong.github.event.IEvent;
import io.theflysong.github.resource.ResourceLocation;

@Events.Event({"easygame$generic"})
public class RegisterEvent<T extends IRegistryEntry> implements IEvent {
    public final IRegister<T> register;
    public final ResourceLocation type;

    public RegisterEvent() {
        register = null;
        type = null;
    }

    public RegisterEvent(IRegister<T> register) {
        this.register = register;
        type = register.getType();
    }

    @Override
    public ResourceLocation getId() {
        return new ResourceLocation("easygame$register");
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
