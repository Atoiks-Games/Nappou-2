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

package org.atoiks.games.nappou2.scenes;

import java.awt.Color;

import org.atoiks.games.framework2d.Input;
import org.atoiks.games.framework2d.KeyCode;
import org.atoiks.games.framework2d.IGraphics;
import org.atoiks.games.framework2d.SceneManager;
import org.atoiks.games.framework2d.ResourceManager;

import org.atoiks.games.framework2d.resource.Font;

import org.atoiks.games.nappou2.Keymap;
import org.atoiks.games.nappou2.ScoreData;
import org.atoiks.games.nappou2.GameConfig;

public final class SaveHighscoreScene extends CenteringScene {

    private static final int NAME_LENGTH_CAP = 26;

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

    private static final String NAME_LENGTH_CAP_MSG =
            "Names over " + NAME_LENGTH_CAP + " characters will be truncated";

    static {
        // sanity check!
        if (BANK_LENGTH != CHAR_BANK.length) {
            throw new AssertionError("BANK_LENGTH(" + BANK_LENGTH + ") != " + CHAR_BANK.length);
        }
    }

    private int currentIdx = 0;
    private String currentStr = "";

    private final Font font16;
    private final Font font30;
    private final Font font80;
    private final Keymap keymap;

    private final int levelId;
    private final int levelScore;
    private final boolean mode;

    public SaveHighscoreScene(int levelId, int score, boolean mode) {
        this.levelId = levelId;
        this.levelScore = score;
        this.mode = mode;

        final Font fnt = ResourceManager.get("/Logisoso.ttf");
        this.font16 = fnt.deriveSize(16f);
        this.font30 = fnt.deriveSize(30f);
        this.font80 = fnt.deriveSize(80f);

        this.keymap = ResourceManager.<GameConfig>get("./game.cfg").keymap;
    }

    @Override
    public void render(IGraphics g) {
        g.setClearColor(Color.black);
        g.clearGraphics();
        super.render(g);

        g.setColor(Color.white);
        this.font80.renderText(g, "Enter Your Name", 200, 120);

        this.font30.renderText(g, currentStr, 275, 350);

        if (currentStr.length() > NAME_LENGTH_CAP) {
            this.font16.renderText(g, NAME_LENGTH_CAP_MSG, 14, 580);
        }
        for (int i = 0; i < BANK_LENGTH; ++i) {
            g.setColor(i == currentIdx ? Color.yellow : Color.white);
            this.font16.renderText(g, CHAR_BANK[i], i % WRAP_LENGTH * 30 + 275, i / WRAP_LENGTH * 18 + 450);
        }
    }

    @Override
    public boolean update(float dt) {
        if (this.keymap.shouldSelectPrevious()) {
            currentIdx -= WRAP_LENGTH;
        }
        if (this.keymap.shouldSelectNext()) {
            currentIdx += WRAP_LENGTH;
        }

        if (this.keymap.shouldSelectLeft()) {
            --currentIdx;
        }
        if (this.keymap.shouldSelectRight()) {
            ++currentIdx;
        }

        // Makes sure the index is always positive
        currentIdx += BANK_LENGTH;
        currentIdx %= BANK_LENGTH;

        if (Input.isKeyPressed(KeyCode.KEY_ENTER)) {
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
                    final ScoreData scoreDat = ResourceManager.get("./score.dat");
                    final String name = currentStr.length() > NAME_LENGTH_CAP
                            ? currentStr.substring(0, NAME_LENGTH_CAP) : currentStr;

                    scoreDat.updateScores(mode, levelId,
                            new ScoreData.Pair(name, (mode ? 2 : 1) * levelScore));

                    // Then transition to correct scene
                    SceneManager.popScene();
                    return true;
                default:
                    currentStr += CHAR_BANK[currentIdx];
                    break;
            }
        }

        return true;
    }
}
