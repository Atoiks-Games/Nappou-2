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

package org.atoiks.games.nappou2.entities.bullet.factory;

import org.atoiks.games.nappou2.entities.Game;
import org.atoiks.games.nappou2.entities.bullet.Beam;

public final class BeamInfo implements BulletFactory {

    public final float thickness;
    public final float length;
    public final float speed;

    public BeamInfo(float thickness, float length, float speed) {
        this.thickness = thickness;
        this.length = length;
        this.speed = speed;
    }

    @Override
    public Beam createBullet(Game g, float x, float y, float angle) {
        return new Beam(x, y, thickness, length, angle, speed);
    }
}