package io.theflysong.github.example.example4;

import io.theflysong.github.network.AbstractServer;

import java.io.IOException;

public class Server extends AbstractServer {
    private static float[] vertices = {
            0.3f, 0.3f, 0.0f,1.0f, 1.0f, 0.0f,
            0.3f, -0.3f, 0.0f,1.0f, 1.0f, 0.0f,
            -0.3f, -0.3f, 0.0f,1.0f, 1.0f, 0.0f,
            -0.3f, 0.3f, 0.0f,1.0f, 1.0f, 0.0f
    };

    public Server(int port) throws IOException {
        super(port);
    }

    @Override
    public void run() {
        super.run();
        try {
            accept();
            managers.get(managers.size() - 1).sendPack(new VertexPack(vertices));
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (true) {
            update();
        }
    }

    @Override
    public void update() {
        super.update();
    }
}
