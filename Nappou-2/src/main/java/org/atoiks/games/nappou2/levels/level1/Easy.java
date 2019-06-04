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

import org.atoiks.games.nappou2.spawner.FishSpawner;

import org.atoiks.games.nappou2.entities.Game;
import org.atoiks.games.nappou2.entities.Player;

import org.atoiks.games.nappou2.entities.enemy.*;

import org.atoiks.games.nappou2.entities.shield.IShield;

import static org.atoiks.games.nappou2.Utils.mb1;
import static org.atoiks.games.nappou2.Utils.miniBomberEnemy;
import static org.atoiks.games.nappou2.Utils.singleShotEnemy;
import static org.atoiks.games.nappou2.Utils.circularPathEnemy;

import static org.atoiks.games.nappou2.levels.level1.Data.*;

import static org.atoiks.games.nappou2.scenes.GameLevelScene.HEIGHT;
import static org.atoiks.games.nappou2.scenes.GameLevelScene.GAME_BORDER;

public final class Easy implements ILevelState {

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

        if (saveData.getCheck() == 3) {
            this.wave = 5;
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
                    //Fish group 1
                    case 400:
                        game.addEnemySpawner(new FishSpawner(375, 10, -10, 0, 500, 7 * (float) Math.PI / 12, 1000, false));
                        game.addEnemySpawner(new FishSpawner(375, 10, -10, 0, 500, 5 * (float) Math.PI / 12, 1000, true));
                        break;

                    //Fish group 2
                    case 455:
                        game.addEnemySpawner(new FishSpawner(760, 0, 50, 10, 500, (float) Math.PI, 100, false));
                        game.addEnemySpawner(new FishSpawner(-10, 0, 550, 10, 500, 0, 100, true));
                        game.addEnemySpawner(new FishSpawner(760, 0, 400, 10, 500, (float) Math.PI, 100, false));
                        game.addEnemySpawner(new FishSpawner(-10, 0, 200, 10, 500, 0, 100, true));
                }
                if (cycles > 605 && game.noMoreEnemies()) {
                    wave++;
                    cycles = 0;
                }
                break;
            case 1:
                switch (cycles) {
                    case 50:
                    case 100:
                    case 150:
                    case 200:
                    case 250:
                    case 300:
                    case 350:
                    case 400:
                    case 450:
                    case 500:
                    case 550:
                    case 600:
                    case 650:
                    case 700:
                    case 750:
                    case 800:
                    case 850:
                    case 900:
                    case 950:
                    case 1000:
                    case 1050:
                    case 1100:
                    case 1150:
                    case 1200:
                    case 1250:
                    case 1300:
                    case 1350:
                    case 1400:
                    case 1450:
                    case 1500:
                    case 1550:
                    case 1600:
                    case 1650:
                    case 1700:
                    case 1750:
                    case 1800:
                        game.addEnemy(new FishPart(1, 750 * rnd.nextFloat(), 615, 20 * rnd.nextFloat() + 10, 900 * rnd.nextFloat() + 100, 3 * (float) Math.PI / 2, 900 * rnd.nextFloat() + 100, 10, rnd.nextBoolean()));
                        break;
                }
                if (cycles > 1800 && game.noMoreEnemies()) {
                    wave++;
                    cycles = 0;
                }
                break;
            case 2:
                if (cycles == 40) {
                    game.addEnemy(new Ripple(10, 375, -10, 20, 500, (float) Math.PI / 2));
                    game.addEnemySpawner(new FishSpawner(700, 10, 610, 0, 250, 3 * (float) Math.PI / 2, 100, false));
                    game.addEnemySpawner(new FishSpawner(50, 10, 610, 0, 250, 3 * (float) Math.PI / 2, 100, true));
                }
                if (cycles > 40 && game.noMoreEnemies()) {
                    wave++;
                    cycles = 0;
                }
                break;
            case 3:
                switch (cycles) {
                    case 40:
                        game.addEnemy(new Squirts(10, 275, -10, 20));
                        game.addEnemy(new Squirts(10, 475, -10, 20));
                        break;
                }
                if (cycles > 80 && game.noMoreEnemies()) {
                    wave++;
                    cycles = 0;
                }
                break;
            case 4:
                switch (cycles) {
                    case 40:
                    case 440:
                    case 840:
                    case 1240:
                    case 1640:
                    case 2040:
                        game.addEnemy(new StreamBeam(1, 10, 610, 8, true));
                        game.addEnemy(new StreamBeam(1, 375, -10, 8, false));
                        game.addEnemy(new StreamBeam(1, 740, 610, 8, true));
                        break;
                }
                if (cycles > 2040 && game.noMoreEnemies()) {
                    final SaveData sData = ResourceManager.get("./saves.dat");
                    sData.setCheck(3);
                    wave++;
                    cycles = 0;
                }
                break;
            case 5:
                // XXX: Placeholder
                ctx.setState(new PrebossDialog(new EasyBossWave()));
                return;
        }
    }
}

final class EasyBossWave implements ILevelState {

    private static final SaveScoreState EXIT_STATE = new SaveScoreState(0, Difficulty.EASY);

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
            game.addEnemy(new Level1Easy(300, 375, -10, 20));
            drift.accelY = -20;
            drift.accelX = 20;
            drift.clampDx(0, 50);
            return;
        }

        if (cycles % 4000 == 0) {
            switch (++phase) {
                case 0:
                    drift.accelY = -20;
                    drift.accelX = 20;
                    drift.clampDx(0, 50);
                    break;
                case 1:
                    drift.accelX = -20;
                    drift.accelY = 20;
                    drift.clampDy(0, 50);
                    break;
                case 2:
                    drift.accelY = -20;
                    drift.clampDx(-50, 0);
                    break;
                case 3:
                    drift.accelX = 20;
                    drift.clampDy(-50, 0);
                    break;
            }
        }
        if (cycles > 40 && game.noMoreEnemies()) {
            ctx.setState(new PostbossDialog(EXIT_STATE));
            return;
        }
    }
}
