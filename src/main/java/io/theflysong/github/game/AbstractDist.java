package io.theflysong.github.game;

public abstract class AbstractDist implements Runnable {
    public Dist dist;

    public AbstractDist(Dist dist) {
        this.dist = dist;
    }
}
