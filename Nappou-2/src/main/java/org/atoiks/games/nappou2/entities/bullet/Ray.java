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

public final class Ray extends BeamLike {

    private final float maxLength;
    private final float growthRate;

    public Ray(final Vector2 position, final float maxLength, final float width, final Vector2 velocity) {
        // Uses velocity as the growth rate
        this(position, maxLength, velocity.getLength(), width, velocity);
    }

    public Ray(final Vector2 position, final float maxLength, final float growthRate, final float width, final Vector2 velocity) {
        super(position, width, velocity);

        this.maxLength = maxLength;
        this.growthRate = growthRate;
    }

    public Ray(final float x, final float y, final float maxLength, final float growthRate, final float width, final float dx, final float dy) {
        this(new Vector2(x, y), maxLength, growthRate, width, new Vector2(dx, dy));
    }

    @Override
    public void update(final float dt) {
        if (this.length < this.maxLength) {
            this.length = Math.min(this.maxLength, this.length + this.growthRate * dt);
            return;
        }

        super.update(dt);
    }
}
