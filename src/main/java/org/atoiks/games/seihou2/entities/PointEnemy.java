package org.atoiks.games.seihou2.entities;

import java.awt.Color;
import java.awt.Graphics;

public final class PointEnemy implements IEnemy {

    private static final long serialVersionUID = 5619264522L;

    private static final int SCREEN_EDGE_BUFFER = 16;
    private static final double PI_OVER_12 = Math.PI / 12;

    private float x, y, r;
    private Game game;
    private float time;

    public PointEnemy(float x, float y, float r) {
        this.x = x;
        this.y = y;
        this.r = r;
    }

    @Override
    public void attachGame(Game game) {
        this.game = game;
    }

    @Override
    public float getX() {
        return x;
    }

    @Override
    public float getY() {
        return y;
    }

    private boolean fireGate = false;

    @Override
    public void update(float dt) {
        // Placeholder
        time += dt;

        final double mult = Math.cos(2 * time) / 2;
        y += 300 * (mult + 0.5) * dt;
        x += 300 * (0.5 - mult) * dt;

        if (!fireGate && Math.cos(6 * time) < 0.5) {
            fireGate = true;
        }

        if (fireGate && Math.cos(6 * time) > 0.5) {
            fireGate = false;
            final double angle = Math.atan2(game.player.getY() - y, game.player.getX() - x);
            game.addEnemyBullet(new Beam(x, y, 2.5f, 45, (float) (angle + PI_OVER_12), 720));
            game.addEnemyBullet(new Beam(x, y, 2.5f, 45, (float) (angle), 720));
            game.addEnemyBullet(new Beam(x, y, 2.5f, 45, (float) (angle - PI_OVER_12), 720));
        }
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