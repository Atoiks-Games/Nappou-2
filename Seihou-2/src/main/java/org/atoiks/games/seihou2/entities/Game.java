package org.atoiks.games.seihou2.entities;

import java.io.Serializable;

import java.util.List;
import java.util.ArrayList;

import org.atoiks.games.framework2d.IRender;
import org.atoiks.games.framework2d.IGraphics;

import org.atoiks.games.seihou2.entities.IBullet;

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

    public void cleanup() {
        enemyBullets.clear();
        playerBullets.clear();
        enemies.clear();
    }
}