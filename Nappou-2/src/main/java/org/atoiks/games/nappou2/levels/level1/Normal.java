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

import javax.sound.sampled.Clip;

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

import static org.atoiks.games.nappou2.Utils.mb1;
import static org.atoiks.games.nappou2.Utils.dropEnemy;
import static org.atoiks.games.nappou2.Utils.miniBomberEnemy;
import static org.atoiks.games.nappou2.Utils.singleShotEnemy;
import static org.atoiks.games.nappou2.Utils.circularPathEnemy;
import static org.atoiks.games.nappou2.Utils.tweenRadialGroupPattern;

import static org.atoiks.games.nappou2.levels.level1.Data.*;

import static org.atoiks.games.nappou2.scenes.GameLevelScene.HEIGHT;
import static org.atoiks.games.nappou2.scenes.GameLevelScene.GAME_BORDER;

public final class Normal implements ILevelState {

    private static final long serialVersionUID = -8000599825518660858L;

    private transient int cycles;
    private transient int wave;

    private transient Clip bgm;

    @Override
    public void enter(final ILevelContext ctx) {
        ctx.getDrifter().clampSpeed(0, 0, 0, 0);
        ctx.clearMessage();

        this.cycles = 0;
        this.wave = 0;

        final GameConfig cfg = ResourceManager.get("./game.cfg");

        final Game game = ctx.getGame();
        game.player.setPosition(GAME_BORDER / 2, HEIGHT / 6 * 5);
        game.player.getHpCounter().restoreTo(cfg.challengeMode ? 1 : 5);
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

        ++cycles;
        switch (wave) {
            case 0:
                switch (cycles) {
                    case 40:
                    case 80:
                    case 120:
                    case 160:
                    case 200:
                        final int k = cycles * 5 / 4;
                        game.addEnemy(singleShotEnemy(1, 300 - k, -10, 8, false));
                        game.addEnemy(singleShotEnemy(1, 450 + k, -10, 8, false));
                        break;
                    case 220:
                        game.addEnemy(dropEnemy(1, -10, 10, 8, false));
                        game.addEnemy(dropEnemy(1, 760, 10, 8, false));
                        break;
                    case 600:
                        game.addEnemy(circularPathEnemy(1, 650, -1, 8, 100, 1, 1, 0, 100));
                        game.addEnemy(circularPathEnemy(1, 100, -1, 8, 100, -1, 1, 2, 100));
                        break;
                }
                if (cycles > 600 && game.noMoreEnemies()) {
                    wave++;
                    cycles = 0;
                }
                break;
            case 1:
                switch (cycles) {
                    case 40:
                        game.addEnemy(dropEnemy(1, 30, -10, 8, false));
                        game.addEnemy(dropEnemy(1, 720, -10, 8, false));
                        game.addEnemy(dropEnemy(1, 50, -10, 8, false));
                        game.addEnemy(dropEnemy(1, 700, -10, 8, false));
                        break;
                    case 1880:
                        game.addEnemy(dropEnemy(1, 30, -10, 8, false));
                        game.addEnemy(dropEnemy(1, 720, -10, 8, false));
                        game.addEnemy(dropEnemy(1, 100, -10, 8, false));
                        game.addEnemy(dropEnemy(1, 650, -10, 8, false));   // FALLTHROUGH
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
                        game.addEnemy(miniBomberEnemy(1, w1eX[offset + 0], w1eY[offset + 0], 8, 1, w1eS[offset + 0]));
                        game.addEnemy(miniBomberEnemy(1, w1eX[offset + 1], w1eY[offset + 1], 8, -1, w1eS[offset + 1]));
                        break;
                }
                if (cycles > 1880 && game.noMoreEnemies()) {
                    wave++;
                    cycles = 0;
                }
                break;
            case 2:
                if (cycles == 40) {
                    game.addEnemy(mb1(10, 225, -10, 20));
                    game.addEnemy(mb1(10, 375, -10, 20));
                    game.addEnemy(mb1(10, 525, -10, 20));
                }
                if (cycles > 40 && game.noMoreEnemies()) {
                    wave++;
                    cycles = 0;
                }
                break;
            case 3:
                switch (cycles) {
                    case 40:
                        game.addEnemy(mb1(10, 375, -10, 20));
                        break;
                    case 80:
                        game.addEnemy(circularPathEnemy(1, 750, 50, 8, 100, 1, 0.25f, 1, 100));
                        game.addEnemy(circularPathEnemy(1, 0, 50, 8, 100, -1, 0.25f, 3, 100));
                        break;
                    case 680:
                        game.addEnemy(circularPathEnemy(1, 750, 0, 8, 100, 1, 0.25f, 1, 100));
                        game.addEnemy(circularPathEnemy(1, 0, 0, 8, 100, -1, 0.25f, 3, 100));
                        game.addEnemy(circularPathEnemy(1, 750, 600, 8, 100, -1, 0.25f, 1, 100));
                        game.addEnemy(circularPathEnemy(1, 0, 600, 8, 100, 1, 0.25f, 3, 100));
                        break;
                }
                if (cycles > 680 && game.noMoreEnemies()) {
                    wave++;
                    cycles = 0;
                }
                break;
            case 4:
                if (cycles == 40) {
                    tweenRadialGroupPattern(game, w4eX, w4eR);
                }
                if (cycles > 680 && game.noMoreEnemies()) {
                    ctx.setState(new PrebossDialog(new NormalBossWave()));
                    return;
                }
                break;
        }
    }
}

final class NormalBossWave implements ILevelState {

    private static final long serialVersionUID = -4505613621927595705L;

    private static final SaveScoreState EXIT_STATE = new SaveScoreState(0, Difficulty.NORMAL);

    private transient int cycles;
    private transient int phase;

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
            game.addEnemy(new Level1Normal(300, 375, -10, 20));
            drift.accelY = -20;
            drift.accelX = 20;
            drift.clampDx(0, 100);
            return;
        }

        if (cycles % 4000 == 0) {
            switch (++phase) {
                case 0:
                    drift.accelY = -20;
                    drift.accelX = 20;
                    drift.clampDx(0, 100);
                    break;
                case 1:
                    drift.accelX = -20;
                    drift.accelY = 20;
                    drift.clampDy(0, 100);
                    break;
                case 2:
                    drift.accelY = -20;
                    drift.clampDx(-100, 0);
                    break;
                case 3:
                    drift.accelX = 20;
                    drift.clampDy(-100, 0);
                    break;
            }
        }
        if (cycles > 40 && game.noMoreEnemies()) {
            ctx.setState(new PostbossDialog(EXIT_STATE));
            return;
        }
    }
}
