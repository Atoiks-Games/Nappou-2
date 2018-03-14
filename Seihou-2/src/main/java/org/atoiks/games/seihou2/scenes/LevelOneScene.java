package org.atoiks.games.seihou2.scenes;

import se.tube42.lib.tweeny.Item;
import se.tube42.lib.tweeny.TweenEquation;

import org.atoiks.games.seihou2.entities.*;
import org.atoiks.games.seihou2.entities.enemy.*;
import org.atoiks.games.seihou2.entities.bullet.*;

public final class LevelOneScene extends AbstractGameScene {

    private int cycles;
    private int wave;

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
                    if(cycles > 30000){
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

                      case 4000:
                      game.addEnemy(new MiniBomberEnemy(1, -10, 30, 8, 1, 12));
                      game.addEnemy(new MiniBomberEnemy(1, 760, 30, 8, -1, 25));
                      break;

                      case 14000:
                      game.addEnemy(new MiniBomberEnemy(1, -7, 10, 8, 1, 10));
                      game.addEnemy(new MiniBomberEnemy(1, 754, 50, 8, -1, 23));
                      break;

                      case 24000:
                      game.addEnemy(new MiniBomberEnemy(1, -12, 25, 8, 1, 4));
                      game.addEnemy(new MiniBomberEnemy(1, 760, 40, 8, -1, 7));
                      break;

                      case 34000:
                      game.addEnemy(new MiniBomberEnemy(1, -11, 32, 8, 1, 17));
                      game.addEnemy(new MiniBomberEnemy(1, 755, 16, 8, -1, 2));
                      break;

                      case 44000:
                      game.addEnemy(new MiniBomberEnemy(1, -11, 50, 8, 1, 10));
                      game.addEnemy(new MiniBomberEnemy(1, 755, 37, 8, -1, 5));
                      break;

                      case 54000:
                      game.addEnemy(new MiniBomberEnemy(1, -11, 15, 8, 1, 7));
                      game.addEnemy(new MiniBomberEnemy(1, 755, 48, 8, -1, 12));
                      break;

                      case 64000:
                      game.addEnemy(new MiniBomberEnemy(1, -11, 76, 8, 1, 9));
                      game.addEnemy(new MiniBomberEnemy(1, 755, 89, 8, -1, 18));
                      break;

                      case 74000:
                      game.addEnemy(new MiniBomberEnemy(1, -11, 98, 8, 1, 19));
                      game.addEnemy(new MiniBomberEnemy(1, 755, 76, 8, -1, 16));
                      break;

                      case 84000:
                      game.addEnemy(new MiniBomberEnemy(1, -11, 35, 8, 1, 100));
                      game.addEnemy(new MiniBomberEnemy(1, 755, 56, 8, -1, 100));
                      break;

                      case 94000:
                      game.addEnemy(new MiniBomberEnemy(1, -11, 32, 8, 1, 17));
                      game.addEnemy(new MiniBomberEnemy(1, 755, 16, 8, -1, 2));
                      game.addEnemy(new DropEnemy(1, 30, -10, 8));
                      game.addEnemy(new DropEnemy(1, 720, -10, 8));
                      game.addEnemy(new DropEnemy(1, 100, -10, 8));
                      game.addEnemy(new DropEnemy(1, 650, -10, 8));
                      break;
                    }
                    if(cycles > 94000){
                        if (game.enemies.isEmpty()) {
                            wave++;
                            cycles = 0;
                        }
                    }
                    break;
                case 2:
                    if (game.enemies.isEmpty()) {
                      //miniboss
                    }
                    break;
                case 3:
                    if (game.enemies.isEmpty()) {
                      // wave
                    }
                    break;
                case 4:
                    if (game.enemies.isEmpty()) {
                      // wave
                    }
                    break;
                case 5:
                //boss
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
