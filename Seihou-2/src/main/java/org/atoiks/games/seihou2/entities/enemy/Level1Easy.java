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
        bulletPattern++;

        enemyTime++;

        if (getY() <= 150) {
            setY(getY() + 300 * dt);
        }

        final float x = getX();
        final float y = getY();

        final float ratio = initialhp / 5;

        if (hp >= 4 * ratio) {
            attack1();
        } else if (hp >= 3 * ratio) {
            attack2();
        } else if (hp >= 2 * ratio) {
            attack3();
        } else if (hp >= 1 * ratio) {
            attack4();
        } else {
            attack5();
        }
    }

    private void attack1() {
        if (enemyTime % 1000 == 0) {
            game.addEnemyBullet(new PointBullet(-10, 550 + (float) (50 * Math.sin(10 * time)), 3, 1000, 0));
            game.addEnemyBullet(new PointBullet(760, 50 + (float) (50 * Math.sin(10 * time)), 3, -1000, 0));
        }
        if (enemyTime % 500 == 0) {
            game.addEnemyBullet(new Beam((float) Math.random() * 750, -15, 5, 30, (float) (Math.PI) / 2, 1000));
        }
    }

    private void attack2() {
        if (enemyTime % 1000 == 0) {
            game.addEnemyBullet(new Beam(375 + (float) (375 * Math.sin(10 * time)), -15, 5, 30, (float) (Math.PI) / 2, 1000));

            final float x = getX();
            final float y = getY();
            final double angle = Math.atan2(game.player.getY() - y, game.player.getX() - x);
            game.addEnemyBullet(new PointBullet(x, y, 2, (float) (1000 * Math.cos(angle)), (float) (1000 * Math.sin(angle))));
        }
    }

    private void attack3() {
        if (enemyTime % 1000 == 0) {
            game.addEnemyBullet(new Beam(-15, (float) (Math.random()) * 600, 5, 30, 0, 1000));
            game.addEnemyBullet(new Beam(765, (float) (Math.random()) * 600, 5, 30, (float) Math.PI, 1000));
        }
        //Save this for NORMAL mode :)
        /*
        if (enemyTime%20000 == 0) {
            game.addEnemyBullet(new PointBullet(x, y, 30, -1000*(float)(Math.cos(4*Math.PI/3)), (float) -1000*(float)(Math.sin(4*Math.PI/3))));
            game.addEnemyBullet(new PointBullet(x, y, 30, (float) -1000*(float)(Math.cos(3*Math.PI/2)), (float) -1000*(float)(Math.sin(3*Math.PI/2))));
            game.addEnemyBullet(new PointBullet(x, y, 30, (float) -1000*(float)(Math.cos(5*Math.PI/3)), (float) -1000*(float)(Math.sin(5*Math.PI/3))));
        }
        */
    }

    private void attack4() {
        if (enemyTime % 1000 == 0) {
            game.addEnemyBullet(new PointBullet((float) 375 + (float) (375 * Math.sin(10 * time)), 610, 2, 0, -1000));
        }
        if (enemyTime % 10000 == 0) {
            final float x = getX();
            final float y = getY();
            final double angle = Math.atan2(game.player.getY() - y, game.player.getX() - x);
            game.addEnemyBullet(new Beam(x, y, 2, 30, (float) angle, 1000));
        }
    }

    private void attack5() {
        if (enemyTime % 1500 == 0) {
            game.addEnemyBullet(new PointBullet((float) 375 + (float) (375 * Math.sin(10*time)), 610, 2, 0, -1000));
            game.addEnemyBullet(new PointBullet((float) 375 + -(float) (375 * Math.sin(10*time)), -10, 2, 0, 1000));
            game.addEnemyBullet(new PointBullet(760,(float) 300 + (float) (300 * Math.sin(10*time)), 2, -1000, 0));
            game.addEnemyBullet(new PointBullet(-10,(float) 300 + -(float) (300 * Math.sin(10*time)), 2, 1000, 0));
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
