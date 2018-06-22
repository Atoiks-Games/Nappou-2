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

public final class ConfigScene extends Scene {

    private static final int MAX_SELECTOR = 1;

    private Image configImg;
    private Clip bgm;
    private GameConfig config;

    private int selector;

    @Override
    public void render(IGraphics g) {
        g.drawImage(configImg, 0, 0);
        g.setColor(Color.white);
        switch (selector) {
            case 0:
                if (config.bgm) g.drawLine(218, 114, 262, 114);
                else g.drawLine(297, 114, 354, 114);
                break;
        }
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
            if (++selector > MAX_SELECTOR) selector = 0;
        }
        if (scene.keyboard().isKeyPressed(KeyEvent.VK_UP)) {
            if (--selector < 0) selector = MAX_SELECTOR - 1;
        }
        if (scene.keyboard().isKeyPressed(KeyEvent.VK_RIGHT)) {
            switch (selector) {
                case 0:
                    config.bgm = !config.bgm;
                    break;
            }
        }
        if (scene.keyboard().isKeyPressed(KeyEvent.VK_LEFT)) {
            switch (selector) {
                case 0:
                    config.bgm = !config.bgm;
                    break;
            }
        }
        return true;
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
