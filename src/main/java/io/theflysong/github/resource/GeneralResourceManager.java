package io.theflysong.github.resource;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class GeneralResourceManager implements IResourceManager{
    private static final GeneralResourceManager INSTANCE = new GeneralResourceManager();

    public static IResourceManager getInstance() {
        return INSTANCE;
    }

    @Override
    public IResource getResource(ResourceLocation path) {
        InputStream resourceStream = null;
        if (Boolean.getBoolean("project.debug_mode")) {
            File file = new File("./build/resource/" + path.getNamespace() + "/" + path.getPath());
            try {
                resourceStream = new FileInputStream(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        else {
            resourceStream = this.getClass().getResourceAsStream("resource/" + path.getNamespace() + "/" + path.getPath());
        }
        return new GeneralResource(resourceStream);
    }
}