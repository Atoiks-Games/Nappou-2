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

package org.atoiks.games.nappou2.levels.tutorial;

import java.awt.Color;

import javax.sound.sampled.Clip;

import org.atoiks.games.framework2d.IGraphics;
import org.atoiks.games.framework2d.ResourceManager;

import org.atoiks.games.framework2d.resource.Font;

import org.atoiks.games.nappou2.GameConfig;

import org.atoiks.games.nappou2.entities.Game;

import org.atoiks.games.nappou2.levels.LevelState;
import org.atoiks.games.nappou2.levels.LevelContext;

import static org.atoiks.games.nappou2.Utils.singleShotEnemy;

import static org.atoiks.games.nappou2.scenes.GameLevelScene.HEIGHT;

/* package */ final class SingleShotWave implements LevelState {

    private static final long serialVersionUID = -8155795240502715804L;

    private final LevelState nextState;

    private transient final Font font75;

    private transient String fireKey;

    public SingleShotWave(LevelState nextState) {
        this.nextState = nextState;

        final Font fnt = ResourceManager.get("/Logisoso.ttf");
        this.font75 = fnt.deriveSize(75f);
    }

    @Override
    public void enter(final LevelContext ctx) {
        final GameConfig cfg = ResourceManager.get("./game.cfg");
        this.fireKey = cfg.keymap.getFireKeystr();

        if (cfg.bgm) {
            ResourceManager.<Clip>get("/music/Awakening.wav").start();
        }
    }

    @Override
    public void exit() {
        ResourceManager.<Clip>get("/music/Awakening.wav").stop();
    }

    @Override
    public void renderBackground(final IGraphics g) {
        g.setColor(Color.green);
        this.font75.renderText(g, this.fireKey, 20, HEIGHT - 30);
    }

    @Override
    public void updateLevel(final LevelContext ctx, final float dt) {
        final Game game = ctx.getGame();
        if (game.noMoreEnemies()) {
            if (game.player.getScoreCounter().getScore() < 6) {
                game.addEnemy(singleShotEnemy(1, 250, -10, 8, false));
                game.addEnemy(singleShotEnemy(1, 500, -10, 8, false));
            } else {
                ctx.setState(new ShieldTesterWave(nextState));
                return;
            }
        }
    }
}
