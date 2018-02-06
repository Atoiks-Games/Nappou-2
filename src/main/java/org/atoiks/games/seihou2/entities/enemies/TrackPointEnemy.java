package org.atoiks.games.seihou2.entities.enemies;

import se.tube42.lib.tweeny.Item;

import org.atoiks.games.seihou2.entities.bullet.PointBullet;

public final class TrackPointEnemy extends AbstractEnemy {

    private static final long serialVersionUID = -2145973374641410758L;
    
    private final int score;
    private final float radius;
    private final float speed;
    private final float fireInterval;
    private final float delay;
    private final float[] angleOffsets;

    private float time;
    private int bulletId;

    private boolean firstRun = true;

    public TrackPointEnemy(int hp, int score, Item tweenInfo, final float fireInterval, boolean immediateFire, float delay, float[] angleOffsets, float radius, float speed) {
        super(hp, tweenInfo);
        this.score = score;
        this.radius = radius;
        this.speed = speed;
        this.angleOffsets = angleOffsets;
        this.delay = delay;
        this.fireInterval = fireInterval;
        if (!immediateFire) {
            bulletId = angleOffsets.length;
        }
    }
    
    @Override
    public void update(float dt) {
        if (firstRun) {
            firstRun = false;
            return;
        }

        time += dt;
        if (bulletId >= angleOffsets.length) {
            if (time >= fireInterval) bulletId = 0;
        } else if (time > delay) {
            final float x = getX();
            final float y = getY();
            final double angle = Math.atan2(game.player.getY() - y, game.player.getX() - x) + angleOffsets[bulletId];
            game.addEnemyBullet(new PointBullet(x, y, radius,  (float) (speed * Math.cos(angle)), (float) (speed * Math.sin(angle))));
            ++bulletId;
            time = 0;
        }
    }

	@Override
	public int getScore() {
		return score;
	}
}