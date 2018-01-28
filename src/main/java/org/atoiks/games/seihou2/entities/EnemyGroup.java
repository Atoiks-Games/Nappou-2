package org.atoiks.games.seihou2.entities;

import java.awt.Graphics;

import java.util.function.Supplier;
import java.util.stream.Stream;

public final class EnemyGroup extends IEnemy {

    private static final long serialVersionUID = 823469624677L;

    private final IEnemy[] enemies;
    private final float delay;

    private Game game;
    private float time;
    private int index;

    public EnemyGroup(float delay, IEnemy... enemies) {
        super(0);
        this.delay = delay;
        this.enemies = enemies;
    }

    public EnemyGroup(float delay, int count, Supplier<? extends IEnemy> sup) {
        this(delay, Stream.generate(sup).limit(count).toArray(IEnemy[]::new));
    }

    @Override
    public boolean isDead() {
        // A spawner cannot die
        return false;
    }

    @Override
    public int changeHp(int delta) {
        return 1;
    }

    @Override
    public void attachGame(Game game) {
        this.game = game;
    }

    @Override
    public float getX() {
        return Integer.MIN_VALUE / 2;
    }

    @Override
    public float getY() {
        return Integer.MIN_VALUE / 2;
    }

    @Override
    public void update(float dt) {
        while (index < enemies.length && (time += dt) >= delay) {
            time -= delay;
            game.addEnemy(enemies[index++]);
        }
    }

    @Override
    public void render(Graphics g) {
        // Do nothing (only in charge of sending enemies)
    }

    @Override
    public boolean collidesWith(float x, float y, float r) {
        // Never collides
        return false;
    }

    @Override
    public boolean isOutOfScreen(int width, int height) {
        // Out of screen is used for resource cleanup
        // Dispose group when all enemies are sent/spawned
        return index >= enemies.length;
    }

    @Override
    public int getScore() {
        // You cannot destroy enemy groups by attacking it...
        return 0;
    }
}