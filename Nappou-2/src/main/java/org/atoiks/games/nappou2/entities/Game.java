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

import java.util.ArrayList;

import org.atoiks.games.framework2d.IGraphics;

public final class Game implements Serializable {

    private static final long serialVersionUID = 62102375L;

    private final ArrayList<IBullet> enemyBullets = new ArrayList<>(32);
    private final ArrayList<IBullet> playerBullets = new ArrayList<>(16);
    private final ArrayList<IEnemy> enemies = new ArrayList<>(16);
    private final ArrayList<EnemySpawner> spawners = new ArrayList<>(16);

    public Player player;

    private int score;

    private int gameWidth = Integer.MAX_VALUE;
    private int gameHeight = Integer.MAX_VALUE;

    public void render(IGraphics g) {
        if (player != null) player.render(g);

        final int szEnemyBullets = enemyBullets.size();
        for (int i = 0; i < szEnemyBullets; ++i) {
            enemyBullets.get(i).render(g);
        }

        final int szPlayerBullets = playerBullets.size();
        for (int i = 0; i < szPlayerBullets; ++i) {
            playerBullets.get(i).render(g);
        }

        final int szEnemies = enemies.size();
        for (int i = 0; i < szEnemies; ++i) {
            enemies.get(i).render(g);
        }
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
        for (int i = 0; i < spawners.size(); ++i) {
            final EnemySpawner spawner = spawners.get(i);
            spawner.update(dt);
            if (spawner.isDoneSpawning()) {
                spawners.remove(i);
                if (--i < -1) break;
            }
        }
    }

    private <T extends IDriftEntity & ICollidable> void updateDriftCollidableIterator(final ArrayList<T> list, final float dt, final float dx, final float dy) {
        for (int i = 0; i < list.size(); ++i) {
            final T entity = list.get(i);
            entity.update(dt);
            entity.drift(dx, dy);
            if (entity.isOutOfScreen(gameWidth, gameHeight)) {
                list.remove(i);
                if (--i < -1) break;
            }
        }
    }

    public void updateEnemyPosition(final float dt, final float dx, final float dy) {
        updateDriftCollidableIterator(enemies, dt, dx, dy);
    }

    public void updateEnemyBulletPosition(final float dt, final float dx, final float dy) {
        updateDriftCollidableIterator(enemyBullets, dt, dx, dy);
    }

    public void updatePlayerBulletPosition(final float dt, final float dx, final float dy) {
        updateDriftCollidableIterator(playerBullets, dt, dx, dy);
    }

    public void performCollisionCheck() {
        final float px = player.getX();
        final float py = player.getY();

        final boolean shieldActive = player.shield.isActive();
        final float sx = player.shield.getX();
        final float sy = player.shield.getY();
        final float sr = player.shield.getR();

        for (int i = 0; i < enemyBullets.size(); ++i) {
            final IBullet bullet = enemyBullets.get(i);
            if (shieldActive && bullet.collidesWith(sx, sy, sr)) {
                enemyBullets.remove(i);
                if (--i < -1) break;
                continue;
            }

            if (!player.isRespawnShieldActive() && bullet.collidesWith(px, py, Player.COLLISION_RADIUS)) {
                enemyBullets.remove(i);
                if (player.changeHpBy(-1) <= 0) {
                    // Player is dead, no more collision can happen
                    return;
                }
                player.activateRespawnShield();
                if (--i < -1) break;
                continue;
            }
        }

        enemy_loop:
        for (int i = 0; i < enemies.size(); ++i) {
            final IEnemy enemy = enemies.get(i);

            final float er = enemy.getR();
            final float ex = enemy.getX();
            final float ey = enemy.getY();

            for (int j = 0; j < playerBullets.size(); ++j) {
                final IBullet bullet = playerBullets.get(j);
                if (bullet.collidesWith(ex, ey, er)) {
                    playerBullets.remove(j);
                    if (enemy.changeHp(-1) <= 0) {
                        changeScore(enemy.getScore());
                        enemies.remove(i);
                        if (--i < -1) break enemy_loop;
                        continue enemy_loop;
                    }
                    // Bullet is already destroyed, move on to next one
                    if (--j < -1) break;
                    continue;
                }
            }

            if (!player.isRespawnShieldActive() && enemy.collidesWith(px, py, Player.COLLISION_RADIUS)) {
                if (player.changeHpBy(-1) <= 0) {
                    return;
                }
                player.activateRespawnShield();
                if (enemy.changeHp(-1) <= 0) {
                    changeScore(enemy.getScore());
                    enemies.remove(i);
                    if (--i < -1) break;
                    continue;
                }
            }
        }
    }
}
