package io.theflysong.github.render.shader;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.theflysong.github.registry.Register;
import io.theflysong.github.render.buffer.VertexBufferUnit;
import io.theflysong.github.resource.ResourceLocation;
import io.theflysong.github.registry.RegistryEntry;

import java.util.*;
import java.util.function.Consumer;

import static org.lwjgl.opengl.GL20.*;

public class Shader {
    protected int shader;
    public static class ShaderInfo {
        public static class Uniform {
            public static class Args {
                public JsonObject args;
                public String name;
                public Shader shader;
                public VertexBufferUnit unit;

                public Args(JsonObject args, String name, Shader shader, VertexBufferUnit unit) {
                    this.args = args;
                    this.name = name;
                    this.shader = shader;
                    this.unit = unit;
                }
            }
            public static abstract class Uniformer extends RegistryEntry implements Consumer<Args> {
                public Uniformer(ResourceLocation id) {
                    super(id);
                }
            }
            public static Register<Uniformer> REGISTER = new Register<>("easygame$uniformer");
            public JsonObject args;
            public String name;
            public Uniformer method;

            public Uniform(String name, ResourceLocation id, JsonObject args) {
                method = REGISTER.getByID(id);
                this.name = name;
                this.args = args;
            }

            public void uniform(Shader shader, VertexBufferUnit unit) {
                method.accept(new Args(args, name, shader, unit));
            }

            public static Uniform parse(JsonObject json) {
                return new Uniform(
                        json.getAsJsonPrimitive("name").getAsString(),
                        new ResourceLocation(json.getAsJsonPrimitive("type").getAsString()),
                        json.getAsJsonObject("args"));
            }
        }
        protected List<Uniform> uniforms = new ArrayList<>();
        protected Map<String, Integer> inputs;
        protected String version;
        public int findLocation(String name) {
            return inputs.get(name);
        }
        public static List<Uniform> parseUniforms(JsonArray json) {
            List<Uniform> uniforms = new ArrayList<>();
            for (JsonElement ele : json) {
                uniforms.add(Uniform.parse(ele.getAsJsonObject()));
            }
            return uniforms;
        }
        public static Map<String, Integer> parseInputs(JsonArray json) {
            Map<String, Integer> inputs = new HashMap<>();
            for (JsonElement ele : json) {
                JsonObject obj = ele.getAsJsonObject();
                inputs.put(obj.getAsJsonPrimitive("name").getAsString(),
                        obj.getAsJsonPrimitive("location").getAsInt());
            }
            return inputs;
        }
        public static ShaderInfo parse(JsonObject json) {
            ShaderInfo info = new ShaderInfo();
            info.version = json.getAsJsonPrimitive("version").getAsString();
            info.uniforms = parseUniforms(json.getAsJsonObject("context").getAsJsonArray("uniforms"));
            info.inputs = parseInputs(json.getAsJsonObject("context").getAsJsonArray("inputs"));
            return info;
        }
        public List<Uniform> getUniforms() {
            return uniforms;
        }
        public Map<String, Integer> getInputs() {
            return inputs;
        }
        public String getVersion() {
            return version;
        }
    }
    protected ShaderInfo info;

    public ShaderInfo getInfo() {
        return info;
    }

    public Shader(char[] vertex, char[] fragment, JsonElement json, ResourceLocation id) {
        this.info = ShaderInfo.parse(json.getAsJsonObject());
        int vertexId, fragmentId;
        int[] status = new int[1];

        String ver_s = new String(vertex);
        ver_s = "#version " + this.info.version + "\n" + ver_s;
        vertex = new char[ver_s.length() + 32];
        ver_s.getChars(0, ver_s.length(), vertex, 0);
        String fragment_s = new String(fragment);
        fragment_s = "#version " + this.info.version + "\n" + fragment_s;
        fragment = new char[fragment_s.length() + 32];
        fragment_s.getChars(0, fragment_s.length(), fragment, 0);

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
    protected void finalize() {
        glDeleteProgram(shader);
    }

    public int getProgram() {
        return shader;
    }

    public void use(VertexBufferUnit unit) {
        for (ShaderInfo.Uniform uniform : info.uniforms) {
            uniform.uniform(this, unit);
        }
        glUseProgram(shader);
    }

    public int getUniformLocation(String name) {
        return glGetUniformLocation(shader, name);
    }
}
