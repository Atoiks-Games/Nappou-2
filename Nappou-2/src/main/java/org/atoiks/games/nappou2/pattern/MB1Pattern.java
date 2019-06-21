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

import org.atoiks.games.nappou2.entities.enemy.IEnemy;

import org.atoiks.games.nappou2.entities.bullet.PointBullet;

public final class MB1Pattern implements IAttackPattern {

    private static final float PI_DIV_6 = (float) Math.PI / 6;
    private static final int ROTATIONS = 7;

    private final int limiter;

    private int time;

    public MB1Pattern(int limiter) {
        this.limiter = limiter;
    }

    @Override
    public void onFireUpdate(IEnemy enemy, float dt) {
        time++;

        final Game game = enemy.getAssocGame();
        final Vector2 pos = enemy.getPosition();

        if (time % limiter == 0) {
            for (int i = 0; i < ROTATIONS; ++i) {
                final float k = i * PI_DIV_6;
                game.addEnemyBullet(new PointBullet(pos, 3, new Vector2((float) (100 * Math.cos(k)), (float) (1000 * Math.sin(k)))));
            }
        }

        if ((time + limiter / 2) % limiter == 0) {
            final float angle = Vector2.angleBetween(pos, game.player.getPosition());
            for (int i = 0; i < ROTATIONS; ++i) {
                final int offset = 3 - i;
                final int s = (4 - Math.abs(offset)) * 100;
                final float k = angle - offset * PI_DIV_6;
                game.addEnemyBullet(new PointBullet(pos, 3, Vector2.fromPolar(s, k)));
            }
        }
    }
}
