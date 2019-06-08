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

package org.atoiks.games.nappou2.pattern;

import org.atoiks.games.nappou2.entities.Game;
import org.atoiks.games.nappou2.entities.enemy.IEnemy;

import org.atoiks.games.nappou2.entities.bullet.factory.BulletFactory;
import java.util.Random;

public final class RandomDropPattern implements IAttackPattern {

    public static final Random rnd = new Random();

    private final BulletFactory factory;
    private final float scale;
    private final float lower;
    private float fire;
    private float time = 0;

    public RandomDropPattern(float limit, BulletFactory factory) {
        this(0, limit, factory);
    }

    public RandomDropPattern(float lo, float hi, BulletFactory factory) {
        this.factory = factory;
        this.scale = Math.abs(hi - lo);
        this.lower = Math.min(lo, hi);
        this.recalcFireTime();
    }

    @Override
    public void onFireUpdate(IEnemy enemy, float dt) {
        this.time += dt;

        if (this.time > this.fire) {
            this.time = 0;
            this.recalcFireTime();
            final Game game = enemy.getAssocGame();
            game.addEnemyBullet(factory.createBullet(enemy.getPosition(), (float) (Math.PI / 2)));
        }
    }

    private void recalcFireTime() {
        this.fire = this.lower + rnd.nextFloat() * this.scale;
    }
}
