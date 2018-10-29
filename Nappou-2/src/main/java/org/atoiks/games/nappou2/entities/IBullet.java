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

package org.atoiks.games.nappou2.entities;

import java.io.Serializable;

import java.awt.Color;

import org.atoiks.games.framework2d.IUpdate;
import org.atoiks.games.framework2d.IRender;

public abstract class IBullet implements ICollidable, IRender, IUpdate, Serializable {

    public Color color = Color.white;

    public abstract float getX();
    public abstract float getY();
    public abstract float getDx();
    public abstract float getDy();

    public abstract void translate(float dx, float dy);
}
