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

import org.atoiks.games.nappou2.graphics.shapes.Shape;

public final class ColorRenderer implements Renderer {

    private final Color fill;
    private final Color draw;

    public ColorRenderer(Color fill, Color draw) {
        this.fill = fill;
        this.draw = draw;
    }

    public void render(IGraphics g, Shape shape) {
        g.setColor(fill);
        shape.fill(g);
        g.setColor(draw);
        shape.draw(g);
    }
}
