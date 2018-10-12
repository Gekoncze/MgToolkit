package cz.mg.toolkit.component.controls;

import cz.mg.toolkit.component.containers.Panel;


public abstract class Slider<T> extends Panel {
    private T value;
    private T minValue;
    private T maxValue;

    public Slider(T defaultValue, T minValue, T maxValue) {
        if(defaultValue == null) throw new NullPointerException();
        if(minValue == null) throw new NullPointerException();
        if(maxValue == null) throw new NullPointerException();
        this.value = defaultValue;
        this.minValue = minValue;
        this.maxValue = maxValue;
    }
    
    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        if(value == null) throw new NullPointerException();
        this.value = value;
        fixValue();
    }

    public T getMinValue() {
        return minValue;
    }

    public T getMaxValue() {
        return maxValue;
    }

    public void setMinMaxValue(T minValue, T maxValue) {
        this.minValue = minValue;
        this.maxValue = maxValue;
        fixValue();
    }
    
    private void fixValue(){
        if(smaller(value, minValue)){
            value = minValue;
        }
        if(greater(value, maxValue)){
            value = maxValue;
        }
    }
    
    protected abstract T computeValue(double t, T minValue, T maxValue);
    protected abstract double computeNorm(T value, T minValue, T maxValue);
    protected abstract boolean greater(T a, T b);
    protected abstract boolean smaller(T a, T b);
}
