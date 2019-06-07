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
 * Pathway with constant velocity
 */
public final class FixedVelocity implements UnboundPathway {

    private final Vector2 velocity;

    private Vector2 position;

    public FixedVelocity(float x, float y, float dx, float dy) {
        this(new Vector2(x, y), new Vector2(dx, dy));
    }

    public FixedVelocity(final Vector2 pos, final Vector2 velocity) {
        this.velocity = velocity;
        this.position = pos;
    }

    @Override
    public Vector2 getPosition() {
        return this.position;
    }

    @Override
    public void update(final float dt) {
        this.position = Vector2.muladd(dt, this.velocity, this.position);
    }

    @Override
    public boolean hasFinished() {
        return false;
    }
}
