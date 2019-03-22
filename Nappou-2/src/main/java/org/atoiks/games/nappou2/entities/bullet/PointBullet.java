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

package org.atoiks.games.nappou2.entities.bullet;

import java.awt.Color;

import org.atoiks.games.framework2d.IGraphics;

import org.atoiks.games.nappou2.entities.IBullet;

import static org.atoiks.games.nappou2.Utils.fastCircleCollision;

public class PointBullet extends AbstractBullet {

    private static final long serialVersionUID = 3928242215L;

    private static final int SCREEN_EDGE_BUFFER = 16;

    protected float x, y, r;

    protected PointBullet(float x, float y, float r) {
        this.x = x;
        this.y = y;
        this.r = r;
    }

    public PointBullet(float x, float y, float r, float dx, float dy) {
        this(x, y, r);
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
        g.setColor(color);
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
    public boolean collidesWith(final float x1, final float y1, final float r1) {
        return fastCircleCollision(x, y, r, x1, y1, r1);
    }

    @Override
    public boolean isOutOfScreen(final int w, final int h) {
        return (x + r < -SCREEN_EDGE_BUFFER)
            || (x - r > w + SCREEN_EDGE_BUFFER)
            || (y + r < -SCREEN_EDGE_BUFFER)
            || (y - r > h + SCREEN_EDGE_BUFFER);
    }
}
