package cz.mg.toolkit.event.adapters;

import cz.mg.toolkit.event.Event;
import cz.mg.toolkit.event.EventListener;
import cz.mg.toolkit.environment.device.devices.Keyboard;
import cz.mg.toolkit.environment.device.devices.Mouse;
import cz.mg.toolkit.event.contexts.InputEventContext;
import cz.mg.toolkit.event.events.MouseMotionEvent;
import cz.mg.toolkit.impl.Impl;


public abstract class MouseMotionAdapter implements EventListener<MouseMotionEvent> {
    @Override
    public final void onEventEnter(MouseMotionEvent e) {
        onMouseMotionEventEnter(e);
    }

    @Override
    public final void onEventLeave(MouseMotionEvent e) {
        onMouseMotionEventLeave(e);
    }
    
    public void onMouseMotionEventEnter(MouseMotionEvent e){
    }
    
    public void onMouseMotionEventLeave(MouseMotionEvent e){
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
    
    public final boolean isLeftButtonPressed(MouseMotionEvent e){
        return e.getMouse().isButtonPressed(Mouse.LEFT_BUTTON);
    }
    
    public final boolean isMiddleButtonPressed(MouseMotionEvent e){
        return e.getMouse().isButtonPressed(Mouse.MIDDLE_BUTTON);
    }

    public final boolean isRightButtonPressed(MouseMotionEvent e){
        return e.getMouse().isButtonPressed(Mouse.RIGHT_BUTTON);
    }
    
    public final double getX(MouseMotionEvent e){
        return ((InputEventContext)e.getEventContext()).getX();
    }
    
    public final double getY(MouseMotionEvent e){
        return ((InputEventContext)e.getEventContext()).getY();
    }

    @Override
    public boolean acceptsEvent(Event e) {
        return e instanceof MouseMotionEvent;
    }
}
