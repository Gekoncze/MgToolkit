package cz.mg.toolkit.event.adapters;

import cz.mg.toolkit.event.Event;
import cz.mg.toolkit.event.EventListener;
import cz.mg.toolkit.event.contexts.DesignerEventContext;
import cz.mg.toolkit.event.events.DesignEvent;
import cz.mg.toolkit.graphics.Designer;


public abstract class DesignAdapter implements EventListener<DesignEvent> {
    @Override
    public void onEventEnter(DesignEvent e) {
    }

    @Override
    public void onEventLeave(DesignEvent e) {
    }

    @Override
    public boolean acceptsEvent(Event e) {
        return e instanceof DesignEvent;
    }
    
    public Designer getDesigner(DesignEvent e){
        return ((DesignerEventContext)e.getEventContext()).getDesigner();
    }
}
