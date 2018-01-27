package org.atoiks.games.seihou2.entities;

import java.awt.Color;
import java.awt.Graphics;

public final class PointBullet implements IBullet {

    private static final int SCREEN_EDGE_BUFFER = 16;

    private float x, y, r, dx, dy;

    public PointBullet(float x, float y, float r, float dx, float dy) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.dx = dx;
        this.dy = dy;
    }

    @Override
    public void render(final Graphics g) {
        // Can change this to using textures later
        g.setColor(Color.white);
        // x, y are the center of the bullet
        final float lr = r;
        final int ld = (int) (lr * 2);
        g.drawOval((int) (x - lr), (int) (y - lr), ld, ld);
    }

    @Override
    public void update(final float dt) {
        this.x += this.dx * dt;
        this.y += this.dy * dt;
    }

    @Override
    public float getR() {
        return this.r;
    }

    @Override
    public float getX() {
        return this.x;
    }

    @Override
    public float getY() {
        return this.y;
    }

    @Override
    public float getDx() {
        return this.dx;
    }

    @Override
    public float getDy() {
        return this.dy;
    }

    @Override
    public boolean collidesWith(float x1, float y1, float r1) {
        final float sumRadius = r + r1;
        return (Math.abs(x1 - x) < sumRadius)
            && (Math.abs(y1 - y) < sumRadius);
    }

    @Override
    public boolean isOutOfScreen(final int w, final int h) {
        return (x + r < -SCREEN_EDGE_BUFFER)
            || (x - r > w + SCREEN_EDGE_BUFFER)
            || (y + r < -SCREEN_EDGE_BUFFER)
            || (y - r > h + SCREEN_EDGE_BUFFER);
    }
}