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

import org.atoiks.games.nappou2.spawner.ISpawner;

import org.atoiks.games.nappou2.entities.enemy.IEnemy;
import org.atoiks.games.nappou2.entities.bullet.IBullet;
import org.atoiks.games.nappou2.entities.shield.IShield;

public final class Game {

    private final LinkedList<IBullet> enemyBullets = new LinkedList<>();
    private final LinkedList<IBullet> playerBullets = new LinkedList<>();
    private final LinkedList<IEnemy> enemies = new LinkedList<>();
    private final LinkedList<ISpawner> spawners = new LinkedList<>();

    public final Drifter drifter = new Drifter();
    public final Player player;

    private final Border border;

    public Game(Player player, Border border) {
        this.player = player;
        this.border = border;
    }

    public void render(IGraphics g) {
        player.render(g);

        for (final IBullet bullet : enemyBullets) bullet.render(g);
        for (final IBullet bullet : playerBullets) bullet.render(g);
        for (final IEnemy enemy : enemies) enemy.render(g);
    }

    public void addEnemyBullet(final IBullet bullet) {
        enemyBullets.add(bullet);
    }

    public void addPlayerBullet(final IBullet bullet) {
        playerBullets.add(bullet);
    }

    public void addEnemy(final IEnemy enemy) {
        enemies.add(enemy);
        enemy.attachGame(this);
    }

    public void addSpawner(final ISpawner spawner) {
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
        updateEnemyPosition(dt, displacement);
        updateEnemyBulletPosition(dt, displacement);
        updatePlayerBulletPosition(dt, displacement);
    }

    private void updateEnemySpawner(final float dt) {
        if (spawners.isEmpty()) {
            return;
        }

        final Iterator<ISpawner> it = spawners.iterator();
        while (it.hasNext()) {
            final ISpawner spawner = it.next();
            spawner.onUpdate(this, dt);
            if (spawner.isDoneSpawning()) {
                it.remove();
            }
        }
    }

    private void updateDriftEntityIterator(final Iterator<? extends IDriftEntity> it, final float dt, final Vector2 drift) {
        while (it.hasNext()) {
            final IDriftEntity entity = it.next();
            entity.update(dt);
            entity.drift(drift);
            if (!this.border.containsCollidable(entity)) {
                it.remove();
            }
        }
    }

    private void updateEnemyPosition(final float dt, final Vector2 drift) {
        updateDriftEntityIterator(enemies.iterator(), dt, drift);
    }

    private void updateEnemyBulletPosition(final float dt, final Vector2 drift) {
        updateDriftEntityIterator(enemyBullets.iterator(), dt, drift);
    }

    private void updatePlayerBulletPosition(final float dt, final Vector2 drift) {
        updateDriftEntityIterator(playerBullets.iterator(), dt, drift);
    }

    public void performCollisionCheck() {
        final IShield shield = player.getShield();
        final boolean shieldActive = shield.isActive();
        final IShield respawnShield = player.getRespawnShield();

        for (final Iterator<IBullet> it = enemyBullets.iterator(); it.hasNext(); ) {
            final IBullet bullet = it.next();
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
        for (final Iterator<IEnemy> outer = enemies.iterator(); outer.hasNext(); ) {
            final IEnemy enemy = outer.next();

            final float er = enemy.getR();
            final Vector2 ep = enemy.getPosition();

            for (final Iterator<IBullet> inner = playerBullets.iterator(); inner.hasNext(); ) {
                if (inner.next().collidesWith(ep, er)) {
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
