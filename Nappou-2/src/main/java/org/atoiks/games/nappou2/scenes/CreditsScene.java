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
import org.atoiks.games.framework2d.ResourceManager;

import org.atoiks.games.nappou2.GameConfig;

import static org.atoiks.games.nappou2.App.SANS_FONT;

public final class CreditsScene extends CenteringScene {

    private Clip bgm;

    @Override
    public void render(IGraphics g) {
        g.setClearColor(Color.black);
        g.clearGraphics();
        super.render(g);

        g.setColor(Color.white);
        g.setFont(TitleScene.OPTION_FONT);
        g.drawString("Thanks folks!", 10, 30);


        g.drawString("Alexander Bimm, Jeongmin Lee, Paul Teng, Shelby Elder, Sam Markowitz", 10, 60);

        g.setFont(SANS_FONT);
        g.drawString("Hit Escape or Enter to return to title screen", 14, 580);
    }

    @Override
    public boolean update(float dt) {
        if (Input.isKeyPressed(KeyEvent.VK_ESCAPE) || Input.isKeyPressed(KeyEvent.VK_ENTER)) {
            return scene.switchToScene("TitleScene");
        }
        return true;
    }

    @Override
    public void enter(String previousSceneId) {
        bgm = ResourceManager.get("/music/Enter_The_Void.wav");

        if (ResourceManager.<GameConfig>get("./game.cfg").bgm) {
            bgm.start();
            bgm.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    @Override
    public void leave() {
        bgm.stop();
    }
}
