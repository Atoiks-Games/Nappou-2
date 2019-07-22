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

package org.atoiks.games.nappou2.scenes;

import org.atoiks.games.framework2d.Scene;
import org.atoiks.games.framework2d.IGraphics;

public abstract class CenteringScene implements Scene {

    private float scaleFactor;
    private float transX;
    private float transY;

    @Override
    public void render(IGraphics g) {
        g.translate(transX, transY);
        g.scale(scaleFactor, scaleFactor);
    }

    protected final void drawSideBlinder(IGraphics g) {
        g.fillRect(-transX, 0, 0, 600);
        g.fillRect(900, 0, 900 + transX, 600);
    }

    @Override
    public final void resize(final int x, final int y) {
        // original size is 900 by 600
        // we only scale the screen (and leave borders on the side)
        final float scaleRatioX = x / 900.0f;
        final float scaleRatioY = y / 600.0f;

        // Determine the correct scale factor
        final float scaleBy = Math.min(scaleRatioX, scaleRatioY);
        this.scaleFactor = scaleBy;

        // Add appropriate padding
        this.transX = (x - 900.0f * scaleBy) / 2;
        this.transY = (y - 600.0f * scaleBy) / 2;
    }
}
