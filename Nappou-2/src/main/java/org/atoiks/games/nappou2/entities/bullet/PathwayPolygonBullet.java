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

import java.awt.Image;

import org.atoiks.games.framework2d.IGraphics;

import org.atoiks.games.nappou2.Vector2;

import org.atoiks.games.nappou2.pathway.IPathway;

import org.atoiks.games.nappou2.graphics.shapes.Polygonal;

import static org.atoiks.games.nappou2.Utils.isSquareOutOfScreen;
import static org.atoiks.games.nappou2.Utils.centerSquareCollision;
import static org.atoiks.games.nappou2.Utils.intersectSegmentCircle;

public class PathwayPolygonBullet<T extends IPathway> extends PathwayBullet<T> implements Polygonal {

    private final float[] coords;
    private final float boundX;
    private final float boundY;
    private final float boundR;

    public PathwayPolygonBullet(final float[] pts, final T pathway) {
        super(pathway);
        this.coords = pts;

        // shape bounding box
        float x1 = Float.POSITIVE_INFINITY;
        float y1 = Float.POSITIVE_INFINITY;
        float x2 = Float.NEGATIVE_INFINITY;
        float y2 = Float.NEGATIVE_INFINITY;

        final int limit = pts.length;
        for (int i = 0; i < limit; i += 2) {
            final float x = pts[i];
            final float y = pts[i + 1];

            if (x < x1) x1 = x;
            if (x > x2) x2 = x;
            if (y < y1) y1 = y;
            if (y > y2) y2 = y;
        }

        this.boundX = (x1 + x2) / 2;
        this.boundY = (y1 + y2) / 2;
        this.boundR = Math.max(x2 - x1, y2 - y1);
    }

    @Override
    public float[] getPoints() {
        return this.coords;
    }

    @Override
    public void renderTexture(IGraphics g, Image img) {
        g.drawImage(img, 0, 0, this.boundX, this.boundY);
    }

    @Override
    public boolean collidesWith(final float x1, final float y1, final float r1) {
        // Instead of shifting the polygon to the screen,
        // we shift the colliding entity.
        // (polygon coordinates are fixed at origin)
        //
        // Colliding entity Item was (x1, y1)
        // Convert to (x1 - x, y1 - y)
        //   where x, y are the polygon's onscreen position

        final Vector2 pos = this.getPosition();
        final float tx = x1 - pos.getX();
        final float ty = y1 - pos.getY();

        if (!centerSquareCollision(boundX, boundY, boundR, tx, ty, r1)) {
            return false;
        }

        final int length = coords.length;
        for (int i = 0; i < length - 2; i += 2) {
            final float startX = coords[i];
            final float startY = coords[i + 1];
            final float endX   = coords[i + 2];
            final float endY   = coords[i + 3];
            if (intersectSegmentCircle(startX, startY, endX, endY, tx, ty, r1)) {
                return true;
            }
        }

        return intersectSegmentCircle(coords[length - 2], coords[length - 1], coords[0], coords[1],
                tx, ty, r1);
    }

    @Override
    public final boolean isOutOfScreen(final int w, final int h) {
        // If the bounding box is completely out of the screen,
        // then the polygon must be as well.

        return isSquareOutOfScreen(this.getTransformedBoundPosition(), this.boundR, w, h);
    }

    private Vector2 getTransformedBoundPosition() {
        return new Vector2(boundX, boundY).add(this.getPosition());
    }
}
