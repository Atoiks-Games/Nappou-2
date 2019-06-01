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

/**
 * Pathway with constant acceleration
 */
public final class FixedAcceleration implements UnboundPathway {

    private final Vector2 acceleration;

    private Vector2 velocity;
    private Vector2 position;

    public FixedAcceleration(float x, float y, float d2x, float d2y) {
        this(new Vector2(x, y), Vector2.ZERO, new Vector2(d2x, d2y));
    }

    public FixedAcceleration(final Vector2 pos, final Vector2 acceleration) {
        this(pos, Vector2.ZERO, acceleration);
    }

    public FixedAcceleration(float x, float y, float dx, float dy, float d2x, float d2y) {
        this(new Vector2(x, y), new Vector2(dx, dy), new Vector2(d2x, d2y));
    }

    public FixedAcceleration(final Vector2 pos, final Vector2 velocity, final Vector2 acceleration) {
        this.acceleration = acceleration;
        this.velocity = velocity;
        this.position = pos;
    }

    @Override
    public Vector2 getPosition() {
        return this.position;
    }

    @Override
    public void update(final float dt) {
        this.velocity = this.velocity.add(this.acceleration.mul(dt));
        this.position = this.position.add(this.velocity.mul(dt));
    }

    @Override
    public boolean hasFinished() {
        return false;
    }
}
