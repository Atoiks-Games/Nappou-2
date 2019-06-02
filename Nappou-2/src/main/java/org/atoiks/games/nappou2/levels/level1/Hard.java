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

package org.atoiks.games.nappou2.levels.level1;

import java.awt.event.KeyEvent;

import javax.sound.sampled.Clip;

import org.atoiks.games.framework2d.Input;
import org.atoiks.games.framework2d.SceneManager;
import org.atoiks.games.framework2d.ResourceManager;

import org.atoiks.games.nappou2.Drifter;
import org.atoiks.games.nappou2.Difficulty;
import org.atoiks.games.nappou2.GameConfig;

import org.atoiks.games.nappou2.levels.ILevelState;
import org.atoiks.games.nappou2.levels.ILevelContext;
import org.atoiks.games.nappou2.levels.SaveScoreState;

import org.atoiks.games.nappou2.entities.Game;
import org.atoiks.games.nappou2.entities.Player;

import org.atoiks.games.nappou2.entities.enemy.*;

import org.atoiks.games.nappou2.entities.shield.IShield;

import static org.atoiks.games.nappou2.Utils.mb1;
import static org.atoiks.games.nappou2.Utils.altMb1;
import static org.atoiks.games.nappou2.Utils.miniBomberEnemy;
import static org.atoiks.games.nappou2.Utils.singleShotEnemy;
import static org.atoiks.games.nappou2.Utils.circularPathEnemy;
import static org.atoiks.games.nappou2.Utils.advancedMiniBomberEnemy;
import static org.atoiks.games.nappou2.Utils.tweenRadialGroupPattern;

import static org.atoiks.games.nappou2.levels.level1.Data.*;

import static org.atoiks.games.nappou2.scenes.RefittedGameScene.HEIGHT;
import static org.atoiks.games.nappou2.scenes.RefittedGameScene.GAME_BORDER;

public final class Hard implements ILevelState {

    private static final SaveScoreState EXIT_STATE = new SaveScoreState(0, Difficulty.HARD);

    private int cycles;
    private int wave;
    private int phase;

    private int prebossMsgPhase;

    private Clip bgm;

    @Override
    public void enter(final ILevelContext ctx) {
        ctx.getDrifter().clampSpeed(0, 0, 0, 0);
        ctx.clearMessage();

        this.cycles = 0;
        this.wave = 0;
        this.phase = 0;

        this.prebossMsgPhase = -1;

        final GameConfig cfg = ResourceManager.get("./game.cfg");

        final Game game = ctx.getGame();
        game.player = new Player(GAME_BORDER / 2, HEIGHT / 6 * 5, (IShield) SceneManager.resources().get("shield"));
        game.player.setHp(cfg.challengeMode ? 1 : 5);
        game.setScore(0);

        bgm = ResourceManager.get("/music/Level_One.wav");
        if (cfg.bgm) {
            bgm.setMicrosecondPosition(0);
            bgm.start();
            bgm.setLoopPoints(LEVEL_LOOP, -1);
            bgm.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    @Override
    public void exit() {
        bgm.stop();
    }

    private boolean displayNextPrebossDialogue(final ILevelContext ctx) {
        if (++prebossMsgPhase < PREBOSS_MSG.length) {
            ctx.displayMessage(PREBOSS_MSG[prebossMsgPhase]);
            return true;
        }

        // no more dialogs, must clear out player portrait
        ctx.clearMessage();
        return false;
    }

    @Override
    public void updateLevel(final ILevelContext ctx, final float dt) {
        final Game game = ctx.getGame();
        final Drifter drift = ctx.getDrifter();

        ++cycles;
        switch (wave) {
            case 0:
                switch (cycles) {
                    case 20:
                    case 40:
                    case 60:
                    case 80:
                    case 100:
                    case 120:
                    case 140:
                    case 160:
                    case 180:
                    case 200:
                        int k = cycles / 4 * 5;
                        game.addEnemy(singleShotEnemy(1, 300 - k, -10, 8, false));
                        game.addEnemy(singleShotEnemy(1, 450 + k, -10, 8, false));
                        break;
                    case 300:
                    case 400:
                    case 500:
                    case 600:
                        game.addEnemy(new DropEnemy(1, -10, 10, 8, false));
                        game.addEnemy(new DropEnemy(1, 760, 10, 8, false));
                        game.addEnemy(circularPathEnemy(1, 650, -1, 8, 100, 1, 1, 0, 100));
                        game.addEnemy(circularPathEnemy(1, 100, -1, 8, 100, -1, 1, 2, 100));
                        break;
                    case 620:
                    case 640:
                    case 660:
                    case 680:
                    case 700:
                    case 720:
                    case 740:
                    case 760:
                    case 780:
                    case 800:
                        k = (cycles / 4 / 5 - 30) * 25;
                        game.addEnemy(singleShotEnemy(1, 300 - k, -10, 8, false));
                        game.addEnemy(singleShotEnemy(1, 450 + k, -10, 8, false));
                        break;
                }
                if (cycles > 800 && game.noMoreEnemies()) {
                    wave++;
                    cycles = 0;
                }
                break;
            case 1:
                switch (cycles) {
                    case 40:
                        game.addEnemy(new DropEnemy(1, 30, -10, 8, false));
                        game.addEnemy(new DropEnemy(1, 720, -10, 8, false));
                        game.addEnemy(new DropEnemy(1, 50, -10, 8, false));
                        game.addEnemy(new DropEnemy(1, 700, -10, 8, false));
                        break;
                    case 1880: // FALLTHROUGH
                        game.addEnemy(new DropEnemy(1, 30, -10, 8, false));
                        game.addEnemy(new DropEnemy(1, 720, -10, 8, false));
                        game.addEnemy(new DropEnemy(1, 50, -10, 8, false));
                        game.addEnemy(new DropEnemy(1, 700, -10, 8, false));
                    case 80:
                    case 280:
                    case 480:
                    case 680:
                    case 880:
                    case 1080:
                    case 1280:
                    case 1480:
                    case 1680:
                        final int offset = (cycles / 4 / 5 - 4) / 5;
                        game.addEnemy(miniBomberEnemy(1, w1eX[offset + 0], w1eY[offset + 0], 8, 1, 100));
                        game.addEnemy(miniBomberEnemy(1, w1eX[offset + 1], w1eY[offset + 1], 8, -1, 100));
                        break;
                }
                if (cycles > 1880 && game.noMoreEnemies()) {
                    wave++;
                    cycles = 0;
                }
                break;
            case 2:
                switch (cycles) {
                    case 40:
                        game.addEnemy(mb1(10, 225, -10, 20));
                        game.addEnemy(mb1(10, 525, -10, 20));
                        game.addEnemy(mb1(10, 375, -10, 20));
                        break;
                    case 80:
                    case 280:
                    case 480:
                    case 680:
                    case 880:
                    case 1080:
                    case 1280:
                    case 1480:
                    case 1680:
                        final int offset = (cycles / 4 / 5 - 4) / 5;
                        game.addEnemy(advancedMiniBomberEnemy(1, w1eX[offset + 0], w1eY[offset + 0], 8, 1, w1eS[offset + 0]));
                        game.addEnemy(advancedMiniBomberEnemy(1, w1eX[offset + 1], w1eY[offset + 1], 8, -1, w1eS[offset + 1]));
                        break;
                }
                if (cycles > 1680 && game.noMoreEnemies()) {
                    wave++;
                    cycles = 0;
                }
                break;
            case 3:
                switch (cycles) {
                    case 40:
                        game.addEnemy(altMb1(10, 375, -10, 20));
                        break;
                    case 80:
                        game.addEnemy(circularPathEnemy(1, 750, 50, 8, 100, 1, 0.25f, 1, 100));
                        game.addEnemy(circularPathEnemy(1, 0, 50, 8, 100, -1, 0.25f, 3, 100));
                        break;
                    case 1080:
                        game.addEnemy(altMb1(10, 375, -10, 20));
                        game.addEnemy(circularPathEnemy(1, 750, 50, 8, 100, 1, 0.25f, 1, 100));
                        game.addEnemy(circularPathEnemy(1, 0, 50, 8, 100, -1, 0.25f, 3, 100));
                        game.addEnemy(circularPathEnemy(1, 750, 0, 8, 100, 1, 0.25f, 1, 100));
                        game.addEnemy(circularPathEnemy(1, 0, 0, 8, 100, -1, 0.25f, 3, 100));
                        game.addEnemy(circularPathEnemy(1, 750, 600, 8, 100, -1, 0.25f, 1, 100));
                        game.addEnemy(circularPathEnemy(1, 0, 600, 8, 100, 1, 0.25f, 3, 100));
                        break;
                    case 2080:
                        game.addEnemy(altMb1(10, 375, -10, 20));
                        game.addEnemy(circularPathEnemy(1, 750, 0, 8, 100, 1, 0.25f, 1, 100));
                        game.addEnemy(circularPathEnemy(1, 0, 0, 8, 100, -1, 0.25f, 3, 100));
                        game.addEnemy(circularPathEnemy(1, 750, 600, 8, 100, -1, 0.25f, 1, 100));
                        game.addEnemy(circularPathEnemy(1, 0, 600, 8, 100, 1, 0.25f, 3, 100));
                        break;
                }
                if (cycles > 2080 && game.noMoreEnemies()) {
                    wave++;
                    cycles = 0;
                }
                break;
            case 4:
                switch (cycles) {
                    case 40:
                        tweenRadialGroupPattern(game, w4eX, w4eR);
                        break;
                    case 240:
                    case 440:
                    case 640:
                    case 840:
                    case 1040:
                    case 1240:
                    case 1440:
                    case 1640:
                    case 1840:
                        game.addEnemy(new DropEnemy(1, 30, -10, 8, false));
                        game.addEnemy(new DropEnemy(1, 720, -10, 8, false));
                        game.addEnemy(new DropEnemy(1, 100, -10, 8, false));
                        game.addEnemy(new DropEnemy(1, 650, -10, 8, false));
                        break;
                }
                if (cycles > 1840 && game.noMoreEnemies()) {
                    wave++;
                    cycles = 0;
                    bgm.stop();
                    ctx.disableDamage();
                    ctx.shouldSkipPlayerUpdate(true);
                    game.clearBullets();
                    bgm = ResourceManager.get("/music/Level_One_Boss.wav");
                    if (ResourceManager.<GameConfig>get("./game.cfg").bgm) {
                        bgm.setMicrosecondPosition(0);
                        bgm.start();
                        bgm.setLoopPoints(BOSS_LOOP, -1);
                        bgm.loop(Clip.LOOP_CONTINUOUSLY);
                    }
                    displayNextPrebossDialogue(ctx);
                }
                break;
            case 5:
                if (Input.isKeyPressed(KeyEvent.VK_ENTER)) {
                    if (!displayNextPrebossDialogue(ctx)) {
                        wave++;
                        ctx.enableDamage();
                        ctx.shouldSkipPlayerUpdate(false);
                        cycles = 0;
                        game.addEnemy(new Level1Hard(300, 375, -10, 20));
                        drift.accelY = -20;
                        drift.accelX = 20;
                        drift.clampDx(0, 200);
                    }
                }
                break;
            case 6:
                if (cycles % 4000 == 0) {
                    switch (++phase) {
                        case 0:
                            drift.accelY = -20;
                            drift.accelX = 20;
                            drift.clampDx(0, 200);
                            break;
                        case 1:
                            drift.accelX = -20;
                            drift.accelY = 20;
                            drift.clampDy(0,200);
                            break;
                        case 2:
                            drift.accelY = -20;
                            drift.clampDx(-200,0);
                            break;
                        case 3:
                            drift.accelX = 20;
                            drift.clampDy(-200,0);
                            break;
                    }
                }
                if (cycles > 40 && game.noMoreEnemies()) {
                    bgm.stop();
                    ctx.disableDamage();
                    ctx.shouldSkipPlayerUpdate(true);
                    game.clearBullets();
                    ctx.displayMessage(POSTBOSS_MSG);
                    if (Input.isKeyPressed(KeyEvent.VK_ENTER)) {
                        ctx.setState(EXIT_STATE);
                        return;
                    }
                }
                break;
        }
    }
}