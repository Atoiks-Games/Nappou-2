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

import org.atoiks.games.framework2d.Input;
import org.atoiks.games.framework2d.KeyCode;
import org.atoiks.games.framework2d.IGraphics;
import org.atoiks.games.framework2d.SceneManager;
import org.atoiks.games.framework2d.ResourceManager;

import org.atoiks.games.framework2d.resource.Font;

import org.atoiks.games.nappou2.levels.NullState;
import org.atoiks.games.nappou2.levels.LevelState;
import org.atoiks.games.nappou2.levels.LevelContext;

import org.atoiks.games.nappou2.SaveData;
import org.atoiks.games.nappou2.GameConfig;

import org.atoiks.games.nappou2.entities.Game;
import org.atoiks.games.nappou2.entities.Border;
import org.atoiks.games.nappou2.entities.Player;
import org.atoiks.games.nappou2.entities.Message;
import org.atoiks.games.nappou2.entities.PlayerController;

public final class GameLevelScene extends CenteringScene implements LevelContext {

    public static final int WIDTH = 900;
    public static final int HEIGHT = 600;
    public static final int GAME_BORDER = 750;

    private final Border border = new Border(GAME_BORDER, HEIGHT);
    private final Game game;
    private final PlayerController playerController;

    private final PauseOverlay pauseOverlay;
    private final DialogOverlay dialogOverlay;
    private final StatusOverlay statusOverlay;

    private LevelState state = NullState.INSTANCE;

    public GameLevelScene(final Player player) {
        final Font fnt = ResourceManager.get("/Logisoso.ttf");
        final GameConfig config = ResourceManager.get("./game.cfg");

        this.game = new Game(player, this.border);
        this.playerController = new PlayerController(this.game, this.border, config.keymap);

        this.pauseOverlay = new PauseOverlay(fnt, this);
        this.dialogOverlay = new DialogOverlay(fnt);
        this.statusOverlay = new StatusOverlay(fnt, player);

        this.reset();
    }

    @Override
    public void setState(LevelState nextState) {
        this.state.exit();

        if (nextState == null) {
            nextState = NullState.INSTANCE;
        }

        nextState.enter(this);
        this.state = nextState;
    }

    @Override
    public LevelState getState() {
        return this.state;
    }

    @Override
    public void reset() {
        this.enableDamage();
        this.shouldSkipPlayerUpdate(false);
        this.clearMessage();
        this.game.cleanup();
    }

    @Override
    public Game getGame() {
        return game;
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
        } else if (Input.isKeyPressed(KeyCode.KEY_ESCAPE)) {
            pauseOverlay.enable();
        } else {
            levelUpdate(dt);
        }
        return true;
    }

    private void levelUpdate(final float dt) {
        // At some point, the update sequence was split up so
        // each update frame only did one thing. It was divided
        // into 5 things.
        this.game.update(dt / 5);

        // And strangely enough, player updates were not part of
        // the *5 things*, so it uses the un-partitioned dt.
        this.playerController.update(dt);

        this.game.performCollisionCheck();
        if (this.game.shouldAbort()) {
            this.performAbort();
            return;
        }

        state.updateLevel(this, dt);
    }

    private void performAbort() {
        // Try to restart at the most recent checkpoint
        // before sending player back to the title scene

        final SaveData saves = ResourceManager.get("./saves.dat");
        if (saves.getCheckpoint() instanceof NullState) {
            SceneManager.swapScene(new TitleScene());
        } else {
            unwindAndStartLevel(new Player(saves.getShieldCopy()), saves.getCheckpoint(), false);
        }
    }

    /* package */ static void unwindAndStartLevel(Player player, LevelState state, boolean callRestore) {
        final GameLevelScene next = new GameLevelScene(player);
        SceneManager.unwindToScene(next);
        if (callRestore) {
            state.restore(next);
        } else {
            state.respawn(next);
        }
        next.setState(state);
    }
}
