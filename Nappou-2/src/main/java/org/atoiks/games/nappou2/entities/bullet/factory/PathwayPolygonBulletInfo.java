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
import java.util.function.BiFunction;

import org.atoiks.games.nappou2.Vector2;

import org.atoiks.games.nappou2.pathway.IPathway;
import org.atoiks.games.nappou2.pathway.FixedVelocity;

import org.atoiks.games.nappou2.entities.bullet.PathwayPolygonBullet;

// Angle parameter is ignored:
//   Bullet can be rotated to an angle but not travel in that direction
//   (we do not bother rotating the polygon at all)
public final class PathwayPolygonBulletInfo implements BulletFactory {

    public final float[] coords;
    public final BiFunction<? super Vector2, ? super Float, ? extends IPathway> pathway;

    public PathwayPolygonBulletInfo(float[] coords, Supplier<? extends IPathway> pathway) {
        this(coords, (_a, _b) -> pathway.get());
    }

    public PathwayPolygonBulletInfo(float[] coords, BiFunction<? super Vector2, ? super Float, ? extends IPathway> pathway) {
        this.coords = coords;
        this.pathway = pathway;
    }

    @Override
    public PathwayPolygonBullet createBullet(Vector2 position, float angle) {
        // Do not need to copy the coordinates since pathway polygon does not
        // mutate the coordinates at all!
        return new PathwayPolygonBullet(coords, pathway.apply(position, angle));
    }

    @Override
    public PathwayPolygonBullet createBullet(float x, float y, final float angle) {
        return createBullet(new Vector2(x, y), angle);
    }

    public static PathwayPolygonBulletInfo createLegacyPolygonBullet(float[] coords, final float speed) {
        return new PathwayPolygonBulletInfo(coords, (position, angle) ->
                new FixedVelocity(position, Vector2.fromPolar(speed, angle)));
    }
}
