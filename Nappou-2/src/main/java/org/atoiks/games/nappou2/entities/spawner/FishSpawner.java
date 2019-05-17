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

package org.atoiks.games.nappou2.entities.spawner;

import org.atoiks.games.nappou2.entities.EnemySpawner;

import org.atoiks.games.nappou2.entities.enemy.FishPart;

public final class FishSpawner extends EnemySpawner {

    private static final long serialVersionUID = 34091232L;

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
    public FishSpawner(float xmid, float xrng, float ymid, float yrng, float speed, float angle, float amplitude, boolean alt) {
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
    public void update(final float dt) {
        switch (++cycles) {
            case 0:
                game.addEnemy(new FishPart(2, xmid, ymid, 16, speed, angle, amplitude, 10, alt));
                break;
            case 25:
                game.addEnemy(new FishPart(1, xmid - xrng, ymid - yrng, 8, speed, angle, amplitude, 10, alt));
                game.addEnemy(new FishPart(1, xmid + xrng, ymid + yrng, 8, speed, angle, amplitude, 10, alt));
                break;
            case 50:
                game.addEnemy(new FishPart(1, xmid - 2.0f * xrng, ymid - 2.0f * yrng, 8, speed, angle, amplitude, 10, alt));
                game.addEnemy(new FishPart(1, xmid + 2.0f * xrng, ymid + 2.0f * yrng, 8, speed, angle, amplitude, 10, alt));
                break;
            case 75:
                game.addEnemy(new FishPart(1, xmid - xrng, ymid - yrng, 8, speed, angle, amplitude, 10, alt));
                game.addEnemy(new FishPart(1, xmid + xrng, ymid + yrng, 8, speed, angle, amplitude, 10, alt));
                break;
            case 100:
                game.addEnemy(new FishPart(1, xmid, ymid, 8, speed, angle, amplitude, 10, alt));
                break;
            case 125:
                game.addEnemy(new FishPart(1, xmid - xrng, ymid - yrng, 6, speed, angle, amplitude, 10, alt));
                game.addEnemy(new FishPart(1, xmid + xrng, ymid + yrng, 6, speed, angle, amplitude, 10, alt));
                break;
            case 145:
                game.addEnemy(new FishPart(1, xmid, ymid, 6, speed, angle, amplitude, 10, alt));
                break;
            case 150:
                game.addEnemy(new FishPart(1, xmid - 2.0f * xrng, ymid - 2.0f * yrng, 6, speed, angle, amplitude, 10, alt));
                game.addEnemy(new FishPart(1, xmid + 2.0f * xrng, ymid + 2.0f * yrng, 6, speed, angle, amplitude, 10, alt));
                break;
        }
    }

    @Override
    public boolean isDoneSpawning() {
        return cycles > 150;
    }
}