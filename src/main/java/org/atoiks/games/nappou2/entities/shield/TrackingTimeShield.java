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

public final class TrackingTimeShield extends TimeBasedShield {

    private static final long serialVersionUID = 3069364776967448434L;

    private TrackingTimeShield(TrackingTimeShield from) {
        super(from);
    }

    public TrackingTimeShield(float timeout, float r) {
        super(timeout, r);
    }

    @Override
    public TrackingTimeShield copy() {
        return new TrackingTimeShield(this);
    }
}
