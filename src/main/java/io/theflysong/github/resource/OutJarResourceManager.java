package io.theflysong.github.resource;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class OutJarResourceManager implements IResourceManager{
    private static final GeneralResourceManager INSTANCE = new GeneralResourceManager();

    public static IResourceManager getInstance() {
        return INSTANCE;
    }

    @Override
    public IResource getResource(ResourceLocation path) {
        try {
            return new GeneralResource(new FileInputStream(path.getPath()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return new GeneralResource();
    }
}