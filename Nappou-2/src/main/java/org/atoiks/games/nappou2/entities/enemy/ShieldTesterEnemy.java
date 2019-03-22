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

import org.atoiks.games.nappou2.entities.bullet.PointBullet;

public final class ShieldTesterEnemy extends ManualEnemy {

    private static final long serialVersionUID = 5619264522L;

    private float time;

    private final int invSign;

    public ShieldTesterEnemy(int hp, float x, float y, float r, boolean inverted) {
        super(hp, x, y, r);
        this.invSign = inverted ? -1 : 1;
    }

    @Override
    public void update(float dt) {
        time += dt;

        setY(getY() + 600 * dt);

        if (time > 0.0001) {
            final float x = getX();
            final float y = getY();
            game.addEnemyBullet(new PointBullet(x, y, 2, invSign * (x > 375 ? -1 : 1) * 2510, 600));
            time = 0;
        }
    }

    @Override
    public int getScore() {
        return 1;
    }
}
