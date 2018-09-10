package cz.mg.toolkit.event.adapters;

import cz.mg.toolkit.event.Event;
import cz.mg.toolkit.event.EventListener;
import cz.mg.toolkit.event.events.ActionEvent;


public abstract class ActionAdapter implements EventListener<ActionEvent> {
    @Override
    public void onEventEnter(ActionEvent e) {
    }

    @Override
    public void onEventLeave(ActionEvent e) {
    }

    @Override
    public boolean acceptsEvent(Event e) {
        return e instanceof ActionEvent;
    }
}
