package cz.mg.toolkit.event.events;

import cz.mg.toolkit.event.Event;
import cz.mg.toolkit.graphics.Designer;


public class DesignEvent extends Event {
    private final Designer designer;

    public DesignEvent(Designer designer) {
        this.designer = designer;
    }

    public Designer getDesigner() {
        return designer;
    }
}
