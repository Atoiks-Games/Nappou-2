package org.atoiks.games.seihou2.entities.enemy;

import org.atoiks.games.framework2d.IGraphics;

import org.atoiks.games.seihou2.entities.bullet.PointBullet;

public final class CircularPathEnemy extends AbstractEnemy {

    private static final long serialVersionUID = 5619264522L;

    private float time;
    private boolean fireGate;
    private float cycles;
    private float rad;
    private float orbitX;
    private float orbitY;
    private int dir;
    private float mod;
    private int spos;
    private float bs;

    public CircularPathEnemy(int hp, float x, float y, float r, float radius, int direction, float speedMod, int startPos, float bulletSpeed) {
        super(hp, x, y, r);
        rad = radius;
        orbitX = x;
        orbitY = y;
        dir = direction;
        mod = speedMod;
        spos = startPos % 3;    // spos can only be {0, 1, 2, 3}
        bs = bulletSpeed;
    }

    @Override
    public void update(float dt) {
        time += dt;
        cycles++;

        final double k = mod * cycles / 10000 + spos * Math.PI / 2;
        setY(orbitY + (float) (dir * rad * Math.sin(k)));
        setX(orbitX + (float) (rad * Math.cos(k)));

        final double cost = Math.cos(bs * time);
        if (!fireGate && cost < 0.01) {
            fireGate = true;
        }

        if (fireGate && cost > 0.01) {
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
