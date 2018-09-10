package cz.mg.toolkit.event.adapters;

import cz.mg.toolkit.event.Event;
import cz.mg.toolkit.event.EventListener;
import cz.mg.toolkit.device.devices.Keyboard;
import cz.mg.toolkit.event.events.MouseWheelEvent;


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
        return Keyboard.getInstance().isCtrlPressed();
    }
    
    public final boolean isAltPressed(){
        return Keyboard.getInstance().isAltPressed();
    }
    
    public final boolean isShiftPressed(){
        return Keyboard.getInstance().isShiftPressed();
    }

    @Override
    public boolean acceptsEvent(Event e) {
        return e instanceof MouseWheelEvent;
    }    
}
