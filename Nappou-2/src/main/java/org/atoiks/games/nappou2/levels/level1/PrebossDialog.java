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
import org.atoiks.games.framework2d.ResourceManager;

import org.atoiks.games.nappou2.GameConfig;

import org.atoiks.games.nappou2.levels.ILevelState;
import org.atoiks.games.nappou2.levels.ILevelContext;

import org.atoiks.games.nappou2.entities.Message;

import static org.atoiks.games.nappou2.entities.Message.VerticalAlignment;
import static org.atoiks.games.nappou2.entities.Message.HorizontalAlignment;

/* package */ final class PrebossDialog implements ILevelState {

    private static final Message[] LINES = {
        new Message("ELLE.png", HorizontalAlignment.RIGHT, "ELLE", "Why are you here?"),
        new Message("LUMA.png", HorizontalAlignment.LEFT, "LUMA", "Oh you know, humans."),
        new Message("ELLE.png", HorizontalAlignment.RIGHT, "ELLE", "I no longer find joy in another's pain."),
        new Message("CAI.png", HorizontalAlignment.CENTER, "CAI", "Why so moody?"),
        new Message("ELLE.png", HorizontalAlignment.RIGHT, "ELLE", "..."),
        new Message("LUMA.png", HorizontalAlignment.LEFT, "LUMA", "Yeah, give me a few centuries and things will be back to normal!"),
        new Message("ELLE.png", HorizontalAlignment.RIGHT, "ELLE", "You haven't changed at all Luma!"),
        new Message(null      , HorizontalAlignment.RIGHT, "ELLE", "You took everything away from me. Do you know how much I suffered?"),
    };

    private static final int BOSS_LOOP = 1222000;

    private final ILevelState nextState;

    private int line;
    private boolean resetMsg;

    public PrebossDialog(ILevelState nextState) {
        this.nextState = nextState;
    }

    @Override
    public void enter(final ILevelContext ctx) {
        ctx.disableDamage();
        ctx.shouldSkipPlayerUpdate(true);
        ctx.getGame().clearBullets();

        this.line = 0;
        this.resetMsg = true;

        if (ResourceManager.<GameConfig>get("./game.cfg").bgm) {
            final Clip bgm = ResourceManager.get("/music/Level_One_Boss.wav");
            bgm.setMicrosecondPosition(0);
            bgm.start();
            bgm.setLoopPoints(BOSS_LOOP, -1);
            bgm.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    @Override
    public void updateLevel(final ILevelContext ctx, final float dt) {
        if (this.resetMsg) {
            this.resetMsg = false;
            ctx.displayMessage(LINES[this.line]);
        }

        if (Input.isKeyPressed(KeyEvent.VK_ENTER)) {
            if (++this.line < LINES.length) {
                this.resetMsg = true;
            } else {
                ctx.clearMessage();
                ctx.setState(nextState);
                return;
            }
        }
    }
}
