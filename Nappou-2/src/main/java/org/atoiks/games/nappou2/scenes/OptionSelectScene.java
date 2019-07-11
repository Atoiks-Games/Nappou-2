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

import org.atoiks.games.nappou2.Keymap;
import org.atoiks.games.nappou2.Vector2;

public abstract class OptionSelectScene extends CenteringScene {

    protected final Font font16;
    protected final Font font30;

    private final Keymap keymap;

    private final boolean supportEsc;

    private String[] optionNames = { };
    private Vector2[] optionPosition = { };

    private int selector;

    public OptionSelectScene(Font font, Keymap keymap) {
        this(font, keymap, true);
    }

    public OptionSelectScene(Font font, Keymap keymap, boolean supportEsc) {
        this.font16 = font.deriveFont(16f);
        this.font30 = font.deriveFont(30f);
        this.keymap = keymap;
        this.supportEsc = supportEsc;
    }

    protected void setOptions(String[] options, Vector2[] positions) {
        this.optionNames = options;
        this.optionPosition = positions;
    }

    @Override
    public void render(IGraphics g) {
        g.setClearColor(Color.black);
        g.clearGraphics();
        super.render(g);

        g.setColor(Color.white);
        g.setFont(this.font30);

        final int max = getMaximumIndex();
        for (int i = getMinimumIndex(); i <= max; ++i) {
            final Vector2 pos = this.optionPosition[i];
            g.drawString(this.optionNames[i], pos.getX(), pos.getY());

            if (i == this.selector) {
                final float endX = pos.getX() - 6;
                final float endY = pos.getY() + 4;
                g.drawRect(endX - 4, endY - 30, endX, endY);
            }
        }

        if (this.supportEsc) {
            g.setFont(this.font16);
            g.drawString("Hit Escape to exit", 84, 540);
        }
    }

    @Override
    public boolean update(float dt) {
        if (this.supportEsc && Input.isKeyPressed(KeyEvent.VK_ESCAPE)) {
            SceneManager.popScene();
            return true;
        }

        if (this.keymap.shouldSelectNext()) ++this.selector;
        if (this.keymap.shouldSelectPrevious()) --this.selector;
        this.normalizeSelectorIndex();

        return true;
    }

    protected final int getSelectorIndex() {
        return this.selector;
    }

    protected void setSelectorIndex(int index) {
        this.selector = index;
        this.normalizeSelectorIndex();
    }

    protected int getMinimumIndex() {
        return 0;
    }

    protected int getMaximumIndex() {
        return this.optionNames.length - 1;
    }

    private void normalizeSelectorIndex() {
        final int min = this.getMinimumIndex();
        final int max = this.getMaximumIndex();

        int dist;
        int sel = this.selector;

        // wrap from back if selector is below minimum value
        while ((dist = sel - min) < 0) {
            sel = max + dist + 1;
        }

        // wrap to front if selector is above maximum value
        while ((dist = sel - max) > 0) {
            sel = min + dist - 1;
        }

        this.selector = sel;
    }
}
