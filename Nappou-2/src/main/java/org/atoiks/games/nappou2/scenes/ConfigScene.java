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

import javax.sound.sampled.Clip;

import org.atoiks.games.framework2d.Input;
import org.atoiks.games.framework2d.IGraphics;

import org.atoiks.games.nappou2.GameConfig;

import static org.atoiks.games.nappou2.App.SANS_FONT;

public final class ConfigScene extends CenteringScene {

    private static final String[] OPTION_NAMES = {
        "BGM", "CHALLENGE MODE", "FULLSCREEN"
    };
    private static final int[] SELECTOR_Y = { 66, 115, 164 };
    private static final int OPT_HEIGHT = 23;
    private static final int[] BOOL_SEL_X = { 560, 588, 720, 764 };

    private Clip bgm;
    private GameConfig config;

    private int selector;
    private float scaleFactor;
    private float transX;
    private float transY;

    @Override
    public void render(IGraphics g) {
        g.setClearColor(Color.black);
        g.clearGraphics();
        super.render(g);

        g.setColor(Color.white);
        g.setFont(TitleScene.OPTION_FONT);
        for (int i = 0; i < OPTION_NAMES.length; ++i) {
            final int h = SELECTOR_Y[i] + TitleScene.OPTION_FONT.getSize() - 7;
            g.drawString(OPTION_NAMES[i], 84, h);
            g.drawString("ON", 560, h);
            g.drawString("OFF", 720, h);
        }

        g.setFont(SANS_FONT);
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
        if (config.bgm) {
            bgm.start();
            bgm.loop(Clip.LOOP_CONTINUOUSLY);
        } else {
            bgm.stop();
            bgm.setMicrosecondPosition(0);
        }

        if (Input.isKeyPressed(KeyEvent.VK_ESCAPE)) {
            return scene.switchToScene("TitleScene");
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
                config.bgm = newValue;
                break;
            case 1:
                config.challengeMode = newValue;
                break;
            case 2:
                scene.frame().setFullScreen(config.fullscreen = newValue);
                break;
            default:
                throw new RuntimeException("Unknown selector index " + selector);
        }
    }

    @Override
    public void init() {
        bgm = (Clip) scene.resources().get("Enter_The_Void.wav");
        config = (GameConfig) scene.resources().get("game.cfg");
    }

    @Override
    public void leave() {
        bgm.stop();
    }
}
