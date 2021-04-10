package io.theflysong.github.render.buffer.uniforms;

import io.theflysong.github.event.Events;
import io.theflysong.github.registry.RegisterEvent;
import io.theflysong.github.render.shader.Shader.ShaderInfo.Uniform.Uniformer;
import io.theflysong.github.resource.ResourceLocation;

import static io.theflysong.github.render.shader.Shader.ShaderInfo.Uniform.REGISTER;

@Events.SubscribeOrBusIn
public class Uniformers {
    public static final Uniformer uniformer = new MatrixUniform(new ResourceLocation("easygame$matrix"));

    @Events.Subscribe({"easygame$generic"})
    public static void register(RegisterEvent<Uniformer> event) {
        if (event.type != REGISTER.getType())
            return;
        event.register.register(uniformer.getID(), uniformer);
    }
}
