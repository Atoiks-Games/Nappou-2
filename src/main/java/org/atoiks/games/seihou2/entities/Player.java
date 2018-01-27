package org.atoiks.games.seihou2.entities;

import java.io.Serializable;

import java.awt.Color;
import java.awt.Graphics;

import org.atoiks.games.framework.IRender;
import org.atoiks.games.framework.IUpdate;

public final class Player implements IRender, IUpdate, Serializable {

    private static final long serialVersionUID = 293042L;

    public static final int RADIUS = 8;
    public static final int COLLISION_RADIUS = 2;
    public static final int HINT_COL_RADIUS = COLLISION_RADIUS + 2;

    private float x, y, dx, dy;
    private float speedScale = 1;

    public Player(float x, float y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void render(final Graphics g) {
        g.setColor(Color.cyan);
        g.fillOval((int) (x - RADIUS), (int) (y - RADIUS), RADIUS * 2, RADIUS * 2);
        if (speedScale != 1) {
            g.setColor(Color.red);
            g.fillOval((int) (x - HINT_COL_RADIUS), (int) (y - HINT_COL_RADIUS), HINT_COL_RADIUS * 2, HINT_COL_RADIUS * 2);
        }
    }

    @Override
    public void update(final float dt) {
        this.x += this.dx * this.speedScale * dt;
        this.y += this.dy * this.speedScale * dt;
    }

    public void setSpeedScale(float scale) {
        this.speedScale = scale;
    }

    public void resetSpeedScale() {
        this.speedScale = 1;
    }

    public void setDx(float dx) {
        this.dx = dx;
    }

    public void setDy(float dy) {
        this.dy = dy;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}
