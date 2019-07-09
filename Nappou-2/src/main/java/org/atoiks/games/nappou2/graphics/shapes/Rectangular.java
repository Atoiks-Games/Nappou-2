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

import java.awt.Image;

import org.atoiks.games.nappou2.Vector2;

import org.atoiks.games.framework2d.IGraphics;

public interface Rectangular extends Shape {

    public float getWidth();
    public float getHeight();

    @Override
    public default Rectangular getMinimumBoundingBox() {
        return this;
    }

    @Override
    public default Rectangular getBoundingBox() {
        return this;
    }

    @Override
    public default void draw(final IGraphics g) {
        final Vector2 pos = getPosition();
        final float x = pos.getX();
        final float y = pos.getY();

        g.drawRect(x, y, x + getWidth(), y + getHeight());
    }

    @Override
    public default void fill(final IGraphics g) {
        final Vector2 pos = getPosition();
        final float x = pos.getX();
        final float y = pos.getY();

        g.fillRect(x, y, x + getWidth(), y + getHeight());
    }

    @Override
    public default void renderTexture(IGraphics g, Image img) {
        final Vector2 pos = getPosition();
        final float x = pos.getX();
        final float y = pos.getY();

        g.drawImage(img, x, y, x + getWidth(), y + getHeight());
    }

    public static Rectangular of(Rectangular r1, Rectangular r2) {
        final Vector2 start1 = r1.getPosition();
        final Vector2 end1 = start1.add(new Vector2(r1.getWidth(), r1.getHeight()));
        final Vector2 start2 = r2.getPosition();
        final Vector2 end2 = start2.add(new Vector2(r2.getWidth(), r2.getHeight()));

        return ImmutableRectangle.formedBetween(
                Vector2.min(start1, start2),
                Vector2.max(end1, end2));
    }
}
