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

package org.atoiks.games.nappou2.entities.bullet.factory;

import org.atoiks.games.nappou2.Vector2;

import org.atoiks.games.nappou2.entities.bullet.Ray;

public final class RayInfo implements BulletFactory {

    public final float growthRate;
    public final float width;
    public final float maxLength;
    public final float speed;

    public RayInfo(float maxLength, float width, final float speed) {
        // uses speed as growth rate
        this(maxLength, speed, width, speed);
    }

    public RayInfo(float maxLength, float growthRate, float width, float speed) {
        this.growthRate = growthRate;
        this.width = width;
        this.maxLength = maxLength;
        this.speed = speed;
    }

    @Override
    public Ray createBullet(Vector2 pos, float angle) {
        return new Ray(pos, maxLength, growthRate, width, Vector2.fromPolar(speed, angle));
    }

    @Override
    public Ray createBullet(float x, float y, float angle) {
        return createBullet(new Vector2(x, y), angle);
    }
}
