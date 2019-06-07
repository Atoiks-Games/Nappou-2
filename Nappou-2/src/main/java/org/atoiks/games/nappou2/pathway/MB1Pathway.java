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

package org.atoiks.games.nappou2.pathway;

import org.atoiks.games.nappou2.Vector2;

public final class MB1Pathway implements UnboundPathway {

    private Vector2 position;

    public MB1Pathway(float x, float y) {
        this.position = new Vector2(x, y);
    }

    @Override
    public Vector2 getPosition() {
        return this.position;
    }

    @Override
    public void update(float dt) {
        if (this.position.getY() <= 150) {
            this.position = new Vector2(0, dt * 300).add(this.position);
        }
    }
}
