package cz.mg.toolkit.utilities;

import cz.mg.toolkit.event.events.KeyboardButtonEvent;


public abstract class KeyboardShortcut {
    public abstract boolean matches(KeyboardButtonEvent e);
}
