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

package org.atoiks.games.nappou2.graphics;

import java.awt.Color;

import org.atoiks.games.framework2d.IGraphics;

import org.atoiks.games.nappou2.Vector2;

import org.atoiks.games.nappou2.graphics.shapes.Shape;

public final class OutlineRenderer implements Renderer {

    public static final OutlineRenderer DEFAULT = new OutlineRenderer(Color.white);

    private final Color color;

    public OutlineRenderer(Color color) {
        this.color = color;
    }

    public void render(IGraphics g, Shape shape, Vector2 pos) {
        final float x = pos.getX();
        final float y = pos.getY();
        g.translate(x, y);

        g.setColor(color);
        shape.draw(g);

        g.translate(-x, -y);
    }
}
