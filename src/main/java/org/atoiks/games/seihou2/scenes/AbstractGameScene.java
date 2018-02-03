package org.atoiks.games.seihou2.scenes;

import java.util.Arrays;

import java.awt.Color;
import java.awt.Image;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import org.atoiks.games.framework.Scene;
import org.atoiks.games.seihou2.entities.*;

public abstract class AbstractGameScene extends Scene {

    public static final int WIDTH = 900;
    public static final int HEIGHT = 600;
    public static final int GAME_BORDER = 750;
    
    public static final float DEFAULT_DX = 300f;
    public static final float DEFAULT_DY = 300f;
    public static final Color PAUSE_OVERLAY = new Color(192, 192, 192, 100);

    // Conventionally, continue is always the first option,
    // sceneDest is always one less than the selectorY
    private static final int[] selectorY = {342, 402};
    private static final int[] sceneDest = {1};

    private int selector;

    protected final Game game = new Game();

    private byte updatePhase = -1;
    private Updater[] updatePhases = new Updater[]{
        this::updateEnemyBulletPos, this::updateEnemyPos, this::updatePlayerBulletPos, this::testCollisions, this::postUpdate
    };

    protected float playerFireTimeout;
    protected Image hpImg;
    protected Image statsImg;
    protected Image pauseImg;
    protected boolean pause;

    public final int sceneId;

    protected AbstractGameScene(int id) {
        sceneId = id;
    }

    @Override
    public final void resize(int w, int h) {
        // Window size is fixed
    }

    @Override
    public void enter(int prevSceneId) {
        hpImg = (Image) scene.resources().get("hp.png");
        statsImg = (Image) scene.resources().get("stats.png");
        pauseImg = (Image) scene.resources().get("pause.png");

        playerFireTimeout = 0f;
        pause = false;
    }

    @Override
    public void leave() {    
        if (sceneId >= 0) {
            final int[][] scoreDat = (int[][]) scene.resources().get("score.dat");
            final int[] alias = scoreDat[sceneId];
            final int[] a = Arrays.copyOf(alias, alias.length + 1);
            a[a.length - 1] = game.getScore();
            Arrays.sort(a);
            System.arraycopy(a, 1, alias, 0, a.length - 1);
        }

        game.cleanup();
    } 

    public void renderBackground(final Graphics g) {
        g.setColor(Color.black);
        g.fillRect(0, 0, GAME_BORDER, HEIGHT);
    }

    public void renderStats(final Graphics g) {
        if (statsImg != null) {
            g.drawImage(statsImg, GAME_BORDER, 0, null);
        }

        if (hpImg != null) {
            final int hp = game.player.getHp();
            final int w = hpImg.getWidth(null);
            for (int i = 0; i < hp; ++i) {
                g.drawImage(hpImg, GAME_BORDER + 5 + i * w, 24, null);
            }
        }

        final String str = game.getScore() == 0 ? "0" : Integer.toString(game.getScore()) + "000";
        g.drawString(str, GAME_BORDER + 5, 72);
    }

    @Override
    public final void render(final Graphics g) {
        // The bullet-curtain part
        renderBackground(g);
        game.render(g);
        if (pause) {
            g.drawImage(pauseImg, 0, 0, PAUSE_OVERLAY, null);
            g.setColor(Color.black);
            g.drawRect(45, selectorY[selector], 4, 37);
        }

        // The game stats part
        g.setColor(Color.black);
        g.fillRect(GAME_BORDER, 0, WIDTH, HEIGHT);
        g.setColor(Color.white);
        g.drawLine(GAME_BORDER, 0, GAME_BORDER, HEIGHT);

        renderStats(g);
    }

    @Override
    public boolean update(final float dt) {
        // Hopefully the only "black magic" in here
        if (!pause) {
            if (scene.keyboard().isKeyPressed(KeyEvent.VK_ESCAPE)) {
                pause = true;
            }
            if (++updatePhase >= updatePhases.length) {
                updatePhase = 0;
            }
            playerFireTimeout -= dt;
            return procPlayerPos(dt) && updatePhases[updatePhase].update(dt);
        } else {
            if (scene.keyboard().isKeyPressed(KeyEvent.VK_DOWN)) {
                if (++selector >= selectorY.length) selector = 0;
            }
            if (scene.keyboard().isKeyPressed(KeyEvent.VK_UP)) {
                if (--selector < 0) selector = selectorY.length - 1;
            }
            if (scene.keyboard().isKeyPressed(KeyEvent.VK_ENTER)) {
                if (selector == 0) {
                    pause = false;
                } else {
                    scene.switchToScene(sceneDest[selector - 1]);
                }
                return true;
            }
        }
        return true;
    }

    private boolean updateEnemyPos(final float dt) {
        for (int i = 0; i < game.enemies.size(); ++i) {
            final IEnemy enemy = game.enemies.get(i);
            enemy.update(dt);
            if (enemy.isOutOfScreen(GAME_BORDER, HEIGHT)) {
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
            if (bullet.isOutOfScreen(GAME_BORDER, HEIGHT)) {
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
            if (bullet.isOutOfScreen(GAME_BORDER, HEIGHT)) {
                game.playerBullets.remove(i);
                if (--i < -1) break;
            }
        }
        return true;
    }

    private boolean procPlayerPos(final float dt) {
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
            if (tmpPos + Player.RADIUS < GAME_BORDER) tmpVal += DEFAULT_DX;
        }
        if (scene.keyboard().isKeyDown(KeyEvent.VK_LEFT)) {
            if (tmpPos - Player.RADIUS > 0) tmpVal -= DEFAULT_DX;
        }
        game.player.setDx(tmpVal);

        game.player.setSpeedScale(scene.keyboard().isKeyDown(KeyEvent.VK_SHIFT) ? 0.55f : 1);

        game.player.update(dt);

        if (playerFireTimeout <= 0 && scene.keyboard().isKeyDown(KeyEvent.VK_Z)) {
            final float px = game.player.getX();
            final float py = game.player.getY();
            game.addPlayerBullet(new PointBullet(px, py, 5, 0, -DEFAULT_DY * 4.5f));
            playerFireTimeout = 0.2f;  // 0.2 second cap
        }

        if (scene.keyboard().isKeyPressed(KeyEvent.VK_X)) {
            game.player.shield.activate();
        }
        return true;
    }

    private boolean testCollisions(final float dt) {
        final float px = game.player.getX();
        final float py = game.player.getY();

        for (int i = 0; i < game.enemyBullets.size(); ++i) {
            final IBullet bullet = game.enemyBullets.get(i);
            if (bullet.collidesWith(px, py, Player.COLLISION_RADIUS)) {
                if (game.player.changeHpBy(-1) <= 0) {
                    // Goto title scene
                    scene.switchToScene(1);
                    return true;
                }
                game.enemyBullets.remove(i);
                if (--i < -1) break;
            }

            if (game.player.shield.isActive() && bullet.collidesWith(game.player.shield.getX(), game.player.shield.getY(), game.player.shield.getR())) {
                game.enemyBullets.remove(i);
                if (--i < -1) break;
            }
        }

        enemy_loop:
        for (int i = 0; i < game.enemies.size(); ++i) {
            final IEnemy enemy = game.enemies.get(i);
            for (int j = 0; j < game.playerBullets.size(); ++j) {
                final IBullet bullet = game.playerBullets.get(j);
                final float r = bullet.getR();
                if (r < 0) continue;
                if (enemy.collidesWith(bullet.getX(), bullet.getY(), r)) {
                    game.playerBullets.remove(j);
                    if (enemy.changeHp(-1) <= 0) {
                        game.enemies.remove(i);
                        game.changeScore(enemy.getScore());
                        if (--i < -1) break enemy_loop;
                    }
                    if (--j < -1) break;

                    // Enemy is killed, do not test collision against the player
                    continue enemy_loop;
                }
            }

            if (enemy.collidesWith(px, py, Player.COLLISION_RADIUS)) {
                if (game.player.changeHpBy(-1) <= 0) {
                    // Goto title scene
                    scene.switchToScene(1);
                    return true;
                }
                if (enemy.changeHp(-1) <= 0) {
                    game.enemies.remove(i);
                    game.changeScore(enemy.getScore());
                    if (--i < -1) break;
                }
            }
        }

        return true;
    }

    public abstract boolean postUpdate(float dt);
}

interface Updater {

    public boolean update(float dt);
}