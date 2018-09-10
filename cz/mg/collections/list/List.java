package cz.mg.collections.list;

import cz.mg.collections.Collection;


public interface List<T> extends Collection<T> {
    public void addFirst(T data);
    public void addLast(T data);
    public void clear();
    public T removeFirst();
    public T removeLast();
    public T remove(T data);
    public T getFirst();
    public T getLast();
    public T get(int i);
    public ListItem<T> getFirstItem();
    public ListItem<T> getLastItem();
    public ListItem<T> getItem(int i);
    
    public default boolean isEmpty(){
        return count() <= 0;
    }
}
