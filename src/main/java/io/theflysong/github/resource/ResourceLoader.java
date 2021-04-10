package io.theflysong.github.resource;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.theflysong.github.render.shader.Shader;
import io.theflysong.github.render.texture.Texture2D;
import io.theflysong.github.resource.AssetsResource;
import io.theflysong.github.resource.ResourceLocation;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

import static org.lwjgl.stb.STBImage.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class ResourceLoader {
    public static Shader loadShader(ResourceLocation id) {
        ResourceLocation vertexShader = new ResourceLocation(id.toString()).addPrefix("shader/").addSuffix(".glvs");
        ResourceLocation fragmentShader = new ResourceLocation(id.toString()).addPrefix("shader/").addSuffix(".glfs");
        ResourceLocation shaderConfig = new ResourceLocation(id.toString()).addPrefix("shader/").addSuffix("_properties.json");

        char[] vertex = new char[65536];
        char[] fragment = new char[65536];
        JsonElement json = null;
        try {
            if (AssetsResourceManager.getInstance().getResource(vertexShader).getResourceAsReader().read(vertex) == 65536)
                throw new ArrayIndexOutOfBoundsException("Your vertex shader code shouldn't over 65535 characters");
            if (AssetsResourceManager.getInstance().getResource(fragmentShader).getResourceAsReader().read(fragment) == 65536)
                throw new ArrayIndexOutOfBoundsException("Your fragment shader code shouldn't over 65535 characters");
            json = new JsonParser().parse(AssetsResourceManager.getInstance().getResource(shaderConfig).getResourceAsReader());
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (json == null) {
            throw new NullPointerException("Shader info shouldn't be null!");
        }
        return new Shader(vertex, fragment, json, id);
    }

    public static Texture2D loadTexture2D(ResourceLocation texture) throws IOException {
        texture.addPrefix("texture/");
        ByteBuffer buffer = MemoryUtil.memAlloc(8192);
        ReadableByteChannel readablebytechannel = Channels.newChannel(AssetsResourceManager.getInstance().getResource(texture).getResourceAsStream());

        while(readablebytechannel.read(buffer) != -1) {
            if (buffer.remaining() == 0) {
                buffer = MemoryUtil.memRealloc(buffer, buffer.capacity() * 2);
            }
        }
        buffer.rewind();
        int[] width = new int[1], height = new int[1], channelSum = new int[1];
        stbi_set_flip_vertically_on_load(true);
        ByteBuffer image = stbi_load_from_memory(buffer, width, height, channelSum, 0);
        if (image == null) {
            throw new IOException("Could not load image: " + STBImage.stbi_failure_reason());
        }
        Texture2D _texture = new Texture2D(image, width[0], height[0]);
        MemoryUtil.memFree(buffer);
        return _texture;
    }
}
