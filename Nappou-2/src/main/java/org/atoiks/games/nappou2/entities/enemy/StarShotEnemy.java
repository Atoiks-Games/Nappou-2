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

public final class StarShotEnemy extends FireGateEnemy {

    private static final long serialVersionUID = -5680129834018943119L;

    private final int invSign;

    public StarShotEnemy(int hp, float x, float y, float r, boolean inverted) {
        super(hp, x, y, r, 6);
        this.invSign = inverted ? -1 : 1;
    }

    @Override
    public int getScore() {
        return 1;
    }

    @Override
    protected void customUpdate(float dt) {
        y += invSign * 300 * dt;
    }

    @Override
    protected void customFireAction(float dt) {
        // see TrigConstant for angle change
        final double angle = Math.atan2(game.player.getY() - y, game.player.getX() - x);
        final float ksinA = 1000 * (float) Math.sin(angle);
        final float kcosA = 1000 * (float) Math.cos(angle);
        game.addEnemyBullet(new PointBullet(x, y, 2,  kcosA,  ksinA)); // +0,    +0
        game.addEnemyBullet(new PointBullet(x, y, 2, -ksinA,  kcosA)); // +pi/2, +pi/2
        game.addEnemyBullet(new PointBullet(x, y, 2, -kcosA, -ksinA)); // +pi,   +pi
        game.addEnemyBullet(new PointBullet(x, y, 2,  ksinA, -kcosA)); // -pi/2, -pi/2
    }
}
