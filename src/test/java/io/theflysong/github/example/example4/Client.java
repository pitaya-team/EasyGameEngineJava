package io.theflysong.github.example.example4;

import io.theflysong.github.Updater;
import io.theflysong.github.network.AbstractClient;
import io.theflysong.github.render.buffer.VertexBufferFormat;
import io.theflysong.github.render.buffer.VertexBufferUnit;
import io.theflysong.github.render.shader.Shader;
import io.theflysong.github.resource.ResourceLocation;
import io.theflysong.github.resource.ResourceLoader;
import io.theflysong.github.util.math.MatrixStack;
import io.theflysong.github.util.math.Vec4f;
import io.theflysong.github.window.Window;

import java.io.IOException;
import java.net.InetAddress;

import static org.lwjgl.opengl.GL11.*;

//TODO: with new renderer api
public class Client extends AbstractClient {
    public Client(InetAddress serverIP, int port) throws IOException {
        super(serverIP, port);
    }

    public static VertexPack pack = null;

    private void waitForPack() {
        while (pack == null) {
            super.update();
        }
    }

    private Window window;
    private Updater updater;
    private VertexBufferUnit unit;
    MatrixStack stack = new MatrixStack();

    @Override
    public void run() {
        super.run();
        /*window = new Window(500, 400, "Example4", new Vec4f(0.2f, 0.3f, 0.3f, 1.0f));
        updater = new Updater().setWindow(window);

        Shader shader = ResourceLoader.loadShader(
                new ResourceLocation("example4$test"),
                new ResourceLocation("example4$test"),
                new ResourceLocation("example4$test")
        );
        unit = new VertexBufferUnit(shader, new VertexBufferFormat().
                addVertex3F().
                addColor3F());

        for (int i = 0 ; i < 6 * 4 ; i ++) {
            waitForPack();
            unit.addVertex(pack.vertices[i]);
        }

        unit.addIndex(0).addIndex(1).addIndex(3).addIndex(1).addIndex(2).addIndex(3);
        unit.init();

        do {
            update();
        }
        while (! window.shouldClose());*/
    }

    @Override
    public void update() {
        super.update();
        glClear(GL_COLOR_BUFFER_BIT|GL_DEPTH_BUFFER_BIT);
        unit.use(stack);
        try {
            updater.update();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
