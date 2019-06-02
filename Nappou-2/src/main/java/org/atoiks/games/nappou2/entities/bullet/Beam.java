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

package org.atoiks.games.nappou2.entities.bullet;

import org.atoiks.games.nappou2.Vector2;

public final class Beam extends BeamLike {

    private static final long serialVersionUID = -810469078842689712L;

    public Beam(Vector2 position, float thickness, float length, Vector2 velocity) {
        super(position, thickness, velocity);

        this.length = length;
    }

    public Beam(float x, float y, float thickness, float length, final float angle, final float dmag) {
        this(new Vector2(x, y), thickness, length, Vector2.fromPolar(dmag, angle));
    }
}
