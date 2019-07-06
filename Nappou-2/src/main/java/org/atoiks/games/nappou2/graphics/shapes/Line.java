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

public interface Line extends Shape {

    public float getHalfWidth();
    public float getLength();
    public float getAngle();

    @Override
    public default void draw(final IGraphics g) {
        final float angle = getAngle();
        final float hw = getHalfWidth();
        final float length = getLength();

        g.rotate(angle, 0, 0);
        g.drawRect(0, -hw, length, hw);
        g.rotate(-angle, 0, 0);
    }

    @Override
    public default void fill(final IGraphics g) {
        final float angle = getAngle();
        final float hw = getHalfWidth();
        final float length = getLength();

        g.rotate(angle, 0, 0);
        g.fillRect(0, -hw, length, hw);
        g.rotate(-angle, 0, 0);
    }

    @Override
    public default void renderTexture(IGraphics g, Image img) {
        final float angle = getAngle();
        final float hw = getHalfWidth();
        final float length = getLength();

        g.rotate(angle, 0, 0);
        g.drawImage(img, 0, -hw, length, hw);
        g.rotate(-angle, 0, 0);
    }
}
