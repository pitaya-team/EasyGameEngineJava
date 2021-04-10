package io.theflysong.github.render.buffer;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class VertexBufferFormat {
    public static class Format {
        public static final byte _1F = 0x01;
        public static final byte _2F = 0x02;
        public static final byte _3F = 0x03;
        public static final byte _4F = 0x04;
        public static final byte Vertex3F = _3F;
        public static final byte Vertex4F = _4F;
        public static final byte Color3F = _3F;
        public static final byte Color4F = _4F;
        public static final byte Texture2F = _2F;
    }
    protected List<Map.Entry<String, Byte>> formats = new ArrayList<>();

    public VertexBufferFormat add(String name, byte format) {
        formats.add(new AbstractMap.SimpleEntry<>(name, format));
        return this;
    }

    public VertexBufferFormat add1F(String name) {
        return this.add(name, Format._1F);
    }

    public VertexBufferFormat add2F(String name) {
        return this.add(name, Format._2F);
    }

    public VertexBufferFormat add3F(String name) {
        return this.add(name, Format._3F);
    }

    public VertexBufferFormat add4F(String name) {
        return this.add(name, Format._4F);
    }

    public VertexBufferFormat addVertex3F() {
        return this.add3F("vertex");
    }

    public VertexBufferFormat addVertex4F() {
        return this.add4F("vertex");
    }

    public VertexBufferFormat addColor3F() {
        return this.add3F("color");
    }

    public VertexBufferFormat addColor4F() {
        return this.add4F("color");
    }

    public VertexBufferFormat addTexture2F() {
        return this.add2F("tex");
    }

    public List<Map.Entry<String, Byte>> getFormats() {
        return formats;
    }

    public static final VertexBufferFormat VER3_TEX2 = new VertexBufferFormat().addVertex3F().addTexture2F();
    public static final VertexBufferFormat VER4_TEX2 = new VertexBufferFormat().addVertex4F().addTexture2F();
    public static final VertexBufferFormat VER3_COLOR3 = new VertexBufferFormat().addVertex3F().addColor3F();
    public static final VertexBufferFormat VER3_COLOR4 = new VertexBufferFormat().addVertex3F().addColor4F();
    public static final VertexBufferFormat VER3_COLOR3_TEX2 = new VertexBufferFormat().addVertex3F().addColor3F().addTexture2F();
    public static final VertexBufferFormat VER3_COLOR4_TEX2 = new VertexBufferFormat().addVertex3F().addColor4F().addTexture2F();
}
