package org.atoiks.games.seihou2.scenes;

import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import org.atoiks.games.framework.Frame;
import org.atoiks.games.framework.Scene;
import org.atoiks.games.seihou2.entities.*;

public final class MainScene extends Scene {

    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;

    public static final float DEFAULT_DX = 400f;
    public static final float DEFAULT_DY = 400f;

    private final List<IBullet> enemyBullets = new ArrayList<>(64);
    private final List<IBullet> playerBullets = new ArrayList<>(32);
    private final List<IEnemy> enemies = new ArrayList<>(32);
    private final Player player = new Player(WIDTH / 2, HEIGHT / 6 * 5);

    private byte updatePhase = -1;
    private Updater[] updatePhases = new Updater[]{
        this::updateEnemyBulletPos, this::updateEnemyPos, this::updatePlayerPos, this::updatePlayerBulletPos, this::testCollisions
    };

    private float playerFireTimeout = 0;

    @Override
    public void render(final Graphics g) {
        g.setColor(Color.black);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        player.render(g);

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

    @Override
    public boolean update(final float dt) {
        if (++updatePhase >= updatePhases.length) {
            updatePhase = 0;
        }
        playerFireTimeout -= dt;
        return updatePhases[updatePhase].test(dt);
    }

    private boolean updateEnemyPos(final float dt) {
        for (int i = 0; i < enemies.size(); ++i) {
            final IEnemy enemy = enemies.get(i);
            enemy.update(dt);
            if (enemy.isOutOfScreen(WIDTH, HEIGHT)) {
                enemies.remove(i);
                if (--i < -1) break;
            }
        }
        return true;
    }

    private boolean updateEnemyBulletPos(final float dt) {
        for (int i = 0; i < enemyBullets.size(); ++i) {
            final IBullet bullet = enemyBullets.get(i);
            bullet.update(dt);
            if (bullet.isOutOfScreen(WIDTH, HEIGHT)) {
                enemyBullets.remove(i);
                if (--i < -1) break;
            }
        }
        return true;
    }

    private boolean updatePlayerBulletPos(final float dt) {
        for (int i = 0; i < playerBullets.size(); ++i) {
            final IBullet bullet = playerBullets.get(i);
            bullet.update(dt);
            if (bullet.isOutOfScreen(WIDTH, HEIGHT)) {
                playerBullets.remove(i);
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
                    float tmpPos = player.getY();
                    if (scene.keyboard().isKeyDown(KeyEvent.VK_DOWN)) {
                        if (tmpPos + Player.RADIUS < HEIGHT) tmpVal += DEFAULT_DY;
                    }
                    if (scene.keyboard().isKeyDown(KeyEvent.VK_UP)) {
                        if (tmpPos - Player.RADIUS > 0) tmpVal -= DEFAULT_DY;
                    }
                    player.setDy(tmpVal);

                    tmpVal = 0;
                    tmpPos = player.getX();
                    if (scene.keyboard().isKeyDown(KeyEvent.VK_RIGHT)) {
                        if (tmpPos + Player.RADIUS < WIDTH) tmpVal += DEFAULT_DX;
                    }
                    if (scene.keyboard().isKeyDown(KeyEvent.VK_LEFT)) {
                        if (tmpPos - Player.RADIUS > 0) tmpVal -= DEFAULT_DX;
                    }
                    player.setDx(tmpVal);

                    player.setSpeedScale(scene.keyboard().isKeyDown(KeyEvent.VK_SHIFT) ? 0.65f : 1);
                }
            });
            thread.start();
        }

        player.update(dt);

        if (playerFireTimeout <= 0 && scene.keyboard().isKeyDown(KeyEvent.VK_Z)) {
            final float px = player.getX();
            final float py = player.getY();
            playerBullets.add(new PointBullet(px, py, 5, 0, -DEFAULT_DY * 2.5f));
            playerFireTimeout = 0.2f;  // 0.2 second cap
        }
        return true;
    }

    private boolean testCollisions(final float dt) {
        final float px = player.getX();
        final float py = player.getY();

        for (int i = 0; i < enemyBullets.size(); ++i) {
            final IBullet bullet = enemyBullets.get(i);
            if (bullet.collidesWith(px, py, Player.COLLISION_RADIUS)) {
                enemyBullets.remove(i);
                if (--i < -1) break;
            }
        }

        enemy_loop:
        for (int i = 0; i < enemies.size(); ++i) {
            final IEnemy enemy = enemies.get(i);
            if (enemy.collidesWith(px, py, Player.COLLISION_RADIUS)) {
                enemies.remove(i);
                if (--i < -1) break;
            }

            for (int j = 0; j < playerBullets.size(); ++j) {
                final IBullet bullet = playerBullets.get(j);
                final float r = bullet.getR();
                if (r < 0) continue;
                if (enemy.collidesWith(bullet.getX(), bullet.getY(), r)) {
                    enemies.remove(i);
                    playerBullets.remove(j);
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
        enemyBullets.add(new PointBullet(WIDTH / 2, -10, 10, 20, 60));
        enemyBullets.add(new Beam(20, 20, 1, 30, (float) (Math.PI / 6), 190));

        playerFireTimeout = 0f;
    }

    @Override
    public void leave() {
        // Dispose references and hope GC cleans them up
        enemyBullets.clear();
        playerBullets.clear();
        enemies.clear();
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