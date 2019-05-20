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

package org.atoiks.games.nappou2.entities.attack;

import org.atoiks.games.nappou2.entities.Game;
import org.atoiks.games.nappou2.entities.IEnemy;
import org.atoiks.games.nappou2.entities.IUpdateListener;

import org.atoiks.games.nappou2.entities.bullet.PointBullet;

public final class SingleShot implements IUpdateListener {

    public static final SingleShot INSTANCE = new SingleShot();

    private SingleShot() {
        //
    }

    @Override
    public void onFireUpdate(IEnemy enemy, float dt) {
        final Game game = enemy.getAssocGame();
        final float x = enemy.getX();
        final float y = enemy.getY();

        final double angle = Math.atan2(game.player.getY() - y, game.player.getX() - x);
        game.addEnemyBullet(new PointBullet(x, y, 2, 1000 * (float) Math.cos(angle), 1000 * (float) Math.sin(angle)));
    }
}
