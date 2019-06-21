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

import org.atoiks.games.nappou2.Vector2;
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
import org.atoiks.games.nappou2.entities.bullet.*;

import org.atoiks.games.nappou2.entities.bullet.factory.RayInfo;

import org.atoiks.games.nappou2.pathway.*;

import org.atoiks.games.nappou2.pattern.*;
import org.atoiks.games.nappou2.TrigConstants;

import static org.atoiks.games.nappou2.Utils.mb1;
import static org.atoiks.games.nappou2.Utils.dropEnemy;
import static org.atoiks.games.nappou2.Utils.miniBomberEnemy;
import static org.atoiks.games.nappou2.Utils.singleShotEnemy;
import static org.atoiks.games.nappou2.Utils.circularPathEnemy;

import static org.atoiks.games.nappou2.levels.level1.Data.*;

import static org.atoiks.games.nappou2.scenes.GameLevelScene.HEIGHT;
import static org.atoiks.games.nappou2.scenes.GameLevelScene.GAME_BORDER;

public final class Easy implements ILevelState {

    private static final long serialVersionUID = 1033236077109661435L;

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
        final SaveData saveData = ResourceManager.get("./saves.dat");

        final Game game = ctx.getGame();
        game.player.setPosition(GAME_BORDER / 2, HEIGHT / 6 * 5);
        game.player.setHp(cfg.challengeMode ? 1 : 5);
        game.setScore(0);

        bgm = ResourceManager.get("/music/Level_One.wav");
        if (cfg.bgm) {
            bgm.setMicrosecondPosition(0);
            bgm.start();
            bgm.setLoopPoints(LEVEL_LOOP, -1);
            bgm.loop(Clip.LOOP_CONTINUOUSLY);
        }

        ResourceManager.<SaveData>get("./saves.dat").setCheckpoint(this);
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
                    ctx.setState(new EasyWave5(bgm.getMicrosecondPosition()));
                    return;
                }
                break;
        }
    }
}

final class EasyWave5 implements ILevelState {

    private static final long serialVersionUID = 5308372197610362137L;

    private static final RayInfo WAVE7_RAY_INFO = new RayInfo(25, 5, 500);

    private long songOffset;

    private int restoreScore;
    private int restoreHp;

    private transient int cycles;
    private transient int wave;

    private transient Clip bgm;

    public EasyWave5(long pos) {
        this.songOffset = pos;
    }

    @Override
    public void restore(final ILevelContext ctx) {
        final Game game = ctx.getGame();
        game.player.setPosition(GAME_BORDER / 2, HEIGHT / 6 * 5);
        game.player.setHp(restoreHp);
        game.setScore(restoreScore);
    }

    @Override
    public void enter(final ILevelContext ctx) {
        this.cycles = 0;
        this.wave = 5;

        final Game game = ctx.getGame();
        this.restoreHp = game.player.getHp();
        this.restoreScore = game.getScore();

        final GameConfig cfg = ResourceManager.get("./game.cfg");
        bgm = ResourceManager.get("/music/Level_One.wav");
        if (cfg.bgm) {
            bgm.setMicrosecondPosition(this.songOffset);
            bgm.start();
            bgm.setLoopPoints(LEVEL_LOOP, -1);
            bgm.loop(Clip.LOOP_CONTINUOUSLY);
        }

        ResourceManager.<SaveData>get("./saves.dat").setCheckpoint(this);
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
            case 5:
                switch (cycles) {
                    case 40:
                        PathwayEnemy e = new PathwayEnemy(1, 1, new CollapsingOrbitalPathway(new Vector2(310, 310), new Vector2(375, 300), 1, 1, 3 * Math.PI / 2), NullPattern.INSTANCE);
                        e.setR(8);
                        game.addEnemy(e);
                        break;
                    case 80:
                        e = new PathwayEnemy(1, 1, new CollapsingOrbitalPathway(new Vector2(350, 350), new Vector2(375, 300), 1, 1, Math.PI), NullPattern.INSTANCE);
                        e.setR(10);
                        game.addEnemy(e);
                        break;
                    case 280:
                        e = new PathwayEnemy(1, 1, new CollapsingOrbitalPathway(new Vector2(310, 310), new Vector2(375, 300), 1, -1, 3 * Math.PI / 2), NullPattern.INSTANCE);
                        e.setR(20);
                        game.addEnemy(e);
                        break;
                    case 380:
                        e = new PathwayEnemy(1, 1, new CollapsingOrbitalPathway(new Vector2(350, 350), new Vector2(375, 300), 1, 1, 0), NullPattern.INSTANCE);
                        e.setR(6);
                        game.addEnemy(e);
                        break;
                    case 580:
                        e = new PathwayEnemy(1, 1, new CollapsingOrbitalPathway(new Vector2(310, 310), new Vector2(375, 300), 1, -1, Math.PI / 2), NullPattern.INSTANCE);
                        e.setR(8);
                        game.addEnemy(e);
                        break;
                    case 600:
                        e = new PathwayEnemy(1, 1, new CollapsingOrbitalPathway(new Vector2(350, 350), new Vector2(375, 300), 1, 1, 0), NullPattern.INSTANCE);
                        e.setR(8);
                        game.addEnemy(e);
                        break;
                    case 880:
                        e = new PathwayEnemy(1, 1, new CollapsingOrbitalPathway(new Vector2(350, 350), new Vector2(375, 300), 1, -1, 0), NullPattern.INSTANCE);
                        e.setR(6);
                        game.addEnemy(e);
                        break;
                    case 1000:
                        e = new PathwayEnemy(1, 1, new CollapsingOrbitalPathway(new Vector2(310, 310), new Vector2(375, 300), 1, -1, 3 * Math.PI / 2), NullPattern.INSTANCE);
                        e.setR(10);
                        game.addEnemy(e);
                        break;
                    case 1200:
                        e = new PathwayEnemy(1, 1, new CollapsingOrbitalPathway(new Vector2(310, 310), new Vector2(375, 300), 1, -1, Math.PI / 2), NullPattern.INSTANCE);
                        e.setR(12);
                        game.addEnemy(e);
                        break;
                    case 1250:
                        e = new PathwayEnemy(1, 1, new CollapsingOrbitalPathway(new Vector2(310, 310), new Vector2(375, 300), 1, 1, 3 * Math.PI / 2), NullPattern.INSTANCE);
                        e.setR(8);
                        game.addEnemy(e);
                        break;
                }
                if (cycles > 1250 && game.noMoreEnemies()) {
                    wave++;
                    cycles = 0;
                }
                break;
            case 6:
                switch (cycles) {
                    case 40:
                        game.addEnemy(new Squirts(10, 375, -10, 20));
                        break;
                    case 50:
                    case 100:
                    case 150:
                    case 200:
                        game.addEnemy(new StreamBeam(1, 10, 610, 8, true));
                        game.addEnemy(new StreamBeam(1, 740, 610, 8, true));
                        break;
                }
                if (cycles > 200 && game.noMoreEnemies()) {
                    wave++;
                    cycles = 0;
                }
                break;
            case 7:
                switch (cycles) {
                    case 100:
                    case 200:
                    case 300:
                    case 400:
                    case 500:
                        PathwayEnemy e = new PathwayEnemy(1, 1, new FixedVelocity(new Vector2(-10, 10), new Vector2(100, 0)), new RandomDropPattern(3, WAVE7_RAY_INFO));
                        e.setR(8);
                        game.addEnemy(e);
                        break;
                    case 190:
                    case 250:
                    case 310:
                    case 370:
                    case 430:
                    case 490:
                    case 550:
                        e = new PathwayEnemy(1, 1, new FixedVelocity(new Vector2(760, 50), new Vector2(-250, 0)), new RandomDropPattern(3, WAVE7_RAY_INFO));
                        e.setR(13);
                        game.addEnemy(e);
                        break;
                    case 325:
                    case 425:
                    case 525:
                    case 625:
                    case 725:
                        e = new PathwayEnemy(1, 1, new FixedVelocity(new Vector2(-10, 75), new Vector2(300, 0)), new RandomDropPattern(3, WAVE7_RAY_INFO));
                        e.setR(25);
                        game.addEnemy(e);
                    case 375:
                    case 475:
                    case 575:
                    case 675:
                    case 775:
                        e = new PathwayEnemy(1, 1, new FixedVelocity(new Vector2(760, 25), new Vector2(-150, 0)), new RandomDropPattern(3, WAVE7_RAY_INFO));
                        e.setR(6);
                        game.addEnemy(e);
                        break;
                }
                if (cycles > 500 && game.noMoreEnemies()) {
                    wave++;
                    cycles = 0;
                }
                break;
            case 10:
                ctx.setState(new PrebossDialog(new EasyBossWave()));
                return;
        }
    }
}

final class EasyBossWave implements ILevelState {

    private static final long serialVersionUID = 1914901384100845861L;

    private static final SaveScoreState EXIT_STATE = new SaveScoreState(0, Difficulty.EASY);

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
