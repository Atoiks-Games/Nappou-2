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

package org.atoiks.games.nappou2.pattern;

import org.atoiks.games.nappou2.Vector2;

import org.atoiks.games.nappou2.entities.Game;

import org.atoiks.games.nappou2.entities.enemy.Enemy;

import org.atoiks.games.nappou2.entities.bullet.FallingBullet;

public final class SquirtsPattern extends TimedCounter {

    private static final Vector2 ACCEL = new Vector2(0, 10000);

    private static final Vector2[] VELOCITIES = {
        Vector2.fromPolar(1000, 20 * (float) Math.PI / 12),
        Vector2.fromPolar(1000, 19 * (float) Math.PI / 12),
        Vector2.fromPolar(1000, 18 * (float) Math.PI / 12),
        Vector2.fromPolar(1000, 17 * (float) Math.PI / 12),
        Vector2.fromPolar(1000, 16 * (float) Math.PI / 12)
    };

    public SquirtsPattern() {
        super(15000, Float.POSITIVE_INFINITY, 0);
    }

    @Override
    public void onTimerUpdate(Enemy enemy, float dt) {
        switch (this.getCount() % 300) {
            case 0:
            case 10:
            case 20:
            case 30:
            case 40:
            case 50: {
                final Game game = enemy.getAssocGame();
                final Vector2 pos = enemy.getPosition();

                for (final Vector2 velocity : VELOCITIES) {
                    game.addEnemyBullet(new FallingBullet(pos, 4, velocity, ACCEL));
                }
                break;
            }
        }
    }
}
