package cz.mg.toolkit.impl.synchronization;


public abstract class OneWaySynchronization<T> implements Synchronization {
    private T cachedValue;
    
    @Override
    public void updateValue(){
        T value = getToolkitValue();
        if(value != cachedValue) setImplValue(value);
    }
    
    public abstract T getToolkitValue();
    public abstract void setImplValue(T value);
}
