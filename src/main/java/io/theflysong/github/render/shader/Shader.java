package io.theflysong.github.render.shader;

import io.theflysong.github.resource.ResourceLocation;
import io.theflysong.github.util.RegistryEntry;

import static org.lwjgl.opengl.GL20.*;

public class Shader extends RegistryEntry {
    protected int shader;

    public Shader(char[] vertex, char[] fragment, ResourceLocation id) {
        super(id);
        int vertexId, fragmentId;
        int[] status = new int[1];

        vertexId = glCreateShader(GL_VERTEX_SHADER);
        glShaderSource(vertexId, new String(vertex));
        glCompileShader(vertexId);

        glGetShaderiv(vertexId, GL_COMPILE_STATUS, status);
        if(status[0] == 0) {
            String log = glGetShaderInfoLog(vertexId, 32767);
            throw new IllegalStateException("[Shader Error] Compile vertex shader " + id.toString() + "fail: " + log);
        }

        fragmentId = glCreateShader(GL_FRAGMENT_SHADER);
        glShaderSource(fragmentId, new String(fragment));
        glCompileShader(fragmentId);

        glGetShaderiv(fragmentId, GL_COMPILE_STATUS, status);
        if(status[0] == 0) {
            String log = glGetShaderInfoLog(fragmentId, 32767);
            throw new IllegalStateException("[Shader Error] Compile fragment shader " + id.toString() + "fail: " + log);
        }

        shader = glCreateProgram();
        glAttachShader(shader, vertexId);
        glAttachShader(shader, fragmentId);
        glLinkProgram(shader);

        glGetProgramiv(fragmentId, GL_LINK_STATUS, status);
        if(status[0] == 0) {
            String log = glGetProgramInfoLog(fragmentId, 32767);
            throw new IllegalStateException("[Shader Error] Link program " + id.toString() + " fail: " + log);
        }

        glDeleteShader(vertexId);
        glDeleteShader(fragmentId);
    }

    @Override
    protected void finalize() throws Throwable {
        glDeleteProgram(shader);
    }

    public int getProgram() {
        return shader;
    }

    public void use() {
        glUseProgram(shader);
    }

    public int getUniformLocation(String name) {
        return glGetUniformLocation(shader, name);
    }
}
