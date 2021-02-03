package io.theflysong.github.resource;

import java.io.BufferedReader;
import java.io.InputStream;

public interface IResource {
    InputStream getResourceAsStream();
    BufferedReader getResourceAsReader();
    void loadResource(ResourceLocation path);
}
