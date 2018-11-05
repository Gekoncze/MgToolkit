package cz.mg.toolkit.component.controls;

import cz.mg.toolkit.component.containers.Panel;
import cz.mg.toolkit.component.controls.buttons.ContentButton;
import cz.mg.toolkit.event.adapters.ActionAdapter;
import cz.mg.toolkit.event.events.ActionEvent;
import cz.mg.toolkit.layout.layouts.HorizontalLayout;
import cz.mg.toolkit.layout.layouts.VerticalLayout;
import static cz.mg.toolkit.utilities.properties.SimplifiedPropertiesInterface.*;
import cz.mg.toolkit.utilities.sizepolices.FillParentSizePolicy;
import cz.mg.toolkit.utilities.sizepolices.WrapContentSizePolicy;


public abstract class Spinner<T> extends Panel {
    public static final String DEFAULT_DESIGN_NAME = "spinner";
    
    private final SinglelineTextInput text = new SinglelineTextInput();
    private final UpButton upButton = new UpButton();
    private final DownButton downButton = new DownButton();
    private T value;
    private T step;
    private T minValue;
    private T maxValue;
    
    public Spinner(T defaultValue, T defaultStep, T minValue, T maxValue) {
        if(defaultValue == null) throw new NullPointerException();
        if(defaultStep == null) throw new NullPointerException();
        if(minValue == null) throw new NullPointerException();
        if(maxValue == null) throw new NullPointerException();
        this.value = defaultValue;
        this.step = defaultStep;
        this.minValue = minValue;
        this.maxValue = maxValue;
        initComponent();
        initComponents();
        addEventListeners();
        updateText();
    }

    private void initComponent() {
        setLayout(new HorizontalLayout());
        setHorizontalSizePolicy(this, new FillParentSizePolicy());
    }

    private void initComponents() {
        Panel buttonsPanel = new Panel();
        buttonsPanel.setLayout(new VerticalLayout());
        setSizePolicy(buttonsPanel, new WrapContentSizePolicy());
        
        text.setParent(this);
        buttonsPanel.setParent(this);
        upButton.setParent(buttonsPanel);
        downButton.setParent(buttonsPanel);
        
        setSizePolicy(text, new FillParentSizePolicy());
    }

    private void addEventListeners() {
        upButton.getEventListeners().addLast(new ActionAdapter() {
            @Override
            public void onEventEnter(ActionEvent e) {
                e.consume();
                setValue(plus(value, step));
                relayout();
            }
        });
        
        downButton.getEventListeners().addLast(new ActionAdapter() {
            @Override
            public void onEventEnter(ActionEvent e) {
                e.consume();
                setValue(minus(value, step));
                relayout();
            }
        });
        
        getEventListeners().addLast(new ActionAdapter() {
            @Override
            public void onEventEnter(ActionEvent e) {
                if(e.getSource() != this){
                    updateNumber();
                }
            }
        });
    }

    public final T getValue() {
        updateNumber();
        return value;
    }

    public final void setValue(T value) {
        if(value == null) throw new NullPointerException();
        this.value = value;
        fixValue();
        updateText();
        raiseOrSendActionEvent();
    }

    public T getStep() {
        return step;
    }

    public void setStep(T step) {
        this.step = step;
    }

    public T getMinValue() {
        return minValue;
    }

    public T getMaxValue() {
        return maxValue;
    }

    public void setMinMaxValue(T minValue, T maxValue) {
        this.minValue = minValue;
        this.maxValue = maxValue;
        fixValue();
    }
    
    private void fixValue(){
        if(smaller(value, minValue)){
            value = minValue;
            updateText();
        }
        if(greater(value, maxValue)){
            value = maxValue;
            updateText();
        }
    }
    
    protected final void updateNumber(){
        try {
            value = parseNumber(text.getTextContent().getText());
            fixValue();
        } catch (ParseNumberException e) {
            onParseNumberException(e);
        }
    }
    
    protected final void updateText(){
        text.getTextContent().setText(numberToString(value));
    }
    
    private void raiseOrSendActionEvent(){
        raiseEvent(new ActionEvent(this));
    }
    
    protected abstract T plus(T a, T b);
    protected abstract T minus(T a, T b);
    protected abstract boolean greater(T a, T b);
    protected abstract boolean smaller(T a, T b);
    protected abstract T parseNumber(String text) throws ParseNumberException;
    protected abstract String numberToString(T number);
    
    protected void onParseNumberException(ParseNumberException e){
        updateText();
    }
    
    public static abstract class SpinnerButton extends ContentButton {
        public static final String DEFAULT_DESIGN_NAME = "spinner button";
        
        public SpinnerButton(Content content) {
            super(content);
        }
        
        public static class Content extends cz.mg.toolkit.component.Content {
            public static final String DEFAULT_DESIGN_NAME = "spinner button content";
        }
    }
    
    public static class UpButton extends SpinnerButton {
        public static final String DEFAULT_DESIGN_NAME = "up spinner button";
        
        public UpButton() {
            super(new Content());
        }
        
        public static class Content extends SpinnerButton.Content {
            public static final String DEFAULT_DESIGN_NAME = "up spinner button content";
        }
    }
    
    public static class DownButton extends SpinnerButton {
        public static final String DEFAULT_DESIGN_NAME = "down spinner button";
        public DownButton() {
            super(new Content());
        }
        
        public static class Content extends SpinnerButton.Content {
            public static final String DEFAULT_DESIGN_NAME = "down spinner button content";
        }
    }
    
    public static class ParseNumberException extends Exception {
        public ParseNumberException() {
        }

        public ParseNumberException(String message) {
            super(message);
        }
    }
}
