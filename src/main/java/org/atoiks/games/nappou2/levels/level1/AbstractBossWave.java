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

import org.atoiks.games.nappou2.Vector2;
import org.atoiks.games.nappou2.Drifter;
import org.atoiks.games.nappou2.Difficulty;

import org.atoiks.games.nappou2.levels.LevelState;
import org.atoiks.games.nappou2.levels.LevelContext;
import org.atoiks.games.nappou2.levels.SaveScoreState;

import org.atoiks.games.nappou2.entities.Game;

public abstract class AbstractBossWave implements LevelState {

    private static final long serialVersionUID = -8597002284637792362L;

    private final SaveScoreState exitState;
    private final float initialClamp;
    private final float clampDx;
    private final float clampDy;

    private transient int cycles;
    private transient int phase;

    protected AbstractBossWave(SaveScoreState exitState, float initialClamp, float clampDx, float clampDy) {
        this.exitState = exitState;
        this.initialClamp = initialClamp;
        this.clampDx = clampDx;
        this.clampDy = clampDy;
    }

    @Override
    public void enter(final LevelContext ctx) {
        ctx.enableDamage();
        ctx.shouldSkipPlayerUpdate(false);

        this.cycles = 0;
        this.phase = 0;

        final Drifter drift = ctx.getGame().drifter;
        drift.accelY = -20;
        drift.accelX = 20;
        drift.clampDx(0, this.initialClamp);
    }

    @Override
    public void updateLevel(final LevelContext ctx, final float dt) {
        if (++cycles % 4000 == 0) {
            final Drifter drift = ctx.getGame().drifter;
            switch (++phase) {
                case 0:
                    drift.accelY = -20;
                    drift.accelX = 20;
                    drift.clampDx(0, this.clampDx);
                    break;
                case 1:
                    drift.accelX = -20;
                    drift.accelY = 20;
                    drift.clampDy(0, this.clampDy);
                    break;
                case 2:
                    drift.accelY = -20;
                    drift.clampDx(-this.clampDx, 0);
                    break;
                case 3:
                    drift.accelX = 20;
                    drift.clampDy(-this.clampDy, 0);
                    break;
            }
        }

        if (cycles > 40 && ctx.getGame().noMoreEnemies()) {
            ctx.setState(new PostbossDialog(this.exitState));
            return;
        }
    }
}
