/**
 *  Nappou-2
 *  Copyright (C) 2017-2018  Atoiks-Games <atoiks-games@outlook.com>

 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.

 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.

 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package org.atoiks.games.nappou2.entities;

import java.io.Serializable;

import java.util.List;
import java.util.ArrayList;

import org.atoiks.games.framework2d.IRender;
import org.atoiks.games.framework2d.IGraphics;

import org.atoiks.games.nappou2.entities.IBullet;

public final class Game implements Serializable, IRender {

    private static final long serialVersionUID = 62102375L;

    public final List<IBullet> enemyBullets = new ArrayList<>(64);
    public final List<IBullet> playerBullets = new ArrayList<>(16);
    public final List<IEnemy> enemies = new ArrayList<>(32);

    public Player player;

    private int score;

    @Override
    public <T> void render(IGraphics<T> g) {
        if (player != null) player.render(g);

        for (int i = 0; i < enemyBullets.size(); ++i) {
            enemyBullets.get(i).render(g);
        }

        for (int i = 0; i < playerBullets.size(); ++i) {
            playerBullets.get(i).render(g);
        }

        for (int i = 0; i < enemies.size(); ++i) {
            enemies.get(i).render(g);
        }
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
}