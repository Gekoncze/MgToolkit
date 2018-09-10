package cz.mg.collections.array;

import cz.mg.collections.Collection;
import java.util.Iterator;


public class Array<T> implements Collection<T> {
    private final Object[] array;

    public Array(int count) {
        this.array = new Object[count];
    }
    
    @Override
    public int count(){
        return array.length;
    }
    
    public T get(int i){
        return (T) array[i];
    }
    
    public void set(int i, T data){
        array[i] = data;
    }
    
    @Override
    public Iterator<T> iterator() {
        return new Iterator() {
            private int i = 0;
            
            @Override
            public boolean hasNext() {
                return i >= 0 && i < array.length;
            }

            @Override
            public T next() {
                return (T) array[i++];
            }
        };
    }
}
