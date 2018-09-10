package cz.mg.toolkit.event.events;

import cz.mg.toolkit.component.window.Window;


public class WindowCloseEvent extends WindowEvent {
    public WindowCloseEvent(Window window) {
        super(window);
    }
}
