package io.theflysong.github.network.pack;

import io.theflysong.github.network.AbstractDist;
import io.theflysong.github.network.AbstractServer;
import io.theflysong.github.network.Dist;
import io.theflysong.github.resource.ResourceLocation;
import io.theflysong.github.util.IUpdater;
import io.theflysong.github.util.PackageUtils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.*;
import java.util.concurrent.Callable;

public class PackManager implements IUpdater {
    protected static Map<ResourceLocation, Callable<IPack>> packFactories = new HashMap<>();
    protected DataInputStream input;
    protected DataOutputStream output;
    protected int num;
    protected AbstractDist dist;
    protected List<IPack> packs = new ArrayList<>();

    public static void register(ResourceLocation id, Callable<IPack> pack) {
        packFactories.put(id, pack);
    }

    public static Map<ResourceLocation, Callable<IPack>> getPackFactories() {
        return packFactories;
    }

    public int getNum() {
        return num;
    }

    public AbstractDist getDist() {
        return dist;
    }

    public PackManager(DataInputStream input, DataOutputStream output, int num, AbstractDist dist) {
        this.input = input;
        this.output = output;
        this.num = num;
        this.dist = dist;
    }

    public void sendPack(IPack pack) {
        if (! packFactories.containsKey(pack.getId())) {
            throw new IllegalArgumentException("Unregistered Pack \"" + pack.getId() + "\"!");
        }
        packs.add(pack);
    }

    @Override
    public void update() throws Exception {
        for (IPack pack : packs) {
            output.writeUTF(pack.getId().toString());
            pack.send(output);
        }
        packs.clear();
        while (input.available() != 0) {
            ResourceLocation id = new ResourceLocation(input.readUTF());
            IPack pack = packFactories.get(id).call();
            pack.receive(input);
            pack.handler(this);
        }
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface Pack {
        String value();
    }

    private static void registerPacks() {
        Set<String> classes = PackageUtils.getAllClass();
        for (String clazz_n : classes) {
            if (clazz_n.startsWith("org.lwjgl") || clazz_n.startsWith("org.joml") || clazz_n.startsWith("com.google.gson"))
                continue;
            try {
                Class<?> clazz = Class.forName(clazz_n);
                if (clazz.isAnnotationPresent(Pack.class)) {
                    if (! Arrays.stream(clazz.getInterfaces()).anyMatch(c -> c == IPack.class)) {
                        throw new IllegalStateException("You should never put a @Pack in any non-pack class");
                    }

                    String packName = clazz.getAnnotation(Pack.class).value();

                    register(new ResourceLocation(packName), () -> (IPack) clazz.getConstructor().newInstance());
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public static void init() {
        registerPacks();
    }

    @Pack("easygame$register_uuid")
    public static class RegisterUUIDPack implements IPack {
        private UUID uuid;

        public RegisterUUIDPack() {
        }

        public RegisterUUIDPack(UUID uuid) {
            this.uuid = uuid;
        }

        @Override
        public ResourceLocation getId() {
            return new ResourceLocation("easygame$register_uuid");
        }

        @Override
        public void send(DataOutputStream stream) throws IOException {
            stream.writeUTF(uuid.toString());
        }

        @Override
        public void receive(DataInputStream stream) throws IOException {
            uuid = UUID.fromString(stream.readUTF());
        }

        @Override
        public void handler(PackManager manager) {
            if (manager.getDist().dist != Dist.SERVER) {
                AbstractServer server = (AbstractServer) manager.getDist();
                server.registerClientUUID(uuid, manager.num);
            }
        }
    }
}
