package cz.mg.toolkit.event.events;

import cz.mg.toolkit.component.window.Window;
import cz.mg.toolkit.event.Event;


public class WindowEvent extends Event {
    private final Window window;

    public WindowEvent(Window window) {
        this.window = window;
    }

    public Window getWindow() {
        return window;
    }
}
