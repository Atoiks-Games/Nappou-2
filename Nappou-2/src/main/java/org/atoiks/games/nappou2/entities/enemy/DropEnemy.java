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

public final class DropEnemy extends AbstractEnemy {

    private static final long serialVersionUID = 8326702143654175787L;

    private float time;
    private int bullets;

    public DropEnemy(int hp, float x, float y, float r) {
        super(hp, x, y, r);
    }

    @Override
    public void update(float dt) {
        time += dt;
        // Never put at x = 375, just don't do it!
        if (getX() < 375) {
            setY(getY() + 400 * dt);
            setX(getX() + 170 * dt);
            if (bullets > 8) {
                if (time > 0.5) bullets = 0;
            } else if (time > 0.05) {
                game.addEnemyBullet(new PointBullet(getX(), getY(), 3, 170, 150));
                ++bullets;
                time = 0;
            }
        }
        if (getX() > 375) {
            setY(getY() + 400 * dt);
            setX(getX() - 170 * dt);
            if (bullets > 8) {
                if (time > 0.5) bullets = 0;
            } else if (time > 0.05) {
                game.addEnemyBullet(new PointBullet(getX(), getY(), 3, -170, 150));
                ++bullets;
                time = 0;
            }
        }
    }

	@Override
	public int getScore() {
		return 1;
	}
}
