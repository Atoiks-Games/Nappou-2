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

import org.atoiks.games.nappou2.spawner.ISpawner;

import org.atoiks.games.nappou2.entities.enemy.IEnemy;
import org.atoiks.games.nappou2.entities.bullet.IBullet;

public final class Game {

    private final LinkedList<IBullet> enemyBullets = new LinkedList<>();
    private final LinkedList<IBullet> playerBullets = new LinkedList<>();
    private final LinkedList<IEnemy> enemies = new LinkedList<>();
    private final LinkedList<ISpawner> spawners = new LinkedList<>();

    public Player player;

    private int score;

    private int gameWidth = Integer.MAX_VALUE;
    private int gameHeight = Integer.MAX_VALUE;

    public void render(IGraphics g) {
        if (player != null) player.render(g);

        for (final IBullet bullet : enemyBullets) bullet.render(g);
        for (final IBullet bullet : playerBullets) bullet.render(g);
        for (final IEnemy enemy : enemies) enemy.render(g);
    }

    public void clipGameBorder(int w, int h) {
        gameWidth = w;
        gameHeight = h;
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

    public void addEnemySpawner(final ISpawner spawner) {
        spawners.add(spawner);
    }

    public boolean noMoreEnemies() {
        return enemies.isEmpty() && spawners.isEmpty();
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int changeScore(int delta) {
        return this.score += delta;
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

    public void updateEnemySpawner(final float dt) {
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
            if (entity.isOutOfScreen(gameWidth, gameHeight)) {
                it.remove();
            }
        }
    }

    public void updateEnemyPosition(final float dt, final Vector2 drift) {
        updateDriftEntityIterator(enemies.iterator(), dt, drift);
    }

    public void updateEnemyBulletPosition(final float dt, final Vector2 drift) {
        updateDriftEntityIterator(enemyBullets.iterator(), dt, drift);
    }

    public void updatePlayerBulletPosition(final float dt, final Vector2 drift) {
        updateDriftEntityIterator(playerBullets.iterator(), dt, drift);
    }

    public void performCollisionCheck() {
        final Vector2 pp = player.getPosition();

        final boolean shieldActive = player.shield.isActive();
        final Vector2 sp = player.shield.getPosition();
        final float sr = player.shield.getR();

        for (final Iterator<IBullet> it = enemyBullets.iterator(); it.hasNext(); ) {
            final IBullet bullet = it.next();
            if (shieldActive && bullet.collidesWith(sp, sr)) {
                it.remove();
            } else if (!player.isRespawnShieldActive() && bullet.collidesWith(pp, Player.COLLISION_RADIUS)) {
                it.remove();
                if (player.changeHpBy(-1) <= 0) {
                    // Player is dead, no more collision can happen
                    return;
                }
                player.activateRespawnShield();
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
                        changeScore(enemy.getScore());
                        outer.remove();
                        continue enemy_loop;
                    }
                }
            }

            if (!player.isRespawnShieldActive() && enemy.collidesWith(pp, Player.COLLISION_RADIUS)) {
                if (player.changeHpBy(-1) <= 0) {
                    return;
                }
                player.activateRespawnShield();
                if (enemy.changeHp(-1) <= 0) {
                    changeScore(enemy.getScore());
                    outer.remove();
                }
            }
        }
    }
}
