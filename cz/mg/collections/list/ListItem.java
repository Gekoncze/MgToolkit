package cz.mg.collections.list;


public interface ListItem<T> {
    public T getData();
    public void setData(T data);
    public boolean hasNext();
    public boolean hasPrevious();
    public T getPrevious();
    public T getNext();
    public ListItem<T> getPreviousItem();
    public ListItem<T> getNextItem();
    public T removePrevious();
    public T removeNext();
    public T remove();
    public void addPrevious(T data);
    public void addNext(T data);
}
