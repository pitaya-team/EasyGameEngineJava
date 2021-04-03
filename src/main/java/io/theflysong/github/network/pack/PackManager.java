package io.theflysong.github.network.pack;

import io.theflysong.github.util.IUpdater;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.List;

public class PackManager implements IUpdater {
    protected DataInputStream input;
    protected DataOutputStream output;
    protected List<IPack> packs = new ArrayList<>();

    public PackManager(DataInputStream input, DataOutputStream output) {
        this.input = input;
        this.output = output;
    }

    @Override
    public void update() {
        for (IPack pack : packs) {
            pack.send(output);
        }

    }
}
