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

import org.atoiks.games.nappou2.Vector2;
import org.atoiks.games.nappou2.SaveData;
import org.atoiks.games.nappou2.GameConfig;

import org.atoiks.games.nappou2.levels.LevelState;
import org.atoiks.games.nappou2.levels.LevelContext;

import org.atoiks.games.nappou2.entities.Game;

import org.atoiks.games.nappou2.graphics.shapes.ImmutableCircle;

import static org.atoiks.games.nappou2.Utils.dropEnemy;
import static org.atoiks.games.nappou2.Utils.singleShotEnemy;
import static org.atoiks.games.nappou2.Utils.circularPathEnemy;

import static org.atoiks.games.nappou2.levels.level1.Data.LEVEL_LOOP;

import static org.atoiks.games.nappou2.scenes.GameLevelScene.HEIGHT;
import static org.atoiks.games.nappou2.scenes.GameLevelScene.GAME_BORDER;

public abstract class AbstractWave0 implements LevelState {

    private static final long serialVersionUID = 2073727756645267798L;

    private static final ImmutableCircle BOUNDARY_650_M1 = new ImmutableCircle(new Vector2(650, -1), 100);
    private static final ImmutableCircle BOUNDARY_100_M1 = new ImmutableCircle(new Vector2(100, -1), 100);

    private transient Clip bgm;

    @Override
    public void enter(final LevelContext ctx) {
        ctx.clearMessage();

        final GameConfig cfg = ResourceManager.get("./game.cfg");
        final SaveData saveData = ResourceManager.get("./saves.dat");

        final Game game = ctx.getGame();
        game.drifter.clampSpeed(0, 0, 0, 0);
        game.player.setPosition(GAME_BORDER / 2, HEIGHT / 6 * 5);
        game.player.getScoreCounter().reset();

        bgm = ResourceManager.get("/music/Level_One.wav");
        if (cfg.bgm) {
            bgm.setMicrosecondPosition(0);
            bgm.setLoopPoints(LEVEL_LOOP, -1);
            bgm.loop(Clip.LOOP_CONTINUOUSLY);
            bgm.start();
        }
    }

    @Override
    public void exit() {
        bgm.stop();
    }

    protected void singleShotEnemiesTopDown(Game game, int k) {
        this.singleShotEnemiesDownwards(game, k);
        this.singleShotEnemiesUpwards(game, k);
    }

    protected void singleShotEnemiesDownwards(Game game, int k) {
        game.addEnemy(singleShotEnemy(1, 300 - k, -10, 8, false));
        game.addEnemy(singleShotEnemy(1, 450 + k, -10, 8, false));
    }

    protected void singleShotEnemiesUpwards(Game game, int k) {
        game.addEnemy(singleShotEnemy(1, 300 - k, 610, 8, true));
        game.addEnemy(singleShotEnemy(1, 450 + k, 610, 8, true));
    }

    protected void dropAndCircularPathEnemies(Game game) {
        this.dropEnemies(game);
        this.circularPathEnemies(game);
    }

    protected void dropEnemies(Game game) {
        game.addEnemy(dropEnemy(1, -10, 10, 8, false));
        game.addEnemy(dropEnemy(1, 760, 10, 8, false));
    }

    protected void circularPathEnemies(Game game) {
        game.addEnemy(circularPathEnemy(1, BOUNDARY_650_M1, 8, 1, 1, 0, 100));
        game.addEnemy(circularPathEnemy(1, BOUNDARY_100_M1, 8, -1, 1, 2, 100));
    }
}
