package cz.mg.collections.list.chainlist;

import cz.mg.collections.list.ListItemListener;
import cz.mg.collections.list.List;
import cz.mg.collections.list.ListItem;
import java.util.Iterator;


public class ChainList<T> implements List<T> {
    private int itemCount = 0;
    private ChainListItem<T> firstItem = null;
    private ChainListItem<T> lastItem = null;
    private ChainList<ListItemListener<T>> itemListeners = null;

    public ChainList() {
    }

    public ChainList(T data) {
        addLast(data);
    }
    
    public ChainList<ListItemListener<T>> getItemListeners() {
        if(itemListeners == null) itemListeners = new ChainList<>();
        return itemListeners;
    }

    @Override
    public final void addFirst(T data) {
        if(!onItemAddFilter(data)) return;
        if (firstItem == null) {
            rootItemAdded(new ChainListItem<>(data, this));
            onItemAdded(data);
        } else {
            firstItem.addPrevious(data);
        }
    }

    @Override
    public final void addLast(T data) {
        if(!onItemAddFilter(data)) return;
        if (lastItem == null) {
            rootItemAdded(new ChainListItem<>(data, this));
            onItemAdded(data);
        } else {
            lastItem.addNext(data);
        }
    }

    @Override
    public final void clear() {
        while (!isEmpty()) {
            removeFirst();
        }
    }

    @Override
    public final T removeFirst() {
        if (firstItem == null) return null;
        return firstItem.remove();
    }

    @Override
    public final T removeLast() {
        if (lastItem == null) return null;
        return lastItem.remove();
    }
    
    @Override
    public final T remove(T data) {
        for(ListItem<T> item = getFirstItem(); item != null; item = item.getNextItem()){
            if(item.getData() == data) return item.remove();
        }
        return null;
    }

    @Override
    public final T getFirst() {
        if (firstItem == null) return null;
        return firstItem.getData();
    }

    @Override
    public final T getLast() {
        if (lastItem == null) return null;
        return lastItem.getData();
    }

    @Override
    public T get(int i) {
        return getItem(i).getData();
    }

    @Override
    public final ListItem<T> getFirstItem() {
        return firstItem;
    }

    @Override
    public final ListItem<T> getLastItem() {
        return lastItem;
    }

    @Override
    public ListItem<T> getItem(int i) {
        if (i < 0 || i >= itemCount) return null;
        ChainListItem<T> currentItem = firstItem;
        for (int ii = 0; ii < i; ii++) currentItem = currentItem.getNextItem();
        return currentItem;
    }

    @Override
    public final int count() {
        return itemCount;
    }

    final void itemRemoved(ChainListItem<T> removedItem) {
        itemCount--;
    }

    final void firstItemRemoved(ChainListItem<T> newFirstItem, ChainListItem<T> removedItem) {
        firstItem = newFirstItem;
        itemCount--;
    }

    final void lastItemRemoved(ChainListItem<T> newLastItem, ChainListItem<T> removedItem) {
        lastItem = newLastItem;
        itemCount--;
    }

    final void rootItemRemoved(ChainListItem<T> removedItem) {
        firstItem = null;
        lastItem = null;
        itemCount = 0;
    }

    final void itemAdded(ChainListItem<T> newItem) {
        itemCount++;
    }

    final void firstItemAdded(ChainListItem<T> newItem) {
        firstItem = newItem;
        itemCount++;
    }

    final void lastItemAdded(ChainListItem<T> newItem) {
        lastItem = newItem;
        itemCount++;
    }
    
    final void rootItemAdded(ChainListItem<T> newItem) {
        firstItem = newItem;
        lastItem = newItem;
        itemCount = 1;
    }
    
    protected boolean onItemAddFilter(T data) {
        return true;
    }

    @Override
    public final Iterator<T> iterator() {
        return new Iterator<T>() {
            private ChainListItem<T> current = firstItem;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public T next() {
                T rval = current.getData();
                current = current.getNextItem();
                return rval;
            }
        };
    }

    protected void onItemRemoved(T data) {
        if(itemListeners != null) for(ListItemListener<T> listener : itemListeners) listener.onItemChanged(data);
    }
    
    protected void onItemAdded(T data) {
        if(itemListeners != null) for(ListItemListener<T> listener : itemListeners) listener.onItemChanged(data);
    }
    
    public static <T> ChainList<T> unionAll(ChainList<T> a, ChainList<T> b) {
        ChainList<T> result = new ChainList<>();
        for (T aa : a) result.addLast(aa);
        for (T bb : b) result.addLast(bb);
        return result;
    }

    public static <T> ChainList<T> unionAll(T a, ChainList<T> b) {
        ChainList<T> result = new ChainList<>();
        result.addLast(a);
        for (T bb : b) result.addLast(bb);
        return result;
    }

    public static <T> ChainList<T> unionAll(ChainList<T> a, T b) {
        ChainList<T> result = new ChainList<>();
        for (T aa : a) result.addLast(aa);
        result.addLast(b);
        return result;
    }

    public String toString(String delim) {
        StringBuilder s = new StringBuilder();
        boolean first = true;
        for (T t : this) {
            if (!first) s.append(delim);
            s.append(t);
            first = false;
        }
        return s.toString();
    }
}
