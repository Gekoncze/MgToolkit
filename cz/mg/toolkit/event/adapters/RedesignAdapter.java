package cz.mg.toolkit.event.adapters;

import cz.mg.toolkit.event.Event;
import cz.mg.toolkit.event.EventListener;
import cz.mg.toolkit.event.events.RedesignEvent;


public abstract class RedesignAdapter implements EventListener<RedesignEvent> {
    @Override
    public void onEventEnter(RedesignEvent e) {
    }

    @Override
    public void onEventLeave(RedesignEvent e) {
    }

    @Override
    public boolean acceptsEvent(Event e) {
        return e instanceof RedesignEvent;
    }
}
