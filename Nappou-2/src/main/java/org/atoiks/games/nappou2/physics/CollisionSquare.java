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

package org.atoiks.games.nappou2.physics;

public final class CollisionSquare {

    float x;
    float y;
    float w;
    float h;

    public CollisionSquare() {
        this(0, 0, 0);
    }

    public CollisionSquare(final CollisionSquare s) {
        this(s.x, s.y, s.w, s.h);
    }

    public CollisionSquare(float x, float y, final float apothem) {
        final float hs = Math.abs(apothem);
        this.x = x - hs;
        this.y = y - hs;
        this.w = 2 * hs;
        this.h = 2 * hs;
    }

    public CollisionSquare(float x, float y, float w, float h) {
        this.x = x;
        this.y = y;
        this.w = Math.abs(w);
        this.h = Math.abs(h);
    }

    public void readFrom(final CollisionSquare s) {
        this.x = s.x;
        this.y = s.y;
        this.w = s.w;
        this.h = s.h;
    }

    public float area() {
        return w * h;
    }

    public boolean contains(final float px, final float py) {
        return x <= px && x + w >= px
            && y <= py && y + h >= py;
    }

    public boolean contains(final CollisionSquare s) {
        final float x11 = x;
        final float y11 = y;
        final float x12 = x + w;
        final float y12 = y + h;

        final float x21 = s.x;
        final float y21 = s.y;
        final float x22 = s.x + s.w;
        final float y22 = s.y + s.h;

        return x11 <= x21 && x12 >= x22
            && y11 <= y21 && y12 >= y22;
    }

    public void union(final CollisionSquare s) {
        final float endX = Math.max(this.x + this.w, s.x + s.w);
        final float endY = Math.max(this.y + this.h, s.y + s.h);

        this.w = endX - (this.x = Math.min(this.x, s.x));
        this.h = endY - (this.y = Math.min(this.y, s.y));
    }

    public boolean collidesWith(final CollisionSquare s) {
        return x < s.x + s.w && x + w > s.x
            && y < s.y + s.h && y + h > s.y;
    }

    @Override
    public String toString() {
        final float x11 = x;
        final float y11 = y;
        final float x12 = x + w;
        final float y12 = y + h;

        return "(" + x11 + "," + y11 + ") to (" + x12 + "," + y12 + ")";
    }
}
