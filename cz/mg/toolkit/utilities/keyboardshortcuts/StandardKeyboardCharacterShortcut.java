package cz.mg.toolkit.utilities.keyboardshortcuts;

import cz.mg.toolkit.event.events.KeyboardButtonEvent;


public class StandardKeyboardCharacterShortcut extends StandardKeyboardShortcut {
    private final char ch;

    public StandardKeyboardCharacterShortcut(boolean ctrl, boolean alt, boolean shift, char ch) {
        super(ctrl, alt, shift);
        this.ch = ch;
    }

    @Override
    public boolean matches(KeyboardButtonEvent e) {
        return super.matches(e) && e.getCh() == ch && e.wasPressed();
    }

    @Override
    public String toString() {
        String string = super.toString();
        if(string.length() > 0) string += "+";
        string += ch;
        return string;
    }
}
