package org.atoiks.games.seihou2.entities.enemies;

import se.tube42.lib.tweeny.Item;

import org.atoiks.games.seihou2.entities.bullet.PointBullet;

public final class RadialPointEnemy extends AbstractEnemy {

    private static final long serialVersionUID = 1L;

    private final int score;
    private final int intervals;
    private final float speed;
    private final float radius;
    private final float fireInterval;
    private final float delay;
    private final float initialAngle;
    private final float anglePerInterval;

    private float time;
    private int bulletId;

    private boolean firstRun = true;

    public RadialPointEnemy(int hp, int score, Item tweenInfo, final float fireInterval, boolean immediateFire, float delay, float initialAngle, int intervals, float anglePerInterval, float radius, float speed) {
        super(hp, tweenInfo);
        this.score = score;
        this.intervals = intervals;
        this.speed = speed;
        this.radius = radius;
        this.delay = delay;
        this.fireInterval = fireInterval;
        if (immediateFire) {
            bulletId = intervals;
        }
        this.initialAngle = initialAngle;
        this.anglePerInterval = anglePerInterval;
    }

    @Override
    public void update(float dt) {
        if (firstRun) {
            firstRun = false;
            return;
        }

        time += dt;
        if (bulletId >= intervals) {
            if (time >= fireInterval) bulletId = 0;
        } else if (time > delay) {
            final float angle = initialAngle + bulletId * anglePerInterval;
            game.addEnemyBullet(new PointBullet(getX(), getY(), radius, (float) (speed * Math.cos(angle)), (float) (speed * Math.sin(angle))));
        }
    }

	@Override
	public int getScore() {
		return score;
	}
}