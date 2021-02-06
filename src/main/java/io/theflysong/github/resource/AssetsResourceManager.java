package io.theflysong.github.resource;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public final class AssetsResourceManager implements IResourceManager{
    private static final AssetsResourceManager INSTANCE = new AssetsResourceManager();

    public static IResourceManager getInstance() {
        return INSTANCE;
    }

    @Override
    public IResource getResource(ResourceLocation path) {
        InputStream resourceStream = null;
        if (Boolean.getBoolean("project.debug_mode")) {
            File file = new File("./build/resource/assets/" + path.getNamespace() + "/" + path.getPath());
            try {
                resourceStream = new FileInputStream(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        else {
            resourceStream = this.getClass().getResourceAsStream("resource/assets/" + path.getNamespace() + "/" + path.getPath());
        }
        return new AssetsResource(resourceStream);
    }
}
