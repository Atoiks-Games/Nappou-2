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

import org.atoiks.games.framework2d.Input;
import org.atoiks.games.framework2d.GameScene;
import org.atoiks.games.framework2d.IGraphics;

import static org.atoiks.games.nappou2.App.SANS_FONT;

public final class PromptNameScene extends GameScene {

    private static final int WRAP_LENGTH = 13;

    // leave line breaks of CHAR_BANK untouched plz...
    private static final String[] CHAR_BANK = {
        "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M",
        "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z",
        "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "+", "-", "=",
        ".", "`", "!", "?", "@", ":", ";", "[", "]", "(", ")", "_", "/",
        "{", "}", "|", "~", "^", "#", "$", "%", "&", "*", " ", "BS", "DONE",
    };

    // This scene acts like a transitioning scene
    private int transition;

    private int currentIdx = 0;
    private String currentStr = "";

    @Override
    public void enter(int from) {
        // transition = (int) scene.resources().get("prompt.trans");
        transition = 1;

        currentIdx = 0;
        currentStr = "";
    }

    @Override
    public void render(IGraphics g) {
        g.setClearColor(Color.black);
        g.clearGraphics();

        g.setColor(Color.white);
        g.setFont(TitleScene.OPTION_FONT);
        g.drawString(currentStr, 275, 350);

        g.setFont(SANS_FONT);
        for (int i = 0; i < CHAR_BANK.length; ++i) {
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

        currentIdx %= CHAR_BANK.length;

        if (Input.isKeyPressed(KeyEvent.VK_ENTER)) {
            if (currentIdx == CHAR_BANK.length - 2) {
                // Backspace
                currentStr = currentStr.substring(0, currentStr.length() - 1);
            } else if (currentIdx == CHAR_BANK.length - 1) {
                // Done
                scene.resources().put("prompt.name", currentStr);
                // return scene.switchToScene(transition);
                return false;
            } else {
                currentStr += CHAR_BANK[currentIdx];
            }
        }

        return true;
    }

    @Override
    public void resize(int w, int h) {
        //
    }
}
