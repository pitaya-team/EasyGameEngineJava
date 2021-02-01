package io.theflysong.github.game;

public abstract class AbstractGame implements Runnable {
    public Dist dist;

    public AbstractGame(Dist dist) {
        this.dist = dist;
    }
}
