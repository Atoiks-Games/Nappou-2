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

import org.atoiks.games.nappou2.entities.bullet.PointBullet;

public final class PointBulletInfo {

    public final float radius;
    public final float speed;

    public PointBulletInfo(float radius, float speed) {
        this.radius = radius;
        this.speed = speed;
    }

    public PointBullet createPointBullet(float x, float y, float angle) {
        return new PointBullet(x, y, radius, speed * (float) Math.cos(angle), speed * (float) Math.sin(angle));
    }
}
