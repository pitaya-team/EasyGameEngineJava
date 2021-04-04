package io.theflysong.github;

import io.theflysong.github.util.IUpdater;
import io.theflysong.github.window.Window;

public class Updater implements IUpdater {
    public Window window;

    public Updater setWindow(Window window) {
        this.window = window;
        return this;
    }

    @Override
    public void update() throws Exception {
        if (window != null) {
            window.update();
        }
    }
}
