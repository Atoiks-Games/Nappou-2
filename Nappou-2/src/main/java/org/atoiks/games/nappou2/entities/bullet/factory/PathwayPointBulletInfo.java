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

import org.atoiks.games.nappou2.entities.bullet.PathwayPointBullet;

// Angle parameter is ignored:
//   Bullet can be rotated to an angle but not travel in that direction
public final class PathwayPointBulletInfo implements BulletFactory {

    public final float radius;
    public final Supplier<? extends IPathway> pathway;

    public PathwayPointBulletInfo(float radius, Supplier<? extends IPathway> pathway) {
        this.radius = radius;
        this.pathway = pathway;
    }

    @Override
    public PathwayPointBullet createBullet(Vector2 position, float angle) {
        final PathwayPointBullet bullet = new PathwayPointBullet(radius, pathway.get());
        bullet.drift(position);
        return bullet;
    }

    @Override
    public PathwayPointBullet createBullet(float x, float y, final float angle) {
        return createBullet(new Vector2(x, y), angle);
    }
}
