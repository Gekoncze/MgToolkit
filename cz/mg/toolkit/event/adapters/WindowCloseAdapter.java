package cz.mg.toolkit.event.adapters;

import cz.mg.toolkit.event.Event;
import cz.mg.toolkit.event.EventListener;
import cz.mg.toolkit.event.events.WindowCloseEvent;


public abstract class WindowCloseAdapter implements EventListener<WindowCloseEvent> {
    @Override
    public void onEventEnter(WindowCloseEvent e) {
    }

    @Override
    public void onEventLeave(WindowCloseEvent e) {
    }

    @Override
    public boolean acceptsEvent(Event e) {
        return e instanceof WindowCloseEvent;
    }
}
