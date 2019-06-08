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

import org.atoiks.games.nappou2.Vector2;

import org.atoiks.games.nappou2.sizer.ISizer;
import org.atoiks.games.nappou2.sizer.FixedSizer;

import org.atoiks.games.nappou2.pathway.IPathway;

import static org.atoiks.games.nappou2.Utils.fastCircleCollision;

public class PathwayPointBullet extends PathwayBullet {

    private static final long serialVersionUID = -3970324591546167543L;

    private static final int SCREEN_EDGE_BUFFER = 16;

    private final ISizer sizer;
    private float r;

    public PathwayPointBullet(float r, ISizer sizer, IPathway pathway) {
        super(pathway);
        this.sizer = sizer;
        this.r = r;
    }

    public PathwayPointBullet(float r, IPathway pathway) {
        this(r, FixedSizer.INSTANCE, pathway);
    }

    @Override
    public void render(final IGraphics g) {
        // Can change this to using textures later
        g.setColor(color);

        final Vector2 pos = this.getPosition();
        final float x = pos.getX();
        final float y = pos.getY();
        // x, y are the center of the bullet
        g.fillOval(x - r, y - r, x + r, y + r);
    }

    @Override
    public void update(final float dt) {
        super.update(dt);

        this.r = this.sizer.getNextSize(r, dt);
    }

    @Override
    public boolean collidesWith(final float x1, final float y1, final float r1) {
        final Vector2 pos = this.getPosition();
        final float x = pos.getX();
        final float y = pos.getY();
        return fastCircleCollision(x, y, r, x1, y1, r1);
    }

    @Override
    public boolean isOutOfScreen(final int w, final int h) {
        final Vector2 pos = this.getPosition();
        final float x = pos.getX();
        final float y = pos.getY();
        return (x + r < -SCREEN_EDGE_BUFFER)
            || (x - r > w + SCREEN_EDGE_BUFFER)
            || (y + r < -SCREEN_EDGE_BUFFER)
            || (y - r > h + SCREEN_EDGE_BUFFER);
    }
}
