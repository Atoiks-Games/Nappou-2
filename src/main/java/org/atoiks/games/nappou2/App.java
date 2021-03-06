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

package org.atoiks.games.nappou2;

import java.io.IOException;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

import org.atoiks.games.framework2d.IFrame;
import org.atoiks.games.framework2d.IRuntime;
import org.atoiks.games.framework2d.FrameInfo;
import org.atoiks.games.framework2d.SceneManager;
import org.atoiks.games.framework2d.ResourceManager;

import org.atoiks.games.framework2d.java2d.JavaRuntime;

import org.atoiks.games.nappou2.scenes.LoadingScene;

public class App {

    public static void main(String[] args) {
        final IRuntime host = JavaRuntime.getInstance();

        final FrameInfo info = new FrameInfo()
                .setTitle("Atoiks Games - Void Walker")
                .setResizable(false)
                .setSize(900, 600)
                .setFps(120.0f);

        try (final IFrame frame = host.createFrame(info)) {
            frame.init();
            SceneManager.pushScene(new LoadingScene());
            frame.loop();
        }

        final GameConfig gameCfg = ResourceManager.get("./game.cfg");
        final ScoreData scoreDat = ResourceManager.get("./score.dat");
        final SaveData saveDat = ResourceManager.get("./saves.dat");

        // Saves config
        try (final ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("./game.cfg"))) {
            gameCfg.writeExternal(oos);
        } catch (IOException ex) {
            // Next time, game will launch with default configurations
            ex.printStackTrace();
        }

        // Saves user score
        try (final ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("./score.dat"))) {
            scoreDat.writeExternal(oos);
        } catch (IOException ex) {
            // Oh well... to bad... the user's score does not get saved...
            ex.printStackTrace();
        }

        // Saves user saves
        try (final ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("./saves.dat"))) {
            saveDat.writeExternal(oos);
        } catch (IOException ex) {
            // Oh well... to bad... the user's saves do not get saved...
            ex.printStackTrace();
        }
    }
}
