package cz.mg.toolkit.event.adapters;

import cz.mg.toolkit.event.Event;
import cz.mg.toolkit.event.EventListener;
import cz.mg.toolkit.event.events.KeyboardEvent;


public abstract class KeyboardAdapter implements EventListener<KeyboardEvent> {
    @Override
    public void onEventEnter(KeyboardEvent e) {
    }

    @Override
    public void onEventLeave(KeyboardEvent e) {
    }

    @Override
    public boolean acceptsEvent(Event e) {
        return e instanceof KeyboardEvent;
    }
}
