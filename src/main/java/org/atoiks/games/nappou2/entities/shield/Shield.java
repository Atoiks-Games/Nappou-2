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

package org.atoiks.games.nappou2.entities.shield;

import org.atoiks.games.nappou2.entities.Collidable;

import org.atoiks.games.framework2d.IGraphics;

import org.atoiks.games.framework2d.resource.Font;

public interface Shield {

    public void activate();
    public void deactivate();
    public boolean isActive();
    public boolean isReady();

    public boolean collidesWith(Collidable col);

    public default void drawStatus(IGraphics g, Font font) {
        if (this.isReady()) {
            font.renderText(g, "Shield Ready", 30, 0);
        }
    }
}
