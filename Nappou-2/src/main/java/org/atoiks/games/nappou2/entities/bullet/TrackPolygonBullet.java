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

package org.atoiks.games.nappou2.entities.bullet;

import org.atoiks.games.nappou2.entities.ITrackable;

import org.atoiks.games.nappou2.pathway.TrackingPathway;

public final class TrackPolygonBullet extends PathwayPolygonBullet {

    public TrackPolygonBullet(float[] coords, ITrackable entity, float pathScale, float moveTime, float delay) {
        // Assumes coords is already placed at initial position.
        // In that case, pathway only needs to start from the origin
        // since the actual position is (coords + pathway + drift)
        super(coords, new TrackingPathway(entity, pathScale, moveTime, delay));
    }
}
