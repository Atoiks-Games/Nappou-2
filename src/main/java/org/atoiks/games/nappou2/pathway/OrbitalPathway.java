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

/**
 * Pathway that orbits with a fixed width around a singular point
 */
public final class OrbitalPathway extends SizerOrbitalPathway<IdentitySizer> {

    public OrbitalPathway(Circular boundary, int direction, float speedMod, int startPos) {
        this(Circular.asElliptical(boundary),
                direction,
                speedMod,
                startPos);
    }

    public OrbitalPathway(final Elliptical boundary, int direction, float speedMod, int startPos) {
        // Direction is applied on the Y component
        super(boundary,
                new Vector2(1, direction),
                speedMod / 50,
                startPos % 4 * (float) (Math.PI / 2),
                IdentitySizer.INSTANCE);

        this.setOrbitalWidth(1);
    }
}
