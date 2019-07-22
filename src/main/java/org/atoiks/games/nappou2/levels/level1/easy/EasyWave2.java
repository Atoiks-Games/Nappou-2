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

import org.atoiks.games.nappou2.GameConfig;

import org.atoiks.games.nappou2.levels.LevelState;
import org.atoiks.games.nappou2.levels.LevelContext;

import org.atoiks.games.nappou2.spawner.FishSpawner;

import org.atoiks.games.nappou2.entities.Game;

import org.atoiks.games.nappou2.entities.enemy.Ripple;

public class EasyWave2 implements LevelState {

    private static final long serialVersionUID = 3774470404482934693L;

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
        if (cycles == 40) {
            final Game game = ctx.getGame();
            game.addEnemy(new Ripple(10, 375, -10, 20, 500, (float) Math.PI / 2));
            game.addSpawner(new FishSpawner(700, 10, 610, 0, 250, 3 * (float) Math.PI / 2, 100, false));
            game.addSpawner(new FishSpawner(50, 10, 610, 0, 250, 3 * (float) Math.PI / 2, 100, true));
        } else if (cycles > 40 && ctx.getGame().noMoreEnemies()) {
            ctx.setState(new EasyWave3());
            return;
        }
    }
}
