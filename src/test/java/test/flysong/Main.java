package test.flysong;

import io.theflysong.github.Init;
import io.theflysong.github.render.buffer.VertexBuffer;
import io.theflysong.github.render.buffer.VertexBufferFormat;
import io.theflysong.github.render.shader.Shader;
import io.theflysong.github.render.texture.Texture2D;
import io.theflysong.github.resource.ResourceLocation;
import io.theflysong.github.resource.ResourceLoader;
import io.theflysong.github.util.math.MatrixStack;
import io.theflysong.github.util.math.Vec4f;
import io.theflysong.github.window.Window;

import java.io.*;
import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;
import static org.lwjgl.stb.STBImage.*;

//TODO: with new render api
public class Main {
    public static void main(String[] args) throws InterruptedException, IOException {
        Init.init();
        /*System.setProperty("project.debug_mode", "true");
        Window window = new Window(500, 400, "Flysong", new Vec4f(0.2f, 0.3f, 0.3f, 1.0f));
        Shader shader = ResourceLoader.loadShader(
                new ResourceLocation("flysong$test"),
                new ResourceLocation("flysong$test"),
                new ResourceLocation("flysong$test")
        );

        VertexBuffer buffer = new VertexBuffer();

        buffer.add(VertexBuffer.getBuilder(
                shader, new VertexBufferFormat().
                        addVertex3F().
                        addColor3F().
                        addTexture2F()).
                pos(0.3f, 0.3f, 0.0f).color(1.0f, 1.0f, 0.0f).texture(1.0f, 1.0f).
                pos(0.3f, -0.3f, 0.0f).color(1.0f, 1.0f, 0.0f).texture(1.0f, 0.0f).
                pos(-0.3f, -0.3f, 0.0f).color(1.0f, 1.0f, 0.0f).texture(0.0f, 0.0f).
                pos(-0.3f, 0.3f, 0.0f).color(1.0f, 1.0f, 0.0f).texture(0.0f, 1.0f).
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

        Texture2D tex2D = ResourceLoader.loadTexture2D(new ResourceLocation("flysong$container.jpg"));
        shader.use();
        glUniform1i(shader.getUniformLocation("texture1"), 0);

        do {
            glClear(GL_COLOR_BUFFER_BIT|GL_DEPTH_BUFFER_BIT);
            tex2D.bindTexture(0);
            buffer.draw(matrixStack);
        }
        while (! window.update());*/
    }
}
