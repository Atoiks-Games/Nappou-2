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

package org.atoiks.games.nappou2.levels.level2;

import javax.sound.sampled.Clip;

import org.atoiks.games.framework2d.ResourceManager;

import org.atoiks.games.nappou2.levels.LevelState;
import org.atoiks.games.nappou2.levels.LevelContext;
import org.atoiks.games.nappou2.levels.AbstractDialogState;

import org.atoiks.games.nappou2.entities.Message;

import static org.atoiks.games.nappou2.entities.Message.HorizontalAlignment;

/* package */ final class PostbossDialog extends AbstractDialogState {

    private static final long serialVersionUID = 1935177017424875199L;

    private static final Message[] LINES = {
        new Message("Cryo", "IS THAT ALL YOU DO?!?! SHOOT AT ANYTHING THAT MOVES?!?!"),
        new Message("CAI", "Pretty much!"),
        new Message("Pyro", "Come brother, they are not worth our time."),
    };

    private transient int line;

    public PostbossDialog(LevelState nextState) {
        super(nextState);
    }

    @Override
    public void enter(final LevelContext ctx) {
        super.enter(ctx);

        this.line = 0;
        ResourceManager.<Clip>get("/music/Broken_Soul.wav").stop();
    }

    @Override
    public boolean hasNext() {
        return line < LINES.length;
    }

    @Override
    public Message next() {
        return LINES[line++];
    }
}
