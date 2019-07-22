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

import org.atoiks.games.nappou2.Vector2;

import org.atoiks.games.framework2d.IGraphics;

public interface Polygonal extends Shape {

    public float[] getPoints();

    @Override
    public default Rectangular getMinimumBoundingBox() {
        final float[] pts = this.getPoints();

        float minX = Float.POSITIVE_INFINITY;
        float minY = Float.POSITIVE_INFINITY;
        float maxX = Float.NEGATIVE_INFINITY;
        float maxY = Float.NEGATIVE_INFINITY;

        final int limit = pts.length;
        for (int i = 0; i < limit; i += 2) {
            final float x = pts[i];
            final float y = pts[i + 1];

            if (x < minX) minX = x;
            if (x > maxX) maxX = x;
            if (y < minY) minY = y;
            if (y > maxY) maxY = y;
        }

        final Vector2 pos = this.getPosition();
        final Vector2 min = new Vector2(minX, minY);
        final Vector2 max = new Vector2(maxX, maxY);
        return ImmutableRectangle.formedBetween(pos.add(min), pos.add(max));
    }

    @Override
    public default void draw(final IGraphics g) {
        final Vector2 pos = getPosition();
        final float x = pos.getX();
        final float y = pos.getY();

        g.translate(x, y);
        g.drawPolygon(getPoints());
        g.translate(-x, -y);
    }

    @Override
    public default void fill(final IGraphics g) {
        final Vector2 pos = getPosition();
        final float x = pos.getX();
        final float y = pos.getY();

        g.translate(x, y);
        g.fillPolygon(getPoints());
        g.translate(-x, -y);
    }
}
