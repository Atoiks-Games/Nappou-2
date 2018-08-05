/**
 *  Nappou-2
 *  Copyright (C) 2017-2018  Atoiks-Games <atoiks-games@outlook.com>

 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.

 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.

 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package org.atoiks.games.nappou2.scenes;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.KeyEvent;

import javax.sound.sampled.Clip;

import org.atoiks.games.framework2d.Scene;
import org.atoiks.games.framework2d.IGraphics;

import org.atoiks.games.nappou2.GameConfig;

import static org.atoiks.games.nappou2.App.SANS_FONT;

public final class ConfigScene extends Scene {

    private static final String[] OPTION_NAMES = {
        "BGM", "CHALLENGE MODE"
    };
    private static final int[] SELECTOR_Y = { 66, 115 };
    private static final int OPT_HEIGHT = 23;
    private static final int[] BOOL_SEL_X = { 560, 588, 720, 764 };

    private Image configImg;
    private Clip bgm;
    private GameConfig config;

    private int selector;

    @Override
    public void render(IGraphics g) {
        g.setClearColor(Color.black);
        g.clearGraphics();
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

        if (scene.keyboard().isKeyPressed(KeyEvent.VK_ESCAPE)) {
            scene.switchToScene(1);
            return true;
        }
        if (scene.keyboard().isKeyPressed(KeyEvent.VK_DOWN)) {
            selector = (selector + 1) % SELECTOR_Y.length;
        }
        if (scene.keyboard().isKeyPressed(KeyEvent.VK_UP)) {
            if (--selector < 0) selector = SELECTOR_Y.length - 1;
        }
        if (scene.keyboard().isKeyPressed(KeyEvent.VK_RIGHT)) {
            switch (selector) {
                case 0:
                    config.bgm = !config.bgm;
                    break;
                case 1:
                    config.challengeMode = !config.challengeMode;
                    break;
            }
        }
        if (scene.keyboard().isKeyPressed(KeyEvent.VK_LEFT)) {
            switch (selector) {
                case 0:
                    config.bgm = !config.bgm;
                    break;
                case 1:
                    config.challengeMode = !config.challengeMode;
                    break;
            }
        }

        final int mouseY = scene.mouse().getLocalY();
        for (int i = 0; i < SELECTOR_Y.length; ++i) {
            final int selBase = SELECTOR_Y[i];
            if (mouseY > selBase && mouseY < (selBase + OPT_HEIGHT)) {
                selector = i;
                break;
            }
        }

        // Only update option with mouse if user dblclicked
        final int mouseX = scene.mouse().getLocalX();
        if (scene.mouse().isButtonClicked(1, 2)) {
            for (int i = 0; i < BOOL_SEL_X.length; i += 2) {
                final int selStart = BOOL_SEL_X[i];
                final int selEnd   = BOOL_SEL_X[i + 1];
                if (mouseX > selStart && mouseX < selEnd) {
                    // Set value to true if i == 0 (offset for ON)
                    setValueAtSelector(i == 0);
                    break;
                }
            }
        }
        return true;
    }

    private boolean getValueAtSelector() {
        switch (selector) {
            case 0: return config.bgm;
            case 1: return config.challengeMode;
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
            default:
                throw new RuntimeException("Unknown selector index " + selector);
        }
    }

    @Override
    public void resize(int x, int y) {
        // Screen size is fixed
    }

    @Override
    public void enter(int previousSceneId) {
        configImg = (Image) scene.resources().get("config.png");
        bgm = (Clip) scene.resources().get("Enter_The_Void.wav");
        config = (GameConfig) scene.resources().get("game.cfg");
    }

    @Override
    public void leave() {
        bgm.stop();
    }
}
