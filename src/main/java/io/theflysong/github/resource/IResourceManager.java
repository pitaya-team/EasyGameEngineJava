package io.theflysong.github.resource;

import java.io.FileNotFoundException;

public interface IResourceManager {
    IResource getResource(ResourceLocation path) throws FileNotFoundException;
}
