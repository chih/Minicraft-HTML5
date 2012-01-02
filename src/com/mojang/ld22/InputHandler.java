package com.mojang.ld22;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;

public class InputHandler implements KeyDownHandler, KeyUpHandler {
    public class Key {
        public int presses, absorbs;
        public boolean down, clicked;

        public Key() {
            keys.add(this);
        }

        public void toggle(boolean pressed) {
            if (pressed != down) {
                down = pressed;
            }
            if (pressed) {
                presses++;
            }
        }

        public void tick() {
            if (absorbs < presses) {
                absorbs++;
                clicked = true;
            } else {
                clicked = false;
            }
        }
    }

    public List<Key> keys = new ArrayList<Key>();

    public Key up = new Key();
    public Key down = new Key();
    public Key left = new Key();
    public Key right = new Key();
    public Key attack = new Key();
    public Key menu = new Key();

    public void releaseAll() {
        for (int i = 0; i < keys.size(); i++) {
            keys.get(i).down = false;
        }
    }

    public void tick() {
        for (int i = 0; i < keys.size(); i++) {
            keys.get(i).tick();
        }
    }

    public InputHandler(Game game) {
        game.canvas.addKeyDownHandler(this);
        game.canvas.addKeyUpHandler(this);
    }

    @Override
    public void onKeyDown(KeyDownEvent event) {
        toggle(event.getNativeKeyCode(), true);
        event.preventDefault();
    }

    @Override
    public void onKeyUp(KeyUpEvent event) {
        toggle(event.getNativeKeyCode(), false);
        event.preventDefault();
    }

    public void toggle(int nativeKeyCode, boolean pressed) {
        if (nativeKeyCode == '8') up.toggle(pressed);
        if (nativeKeyCode == '2') down.toggle(pressed);
        if (nativeKeyCode == '4') left.toggle(pressed);
        if (nativeKeyCode == '6') right.toggle(pressed);
        if (nativeKeyCode == 'W') up.toggle(pressed);
        if (nativeKeyCode == 'S') down.toggle(pressed);
        if (nativeKeyCode == 'A') left.toggle(pressed);
        if (nativeKeyCode == 'D') right.toggle(pressed);
        if (nativeKeyCode == KeyCodes.KEY_UP) up.toggle(pressed);
        if (nativeKeyCode == KeyCodes.KEY_DOWN) down.toggle(pressed);
        if (nativeKeyCode == KeyCodes.KEY_LEFT) left.toggle(pressed);
        if (nativeKeyCode == KeyCodes.KEY_RIGHT) right.toggle(pressed);

        if (nativeKeyCode == KeyCodes.KEY_TAB) menu.toggle(pressed);
        if (nativeKeyCode == KeyCodes.KEY_ALT) menu.toggle(pressed);
        //if (ke.getNativeKeyCode() == KeyCodes.KEY_ALT_GRAPH) menu.toggle(pressed);
        if (nativeKeyCode == ' ') attack.toggle(pressed);
        if (nativeKeyCode == KeyCodes.KEY_CTRL) attack.toggle(pressed);
        if (nativeKeyCode == '0') attack.toggle(pressed);
        if (nativeKeyCode == 45) attack.toggle(pressed);
        if (nativeKeyCode == KeyCodes.KEY_ENTER) menu.toggle(pressed);

        if (nativeKeyCode == 'X') menu.toggle(pressed);
        if (nativeKeyCode == 'C') attack.toggle(pressed);
    }
}
