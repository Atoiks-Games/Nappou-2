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

import org.atoiks.games.nappou2.sizer.ISizer;

public final class DynamicBeam extends BeamLike {

    private final ISizer sizer;

    public DynamicBeam(final Vector2 position, final float width, final ISizer lengthSizer, final Vector2 velocity) {
        super(position, width, velocity);

        this.sizer = lengthSizer;
    }

    public DynamicBeam(final float x, final float y, final float width, final ISizer lengthSizer, final float dx, final float dy) {
        this(new Vector2(x, y), width, lengthSizer, new Vector2(dx, dy));
    }

    @Override
    public final void update(final float dt) {
        this.length = this.sizer.getNextSize(this.length, dt);
        super.update(dt);
    }
}
