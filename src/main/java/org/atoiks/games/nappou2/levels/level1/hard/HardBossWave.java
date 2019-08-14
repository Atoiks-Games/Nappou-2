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

package org.atoiks.games.nappou2.levels.level1.hard;

import org.atoiks.games.nappou2.Difficulty;

import org.atoiks.games.nappou2.levels.LevelContext;
import org.atoiks.games.nappou2.levels.SaveScoreState;

import org.atoiks.games.nappou2.entities.enemy.Level1Hard;

import org.atoiks.games.nappou2.levels.level1.AbstractBossWave;

public class HardBossWave extends AbstractBossWave {

    private static final long serialVersionUID = -963938928723781698L;

    public HardBossWave() {
        super(new SaveScoreState(0, Difficulty.HARD), 200, 200, 200);
    }

    @Override
    public void enter(final LevelContext ctx) {
        super.enter(ctx);

        ctx.getGame().addEnemy(new Level1Hard(300, 375, -10, 20));
    }
}
