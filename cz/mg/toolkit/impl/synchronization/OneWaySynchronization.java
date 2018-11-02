package cz.mg.toolkit.impl.synchronization;


public abstract class OneWaySynchronization<T> implements Synchronization {
    private T cachedValue;
    
    @Override
    public void updateValue(){
        T value = getInternalValue();
        if(!equals(value, cachedValue)){
            setExternalValue(value);
            cachedValue = value;
        }
    }
    
    private boolean equals(T a, T b){
        if(a == b) return true;
        if(a == null) return false;
        if(b == null) return false;
        return a.equals(b);
    }
    
    public abstract T getInternalValue();
    public abstract void setExternalValue(T value);
}
