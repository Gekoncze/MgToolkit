package cz.mg.toolkit.utilities.keyboardshortcuts;

import cz.mg.toolkit.environment.device.devices.Keyboard;
import cz.mg.toolkit.event.events.KeyboardButtonEvent;


public class StandardKeyboardButtonShortcut extends StandardKeyboardShortcut {
    private final Keyboard.Button button;
    private String label;

    public StandardKeyboardButtonShortcut(boolean ctrl, boolean alt, boolean shift, Keyboard.Button button, String label) {
        super(ctrl, alt, shift);
        this.button = button;
    }

    @Override
    public boolean matches(KeyboardButtonEvent e) {
        return super.matches(e) && e.getLogicalButton() == button && e.wasPressed();
    }
    
    @Override
    public String toString() {
        return label;
    }
}
