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

import org.atoiks.games.nappou2.entities.Game;

public final class TrackPointBullet extends PointBullet {

    private static final long serialVersionUID = -1696891011951230605L;

    private static final int SCREEN_EDGE_BUFFER = 16;

    private final Game game;
    private final float scale;
    private final float moveTime;
    private final float delay;

    private float time;
    private boolean moving;

    public TrackPointBullet(float x, float y, float r, Game game, float pathScale, float moveTime, float delay) {
        super(x, y, r);

        this.game = game;
        this.scale = pathScale;
        this.moveTime = moveTime;
        this.delay = delay;

        // These values force endpoints to be calculated
        time = delay;
        moving = false;
    }

    @Override
    public void update(float dt) {
        if (game.player == null) return;

        time += dt;
        if (moving) {
            if (time >= moveTime) {
                moving = false;
                time = 0;
            } else {
                super.update(dt);
            }
        } else if (time >= delay) {
            // Re-calculate endpoints
            dx = scale * (game.player.getX() - x) / moveTime;
            dy = scale * (game.player.getY() - y) / moveTime;
            moving = true;
            time = 0;
        }
    }
}
