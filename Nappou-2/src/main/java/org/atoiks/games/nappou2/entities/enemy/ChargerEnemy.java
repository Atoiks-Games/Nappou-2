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

package org.atoiks.games.nappou2.entities.enemy;

public final class ChargerEnemy extends AbstractEnemy {

    private static final long serialVersionUID = 561492645221L;

    private float speed;
    private float timer;
    private double angle = 0;

    public ChargerEnemy(int hp, float x, float y, float r, float timer, float speed) {
        super(hp, x, y, r);
        this.speed = speed;
        this.timer = timer;
    }

    @Override
    public void update(float dt) {
        if (timer > 0) {
            timer -= 10 * dt;
        }

        if (timer >= 0) {
            angle = Math.atan2(game.player.getY() - y, game.player.getX() - x);
        }

        x += speed * (float) Math.cos(angle) * dt;
        y += speed * (float) Math.sin(angle) * dt;
    }

    @Override
    public int getScore() {
        return 0;
    }
}
