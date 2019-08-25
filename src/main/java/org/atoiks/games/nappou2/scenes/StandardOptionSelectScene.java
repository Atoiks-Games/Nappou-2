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
import org.atoiks.games.nappou2.GameInput;

public abstract class StandardOptionSelectScene extends OptionSelectScene<GameInput> {

    public StandardOptionSelectScene(Font font, GameInput keymap) {
        super(font, keymap, true);
    }

    public StandardOptionSelectScene(Font font, boolean supportEsc) {
        super(font, new Keymap(), true);
    }

    public StandardOptionSelectScene(Font font, GameInput keymap, boolean supportEsc) {
        super(font, keymap, supportEsc);
    }

    @Override
    public boolean update(float dt) {
        if (this.supportEsc && Input.isKeyPressed(KeyCode.KEY_ESCAPE)) {
            SceneManager.popScene();
            return true;
        }

        if (this.keymap.shouldSelectNext()) this.selectNext();
        if (this.keymap.shouldSelectPrevious()) this.selectPrevious();

        return true;
    }
}
