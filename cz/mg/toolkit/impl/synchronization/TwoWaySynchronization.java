package cz.mg.toolkit.impl.synchronization;


public abstract class TwoWaySynchronization<T> implements Synchronization {
    private boolean internalToExternalEnabled = true;
    private boolean externalToInternalEnabled = true;
    private T cachedValue;

    public boolean isInternalToExternalEnabled() {
        return internalToExternalEnabled;
    }

    public void setInternalToExternalEnabled(boolean toolkitToEnvironmentEnabled) {
        this.internalToExternalEnabled = toolkitToEnvironmentEnabled;
    }

    public boolean isExternalToInternalEnabled() {
        return externalToInternalEnabled;
    }

    public void setExternalToInternalEnabled(boolean externalToInternalEnabled) {
        this.externalToInternalEnabled = externalToInternalEnabled;
    }
    
    @Override
    public void updateValue() {
        T iv = getInternalValue();
        T ev = getExternalValue();
        boolean internalChange = !equals(iv, cachedValue);
        boolean externalChange = !equals(ev, cachedValue);
        boolean mismatch = internalChange || externalChange;
        
        if(internalToExternalEnabled && externalToInternalEnabled){
            if(internalChange && externalChange){
                keepInternalValue(iv);
            } else if(internalChange){
                keepInternalValue(iv);
            } else if(externalChange) {
                keepExternalValue(ev);
            }
        } else if(internalToExternalEnabled) {
            if(mismatch){
                keepInternalValue(iv);
            }
        } else if(externalToInternalEnabled) {
            if(mismatch){
                keepExternalValue(ev);
            }
        }
    }
    
    private void keepInternalValue(T iv){
        setExternalValue(iv);
        cachedValue = iv;
    }
    
    private void keepExternalValue(T ev){
        setInternalValue(ev);
        cachedValue = ev;
    }
    
    private boolean equals(T a, T b){
        if(a == b) return true;
        if(a == null) return false;
        if(b == null) return false;
        return a.equals(b);
    }
    
    public abstract T getInternalValue();
    public abstract void setInternalValue(T value);
    public abstract T getExternalValue();
    public abstract void setExternalValue(T value);
}
