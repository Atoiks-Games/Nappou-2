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

import java.io.Serializable;

public final class Vector2 implements Serializable {

    private static final long serialVersionUID = 5016983330876743514L;

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

    public static Vector2 muladd(final float u, final Vector2 v, final Vector2 w) {
        // (u * v) + w
        return new Vector2(u * v.x + w.x, u * v.y + w.y);
    }

    public static Vector2 clamp(final Vector2 v, final Vector2 min, final Vector2 max) {
        return new Vector2(
                Utils.clamp(v.x, min.x, max.x),
                Utils.clamp(v.y, min.y, max.y));
    }

    public static Vector2 lerp(final Vector2 start, final Vector2 end, final float frac) {
        return new Vector2(
                Utils.lerp(start.x, end.x, frac),
                Utils.lerp(start.y, end.y, frac));
    }

    public static Vector2 abs(final Vector2 v) {
        return new Vector2(
                Math.abs(v.x),
                Math.abs(v.y));
    }

    public static Vector2 min(final Vector2 u, final Vector2 v) {
        return new Vector2(
                Math.min(u.x, v.x),
                Math.min(u.y, v.y));
    }

    public static Vector2 max(final Vector2 u, final Vector2 v) {
        return new Vector2(
                Math.max(u.x, v.x),
                Math.max(u.y, v.y));
    }

    public static Vector2 rotateBy(final Vector2 u, final float angle) {
        final float cos = (float) Math.cos(angle);
        final float sin = (float) Math.sin(angle);
        return new Vector2(
                cos * u.x - sin * u.y,
                sin * u.x + cos * u.y);
    }

    public static float angleBetween(final Vector2 u, final Vector2 v) {
        return (float) Math.atan2(v.y - u.y, v.x - u.x);
    }

    public static float distanceBetween(final Vector2 u, final Vector2 v) {
        final float dx = v.x - u.x;
        final float dy = v.y - u.y;
        return (float) Math.sqrt(dx * dx + dy * dy);
    }

    public static boolean inRangeExclusive(final Vector2 v, final Vector2 min, final Vector2 max) {
        final float x = v.x;
        final float y = v.y;
        return min.x < x && x < max.x
            && min.y < y && y < max.y;
    }
}
