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

import java.awt.event.KeyEvent;

import javax.sound.sampled.Clip;

import org.atoiks.games.framework2d.Input;
import org.atoiks.games.framework2d.IGraphics;
import org.atoiks.games.framework2d.SceneManager;
import org.atoiks.games.framework2d.ResourceManager;

import org.atoiks.games.nappou2.Vector2;
import org.atoiks.games.nappou2.ScoreData;
import org.atoiks.games.nappou2.GameConfig;

public final class ConfigScene extends OptionSelectScene {

    private static final String[] OPTION_NAMES = {
        "BGM", "CHALLENGE MODE", "FULLSCREEN", "CONFIGURE CONTROLS", "CLEAR SCORES"
    };
    private static final Vector2[] OPTION_POS = {
        new Vector2(84, 66),
        new Vector2(84, 115),
        new Vector2(84, 164),
        new Vector2(84, 213),
        new Vector2(84, 262)
    };

    private final Clip bgm;
    private final GameConfig config;

    public ConfigScene() {
        super(ResourceManager.get("/Logisoso.ttf"), true);

        this.bgm = ResourceManager.get("/music/Enter_The_Void.wav");
        this.config = ResourceManager.get("./game.cfg");
        this.keymap = this.config.keymap;

        this.setOptions(OPTION_NAMES, OPTION_POS);
    }

    @Override
    public boolean update(float dt) {
        super.update(dt);

        switch (this.getSelectedIndex()) {
            case 3:
                // This entry invokes an overlay!
                if (Input.isKeyPressed(KeyEvent.VK_ENTER)) {
                    SceneManager.pushScene(new KeymapConfigScene(config.keymap));
                    return true;
                }
                break;
            case 4:
                // This clears the score and hides the option!
                if (Input.isKeyPressed(KeyEvent.VK_ENTER)) {
                    ResourceManager.<ScoreData>get("./score.dat").clear();
                    this.updateSelectableIndices(new int[] {
                        0, 1, 2, 3
                    });
                    this.normalizeSelectorIndex();
                }
                break;
            default:
                // Only dealing with boolean values, both right and left keys only need to invert value
                if (this.keymap.shouldSelectRight() || this.keymap.shouldSelectLeft()) {
                    setValueAtSelector(!getValueAtSelector());
                }
                break;
        }
        return true;
    }

    private boolean getValueAtSelector(int selector) {
        switch (selector) {
            case 0: return config.bgm;
            case 1: return config.challengeMode;
            case 2: return config.fullscreen;
            default: throw new RuntimeException("Unknown selector index " + selector);
        }
    }

    private boolean getValueAtSelector() {
        return getValueAtSelector(this.getSelectedIndex());
    }

    private void setValueAtSelector(final boolean newValue) {
        final int selector = this.getSelectedIndex();
        switch (selector) {
            case 0:
                if ((config.bgm = newValue)) {
                    bgm.start();
                    bgm.loop(Clip.LOOP_CONTINUOUSLY);
                } else {
                    bgm.stop();
                }
                break;
            case 1:
                config.challengeMode = newValue;
                break;
            case 2:
                SceneManager.frame().setFullScreen(config.fullscreen = newValue);
                break;
            default:
                throw new RuntimeException("Unknown selector index " + selector);
        }
    }

    @Override
    public void render(IGraphics g) {
        super.render(g);

        for (int i = 0; i < 3; ++i) {
            final float h = OPTION_POS[i].getY();
            g.setFont(this.font30);
            g.drawString("ON", 560, h);
            g.drawString("OFF", 720, h);

            g.setFont(this.font16);
            this.renderBoolValue(g, this.getValueAtSelector(i), h + 4);
        }
    }

    private void renderBoolValue(final IGraphics g, final boolean value, final float height) {
        if (value) {
            g.drawLine(560, height, 588, height);
        } else {
            g.drawLine(720, height, 760, height);
        }
    }
}
