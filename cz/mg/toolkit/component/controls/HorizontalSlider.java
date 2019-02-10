package cz.mg.toolkit.component.controls;

import cz.mg.toolkit.event.adapters.MouseButtonAdapter;
import cz.mg.toolkit.event.adapters.MouseMotionAdapter;
import cz.mg.toolkit.event.events.ActionEvent;
import cz.mg.toolkit.event.events.MouseButtonEvent;
import cz.mg.toolkit.event.events.MouseMotionEvent;
import static cz.mg.toolkit.utilities.properties.SimplifiedPropertiesInterface.*;
import cz.mg.toolkit.utilities.sizepolices.FillParentSizePolicy;


public abstract class HorizontalSlider<T> extends Slider<T> {
    public static final String DEFAULT_DESIGN_NAME = "horizontal slider";
    
    public HorizontalSlider(T defaultValue, T minValue, T maxValue) {
        super(defaultValue, minValue, maxValue);
        initComponent();
        addEventListeners();
    }
    
    private void initComponent(){
        setHorizontalSizePolicy(this, new FillParentSizePolicy());
        setVerticalSizePolicy(this, new FixedSizePolicy());
    }

    private void addEventListeners() {
        getEventListeners().addLast(new MouseButtonAdapter() {
            @Override
            public void onMouseButtonEventEnter(MouseButtonEvent e) {
                if(!wasLeftButton(e)) return;
                if(wasInside(e)){
                    if(wasPressed(e)){
                        requestMouseFocus();
                        updateSliderPosition(getX(e));
                        redraw();
                        raiseOrSendActionEvent();
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
                updateSliderPosition(getX(e));
                redraw();
                raiseOrSendActionEvent();
            }
        });
    }
    
    private void updateSliderPosition(double x){
        double ss = getSliderSize();
        double size = getWidth() - ss;
        size = Math.max(0, size);
        double t = (x - ss/2) / size;
        T value = computeValue(t, getMinValue(), getMaxValue());
        setValue(value);
    }

    public double getSliderPosition(){
        double size = getWidth() - getSliderSize();
        return computeNorm(getValue(), getMinValue(), getMaxValue()) * size + getSliderSize()/2;
    }
    
    public double getSliderSize(){
        return getHeight();
    }
    
    private void raiseOrSendActionEvent(){
        raiseEvent(new ActionEvent(this));
    }
}
