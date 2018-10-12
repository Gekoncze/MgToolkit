package cz.mg.toolkit.component.controls.spinners;

import cz.mg.toolkit.component.controls.Spinner;


public class FloatSpinner extends Spinner<Float> {
    public FloatSpinner() {
        super(0.0f, 1.0f, -Float.MAX_VALUE, Float.MAX_VALUE);
    }
    
    public FloatSpinner(Float defaultValue, Float defaultStep, Float minValue, Float maxValue) {
        super(defaultValue, defaultStep, minValue, maxValue);
    }

    @Override
    protected Float plus(Float a, Float b) {
        return a + b;
    }

    @Override
    protected Float minus(Float a, Float b) {
        return a - b;
    }

    @Override
    protected boolean greater(Float a, Float b) {
        return a > b;
    }

    @Override
    protected boolean smaller(Float a, Float b) {
        return a < b;
    }

    @Override
    protected Float parseNumber(String text) throws ParseNumberException {
        try {
            return Float.parseFloat(text);
        } catch(NumberFormatException e){
            throw new ParseNumberException("Could not convert '" + text + "' to float.");
        }
    }

    @Override
    protected String numberToString(Float number) {
        return "" + number;
    }
}
