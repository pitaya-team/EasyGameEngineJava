package io.theflysong.github.network;

import io.theflysong.github.network.pack.PackManager;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public abstract class AbstractClient extends AbstractDist {
    protected Socket server;
    protected PackManager manager;

    protected DataOutputStream getC2SSendStream() throws IOException {
        return new DataOutputStream(server.getOutputStream());
    }

    protected DataInputStream getS2CReceiveStream() throws IOException {
        return new DataInputStream(server.getInputStream());
    }

    protected AbstractClient(InetAddress serverIP, int port) throws IOException {
        super(Dist.CLIENT);
        server = new Socket(serverIP, port);
        manager = new PackManager(getS2CReceiveStream(), getC2SSendStream(), -1, this);
    }

    @Override
    public void run() {
        manager.sendPack(new PackManager.RegisterUUIDPack(UUID.randomUUID()));
    }

    @Override
    public List<PackManager> managers() {
        return Collections.singletonList(manager);
    }

    @Override
    public void update() {
        try {
            manager.update();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
