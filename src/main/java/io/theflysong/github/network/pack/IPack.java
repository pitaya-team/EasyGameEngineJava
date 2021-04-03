package io.theflysong.github.network.pack;

import java.io.DataInputStream;
import java.io.DataOutputStream;

public interface IPack {
    String getId();
    void send(DataOutputStream stream);
    void receive(DataInputStream stream);
    void handler(PackManager manager);
}
