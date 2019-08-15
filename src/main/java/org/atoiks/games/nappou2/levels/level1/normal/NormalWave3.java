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

package org.atoiks.games.nappou2.levels.level1.normal;

import javax.sound.sampled.Clip;

import org.atoiks.games.framework2d.ResourceManager;

import org.atoiks.games.nappou2.GameConfig;

import org.atoiks.games.nappou2.levels.LevelState;
import org.atoiks.games.nappou2.levels.LevelContext;

import org.atoiks.games.nappou2.entities.Game;

import org.atoiks.games.nappou2.entities.enemy.*;

import static org.atoiks.games.nappou2.Utils.mb1;
import static org.atoiks.games.nappou2.Utils.circularPathEnemy;

import static org.atoiks.games.nappou2.levels.level1.Data.*;

public class NormalWave3 implements LevelState {

    private static final long serialVersionUID = -8408611020851521375L;

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
            case 40:
                ctx.getGame().addEnemy(mb1(10, 375, -10, 20));
                break;
            case 80: {
                final Game game = ctx.getGame();
                game.addEnemy(circularPathEnemy(1, BOUNDARY_750_50, 8, 1, 0.25f, 1, 100));
                game.addEnemy(circularPathEnemy(1, BOUNDARY_0_50, 8, -1, 0.25f, 3, 100));
                break;
            }
            case 680: {
                final Game game = ctx.getGame();
                game.addEnemy(circularPathEnemy(1, BOUNDARY_750_0, 8, 1, 0.25f, 1, 100));
                game.addEnemy(circularPathEnemy(1, BOUNDARY_0_0, 8, -1, 0.25f, 3, 100));
                game.addEnemy(circularPathEnemy(1, BOUNDARY_750_600, 8, -1, 0.25f, 1, 100));
                game.addEnemy(circularPathEnemy(1, BOUNDARY_0_600, 8, 1, 0.25f, 3, 100));
                break;
            }
        }
        if (cycles > 680 && ctx.getGame().noMoreEnemies()) {
            ctx.setState(new NormalWave4());
            return;
        }
    }
}
