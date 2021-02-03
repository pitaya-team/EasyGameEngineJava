package io.theflysong.github.resource;

public final class ResourceLocation {
    protected String namespace;
    protected String path;

    public ResourceLocation(String namespace, String path) {
        this.namespace = namespace;
        this.path = path;
    }

    public ResourceLocation(String rl) {
        String[] str = rl.split("\\$", 2);
        this.namespace = str[0];
        this.path = str[1];
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getNamespace() {
        return namespace;
    }

    public String getPath() {
        return path;
    }

    public void addPrefix(String prefix) {
        path = prefix + path;
    }

    public void addSuffix(String suffix) {
        path += suffix;
    }

    @Override
    public String toString() {
        return namespace + "$" + path;
    }
}
