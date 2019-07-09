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

package org.atoiks.games.nappou2.entities.enemy;

import org.atoiks.games.nappou2.Vector2;

public final class FishPart extends ManualEnemy {

    private static final int SCREEN_EDGE_BUFFER = 100;

    private float direction;
    private float speed;
    private int score;
    private float time;
    private float amplitude;
    private float wspd;
    private int alt;

    public FishPart(int hp, float x, float y, float r, float speed, float direction, float amplitude, float wspd, boolean alt) {
        super(hp, x, y, r);
        this.speed = speed;
        this.direction = direction;
        this.score = hp;
        this.time = 0;
        this.amplitude = amplitude;
        this.wspd = wspd;
        this.alt = alt ? -1 : 1;
    }

    @Override
    public void update(float dt) {
        this.time += dt;

        final float aca = this.alt * (float) Math.cos(wspd * this.time) * this.amplitude;
        final float cosd = (float) Math.cos(this.direction);
        final float sind = (float) Math.sin(this.direction);

        this.x += dt * (cosd * this.speed + aca * sind);
        this.y += dt * (sind * this.speed - aca * cosd);
    }

    @Override
    public int getScore() {
        return score;
    }

    @Override
    public boolean isOutOfScreen(final int w, final int h) {
        final Vector2 pos = this.getPosition();
        final float x = pos.getX();
        final float y = pos.getY();
        final float r = getR();
        return (x + r < -SCREEN_EDGE_BUFFER)
            || (x - r > w + SCREEN_EDGE_BUFFER)
            || (y + r < -SCREEN_EDGE_BUFFER)
            || (y - r > h + SCREEN_EDGE_BUFFER);
    }
}
