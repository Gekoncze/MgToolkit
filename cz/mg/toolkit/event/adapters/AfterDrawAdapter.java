package cz.mg.toolkit.event.adapters;

import cz.mg.toolkit.event.Event;
import cz.mg.toolkit.event.EventListener;
import cz.mg.toolkit.event.events.AfterDrawEvent;


public abstract class AfterDrawAdapter implements EventListener<AfterDrawEvent> {
    @Override
    public void onEventEnter(AfterDrawEvent e) {
    }

    @Override
    public void onEventLeave(AfterDrawEvent e) {
    }

    @Override
    public boolean acceptsEvent(Event e) {
        return e instanceof AfterDrawEvent;
    }
}
