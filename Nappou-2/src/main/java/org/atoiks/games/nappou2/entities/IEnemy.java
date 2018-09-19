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

package org.atoiks.games.nappou2.entities;

import java.io.Serializable;

import java.awt.Color;

import org.atoiks.games.framework2d.IGraphics;

import org.atoiks.games.framework2d.IRender;
import org.atoiks.games.framework2d.IUpdate;

public abstract class IEnemy implements ICollidable, IRender, IUpdate, Serializable {

    private static final long serialVersionUID = 8123472652L;

    protected static final int SCREEN_EDGE_BUFFER = 16;

    protected int hp;

    protected IEnemy(int hp) {
        this.hp = hp;
    }

    public boolean isDead() {
        return hp <= 0;
    }

    public int changeHp(int delta) {
        return this.hp += delta;
    }

    @Override
    public void update(float dt) {
        // Default do nothing
    }

    @Override
    public void render(IGraphics g) {
        g.setColor(Color.white);
        // x, y are the center of the enemy
        final float x = getX();
        final float y = getY();
        final float r = getR();
        g.drawOval(x - r, y - r, x + r, y + r);
    }

    @Override
    public boolean collidesWith(float x1, float y1, float r1) {
        return Math.hypot(x1 - getX(), y1 - getY()) < getR() + r1;
    }

    @Override
    public boolean isOutOfScreen(final int w, final int h) {
        final float x = getX();
        final float y = getY();
        final float r = getR();
        return (x + r < -SCREEN_EDGE_BUFFER)
            || (x - r > w + SCREEN_EDGE_BUFFER)
            || (y + r < -SCREEN_EDGE_BUFFER)
            || (y - r > h + SCREEN_EDGE_BUFFER);
    }

    public abstract float getX();
    public abstract float getY();
    public abstract float getR();

    public abstract void attachGame(Game game);

    public abstract int getScore();
}