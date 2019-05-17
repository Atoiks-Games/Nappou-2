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

package org.atoiks.games.nappou2.entities;

import java.util.stream.Stream;
import java.util.function.Supplier;
import java.util.function.IntFunction;

public abstract class EnemySpawner {

    protected Game game;

    public abstract void update(float dt);

    public final void attachGame(Game game) {
        this.game = game;
    }

    public abstract boolean isDoneSpawning();

    public static EnemySpawner createImmediateGroup(float delay, IEnemy... enemies) {
        return new ImmediateEnemyGroup(delay, enemies);
    }

    public static EnemySpawner createImmediateGroup(float delay, int count, Supplier<? extends IEnemy> supplier) {
        return new ImmediateEnemyGroup(delay, Stream.generate(supplier).limit(count).toArray(IEnemy[]::new));
    }

    public static EnemySpawner createImmediateGroup(float delay, int count, IntFunction<? extends IEnemy> supplier) {
        final IEnemy[] arr = new IEnemy[count];
        for (int i = 0; i < arr.length; ++i) {
            arr[i] = supplier.apply(i);
        }
        return new ImmediateEnemyGroup(delay, arr);
    }

    public static EnemySpawner createLazyGroup(float delay, int limit, Supplier<? extends IEnemy> supplier) {
        return new LazyEnemyGroup(delay, limit, supplier);
    }

    public static EnemySpawner createInfiniteSpawner(float delay, Supplier<? extends IEnemy> supplier) {
        return new LazyEnemyGroup(delay, -1, supplier);
    }
}

class LazyEnemyGroup extends EnemySpawner {

    private final float delay;
    private final int limit;
    private final Supplier<? extends IEnemy> supplier;

    private float time;
    private int index;

    public LazyEnemyGroup(float delay, int limit, Supplier<? extends IEnemy> supplier) {
        this.delay = delay;
        this.limit = limit;
        this.supplier = supplier;
    }

    @Override
    public void update(float dt) {
        while (!isDoneSpawning() && (time += dt) >= delay) {
            time -= delay;
            index++;
            final IEnemy enemy = supplier.get();
            if (enemy != null) game.addEnemy(enemy);
        }
    }

    @Override
    public boolean isDoneSpawning() {
        // Negative limits are used as infinite-spawners
        return limit >= 0 && index >= limit;
    }
}

class ImmediateEnemyGroup extends EnemySpawner {

    private final IEnemy[] enemies;
    private final float delay;

    private float time;
    private int index;

    public ImmediateEnemyGroup(float delay, IEnemy... enemies) {
        this.delay = delay;
        this.enemies = enemies;
    }

    @Override
    public void update(float dt) {
        while (index < enemies.length && (time += dt) >= delay) {
            time -= delay;
            final IEnemy enemy = enemies[index++];
            if (enemy != null) game.addEnemy(enemy);
        }
    }

    @Override
    public boolean isDoneSpawning() {
        return index >= enemies.length;
    }
}
