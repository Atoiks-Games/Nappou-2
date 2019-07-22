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

import org.atoiks.games.nappou2.entities.enemy.Enemy;

public final class EnemySpawner implements Spawner {

    private final Enemy[] enemies;
    private final float delay;

    private float time;
    private int index;

    public EnemySpawner(float delay, Enemy... enemies) {
        this.delay = delay;
        this.enemies = enemies;
    }

    @Override
    public void onUpdate(final Game game, float dt) {
        while (index < enemies.length && (time += dt) >= delay) {
            time -= delay;
            final Enemy enemy = enemies[index++];
            if (enemy != null) game.addEnemy(enemy);
        }
    }

    @Override
    public boolean isDoneSpawning() {
        return index >= enemies.length;
    }
}
