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
import org.atoiks.games.framework2d.Scene;
import org.atoiks.games.framework2d.IGraphics;
import org.atoiks.games.framework2d.SceneManager;
import org.atoiks.games.framework2d.ResourceManager;

import org.atoiks.games.nappou2.GameConfig;

public final class TitleScene extends CenteringScene {

    // Conventionally, last scene is always Quit,
    // sceneDest is always one less than the selectorY
    private static final String[] OPT_MSG = {
        "Continue", "New Game", "Highscore", "Settings", "Credits", "Quit"
    };
    private static final int[] selectorY = {232, 270, 308, 346, 384, 469};
    private static final int OPT_HEIGHT = 30;

    private final Clip bgm;
    private int selector;

    private final Font font16;
    private final Font font30;
    private final Font font80;

    public TitleScene() {
        this.bgm = ResourceManager.get("/music/Enter_The_Void.wav");

        final Font fnt = ResourceManager.get("/Logisoso.ttf");
        this.font16 = fnt.deriveFont(16f);
        this.font30 = fnt.deriveFont(30f);
        this.font80 = fnt.deriveFont(80f);
    }

    @Override
    public void render(IGraphics g) {
        g.setClearColor(Color.black);
        g.clearGraphics();
        super.render(g);

        g.setColor(Color.white);
        g.setFont(font80);
        g.drawString("Void Walker", 260, 178);

        g.setFont(font30);
        for (int i = 0; i < OPT_MSG.length; ++i) {
            g.drawString(OPT_MSG[i], 68, selectorY[i] + font30.getSize() - 2);
        }

        g.setFont(font16);
        g.drawString("      Made with love by Atoiks Games", 602, 540);
        g.drawString("In association with Harvard Game Devs", 600, 560);


        g.drawRect(61, selectorY[selector], 65, selectorY[selector] + OPT_HEIGHT);
    }

    @Override
    public boolean update(float dt) {
        if (Input.isKeyPressed(KeyEvent.VK_ENTER)) {
            switch (selector) {
                case 0: {
                    final GameLevelScene next = new GameLevelScene();
                    SceneManager.swapScene(next);
                    next.setState(new org.atoiks.games.nappou2.levels.tutorial.Preface());
                    break;
                }
                case 1:
                    SceneManager.pushScene(new DiffOptionScene());
                    break;
                case 2:
                    SceneManager.pushScene(new ScoreScene());
                    break;
                case 3:
                    SceneManager.swapScene(new ConfigScene());
                    break;
                case 4:
                    SceneManager.pushScene(new CreditsScene());
                    break;
                case 5:
                    return false;
                default:
                    System.err.println("Unhandled selector jump: " + selector);
                    return false;
            }
            return true;
        }
        if (Input.isKeyPressed(KeyEvent.VK_DOWN)) {
            if (++selector >= selectorY.length) selector = 0;
        }
        if (Input.isKeyPressed(KeyEvent.VK_UP)) {
            if (--selector < 0) selector = selectorY.length - 1;
        }
        return true;
    }

    @Override
    public void enter(final Scene from) {
        // Enter only deals with scenes that play different songs!
        if (ResourceManager.<GameConfig>get("./game.cfg").bgm) {
            if (!(from instanceof ConfigScene)) {
                bgm.setMicrosecondPosition(0);
                bgm.start();
                bgm.loop(Clip.LOOP_CONTINUOUSLY);
            }
        }
    }

    @Override
    public void leave() {
        bgm.stop();
    }
}
