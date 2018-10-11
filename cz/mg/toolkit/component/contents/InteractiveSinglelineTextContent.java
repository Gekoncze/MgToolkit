package cz.mg.toolkit.component.contents;

import cz.mg.toolkit.environment.device.devices.Keyboard;
import cz.mg.toolkit.environment.Clipboard;
import cz.mg.toolkit.event.adapters.BeforeDrawAdapter;
import cz.mg.toolkit.event.adapters.GraphicsDrawAdapter;
import cz.mg.toolkit.event.adapters.KeyboardButtonAdapter;
import cz.mg.toolkit.event.adapters.LocalMouseButtonAdapter;
import cz.mg.toolkit.event.adapters.MouseButtonAdapter;
import cz.mg.toolkit.event.adapters.MouseMotionAdapter;
import cz.mg.toolkit.event.events.BeforeDrawEvent;
import cz.mg.toolkit.event.events.KeyboardButtonEvent;
import cz.mg.toolkit.event.events.MouseButtonEvent;
import cz.mg.toolkit.event.events.MouseMotionEvent;
import cz.mg.toolkit.graphics.Font;
import cz.mg.toolkit.graphics.Graphics;
import cz.mg.toolkit.utilities.keyboardshortcuts.CommonKeyboardShortcuts;
import static cz.mg.toolkit.utilities.properties.SimplifiedPropertiesInterface.*;
import cz.mg.toolkit.utilities.text.textmodels.StringBuilderSinglelineTextModel;


public class InteractiveSinglelineTextContent extends SinglelineTextContent {
    private int caret = 0;
    private int selectionCaret = 0;
    private boolean editable = false;
    private boolean partialFocus = false; // for read only selection and copy support
    
    public InteractiveSinglelineTextContent() {
        setTextModel(new StringBuilderSinglelineTextModel());
        addEventListeners();
    }

    public InteractiveSinglelineTextContent(String text) {
        setTextModel(new StringBuilderSinglelineTextModel());
        setText(text);
        addEventListeners();
    }

    private void addEventListeners() {
        getEventListeners().addLast(new BeforeDrawAdapter() {
            @Override
            public void onEventEnter(BeforeDrawEvent e) {
                setHighlighted(InteractiveSinglelineTextContent.this, hasKeyboardFocus());
            }
        });
        
        getEventListeners().addLast(new GraphicsDrawAdapter() {
            @Override
            public void onDrawEventEnter(Graphics g) {
                if(caret != selectionCaret) {
                    int min = Math.min(caret, selectionCaret);
                    int max = Math.max(caret, selectionCaret);
                    double x = caretToPosition(min);
                    double y = getVerticalTextPosition();
                    double w = caretToPosition(max) - x;
                    double h = getLineHeight();
                    g.setColor(getCurrentForegroundColor());
                    g.drawRectangle(x, y, w, h);
                }
            }
            
            @Override
            public void onDrawEventLeave(Graphics g) {
                if(caret != selectionCaret){
                    int min = Math.min(caret, selectionCaret);
                    double x = caretToPosition(min);
                    double y = getVerticalTextPosition();
                    g.setColor(getCurrentBackgroundColor());
                    g.drawText(getSelectedText(), x, y);
                }
                
                if(hasKeyboardFocus()){
                    double x = caretToPosition(caret);
                    double y = getVerticalTextPosition();
                    g.setColor(getContrastColor(InteractiveSinglelineTextContent.this));
                    g.drawLine(x, y, x, y + getLineHeight());
                }
            }
        });
        
        getEventListeners().addLast(new MouseButtonAdapter() {
            @Override
            public void onMouseButtonEventEnter(MouseButtonEvent e) {
                if(!wasLeftButton(e)) return;
                if(wasReleased(e) && hasMouseFocus()){
                    releaseMouseFocus();
                    redraw();
                }
                if(wasPressed(e) && hasKeyboardFocus()){
                    releaseKeyboardFocus();
                    partialFocus = false;
                    redraw();
                }
            }
        });
        
        getEventListeners().addLast(new LocalMouseButtonAdapter() {
            @Override
            public void onMouseButtonEventEnter(MouseButtonEvent e) {
                if(!wasLeftButton(e) || !wasPressed(e)) return;
                if(editable && !hasKeyboardFocus()) requestKeyboardFocus();
                requestMouseFocus();
                int c = positionToCaret(getX(e));
                setCaret(c);
                setSelectionCaret(c);
                partialFocus = true;
                relayout();
            }
        });
        
        getEventListeners().addLast(new MouseMotionAdapter() {
            @Override
            public void onMouseMotionEventLeave(MouseMotionEvent e) {
                if(!hasMouseFocus()) return;
                int c = positionToCaret(getX(e));
                if(c != caret){
                    setCaret(c);
                    relayout();
                }
            }
        });
        
        getEventListeners().addLast(new KeyboardButtonAdapter() {
            @Override
            public void onKeyboardButtonEventEnter(KeyboardButtonEvent e) {
                if(!e.isRepeated()){
                    if(e.wasPressed()){
                        getWindow().getKeystrokeRepeater().onKeyboardButtonPressedEvent(e, InteractiveSinglelineTextContent.this);
                    } else if(e.wasReleased()){
                        getWindow().getKeystrokeRepeater().onKeyboardButtonReleasedEvent(e, InteractiveSinglelineTextContent.this);
                    }
                }
            }
        });
        
        getEventListeners().addLast(new KeyboardButtonAdapter() {
            @Override
            public void onKeyboardButtonEventEnter(KeyboardButtonEvent e) {
                if(!e.isRepeated() && partialFocus && wasButtonPressed(e)){
                    if(CommonKeyboardShortcuts.SELECT_ALL.matches(e)){
                        selectionCaret = 0;
                        caret = getTextModel().characterCount();
                        e.consume();
                        redraw();
                        return;
                    }
                    
                    if(CommonKeyboardShortcuts.COPY.matches(e)){
                        if(caret != selectionCaret) Clipboard.getInstance().setText(copy());
                        e.consume();
                        relayout();
                        return;
                    }
                    
                    if(CommonKeyboardShortcuts.PASTE.matches(e)){
                        paste(Clipboard.getInstance().getText());
                        e.consume();
                        relayout();
                        return;
                    }
                    
                    if(CommonKeyboardShortcuts.CUT.matches(e)){
                        if(caret != selectionCaret) Clipboard.getInstance().setText(cut());
                        e.consume();
                        relayout();
                        return;
                    }
                }
            }
        });
        
        getEventListeners().addLast(new KeyboardButtonAdapter() {
            @Override
            public void onKeyboardButtonEventEnter(KeyboardButtonEvent e) {
                if(!hasKeyboardFocus() || !editable || !wasButtonPressed(e)) return;
                if(isPrintable(e.getCh())){
                    paste("" + e.getCh());
                    relayout();
                } else {
                    if(e.getButton() == Keyboard.LEFT_BUTTON){
                        setCaret(caret - 1);
                        if(!isShiftPressed()) setSelectionCaret(caret);
                        relayout();
                    } else if(e.getButton() == Keyboard.RIGHT_BUTTON){
                        setCaret(caret + 1);
                        if(!isShiftPressed()) setSelectionCaret(caret);
                        relayout();
                    } else if(e.getButton() == Keyboard.BACKSPACE_BUTTON){
                        if(selectionCaret == caret) setSelectionCaret(caret - 1);
                        delete();
                        relayout();
                    } else if(e.getButton() == Keyboard.DELETE_BUTTON){
                        if(selectionCaret == caret) setSelectionCaret(caret + 1);
                        delete();
                        relayout();
                    } else if(e.getButton() == Keyboard.ESC_BUTTON){
                        done();
                    }
                }
                
                e.consume();
            }
        });
    }
    
    private boolean isPrintable(char ch){
        if(ch == '\b') return false;
        if(!getFont(this).canDisplay(ch)) return false;
        if(Character.isWhitespace(ch) && ch != ' ') return false;
        if(ch < 32) return false;
        return true;
    }
    
    private int positionToCaret(double px){
        px -= getHorizontalTextPosition();
        return getClosestCharacter(getFont(this), getTextModel().getText(), px);
    }
    
    private double caretToPosition(int cx){
        if(cx <= 0) return getHorizontalTextPosition();
        if(cx > getTextModel().characterCount()) cx = getTextModel().characterCount();
        return getFont(this).getWidth(getTextModel().getText(0, cx)) + getHorizontalTextPosition();
    }
    
    private static int getClosestCharacter(Font font, String string, double px){
        int id = 0;
        double minDistance = Math.abs(px - 0);
        for(int i = 0; i < string.length(); i++){
            int currentCaret = i+1;
            double currentCaretPosition = font.getWidth(string.substring(0, currentCaret));
            double dx = Math.abs(px - currentCaretPosition);
            if(dx < minDistance){
                minDistance = dx;
                id = currentCaret;
            }
        }
        return id;
    }

    public final int getCaret() {
        return caret;
    }

    public final int getSelectionCaret() {
        return selectionCaret;
    }

    public final void setCaret(int caret) {
        if(caret < 0) caret = 0;
        if(caret > getTextModel().characterCount()) caret = getTextModel().characterCount();
        this.caret = caret;
    }
    
    public final void setSelectionCaret(int selectionCaret) {
        if(selectionCaret < 0) selectionCaret = 0;
        if(selectionCaret > getTextModel().characterCount()) selectionCaret = getTextModel().characterCount();
        this.selectionCaret = selectionCaret;
    }
    
    public final double getCaretPosition(){
        return caretToPosition(caret);
    }

    public final boolean isEditable() {
        return editable;
    }

    public final void setEditable(boolean editable) {
        this.editable = editable;
    }
    
    private int getMinCatet(){
        int min = Math.min(caret, selectionCaret);
        if(min < 0) min = 0;
        if(min > getTextModel().characterCount()) min = getTextModel().characterCount();
        return min;
    }
    
    private int getMaxCatet(){
        int max = Math.max(caret, selectionCaret);
        if(max < 0) max = 0;
        if(max > getTextModel().characterCount()) max = getTextModel().characterCount();
        return max;
    }
    
    public final String getSelectedText(){
        if(caret == selectionCaret) return "";
        int min = getMinCatet();
        int max = getMaxCatet();
        return getTextModel().getText(min, max);
    }
    
    public final void delete(){
        if(!editable) return;
        if(caret == selectionCaret) return;
        int min = getMinCatet();
        int max = getMaxCatet();
        getTextModel().remove(min, max);
        setCaret(min);
        setSelectionCaret(caret);
    }
    
    public final String copy(){
        return getSelectedText();
    }
    
    public final void paste(String s){
        if(s == null) return;
        if(s.length() <= 0) return;
        if(!editable) return;
        if(selectionCaret != caret) delete();
        getTextModel().insert(caret, s);
        setCaret(caret + s.length());
        setSelectionCaret(caret);
    }
    
    public final String cut(){
        String s = copy();
        delete();
        return s;
    }
    
    public final void done(){
        releaseMouseFocus();
        releaseKeyboardFocus();
        partialFocus = false;
        setSelectionCaret(caret);
        redraw();
    }
}
