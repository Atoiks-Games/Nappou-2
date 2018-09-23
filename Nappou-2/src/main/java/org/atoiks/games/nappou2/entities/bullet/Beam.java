/**
 *  Nappou-2
 *  Copyright (C) 2017-2018  Atoiks-Games <atoiks-games@outlook.com>

 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.

 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.

 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package org.atoiks.games.nappou2.entities.bullet;

import java.awt.Color;
import java.awt.geom.AffineTransform;

import org.atoiks.games.framework2d.IGraphics;

import org.atoiks.games.nappou2.entities.IBullet;

public final class Beam implements IBullet {

    private static final long serialVersionUID = 4412375L;

    private final float angle, dmag, cos, sin;
    private float x, y, thickness, length;

    private final float[] dest = new float[8];

    public Beam(float x, float y, float thickness, float length, float angle, float dmag) {
        this.x = x;
        this.y = y;
        this.thickness = thickness;
        this.length = length;
        this.dmag = dmag;

        final float rad = normalizeRadians(angle);
        this.angle = rad;
        this.cos = (float) Math.cos(rad);
        this.sin = (float) Math.sin(rad);
    }

    private static float normalizeRadians(float radians) {
        radians = (float) (radians % (2 * Math.PI));
        if (radians < 0) {
            radians += 2 * Math.PI;
        }
        return radians;
    }

    @Override
    public void translate(float dx, float dy) {
        this.x += dx;
        this.y += dy;
    }

    @Override
    public void render(final IGraphics g) {
        g.setColor(Color.white);
        g.fillPolygon(dest);
    }

    @Override
    public void update(final float dt) {
        this.x += getDx() * dt;
        this.y += getDy() * dt;

        final float angle = (float) (3 * Math.PI / 2 - this.angle);
        final float t2 = thickness / 2;
        final float l2 = length / 2;

        final AffineTransform t = AffineTransform.getRotateInstance(-angle, x, y);

        final float[] input = {
            x - t2, y - l2,
            x + t2, y - l2,
            x + t2, y + t2,
            x - t2, y + t2,
        };

        t.transform(input, 0, dest, 0, input.length / 2);
    }

    @Override
    public float getX() {
        return this.x;
    }

    @Override
    public float getY() {
        return this.y;
    }

    @Override
    public float getDx() {
        return cos * this.dmag;
    }

    @Override
    public float getDy() {
        return sin * this.dmag;
    }

    @Override
    public boolean collidesWith(float x1, float y1, float r1) {
        for (int i = 0; i < dest.length; i += 2) {
            final float startX = dest[i];
            final float startY = dest[i + 1];
            final float endX   = dest[(i + 2) % dest.length];
            final float endY   = dest[(i + 3) % dest.length];
            if (intersectSegmentCircle(startX, startY, endX, endY, x1, y1, r1)) {
                return true;
            }
        }
        return false;
    }

    private static boolean intersectSegmentCircle(float x1, float y1, float x2, float y2,
                                                  float cx, float cy, float cr) {
        // Taken from https://stackoverflow.com/a/10392860
        final float acx = cx - x1;
        final float acy = cy - y1;

        final float abx = x2 - x1;
        final float aby = y2 - y1;

        final float ab2 = abx * abx + aby * aby;
        final float acab = acx * abx + acy * aby;
        float t = acab / ab2;

        if (t < 0) {
            t = 0;
        } else if (t > 1) {
            t = 1;
        }

        final float hx = (abx * t) + x1 - cx;
        final float hy = (aby * t) + y1 - cy;

        return hx * hx + hy * hy <= cr * cr;
    }

    @Override
    public boolean isOutOfScreen(final int w, final int h) {
        if (angle == 0) return x > w;
        if (angle > 0 && angle < Math.PI / 2) return x > w && y > h;
        if (angle == Math.PI / 2) return y > h;
        if (angle > Math.PI / 2) return y > h && x < 0;
        if (angle == Math.PI) return x < 0;
        if (angle > Math.PI && angle < Math.PI * 3 / 2) return x < 0 && y < 0;
        if (angle == Math.PI * 3 / 2) return y < 0;
        return y < 0 && x > w;
    }
}