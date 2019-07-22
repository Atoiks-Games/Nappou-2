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
public final class CollapsingOrbitalPathway implements UnboundPathway {

    private Vector2 axis;
    private final Vector2 center;

    private final Vector2 direction;
    private final float mod;
    private final double spos;
    private float r = 1;

    private Vector2 position;

    private int cycles;

    // Use if path is elliptical
    public CollapsingOrbitalPathway(float rx, float ry, float x, float y, int direction, float speedMod, double startPos) {
        this(new Vector2(rx, ry), new Vector2(x, y), direction, speedMod, startPos);
    }

    public CollapsingOrbitalPathway(Vector2 axis, Vector2 center, int direction, float speedMod, double startPos) {
        this.axis = axis;
        this.center = center;

        // Direction is applied on the Y component
        this.direction = new Vector2(1, direction);
        this.mod = speedMod;
        this.spos = startPos;

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

        r -= dt;

        if (r < 0) {
            r = 100;
        }

        final float k = mod / (10 * r) * cycles / 50 + (float) spos;
        this.position = Vector2.muladd(this.axis, Vector2.fromPolar(r, k).mul(direction), center);
    }
}
