/**
 *  Nappou-2
 *  Copyright (C) 2017-2018  Atoiks-Games <atoiks-games@outlook.com>

 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.

 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.

 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package org.atoiks.games.nappou2.entities.enemy;

import java.util.Random;

import se.tube42.lib.tweeny.Item;
import se.tube42.lib.tweeny.TweenEquation;

import org.atoiks.games.framework2d.IGraphics;

import org.atoiks.games.nappou2.entities.*;
import org.atoiks.games.nappou2.entities.bullet.*;

import static org.atoiks.games.nappou2.Utils.tweenRadialGroupPattern;

public final class Level1Insane extends AbstractEnemy {

    private static final long serialVersionUID = 5619264524L;

    private final Random rnd = new Random();

    private float time;
    private boolean fireGate;
    private boolean right = true;
    private boolean up = false;
    private int bulletPattern;
    private int enemyTime;
    private double spiralAngle = 0;
    private float initialhp;

    private static final float PI_DIV_2 = (float) Math.PI / 2;
    private static final float PI_DIV_3 = (float) Math.PI / 3;
    private static final float PI_DIV_6 = (float) Math.PI / 6;

    public Level1Insane(int hp, float x, float y, float r) {
        super(hp, x, y, r);
        initialhp = hp;
    }

    @Override
    public void drift(float dx, float dy) {
        // Bosses / Mini bosses do not drift
    }

    @Override
    public void update(float dt) {
        time += dt;
        bulletPattern++;

        enemyTime++;

        if (getY() <= 150) {
            up = false;
        }

        if (getY() >= 250) {
            up = true;
        }

        if (getX() <= 100) {
            right = true;
        }

        if (getX() >= 650) {
            right = false;
        }

        setX(getX() + (right ? 1 : -1) * 100 * dt);
        setY(getY() + (up ? -1 : 1) * 100 * dt);

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
            game.addEnemyBullet(new PointBullet(-10, 450 + (float) (150 * Math.sin(10 * time)), 3, 1000, 0));
            game.addEnemyBullet(new PointBullet(760, 150 + (float) (150 * Math.sin(10 * time)), 3, -1000, 0));
        }
        if (enemyTime % 500 == 0) {
            game.addEnemyBullet(new Beam(rnd.nextFloat() * 750, -15, 5, 30, PI_DIV_2, 1000));
        }

    }

    private void attack2() {

        if (enemyTime % 1000 == 0) {
            game.addEnemyBullet(new Beam(375 + (float) (375 * Math.sin(10 * time)), -15, 5, 30, PI_DIV_2, 1000));
            game.addEnemyBullet(new Beam(375 + (float) (-375 * Math.sin(10 * time)), -15, 5, 30, PI_DIV_2, 1000));

            final float x = getX();
            final float y = getY();
            final double angle = Math.atan2(game.player.getY(), game.player.getX() - x);
            game.addEnemyBullet(new PointBullet(x, y, 8, -1000*(float)(Math.cos(-angle * angle * angle + PI_DIV_3)), (float) -1000*(float)(Math.sin((-angle + PI_DIV_3)%(4*PI_DIV_2)))));
            game.addEnemyBullet(new PointBullet(x, y, 8, (float) -1000*(float)(Math.cos(-angle * angle * angle)), (float) -1000*(float)(Math.sin(angle))));
            game.addEnemyBullet(new PointBullet(x, y, 8, (float) -1000*(float)(Math.cos(-angle * angle * angle - PI_DIV_3)), (float) -1000*(float)(Math.sin((-angle - PI_DIV_3)%(4*PI_DIV_2)))));
            game.addEnemyBullet(new PointBullet(x, y, 8, -1000*(float)(Math.cos(-angle * angle * angle +  PI_DIV_3 + PI_DIV_6)), (float) -1000*(float)(Math.sin((-angle + PI_DIV_3)%(4*PI_DIV_2)))));
            game.addEnemyBullet(new PointBullet(x, y, 8, (float) -1000*(float)(Math.cos(-angle * angle * angle + PI_DIV_6)), (float) -1000*(float)(Math.sin(angle))));
            game.addEnemyBullet(new PointBullet(x, y, 8, (float) -1000*(float)(Math.cos(-angle * angle * angle - PI_DIV_3 + PI_DIV_6)), (float) -1000*(float)(Math.sin((-angle - PI_DIV_3)%(4*PI_DIV_2)))));


        }
    }

    private void attack3() {
        if (enemyTime % 1000 == 0) {
            game.addEnemyBullet(new Beam(-15, rnd.nextFloat() * 600, 5, 30, 0, 1000));
            game.addEnemyBullet(new Beam(765, rnd.nextFloat() * 600, 5, 30, (float) Math.PI, 1000));
        }

        if (enemyTime%2000 == 0) {
            spiralAngle += PI_DIV_6;
            game.addEnemyBullet(new PointBullet(x, y, 30, -1000*(float)(Math.cos(spiralAngle + PI_DIV_3)), (float) -1000*(float)(Math.sin(spiralAngle + PI_DIV_3))));
            game.addEnemyBullet(new PointBullet(x, y, 30, (float) -1000*(float)(Math.cos(spiralAngle)), (float) -1000*(float)(Math.sin(spiralAngle))));
            game.addEnemyBullet(new PointBullet(x, y, 30, (float) -1000*(float)(Math.cos(spiralAngle - PI_DIV_3)), (float) -1000*(float)(Math.sin(spiralAngle - PI_DIV_3))));
        }

    }

    private void attack4() {
        if (enemyTime % 7500 == 0) {
        game.addEnemyBullet(new PointBullet(x, y, 50, (float) -300*(float)(Math.cos(spiralAngle)), (float) -300*(float)(Math.sin(spiralAngle))));
        game.addEnemyBullet(new PointBullet(x, y, 50, (float) 300*(float)(Math.cos(spiralAngle)), (float) 300*(float)(Math.sin(spiralAngle))));

        spiralAngle += PI_DIV_6;
        }
        if (enemyTime % 900 == 0) {
            game.addEnemyBullet(new PointBullet(375 + (float) (375 * Math.sin(10 * time)), 610, 2, 0, -1000));


            final float x = getX();
            final float y = getY();
            final double angle = Math.atan2(game.player.getY() - y, game.player.getX() - x);
            game.addEnemyBullet(new PointBullet(x, y, 2, (float) (1000 * Math.cos(angle)), (float) (1000 * Math.sin(angle))));
        }
    }

    private void attack5() {
        if (enemyTime % 1500 == 0) {
            game.addEnemyBullet(new PointBullet(375 + (float) (375 * Math.sin(10 * time)), 610, 20, 0, -250));
            game.addEnemyBullet(new PointBullet(375 - (float) (375 * Math.sin(10 * time)), -10, 15, 0, 500));
            game.addEnemyBullet(new PointBullet(760, 300 + (float) (300 * Math.sin(10 * time)), 10, -1000, 0));
            game.addEnemyBullet(new PointBullet(-10, 300 - (float) (300 * Math.sin(10 * time)), 5, 2000, 0));
        }

        if (enemyTime%20000 == 0) {
            game.addEnemyBullet(new PointBullet(x, y, 30, -1000*(float)(Math.cos(4*PI_DIV_3)), (float) -1000*(float)(Math.sin(4*PI_DIV_3))));
            game.addEnemyBullet(new PointBullet(x, y, 30, (float) -1000*(float)(Math.cos(3*PI_DIV_2)), (float) -1000*(float)(Math.sin(3*PI_DIV_2))));
            game.addEnemyBullet(new PointBullet(x, y, 30, (float) -1000*(float)(Math.cos(5*PI_DIV_3)), (float) -1000*(float)(Math.sin(5*PI_DIV_3))));
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
