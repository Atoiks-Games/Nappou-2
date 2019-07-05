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

import org.atoiks.games.nappou2.levels.ILevelContext;

import org.atoiks.games.nappou2.levels.level1.normal.NormalWave1;

public final class Normal extends AbstractWave0 {

    private static final long serialVersionUID = -8000599825518660858L;

    private transient int cycles;

    @Override
    public void enter(final ILevelContext ctx) {
        super.enter(ctx);
        this.cycles = 0;
    }

    @Override
    public void updateLevel(final ILevelContext ctx, final float dt) {
        switch (++cycles) {
            case 40:
            case 80:
            case 120:
            case 160:
            case 200:
                this.singleShotEnemiesDownwards(ctx.getGame(), cycles * 5 / 4);
                break;
            case 220:
                this.dropEnemies(ctx.getGame());
                break;
            case 600:
                this.circularPathEnemies(ctx.getGame());
                break;
        }
        if (cycles > 600 && ctx.getGame().noMoreEnemies()) {
            ctx.setState(new NormalWave1());
            return;
        }
    }
}
