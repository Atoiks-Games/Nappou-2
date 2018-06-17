package org.atoiks.games.seihou2.entities;

import java.io.Serializable;

import java.awt.Color;

import org.atoiks.games.framework2d.IGraphics;

import org.atoiks.games.framework2d.IRender;
import org.atoiks.games.framework2d.IUpdate;

public abstract class IEnemy implements ICollidable, IRender, IUpdate, Serializable {

    private static final long serialVersionUID = 8123472652L;

    protected static final int SCREEN_EDGE_BUFFER = 16;

    protected int hp;

    protected IEnemy(int hp) {
        this.hp = hp;
    }

    public boolean isDead() {
        return hp <= 0;
    }

    public int changeHp(int delta) {
        return this.hp += delta;
    }

    @Override
    public void update(float dt) {
        // Default do nothing
    }

    @Override
    public void render(IGraphics g) {
        g.setColor(Color.white);
        // x, y are the center of the enemy
        final float x = getX();
        final float y = getY();
        final float r = getR();
        g.drawOval(x - r, y - r, x + r, y + r);
    }

    @Override
    public boolean collidesWith(float x1, float y1, float r1) {
        final float sumRadius = getR() + r1;
        return (Math.abs(x1 - getX()) < sumRadius)
            && (Math.abs(y1 - getY()) < sumRadius);
    }

    @Override
    public boolean isOutOfScreen(final int w, final int h) {
        final float x = getX();
        final float y = getY();
        final float r = getR();
        return (x + r < -SCREEN_EDGE_BUFFER)
            || (x - r > w + SCREEN_EDGE_BUFFER)
            || (y + r < -SCREEN_EDGE_BUFFER)
            || (y - r > h + SCREEN_EDGE_BUFFER);
    }

    public abstract float getX();
    public abstract float getY();
    public abstract float getR();

    public abstract void attachGame(Game game);

    public abstract int getScore();
}