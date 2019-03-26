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

import java.awt.geom.AffineTransform;

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
    private float halfThickness, length;

    private final float[] dest = new float[ARR_SIZE];
    private final AffineTransform mat;

    public Beam(float x, float y, float thickness, float length, float angle, float dmag) {
        this.halfThickness = thickness / 2;
        this.length = length;

        final float cosA = (float) Math.cos(angle);
        final float sinA = (float) Math.sin(angle);

        this.dx = dmag * cosA;
        this.dy = dmag * sinA;

        this.mx = x + (this.sx = length / 2 * cosA);
        this.my = y + (this.sy = length / 2 * sinA);

        this.mat = AffineTransform.getRotateInstance(angle - 3 * PI_DIV_2);
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
        this.mx += dx * dt;
        this.my += dy * dt;

        // If we calculate a beam with (x, y) as (0, 0),
        // it will be rectangle with diagonal from (-halfThickness, 0) to (halfThickness, -length).
        // we rotate it around (0, 0) by angle, which has fixed value,
        // then, we translate it by (x, y)

        final float len = -length;
        final float[] input = {
            -halfThickness, 0,
            halfThickness , 0,
            halfThickness , len,
            -halfThickness, len,
        };

        final AffineTransform trans = AffineTransform.getTranslateInstance(getX(), getY());
        trans.concatenate(mat);
        trans.transform(input, 0, dest, 0, ARR_SIZE / 2);
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
        final float apothem = Math.max(halfThickness * 2, length) / 2;
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
