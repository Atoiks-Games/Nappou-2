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

package org.atoiks.games.nappou2.scenes;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.KeyEvent;

import org.atoiks.games.framework2d.Input;
import org.atoiks.games.framework2d.IGraphics;
import org.atoiks.games.framework2d.SceneManager;
import org.atoiks.games.framework2d.ResourceManager;

import org.atoiks.games.nappou2.Drifter;
import org.atoiks.games.nappou2.Difficulty;

import org.atoiks.games.nappou2.entities.Game;
import org.atoiks.games.nappou2.entities.Player;
import org.atoiks.games.nappou2.entities.Message;

import org.atoiks.games.nappou2.entities.bullet.factory.PointBulletInfo;

public abstract class AbstractGameScene extends CenteringScene {

    public static final int WIDTH = 900;
    public static final int HEIGHT = 600;
    public static final int GAME_BORDER = 750;

    public static final float DEFAULT_DX = 300f;
    public static final float DEFAULT_DY = 300f;

    private static final PointBulletInfo PLAYER_BULLET_INFO = new PointBulletInfo(5, DEFAULT_DY * 4.5f);
    private static final float MIN_FIRE_DELAY = 0.2f;

    private final PauseOverlay pauseOverlay = new PauseOverlay();
    private final StatusOverlay statusOverlay = new StatusOverlay();
    private final DialogOverlay dialogOverlay = new DialogOverlay();

    protected final Game game = new Game();
    protected final Drifter drift = new Drifter();

    protected Difficulty difficulty;

    private boolean skipPlayerUpdate;
    private float playerFireLimiter;

    public final int sceneId;

    protected AbstractGameScene(int id) {
        sceneId = id;
    }

    protected final void disableDamage() {
        game.player.setIgnoreHpChange(true);
    }

    protected final void enableDamage() {
        game.player.setIgnoreHpChange(false);
    }

    protected final void shouldSkipPlayerUpdate(final boolean flag) {
        skipPlayerUpdate = flag;
    }

    protected final void clearMessage() {
        dialogOverlay.clearMessage();
    }

    protected final void displayMessage(final Message msg) {
        dialogOverlay.displayMessage(msg);
    }

    @Override
    public void init() {
        final Image hpImg = (Image) scene.resources().get("hp.png");
        game.clipGameBorder(GAME_BORDER, HEIGHT);

        pauseOverlay.attachSceneManager(this.scene);
        statusOverlay.attachGame(this.game);
        statusOverlay.attachHpImg(hpImg);
        dialogOverlay.attachSceneManager(this.scene);
    }

    @Override
    public void enter(String prevSceneId) {
        difficulty = (Difficulty) SceneManager.resources().get("difficulty");

        playerFireLimiter = 0f;
        shouldSkipPlayerUpdate(false);
    }

    @Override
    public void leave() {
        SceneManager.resources().put("level.id", sceneId);
        SceneManager.resources().put("level.score", game.getScore());
        game.cleanup();
    }

    public void renderBackground(final IGraphics g) {
        g.setColor(Color.black);
        g.fillRect(0, 0, GAME_BORDER, HEIGHT);
    }

    @Override
    public final void render(final IGraphics g) {
        // setup aspect ratio
        super.render(g);

        // The bullet-curtain part
        renderBackground(g);
        game.render(g);

        statusOverlay.render(g);
        dialogOverlay.render(g);

        pauseOverlay.render(g);

        g.setColor(StatusOverlay.BACKGROUND_COLOR);
        drawSideBlinder(g);
    }

    @Override
    public boolean update(final float dt) {
        if (pauseOverlay.isEnabled()) {
            pauseOverlay.update(dt);
        } else {
            levelUpdate(dt);
        }
        return true;
    }

    private void levelUpdate(final float dt) {
        if (Input.isKeyPressed(KeyEvent.VK_ESCAPE)) {
            pauseOverlay.enable();
            return;
        }

        // And strangely enough, if you have read the next comment
        // already, player updates were not part of the *5
        // things*, so it uses the un-partitioned dt.
        processPlayerTime(dt);

        // At some point, the update sequence used to be split up
        // so each update frame only did one thing. It was divided
        // into 5 things.
        final float dtDiv5 = dt / 5;
        final float driftX = dtDiv5 * drift.getDx();
        final float driftY = dtDiv5 * drift.getDy();
        game.updateEnemySpawner(dtDiv5);
        game.updateEnemyPosition(dtDiv5, driftX, driftY);
        game.updateEnemyBulletPosition(dtDiv5, driftX, driftY);
        game.updatePlayerBulletPosition(dtDiv5, driftX, driftY);

        game.performCollisionCheck();
        if (game.player.getHp() <= 0) {
            scene.switchToScene("TitleScene");
            return;
        }

        postUpdate(dtDiv5);
    }

    private void processPlayerTime(final float dt) {
        if (skipPlayerUpdate) {
            return;
        }

        processPlayerMovement(dt);
        processPlayerAttack(dt);
        processPlayerShield(dt);
    }

    private void processPlayerMovement(final float dt) {
        final Player player = game.player;

        // Calculate player's unscaled speed in y
        float tmpVal = drift.getDy();
        float tmpPos = player.getY();
        if (Input.isKeyDown(KeyEvent.VK_DOWN))  tmpVal += DEFAULT_DY;
        if (Input.isKeyDown(KeyEvent.VK_UP))    tmpVal -= DEFAULT_DY;
        if ((tmpPos + Player.RADIUS >= HEIGHT && tmpVal > 0) || (tmpPos - Player.RADIUS <= 0 && tmpVal < 0)) {
            tmpVal = 0;
        }
        player.setDy(tmpVal);

        // Calculate player's unscaled speed in x
        tmpVal = drift.getDx();
        tmpPos = player.getX();
        if (Input.isKeyDown(KeyEvent.VK_RIGHT)) tmpVal += DEFAULT_DX;
        if (Input.isKeyDown(KeyEvent.VK_LEFT))  tmpVal -= DEFAULT_DX;
        if ((tmpPos + Player.RADIUS >= GAME_BORDER && tmpVal > 0) || (tmpPos - Player.RADIUS <= 0 && tmpVal < 0)) {
            tmpVal = 0;
        }
        player.setDx(tmpVal);

        player.setSpeedScale(Input.isKeyDown(KeyEvent.VK_SHIFT) ? 0.55f : 1);
        player.update(dt);
    }

    private void processPlayerAttack(final float dt) {
        if ((playerFireLimiter -= dt) <= 0 && Input.isKeyDown(KeyEvent.VK_Z)) {
            final Player player = game.player;
            game.addPlayerBullet(PLAYER_BULLET_INFO.createBullet(
                    player.getX(), player.getY(), (float) (-Math.PI / 2)));
            playerFireLimiter = MIN_FIRE_DELAY;
        }
    }

    private void processPlayerShield(final float dt) {
        if (Input.isKeyPressed(KeyEvent.VK_X)) {
            game.player.shield.activate();
        }
    }

    public abstract boolean postUpdate(float dt);
}
