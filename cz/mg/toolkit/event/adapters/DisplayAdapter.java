package cz.mg.toolkit.event.adapters;

import cz.mg.toolkit.event.Event;
import cz.mg.toolkit.event.EventListener;
import cz.mg.toolkit.event.events.DisplayEvent;


public abstract class DisplayAdapter implements EventListener<DisplayEvent> {
    @Override
    public void onEventEnter(DisplayEvent e) {
    }

    @Override
    public void onEventLeave(DisplayEvent e) {
    }

    @Override
    public boolean acceptsEvent(Event e) {
        return e instanceof DisplayEvent;
    }
}
