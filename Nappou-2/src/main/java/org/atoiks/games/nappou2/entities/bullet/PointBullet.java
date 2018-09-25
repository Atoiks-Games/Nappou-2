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

package org.atoiks.games.nappou2.entities.bullet;

import java.awt.Color;

import org.atoiks.games.framework2d.IGraphics;

import org.atoiks.games.nappou2.entities.IBullet;

public final class PointBullet implements IBullet {

    private static final long serialVersionUID = 3928242215L;

    private static final int SCREEN_EDGE_BUFFER = 16;

    private float x, y, r, dx, dy;

    public PointBullet(float x, float y, float r, float dx, float dy) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.dx = dx;
        this.dy = dy;
    }

    @Override
    public void translate(float dx, float dy) {
        this.x += dx;
        this.y += dy;
    }

    @Override
    public void render(final IGraphics g) {
        // Can change this to using textures later
        g.setColor(Color.white);
        // x, y are the center of the bullet
        g.drawOval(x - r, y - r, x + r, y + r);
    }

    @Override
    public void update(final float dt) {
        this.x += this.dx * dt;
        this.y += this.dy * dt;
    }

    @Override
    public float getX() {
        return this.x;
    }

    @Override
    public float getY() {
        return this.y;
    }

    @Override
    public float getDx() {
        return this.dx;
    }

    @Override
    public float getDy() {
        return this.dy;
    }

    @Override
    public boolean collidesWith(float x1, float y1, float r1) {
        final float dx = x1 - x;
        final float dy = y1 - y;
        final float dr = r1 + r;
        return dx * dx + dy * dy < dr * dr;
    }

    @Override
    public boolean isOutOfScreen(final int w, final int h) {
        return (x + r < -SCREEN_EDGE_BUFFER)
            || (x - r > w + SCREEN_EDGE_BUFFER)
            || (y + r < -SCREEN_EDGE_BUFFER)
            || (y - r > h + SCREEN_EDGE_BUFFER);
    }
}
