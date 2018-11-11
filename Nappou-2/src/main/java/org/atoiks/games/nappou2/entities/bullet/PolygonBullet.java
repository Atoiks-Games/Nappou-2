/**
 *  Nappou-2
 *  Copyright (C) 2017-2018  Atoiks-Games <atoiks-games@outlook.com>
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

import java.util.Arrays;

import org.atoiks.games.framework2d.IGraphics;

import org.atoiks.games.nappou2.entities.IBullet;

import static org.atoiks.games.nappou2.Utils.isPtOutOfScreen;
import static org.atoiks.games.nappou2.Utils.centerSquareCollision;
import static org.atoiks.games.nappou2.Utils.intersectSegmentCircle;

public class PolygonBullet extends IBullet {

    private static final long serialVersionUID = 2983462354L;

    private static final int SCREEN_EDGE_BUFFER = 16;

    // Stored as x1, y1, ..., xn, yn pairs
    protected final float[] coords;
    protected float dx, dy;

    protected float boundX, boundY, boundR;

    protected PolygonBullet(final float[] coords) {
        final float[] copy = Arrays.copyOf(coords, coords.length);
        this.coords = copy;

        // Reshape the bounding box here
        float x1 = Float.POSITIVE_INFINITY;
        float y1 = Float.POSITIVE_INFINITY;
        float x2 = Float.NEGATIVE_INFINITY;
        float y2 = Float.NEGATIVE_INFINITY;
        for (int i = 0; i < coords.length; i += 2) {
            final float x = coords[i];
            final float y = coords[i + 1];
            if (x < x1) x1 = x;
            if (x > x2) x2 = x;
            if (y < y1) y1 = y;
            if (y > y2) y2 = y;
        }

        // boundX boundY is center of bounding box
        boundX = (x1 + x2) / 2;
        boundY = (y1 + y2) / 2;
        // + 4 just in case for some reason the shape is actually not contained
        // properly within the bounding box
        boundR = Math.max(x2 - x1, y2 - y1) / 2 + 4;
    }

    public PolygonBullet(final float[] coords, float dx, float dy) {
        this(coords);

        this.dx = dx;
        this.dy = dy;
    }

    @Override
    public void translate(float dx, float dy) {
        for (int i = 0; i < coords.length; i += 2) {
            coords[i] += dx;
            coords[i + 1] += dy;
        }
        boundX += dx;
        boundY += dy;
    }

    @Override
    public void render(final IGraphics g) {
        g.setColor(color);
        g.drawPolygon(coords);
    }

    @Override
    public void update(final float dt) {
        translate(this.dx * dt, this.dy * dt);
    }

    @Override
    public float getX() {
        return this.coords[0];
    }

    @Override
    public float getY() {
        return this.coords[1];
    }

    @Override
    public float getDx() {
        return this.dx;
    }

    @Override
    public float getDy() {
        return this.dy;
    }

    @Override
    public boolean collidesWith(final float x1, final float y1, final float r1) {
        if (centerSquareCollision(boundX, boundY, boundR, x1, y1, r1)) {
            for (int i = 0; i < coords.length; i += 2) {
                final float startX = coords[i];
                final float startY = coords[i + 1];
                final float endX   = coords[(i + 2) % coords.length];
                final float endY   = coords[(i + 3) % coords.length];
                if (intersectSegmentCircle(startX, startY, endX, endY, x1, y1, r1)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean isOutOfScreen(final int w, final int h) {
        for (int i = 0; i < coords.length; i += 2) {
            if (isPtOutOfScreen(coords[i], coords[i + 1], w, h)) {
                return true;
            }
        }
        return false;
    }
}
