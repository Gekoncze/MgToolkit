package cz.mg.toolkit.event.adapters;

import cz.mg.toolkit.event.Event;
import cz.mg.toolkit.event.EventListener;
import cz.mg.toolkit.event.events.RelayoutEvent;


public abstract class RelayoutAdapter implements EventListener<RelayoutEvent> {
    @Override
    public void onEventEnter(RelayoutEvent e) {
    }

    @Override
    public void onEventLeave(RelayoutEvent e) {
    }

    @Override
    public final boolean acceptsEvent(Event e) {
        return e instanceof RelayoutEvent;
    }
}
