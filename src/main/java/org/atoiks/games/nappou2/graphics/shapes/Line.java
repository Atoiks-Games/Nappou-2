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

public interface Line extends Shape {

    public float getHalfWidth();
    public float getLength();
    public float getAngle();

    @Override
    public default void draw(final IGraphics g) {
        final float angle = getAngle();
        final float hw = getHalfWidth();
        final float length = getLength();
        final Vector2 pos = getPosition();
        final float x = pos.getX();
        final float y = pos.getY();

        g.translate(x, y);
        g.rotate(angle, 0, 0);
        g.drawRect(0, -hw, length, hw);
        g.rotate(-angle, 0, 0);
        g.translate(-x, -y);
    }

    @Override
    public default Rectangular getBoundingBox() {
        final float max = Math.max(this.getLength(), this.getHalfWidth());
        final Vector2 shift = new Vector2(max, max);
        final Vector2 pos = this.getPosition();
        return ImmutableRectangle.formedBetween(pos.sub(shift), pos.add(shift));
    }

    @Override
    public default Rectangular getMinimumBoundingBox() {
        // Idea is to draw a rectangle with its diagonal corresponding to the
        // length and direction of the line.
        // That does part of the job because it does not account for the half-
        // width.
        // This computes two rectangles: one from each half-width extension
        // then finds the minimum bounding box of these two rectangles.

        final Vector2 pos = this.getPosition();
        final float angle = this.getAngle();
        final Vector2 v1 = Vector2.fromPolar(this.getLength(), angle);
        final Vector2 v2 = Vector2.fromPolar(this.getHalfWidth(), angle + (float) (Math.PI / 2));

        final Vector2 p1 = pos.add(v2);
        final Vector2 p2 = pos.sub(v2);
        return Rectangular.of(
                ImmutableRectangle.formedBetween(p1, p1.add(v1)),
                ImmutableRectangle.formedBetween(p2, p2.add(v1)));
    }

    @Override
    public default void fill(final IGraphics g) {
        final float angle = getAngle();
        final float hw = getHalfWidth();
        final float length = getLength();
        final Vector2 pos = getPosition();
        final float x = pos.getX();
        final float y = pos.getY();

        g.translate(x, y);
        g.rotate(angle, 0, 0);
        g.fillRect(0, -hw, length, hw);
        g.rotate(-angle, 0, 0);
        g.translate(-x, -y);
    }

    @Override
    public default void renderTexture(IGraphics g, Texture img) {
        final float angle = getAngle();
        final float hw = getHalfWidth();
        final float length = getLength();
        final Vector2 pos = getPosition();
        final float x = pos.getX();
        final float y = pos.getY();

        g.translate(x, y);
        g.rotate(angle, 0, 0);
        g.drawTexture(img, 0, -hw, length, hw);
        g.rotate(-angle, 0, 0);
        g.translate(-x, -y);
    }
}
