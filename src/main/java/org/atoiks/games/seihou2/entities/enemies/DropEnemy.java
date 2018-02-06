package org.atoiks.games.seihou2.entities.enemies;

import org.atoiks.games.seihou2.entities.bullet.PointBullet;

public final class DropEnemy extends AbstractEnemy {

    private static final long serialVersionUID = 8326702143654175787L;

    private float time;
    private int bullets;
    
    public DropEnemy(int hp, float x, float y, float r) {
        super(hp, x, y, r);
    }

    @Override
    public void update(float dt) {
        time += dt;

        setY(getY() + 400 * dt);
        setX(getX() + 170 * dt);

        if (bullets > 8) {
            if (time > 0.5) bullets = 0;
        } else if (time > 0.05) {
            game.addEnemyBullet(new PointBullet(getX(), getY(), 3, 170, 150));
            ++bullets;
            time = 0;
        }
    }

	@Override
	public int getScore() {
		return 1;
	}
}