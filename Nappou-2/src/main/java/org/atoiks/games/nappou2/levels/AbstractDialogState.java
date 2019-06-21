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

import java.awt.event.KeyEvent;

import java.util.Iterator;

import org.atoiks.games.framework2d.Input;

import org.atoiks.games.nappou2.entities.Message;

public abstract class AbstractDialogState implements ILevelState, Iterator<Message> {

    private static final long serialVersionUID = 7680013812202865154L;

    private final ILevelState nextState;

    private transient boolean fetchMessage;

    public AbstractDialogState(ILevelState nextState) {
        this.nextState = nextState;
    }

    @Override
    public void enter(final ILevelContext ctx) {
        ctx.disableDamage();
        ctx.shouldSkipPlayerUpdate(true);
        ctx.getGame().clearBullets();

        this.fetchMessage = true;
    }

    @Override
    public void updateLevel(final ILevelContext ctx, final float dt) {
        if (this.fetchMessage) {
            if (this.hasNext()) {
                this.fetchMessage = false;
                ctx.displayMessage(this.next());
            } else {
                ctx.clearMessage();
                ctx.setState(nextState);
                return;
            }
        }

        if (Input.isKeyPressed(KeyEvent.VK_ENTER)) {
            this.fetchMessage = true;
        }
    }
}
