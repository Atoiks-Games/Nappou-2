package org.atoiks.games.seihou2.entities.enemy;

import java.awt.Graphics;

import org.atoiks.games.seihou2.entities.bullet.PointBullet;

public final class ShiftEnemy extends AbstractEnemy {

    private static final long serialVersionUID = 5619264522L;

    private float time;
    private int bullets;

    public ShiftEnemy(int hp, float x, float y, float r) {
        super(hp, x, y, r);
    }

    @Override
    public void update(float dt) {
        time += dt;

        setX(getX() + 300 * dt);

        if (bullets > 5) {
            if (time > 0.5) bullets = 0;
        } else if (time > 0.05) {
            game.addEnemyBullet(new PointBullet(getX(), getY(), 3, 0, 175));
            ++bullets;
            time = 0;
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