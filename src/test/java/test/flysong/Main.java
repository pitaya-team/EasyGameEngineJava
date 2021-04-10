package test.flysong;

import io.theflysong.github.Init;
import io.theflysong.github.render.buffer.VertexBuffer;
import io.theflysong.github.render.buffer.VertexBufferFormat;
import io.theflysong.github.render.shader.Shader;
import io.theflysong.github.render.texture.Texture2D;
import io.theflysong.github.resource.ResourceLoader;
import io.theflysong.github.resource.ResourceLocation;
import io.theflysong.github.util.math.Vec4f;
import io.theflysong.github.window.Window;

import java.io.IOException;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

public class Main {
    public static void main(String[] args) throws InterruptedException, IOException {
        Init.init();

        System.setProperty("project.debug_mode", "true");
        Window window = new Window(500, 400, "Flysong", new Vec4f(0.2f, 0.3f, 0.3f, 1.0f));
        Shader shader = ResourceLoader.loadShader(new ResourceLocation("flysong$test"));

        VertexBuffer buffer = new VertexBuffer("default");

        buffer.matrix.push();
        buffer.matrix.scale(3, 3, 3);
        buffer.add(VertexBuffer.getBuilder(
                shader, VertexBufferFormat.VER3_COLOR3_TEX2).
                pos(0.3f, 0.3f, 0.0f).color(1.0f, 1.0f, 0.0f).texture(1.0f, 1.0f).
                pos(0.3f, -0.3f, 0.0f).color(1.0f, 1.0f, 0.0f).texture(1.0f, 0.0f).
                pos(-0.3f, -0.3f, 0.0f).color(1.0f, 1.0f, 0.0f).texture(0.0f, 0.0f).
                pos(-0.3f, 0.3f, 0.0f).color(1.0f, 1.0f, 0.0f).texture(0.0f, 1.0f).
                index(0, 1, 3).
                index(1, 2, 3)
        );
        buffer.matrix.pop();

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

        Texture2D tex2D = ResourceLoader.loadTexture2D(new ResourceLocation("flysong$container.jpg"));
        glUseProgram(shader.getProgram());
        glUniform1i(shader.getUniformLocation("texture1"), 0);

        do {
            glClear(GL_COLOR_BUFFER_BIT|GL_DEPTH_BUFFER_BIT);
            tex2D.bindTexture(0);
            buffer.draw();
        }
        while (! window.update());
    }
}
