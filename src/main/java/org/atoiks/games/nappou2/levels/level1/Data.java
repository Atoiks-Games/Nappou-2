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

package org.atoiks.games.nappou2.levels.level1;

import java.util.Random;

import org.atoiks.games.nappou2.Vector2;

import org.atoiks.games.nappou2.graphics.shapes.ImmutableCircle;

import static org.atoiks.games.nappou2.scenes.GameLevelScene.GAME_BORDER;

public final class Data {

    public static final Random rnd = new Random();

    // loop frame for level
    public static final int LEVEL_LOOP = 1229110;

    // wave-number-diff-name = { bomber1A, bomber2A, bomber1B, bomber2B, ... }
    public static final float[] w1eX = {-10, 760, -7, 754, -12, 760, -11, 755, -11, 755, -11, 755, -11, 755, -11, 755, -11, 755, -11, 755};
    public static final float[] w1eY = {30, 30, 10, 50, 25, 40, 32, 16, 50, 37, 15, 48, 76, 89, 98, 76, 56, 56, 32, 16};
    public static final float[] w1eS = {12, 25, 10, 23, 4, 7, 17, 2, 10, 5, 7, 12, 9, 18, 19, 16, 100, 100, 17, 2};

    public static final float[] w4eX = { 30, GAME_BORDER - 30, 10, GAME_BORDER - 10 };
    public static final float[] w4eR = { 0, (float) Math.PI, (float) (Math.PI / 2), (float) (Math.PI / 2) };

    public static final ImmutableCircle BOUNDARY_750_0 = new ImmutableCircle(new Vector2(750, 0), 100);
    public static final ImmutableCircle BOUNDARY_0_0 = new ImmutableCircle(Vector2.ZERO, 100);
    public static final ImmutableCircle BOUNDARY_750_50 = new ImmutableCircle(new Vector2(750, 50), 100);
    public static final ImmutableCircle BOUNDARY_0_50 = new ImmutableCircle(new Vector2(0, 50), 100);
    public static final ImmutableCircle BOUNDARY_750_600 = new ImmutableCircle(new Vector2(750, 600), 100);
    public static final ImmutableCircle BOUNDARY_0_600 = new ImmutableCircle(new Vector2(0, 600), 100);

    private Data() {
    }
}
