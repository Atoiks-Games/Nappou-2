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

package org.atoiks.games.nappou2.levels.level1.easy;

import javax.sound.sampled.Clip;

import org.atoiks.games.framework2d.ResourceManager;

import org.atoiks.games.nappou2.Drifter;

import org.atoiks.games.nappou2.levels.LevelState;
import org.atoiks.games.nappou2.levels.LevelContext;
import org.atoiks.games.nappou2.levels.SaveScoreState;

import org.atoiks.games.nappou2.entities.enemy.Level1Easy;

import org.atoiks.games.nappou2.levels.level1.PostbossDialog;

public class EasyBossWave implements LevelState {

    private static final long serialVersionUID = 1914901384100845861L;

    private final SaveScoreState exitState;
    private final float initialClamp;
    private final float clampDx;
    private final float clampDy;

    private transient int cycles;
    private transient int phase;

    private transient Clip bgm;

    public EasyBossWave() {
        this.exitState = new SaveScoreState(0);
        this.initialClamp = 50;
        this.clampDx = 50;
        this.clampDy = 50;
    }

    @Override
    public void enter(final LevelContext ctx) {
        this.bgm = ResourceManager.get("/music/Level_One_Boss.wav");

        ctx.enableDamage();
        ctx.shouldSkipPlayerUpdate(false);

        this.cycles = 0;
        this.phase = 0;

        final Drifter drift = ctx.getGame().drifter;
        drift.accelY = -20;
        drift.accelX = 20;
        drift.clampDx(0, this.initialClamp);

        ctx.getGame().addEnemy(new Level1Easy(300, 375, -10, 20));
    }

    @Override
    public void exit() {
        this.bgm.stop();
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
