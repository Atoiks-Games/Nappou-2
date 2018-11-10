/**
 *  Nappou-2
 *  Copyright (C) 2017-2018  Atoiks-Games <atoiks-games@outlook.com>
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

public final class FixedTimeShield extends TimeBasedShield {

    private static final long serialVersionUID = 259728713501591561L;

    private boolean relocateX = false;
    private boolean relocateY = false;

    public FixedTimeShield(float timeout, float reloadTime, float r) {
        super(timeout, reloadTime, r);
    }

    @Override
    public void activate() {
        if (!active) {
            relocateX = relocateY = true;
        }
    }

    @Override
    public void setX(float x) {
        if (relocateX) {
            super.setX(x);
            relocateX = false;
            tryActivateShieldWhenReady();
        }
    }

    @Override
    public void setY(float y) {
        if (relocateY) {
            super.setY(y);
            relocateY = false;
            tryActivateShieldWhenReady();
        }
    }

    private void tryActivateShieldWhenReady() {
        // only activate shield when both x, y
        // components have been relocated
        if (!relocateX && !relocateY) {
            super.activate();
        }
    }
}
