package cz.mg.toolkit.event.adapters;

import cz.mg.toolkit.event.Event;
import cz.mg.toolkit.event.EventListener;
import cz.mg.toolkit.event.events.BeforeLayoutEvent;


public abstract class BeforeLayoutAdapter implements EventListener<BeforeLayoutEvent> {
    @Override
    public void onEventEnter(BeforeLayoutEvent e) {
    }

    @Override
    public void onEventLeave(BeforeLayoutEvent e) {
    }

    @Override
    public boolean acceptsEvent(Event e) {
        return e instanceof BeforeLayoutEvent;
    }
}
