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

import org.atoiks.games.nappou2.entities.bullet.PointBullet;
import org.atoiks.games.nappou2.entities.bullet.FallingBullet;

public final class StreamBeam extends ManualEnemy {

    private static final long serialVersionUID = -5994031295806183501L;

    private int time;

    private final int invSign;

    public StreamBeam(int hp, float x, float y, float r, boolean inverted) {
        super(hp, x, y, r);
        this.invSign = inverted ? -1 : 1;
    }

    @Override
    public void update(float dt) {
        time++;

        setY(getY() + invSign * 300 * dt);

        switch (time % 300) {
            case 0:
            case 10:
            case 20:
            case 30:
            case 40:
            case 50:
                final float x = getX();
                final float y = getY();
                final double angle = Math.atan2(Math.min(game.player.getY() - 200, -15) - y, game.player.getX() - x);
                game.addEnemyBullet(new FallingBullet(x, y, 4, 2000, (float) angle, 10000, false));
                break;
        }
    }

    @Override
    public int getScore() {
        return 1;
    }
}
