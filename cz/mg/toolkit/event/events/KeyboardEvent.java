package cz.mg.toolkit.event.events;

import cz.mg.toolkit.component.Component;
import cz.mg.toolkit.event.Event;
import cz.mg.toolkit.environment.device.devices.Keyboard;


public class KeyboardEvent extends Event {
    private final Keyboard keyboard;
    private Component focus = null;

    public KeyboardEvent(Keyboard keyboard) {
        this.keyboard = keyboard;
    }

    public final Keyboard getKeyboard() {
        return keyboard;
    }

    public final Component getFocus() {
        return focus;
    }

    public final void setFocus(Component focus) {
        this.focus = focus;
    }
}
