package org.atoiks.games.framework;

public abstract class AbstractFrame<T> implements IFrame<T> {

    protected SceneManager sceneMgr;
    protected float secsPerUpdate;

    protected AbstractFrame(Float fps, SceneManager mgr) {
        this.sceneMgr = mgr;
        this.secsPerUpdate = 1.0f / fps;
    }

    public void init() {
        sceneMgr.switchToScene(0);
    }

    public void loop() {
        double previous = System.currentTimeMillis();
        double steps = 0.0f;

        outer:
        while (true) {
            final double now = System.currentTimeMillis();
            final double elapsed = now - previous;
            previous = now;
            steps += elapsed;

            sceneMgr.resizeCurrentScene(this.getWidth(), this.getHeight());
            while (steps >= secsPerUpdate) {
                if (!sceneMgr.updateCurrentScene(secsPerUpdate / 1000)) {
                    return;
                }
                if (sceneMgr.shouldSkipCycle()) {
                    // Reset time info
                    previous = System.currentTimeMillis();
                    steps = 0.0f;
                    continue outer; // Restart entire process
                }
                steps -= secsPerUpdate;
            }

            // Force redraw here
            renderGame();
        }
    }

    public void close() {
        // Ensures leave for Scene gets called
        sceneMgr.switchToScene(-1);
    }

    protected abstract int getWidth();

    protected abstract int getHeight();

    protected abstract void renderGame();
}