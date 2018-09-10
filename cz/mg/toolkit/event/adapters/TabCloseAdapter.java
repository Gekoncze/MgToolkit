package cz.mg.toolkit.event.adapters;

import cz.mg.toolkit.event.Event;
import cz.mg.toolkit.event.EventListener;
import cz.mg.toolkit.event.events.TabCloseEvent;


public abstract class TabCloseAdapter implements EventListener<TabCloseEvent> {
    @Override
    public void onEventEnter(TabCloseEvent e) {
    }

    @Override
    public void onEventLeave(TabCloseEvent e) {
    }

    @Override
    public boolean acceptsEvent(Event e) {
        return e instanceof TabCloseEvent;
    }
}
