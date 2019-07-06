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

import org.atoiks.games.nappou2.entities.bullet.factory.BulletFactory;

public final class TrackingPattern extends TimedCounter {

    private final BulletFactory factory;

    private final float[] angleOffsets;

    public TrackingPattern(float fireInterval, boolean immediateFire, float delay, float[] angleOffsets, BulletFactory factory) {
        super(angleOffsets.length, fireInterval, delay, immediateFire ? TrackingPattern.InitialState.COUNTER_RESET : TrackingPattern.InitialState.DO_PAUSE);

        this.factory = factory;
        this.angleOffsets = angleOffsets;
    }

    @Override
    protected void onTimerUpdate(Enemy enemy, float dt) {
        final Game game = enemy.getAssocGame();
        final Vector2 pos = enemy.getPosition();
        final float angle = Vector2.angleBetween(pos, game.player.getPosition()) + angleOffsets[this.getCount()];

        game.addEnemyBullet(factory.createBullet(pos, angle));
    }
}
