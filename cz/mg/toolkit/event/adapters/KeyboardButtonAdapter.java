package cz.mg.toolkit.event.adapters;

import cz.mg.toolkit.event.Event;
import cz.mg.toolkit.event.EventListener;
import cz.mg.toolkit.environment.device.devices.Keyboard;
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
        return e.getKeyboard().isButtonPressed(e.getButton());
    }
    
    public final boolean isButtonReleased(KeyboardButtonEvent e){
        return e.getKeyboard().isButtonReleased(e.getButton());
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

    @Override
    public boolean acceptsEvent(Event e) {
        return e.getClass() == KeyboardButtonEvent.class;
    }
}
