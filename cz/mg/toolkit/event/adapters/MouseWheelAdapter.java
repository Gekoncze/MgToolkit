package cz.mg.toolkit.event.adapters;

import cz.mg.toolkit.event.Event;
import cz.mg.toolkit.event.EventListener;
import cz.mg.toolkit.event.contexts.InputEventContext;
import cz.mg.toolkit.event.events.MouseWheelEvent;
import cz.mg.toolkit.impl.Impl;


public abstract class MouseWheelAdapter implements EventListener<MouseWheelEvent>  {
    @Override
    public final void onEventEnter(MouseWheelEvent e) {
        onMouseWheelEventEnter(e);
    }

    @Override
    public final void onEventLeave(MouseWheelEvent e) {
        onMouseWheelEventLeave(e);
    }
    
    public void onMouseWheelEventEnter(MouseWheelEvent e){
    }
    
    public void onMouseWheelEventLeave(MouseWheelEvent e){
    }
    
    public final boolean isDirectionUp(MouseWheelEvent e){
        return e.getDirection() == MouseWheelEvent.Direction.UP;
    }
    
    public final boolean isDirectionDown(MouseWheelEvent e){
        return e.getDirection() == MouseWheelEvent.Direction.DOWN;
    }
    
    public final boolean isCtrlPressed(){
        return Impl.getImplApi().getPrimaryKeyboard().isCtrlPressed();
    }
    
    public final boolean isAltPressed(){
        return Impl.getImplApi().getPrimaryKeyboard().isAltPressed();
    }
    
    public final boolean isShiftPressed(){
        return Impl.getImplApi().getPrimaryKeyboard().isShiftPressed();
    }
    
    public final boolean wasInside(MouseWheelEvent e){
        return ((InputEventContext)e.getEventContext()).getMouseLocation() == InputEventContext.MouseLocation.INSIDE;
    }

    @Override
    public boolean acceptsEvent(Event e) {
        return e instanceof MouseWheelEvent;
    }    
}
