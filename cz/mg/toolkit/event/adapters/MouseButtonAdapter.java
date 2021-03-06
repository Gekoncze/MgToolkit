package cz.mg.toolkit.event.adapters;

import cz.mg.toolkit.event.Event;
import cz.mg.toolkit.event.EventListener;
import cz.mg.toolkit.environment.device.devices.Mouse;
import cz.mg.toolkit.event.contexts.InputEventContext;
import cz.mg.toolkit.event.events.MouseButtonEvent;
import cz.mg.toolkit.impl.Impl;


public abstract class MouseButtonAdapter implements EventListener<MouseButtonEvent> {
    @Override
    public final void onEventEnter(MouseButtonEvent e) {
        onMouseButtonEventEnter(e);
    }

    @Override
    public final void onEventLeave(MouseButtonEvent e) {
        onMouseButtonEventLeave(e);
    }
    
    public void onMouseButtonEventEnter(MouseButtonEvent e){
    }
    
    public void onMouseButtonEventLeave(MouseButtonEvent e){
    }
    
    public final boolean wasPressed(MouseButtonEvent e){
        return e.wasPressed();
    }
    
    public final boolean wasReleased(MouseButtonEvent e){
        return e.wasReleased();
    }
    
    public final boolean wasLeftButton(MouseButtonEvent e){
        return e.getButton() == e.getMouse().logicalToPhysicalButton(Mouse.Button.LEFT);
    }
    
    public final boolean wasMiddleButton(MouseButtonEvent e){
        return e.getButton() == e.getMouse().logicalToPhysicalButton(Mouse.Button.MIDDLE);
    }

    public final boolean wasRightButton(MouseButtonEvent e){
        return e.getButton() == e.getMouse().logicalToPhysicalButton(Mouse.Button.RIGHT);
    }
    
    public final boolean isPressed(MouseButtonEvent e){
        return e.getMouse().isButtonPressed(e.getButton());
    }
    
    public final boolean isReleased(MouseButtonEvent e){
        return e.getMouse().isButtonReleased(e.getButton());
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
    
    public final double getX(MouseButtonEvent e){
        return ((InputEventContext)e.getEventContext()).getX();
    }
    
    public final double getY(MouseButtonEvent e){
        return ((InputEventContext)e.getEventContext()).getY();
    }
    
    public final boolean wasInside(MouseButtonEvent e){
        return ((InputEventContext)e.getEventContext()).getMouseLocation() == InputEventContext.MouseLocation.INSIDE;
    }
    
    @Override
    public boolean acceptsEvent(Event e) {
        return e instanceof MouseButtonEvent;
    }
}
