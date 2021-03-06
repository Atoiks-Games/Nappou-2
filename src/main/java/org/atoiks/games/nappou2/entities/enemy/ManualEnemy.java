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

package org.atoiks.games.nappou2.entities.enemy;

import org.atoiks.games.nappou2.Vector2;

public abstract class ManualEnemy extends AbstractEnemy {

    protected float x, y, r;

    protected ManualEnemy(int hp, float x, float y, float r) {
        super(hp);
        this.x = x;
        this.y = y;
        this.r = r;
    }

    @Override
    public void drift(Vector2 d) {
        this.x += d.getX();
        this.y += d.getY();
    }

    public final float getX() {
        return this.x;
    }

    public final float getY() {
        return this.y;
    }

    @Override
    public final Vector2 getPosition() {
        return new Vector2(x, y);
    }

    @Override
    public final float getRadius() {
        return this.r;
    }

    public final void setX(float x) {
        this.x = x;
    }

    public final void setY(float y) {
        this.y = y;
    }

    public final void setRadius(float r) {
        this.r = r;
    }
}
