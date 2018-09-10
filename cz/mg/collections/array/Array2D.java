package cz.mg.collections.array;

import cz.mg.collections.Collection;
import java.util.Iterator;


public class Array2D<T> implements Collection<T> {
    private final Array<T> innerArray;
    private final int columnCount;
    private final int rowCount;

    public Array2D(int columnCount, int rowCount) {
        if(columnCount < 0 || rowCount < 0) throw new IllegalArgumentException();
        this.columnCount = columnCount;
        this.rowCount = rowCount;
        this.innerArray = new Array<>(columnCount*rowCount);
    }
    
    public T get(int x, int y){
        return innerArray.get(x+y*columnCount);
    }
    
    public void set(int x, int y, T data){
        innerArray.set(x+y*columnCount, data);
    }

    public int getColumnCount() {
        return columnCount;
    }

    public int getRowCount() {
        return rowCount;
    }
    
    @Override
    public int count() {
        return columnCount*rowCount;
    }

    @Override
    public Iterator<T> iterator() {
        return innerArray.iterator();
    }
}
