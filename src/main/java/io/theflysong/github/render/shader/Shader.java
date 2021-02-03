package io.theflysong.github.render.shader;

import io.theflysong.github.resource.AssetsResource;
import io.theflysong.github.resource.ResourceLocation;

import java.io.IOException;

import static org.lwjgl.opengl.GL20.*;

public class Shader {
    protected int shader;

    public Shader(ResourceLocation vertexShader, ResourceLocation fragmentShader) {
        vertexShader.addPrefix("shader/");
        fragmentShader.addPrefix("shader/");
        vertexShader.addSuffix(".glvs");
        fragmentShader.addSuffix(".glfs");

        char[] vertex = new char[32767];
        char[] fragment = new char[32767];
        try {
            new AssetsResource(vertexShader).getResourceAsReader().read(vertex);
            new AssetsResource(fragmentShader).getResourceAsReader().read(fragment);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int vertexId, fragmentId;
        int[] status = new int[1];

        vertexId = glCreateShader(GL_VERTEX_SHADER);
        glShaderSource(vertexId, new String(vertex));
        glCompileShader(vertexId);

        glGetShaderiv(vertexId, GL_COMPILE_STATUS, status);
        if(status[0] == 0) {
            String log = glGetShaderInfoLog(vertexId, 32767);
            throw new IllegalStateException("[Shader Error] Compile vertex shader " + vertexShader.toString() + "fail: " + log);
        }

        fragmentId = glCreateShader(GL_FRAGMENT_SHADER);
        glShaderSource(fragmentId, new String(fragment));
        glCompileShader(fragmentId);

        glGetShaderiv(fragmentId, GL_COMPILE_STATUS, status);
        if(status[0] == 0) {
            String log = glGetShaderInfoLog(fragmentId, 32767);
            throw new IllegalStateException("[Shader Error] Compile fragment shader " + fragmentShader.toString() + "fail: " + log);
        }

        shader = glCreateProgram();
        glAttachShader(shader, vertexId);
        glAttachShader(shader, fragmentId);
        glLinkProgram(shader);

        glGetProgramiv(fragmentId, GL_LINK_STATUS, status);
        if(status[0] == 0) {
            String log = glGetProgramInfoLog(fragmentId, 32767);
            throw new IllegalStateException("[Shader Error] Link program with vertex shader " + vertexShader.toString() + " and fragment shader " + fragmentShader.toString() + "fail: " + log);
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
}
