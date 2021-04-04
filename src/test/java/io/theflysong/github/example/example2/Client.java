package io.theflysong.github.example.example2;

import io.theflysong.github.network.AbstractClient;

import java.io.IOException;
import java.net.InetAddress;

public class Client extends AbstractClient {
    public Client(InetAddress serverIP, int port) throws IOException {
        super(serverIP, port);
    }

    @Override
    public void run() {
        super.run();
        manager.sendPack(new TestPack("Hello, Server!"));
    }

    @Override
    public void update() {
        super.update();
    }
}
