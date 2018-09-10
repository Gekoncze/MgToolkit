package cz.mg.collections.list.chainlist;

import cz.mg.collections.list.ListItem;


class ChainListItem<T> implements ListItem<T> {
    private T data = null;
    private ChainListItem<T> previousItem = null;
    private ChainListItem<T> nextItem = null;
    private ChainList<T> list = null;

    ChainListItem(T data) {
        this.data = data;
    }
    
    ChainListItem(T data, ChainList<T> list) {
        this.data = data;
        this.list = list;
    }

    @Override
    public final T getData() {
        return data;
    }

    @Override
    public final void setData(T data) {
        this.data = data;
    }
    
    @Override
    public final boolean hasNext(){
        return nextItem != null;
    }
    
    @Override
    public final boolean hasPrevious(){
        return previousItem != null;
    }
    
    @Override
    public final T getPrevious(){
        if(previousItem == null) return null;
        return previousItem.data;
    }
    
    @Override
    public final T getNext(){
        if(nextItem == null) return null;
        return nextItem.data;
    }

    @Override
    public final ChainListItem<T> getPreviousItem() {
        return previousItem;
    }

    @Override
    public final ChainListItem<T> getNextItem() {
        return nextItem;
    }

    @Override
    public final T removePrevious() {
        if(previousItem == null) return null;
        return previousItem.remove();
    }

    @Override
    public final T removeNext() {
        if(nextItem == null) return null;
        return nextItem.remove();
    }
    
    @Override
    public final T remove() {
        ChainList<T> backup = list;
        if(list == null) return null;
        if(previousItem == null && nextItem == null) list.rootItemRemoved(this);
        else if(previousItem == null) list.firstItemRemoved(nextItem, this);
        else if(nextItem == null) list.lastItemRemoved(previousItem, this);
        else list.itemRemoved(this);
        disconnectMiddle(previousItem, this, nextItem);
        backup.onItemRemoved(data);
        return data;
    }

    @Override
    public final void addPrevious(T data) {
        if(!list.onItemAddFilter(data)) return;
        ChainListItem<T> newItem = new ChainListItem<>(data);
        if(previousItem == null) list.firstItemAdded(newItem);
        else list.itemAdded(newItem);
        connectMiddle(previousItem, newItem, this);
        list.onItemAdded(data);
    }

    @Override
    public final void addNext(T data) {
        if(!list.onItemAddFilter(data)) return;
        ChainListItem<T> newItem = new ChainListItem<>(data);
        if(nextItem == null) list.lastItemAdded(newItem);
        else list.itemAdded(newItem);
        connectMiddle(this, newItem, nextItem);
        list.onItemAdded(data);
    }

    private void disconnectMiddle(ChainListItem left, ChainListItem middle, ChainListItem right) {
        middle.list = null;
        middle.previousItem = null;
        middle.nextItem = null;
        if(left != null) left.nextItem = right;
        if(right != null) right.previousItem = left;
    }

    private void connectMiddle(ChainListItem left, ChainListItem middle, ChainListItem right) {
        middle.list = left == null ? right.list : left.list;
        middle.previousItem = left;
        middle.nextItem = right;
        if(left != null) left.nextItem = middle;
        if(right != null) right.previousItem = middle;
    }
}