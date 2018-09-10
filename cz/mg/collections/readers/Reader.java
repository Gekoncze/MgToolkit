package cz.mg.collections.readers;


public abstract class Reader<T, M> {
    private M mark;
    
    protected Reader(M mark) {
        setMark(mark);
    }

    public final void setMark(M mark) {
        this.mark = mark;
    }

    public final M getMark() {
        return mark;
    }
    
    public final T get() {
        if(!has()) throw outOfBoundsException();
        return getImpl();
    }
    
    public final T getOptional() {
        if(!has()) return null;
        return getImpl();
    }

    public final T getNext() {
        if(!hasNext()) throw outOfBoundsException();
        return getNextImpl();
    }
    
    public final T getNextOptional() {
        if(!hasNext()) return null;
        return getNextImpl();
    }

    public final T getPrevious() {
        if(!hasPrevious()) throw outOfBoundsException();
        return getPreviousImpl();
    }
    
    public final T getPreviousOptional() {
        if(!hasPrevious()) return null;
        return getPreviousImpl();
    }

    public final T readNext() {
        if(!hasNext()) throw outOfBoundsException();
        return readNextImpl();
    }
    
    public final T readNextOptional() {
        if(!hasNext()) return null;
        return readNextImpl();
    }

    public final T readPrevious() {
        if(!hasPrevious()) throw outOfBoundsException();
        return readPreviousImpl();
    }
    
    public final T readPreviousOptional() {
        if(!hasPrevious()) return null;
        return readPreviousImpl();
    }
    
    public abstract boolean has();
    public abstract boolean hasNext();
    public abstract boolean hasPrevious();
    public abstract void setMark();
    public abstract void returnToMark();
    
    protected abstract T getImpl();
    protected abstract T getNextImpl();
    protected abstract T getPreviousImpl();
    protected abstract T readNextImpl();
    protected abstract T readPreviousImpl();
    
    protected abstract RuntimeException outOfBoundsException();
}
