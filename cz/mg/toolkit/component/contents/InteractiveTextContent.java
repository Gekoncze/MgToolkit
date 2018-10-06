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
import cz.mg.toolkit.graphics.Graphics;
import cz.mg.toolkit.utilities.KeyboardShortcut;
import cz.mg.toolkit.utilities.keyboardshortcuts.StandardKeyboardCharacterShortcut;
import static cz.mg.toolkit.utilities.properties.SimplifiedPropertiesInterface.*;


public class InteractiveTextContent extends TextContent {
    private int caret = 0;
    private int selectionCaret = 0;
    private boolean editable = false;
    private boolean partialFocus = false;
    
    private static final KeyboardShortcut copyShortcut = new StandardKeyboardCharacterShortcut(true, false, false, 'c');
    private static final KeyboardShortcut pasteShortcut = new StandardKeyboardCharacterShortcut(true, false, false, 'v');
    private static final KeyboardShortcut cutShortcut = new StandardKeyboardCharacterShortcut(true, false, false, 'x');
    
    public InteractiveTextContent() {
        addEventListeners();
    }

    public InteractiveTextContent(String text) {
        super(text);
        addEventListeners();
    }

    private void addEventListeners() {
        getEventListeners().addLast(new BeforeDrawAdapter() {
            @Override
            public void onEventEnter(BeforeDrawEvent e) {
                setHighlighted(InteractiveTextContent.this, hasKeyboardFocus());
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
                    double h = getFont(InteractiveTextContent.this).getHeight();
                    g.setColor(getCurrentForegroundColor());
                    g.fillRectangle(x, y, w, h);
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
                
                if(hasKeyboardFocus() && editable){
                    double x = caretToPosition(caret);
                    double y = getVerticalTextPosition();
                    g.setColor(getContrastColor(InteractiveTextContent.this));
                    g.drawLine(x, y, x, y + getFont(InteractiveTextContent.this).getHeight());
                }
            }
        });
        
        getEventListeners().addLast(new MouseButtonAdapter() {
            @Override
            public void onMouseButtonEventEnter(MouseButtonEvent e) {
                if(wasLeftButton(e) && wasReleased(e) && hasMouseFocus()){
                    releaseMouseFocus();
                    redraw();
                }
                if(wasLeftButton(e) && wasPressed(e) && hasKeyboardFocus()){
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
                        getWindow().getKeystrokeRepeater().onKeyboardButtonPressedEvent(e, InteractiveTextContent.this);
                    } else if(e.wasReleased()){
                        getWindow().getKeystrokeRepeater().onKeyboardButtonReleasedEvent(e, InteractiveTextContent.this);
                    }
                }
                
                if(!e.isRepeated() && partialFocus && wasButtonPressed(e)){
                    if(caret != selectionCaret && copyShortcut.matches(e)){
                        Clipboard.getInstance().setText(copy());
                        e.consume();
                        relayout();
                        return;
                    }
                    
                    if(pasteShortcut.matches(e)){
                        paste(Clipboard.getInstance().getText());
                        e.consume();
                        relayout();
                        return;
                    }
                    
                    if(caret != selectionCaret && cutShortcut.matches(e)){
                        Clipboard.getInstance().setText(cut());
                        e.consume();
                        relayout();
                        return;
                    }
                }
                
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
        return true;
    }
    
    private int positionToCaret(double px){
        px -= getHorizontalTextPosition();
        int caret = 0;
        double minDistance = Math.abs(px - 0);
        for(int i = 0; i < getText().length(); i++){
            int currentCaret = i+1;
            double currentCaretPosition = getFont(this).getWidth(getText().substring(0, currentCaret));
            double dx = Math.abs(px - currentCaretPosition);
            if(dx < minDistance){
                minDistance = dx;
                caret = currentCaret;
            }
        }
        return caret;
    }
    
    private double caretToPosition(int cx){
        if(cx <= 0) return getHorizontalTextPosition();
        if(cx > getText().length()) cx = getText().length();
        return getFont(this).getWidth(getText().substring(0, cx)) + getHorizontalTextPosition();
    }

    public final int getCaret() {
        return caret;
    }

    public final int getSelectionCaret() {
        return selectionCaret;
    }

    public final void setCaret(int caret) {
        if(caret < 0) caret = 0;
        if(caret > getText().length()) caret = getText().length();
        this.caret = caret;
    }
    
    public final void setSelectionCaret(int selectionCaret) {
        if(selectionCaret < 0) selectionCaret = 0;
        if(selectionCaret > getText().length()) selectionCaret = getText().length();
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
    
    public final String getSelectedText(){
        if(caret == selectionCaret) return "";
        int min = Math.min(caret, selectionCaret);
        int max = Math.max(caret, selectionCaret);
        if(min < 0) min = 0;
        if(max > getText().length()) max = getText().length();
        return getText().substring(min, max);
    }
    
    public final void delete(){
        if(!editable) return;
        if(caret == selectionCaret) return;
        int min = Math.min(caret, selectionCaret);
        int max = Math.max(caret, selectionCaret);
        if(min < 0) min = 0;
        if(max > getText().length()) max = getText().length();
        
        StringBuilder sb = new StringBuilder(getText());
        sb.delete(min, max);
        setText(sb.toString());
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
        StringBuilder sb = new StringBuilder(getText());
        sb.insert(caret, s);
        setText(sb.toString());
        setCaret(caret + s.length());
        setSelectionCaret(caret);
    }
    
    public final String cut(){
        String s = copy();
        delete();
        return s;
    }
}
