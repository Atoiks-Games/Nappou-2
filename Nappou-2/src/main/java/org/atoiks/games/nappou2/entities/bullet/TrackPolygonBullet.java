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

import org.atoiks.games.nappou2.entities.Game;
import org.atoiks.games.nappou2.entities.IBullet;

import static org.atoiks.games.nappou2.Utils.intersectSegmentCircle;

public final class TrackPolygonBullet implements IBullet {

    private static final long serialVersionUID = 2983462354L;

    private static final int SCREEN_EDGE_BUFFER = 16;

    private final Game game;
    private final float scale;
    private final float moveTime;
    private final float delay;

    // Stored as x1, y1, ..., xn, yn pairs
    private final float[] coords;
    private float dx, dy;

    private float time;
    private boolean moving;

    public TrackPolygonBullet(float[] coords, Game game, float pathScale, float moveTime, float delay) {
        this.coords = coords;
        this.game = game;
        this.scale = pathScale;
        this.moveTime = moveTime;
        this.delay = delay;
    }

    @Override
    public void translate(float dx, float dy) {
        for (int i = 0; i < coords.length; i += 2) {
            coords[i + 0] += dx;
            coords[i + 1] += dy;
        }
    }

    @Override
    public void render(final IGraphics g) {
        g.setColor(Color.white);
        g.drawPolygon(coords);
    }

    @Override
    public void update(final float dt) {
        if (game.player == null) return;

        time += dt;
        if (moving) {
            if (time >= moveTime) {
                moving = false;
                time = 0;
            } else {
                translate(dx * dt, dy * dt);
            }
        } else if (time >= delay) {
            // Re-calculate endpoints
            dx = scale * (game.player.getX() - coords[0]) / moveTime;
            dy = scale * (game.player.getY() - coords[1]) / moveTime;
            moving = true;
            time = 0;
        }
    }

    @Override
    public float getX() {
        return this.coords[0];
    }

    @Override
    public float getY() {
        return this.coords[1];
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
        for (int i = 0; i < coords.length; i += 2) {
            final float startX = coords[i];
            final float startY = coords[i + 1];
            final float endX   = coords[(i + 2) % coords.length];
            final float endY   = coords[(i + 3) % coords.length];
            if (intersectSegmentCircle(startX, startY, endX, endY, x1, y1, r1)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isOutOfScreen(final int w, final int h) {
        for (int i = 0; i < coords.length; i += 2) {
            if (pointIsOutOfScreen(coords[i], coords[i + 1])) {
                return true;
            }
        }
        return false;
    }

    private static boolean pointIsOutOfScreen(float x, float y) {
        return (x < -SCREEN_EDGE_BUFFER)
            || (x > SCREEN_EDGE_BUFFER)
            || (y < -SCREEN_EDGE_BUFFER)
            || (y > SCREEN_EDGE_BUFFER);
    }
}