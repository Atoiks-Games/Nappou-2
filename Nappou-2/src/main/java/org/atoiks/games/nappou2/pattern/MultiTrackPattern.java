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

import org.atoiks.games.nappou2.entities.bullet.factory.BulletFactory;

public final class MultiTrackPattern implements IAttackPattern {

    private final float[] angles;
    private final BulletFactory factory;

    public MultiTrackPattern(BulletFactory factory, float... angles) {
        this.angles = angles;
        this.factory = factory;
    }

    @Override
    public void onFireUpdate(IEnemy enemy, float dt) {
        final Game game = enemy.getAssocGame();
        final Vector2 pos = enemy.getPosition();
        final float x = pos.getX();
        final float y = pos.getY();

        final float base = (float) Math.atan2(game.player.getY() - y, game.player.getX() - x);
        for (final float angle : angles) {
            game.addEnemyBullet(factory.createBullet(pos, base + angle));
        }
    }
}
