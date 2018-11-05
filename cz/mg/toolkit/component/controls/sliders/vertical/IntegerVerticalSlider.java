package cz.mg.toolkit.component.controls.sliders.vertical;

import cz.mg.toolkit.component.controls.VerticalSlider;



public class IntegerVerticalSlider extends VerticalSlider<Integer> {
    public static final String DEFAULT_DESIGN_NAME = "integer vertical slider";
    
    public IntegerVerticalSlider() {
        super(0, Integer.MIN_VALUE/2, Integer.MAX_VALUE/2);
    }
    
    public IntegerVerticalSlider(Integer defaultValue, Integer minValue, Integer maxValue) {
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
