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

package org.atoiks.games.nappou2.levels.level1.normal;

import org.atoiks.games.nappou2.Drifter;
import org.atoiks.games.nappou2.Difficulty;

import org.atoiks.games.nappou2.levels.LevelState;
import org.atoiks.games.nappou2.levels.LevelContext;
import org.atoiks.games.nappou2.levels.SaveScoreState;

import org.atoiks.games.nappou2.entities.Game;

import org.atoiks.games.nappou2.entities.enemy.Level1Normal;

import org.atoiks.games.nappou2.levels.level1.PostbossDialog;

public class NormalBossWave implements LevelState {

    private static final long serialVersionUID = -4505613621927595705L;

    private static final SaveScoreState EXIT_STATE = new SaveScoreState(0, Difficulty.NORMAL);

    private transient int cycles;
    private transient int phase;

    @Override
    public void enter(final LevelContext ctx) {
        ctx.enableDamage();
        ctx.shouldSkipPlayerUpdate(false);

        this.cycles = 0;
        this.phase = 0;
    }

    @Override
    public void updateLevel(final LevelContext ctx, final float dt) {
        final Game game = ctx.getGame();
        final Drifter drift = game.drifter;

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
