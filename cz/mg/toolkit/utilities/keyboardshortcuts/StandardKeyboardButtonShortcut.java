package cz.mg.toolkit.utilities.keyboardshortcuts;

import cz.mg.toolkit.device.devices.Keyboard;
import cz.mg.toolkit.event.events.KeyboardButtonEvent;


public class StandardKeyboardButtonShortcut extends StandardKeyboardShortcut {
    private final int button;

    public StandardKeyboardButtonShortcut(boolean ctrl, boolean alt, boolean shift, int button) {
        super(ctrl, alt, shift);
        this.button = button;
    }

    @Override
    public boolean matches(KeyboardButtonEvent e) {
        return super.matches(e) && e.getButton() == button && e.wasPressed();
    }
    
    @Override
    public String toString() {
        String string = super.toString();
        if(string.length() > 0) string += "+";
        string += Keyboard.getButtonLabel(button);
        return string;
    }
}
