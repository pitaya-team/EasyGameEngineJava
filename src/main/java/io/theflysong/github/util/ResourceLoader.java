package io.theflysong.github.util;

import io.theflysong.github.render.shader.Shader;
import io.theflysong.github.resource.AssetsResource;
import io.theflysong.github.resource.ResourceLocation;

import java.io.IOException;

public class ResourceLoader {
    public static Shader loadShader(ResourceLocation vertexShader, ResourceLocation fragmentShader, ResourceLocation id) {
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

        return new Shader(vertex, fragment, id);
    }
}
