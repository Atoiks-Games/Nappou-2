package org.atoiks.games.nappou2.scenes;

import se.tube42.lib.tweeny.Item;
import se.tube42.lib.tweeny.TweenEquation;

import org.atoiks.games.nappou2.entities.*;
import org.atoiks.games.nappou2.entities.enemy.*;
import org.atoiks.games.nappou2.entities.bullet.*;

import java.util.Random;

public final class LevelTwoScene extends AbstractGameScene {

    private int cycles;
    private int wave;
    
    private final Random rnd = new Random();

    public LevelTwoScene() {
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
                        case 12000:
                        case 22000:
                        case 32000:
                            for (int i = 0; i < 4; ++i) {
                                game.addEnemyBullet(new PointBullet(rnd.nextFloat() * 750, -10, 4, rnd.nextFloat() * 100, rnd.nextFloat() * 100));
                                game.addEnemy(new DummyEnemy(1, -10, rnd.nextFloat() * 600, 4, true));
                                game.addEnemy(new DummyEnemy(1, 760, rnd.nextFloat() * 600, 4, false));
                            }
                            break;
                    }
                    if (cycles > 32000) {
                        if (game.enemies.isEmpty()) {
                            wave++;
                            cycles = 0;
                        }
                    }
                    break;
                case 1:
                    switch (cycles) {
                        case 2000:
                        game.addEnemy(new ShiftEnemy(2, 225, -10, 8));
                        game.addEnemy(new ShiftEnemy(2, 375, -10, 8));
                        game.addEnemy(new ShiftEnemy(2, 525, -10, 8));
                        break;
                    }
                    if (cycles > 94000) {
                        if (game.enemies.isEmpty()) {
                            //wave++;
                            //cycles = 0;
                        }
                    }
                    break;
                case 2:
                    switch (cycles) {
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
                   switch (cycles) {
                        case 2000:
                            game.addEnemy(new MB1(10, 375, -10, 20));
                            break;
                        case 4000:
                            game.addEnemy(new CircularPathEnemy(1, 750, 50, 8, 100, 1, 0.25f, 1, 100));
                            game.addEnemy(new CircularPathEnemy(1, 0, 50, 8, 100, -1, 0.25f, 3, 100));
                            break;
                        case 54000:
                            game.addEnemy(new CircularPathEnemy(1, 750, 0, 8, 100, 1, 0.25f, 1, 100));
                            game.addEnemy(new CircularPathEnemy(1, 0, 0, 8, 100, -1, 0.25f, 3, 100));
                            game.addEnemy(new CircularPathEnemy(1, 750, 600, 8, 100, -1, 0.25f, 1, 100));
                            game.addEnemy(new CircularPathEnemy(1, 0, 600, 8, 100, 1, 0.25f, 3, 100));
                        break;
                    }
                    if (cycles > 54000) {
                        if (game.enemies.isEmpty()) {
                            wave++;
                            cycles = 0;
                        }
                   }
                    break;
                case 4:
                    final float pi1 = (float) (2*Math.PI)/3;
                    final float pi2 = (float) (3*Math.PI)/2;
                    switch(cycles){
                      case 2000:
                        final float[] offset = { (float) (-Math.PI / 12), 0, (float) (Math.PI / 12) };
                        game.addEnemy(EnemyGroup.createLazyGroup(0.17f, 5, () -> {
                          final Item tweenInfo = new Item(3);
                          tweenInfo.set(0, 30, GAME_BORDER - 30).configure(28000, TweenEquation.QUAD_INOUT);
                          tweenInfo.setImmediate(2, 8);
                          tweenInfo.set(1, 10, HEIGHT + 40).configure(28000, TweenEquation.LINEAR);
                          return new RadialPointEnemy(2, 2, tweenInfo, 0.5f, true, 0f, 0f, 3, pi1, 15f, 100f);
                        }));
                      game.addEnemy(EnemyGroup.createLazyGroup(0.17f, 5, () -> {
                        final Item tweenInfo = new Item(3);
                        tweenInfo.set(0, GAME_BORDER - 30, 30).configure(28000, TweenEquation.QUAD_INOUT);
                        tweenInfo.set(1, 10, HEIGHT + 40).configure(28000, TweenEquation.LINEAR);
                        tweenInfo.setImmediate(2, 8);
                        return new RadialPointEnemy(2, 2, tweenInfo, 0.5f, true, 0f, (float) Math.PI, 3, pi1, 15f, 100f);
                      }));
                      game.addEnemy(EnemyGroup.createLazyGroup(0.17f, 5, () -> {
                        final Item tweenInfo = new Item(3);
                        tweenInfo.set(0, 10, GAME_BORDER - 10).configure(28000, TweenEquation.QUAD_INOUT);
                        tweenInfo.set(1, 10, HEIGHT + 40).configure(28000, TweenEquation.LINEAR);
                        tweenInfo.setImmediate(2, 8);
                        return new RadialPointEnemy(2, 2, tweenInfo, 0.5f, true, 0f, (float) pi2, 3, pi1, 15f, 100f);
                      }));
                      game.addEnemy(EnemyGroup.createLazyGroup(0.17f, 5, () -> {
                        final Item tweenInfo = new Item(3);
                        tweenInfo.set(0, GAME_BORDER - 10, 10).configure(28000, TweenEquation.QUAD_INOUT);
                        tweenInfo.set(1, 10, HEIGHT + 40).configure(28000, TweenEquation.LINEAR);
                        tweenInfo.setImmediate(2, 8);
                        return new RadialPointEnemy(2, 2, tweenInfo, 0.5f, true, 0f, (float) pi2, 3, pi1, 15f, 100f);
                      }));
                      break;
                      case 12000:
                      case 22000:
                      case 33000:
                      case 42000:
                      case 52000:
                      case 63000:
                      case 72000:
                      case 82000:
                      case 93000:
                      game.addEnemy(new DropEnemy(1, 30, -10, 8));
                      game.addEnemy(new DropEnemy(1, 720, -10, 8));
                      game.addEnemy(new DropEnemy(1, 100, -10, 8));
                      game.addEnemy(new DropEnemy(1, 650, -10, 8));
                      break;
                    }
                    if (cycles > 54000) {
                        if (game.enemies.isEmpty()) {
                            wave++;
                            cycles = 0;
                        }
                   }
                    break;
                case 5:
                  switch (cycles) {
                      case 2000:
                          game.addEnemy(new Level1Easy(300, 375, -10, 20));
                       break;
                  }
                       if (cycles > 54000) {
                         if (game.enemies.isEmpty()) {
                          //END LV
                        }
                  }
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
