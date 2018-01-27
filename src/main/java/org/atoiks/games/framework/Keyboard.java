package org.atoiks.games.framework;

import java.util.BitSet;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;

public final class Keyboard extends KeyAdapter {

    private final BitSet keybuf = new BitSet(256);

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() < this.keybuf.size()) {
            this.keybuf.set(e.getKeyCode());
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() < this.keybuf.size()) {
            this.keybuf.clear(e.getKeyCode());
        }
    }

    public void reset() {
        this.keybuf.clear();
    }

    public boolean isKeyDown(int keycode) {
        if (keycode < keybuf.length()) {
            return keybuf.get(keycode);
        }
        return false;
    }

    public boolean isKeyUp(int keycode) {
        if (keycode < keybuf.length()) {
            return !keybuf.get(keycode);
        }
        return true;
    }

    public boolean isKeyPressed(int keycode) {
        if (isKeyDown(keycode)) {
            keybuf.clear(keycode);
            return true;
        }
        return false;
    }
}