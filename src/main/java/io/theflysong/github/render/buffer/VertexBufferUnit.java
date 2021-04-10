package io.theflysong.github.render.buffer;

import io.theflysong.github.render.shader.Shader;
import io.theflysong.github.util.math.MatrixStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL30.*;

public class VertexBufferUnit {
    protected int VAO;
    protected int VBO;
    protected int IBO;
    protected Shader shader;
    protected ArrayList<Float> vertices = new ArrayList<>();
    protected ArrayList<Integer> indices = new ArrayList<>();
    protected VertexBufferFormat format;
    protected int drawMode;
    protected MatrixStack stack;

    public VertexBufferUnit transform(MatrixStack stack) {
        try {
            this.stack = stack.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return this;
    }

    public VertexBufferUnit(Shader shader, VertexBufferFormat format) {
        this(shader, format, GL_STATIC_DRAW);
    }

    public VertexBufferUnit(Shader shader, VertexBufferFormat format, int drawMode) {
        VAO = glGenVertexArrays();
        VBO = glGenBuffers();
        IBO = glGenBuffers();
        this.shader = shader;
        this.format = format;
        this.drawMode = drawMode;
    }

    public VertexBufferUnit addIndex(int index) {
        indices.add(index);
        return this;
    }

    public VertexBufferUnit addVertex(float vertex) {
        vertices.add(vertex);
        return this;
    }

    public float[] getVertices() {
        return Vertices2Float();
    }

    protected float[] Vertices2Float() {
        float[] array = new float[vertices.size()];
        int i = 0;

        for (Float vertex : vertices) {
            array[i++] = (vertex != null ? vertex : Float.NaN);
        }
        return array;
    }

    public int[] getIndices() {
        return Indices2Int();
    }

    protected int[] Indices2Int() {
        int[] array = new int[indices.size()];
        int i = 0;

        for (Integer index : indices) {
            array[i++] = index;
        }
        return array;
    }

    public void init() {
        glBindVertexArray(VAO);
        glBindBuffer(GL_ARRAY_BUFFER, VBO);
        glBufferData(GL_ARRAY_BUFFER, Vertices2Float(), drawMode);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, IBO);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, Indices2Int(), drawMode);

        List<Map.Entry<String, Byte>> layout = format.formats;
        int stride = 0;

        for (Map.Entry<String, Byte> i : layout) {
            stride += i.getValue();
        }

        stride *= Float.BYTES;

        int offset = 0;
        for (Map.Entry<String, Byte> i : layout) {
            int index = shader.getInfo().findLocation(i.getKey());
            glVertexAttribPointer(index, i.getValue(), GL_FLOAT,false, stride,offset);
            glEnableVertexAttribArray(index);
            offset += i.getValue() * Float.BYTES;
        }

        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindVertexArray(0);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
    }

    public void use() {
        shader.use(this);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, IBO);
        glBindVertexArray(VAO);
        glDrawElements(GL_TRIANGLES, indices.size(), GL_UNSIGNED_INT, 0);
        glBindVertexArray(0);
    }

    @Override
    protected void finalize() {
        glDeleteVertexArrays(VAO);
        glDeleteBuffers(VBO);
        glDeleteBuffers(IBO);
    }

    public MatrixStack getTransform() {
        return stack;
    }
}
