package org.atoiks.games.seihou2.scenes;

import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import org.atoiks.games.framework.Scene;
import org.atoiks.games.seihou2.entities.*;

public final class MainScene extends Scene {

    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;

    public static final float DEFAULT_DX = 400f;
    public static final float DEFAULT_DY = 400f;

    private final Game game = new Game(WIDTH / 2, HEIGHT / 6 * 5);

    private byte updatePhase = -1;
    private Updater[] updatePhases = new Updater[]{
        this::updateEnemyBulletPos, this::updateEnemyPos, this::updatePlayerPos, this::updatePlayerBulletPos, this::testCollisions
    };

    private float playerFireTimeout = 0;

    @Override
    public void render(final Graphics g) {
        g.setColor(Color.black);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        game.render(g);
    }

    @Override
    public boolean update(final float dt) {
        if (++updatePhase >= updatePhases.length) {
            updatePhase = 0;
        }
        playerFireTimeout -= dt;
        return updatePhases[updatePhase].test(dt);
    }

    private boolean updateEnemyPos(final float dt) {
        for (int i = 0; i < game.enemies.size(); ++i) {
            final IEnemy enemy = game.enemies.get(i);
            enemy.update(dt);
            if (enemy.isOutOfScreen(WIDTH, HEIGHT)) {
                game.enemies.remove(i);
                if (--i < -1) break;
            }
        }
        return true;
    }

    private boolean updateEnemyBulletPos(final float dt) {
        for (int i = 0; i < game.enemyBullets.size(); ++i) {
            final IBullet bullet = game.enemyBullets.get(i);
            bullet.update(dt);
            if (bullet.isOutOfScreen(WIDTH, HEIGHT)) {
                game.enemyBullets.remove(i);
                if (--i < -1) break;
            }
        }
        return true;
    }

    private boolean updatePlayerBulletPos(final float dt) {
        for (int i = 0; i < game.playerBullets.size(); ++i) {
            final IBullet bullet = game.playerBullets.get(i);
            bullet.update(dt);
            if (bullet.isOutOfScreen(WIDTH, HEIGHT)) {
                game.playerBullets.remove(i);
                if (--i < -1) break;
            }
        }
        return true;
    }

    private Thread thread = null;
    private AtomicBoolean running = new AtomicBoolean(true);

    private boolean updatePlayerPos(final float dt) {
        if (thread == null) {
            thread = new Thread(() -> {
                while (running.get()) {
                    float tmpVal = 0;
                    float tmpPos = game.player.getY();
                    if (scene.keyboard().isKeyDown(KeyEvent.VK_DOWN)) {
                        if (tmpPos + Player.RADIUS < HEIGHT) tmpVal += DEFAULT_DY;
                    }
                    if (scene.keyboard().isKeyDown(KeyEvent.VK_UP)) {
                        if (tmpPos - Player.RADIUS > 0) tmpVal -= DEFAULT_DY;
                    }
                    game.player.setDy(tmpVal);

                    tmpVal = 0;
                    tmpPos = game.player.getX();
                    if (scene.keyboard().isKeyDown(KeyEvent.VK_RIGHT)) {
                        if (tmpPos + Player.RADIUS < WIDTH) tmpVal += DEFAULT_DX;
                    }
                    if (scene.keyboard().isKeyDown(KeyEvent.VK_LEFT)) {
                        if (tmpPos - Player.RADIUS > 0) tmpVal -= DEFAULT_DX;
                    }
                    game.player.setDx(tmpVal);

                    game.player.setSpeedScale(scene.keyboard().isKeyDown(KeyEvent.VK_SHIFT) ? 0.65f : 1);
                }
            });
            thread.start();
        }

        game.player.update(dt);

        if (playerFireTimeout <= 0 && scene.keyboard().isKeyDown(KeyEvent.VK_Z)) {
            final float px = game.player.getX();
            final float py = game.player.getY();
            game.addPlayerBullet(new PointBullet(px, py, 5, 0, -DEFAULT_DY * 4.5f));
            playerFireTimeout = 0.2f;  // 0.2 second cap
        }
        return true;
    }

    private boolean testCollisions(final float dt) {
        final float px = game.player.getX();
        final float py = game.player.getY();

        for (int i = 0; i < game.enemyBullets.size(); ++i) {
            final IBullet bullet = game.enemyBullets.get(i);
            if (bullet.collidesWith(px, py, Player.COLLISION_RADIUS)) {
                game.enemyBullets.remove(i);
                if (--i < -1) break;
            }
        }

        enemy_loop:
        for (int i = 0; i < game.enemies.size(); ++i) {
            final IEnemy enemy = game.enemies.get(i);
            if (enemy.collidesWith(px, py, Player.COLLISION_RADIUS)) {
                game.enemies.remove(i);
                if (--i < -1) break;
            }

            for (int j = 0; j < game.playerBullets.size(); ++j) {
                final IBullet bullet = game.playerBullets.get(j);
                final float r = bullet.getR();
                if (r < 0) continue;
                if (enemy.collidesWith(bullet.getX(), bullet.getY(), r)) {
                    game.enemies.remove(i);
                    game.playerBullets.remove(j);
                    if (--i < -1) break enemy_loop;
                    if (--j < -1) break;
                }
            }
        }

        return true;
    }

    @Override
    public void resize(int width, int height) {
        // Window size is fixed
    }

    @Override
    public void enter() {
        game.addEnemyBullet(new PointBullet(WIDTH / 2, -10, 10, 20, 60));
        game.addEnemy(new PointEnemy(30, 30, 8));

        playerFireTimeout = 0f;
    }

    @Override
    public void leave() {
        game.cleanup();
        running.set(false);
        try {
            thread.join();
        } catch (Exception ex) {
        }
    }
}

interface Updater {

    public boolean test(float dt);
}