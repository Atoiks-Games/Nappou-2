package org.atoiks.games.seihou2.entities.shield;

import java.awt.Color;
import java.awt.Graphics;

import org.atoiks.games.seihou2.entities.IShield;

public class TimeBasedShield implements IShield {

    private static final long serialVersionUID = 172635916L;

    private final float timeout;

    private boolean active = false;
    private float time = 0;
    private float x, y, r;

    public TimeBasedShield(float timeout, float r) {
        this.timeout = timeout;
        this.r = r;
    }

    @Override
    public float getX() {
        return x;
    }

    @Override
    public float getY() {
        return y;
    }

    @Override
    public float getR() {
        return r;
    }

    @Override
    public void setX(float x) {
        this.x = x;
    }

    @Override
    public void setY(float y) {
        this.y = y;
    }

    @Override
    public void render(Graphics g) {
        if (active) {
            g.setColor(Color.orange);
            // x, y are the center of the shield
            final float lr = r;
            final int ld = (int) (lr * 2);
            g.drawOval((int) (x - lr), (int) (y - lr), ld, ld);
        }
    }

    @Override
    public void update(float dt) {
        if (active && (time += dt) >= timeout) {
            deactivate();
        }
    }
    
    @Override
    public void activate() {
        if (!active) active = true;
    }
    
    @Override
    public void deactivate() {
        active = false;
        time = 0;
    }

    @Override
    public boolean isActive() {
        return active;
    }

    @Override
    public boolean isOutOfScreen(int width, int height) {
        // Being out of screen does not qualify it for deallocation
        return false;
    }

    @Override
    public boolean collidesWith(float x1, float y1, float r1) {
        if (active) {
            final float sumRadius = r + r1;
            return (Math.abs(x1 - x) < sumRadius)
                && (Math.abs(y1 - y) < sumRadius);
        }
        return false;
    }
}