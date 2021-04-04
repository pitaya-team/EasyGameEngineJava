package io.theflysong.github.example.example2;

import io.theflysong.github.network.AbstractServer;
import io.theflysong.github.network.pack.PackManager;

import java.io.IOException;

public class Server extends AbstractServer {
    public Server(int port) throws IOException {
        super(port);
    }

    @Override
    public void run() {
        super.run();
        try {
            accept();
            managers.get(managers.size() - 1).sendPack(new TestPack("Hello, Client!"));
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
