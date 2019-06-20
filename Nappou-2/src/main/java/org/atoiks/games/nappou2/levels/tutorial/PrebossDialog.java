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

import org.atoiks.games.framework2d.Input;
import org.atoiks.games.framework2d.ResourceManager;

import org.atoiks.games.nappou2.SaveData;

import org.atoiks.games.nappou2.levels.ILevelContext;
import org.atoiks.games.nappou2.levels.ILevelCheckpoint;
import org.atoiks.games.nappou2.levels.AbstractDialogState;

import org.atoiks.games.nappou2.entities.Message;

import static org.atoiks.games.nappou2.entities.Message.HorizontalAlignment;

/* package */ final class PrebossDialog extends AbstractDialogState implements ILevelCheckpoint {

    private static final Message[] LINES = {
        new Message("CAI.png", HorizontalAlignment.RIGHT, "CAI", "Good morning! You're dead!"),
        new Message("LUMA.png", HorizontalAlignment.LEFT, "LUMA", "What?"),
        new Message("CAI.png", HorizontalAlignment.RIGHT, "CAI", "Just kidding! You're just in the void. Which is arguably worse."),
        new Message("LUMA.png", HorizontalAlignment.LEFT, "LUMA", "What?!"),
        new Message("CAI.png", HorizontalAlignment.RIGHT, "CAI", "Yep, the humans threw us in just like that."),
        new Message("LUMA.png", HorizontalAlignment.LEFT, "LUMA", "Cai, you don't understand, we have to get out of here."),
        new Message("CAI.png", HorizontalAlignment.RIGHT, "CAI", "Not before you finish your daily combat exercises!"),
        new Message("LUMA.png", HorizontalAlignment.LEFT, "LUMA", "Cai, now's not that time."),
        new Message("CAI.png", HorizontalAlignment.RIGHT, "CAI", "There's always time for senseless violence!"),
    };

    private transient int line;

    public PrebossDialog() {
        super(new BossWave());
    }

    @Override
    public void enter(final ILevelContext ctx) {
        super.enter(ctx);

        this.line = 0;
        ResourceManager.<Clip>get("/music/Awakening.wav").stop();
        ResourceManager.<SaveData>get("./saves.dat").setCheckpoint(this);
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
