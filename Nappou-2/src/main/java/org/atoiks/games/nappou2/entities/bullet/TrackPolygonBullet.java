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

public final class TrackPolygonBullet extends PolygonBullet {

    private static final long serialVersionUID = 2983462354L;

    private static final int SCREEN_EDGE_BUFFER = 16;

    private final ITrackable entity;
    private final float scale;
    private final float moveTime;
    private final float delay;

    private float time;
    private boolean moving;

    public TrackPolygonBullet(float[] coords, ITrackable entity, float pathScale, float moveTime, float delay) {
        super(coords);

        this.entity = entity;
        this.scale = pathScale;
        this.moveTime = moveTime;
        this.delay = delay;
    }

    @Override
    public void update(final float dt) {
        time += dt;
        if (moving) {
            if (time >= moveTime) {
                moving = false;
                time = 0;
            } else {
                super.update(dt);
            }
        } else if (time >= delay) {
            // Re-calculate endpoints
            dx = scale * (entity.getX() - coords[0]) / moveTime;
            dy = scale * (entity.getY() - coords[1]) / moveTime;
            moving = true;
            time = 0;
        }
    }
}
