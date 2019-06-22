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

package org.atoiks.games.nappou2.levels.level2;

import org.atoiks.games.framework2d.ResourceManager;

import org.atoiks.games.nappou2.Difficulty;
import org.atoiks.games.nappou2.GameConfig;

import org.atoiks.games.nappou2.levels.ILevelState;
import org.atoiks.games.nappou2.levels.ILevelContext;
import org.atoiks.games.nappou2.levels.SaveScoreState;

import org.atoiks.games.nappou2.entities.Game;

import org.atoiks.games.nappou2.entities.enemy.*;

import static org.atoiks.games.nappou2.Utils.dropEnemy;
import static org.atoiks.games.nappou2.Utils.leapEnemy;
import static org.atoiks.games.nappou2.Utils.shiftEnemy;
import static org.atoiks.games.nappou2.Utils.starShotEnemy;
import static org.atoiks.games.nappou2.Utils.singleShotEnemy;

import static org.atoiks.games.nappou2.scenes.GameLevelScene.HEIGHT;
import static org.atoiks.games.nappou2.scenes.GameLevelScene.GAME_BORDER;

public final class Easy implements ILevelState {

    private static final long serialVersionUID = 1935810609973937620L;

    private transient int cycles;
    private transient int wave;

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
        game.getScoreCounter().reset();
    }

    @Override
    public void updateLevel(final ILevelContext ctx, final float dt) {
        final Game game = ctx.getGame();

        ++cycles;
        switch (wave) {
            case 0:
                switch (cycles) {
                    case 20:
                        game.addEnemy(singleShotEnemy(1, 375, -10, 8, false));
                        game.addEnemy(new DummyEnemy(2, -10, 20, 20, 500, 1, true));
                        game.addEnemy(new DummyEnemy(2, 760, 20, 20, 500, 1, false));
                        game.addEnemy(new DummyEnemy(2, -10, 120, 20, 500, 1, true));
                        game.addEnemy(new DummyEnemy(2, 760, 120, 20, 500, 1, false));
                        game.addEnemy(new DummyEnemy(2, -10, 220, 20, 500, 1, true));
                        game.addEnemy(new DummyEnemy(2, 760, 220, 20, 500, 1, false));
                        game.addEnemy(new DummyEnemy(2, -10, 320, 20, 500, 1, true));
                        game.addEnemy(new DummyEnemy(2, 760, 320, 20, 500, 1, false));
                        break;
                    case 120:
                        game.addEnemy(dropEnemy(1, 30, -10, 8, false));
                        game.addEnemy(dropEnemy(1, 720, -10, 8, false));
                        game.addEnemy(new DummyEnemy(2, -10, 280, 20, 500, 1, true));
                        game.addEnemy(new DummyEnemy(2, 760, 280, 20, 500, 1, false));
                        game.addEnemy(new DummyEnemy(2, -10, 380, 20, 500, 1, true));
                        game.addEnemy(new DummyEnemy(2, 760, 380, 20, 500, 1, false));
                        game.addEnemy(new DummyEnemy(2, -10, 480, 20, 500, 1, true));
                        game.addEnemy(new DummyEnemy(2, 760, 480, 20, 500, 1, false));
                        game.addEnemy(new DummyEnemy(2, -10, 580, 20, 500, 1, true));
                        game.addEnemy(new DummyEnemy(2, 760, 580, 20, 500, 1, false));
                        break;
                    case 220:
                        game.addEnemy(dropEnemy(1, 30, -10, 8, false));
                        game.addEnemy(dropEnemy(1, 720, -10, 8, false));
                        game.addEnemy(new DummyEnemy(1, -10, 30, 10, 500, 1, true));
                        game.addEnemy(new DummyEnemy(1, 760, 30, 10, 500, 1, false));
                        game.addEnemy(new DummyEnemy(1, -10, 70, 10, 500, 1, true));
                        game.addEnemy(new DummyEnemy(1, 760, 70, 10, 500, 1, false));
                        game.addEnemy(new DummyEnemy(1, -10, 110, 10, 500, 1, true));
                        game.addEnemy(new DummyEnemy(1, 760, 110, 10, 500, 1, false));
                        game.addEnemy(new DummyEnemy(1, -10, 150, 10, 500, 1, true));
                        game.addEnemy(new DummyEnemy(1, 760, 150, 10, 500, 1, false));
                        break;
                    case 320:
                        game.addEnemy(singleShotEnemy(1, 375, -10, 8, false));
                        game.addEnemy(new DummyEnemy(1, -10, 590, 10, 1000, 1, true));
                        game.addEnemy(new DummyEnemy(1, 760, 590, 10, 1000, 1, false));
                        game.addEnemy(new DummyEnemy(1, -10, 550, 10, 1000, 1, true));
                        game.addEnemy(new DummyEnemy(1, 760, 550, 10, 1000, 1, false));
                        game.addEnemy(new DummyEnemy(1, -10, 510, 10, 1000, 1, true));
                        game.addEnemy(new DummyEnemy(1, 760, 510, 10, 1000, 1, false));
                        game.addEnemy(new DummyEnemy(1, -10, 470, 10, 1000, 1, true));
                        game.addEnemy(new DummyEnemy(1, 760, 470, 10, 1000, 1, false));
                        break;
                    case 600:
                        game.addEnemy(dropEnemy(1, 30, -10, 8, false));
                        game.addEnemy(dropEnemy(1, 720, -10, 8, false));
                        game.addEnemy(new DummyEnemy(1, -10, 200, 10, 1000, 1, true));
                        game.addEnemy(new DummyEnemy(1, 760, 200, 10, 1000, 1, false));
                        game.addEnemy(new DummyEnemy(1, -10, 240, 10, 1000, 1, true));
                        game.addEnemy(new DummyEnemy(1, 760, 240, 10, 1000, 1, false));
                        game.addEnemy(new DummyEnemy(1, -10, 280, 10, 1000, 1, true));
                        game.addEnemy(new DummyEnemy(1, 760, 280, 10, 1000, 1, false));
                        game.addEnemy(new DummyEnemy(1, -10, 320, 10, 1000, 1, true));
                        game.addEnemy(new DummyEnemy(1, 760, 320, 10, 1000, 1, false));
                        game.addEnemy(new DummyEnemy(1, -10, 360, 10, 1000, 1, true));
                        game.addEnemy(new DummyEnemy(1, 760, 360, 10, 1000, 1, false));
                        game.addEnemy(new DummyEnemy(1, -10, 400, 10, 1000, 1, true));
                        game.addEnemy(new DummyEnemy(1, 760, 400, 10, 1000, 1, false));
                        break;
                }
                if (cycles > 600) {
                    if (game.noMoreEnemies()) {
                        wave++;
                        cycles = 0;
                    }
                }
                break;
            case 1:
                switch (cycles) {
                    case 20:
                        game.addEnemy(singleShotEnemy(1, 375, -10, 8, false));
                        game.addEnemy(new ChargerEnemy(1, 0, 0, 5, 5, 500));
                        game.addEnemy(new ChargerEnemy(1, 750, 0, 5, 5, 500));
                        break;
                    case 120:
                        game.addEnemy(singleShotEnemy(1, 375, -10, 8, false));
                        game.addEnemy(new ChargerEnemy(1, 0, 600, 10, 6, 500));
                        game.addEnemy(new ChargerEnemy(1, 750, 600, 10, 6, 500));
                        break;
                    case 220:
                        game.addEnemy(singleShotEnemy(1, 375, -10, 8, false));
                        game.addEnemy(new ChargerEnemy(1, 0, 300, 15, 7, 500));
                        game.addEnemy(new ChargerEnemy(1, 750, 300, 15, 7, 500));
                        break;
                    case 310:
                        game.addEnemy(singleShotEnemy(5, 375, -10, 15, false));
                        break;
                    }
                if (cycles > 310) {
                    if (game.noMoreEnemies()) {
                        wave++;
                        cycles = 0;
                    }
                }
                break;
            case 2:
                switch (cycles) {
                    case 10:
                        game.addEnemy(dropEnemy(1, 750, -10, 8, false));
                        game.addEnemy(shiftEnemy(5, 0, 10, 10, 0.325f, false));
                        break;
                    case 100:
                        game.addEnemy(shiftEnemy(5, 0, 10, 10, 0.325f, true));
                        break;
                    case 200:
                    case 300:
                    case 400:
                    case 500:
                        game.addEnemy(starShotEnemy(1, 600, 0, 5, false));
                        game.addEnemy(starShotEnemy(1, 450, 0, 5, false));
                        game.addEnemy(starShotEnemy(1, 600, -10, 5, false));
                        game.addEnemy(starShotEnemy(1, 450, -10, 5, false));
                        break;
                    case 510:
                        game.addEnemy(dropEnemy(1, 0, 610, 8, true));
                        game.addEnemy(shiftEnemy(5, 0, 10, 10, 0.325f, false));
                        break;
                    case 600:
                        game.addEnemy(shiftEnemy(5, 0, 10, 10, 0.325f, true));
                        break;
                    }
                if (cycles > 600) {
                    if (game.noMoreEnemies()) {
                        wave++;
                        cycles = 0;
                    }
                }
                break;
            case 3:
                switch (cycles) {
                    case 10:
                        game.addEnemy(leapEnemy(1, 375, 600, 8, 75, 1, 0.25f, 2, 1, 5));
                        break;
                    case 510:
                        game.addEnemy(leapEnemy(1, 300, 600, 9, 75, -1, 0.25f, 4, 1, 4));
                        game.addEnemy(leapEnemy(1, 450, 600, 9, 75, 1, 0.25f, 2, 1, 6.25f));
                        game.addEnemy(starShotEnemy(1, 300, 0, 5, false));
                        game.addEnemy(starShotEnemy(1, 450, 0, 5, false));
                        break;
                    case 1010:
                        game.addEnemy(leapEnemy(1, 200, 600, 10, 75, -1, 0.25f, 4, 2, 3));
                        game.addEnemy(leapEnemy(1, 350, 600, 10, 75, 1, 0.25f, 2, 0.5f, 7));
                        game.addEnemy(leapEnemy(1, 400, 600, 10, 75, -1, 0.25f, 4, 3, 2));
                        game.addEnemy(leapEnemy(1, 550, 600, 10, 75, 1, 0.25f, 2, 1, 6.25f));
                        game.addEnemy(starShotEnemy(1, 350, 0, 5, false));
                        game.addEnemy(starShotEnemy(1, 400, 0, 5, false));
                        game.addEnemy(starShotEnemy(1, 550, 0, 5, false));
                        game.addEnemy(starShotEnemy(1, 200, 0, 5, false));
                        break;
                    case 1510:
                        game.addEnemy(leapEnemy(1, 150, 600, 8, 75, -1, 0.25f, 4, 2, 0.75f));
                        game.addEnemy(leapEnemy(1, 276, 600, 9, 75, -1, 0.25f, 4, 0.5f, 4.5f));
                        game.addEnemy(leapEnemy(1, 367, 600, 10, 75, -1, 0.25f, 4, 3, 2));
                        game.addEnemy(leapEnemy(1, 675, 600, 8, 75, 1, 0.25f, 2, 0.345f, 1.3f));
                        game.addEnemy(leapEnemy(1, 293, 600, 9, 75, 1, 0.25f, 2, 0.23f, 0.5f));
                        game.addEnemy(leapEnemy(1, 700, 600, 10, 75, 1, 0.25f, 2, 5, 7));
                        game.addEnemy(leapEnemy(1, 100, 600, 12, 75, -1, 0.25f, 4, 3, 10));
                        game.addEnemy(leapEnemy(1, 545, 600, 12, 75, 1, 0.25f, 2, 1, 6.25f));
                        break;
                }
                if (cycles > 1510) {
                    if (game.noMoreEnemies()) {
                        ctx.setState(new PrebossDialog(new EasyBossWave()));
                        return;
                    }
                }
                break;
        }
    }
}

final class EasyBossWave implements ILevelState {

    private static final long serialVersionUID = -4257651682666683051L;

    private static final SaveScoreState EXIT_STATE = new SaveScoreState(1, Difficulty.EASY);

    private transient int cycles;

    @Override
    public void enter(final ILevelContext ctx) {
        ctx.enableDamage();
        ctx.shouldSkipPlayerUpdate(false);
    }

    @Override
    public void updateLevel(final ILevelContext ctx, final float dt) {
        final Game game = ctx.getGame();

        if (cycles++ == 0) {
            game.addEnemy(new Level2Easy1(12, 500, -10, 20));
            game.addEnemy(new Level2Easy2(12, 250, -10, 20));
            return;
        }

        if (cycles > 40 && game.noMoreEnemies()) {
            ctx.setState(new PostbossDialog(EXIT_STATE));
            return;
        }
    }
}
