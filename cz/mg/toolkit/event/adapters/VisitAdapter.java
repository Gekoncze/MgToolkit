package cz.mg.toolkit.event.adapters;

import cz.mg.toolkit.event.Event;
import cz.mg.toolkit.event.EventListener;
import cz.mg.toolkit.event.events.VisitEvent;


public abstract class VisitAdapter implements EventListener<VisitEvent> {
    @Override
    public void onEventEnter(VisitEvent e) {
    }

    @Override
    public void onEventLeave(VisitEvent e) {
    }

    @Override
    public boolean acceptsEvent(Event e) {
        return e instanceof VisitEvent;
    }
}
