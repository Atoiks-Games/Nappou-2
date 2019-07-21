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

import org.atoiks.games.framework2d.resource.Font;

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

        private void render(Font fnt, IGraphics g) {
            g.setColor(this.color);
            fnt.renderText(g, this.text, this.position.getX(), this.position.getY());
        }

        private void renderSelector(Font fnt, IGraphics g) {
            final float endX = this.position.getX() - 6;
            final float endY = this.position.getY() + 4;
            final float height = fnt.getSize();
            g.drawRect(endX - 4, endY - height, endX, endY);
        }
    }

    protected final Font font16;
    protected final Font font30;

    private final boolean supportEsc;

    protected Keymap keymap;

    private Entry[] entries = { };
    private int[] validIndices = { };
    private boolean renderAll = false;

    private int selector;

    public OptionSelectScene(Font font, Keymap keymap) {
        this(font, keymap, true);
    }

    public OptionSelectScene(Font font, Keymap keymap, boolean supportEsc) {
        this.font16 = font.deriveSize(16f);
        this.font30 = font.deriveSize(30f);
        this.keymap = keymap;
        this.supportEsc = supportEsc;
    }

    public OptionSelectScene(Font font, boolean supportEsc) {
        this(font, new Keymap(), supportEsc);
    }

    protected void setRenderAllEntries(boolean flag) {
        this.renderAll = flag;
    }

    protected void setOptions(Entry... entries) {
        this.entries = entries;

        // All entries are valid indices
        final int limit = entries.length;
        final int[] indices = new int[limit];
        for (int i = 0; i < limit; ++i) {
            indices[i] = i;
        }
        this.validIndices = indices;
    }

    protected void setOptions(final int[] indices, final Entry... entries) {
        // Make sure all indices point to valid entries
        this.indicesReferenceCheck(indices, entries);

        this.entries = entries;
        this.validIndices = indices;
    }

    protected void updateSelectableIndices(final int... indices) {
        this.indicesReferenceCheck(indices, this.entries);

        this.validIndices = indices;
    }

    private static void indicesReferenceCheck(final int[] indices, final Entry[] entries) {
        for (final int offset : indices) {
            if (offset < 0 || offset >= entries.length) {
                throw new IndexOutOfBoundsException("Index offset " + offset + " does not reference valid entry");
            }
        }
    }

    @Override
    public void render(IGraphics g) {
        g.setClearColor(Color.black);
        g.clearGraphics();
        super.render(g);

        g.setColor(Color.white);

        this.renderEntries(this.font30, g);
        this.getSelectedEntry().renderSelector(this.font30, g);

        if (this.supportEsc) {
            this.font16.renderText(g, "Hit Escape to exit", 84, 540);
        }
    }

    private void renderEntries(final Font fnt, final IGraphics g) {
        if (this.renderAll) {
            for (final Entry entry : this.entries) {
                entry.render(fnt, g);
            }
        } else {
            for (int i = 0; i < this.validIndices.length; ++i) {
                this.entries[this.validIndices[i]].render(fnt, g);
            }
        }
    }

    @Override
    public boolean update(float dt) {
        if (this.supportEsc && Input.isKeyPressed(KeyCode.KEY_ESCAPE)) {
            SceneManager.popScene();
            return true;
        }

        if (this.keymap.shouldSelectNext()) ++this.selector;
        if (this.keymap.shouldSelectPrevious()) --this.selector;
        this.normalizeSelectorIndex();

        return true;
    }

    protected final void selectNext() {
        ++this.selector;
        this.normalizeSelectorIndex();
    }

    protected final void selectPrevious() {
        --this.selector;
        this.normalizeSelectorIndex();
    }

    protected final Entry getSelectedEntry() {
        return this.entries[this.getSelectedIndex()];
    }

    protected final int getSelectedIndex() {
        return this.validIndices[this.selector];
    }

    protected final int getSelectorIndex() {
        return this.selector;
    }

    protected void setSelectorIndex(int index) {
        this.selector = index;
        this.normalizeSelectorIndex();
    }

    protected final void normalizeSelectorIndex() {
        final int limit = this.validIndices.length;

        // wrap to front if selector is above maximum value
        // wrap from back if selector is below minimum value
        this.selector = (this.selector % limit + limit) % limit;
    }
}
