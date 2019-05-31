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

import java.util.function.Supplier;

import org.atoiks.games.nappou2.Vector2;

import org.atoiks.games.nappou2.pathway.IPathway;

import org.atoiks.games.nappou2.entities.bullet.PathwayPolygonBullet;

// Angle parameter is ignored:
//   Bullet can be rotated to an angle but not travel in that direction
//   (we do not bother rotating the polygon at all)
public final class PathwayPolygonBulletInfo implements BulletFactory {

    public final float[] coords;
    public final Supplier<? extends IPathway> pathway;

    public PathwayPolygonBulletInfo(float[] coords, Supplier<? extends IPathway> pathway) {
        this.coords = coords;
        this.pathway = pathway;
    }

    @Override
    public PathwayPolygonBullet createBullet(Vector2 position, float angle) {
        // Do not need to copy the coordinates since pathway polygon does not
        // mutate the coordinates at all!
        final PathwayPolygonBullet bullet = new PathwayPolygonBullet(coords, pathway.get());
        bullet.drift(position);
        return bullet;
    }

    @Override
    public PathwayPolygonBullet createBullet(float x, float y, final float angle) {
        return createBullet(new Vector2(x, y), angle);
    }
}
