package org.atoiks.games.framework;

import java.util.Map;
import java.util.HashMap;
import java.io.Serializable;

public final class FrameInfo implements Serializable {

    private static final long serialVersionUID = -982134728L;

    // Package level access!
    String titleName = "";
    float fps = 30.0f;
    Scene[] scenes = new Scene[0];
    int width = 800;
    int height = 600;
    boolean resizable = false;
    Map<String, Object> res = new HashMap<>();

    @Override
    public String toString() {
        return new StringBuilder()
                .append(titleName).append('@').append(fps)
                .append('[').append(width)
                .append('x').append(height).append(']')
                .append(resizable ? "resizable" : "fixed")
                .toString();
    }

    // ----- A bunch of setters (nothing interesting)

    public FrameInfo setTitle(String name) {
        this.titleName = name;
        return this;
    }

    public FrameInfo setFps(float fps) {
        this.fps = fps;
        return this;
    }

    public FrameInfo setScenes(Scene... scenes) {
        this.scenes = scenes;
        return this;
    }

    public FrameInfo setWidth(int width) {
        this.width = width;
        return this;
    }

    public FrameInfo setHeight(int height) {
        this.height = height;
        return this;
    }

    public FrameInfo setSize(int width, int height) {
        this.width = width;
        this.height = height;
        return this;
    }

    public FrameInfo setResizable(boolean resizable) {
        this.resizable = resizable;
        return this;
    }

    public FrameInfo addResources(Map<String, ? extends Object> res) {
        this.res.putAll(res);
        return this;
    }

    public FrameInfo addResource(String name, Object data) {
        this.res.put(name, data);
        return this;
    }
}