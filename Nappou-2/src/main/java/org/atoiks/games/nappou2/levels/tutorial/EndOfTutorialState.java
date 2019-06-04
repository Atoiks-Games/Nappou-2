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

import org.atoiks.games.framework2d.SceneManager;
import org.atoiks.games.framework2d.ResourceManager;

import org.atoiks.games.nappou2.SaveData;
import org.atoiks.games.nappou2.Difficulty;

import org.atoiks.games.nappou2.levels.ILevelState;
import org.atoiks.games.nappou2.levels.ILevelContext;

import org.atoiks.games.nappou2.levels.level1.*;

/* package */ class EndOfTutorialState implements ILevelState {

    public static final EndOfTutorialState INSTANCE = new EndOfTutorialState();

    private EndOfTutorialState() {
    }

    @Override
    public void enter(final ILevelContext ctx) {
        // Reset context so game can actually be played
        // (the stages themselves do not reset the context)
        ctx.enableDamage();
        ctx.shouldSkipPlayerUpdate(false);

        ctx.getGame().cleanup();
    }

    @Override
    public void updateLevel(final ILevelContext ctx, final float dt) {
        final Difficulty diff = ResourceManager.<SaveData>get("./saves.dat").getDif();
        final ILevelState state;
        switch (diff) {
            case EASY:
                state = new Easy();
                break;
            case NORMAL:
                state = new Normal();
                break;
            case HARD:
                state = new Hard();
                break;
            case INSANE:
                state = new Insane();
                break;
            default:
                throw new AssertionError("Unhandled difficulty: " + diff);
        }

        ctx.setState(state);
        return;
    }
}
