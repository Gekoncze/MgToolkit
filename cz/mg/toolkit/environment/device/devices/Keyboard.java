package cz.mg.toolkit.environment.device.devices;

import cz.mg.toolkit.impl.ImplKeyboard;
import java.util.HashMap;


public final class Keyboard {
    private static final int DEFAULT_ESTIMATED_AVERAGE_BUTTON_COUNT = 100;
    private static final int DEFAULT_ESTIMATED_AVERAGE_CHARACTER_COUNT = 100;
    
    private final ImplKeyboard implKeyboard;
    private HashMap<Integer,State> buttonStates;
    private HashMap<Character,State> characterStates;
    private int[] logicalToPhysicalButtonMap;

   
    public Keyboard(ImplKeyboard implKeyboard) {
        this.implKeyboard = implKeyboard;
        reset();
    }

    public final ImplKeyboard getImplKeyboard() {
        return implKeyboard;
    }

    public final int[] getLogicalToPhysicalButtonMap() {
        return logicalToPhysicalButtonMap;
    }
    
    public final void setLogicalToPhysicalButtonMap(int[] logicalToPhysicalButtonMap) {
        if(logicalToPhysicalButtonMap != null) if(logicalToPhysicalButtonMap.length != Button.values().length) throw new IllegalArgumentException();
        this.logicalToPhysicalButtonMap = logicalToPhysicalButtonMap;
    }
    
    public final boolean isCapsLockActive(){
        return implKeyboard.isCapsLockActive();
    }
    
    public final boolean isNumLockActive(){
        return implKeyboard.isNumLockActive();
    }
    
    public final boolean isScrollLockActive(){
        return implKeyboard.isScrollLockActive();
    }
    
    public final void setCapsLockActive(boolean value){
        implKeyboard.setCapsLockActive(value);
    }
    
    public final void setNumLockActive(boolean value){
        implKeyboard.setNumLockActive(value);
    }
    
    public final void setScrollLockActive(boolean value){
        implKeyboard.setScrollLockActive(value);
    }
    
    public final void reset(){
        reset(DEFAULT_ESTIMATED_AVERAGE_BUTTON_COUNT, DEFAULT_ESTIMATED_AVERAGE_CHARACTER_COUNT);
    }
    
    public final void reset(int estimatedAverageButtonCount, int estimatedAverageCharacterCount){
        buttonStates = new HashMap<>(estimatedAverageButtonCount*10);
        characterStates = new HashMap<>(estimatedAverageCharacterCount*10);
    }
    
    public final void pressButton(int i){
        buttonStates.put(i, State.PRESSED);
    }
    
    public final void releaseButton(int i){
        buttonStates.put(i, State.RELEASED);
    }
    
    public final boolean isButtonPressed(int i){
        State buttonState = buttonStates.get(i);
        if(buttonState == null) return false;
        return buttonState == State.PRESSED;
    }
    
    public final boolean isButtonReleased(int i){
        State buttonState = buttonStates.get(i);
        if(buttonState == null) return true;
        return buttonState == State.RELEASED;
    }
    
    public final boolean isButtonPressed(Button button){
        return isButtonPressed(logicalToPhysicalButton(button));
    }
    
    public final boolean isButtonReleased(Button button){
        return isButtonReleased(logicalToPhysicalButton(button));
    }
    
    public final void pressCharacter(char ch){
        characterStates.put(ch, State.PRESSED);
    }
    
    public final void releaseCharacter(char ch){
        characterStates.put(ch, State.RELEASED);
    }
    
    public final boolean isCharacterPressed(char ch){
        State characterState = characterStates.get(ch);
        if(characterState == null) return false;
        return characterState == State.PRESSED;
    }
    
    public final boolean isCharacterReleased(char ch){
        State characterState = characterStates.get(ch);
        if(characterState == null) return true;
        return characterState == State.RELEASED;
    }
    
    public boolean isAltPressed(){
        return isButtonPressed(Button.ALT_LEFT) || isButtonPressed(Button.ALT_RIGHT);
    }
    
    public boolean isCtrlPressed(){
        return isButtonPressed(Button.CTRL_LEFT) || isButtonPressed(Button.CTRL_RIGHT);
    }
    
    public boolean isShiftPressed(){
        return isButtonPressed(Button.SHIFT_LEFT) || isButtonPressed(Button.SHIFT_RIGHT);
    }
    
    public int logicalToPhysicalButton(Button button){
        if(logicalToPhysicalButtonMap == null) return -1;
        else return logicalToPhysicalButtonMap[button.ordinal()];
    }
    
    public Button physicalToLogicalButton(int physicalButton){
        if(logicalToPhysicalButtonMap != null){
            for(int i = 0; i < logicalToPhysicalButtonMap.length; i++){
                if(logicalToPhysicalButtonMap[i] == physicalButton){
                    return Button.values()[i];
                }
            }
        }
        return null;
    }
    
    public static enum State {
        PRESSED,
        RELEASED
    }
    
    public static enum Button {
        CTRL_LEFT,
        CTRL_RIGHT,
        ALT_LEFT,
        ALT_RIGHT,
        SHIFT_LEFT,
        SHIFT_RIGHT,

        ESC,

        F1,
        F2,
        F3,
        F4,
        F5,
        F6,
        F7,
        F8,
        F9,
        F10,
        F11,
        F12,

        PRINT_SCREEN,
        PAUSE,

        INSERT,
        DELETE,
        HOME,
        END,
        PAGE_UP,
        PAGE_DOWN,

        CAPS_LOCK,
        NUM_LOCK,
        SCROLL_LOCK,

        ENTER,
        BACKSPACE,
        TAB,
        SPACE,

        LEFT,
        RIGHT,
        UP,
        DOWN,

        NUM_0,
        NUM_1,
        NUM_2,
        NUM_3,
        NUM_4,
        NUM_5,
        NUM_6,
        NUM_7,
        NUM_8,
        NUM_9,
        NUM_ENTER,
        NUM_DIVIDE,
        NUM_MULTIPLY,
        NUM_MINUS,
        NUM_PLUS,
        NUM_COMMA,
    }
}
