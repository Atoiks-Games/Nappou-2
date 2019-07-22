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

import org.atoiks.games.nappou2.sizer.Sizer;

/**
 * Pathway that orbits around a singular point
 */
public abstract class SizerOrbitalPathway<T extends Sizer> implements UnboundPathway {

    protected final T sizer;

    private final Vector2 axis;
    private final Vector2 center;
    private final float mod;
    private final float baseAngle;

    private Vector2 position;
    private float r = 1;

    private int cycles;

    protected SizerOrbitalPathway(Vector2 axis, Vector2 center, float speedMod, float baseAngle, T sizer) {
        this.axis = axis;
        this.center = center;
        this.mod = speedMod;
        this.baseAngle = baseAngle;

        this.sizer = sizer;

        this.computePosition();
    }

    public void setOrbitalWidth(float width) {
        this.r = width;
        this.computePosition();
    }

    @Override
    public Vector2 getPosition() {
        return this.position;
    }

    @Override
    public void update(final float dt) {
        cycles++;
        this.r = this.sizer.getNextSize(this.r, dt);
        this.computePosition();
    }

    private void computePosition() {
        final float k = this.mod * this.cycles / this.r + this.baseAngle;
        this.position = Vector2.muladd(this.axis, Vector2.fromPolar(this.r, k), this.center);
    }
}
