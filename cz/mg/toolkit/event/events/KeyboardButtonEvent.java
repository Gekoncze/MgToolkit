package cz.mg.toolkit.event.events;

import cz.mg.toolkit.environment.device.devices.Keyboard;


public class KeyboardButtonEvent extends KeyboardEvent {
    private final int physicalButton;
    private final Keyboard.Button logicalButton;
    private final char ch;
    private boolean repeated;
    private final boolean pressed;
    private final boolean released;

    public KeyboardButtonEvent(Keyboard keyboard, int physicalButton, char ch, boolean repeated, boolean pressed, boolean released) {
        super(keyboard);
        this.physicalButton = physicalButton;
        this.logicalButton = keyboard.physicalToLogicalButton(physicalButton);
        this.ch = ch;
        this.repeated = repeated;
        this.pressed = pressed;
        this.released = released;
    }

    public final int getPhysicalButton() {
        return physicalButton;
    }

    public final Keyboard.Button getLogicalButton() {
        return logicalButton;
    }

    public final char getCh() {
        return ch;
    }

    public final boolean isRepeated() {
        return repeated;
    }

    public final void setRepeated(boolean repeated) {
        this.repeated = repeated;
    }
    
    public final boolean wasPressed() {
        return pressed;
    }

    public final boolean wasReleased() {
        return released;
    }
}
