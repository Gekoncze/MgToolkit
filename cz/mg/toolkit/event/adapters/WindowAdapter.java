package cz.mg.toolkit.event.adapters;

import cz.mg.toolkit.event.Event;
import cz.mg.toolkit.event.EventListener;
import cz.mg.toolkit.event.events.WindowEvent;


public abstract class WindowAdapter implements EventListener<WindowEvent> {
    @Override
    public void onEventEnter(WindowEvent e) {
    }

    @Override
    public void onEventLeave(WindowEvent e) {
    }

    @Override
    public boolean acceptsEvent(Event e) {
        return e instanceof WindowEvent;
    }
}
