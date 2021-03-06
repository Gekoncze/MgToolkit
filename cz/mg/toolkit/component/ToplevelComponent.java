package cz.mg.toolkit.component;

import cz.mg.toolkit.utilities.KeystrokeRepeater;


public interface ToplevelComponent {
    public Component getKeyboardFocus();
    public Component getMouseFocus();
    public void setKeyboardFocus(Component component);
    public void setMouseFocus(Component component);
    public KeystrokeRepeater getKeystrokeRepeater();
}
