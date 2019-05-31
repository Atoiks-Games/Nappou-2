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

package org.atoiks.games.nappou2.equations;

import static org.atoiks.games.nappou2.Utils.lerp;

public final class CubicBezier implements IEquation {

    public final float p1;
    public final float p2;
    public final float p3;
    public final float p4;

    public CubicBezier(float p1, float p2, float p3, float p4) {
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
        this.p4 = p4;
    }

    @Override
    public float compute(float t) {
        final float q1 = lerp(p1, p2, t);
        final float q2 = lerp(p2, p3, t);
        final float q3 = lerp(p3, p4, t);

        final float r1 = lerp(q1, q2, t);
        final float r2 = lerp(q2, q3, t);

        return lerp(r1, r2, t);
    }
}
