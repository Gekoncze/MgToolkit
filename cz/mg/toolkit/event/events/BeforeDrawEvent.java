package cz.mg.toolkit.event.events;

import cz.mg.toolkit.event.Event;


public class BeforeDrawEvent extends Event {
    private final boolean relayout;

    public BeforeDrawEvent(boolean relayout) {
        this.relayout = relayout;
    }

    public boolean isRelayout() {
        return relayout;
    }
}
