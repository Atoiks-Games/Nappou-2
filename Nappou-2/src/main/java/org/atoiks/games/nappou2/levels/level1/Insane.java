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
import org.atoiks.games.nappou2.SaveData;
import org.atoiks.games.nappou2.Difficulty;
import org.atoiks.games.nappou2.GameConfig;

import org.atoiks.games.nappou2.levels.ILevelState;
import org.atoiks.games.nappou2.levels.ILevelContext;
import org.atoiks.games.nappou2.levels.SaveScoreState;

import org.atoiks.games.nappou2.entities.Game;
import org.atoiks.games.nappou2.entities.Player;

import org.atoiks.games.nappou2.entities.enemy.*;

import static org.atoiks.games.nappou2.Utils.mb1;
import static org.atoiks.games.nappou2.Utils.altMb1;
import static org.atoiks.games.nappou2.Utils.dropEnemy;
import static org.atoiks.games.nappou2.Utils.miniBomberEnemy;
import static org.atoiks.games.nappou2.Utils.singleShotEnemy;
import static org.atoiks.games.nappou2.Utils.circularPathEnemy;
import static org.atoiks.games.nappou2.Utils.advancedMiniBomberEnemy;
import static org.atoiks.games.nappou2.Utils.tweenRadialGroupPattern;

import static org.atoiks.games.nappou2.levels.level1.Data.*;

import static org.atoiks.games.nappou2.scenes.GameLevelScene.HEIGHT;
import static org.atoiks.games.nappou2.scenes.GameLevelScene.GAME_BORDER;

public final class Insane implements ILevelState {

    private int cycles;
    private int wave;

    private Clip bgm;

    @Override
    public void enter(final ILevelContext ctx) {
        ctx.getDrifter().clampSpeed(0, 0, 0, 0);
        ctx.clearMessage();

        this.cycles = 0;
        this.wave = 0;

        final GameConfig cfg = ResourceManager.get("./game.cfg");
        final SaveData saveData = ResourceManager.get("./saves.dat");

        final Game game = ctx.getGame();
        game.player = new Player(GAME_BORDER / 2, HEIGHT / 6 * 5, saveData.getShieldCopy());
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
                        game.addEnemy(singleShotEnemy(1, 300 - k, 610, 8, true));
                        game.addEnemy(singleShotEnemy(1, 450 + k, 610, 8, true));
                        game.addEnemy(singleShotEnemy(1, 300 - k, -10, 8, false));
                        game.addEnemy(singleShotEnemy(1, 450 + k, -10, 8, false));
                        break;
                    case 300:
                    case 400:
                    case 500:
                    case 600:
                        game.addEnemy(dropEnemy(1, -10, 10, 8, false));
                        game.addEnemy(dropEnemy(1, 760, 10, 8, false));
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
                        game.addEnemy(singleShotEnemy(1, 300 - k, 610, 8, true));
                        game.addEnemy(singleShotEnemy(1, 450 + k, 610, 8, true));
                        game.addEnemy(singleShotEnemy(1, 300 - k, -10, 8, false));
                        game.addEnemy(singleShotEnemy(1, 450 + k, -10, 8, false));
                        break;
                    case 900:
                    case 1000:
                    case 1100:
                    case 1200:
                        game.addEnemy(dropEnemy(1, -10, 10, 8, false));
                        game.addEnemy(dropEnemy(1, 760, 10, 8, false));
                        game.addEnemy(circularPathEnemy(1, 650, -1, 8, 100, 1, 1, 0, 100));
                        game.addEnemy(circularPathEnemy(1, 100, -1, 8, 100, -1, 1, 2, 100));
                        break;
                    case 1220:
                    case 1240:
                    case 1260:
                    case 1280:
                    case 1300:
                    case 1320:
                    case 1340:
                    case 1360:
                    case 1380:
                    case 1400:
                        k = (cycles / 4 / 5 - 40) * 25;
                        game.addEnemy(singleShotEnemy(1, 300 - k, 610, 8, true));
                        game.addEnemy(singleShotEnemy(1, 450 + k, 610, 8, true));
                        game.addEnemy(singleShotEnemy(1, 300 - k, -10, 8, false));
                        game.addEnemy(singleShotEnemy(1, 450 + k, -10, 8, false));
                        break;
                    case 1500:
                    case 1600:
                    case 1700:
                    case 1800:
                        game.addEnemy(dropEnemy(1, -10, 10, 8, false));
                        game.addEnemy(dropEnemy(1, 760, 10, 8, false));
                        game.addEnemy(circularPathEnemy(1, 650, -1, 8, 100, 1, 1, 0, 100));
                        game.addEnemy(circularPathEnemy(1, 100, -1, 8, 100, -1, 1, 2, 100));
                        break;
                }
                if (cycles > 1800 && game.noMoreEnemies()) {
                    wave++;
                    cycles = 0;
                }
                break;
            case 1:
                switch (cycles) {
                    case 40:
                    case 140:
                    case 240:
                    case 340:
                    case 440:
                    case 540:
                    case 640:
                    case 740:
                    case 840:
                    case 940:
                        game.addEnemy(dropEnemy(1, 30, -10, 8, false));
                        game.addEnemy(dropEnemy(1, 720, -10, 8, false));
                        game.addEnemy(dropEnemy(1, 50, 610, 8, true));
                        game.addEnemy(dropEnemy(1, 700, 610, 8, true));
                        final int offset = (cycles / 4 / 5 - 4) / 5;
                        game.addEnemy(advancedMiniBomberEnemy(1, w1eX[offset + 0], w1eY[offset + 0], 8, 1, 100));
                        game.addEnemy(advancedMiniBomberEnemy(1, w1eX[offset + 1], w1eY[offset + 1], 8, -1, 100));
                        break;
                }
                if (cycles > 940 && game.noMoreEnemies()) {
                    wave++;
                    cycles = 0;
                }
                break;
            case 2:
                switch (cycles) {
                    case 40:
                        game.addEnemy(mb1(10, 225, -10, 20));
                        game.addEnemy(mb1(10, 525, -10, 20));
                        game.addEnemy(altMb1(10, 375, -10, 20));
                        break;
                    case 80:
                    case 280:
                    case 380:
                    case 480:
                    case 580:
                    case 680:
                    case 780:
                    case 880:
                    case 980:
                        final int offset = (cycles / 4 / 5 - 4) / 5;
                        game.addEnemy(advancedMiniBomberEnemy(1, w1eX[offset + 0], w1eY[offset + 0], 8, 1, 100));
                        game.addEnemy(advancedMiniBomberEnemy(1, w1eX[offset + 1], w1eY[offset + 1], 8, -1, 100));
                        break;
                    case 1000:
                    case 1100:
                    case 1200:
                    case 1300:
                        game.addEnemy(altMb1(10, 375, -10, 20));
                        break;
                }
                if (cycles > 1300 && game.noMoreEnemies()) {
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
                        game.addEnemy(altMb1(10, 225, -10, 20));
                        game.addEnemy(altMb1(10, 525, -10, 20));
                        break;
                    case 1080:
                        game.addEnemy(altMb1(10, 375, -10, 20));
                        game.addEnemy(altMb1(10, 225, -10, 20));
                        game.addEnemy(altMb1(10, 525, -10, 20));
                        game.addEnemy(circularPathEnemy(1, 750, 50, 8, 100, 1, 0.25f, 1, 100));
                        game.addEnemy(circularPathEnemy(1, 0, 50, 8, 100, -1, 0.25f, 3, 100));
                        game.addEnemy(circularPathEnemy(1, 750, 0, 8, 100, 1, 0.25f, 1, 100));
                        game.addEnemy(circularPathEnemy(1, 0, 0, 8, 100, -1, 0.25f, 3, 100));
                        game.addEnemy(circularPathEnemy(1, 750, 600, 8, 100, -1, 0.25f, 1, 100));
                        game.addEnemy(circularPathEnemy(1, 0, 600, 8, 100, 1, 0.25f, 3, 100));
                        break;
                    case 2080:
                        game.addEnemy(altMb1(10, 375, -10, 20));
                        game.addEnemy(altMb1(10, 225, -10, 20));
                        game.addEnemy(altMb1(10, 525, -10, 20));
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
                    case 240:
                    case 440:
                    case 640:
                    case 840:
                    case 1040:
                    case 1240:
                    case 1440:
                    case 1640:
                    case 1840:
                        game.addEnemy(dropEnemy(1, 30, -10, 8, false));
                        game.addEnemy(dropEnemy(1, 100, -10, 8, false));
                        game.addEnemy(dropEnemy(1, 720, 610, 8, true));
                        game.addEnemy(dropEnemy(1, 650, 610, 8, true));
                        game.addEnemy(singleShotEnemy(1, 10, 610, 8, true));
                        game.addEnemy(singleShotEnemy(1, 740, -10, 8, false));
                        game.addEnemy(singleShotEnemy(1, 450, 610, 8, true));
                        game.addEnemy(singleShotEnemy(1, 300, -10, 8, false));
                        break;
                }
                if (cycles > 1840 && game.noMoreEnemies()) {
                    ctx.setState(new PrebossDialog(new InsaneBossWave()));
                    return;
                }
                break;
        }
    }
}

final class InsaneBossWave implements ILevelState {

    private static final SaveScoreState EXIT_STATE = new SaveScoreState(0, Difficulty.INSANE);

    private int cycles;
    private int phase;

    @Override
    public void enter(final ILevelContext ctx) {
        ctx.enableDamage();
        ctx.shouldSkipPlayerUpdate(false);
    }

    @Override
    public void updateLevel(final ILevelContext ctx, final float dt) {
        final Game game = ctx.getGame();
        final Drifter drift = ctx.getDrifter();

        if (cycles++ == 0) {
            game.addEnemy(new Level1Insane(300, 375, -10, 20));
            drift.accelY = -20;
            drift.accelX = 20;
            drift.clampDx(0, 200);
            return;
        }

        if (cycles % 4000 == 0) {
            switch (++phase) {
                case 0:
                    drift.accelY = -20;
                    drift.accelX = 20;
                    drift.clampDx(0, 250);
                    break;
                case 1:
                    drift.accelX = -20;
                    drift.accelY = 20;
                    drift.clampDy(0,250);
                    break;
                case 2:
                    drift.accelY = -20;
                    drift.clampDx(-250,0);
                    break;
                case 3:
                    drift.accelX = 20;
                    drift.clampDy(-250,0);
                    break;
            }
        }
        if (cycles > 40 && game.noMoreEnemies()) {
            ctx.setState(new PostbossDialog(EXIT_STATE));
            return;
        }
    }
}
