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

import org.atoiks.games.nappou2.entities.Game;
import org.atoiks.games.nappou2.entities.IEnemy;

import org.atoiks.games.nappou2.entities.bullet.factory.BulletFactory;

public final class TrackingPattern implements IAttackPattern {

    private final BulletFactory factory;

    private final float fireInterval;
    private final float delay;
    private final float[] angleOffsets;

    private float time;
    private int bulletId;

    public TrackingPattern(float fireInterval, boolean immediateFire, float delay, float[] angleOffsets, BulletFactory factory) {
        this.factory = factory;

        this.fireInterval = fireInterval;
        this.delay = delay;
        this.angleOffsets = angleOffsets;

        if (!immediateFire) {
            bulletId = angleOffsets.length;
        }
    }

    @Override
    public void onFireUpdate(IEnemy enemy, float dt) {
        time += dt;
        if (bulletId >= angleOffsets.length) {
            if (time >= fireInterval) bulletId = 0;
        } else if (time > delay) {
            final Game game = enemy.getAssocGame();
            final float x = enemy.getX();
            final float y = enemy.getY();

            final float angle = (float) Math.atan2(game.player.getY() - y, game.player.getX() - x) + angleOffsets[bulletId];
            game.addEnemyBullet(factory.createBullet(x, y, angle));
            ++bulletId;
            time = 0;
        }
    }
}
