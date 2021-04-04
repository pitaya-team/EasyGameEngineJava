package io.theflysong.github.network.pack;

import io.theflysong.github.resource.ResourceLocation;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public interface IPack {
    ResourceLocation getId();
    void send(DataOutputStream stream) throws IOException;
    void receive(DataInputStream stream) throws IOException;
    void handler(PackManager manager);
}
