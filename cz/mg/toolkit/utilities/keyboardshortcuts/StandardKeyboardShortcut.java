package cz.mg.toolkit.utilities.keyboardshortcuts;

import cz.mg.toolkit.event.events.KeyboardButtonEvent;
import cz.mg.toolkit.utilities.KeyboardShortcut;


public abstract class StandardKeyboardShortcut extends KeyboardShortcut {
    private final boolean ctrl, alt, shift;

    public StandardKeyboardShortcut(boolean ctrl, boolean alt, boolean shift) {
        this.ctrl = ctrl;
        this.alt = alt;
        this.shift = shift;
    }
    
    @Override
    public boolean matches(KeyboardButtonEvent e) {
        if(ctrl != e.getKeyboard().isCtrlPressed()) return false;
        if(alt != e.getKeyboard().isAltPressed()) return false;
        if(shift != e.getKeyboard().isShiftPressed()) return false;
        return true;
    }

    @Override
    public String toString() {
        String string = "";
        if(ctrl){
            if(string.length() > 0) string += "+";
            string += "ctrl";
        }
        if(alt){
            if(string.length() > 0) string += "+";
            string += "alt";
        }
        if(shift){
            if(string.length() > 0) string += "+";
            string += "shift";
        }
        return string;
    }
}
