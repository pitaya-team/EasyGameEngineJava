package io.theflysong.github.example.example4;

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

public class Client extends AbstractClient {
    public Client(InetAddress serverIP, int port) throws IOException {
        super(serverIP, port);
    }

    @Override
    public void run() {
        System.setProperty("project.debug_mode", "true");
        Window window = new Window(500, 400, "Example4", new Vec4f(0.2f, 0.3f, 0.3f, 1.0f));
        Shader shader = ResourceLoader.loadShader(
                new ResourceLocation("example4$test"),
                new ResourceLocation("example4$test"),
                new ResourceLocation("example4$test")
        );
        VertexBufferUnit unit = new VertexBufferUnit(shader, new VertexBufferFormat().
                addVertex3F().
                addColor3F());
        try {
            for (int i = 0 ; i < 6 * 4 ; i ++) {
                unit.addVertex(receiveStream.readFloat());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        unit.addIndex(0).addIndex(1).addIndex(3).addIndex(1).addIndex(2).addIndex(3);
        unit.init();

        MatrixStack stack = new MatrixStack();

        try {
            do {
                glClear(GL_COLOR_BUFFER_BIT|GL_DEPTH_BUFFER_BIT);
                unit.use(stack);
            }
            while (! window.update());
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update() {
    }
}
