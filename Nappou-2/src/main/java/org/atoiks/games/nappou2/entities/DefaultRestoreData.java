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

package org.atoiks.games.nappou2.entities;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Externalizable;

public final class DefaultRestoreData implements Externalizable {

    private static final long serialVersionUID = 7388399896699153942L;

    private int hp;
    private int score;

    public void restore(Game game) {
        game.player.getHpCounter().restoreTo(this.hp);
        game.player.getScoreCounter().restoreTo(this.score);
    }

    public void fetch(Game game) {
        this.hp = game.player.getHpCounter().getHp();
        this.score = game.player.getScoreCounter().getScore();
    }

    public void readExternal(ObjectInput s) throws IOException, ClassNotFoundException {
        this.hp = s.readInt();
        this.score = s.readInt();
    }

    public void writeExternal(ObjectOutput s) throws IOException {
        s.writeInt(this.hp);
        s.writeInt(this.score);
    }
}
