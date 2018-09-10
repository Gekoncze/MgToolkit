package cz.mg.toolkit.event.events;

import cz.mg.toolkit.component.Component;
import cz.mg.toolkit.event.Event;
import cz.mg.toolkit.environment.device.devices.Mouse;


public class MouseEvent extends Event {
    private final Mouse mouse;
    private Component focus = null;

    public MouseEvent(Mouse mouse) {
        this.mouse = mouse;
    }

    public final Mouse getMouse() {
        return mouse;
    }

    public final Component getFocus() {
        return focus;
    }

    public final void setFocus(Component focus) {
        this.focus = focus;
    }
}
