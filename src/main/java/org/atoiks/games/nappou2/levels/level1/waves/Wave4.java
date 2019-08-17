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

package org.atoiks.games.nappou2.levels.level1.waves;

import javax.sound.sampled.Clip;

import org.atoiks.games.nappou2.levels.LevelContext;
import org.atoiks.games.nappou2.levels.AbstractGameWave;

import org.atoiks.games.nappou2.levels.level1.Stage;

import org.atoiks.games.nappou2.entities.Game;

import static org.atoiks.games.nappou2.Utils.streamBeam;

import static org.atoiks.games.nappou2.levels.level1.Data.LEVEL_LOOP;

public class Wave4 extends AbstractGameWave {

    private static final long serialVersionUID = -5316067922477683583L;

    public Wave4() {
        super("/music/Level_One.wav");
    }

    @Override
    protected void configureBgm(Clip bgm) {
        super.configureBgm(bgm);
        bgm.setLoopPoints(LEVEL_LOOP, -1);
    }

    @Override
    public void updateLevel(final LevelContext ctx, final float dt) {
        switch (++cycles) {
            case 40:
            case 440:
            case 840:
            case 1240:
            case 1640:
            case 2040: {
                final Game game = ctx.getGame();
                game.addEnemy(streamBeam(1, 10, 610, 8, true));
                game.addEnemy(streamBeam(1, 375, -10, 8, false));
                game.addEnemy(streamBeam(1, 740, 610, 8, true));
                break;
            }
            default:
                if (cycles > 2040 && ctx.getGame().noMoreEnemies()) {
                    ctx.setState(new Wave5());
                    return;
                }
        }
    }

    @Override
    public Stage getAssociatedLevel() {
        return new Stage();
    }
}
