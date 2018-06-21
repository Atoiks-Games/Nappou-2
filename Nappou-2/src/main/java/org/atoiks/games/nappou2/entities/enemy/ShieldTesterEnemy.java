package org.atoiks.games.nappou2.entities.enemy;

import org.atoiks.games.framework2d.IGraphics;

import org.atoiks.games.nappou2.entities.bullet.PointBullet;

public final class ShieldTesterEnemy extends AbstractEnemy {

    private static final long serialVersionUID = 5619264522L;

    private float time;

    public ShieldTesterEnemy(int hp, float x, float y, float r) {
        super(hp, x, y, r);
    }

    @Override
    public void update(float dt) {
        time += dt;

        setY(getY() + 300 * dt);

        if (time > 0.001) {
            final float x = getX();
            final float y = getY();
            game.addEnemyBullet(new PointBullet(x, y, 2, (x > 375 ? -1 : 1) * 5000, 0));
            time = 0;
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
