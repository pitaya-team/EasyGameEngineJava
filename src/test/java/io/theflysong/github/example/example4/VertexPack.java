package io.theflysong.github.example.example4;

import io.theflysong.github.network.pack.IPack;
import io.theflysong.github.network.pack.PackManager;
import io.theflysong.github.resource.ResourceLocation;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

@PackManager.Pack("example4$vertex")
public class VertexPack  implements IPack {
    public float[] vertices;

    public VertexPack() {
    }

    public VertexPack(float[] vertices) {
        this.vertices = vertices;
    }

    @Override
    public ResourceLocation getId() {
        return new ResourceLocation("example4$vertex");
    }

    @Override
    public void send(DataOutputStream stream) throws IOException {
        stream.writeInt(vertices.length);
        for (float f : vertices)
            stream.writeFloat(f);
    }

    @Override
    public void receive(DataInputStream stream) throws IOException {
        int length = stream.readInt();
        vertices = new float[length];
        for (int i = 0 ; i < length ; i ++) {
            vertices[i] = stream.readFloat();
        }
    }

    @Override
    public void handler(PackManager manager) {
        Client.pack = this;
    }
}
