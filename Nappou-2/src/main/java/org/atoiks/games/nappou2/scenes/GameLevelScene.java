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

import java.awt.Font;
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
import org.atoiks.games.nappou2.entities.Border;
import org.atoiks.games.nappou2.entities.Player;
import org.atoiks.games.nappou2.entities.Message;
import org.atoiks.games.nappou2.entities.PlayerController;

public final class GameLevelScene extends CenteringScene implements ILevelContext {

    public static final int WIDTH = 900;
    public static final int HEIGHT = 600;
    public static final int GAME_BORDER = 750;

    private final Drifter drift = new Drifter();
    private final Border border = new Border(GAME_BORDER, HEIGHT);
    private final Game game;
    private final PlayerController playerController;

    private final PauseOverlay pauseOverlay;
    private final StatusOverlay statusOverlay;
    private final DialogOverlay dialogOverlay;

    private ILevelState state = NullState.INSTANCE;

    public GameLevelScene(final Player player) {
        this.game = new Game(player, this.border);
        this.playerController = new PlayerController(this.game, this.border);

        final Image hpImg = ResourceManager.get("/image/hp.png");
        final Font fnt = ResourceManager.get("/Logisoso.ttf");

        this.pauseOverlay = new PauseOverlay(fnt);
        this.dialogOverlay = new DialogOverlay(fnt);
        this.statusOverlay = new StatusOverlay(fnt, this.game.player, hpImg);
    }

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
        this.playerController.setIgnoreUpdate(flag);
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

        // At some point, the update sequence was split up so
        // each update frame only did one thing. It was divided
        // into 5 things.
        final float dtDiv5 = dt / 5;
        drift.update(dtDiv5);
        final Vector2 driftVelocity = drift.getDrift();

        // And strangely enough, player updates were not part of
        // the *5 things*, so it uses the un-partitioned dt.
        this.playerController.update(dt, driftVelocity);

        game.update(dtDiv5, driftVelocity.mul(dtDiv5));

        if (game.player.getHpCounter().isOutOfHp()) {
            SceneManager.swapScene(new TitleScene());
            return;
        }

        state.updateLevel(this, dt);
    }
}
