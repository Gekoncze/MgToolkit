package cz.mg.toolkit.component.controls.sliders.vertical;

import cz.mg.toolkit.component.controls.VerticalSlider;



public class FloatVerticalSlider extends VerticalSlider<Float> {
    public FloatVerticalSlider() {
        super(0.0f, -Float.MAX_VALUE/2.0f, Float.MAX_VALUE/2.0f);
    }
    
    public FloatVerticalSlider(Float defaultValue, Float minValue, Float maxValue) {
        super(defaultValue, minValue, maxValue);
    }

    @Override
    protected Float computeValue(double t, Float minValue, Float maxValue) {
        return (float)(minValue + t*(maxValue - minValue));
    }

    @Override
    protected double computeNorm(Float value, Float minValue, Float maxValue) {
        if((maxValue - minValue) == 0) return 0.5;
        return (double)(value - minValue)/(double)(maxValue - minValue);
    }

    @Override
    protected boolean greater(Float a, Float b) {
        return a > b;
    }

    @Override
    protected boolean smaller(Float a, Float b) {
        return a < b;
    }
}
