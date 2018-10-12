package cz.mg.toolkit.component.controls.sliders.horizontal;

import cz.mg.toolkit.component.controls.HorizontalSlider;


public class LongHorizontalSlider extends HorizontalSlider<Long> {
    public LongHorizontalSlider() {
        super(0L, Long.MIN_VALUE/2L, Long.MAX_VALUE/2L);
    }
    
    public LongHorizontalSlider(Long defaultValue, Long minValue, Long maxValue) {
        super(defaultValue, minValue, maxValue);
    }

    @Override
    protected Long computeValue(double t, Long minValue, Long maxValue) {
        return Math.round(minValue + t*(maxValue - minValue));
    }

    @Override
    protected double computeNorm(Long value, Long minValue, Long maxValue) {
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
