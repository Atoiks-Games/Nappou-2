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
 * Pathway that follows a trackable entity with constant speed. The path it takes is linear.
 */
public final class FixedSpeedTrackingPathway extends TrackingPathway {

    private final float speed;

    private float timer;

    public FixedSpeedTrackingPathway(final Trackable entity, float timer, float speed) {
        super(entity);
        this.speed = speed;
        this.timer = timer;
    }

    public void setPosition(Vector2 pos) {
        this.position = pos != null ? pos : Vector2.ZERO;
    }

    @Override
    public void update(float dt) {
        if (this.timer > 0) {
            this.timer -= 10 * dt;
        }

        if (this.timer >= 0) {
            this.velocity = this.entity.getPosition().sub(this.position)
                    .normalize()
                    .mul(this.speed);
        }

        this.position = Vector2.muladd(
                dt,
                this.velocity,
                this.position);
    }
}
