package io.theflysong.github.example.example1;

import io.theflysong.github.Init;
import io.theflysong.github.Updater;
import io.theflysong.github.util.math.Vec4f;
import io.theflysong.github.window.Window;

import static org.lwjgl.opengl.GL11.*;



public class Main {
    public static void main(String[] args) throws Exception {
        Window window = new Window(500, 500, "Example1", new Vec4f(1.0f, 1.0f, 0.0f, 1.0f));
        Init.init();
        Updater updater = new Updater().setWindow(window);

        do {
            glClear(GL_COLOR_BUFFER_BIT|GL_DEPTH_BUFFER_BIT);
            updater.update();
        }
        while (! window.shouldClose());
    }
}
