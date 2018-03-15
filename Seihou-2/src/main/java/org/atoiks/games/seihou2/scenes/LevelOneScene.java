package org.atoiks.games.seihou2.scenes;

import se.tube42.lib.tweeny.Item;
import se.tube42.lib.tweeny.TweenEquation;

import org.atoiks.games.seihou2.entities.*;
import org.atoiks.games.seihou2.entities.enemy.*;
import org.atoiks.games.seihou2.entities.bullet.*;

public final class LevelOneScene extends AbstractGameScene {

    private int cycles;
    private int wave;

    // wave-number-diff-name = { bomber1A, bomber2A, bomber1B, bomber2B, ... }
    private final float[] w1eX = {-10, 760, -7, 754, -12, 760, -11, 755, -11, 755, -11, 755, -11, 755, -11, 755, -11, 755, -11, 755};
    private final float[] w1eY = {30, 30, 10, 50, 25, 40, 32, 16, 50, 37, 15, 48, 76, 89, 98, 76, 56, 56, 32, 16};
    private final float[] w1eS = {12, 25, 10, 23, 4, 7, 17, 2, 10, 5, 7, 12, 9, 18, 19, 16, 100, 100, 17, 2};

    public LevelOneScene() {
        super(0);
    }

    @Override
    public void enter(final int prevSceneId) {
        super.enter(prevSceneId);

        cycles = 0;
        wave = 0;

        game.player = new Player(GAME_BORDER / 2, HEIGHT / 6 * 5, (IShield) scene.resources().get("shield"));
        game.player.setHp(5);
        game.setScore(0);
    }

    @Override
    public boolean postUpdate(float dt) {
        ++cycles;
        switch (difficulty) {
            case EASY:
            switch (wave) {
                case 0:
                    switch (cycles) {
                        case 2000:
                        case 4000:
                        case 6000:
                        case 8000:
                        case 10000:
                            final int k = cycles / 1000 * 25;
                            game.addEnemy(new SingleShotEnemy(1, 300 - k, -10, 8));
                            game.addEnemy(new SingleShotEnemy(1, 450 + k, -10, 8));
                            break;
                        case 11000:
                            game.addEnemy(new DropEnemy(1, -10, 10, 8));
                            game.addEnemy(new DropEnemy(1, 760, 10, 8));
                            break;
                        case 30000:
                            game.addEnemy(new CircularPathEnemy(1, 650, -1, 8, 100, 1, 1, 0, 100));
                            game.addEnemy(new CircularPathEnemy(1, 100, -1, 8, 100, -1, 1, 2, 100));
                            break;
                    }
                    if (cycles > 30000) {
                        if (game.enemies.isEmpty()) {
                            wave++;
                            cycles = 0;
                        }
                    }
                    break;
                case 1:
                    switch (cycles) {
                        case 2000:
                            game.addEnemy(new DropEnemy(1, 30, -10, 8));
                            game.addEnemy(new DropEnemy(1, 720, -10, 8));
                            game.addEnemy(new DropEnemy(1, 50, -10, 8));
                            game.addEnemy(new DropEnemy(1, 700, -10, 8));
                            break;
                        case 94000:
                            game.addEnemy(new DropEnemy(1, 30, -10, 8));
                            game.addEnemy(new DropEnemy(1, 720, -10, 8));
                            game.addEnemy(new DropEnemy(1, 100, -10, 8));
                            game.addEnemy(new DropEnemy(1, 650, -10, 8));   // FALLTHROUGH
                        case 84000:
                        case 74000:
                        case 64000:
                        case 54000:
                        case 44000:
                        case 34000:
                        case 24000:
                        case 14000:
                        case 4000:
                            final int offset = (cycles - 4000) / 5000;
                            game.addEnemy(new MiniBomberEnemy(1, w1eX[offset + 0], w1eY[offset + 0], 8, 1, w1eS[offset + 0]));
                            game.addEnemy(new MiniBomberEnemy(1, w1eX[offset + 1], w1eY[offset + 1], 8, -1, w1eS[offset + 1]));
                            break;
                    }
                    if (cycles > 94000) {
                        if (game.enemies.isEmpty()) {
                            wave++;
                            cycles = 0;
                        }
                    }
                    break;
                case 2:
                    switch(cycles){
                      case 2000:
                        game.addEnemy(new MB1(10, 225, -10, 20));
                        game.addEnemy(new MB1(10, 375, -10, 20));
                        game.addEnemy(new MB1(10, 525, -10, 20));
                      break;
                      case 22000:
                        game.addEnemy(new MB1(10, 300, -10, 20));
                        game.addEnemy(new MB1(10, 450, -10, 20));
                      break;
                    }
                    if (cycles > 22000) {
                        if (game.enemies.isEmpty()) {
                            wave++;
                            cycles = 0;
                        }
                    }
                    break;
                case 3:
                    switch(cycles){
                        case 2000:
                          game.addEnemy(new MB1(10, 375, -10, 20));
                        break;
                        case 4000:
                          game.addEnemy(new CircularPathEnemy(1, 750, 50, 8, 100, 1, (float) 0.25, 1, 100));
                          game.addEnemy(new CircularPathEnemy(1, 0, 50, 8, 100, -1, (float) 0.25, 3, 100));
                        break;
                      }
                    break;
                case 4:
                    if (game.enemies.isEmpty()) {
                        // wave
                    }
                    break;
                case 5:
                    // boss
                    break;
            }
            break;
        }
        return true;
    }

    @Override
    public void leave() {
        super.leave();
    }
}
