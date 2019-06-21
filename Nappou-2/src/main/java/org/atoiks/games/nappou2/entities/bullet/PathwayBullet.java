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

package org.atoiks.games.nappou2.entities.bullet;

import java.awt.Color;

import org.atoiks.games.nappou2.Vector2;

import org.atoiks.games.nappou2.pathway.IPathway;

public abstract class PathwayBullet implements IBullet {

    private final IPathway pathway;

    protected Vector2 drift = Vector2.ZERO;

    protected Color color = Color.white;

    protected PathwayBullet(IPathway pathway) {
        this.pathway = pathway;
    }

    @Override
    public void drift(Vector2 d) {
        this.drift = this.drift.add(d);
    }

    @Override
    public final void setColor(Color newColor) {
        this.color = newColor;
    }

    @Override
    public final Color getColor() {
        return this.color;
    }

    @Override
    public void update(final float dt) {
        this.pathway.update(dt);
    }

    @Override
    public Vector2 getPosition() {
        return this.pathway.getPosition().add(drift);
    }
}
