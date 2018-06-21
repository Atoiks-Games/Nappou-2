package org.atoiks.games.nappou2.entities;

public interface ICollidable {

    public boolean collidesWith(float x, float y, float r);
    public boolean isOutOfScreen(int width, int height);
}