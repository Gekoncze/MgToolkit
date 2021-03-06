package cz.mg.toolkit.event.adapters;

import cz.mg.toolkit.event.Event;
import cz.mg.toolkit.event.EventListener;
import cz.mg.toolkit.event.contexts.InputEventContext;
import cz.mg.toolkit.event.events.KeyboardButtonEvent;
import cz.mg.toolkit.impl.Impl;


public abstract class KeyboardButtonAdapter implements EventListener<KeyboardButtonEvent> {
    @Override
    public final void onEventEnter(KeyboardButtonEvent e) {
        onKeyboardButtonEventEnter(e);
    }

    @Override
    public final void onEventLeave(KeyboardButtonEvent e) {
        onKeyboardButtonEventLeave(e);
    }
    
    public void onKeyboardButtonEventEnter(KeyboardButtonEvent e){
    }
    
    public void onKeyboardButtonEventLeave(KeyboardButtonEvent e){
    }
    
    public final boolean wasButtonPressed(KeyboardButtonEvent e){
        return e.wasPressed();
    }
    
    public final boolean wasButtonReleased(KeyboardButtonEvent e){
        return e.wasReleased();
    }
    
    public final boolean isButtonPressed(KeyboardButtonEvent e){
        return e.getKeyboard().isButtonPressed(e.getPhysicalButton());
    }
    
    public final boolean isButtonReleased(KeyboardButtonEvent e){
        return e.getKeyboard().isButtonReleased(e.getPhysicalButton());
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
    
    public final boolean wasInside(KeyboardButtonEvent e){
        return ((InputEventContext)e.getEventContext()).getMouseLocation() == InputEventContext.MouseLocation.INSIDE;
    }

    @Override
    public boolean acceptsEvent(Event e) {
        return e.getClass() == KeyboardButtonEvent.class;
    }
}
