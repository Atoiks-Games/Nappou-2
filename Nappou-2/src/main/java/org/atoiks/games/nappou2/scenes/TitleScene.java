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

import static org.atoiks.games.nappou2.App.SANS_FONT;

public final class TitleScene extends CenteringScene {

    public static final Font TITLE_FONT = SANS_FONT.deriveFont(80f);
    public static final Font OPTION_FONT = SANS_FONT.deriveFont(30f);

    // Conventionally, last scene is always Quit,
    // sceneDest is always one less than the selectorY
    private static final String[] OPT_MSG = {
        "Tutorial", "Story Mode", "Highscore", "Settings", "Credits", "Quit"
    };
    private static final int[] selectorY = {232, 270, 308, 346, 384, 469};
    private static final String[] sceneDest = {
        "GameLevelScene", "DiffOptionScene", "ScoreScene", "ConfigScene", "CreditsScene"
    };
    private static final int OPT_HEIGHT = 30;

    private Clip bgm;
    private int selector;

    @Override
    public void render(IGraphics g) {
        g.setClearColor(Color.black);
        g.clearGraphics();
        super.render(g);

        g.setColor(Color.white);
        g.setFont(TITLE_FONT);
        g.drawString("Void Walker", 260, 178);

        g.setFont(OPTION_FONT);
        for (int i = 0; i < OPT_MSG.length; ++i) {
            g.drawString(OPT_MSG[i], 68, selectorY[i] + OPTION_FONT.getSize() - 2);
        }

        g.setFont(SANS_FONT);
        g.drawString("      Made with love by Atoiks Games", 602, 540);
        g.drawString("In association with Harvard Game Devs", 600, 560);


        g.drawRect(61, selectorY[selector], 65, selectorY[selector] + OPT_HEIGHT);
    }

    @Override
    public boolean update(float dt) {
        if (Input.isKeyPressed(KeyEvent.VK_ENTER)) {
            if (selector < sceneDest.length) {
                if (selector == 0) {
                    // GameLevelScene will fetch the level from level.state
                    // selector 0 points to tutorial stage, so we put in the
                    // entry state for the tutorial stage.
                    SceneManager.resources().put("level.state",
                            org.atoiks.games.nappou2.levels.tutorial.Preface.INSTANCE);
                }
                return SceneManager.switchToScene(sceneDest[selector]);
            }

            // Quit was chosen
            return false;
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
    public void init() {
        bgm = ResourceManager.get("/music/Enter_The_Void.wav");
    }

    @Override
    public void enter(final String prevSceneId) {
        if (ResourceManager.<GameConfig>get("./game.cfg").bgm) {
            // switch-case with strings crash when value is null
            switch (prevSceneId != null ? prevSceneId : "") {
                case "ScoreScene":
                case "CreditsScene":
                case "ConfigScene":
                case "DiffOptionScene":
                    // These scenes continue playing music.
                    // No need to reset playback
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
