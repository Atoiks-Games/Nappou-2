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

import org.atoiks.games.nappou2.pathway.FixedAcceleration;

public final class FallingBullet extends PathwayPointBullet {

    private static final long serialVersionUID = 5844654826027301562L;

    public FallingBullet(Vector2 pos, float r, Vector2 vel, Vector2 acc) {
        super(r, new FixedAcceleration(pos, vel, acc));
    }

    public FallingBullet(float x, float y, float r, float speed, float direction, float accel, boolean xdir) {
        this(new Vector2(x, y), r, Vector2.fromPolar(speed, direction), (xdir ? Vector2.UNIT_X : Vector2.UNIT_Y).mul(accel));
    }
}
