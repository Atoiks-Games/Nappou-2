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

public final class RadialPattern extends TimedCounter {

    private final BulletFactory factory;

    private final float initialAngle;
    private final float anglePerInterval;

    public RadialPattern(float fireInterval, boolean immediateFire, float delay, float initialAngle, int intervals, float anglePerInterval, BulletFactory factory) {
        super(intervals, fireInterval, delay, immediateFire ? TimedCounter.InitialState.DO_PAUSE : TimedCounter.InitialState.COUNTER_RESET);

        this.factory = factory;

        this.initialAngle = initialAngle;
        this.anglePerInterval = anglePerInterval;
    }

    @Override
    protected void onTimerUpdate(IEnemy enemy, float dt) {
        final Game game = enemy.getAssocGame();
        final Vector2 pos = enemy.getPosition();
        final int bulletId = this.getCount();

        game.addEnemyBullet(factory.createBullet(pos, initialAngle + bulletId * anglePerInterval));
    }
}
