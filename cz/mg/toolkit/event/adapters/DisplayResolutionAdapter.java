package cz.mg.toolkit.event.adapters;

import cz.mg.toolkit.event.Event;
import cz.mg.toolkit.event.EventListener;
import cz.mg.toolkit.event.events.DisplayResolutionEvent;


public abstract class DisplayResolutionAdapter implements EventListener<DisplayResolutionEvent> {
    @Override
    public void onEventEnter(DisplayResolutionEvent e) {
    }

    @Override
    public void onEventLeave(DisplayResolutionEvent e) {
    }

    @Override
    public boolean acceptsEvent(Event e) {
        return e instanceof DisplayResolutionEvent;
    }
}
