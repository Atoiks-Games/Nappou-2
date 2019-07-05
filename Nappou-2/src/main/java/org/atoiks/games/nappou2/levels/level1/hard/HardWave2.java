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

import javax.sound.sampled.Clip;

import org.atoiks.games.framework2d.ResourceManager;

import org.atoiks.games.nappou2.GameConfig;

import org.atoiks.games.nappou2.levels.ILevelState;
import org.atoiks.games.nappou2.levels.ILevelContext;

import org.atoiks.games.nappou2.entities.Game;

import org.atoiks.games.nappou2.entities.enemy.*;

import static org.atoiks.games.nappou2.Utils.mb1;
import static org.atoiks.games.nappou2.Utils.advancedMiniBomberEnemy;

import static org.atoiks.games.nappou2.levels.level1.Data.*;

public class HardWave2 implements ILevelState {

    private static final long serialVersionUID = 8563499939559109295L;

    private transient int cycles;

    private transient Clip bgm;

    @Override
    public void enter(final ILevelContext ctx) {
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
    public void updateLevel(final ILevelContext ctx, final float dt) {
        ++cycles;
        switch (cycles) {
            case 40: {
                final Game game = ctx.getGame();
                game.addEnemy(mb1(10, 225, -10, 20));
                game.addEnemy(mb1(10, 525, -10, 20));
                game.addEnemy(mb1(10, 375, -10, 20));
                break;
            }
            case 80:
            case 280:
            case 480:
            case 680:
            case 880:
            case 1080:
            case 1280:
            case 1480:
            case 1680: {
                final Game game = ctx.getGame();
                final int offset = (cycles / 4 / 5 - 4) / 5;
                game.addEnemy(advancedMiniBomberEnemy(1, w1eX[offset + 0], w1eY[offset + 0], 8, 1, w1eS[offset + 0]));
                game.addEnemy(advancedMiniBomberEnemy(1, w1eX[offset + 1], w1eY[offset + 1], 8, -1, w1eS[offset + 1]));
                break;
            }
            default:
                if (cycles > 1680 && ctx.getGame().noMoreEnemies()) {
                    ctx.setState(new HardWave3());
                    return;
                }
        }
    }
}
