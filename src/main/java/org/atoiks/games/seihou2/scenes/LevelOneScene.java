package org.atoiks.games.seihou2.scenes;

import se.tube42.lib.tweeny.Item;
import se.tube42.lib.tweeny.TweenEquation;

import org.atoiks.games.seihou2.entities.*;
import org.atoiks.games.seihou2.entities.bullet.*;
import org.atoiks.games.seihou2.entities.enemies.*;

public final class LevelOneScene extends AbstractGameScene {

    private float time;
    private int wave;

    public LevelOneScene() {
        super(0);
    }

    @Override
    public void enter(final int prevSceneId) {
        super.enter(prevSceneId);

        time = 0;
        wave = 0;

        game.player = new Player(GAME_BORDER / 2, HEIGHT / 6 * 5, (IShield) scene.resources().get("shield"));

        final float[] offset = { (float) (-Math.PI / 12), 0, (float) (Math.PI / 12) };
        game.addEnemy(EnemyGroup.createLazyGroup(0.17f, 5, () -> {
            final Item tweenInfo = new Item(3);
            tweenInfo.set(0, 30, GAME_BORDER - 30).configure(28000, TweenEquation.QUAD_INOUT);
            tweenInfo.set(1, 10, HEIGHT + 40).configure(28000, TweenEquation.LINEAR);
            tweenInfo.setImmediate(2, 8);
            return new TrackBeamEnemy(4, 1, tweenInfo, 2f, true, 0f, offset, 2.5f, 45, 720);
        }));
        game.addEnemy(EnemyGroup.createLazyGroup(0.17f, 5, () -> {
            final Item tweenInfo = new Item(3);
            tweenInfo.set(0, 50, GAME_BORDER - 10).configure(28000, TweenEquation.QUAD_INOUT);
            tweenInfo.set(1, 10, HEIGHT + 40).configure(28000, TweenEquation.LINEAR);
            tweenInfo.setImmediate(2, 8);
            return new TrackBeamEnemy(4, 1, tweenInfo, 2f, true, 0f, offset, 2.5f, 45, 720);
        }));
        game.addEnemy(EnemyGroup.createLazyGroup(0.17f, 5, () -> {
            final Item tweenInfo = new Item(3);
            tweenInfo.set(0, GAME_BORDER - 30, 30).configure(28000, TweenEquation.QUAD_INOUT);
            tweenInfo.set(1, 10, HEIGHT + 40).configure(28000, TweenEquation.LINEAR);
            tweenInfo.setImmediate(2, 8);
            return new TrackBeamEnemy(4, 1, tweenInfo, 2f, true, 0f, offset, 2.5f, 45, 720);
        }));
        game.addEnemy(EnemyGroup.createLazyGroup(0.17f, 5, () -> {
            final Item tweenInfo = new Item(3);
            tweenInfo.set(0, GAME_BORDER - 10, 50).configure(28000, TweenEquation.QUAD_INOUT);
            tweenInfo.set(1, 10, HEIGHT + 40).configure(28000, TweenEquation.LINEAR);
            tweenInfo.setImmediate(2, 8);
            return new TrackBeamEnemy(4, 1, tweenInfo, 2f, true, 0f, offset, 2.5f, 45, 720);
        }));

        game.player.setHp(5);
        game.setScore(0);
    }

    @Override
    public boolean postUpdate(float dt) {
        time += dt;
        switch (wave) {
            case 0:
                if (time > 2) {
                    game.addEnemy(EnemyGroup.createImmediateGroup(0.19f, 4, () -> new ShiftEnemy(5, 0, 15, 8)));
                    time = 0;
                    ++wave;
                }
                break;
            case 1:
                if (time > 0.5) {
                    game.addEnemy(EnemyGroup.createImmediateGroup(0.2f, 8, i -> {
                        return new DropEnemy(5, GAME_BORDER / 8 * i - 20, 0, 12);
                    }));
                    time = 0;
                    ++wave;
                }
                break;
            case 2:
            case 3:
            case 4:
                if (time > 0.75) {
                    final float playerX = game.player.getX();
                    final float playerY = game.player.getY();

                    game.addEnemyBullet(new Beam(-9, -9, 4f, 100, (float) Math.atan2(playerY, playerX), 900));
                    game.addEnemyBullet(new Beam(GAME_BORDER + 9, -9, 4f, 100, (float) Math.atan2(playerY, playerX - GAME_BORDER), 900));
                    game.addEnemyBullet(new Beam(-9, HEIGHT + 9, 4f, 100, (float) Math.atan2(playerY - HEIGHT, playerX), 900));
                    game.addEnemyBullet(new Beam(GAME_BORDER + 9, HEIGHT + 9, 4f, 100, (float) Math.atan2(playerY - HEIGHT, playerX - GAME_BORDER), 900));
                    time = 0;
                    ++wave;
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