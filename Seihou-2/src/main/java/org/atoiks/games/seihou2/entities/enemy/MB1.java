package org.atoiks.games.seihou2.entities.enemy;

import org.atoiks.games.framework2d.IGraphics;

import org.atoiks.games.seihou2.entities.bullet.Beam;
import org.atoiks.games.seihou2.entities.bullet.PointBullet;

public final class MB1 extends AbstractEnemy {

    private static final long serialVersionUID = 5619264522L;

    private float time;
    private int bulletPattern;
    private int enemyTime;
    private double pi12 = Math.PI/12;

    public MB1(int hp, float x, float y, float r) {
        super(hp, x, y, r);
    }

    @Override
    public void update(float dt) {
        time += dt;
        bulletPattern ++;
        enemyTime++;

        final float x = getX();
        final float y = getY();
        final double angle = Math.atan2(game.player.getY() - y, game.player.getX() - x);


        if(enemyTime%30000 == 0){
            game.addEnemyBullet(new PointBullet(x, y, 3, (float) (100 * Math.cos(0)), (float) (1000 * Math.sin(0))));
            game.addEnemyBullet(new PointBullet(x, y, 3, (float) (100 * Math.cos(2*pi12)), (float) (1000 * Math.sin(2*pi12))));
            game.addEnemyBullet(new PointBullet(x, y, 3, (float) (100 * Math.cos(4 * pi12)), (float) (1000 * Math.sin(4*pi12))));
            game.addEnemyBullet(new PointBullet(x, y, 3, (float) (100 * Math.cos(6*pi12)), (float) (1000 * Math.sin(6*pi12))));
            game.addEnemyBullet(new PointBullet(x, y, 3, (float) (100 * Math.cos(8*pi12)), (float) (1000 * Math.sin(8*pi12))));
            game.addEnemyBullet(new PointBullet(x, y, 3, (float) (100 * Math.cos(10*pi12)), (float) (1000 * Math.sin(10*pi12))));
            game.addEnemyBullet(new PointBullet(x, y, 3, (float) (100 * Math.cos(12*pi12)), (float) (1000 * Math.sin(12*pi12))));

        }

        if((enemyTime+15000)%30000 == 0){
            game.addEnemyBullet(new PointBullet(x, y, 3, (float) (100 * Math.cos(angle - 6*pi12)), (float) (100 * Math.sin(angle - 6*pi12))));
            game.addEnemyBullet(new PointBullet(x, y, 3, (float) (200 * Math.cos(angle - 4*pi12)), (float) (200 * Math.sin(angle - 4*pi12))));
            game.addEnemyBullet(new PointBullet(x, y, 3, (float) (300 * Math.cos(angle - 2*pi12)), (float) (300 * Math.sin(angle - 2*pi12))));
            game.addEnemyBullet(new PointBullet(x, y, 3, (float) (400 * Math.cos(angle)), (float) (400 * Math.sin(angle))));
            game.addEnemyBullet(new PointBullet(x, y, 3, (float) (300 * Math.cos(angle + 2*pi12)), (float) (300 * Math.sin(angle + 2*pi12))));
            game.addEnemyBullet(new PointBullet(x, y, 3, (float) (200 * Math.cos(angle + 4*pi12)), (float) (200 * Math.sin(angle + 4*pi12))));
            game.addEnemyBullet(new PointBullet(x, y, 3, (float) (100 * Math.cos(angle + 6*pi12)), (float) (100 * Math.sin(angle + 6*pi12))));

        }

        if (getY() <= 150) {
            setY(getY() + 300 * dt);
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
