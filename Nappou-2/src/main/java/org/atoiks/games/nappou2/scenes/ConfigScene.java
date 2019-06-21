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

import javax.sound.sampled.Clip;

import org.atoiks.games.framework2d.Input;
import org.atoiks.games.framework2d.IGraphics;
import org.atoiks.games.framework2d.SceneManager;
import org.atoiks.games.framework2d.ResourceManager;

import org.atoiks.games.nappou2.GameConfig;

public final class ConfigScene extends CenteringScene {

    private static final String[] OPTION_NAMES = {
        "BGM", "CHALLENGE MODE", "FULLSCREEN"
    };
    private static final int[] SELECTOR_Y = { 66, 115, 164 };
    private static final int OPT_HEIGHT = 23;
    private static final int[] BOOL_SEL_X = { 560, 588, 720, 764 };

    private final Font font16;
    private final Font font30;

    private final Clip bgm;
    private final GameConfig config;

    private int selector;
    private float scaleFactor;
    private float transX;
    private float transY;

    public ConfigScene() {
        bgm = ResourceManager.get("/music/Enter_The_Void.wav");
        config = ResourceManager.get("./game.cfg");

        final Font fnt = ResourceManager.get("/Logisoso.ttf");
        this.font16 = fnt.deriveFont(16f);
        this.font30 = fnt.deriveFont(30f);
    }

    @Override
    public void render(IGraphics g) {
        g.setClearColor(Color.black);
        g.clearGraphics();
        super.render(g);

        g.setColor(Color.white);
        g.setFont(this.font30);
        for (int i = 0; i < OPTION_NAMES.length; ++i) {
            final int h = SELECTOR_Y[i] + this.font30.getSize() - 7;
            g.drawString(OPTION_NAMES[i], 84, h);
            g.drawString("ON", 560, h);
            g.drawString("OFF", 720, h);
        }

        g.setFont(font16);
        g.drawString("Hit Escape to return to title screen", 84, 540);

        g.drawRect(74, SELECTOR_Y[selector], 78, SELECTOR_Y[selector] + OPT_HEIGHT);

        renderBoolValue(g, config.bgm, 91);
        renderBoolValue(g, config.challengeMode, 140);
        renderBoolValue(g, config.fullscreen, 189);
    }

    private void renderBoolValue(final IGraphics g, final boolean value, final float height) {
        final int offset = 2 * (value ? 0 : 1);
        g.drawLine(BOOL_SEL_X[offset], height, BOOL_SEL_X[offset + 1], height);
    }

    @Override
    public boolean update(float dt) {
        if (Input.isKeyPressed(KeyEvent.VK_ESCAPE)) {
            SceneManager.popScene();
            return true;
        }
        if (Input.isKeyPressed(KeyEvent.VK_DOWN)) {
            selector = (selector + 1) % SELECTOR_Y.length;
        }
        if (Input.isKeyPressed(KeyEvent.VK_UP)) {
            if (--selector < 0) selector = SELECTOR_Y.length - 1;
        }

        // Only dealing with boolean values, both right and left keys only need to invert value
        if (Input.isKeyPressed(KeyEvent.VK_RIGHT) || Input.isKeyPressed(KeyEvent.VK_LEFT)) {
            setValueAtSelector(!getValueAtSelector());
        }
        return true;
    }

    private boolean getValueAtSelector() {
        switch (selector) {
            case 0: return config.bgm;
            case 1: return config.challengeMode;
            case 2: return config.fullscreen;
            default: throw new RuntimeException("Unknown selector index " + selector);
        }
    }

    private void setValueAtSelector(final boolean newValue) {
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
}
