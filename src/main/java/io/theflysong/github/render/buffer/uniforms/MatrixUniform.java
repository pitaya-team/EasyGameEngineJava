package io.theflysong.github.render.buffer.uniforms;

import io.theflysong.github.render.shader.Shader;
import io.theflysong.github.resource.ResourceLocation;
import static org.lwjgl.opengl.GL20.*;

public class MatrixUniform extends Shader.ShaderInfo.Uniform.Uniformer {
    public MatrixUniform(ResourceLocation id) {
        super(id);
    }

    @Override
    public void accept(Shader.ShaderInfo.Uniform.Args args) {
        glUniformMatrix4fv(args.shader.getUniformLocation(args.name), false, args.unit.getTransform().toValue());
    }
}
