package org.atoiks.games.seihou2.entities;

import java.io.Serializable;

import org.atoiks.games.framework.IRender;
import org.atoiks.games.framework.IUpdate;

public interface IEnemy extends ICollidable, IRender, IUpdate, Serializable {

    public float getX();
    public float getY();

    public void attachGame(Game game);
}