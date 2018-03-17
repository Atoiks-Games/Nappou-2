package org.atoiks.games.seihou2.entities.enemy;

import org.atoiks.games.framework2d.IGraphics;
import se.tube42.lib.tweeny.Item;
import se.tube42.lib.tweeny.TweenEquation;

import org.atoiks.games.seihou2.entities.*;
import org.atoiks.games.seihou2.entities.enemy.*;
import org.atoiks.games.seihou2.entities.bullet.*;

import org.atoiks.games.seihou2.entities.bullet.Beam;
import org.atoiks.games.seihou2.entities.bullet.PointBullet;

public final class Level1Easy extends AbstractEnemy {

    private static final long serialVersionUID = 5619264522L;

    private float time;
    private boolean fireGate;
    private int bulletPattern;
    private int enemyTime;
    private double spiralAngle = 0;
    private float initialhp;
    private float radius;

    private static final double PI_DIV_12 = Math.PI / 12;
    private static final double PI_DIV_2 = Math.PI / 2;
    private static final int[] scale = new int[]{0, 2, 4, 6, 8, 10, 12};

    public Level1Easy(int hp, float x, float y, float r) {
        super(hp, x, y, r);
        initialhp = hp;
        radius = r;
    }

    @Override
    public void update(float dt) {
        time += dt;
        bulletPattern ++;

        enemyTime++;

        if (getY() <= 150) {
            setY(getY() + 300 * dt);
        }

        final float x = getX();
        final float y = getY();

        if(hp>= 2*initialhp/3){
          if(enemyTime%30000 == 0){
            game.addEnemy(new DropEnemy(1, 30, -10, 8));
            game.addEnemy(new DropEnemy(1, 720, -10, 8));
            game.addEnemy(new DropEnemy(1, 100, -10, 8));
            game.addEnemy(new DropEnemy(1, 650, -10, 8));
          }
          if (bulletPattern % 25000 == 0) {
              final double angle = Math.atan2(game.player.getY() - y, game.player.getX() - x);
              if (Math.random() >= 0.5) {
                for (int i = 0; i < scale.length; ++i) {
                    final double k = scale[i] * PI_DIV_12;
                    final double k1 = scale[i] * -1 * PI_DIV_12;
                    final double k2 = (scale[i] * PI_DIV_12) + PI_DIV_2;
                    final double k3 = (scale[i] * -1 * PI_DIV_12) + PI_DIV_2;
                    game.addEnemyBullet(new PointBullet(x, y, 3, (float) (100 * Math.cos(k)), (float) (1000 * Math.sin(k))));
                    game.addEnemyBullet(new PointBullet(x, y, 3, (float) (100 * Math.cos(k1)), (float) (1000 * Math.sin(k1))));
                    game.addEnemyBullet(new PointBullet(x, y, 3, (float) (1000 * Math.cos(k2)), (float) (100 * Math.sin(k2))));
                    game.addEnemyBullet(new PointBullet(x, y, 3, (float) (1000 * Math.cos(k3)), (float) (100 * Math.sin(k3))));
                  }
                } else {
                  for (int i = 0; i < scale.length; ++i) {
                      final double k = scale[i] * PI_DIV_12;
                      final double k1 = scale[i] * -1 * PI_DIV_12;
                      final double k2 = (scale[i] * PI_DIV_12) + PI_DIV_2;
                      final double k3 = (scale[i] * -1 * PI_DIV_12) + PI_DIV_2;
                      game.addEnemyBullet(new PointBullet(x, y, 3, (float) (100 * Math.tan(Math.cos(k))), (float) (100 * Math.tan(Math.sin(k)))));
                      game.addEnemyBullet(new PointBullet(x, y, 3, (float) (100 * Math.tan(Math.cos(k1))), (float) (100 * Math.tan(Math.sin((k1))))));
                      game.addEnemyBullet(new PointBullet(x, y, 3, (float) (100 * Math.tan(Math.cos(k2))), (float) (100 * Math.tan(Math.sin(k2)))));
                      game.addEnemyBullet(new PointBullet(x, y, 3, (float) (100 * Math.tan(Math.cos(k3))), (float) (100 * Math.tan(Math.sin(k3)))));
                    }
                }
            }
        }else if(hp>= initialhp/3){
          if(enemyTime%60000 == 0){
            final float pi1 = (float) (2*Math.PI)/3;
            final float pi2 = (float) (3*Math.PI)/2;
            final float[] offset = { (float) (-Math.PI / 12), 0, (float) (Math.PI / 12) };

            game.addEnemy(EnemyGroup.createLazyGroup(0.17f, 5, () -> {
            final Item tweenInfo = new Item(3);
            tweenInfo.set(0, 30, 750 - 30).configure(28000, TweenEquation.QUAD_INOUT);
            tweenInfo.setImmediate(2, 8);
            tweenInfo.set(1, 10, 600 + 40).configure(28000, TweenEquation.LINEAR);
            return new RadialPointEnemy(2, 2, tweenInfo, 0.5f, true, 0f, 0f, 3, pi1, 15f, 100f);
            }));
          game.addEnemy(EnemyGroup.createLazyGroup(0.17f, 5, () -> {
            final Item tweenInfo = new Item(3);
            tweenInfo.set(0, 750 - 30, 30).configure(28000, TweenEquation.QUAD_INOUT);
            tweenInfo.set(1, 10, 600 + 40).configure(28000, TweenEquation.LINEAR);
            tweenInfo.setImmediate(2, 8);
            return new RadialPointEnemy(2, 2, tweenInfo, 0.5f, true, 0f, (float) Math.PI, 3, pi1, 15f, 100f);
          }));
          game.addEnemy(EnemyGroup.createLazyGroup(0.17f, 5, () -> {
            final Item tweenInfo = new Item(3);
            tweenInfo.set(0, 10, 750 - 10).configure(28000, TweenEquation.QUAD_INOUT);
            tweenInfo.set(1, 10, 600 + 40).configure(28000, TweenEquation.LINEAR);
            tweenInfo.setImmediate(2, 8);
            return new RadialPointEnemy(2, 2, tweenInfo, 0.5f, true, 0f, (float) pi2, 3, pi1, 15f, 100f);
          }));
          game.addEnemy(EnemyGroup.createLazyGroup(0.17f, 5, () -> {
            final Item tweenInfo = new Item(3);
            tweenInfo.set(0, 750 - 10, 10).configure(28000, TweenEquation.QUAD_INOUT);
            tweenInfo.set(1, 10, 600 + 40).configure(28000, TweenEquation.LINEAR);
            tweenInfo.setImmediate(2, 8);
            return new RadialPointEnemy(2, 2, tweenInfo, 0.5f, true, 0f, (float) pi2, 3, pi1, 15f, 100f);
          }));
          }
          if(bulletPattern % 20000 == 0){
            if (Math.random() >= 0.5) {
              game.addEnemyBullet(new PointBullet(x, y, 3, 0, 700));
            }
            if (Math.random() >= 0.5) {
              game.addEnemyBullet(new PointBullet(x+radius, y, 3, 0, 700));
            }
            if (Math.random() >= 0.5) {
              game.addEnemyBullet(new PointBullet(x-radius, y, 3, 0, 700));
            }
          }
        }else{
          if(enemyTime%60000 == 0){
            final float pi1 = (float) (2*Math.PI)/3;
            final float pi2 = (float) (3*Math.PI)/2;
            final float[] offset = { (float) (-Math.PI / 12), 0, (float) (Math.PI / 12) };

            game.addEnemy(EnemyGroup.createLazyGroup(0.17f, 5, () -> {
            final Item tweenInfo = new Item(3);
            tweenInfo.set(0, 30, 750 - 30).configure(28000, TweenEquation.QUAD_INOUT);
            tweenInfo.setImmediate(2, 8);
            tweenInfo.set(1, 10, 600 + 40).configure(28000, TweenEquation.QUAD_INOUT);
            return new RadialPointEnemy(2, 2, tweenInfo, 0.5f, true, 0f, 0f, 3, pi1, 15f, 100f);
            }));
          game.addEnemy(EnemyGroup.createLazyGroup(0.17f, 5, () -> {
            final Item tweenInfo = new Item(3);
            tweenInfo.set(0, 750 - 30, 30).configure(28000, TweenEquation.QUAD_INOUT);
            tweenInfo.set(1, 10, 600 + 40).configure(28000, TweenEquation.QUAD_INOUT);
            tweenInfo.setImmediate(2, 8);
            return new RadialPointEnemy(2, 2, tweenInfo, 0.5f, true, 0f, (float) Math.PI, 3, pi1, 15f, 100f);
          }));
          game.addEnemy(EnemyGroup.createLazyGroup(0.17f, 5, () -> {
            final Item tweenInfo = new Item(3);
            tweenInfo.set(0, 10, 750 - 10).configure(28000, TweenEquation.QUAD_INOUT);
            tweenInfo.set(1, 10, 600 + 40).configure(28000, TweenEquation.QUAD_INOUT);
            tweenInfo.setImmediate(2, 8);
            return new RadialPointEnemy(2, 2, tweenInfo, 0.5f, true, 0f, (float) pi2, 3, pi1, 15f, 100f);
          }));
          game.addEnemy(EnemyGroup.createLazyGroup(0.17f, 5, () -> {
            final Item tweenInfo = new Item(3);
            tweenInfo.set(0, 750 - 10, 10).configure(28000, TweenEquation.QUAD_INOUT);
            tweenInfo.set(1, 10, 600 + 40).configure(28000, TweenEquation.QUAD_INOUT);
            tweenInfo.setImmediate(2, 8);
            return new RadialPointEnemy(2, 2, tweenInfo, 0.5f, true, 0f, (float) pi2, 3, pi1, 15f, 100f);
          }));
          }
          if (bulletPattern % 25000 == 0) {
              final double angle = Math.atan2(game.player.getY() - y, game.player.getX() - x);
              if (Math.random() >= 0.5) {
                for (int i = 0; i < scale.length; ++i) {
                    final double k = scale[i] * PI_DIV_12;
                    final double k1 = scale[i] * -1 * PI_DIV_12;
                    final double k2 = (scale[i] * PI_DIV_12) + PI_DIV_2;
                    final double k3 = (scale[i] * -1 * PI_DIV_12) + PI_DIV_2;
                    game.addEnemyBullet(new PointBullet(x, y, 3, (float) (100 * Math.cos(k)), (float) (1000 * Math.sin(k))));
                    game.addEnemyBullet(new PointBullet(x, y, 3, (float) (100 * Math.cos(k1)), (float) (1000 * Math.sin(k1))));
                    game.addEnemyBullet(new PointBullet(x, y, 3, (float) (1000 * Math.cos(k2)), (float) (100 * Math.sin(k2))));
                    game.addEnemyBullet(new PointBullet(x, y, 3, (float) (1000 * Math.cos(k3)), (float) (100 * Math.sin(k3))));
                  }
                } else {
                  for (int i = 0; i < scale.length; ++i) {
                      final double k = scale[i] * PI_DIV_12;
                      final double k1 = scale[i] * -1 * PI_DIV_12;
                      final double k2 = (scale[i] * PI_DIV_12) + PI_DIV_2;
                      final double k3 = (scale[i] * -1 * PI_DIV_12) + PI_DIV_2;
                      game.addEnemyBullet(new PointBullet(x, y, 3, (float) (100 * Math.tan(Math.cos(k))), (float) (100 * Math.tan(Math.sin(k)))));
                      game.addEnemyBullet(new PointBullet(x, y, 3, (float) (100 * Math.tan(Math.cos(k1))), (float) (100 * Math.tan(Math.sin((k1))))));
                      game.addEnemyBullet(new PointBullet(x, y, 3, (float) (100 * Math.tan(Math.cos(k2))), (float) (100 * Math.tan(Math.sin(k2)))));
                      game.addEnemyBullet(new PointBullet(x, y, 3, (float) (100 * Math.tan(Math.cos(k3))), (float) (100 * Math.tan(Math.sin(k3)))));
                    }
                }
            }
        }
    }

    @Override
    public void render(IGraphics g) {
        // Convert to drawImage later on?
        super.render(g);
    }

    @Override
    public int getScore() {
        return 1;
    }
}
