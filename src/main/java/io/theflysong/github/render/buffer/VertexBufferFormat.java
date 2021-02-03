package io.theflysong.github.render.buffer;

import org.lwjgl.system.CallbackI;

import java.util.ArrayList;
import java.util.List;

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
    protected List<Byte> formats = new ArrayList<>();

    public VertexBufferFormat add(byte format) {
        formats.add(format);
        return this;
    }

    public VertexBufferFormat add1F() {
        return this.add(Format._1F);
    }

    public VertexBufferFormat add2F() {
        return this.add(Format._2F);
    }

    public VertexBufferFormat add3F() {
        return this.add(Format._3F);
    }

    public VertexBufferFormat add4F() {
        return this.add(Format._4F);
    }

    public VertexBufferFormat addVertex3F() {
        return this.add3F();
    }

    public VertexBufferFormat addVertex4F() {
        return this.add4F();
    }

    public VertexBufferFormat addColor3F() {
        return this.add3F();
    }

    public VertexBufferFormat addColor4F() {
        return this.add4F();
    }

    public VertexBufferFormat addTexture2F() {
        return this.add2F();
    }

    public List<Byte> getFormats() {
        return formats;
    }

    public List<Integer> getLayout() {
        List<Integer> layout = new ArrayList();
        for (byte format : formats) {
            layout.add((int) format);
        }
        return layout;
    }
}
