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

import java.awt.Color;
import java.awt.event.KeyEvent;

import javax.sound.sampled.Clip;

import org.atoiks.games.framework2d.Input;
import org.atoiks.games.framework2d.SceneManager;
import org.atoiks.games.framework2d.ResourceManager;

import org.atoiks.games.nappou2.levels.ILevelState;
import org.atoiks.games.nappou2.levels.ILevelContext;

import org.atoiks.games.nappou2.entities.Message;

import static org.atoiks.games.nappou2.entities.Message.VerticalAlignment;
import static org.atoiks.games.nappou2.entities.Message.HorizontalAlignment;

/* package */ final class PostbossDialog implements ILevelState {

    private static final Message MESSAGE = new Message(
            "ELLE.png", HorizontalAlignment.RIGHT, "ELLE", "I just want to go home...");

    private final ILevelState nextState;

    private boolean firstRun;

    public PostbossDialog(ILevelState nextState) {
        this.nextState = nextState;
    }

    @Override
    public void enter(final ILevelContext ctx) {
        ctx.disableDamage();
        ctx.shouldSkipPlayerUpdate(true);
        ctx.getGame().clearBullets();

        ResourceManager.<Clip>get("/music/Level_One_Boss.wav").stop();

        this.firstRun = true;
    }

    @Override
    public void updateLevel(final ILevelContext ctx, final float dt) {
        if (this.firstRun) {
            this.firstRun = false;
            ctx.displayMessage(MESSAGE);
        }

        if (Input.isKeyPressed(KeyEvent.VK_ENTER)) {
            ctx.setState(nextState);
            return;
        }
    }
}
