package io.theflysong.github.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Collections;
import java.util.List;

public abstract class AbstractClient extends AbstractDist {
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

    @Override
    public List<DataOutputStream> sends() {
        return Collections.singletonList(sendStream);
    }

    @Override
    public List<DataInputStream> receives() {
        return Collections.singletonList(receiveStream);
    }
}
