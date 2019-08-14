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

package org.atoiks.games.nappou2.graphics.shapes;

import org.atoiks.games.nappou2.Vector2;

public final class ImmutableEllipses implements Elliptical {

    private final Vector2 pos;
    private final Vector2 semis;

    public ImmutableEllipses(Vector2 position, float semiX, float semiY) {
        this(position, new Vector2(semiX, semiY));
    }

    public ImmutableEllipses(Vector2 position, Vector2 semiAxes) {
        this.pos = position != null ? position : Vector2.ZERO;
        this.semis = Vector2.abs(semiAxes);
    }

    public static Elliptical formedBetween(Vector2 u, Vector2 v) {
        final Vector2 axes = v.sub(u).div(2);
        return new ImmutableEllipses(u.add(axes), axes);
    }

    @Override
    public Vector2 getSemiAxes() {
        return this.semis;
    }

    @Override
    public Vector2 getPosition() {
        return this.pos;
    }
}
