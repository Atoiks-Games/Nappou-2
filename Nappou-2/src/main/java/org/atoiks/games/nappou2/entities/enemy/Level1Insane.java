/**
 *  Nappou-2
 *  Copyright (C) 2017-2018  Atoiks-Games <atoiks-games@outlook.com>
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


import org.atoiks.games.framework2d.IGraphics;

import org.atoiks.games.nappou2.entities.bullet.*;

import static org.atoiks.games.nappou2.TrigConstants.*;

public final class Level1Insane extends AbstractEnemy {

    private static final long serialVersionUID = 5619264524L;

    private final Random rnd = new Random();

    private float time;
    private boolean right = true;
    private boolean up = false;
    private int enemyTime;
    private double spiralAngle = 0;

    private final float ratio;

    public Level1Insane(int hp, float x, float y, float r) {
        super(hp, x, y, r);
        ratio = hp / 5;
    }

    @Override
    public void drift(float dx, float dy) {
        // Bosses / Mini bosses do not drift
    }

    @Override
    public void update(float dt) {
        time += dt;
        enemyTime++;

        final float x = getX();
        final float y = getY();

        if (y <= 150) {
            up = false;
        }

        if (y >= 250) {
            up = true;
        }

        if (x <= 100) {
            right = true;
        }

        if (x >= 650) {
            right = false;
        }

        setX(x + (right ? 1 : -1) * 100 * dt);
        setY(y + (up ? -1 : 1) * 100 * dt);

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
            final float sin10t = (float) Math.sin(10 * time);
            game.addEnemyBullet(new PointBullet(-10, 450 + 150 * sin10t, 3, 1000, 0));
            game.addEnemyBullet(new PointBullet(760, 150 + 150 * sin10t, 3, -1000, 0));
        }
        if (enemyTime % 500 == 0) {
            game.addEnemyBullet(new Beam(rnd.nextFloat() * 750, -15, 5, 30, PI_DIV_2, 1000));
        }
    }

    private void attack2() {
        if (enemyTime % 1000 == 0) {
            final float sin10t = (float) Math.sin(10 * time);
            game.addEnemyBullet(new Beam(375 + 375 * sin10t, -15, 5, 30, PI_DIV_2, 1000));
            game.addEnemyBullet(new Beam(375 + -375 * sin10t, -15, 5, 30, PI_DIV_2, 1000));

            final float x = getX();
            final float y = getY();
            final float angle = (float) Math.atan2(game.player.getY(), game.player.getX() - x);
            final float ncube = -angle * angle * angle;
            final float nksink = -1000 * (float) Math.sin((-angle - PI_DIV_3) % (4 * PI_DIV_2));
            final float nkSinAngle = (float) Math.sin(angle);

            final float sinNcube = (float) Math.sin(ncube);
            final float cosNcube = (float) Math.cos(ncube);

            game.addEnemyBullet(new PointBullet(x, y, 8, -1000 * cosNcube, nkSinAngle));

            // cos(ncube + PI_DIV_3) = cosNcube * SIN_PI_DIV_6 - sinNcube * SIN_PI_DIV_3
            game.addEnemyBullet(new PointBullet(x, y, 8, 1000 * (sinNcube * SIN_PI_DIV_3 - cosNcube * SIN_PI_DIV_6), nksink));

            // cos(ncube - PI_DIV_3) = cosNcube * SIN_PI_DIV_6 + sinNcube * SIN_PI_DIV_3
            game.addEnemyBullet(new PointBullet(x, y, 8, -1000 * (cosNcube * SIN_PI_DIV_6 + sinNcube * SIN_PI_DIV_3), nksink));

            // cos(ncube + PI_DIV_2) = -sinNcube
            game.addEnemyBullet(new PointBullet(x, y, 8, 1000 * sinNcube, nksink));

            // cos(ncube - PI_DIV_6) = cosNcube * SIN_PI_DIV_3 + sinNcube * SIN_PI_DIV_6
            game.addEnemyBullet(new PointBullet(x, y, 8, -1000 * (cosNcube * SIN_PI_DIV_3 + sinNcube * SIN_PI_DIV_6), nksink));

            // cos(ncube + PI_DIV_6) = cosNcube * SIN_PI_DIV_3 - sinNcube * SIN_PI_DIV_6
            game.addEnemyBullet(new PointBullet(x, y, 8, 1000 * (sinNcube * SIN_PI_DIV_6 - cosNcube * SIN_PI_DIV_3), nkSinAngle));
        }
    }

    private void attack3() {
        if (enemyTime % 1000 == 0) {
            game.addEnemyBullet(new Beam(-15, rnd.nextFloat() * 600, 5, 30, 0, 1000));
            game.addEnemyBullet(new Beam(765, rnd.nextFloat() * 600, 5, 30, (float) Math.PI, 1000));
        }

        if (enemyTime%2000 == 0) {
            spiralAngle += PI_DIV_6;
            final float cosSpiral = (float) Math.cos(spiralAngle);
            final float sinSpiral = (float) Math.sin(spiralAngle);

            game.addEnemyBullet(new PointBullet(x, y, 30, -1000 * cosSpiral, -1000 * sinSpiral));

            // cos(spiralAngle + PI_DIV_3) = cosSpiral * SIN_PI_DIV_6 - sinSpiral * SIN_PI_DIV_3
            // sin(spiralAngle + PI_DIV_3) = sinSpiral * SIN_PI_DIV_6 + cosSpiral * SIN_PI_DIV_3
            game.addEnemyBullet(new PointBullet(x, y, 30,
                    1000 * (sinSpiral * SIN_PI_DIV_3 - cosSpiral * SIN_PI_DIV_6),
                    -1000 * (sinSpiral * SIN_PI_DIV_6 + cosSpiral * SIN_PI_DIV_3)));

            // cos(spiralAngle - PI_DIV_3) = cosSpiral * SIN_PI_DIV_6 + sinSpiral * SIN_PI_DIV_3
            // sin(spiralAngle - PI_DIV_3) = sinSpiral * SIN_PI_DIV_6 - cosSpiral * SIN_PI_DIV_3
            game.addEnemyBullet(new PointBullet(x, y, 30,
                    -1000 * (cosSpiral * SIN_PI_DIV_6 + sinSpiral * SIN_PI_DIV_3),
                    1000 * (cosSpiral * SIN_PI_DIV_3 - sinSpiral * SIN_PI_DIV_6)));
        }
    }

    private void attack4() {
        if (enemyTime % 7500 == 0) {
            final float cosSpiral = (float) Math.cos(spiralAngle);
            final float sinSpiral = (float) Math.sin(spiralAngle);
            game.addEnemyBullet(new PointBullet(x, y, 50,  300 * cosSpiral,  300 * sinSpiral));
            game.addEnemyBullet(new PointBullet(x, y, 50, -300 * cosSpiral, -300 * sinSpiral));

            spiralAngle += PI_DIV_6;
        }
        if (enemyTime % 900 == 0) {
            game.addEnemyBullet(new PointBullet(375 + 375 * (float) Math.sin(10 * time), 610, 2, 0, -1000));

            final float x = getX();
            final float y = getY();
            final double angle = Math.atan2(game.player.getY() - y, game.player.getX() - x);
            game.addEnemyBullet(new PointBullet(x, y, 2, 1000 * (float) Math.cos(angle), 1000 * (float) Math.sin(angle)));
        }
    }

    private void attack5() {
        if (enemyTime % 1500 == 0) {
            final float sin10t = (float) Math.sin(10 * time);
            game.addEnemyBullet(new PointBullet(375 + 375 * sin10t, 610, 20, 0, -250));
            game.addEnemyBullet(new PointBullet(375 - 375 * sin10t, -10, 15, 0, 500));
            game.addEnemyBullet(new PointBullet(760, 300 + 300 * sin10t, 10, -1000, 0));
            game.addEnemyBullet(new PointBullet(-10, 300 - 300 * sin10t, 5, 2000, 0));
        }

        if (enemyTime%20000 == 0) {
            game.addEnemyBullet(new PointBullet(x, y, 30, -1000 * COS_4_PI_DIV_3, -1000 * SIN_4_PI_DIV_3));
            game.addEnemyBullet(new PointBullet(x, y, 30, -1000 * COS_3_PI_DIV_2, -1000 * SIN_3_PI_DIV_2));
            game.addEnemyBullet(new PointBullet(x, y, 30, -1000 * COS_5_PI_DIV_3, -1000 * SIN_5_PI_DIV_3));
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
