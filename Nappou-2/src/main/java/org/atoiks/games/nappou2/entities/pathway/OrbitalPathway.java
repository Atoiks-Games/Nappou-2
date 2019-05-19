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

package org.atoiks.games.nappou2.entities.pathway;

import org.atoiks.games.nappou2.entities.IPathway;

/**
 * An unlimited pathway that orbits around a singular point
 */
public final class OrbitalPathway implements IPathway {

    private final float radius;
    private final float orbitX;
    private final float orbitY;

    private final int direction;
    private final float mod;
    private final int spos;

    private float x;
    private float y;

    private int cycles;

    public OrbitalPathway(float radius, float x, float y, int direction, float speedMod, int startPos) {
        this.radius = radius;
        this.orbitX = x;
        this.orbitY = y;

        this.direction = direction;
        this.mod = speedMod;
        this.spos = startPos % 4;

        // calcuate initial position here, update will do increment cycles
        this.cycles = -1;
        update(0);
    }

    @Override
    public float getX() {
        return x;
    }

    @Override
    public float getY() {
        return y;
    }

    @Override
    public void update(final float dt) {
        cycles++;

        final double k = mod * cycles / 50 + spos * Math.PI / 2;
        y = orbitY + direction * radius * (float) Math.sin(k);
        x = orbitX + radius * (float) Math.cos(k);
    }

    @Override
    public boolean canFinish() {
        return false;
    }

    @Override
    public boolean hasFinished() {
        return false;
    }
}
