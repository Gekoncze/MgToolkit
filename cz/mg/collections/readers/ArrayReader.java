package cz.mg.collections.readers;

public abstract class ArrayReader<T> extends Reader<T, Integer> {
    private int position = -1;

    public ArrayReader() {
        super(0);
    }
    
    @Override
    public T getImpl() {
        return getImpl(position);
    }
    
    @Override
    protected T getPreviousImpl() {
        return getImpl(position - 1);
    }
    
    @Override
    protected T getNextImpl() {
        return getImpl(position + 1);
    }
    
    @Override
    protected T readNextImpl() {
        position++;
        return getImpl(position);
    }
    
    @Override
    protected T readPreviousImpl() {
        position--;
        return getImpl(position);
    }

    @Override
    public boolean has() {
        return position >= 0 && position < size();
    }
    
    @Override
    public boolean hasNext(){
        return (position + 1) >= 0 && (position + 1) < size();
    }
    
    @Override
    public boolean hasPrevious(){
        return (position - 1) >= 0 && (position - 1) < size();
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
    
    @Override
    public void setMark() {
        setMark(getPosition());
    }
    
    @Override
    public void returnToMark(){
        position = getMark();
    }
    
    protected abstract int size();
    protected abstract T getImpl(int i);
}
