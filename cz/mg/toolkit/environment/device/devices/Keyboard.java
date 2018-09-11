package cz.mg.toolkit.environment.device.devices;

import java.util.HashMap;


public final class Keyboard {
    private static final int DEFAULT_ESTIMATED_AVERAGE_BUTTON_COUNT = 100;
    private static final int DEFAULT_ESTIMATED_AVERAGE_CHARACTER_COUNT = 100;
    
    private HashMap<Integer,State> buttonStates;
    private HashMap<Character,State> characterStates;
    
    public static final HashMap<Integer,String> BUTTON_LABELS = new HashMap<>(DEFAULT_ESTIMATED_AVERAGE_BUTTON_COUNT);
    
    public static int CTRL_LEFT_BUTTON = -1;
    public static int CTRL_RIGHT_BUTTON = -1;
    public static int ALT_LEFT_BUTTON = -1;
    public static int ALT_RIGHT_BUTTON = -1;
    public static int SHIFT_LEFT_BUTTON = -1;
    public static int SHIFT_RIGHT_BUTTON = -1;
    
    public static int ESC_BUTTON = -1;
    
    public static int F1_BUTTON = -1;
    public static int F2_BUTTON = -1;
    public static int F3_BUTTON = -1;
    public static int F4_BUTTON = -1;
    public static int F5_BUTTON = -1;
    public static int F6_BUTTON = -1;
    public static int F7_BUTTON = -1;
    public static int F8_BUTTON = -1;
    public static int F9_BUTTON = -1;
    public static int F10_BUTTON = -1;
    public static int F11_BUTTON = -1;
    public static int F12_BUTTON = -1;
    
    public static int PRINT_SCREEN_BUTTON = -1;
    public static int PAUSE_BUTTON = -1;
    
    public static int INSERT_BUTTON = -1;
    public static int DELETE_BUTTON = -1;
    public static int HOME_BUTTON = -1;
    public static int END_BUTTON = -1;
    public static int PAGE_UP_BUTTON = -1;
    public static int PAGE_DOWN_BUTTON = -1;
    
    public static int CAPS_LOCK_BUTTON = -1;
    public static int NUM_LOCK_BUTTON = -1;
    public static int SCROLL_LOCK_BUTTON = -1;
    
    public static int ENTER_BUTTON = -1;
    public static int BACKSPACE_BUTTON = -1;
    public static int TAB_BUTTON = -1;
    public static int SPACE_BUTTON = -1;
    
    public static int LEFT_BUTTON = -1;
    public static int RIGHT_BUTTON = -1;
    public static int UP_BUTTON = -1;
    public static int DOWN_BUTTON = -1;
    
    public static int NUM_0_BUTTON = -1;
    public static int NUM_1_BUTTON = -1;
    public static int NUM_2_BUTTON = -1;
    public static int NUM_3_BUTTON = -1;
    public static int NUM_4_BUTTON = -1;
    public static int NUM_5_BUTTON = -1;
    public static int NUM_6_BUTTON = -1;
    public static int NUM_7_BUTTON = -1;
    public static int NUM_8_BUTTON = -1;
    public static int NUM_9_BUTTON = -1;
    public static int NUM_ENTER_BUTTON = -1;
    public static int NUM_DIVIDE_BUTTON = -1;
    public static int NUM_MULTIPLY_BUTTON = -1;
    public static int NUM_MINUS_BUTTON = -1;
    public static int NUM_PLUS_BUTTON = -1;
    public static int NUM_COMMA_BUTTON = -1;

    public Keyboard() {
        reset();
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
        return isButtonPressed(ALT_LEFT_BUTTON) || isButtonPressed(ALT_RIGHT_BUTTON);
    }
    
    public boolean isCtrlPressed(){
        return isButtonPressed(CTRL_LEFT_BUTTON) || isButtonPressed(CTRL_RIGHT_BUTTON);
    }
    
    public boolean isShiftPressed(){
        return isButtonPressed(SHIFT_LEFT_BUTTON) || isButtonPressed(SHIFT_RIGHT_BUTTON);
    }
    
    public static enum State {
        PRESSED,
        RELEASED
    }
    
    public static String getButtonLabel(int button){
        String label = BUTTON_LABELS.get(button);
        if(label == null) return "<?>";
        return label;
    }
}
