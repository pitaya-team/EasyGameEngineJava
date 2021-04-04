package io.theflysong.github.network;

import io.theflysong.github.network.pack.PackManager;
import io.theflysong.github.util.IUpdater;

import java.util.List;

public abstract class AbstractDist implements Runnable, IUpdater {
    public Dist dist;

    public AbstractDist(Dist dist) {
        this.dist = dist;
    }

    public abstract List<PackManager> managers();
}
