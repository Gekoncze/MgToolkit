package cz.mg.toolkit.event.adapters;

import cz.mg.toolkit.event.Event;
import cz.mg.toolkit.event.EventListener;
import cz.mg.toolkit.event.events.LayoutEvent;


public abstract class LayoutAdapter implements EventListener<LayoutEvent> {
    @Override
    public void onEventEnter(LayoutEvent e) {
    }

    @Override
    public void onEventLeave(LayoutEvent e) {
    }

    @Override
    public boolean acceptsEvent(Event e) {
        return e instanceof LayoutEvent;
    }
}
