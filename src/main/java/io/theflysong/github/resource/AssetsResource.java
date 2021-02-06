package io.theflysong.github.resource;

import java.io.*;

public final class AssetsResource implements IResource {
    private InputStream resourceStream;

    public AssetsResource() {
    }

    public AssetsResource(InputStream stream) {
        loadResource(stream);
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
    public void loadResource(InputStream stream) {
        this.resourceStream = stream;
    }
}
