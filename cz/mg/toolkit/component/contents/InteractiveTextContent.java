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
import cz.mg.toolkit.utilities.keyboardshortcuts.CommonKeyboardShortcuts;
import static cz.mg.toolkit.utilities.properties.SimplifiedPropertiesInterface.*;
import cz.mg.toolkit.utilities.text.Caret;


public class InteractiveTextContent extends TextContent {
    public static final String DEFAULT_DESIGN_NAME = "interactive text content";
    
    private boolean editable = false;
    private boolean partialFocus = false; // for load only selection and copy support
    
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
                getTextModel().getTextArrangement().getBeginCaret().setCaret(getX(e), getY(e));
                getTextModel().getTextArrangement().getEndCaret().setCaret(getX(e), getY(e));
                partialFocus = true;
                redraw();
            }
        });
        
        getEventListeners().addLast(new MouseMotionAdapter() {
            @Override
            public void onMouseMotionEventLeave(MouseMotionEvent e) {
                if(!hasMouseFocus()) return;
                getTextModel().getTextArrangement().getEndCaret().setCaret(getX(e), getY(e));
                redraw();
            }
        });
        
        getEventListeners().addLast(new KeyboardButtonAdapter() {
            @Override
            public void onKeyboardButtonEventEnter(KeyboardButtonEvent e) {
                if(!e.isRepeated()){
                    if(e.wasPressed()){
                        getToplevelComponent().getKeystrokeRepeater().onKeyboardButtonPressedEvent(e, InteractiveTextContent.this);
                    } else if(e.wasReleased()){
                        getToplevelComponent().getKeystrokeRepeater().onKeyboardButtonReleasedEvent(e, InteractiveTextContent.this);
                    }
                }
            }
        });
        
        getEventListeners().addLast(new KeyboardButtonAdapter() {
            @Override
            public void onKeyboardButtonEventEnter(KeyboardButtonEvent e) {
                if(!e.isRepeated() && partialFocus && wasButtonPressed(e)){
                    Caret beginCaret = getTextModel().getTextArrangement().getBeginCaret();
                    Caret endCaret = getTextModel().getTextArrangement().getEndCaret();
                    
                    if(CommonKeyboardShortcuts.SELECT_ALL.matches(e)){
                        beginCaret.setCaret(0);
                        endCaret.setCaret(getTextModel().getText().length());
                        redraw();
                        return;
                    }
                    
                    if(CommonKeyboardShortcuts.COPY.matches(e)){
                        if(beginCaret.getCaret() != endCaret.getCaret()) Clipboard.getInstance().setText(getTextModel().copy());
                        redraw();
                        return;
                    }
                    
                    if(editable && CommonKeyboardShortcuts.PASTE.matches(e)){
                        String text = Clipboard.getInstance().getText();
                        if(text != null && text.length() > 0) getTextModel().paste(text);
                        relayout();
                        return;
                    }
                    
                    if(editable && CommonKeyboardShortcuts.CUT.matches(e)){
                        if(beginCaret.getCaret() != endCaret.getCaret()) Clipboard.getInstance().setText(getTextModel().cut());
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

                Caret beginCaret = getTextModel().getTextArrangement().getBeginCaret();
                Caret endCaret = getTextModel().getTextArrangement().getEndCaret();

                switch(e.getLogicalButton()){
                    case LEFT:
                        endCaret.moveHorizontally(-1);
                        if(!isShiftPressed()) beginCaret.setCaret(endCaret.getCaret());
                        redraw();
                        break;

                    case RIGHT:
                        endCaret.moveHorizontally(+1);
                        if(!isShiftPressed()) beginCaret.setCaret(endCaret.getCaret());
                        redraw();
                        break;

                    case UP:
                        endCaret.moveVertically(-1);
                        if(!isShiftPressed()) beginCaret.setCaret(endCaret.getCaret());
                        redraw();
                        break;

                    case DOWN:
                        endCaret.moveVertically(+1);
                        if(!isShiftPressed()) beginCaret.setCaret(endCaret.getCaret());
                        redraw();
                        break;

                    case BACKSPACE:
                        if(beginCaret.getCaret() == endCaret.getCaret()) beginCaret.moveHorizontally(-1);
                        getTextModel().delete();
                        relayout();
                        break;

                    case DELETE:
                        if(beginCaret.getCaret() == endCaret.getCaret()) beginCaret.moveHorizontally(1);
                        getTextModel().delete();
                        relayout();
                        break;

                    case ESC:
                        done();
                        break;

                    case ENTER:
                    case NUM_ENTER:
                        getTextModel().paste("\n");
                        relayout();
                        break;

                    default:
                        if(isPrintable(e.getCh())){
                            getTextModel().paste("" + e.getCh());
                            relayout();
                        }
                        break;
                }
            }
        });
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }
    
    private boolean isPrintable(char ch){
        if(ch == '\b') return false;
        if(!getFont(this).canDisplay(ch)) return false;
        if(Character.isWhitespace(ch) && ch != ' ') return false;
        if(ch < 32) return false;
        return true;
    }
    
    public final void done(){
        releaseMouseFocus();
        releaseKeyboardFocus();
        partialFocus = false;
        getTextModel().joinBeginCaret();
        redraw();
        raiseOrSendActionEvent();
    }
    
    private void raiseOrSendActionEvent(){
        raiseEvent(new ActionEvent(this));
    }
}
