package cz.mg.toolkit.utilities.properties;

import java.util.HashMap;


public class Properties {
    private static final int DEFAULT_MAP_SIZE = 30;
    
    private static int currentId = 0;
    
    public static synchronized int generateId(){
        return currentId++;
    }
    
    private final HashMap<Integer,Object> map = new HashMap<>(DEFAULT_MAP_SIZE);
    
    public final void set(int id, Object value){
        map.put(id, value);
    }
    
    public final Object get(int id, Object defaultValue){
        Object value = map.get(id);
        if(value == null) value = defaultValue;
        return value;
    }
}
