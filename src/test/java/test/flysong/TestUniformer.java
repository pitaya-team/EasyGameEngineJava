package test.flysong;

import io.theflysong.github.event.Events;
import io.theflysong.github.registry.RegisterEvent;
import io.theflysong.github.render.shader.Shader;
import io.theflysong.github.render.shader.Shader.ShaderInfo.Uniform.Uniformer;
import io.theflysong.github.render.shader.Shader.ShaderInfo.Uniform;
import io.theflysong.github.resource.ResourceLocation;

@Events.SubscribeOrBusIn
public class TestUniformer {
    public static class UniformerA extends Uniformer {
        public UniformerA(ResourceLocation id) {
            super(id);
        }

        @Override
        public void apply(Shader shader, String name) {

        }
    }

    public static final UniformerA UNI_A = new UniformerA(new ResourceLocation("flysong$a"));

    @Events.Subscribe({"easygame$generic"})
    public static void register(RegisterEvent<Uniformer> event) {
        if (event.type != Uniform.REGISTER.getType())
            return;
        event.register.register(UNI_A.getID(), UNI_A);
    }
}
