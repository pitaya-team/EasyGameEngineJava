package io.theflysong.github.game;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public abstract class AbstractClient extends AbstractGame {
    protected Socket server;
    protected DataOutputStream sendStream;
    protected DataInputStream receiveStream;

    protected DataOutputStream getC2SSendStream() throws IOException {
        return new DataOutputStream(server.getOutputStream());
    }

    protected DataInputStream getS2CReceiveStream() throws IOException {
        return new DataInputStream(server.getInputStream());
    }

    protected AbstractClient(InetAddress serverIP, int port) throws IOException {
        super(Dist.CLIENT);
        server = new Socket(serverIP, port);
        sendStream = getC2SSendStream();
        receiveStream = getS2CReceiveStream();
    }
}
