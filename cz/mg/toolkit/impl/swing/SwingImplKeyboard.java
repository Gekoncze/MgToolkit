package cz.mg.toolkit.impl.swing;

import cz.mg.toolkit.environment.device.devices.Keyboard;
import cz.mg.toolkit.impl.ImplKeyboard;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;


public class SwingImplKeyboard implements ImplKeyboard {
    public SwingImplKeyboard() {
    }
    
    @Override
    public final boolean isCapsLockActive(){
        return Toolkit.getDefaultToolkit().getLockingKeyState(KeyEvent.VK_CAPS_LOCK);
    }
    
    @Override
    public final boolean isNumLockActive(){
        return Toolkit.getDefaultToolkit().getLockingKeyState(KeyEvent.VK_NUM_LOCK);
    }
    
    @Override
    public final boolean isScrollLockActive(){
        return Toolkit.getDefaultToolkit().getLockingKeyState(KeyEvent.VK_SCROLL_LOCK);
    }
    
    @Override
    public final void setCapsLockActive(boolean value){
        Toolkit.getDefaultToolkit().setLockingKeyState(KeyEvent.VK_CAPS_LOCK, value);
    }
    
    @Override
    public final void setNumLockActive(boolean value){
        Toolkit.getDefaultToolkit().setLockingKeyState(KeyEvent.VK_NUM_LOCK, value);
    }
    
    @Override
    public final void setScrollLockActive(boolean value){
        Toolkit.getDefaultToolkit().setLockingKeyState(KeyEvent.VK_SCROLL_LOCK, value);
    }
    
    static char getAndSanitizeCharacter(SwingImplApi api, java.awt.event.KeyEvent e){
        boolean upper = false;
        if(api.KEYBOARD_INSTANCE.isCapsLockActive()) upper = true;
        if(api.KEYBOARD_INSTANCE.isShiftPressed()) upper = !upper;
        if(upper) return Character.toUpperCase(getAndSanitizeCharacterRaw(api, e));
        else return getAndSanitizeCharacterRaw(api, e);
    }
    
    private static char getAndSanitizeCharacterRaw(SwingImplApi api, java.awt.event.KeyEvent e){
        if(e.getKeyChar() == 1 && api.KEYBOARD_INSTANCE.isButtonPressed(Keyboard.Button.CTRL_LEFT)) return 'a';
        if(e.getKeyChar() == 2 && api.KEYBOARD_INSTANCE.isButtonPressed(Keyboard.Button.CTRL_LEFT)) return 'b';
        if(e.getKeyChar() == 3 && api.KEYBOARD_INSTANCE.isButtonPressed(Keyboard.Button.CTRL_LEFT)) return 'c';
        if(e.getKeyChar() == 4 && api.KEYBOARD_INSTANCE.isButtonPressed(Keyboard.Button.CTRL_LEFT)) return 'd';
        if(e.getKeyChar() == 5 && api.KEYBOARD_INSTANCE.isButtonPressed(Keyboard.Button.CTRL_LEFT)) return 'e';
        if(e.getKeyChar() == 6 && api.KEYBOARD_INSTANCE.isButtonPressed(Keyboard.Button.CTRL_LEFT)) return 'f';
        if(e.getKeyChar() == 7 && api.KEYBOARD_INSTANCE.isButtonPressed(Keyboard.Button.CTRL_LEFT)) return 'g';
        if(e.getKeyChar() == 8 && api.KEYBOARD_INSTANCE.isButtonPressed(Keyboard.Button.CTRL_LEFT)) return 'h';
        if(e.getKeyChar() == 9 && api.KEYBOARD_INSTANCE.isButtonPressed(Keyboard.Button.CTRL_LEFT)) return 'i';
        if(e.getKeyChar() == 10 && api.KEYBOARD_INSTANCE.isButtonPressed(Keyboard.Button.CTRL_LEFT)) return 'j';
        if(e.getKeyChar() == 11 && api.KEYBOARD_INSTANCE.isButtonPressed(Keyboard.Button.CTRL_LEFT)) return 'k';
        if(e.getKeyChar() == 12 && api.KEYBOARD_INSTANCE.isButtonPressed(Keyboard.Button.CTRL_LEFT)) return 'l';
        if(e.getKeyChar() == 13 && api.KEYBOARD_INSTANCE.isButtonPressed(Keyboard.Button.CTRL_LEFT)) return 'm';
        if(e.getKeyChar() == 14 && api.KEYBOARD_INSTANCE.isButtonPressed(Keyboard.Button.CTRL_LEFT)) return 'n';
        if(e.getKeyChar() == 15 && api.KEYBOARD_INSTANCE.isButtonPressed(Keyboard.Button.CTRL_LEFT)) return 'o';
        if(e.getKeyChar() == 16 && api.KEYBOARD_INSTANCE.isButtonPressed(Keyboard.Button.CTRL_LEFT)) return 'p';
        if(e.getKeyChar() == 17 && api.KEYBOARD_INSTANCE.isButtonPressed(Keyboard.Button.CTRL_LEFT)) return 'q';
        if(e.getKeyChar() == 18 && api.KEYBOARD_INSTANCE.isButtonPressed(Keyboard.Button.CTRL_LEFT)) return 'r';
        if(e.getKeyChar() == 19 && api.KEYBOARD_INSTANCE.isButtonPressed(Keyboard.Button.CTRL_LEFT)) return 's';
        if(e.getKeyChar() == 20 && api.KEYBOARD_INSTANCE.isButtonPressed(Keyboard.Button.CTRL_LEFT)) return 't';
        if(e.getKeyChar() == 21 && api.KEYBOARD_INSTANCE.isButtonPressed(Keyboard.Button.CTRL_LEFT)) return 'u';
        if(e.getKeyChar() == 22 && api.KEYBOARD_INSTANCE.isButtonPressed(Keyboard.Button.CTRL_LEFT)) return 'v';
        if(e.getKeyChar() == 23 && api.KEYBOARD_INSTANCE.isButtonPressed(Keyboard.Button.CTRL_LEFT)) return 'w';
        if(e.getKeyChar() == 24 && api.KEYBOARD_INSTANCE.isButtonPressed(Keyboard.Button.CTRL_LEFT)) return 'x';
        if(e.getKeyChar() == 25 && api.KEYBOARD_INSTANCE.isButtonPressed(Keyboard.Button.CTRL_LEFT)) return 'y';
        if(e.getKeyChar() == 26 && api.KEYBOARD_INSTANCE.isButtonPressed(Keyboard.Button.CTRL_LEFT)) return 'z';
        return e.getKeyChar();
    }
    
    static int codeLocationToButton(int code, int location){
        return code + locationToDeltaCode(location);
    }
    
    static int locationToDeltaCode(int location){
        switch(location){
            case KeyEvent.KEY_LOCATION_LEFT: return 1000000;
            case KeyEvent.KEY_LOCATION_RIGHT: return 2000000;
            case KeyEvent.KEY_LOCATION_STANDARD: return 3000000;
            case KeyEvent.KEY_LOCATION_NUMPAD: return 4000000;
            case KeyEvent.KEY_LOCATION_UNKNOWN: return 5000000;
            default: throw new RuntimeException();
        }
    }
    
    public static int[] createDefaultLogicalToPhysicalButtonMap(){
        int[] map = new int[Keyboard.Button.values().length];
        
        map[Keyboard.Button.CTRL_LEFT.ordinal()] = codeLocationToButton(KeyEvent.VK_CONTROL, KeyEvent.KEY_LOCATION_LEFT);
        map[Keyboard.Button.CTRL_RIGHT.ordinal()] = codeLocationToButton(KeyEvent.VK_CONTROL, KeyEvent.KEY_LOCATION_RIGHT);
        map[Keyboard.Button.ALT_LEFT.ordinal()] = codeLocationToButton(KeyEvent.VK_ALT, KeyEvent.KEY_LOCATION_LEFT);
        map[Keyboard.Button.ALT_RIGHT.ordinal()] = codeLocationToButton(KeyEvent.VK_ALT_GRAPH, KeyEvent.KEY_LOCATION_STANDARD);
        map[Keyboard.Button.SHIFT_LEFT.ordinal()] = codeLocationToButton(KeyEvent.VK_SHIFT, KeyEvent.KEY_LOCATION_LEFT);
        map[Keyboard.Button.SHIFT_RIGHT.ordinal()] = codeLocationToButton(KeyEvent.VK_SHIFT, KeyEvent.KEY_LOCATION_RIGHT);

        map[Keyboard.Button.ESC.ordinal()] = codeLocationToButton(KeyEvent.VK_ESCAPE, KeyEvent.KEY_LOCATION_STANDARD);

        map[Keyboard.Button.F1.ordinal()] = codeLocationToButton(KeyEvent.VK_F1, KeyEvent.KEY_LOCATION_STANDARD);
        map[Keyboard.Button.F2.ordinal()] = codeLocationToButton(KeyEvent.VK_F2, KeyEvent.KEY_LOCATION_STANDARD);
        map[Keyboard.Button.F3.ordinal()] = codeLocationToButton(KeyEvent.VK_F3, KeyEvent.KEY_LOCATION_STANDARD);
        map[Keyboard.Button.F4.ordinal()] = codeLocationToButton(KeyEvent.VK_F4, KeyEvent.KEY_LOCATION_STANDARD);
        map[Keyboard.Button.F5.ordinal()] = codeLocationToButton(KeyEvent.VK_F5, KeyEvent.KEY_LOCATION_STANDARD);
        map[Keyboard.Button.F6.ordinal()] = codeLocationToButton(KeyEvent.VK_F6, KeyEvent.KEY_LOCATION_STANDARD);
        map[Keyboard.Button.F7.ordinal()] = codeLocationToButton(KeyEvent.VK_F7, KeyEvent.KEY_LOCATION_STANDARD);
        map[Keyboard.Button.F8.ordinal()] = codeLocationToButton(KeyEvent.VK_F8, KeyEvent.KEY_LOCATION_STANDARD);
        map[Keyboard.Button.F9.ordinal()] = codeLocationToButton(KeyEvent.VK_F9, KeyEvent.KEY_LOCATION_STANDARD);
        map[Keyboard.Button.F10.ordinal()] = codeLocationToButton(KeyEvent.VK_F10, KeyEvent.KEY_LOCATION_STANDARD);
        map[Keyboard.Button.F11.ordinal()] = codeLocationToButton(KeyEvent.VK_F11, KeyEvent.KEY_LOCATION_STANDARD);
        map[Keyboard.Button.F12.ordinal()] = codeLocationToButton(KeyEvent.VK_F12, KeyEvent.KEY_LOCATION_STANDARD);

        map[Keyboard.Button.PRINT_SCREEN.ordinal()] = codeLocationToButton(KeyEvent.VK_PRINTSCREEN, KeyEvent.KEY_LOCATION_STANDARD);
        map[Keyboard.Button.PAUSE.ordinal()] = codeLocationToButton(KeyEvent.VK_PAUSE, KeyEvent.KEY_LOCATION_STANDARD);

        map[Keyboard.Button.INSERT.ordinal()] = codeLocationToButton(KeyEvent.VK_INSERT, KeyEvent.KEY_LOCATION_STANDARD);
        map[Keyboard.Button.DELETE.ordinal()] = codeLocationToButton(KeyEvent.VK_DELETE, KeyEvent.KEY_LOCATION_STANDARD);
        map[Keyboard.Button.HOME.ordinal()] = codeLocationToButton(KeyEvent.VK_HOME, KeyEvent.KEY_LOCATION_STANDARD);
        map[Keyboard.Button.END.ordinal()] = codeLocationToButton(KeyEvent.VK_END, KeyEvent.KEY_LOCATION_STANDARD);
        map[Keyboard.Button.PAGE_UP.ordinal()] = codeLocationToButton(KeyEvent.VK_PAGE_UP, KeyEvent.KEY_LOCATION_STANDARD);
        map[Keyboard.Button.PAGE_DOWN.ordinal()] = codeLocationToButton(KeyEvent.VK_PAGE_DOWN, KeyEvent.KEY_LOCATION_STANDARD);

        map[Keyboard.Button.CAPS_LOCK.ordinal()] = codeLocationToButton(KeyEvent.VK_CAPS_LOCK, KeyEvent.KEY_LOCATION_STANDARD);
        map[Keyboard.Button.NUM_LOCK.ordinal()] = codeLocationToButton(KeyEvent.VK_NUM_LOCK, KeyEvent.KEY_LOCATION_NUMPAD);
        map[Keyboard.Button.SCROLL_LOCK.ordinal()] = codeLocationToButton(KeyEvent.VK_SCROLL_LOCK, KeyEvent.KEY_LOCATION_STANDARD);

        map[Keyboard.Button.ENTER.ordinal()] = codeLocationToButton(KeyEvent.VK_ENTER, KeyEvent.KEY_LOCATION_STANDARD);
        map[Keyboard.Button.BACKSPACE.ordinal()] = codeLocationToButton(KeyEvent.VK_BACK_SPACE, KeyEvent.KEY_LOCATION_STANDARD);
        map[Keyboard.Button.TAB.ordinal()] = codeLocationToButton(KeyEvent.VK_TAB, KeyEvent.KEY_LOCATION_STANDARD);
        map[Keyboard.Button.SPACE.ordinal()] = codeLocationToButton(KeyEvent.VK_SPACE, KeyEvent.KEY_LOCATION_STANDARD);

        map[Keyboard.Button.LEFT.ordinal()] = codeLocationToButton(KeyEvent.VK_LEFT, KeyEvent.KEY_LOCATION_STANDARD);
        map[Keyboard.Button.RIGHT.ordinal()] = codeLocationToButton(KeyEvent.VK_RIGHT, KeyEvent.KEY_LOCATION_STANDARD);
        map[Keyboard.Button.UP.ordinal()] = codeLocationToButton(KeyEvent.VK_UP, KeyEvent.KEY_LOCATION_STANDARD);
        map[Keyboard.Button.DOWN.ordinal()] = codeLocationToButton(KeyEvent.VK_DOWN, KeyEvent.KEY_LOCATION_STANDARD);

        map[Keyboard.Button.NUM_0.ordinal()] = codeLocationToButton(KeyEvent.VK_NUMPAD0, KeyEvent.KEY_LOCATION_NUMPAD);
        map[Keyboard.Button.NUM_1.ordinal()] = codeLocationToButton(KeyEvent.VK_NUMPAD1, KeyEvent.KEY_LOCATION_NUMPAD);
        map[Keyboard.Button.NUM_2.ordinal()] = codeLocationToButton(KeyEvent.VK_NUMPAD2, KeyEvent.KEY_LOCATION_NUMPAD);
        map[Keyboard.Button.NUM_3.ordinal()] = codeLocationToButton(KeyEvent.VK_NUMPAD3, KeyEvent.KEY_LOCATION_NUMPAD);
        map[Keyboard.Button.NUM_4.ordinal()] = codeLocationToButton(KeyEvent.VK_NUMPAD4, KeyEvent.KEY_LOCATION_NUMPAD);
        map[Keyboard.Button.NUM_5.ordinal()] = codeLocationToButton(KeyEvent.VK_NUMPAD5, KeyEvent.KEY_LOCATION_NUMPAD);
        map[Keyboard.Button.NUM_6.ordinal()] = codeLocationToButton(KeyEvent.VK_NUMPAD6, KeyEvent.KEY_LOCATION_NUMPAD);
        map[Keyboard.Button.NUM_7.ordinal()] = codeLocationToButton(KeyEvent.VK_NUMPAD7, KeyEvent.KEY_LOCATION_NUMPAD);
        map[Keyboard.Button.NUM_8.ordinal()] = codeLocationToButton(KeyEvent.VK_NUMPAD8, KeyEvent.KEY_LOCATION_NUMPAD);
        map[Keyboard.Button.NUM_9.ordinal()] = codeLocationToButton(KeyEvent.VK_NUMPAD9, KeyEvent.KEY_LOCATION_NUMPAD);
        
        map[Keyboard.Button.NUM_ENTER.ordinal()] = codeLocationToButton(KeyEvent.VK_ENTER, KeyEvent.KEY_LOCATION_NUMPAD);
        map[Keyboard.Button.NUM_DIVIDE.ordinal()] = codeLocationToButton(KeyEvent.VK_DIVIDE, KeyEvent.KEY_LOCATION_NUMPAD);
        map[Keyboard.Button.NUM_MULTIPLY.ordinal()] = codeLocationToButton(KeyEvent.VK_MULTIPLY, KeyEvent.KEY_LOCATION_NUMPAD);
        map[Keyboard.Button.NUM_MINUS.ordinal()] = codeLocationToButton(KeyEvent.VK_SUBTRACT, KeyEvent.KEY_LOCATION_NUMPAD);
        map[Keyboard.Button.NUM_PLUS.ordinal()] = codeLocationToButton(KeyEvent.VK_ADD, KeyEvent.KEY_LOCATION_NUMPAD);
        map[Keyboard.Button.NUM_COMMA.ordinal()] = codeLocationToButton(KeyEvent.VK_DECIMAL, KeyEvent.KEY_LOCATION_NUMPAD);
        
        return map;
    }
}
