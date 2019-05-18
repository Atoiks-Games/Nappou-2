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

import org.atoiks.games.nappou2.entities.bullet.Beam;

public final class TrackBeamEnemy extends PathwayEnemy {

    private static final long serialVersionUID = -2145973374641410758L;

    private final int score;
    private final float thickness;
    private final int length;
    private final float speed;
    private final float fireInterval;
    private final float delay;
    private final float[] angleOffsets;

    private float time;
    private int bulletId;

    public TrackBeamEnemy(int hp, int score, final float fireInterval, boolean immediateFire, float delay, float[] angleOffsets, float thickness, int length, float speed) {
        super(hp);
        this.score = score;
        this.thickness = thickness;
        this.length = length;
        this.speed = speed;
        this.angleOffsets = angleOffsets;
        this.delay = delay;
        this.fireInterval = fireInterval;
        if (!immediateFire) {
            bulletId = angleOffsets.length;
        }
    }

    @Override
    public void customUpdate(float dt) {
        time += dt;
        if (bulletId >= angleOffsets.length) {
            if (time >= fireInterval) bulletId = 0;
        } else if (time > delay) {
            final float x = getX();
            final float y = getY();
            final float angle = (float) Math.atan2(game.player.getY() - y, game.player.getX() - x);
            game.addEnemyBullet(new Beam(x, y, thickness, length, angle + angleOffsets[bulletId], speed));
            ++bulletId;
            time = 0;
        }
    }

    @Override
    public int getScore() {
        return score;
    }
}
