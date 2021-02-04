package io.theflysong.github.render.buffer;

import io.theflysong.github.render.shader.Shader;
import io.theflysong.github.util.IBuilder;
import io.theflysong.github.util.math.*;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;

public class VertexBuffer {
    protected List<VertexBufferUnit> units = new ArrayList();

    public static VertexBufferBuilder getBuilder(Shader shader, VertexBufferFormat format, int mode) {
        return VertexBufferBuilder.create().shader(shader).format(format).mode(mode).createUnit();
    }

    public static VertexBufferBuilder getBuilder(Shader shader, VertexBufferFormat format) {
        return VertexBufferBuilder.create().shader(shader).format(format).createUnit();
    }

    public void add(VertexBufferBuilder builder) {
        this.add(builder.build());
    }

    public void add(VertexBufferUnit unit) {
        units.add(unit);
    }

    public void copyLast(Function<MatrixStack, MatrixStack> generator) {
        units.add(getLastUnit());
        getLastUnit().setMatrix(generator);
    }

    public VertexBufferUnit getLastUnit() {
        return units.get(units.size() - 1);
    }

    public void init() {
        for (VertexBufferUnit unit : units) {
            unit.init();
        }
    }

    public void draw(MatrixStack matrixStack) {
        for (VertexBufferUnit unit : units) {
            unit.use(matrixStack);
        }
    }

    public static class VertexBufferBuilder implements IBuilder<VertexBufferUnit> {
        protected VertexBufferUnit unit;
        protected Shader shader;
        protected VertexBufferFormat format;
        protected int drawMode = GL_STATIC_DRAW;
        
        protected VertexBufferBuilder() {
        }
        
        protected static VertexBufferBuilder create() {
            return new VertexBufferBuilder();
        }
        
        public VertexBufferBuilder shader(Shader shader) {
            this.shader = shader;
            return this;
        }
        
        public VertexBufferBuilder mode(int drawMode) {
            this.drawMode = drawMode;
            return this;
        }
        
        public VertexBufferBuilder format(VertexBufferFormat format) {
            this.format = format;
            return this;
        }
        
        public VertexBufferBuilder createUnit() {
            this.unit = new VertexBufferUnit(shader, format, drawMode);
            return this;
        }

        public VertexBufferBuilder pos(float x, float y, float z) {
            unit.addVertex(x).addVertex(y).addVertex(z);
            return this;
        }

        public VertexBufferBuilder pos(float x, float y, float z, float w) {
            unit.addVertex(x).addVertex(y).addVertex(z).addVertex(w);
            return this;
        }
        
        public VertexBufferBuilder pos(Vec3f pos) {
            this.pos(pos.x, pos.y, pos.z);
            return this;
        }
        
        public VertexBufferBuilder pos(Vec4f pos) {
            this.pos(pos.x, pos.y, pos.z, pos.w);
            return this;
        }

        public VertexBufferBuilder color(float red, float green, float blue) {
            unit.addVertex(red).addVertex(green).addVertex(blue);
            return this;
        }

        public VertexBufferBuilder color(float red, float green, float blue, float alpha) {
            unit.addVertex(red).addVertex(green).addVertex(blue).addVertex(alpha);
            return this;
        }

        public VertexBufferBuilder color(Vec3f color) {
            this.color(color.x, color.y, color.z);
            return this;
        }

        public VertexBufferBuilder color(Vec4f color) {
            this.color(color.x, color.y, color.z, color.w);
            return this;
        }

        public VertexBufferBuilder texture(float x, float y) {
            unit.addVertex(x).addVertex(y);
            return this;
        }

        public VertexBufferBuilder texture(Vec2f texture) {
            this.texture(texture.x, texture.y);
            return this;
        }

        public VertexBufferBuilder index(int i, int j, int k) {
            unit.addIndex(i).addIndex(j).addIndex(k);
            return this;
        }

        public VertexBufferBuilder index(Vec3i index) {
            this.index(index.x, index.y, index.z);
            return this;
        }

        public VertexBufferBuilder matrix(Function<MatrixStack, MatrixStack> generator) {
            unit.setMatrix(generator);
            return this;
        }

        @Override
        public VertexBufferUnit build() {
            return unit;
        }
    }
}
