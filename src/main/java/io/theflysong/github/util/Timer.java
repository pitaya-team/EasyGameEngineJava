package io.theflysong.github.util;

import org.lwjgl.glfw.GLFW;

import java.util.function.Supplier;

public class Timer implements Runnable{
    protected long last_time = 0;
    protected final Runnable update;
    protected final Supplier<Boolean> end;
    protected final int max_tps;
    public int last_tps = 0;
    public int tps = 0;

    public static long getTime() {
        return (long) (GLFW.glfwGetTime() * 1000);
    }

    public Timer(int max_tps, Runnable update, Supplier<Boolean> end) {
        this.max_tps = max_tps;
        this.update = update;
        this.end = end;
    }

    @Override
    public void run() {
        do {
            update.run();
            long curTime = getTime();
            if (curTime - last_time > (1000 / max_tps)) {
                try {
                    Thread.sleep(curTime - last_time);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                last_time = curTime;
            }
            if ((curTime / 1000) - (last_time / 1000) >= 1) {
                last_tps = tps;
                tps = 0;
            }
            tps ++;
        }
        while (end.get());
    }
}
