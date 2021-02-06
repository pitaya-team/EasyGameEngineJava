package io.theflysong.github.resource;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class DataResourceManager implements IResourceManager{
    private static final DataResourceManager INSTANCE = new DataResourceManager();

    public static IResourceManager getInstance() {
        return INSTANCE;
    }

    @Override
    public IResource getResource(ResourceLocation path) {
        InputStream resourceStream = null;
        if (Boolean.getBoolean("project.debug_mode")) {
            File file = new File("./build/resource/data/" + path.getNamespace() + "/" + path.getPath());
            try {
                resourceStream = new FileInputStream(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        else {
            resourceStream = this.getClass().getResourceAsStream("resource/data/" + path.getNamespace() + "/" + path.getPath());
        }
        return new DataResource(resourceStream);
    }
}
