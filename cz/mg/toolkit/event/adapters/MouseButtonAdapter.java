package cz.mg.toolkit.event.adapters;

import cz.mg.toolkit.event.Event;
import cz.mg.toolkit.event.EventListener;
import cz.mg.toolkit.environment.device.devices.Keyboard;
import cz.mg.toolkit.environment.device.devices.Mouse;
import cz.mg.toolkit.event.contexts.InputEventContext;
import cz.mg.toolkit.event.events.MouseButtonEvent;


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
        return e.getButton() == Mouse.LEFT_BUTTON;
    }
    
    public final boolean wasMiddleButton(MouseButtonEvent e){
        return e.getButton() == Mouse.MIDDLE_BUTTON;
    }

    public final boolean wasRightButton(MouseButtonEvent e){
        return e.getButton() == Mouse.RIGHT_BUTTON;
    }
    
    public final boolean isPressed(MouseButtonEvent e){
        return e.getMouse().isButtonPressed(e.getButton());
    }
    
    public final boolean isReleased(MouseButtonEvent e){
        return e.getMouse().isButtonReleased(e.getButton());
    }
    
    public final boolean isCtrlPressed(){
        return Keyboard.getInstance().isCtrlPressed();
    }
    
    public final boolean isAltPressed(){
        return Keyboard.getInstance().isAltPressed();
    }
    
    public final boolean isShiftPressed(){
        return Keyboard.getInstance().isShiftPressed();
    }
    
    public final double getX(MouseButtonEvent e){
        return ((InputEventContext)e.getEventContext()).getX();
    }
    
    public final double getY(MouseButtonEvent e){
        return ((InputEventContext)e.getEventContext()).getY();
    }
    
    @Override
    public boolean acceptsEvent(Event e) {
        return e instanceof MouseButtonEvent;
    }
}
