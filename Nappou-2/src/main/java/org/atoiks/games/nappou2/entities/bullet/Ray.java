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

import org.atoiks.games.framework2d.IGraphics;

import org.atoiks.games.nappou2.Vector2;

import static org.atoiks.games.nappou2.Utils.isPtOutOfScreen;
import static org.atoiks.games.nappou2.Utils.centerSquareCollision;
import static org.atoiks.games.nappou2.Utils.intersectSegmentCircle;

public final class Ray extends AbstractBullet {

    private static final long serialVersionUID = 610071490807998656L;

    private final float halfWidth;

    private final float maxLength;
    private final float growthRate;

    private final float angle;

    private float length;

    private Vector2 vector;

    public Ray(final Vector2 position, final float maxLength, final float width, final Vector2 velocity) {
        // Uses velocity as the growth rate
        this(position, maxLength, velocity.getLength(), width, velocity);
    }

    public Ray(final Vector2 position, final float maxLength, final float growthRate, final float width, final Vector2 velocity) {
        this.halfWidth = width / 2;

        this.maxLength = maxLength;
        this.growthRate = growthRate;

        this.vector = position;

        this.dx = velocity.getX();
        this.dy = velocity.getY();
        this.angle = velocity.getTheta();
    }

    public Ray(final float x, final float y, final float maxLength, final float growthRate, final float width, final float dx, final float dy) {
        this(new Vector2(x, y), maxLength, growthRate, width, new Vector2(dx, dy));
    }

    @Override
    public void drift(final Vector2 d) {
        this.vector = this.vector.add(d);
    }

    @Override
    public void render(final IGraphics g) {
        // Instead of shifting the ray to the screen,
        // we translate the rendering matrix.

        if (length == 0) {
            return;
        }

        final Vector2 pos = this.getPosition();
        final float tx = pos.getX();
        final float ty = pos.getY();
        final float hw = Math.min(length, this.halfWidth);

        // x, y pairs
        final float[] coords = {
            0,      -hw,
            length, -hw,
            length,  hw,
            0,       hw,
        };

        g.translate(tx, ty);
        g.rotate(angle, 0, 0);

        g.setColor(this.color);
        g.fillPolygon(coords);

        g.rotate(-angle, 0, 0);
        g.translate(-tx, -ty);
    }

    @Override
    public void update(final float dt) {
        if (this.length < this.maxLength) {
            this.length = Math.min(this.maxLength, this.length + this.growthRate * dt);
            return;
        }

        this.drift(new Vector2(this.dx, this.dy).mul(dt));
    }

    @Override
    public Vector2 getPosition() {
        return this.vector;
    }

    @Override
    public float getX() {
        return this.getPosition().getX();
    }

    @Override
    public float getY() {
        return this.getPosition().getY();
    }

    @Override
    public boolean collidesWith(final float x1, final float y1, final float r1) {
        // transform the colliding entity to the ray's coordinate system

        final Vector2 pos = this.getPosition();
        final float tx = x1 - pos.getX();
        final float ty = y1 - pos.getY();
        if (centerSquareCollision(0, 0, Math.max(halfWidth, maxLength), tx, ty, r1)) {
            // Apply rotation on to the colliding entity

            final float cos = (float) Math.cos(angle);
            final float sin = (float) Math.sin(angle);

            final float rx = cos * tx - sin * ty;
            final float ry = sin * tx + cos * ty;
            final float hw = Math.min(length, this.halfWidth);
            return intersectSegmentCircle(0, -hw, length, -hw, rx, ry, r1)
                || intersectSegmentCircle(length, -hw, length, hw, rx, ry, r1)
                || intersectSegmentCircle(length, hw, 0, hw, rx, ry, r1)
                || intersectSegmentCircle(0, hw, 0, -hw, rx, ry, r1);
        }
        return false;
    }

    @Override
    public boolean isOutOfScreen(final int w, final int h) {
        // Extrude the tail of the ray by Math.min(length, this.halfWidth).
        // Now it would be a square. If such square is out of the screen,
        // then the ray must be as well.

        final Vector2 pos = this.getPosition();
        final float x = pos.getX();
        final float y = pos.getY();
        final float hw = Math.min(length, this.halfWidth);

        final float maxX = x + hw;
        final float minX = x - hw;
        final float maxY = y + hw;
        final float minY = y - hw;

        return isPtOutOfScreen(maxX, maxY, w, h)
            || isPtOutOfScreen(maxX, minY, w, h)
            || isPtOutOfScreen(minX, minY, w, h)
            || isPtOutOfScreen(minX, maxY, w, h);
    }
}
