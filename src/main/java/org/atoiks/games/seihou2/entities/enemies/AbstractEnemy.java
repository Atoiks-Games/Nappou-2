package org.atoiks.games.seihou2.entities.enemies;

import java.awt.Color;
import java.awt.Graphics;

import org.atoiks.games.seihou2.entities.Game;
import org.atoiks.games.seihou2.entities.IEnemy;

public abstract class AbstractEnemy extends IEnemy {

    private static final long serialVersionUID = 7192746L;

    private static final int SCREEN_EDGE_BUFFER = 16;

    protected float x, y, r;
    protected Game game;

    protected AbstractEnemy(int hp, float x, float y, float r) {
        super(hp);
        this.x = x;
        this.y = y;
        this.r = r;
    }

    @Override
    public final void attachGame(Game game) {
        this.game = game;
    }

    @Override
    public final float getX() {
        return x;
    }

    @Override
    public final float getY() {
        return y;
    }

    @Override
    public void update(float dt) {
        // Default do nothing
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.white);
        // x, y are the center of the enemy
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