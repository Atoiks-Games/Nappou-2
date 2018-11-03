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

import org.atoiks.games.framework2d.IGraphics;

import org.atoiks.games.nappou2.entities.bullet.PointBullet;

public final class CircularPathEnemy extends AbstractEnemy {

    private static final long serialVersionUID = 5619264522L;

    private float time;
    private boolean fireGate;
    private float cycles;
    private float rad;
    private float orbitX;
    private float orbitY;
    private float dir;
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
        spos = startPos % 4;    // spos can only be {0, 1, 2, 3}
        bs = bulletSpeed;
    }

    @Override
    public void update(float dt) {
        time += dt;
        cycles++;

        final double k = mod * cycles / 10000 + spos * Math.PI / 2;
        setY(orbitY + dir * rad * (float) Math.sin(k));
        setX(orbitX + rad * (float) Math.cos(k));

        final double cost = Math.cos(bs * time);
        if (!fireGate && cost < 0.01) {
            fireGate = true;
        }

        if (fireGate && cost > 0.01) {
            fireGate = false;
            final float x = getX();
            final float y = getY();
            final double angle = Math.atan2(game.player.getY() - y, game.player.getX() - x);
            game.addEnemyBullet(new PointBullet(x, y, 2, 1000 * (float) Math.cos(angle), 1000 * (float) Math.sin(angle)));
        }
    }

    @Override
    public int getScore() {
        return 1;
    }
}
