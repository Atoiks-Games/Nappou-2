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

import org.atoiks.games.nappou2.entities.spawner.LazyEnemySpawner;
import org.atoiks.games.nappou2.entities.spawner.ImmediateEnemySpawner;

public abstract class EnemySpawner {

    protected Game game;

    public abstract void update(float dt);

    public final void attachGame(Game game) {
        this.game = game;
    }

    public abstract boolean isDoneSpawning();

    public static EnemySpawner createImmediateGroup(float delay, IEnemy... enemies) {
        return new ImmediateEnemySpawner(delay, enemies);
    }

    public static EnemySpawner createImmediateGroup(float delay, int count, Supplier<? extends IEnemy> supplier) {
        return new ImmediateEnemySpawner(delay, Stream.generate(supplier).limit(count).toArray(IEnemy[]::new));
    }

    public static EnemySpawner createImmediateGroup(float delay, int count, IntFunction<? extends IEnemy> supplier) {
        final IEnemy[] arr = new IEnemy[count];
        for (int i = 0; i < arr.length; ++i) {
            arr[i] = supplier.apply(i);
        }
        return new ImmediateEnemySpawner(delay, arr);
    }

    public static EnemySpawner createLazyGroup(float delay, int limit, Supplier<? extends IEnemy> supplier) {
        return new LazyEnemySpawner(delay, limit, supplier);
    }

    public static EnemySpawner createInfiniteSpawner(float delay, Supplier<? extends IEnemy> supplier) {
        return new LazyEnemySpawner(delay, -1, supplier);
    }
}
