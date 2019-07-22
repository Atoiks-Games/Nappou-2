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

import org.atoiks.games.nappou2.graphics.shapes.Line;

import static org.atoiks.games.nappou2.Utils.isSquareOutOfScreen;
import static org.atoiks.games.nappou2.Utils.centerSquareCollision;
import static org.atoiks.games.nappou2.Utils.intersectSegmentCircle;

/* package */ abstract class BeamLike extends AbstractBullet implements Line {

    private final float halfWidth;

    private final float angle;
    private final Vector2 velocity;

    private Vector2 vector;

    protected float length;

    protected BeamLike(final Vector2 position, final float width, final Vector2 velocity) {
        this.halfWidth = width / 2;

        this.angle = velocity.getTheta();
        this.velocity = velocity;

        this.vector = position;
    }

    @Override
    public void drift(final Vector2 d) {
        this.vector = this.vector.add(d);
    }

    @Override
    public float getHalfWidth() {
        return Math.min(this.length, this.halfWidth);
    }

    @Override
    public float getLength() {
        return this.length;
    }

    @Override
    public float getAngle() {
        return this.angle;
    }

    @Override
    public void update(final float dt) {
        this.drift(this.velocity.mul(dt));
    }

    @Override
    public Vector2 getPosition() {
        return this.vector;
    }

    @Override
    public final boolean collidesWith(final float x1, final float y1, final float r1) {
        // transform the colliding entity to the ray's coordinate system

        final Vector2 pos = this.getPosition();
        final float tx = x1 - pos.getX();
        final float ty = y1 - pos.getY();
        if (centerSquareCollision(0, 0, Math.max(length, halfWidth), tx, ty, r1)) {
            // Apply rotation on to the colliding entity

            final float cos = (float) Math.cos(-angle);
            final float sin = (float) Math.sin(-angle);

            final float rx = cos * tx - sin * ty;
            final float ry = sin * tx + cos * ty;
            final float hw = Math.min(length, halfWidth);
            return intersectSegmentCircle(0, -hw, length, -hw, rx, ry, r1)
                || intersectSegmentCircle(length, -hw, length, hw, rx, ry, r1)
                || intersectSegmentCircle(length, hw, 0, hw, rx, ry, r1)
                || intersectSegmentCircle(0, hw, 0, -hw, rx, ry, r1);
        }
        return false;
    }

    @Override
    public final boolean isOutOfScreen(final int w, final int h) {
        // Extrude the tail of the ray by Math.max(length, this.halfWidth).
        // Now it would be a square. If such square is out of the screen,
        // then the ray must be as well.

        return isSquareOutOfScreen(this.getPosition(), Math.max(this.length, this.halfWidth), w, h);
    }
}
