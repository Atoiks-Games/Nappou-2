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
import static org.atoiks.games.nappou2.Utils.clamp01;

public final class CubicBezier implements Equation {

    public final float t1;
    public final float y1;
    public final float t2;
    public final float y2;

    public CubicBezier(float t1, float y1, float t2, float y2) {
        this.t1 = clamp01(t1);
        this.y1 = y1;
        this.t2 = clamp01(t2);
        this.y2 = y2;
    }

    @Override
    public float compute(float t) {
        // Cubic --> Quadratic
        final float qt1 = lerp(0, t1, t);
        final float qy1 = lerp(0, y1, t);
        final float qt2 = lerp(t1, t2, t);
        final float qy2 = lerp(y1, y2, t);
        final float qt3 = lerp(t2, 1, t);
        final float qy3 = lerp(y2, 1, t);

        // Quadratic --> Linear
        final float lt1 = lerp(qt1, qt2, t);
        final float ly1 = lerp(qy1, qy2, t);
        final float lt2 = lerp(qt2, qt3, t);
        final float ly2 = lerp(qy2, qy3, t);

        // Interpolate progress using interpolated time
        return lerp(ly1, ly2, lerp(lt1, lt2, t));
    }
}
