package cz.mg.toolkit.component.controls.sliders.vertical;

import cz.mg.toolkit.component.controls.VerticalSlider;


public class LongVerticalSlider extends VerticalSlider<Long> {
    public static final String DEFAULT_DESIGN_NAME = "long vertical slider";
    
    public LongVerticalSlider() {
        super(0L, Long.MIN_VALUE/2L, Long.MAX_VALUE/2L);
    }
    
    public LongVerticalSlider(Long defaultValue, Long minValue, Long maxValue) {
        super(defaultValue, minValue, maxValue);
    }

    @Override
    protected Long computeValue(double t, Long minValue, Long maxValue) {
        return Math.round(minValue + t*(maxValue - minValue));
    }

    @Override
    protected double computeNorm(Long value, Long minValue, Long maxValue) {
        if((maxValue - minValue) == 0) return 0.5;
        return (double)(value - minValue)/(double)(maxValue - minValue);
    }

    @Override
    protected boolean greater(Long a, Long b) {
        return a > b;
    }

    @Override
    protected boolean smaller(Long a, Long b) {
        return a < b;
    }
}
