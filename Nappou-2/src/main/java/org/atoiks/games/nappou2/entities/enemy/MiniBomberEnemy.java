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

import org.atoiks.games.nappou2.entities.bullet.PointBullet;

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
            game.addEnemyBullet(new PointBullet(getX(), getY(), 2, 0, 1000));
        }
    }

    @Override
    public int getScore() {
        return 1;
    }
}
