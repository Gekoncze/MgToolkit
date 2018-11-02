package cz.mg.toolkit.impl.synchronization;


public abstract class TwoWaySynchronization<T> implements Synchronization {
    @Override
    public void updateValue() {
//        todo;
    }
    
    public abstract T getToolkitValue();
    public abstract void setToolkitValue(T value);
    public abstract T getImplValue();
    public abstract void setImplValue(T value);
}
