package io.theflysong.github.example.example1;

import io.theflysong.github.window.Window;
import org.lwjgl.opengl.GL11;

public class Main {
    public static void main(String[] args) {
        Window window = new Window(200, 200, "Test", 30);

        GL11.glClearColor(0.0f, 1.0f, 0.0f, 1.0f);

        do {
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        }
        while (window.update());
    }
}
