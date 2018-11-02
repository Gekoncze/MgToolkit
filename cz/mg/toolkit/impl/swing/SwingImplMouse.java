package cz.mg.toolkit.impl.swing;

import cz.mg.toolkit.environment.device.devices.Mouse;
import cz.mg.toolkit.impl.ImplMouse;


public class SwingImplMouse implements ImplMouse {
    public static int[] createDefaultLogicalToPhysicalButtonMap() {
        int[] map = new int[Mouse.Button.values().length];
        map[Mouse.Button.LEFT.ordinal()] = java.awt.event.MouseEvent.BUTTON1;
        map[Mouse.Button.MIDDLE.ordinal()] = java.awt.event.MouseEvent.BUTTON2;
        map[Mouse.Button.RIGHT.ordinal()] = java.awt.event.MouseEvent.BUTTON3;
        return map;
    }
}
