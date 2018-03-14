package org.atoiks.games.seihou2.entities.enemy;

import org.atoiks.games.framework2d.IGraphics;

import org.atoiks.games.seihou2.entities.bullet.PointBullet;

public final class CircularPathEnemy extends AbstractEnemy {

    private static final long serialVersionUID = 5619264522L;

    private float time;
    private boolean fireGate;
    private float cycles;
    private float rad;
    private float X;
    private float Y;
    private int dir;
    private float mod;
    private int spos;
    private float bs;

    public CircularPathEnemy(int hp, float x, float y, float r, float radius, int direction, float speedmod, int startpos, float bulletspeed) {
        super(hp, x, y, r);
        rad = radius;
        X = x;
        Y = y;
        dir = direction;
        mod = speedmod;
        spos = startpos;
        bs = bulletspeed;
    }

    @Override
    public void update(float dt) {
        time += dt;
        cycles++;

        switch(spos){
          case 1:
          setY(Y + (float) (dir * rad * Math.sin(mod * cycles/10000)));
          setX(X + (float) (rad * Math.cos(mod * cycles/10000)));
          break;

          case 2:
          setY(Y + (float) (dir * rad * Math.sin((mod * cycles/10000) + Math.PI/2)));
          setX(X + (float) (rad * Math.cos((mod * cycles/10000)+ Math.PI/2)));
          break;

          case 3:
          setY(Y + (float) (dir * rad * Math.sin((mod * cycles/10000) + Math.PI)));
          setX(X + (float) (rad * Math.cos((mod * cycles/10000)+ Math.PI)));
          break;

          case 4:
          setY(Y + (float) (dir * rad * Math.sin((mod * cycles/10000)+ 3*Math.PI/2)));
          setX(X + (float) (rad * Math.cos((mod * cycles/10000) + 3*Math.PI/2)));
          break;
        }


        final double cost = Math.cos(bs * time);
        if (!fireGate && cost < 0.01) {
            fireGate = true;
        }

        if (fireGate && cost > 0.01) {
            fireGate = false;
            final float x = getX();
            final float y = getY();
            final double angle = Math.atan2(game.player.getY() - y, game.player.getX() - x);
            game.addEnemyBullet(new PointBullet(x, y, 2, (float) (1000 * Math.cos(angle)), (float)(1000 * Math.sin(angle))));
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
