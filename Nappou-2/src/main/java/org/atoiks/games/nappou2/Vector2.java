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

package org.atoiks.games.nappou2;

public final class Vector2 {

    public static final Vector2 ZERO = new Vector2(0, 0);
    public static final Vector2 ONE = new Vector2(1, 1);
    public static final Vector2 UNIT_X = new Vector2(1, 0);
    public static final Vector2 UNIT_Y = new Vector2(0, 1);

    private final float x;
    private final float y;

    public Vector2(final Vector2 vec) {
        this(vec.x, vec.y);
    }

    public Vector2(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getLength() {
        return (float) Math.sqrt(x * x + y * y);
    }

    public float getLengthSquared() {
        return x * x + y * y;
    }

    public float getTheta() {
        return (float) Math.atan2(y, x);
    }

    public Vector2 normalize() {
        final float norm = getLength();
        return new Vector2(x / norm, y / norm);
    }

    public Vector2 add(final Vector2 v) {
        return new Vector2(x + v.x, y + v.y);
    }

    public Vector2 sub(final Vector2 v) {
        return new Vector2(x - v.x, y - v.y);
    }

    public Vector2 mul(final Vector2 v) {
        return new Vector2(x * v.x, y * v.y);
    }

    public Vector2 div(final Vector2 v) {
        return new Vector2(x / v.x, y / v.y);
    }

    public Vector2 mul(final float k) {
        return new Vector2(x * k, y * k);
    }

    public Vector2 div(final float k) {
        return new Vector2(x / k, y / k);
    }

    public float dot(final Vector2 v) {
        return x * v.x + y * v.y;
    }

    public float cross(final Vector2 v) {
        return x * v.y - y * v.x;
    }

    public Vector2 neg() {
        return new Vector2(-x, -y);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(x, y);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null) return false;
        if (obj.getClass() != this.getClass()) return false;

        final Vector2 vec = (Vector2) obj;
        return vec.x == x && vec.y == y;
    }

    @Override
    public String toString() {
        return "" + '(' + x + ',' + y + ')';
    }

    public static Vector2 fromCartesian(float x, float y) {
        return new Vector2(x, y);
    }

    public static Vector2 fromPolar(final float r, final float thetaRad) {
        return new Vector2(r * (float) Math.cos(thetaRad), r * (float) Math.sin(thetaRad));
    }

    public static Vector2 muladd(final Vector2 u, final Vector2 v, final Vector2 w) {
        // (u * v) + w
        return new Vector2(u.x * v.x + w.x, u.y * v.y + w.y);
    }

    public static Vector2 clamp(final Vector2 v, final Vector2 min, final Vector2 max) {
        return new Vector2(
                Utils.clamp(v.x, min.x, max.x),
                Utils.clamp(v.y, min.y, max.y));
    }

    public static boolean inRangeExclusive(final Vector2 v, final Vector2 lo, final Vector2 hi) {
        final float vx = v.getX();
        final float vy = v.getY();
        return lo.getX() < vx && vx < hi.getX()
            && lo.getY() < vy && vy < hi.getY();
    }
}
