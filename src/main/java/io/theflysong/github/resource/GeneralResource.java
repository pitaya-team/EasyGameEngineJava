package io.theflysong.github.resource;

import java.io.*;

public final class GeneralResource implements IResource {
    private InputStream resourceStream;

    public GeneralResource() {
    }

    public GeneralResource(ResourceLocation path) {
        loadResource(path);
    }

    @Override
    public InputStream getResourceAsStream() {
        return resourceStream;
    }

    @Override
    public BufferedReader getResourceAsReader() {
        return new BufferedReader(new InputStreamReader(resourceStream));
    }

    @Override
    public void loadResource(ResourceLocation path) {
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
    }
}
