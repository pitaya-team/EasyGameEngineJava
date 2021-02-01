package io.theflysong.github.window;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.*;

import java.nio.ByteBuffer;

public class Window {
    protected int width;
    protected int length;
    protected int max_fps;
    protected DisplayMode mode;

    public Window(int width, int length, String title) {
        this(width, length, title, Integer.MAX_VALUE);
    }

    public Window(int width, int length, String title, int max_fps) {
        this.max_fps = max_fps;
        ContextAttribs context = new ContextAttribs(3, 2)
                .withProfileCore(true);
        try {
            mode = new DisplayMode(width, length);
            Display.create(new PixelFormat(), context);
            Display.setDisplayMode(mode);
            Display.setTitle(title);
            //Display.setResizable(true);
        } catch (LWJGLException e) {
            e.printStackTrace();
        }

        GL11.glViewport(0, 0, width, length);
    }

    public void setIcon(ByteBuffer[] icon) {
        Display.setIcon(icon);
        Display.update();
    }

    public void setWidth(int width) throws LWJGLException {
        this.width = width;
        mode = new DisplayMode(width, length);
        Display.setDisplayMode(mode);
        Display.update();

        GL11.glViewport(0, 0, width, length);
    }

    public void setLength(int length) throws LWJGLException {
        this.length = length;
        mode = new DisplayMode(width, length);
        Display.setDisplayMode(mode);
        Display.update();

        GL11.glViewport(0, 0, width, length);
    }

    public void setFullScreen(boolean fullScreen) throws LWJGLException {
        Display.setFullscreen(fullScreen);
        Display.update();
    }

    public void setTitle(String title) {
        Display.setTitle(title);
        Display.update();
    }

    public boolean update() {
        Display.sync(max_fps);
        Display.update();
        if (Display.isCloseRequested()) {
            close();
            return false;
        }
        return true;
    }

    public void close() {
        Display.destroy();
    }
}
