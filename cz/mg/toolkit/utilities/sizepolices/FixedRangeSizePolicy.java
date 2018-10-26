package cz.mg.toolkit.utilities.sizepolices;

import cz.mg.toolkit.component.Component;
import cz.mg.toolkit.utilities.SizePolicy;
import static cz.mg.toolkit.utilities.properties.PropertiesInterface.*;


public class FixedRangeSizePolicy implements SizePolicy {
    private double minValue;
    private double maxValue;

    public FixedRangeSizePolicy(double minValue, double maxValue) {
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    public final double getMinValue() {
        return minValue;
    }

    public final void setMinValue(double minValue) {
        this.minValue = minValue;
    }

    public final double getMaxValue() {
        return maxValue;
    }

    public final void setMaxValue(double maxValue) {
        this.maxValue = maxValue;
    }
    
    @Override
    public void beforeLayoutUpdateHorizontal(Component component) {
        setMinWidth(component, minValue);
        setMaxWidth(component, maxValue);
    }

    @Override
    public void beforeLayoutUpdateVertical(Component component) {
        setMinHeight(component, minValue);
        setMaxHeight(component, maxValue);
    }

    @Override
    public void afterLayoutUpdateHorizontal(Component component) {
    }

    @Override
    public void afterLayoutUpdateVertical(Component component) {
    }
}
