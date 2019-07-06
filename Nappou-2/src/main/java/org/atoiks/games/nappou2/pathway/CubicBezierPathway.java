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
 * Follows a cubic bezier curve
 */
public final class CubicBezierPathway implements Pathway {

    private final Vector2 p1;
    private final Vector2 p2;
    private final Vector2 p3;
    private final Vector2 p4;

    private final float limit;
    private float elapsed;

    private Vector2 currentPos; // null means should be recomputed

    public CubicBezierPathway(float timeLimit, Vector2 p1, Vector2 p2, Vector2 p3, Vector2 p4) {
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
        this.p4 = p4;

        this.limit = timeLimit;
    }

    @Override
    public Vector2 getPosition() {
        Vector2 local = this.currentPos;
        if (local == null) {
            final float frac = this.elapsed / this.limit;

            final Vector2 q1 = Vector2.lerp(p1, p2, frac);
            final Vector2 q2 = Vector2.lerp(p2, p3, frac);
            final Vector2 q3 = Vector2.lerp(p3, p4, frac);

            final Vector2 r1 = Vector2.lerp(q1, q2, frac);
            final Vector2 r2 = Vector2.lerp(q2, q3, frac);

            this.currentPos = local = Vector2.lerp(r1, r2, frac);
        }
        return local;
    }

    @Override
    public void update(final float dt) {
        if (!hasFinished()) {
            this.elapsed += dt;
            this.currentPos = null;
        }
    }

    @Override
    public boolean hasFinished() {
        return this.elapsed >= this.limit;
    }
}
