package io.theflysong.github.game;

public enum Dist {
    CLIENT("client"),
    SERVER("server");

    private String name;
    Dist(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
