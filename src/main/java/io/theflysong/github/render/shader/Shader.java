package io.theflysong.github.render.shader;

import io.theflysong.github.registry.Register;
import io.theflysong.github.resource.ResourceLocation;
import io.theflysong.github.registry.RegistryEntry;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL20.*;

public class Shader {
    protected int shader;
    public static class ShaderInfo {
        public static class Uniform {
            public interface Apply<S, N> {
                void apply(S shader, N name);
            }
            public static abstract class Uniformer extends RegistryEntry implements Apply<Shader, String> {
                public Uniformer(ResourceLocation id) {
                    super(id);
                }
            }
            public static Register<Uniformer> REGISTER = new Register<>("easygame$uniformer");
            public String name;
            public Uniformer method;

            public Uniform(String name, ResourceLocation id) {
                method = REGISTER.getByID(id);
                this.name = name;
            }

            public void uniform(Shader shader) {
                method.apply(shader, name);
            }
        }
        public List<Uniform> uniforms = new ArrayList<>();
    }
    protected ShaderInfo info;

    public Shader(char[] vertex, char[] fragment, ResourceLocation id) {
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
