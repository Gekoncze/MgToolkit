package cz.mg.toolkit.component.controls.sliders.horizontal;

import cz.mg.toolkit.component.controls.HorizontalSlider;


public class FloatHorizontalSlider extends HorizontalSlider<Float> {
    public FloatHorizontalSlider() {
        super(0.0f, -Float.MAX_VALUE/2.0f, Float.MAX_VALUE/2.0f);
    }
    
    public FloatHorizontalSlider(Float defaultValue, Float minValue, Float maxValue) {
        super(defaultValue, minValue, maxValue);
    }

    @Override
    protected Float computeValue(double t, Float minValue, Float maxValue) {
        return (float)(minValue + t*(maxValue - minValue));
    }

    @Override
    protected double computeNorm(Float value, Float minValue, Float maxValue) {
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
