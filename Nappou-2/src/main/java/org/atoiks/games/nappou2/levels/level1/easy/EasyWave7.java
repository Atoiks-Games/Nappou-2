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

import org.atoiks.games.nappou2.spawner.BIGFishSpawner;
import org.atoiks.games.nappou2.spawner.FishSpawner;
import javax.sound.sampled.Clip;

import org.atoiks.games.framework2d.ResourceManager;

import org.atoiks.games.nappou2.Vector2;
import org.atoiks.games.nappou2.SaveData;
import org.atoiks.games.nappou2.GameConfig;

import org.atoiks.games.nappou2.levels.LevelState;
import org.atoiks.games.nappou2.levels.LevelContext;

import org.atoiks.games.nappou2.entities.Game;
import org.atoiks.games.nappou2.entities.DefaultRestoreData;

import org.atoiks.games.nappou2.entities.enemy.*;
import org.atoiks.games.nappou2.entities.bullet.*;

import org.atoiks.games.nappou2.entities.bullet.factory.RayInfo;

import org.atoiks.games.nappou2.pathway.*;
import org.atoiks.games.nappou2.pattern.*;

import org.atoiks.games.nappou2.levels.level1.PrebossDialog;

import static org.atoiks.games.nappou2.levels.level1.Data.*;

import static org.atoiks.games.nappou2.scenes.GameLevelScene.HEIGHT;
import static org.atoiks.games.nappou2.scenes.GameLevelScene.GAME_BORDER;

public class EasyWave7 implements LevelState {

    private static final long serialVersionUID = 4818290874443924057L;

    private static final RayInfo WAVE7_RAY_INFO = new RayInfo(25, 5, 500);

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
        final Game game = ctx.getGame();

        ++cycles;
        switch (cycles) {
            case 100:
            case 200:
            case 300:
            case 400:
            case 500:
                PathwayEnemy e = new PathwayEnemy(1, 1, new FixedVelocity(new Vector2(-10, 10), new Vector2(100, 0)), new RandomDropPattern(3, WAVE7_RAY_INFO));
                e.setRadius(8);
                game.addEnemy(e);
                break;
            case 190:
            case 250:
            case 310:
            case 370:
            case 430:
            case 490:
            case 550:
                e = new PathwayEnemy(1, 1, new FixedVelocity(new Vector2(760, 50), new Vector2(-250, 0)), new RandomDropPattern(3, WAVE7_RAY_INFO));
                e.setRadius(13);
                game.addEnemy(e);
                break;
            case 325:
            case 425:
            case 525:
            case 625:
            case 725:
                e = new PathwayEnemy(1, 1, new FixedVelocity(new Vector2(-10, 75), new Vector2(300, 0)), new RandomDropPattern(3, WAVE7_RAY_INFO));
                e.setRadius(25);
                game.addEnemy(e);
            case 375:
            case 475:
            case 575:
            case 675:
            case 775:
                e = new PathwayEnemy(1, 1, new FixedVelocity(new Vector2(760, 25), new Vector2(-150, 0)), new RandomDropPattern(3, WAVE7_RAY_INFO));
                e.setRadius(6);
                game.addEnemy(e);
                break;
        }
        if (cycles > 775 && game.noMoreEnemies()) {
            ctx.setState(new EasyWave8());
            return;
        }
    }
}
