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
 * Pathway that orbits around a singular point
 */
public final class OrbitalPathway implements UnboundPathway {

    private final Vector2 scaledAxis;
    private final Vector2 center;
    private final float mod;
    private final int spos;

    private Vector2 position;

    private int cycles;

    // Use if path is circular
    public OrbitalPathway(float radius, float x, float y, int direction, float speedMod, int startPos) {
        this(radius, radius, x, y, direction, speedMod, startPos);
    }

    // Use if path is elliptical
    public OrbitalPathway(float rx, float ry, float x, float y, int direction, float speedMod, int startPos) {
        this(new Vector2(rx, ry), new Vector2(x, y), direction, speedMod, startPos);
    }

    public OrbitalPathway(Vector2 axis, Vector2 center, int direction, float speedMod, int startPos) {
        // Direction is applied on the Y component
        this.scaledAxis = new Vector2(1, direction).mul(axis);
        this.center = center;
        this.mod = speedMod;
        this.spos = startPos % 4;

        // calcuate initial position here, update will do increment cycles
        this.cycles = -1;
        update(0);
    }

    @Override
    public Vector2 getPosition() {
        return this.position;
    }

    @Override
    public void update(final float dt) {
        cycles++;

        final float k = mod * cycles / 50 + spos * (float) Math.PI / 2;
        this.position = Vector2.muladd(this.scaledAxis, Vector2.fromPolar(1, k), center);
    }
}
