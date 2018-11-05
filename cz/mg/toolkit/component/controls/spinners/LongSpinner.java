package cz.mg.toolkit.component.controls.spinners;

import cz.mg.toolkit.component.controls.Spinner;


public class LongSpinner extends Spinner<Long> {
    public static final String DEFAULT_DESIGN_NAME = "long spinner";
    
    public LongSpinner() {
        super(0L, 1L, Long.MIN_VALUE, Long.MAX_VALUE);
    }
    
    public LongSpinner(Long defaultValue, Long defaultStep, Long minValue, Long maxValue) {
        super(defaultValue, defaultStep, minValue, maxValue);
    }
    
    @Override
    protected Long plus(Long a, Long b) {
        return a + b;
    }

    @Override
    protected Long minus(Long a, Long b) {
        return a - b;
    }

    @Override
    protected boolean greater(Long a, Long b) {
        return a > b;
    }

    @Override
    protected boolean smaller(Long a, Long b) {
        return a < b;
    }

    @Override
    protected Long parseNumber(String text) throws ParseNumberException {
        try {
            return Long.parseLong(text);
        } catch(NumberFormatException e){
            throw new ParseNumberException("Could not convert '" + text + "' to long.");
        }
    }

    @Override
    protected String numberToString(Long number) {
        return "" + number;
    }
}
