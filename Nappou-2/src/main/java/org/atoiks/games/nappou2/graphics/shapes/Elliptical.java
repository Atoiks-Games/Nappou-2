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

public interface Elliptical extends Shape {

    public float getWidth();
    public float getHeight();

    @Override
    public default Rectangular getBoundingBox() {
        return new ImmutRectImpl(getWidth(), getHeight());
    }

    @Override
    public default Vector2 getCenterPoint() {
        return new Vector2(getWidth() / 2, getHeight() / 2);
    }

    @Override
    public default void draw(final IGraphics g) {
        g.drawOval(0, 0, getWidth(), getHeight());
    }

    @Override
    public default void fill(final IGraphics g) {
        g.fillOval(0, 0, getWidth(), getHeight());
    }

    @Override
    public default void renderTexture(IGraphics g, Image img) {
        g.drawImage(img, 0, 0, getWidth(), getHeight());
    }
}
