package org.atoiks.games.seihou2.entities.enemies;

import se.tube42.lib.tweeny.Item;

import org.atoiks.games.seihou2.entities.bullet.Beam;

public final class RadialBeamEnemy extends AbstractEnemy {

    private static final long serialVersionUID = 7329566493126725388L;

    private final int score;
    private final int intervals;
    private final float thickness;
    private final float length;
    private final float fireInterval;
    private final float delay;
    private final float initialAngle;
    private final float anglePerInterval;
    private final float speed;

    private float time;
    private int bulletId;

    private boolean firstRun = true;

    public RadialBeamEnemy(int hp, int score, Item tweenInfo, final float fireInterval, boolean immediateFire, float delay, float initialAngle, int intervals, float anglePerInterval, float thickness, int length, float speed) {
        super(hp, tweenInfo);
        this.score = score;
        this.thickness = thickness;
        this.length = length;
        this.initialAngle = initialAngle;
        this.anglePerInterval = anglePerInterval;
        this.speed = speed;
        this.intervals = intervals;
        this.delay = delay;
        this.fireInterval = fireInterval;
        if (!immediateFire) {
            bulletId = intervals;
        }
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
            game.addEnemyBullet(new Beam(getX(), getY(), thickness, length, initialAngle + bulletId * anglePerInterval, speed));
            ++bulletId;
            time = 0;
        }
    }

	@Override
	public int getScore() {
		return score;
	}
}