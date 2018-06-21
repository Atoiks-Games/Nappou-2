package org.atoiks.games.nappou2.entities.shield;

import org.atoiks.games.framework2d.IGraphics;

import org.atoiks.games.nappou2.entities.IShield;

public class NullShield implements IShield {

    private static final long serialVersionUID = -6024720306180805901L;

    @Override
    public boolean collidesWith(float x, float y, float r) {
        return false;
    }

    @Override
    public boolean isOutOfScreen(int width, int height) {
        return false;
    }

    @Override
    public void render(IGraphics g) {
        // Do nothing
    }

    @Override
    public void update(float dt) {
        // Do nothing
    }

    @Override
    public float getX() {
        return -1;
    }

    @Override
    public float getY() {
        return -1;
    }

    @Override
    public float getR() {
        return -1;
    }

    @Override
    public void setX(float x) {
        // Do nothing
    }

    @Override
    public void setY(float y) {
        // Do nothing		
    }

    @Override
    public void activate() {
        // Do nothing		
    }

    @Override
    public void deactivate() {
        // Do nothing		
    }

    @Override
    public boolean isActive() {
        return false;
    }

    @Override
    public boolean isReady() {
        return false;
    }
}