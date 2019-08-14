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

import org.atoiks.games.nappou2.sizer.IdentitySizer;

import org.atoiks.games.nappou2.graphics.shapes.Circular;
import org.atoiks.games.nappou2.graphics.shapes.Elliptical;
import org.atoiks.games.nappou2.graphics.shapes.ImmutableEllipses;

/**
 * Pathway that orbits with a fixed width around a singular point
 */
public final class OrbitalPathway extends SizerOrbitalPathway<IdentitySizer> {

    // Use if path is circular
    public OrbitalPathway(float radius, float x, float y, int direction, float speedMod, int startPos) {
        this(radius, radius, x, y, direction, speedMod, startPos);
    }

    public OrbitalPathway(Circular boundary, int direction, float speedMod, int startPos) {
        this(Circular.asElliptical(boundary),
                direction,
                speedMod,
                startPos);
    }

    // Use if path is elliptical
    public OrbitalPathway(float rx, float ry, float x, float y, int direction, float speedMod, int startPos) {
        this(new ImmutableEllipses(new Vector2(x, y), new Vector2(rx, ry)),
                direction,
                speedMod,
                startPos);
    }

    public OrbitalPathway(Vector2 axis, Vector2 center, int direction, float speedMod, int startPos) {
        // Direction is applied on the Y component
        super(new Vector2(1, direction).mul(axis),
                center,
                speedMod / 50,
                startPos % 4 * (float) (Math.PI / 2),
                IdentitySizer.INSTANCE);

        this.setOrbitalWidth(1);
    }

    public OrbitalPathway(final Elliptical boundary, int direction, float speedMod, int startPos) {
        this(boundary.getSemiAxes(), boundary.getPosition(), direction, speedMod, startPos);
    }
}
