package cz.mg.toolkit.component.controls.spinners;

import cz.mg.toolkit.component.controls.Spinner;


public class IntegerSpinner extends Spinner<Integer> {
    public IntegerSpinner() {
        super(0, 1, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }
    
    public IntegerSpinner(Integer defaultValue, Integer defaultStep, Integer minValue, Integer maxValue) {
        super(defaultValue, defaultStep, minValue, maxValue);
    }

    @Override
    protected Integer plus(Integer a, Integer b) {
        return a + b;
    }

    @Override
    protected Integer minus(Integer a, Integer b) {
        return a - b;
    }

    @Override
    protected boolean greater(Integer a, Integer b) {
        return a > b;
    }

    @Override
    protected boolean smaller(Integer a, Integer b) {
        return a < b;
    }

    @Override
    protected Integer parseNumber(String text) throws ParseNumberException {
        try {
            return Integer.parseInt(text);
        } catch(NumberFormatException e){
            throw new ParseNumberException("Could not convert '" + text + "' to integer.");
        }
    }

    @Override
    protected String numberToString(Integer number) {
        return "" + number;
    }
}
