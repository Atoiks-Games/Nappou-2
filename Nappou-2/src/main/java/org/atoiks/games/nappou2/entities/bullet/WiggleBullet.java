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

import static org.atoiks.games.nappou2.Utils.fastCircleCollision;

public class WiggleBullet extends PointBullet {

    private static final long serialVersionUID = 3928242215L;

    protected float m, s, time;
    protected boolean d;

    public WiggleBullet(float x, float y, float r, float dx, float dy, boolean d, float m, float s) {
        super(x, y, r, dx, dy);

        this.d = d;
        this.m = m;
        this.s = s;
        this.time = 0;
    }

    @Override
    public void update(final float dt) {
        this.time += dt;

        if (this.d) {
            this.x += dt * (this.dx + this.m * Math.cos(this.s * this.time));
            this.y += this.dy * dt;
        } else {
            this.x += this.dx * dt;
            this.y += dt * (this.dy + this.m * Math.cos(this.s * this.time));
        }
    }
}
