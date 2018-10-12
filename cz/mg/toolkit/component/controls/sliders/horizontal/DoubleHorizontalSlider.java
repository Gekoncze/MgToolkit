package cz.mg.toolkit.component.controls.sliders.horizontal;

import cz.mg.toolkit.component.controls.HorizontalSlider;


public class DoubleHorizontalSlider extends HorizontalSlider<Double> {
    public DoubleHorizontalSlider() {
        super(0.0, -Double.MAX_VALUE/2.0, Double.MAX_VALUE/2.0);
    }
    
    public DoubleHorizontalSlider(Double defaultValue, Double minValue, Double maxValue) {
        super(defaultValue, minValue, maxValue);
    }

    @Override
    protected Double computeValue(double t, Double minValue, Double maxValue) {
        return (double)(minValue + t*(maxValue - minValue));
    }

    @Override
    protected double computeNorm(Double value, Double minValue, Double maxValue) {
        return (double)(value - minValue)/(double)(maxValue - minValue);
    }

    @Override
    protected boolean greater(Double a, Double b) {
        return a > b;
    }

    @Override
    protected boolean smaller(Double a, Double b) {
        return a < b;
    }
}
