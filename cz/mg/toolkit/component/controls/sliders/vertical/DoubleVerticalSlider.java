package cz.mg.toolkit.component.controls.sliders.vertical;

import cz.mg.toolkit.component.controls.VerticalSlider;


public class DoubleVerticalSlider extends VerticalSlider<Double> {
    public static final String DEFAULT_DESIGN_NAME = "double vertical slider";
    
    public DoubleVerticalSlider() {
        super(0.0, -Double.MAX_VALUE/2.0, Double.MAX_VALUE/2.0);
    }
    
    public DoubleVerticalSlider(Double defaultValue, Double minValue, Double maxValue) {
        super(defaultValue, minValue, maxValue);
    }

    @Override
    protected Double computeValue(double t, Double minValue, Double maxValue) {
        return (double)(minValue + t*(maxValue - minValue));
    }

    @Override
    protected double computeNorm(Double value, Double minValue, Double maxValue) {
        if((maxValue - minValue) == 0) return 0.5;
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
