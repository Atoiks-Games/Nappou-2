/**
 *  Nappou-2
 *  Copyright (C) 2017-2018  Atoiks-Games <atoiks-games@outlook.com>
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

import org.atoiks.games.framework2d.IRender;
import org.atoiks.games.framework2d.IGraphics;

public final class Game implements Serializable, IRender {

    private static final long serialVersionUID = 62102375L;


    private final LinkedList<IBullet> enemyBullets = new LinkedList<>();
    private final LinkedList<IBullet> playerBullets = new LinkedList<>();
    private final LinkedList<IEnemy> enemies = new LinkedList<>();

    public Player player;

    private int score;

    private int gameWidth = Integer.MAX_VALUE;
    private int gameHeight = Integer.MAX_VALUE;

    @Override
    public <T> void render(IGraphics<T> g) {
        if (player != null) player.render(g);

        for (final IBullet r : enemyBullets) {
            r.render(g);
        }
        for (final IBullet r : playerBullets) {
            r.render(g);
        }
        for (final IEnemy r : enemies) {
            r.render(g);
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

    public boolean noMoreEnemies() {
        return enemies.isEmpty();
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
    }

    public void updateEnemyPosition(final float dt, final float dx, final float dy) {
        for (final Iterator<IEnemy> it = enemies.iterator(); it.hasNext(); ) {
            final IEnemy enemy = it.next();
            enemy.update(dt);
            enemy.drift(dx, dy);
            if (enemy.isOutOfScreen(gameWidth, gameHeight)) {
                it.remove();
                continue;
            }
        }
    }

    public void updateEnemyBulletPosition(final float dt, final float dx, final float dy) {
        for (final Iterator<IBullet> it = enemyBullets.iterator(); it.hasNext(); ) {
            final IBullet bullet = it.next();
            bullet.update(dt);
            bullet.translate(dx, dy);
            if (bullet.isOutOfScreen(gameWidth, gameHeight)) {
                it.remove();
                continue;
            }
        }
    }

    public void updatePlayerBulletPosition(final float dt, final float dx, final float dy) {
        for (final Iterator<IBullet> it = playerBullets.iterator(); it.hasNext(); ) {
            final IBullet bullet = it.next();
            bullet.update(dt);
            bullet.translate(dx, dy);
            if (bullet.isOutOfScreen(gameWidth, gameHeight)) {
                it.remove();
                continue;
            }
        }
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
                continue;
            }

            if (!player.isRespawnShieldActive() && bullet.collidesWith(px, py, Player.COLLISION_RADIUS)) {
                if (player.changeHpBy(-1) <= 0) {
                    // Player is dead, no more collision can happen
                    return;
                }
                player.activateRespawnShield();
                it.remove();
                continue;
            }
        }

        enemy_loop:
        for (final Iterator<IEnemy> outer = enemies.iterator(); outer.hasNext(); ) {
            final IEnemy enemy = outer.next();

            // If radius is less than zero, it cannot collide with anything, so skip iteration
            final float er = enemy.getR();
            if (er < 0) continue;

            final float ex = enemy.getX();
            final float ey = enemy.getY();

            for (final Iterator<IBullet> inner = playerBullets.iterator(); inner.hasNext(); ) {
                final IBullet bullet = inner.next();
                if (bullet.collidesWith(ex, ey, er)) {
                    inner.remove();
                    if (enemy.changeHp(-1) <= 0) {
                        changeScore(enemy.getScore());
                        outer.remove();
                        continue enemy_loop;
                    }
                    // Bullet is already destroyed, move on to next one
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
                    outer.remove();
                    continue;
                }
            }
        }
    }
}
