package org.atoiks.games.seihou2.entities;

import java.awt.Color;
import java.awt.Graphics;

public final class PointEnemy implements IEnemy {

    private static final int SCREEN_EDGE_BUFFER = 16;

    private float x, y, r;

    @Override
    public float getX() {
        return x;
    }

    @Override
    public float getY() {
        return y;
    }

    @Override
    public void update(float dt) {
        // TODO
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.white);
        // x, y are the center of the bullet
        final float lr = r;
        final int ld = (int) (lr * 2);
        g.drawOval((int) (x - lr), (int) (y - lr), ld, ld);
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