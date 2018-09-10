package cz.mg.toolkit.event.adapters;

import cz.mg.toolkit.event.Event;
import cz.mg.toolkit.event.contexts.InputEventContext;
import cz.mg.toolkit.event.events.MouseWheelEvent;


public abstract class LocalMouseWheelAdapter extends MouseWheelAdapter {
    @Override
    public boolean acceptsEvent(Event e) {
        if(!(e instanceof MouseWheelEvent)) return false;
        if(!(e.getEventContext() instanceof InputEventContext)) return false;
        InputEventContext ie = (InputEventContext) e.getEventContext();
        return ie.getMouseLocation() == InputEventContext.MouseLocation.INSIDE;
    }
}
