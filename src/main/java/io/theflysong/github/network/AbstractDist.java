package io.theflysong.github.network;

import io.theflysong.github.util.IUpdater;

public abstract class AbstractDist implements Runnable, IUpdater {
    public Dist dist;

    public AbstractDist(Dist dist) {
        this.dist = dist;
    }
}
