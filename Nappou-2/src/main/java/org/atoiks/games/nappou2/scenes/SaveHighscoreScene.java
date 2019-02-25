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

package org.atoiks.games.nappou2.scenes;

import java.awt.Color;
import java.awt.event.KeyEvent;

import java.util.Arrays;
import java.util.Comparator;

import org.atoiks.games.framework2d.Input;
import org.atoiks.games.framework2d.GameScene;
import org.atoiks.games.framework2d.IGraphics;

import org.atoiks.games.nappou2.ScoreData;
import org.atoiks.games.nappou2.GameConfig;
import org.atoiks.games.nappou2.Difficulty;

import static org.atoiks.games.nappou2.App.SANS_FONT;

public final class SaveHighscoreScene extends GameScene {

    private static final int WRAP_LENGTH = 13;
    private static final int BANK_LENGTH = WRAP_LENGTH * 5;

    // leave line breaks of CHAR_BANK untouched plz...
    // U2610 is a ballot box, which means a space character
    private static final String[] CHAR_BANK = {
        "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M",
        "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z",
        "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "+", "-", "=",
        ".", "`", "!", "?", "@", ":", ";", "[", "]", "(", ")", "_", "/",
        "{", "}", "|", "~", "^", "#", "$", "%", "&", "*", "\u2610", "BS", "DONE",
    };

    private static final Comparator<ScoreData.Pair> NULLS_FIRST_CMP =
            Comparator.nullsFirst(Comparator.naturalOrder());

    static {
        // sanity check!
        if (BANK_LENGTH != CHAR_BANK.length) {
            throw new AssertionError("BANK_LENGTH(" + BANK_LENGTH + ") != " + CHAR_BANK.length);
        }
    }

    // This scene acts like a transitioning scene
    private int transition;

    private int currentIdx = 0;
    private String currentStr = "";

    @Override
    public void enter(int from) {
        transition = (int) scene.resources().get("prompt.trans");

        currentIdx = 0;
        currentStr = "";
    }

    @Override
    public void render(IGraphics g) {
        g.setClearColor(Color.black);
        g.clearGraphics();

        g.setColor(Color.white);
        g.setFont(TitleScene.TITLE_FONT);
        g.drawString("Enter Your Name", 200, 120);

        g.setFont(TitleScene.OPTION_FONT);
        g.drawString(currentStr, 275, 350);

        g.setFont(SANS_FONT);
        if (currentStr.length() > 26) {
            g.drawString("Names over 26 characters will be truncated", 14, 580);
        }
        for (int i = 0; i < BANK_LENGTH; ++i) {
            g.setColor(i == currentIdx ? Color.yellow : Color.white);
            g.drawString(CHAR_BANK[i], i % WRAP_LENGTH * 30 + 275, i / WRAP_LENGTH * 18 + 450);
        }
    }

    @Override
    public boolean update(float dt) {
        if (Input.isKeyPressed(KeyEvent.VK_UP)) {
            currentIdx -= WRAP_LENGTH;
        }
        if (Input.isKeyPressed(KeyEvent.VK_DOWN)) {
            currentIdx += WRAP_LENGTH;
        }

        if (Input.isKeyPressed(KeyEvent.VK_LEFT)) {
            --currentIdx;
        }
        if (Input.isKeyPressed(KeyEvent.VK_RIGHT)) {
            ++currentIdx;
        }

        // Makes sure the index is always positive
        currentIdx += BANK_LENGTH;
        currentIdx %= BANK_LENGTH;

        if (Input.isKeyPressed(KeyEvent.VK_ENTER)) {
            switch (currentIdx) {
                case BANK_LENGTH - 3: // Space
                    currentStr += ' ';
                    break;
                case BANK_LENGTH - 2: // Backspace
                    if (!currentStr.isEmpty()) {
                        currentStr = currentStr.substring(0, currentStr.length() - 1);
                    }
                    break;
                case BANK_LENGTH - 1: // Done
                    // We will save the score here!
                    // Names must be 26 chars. Pad space if necessary!
                    final boolean challengeMode = ((GameConfig) scene.resources().get("game.cfg")).challengeMode;
                    final int levelId = (int) scene.resources().get("level.id");
                    final int levelScore = (int) scene.resources().get("level.score");
                    final int levelDiff = ((Difficulty) scene.resources().get("difficulty")).ordinal();

                    final ScoreData scoreDat = (ScoreData) scene.resources().get("score.dat");
                    final String name = restrictString(currentStr, 26);

                    final ScoreData.Pair[] alias = scoreDat.data[challengeMode ? 1 : 0][levelId][levelDiff];
                    final ScoreData.Pair[] a = Arrays.copyOf(alias, alias.length + 1);
                    a[a.length - 1] = new ScoreData.Pair(name, (challengeMode ? 2 : 1) * levelScore);
                    Arrays.sort(a, NULLS_FIRST_CMP);
                    System.arraycopy(a, 1, alias, 0, a.length - 1);

                    // Then transition to correct scene
                    return scene.switchToScene(transition);
                default:
                    currentStr += CHAR_BANK[currentIdx];
                    break;
            }
        }

        return true;
    }

    private static String restrictString(final String str, final int width) {
        if (str == null || str.isEmpty()) {
            return String.valueOf(new char[width]);
        }

        final int strlen = str.length();
        if (strlen == width) {
            return str;
        }
        if (strlen > width) {
            return str.substring(0, width);
        }

        // strlen < width
        return str + String.valueOf(new char[width - strlen]);
    }

    @Override
    public void resize(int w, int h) {
        //
    }
}
