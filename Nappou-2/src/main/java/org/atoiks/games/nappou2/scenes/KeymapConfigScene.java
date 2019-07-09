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

import java.awt.Font;
import java.awt.Color;

import java.awt.event.KeyEvent;

import org.atoiks.games.framework2d.Input;
import org.atoiks.games.framework2d.IGraphics;
import org.atoiks.games.framework2d.SceneManager;
import org.atoiks.games.framework2d.ResourceManager;

import org.atoiks.games.nappou2.Keymap;

public final class KeymapConfigScene extends CenteringScene {

    private final Keymap keymap;
    private final Font font16;
    private final Font font30;

    private String[][] infoMsg;
    private boolean[] paintRed;

    private int selector;

    public KeymapConfigScene(Keymap keymap) {
        final Font fnt = ResourceManager.get("/Logisoso.ttf");

        this.keymap = keymap;
        this.font16 = fnt.deriveFont(16f);
        this.font30 = fnt.deriveFont(30f);

        this.validateInfoMsg();
    }

    @Override
    public void render(IGraphics g) {
        g.setClearColor(Color.black);
        g.clearGraphics();
        super.render(g);

        g.setFont(font30);
        for (int i = 0; i < this.infoMsg.length; ++i) {
            final int h = getHeightForIndex(i);
            g.setColor(this.paintRed[i] ? Color.red : Color.white);
            g.drawString(this.infoMsg[i][0], 124, h);
            g.drawString(this.infoMsg[i][1], 634, h);
        }

        g.setColor(Color.white);
        g.setFont(font16);
        g.drawString("Hit Escape to return to title screen", 84, 540);
        g.drawString("Hit Enter to cycle through the list", 84, 560);

        final int selectorHeight = getHeightForIndex(selector) - 18;
        g.drawRect(621, selectorHeight, 625, selectorHeight + 20);
    }

    @Override
    public boolean update(float dt) {
        if (Input.isKeyPressed(KeyEvent.VK_ESCAPE)) {
            SceneManager.popScene();
            return true;
        }

        if (Input.isKeyPressed(KeyEvent.VK_ENTER)) {
            selector = (selector + 1) % this.getMaxOptions();
        }

        final int lastKey = Input.getLastDownKey();
        switch (lastKey) {
            case KeyEvent.VK_ESCAPE:
            case KeyEvent.VK_ENTER:
            case KeyEvent.VK_UNDEFINED:
                // These keys cannot be binded as input keys
                break;
            default: {
                final boolean collidingKeycode = !this.canAssignKeycodeToIndex(this.selector, lastKey);
                this.paintRed[this.selector] = collidingKeycode;
                if (collidingKeycode) {
                    // Do not change it, instead modify it locally
                    this.infoMsg[this.selector][1] = Keymap.keycodeToString(lastKey);
                } else {
                    this.keymap.changeKeycodeOfIndex(this.selector, lastKey);
                    this.invalidateInfoMsg();
                }
                break;
            }
        }

        this.validateInfoMsg();
        return true;
    }

    private boolean canAssignKeycodeToIndex(int index, final int kc) {
        if (this.keymap.keyIsAlreadyAssigned(kc)) {
            // Check if the key is same as the existing value
            // if that is the case, then no problem assigning
            return this.keymap.getKeycodeOfIndex(index) == kc;
        }
        return true;
    }

    private void invalidateInfoMsg() {
        this.infoMsg = null;
    }

    private void validateInfoMsg() {
        if (this.infoMsg == null) {
            this.infoMsg = this.keymap.getInfoMessage();
            if (this.paintRed == null) {
                this.paintRed = new boolean[this.infoMsg.length];
            }
        }
    }

    private int getHeightForIndex(int i) {
        return 94 + i * font30.getSize() + 5;
    }

    private int getMaxOptions() {
        // -2 because last two entries (pause and select cannot be changed!)
        return this.infoMsg.length - 2;
    }
}
