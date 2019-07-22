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

package org.atoiks.games.nappou2.pathway;

import org.atoiks.games.nappou2.Vector2;

import org.atoiks.games.nappou2.entities.Trackable;

/**
 * Pathway that follows a trackable entity. The path it takes is linear.
 */
public final class TrackingPathway implements UnboundPathway {

    // Should this be a weak reference?
    private final Trackable entity;
    private final float scale;
    private final float moveTime;
    private final float delay;

    private Vector2 velocity;
    private Vector2 position;

    private float time;
    private boolean moving;

    public TrackingPathway(Trackable entity, float pathScale, float moveTime, float delay) {
        this(Vector2.ZERO, entity, pathScale, moveTime, delay);
    }

    public TrackingPathway(Vector2 pos, Trackable entity, float pathScale, float moveTime, float delay) {
        this.entity = entity;
        this.scale = pathScale;
        this.moveTime = moveTime;
        this.delay = delay;

        this.setPosition(pos);
    }

    public void setPosition(Vector2 pos) {
        this.position = pos;
        this.calculateEndpoint();
    }

    private void calculateEndpoint() {
        // velocity = scale * (entity's position - my location) / (move time)
        this.velocity = entity.getPosition().sub(this.position).mul(scale / moveTime);
    }

    @Override
    public Vector2 getPosition() {
        return this.position;
    }

    @Override
    public void update(final float dt) {
        time += dt;
        if (moving) {
            if (time >= moveTime) {
                moving = false;
                time = 0;
            } else {
                this.position = Vector2.muladd(dt, this.velocity, this.position);
            }
        } else if (time >= delay) {
            this.calculateEndpoint();
            moving = true;
            time = 0;
        }
    }
}
