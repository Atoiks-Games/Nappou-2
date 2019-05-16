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

import java.io.Serializable;

import java.util.Iterator;
import java.util.LinkedList;

import org.atoiks.games.framework2d.IGraphics;

public final class Game implements Serializable {

    private static final long serialVersionUID = 62102375L;

    private final LinkedList<IBullet> enemyBullets = new LinkedList<>();
    private final LinkedList<IBullet> playerBullets = new LinkedList<>();
    private final LinkedList<IEnemy> enemies = new LinkedList<>();
    private final LinkedList<EnemySpawner> spawners = new LinkedList<>();

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

    public void addEnemySpawner(final EnemySpawner spawner) {
        spawners.add(spawner);
        spawner.attachGame(this);
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
        final Iterator<EnemySpawner> it = spawners.iterator();
        while (it.hasNext()) {
            final EnemySpawner spawner = it.next();
            spawner.update(dt);
            if (spawner.isDoneSpawning()) {
                it.remove();
            }
        }
    }

    private <T extends IDriftEntity & ICollidable> void updateDriftCollidableIterator(final Iterator<T> it, final float dt, final float dx, final float dy) {
        while (it.hasNext()) {
            final T entity = it.next();
            entity.update(dt);
            entity.drift(dx, dy);
            if (entity.isOutOfScreen(gameWidth, gameHeight)) {
                it.remove();
            }
        }
    }

    public void updateEnemyPosition(final float dt, final float dx, final float dy) {
        updateDriftCollidableIterator(enemies.iterator(), dt, dx, dy);
    }

    public void updateEnemyBulletPosition(final float dt, final float dx, final float dy) {
        updateDriftCollidableIterator(enemyBullets.iterator(), dt, dx, dy);
    }

    public void updatePlayerBulletPosition(final float dt, final float dx, final float dy) {
        updateDriftCollidableIterator(playerBullets.iterator(), dt, dx, dy);
    }

    public void performCollisionCheck() {
        final float px = player.getX();
        final float py = player.getY();

        final boolean shieldActive = player.shield.isActive();
        final float sx = player.shield.getX();
        final float sy = player.shield.getY();
        final float sr = player.shield.getR();

        for (final Iterator<IBullet> it = enemyBullets.iterator(); it.hasNext(); ) {
            final IBullet bullet = it.next();
            if (shieldActive && bullet.collidesWith(sx, sy, sr)) {
                it.remove();
            } else if (!player.isRespawnShieldActive() && bullet.collidesWith(px, py, Player.COLLISION_RADIUS)) {
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
            final float ex = enemy.getX();
            final float ey = enemy.getY();

            for (final Iterator<IBullet> inner = playerBullets.iterator(); inner.hasNext(); ) {
                if (inner.next().collidesWith(ex, ey, er)) {
                    inner.remove();
                    if (enemy.changeHp(-1) <= 0) {
                        changeScore(enemy.getScore());
                        outer.remove();
                        continue enemy_loop;
                    }
                }
            }

            if (!player.isRespawnShieldActive() && enemy.collidesWith(px, py, Player.COLLISION_RADIUS)) {
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
