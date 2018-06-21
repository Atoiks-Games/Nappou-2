package org.atoiks.games.nappou2.entities.enemy;

import org.atoiks.games.framework2d.IGraphics;

import org.atoiks.games.nappou2.entities.bullet.Beam;
import org.atoiks.games.nappou2.entities.bullet.PointBullet;

public final class MB1 extends AbstractEnemy {

    private static final long serialVersionUID = 5619264522L;

    private static final double PI_DIV_12 = Math.PI / 12;
    private static final int[] SCALE = { 0, 2, 4, 6, 8, 10, 12 };

    private float time;
    private int bulletPattern;
    private int enemyTime;

    public MB1(int hp, float x, float y, float r) {
        super(hp, x, y, r);
    }

    @Override
    public void update(float dt) {
        time += dt;
        bulletPattern ++;
        enemyTime++;

        final float x = getX();
        final float y = getY();
        final double angle = Math.atan2(game.player.getY() - y, game.player.getX() - x);

        if (enemyTime % 30000 == 0) {
            for (int i = 0; i < SCALE.length; ++i) {
                final double k = SCALE[i] * PI_DIV_12;
                game.addEnemyBullet(new PointBullet(x, y, 3, (float) (100 * Math.cos(k)), (float) (1000 * Math.sin(k))));
            }
        }

        if ((enemyTime + 15000) % 30000 == 0) {
            for (int i = 0; i < SCALE.length; ++i) {
                final double k = angle - (6 - SCALE[i]) * PI_DIV_12;
                final int s = (4 - Math.abs(3 - i)) * 100;
                game.addEnemyBullet(new PointBullet(x, y, 3, (float) (s * Math.cos(k)), (float) (s * Math.sin(k))));
            }
        }

        if (getY() <= 150) {
            setY(getY() + 300 * dt);
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
