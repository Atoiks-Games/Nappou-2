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

import java.awt.Image;

import org.atoiks.games.framework2d.IGraphics;

import org.atoiks.games.nappou2.entities.IEnemy;

public final class ImageEnemyRenderer implements IEnemyRenderer {

    private final Image image;

    public ImageEnemyRenderer(final Image image) {
        this.image = image;
    }

    public void render(IGraphics g, IEnemy obj) {
        // x, y are the center of the enemy
        final float x = obj.getX();
        final float y = obj.getY();
        final float r = obj.getR();
        // Draw the image over the square occupied by the enemy
        g.drawImage(image, x - r, y - r, x + r, y + r);
    }
}
