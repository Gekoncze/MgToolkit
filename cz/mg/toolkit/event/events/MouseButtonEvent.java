package cz.mg.toolkit.event.events;

import cz.mg.toolkit.device.devices.Mouse;


public final class MouseButtonEvent extends MouseEvent {
    private final int button;
    private final boolean pressed;
    private final boolean released;

    public MouseButtonEvent(Mouse mouse, int button, boolean pressed, boolean released) {
        super(mouse);
        this.button = button;
        this.pressed = pressed;
        this.released = released;
    }

    public final int getButton() {
        return button;
    }

    public final boolean wasPressed() {
        return pressed;
    }

    public final boolean wasReleased() {
        return released;
    }
}
