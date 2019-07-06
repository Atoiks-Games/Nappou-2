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

import org.atoiks.games.nappou2.GameConfig;

import org.atoiks.games.nappou2.levels.LevelState;
import org.atoiks.games.nappou2.levels.LevelContext;
import org.atoiks.games.nappou2.levels.AbstractDialogState;

import org.atoiks.games.nappou2.entities.Message;

import static org.atoiks.games.nappou2.entities.Message.HorizontalAlignment;

/* package */ final class PrebossDialog extends AbstractDialogState {

    private static final long serialVersionUID = 5132826077426353897L;

    private static final Message[] LINES = {
        new Message("Cryo", "*Player*?! What are you doing here?!?!"),
        new Message("Pyro", "Clearly the humans betrayed her like she betrayed us. Do you even remember us, traitor?"),
        new Message("CAI", "Nope. Not at all."),
        new Message("Cryo", "WE HAVE BEEN TRAPPED HERE FOR YEARS BECAUSE OF YOU TWO!!!!"),
        new Message("CAI", "Oh right, you are the one that yells."),
        new Message("Player", "Yeah, I remember. Once I fight the humans, everything will be back to normal."),
        new Message("Cryo", "YOU POWER HUNGRY IDIOT!!!!"),
        new Message("Pyro", "I concur."),
        new Message("Player", "Alright, I'm sorry! Is that what you wanted?"),
        new Message("Pyro", "Apology..."),
        new Message("Cryo", "DENIED!!!"),
    };

    private transient int line;

    public PrebossDialog(LevelState nextState) {
        super(nextState);
    }

    @Override
    public void enter(final LevelContext ctx) {
        super.enter(ctx);

        this.line = 0;
        if (ResourceManager.<GameConfig>get("./game.cfg").bgm) {
            final Clip bgm = ResourceManager.get("/music/Broken_Soul.wav");
            bgm.setMicrosecondPosition(0);
            bgm.start();
            bgm.loop(Clip.LOOP_CONTINUOUSLY);
        }
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
