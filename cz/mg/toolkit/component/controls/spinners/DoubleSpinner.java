package cz.mg.toolkit.component.controls.spinners;

import cz.mg.toolkit.component.controls.Spinner;


public class DoubleSpinner extends Spinner<Double> {
    public static final String DEFAULT_DESIGN_NAME = "double spinner";
    
    public DoubleSpinner() {
        super(0.0, 1.0, -Double.MAX_VALUE, Double.MAX_VALUE);
    }
    
    public DoubleSpinner(Double defaultValue, Double defaultStep, Double minValue, Double maxValue) {
        super(defaultValue, defaultStep, minValue, maxValue);
    }

    @Override
    protected Double plus(Double a, Double b) {
        return a + b;
    }

    @Override
    protected Double minus(Double a, Double b) {
        return a - b;
    }

    @Override
    protected boolean greater(Double a, Double b) {
        return a > b;
    }

    @Override
    protected boolean smaller(Double a, Double b) {
        return a < b;
    }

    @Override
    protected Double parseNumber(String text) throws ParseNumberException {
        try {
            return Double.parseDouble(text);
        } catch(NumberFormatException e){
            throw new ParseNumberException("Could not convert '" + text + "' to double.");
        }
    }

    @Override
    protected String numberToString(Double number) {
        return "" + number;
    }
}
