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
import org.atoiks.games.nappou2.Vector2;

import org.atoiks.games.nappou2.levels.NullState;
import org.atoiks.games.nappou2.levels.ILevelState;
import org.atoiks.games.nappou2.levels.ILevelContext;

import org.atoiks.games.nappou2.entities.Game;
import org.atoiks.games.nappou2.entities.Player;
import org.atoiks.games.nappou2.entities.Message;

import org.atoiks.games.nappou2.entities.bullet.factory.BulletFactory;
import org.atoiks.games.nappou2.entities.bullet.factory.PathwayPointBulletInfo;

public final class GameLevelScene extends CenteringScene implements ILevelContext {

    public static final int WIDTH = 900;
    public static final int HEIGHT = 600;
    public static final int GAME_BORDER = 750;

    public static final float DEFAULT_DX = 300f;
    public static final float DEFAULT_DY = 300f;

    private static final BulletFactory PLAYER_BULLET_INFO = PathwayPointBulletInfo.createLegacyPointBullet(5, DEFAULT_DY * 4.5f);
    private static final float MIN_FIRE_DELAY = 0.2f;

    private final PauseOverlay pauseOverlay = new PauseOverlay();
    private final StatusOverlay statusOverlay = new StatusOverlay();
    private final DialogOverlay dialogOverlay = new DialogOverlay();

    private final Game game = new Game();
    private final Drifter drift = new Drifter();

    private boolean skipPlayerUpdate;
    private float playerFireLimiter;

    private ILevelState state = NullState.INSTANCE;

    @Override
    public void setState(ILevelState nextState) {
        this.state.exit();

        if (nextState == null) {
            nextState = NullState.INSTANCE;
        }

        nextState.enter(this);
        this.state = nextState;
    }

    @Override
    public Game getGame() {
        return game;
    }

    @Override
    public Drifter getDrifter() {
        return drift;
    }

    @Override
    public void shouldSkipPlayerUpdate(final boolean flag) {
        skipPlayerUpdate = flag;
    }

    @Override
    public void clearMessage() {
        dialogOverlay.clearMessage();
    }

    @Override
    public void displayMessage(final Message msg) {
        dialogOverlay.displayMessage(msg);
    }

    @Override
    public void init() {
        final Image hpImg = ResourceManager.get("/image/hp.png");
        game.clipGameBorder(GAME_BORDER, HEIGHT);

        statusOverlay.attachGame(this.game);
        statusOverlay.attachHpImg(hpImg);
    }

    @Override
    public void enter(String prevSceneId) {
        playerFireLimiter = 0f;
        shouldSkipPlayerUpdate(false);

        setState((ILevelState) SceneManager.resources().get("level.state"));
    }

    @Override
    public void leave() {
        // Make sure exit is called
        setState(NullState.INSTANCE);
        game.cleanup();
    }

    public void renderBackground(final IGraphics g) {
        g.setColor(Color.black);
        g.fillRect(0, 0, GAME_BORDER, HEIGHT);

        state.renderBackground(g);
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
        drift.update(dtDiv5);
        final Vector2 disp = drift.getDrift().mul(dtDiv5);
        game.updateEnemySpawner(dtDiv5);
        game.updateEnemyPosition(dtDiv5, disp);
        game.updateEnemyBulletPosition(dtDiv5, disp);
        game.updatePlayerBulletPosition(dtDiv5, disp);

        game.performCollisionCheck();
        if (game.player.getHp() <= 0) {
            SceneManager.switchToScene("TitleScene");
            return;
        }

        state.updateLevel(this, dt);
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

        final Vector2 disp = drift.getDrift();

        // Calculate player's unscaled speed in y
        float tmpDy = disp.getY();
        final float tmpY = player.getY();
        if (Input.isKeyDown(KeyEvent.VK_DOWN))  tmpDy += DEFAULT_DY;
        if (Input.isKeyDown(KeyEvent.VK_UP))    tmpDy -= DEFAULT_DY;
        if ((tmpY + Player.RADIUS >= HEIGHT && tmpDy > 0) || (tmpY - Player.RADIUS <= 0 && tmpDy < 0)) {
            tmpDy = 0;
        }

        // Calculate player's unscaled speed in x
        float tmpDx = disp.getX();
        final float tmpX = player.getX();
        if (Input.isKeyDown(KeyEvent.VK_RIGHT)) tmpDx += DEFAULT_DX;
        if (Input.isKeyDown(KeyEvent.VK_LEFT))  tmpDx -= DEFAULT_DX;
        if ((tmpX + Player.RADIUS >= GAME_BORDER && tmpDx > 0) || (tmpX - Player.RADIUS <= 0 && tmpDx < 0)) {
            tmpDx = 0;
        }

        player.setVelocity(new Vector2(tmpDx, tmpDy));

        player.setSpeedScale(Input.isKeyDown(KeyEvent.VK_SHIFT) ? 0.55f : 1);
        player.update(dt);
    }

    private void processPlayerAttack(final float dt) {
        if ((playerFireLimiter -= dt) <= 0 && Input.isKeyDown(KeyEvent.VK_Z)) {
            final Player player = game.player;
            game.addPlayerBullet(PLAYER_BULLET_INFO.createBullet(player.getPosition(), (float) (-Math.PI / 2)));
            playerFireLimiter = MIN_FIRE_DELAY;
        }
    }

    private void processPlayerShield(final float dt) {
        if (Input.isKeyPressed(KeyEvent.VK_X)) {
            game.player.shield.activate();
        }
    }
}
