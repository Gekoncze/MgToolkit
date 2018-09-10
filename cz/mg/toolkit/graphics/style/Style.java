package cz.mg.toolkit.graphics.style;

import java.util.HashMap;


public class Style {
    private static int currentId = 0;
    
    public static synchronized int generateId(){
        return currentId++;
    }
    
    private final HashMap<Integer,Object> map = new HashMap<>(20);
    
    public final void set(int id, Object value){
        map.put(id, value);
    }
    
    public final Object get(int id, Object defaultValue){
        Object value = map.get(id);
        if(value == null) value = defaultValue;
        return value;
    }
}
