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

package org.atoiks.games.nappou2.levels.level1;

import javax.sound.sampled.Clip;

import org.atoiks.games.framework2d.ResourceManager;

import org.atoiks.games.nappou2.levels.ILevelState;
import org.atoiks.games.nappou2.levels.ILevelContext;
import org.atoiks.games.nappou2.levels.AbstractDialogState;

import org.atoiks.games.nappou2.entities.Message;

import static org.atoiks.games.nappou2.entities.Message.HorizontalAlignment;

public final class PostbossDialog extends AbstractDialogState {

    private static final long serialVersionUID = 6378909470491402086L;

    private static final Message MESSAGE = new Message(
            "ELLE.png", HorizontalAlignment.RIGHT, "ELLE", "I just want to go home...");

    private transient boolean firstRun;

    public PostbossDialog(ILevelState nextState) {
        super(nextState);
    }

    @Override
    public void enter(final ILevelContext ctx) {
        super.enter(ctx);

        ResourceManager.<Clip>get("/music/Level_One_Boss.wav").stop();

        this.firstRun = true;
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
