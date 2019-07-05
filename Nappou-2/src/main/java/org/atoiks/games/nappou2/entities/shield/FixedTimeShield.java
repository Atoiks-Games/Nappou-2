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

package org.atoiks.games.nappou2.entities.shield;

import org.atoiks.games.nappou2.Vector2;

public final class FixedTimeShield extends TimeBasedShield {

    private static final long serialVersionUID = 259728713501591561L;

    private transient boolean relocate;

    public FixedTimeShield(float timeout, float reloadTime, float r) {
        super(timeout, reloadTime, r);
    }

    @Override
    public void activate() {
        if (!active) {
            relocate = true;
        }
    }

    @Override
    public void setPosition(final Vector2 position) {
        if (relocate) {
            super.setPosition(position);
            relocate = false;
            super.activate();
        }
    }

    @Override
    public FixedTimeShield copy() {
        final FixedTimeShield s = new FixedTimeShield(getTimeout(), getReloadTime(), r);
        s.setColor(this.color);
        return s;
    }
}
