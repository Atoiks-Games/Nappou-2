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

import java.awt.Font;
import java.awt.Color;
import java.awt.event.KeyEvent;

import javax.sound.sampled.Clip;

import org.atoiks.games.framework2d.Scene;
import org.atoiks.games.framework2d.IGraphics;

import org.atoiks.games.nappou2.GameConfig;

import static org.atoiks.games.nappou2.App.SANS_FONT;

public final class TitleScene extends Scene {

    public static final Font TITLE_FONT = SANS_FONT.deriveFont(80f);
    public static final Font OPTION_FONT = SANS_FONT.deriveFont(30f);

    // Conventionally, last scene is always Quit,
    // sceneDest is always one less than the selectorY
    private static final String[] OPT_MSG = {
        "Tutorial", "Story Mode", "Highscore", "Settings", "Quit"
    };
    private static final int[] selectorY = {235, 276, 318, 357, 469};
    private static final int[] sceneDest = {2, 5, 3, 4};
    private static final int OPT_HEIGHT = 30;

    private Clip bgm;
    private int selector;

    @Override
    public void render(IGraphics g) {
        g.setClearColor(Color.black);
        g.clearGraphics();

        g.setColor(Color.white);
        g.setFont(TITLE_FONT);
        g.drawString("Nappou 2", 319, 178);

        g.setFont(OPTION_FONT);
        for (int i = 0; i < OPT_MSG.length; ++i) {
            g.drawString(OPT_MSG[i], 68, selectorY[i] + OPTION_FONT.getSize() - 2);
        }

        g.setFont(SANS_FONT);
        g.drawString("Made with love by Atoiks Games", 600, 560);

        g.drawRect(61, selectorY[selector], 65, selectorY[selector] + OPT_HEIGHT);
    }

    @Override
    public boolean update(float dt) {
        if (scene.keyboard().isKeyPressed(KeyEvent.VK_ENTER) || scene.mouse().isButtonClicked(1, 2)) {
            if (selector < sceneDest.length) {
                scene.switchToScene(sceneDest[selector]);
                return true;
            }

            // Quit was chosen
            return false;
        }
        if (scene.keyboard().isKeyPressed(KeyEvent.VK_DOWN)) {
            if (++selector >= selectorY.length) selector = 0;
        }
        if (scene.keyboard().isKeyPressed(KeyEvent.VK_UP)) {
            if (--selector < 0) selector = selectorY.length - 1;
        }

        final int mouseY = scene.mouse().getLocalY();
        for (int i = 0; i < selectorY.length; ++i) {
            final int selBase = selectorY[i];
            if (mouseY > selBase && mouseY < (selBase + OPT_HEIGHT)) {
                selector = i;
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
    public void enter(final int prevSceneId) {
        bgm = (Clip) scene.resources().get("Enter_The_Void.wav");

        if (((GameConfig) scene.resources().get("game.cfg")).bgm) {
            // ScoreScene and ConfigScene continues to play music
            switch (prevSceneId) {
                case 3:
                case 4:
                case 5:
                case 6:
                    break;
                default:
                    bgm.setMicrosecondPosition(0);
                    break;
            }
            bgm.start();
            bgm.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    @Override
    public void leave() {
        bgm.stop();
    }
}
