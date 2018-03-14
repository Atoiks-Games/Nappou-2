package org.atoiks.games.seihou2.entities.enemy;

import org.atoiks.games.framework2d.IGraphics;

import org.atoiks.games.seihou2.entities.bullet.PointBullet;

public final class MiniBomberEnemy extends AbstractEnemy {

    private static final long serialVersionUID = 5619264522L;

    private float time;
    private boolean fireGate;
    private int dir;
    private float spd;

    public MiniBomberEnemy(int hp, float x, float y, float r, int direction, float speed) {
        super(hp, x, y, r);
        dir = direction;
        spd = speed;
    }

    @Override
    public void update(float dt) {
        time += dt;

        setX(getX() + dir * 300 * dt);

        final double cosSpdTime = Math.cos(spd * time);
        if (!fireGate && cosSpdTime < 0.5) {
            fireGate = true;
        }

        if (fireGate && cosSpdTime > 0.5) {
            fireGate = false;
            final float x = getX();
            final float y = getY();
            game.addEnemyBullet(new PointBullet(x, y, 2, 0, 1000));
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
