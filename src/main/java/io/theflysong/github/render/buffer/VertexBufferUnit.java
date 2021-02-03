package io.theflysong.github.render.buffer;

import io.theflysong.github.render.shader.Shader;

import java.util.ArrayList;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL30.*;

public class VertexBufferUnit {
    protected int VAO = 0;
    protected int VBO = 0;
    protected int IBO = 0;
    protected Shader shader;
    public ArrayList<Float> vertices = new ArrayList<>();
    public ArrayList<Integer> indices = new ArrayList<>();
    protected VertexBufferFormat format;

    public VertexBufferUnit(Shader shader) {
        VAO = glGenVertexArrays();
        VBO = glGenBuffers();
        IBO = glGenBuffers();
        this.shader = shader;
    }

    public VertexBufferUnit addIndex(int index) {
        indices.add(index);
        return this;
    }

    public VertexBufferUnit addVertex(float vertex) {
        vertices.add(vertex);
        return this;
    }

    public float[] Vertices2Float() {
        float[] array = new float[vertices.size()];
        int i = 0;

        for (Float vertex : vertices) {
            array[i++] = (vertex != null ? vertex : Float.NaN);
        }
        return array;
    }

    public int[] Indices2Int() {
        int[] array = new int[indices.size()];
        int i = 0;

        for (Integer index : indices) {
            array[i++] = index;
        }
        return array;
    }

    public void start() {

    }
}
