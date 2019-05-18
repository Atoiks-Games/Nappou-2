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

public final class RadialPointEnemy extends PathwayEnemy {

    private static final long serialVersionUID = 1L;

    private final int score;
    private final int intervals;
    private final float speed;
    private final float radius;
    private final float fireInterval;
    private final float delay;
    private final float initialAngle;
    private final float anglePerInterval;

    private float time;
    private int bulletId;

    public RadialPointEnemy(int hp, int score, final float fireInterval, boolean immediateFire, float delay, float initialAngle, int intervals, float anglePerInterval, float radius, float speed) {
        super(hp);
        this.score = score;
        this.intervals = intervals;
        this.speed = speed;
        this.radius = radius;
        this.delay = delay;
        this.fireInterval = fireInterval;
        if (immediateFire) {
            bulletId = intervals;
        }
        this.initialAngle = initialAngle;
        this.anglePerInterval = anglePerInterval;
    }

    @Override
    public void customUpdate(float dt) {
        time += dt;
        if (bulletId >= intervals) {
            if (time >= fireInterval) bulletId = 0;
        } else if (time > delay) {
            float angle = initialAngle + bulletId * anglePerInterval;
            game.addEnemyBullet(new PointBullet(getX(), getY(), radius, speed * (float) Math.cos(angle), speed * (float) Math.sin(angle)));
            bulletId++;
            time = 0;
        }
    }

    @Override
    public int getScore() {
        return score;
    }
}
