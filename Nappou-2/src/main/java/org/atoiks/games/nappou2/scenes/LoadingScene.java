/**
 *  Nappou-2
 *  Copyright (C) 2017-2018  Atoiks-Games <atoiks-games@outlook.com>
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

import java.io.IOException;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.BufferedInputStream;

import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

import javax.imageio.ImageIO;
import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.atoiks.games.framework2d.Scene;
import org.atoiks.games.framework2d.IGraphics;

import org.atoiks.games.nappou2.ScoreData;
import org.atoiks.games.nappou2.GameConfig;
import org.atoiks.games.nappou2.SoundEffect;

import static org.atoiks.games.nappou2.App.SANS_FONT;
import static org.atoiks.games.nappou2.scenes.LevelOneScene.WIDTH;
import static org.atoiks.games.nappou2.scenes.LevelOneScene.HEIGHT;

public final class LoadingScene extends Scene {

    private enum LoadState {
        WAITING, LOADING, DONE, NO_RES
    }

    public static final Font LOADING_FONT = SANS_FONT.deriveFont(45f);

    private static final int RADIUS = 100;

    private final ExecutorService loader = Executors.newSingleThreadExecutor();

    private LoadState loaded = LoadState.WAITING;
    private boolean enterFullscreen = false;

    private float time;

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

        g.setFont(LOADING_FONT);
        g.drawString("Loading", WIDTH - 200, HEIGHT - LOADING_FONT.getSize() - 10);
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
                scene.frame().setFullScreen(enterFullscreen);
                // Title is remapped to 1!
                return scene.switchToScene(1);
            case WAITING:
                loaded = LoadState.LOADING;
                loader.submit(() -> {
                    loadImageFromResources("hp.png");
                    loadImageFromResources("z.png");
                    loadImageFromResources("x.png");
                    loadImageFromResources("CAI.png");
                    loadImageFromResources("ELLE.png");
                    loadImageFromResources("LUMA.png");

                    loadMusicFromResources("Level_One.wav");
                    loadMusicFromResources("Enter_The_Void.wav");
                    loadMusicFromResources("Awakening.wav");
                    loadMusicFromResources("Broken_Soul.wav");
                    loadMusicFromResources("Haunted.wav");
                    loadMusicFromResources("Unlocked.wav");

                    // Load configuration file from "current" directory
                    try (final ObjectInputStream ois = new ObjectInputStream(new FileInputStream("./game.cfg"))) {
                        final GameConfig cfg = (GameConfig) ois.readObject();
                        scene.resources().put("game.cfg", cfg == null ? new GameConfig() : cfg);
                        if (cfg != null) {
                            // Check if user wanted fullscreen mode
                            enterFullscreen = cfg.fullscreen;
                        }
                    } catch (IOException | ClassNotFoundException ex) {
                        // Supply default configuration
                        scene.resources().put("game.cfg", new GameConfig());
                    }

                    // Load score file from "current" directory
                    try (final ObjectInputStream ois = new ObjectInputStream(new FileInputStream("./score.dat"))) {
                        final ScoreData data = (ScoreData) ois.readObject();

                        ScoreData sanitized;
                        if (data == null) {
                            // if we cannot find the old score, supply blank score
                            sanitized = new ScoreData();
                        } else if (data.data[0].length != ScoreData.LEVELS) {
                            // amount of levels is changed, assume old score is wrong
                            sanitized = new ScoreData();
                        } else {
                            // keep old score, it is probably valid
                            sanitized = data;
                        }
                        scene.resources().put("score.dat", sanitized);
                    } catch (IOException | ClassNotFoundException ex) {
                        // Supply default score
                        scene.resources().put("score.dat", new ScoreData());
                    }

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

    private InputStream getResourceStreamFrom(final String folder, final String name) {
        InputStream is = this.getClass().getResourceAsStream("/" + folder + "/en/" + name);
        if (is == null) {
            is = this.getClass().getResourceAsStream("/" + folder + "/" + name);
        }
        return is;
    }

    private void loadImageFromResources(final String name) {
        try {
            InputStream is = getResourceStreamFrom("image", name);
            if (is == null) {
                loaded = LoadState.NO_RES;
                return;
            }
            scene.resources().put(name, ImageIO.read(is));
        } catch (IOException ex) {
        }
    }

    private void loadMusicFromResources(final String name) {
        InputStream is = getResourceStreamFrom("music", name);
        if (is == null) {
            loaded = LoadState.NO_RES;
            return;
        }
        try (final AudioInputStream in = AudioSystem.getAudioInputStream(new BufferedInputStream(is))) {
            final Clip clip = SoundEffect.getFromAudioInputStream(in).makeClip();
            scene.resources().put(name, clip);
        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException ex) {
            ex.printStackTrace();
        }
    }
}
