package io.theflysong.github;

import io.theflysong.github.network.AbstractDist;
import io.theflysong.github.network.pack.PackManager;
import io.theflysong.github.util.IUpdater;
import io.theflysong.github.window.Window;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.List;

public class Updater implements IUpdater {
    public Window window;
    public AbstractDist dist;
    public List<PackManager> packManagers = new ArrayList<>();

    public Updater setWindow(Window window) {
        this.window = window;
        return this;
    }

    public Updater setDist(AbstractDist dist) {
        this.dist = dist;
        packManagers.clear();
        if (dist == null)
            return this;
        int num = 0;
        List<DataOutputStream> sends = dist.sends();
        List<DataInputStream> receives = dist.receives();
        while (num <= sends.size()) {
            packManagers.add(new PackManager(receives.get(num), sends.get(num), num));
            num ++;
        }
        return this;
    }

    @Override
    public void update() throws Exception {
        if (window != null) {
            window.update();
        }
        for (PackManager packManager : packManagers) {
            packManager.update();
        }
    }
}
