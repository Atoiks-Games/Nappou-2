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

package org.atoiks.games.nappou2;

public final class HitpointCounter {

    // The lowest value is capped at zero
    private int hitpoints;
    private boolean ignoreChangeFlag;

    public int getHp() {
        return this.hitpoints;
    }

    public void restoreTo(int hp) {
        this.hitpoints = Math.max(0, hp);
    }

    public HitpointCounter changeBy(int delta) {
        if (!ignoreChangeFlag) {
            this.hitpoints = Math.max(0, this.hitpoints + delta);
        }
        return this;
    }

    public boolean hasHpRemaining() {
        return this.hitpoints > 0;
    }

    public boolean isOutOfHp() {
        return this.hitpoints <= 0;
    }

    public boolean isHpChangeIgnored() {
        return this.ignoreChangeFlag;
    }

    public void setIgnoreHpChange(boolean flag) {
        this.ignoreChangeFlag = flag;
    }
}
