package org.atoiks.games.seihou2.entities.enemy;

import se.tube42.lib.tweeny.Item;
import se.tube42.lib.tweeny.TweenEquation;

import org.atoiks.games.framework2d.IGraphics;

import org.atoiks.games.seihou2.entities.*;
import org.atoiks.games.seihou2.entities.bullet.*;

import static org.atoiks.games.seihou2.Utils.tweenRadialGroupPattern;

public final class Level1Easy extends AbstractEnemy {

    private static final long serialVersionUID = 5619264522L;

    private float time;
    private boolean fireGate;
    private int bulletPattern;
    private int enemyTime;
    private double spiralAngle = 0;
    private float initialhp;
    private float radius;

    private static final double PI_DIV_12 = Math.PI / 12;
    private static final double PI_DIV_2 = Math.PI / 2;
    private static final int[] SCALE = { 0, 2, 4, 6, 8, 10, 12 };

    private static final float[] XBOUND = { 30, 750 - 30, 10, 750 - 10 };
    private static final float[] OFFSET = { 0, (float) Math.PI, 3 * (float) PI_DIV_2, 3 * (float) PI_DIV_2 };

    public Level1Easy(int hp, float x, float y, float r) {
        super(hp, x, y, r);
        initialhp = hp;
        radius = r;
    }

    @Override
    public void update(float dt) {
        time += dt;
        bulletPattern ++;

        enemyTime++;

        if (getY() <= 150) {
            setY(getY() + 300 * dt);
        }

        final float x = getX();
        final float y = getY();

        if (hp >= 2 * initialhp / 3) {
            if (enemyTime % 30000 == 0) {
                game.addEnemy(new DropEnemy(1, 30, -10, 8));
                game.addEnemy(new DropEnemy(1, 720, -10, 8));
                game.addEnemy(new DropEnemy(1, 100, -10, 8));
                game.addEnemy(new DropEnemy(1, 650, -10, 8));
            }

            bulletPatternMod25000();
        } else if (hp >= initialhp / 3) {
            bulletPatternMod60000();

            if (bulletPattern % 20000 == 0) {
                if (Math.random() >= 0.5) {
                    game.addEnemyBullet(new PointBullet(x, y, 3, 0, 700));
                }
                if (Math.random() >= 0.5) {
                    game.addEnemyBullet(new PointBullet(x + radius, y, 3, 0, 700));
                }
                if (Math.random() >= 0.5) {
                    game.addEnemyBullet(new PointBullet(x - radius, y, 3, 0, 700));
                }
            }
        } else {
            bulletPatternMod60000();
            bulletPatternMod25000();
        }
    }

    // TODO: Make a better method name
    private void bulletPatternMod25000() {
        if (bulletPattern % 25000 != 0) return;

        final float x = getX();
        final float y = getY();
        final double angle = Math.atan2(game.player.getY() - y, game.player.getX() - x);
        if (Math.random() >= 0.5) {
            for (int i = 0; i < SCALE.length; ++i) {
                final double k0 = SCALE[i] * PI_DIV_12;
                final double k1 = SCALE[i] * -PI_DIV_12;
                final double k2 = SCALE[i] * PI_DIV_12 + PI_DIV_2;
                final double k3 = SCALE[i] * -PI_DIV_12 + PI_DIV_2;
                game.addEnemyBullet(new PointBullet(x, y, 3, (float) (100 * Math.cos(k0)), (float) (1000 * Math.sin(k0))));
                game.addEnemyBullet(new PointBullet(x, y, 3, (float) (100 * Math.cos(k1)), (float) (1000 * Math.sin(k1))));
                game.addEnemyBullet(new PointBullet(x, y, 3, (float) (1000 * Math.cos(k2)), (float) (100 * Math.sin(k2))));
                game.addEnemyBullet(new PointBullet(x, y, 3, (float) (1000 * Math.cos(k3)), (float) (100 * Math.sin(k3))));
            }
        } else {
            for (int i = 0; i < SCALE.length; ++i) {
                final double k0 = SCALE[i] * PI_DIV_12;
                final double k1 = SCALE[i] * -PI_DIV_12;
                final double k2 = SCALE[i] * PI_DIV_12 + PI_DIV_2;
                final double k3 = SCALE[i] * -PI_DIV_12 + PI_DIV_2;
                game.addEnemyBullet(new PointBullet(x, y, 3, (float) (100 * Math.tan(Math.cos(k0))), (float) (100 * Math.tan(Math.sin(k0)))));
                game.addEnemyBullet(new PointBullet(x, y, 3, (float) (100 * Math.tan(Math.cos(k1))), (float) (100 * Math.tan(Math.sin(k1)))));
                game.addEnemyBullet(new PointBullet(x, y, 3, (float) (100 * Math.tan(Math.cos(k2))), (float) (100 * Math.tan(Math.sin(k2)))));
                game.addEnemyBullet(new PointBullet(x, y, 3, (float) (100 * Math.tan(Math.cos(k3))), (float) (100 * Math.tan(Math.sin(k3)))));
            }
        }
    }

    // TODO: Make a better method name
    private void bulletPatternMod60000() {
        if (bulletPattern % 60000 != 0) return;

        tweenRadialGroupPattern(game, XBOUND, OFFSET);
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
