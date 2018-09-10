package cz.mg.toolkit.event.adapters;

import cz.mg.toolkit.event.Event;
import cz.mg.toolkit.event.EventListener;
import cz.mg.toolkit.event.events.RedrawEvent;


public abstract class RedrawAdapter implements EventListener<RedrawEvent> {
    @Override
    public void onEventEnter(RedrawEvent e) {
    }

    @Override
    public void onEventLeave(RedrawEvent e) {
    }

    @Override
    public final boolean acceptsEvent(Event e) {
        return e instanceof RedrawEvent;
    }
}
