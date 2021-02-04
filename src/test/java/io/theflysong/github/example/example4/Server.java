package io.theflysong.github.example.example4;

import io.theflysong.github.game.AbstractServer;

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
        try {
            accept();
            for (int i = 0 ; i < vertices.length ; i ++) {
                sendStreams.get(clients.size() - 1).writeFloat(vertices[i]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
