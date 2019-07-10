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

package org.atoiks.games.nappou2.graphics.shapes;

import org.atoiks.games.framework2d.IGraphics;

import org.atoiks.games.framework2d.resource.Texture;

import org.atoiks.games.nappou2.Vector2;

public interface Circular extends Shape {

    public float getRadius();

    @Override
    public default Rectangular getMinimumBoundingBox() {
        final float r = getRadius();
        final Vector2 shift = new Vector2(r, r);
        final Vector2 pos = this.getPosition();
        return ImmutableRectangle.formedBetween(pos.sub(shift), pos.add(shift));
    }

    @Override
    public default void draw(final IGraphics g) {
        final float r = getRadius();
        final Vector2 pos = getPosition();
        final float x = pos.getX();
        final float y = pos.getY();

        g.drawOval(x - r, y - r, x + r, y + r);
    }

    @Override
    public default void fill(final IGraphics g) {
        final float r = getRadius();
        final Vector2 pos = getPosition();
        final float x = pos.getX();
        final float y = pos.getY();

        g.fillOval(x - r, y - r, x + r, y + r);
    }

    @Override
    public default void renderTexture(IGraphics g, Texture img) {
        final float r = getRadius();
        final Vector2 pos = getPosition();
        final float x = pos.getX();
        final float y = pos.getY();

        g.drawTexture(img, x - r, y - r, x + r, y + r);
    }

    public static Elliptical asElliptical(final Circular circ) {
        return new ApproxElliptical(circ);
    }

    public static boolean overlaps(final Circular a, final Circular b) {
        if (overlapsAsSquare(a, b)) {
            final float dist = a.getRadius() + b.getRadius();
            final Vector2 d = a.getPosition().sub(b.getPosition());
            return d.dot(d) < dist * dist;
        }
        return false;
    }

    public static boolean overlapsAsSquare(final Circular a, final Circular b) {
        // +------+
        // |      | x, y = the center
        // |      | h    = side length / 2 = apothem
        // +------+
        //
        // squares do not rotate just check distance between

        final float dist = a.getRadius() + b.getRadius();
        final Vector2 d = Vector2.abs(a.getPosition().sub(b.getPosition()));
        return d.getX() < dist && d.getY() < dist;
    }
}

final class ApproxElliptical implements Elliptical {

    private final Circular circ;

    public ApproxElliptical(Circular circ) {
        this.circ = circ;
    }

    @Override
    public Vector2 getPosition() {
        return circ.getPosition();
    }

    @Override
    public Vector2 getSemiAxes() {
        final float r = circ.getRadius();
        return new Vector2(r, r);
    }
}
