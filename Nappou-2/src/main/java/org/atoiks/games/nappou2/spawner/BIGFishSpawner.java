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

import org.atoiks.games.nappou2.entities.enemy.FishPart;

public final class BIGFishSpawner implements ISpawner {

    private int cycles = -1;

    private final float xmid, xrng;
    private final float ymid, yrng;

    private final float speed;
    private final float angle;
    private final float amplitude;
    private final boolean alt;

    /**
     * Cycle based: constructs another fish part every 25 cycles from cycle 0
     *
     * Recommendations:
     *  - when moving left-right, xrng should be 0
     *  - when moving top-bottom, yrng should be 0
     */
    public BIGFishSpawner(float xmid, float xrng, float ymid, float yrng, float speed, float angle, float amplitude, boolean alt) {
        this.xmid = xmid;
        this.xrng = xrng;
        this.ymid = ymid;
        this.yrng = yrng;

        this.speed = speed;
        this.angle = angle;
        this.amplitude = amplitude;
        this.alt = alt;
    }

    @Override
    public void onUpdate(final Game game, final float dt) {
        switch (++cycles) {
            case 0:
                game.addEnemy(new FishPart(16, xmid, ymid, 100, speed, angle, amplitude, 10, alt));
                break;
            case 200:
                game.addEnemy(new FishPart(8, xmid - xrng, ymid - yrng, 64, speed, angle, amplitude, 10, alt));
                game.addEnemy(new FishPart(8, xmid + xrng, ymid + yrng, 64, speed, angle, amplitude, 10, alt));
                break;
            case 400:
                game.addEnemy(new FishPart(8, xmid - 2.0f * xrng, ymid - 2.0f * yrng, 64, speed, angle, amplitude, 10, alt));
                game.addEnemy(new FishPart(8, xmid + 2.0f * xrng, ymid + 2.0f * yrng, 64, speed, angle, amplitude, 10, alt));
                break;
            case 600:
                game.addEnemy(new FishPart(8, xmid - xrng, ymid - yrng, 64, speed, angle, amplitude, 10, alt));
                game.addEnemy(new FishPart(8, xmid + xrng, ymid + yrng, 64, speed, angle, amplitude, 10, alt));
                break;
            case 800:
                game.addEnemy(new FishPart(8, xmid, ymid, 64, speed, angle, amplitude, 10, alt));
                break;
            case 1000:
                game.addEnemy(new FishPart(8, xmid - xrng, ymid - yrng, 48, speed, angle, amplitude, 10, alt));
                game.addEnemy(new FishPart(8, xmid + xrng, ymid + yrng, 48, speed, angle, amplitude, 10, alt));
                break;
            case 1160:
                game.addEnemy(new FishPart(8, xmid, ymid, 48, speed, angle, amplitude, 10, alt));
                break;
            case 1200:
                game.addEnemy(new FishPart(8, xmid - 2.0f * xrng, ymid - 2.0f * yrng, 48, speed, angle, amplitude, 10, alt));
                game.addEnemy(new FishPart(8, xmid + 2.0f * xrng, ymid + 2.0f * yrng, 48, speed, angle, amplitude, 10, alt));
                break;
        }
    }

    @Override
    public boolean isDoneSpawning() {
        return cycles > 1200;
    }
}
