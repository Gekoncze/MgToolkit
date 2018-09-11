package cz.mg.toolkit.environment.device.devices;

import java.util.HashMap;


public final class Mouse {
    private static final int DEFAULT_ESTIMATED_AVERAGE_BUTTON_COUNT = 5;
    
    public static int LEFT_BUTTON = -1;
    public static int MIDDLE_BUTTON = -1;
    public static int RIGHT_BUTTON = -1;
    
    private HashMap<Integer,State> buttonStates;
    private double screenX, screenY;

    public Mouse() {
        reset();
    }
    
    public final void reset(){
        reset(DEFAULT_ESTIMATED_AVERAGE_BUTTON_COUNT);
    }
    
    public final void reset(int estimatedAverageButtonCount){
        screenX = 0;
        screenY = 0;
        buttonStates = new HashMap<>(estimatedAverageButtonCount*10);
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

    public final double getScreenX() {
        return screenX;
    }

    public final double getScreenY() {
        return screenY;
    }

    public final void setScreenX(double screenX) {
        this.screenX = screenX;
    }

    public final void setScreenY(double screenY) {
        this.screenY = screenY;
    }
    
    public static enum State {
        PRESSED,
        RELEASED
    }
}
