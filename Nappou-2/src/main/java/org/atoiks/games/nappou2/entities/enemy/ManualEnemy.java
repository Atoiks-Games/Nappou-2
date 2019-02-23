/**
 *  Nappou-2
 *  Copyright (C) 2017-2018  Atoiks-Games <atoiks-games@outlook.com>
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

package org.atoiks.games.nappou2.entities.enemy;

import org.atoiks.games.nappou2.entities.Game;
import org.atoiks.games.nappou2.entities.IEnemy;

public abstract class ManualEnemy extends AbstractEnemy {

    private static final long serialVersionUID = 7192746L;

    protected float x, y, r;

    protected ManualEnemy(int hp, float x, float y, float r) {
        super(hp);
        this.x = x;
        this.y = y;
        this.r = r;
    }

    @Override
    public void drift(float dx, float dy) {
        this.x += dx;
        this.y += dy;
    }

    @Override
    public final float getX() {
        return this.x;
    }

    @Override
    public final float getY() {
        return this.y;
    }

    @Override
    public final float getR() {
        return this.r;
    }

    public final void setX(float x) {
        this.x = x;
    }

    public final void setY(float y) {
        this.y = y;
    }

    public final void setR(float r) {
        this.r = r;
    }
}
