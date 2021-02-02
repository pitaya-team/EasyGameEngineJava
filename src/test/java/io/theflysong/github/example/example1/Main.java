package io.theflysong.github.example.example1;

import io.theflysong.github.util.math.Vec4f;
import io.theflysong.github.window.Window;

import java.io.*;

import static org.lwjgl.opengl.GL11.*;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Window window = new Window(500, 500, "eee", new Vec4f(1.0f, 1.0f, 0.0f, 1.0f));

        DataOutputStream stream = new DataOutputStream(new OutputStream() {
            @Override
            public void write(int b) throws IOException {

            }
        });

        do {
            glClear(GL_COLOR_BUFFER_BIT|GL_DEPTH_BUFFER_BIT);
        }
        while (window.update());
    }
}
