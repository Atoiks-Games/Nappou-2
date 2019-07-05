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

import org.atoiks.games.nappou2.levels.ILevelState;
import org.atoiks.games.nappou2.levels.ILevelContext;

import org.atoiks.games.nappou2.entities.Game;

import org.atoiks.games.nappou2.entities.enemy.*;

import static org.atoiks.games.nappou2.Utils.dropEnemy;
import static org.atoiks.games.nappou2.Utils.advancedMiniBomberEnemy;

import static org.atoiks.games.nappou2.levels.level1.Data.*;

public class InsaneWave1 implements ILevelState {

    private static final long serialVersionUID = -2945314718064501302L;

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
            case 40:
            case 140:
            case 240:
            case 340:
            case 440:
            case 540:
            case 640:
            case 740:
            case 840:
            case 940: {
                final Game game = ctx.getGame();
                game.addEnemy(dropEnemy(1, 30, -10, 8, false));
                game.addEnemy(dropEnemy(1, 720, -10, 8, false));
                game.addEnemy(dropEnemy(1, 50, 610, 8, true));
                game.addEnemy(dropEnemy(1, 700, 610, 8, true));
                final int offset = (cycles / 4 / 5 - 4) / 5;
                game.addEnemy(advancedMiniBomberEnemy(1, w1eX[offset + 0], w1eY[offset + 0], 8, 1, 100));
                game.addEnemy(advancedMiniBomberEnemy(1, w1eX[offset + 1], w1eY[offset + 1], 8, -1, 100));
                break;
            }
        }
        if (cycles > 940 && ctx.getGame().noMoreEnemies()) {
            ctx.setState(new InsaneWave2());
        }
    }
}
