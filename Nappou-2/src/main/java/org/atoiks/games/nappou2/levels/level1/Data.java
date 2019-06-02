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

import org.atoiks.games.nappou2.entities.Message;

import static org.atoiks.games.nappou2.entities.Message.HorizontalAlignment;

import static org.atoiks.games.nappou2.scenes.RefittedGameScene.GAME_BORDER;

/* package */ final class Data {

    public static final Message[] PREBOSS_MSG = {
        new Message("ELLE.png", HorizontalAlignment.RIGHT, "ELLE", "Why are you here?"),
        new Message("LUMA.png", HorizontalAlignment.LEFT, "LUMA", "Oh you know, humans."),
        new Message("ELLE.png", HorizontalAlignment.RIGHT, "ELLE", "I no longer find joy in another's pain."),
        new Message("CAI.png", HorizontalAlignment.CENTER, "CAI", "Why so moody?"),
        new Message("ELLE.png", HorizontalAlignment.RIGHT, "ELLE", "..."),
        new Message("LUMA.png", HorizontalAlignment.LEFT, "LUMA", "Yeah, give me a few centuries and things will be back to normal!"),
        new Message("ELLE.png", HorizontalAlignment.RIGHT, "ELLE", "You haven't changed at all Luma!"),
        new Message(null      , HorizontalAlignment.RIGHT, "ELLE", "You took everything away from me. Do you know how much I suffered?"),
    };

    public static final Message POSTBOSS_MSG = new Message("ELLE.png", HorizontalAlignment.RIGHT, "ELLE", "I just want to go home...");

    // loop frame for level
    public static final int LEVEL_LOOP = 1229110;

    // loop frame for BOSS
    public static final int BOSS_LOOP = 1222000;

    // wave-number-diff-name = { bomber1A, bomber2A, bomber1B, bomber2B, ... }
    public static final float[] w1eX = {-10, 760, -7, 754, -12, 760, -11, 755, -11, 755, -11, 755, -11, 755, -11, 755, -11, 755, -11, 755};
    public static final float[] w1eY = {30, 30, 10, 50, 25, 40, 32, 16, 50, 37, 15, 48, 76, 89, 98, 76, 56, 56, 32, 16};
    public static final float[] w1eS = {12, 25, 10, 23, 4, 7, 17, 2, 10, 5, 7, 12, 9, 18, 19, 16, 100, 100, 17, 2};

    public static final float[] w4eX = { 30, GAME_BORDER - 30, 10, GAME_BORDER - 10 };
    public static final float[] w4eR = { 0, (float) Math.PI, (float) (Math.PI / 2), (float) (Math.PI / 2) };

    private Data() {
    }
}
