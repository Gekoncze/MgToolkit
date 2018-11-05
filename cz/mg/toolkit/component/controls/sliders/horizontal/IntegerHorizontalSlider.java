package cz.mg.toolkit.component.controls.sliders.horizontal;

import cz.mg.toolkit.component.controls.HorizontalSlider;


public class IntegerHorizontalSlider extends HorizontalSlider<Integer> {
    public static final String DEFAULT_DESIGN_NAME = "integer horizontal slider";
    
    public IntegerHorizontalSlider() {
        super(0, Integer.MIN_VALUE/2, Integer.MAX_VALUE/2);
    }
    
    public IntegerHorizontalSlider(Integer defaultValue, Integer minValue, Integer maxValue) {
        super(defaultValue, minValue, maxValue);
    }

    @Override
    protected Integer computeValue(double t, Integer minValue, Integer maxValue) {
        return (int)Math.round(minValue + t*(maxValue - minValue));
    }

    @Override
    protected double computeNorm(Integer value, Integer minValue, Integer maxValue) {
        if((maxValue - minValue) == 0) return 0.5;
        return (double)(value - minValue)/(double)(maxValue - minValue);
    }

    @Override
    protected boolean greater(Integer a, Integer b) {
        return a > b;
    }

    @Override
    protected boolean smaller(Integer a, Integer b) {
        return a < b;
    }
}
