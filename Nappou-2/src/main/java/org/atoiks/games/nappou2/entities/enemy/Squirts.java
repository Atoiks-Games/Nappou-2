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
import org.atoiks.games.nappou2.entities.bullet.FallingBullet;

public final class Squirts extends ManualEnemy {

    private static final long serialVersionUID = -6831272651450179157L;

    private static final double PI_DIV_6 = Math.PI / 6;
    private static final int ROTATIONS = 7;

    private int enemyTime;

    public Squirts(int hp, float x, float y, float r) {
        super(hp, x, y, r);
    }

    @Override
    public void drift(float dx, float dy) {
        // Bosses / Mini bosses do not drift
    }

    @Override
    public void update(float dt) {
        enemyTime++;

        switch (enemyTime % 300) {
            case 0:
            case 10:
            case 20:
            case 30:
            case 40:
            case 50:
                game.addEnemyBullet(new FallingBullet(x, y, 4, 1000, 20 * (float) Math.PI / 12, 10000, false));
                game.addEnemyBullet(new FallingBullet(x, y, 4, 1000, 19 * (float) Math.PI / 12, 10000, false));
                game.addEnemyBullet(new FallingBullet(x, y, 4, 1000, 18 * (float) Math.PI / 12, 10000, false));
                game.addEnemyBullet(new FallingBullet(x, y, 4, 1000, 17 * (float) Math.PI / 12, 10000, false));
                game.addEnemyBullet(new FallingBullet(x, y, 4, 1000, 16 * (float) Math.PI / 12, 10000, false));
                break;
        }

        setY(y + 300 * dt);
    }

    @Override
    public int getScore() {
        return 1;
    }
}
