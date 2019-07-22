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

package org.atoiks.games.nappou2.levels.level1.insane;

import javax.sound.sampled.Clip;

import org.atoiks.games.framework2d.ResourceManager;

import org.atoiks.games.nappou2.GameConfig;

import org.atoiks.games.nappou2.levels.LevelState;
import org.atoiks.games.nappou2.levels.LevelContext;

import org.atoiks.games.nappou2.entities.Game;

import org.atoiks.games.nappou2.entities.enemy.*;

import static org.atoiks.games.nappou2.Utils.mb1;
import static org.atoiks.games.nappou2.Utils.altMb1;
import static org.atoiks.games.nappou2.Utils.advancedMiniBomberEnemy;

import static org.atoiks.games.nappou2.levels.level1.Data.*;

public class InsaneWave2 implements LevelState {

    private static final long serialVersionUID = -2410590536620961387L;

    private transient int cycles;

    private transient Clip bgm;

    @Override
    public void enter(final LevelContext ctx) {
        this.cycles = 0;

        this.bgm = ResourceManager.get("/music/Level_One.wav");
        if (ResourceManager.<GameConfig>get("./game.cfg").bgm) {
            bgm.start();
        }
    }

    @Override
    public void exit() {
        bgm.stop();
    }

    @Override
    public void updateLevel(final LevelContext ctx, final float dt) {
        ++cycles;
        switch (cycles) {
            case 40: {
                final Game game = ctx.getGame();
                game.addEnemy(mb1(10, 225, -10, 20));
                game.addEnemy(mb1(10, 525, -10, 20));
                game.addEnemy(altMb1(10, 375, -10, 20));
                break;
            }
            case 80:
            case 280:
            case 380:
            case 480:
            case 580:
            case 680:
            case 780:
            case 880:
            case 980: {
                final Game game = ctx.getGame();
                final int offset = (cycles / 4 / 5 - 4) / 5;
                game.addEnemy(advancedMiniBomberEnemy(1, w1eX[offset + 0], w1eY[offset + 0], 8, 1, 100));
                game.addEnemy(advancedMiniBomberEnemy(1, w1eX[offset + 1], w1eY[offset + 1], 8, -1, 100));
                break;
            }
            case 1000:
            case 1100:
            case 1200:
            case 1300:
                ctx.getGame().addEnemy(altMb1(10, 375, -10, 20));
                break;
        }
        if (cycles > 1300 && ctx.getGame().noMoreEnemies()) {
            ctx.setState(new InsaneWave3());
        }
    }
}
