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

import org.atoiks.games.nappou2.levels.LevelContext;

import org.atoiks.games.nappou2.levels.level1.insane.InsaneWave1;

public final class Insane extends AbstractWave0 {

    private static final long serialVersionUID = -5655492343453489256L;

    private transient int cycles;

    @Override
    public void enter(final LevelContext ctx) {
        super.enter(ctx);
        this.cycles = 0;
    }

    @Override
    public void updateLevel(final LevelContext ctx, final float dt) {
        ++cycles;
        switch (cycles) {
            case 20:
            case 40:
            case 60:
            case 80:
            case 100:
            case 120:
            case 140:
            case 160:
            case 180:
            case 200:
                this.singleShotEnemiesTopDown(ctx.getGame(), cycles / 4 * 5);
                break;
            case 300:
            case 400:
            case 500:
            case 600:
                this.dropAndCircularPathEnemies(ctx.getGame());
                break;
            case 620:
            case 640:
            case 660:
            case 680:
            case 700:
            case 720:
            case 740:
            case 760:
            case 780:
            case 800:
                this.singleShotEnemiesTopDown(ctx.getGame(), (cycles / 4 / 5 - 30) * 25);
                break;
            case 900:
            case 1000:
            case 1100:
            case 1200:
                this.dropAndCircularPathEnemies(ctx.getGame());
                break;
            case 1220:
            case 1240:
            case 1260:
            case 1280:
            case 1300:
            case 1320:
            case 1340:
            case 1360:
            case 1380:
            case 1400:
                this.singleShotEnemiesTopDown(ctx.getGame(), (cycles / 4 / 5 - 40) * 25);
                break;
            case 1500:
            case 1600:
            case 1700:
            case 1800:
                this.dropAndCircularPathEnemies(ctx.getGame());
                break;
        }
        if (cycles > 1800 && ctx.getGame().noMoreEnemies()) {
            ctx.setState(new InsaneWave1());
        }
    }
}
