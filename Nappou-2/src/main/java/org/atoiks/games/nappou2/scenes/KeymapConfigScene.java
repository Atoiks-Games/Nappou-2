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

import java.awt.event.KeyEvent;

import org.atoiks.games.framework2d.Input;
import org.atoiks.games.framework2d.IGraphics;
import org.atoiks.games.framework2d.SceneManager;
import org.atoiks.games.framework2d.ResourceManager;

import org.atoiks.games.nappou2.Keymap;
import org.atoiks.games.nappou2.Vector2;

public final class KeymapConfigScene extends OptionSelectScene {

    private static final Entry RESET_TO_DEFAULT_ENTRY = new Entry("Reset to default", new Vector2(634, 400));

    private Entry[] entries;
    private int[] indices;

    public KeymapConfigScene(Keymap keymap) {
        super(ResourceManager.get("/Logisoso.ttf"), keymap, true);

        this.setRenderAllEntries(true);
        this.updateOptionEntries();

        // This works because arrays are aliased
        this.setOptions(this.indices, this.entries);
    }

    @Override
    public void render(IGraphics g) {
        super.render(g);

        g.setFont(this.font16);
        g.drawString(this.getHintString(), 84, 500);
        g.drawString("Hit Tab to cycle through the list", 84, 560);
    }

    @Override
    public boolean update(float dt) {
        // We do not use the parent class's update
        // since it handles option selection differently!

        if (Input.isKeyPressed(KeyEvent.VK_ESCAPE)) {
            SceneManager.popScene();
            return true;
        }

        if (Input.isKeyPressed(KeyEvent.VK_TAB)) {
            this.selectNext();
        }

        if (this.isSelectingResetEntry()) {
            if (Input.isKeyPressed(KeyEvent.VK_ENTER)) {
                this.keymap.reset();
                this.updateOptionEntries();
            }
        } else {
            final int lastKey = Input.getLastDownKey();
            switch (lastKey) {
                case KeyEvent.VK_ESCAPE:
                case KeyEvent.VK_ENTER:
                case KeyEvent.VK_TAB:
                case KeyEvent.VK_UNDEFINED:
                    // These keys cannot be binded as input keys
                    break;
                default: {
                    final int selector = this.getSelectorIndex();
                    final boolean collidingKeycode = !this.canAssignKeycodeToIndex(selector, lastKey);

                    final Entry selectEntry = this.getSelectedEntry();
                    if (collidingKeycode) {
                        // Do not change it, instead modify it locally
                        selectEntry.setColor(Color.red);
                        selectEntry.setText(Keymap.keycodeToString(lastKey));
                    } else {
                        selectEntry.setColor(Color.white);
                        this.keymap.changeKeycodeOfIndex(selector, lastKey);
                        this.updateOptionEntries();
                    }
                    break;
                }
            }
        }
        return true;
    }

    private String getHintString() {
        if (this.isSelectingResetEntry()) {
            return "Hit Enter to reset all keys bindings";
        } else {
            return "Hit the key you want when item is selected, Red entries are not saved!";
        }
    }

    private boolean isSelectingResetEntry() {
        return this.getSelectedEntry() == RESET_TO_DEFAULT_ENTRY;
    }

    private void updateOptionEntries() {
        // Convert info message into proper format!
        final String[][] infoMsg = this.keymap.getInfoMessage();

        // Allocate the arrays
        this.generateEntriesArray(infoMsg.length);
        this.generateIndicesArray(infoMsg.length);

        // Fill the option entries
        for (int i = 0; i < infoMsg.length; ++i) {
            this.entries[2 * i + 0].setText(infoMsg[i][0]);
            this.entries[2 * i + 1].setText(infoMsg[i][1]);
        }
        this.entries[this.entries.length - 1] = RESET_TO_DEFAULT_ENTRY;
    }

    private void generateEntriesArray(final int length) {
        if (this.entries == null) {
            // * 2 because info message has two parts: description and key
            // + 1 because of "Reset to default"
            this.entries = new Entry[2 * length + 1];

            // Also allocate the entry slots
            for (int i = 0; i < length; ++i) {
                final int h = 94 + i * font30.getSize() + 5;
                this.entries[2 * i + 0] = new Entry("", new Vector2(124, h));
                this.entries[2 * i + 1] = new Entry("", new Vector2(634, h));
            }
        }
    }

    private void generateIndicesArray(final int length) {
        if (this.indices == null) {
            // -1 comes from:
            //   -2 due to pause and confirm being fixed
            //   +1 due to reset game
            this.indices = new int[length - 1];

            // Link indices to the correct entries (works because links do not change!)
            for (int i = 0; i < length - 2; ++i) {
                this.indices[i] = 2 * i + 1;
            }
            this.indices[length - 2] = this.entries.length - 1;
        }
    }

    private boolean canAssignKeycodeToIndex(int index, final int kc) {
        if (this.keymap.keyIsAlreadyAssigned(kc)) {
            // Check if the key is same as the existing value
            // if that is the case, then no problem assigning
            return this.keymap.getKeycodeOfIndex(index) == kc;
        }
        return true;
    }
}
