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

import org.atoiks.games.nappou2.entities.Game;

import org.atoiks.games.nappou2.levels.ILevelState;
import org.atoiks.games.nappou2.levels.ILevelContext;

import org.atoiks.games.nappou2.entities.enemy.CAITutorial;

import static org.atoiks.games.nappou2.scenes.RefittedGameScene.GAME_BORDER;

/* package */ final class BossWave implements ILevelState {

    public static final BossWave INSTANCE = new BossWave();

    private boolean firstRun;

    private BossWave() {
    }

    @Override
    public void enter(final ILevelContext ctx) {
        ctx.enableDamage();
        ctx.shouldSkipPlayerUpdate(false);

        this.firstRun = true;
    }

    @Override
    public void updateLevel(final ILevelContext ctx, final float dt) {
        final Game game = ctx.getGame();
        if (game.noMoreEnemies()) {
            if (firstRun) {
                firstRun = false;
                game.addEnemy(new CAITutorial(50, 375, -10, 20));
            } else {
                ctx.setState(PostbossDialog.INSTANCE);
                return;
            }
        }
    }
}
