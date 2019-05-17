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

import org.atoiks.games.nappou2.entities.bullet.StutterBullet;

public final class Ripple extends ManualEnemy {

    private static final long serialVersionUID = 56192645221L;
    //This needs to be different for every enemy...

    private float direction;
    private float speed;
    private int score;
    private float time;
    private int m;

    public Ripple(int hp, float x, float y, float r, float speed, float direction) {
        super(hp, x, y, r);
        this.speed = speed;
        this.direction = direction;
        this.score = hp;
        this.time = 0;
        this.m = 300;
    }

    @Override
    public void update(float dt) {
        this.time++;

        this.x += dt * (float) (Math.cos(this.direction) * this.speed);
        this.y += dt * (float) (Math.sin(this.direction) * this.speed);

        if (this.time % this.m == 0){
            if (this.m > 50) {
                this.time = 0;
                this.m = this.m / 2;

                for (int i = 0; i < 24; ++i) {
                    game.addEnemyBullet(new StutterBullet(x, y, 8, 250, i * (float) Math.PI / 12, 500, 10));
                }
            } else {
                this.x = -100;
            }
        }
    }

    @Override
    public int getScore() {
        return score;
    }
}
