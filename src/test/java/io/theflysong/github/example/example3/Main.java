package io.theflysong.github.example.example3;

import io.theflysong.github.render.buffer.VertexBufferUnit;
import io.theflysong.github.render.shader.Shader;
import io.theflysong.github.resource.ResourceLocation;
import io.theflysong.github.util.math.Vec4f;
import io.theflysong.github.window.Window;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        System.setProperty("project.debug_mode", "true");
        Window window = new Window(500, 400, "Example3", new Vec4f(0.2f, 0.3f, 0.3f, 1.0f));
        Shader shader = new Shader(
                new ResourceLocation("example3$test"),
                new ResourceLocation("example3$test")
        );
        VertexBufferUnit unit = new VertexBufferUnit(shader);
        unit.addVertex(0.5f).addVertex(0.5f).addVertex(0.0f).
                addVertex(0.5f).addVertex(-0.5f).addVertex(0.0f).
                addVertex(-0.5f).addVertex(-0.5f).addVertex(0.0f).
                addVertex(-0.5f).addVertex(0.5f).addVertex(0.0f).
                addIndex(0).addIndex(1).addIndex(3).
                addIndex(1).addIndex(2).addIndex(3);
        int VBO = glGenBuffers();
        int VAO = glGenVertexArrays();
        int IBO = glGenBuffers();
        glBindVertexArray(VAO);
        glBindBuffer(GL_ARRAY_BUFFER, VBO);
        glBufferData(GL_ARRAY_BUFFER, unit.Vertices2Float(), GL_STATIC_DRAW);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, IBO);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, unit.Indices2Int(), GL_STATIC_DRAW);

        glVertexAttribPointer(0, 3, GL_FLOAT, false, 3 * Float.BYTES, 0);
        glEnableVertexAttribArray(0);

        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindVertexArray(0);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);

        do {
            glClear(GL_COLOR_BUFFER_BIT|GL_DEPTH_BUFFER_BIT);
            glUseProgram(shader.getProgram());

            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, IBO);
            glBindVertexArray(VAO);
            glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0);
            glBindVertexArray(0);
        }
        while (! window.update());

        glDeleteVertexArrays(VAO);
        glDeleteBuffers(VBO);
        glDeleteBuffers(IBO);
    }
}
