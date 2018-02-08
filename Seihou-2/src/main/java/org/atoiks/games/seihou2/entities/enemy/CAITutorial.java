package org.atoiks.games.seihou2.entities.enemy;

import java.awt.Graphics;

import org.atoiks.games.seihou2.entities.bullet.PointBullet;
import org.atoiks.games.seihou2.entities.bullet.Beam;

public final class CAITutorial extends AbstractEnemy {

    private static final long serialVersionUID = 5619264522L;

    private float time;
    private boolean fireGate;
    private int bulletPattern;
    private double pi = Math.PI;
    private double spiralAngle = 0;

    public CAITutorial(int hp, float x, float y, float r) {
        super(hp, x, y, r);
    }

    @Override
    public void update(float dt) {
        time += dt;
        bulletPattern ++;

        if(getY() <= 150){
          setY(getY() + 300 * dt);
        }

         if(hp < 50 && hp >= 10){
           if(bulletPattern % 2500 == 0 ){
             spiralAngle += pi/24;
             final float x = getX();
             final float y = getY();
             game.addEnemyBullet(new PointBullet(x, y, 2, (float) (1000 * Math.cos(spiralAngle)), (float)(1000 * Math.sin(spiralAngle))));
           }
         }

           if(hp < 15){
             if(bulletPattern % 1250 == 0 ){
               spiralAngle += pi/24;
               final float x = getX();
               final float y = getY();
               game.addEnemyBullet(new PointBullet(x, y, 2, (float) (1000 * Math.cos(spiralAngle)), (float)(1000 * Math.sin(spiralAngle))));
             }
           }

         if(hp < 25 && hp >= 10){
           if(bulletPattern % 2500 == 0 ){
             final float x = getX();
             final float y = getY();
             game.addEnemyBullet(new PointBullet(x, y, 2, (float) (1000 * Math.cos(spiralAngle - pi)), (float)(1000 * Math.sin(spiralAngle - pi))));
           }
        }

        if(hp < 15){
          if(bulletPattern % 1250 == 0 ){
            final float x = getX();
            final float y = getY();
            game.addEnemyBullet(new PointBullet(x, y, 2, (float) (1000 * Math.cos(spiralAngle - pi)), (float)(1000 * Math.sin(spiralAngle - pi))));
           }
         }

         if(hp <= 15){
           if(bulletPattern % 1250 == 0 ){
             final float x = getX();
             final float y = getY();
             game.addEnemyBullet(new PointBullet(x, y, 2, (float) (1000 * Math.cos(spiralAngle + pi/2)), (float)(1000 * Math.sin(spiralAngle + pi/2))));
             game.addEnemyBullet(new PointBullet(x, y, 2, (float) (1000 * Math.cos(spiralAngle - pi/2)), (float)(1000 * Math.sin(spiralAngle - pi/2))));
           }
         }

        if(bulletPattern % 25000 == 0 ){
          double random = Math.random();
          if (random >= 0.5){
            final float x = getX();
            final float y = getY();
            final double angle = Math.atan2(game.player.getY() - y, game.player.getX() - x);
            game.addEnemyBullet(new PointBullet(x, y, 2, (float) (1000 * Math.cos(angle)), (float)(1000 * Math.sin(angle))));
            game.addEnemyBullet(new Beam(x, y, (float) 2, (float) 10, (float) (angle + pi/4), (float) 500));
            game.addEnemyBullet(new Beam(x, y, (float) 2, (float) 10, (float) (angle - pi/4), (float) 500));
            game.addEnemyBullet(new Beam(x, y, (float) 2, (float) 10, (float) (angle - pi/6), (float) 750));
            game.addEnemyBullet(new Beam(x, y, (float) 2, (float) 10, (float) (angle + pi/6), (float) 750));
            game.addEnemyBullet(new Beam(x, y, (float) 2, (float) 10, (float) (angle - pi/12), (float) 1000));
            game.addEnemyBullet(new Beam(x, y, (float) 2, (float) 10, (float) (angle + pi/12), (float) 1000));
          } else{
            final float x = getX();
            final float y = getY();
            final double angle = Math.atan2(game.player.getY() - y, game.player.getX() - x);
            game.addEnemyBullet(new Beam(x, y, (float) 10, (float) 100, (float) (angle), (float) 750));
            game.addEnemyBullet(new Beam(x, y, (float) 100, (float) 100, (float) (angle + pi/4), (float) 500));
            game.addEnemyBullet(new Beam(x, y, (float) 100, (float) 100, (float) (angle - pi/4), (float) 500));
          }
        }

        if (!fireGate && Math.cos(6 * time) < 0.5) {
            fireGate = true;
        }

        if (fireGate && Math.cos(6 * time) > 0.5) {
            fireGate = false;
            final float x = getX();
            final float y = getY();
            final double angle = Math.atan2(game.player.getY() - y, game.player.getX() - x);
            game.addEnemyBullet(new PointBullet(x, y, 2, (float) (1000 * Math.cos(angle)), (float)(1000 * Math.sin(angle))));
        }
    }

    @Override
    public void render(Graphics g) {
        // Convert to drawImage later on?
        super.render(g);
    }

    @Override
    public int getScore() {
        return 1;
    }
}
