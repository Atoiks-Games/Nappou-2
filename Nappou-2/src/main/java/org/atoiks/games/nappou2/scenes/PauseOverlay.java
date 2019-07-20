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

import org.atoiks.games.framework2d.resource.Font;

import org.atoiks.games.nappou2.Keymap;
import org.atoiks.games.nappou2.GameConfig;

import static org.atoiks.games.nappou2.scenes.GameLevelScene.HEIGHT;
import static org.atoiks.games.nappou2.scenes.GameLevelScene.GAME_BORDER;

/* package */ final class PauseOverlay {

    public static final Color BACKGROUND_COLOR = new Color(192, 192, 192, 100);

    private static final int[] SELECTOR_Y = {342, 402};
    private static final int SEL_RESUME_GAME = 0;
    private static final int OPT_HEIGHT = 37;

    private int selector;

    private boolean enabled;

    private final Font font30;
    private final Font font80;
    private final Keymap keymap;

    public PauseOverlay(Font font) {
        this.font30 = font.deriveSize(30f);
        this.font80 = font.deriveSize(80f);

        this.keymap = ResourceManager.<GameConfig>get("./game.cfg").keymap;
    }

    public void enable() {
        this.enabled = true;
        this.selector = 0;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void render(final IGraphics g) {
        if (!enabled) {
            return;
        }

        g.setColor(BACKGROUND_COLOR);
        g.fillRect(0, 0, GAME_BORDER, HEIGHT);
        g.setColor(Color.black);
        this.font80.renderText(g, "PAUSE", 274, 202);

        this.font30.renderText(g, "Continue Game", 52, 373);
        this.font30.renderText(g, "Return to Title", 52, 433);
        g.drawRect(45, SELECTOR_Y[selector], 49, SELECTOR_Y[selector] + OPT_HEIGHT);
    }


    public void update(float dt) {
        if (!enabled) {
            return;
        }

        if (Input.isKeyPressed(KeyEvent.VK_ESCAPE)) {
            enabled = false;
            selector = 0;
            return;
        }

        if (Input.isKeyPressed(KeyEvent.VK_ENTER)) {
            enabled = false;

            if (selector != SEL_RESUME_GAME) {
                SceneManager.swapScene(new TitleScene());
            }
            return;
        }

        if (this.keymap.shouldSelectNext()) {
            if (++selector >= SELECTOR_Y.length) selector = 0;
        }
        if (this.keymap.shouldSelectPrevious()) {
            if (--selector < 0) selector = SELECTOR_Y.length - 1;
        }
    }
}
