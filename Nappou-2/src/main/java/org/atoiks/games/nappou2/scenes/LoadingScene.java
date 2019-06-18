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
import java.awt.FontFormatException;

import java.io.IOException;

import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

import org.atoiks.games.framework2d.Scene;
import org.atoiks.games.framework2d.IGraphics;
import org.atoiks.games.framework2d.SceneManager;
import org.atoiks.games.framework2d.ResourceManager;

import org.atoiks.games.framework2d.decoder.ImageDecoder;
import org.atoiks.games.framework2d.decoder.AudioDecoder;
import org.atoiks.games.framework2d.decoder.DecodeException;
import org.atoiks.games.framework2d.decoder.ExternalizableDecoder;

import org.atoiks.games.framework2d.resolver.ExternalResourceResolver;

import org.atoiks.games.nappou2.ScoreData;
import org.atoiks.games.nappou2.GameConfig;

import static org.atoiks.games.nappou2.scenes.GameLevelScene.WIDTH;
import static org.atoiks.games.nappou2.scenes.GameLevelScene.HEIGHT;

public final class LoadingScene implements Scene {

    private enum LoadState {
        WAITING, LOADING, DONE, NO_RES
    }

    private static final int RADIUS = 100;

    private final ExecutorService loader = Executors.newSingleThreadExecutor();

    private LoadState loaded = LoadState.WAITING;
    private boolean enterFullscreen = false;

    private Font font;

    private float time;

    @Override
    public void enter(Scene from) {
        font = ResourceManager.load("/Logisoso.ttf", src -> {
            try {
                return Font.createFont(Font.PLAIN, src);
            } catch (IOException | FontFormatException ex) {
                throw new DecodeException(ex);
            }
        }).deriveFont(45f);
    }

    @Override
    public void render(IGraphics g) {
        g.setClearColor(Color.black);
        g.clearGraphics();
        g.setColor(Color.white);
        for (double i = 0; i < Math.PI * 2; i += Math.PI / 6) {
            final int x = (int) (Math.cos(time + i) * Math.sin(time) * RADIUS) + WIDTH / 2;
            final int y = (int) (Math.sin(time + i) * Math.sin(time) * RADIUS) + HEIGHT / 2;
            g.drawOval(x - 5, y - 5, x + 5, y + 5);
        }

        g.setFont(font);
        g.drawString("Loading", WIDTH - 200, HEIGHT - font.getSize() - 10);
    }

    @Override
    public boolean update(float dt) {
        time += dt;
        switch (loaded) {
            case NO_RES:
                return false;
            case LOADING:
                break;
            case DONE:
                loader.shutdown();
                // Now entering fullscreen if user wanted it.
                SceneManager.frame().setFullScreen(enterFullscreen);
                SceneManager.swapScene(new TitleScene());
                return true;
            case WAITING:
                loaded = LoadState.LOADING;
                loader.submit(() -> {
                    try {
                        loadImageFromResources("hp.png");
                        loadImageFromResources("z.png");
                        loadImageFromResources("x.png");
                        loadImageFromResources("CAI.png");
                        loadImageFromResources("ELLE.png");
                        loadImageFromResources("LUMA.png");

                        loadMusicFromResources("Level_One.wav");
                        loadMusicFromResources("Level_One_Boss.wav");
                        loadMusicFromResources("Enter_The_Void.wav");
                        loadMusicFromResources("Awakening.wav");
                        loadMusicFromResources("Broken_Soul.wav");
                        loadMusicFromResources("Haunted.wav");
                        loadMusicFromResources("Unlocked.wav");
                    } catch (DecodeException ex) {
                        ex.printStackTrace();
                        loaded = LoadState.NO_RES;
                        return;
                    }

                    // Load configuration file from "current" directory
                    final GameConfig cfg = ResourceManager.loadOrDefault("./game.cfg", ExternalResourceResolver.INSTANCE,
                            ExternalizableDecoder.forInstance(GameConfig::new), GameConfig::new);
                    enterFullscreen = cfg.fullscreen;

                    // Load score file from "current" directory
                    final ScoreData data = ResourceManager.loadOrDefault("./score.dat", ExternalResourceResolver.INSTANCE,
                            ExternalizableDecoder.forInstance(ScoreData::new), ScoreData::new);

                    loaded = LoadState.DONE;
                });
                break;
        }
        return true;
    }

    @Override
    public void resize(int x, int y) {
        // Ignore, screen size is fixed
    }

    private void loadImageFromResources(final String name) {
        ResourceManager.load("/image/" + name, ImageDecoder.INSTANCE);
    }

    private void loadMusicFromResources(final String name) {
        ResourceManager.load("/music/" + name, AudioDecoder.INSTANCE);
    }
}
