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

import org.atoiks.games.nappou2.levels.ILevelState;
import org.atoiks.games.nappou2.levels.ILevelContext;

import org.atoiks.games.nappou2.entities.Game;

import org.atoiks.games.nappou2.entities.enemy.*;

import org.atoiks.games.nappou2.levels.level1.normal.NormalWave1;

import static org.atoiks.games.nappou2.Utils.dropEnemy;
import static org.atoiks.games.nappou2.Utils.singleShotEnemy;
import static org.atoiks.games.nappou2.Utils.circularPathEnemy;

import static org.atoiks.games.nappou2.levels.level1.Data.*;

import static org.atoiks.games.nappou2.scenes.GameLevelScene.HEIGHT;
import static org.atoiks.games.nappou2.scenes.GameLevelScene.GAME_BORDER;

public final class Normal implements ILevelState {

    private static final long serialVersionUID = -8000599825518660858L;

    private transient int cycles;

    private transient Clip bgm;

    @Override
    public void enter(final ILevelContext ctx) {
        ctx.clearMessage();

        this.cycles = 0;

        final GameConfig cfg = ResourceManager.get("./game.cfg");
        final SaveData saveData = ResourceManager.get("./saves.dat");

        final Game game = ctx.getGame();
        game.drifter.clampSpeed(0, 0, 0, 0);
        game.player.setPosition(GAME_BORDER / 2, HEIGHT / 6 * 5);
        game.player.getHpCounter().restoreTo(saveData.isChallengeMode() ? 1 : 5);
        game.player.getScoreCounter().reset();

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

        switch (++cycles) {
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
            ctx.setState(new NormalWave1());
            return;
        }
    }
}
