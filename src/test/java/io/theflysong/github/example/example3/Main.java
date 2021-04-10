package io.theflysong.github.example.example3;

import io.theflysong.github.Init;
import io.theflysong.github.Updater;
import io.theflysong.github.render.buffer.VertexBuffer;
import io.theflysong.github.render.buffer.VertexBufferFormat;
import io.theflysong.github.render.shader.Shader;
import io.theflysong.github.resource.ResourceLocation;
import io.theflysong.github.resource.ResourceLoader;
import io.theflysong.github.util.math.MatrixStack;
import io.theflysong.github.util.math.Vec4f;
import io.theflysong.github.window.Window;

import static org.lwjgl.opengl.GL15.*;

public class Main {
    public static void main(String[] args) throws Exception {
        Init.init();

        System.setProperty("project.debug_mode", "true");
        Window window = new Window(500, 400, "Example3", new Vec4f(0.2f, 0.3f, 0.3f, 1.0f));
        Updater updater = new Updater().setWindow(window);
        Shader shader = ResourceLoader.loadShader(
                new ResourceLocation("example3$test")
        );

        VertexBuffer buffer = new VertexBuffer("default");

        buffer.add(VertexBuffer.getBuilder(
                shader, VertexBufferFormat.VER3_COLOR3).
                pos(0.3f, 0.3f, 0.0f).color(1.0f, 1.0f, 0.0f).
                pos(0.3f, -0.3f, 0.0f).color(1.0f, 1.0f, 0.0f).
                pos(-0.3f, -0.3f, 0.0f).color(1.0f, 1.0f, 0.0f).
                pos(-0.3f, 0.3f, 0.0f).color(1.0f, 1.0f, 0.0f).
                index(0, 1, 3).
                index(1, 2, 3)
        );

        buffer.add(VertexBuffer.getBuilder(
                shader, VertexBufferFormat.VER3_COLOR3).
                pos(1.0f, 1.0f, 0.0f).color(1.0f, 0.0f, 0.0f).
                pos(1.0f, 0.5f, 0.0f).color(0.0f, 1.0f, 0.0f).
                pos(0.5f, 0.5f, 0.0f).color(0.0f, 0.0f, 1.0f).
                pos(0.5f, 1.0f, 0.0f).color(1.0f, 0.0f, 1.0f).
                index(0, 1, 3).
                index(1, 2, 3)
        );

        buffer.init();

        do {
            glClear(GL_COLOR_BUFFER_BIT|GL_DEPTH_BUFFER_BIT);
            buffer.draw();
            updater.update();
        }
        while (! window.shouldClose());
    }
}
