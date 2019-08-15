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

package org.atoiks.games.nappou2.spawner;

import org.atoiks.games.nappou2.entities.Game;

public final class BIGFishSpawner extends FishSpawner {

    private int cycles = -1;

    /**
     * Cycle based: constructs another fish part every 25 cycles from cycle 0
     *
     * Recommendations:
     *  - when moving left-right, xrng should be 0
     *  - when moving top-bottom, yrng should be 0
     */
    public BIGFishSpawner(float xmid, float xrng, float ymid, float yrng, float speed, float angle, float amplitude, boolean alt) {
        super(xmid, xrng, ymid, yrng, speed, angle, amplitude, alt);
    }

    @Override
    public void onUpdate(final Game game, final float dt) {
        switch (++cycles) {
            case 0:
                this.addSingular(game, 16, 100);
                break;
            case 200:
                this.addSingleSpacing(game, 8, 64);
                break;
            case 400:
                this.addDoubleSpacing(game, 8, 64);
                break;
            case 600:
                this.addSingleSpacing(game, 8, 64);
                break;
            case 800:
                this.addSingular(game, 8, 64);
                break;
            case 1000:
                this.addSingleSpacing(game, 8, 48);
                break;
            case 1160:
                this.addSingular(game, 8, 48);
                break;
            case 1200:
                this.addDoubleSpacing(game, 8, 48);
                break;
        }
    }

    @Override
    public boolean isDoneSpawning() {
        return cycles > 1200;
    }
}
