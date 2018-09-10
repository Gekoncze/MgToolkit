package cz.mg.toolkit.event.adapters;

import cz.mg.toolkit.event.Event;
import cz.mg.toolkit.event.EventListener;
import cz.mg.toolkit.event.events.BeforeDrawEvent;


public abstract class BeforeDrawAdapter implements EventListener<BeforeDrawEvent> {
    @Override
    public void onEventEnter(BeforeDrawEvent e) {
    }

    @Override
    public void onEventLeave(BeforeDrawEvent e) {
    }

    @Override
    public boolean acceptsEvent(Event e) {
        return e instanceof BeforeDrawEvent;
    }
}
