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

    public static class Entry {

        private static final Color DEFAULT_COLOR = Color.white;

        private String text;
        private Vector2 position;
        private Color color;

        public Entry(String text, Vector2 position) {
            this(text, position, DEFAULT_COLOR);
        }

        public Entry(String text, Vector2 position, Color color) {
            this.text = text;
            this.position = position;
            this.color = color;
        }

        public void setText(final String t) {
            this.text = t != null ? t : "";
        }

        public void setColor(final Color c) {
            this.color = c != null ? c : DEFAULT_COLOR;
        }

        public void setPosition(final Vector2 pos) {
            this.position = pos != null ? pos : Vector2.ZERO;
        }

        public Vector2 getPosition() {
            return this.position;
        }

        private void render(IGraphics g) {
            g.setColor(this.color);
            g.drawString(this.text, this.position.getX(), this.position.getY());
        }

        private void renderSelector(IGraphics g) {
            final float endX = this.position.getX() - 6;
            final float endY = this.position.getY() + 4;
            final float height = g.getFont().getSize2D();
            g.drawRect(endX - 4, endY - height, endX, endY);
        }
    }

    protected final Font font16;
    protected final Font font30;

    private final boolean supportEsc;

    protected Keymap keymap;

    private Entry[] entries = { };

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

    public OptionSelectScene(Font font, boolean supportEsc) {
        this(font, new Keymap(), supportEsc);
    }

    protected void setOptions(String[] options, Vector2[] positions) {
        final int limit = Math.min(options.length, positions.length);
        final Entry[] entries = new Entry[limit];
        for (int i = 0; i < limit; ++i) {
            entries[i] = new Entry(options[i], positions[i]);
        }

        this.setOptions(entries);
    }

    protected void setOptions(Entry... entries) {
        this.entries = entries;
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
            final Entry entry = this.entries[i];
            entry.render(g);
            if (i == this.selector) {
                entry.renderSelector(g);
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
        return this.entries.length - 1;
    }

    protected final void normalizeSelectorIndex() {
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
