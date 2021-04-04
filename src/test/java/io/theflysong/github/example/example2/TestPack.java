package io.theflysong.github.example.example2;

import io.theflysong.github.network.pack.IPack;
import io.theflysong.github.network.pack.PackManager;
import io.theflysong.github.resource.ResourceLocation;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

@PackManager.Pack("example2$test")
public class TestPack implements IPack {
    private String msg;

    public TestPack() {
    }

    public TestPack(String msg) {
        this.msg = msg;
    }

    @Override
    public ResourceLocation getId() {
        return new ResourceLocation("example2$test");
    }

    @Override
    public void send(DataOutputStream stream) throws IOException {
        stream.writeUTF(msg);
    }

    @Override
    public void receive(DataInputStream stream) throws IOException {
        msg = stream.readUTF();
    }

    @Override
    public void handler(PackManager manager) {
        System.out.println(msg);
    }
}
