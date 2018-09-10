package cz.mg.collections;


public interface Collection<T> extends Iterable<T> {
    public int count();
    
    public default String toString(String delim) {
        StringBuilder s = new StringBuilder();
        boolean first = true;
        for (Object o : this) {
            if (!first){
                first = false;
                s.append(delim);
            }
            s.append(o);
        }
        return s.toString();
    }
}
