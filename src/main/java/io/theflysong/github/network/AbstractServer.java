package io.theflysong.github.network;

import io.theflysong.github.network.pack.PackManager;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public abstract class AbstractServer extends AbstractDist {
    protected List<Socket> clients = new ArrayList();
    protected List<PackManager> managers = new ArrayList();
    protected Map<UUID, Integer> clientMap = new HashMap<>();
    protected ServerSocket server;

    protected DataOutputStream getS2CSendStream(int client) throws IOException {
        return new DataOutputStream(clients.get(client).getOutputStream());
    }

    protected DataInputStream getC2SReceiveStream(int client) throws IOException {
        return new DataInputStream(clients.get(client).getInputStream());
    }

    public void registerClientUUID(UUID uuid, int no) {
        clientMap.put(uuid, no);
    }

    public int getNumWithUUID(UUID uuid) {
        return clientMap.get(uuid);
    }

    @Override
    public void run() {

    }

    protected void accept() throws IOException {
        clients.add(server.accept());
        managers.add(
                new PackManager(
                        getC2SReceiveStream(clients.size() - 1),
                        getS2CSendStream(clients.size() - 1),
                        clients.size() - 1, this
                ));
    }

    protected AbstractServer(int port) throws IOException {
        super(Dist.SERVER);
        this.server = new ServerSocket(port);
        this.server.setSoTimeout(60000);
    }

    @Override
    public List<PackManager> managers() {
        return managers;
    }

    @Override
    public void update() {
        try {
            for (PackManager manager : managers) {
                manager.update();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
