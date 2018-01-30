package org.atoiks.games.seihou2.scenes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import java.io.BufferedInputStream;
import java.io.IOException;

import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

import javax.imageio.ImageIO;
import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.atoiks.games.framework.Scene;

import static org.atoiks.games.seihou2.scenes.MainScene.HEIGHT;
import static org.atoiks.games.seihou2.scenes.MainScene.WIDTH;

public final class LoadingScene extends Scene {

    private enum LoadState {
        WAITING, LOADING, DONE
    }

    private static final int RADIUS = 100;

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
            case LOADING:
                break;
            case DONE:
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

                    loadMusicFromResources("title.wav");

                    loaded = LoadState.DONE;
                });
                break;
        }
        return true;
	}

	@Override
	public void resize(int x, int y) {
		// Ignore, screen size is fixed to 800 x 600
    }

    private void loadImageFromResources(String str) {
        try {
            scene.resources().put(str, ImageIO.read(this.getClass().getResourceAsStream("/image/" + str)));
        } catch (java.io.IOException ex) {
        }
    }

    private void loadMusicFromResources(String name) {
        try (final AudioInputStream in = AudioSystem.getAudioInputStream(new BufferedInputStream(this.getClass().getResourceAsStream("/music/" + name)))) {
            final Clip clip = AudioSystem.getClip();
            clip.open(in);
            clip.stop();
            scene.resources().put(name, clip);
        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException ex) {
        }
    }
}
