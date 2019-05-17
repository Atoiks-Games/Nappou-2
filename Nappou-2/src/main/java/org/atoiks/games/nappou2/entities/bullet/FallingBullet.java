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

public class FallingBullet extends PointBullet {

    private static final long serialVersionUID = 3928242215L;

    private float direction;
    private float speed;
    private int score;
    private float time;
    private float accel;
    private boolean xdir;
    private float dx;
    private float dy;

    public FallingBullet(float x, float y, float r, float speed, float direction, float accel, boolean xdir) {
        super(x, y, r, speed * (float) Math.cos(direction), speed * (float) Math.sin(direction) );
        this.speed = speed;
        this.direction = direction;
        this.time = 0;
        this.accel = accel;
        this.xdir = xdir;
        this.dx = speed * (float) Math.cos(direction);
        this.dy = speed * (float) Math.sin(direction);
    }

    @Override
    public void update(final float dt) {

        if (xdir) {
            this.dx += dt * this.accel;
        } else {
            this.dy += dt * this.accel;
        }

        this.x += dt * this.dx;
        this.y += dt * this.dy;
    }
}
