package cz.mg.collections.list.quicklist;

import cz.mg.collections.array.Array;
import cz.mg.collections.list.ListItem;
import cz.mg.collections.list.ListItemListener;
import cz.mg.collections.list.chainlist.ChainList;


public class QuickList<T> extends ChainList<T> {
    private Array<ListItem<T>> array;

    public QuickList() {
        getItemListeners().addLast(new ListItemListener<T>() {
            @Override
            public void onItemChanged(T item) {
                array = null;
            }
        });
    }
    
    @Override
    public final T get(int i) {
        if(array == null) rebuildArray();
        return array.get(i).getData();
    }

    @Override
    public final ListItem<T> getItem(int i) {
        if(array == null) rebuildArray();
        return array.get(i);
    }
    
    private void rebuildArray(){
        array = new Array<>(count());
        int i = 0;
        for(ListItem<T> item = getFirstItem(); item != null; item = item.getNextItem()){
            array.set(i, item);
            i++;
        }
    }
}
