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

package org.atoiks.games.nappou2.scenes;

import java.util.Arrays;

import java.awt.Font;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.KeyEvent;

import se.tube42.lib.tweeny.TweenManager;

import org.atoiks.games.framework2d.GameScene;
import org.atoiks.games.framework2d.IGraphics;

import org.atoiks.games.nappou2.Drifter;
import org.atoiks.games.nappou2.ScoreData;
import org.atoiks.games.nappou2.Difficulty;
import org.atoiks.games.nappou2.GameConfig;

import org.atoiks.games.nappou2.entities.*;
import org.atoiks.games.nappou2.entities.bullet.*;

import static org.atoiks.games.nappou2.App.SANS_FONT;

public abstract class AbstractGameScene extends GameScene {

    public static final int WIDTH = 900;
    public static final int HEIGHT = 600;
    public static final int GAME_BORDER = 750;

    public static final float DEFAULT_DX = 300f;
    public static final float DEFAULT_DY = 300f;
    public static final Color PAUSE_OVERLAY = new Color(192, 192, 192, 100);
    public static final Color STATS_GREY = new Color(106, 106, 106);

    // Conventionally, continue is always the first option,
    // sceneDest is always one less than the selectorY
    private static final int[] selectorY = {342, 402};
    private static final int[] sceneDest = {0};
    private static final int OPT_HEIGHT = 37;

    private int selector;

    protected final Game game = new Game();

    private byte updatePhase = -1;
    private boolean ignoreDamage = false;
    private boolean disableInput = false;

    protected float playerFireTimeout;
    protected Image hpImg;
    protected boolean pause;
    protected Difficulty difficulty;

    protected final Drifter drift = new Drifter();

    public final int sceneId;

    private boolean challengeMode;

    private String dialogSpeaker;
    private String[] dialogMessage;

    protected AbstractGameScene(int id) {
        sceneId = id;
    }

    @Override
    public final void resize(int w, int h) {
        // Window size is fixed
    }

    protected final void disableDamage() {
        ignoreDamage = true;
    }

    protected final void enableDamage() {
        ignoreDamage = false;
    }

    protected final void disableInput() {
        disableInput = true;
    }

    protected final void enableInput() {
        disableInput = false;
    }

    protected final void resetDialogue() {
        dialogSpeaker = null;
        dialogMessage = null;
    }

    protected final void updateDialogue(final String speaker, final String... lines) {
        this.dialogSpeaker = speaker + ":";
        this.dialogMessage = lines;
    }

    @Override
    public void init() {
        hpImg = (Image) scene.resources().get("hp.png");
    }

    @Override
    public void enter(int prevSceneId) {
        difficulty = (Difficulty) scene.resources().get("difficulty");
        challengeMode = ((GameConfig) scene.resources().get("game.cfg")).challengeMode;

        playerFireTimeout = 0f;
        pause = false;
        enableInput();
        enableDamage();
    }

    @Override
    public void leave() {
        if (sceneId >= 0) {
            final ScoreData scoreDat = (ScoreData) scene.resources().get("score.dat");
            final int[] alias = scoreDat.data[challengeMode ? 1 : 0][sceneId][difficulty.ordinal()];
            final int[] a = Arrays.copyOf(alias, alias.length + 1);
            a[a.length - 1] = (challengeMode ? 2 : 1) * game.getScore();
            Arrays.sort(a);
            System.arraycopy(a, 1, alias, 0, a.length - 1);
        }

        game.cleanup();
    }

    public void renderBackground(final IGraphics g) {
        g.setColor(Color.black);
        g.fillRect(0, 0, GAME_BORDER, HEIGHT);
    }

    public void renderStats(final IGraphics g) {
        g.setColor(STATS_GREY);
        g.fillRect(GAME_BORDER, 0, WIDTH, HEIGHT);

        g.setColor(Color.white);
        g.setFont(SANS_FONT);
        g.drawString("HP Remaining", GAME_BORDER + 2, 16);
        g.drawString("Score", GAME_BORDER + 2, 58);

        if (hpImg != null) {
            final int hp = game.player.getHp();
            final int w = hpImg.getWidth(null);
            for (int i = 0; i < hp; ++i) {
                g.drawImage(hpImg, GAME_BORDER + 5 + i * w, 24);
            }
        }

        final String str = game.getScore() == 0 ? "0" : Integer.toString(game.getScore()) + "000";
        g.drawString(str, GAME_BORDER + 5, 74);

        if (game.player.shield.isReady()) {
            g.drawString("Lumas Ready", GAME_BORDER + 30, 96);
        }

        if (dialogSpeaker != null) {
            drawDialog(g, dialogSpeaker, dialogMessage);
        }
    }

    @Override
    public final <T> void render(final IGraphics<T> g) {
        // The bullet-curtain part
        renderBackground(g);
        game.render(g);

        // The game stats part
        g.setColor(Color.black);
        g.fillRect(GAME_BORDER, 0, WIDTH, HEIGHT);
        g.setColor(Color.white);
        g.drawLine(GAME_BORDER, 0, GAME_BORDER, HEIGHT);

        renderStats(g);

        if (pause) {
            g.setColor(PAUSE_OVERLAY);
            g.fillRect(0, 0, GAME_BORDER, HEIGHT);
            g.setColor(Color.black);
            g.setFont(TitleScene.TITLE_FONT);
            g.drawString("PAUSE", 274, 202);
            g.setFont(TitleScene.OPTION_FONT);
            g.drawString("Continue Game", 52, 373);
            g.drawString("Return to Title", 52, 433);
            g.drawRect(45, selectorY[selector], 49, selectorY[selector] + OPT_HEIGHT);
        }
    }

    @Override
    public boolean update(final float dt) {
        // Hopefully the only "black magic" in here
        if (!pause) {
            if (scene.keyboard().isKeyPressed(KeyEvent.VK_ESCAPE)) {
                pause = true;
            }
            playerFireTimeout -= dt;
            TweenManager.service((long) (dt * 1000000));
            if (!procPlayerPos(dt)) return false;
            switch (++updatePhase) {
                default: {
                    updatePhase = 0;    // set updatePhase to resonable value
                    drift.update(dt);   // update drift speed
                    // FALLTHROUGH!
                }
                case 0: return updateEnemyBulletPos(dt);
                case 1: return updateEnemyPos(dt);
                case 2: return updatePlayerBulletPos(dt);
                case 3: return testCollisions(dt);
                case 4: return postUpdate(dt);
            }
        } else {
            if (scene.keyboard().isKeyPressed(KeyEvent.VK_ESCAPE)) {
                pause = false;
                selector = 0;
                return true;
            }

            if (scene.keyboard().isKeyPressed(KeyEvent.VK_ENTER) || scene.mouse().isButtonClicked(1, 2)) {
                if (selector == 0) {
                    pause = false;
                } else {
                    scene.switchToScene(sceneDest[selector - 1]);
                    // reset selector to 0, otherwise pause in next game brings user to unexpected option
                    selector = 0;
                }
                return true;
            }
            if (scene.keyboard().isKeyPressed(KeyEvent.VK_DOWN)) {
                if (++selector >= selectorY.length) selector = 0;
            }
            if (scene.keyboard().isKeyPressed(KeyEvent.VK_UP)) {
                if (--selector < 0) selector = selectorY.length - 1;
            }

            if (scene.mouse().positionChanged()) {
                final int mouseY = scene.mouse().getLocalY();
                for (int i = 0; i < selectorY.length; ++i) {
                    final int selBase = selectorY[i];
                    if (mouseY > selBase && mouseY < (selBase + OPT_HEIGHT)) {
                        selector = i;
                        break;
                    }
                }
            }
        }
        return true;
    }

    private boolean updateEnemyPos(final float dt) {
        final float driftX = dt * drift.getDx();
        final float driftY = dt * drift.getDy();

        for (int i = 0; i < game.enemies.size(); ++i) {
            final IEnemy enemy = game.enemies.get(i);
            enemy.update(dt);
            enemy.drift(driftX, driftY);
            if (enemy.isOutOfScreen(GAME_BORDER, HEIGHT)) {
                game.enemies.remove(i);
                if (--i < -1) break;
            }
        }
        return true;
    }

    private boolean updateEnemyBulletPos(final float dt) {
        final float driftX = dt * drift.getDx();
        final float driftY = dt * drift.getDy();

        for (int i = 0; i < game.enemyBullets.size(); ++i) {
            final IBullet bullet = game.enemyBullets.get(i);
            bullet.update(dt);
            bullet.translate(driftX, driftY);
            if (bullet.isOutOfScreen(GAME_BORDER, HEIGHT)) {
                game.enemyBullets.remove(i);
                if (--i < -1) break;
            }
        }
        return true;
    }

    private boolean updatePlayerBulletPos(final float dt) {
        final float driftX = dt * drift.getDx();
        final float driftY = dt * drift.getDy();

        for (int i = 0; i < game.playerBullets.size(); ++i) {
            final IBullet bullet = game.playerBullets.get(i);
            bullet.update(dt);
            bullet.translate(driftX, driftY);
            if (bullet.isOutOfScreen(GAME_BORDER, HEIGHT)) {
                game.playerBullets.remove(i);
                if (--i < -1) break;
            }
        }
        return true;
    }

    private boolean procPlayerPos(final float dt) {
        if (disableInput) return true;

        // TODO: Simplify this
        float tmpVal = drift.getDy();
        float tmpPos = game.player.getY();
        if (scene.keyboard().isKeyDown(KeyEvent.VK_DOWN))   tmpVal += DEFAULT_DY;
        if (scene.keyboard().isKeyDown(KeyEvent.VK_UP))     tmpVal -= DEFAULT_DY;
        if ((tmpPos + Player.RADIUS >= HEIGHT && tmpVal > 0) || (tmpPos - Player.RADIUS <= 0 && tmpVal < 0)) {
            tmpVal = 0;
        }
        game.player.setDy(tmpVal);

        tmpVal = drift.getDx();
        tmpPos = game.player.getX();
        if (scene.keyboard().isKeyDown(KeyEvent.VK_RIGHT))  tmpVal += DEFAULT_DX;
        if (scene.keyboard().isKeyDown(KeyEvent.VK_LEFT))   tmpVal -= DEFAULT_DX;
        if ((tmpPos + Player.RADIUS >= GAME_BORDER && tmpVal > 0) || (tmpPos - Player.RADIUS <= 0 && tmpVal < 0)) {
            tmpVal = 0;
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
        if (ignoreDamage) return true;

        final float px = game.player.getX();
        final float py = game.player.getY();

        final boolean shieldActive = game.player.shield.isActive();
        final float sx = game.player.shield.getX();
        final float sy = game.player.shield.getY();
        final float sr = game.player.shield.getR();

        for (int i = 0; i < game.enemyBullets.size(); ++i) {
            final IBullet bullet = game.enemyBullets.get(i);

            if (shieldActive && bullet.collidesWith(sx, sy, sr)) {
                game.enemyBullets.remove(i);
                if (--i < -1) break;
                // bullet is already destroyed, start collision testing for next bullet
                continue;
            }

            if (!game.player.isRespawnShieldActive() && bullet.collidesWith(px, py, Player.COLLISION_RADIUS)) {
                if (game.player.changeHpBy(-1) <= 0) {
                    // Goto title scene
                    scene.switchToScene(0);
                    return true;
                }
                game.player.activateRespawnShield();
                game.enemyBullets.remove(i);
                if (--i < -1) break;
            }
        }

        enemy_loop:
        for (int i = 0; i < game.enemies.size(); ++i) {
            final IEnemy enemy = game.enemies.get(i);

            // If radius is less than zero, it cannot collide with anything, so skip iteration
            final float er = enemy.getR();
            if (er < 0) continue;

            final float ex = enemy.getX();
            final float ey = enemy.getY();

            for (int j = 0; j < game.playerBullets.size(); ++j) {
                final IBullet bullet = game.playerBullets.get(j);
                if (bullet.collidesWith(ex, ey, er)) {
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

            if (!game.player.isRespawnShieldActive() && enemy.collidesWith(px, py, Player.COLLISION_RADIUS)) {
                if (game.player.changeHpBy(-1) <= 0) {
                    // Goto title scene
                    scene.switchToScene(0);
                    return true;
                }
                game.player.activateRespawnShield();
                if (enemy.changeHp(-1) <= 0) {
                    game.enemies.remove(i);
                    game.changeScore(enemy.getScore());
                    if (--i < -1) break;
                }
            }
        }

        return true;
    }

    public static void drawDialog(IGraphics g, String speaker, String[] msg) {
        // Draw msgbox border
        g.setColor(Color.white);
        g.fillRect(12, HEIGHT - 200, GAME_BORDER - 12, HEIGHT - 12);
        // Draw grey area
        g.setColor(Color.gray);
        g.fillRect(20, HEIGHT - 192, GAME_BORDER - 20, HEIGHT - 20);
        // Draw name inside msgbox
        g.setColor(Color.white);
        g.setFont(TitleScene.OPTION_FONT);
        g.drawString(speaker, 28, HEIGHT - 162);
        // Draw message inside msgbox
        g.setFont(SANS_FONT);
        for (int i = 0; i < msg.length; ++i) {
            g.drawString(msg[i], 28, HEIGHT - 142 + i * (SANS_FONT.getSize()) + 10);
        }
        // Draw footer
        g.drawString("Press Enter to continue", GAME_BORDER - 180, HEIGHT - 26);
    }

    public abstract boolean postUpdate(float dt);
}
