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

package org.atoiks.games.nappou2.levels;

import org.atoiks.games.framework2d.SceneManager;

import org.atoiks.games.nappou2.Difficulty;

import org.atoiks.games.nappou2.scenes.TitleScene;
import org.atoiks.games.nappou2.scenes.SaveHighscoreScene;

/**
 * State to transition to when it seems like a good place to prompt for a high
 * score save.
 */
public final class SaveScoreState implements ILevelState {

    private final int levelId;
    private final Difficulty diff;

    public SaveScoreState(int levelId, Difficulty diff) {
        this.levelId = levelId;
        this.diff = diff;
    }

    @Override
    public void updateLevel(ILevelContext ctx, float dt) {
        SceneManager.swapScene(new TitleScene());
        SceneManager.pushScene(new SaveHighscoreScene(
                diff,
                levelId,
                ctx.getGame().getScore()));
    }
}
