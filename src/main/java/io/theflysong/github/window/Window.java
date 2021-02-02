package io.theflysong.github.window;

import io.theflysong.github.util.Timer;
import io.theflysong.github.util.math.Vec4f;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;
import static io.theflysong.github.util.GLConstant.*;
import static org.lwjgl.opengl.GL11.*;

public class Window {
    private GLFWErrorCallback glfwErrorHandle = new GLFWErrorCallback() {
        @Override
        public void invoke(int error, long description) {
            System.err.println("[GLFW Error] Error Code: " + error);
            System.err.println("[GLFW Error] Error Description: " + description);
        }
    };
    protected int width;
    protected int height;
    protected String title;
    protected long window;
    protected int max_fps;
    protected int fps = 0;
    protected int last_fps = 0;
    protected int last_time = 0;

    public Window(int width, int height, String title) {
        this(width, height, title, 30);
    }

    public Window(int width, int height, String title, Vec4f backgroundColor) {
        this(width, height, title, 30, backgroundColor);
    }

    public Window(int width, int height, String title, int max_fps) {
        this(width, height, title, max_fps, new Vec4f(0, 0, 0, 1));
    }

    public Window(int width, int height, String title, int max_fps, Vec4f backgroundColor) {
        this.width = width;
        this.height = height;
        this.title = title;
        this.max_fps = max_fps;

        glfwSetErrorCallback(glfwErrorHandle);

        if (! glfwInit()) {
            throw new IllegalStateException("Can't init GLFW Library, please check your VGA driver and update it");
        }

        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);

        window = glfwCreateWindow(width, height, title, nullptr, nullptr);
        if (window == nullptr) {
            glfwTerminate();
            throw new RuntimeException("Failed to create game window");
        }

        glfwMakeContextCurrent(window);
        GL.createCapabilities();

        glViewport(0, 0, width, height);
        glClearColor(backgroundColor.x, backgroundColor.y, backgroundColor.z, backgroundColor.w);
    }

    public boolean update() throws InterruptedException {
        glfwSwapBuffers(window);
        glfwPollEvents();

        if ((Timer.getTime() / 1000 - last_time / 1000) >= 1) {
            last_fps = fps;
            fps = 0;
        }

        if (Timer.getTime() - last_time < (1000 / max_fps)) {
            Thread.sleep(Timer.getTime() - last_time);
        }
        fps ++;

        if (glfwWindowShouldClose(window)) {
            glfwDestroyWindow(window);
            glfwTerminate();
            glfwErrorHandle.free();
            return false;
        }
        return true;
    }
}
