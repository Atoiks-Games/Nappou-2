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

import org.atoiks.games.nappou2.physics.CollisionTree;
import org.atoiks.games.nappou2.physics.CollisionBox;

public final class Game implements Serializable {

    private static final long serialVersionUID = 62102375L;

    private final ArrayList<IBullet> playerBullets = new ArrayList<>(16);
    private final ArrayList<EnemyGroup> spawners = new ArrayList<>();

    private final CollisionTree<IBullet> enemyBulletTree = new CollisionTree<>();
    private final CollisionTree<IEnemy> enemyTree = new CollisionTree<>();

    private final ArrayList<IEnemy> bufEnemy = new ArrayList<>(8);
    private final ArrayList<IBullet> bufBullet = new ArrayList<>(8);

    public Player player;

    private int score;

    private int gameWidth = Integer.MAX_VALUE;
    private int gameHeight = Integer.MAX_VALUE;

    public void render(IGraphics g) {
        if (player != null) player.render(g);

        for (final IBullet item : enemyBulletTree.keySet()) {
            item.render(g);
        }

        final int szPlayerBullets = playerBullets.size();
        for (int i = 0; i < szPlayerBullets; ++i) {
            playerBullets.get(i).render(g);
        }

        for (final IEnemy item : enemyTree.keySet()) {
            item.render(g);
        }
    }

    public void clipGameBorder(int w, int h) {
        gameWidth = w;
        gameHeight = h;
    }

    public void addEnemyBullet(final IBullet bullet) {
        enemyBulletTree.add(bullet, bullet.makeCollisionBox());
    }

    public void addPlayerBullet(final IBullet bullet) {
        playerBullets.add(bullet);
    }

    public void addEnemy(final IEnemy enemy) {
        enemyTree.add(enemy, enemy.makeCollisionBox());
        enemy.attachGame(this);
    }

    public void addEnemyGroup(final EnemyGroup group) {
        spawners.add(group);
        group.attachGame(this);
    }

    public boolean noMoreEnemies() {
        return enemyTree.isEmpty() && spawners.isEmpty();
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
        enemyBulletTree.clear();
        playerBullets.clear();
    }

    public void cleanup() {
        clearBullets();
        enemyTree.clear();
    }

    public void updateEnemyGroup(final float dt) {
        for (int i = 0; i < spawners.size(); ++i) {
            final EnemyGroup group = spawners.get(i);
            group.update(dt);
            if (group.isDoneSpawning()) {
                spawners.remove(i);
                if (--i < -1) break;
            }
        }
    }

    public void updateEnemyPosition(final float dt, final float dx, final float dy) {
        bufEnemy.clear();
        for (final IEnemy enemy : enemyTree.keySet()) {
            enemy.update(dt);
            enemy.drift(dx, dy);
            if (enemy.isOutOfScreen(gameWidth, gameHeight)) {
                bufEnemy.add(enemy);
                continue;
            }

            enemyTree.update(enemy, enemy.makeCollisionBox());
        }

        for (final IEnemy enemy : bufEnemy) {
            enemyTree.remove(enemy);
        }
    }

    public void updateEnemyBulletPosition(final float dt, final float dx, final float dy) {
        bufBullet.clear();
        for (final IBullet bullet : enemyBulletTree.keySet()) {
            bullet.update(dt);
            bullet.translate(dx, dy);
            if (bullet.isOutOfScreen(gameWidth, gameHeight)) {
                bufBullet.add(bullet);
                continue;
            }

            enemyBulletTree.update(bullet, bullet.makeCollisionBox());
        }

        for (final IBullet bullet : bufBullet) {
            enemyBulletTree.remove(bullet);
        }
    }

    public void updatePlayerBulletPosition(final float dt, final float dx, final float dy) {
        for (int i = 0; i < playerBullets.size(); ++i) {
            final IBullet bullet = playerBullets.get(i);
            bullet.update(dt);
            bullet.translate(dx, dy);
            if (bullet.isOutOfScreen(gameWidth, gameHeight)) {
                playerBullets.remove(i);
                if (--i < -1) break;
            }
        }
    }

    public void performCollisionCheck() {
        bufEnemy.clear();
        bufBullet.clear();

        if (player.shield.isActive()) {
            enemyBulletTree.getCollidingKeys(bufBullet, player.shield.makeCollisionBox());

            final float sx = player.shield.getX();
            final float sy = player.shield.getY();
            final float sr = player.shield.getR();

            for (final IBullet item : bufBullet) {
                if (item.collidesWith(sx, sy, sr)) {
                    enemyBulletTree.remove(item);
                }
            }
        }

        outer:
        for (int i = 0; i < playerBullets.size(); ++i) {
            final IBullet bullet = playerBullets.get(i);
            enemyTree.getCollidingKeys(bufEnemy, bullet.makeCollisionBox());
            for (final IEnemy item : bufEnemy) {
                if (bullet.collidesWith(item.getX(), item.getY(), item.getR())) {
                    playerBullets.remove(i);
                    if (item.changeHp(-1) <= 0) {
                        changeScore(item.getScore());
                        enemyTree.remove(item);
                    }

                    if (--i < -1) break outer;

                    // bullet is destroyed, move on to next one
                    break;
                }
            }
        }

        if (player.isRespawnShieldActive()) return;

        final float px = player.getX();
        final float py = player.getY();
        final CollisionBox playerBox = new CollisionBox(px, py, Player.COLLISION_RADIUS);

        bufBullet.clear();
        enemyBulletTree.getCollidingKeys(bufBullet, playerBox);

        for (final IBullet item : bufBullet) {
            if (item.collidesWith(px, py, Player.COLLISION_RADIUS)) {
                if (player.changeHpBy(-1) > 0) {
                    enemyBulletTree.remove(item);
                    player.activateRespawnShield();
                }
                return;
            }
        }

        bufEnemy.clear();
        enemyTree.getCollidingKeys(bufEnemy, playerBox);

        for (final IEnemy item : bufEnemy) {
            if (item.collidesWith(px, py, Player.COLLISION_RADIUS)) {
                if (player.changeHpBy(-1) > 0) {
                    if (item.changeHp(-1) <= 0) {
                        changeScore(item.getScore());
                        enemyTree.remove(item);
                    }
                    player.activateRespawnShield();
                }
                return;
            }
        }
    }
}
