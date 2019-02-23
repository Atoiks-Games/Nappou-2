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

public final class ShiftEnemy extends AbstractEnemy {

    private static final long serialVersionUID = 5619264522L;

    private float time;
    private int bullets = 0;
    private float offset;

    public ShiftEnemy(int hp, float x, float y, float r, float offset, boolean alt) {
        super(hp, x, y, r);
        this.offset = offset;
        if(alt){
            bullets = 6;
        }
    }

    @Override
    public void update(float dt) {
        time += dt;

        x += 300 * dt;

        if (bullets > 5) {
            if (time > offset) bullets = 0;
        } else if (time > 0.05) {
            game.addEnemyBullet(new PointBullet(x, y, 3, 0, 175));
            ++bullets;
            time = 0;
        }
    }

    @Override
    public int getScore() {
        return 1;
    }
}
