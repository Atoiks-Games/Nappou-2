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

import se.tube42.lib.tweeny.Item;

import org.atoiks.games.nappou2.entities.bullet.Beam;

public final class RadialBeamEnemy extends TweenEnemy {

    private static final long serialVersionUID = 7329566493126725388L;

    private final int score;
    private final int intervals;
    private final float thickness;
    private final float length;
    private final float fireInterval;
    private final float delay;
    private final float initialAngle;
    private final float anglePerInterval;
    private final float speed;

    private float time;
    private int bulletId;

    private boolean firstRun = true;

    public RadialBeamEnemy(int hp, int score, Item tweenInfo, final float fireInterval, boolean immediateFire, float delay, float initialAngle, int intervals, float anglePerInterval, float thickness, int length, float speed) {
        super(hp, tweenInfo);
        this.score = score;
        this.thickness = thickness;
        this.length = length;
        this.initialAngle = initialAngle;
        this.anglePerInterval = anglePerInterval;
        this.speed = speed;
        this.intervals = intervals;
        this.delay = delay;
        this.fireInterval = fireInterval;
        if (!immediateFire) {
            bulletId = intervals;
        }
    }

    @Override
    public void update(float dt) {
        if (firstRun) {
            firstRun = false;
            return;
        }

        time += dt;
        if (bulletId >= intervals) {
            if (time >= fireInterval) bulletId = 0;
        } else if (time > delay) {
            game.addEnemyBullet(new Beam(getX(), getY(), thickness, length, initialAngle + bulletId * anglePerInterval, speed));
            ++bulletId;
            time = 0;
        }
    }

    @Override
    public int getScore() {
        return score;
    }
}
