package cz.mg.toolkit.component.controls;

import cz.mg.toolkit.event.adapters.MouseButtonAdapter;
import cz.mg.toolkit.event.adapters.MouseMotionAdapter;
import cz.mg.toolkit.event.events.ActionEvent;
import cz.mg.toolkit.event.events.MouseButtonEvent;
import cz.mg.toolkit.event.events.MouseMotionEvent;
import static cz.mg.toolkit.utilities.properties.SimplifiedPropertiesInterface.*;


public abstract class VerticalSlider<T> extends Slider<T> {
    private static final int DEFAULT_WIDTH = 24;
    private static final int DEFAULT_HEIGHT = 128;
    
    public VerticalSlider(T defaultValue, T minValue, T maxValue) {
        super(defaultValue, minValue, maxValue);
        initComponent();
        addEventListeners();
    }

    private void initComponent() {
        setFixedSize(this, DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    private void addEventListeners() {
        getEventListeners().addLast(new MouseButtonAdapter() {
            @Override
            public void onMouseButtonEventEnter(MouseButtonEvent e) {
                if(!wasLeftButton(e)) return;
                if(wasInside(e)){
                    if(wasPressed(e)){
                        requestMouseFocus();
                    } else {
                        releaseMouseFocus();
                    }
                } else {
                    releaseMouseFocus();
                }
            }
        });
        
        getEventListeners().addLast(new MouseMotionAdapter() {
            @Override
            public void onMouseMotionEventEnter(MouseMotionEvent e) {
                if(!hasMouseFocus() || !isLeftButtonPressed(e)) return;
                double tp = getTopPadding(VerticalSlider.this);
                double bp = getBottomPadding(VerticalSlider.this);
                double size = getHeight() - tp - bp;
                size = Math.max(0, size);
                double t = (getY(e) - tp) / size;
                T value = computeValue(t, getMinValue(), getMaxValue());
                setValue(value);
                redraw();
                raiseOrSendActionEvent();
            }
        });
    }

    public double getSliderPosition(){
        double tp = getTopPadding(VerticalSlider.this);
        double bp = getBottomPadding(VerticalSlider.this);
        double size = getHeight() - tp - bp;
        return computeNorm(getValue(), getMinValue(), getMaxValue()) * size + tp;
    }
    
    private void raiseOrSendActionEvent(){
        raiseEvent(new ActionEvent(this));
    }
}
