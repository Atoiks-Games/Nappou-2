package org.atoiks.games.seihou2.scenes;

import java.awt.Color;
import java.awt.Image;
import java.awt.Graphics;

import java.io.IOException;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.BufferedInputStream;

import java.util.Locale;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

import javax.imageio.ImageIO;
import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.msgpack.core.MessagePack;
import org.msgpack.core.MessageUnpacker;

import org.atoiks.games.framework2d.Scene;

import org.atoiks.games.seihou2.GameConfig;

import static org.atoiks.games.seihou2.scenes.LevelOneScene.WIDTH;
import static org.atoiks.games.seihou2.scenes.LevelOneScene.HEIGHT;

public final class LoadingScene extends Scene {

    private enum LoadState {
        WAITING, LOADING, DONE, NO_RES
    }

    private static final int RADIUS = 100;
    private static final String LC_LANG = Locale.getDefault().getLanguage();

    private final ExecutorService loader = Executors.newSingleThreadExecutor();

    private LoadState loaded = LoadState.WAITING;

    private float time;
    private Image loadingTxt;

    @Override
    public void render(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        g.setColor(Color.white);
        for (double i = 0; i < Math.PI * 2; i += Math.PI / 6) {
            final int x = (int) (Math.cos(time + i) * Math.sin(time) * RADIUS) + WIDTH / 2;
            final int y = (int) (Math.sin(time + i) * Math.sin(time) * RADIUS) + HEIGHT / 2;
            g.drawOval(x - 5, y - 5, 10, 10);
        }

        if (loadingTxt == null) {
            loadingTxt = (Image) scene.resources().get("loading.png");
        }
        if (loadingTxt != null) {
            g.drawImage(loadingTxt, WIDTH - loadingTxt.getWidth(null), HEIGHT - loadingTxt.getHeight(null), null);
        }
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
                scene.gotoNextScene();
                break;
            case WAITING:
                loaded = LoadState.LOADING;
                loader.submit(() -> {
                    loadImageFromResources("loading.png");
                    loadImageFromResources("title.png");
                    loadImageFromResources("hp.png");
                    loadImageFromResources("stats.png");
                    loadImageFromResources("pause.png");
                    loadImageFromResources("z.png");
                    loadImageFromResources("x.png");
                    loadImageFromResources("controls.png");
                    loadImageFromResources("opt_shield.png");
                    loadImageFromResources("config.png");
                    loadImageFromResources("tutorial_preboss_1.png");

                    loadMusicFromResources("Enter_The_Void.wav");
                    loadMusicFromResources("Awakening.wav");

                    // Load configuration file from "current" directory
                    try (final ObjectInputStream ois = new ObjectInputStream(new FileInputStream("./game.cfg"))) {
                        final GameConfig cfg = (GameConfig) ois.readObject();
                        scene.resources().put("game.cfg", cfg == null ? new GameConfig() : cfg);
                    } catch (IOException | ClassNotFoundException ex) {
                        // Supply default configuration
                        scene.resources().put("game.cfg", new GameConfig());
                    }

                    // Load score file from "current" directory
                    // Format:
                    // [ levelNumber
                    //   [ score1, score2, score3, score4, score5 ]?
                    // ]
                    final int[][] scoreData = new int[1][5];
                    try (final BufferedInputStream in = new BufferedInputStream(new FileInputStream("./score.dat"))) {
                        final MessageUnpacker unpacker = MessagePack.newDefaultUnpacker(in);
                        final int lvls = unpacker.unpackArrayHeader();
                        if (lvls == scoreData.length) {
                            for (int i = 0; i < lvls; ++i) {
                                if (unpacker.tryUnpackNil()) continue;
                                final int n = unpacker.unpackArrayHeader();
                                if (n != 5) continue;
                                for (int j = 0; j < n; ++j) {
                                    scoreData[i][j] = unpacker.unpackInt();
                                }
                            }
                        }
                    } catch (IOException ex) {
                        // Ignore
                    } finally {
                        scene.resources().put("score.dat", scoreData);
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
        // Try to find locale specific files
        InputStream is = this.getClass().getResourceAsStream("/" + folder + "/" + LC_LANG + "/" + name);
        if (is == null) {
            // Try to look for english
            is = this.getClass().getResourceAsStream("/" + folder + "/" + Locale.ENGLISH.getLanguage() + "/" + name);
        }
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
            final Clip clip = AudioSystem.getClip();
            clip.open(in);
            clip.stop();
            scene.resources().put(name, clip);
        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException ex) {
        }
    }
}
