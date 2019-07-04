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

import javax.sound.sampled.Clip;

import org.atoiks.games.framework2d.ResourceManager;

import org.atoiks.games.nappou2.SaveData;

import org.atoiks.games.nappou2.levels.ILevelState;
import org.atoiks.games.nappou2.levels.ILevelContext;
import org.atoiks.games.nappou2.levels.AbstractDialogState;

import org.atoiks.games.nappou2.entities.Game;
import org.atoiks.games.nappou2.entities.Message;
import org.atoiks.games.nappou2.entities.DefaultRestoreData;

import static org.atoiks.games.nappou2.scenes.GameLevelScene.HEIGHT;
import static org.atoiks.games.nappou2.scenes.GameLevelScene.GAME_BORDER;

import static org.atoiks.games.nappou2.entities.Message.HorizontalAlignment;

/* package */ final class PostbossDialog extends AbstractDialogState implements ILevelState {

    private static final long serialVersionUID = 942221556144923194L;

    private static final Message MESSAGE = new Message(
            "CAI.png", HorizontalAlignment.RIGHT, "CAI", "Alright now we are ready for whomever we come across!");

    private final DefaultRestoreData data = new DefaultRestoreData();

    private transient boolean firstRun;

    public PostbossDialog(ILevelState nextState) {
        super(new EndOfTutorialState(nextState));
    }

    @Override
    public void restore(final ILevelContext ctx) {
        final Game game = ctx.getGame();
        game.player.setPosition(GAME_BORDER / 2, HEIGHT / 6 * 5);
        this.data.restore(game);
    }

    @Override
    public void enter(final ILevelContext ctx) {
        super.enter(ctx);

        final Game game = ctx.getGame();
        this.data.fetch(game);

        this.firstRun = true;

        ResourceManager.<SaveData>get("./saves.dat").setCheckpoint(this);
    }

    @Override
    public boolean hasNext() {
        return this.firstRun;
    }

    @Override
    public Message next() {
        this.firstRun = false;
        return MESSAGE;
    }
}
