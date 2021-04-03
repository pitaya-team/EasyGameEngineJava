package io.theflysong.github.example.example2;

import io.theflysong.github.network.AbstractServer;

import java.io.IOException;

public class Server extends AbstractServer {
    public Server(int port) throws IOException {
        super(port);
    }

    @Override
    public void run() {
        try {
            accept();
            sendStreams.get(clients.size() - 1).writeUTF("Hello,Client!");
            System.out.println(receiveStreams.get(clients.size() - 1).readUTF());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update() {
    }
}
