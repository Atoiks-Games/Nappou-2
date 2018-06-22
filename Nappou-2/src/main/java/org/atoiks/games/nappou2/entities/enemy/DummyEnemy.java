/**
 *  Nappou-2
 *  Copyright (C) 2017-2018  Atoiks-Games <atoiks-games@outlook.com>

 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.

 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.

 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package org.atoiks.games.nappou2.entities.enemy;

import org.atoiks.games.framework2d.IGraphics;

public final class DummyEnemy extends AbstractEnemy {

    private static final long serialVersionUID = 56192645221L;
    //This needs to be different for every enemy...

    private boolean right;

    public DummyEnemy(int hp, float x, float y, float r, boolean right) {
        super(hp, x, y, r);
        this.right = right;
    }

    @Override
    public void update(float dt) {
        if (right && getX() > 700) {
            right = false;
        }
        if (!right && getX() < 50) {
            right = true;
        }

        setX(getX() + (right ? 1 : -1) * 100 * dt);
    }

    @Override
    public void render(IGraphics g) {
        // Convert to drawImage later on?
        super.render(g);
    }

    @Override
    public int getScore() {
        return 0;
    }
}
