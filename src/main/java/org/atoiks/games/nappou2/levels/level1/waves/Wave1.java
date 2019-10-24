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

import static org.atoiks.games.nappou2.spawner.FishSpawner.fishPart;

import static org.atoiks.games.nappou2.levels.level1.Data.*;

public class Wave1 extends AbstractGameWave {

    private static final long serialVersionUID = 9135918145681392179L;

    public Wave1() {
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
            case 50:
            case 100:
            case 150:
            case 200:
            case 250:
            case 300:
            case 350:
            case 400:
            case 450:
            case 500:
            case 550:
            case 600:
            case 650:
            case 700:
            case 750:
            case 800:
            case 850:
            case 900:
            case 950:
            case 1000:
            case 1050:
            case 1100:
            case 1150:
            case 1200:
            case 1250:
            case 1300:
            case 1350:
            case 1400:
            case 1450:
            case 1500:
            case 1550:
            case 1600:
            case 1650:
            case 1700:
            case 1750:
            case 1800:
                ctx.getGame().addEnemy(fishPart(1, 750 * rnd.nextFloat(), 615, 20 * rnd.nextFloat() + 10, 900 * rnd.nextFloat() + 100, 3 * (float) Math.PI / 2, 900 * rnd.nextFloat() + 100, 10, rnd.nextBoolean()));
                break;
            default:
                if (cycles > 1800 && ctx.getGame().noMoreEnemies()) {
                    ctx.setState(new Wave2());
                    return;
                }
        }
    }

    @Override
    public Stage getAssociatedLevel() {
        return new Stage();
    }
}
