package cz.mg.toolkit.event.events;

import cz.mg.toolkit.component.window.Window;


public class WindowStateEvent extends WindowEvent {
    private StateChange stateChange;
    
    public WindowStateEvent(Window window, StateChange stateChange) {
        super(window);
        this.stateChange = stateChange;
    }

    public final StateChange getStateChange() {
        return stateChange;
    }
    
    public static enum StateChange {
        SHOWN,
        HIDDEN,
        ACTIVATED,
        DEACTIVATED,
        OTHER
    }
}
