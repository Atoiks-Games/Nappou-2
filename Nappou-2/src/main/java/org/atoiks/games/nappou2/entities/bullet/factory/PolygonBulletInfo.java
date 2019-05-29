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

import java.util.Arrays;

import org.atoiks.games.nappou2.Vector2;

import org.atoiks.games.nappou2.entities.Game;
import org.atoiks.games.nappou2.entities.bullet.PolygonBullet;

public final class PolygonBulletInfo implements BulletFactory {

    public final float[] coords;
    public final float speed;

    public PolygonBulletInfo(float[] coords, float speed) {
        this.coords = coords;
        this.speed = speed;
    }

    @Override
    public PolygonBullet createBullet(float x, float y, float angle) {
        final PolygonBullet bullet = new PolygonBullet(Arrays.copyOf(coords, coords.length), speed * (float) Math.cos(angle), speed * (float) Math.sin(angle));
        bullet.drift(new Vector2(x, y));
        return bullet;
    }
}
