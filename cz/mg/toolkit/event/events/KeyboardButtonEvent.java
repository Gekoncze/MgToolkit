package cz.mg.toolkit.event.events;

import cz.mg.toolkit.environment.device.devices.Keyboard;


public class KeyboardButtonEvent extends KeyboardEvent {
    private final int button;
    private final char ch;
    private boolean repeated;
    private final boolean pressed;
    private final boolean released;

    public KeyboardButtonEvent(Keyboard keyboard, int button, char ch, boolean repeated, boolean pressed, boolean released) {
        super(keyboard);
        this.button = button;
        this.ch = ch;
        this.repeated = repeated;
        this.pressed = pressed;
        this.released = released;
    }

    public int getButton() {
        return button;
    }

    public char getCh() {
        return ch;
    }

    public boolean isRepeated() {
        return repeated;
    }

    public void setRepeated(boolean repeated) {
        this.repeated = repeated;
    }
    
    public final boolean wasPressed() {
        return pressed;
    }

    public final boolean wasReleased() {
        return released;
    }
}
