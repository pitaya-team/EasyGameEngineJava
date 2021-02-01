package io.theflysong.github.example.example2;

import io.theflysong.github.game.AbstractClient;

import java.io.IOException;
import java.net.InetAddress;

public class Client extends AbstractClient {
    public Client(InetAddress serverIP, int port) throws IOException {
        super(serverIP, port);
    }

    @Override
    public void run() {
        try {
            sendStream.writeUTF("Hello,Server!");
            System.out.println(receiveStream.readUTF());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
