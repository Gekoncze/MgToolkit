package cz.mg.toolkit.environment.device.devices;

import cz.mg.toolkit.environment.Cursor;
import cz.mg.toolkit.impl.ImplMouse;
import java.util.HashMap;


public final class Mouse {
    private static final int DEFAULT_ESTIMATED_AVERAGE_BUTTON_COUNT = 5;
    
    private final ImplMouse implMouse;
    private HashMap<Integer,State> buttonStates;
    private double screenX, screenY;
    private Cursor cursor;
    private int[] logicalToPhysicalButtonMap;

    public Mouse(ImplMouse implMouse) {
        this.implMouse = implMouse;
        reset();
    }

    public final ImplMouse getImplMouse() {
        return implMouse;
    }

    public final int[] getLogicalToPhysicalButtonMap() {
        return logicalToPhysicalButtonMap;
    }

    public final void setLogicalToPhysicalButtonMap(int[] logicalToPhysicalButtonMap) {
        this.logicalToPhysicalButtonMap = logicalToPhysicalButtonMap;
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
    
    public final boolean isButtonPressed(Button button){
        return isButtonPressed(logicalToPhysicalButton(button));
    }
    
    public final boolean isButtonReleased(Button button){
        return isButtonReleased(logicalToPhysicalButton(button));
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

    public final Cursor getCursor() {
        return cursor;
    }

    public final void setCursor(Cursor cursor) {
        this.cursor = cursor;
    }
    
    public int logicalToPhysicalButton(Button button){
        if(logicalToPhysicalButtonMap == null) return -1;
        else return logicalToPhysicalButtonMap[button.ordinal()];
    }
    
    public static enum State {
        PRESSED,
        RELEASED
    }
    
    public static enum Button {
        LEFT,
        MIDDLE,
        RIGHT
    }
}
