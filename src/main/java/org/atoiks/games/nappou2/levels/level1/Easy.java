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

import org.atoiks.games.nappou2.SaveData;
import org.atoiks.games.nappou2.GameConfig;

import org.atoiks.games.nappou2.levels.LevelState;
import org.atoiks.games.nappou2.levels.LevelContext;

import org.atoiks.games.nappou2.spawner.SmallFishSpawner;

import org.atoiks.games.nappou2.entities.Game;

import org.atoiks.games.nappou2.entities.shield.Shield;
import org.atoiks.games.nappou2.entities.shield.CounterBasedShield;

import org.atoiks.games.nappou2.levels.level1.easy.EasyWave1;

import static org.atoiks.games.nappou2.levels.level1.Data.LEVEL_LOOP;

import static org.atoiks.games.nappou2.scenes.GameLevelScene.HEIGHT;
import static org.atoiks.games.nappou2.scenes.GameLevelScene.GAME_BORDER;

public final class Easy implements LevelState {

    private static final long serialVersionUID = 1033236077109661435L;

    private transient int cycles;

    private transient Clip bgm;

    @Override
    public void enter(final LevelContext ctx) {
        ctx.clearMessage();

        this.cycles = 0;

        final GameConfig cfg = ResourceManager.get("./game.cfg");
        final SaveData saveData = ResourceManager.get("./saves.dat");

        final Game game = ctx.getGame();
        game.drifter.clampSpeed(0, 0, 0, 0);
        game.player.setPosition(GAME_BORDER / 2, HEIGHT / 6 * 5);
        game.player.getHpCounter().restoreTo(1);
        game.player.getScoreCounter().reset();

        final Shield shield = game.player.getShield();
        if (shield instanceof CounterBasedShield) {
            ((CounterBasedShield) shield).resetCounter();
        }

        bgm = ResourceManager.get("/music/Level_One.wav");
        if (cfg.bgm) {
            bgm.setMicrosecondPosition(0);
            bgm.start();
            bgm.setLoopPoints(LEVEL_LOOP, -1);
            bgm.loop(Clip.LOOP_CONTINUOUSLY);
        }

        saveData.setCheckpoint(this);
    }

    @Override
    public void exit() {
        bgm.stop();
    }

    @Override
    public void updateLevel(final LevelContext ctx, final float dt) {
        switch (++cycles) {
            //Fish group 1
            case 400: {
                final Game game = ctx.getGame();
                game.addSpawner(new SmallFishSpawner(375, 10, -10, 0, 500, 7 * (float) Math.PI / 12, 1000, false));
                game.addSpawner(new SmallFishSpawner(375, 10, -10, 0, 500, 5 * (float) Math.PI / 12, 1000, true));
                break;
            }
            //Fish group 2
            case 455: {
                final Game game = ctx.getGame();
                game.addSpawner(new SmallFishSpawner(760, 0, 50, 10, 500, (float) Math.PI, 100, false));
                game.addSpawner(new SmallFishSpawner(-10, 0, 550, 10, 500, 0, 100, true));
                game.addSpawner(new SmallFishSpawner(760, 0, 400, 10, 500, (float) Math.PI, 100, false));
                game.addSpawner(new SmallFishSpawner(-10, 0, 200, 10, 500, 0, 100, true));
                break;
            }
            default:
                if (cycles > 605 && ctx.getGame().noMoreEnemies()) {
                    ctx.setState(new EasyWave1());
                    return;
                }
        }
    }
}
