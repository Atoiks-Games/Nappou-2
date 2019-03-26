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

import org.atoiks.games.framework2d.IGraphics;

import org.atoiks.games.nappou2.entities.IBullet;

import static org.atoiks.games.nappou2.Utils.isPtOutOfScreen;
import static org.atoiks.games.nappou2.Utils.centerSquareCollision;
import static org.atoiks.games.nappou2.Utils.intersectSegmentCircle;
import static org.atoiks.games.nappou2.TrigConstants.*;

public final class Beam extends AbstractBullet {

    private static final long serialVersionUID = 4412375L;
    private static final int ARR_SIZE = 8;

    private float mx, my;   // mid point of the beam
    private float sx, sy;   // displacement to get to the head of the beam
    private float halfThickness, halfLength;
    private float cosA, sinA;

    private final float[] dest = new float[ARR_SIZE];

    public Beam(float x, float y, float thickness, float length, final float angle, final float dmag) {
        this.halfThickness = thickness / 2;
        this.halfLength = length / 2;

        this.cosA = (float) Math.cos(angle);
        this.sinA = (float) Math.sin(angle);

        this.dx = dmag * this.cosA;
        this.dy = dmag * this.sinA;

        this.mx = x + (this.sx = this.halfLength * this.cosA);
        this.my = y + (this.sy = this.halfLength * this.sinA);
    }

    @Override
    public void translate(float dx, float dy) {
        this.mx += dx;
        this.my += dy;
    }

    @Override
    public void render(final IGraphics g) {
        g.setColor(color);
        g.fillPolygon(dest);
    }

    @Override
    public void update(final float dt) {
        final float dhoriz = this.mx += dx * dt;
        final float dvert  = this.my += dy * dt;

        /*
        let (mx, my) be (0, 0)
        the beam is a rectangle from (-thickness / 2, length / 2) to (thickness / 2, -length / 2)
        we rotate it around (0, 0) by angle + pi/2, which has fixed value,

        (x, y) --> rotate pi/2 --> (-y, x)

        let m = halfLength, n = halfThickness
        | cos -sin | * |-m -m m  m| = | -mcos+nsin -mcos-nsin +mcos-nsin +mcos+nsin |
        | sin  cos |   |-n  n n -n|   | -msin-ncos -msin+ncos +msin+ncos +msin-ncos |

        let k1 = -mcos+nsin, k2 = +mcos+nsin
        let k3 = +msin+ncos, k4 = -msin+ncos
        |  k1 -k2 -k1  k2 |
        | -k3  k4  k3 -k4 |
         */

        final float m = halfLength;
        final float n = halfThickness;

        final float k1 = n * sinA - m * cosA;
        final float k2 = n * sinA + m * cosA;
        final float k3 = n * cosA + m * sinA;
        final float k4 = n * cosA - m * sinA;

        dest[0] = dhoriz + k1;
        dest[1] = dvert - k3;
        dest[2] = dhoriz - k2;
        dest[3] = dvert + k4;
        dest[4] = dhoriz - k1;
        dest[5] = dvert + k3;
        dest[6] = dhoriz + k2;
        dest[7] = dvert - k4;
    }

    @Override
    public float getX() {
        return this.mx - this.sx;
    }

    @Override
    public float getY() {
        return this.my - this.sy;
    }

    @Override
    public boolean collidesWith(final float x1, final float y1, final float r1) {
        // Only perform accurate collision if the square formed by center
        // point (mx, my) with apothem collides with the circle also
        // approximated as a square with the apothem being its radius.

        // multiply by 1.5 to account for near square-shaped beams (technically it's sqrt(2))
        final float apothem = Math.max(halfThickness, halfLength) * 1.5f;
        if (centerSquareCollision(mx, my, apothem, x1, y1, r1)) {
            // Accurate collision checks if any of the sides intersect with
            // the circle.
            return intersectSegmentCircle(dest[0], dest[1], dest[2], dest[3], x1, y1, r1)
                || intersectSegmentCircle(dest[2], dest[3], dest[4], dest[5], x1, y1, r1)
                || intersectSegmentCircle(dest[4], dest[5], dest[6], dest[7], x1, y1, r1)
                || intersectSegmentCircle(dest[6], dest[7], dest[0], dest[1], x1, y1, r1);
        }
        return false;
    }

    @Override
    public boolean isOutOfScreen(final int w, final int h) {
        return isPtOutOfScreen(dest[0], dest[1], w, h)
            && isPtOutOfScreen(dest[2], dest[3], w, h)
            && isPtOutOfScreen(dest[4], dest[5], w, h)
            && isPtOutOfScreen(dest[6], dest[7], w, h);
    }
}
