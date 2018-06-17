package org.atoiks.games.seihou2.entities.enemy;

import se.tube42.lib.tweeny.Item;

import org.atoiks.games.seihou2.entities.bullet.Beam;

public final class TrackBeamEnemy extends TweenEnemy {

    private static final long serialVersionUID = -2145973374641410758L;
    
    private final int score;
    private final float thickness;
    private final int length;
    private final float speed;
    private final float fireInterval;
    private final float delay;
    private final float[] angleOffsets;

    private float time;
    private int bulletId;

    private boolean firstRun = true;

    public TrackBeamEnemy(int hp, int score, Item tweenInfo, final float fireInterval, boolean immediateFire, float delay, float[] angleOffsets, float thickness, int length, float speed) {
        super(hp, tweenInfo);
        this.score = score;
        this.thickness = thickness;
        this.length = length;
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
            final double angle = Math.atan2(game.player.getY() - y, game.player.getX() - x);
            game.addEnemyBullet(new Beam(x, y, thickness, length, (float) (angle + angleOffsets[bulletId]), speed));
            ++bulletId;
            time = 0;
        }
    }

	@Override
	public int getScore() {
		return score;
	}
}