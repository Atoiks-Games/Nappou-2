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

import org.atoiks.games.framework2d.IGraphics;

import org.atoiks.games.nappou2.graphics.NullEnemyRenderer;

public abstract class EnemyGroup implements IEnemy {

    private static final long serialVersionUID = 823469624677L;

    protected Game game;

    @Override
    public final boolean isDead() {
        // A spawner cannot die
        return false;
    }

    @Override
    public final int changeHp(int delta) {
        return 1;
    }

    @Override
    public final void drift(float dx, float dy) {
        // do nothing
    }

    @Override
    public final void render(IGraphics g) {
        // do nothing
    }

    @Override
    public final void attachGame(Game game) {
        this.game = game;
    }

    @Override
    public final float getX() {
        return Integer.MIN_VALUE / 2;
    }

    @Override
    public final float getY() {
        return Integer.MIN_VALUE / 2;
    }

    @Override
    public final float getR() {
        return -1;
    }

    @Override
    public final boolean collidesWith(float x, float y, float r) {
        // Never collides
        return false;
    }

    @Override
    public final int getScore() {
        // You cannot destroy enemy groups by attacking it...
        return 0;
    }

    @Override
    public final boolean isOutOfScreen(int width, int height) {
        return isDoneSpawning();
    }

    public abstract boolean isDoneSpawning();

    public static EnemyGroup createImmediateGroup(float delay, IEnemy... enemies) {
        return new ImmediateEnemyGroup(delay, enemies);
    }

    public static EnemyGroup createImmediateGroup(float delay, int count, Supplier<? extends IEnemy> supplier) {
        return new ImmediateEnemyGroup(delay, Stream.generate(supplier).limit(count).toArray(IEnemy[]::new));
    }

    public static EnemyGroup createImmediateGroup(float delay, int count, IntFunction<? extends IEnemy> supplier) {
        final IEnemy[] arr = new IEnemy[count];
        for (int i = 0; i < arr.length; ++i) {
            arr[i] = supplier.apply(i);
        }
        return new ImmediateEnemyGroup(delay, arr);
    }

    public static EnemyGroup createLazyGroup(float delay, int limit, Supplier<? extends IEnemy> supplier) {
        return new LazyEnemyGroup(delay, limit, supplier);
    }

    public static EnemyGroup createInfiniteSpawner(float delay, Supplier<? extends IEnemy> supplier) {
        return new LazyEnemyGroup(delay, -1, supplier);
    }
}

class LazyEnemyGroup extends EnemyGroup {

    private static final long serialVersionUID = 2777668966432828726L;

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

class ImmediateEnemyGroup extends EnemyGroup {

    private static final long serialVersionUID = 8495734797446043322L;

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
