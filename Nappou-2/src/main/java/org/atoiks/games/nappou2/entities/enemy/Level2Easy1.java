/**
 *  Nappou-2
 *  Copyright (C) 2017-2019  Atoiks-Games <atoiks-games@outlook.com>
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package org.atoiks.games.nappou2.entities.enemy;

import java.util.Random;

import org.atoiks.games.nappou2.Vector2;

import org.atoiks.games.nappou2.entities.bullet.*;

public final class Level2Easy1 extends ManualEnemy {

    private static final long serialVersionUID = 3512063076147021469L;

    private final Random rnd = new Random();

    private float time;
    private float q = 0;
    private int enemyTime;
    private float initialhp;

    private static final float PI_DIV_2 = (float) Math.PI / 2;

    public Level2Easy1(int hp, float x, float y, float r) {
        super(hp, x, y, r);
        initialhp = hp;
    }

    @Override
    public void drift(Vector2 d) {
        // Bosses / Mini bosses do not drift
    }

    @Override
    public void update(float dt) {
        time += dt;
        enemyTime++;

        final float y = getY();

        if (y <= 150) {
            setY(y + 300 * dt);
        }

        final float ratio = initialhp / 6;

        if (hp >= 5 * ratio) {
            attack1();
        } else if (hp >= 4 * ratio) {
            attack2();
        } else if (hp >= 3 * ratio) {
            attack3();
        } else if (hp >= 2 * ratio) {
            attack4();
        } else if (hp >= 1 * ratio) {
            attack5();
        } else {
            attack6();
        }
    }

    private void attack1() {
        if (enemyTime % 600 == 0) {
            q = rnd.nextFloat() * 10;
        }

        if (enemyTime % 50 == 0) {
            game.addEnemyBullet(new WiggleBullet(75 * (rnd.nextFloat() + q), 600, 2, 0, -200, true, rnd.nextFloat() * 200, rnd.nextFloat() * 40));
        }
    }

    private void attack2() {
        if (enemyTime % 200 == 0) {
            wiggleBulletPattern(0);
        }
        if (enemyTime % 200 == 10) {
            wiggleBulletPattern(10);
        }
        if (enemyTime % 200 == 20) {
            wiggleBulletPattern(30);
        }
        if (enemyTime % 200 == 30) {
            wiggleBulletPattern(60);
        }
    }

    private void wiggleBulletPattern(final int angleOffset) {
        final double angle = angleOffset + Math.atan2(game.player.getY() - y, game.player.getX() - x);
        final float cosK = 1000 * (float) Math.cos(angle);
        final float sinK = 1000 * (float) Math.sin(angle);
        game.addEnemyBullet(new WiggleBullet(x, y, 10, cosK, sinK, true, 800, 20));
        game.addEnemyBullet(new WiggleBullet(x, y, 10, cosK, sinK, false, 800, 20));
        game.addEnemyBullet(new WiggleBullet(x, y, 10, cosK, sinK, true, -800, 20));
        game.addEnemyBullet(new WiggleBullet(x, y, 10, cosK, sinK, false, -800, 20));
    }

    private void attack3() {
        if (enemyTime % 50 == 0) {
            game.addEnemyBullet(new Beam(-15, rnd.nextFloat() * 600, 5, 30, 0, 1000));
            game.addEnemyBullet(new Beam(765, rnd.nextFloat() * 600, 5, 30, (float) Math.PI, 1000));
        }
    }

    private void attack4() {
        if (enemyTime % 20 == 0) {
            game.addEnemyBullet(new PointBullet(375 + 375 * (float) Math.sin(10 * time), 610, 4, 0, -500));
        }
        if (enemyTime % 200 == 0) {
            final float x = getX();
            final float y = getY();
            final float angle = (float) Math.atan2(game.player.getY() - y, game.player.getX() - x);
            game.addEnemyBullet(new Beam(x, y, 2, 30, angle, 1000));
        }
    }

    private void attack5() {
        if (enemyTime % 30 == 0) {
            final float sin10t = (float) Math.sin(10 * time);
            game.addEnemyBullet(new PointBullet(375 + 375 * sin10t, 610, 4, 0, -500));
            game.addEnemyBullet(new PointBullet(375 - 375 * sin10t, -10, 4, 0, 500));
            game.addEnemyBullet(new PointBullet(760, 300 + 300 * sin10t, 4, -500, 0));
            game.addEnemyBullet(new PointBullet(-10, 300 - 300 * sin10t, 4, 500, 0));
        }
    }

    private void attack6() {
        if (enemyTime % 30 == 0) {
            final float sin10t = (float) Math.sin(10 * time);
            game.addEnemyBullet(new PointBullet(375 + 375 * sin10t, 610, 4, 0, -500));
            game.addEnemyBullet(new PointBullet(375 - 375 * sin10t, -10, 4, 0, 500));
        }
    }

    @Override
    public int getScore() {
        return 1;
    }
}
