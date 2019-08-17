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

package org.atoiks.games.nappou2.levels.tutorial;

import java.awt.Color;

import javax.sound.sampled.Clip;

import org.atoiks.games.framework2d.Input;
import org.atoiks.games.framework2d.KeyCode;
import org.atoiks.games.framework2d.IGraphics;
import org.atoiks.games.framework2d.ResourceManager;

import org.atoiks.games.framework2d.resource.Font;

import org.atoiks.games.nappou2.SaveData;
import org.atoiks.games.nappou2.GameConfig;

import org.atoiks.games.nappou2.entities.Game;

import org.atoiks.games.nappou2.entities.shield.Shield;
import org.atoiks.games.nappou2.entities.shield.CounterBasedShield;

import org.atoiks.games.nappou2.levels.LevelState;
import org.atoiks.games.nappou2.levels.LevelContext;

import static org.atoiks.games.nappou2.scenes.GameLevelScene.HEIGHT;
import static org.atoiks.games.nappou2.scenes.GameLevelScene.GAME_BORDER;

public final class Preface implements LevelState {

    private static final long serialVersionUID = 4928495316273389216L;

    private final LevelState nextState;

    private transient Font font16;
    private transient Font font30;

    private transient String[][] infoMsg;

    // Current hack to make sure you cannot enable challenge mode
    // part way through the game play via continue!
    private boolean shouldSaveChallengeMode = true;

    public Preface(LevelState nextState) {
        this.nextState = nextState;
    }

    @Override
    public void restore(final LevelContext ctx) {
        final Game game = ctx.getGame();
        game.player.setPosition(GAME_BORDER / 2, HEIGHT / 6 * 5);
        game.player.getHpCounter().restoreTo(1);
        game.player.getScoreCounter().reset();

        final Shield shield = game.player.getShield();
        if (shield instanceof CounterBasedShield) {
            ((CounterBasedShield) shield).resetCounter();
        }
    }

    @Override
    public void enter(final LevelContext ctx) {
        final Font fnt = ResourceManager.get("/Logisoso.ttf");
        this.font16 = fnt.deriveSize(16f);
        this.font30 = fnt.deriveSize(30f);

        ctx.clearMessage();

        final GameConfig cfg = ResourceManager.get("./game.cfg");
        final SaveData saveData = ResourceManager.get("./saves.dat");

        // Only save the challenge-mode flag when we first enter. Any
        // other time, we do not read from that flag directly since
        // the user could have quit the game, change it in settings,
        // and restore it (which at this point, the value is
        // inconsistent and this can cause incorrect hp restores!)
        if (this.shouldSaveChallengeMode) {
            this.shouldSaveChallengeMode = false;
            saveData.setCheckpoint(this, cfg.challengeMode);
        } else {
            saveData.setCheckpoint(this);
        }

        if (cfg.bgm) {
            final Clip bgm = ResourceManager.get("/music/Awakening.wav");
            bgm.setMicrosecondPosition(0);
            bgm.start();
            bgm.loop(Clip.LOOP_CONTINUOUSLY);
        }

        this.infoMsg = cfg.keymap.getInfoMessage();
    }

    @Override
    public void exit() {
        ResourceManager.<Clip>get("/music/Awakening.wav").stop();
    }

    @Override
    public void renderBackground(final IGraphics g) {
        g.setColor(Color.white);

        this.font30.renderText(g, "Controls", 25, 70);

        for (int i = 0; i < this.infoMsg.length; ++i) {
            final float h = 94 + i * (this.font16.getSize() + 5);
            this.font16.renderText(g, this.infoMsg[i][0], 40, h);
            this.font16.renderText(g, this.infoMsg[i][1], 160, h);
        }

        this.font16.renderText(g, "Survive the void", 25, 550);
        this.font16.renderText(g, "Press Enter to continue", 25, 580);
    }

    @Override
    public void updateLevel(final LevelContext ctx, final float dt) {
        if (Input.isKeyPressed(KeyCode.KEY_ENTER)) {
            ctx.setState(new SingleShotWave(this.nextState));
            return;
        }
    }
}
