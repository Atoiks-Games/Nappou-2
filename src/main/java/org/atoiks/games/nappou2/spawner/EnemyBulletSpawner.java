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

package org.atoiks.games.nappou2.spawner;

import org.atoiks.games.nappou2.entities.Game;

import org.atoiks.games.nappou2.entities.bullet.Bullet;

public final class EnemyBulletSpawner implements Spawner {

    private final Bullet[] bullets;
    private final float delay;

    private float time;
    private int index;

    public EnemyBulletSpawner(float delay, Bullet... bullets) {
        this.delay = delay;
        this.bullets = bullets;
    }

    @Override
    public void onUpdate(final Game game, float dt) {
        while (index < bullets.length && (time += dt) >= delay) {
            time -= delay;
            final Bullet bullet = bullets[index++];
            if (bullet != null) game.addEnemyBullet(bullet);
        }
    }

    @Override
    public boolean isDoneSpawning() {
        return index >= bullets.length;
    }
}
