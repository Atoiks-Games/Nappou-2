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

import org.atoiks.games.nappou2.entities.bullet.PointBullet;

public final class DropEnemyPattern extends TimedCounter {

    private final float signY;

    public DropEnemyPattern(boolean inverted) {
        super(9, 0.5f, 0.05f);

        this.signY = inverted ? -1 : 1;
    }

    @Override
    protected void onTimerUpdate(final Enemy enemy, float dt) {
        final Game game = enemy.getAssocGame();
        final Vector2 pos = enemy.getPosition();
        final float signX = Math.signum(375 - pos.getX());

        game.addEnemyBullet(new PointBullet(pos, 3, new Vector2(signX * 170, signY * 150)));
    }
}
