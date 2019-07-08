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

    public KeymapConfigScene(Keymap keymap) {
        final Font fnt = ResourceManager.get("/Logisoso.ttf");

        this.keymap = keymap;
        this.font16 = fnt.deriveFont(16f);
        this.font30 = fnt.deriveFont(30f);

        this.infoMsg = this.keymap.getInfoMessage();
    }

    @Override
    public void render(IGraphics g) {
        g.setClearColor(Color.black);
        g.clearGraphics();
        super.render(g);

        g.setColor(Color.white);
        g.setFont(font30);
        for (int i = 0; i < this.infoMsg.length; ++i) {
            final int h = getHeightForIndex(i);
            g.drawString(this.infoMsg[i][0], 124, h);
            g.drawString(this.infoMsg[i][1], 634, h);
        }

        g.setFont(font16);
        g.drawString("Hit Escape to return to title screen", 84, 540);
    }

    @Override
    public boolean update(float dt) {
        if (Input.isKeyPressed(KeyEvent.VK_ESCAPE)) {
            SceneManager.popScene();
            return true;
        }

        this.infoMsg = this.keymap.getInfoMessage();
        return true;
    }

    private int getHeightForIndex(int i) {
        return 94 + i * font30.getSize() + 5;
    }
}

// Input.getLastDownKey(); // KeyEvent.VK_UNDEFINED
