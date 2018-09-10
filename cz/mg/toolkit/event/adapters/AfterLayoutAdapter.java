package cz.mg.toolkit.event.adapters;

import cz.mg.toolkit.event.Event;
import cz.mg.toolkit.event.EventListener;
import cz.mg.toolkit.event.events.AfterLayoutEvent;


public abstract class AfterLayoutAdapter implements EventListener<AfterLayoutEvent> {
    @Override
    public void onEventEnter(AfterLayoutEvent e) {
    }
    
    @Override
    public void onEventLeave(AfterLayoutEvent e) {
    }

    @Override
    public boolean acceptsEvent(Event e) {
        return e instanceof AfterLayoutEvent;
    }
}
