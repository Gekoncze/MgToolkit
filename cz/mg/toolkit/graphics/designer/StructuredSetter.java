package cz.mg.toolkit.graphics.designer;

import cz.mg.toolkit.component.Component;
import java.lang.reflect.Method;


public class StructuredSetter {
    private final Object[] values;
    private final Method method;

    public StructuredSetter(Object[] values, Method method) {
        this.values = new Object[values.length + 1];
        this.method = method;
        for(int i = 0; i < values.length; i++) this.values[i+1] = values[i];
    }

    public void set(Component component){
        try {
            values[0] = component;
            method.invoke(null, values);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("Could not invoke design setter: " + e.getClass().getSimpleName() + ": " + e.getMessage());
        } finally {
            values[0] = null;
        }
    }
}
