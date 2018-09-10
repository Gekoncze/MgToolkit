package cz.mg.toolkit.event.adapters;

import cz.mg.toolkit.event.Event;
import cz.mg.toolkit.event.EventListener;
import cz.mg.toolkit.event.events.WindowStateEvent;


public abstract class WindowStateAdapter implements EventListener<WindowStateEvent> {
    @Override
    public void onEventEnter(WindowStateEvent e) {
    }

    @Override
    public void onEventLeave(WindowStateEvent e) {
    }

    @Override
    public boolean acceptsEvent(Event e) {
        return e instanceof WindowStateEvent;
    }
}
