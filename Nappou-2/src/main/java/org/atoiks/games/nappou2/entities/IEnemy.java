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

package org.atoiks.games.nappou2.entities;

import java.io.Serializable;

import java.awt.Color;

import org.atoiks.games.framework2d.IGraphics;

import org.atoiks.games.framework2d.IRender;
import org.atoiks.games.framework2d.IUpdate;

import org.atoiks.games.nappou2.graphics.IEnemyRenderer;
import org.atoiks.games.nappou2.graphics.ColorEnemyRenderer;

import static org.atoiks.games.nappou2.Utils.centerSquareCollision;

public abstract class IEnemy implements ICollidable, IRender, IUpdate, Serializable {

    private static final long serialVersionUID = 8123472652L;

    protected static final int SCREEN_EDGE_BUFFER = 16;

    protected int hp;

    public IEnemyRenderer compRenderer = ColorEnemyRenderer.DEFAULT;

    protected IEnemy(int hp) {
        this.hp = hp;
    }

    public boolean isDead() {
        return hp <= 0;
    }

    public int changeHp(int delta) {
        return this.hp += delta;
    }

    public void drift(float dx, float dy) {
        // Default do nothing
    }

    @Override
    public void update(float dt) {
        // Default do nothing
    }

    @Override
    public final void render(IGraphics g) {
        compRenderer.render(g, this);
    }

    @Override
    public boolean collidesWith(final float x1, final float y1, final float r1) {
        final float x = getX();
        final float y = getY();
        final float r = getR();
        // Only perform accurate collision if two both circles collide as
        // squares.
        if (centerSquareCollision(x, y, r, x1, y1, r1)) {
            // Accurate collision checks distance between the circles.
            final float dx = x1 - x;
            final float dy = y1 - y;
            final float dr = r1 + r;
            return dx * dx + dy * dy < dr * dr;
        }
        return false;
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
