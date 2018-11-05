package cz.mg.toolkit.component.contents;

import cz.mg.toolkit.environment.Clipboard;
import cz.mg.toolkit.environment.device.devices.Keyboard;
import cz.mg.toolkit.event.adapters.BeforeDrawAdapter;
import cz.mg.toolkit.event.adapters.KeyboardButtonAdapter;
import cz.mg.toolkit.event.adapters.LocalMouseButtonAdapter;
import cz.mg.toolkit.event.adapters.MouseButtonAdapter;
import cz.mg.toolkit.event.adapters.MouseMotionAdapter;
import cz.mg.toolkit.event.events.ActionEvent;
import cz.mg.toolkit.event.events.BeforeDrawEvent;
import cz.mg.toolkit.event.events.KeyboardButtonEvent;
import cz.mg.toolkit.event.events.MouseButtonEvent;
import cz.mg.toolkit.event.events.MouseMotionEvent;
import cz.mg.toolkit.graphics.Font;
import cz.mg.toolkit.utilities.keyboardshortcuts.CommonKeyboardShortcuts;
import static cz.mg.toolkit.utilities.properties.PropertiesInterface.getFont;
import static cz.mg.toolkit.utilities.properties.PropertiesInterface.setHighlighted;
import cz.mg.toolkit.utilities.text.textmodels.StringBuilderMultilineTextModel;


public class InteractiveMultilineTextContent extends MultilineTextContent {
    public static final String DEFAULT_DESIGN_NAME = "interactive multiline text content";
    
    private int caret = 0;
    private int selectionCaret = 0;
    private boolean editable = false;
    private boolean partialFocus = false; // for read only selection and copy support
    
    public InteractiveMultilineTextContent() {
        setTextModel(new StringBuilderMultilineTextModel());
        addEventListeners();
    }

    public InteractiveMultilineTextContent(String text) {
        setTextModel(new StringBuilderMultilineTextModel());
        setText(text);
        addEventListeners();
    }
    
    private void addEventListeners() {
        getEventListeners().addLast(new BeforeDrawAdapter() {
            @Override
            public void onEventEnter(BeforeDrawEvent e) {
                setHighlighted(InteractiveMultilineTextContent.this, hasKeyboardFocus());
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
                if(wasPressed(e) && hasKeyboardFocus() && !wasInside(e)) done();
            }
        });
        
        getEventListeners().addLast(new LocalMouseButtonAdapter() {
            @Override
            public void onMouseButtonEventEnter(MouseButtonEvent e) {
                if(!wasLeftButton(e) || !wasPressed(e)) return;
                if(editable && !hasKeyboardFocus()) requestKeyboardFocus();
                requestMouseFocus();
                int c = positionToCaret(getX(e), getY(e));
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
                int c = positionToCaret(getX(e), getY(e));
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
                        getToplevelComponent().getKeystrokeRepeater().onKeyboardButtonPressedEvent(e, InteractiveMultilineTextContent.this);
                    } else if(e.wasReleased()){
                        getToplevelComponent().getKeystrokeRepeater().onKeyboardButtonReleasedEvent(e, InteractiveMultilineTextContent.this);
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
                    if(e.getLogicalButton() == Keyboard.Button.LEFT){
                        setCaret(caret - 1);
                        if(!isShiftPressed()) setSelectionCaret(caret);
                        relayout();
                    } else if(e.getLogicalButton() == Keyboard.Button.RIGHT){
                        setCaret(caret + 1);
                        if(!isShiftPressed()) setSelectionCaret(caret);
                        relayout();
                    } else if(e.getLogicalButton() == Keyboard.Button.UP){
                        double[] p = caretToPosition(caret);
                        p[1] -= getLineHeight() * 0.5;
                        setCaret(positionToCaret(p[0], p[1]));
                        if(!isShiftPressed()) setSelectionCaret(caret);
                        relayout();
                    } else if(e.getLogicalButton() == Keyboard.Button.DOWN){
                        double[] p = caretToPosition(caret);
                        p[1] += getLineHeight() * 1.5;
                        setCaret(positionToCaret(p[0], p[1]));
                        if(!isShiftPressed()) setSelectionCaret(caret);
                        relayout();
                    } else if(e.getLogicalButton() == Keyboard.Button.BACKSPACE){
                        if(selectionCaret == caret) setSelectionCaret(caret - 1);
                        delete();
                        relayout();
                    } else if(e.getLogicalButton() == Keyboard.Button.DELETE){
                        if(selectionCaret == caret) setSelectionCaret(caret + 1);
                        delete();
                        relayout();
                    } else if(e.getLogicalButton() == Keyboard.Button.ESC){
                        done();
                    } else if(e.getLogicalButton() == Keyboard.Button.ENTER || e.getLogicalButton() == Keyboard.Button.ENTER){
                        paste("\n");
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
        if(ch < 32) return false;
        return true;
    }
    
    private int positionToCaret(double px, double py){
        double lpy = py - getVerticalTextPosition();
        int line = getClosestLine(getFont(this), lpy);
        
        double lpx = px - getHorizontalLinePosition(line);
        int character = getClosestCharacter(getFont(this), getTextModel().getLine(line), lpx);
        
        return caretsToCaret(character, line);
    }
    
    public double[] caretToPosition(int c){
        int[] cs = caretToCarets(c);
        Font font = getFont(this);
        String textLine = getTextModel().getLine(cs[1]);
        return new double[]{
            getHorizontalLinePosition(cs[1]) + font.getWidth(textLine.substring(0, cs[0])),
            getVerticalLinePosition(cs[1])
        };
    }
    
    private int getClosestLine(Font font, double py){
        int line = (int) (py / font.getHeight());
        if(line >= getTextModel().lineCount()) line = getTextModel().lineCount() - 1;
        if(line < 0) line = 0;
        return line;
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
    
    public int[] caretToCarets(int c){
        if(c <= 0) return new int[]{0, 0};
        if(c > getTextModel().characterCount()) c = getTextModel().characterCount();
        
        int cy = 0;
        int cx = 0;
        for(int l = 0; l < getTextModel().lineCount(); l++){
            String textLine = getTextModel().getLine(l);
            if((c - textLine.length()) > 0){
                c -= textLine.length() + 1;
                cy++;
            } else {
                cx = c;
                break;
            }
        }
        return new int[]{cx, cy};
    }
    
    private int caretsToCaret(int cx, int cy){
        int caret = 0;
        for(int l = 0; l < cy; l++){
            String textLine = getTextModel().getLine(l);
            caret += textLine.length() + 1;
        }
        return caret + cx;
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
    
    public final double[] getCaretPosition(){
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
        raiseOrSendActionEvent();
    }
    
    private void raiseOrSendActionEvent(){
        raiseEvent(new ActionEvent(this));
    }
}
