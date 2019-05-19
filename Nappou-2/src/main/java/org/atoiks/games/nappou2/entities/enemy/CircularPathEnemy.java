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

package org.atoiks.games.nappou2.entities.enemy;

import org.atoiks.games.nappou2.entities.attack.SingleShot;
import org.atoiks.games.nappou2.entities.pathway.OrbitalPathway;

public final class CircularPathEnemy extends FireGateEnemy {

    private static final long serialVersionUID = 5619264522L;

    public CircularPathEnemy(int hp, float x, float y, float r, float radius, int direction, float speedMod, int startPos, float bulletSpeed) {
        super(hp, 1, r, bulletSpeed, 0.01);
        setPathway(new OrbitalPathway(radius, x, y, direction, speedMod, startPos));
        setUpdateListener(new SingleShot());
    }
}
