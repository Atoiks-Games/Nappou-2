package org.atoiks.games.seihou2.entities.enemy;

import org.atoiks.games.framework2d.IGraphics;

import org.atoiks.games.seihou2.entities.bullet.PointBullet;

public final class CircularPathEnemy extends AbstractEnemy {

    private static final long serialVersionUID = 5619264522L;

    private float time;
    private boolean fireGate;
    private int cycles;

    public CircularPathEnemy(int hp, float x, float y, float r, float origin, float radius, float direction) {
        super(hp, x, y, r);
    }

    @Override
    public void update(float dt) {
        time += dt;
        cycles++;

        setY(getY() + (float) (10 * Math.sin(cycles)));

        final double cos6t = Math.cos(6 * time);
        if (!fireGate && cos6t < 0.5) {
            fireGate = true;
        }

        if (fireGate && cos6t > 0.5) {
            fireGate = false;
            final float x = getX();
            final float y = getY();
            final double angle = Math.atan2(game.player.getY() - y, game.player.getX() - x);
            game.addEnemyBullet(new PointBullet(x, y, 2, (float) (1000 * Math.cos(angle)), (float)(1000 * Math.sin(angle))));
        }
    }

    @Override
    public void render(IGraphics g) {
        // Convert to drawImage later on?
        super.render(g);
    }

    @Override
    public int getScore() {
        return 1;
    }
}
