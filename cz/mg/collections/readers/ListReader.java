package cz.mg.collections.readers;

import cz.mg.collections.list.List;
import cz.mg.collections.list.ListItem;


public abstract class ListReader<T> extends Reader<T, ListItem<T>> {
    private final List<T> list;
    private ListItem<T> current = null;
    
    public ListReader(List<T> list){
        super(null);
        this.list = list;
    }
    
    public int size(){
        return list.count();
    }
    
    @Override
    protected T getImpl() {
        return current.getData();
    }

    @Override
    protected T getPreviousImpl() {
        return current.getPrevious();
    }

    @Override
    protected T getNextImpl() {
        if(current == null) return list.getFirst();
        return current.getNext();
    }

    @Override
    protected T readNextImpl() {
        if(current == null){
            current = list.getFirstItem();
        } else {
            current = current.getNextItem();
        }
        return current.getData();
    }

    @Override
    protected T readPreviousImpl() {
        current = current.getPreviousItem();
        return current.getData();
    }

    @Override
    public boolean has() {
        return current != null;
    }

    @Override
    public boolean hasNext() {
        if(current == null) return list.getFirstItem() != null;
        return current.getNextItem() != null;
    }

    @Override
    public boolean hasPrevious() {
        if(current == null) return false;
        return current.getPreviousItem() != null;
    }

    @Override
    public void setMark() {
        setMark(current);
    }

    @Override
    public void returnToMark() {
        current = getMark();
    }
}
