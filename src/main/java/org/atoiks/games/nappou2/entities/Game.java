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

import java.util.Iterator;
import java.util.LinkedList;

import org.atoiks.games.framework2d.IGraphics;

import org.atoiks.games.nappou2.Vector2;
import org.atoiks.games.nappou2.Drifter;
import org.atoiks.games.nappou2.Updatable;

import org.atoiks.games.nappou2.spawner.Spawner;

import org.atoiks.games.nappou2.entities.enemy.Enemy;
import org.atoiks.games.nappou2.entities.bullet.Bullet;
import org.atoiks.games.nappou2.entities.shield.Shield;

public final class Game {

    private final LinkedList<Bullet> enemyBullets = new LinkedList<>();
    private final LinkedList<Bullet> playerBullets = new LinkedList<>();
    private final LinkedList<Enemy> enemies = new LinkedList<>();
    private final LinkedList<Spawner> spawners = new LinkedList<>();

    public final Drifter drifter = new Drifter();
    public final Player player;

    private final Border border;

    public Game(Player player, Border border) {
        this.player = player;
        this.border = border;
    }

    public void render(IGraphics g) {
        player.render(g);

        for (final Drawable bullet : enemyBullets) Drawable.render(g, bullet);
        for (final Drawable bullet : playerBullets) Drawable.render(g, bullet);
        for (final Drawable enemy : enemies) Drawable.render(g, enemy);
    }

    public void addEnemyBullet(final Bullet bullet) {
        enemyBullets.add(bullet);
    }

    public void addPlayerBullet(final Bullet bullet) {
        playerBullets.add(bullet);
    }

    public void addEnemy(final Enemy enemy) {
        enemies.add(enemy);
        enemy.attachGame(this);
    }

    public void addSpawner(final Spawner spawner) {
        spawners.add(spawner);
    }

    public boolean noMoreEnemies() {
        return enemies.isEmpty() && spawners.isEmpty();
    }

    public void clearBullets() {
        enemyBullets.clear();
        playerBullets.clear();
    }

    public void cleanup() {
        clearBullets();
        enemies.clear();
        spawners.clear();
    }

    public boolean shouldAbort() {
        return this.player.getHpCounter().isOutOfHp();
    }

    public void update(final float dt) {
        this.drifter.update(dt);
        final Vector2 displacement = this.drifter.getDrift().mul(dt);

        updateEnemySpawner(dt);
        updateEnemies(dt, displacement);
        updateEnemyBullets(dt, displacement);
        updatePlayerBullets(dt, displacement);
    }

    private void updateEnemySpawner(final float dt) {
        if (spawners.isEmpty()) {
            return;
        }

        final Iterator<Spawner> it = spawners.iterator();
        while (it.hasNext()) {
            final Spawner spawner = it.next();
            spawner.onUpdate(this, dt);
            if (spawner.isDoneSpawning()) {
                it.remove();
            }
        }
    }

    private void performEntityUpdate(final Iterator<? extends Updatable> it, final float dt) {
        while (it.hasNext()) {
            it.next().update(dt);
        }
    }

    private void performEntityDrift(final Iterator<? extends Driftable> it, final Vector2 drift) {
        while (it.hasNext()) {
            it.next().drift(drift);
        }
    }

    private void performEntityScreenCheck(final Iterator<? extends Collidable> it) {
        while (it.hasNext()) {
            if (!this.border.containsCollidable(it.next())) {
                it.remove();
            }
        }
    }

    private void updateEnemies(final float dt, final Vector2 drift) {
        if (!enemies.isEmpty()) {
            this.performEntityUpdate(enemies.iterator(), dt);
            this.performEntityDrift(enemies.iterator(), drift);
            this.performEntityScreenCheck(enemies.iterator());
        }
    }

    private void updateEnemyBullets(final float dt, final Vector2 drift) {
        if (!enemyBullets.isEmpty()) {
            this.performEntityUpdate(enemyBullets.iterator(), dt);
            this.performEntityDrift(enemyBullets.iterator(), drift);
            this.performEntityScreenCheck(enemyBullets.iterator());
        }
    }

    private void updatePlayerBullets(final float dt, final Vector2 drift) {
        if (!playerBullets.isEmpty()) {
            this.performEntityUpdate(playerBullets.iterator(), dt);
            this.performEntityDrift(playerBullets.iterator(), drift);
            this.performEntityScreenCheck(playerBullets.iterator());
        }
    }

    public void performCollisionCheck() {
        final Shield shield = player.getShield();
        final boolean shieldActive = shield.isActive();
        final Shield respawnShield = player.getRespawnShield();

        for (final Iterator<Bullet> it = enemyBullets.iterator(); it.hasNext(); ) {
            final Bullet bullet = it.next();
            if (shieldActive && shield.collidesWith(bullet)) {
                it.remove();
            } else if (!respawnShield.isActive() && player.collidesWith(bullet)) {
                it.remove();
                if (player.getHpCounter().changeBy(-1).isOutOfHp()) {
                    // Player is dead, no more collision can happen
                    return;
                }
                respawnShield.activate();
            }
        }

        enemy_loop:
        for (final Iterator<Enemy> outer = enemies.iterator(); outer.hasNext(); ) {
            final Enemy enemy = outer.next();

            for (final Iterator<Bullet> inner = playerBullets.iterator(); inner.hasNext(); ) {
                if (enemy.collidesWith(inner.next())) {
                    inner.remove();
                    if (enemy.changeHp(-1) <= 0) {
                        this.player.getScoreCounter().changeBy(enemy.getScore());
                        outer.remove();
                        continue enemy_loop;
                    }
                }
            }

            if (!respawnShield.isActive() && player.collidesWith(enemy)) {
                if (player.getHpCounter().changeBy(-1).isOutOfHp()) {
                    return;
                }
                respawnShield.activate();
                if (enemy.changeHp(-1) <= 0) {
                    this.player.getScoreCounter().changeBy(enemy.getScore());
                    outer.remove();
                }
            }
        }
    }
}
