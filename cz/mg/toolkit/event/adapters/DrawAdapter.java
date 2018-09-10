package cz.mg.toolkit.event.adapters;

import cz.mg.toolkit.event.Event;
import cz.mg.toolkit.event.EventListener;
import cz.mg.toolkit.event.events.DrawEvent;


public abstract class DrawAdapter implements EventListener<DrawEvent> {
    @Override
    public void onEventEnter(DrawEvent e) {
    }

    @Override
    public void onEventLeave(DrawEvent e) {
    }

    @Override
    public boolean acceptsEvent(Event e) {
        return e instanceof DrawEvent;
    }
}
