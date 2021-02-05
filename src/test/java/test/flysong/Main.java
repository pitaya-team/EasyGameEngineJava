package test.flysong;

import io.theflysong.github.render.buffer.VertexBuffer;
import io.theflysong.github.render.buffer.VertexBufferFormat;
import io.theflysong.github.render.shader.Shader;
import io.theflysong.github.resource.ResourceLocation;
import io.theflysong.github.util.ResourceLoader;
import io.theflysong.github.util.math.MatrixStack;
import io.theflysong.github.util.math.Vec4f;
import io.theflysong.github.window.Window;
import org.lwjgl.system.MemoryStack;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        System.setProperty("project.debug_mode", "true");
        Window window = new Window(500, 400, "Example3", new Vec4f(0.2f, 0.3f, 0.3f, 1.0f));
        Shader shader = ResourceLoader.loadShader(
                new ResourceLocation("flysong$test"),
                new ResourceLocation("flysong$test"),
                new ResourceLocation("flysong$test")
        );

        VertexBuffer buffer = new VertexBuffer();

        buffer.add(VertexBuffer.getBuilder(
                shader, new VertexBufferFormat().
                        addVertex3F().
                        addColor3F()).
                pos(0.3f, 0.3f, 0.0f).color(1.0f, 1.0f, 0.0f).
                pos(0.3f, -0.3f, 0.0f).color(1.0f, 1.0f, 0.0f).
                pos(-0.3f, -0.3f, 0.0f).color(1.0f, 1.0f, 0.0f).
                pos(-0.3f, 0.3f, 0.0f).color(1.0f, 1.0f, 0.0f).
                index(0, 1, 3).
                index(1, 2, 3)
                .matrix(
                        (matrixStack) -> {
                            matrixStack.translate(-0.3f, -0.3f, 0.0f);
                            matrixStack.scale(1.5f, 1.5f, 1.5f);
                            return matrixStack;
                        }
                )
        );

        buffer.add(VertexBuffer.getBuilder(
                shader, new VertexBufferFormat().
                        addVertex3F().
                        addColor3F()).
                pos(1.0f, 1.0f, 0.0f).color(1.0f, 0.0f, 0.0f).
                pos(1.0f, 0.5f, 0.0f).color(0.0f, 1.0f, 0.0f).
                pos(0.5f, 0.5f, 0.0f).color(0.0f, 0.0f, 1.0f).
                pos(0.5f, 1.0f, 0.0f).color(1.0f, 0.0f, 1.0f).
                index(0, 1, 3).
                index(1, 2, 3)
        );

        buffer.init();
        MatrixStack matrixStack = new MatrixStack();

        do {
            MemoryStack.stackPush();
            glClear(GL_COLOR_BUFFER_BIT|GL_DEPTH_BUFFER_BIT);
            buffer.draw(matrixStack);
            MemoryStack.stackPop();
        }
        while (! window.update());
    }
}
