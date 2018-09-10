package cz.mg.toolkit.event.adapters;

import cz.mg.toolkit.event.Event;
import cz.mg.toolkit.event.EventListener;
import cz.mg.toolkit.event.events.MouseEvent;


public abstract class MouseAdapter implements EventListener<MouseEvent> {
    @Override
    public void onEventEnter(MouseEvent e) {
    }

    @Override
    public void onEventLeave(MouseEvent e) {
    }

    @Override
    public boolean acceptsEvent(Event e) {
        return e instanceof MouseEvent;
    }
}
